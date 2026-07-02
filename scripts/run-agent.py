#!/usr/bin/env python3
"""Run a generated AI Native SDLC agent prompt with a configured CLI runner."""

from __future__ import annotations

import argparse
import json
import os
import shlex
import shutil
import subprocess
import sys
from pathlib import Path
from typing import Any


RUNNER_ALIASES = {
    "claude-code": "claude",
    "claude_code": "claude",
    "cursor-agent": "cursor",
    "cursor_agent": "cursor",
}


def relpath(path: Path, start: Path | None = None) -> str:
    start = start or Path.cwd()
    try:
        return os.path.relpath(path.resolve(), start.resolve())
    except ValueError:
        return str(path.resolve())


def load_json(path: Path) -> dict[str, Any]:
    if not path.is_file():
        raise SystemExit(f"File not found: {path}")
    try:
        return json.loads(path.read_text(encoding="utf-8"))
    except json.JSONDecodeError as exc:
        raise SystemExit(f"Invalid JSON file {relpath(path)}: {exc}") from exc


def array_value(value: Any) -> list[str]:
    if value is None:
        return []
    if isinstance(value, list):
        return [str(item) for item in value]
    return [str(value)]


def bool_value(value: Any) -> bool:
    if isinstance(value, bool):
        return value
    if value is None:
        return False
    return str(value).strip().lower() in {"1", "true", "yes", "y", "on"}


def resolve_config(config_arg: str | None, run_dir: Path) -> Path:
    if config_arg:
        return Path(config_arg).expanduser().resolve()
    if os.environ.get("AI_SDLC_CONFIG"):
        return Path(os.environ["AI_SDLC_CONFIG"]).expanduser().resolve()

    state_file = run_dir / "state.json"
    if state_file.is_file():
        state = load_json(state_file)
        config_path = state.get("configPath")
        if config_path:
            candidate = Path(config_path).expanduser()
            if candidate.is_absolute():
                return candidate
            cwd_candidate = (Path.cwd() / candidate).resolve()
            if cwd_candidate.is_file():
                return cwd_candidate
            return (run_dir / candidate).resolve()

    return Path(".ai-sdlc.json").resolve()


def runner_env_name(runner_key: str, suffix: str) -> str:
    return f"AI_SDLC_{runner_key.upper().replace('-', '_')}_{suffix}"


def runner_setting(runner_key: str, runner: dict[str, Any], key: str, env_suffix: str | None = None) -> Any:
    env_name = runner_env_name(runner_key, env_suffix or key.upper())
    if os.environ.get(env_name) is not None:
        return os.environ[env_name]
    return runner.get(key)


def prompt_text(run_dir: Path) -> str:
    prompt_file = run_dir / "agent-prompt.md"
    if not prompt_file.is_file():
        raise SystemExit(f"Prompt file not found: {prompt_file}")
    return prompt_file.read_text(encoding="utf-8")


def record_state(script_dir: Path, run_dir: Path, status: str, summary: str, runner_key: str) -> None:
    subprocess.run(
        [
            sys.executable,
            str(script_dir / "record-agent-state.py"),
            "--run-dir",
            str(run_dir),
            "--status",
            status,
            "--summary",
            summary,
            "--actor",
            f"{runner_key} runner",
            "--runner",
            runner_key,
        ],
        check=True,
    )


def executable_path(executable: str) -> str:
    resolved = shutil.which(executable)
    if not resolved:
        raise SystemExit(f"Agent runner executable not found on PATH: {executable}")
    return resolved


def build_codex_command(runner_key: str, runner: dict[str, Any], workdir: Path, prompt: str, extra_args: list[str]) -> list[str]:
    executable = executable_path(str(runner.get("executable") or "codex"))
    sandbox = os.environ.get("CODEX_AGENT_SANDBOX") or runner_setting(runner_key, runner, "sandbox") or "workspace-write"
    approval = os.environ.get("CODEX_AGENT_APPROVAL_POLICY") or runner_setting(runner_key, runner, "approvalPolicy", "APPROVAL_POLICY") or "never"
    model = os.environ.get("CODEX_AGENT_MODEL") or runner_setting(runner_key, runner, "model")

    command = [executable, "exec", "-C", str(workdir), "--sandbox", str(sandbox), "--ask-for-approval", str(approval)]
    if model:
        command.extend(["--model", str(model)])
    command.extend(array_value(runner.get("extraArgs")))
    command.extend(extra_args)
    command.append(prompt)
    return command


def build_claude_command(runner_key: str, runner: dict[str, Any], _workdir: Path, prompt: str, extra_args: list[str]) -> list[str]:
    executable = executable_path(str(runner.get("executable") or "claude"))
    output_format = runner_setting(runner_key, runner, "outputFormat", "OUTPUT_FORMAT") or "text"
    permission_mode = os.environ.get("CLAUDE_AGENT_PERMISSION_MODE") or runner_setting(runner_key, runner, "permissionMode", "PERMISSION_MODE")
    model = os.environ.get("CLAUDE_AGENT_MODEL") or runner_setting(runner_key, runner, "model")

    command = [executable, "--print", "--output-format", str(output_format)]
    if permission_mode:
        command.extend(["--permission-mode", str(permission_mode)])
    if bool_value(runner.get("noSessionPersistence", True)):
        command.append("--no-session-persistence")
    if model:
        command.extend(["--model", str(model)])
    command.extend(array_value(runner.get("extraArgs")))
    command.extend(extra_args)
    command.append(prompt)
    return command


def build_cursor_command(runner_key: str, runner: dict[str, Any], workdir: Path, prompt: str, extra_args: list[str]) -> list[str]:
    executable = executable_path(str(runner.get("executable") or "cursor-agent"))
    output_format = runner_setting(runner_key, runner, "outputFormat", "OUTPUT_FORMAT") or "text"
    sandbox = runner_setting(runner_key, runner, "sandbox")
    model = os.environ.get("CURSOR_AGENT_MODEL") or runner_setting(runner_key, runner, "model")
    mode = runner_setting(runner_key, runner, "mode")

    command = [executable, "--print", "--output-format", str(output_format), "--workspace", str(workdir)]
    if sandbox:
        command.extend(["--sandbox", str(sandbox)])
    if bool_value(runner.get("trustWorkspace", True)):
        command.append("--trust")
    if bool_value(runner_setting(runner_key, runner, "force")):
        command.append("--force")
    if model:
        command.extend(["--model", str(model)])
    if mode:
        command.extend(["--mode", str(mode)])
    command.extend(array_value(runner.get("extraArgs")))
    command.extend(extra_args)
    command.append(prompt)
    return command


def build_command(runner_key: str, runner: dict[str, Any], workdir: Path, prompt: str, extra_args: list[str]) -> list[str]:
    runner_type = str(runner.get("type") or runner_key)
    if runner_type == "codex":
        return build_codex_command(runner_key, runner, workdir, prompt, extra_args)
    if runner_type == "claude":
        return build_claude_command(runner_key, runner, workdir, prompt, extra_args)
    if runner_type == "cursor":
        return build_cursor_command(runner_key, runner, workdir, prompt, extra_args)
    raise SystemExit(f"Unsupported runner type '{runner_type}' for runner '{runner_key}'.")


def parse_args() -> argparse.Namespace:
    parser = argparse.ArgumentParser(
        description="Run a generated AI Native SDLC agent prompt with a configured CLI runner.",
    )
    parser.add_argument("run_dir", help="Agent run folder containing agent-prompt.md and state.json.")
    parser.add_argument("--config", default=None, help="Runner config path. Defaults to AI_SDLC_CONFIG or .ai-sdlc.json.")
    parser.add_argument("--runner", default=None, help="Runner key from config, e.g. codex, claude, cursor, manual.")
    parser.add_argument("--workdir", default=None, help="Working directory for the agent. Defaults to repo root.")
    parser.add_argument("--runner-arg", action="append", default=[], help="Extra argument passed through to the selected runner. Repeatable.")
    parser.add_argument("--dry-run", action="store_true", help="Resolve and print the runner command without executing it.")
    return parser.parse_args()


def main() -> int:
    args = parse_args()
    script_dir = Path(__file__).resolve().parent
    repo_root = script_dir.parent
    run_dir = Path(args.run_dir).expanduser().resolve()
    config_path = resolve_config(args.config, run_dir)
    config = load_json(config_path)

    runner_key = args.runner or os.environ.get("AI_SDLC_RUNNER") or config.get("defaultRunner") or "codex"
    runner_key = RUNNER_ALIASES.get(str(runner_key), str(runner_key))
    runners = config.get("runners", {})
    runner = runners.get(runner_key)
    if not runner:
        known = ", ".join(sorted(runners.keys())) or "<none configured>"
        raise SystemExit(f"Runner '{runner_key}' is not configured in {relpath(config_path)}. Known runners: {known}")

    if runner.get("type") == "manual":
        print(f"Manual runner selected. Paste {run_dir / 'agent-prompt.md'} into your agent tool and keep state in {run_dir}.")
        record_state(script_dir, run_dir, "manual_prompt_ready", "Manual runner selected; prompt is ready for external execution", runner_key)
        return 0

    workdir = Path(args.workdir or os.environ.get("AI_SDLC_AGENT_WORKDIR") or os.environ.get("CODEX_AGENT_WORKDIR") or repo_root).expanduser().resolve()
    prompt = prompt_text(run_dir)
    transcript_file = run_dir / "transcript.log"
    command = build_command(runner_key, runner, workdir, prompt, args.runner_arg)

    if args.dry_run:
        printable = [*command[:-1], "[agent prompt omitted]"]
        print(f"Runner: {runner_key}")
        print(f"Working directory: {workdir}")
        print("Command:")
        print(" ".join(shlex.quote(part) for part in printable))
        return 0

    record_state(script_dir, run_dir, "in_progress", f"{runner_key} runner execution started", runner_key)

    with transcript_file.open("w", encoding="utf-8") as transcript:
        transcript.write(f"# Runner: {runner_key}\n")
        transcript.write(f"# Working directory: {workdir}\n\n")
        process = subprocess.Popen(
            command,
            cwd=str(workdir),
            stdout=subprocess.PIPE,
            stderr=subprocess.STDOUT,
            text=True,
            bufsize=1,
        )
        assert process.stdout is not None
        for line in process.stdout:
            sys.stdout.write(line)
            transcript.write(line)
        status = process.wait()

    if status == 0:
        record_state(script_dir, run_dir, "agent_execution_finished", f"{runner_key} runner finished; human review still required", runner_key)
    else:
        record_state(script_dir, run_dir, "agent_execution_failed", f"{runner_key} runner failed with exit code {status}", runner_key)
    return status


if __name__ == "__main__":
    raise SystemExit(main())

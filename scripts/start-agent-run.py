#!/usr/bin/env python3
"""Create a durable AI Native SDLC agent run folder."""

from __future__ import annotations

import argparse
import json
import os
import re
import sys
from datetime import datetime, timezone
from pathlib import Path
from typing import Any


def utc_now() -> str:
    return datetime.now(timezone.utc).replace(microsecond=0).isoformat().replace("+00:00", "Z")


def slugify(value: str) -> str:
    return re.sub(r"(^-+|-+$)", "", re.sub(r"[^a-z0-9]+", "-", value.lower()))


def array_value(value: Any) -> list[Any]:
    if value is None:
        return []
    if isinstance(value, list):
        return value
    return [value]


def relpath(path: Path, start: Path | None = None) -> str:
    start = start or Path.cwd()
    try:
        return os.path.relpath(path.resolve(), start.resolve())
    except ValueError:
        return str(path.resolve())


def expand_from(base: Path, path: str) -> Path:
    candidate = Path(path)
    if candidate.is_absolute():
        return candidate
    return (base / candidate).resolve()


def load_config(path: Path) -> dict[str, Any]:
    if not path.is_file():
        raise SystemExit(
            f"Runner config not found: {path}\n"
            "Create one from docs/templates/ai-sdlc-run-config.json or pass --config."
        )
    try:
        return json.loads(path.read_text(encoding="utf-8"))
    except json.JSONDecodeError as exc:
        raise SystemExit(f"Invalid JSON config {relpath(path)}: {exc}") from exc


def find_agent(config: dict[str, Any], agent_input: str) -> tuple[str, dict[str, Any]]:
    agents = config.get("agents", {})
    input_slug = slugify(agent_input)
    for key, agent_config in agents.items():
        names = [key, agent_config.get("displayName", ""), *array_value(agent_config.get("aliases"))]
        if any(slugify(str(name)) == input_slug for name in names):
            return key, agent_config

    return input_slug or "custom", {
        "displayName": agent_input,
        "aliases": [agent_input],
        "reviewer": config.get("defaultReviewer"),
        "requiredSpecs": [],
    }


def write_markdown(path: Path, lines: list[str]) -> None:
    path.write_text("\n".join(lines) + "\n", encoding="utf-8")


def parse_args() -> argparse.Namespace:
    parser = argparse.ArgumentParser(
        description="Create a durable AI Native SDLC agent run folder.",
        formatter_class=argparse.RawDescriptionHelpFormatter,
        epilog=(
            "Example:\n"
            "  scripts/start-agent-run.sh \\\n"
            "    --agent coding \\\n"
            "    --task \"Implement the approved policy assistant feature from the AI specs\""
        ),
    )
    parser.add_argument("--config", default=os.environ.get("AI_SDLC_CONFIG", ".ai-sdlc.json"))
    parser.add_argument("--use-case")
    parser.add_argument("--agent", required=True)
    parser.add_argument("--task", required=True)
    parser.add_argument("--reviewer")
    parser.add_argument("--command", action="append", dest="commands", default=[])
    parser.add_argument("--run-id")
    parser.add_argument("--allow-missing-specs", action="store_true")
    return parser.parse_args()


def main() -> int:
    args = parse_args()
    config_path = Path(args.config).expanduser().resolve()
    config_dir = config_path.parent
    config = load_config(config_path)

    use_case_config = args.use_case or config.get("defaultUseCase")
    if not use_case_config:
        raise SystemExit(f"No use case path supplied. Pass --use-case or set defaultUseCase in {relpath(config_path)}.")

    use_case_path = expand_from(config_dir, str(use_case_config))
    if not use_case_path.is_dir():
        raise SystemExit(f"Use case folder not found: {relpath(use_case_path)}")

    agent_key, agent_config = find_agent(config, args.agent)
    agent_display = agent_config.get("displayName") or args.agent
    agent_slug = slugify(str(agent_display))
    reviewer = args.reviewer or agent_config.get("reviewer") or config.get("defaultReviewer") or "Accountable human reviewer"
    run_id = args.run_id or f"{datetime.now(timezone.utc).strftime('%Y%m%dT%H%M%SZ')}-{agent_slug}"
    run_dir_name = config.get("runDirectory", "16-agent-runs")
    run_root = expand_from(use_case_path, str(run_dir_name))
    run_dir = run_root / run_id

    if run_dir.exists():
        raise SystemExit(f"Run already exists: {relpath(run_dir)}")

    verification_commands = args.commands or array_value(
        config.get("defaultVerificationCommands") or config.get("defaultVerificationCommand")
    )
    if not verification_commands:
        verification_commands = ["[No verification command configured]"]

    common_specs = array_value(config.get("commonRequiredSpecs"))
    agent_specs = array_value(agent_config.get("requiredSpecs"))
    required_specs = []
    for spec in [*common_specs, *agent_specs]:
        spec_path = expand_from(use_case_path, str(spec))
        if spec_path not in required_specs:
            required_specs.append(spec_path)

    missing_specs = [path for path in required_specs if not path.is_file()]
    missing_policy = "warn" if args.allow_missing_specs else config.get("missingSpecPolicy", "fail")
    if missing_specs and missing_policy == "fail":
        print(f"Required specs are missing for {agent_display}:", file=sys.stderr)
        for path in missing_specs:
            print(f"  - {relpath(path)}", file=sys.stderr)
        print("Use --allow-missing-specs to create the run anyway.", file=sys.stderr)
        return 1
    if missing_specs:
        print(f"Warning: required specs are missing for {agent_display}:", file=sys.stderr)
        for path in missing_specs:
            print(f"  - {relpath(path)}", file=sys.stderr)

    run_dir.mkdir(parents=True)
    now = utc_now()
    required_specs_for_state = [relpath(path) for path in required_specs]
    run_dir_for_state = relpath(run_dir)
    use_case_for_state = relpath(use_case_path)
    config_for_state = relpath(config_path)

    (run_dir / "required-specs.txt").write_text("\n".join(required_specs_for_state) + "\n", encoding="utf-8")

    state_machine = config.get("stateMachine", {})
    initial_status = state_machine.get("initialStatus", "ready_for_agent")
    state = {
        "schemaVersion": "1.0",
        "configPath": config_for_state,
        "defaultRunner": config.get("defaultRunner", "codex"),
        "runId": run_id,
        "useCasePath": use_case_for_state,
        "agent": agent_display,
        "agentKey": agent_key,
        "agentSlug": agent_slug,
        "status": initial_status,
        "reviewer": reviewer,
        "createdAtUtc": now,
        "updatedAtUtc": now,
        "task": args.task,
        "verificationCommands": verification_commands,
        "stateFiles": {
            "prompt": "agent-prompt.md",
            "handoff": "handoff.md",
            "review": "review.md",
            "transcript": "transcript.log",
            "events": "events.md",
        },
        "requiredSpecs": required_specs_for_state,
    }
    (run_dir / "state.json").write_text(json.dumps(state, indent=2) + "\n", encoding="utf-8")

    prompt_lines = [
        "# Agent Prompt",
        "",
        f"You are the {agent_display} for the AI Native SDLC use case at `{use_case_for_state}`.",
        "",
        "## Task",
        "",
        args.task,
        "",
        "## Required State Discipline",
        "",
        f"1. Read `{run_dir_for_state}/state.json` first.",
        f"2. Read every file in `{run_dir_for_state}/required-specs.txt` before changing anything.",
        "3. Record progress with:",
        "",
        "```bash",
        f"ai-sdlc state --run-dir \"{run_dir_for_state}\" --status in_progress --summary \"Started work\"",
        "```",
        "",
        "4. Stay inside the allowed scope defined by the role/agent operating model and generation plans.",
        "5. Do not approve your own work.",
        "6. Do not remove guardrails, tests, audit logging, or human review boundaries.",
        f"7. When finished, update `{run_dir_for_state}/handoff.md` with files changed, commands run, test results, risks, and review request.",
        "8. Then record final agent state:",
        "",
        "```bash",
        f"ai-sdlc state --run-dir \"{run_dir_for_state}\" --status ready_for_human_review --summary \"Agent work complete\"",
        "```",
        "",
        "## Required Specs",
        "",
        *[f"- {spec}" for spec in required_specs_for_state],
        "",
        "## Verification Commands",
        "",
        "```bash",
        *[str(command) for command in verification_commands],
        "```",
    ]
    write_markdown(run_dir / "agent-prompt.md", prompt_lines)

    handoff_lines = [
        "# Agent Handoff",
        "",
        "| Field | Value |",
        "| --- | --- |",
        f"| Run ID | {run_id} |",
        f"| Agent | {agent_display} |",
        f"| Human reviewer | {reviewer} |",
        "| Status | Draft |",
        "",
        "## Summary",
        "",
        "[Agent fills in what changed and why.]",
        "",
        "## Files Changed",
        "",
        "- [ ] [path]",
        "",
        "## Commands Run",
        "",
        *[f"- [ ] `{command}`" for command in verification_commands],
        "",
        "## Test / Eval Result",
        "",
        "[Agent fills in result and relevant output.]",
        "",
        "## Risks / Follow-ups",
        "",
        "- None recorded yet.",
        "",
        "## Review Request",
        "",
        f"Reviewer: {reviewer}",
    ]
    write_markdown(run_dir / "handoff.md", handoff_lines)

    review_lines = [
        "# Human Review",
        "",
        "| Field | Value |",
        "| --- | --- |",
        f"| Run ID | {run_id} |",
        f"| Reviewer | {reviewer} |",
        "| Decision | Pending |",
        "| Reviewed at | |",
        "",
        "## Checklist",
        "",
        "- [ ] Agent read required specs.",
        "- [ ] Changed files are within allowed scope.",
        "- [ ] Guardrails, tests, audit logging, and human review boundaries remain intact.",
        "- [ ] Required verification commands passed or failure is explained.",
        "- [ ] Handoff includes files changed, commands run, risks, and follow-ups.",
        "",
        "## Decision Notes",
        "",
        "[Human reviewer fills in approval, requested changes, or rejection rationale.]",
    ]
    write_markdown(run_dir / "review.md", review_lines)

    events_lines = [
        "# Agent Run Events",
        "",
        f"## {now} - {initial_status}",
        "",
        f"Run created for {agent_display}. Human reviewer: {reviewer}.",
    ]
    write_markdown(run_dir / "events.md", events_lines)
    (run_dir / "transcript.log").touch()

    print(
        f"""Created agent run:
  {run_dir_for_state}

Config:
  {config_for_state}

Agent prompt:
  {run_dir_for_state}/agent-prompt.md

Run with the configured default runner:
  ai-sdlc run "{run_dir_for_state}"

Run with another supported runner:
  ai-sdlc run --runner claude "{run_dir_for_state}"
  ai-sdlc run --runner cursor "{run_dir_for_state}"

Unix fallback:
  scripts/run-codex-agent.sh "{run_dir_for_state}"

Or paste the prompt into another agent and keep state in:
  {run_dir_for_state}/state.json"""
    )
    return 0


if __name__ == "__main__":
    raise SystemExit(main())

# Agent CLI Prerequisites And Errors

This guide lists the local tools, authentication, API keys, and common errors needed to run AI Native SDLC agent workflows.

Use this together with:

- [`../README.md`](../README.md)
- [`.ai-sdlc.json`](../.ai-sdlc.json)
- [`templates/ai-sdlc-run-config.json`](./templates/ai-sdlc-run-config.json)
- [`ai-sdlc-cli-how-to.md`](./ai-sdlc-cli-how-to.md)

## Required Local Tools

| Tool | Required for | Validation command |
| --- | --- | --- |
| Python 3.10+ | `ai-sdlc` Python runner scripts | `python3 --version` |
| Bash | Linux/macOS wrappers and Make targets | `bash --version` |
| PowerShell | Windows install and `ai-sdlc.cmd` shim | `powershell -NoProfile -Command "$PSVersionTable.PSVersion"` |
| Make | Convenience targets on Linux/macOS | `make help` |
| Java 25 | Spring Boot sample verification | `java -version` |
| Gradle wrapper | Spring Boot sample verification | `cd sample/policy-assistant && ./gradlew test` |
| Git | Reviewing diffs and agent output | `git status --short` |

Windows users can use PowerShell directly and do not need `make` for the CLI:

```powershell
.\scripts\install-cli.ps1
ai-sdlc help
```

## Agent CLI Prerequisites

The framework supports four runner modes:

| Runner | CLI executable | Required account or key | Config key |
| --- | --- | --- | --- |
| Codex | `codex` | Codex/ChatGPT login or OpenAI API key, depending on your Codex setup | `runners.codex` |
| Claude Code | `claude` | Claude account login, Anthropic Console key, or supported enterprise token flow | `runners.claude` |
| Cursor Agent | `cursor-agent` | Cursor login or `CURSOR_API_KEY` | `runners.cursor` |
| Manual | none | No CLI account; human copies prompt into approved tool | `runners.manual` |

Validate availability:

```bash
command -v codex || true
command -v claude || true
command -v cursor-agent || true
```

Dry-run each configured runner before execution:

```bash
ai-sdlc run --runner codex --dry-run <run-dir>
ai-sdlc run --runner claude --dry-run <run-dir>
ai-sdlc run --runner cursor --dry-run <run-dir>
```

## API Keys And Login

Do not commit API keys to the repository. Store them in your shell profile, CI secret store, password manager, or enterprise secret manager.

### Codex

Prerequisites:

- `codex` available on `PATH`
- Authenticated Codex session or OpenAI API-key configuration supported by your Codex installation
- Network access to the OpenAI service

Checks:

```bash
codex --help
codex exec --help
```

Common environment or setup considerations:

| Item | Notes |
| --- | --- |
| ChatGPT login | Recommended for interactive local use when supported by your Codex installation |
| `OPENAI_API_KEY` | Use only when your Codex setup expects API-key authentication |
| `CODEX_AGENT_SANDBOX` | Overrides `.ai-sdlc.json` Codex sandbox |
| `CODEX_AGENT_APPROVAL_POLICY` | Overrides `.ai-sdlc.json` approval policy |
| `CODEX_AGENT_MODEL` | Optional model override |

Official reference: [OpenAI Codex docs](https://developers.openai.com/codex/)

### Claude Code

Prerequisites:

- `claude` available on `PATH`
- Claude Code authenticated with a Claude account, Anthropic Console API key, or enterprise token flow
- Network access to Anthropic services

Checks:

```bash
claude --help
claude doctor
```

Common environment or setup considerations:

| Item | Notes |
| --- | --- |
| Claude account login | Use local login for normal workstation usage |
| `ANTHROPIC_API_KEY` | API-key path for Anthropic Console usage |
| `ANTHROPIC_AUTH_TOKEN` | Enterprise or managed auth token path when provided by your platform team |
| `CLAUDE_CODE_OAUTH_TOKEN` | CI/CD token path produced by Claude Code setup-token flows |
| `CLAUDE_AGENT_PERMISSION_MODE` | Overrides runner permission mode; use `plan` for read-only planning |

Official references:

- [Claude Code overview](https://docs.anthropic.com/en/docs/claude-code/overview)
- [Claude Code authentication](https://docs.anthropic.com/en/docs/claude-code/iam)
- [Claude Code CLI reference](https://docs.anthropic.com/en/docs/claude-code/cli-reference)

### Cursor Agent

Prerequisites:

- `cursor-agent` available on `PATH`
- Cursor login or `CURSOR_API_KEY`
- Network access to Cursor services

Checks:

```bash
cursor-agent --help
cursor-agent status
```

Common environment or setup considerations:

| Item | Notes |
| --- | --- |
| Cursor login | Use `cursor-agent login` for local interactive authentication |
| `CURSOR_API_KEY` | API-key path for headless usage |
| `CURSOR_AGENT_MODEL` | Optional model override used by this framework runner |
| Runner sandbox | `.ai-sdlc.json` defaults Cursor to sandbox enabled and no `--force` |

Official references:

- [Cursor CLI overview](https://cursor.com/docs/cli/overview)
- [Cursor CLI authentication](https://cursor.com/docs/cli/reference/authentication)
- [Cursor Agent parameters](https://cursor.com/docs/cli/reference/parameters)

### Manual Runner

Manual mode requires no AI CLI or API key.

Use it when:

- The target agent CLI is not installed
- The API key cannot be used on the current machine
- Governance requires a human to paste the prompt into an approved tool
- You want to validate state flow without spending tokens

```bash
ai-sdlc run --runner manual <run-dir>
```

## Framework Error Messages

These errors come from the AI Native SDLC scripts.

| Message | Likely cause | Fix |
| --- | --- | --- |
| `ai-sdlc: command not found` | CLI was not installed or install directory is not on `PATH` | Run `make install-cli` on Linux/macOS or `.\scripts\install-cli.ps1` on Windows; add install directory to `PATH` |
| `Runner config not found: ...` | `.ai-sdlc.json` cannot be resolved | Run from repo root, set `AI_SDLC_CONFIG`, or pass `--config` |
| `Invalid JSON config ...` | `.ai-sdlc.json` has invalid JSON | Run `python3 -m json.tool .ai-sdlc.json` and fix syntax |
| `No use case path supplied` | Config lacks `defaultUseCase` and `--use-case` was not passed | Add `defaultUseCase` or pass `--use-case` |
| `Use case folder not found: ...` | Config points to a missing governance packet | Create the folder or correct `defaultUseCase` / `--use-case` |
| `Required specs are missing for ...` | Required files in `commonRequiredSpecs` or `agents.*.requiredSpecs` do not exist | Add the missing docs, update config, or use `--allow-missing-specs` for draft work |
| `Run already exists: ...` | The same `--run-id` was reused | Use a new run id or remove the old smoke run folder |
| `Prompt file not found: ...` | `ai-sdlc run` was pointed at a folder without `agent-prompt.md` | Run `ai-sdlc start` first or use the correct run folder |
| `State file not found: .../state.json` | `ai-sdlc state` was pointed at a folder without state | Use the generated run folder from `ai-sdlc start` |
| `Runner 'x' is not configured` | `--runner` does not match `.ai-sdlc.json` | Use `codex`, `claude`, `cursor`, `manual`, or add a runner config |
| `Agent runner executable not found on PATH: ...` | Selected CLI is not installed or not on `PATH` | Install the CLI, fix `PATH`, update `.ai-sdlc.json`, or use `--runner manual` |

## Agent CLI Error Patterns

Exact wording varies by CLI version and operating system.

| Pattern | Applies to | Likely cause | Fix |
| --- | --- | --- | --- |
| `command not found: codex` | Codex | Codex CLI missing from `PATH` | Install Codex CLI or fix `PATH`; use `ai-sdlc run --runner manual` meanwhile |
| `command not found: claude` | Claude Code | Claude CLI missing from `PATH` | Install Claude Code and validate with `claude --help` |
| `command not found: cursor-agent` | Cursor Agent | Cursor Agent CLI missing from `PATH` | Install Cursor Agent and validate with `cursor-agent --help` |
| `401`, `Unauthorized`, `invalid_api_key` | Any API-key runner | Missing, expired, or wrong API key | Re-authenticate, rotate key, confirm environment variable is set in the current shell |
| `403`, `Forbidden`, `permission denied` | Any runner | Account lacks model/tool/org access | Confirm plan, org, model access, and enterprise policy |
| `429`, `rate_limit`, `quota` | Any hosted runner | Rate limit or quota exceeded | Retry later, use a different account/model, or increase quota |
| `model not found` | Any hosted runner | Configured model unavailable | Remove model override or choose an available model |
| Workspace trust prompt or refusal | Cursor Agent | Workspace not trusted in headless mode | Use configured `trustWorkspace`, run `--dry-run`, or trust the workspace manually |
| `Authentication required. Please run 'agent login' first, or set CURSOR_API_KEY environment variable.` | Cursor Agent | Cursor Agent is installed but not authenticated | Run `cursor-agent login`, or set `CURSOR_API_KEY` in the current shell or CI secret store |
| Permission prompt blocks execution | Claude Code | Permission mode requires approval | Use `CLAUDE_AGENT_PERMISSION_MODE=plan` for planning or configure the intended permission mode |
| Sandbox denies file or command access | Codex/Cursor | Runner sandbox too restrictive | Prefer fixing task scope; only loosen sandbox through approved config |
| TLS/proxy/network errors | Any hosted runner | Corporate proxy, VPN, or certificate issue | Configure proxy/certs according to company policy |
| Agent modifies unexpected files | Any runner | Prompt/config scope too broad | Stop the run, inspect `transcript.log`, request changes, tighten required specs and runner permissions |

### Cursor Authentication Failure Example

Example output:

```text
Recorded state 'in_progress' in sample/ai-docs/policy-assistant/16-agent-runs/20260702T222455Z-coding-agent
Error: Authentication required. Please run 'agent login' first, or set CURSOR_API_KEY environment variable.
Recorded state 'agent_execution_failed' in sample/ai-docs/policy-assistant/16-agent-runs/20260702T222455Z-coding-agent
```

Meaning:

| Line | Meaning |
| --- | --- |
| `Recorded state 'in_progress'` | The framework successfully started the runner and updated `state.json` |
| `Authentication required...` | Cursor Agent rejected execution before doing agent work |
| `Recorded state 'agent_execution_failed'` | The framework captured the failed run state for review |

Fix locally:

```bash
cursor-agent login
cursor-agent status
ai-sdlc run --runner cursor <run-dir>
```

Fix for headless or CI execution:

```bash
export CURSOR_API_KEY="..."
cursor-agent status
ai-sdlc run --runner cursor <run-dir>
```

Safer fallback while authentication is being fixed:

```bash
ai-sdlc run --runner manual <run-dir>
```

## Validation Commands

Validate framework scripts and config:

```bash
python3 -m py_compile scripts/start-agent-run.py scripts/record-agent-state.py scripts/run-agent.py
python3 -m json.tool .ai-sdlc.json >/dev/null
python3 -m json.tool docs/templates/ai-sdlc-run-config.json >/dev/null
bash -n scripts/start-agent-run.sh scripts/record-agent-state.sh scripts/run-agent.sh scripts/run-codex-agent.sh bin/ai-sdlc
```

Validate runner availability without executing an agent:

```bash
ai-sdlc run --runner codex --dry-run <run-dir>
ai-sdlc run --runner claude --dry-run <run-dir>
ai-sdlc run --runner cursor --dry-run <run-dir>
```

Validate product behavior:

```bash
make verify
```

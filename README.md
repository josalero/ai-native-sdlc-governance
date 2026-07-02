# AI Native SDLC Governance

This repository defines an **AI Native SDLC framework** and a working end-to-end sample for governed AI-assisted software delivery.

The framework combines:

- Specification-driven development
- Human accountability and approval gates
- AI delivery agents for product, architecture, coding, testing, security, and release work
- Rules, skills, guardrails, evaluation, auditability, and monitoring
- Durable run state so humans and other agents can review what happened

The reference implementation is a Spring Boot 4.1 / Java 25 HR Policy Assistant with completed governance artifacts and automated tests.

## Repository Map

| Path | Purpose |
| --- | --- |
| [`docs/`](./docs/README.md) | Framework documentation and operational templates |
| [`docs/ai-native-sdlc-framework.md`](./docs/ai-native-sdlc-framework.md) | Master AI Native SDLC reference |
| [`docs/templates/`](./docs/templates/README.md) | Reusable artifact templates |
| [`sample/ai-docs/policy-assistant/`](./sample/ai-docs/policy-assistant/README.md) | Completed AI specs and governance packet |
| [`sample/policy-assistant/`](./sample/policy-assistant/README.md) | Working Spring Boot sample application |
| [`.ai-sdlc.json`](./.ai-sdlc.json) | Agent registry, runner defaults, required specs, and verification commands |
| [`bin/`](./bin/ai-sdlc) | Cross-platform CLI wrappers |
| [`scripts/`](./scripts/start-agent-run.py) | Python runner implementation |

## Required Folder Structure

The CLI can run in this repository or in another product repository, but it expects a predictable structure.

### Framework Repository Structure

This repository is organized like this:

```text
.
├── .ai-sdlc.json
├── Makefile
├── README.md
├── bin/
│   ├── ai-sdlc
│   ├── ai-sdlc.cmd
│   └── ai-sdlc.ps1
├── docs/
│   ├── README.md
│   ├── ai-native-sdlc-framework.md
│   └── templates/
├── sample/
│   ├── ai-docs/
│   │   └── policy-assistant/
│   └── policy-assistant/
└── scripts/
    ├── install-cli.ps1
    ├── record-agent-state.py
    ├── record-agent-state.sh
    ├── run-agent.py
    ├── run-agent.sh
    ├── run-codex-agent.sh
    ├── start-agent-run.py
    └── start-agent-run.sh
```

### Minimum Product Repository Structure

For another repo to use the framework runner, it needs:

```text
.
├── .ai-sdlc.json
├── ai-docs/
│   └── [use-case-slug]/
│       ├── README.md
│       ├── 03-specification/
│       │   ├── ai-spec.md
│       │   ├── feature-ai-spec.yml
│       │   ├── manual-review-and-approval-workflow.md
│       │   └── role-and-agent-operating-model.md
│       ├── 09-controls/
│       │   ├── guardrail-specification.md
│       │   └── policy-rules.md
│       ├── 10-build/
│       │   ├── ai-test-generation-plan.md
│       │   └── code-generation-plan.md
│       └── 16-agent-runs/
│           └── README.md
└── [application source code]
```

That is the minimum for a `coding` agent run with the default config. Other agents need additional phase folders:

| Agent | Additional folders commonly required |
| --- | --- |
| `product` | `01-intake/`, `02-risk/` |
| `architecture` | `04-architecture/`, `05-data/`, `08-rag/` when applicable |
| `test` | `11-evaluation/` |
| `security` | `12-security/` |
| `release` | `14-monitoring/`, `15-release/` |

### Recommended Full Use-Case Packet

Use numbered phase folders so governance artifacts sort in SDLC order:

```text
ai-docs/
└── [use-case-slug]/
    ├── README.md
    ├── 00-overview/
    │   └── feature-walkthrough.md
    ├── 01-intake/
    │   └── intake.md
    ├── 02-risk/
    │   └── risk-classification.md
    ├── 03-specification/
    │   ├── ai-spec.md
    │   ├── feature-ai-spec.yml
    │   ├── manual-review-and-approval-workflow.md
    │   └── role-and-agent-operating-model.md
    ├── 04-architecture/
    │   └── architecture-pattern.md
    ├── 05-data/
    │   └── data-governance.md
    ├── 06-model/
    │   └── model-card.md
    ├── 07-prompt/
    │   └── prompt-card-[name]-v1.md
    ├── 08-rag/
    │   └── rag-governance.md
    ├── 09-controls/
    │   ├── guardrail-specification.md
    │   ├── policy-rules.md
    │   └── skill-binding-matrix.md
    ├── 10-build/
    │   ├── ai-test-generation-plan.md
    │   └── code-generation-plan.md
    ├── 11-evaluation/
    │   ├── evaluation-report.md
    │   └── test-cases.md
    ├── 12-security/
    │   └── security-review.md
    ├── 13-human-review/
    │   └── human-review-procedure.md
    ├── 14-monitoring/
    │   └── monitoring.md
    ├── 15-release/
    │   └── production-readiness.md
    └── 16-agent-runs/
        ├── README.md
        └── [run-id]/
            ├── agent-prompt.md
            ├── events.md
            ├── handoff.md
            ├── required-specs.txt
            ├── review.md
            ├── state.json
            └── transcript.log
```

### How Config Maps To Folders

The runner reads [`.ai-sdlc.json`](./.ai-sdlc.json). These fields control folder expectations:

| Config field | Meaning |
| --- | --- |
| `defaultUseCase` | Default use-case packet, for example `sample/ai-docs/policy-assistant` |
| `runDirectory` | Folder under each use-case packet where run state is stored, default `16-agent-runs` |
| `commonRequiredSpecs` | Specs every agent must read |
| `agents.*.requiredSpecs` | Extra specs required by a specific agent |
| `defaultVerificationCommands` | Commands copied into `state.json` and `handoff.md` |

When `ai-sdlc start` runs, it resolves every spec path relative to the use-case folder. Missing required specs fail the run unless `--allow-missing-specs` is passed.

## Core Concepts

| Concept | Meaning |
| --- | --- |
| Use-case packet | The governance folder for a feature, such as `sample/ai-docs/policy-assistant/` |
| AI specs | The required artifacts agents must read before work, including feature spec, operating model, guardrails, code plan, and test plan |
| Agent run | A durable folder under `16-agent-runs/` that stores prompt, transcript, state, handoff, and review notes |
| Runner | The CLI used to execute an agent prompt: Codex, Claude Code, Cursor Agent, or manual mode |
| Validation | Checking that framework scripts, config, state, and generated run folders are structurally correct |
| Verification | Checking that the product feature still works, usually through tests or release gates |

## Features

### Framework Features

| Feature | What it provides |
| --- | --- |
| AI Native SDLC reference model | End-to-end lifecycle from intake through production readiness and incidents |
| Numbered governance artifact structure | Repeatable folder convention for use-case packets |
| AI spec templates | Human-readable and machine-readable specs for agent-assisted delivery |
| Human accountability model | Product Owner, AI Architect, Software Engineer, QA / AI Tester, Security Reviewer, SRE / Ops, and Data Owner boundaries |
| Agent operating model | Product, architecture, coding, test, security, and release agents with explicit reviewer ownership |
| Manual review workflow | Approval gates for scope, risk, architecture, data use, code, tests, security, and release |
| Rules, skills, and guardrails | Policy cards, skill binding matrix, prompt/RAG controls, and guardrail specs |
| Evaluation and release evidence | Test cases, evaluation reports, monitoring, and production readiness artifacts |
| Durable audit trail | Agent state, transcript, handoff, review notes, and event history per run |

### CLI Features

| Feature | Command or file |
| --- | --- |
| Installable local CLI | `make install-cli` or `.\scripts\install-cli.ps1` |
| Cross-platform wrappers | `bin/ai-sdlc`, `bin/ai-sdlc.ps1`, `bin/ai-sdlc.cmd` |
| Config-driven agent registry | `.ai-sdlc.json` |
| Agent run creation | `ai-sdlc start` |
| Multi-runner execution | `ai-sdlc run --runner codex|claude|cursor|manual` |
| Safe dry-run preview | `ai-sdlc run --dry-run` |
| Manual execution mode | `ai-sdlc run --runner manual` |
| State tracking | `ai-sdlc state` |
| Custom use-case support | `ai-sdlc start --use-case ai-docs/my-feature` |
| Custom verification commands | `ai-sdlc start --command "..."` |
| Make shortcuts | `make agent-coding`, `make agent-test`, `make verify` |

### Sample Application Features

| Feature | What it demonstrates |
| --- | --- |
| Spring Boot 4.1 application | Working Java 25 service scaffold |
| HR Policy Assistant | RAG-style question answering over bundled policy documents |
| Governed AI endpoint | `POST /api/v1/policy/questions` |
| Guardrails | Scope, refusal, source citation, and no-answer behavior |
| Audit logging | Request outcome, retrieved document identifiers, and safety decisions |
| Deterministic test profile | Stubbed model behavior for repeatable tests |
| AI evaluation cases | Test resources and JUnit coverage for expected answer behavior |
| Production readiness packet | Security, monitoring, release, and human review artifacts |

## Install The CLI

The `ai-sdlc` CLI creates agent runs, runs a selected AI coding CLI, and records state for human review.

Linux/macOS:

```bash
make install-cli
```

Make sure `~/.local/bin` is on your `PATH`.

Windows PowerShell:

```powershell
.\scripts\install-cli.ps1
```

The install creates an `ai-sdlc` command backed by:

- `bin/ai-sdlc` on Linux/macOS
- `bin/ai-sdlc.ps1` and `bin/ai-sdlc.cmd` on Windows

Health check:

```bash
ai-sdlc help
ai-sdlc config
```

If you do not install the CLI, you can still use the scripts directly:

```bash
scripts/start-agent-run.sh --help
scripts/run-agent.sh --help
scripts/record-agent-state.sh --help
```

## CLI Commands

| Command | Purpose |
| --- | --- |
| `ai-sdlc config` | Print the resolved runner config path |
| `ai-sdlc start` | Create a new agent run folder from the configured AI specs |
| `ai-sdlc run` | Execute or prepare the generated `agent-prompt.md` |
| `ai-sdlc state` | Update `state.json` and append an event to `events.md` |
| `ai-sdlc help` | Show CLI help |

Common environment variables:

| Variable | Purpose |
| --- | --- |
| `AI_SDLC_CONFIG` | Override the config path, default `.ai-sdlc.json` |
| `AI_SDLC_RUNNER` | Override default runner for `ai-sdlc run` |
| `AI_SDLC_AGENT_WORKDIR` | Override the working directory for agent execution |
| `CODEX_AGENT_SANDBOX` | Override Codex sandbox mode |
| `CODEX_AGENT_APPROVAL_POLICY` | Override Codex approval policy |
| `CLAUDE_AGENT_PERMISSION_MODE` | Override Claude Code permission mode |
| `CURSOR_AGENT_MODEL` | Override Cursor Agent model |

## Configure Agent Runners

Runner defaults live in [`.ai-sdlc.json`](./.ai-sdlc.json).

Configured runners:

| Runner | CLI | Default posture |
| --- | --- | --- |
| `codex` | `codex` | Default runner, workspace-write sandbox, approval policy from config |
| `claude` | `claude` | Uses `--print`, text output, `acceptEdits`, no session persistence |
| `cursor` | `cursor-agent` | Uses `--print`, workspace sandbox enabled, no `--force` by default |
| `manual` | none | Records that the prompt should be pasted into another reviewed tool |

Use [`docs/templates/ai-sdlc-run-config.json`](./docs/templates/ai-sdlc-run-config.json) to create a config for another repo or use case.

## Scenario 1: Review The AI Specs

The completed sample specs are in [`sample/ai-docs/policy-assistant/`](./sample/ai-docs/policy-assistant/README.md).

Start with these files:

| Artifact | Purpose |
| --- | --- |
| [`feature-ai-spec.yml`](./sample/ai-docs/policy-assistant/03-specification/feature-ai-spec.yml) | Machine-readable feature contract |
| [`ai-spec.md`](./sample/ai-docs/policy-assistant/03-specification/ai-spec.md) | Human-readable AI system specification |
| [`role-and-agent-operating-model.md`](./sample/ai-docs/policy-assistant/03-specification/role-and-agent-operating-model.md) | Human roles, agents, RACI, approval boundaries |
| [`manual-review-and-approval-workflow.md`](./sample/ai-docs/policy-assistant/03-specification/manual-review-and-approval-workflow.md) | Human review and manual action gates |
| [`code-generation-plan.md`](./sample/ai-docs/policy-assistant/10-build/code-generation-plan.md) | Coding-agent implementation rules |
| [`ai-test-generation-plan.md`](./sample/ai-docs/policy-assistant/10-build/ai-test-generation-plan.md) | Test-agent and eval generation rules |

## Scenario 2: Create A Coding Agent Run

Create an agent run from the configured sample packet:

```bash
ai-sdlc start --agent coding --task "Implement the approved HR Policy Assistant feature from the AI specs"
```

Or with Make:

```bash
make agent-coding TASK="Implement the approved HR Policy Assistant feature from the AI specs"
```

The command creates:

```text
sample/ai-docs/policy-assistant/16-agent-runs/<run-id>/
```

Each run contains:

| File | Purpose |
| --- | --- |
| `state.json` | Current run status, agent, reviewer, specs, commands |
| `required-specs.txt` | Specs the agent must read before work |
| `agent-prompt.md` | Prompt to run with an AI coding tool |
| `transcript.log` | Captured agent output |
| `handoff.md` | Agent summary for human review |
| `review.md` | Human approval or requested changes |
| `events.md` | Append-only state history |

## Scenario 3: Create Other Agent Runs

Configured agent aliases are defined in [`.ai-sdlc.json`](./.ai-sdlc.json).

```bash
make agent-product TASK="Clarify product acceptance criteria"
make agent-architecture TASK="Review architecture and controls"
make agent-coding TASK="Implement the approved feature"
make agent-test TASK="Generate or update AI evaluation tests"
make agent-security TASK="Review prompt injection, leakage, and abuse risks"
make agent-release TASK="Prepare release readiness evidence"
```

Equivalent direct command:

```bash
ai-sdlc start \
  --agent security \
  --task "Review prompt injection, leakage, and abuse risks" \
  --reviewer "Security Reviewer"
```

## Scenario 4: Override Use Case, Config, Or Verification Command

Use another governance packet:

```bash
ai-sdlc start \
  --config .ai-sdlc.json \
  --use-case ai-docs/my-feature \
  --agent coding \
  --task "Implement the approved feature"
```

Add a custom verification command to the run:

```bash
ai-sdlc start \
  --agent coding \
  --task "Implement the approved feature" \
  --command "cd sample/policy-assistant && ./gradlew test" \
  --command "cd sample/policy-assistant && ./gradlew check"
```

Create a deterministic run id for repeatable demos:

```bash
ai-sdlc start \
  --agent coding \
  --task "Demo run" \
  --run-id demo-coding-run
```

If a required spec is intentionally missing during early adoption, create the run with warnings:

```bash
ai-sdlc start \
  --use-case ai-docs/early-feature \
  --agent coding \
  --task "Draft implementation plan" \
  --allow-missing-specs
```

## Scenario 5: Dry-Run A Runner

Dry-run resolves the selected runner command without executing the agent.

```bash
ai-sdlc run --runner codex --dry-run sample/ai-docs/policy-assistant/16-agent-runs/<run-id>
ai-sdlc run --runner claude --dry-run sample/ai-docs/policy-assistant/16-agent-runs/<run-id>
ai-sdlc run --runner cursor --dry-run sample/ai-docs/policy-assistant/16-agent-runs/<run-id>
```

Use this before granting an agent access to the repo. It confirms:

- The run folder exists
- `agent-prompt.md` exists
- The selected runner is configured
- The runner executable is available on `PATH`
- The resolved command uses the expected sandbox, permission, model, and workspace options

## Scenario 6: Execute With Codex

Codex is the default runner in [`.ai-sdlc.json`](./.ai-sdlc.json).

```bash
ai-sdlc run sample/ai-docs/policy-assistant/16-agent-runs/<run-id>
```

Or explicitly:

```bash
ai-sdlc run --runner codex sample/ai-docs/policy-assistant/16-agent-runs/<run-id>
```

Useful overrides:

```bash
CODEX_AGENT_SANDBOX=workspace-write \
CODEX_AGENT_APPROVAL_POLICY=never \
ai-sdlc run --runner codex sample/ai-docs/policy-assistant/16-agent-runs/<run-id>
```

## Scenario 7: Execute With Claude Code CLI

Claude Code runner defaults are configured to avoid bypass permissions.

```bash
ai-sdlc run --runner claude sample/ai-docs/policy-assistant/16-agent-runs/<run-id>
```

Preview first:

```bash
ai-sdlc run --runner claude --dry-run sample/ai-docs/policy-assistant/16-agent-runs/<run-id>
```

Example override:

```bash
CLAUDE_AGENT_PERMISSION_MODE=plan \
ai-sdlc run --runner claude sample/ai-docs/policy-assistant/16-agent-runs/<run-id>
```

Use `plan` when you want Claude to inspect and propose before edits.

## Scenario 8: Execute With Cursor Agent CLI

Cursor Agent runner defaults keep sandboxing enabled and do not use `--force` unless configured.

```bash
ai-sdlc run --runner cursor sample/ai-docs/policy-assistant/16-agent-runs/<run-id>
```

Preview first:

```bash
ai-sdlc run --runner cursor --dry-run sample/ai-docs/policy-assistant/16-agent-runs/<run-id>
```

Example model override:

```bash
CURSOR_AGENT_MODEL=sonnet-4 \
ai-sdlc run --runner cursor sample/ai-docs/policy-assistant/16-agent-runs/<run-id>
```

## Scenario 9: Manual Runner

Use manual mode when:

- The selected CLI is not installed
- The agent must run in another approved environment
- A human needs to copy the prompt into a reviewed tool
- You want to validate governance state without spending agent tokens

```bash
ai-sdlc run --runner manual sample/ai-docs/policy-assistant/16-agent-runs/<run-id>
```

Manual mode records `manual_prompt_ready` in `state.json` and appends an event to `events.md`.

## Scenario 10: Record Human Review State

Agents may draft artifacts, code, tests, reviews, and release evidence. Humans approve scope, risk, architecture, data use, merge readiness, security posture, and production release.

Record state from either an agent or human review step:

```bash
ai-sdlc state \
  --run-dir sample/ai-docs/policy-assistant/16-agent-runs/<run-id> \
  --status ready_for_human_review \
  --summary "Agent completed implementation and test evidence" \
  --actor "Coding Agent"
```

Human reviewer decision examples:

```bash
ai-sdlc state \
  --run-dir sample/ai-docs/policy-assistant/16-agent-runs/<run-id> \
  --status changes_requested \
  --summary "Reviewer requested stronger leakage tests" \
  --actor "QA / AI Tester"
```

```bash
ai-sdlc state \
  --run-dir sample/ai-docs/policy-assistant/16-agent-runs/<run-id> \
  --status human_approved \
  --summary "Reviewed handoff, tests, and risk notes; approved for merge" \
  --actor "Software Engineer"
```

Human reviewers should inspect:

- `handoff.md`
- `review.md`
- `events.md`
- `transcript.log`
- The code diff and test output

Agents must not approve their own work.

## State Lifecycle

Typical statuses:

| Status | Meaning |
| --- | --- |
| `ready_for_agent` | Run folder and prompt were created |
| `in_progress` | A runner started work |
| `agent_execution_finished` | Runner finished, but human review is still required |
| `agent_execution_failed` | Runner failed |
| `manual_prompt_ready` | Manual runner prepared the prompt for external execution |
| `ready_for_human_review` | Agent says work is ready for review |
| `changes_requested` | Human reviewer requested changes |
| `human_approved` | Human reviewer approved the run outcome |

Teams can add statuses in their process, but every status change should be recorded with `ai-sdlc state`.

## Validation vs Verification

Use both. They answer different questions.

| Activity | Question answered | Example |
| --- | --- | --- |
| Validation | Is the SDLC framework state structurally correct? | JSON parses, scripts compile, run folder contains expected files |
| Verification | Does the product behavior meet requirements? | Spring Boot tests pass, eval cases pass, release gate passes |

## Validate The Framework

Validate local prerequisites:

```bash
python3 --version
ai-sdlc help
ai-sdlc config
```

Optional runner checks:

```bash
command -v codex || true
command -v claude || true
command -v cursor-agent || true
```

Validate runner scripts and config:

```bash
python3 -m py_compile scripts/start-agent-run.py scripts/record-agent-state.py scripts/run-agent.py
python3 -m json.tool .ai-sdlc.json >/dev/null
python3 -m json.tool docs/templates/ai-sdlc-run-config.json >/dev/null
bash -n scripts/start-agent-run.sh scripts/record-agent-state.sh scripts/run-agent.sh scripts/run-codex-agent.sh bin/ai-sdlc
```

Validate CLI behavior without running an AI agent:

```bash
tmp="$(mktemp -d)"
cp -R sample/ai-docs/policy-assistant "$tmp/use-case"
ai-sdlc start --use-case "$tmp/use-case" --agent coding --task "Smoke test AI SDLC runner" --run-id smoke-coding
ai-sdlc run --runner manual "$tmp/use-case/16-agent-runs/smoke-coding"
ai-sdlc state \
  --run-dir "$tmp/use-case/16-agent-runs/smoke-coding" \
  --status ready_for_human_review \
  --summary "Smoke test complete" \
  --actor "Smoke Test"
```

Validate runner command resolution:

```bash
ai-sdlc run --runner codex --dry-run "$tmp/use-case/16-agent-runs/smoke-coding"
ai-sdlc run --runner claude --dry-run "$tmp/use-case/16-agent-runs/smoke-coding"
ai-sdlc run --runner cursor --dry-run "$tmp/use-case/16-agent-runs/smoke-coding"
```

Validate run state:

```bash
python3 -m json.tool "$tmp/use-case/16-agent-runs/smoke-coding/state.json" >/dev/null
test -s "$tmp/use-case/16-agent-runs/smoke-coding/required-specs.txt"
test -s "$tmp/use-case/16-agent-runs/smoke-coding/agent-prompt.md"
test -s "$tmp/use-case/16-agent-runs/smoke-coding/events.md"
```

Windows PowerShell equivalents:

```powershell
$tmp = New-Item -ItemType Directory -Path ([System.IO.Path]::GetTempPath()) -Name "ai-sdlc-smoke-$([guid]::NewGuid())"
Copy-Item -Recurse sample\ai-docs\policy-assistant "$tmp\use-case"
ai-sdlc start --use-case "$tmp\use-case" --agent coding --task "Smoke test AI SDLC runner" --run-id smoke-coding
ai-sdlc run --runner manual "$tmp\use-case\16-agent-runs\smoke-coding"
ai-sdlc state --run-dir "$tmp\use-case\16-agent-runs\smoke-coding" --status ready_for_human_review --summary "Smoke test complete" --actor "Smoke Test"
```

## Verify The Spring Boot Sample

Run the product release gate:

```bash
make verify
```

Or directly:

```bash
cd sample/policy-assistant
./gradlew test
```

Expected result:

- Gradle build succeeds
- Unit tests pass
- Evaluation tests pass
- Generated class files target Java 25

Optional Java target check:

```bash
cd sample/policy-assistant
javap -verbose build/classes/java/main/com/example/policyassistant/PolicyAssistantApplication.class | rg "major version"
```

Expected Java 25 class major version: `69`.

## Verify A Completed Agent Run

After an agent finishes, reviewers should verify:

| Check | Command or artifact |
| --- | --- |
| Run state is valid JSON | `python3 -m json.tool <run-dir>/state.json` |
| Agent read required specs | `<run-dir>/required-specs.txt`, `<run-dir>/handoff.md` |
| Transcript exists | `<run-dir>/transcript.log` |
| Handoff is complete | `<run-dir>/handoff.md` |
| Human decision recorded | `<run-dir>/review.md` and `<run-dir>/events.md` |
| Product tests pass | `make verify` |
| Diff is acceptable | `git diff` |

The agent run is not complete until a human reviewer records the decision.

## First Workflow To Try

```bash
make install-cli
ai-sdlc start --agent coding --task "Review the AI specs and propose one safe implementation improvement"
ai-sdlc run --runner manual sample/ai-docs/policy-assistant/16-agent-runs/<run-id>
```

Then open the generated `agent-prompt.md`, paste it into the agent tool you want to use, and keep the review state in the same run folder.

## Troubleshooting

| Symptom | Fix |
| --- | --- |
| `ai-sdlc: command not found` | Run `make install-cli` and add `~/.local/bin` to `PATH` |
| `Runner config not found` | Run from repo root or set `AI_SDLC_CONFIG=/path/to/.ai-sdlc.json` |
| `Required specs are missing` | Complete the use-case packet or pass `--allow-missing-specs` for draft work |
| `Agent runner executable not found` | Install the selected CLI or use `--runner manual` |
| Cursor or Claude starts with unexpected permissions | Check `.ai-sdlc.json`, `--dry-run`, and runner-specific environment overrides |
| Reusing `--run-id` fails | Pick a new run id or delete the old smoke run folder |

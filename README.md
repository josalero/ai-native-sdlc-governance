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
| [`sample/README.md`](./sample/README.md) | Run and validate the HR Policy Assistant sample |
| [`docs/ai-sdlc-cli-how-to.md`](./docs/ai-sdlc-cli-how-to.md) | Detailed CLI how-to with samples for every scenario |
| [`docs/agent-cli-prerequisites-and-errors.md`](./docs/agent-cli-prerequisites-and-errors.md) | Agent CLI prerequisites, API keys, validation, and error messages |
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
│   ├── ai-sdlc-cli-how-to.md
│   ├── agent-cli-prerequisites-and-errors.md
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

Operational guides:

| Guide | Use when |
| --- | --- |
| [**docs/ai-sdlc-cli-how-to.md**](./docs/ai-sdlc-cli-how-to.md) | Installing the CLI, creating agent runs, every runner scenario, state recording, validation, and troubleshooting |
| [**docs/agent-cli-prerequisites-and-errors.md**](./docs/agent-cli-prerequisites-and-errors.md) | API keys, login, and error messages for Codex, Claude, and Cursor |
| [**sample/README.md**](./sample/README.md#run-and-validate-the-hr-policy-assistant) | Running and signing off the HR Policy Assistant sample end to end |

See [**sample/README.md — Run and validate the HR Policy Assistant**](./sample/README.md#run-and-validate-the-hr-policy-assistant) for the sample walkthrough: install CLI → agent run → automated verification → manual API sign-off.

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

## Quick Start

**Sample app (no agent CLI required):**

```bash
jenv shell 25    # if you use jenv
make verify
cd sample/policy-assistant && ./gradlew bootRun
```

Then follow the manual API checks in [**sample/README.md**](./sample/README.md#run-and-validate-the-hr-policy-assistant).

**Agent CLI workflow:**

```bash
make install-cli
ai-sdlc start --agent coding --task "Implement and verify the HR Policy Assistant sample from the AI specs"
```

Full command reference, runner samples, and every scenario: [**docs/ai-sdlc-cli-how-to.md**](./docs/ai-sdlc-cli-how-to.md).

Step-by-step sample sign-off (install → execute → validate → approve): [**sample/README.md**](./sample/README.md#run-and-validate-the-hr-policy-assistant).

# Policy Assistant Governance Packet

This folder applies the [AI Native SDLC Framework](../../../docs/ai-native-sdlc-framework.md) to one feature in the [Spring Boot policy assistant](../../policy-assistant/README.md):

> Employees can ask HR policy questions and receive grounded answers with citations.

Artifacts are organized by **SDLC phase** (numbered folders). Work top to bottom when onboarding or auditing a use case.

## Folder Structure

```
policy-assistant/
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
│   ├── role-and-agent-operating-model.md
│   └── manual-review-and-approval-workflow.md
├── 04-architecture/
│   └── architecture-pattern.md
├── 05-data/
│   └── data-governance.md
├── 06-model/
│   └── model-card.md
├── 07-prompt/
│   └── prompt-card-policy-answer-v1.md
├── 08-rag/
│   └── rag-governance.md
├── 09-controls/
│   ├── policy-rules.md
│   ├── skill-binding-matrix.md
│   └── guardrail-specification.md
├── 10-build/
│   ├── code-generation-plan.md
│   └── ai-test-generation-plan.md
├── 11-evaluation/
│   ├── test-cases.md
│   └── evaluation-report.md
├── 12-security/
│   └── security-review.md
├── 13-human-review/
│   └── human-review-procedure.md
├── 14-monitoring/
│   └── monitoring.md
└── 15-release/
    └── production-readiness.md
└── 16-agent-runs/
    ├── README.md
    └── [run-id]/
        ├── state.json
        ├── required-specs.txt
        ├── agent-prompt.md
        ├── transcript.log
        ├── handoff.md
        ├── review.md
        └── events.md
```

## Framework Status

| Phase | Folder | Artifact | Status |
| --- | --- | --- | --- |
| Overview | `00-overview/` | [feature-walkthrough.md](./00-overview/feature-walkthrough.md) | Complete |
| Intake | `01-intake/` | [intake.md](./01-intake/intake.md) | Complete |
| Risk | `02-risk/` | [risk-classification.md](./02-risk/risk-classification.md) | Complete |
| Specification | `03-specification/` | [ai-spec.md](./03-specification/ai-spec.md), [feature-ai-spec.yml](./03-specification/feature-ai-spec.yml) | Complete |
| Role and agent model | `03-specification/` | [role-and-agent-operating-model.md](./03-specification/role-and-agent-operating-model.md) | Complete |
| Manual review workflow | `03-specification/` | [manual-review-and-approval-workflow.md](./03-specification/manual-review-and-approval-workflow.md) | Complete |
| Architecture | `04-architecture/` | [architecture-pattern.md](./04-architecture/architecture-pattern.md) | Complete |
| Data | `05-data/` | [data-governance.md](./05-data/data-governance.md) | Complete |
| Model | `06-model/` | [model-card.md](./06-model/model-card.md) | Complete |
| Prompt | `07-prompt/` | [prompt-card-policy-answer-v1.md](./07-prompt/prompt-card-policy-answer-v1.md) | Complete |
| RAG | `08-rag/` | [rag-governance.md](./08-rag/rag-governance.md) | Complete |
| Rules / skills / guardrails | `09-controls/` | [policy-rules.md](./09-controls/policy-rules.md), [skill-binding-matrix.md](./09-controls/skill-binding-matrix.md), [guardrail-specification.md](./09-controls/guardrail-specification.md) | Complete |
| Build | `10-build/` | [code-generation-plan.md](./10-build/code-generation-plan.md), [ai-test-generation-plan.md](./10-build/ai-test-generation-plan.md) | Complete |
| Evaluation | `11-evaluation/` | [test-cases.md](./11-evaluation/test-cases.md), [evaluation-report.md](./11-evaluation/evaluation-report.md) | Complete |
| Security | `12-security/` | [security-review.md](./12-security/security-review.md) | Complete |
| Human review | `13-human-review/` | [human-review-procedure.md](./13-human-review/human-review-procedure.md) | Complete |
| Monitoring | `14-monitoring/` | [monitoring.md](./14-monitoring/monitoring.md) | Complete |
| Release | `15-release/` | [production-readiness.md](./15-release/production-readiness.md) | Complete |

## Code Traceability

| Control | Implementation |
| --- | --- |
| API contract | `sample/policy-assistant/src/main/java/com/example/policyassistant/api/PolicyQuestionController.java` |
| Input prompt-injection guardrail | `sample/policy-assistant/src/main/java/com/example/policyassistant/guardrail/InputGuardrail.java` |
| Output PII/citation guardrail | `sample/policy-assistant/src/main/java/com/example/policyassistant/guardrail/OutputGuardrail.java` |
| RAG retrieval | `sample/policy-assistant/src/main/java/com/example/policyassistant/rag/PolicyRetrievalService.java` |
| Prompt orchestration | `sample/policy-assistant/src/main/java/com/example/policyassistant/rag/PolicyAnswerOrchestrator.java` |
| Prompt version | `sample/policy-assistant/prompts/policy-answer-v1.prompt` |
| Audit logging | `sample/policy-assistant/src/main/java/com/example/policyassistant/audit/AiAuditLogger.java` |
| Runtime config | `sample/policy-assistant/src/main/resources/application.yml` |
| Evaluation dataset | `sample/policy-assistant/src/test/resources/evaluation/policy-assistant-eval-cases.json` |
| Evaluation runner | `sample/policy-assistant/src/test/java/com/example/policyassistant/eval/PolicyAssistantEvaluationTest.java` |

## AI-Ready Specs

Use these when handing the feature to an AI coding or testing agent:

1. [feature-ai-spec.yml](./03-specification/feature-ai-spec.yml) — machine-readable feature contract
2. [code-generation-plan.md](./10-build/code-generation-plan.md) — how to generate the Spring Boot implementation
3. [ai-test-generation-plan.md](./10-build/ai-test-generation-plan.md) — how to generate and run AI eval tests
4. [role-and-agent-operating-model.md](./03-specification/role-and-agent-operating-model.md) — accountable humans, delivery agents, RACI
5. [manual-review-and-approval-workflow.md](./03-specification/manual-review-and-approval-workflow.md) — human review gates

## AI Spec Completeness

| Spec | Purpose | Status |
| --- | --- | --- |
| [ai-spec.md](./03-specification/ai-spec.md) | Human-readable feature contract | Complete |
| [feature-ai-spec.yml](./03-specification/feature-ai-spec.yml) | Machine-readable contract for agents | Complete |
| [role-and-agent-operating-model.md](./03-specification/role-and-agent-operating-model.md) | Human/accountable role and agent boundaries | Complete |
| [manual-review-and-approval-workflow.md](./03-specification/manual-review-and-approval-workflow.md) | Human review and manual action gates | Complete |
| [code-generation-plan.md](./10-build/code-generation-plan.md) | Coding-agent instructions | Complete |
| [ai-test-generation-plan.md](./10-build/ai-test-generation-plan.md) | Test-agent instructions | Complete |

**Start here:** [feature-walkthrough.md](./00-overview/feature-walkthrough.md)

## Run The Gate

```bash
cd sample/policy-assistant
./gradlew test
```

Release gate result for this sample: pass on 2026-07-02.

## Start An Agent Run

```bash
make agent-coding TASK="Implement or update the approved HR Policy Assistant feature from the AI specs"
```

Or install and use the local CLI.

Linux/macOS:

```bash
make install-cli
ai-sdlc start --agent coding --task "Implement or update the approved HR Policy Assistant feature from the AI specs"
```

Windows PowerShell:

```powershell
.\scripts\install-cli.ps1
ai-sdlc start --agent coding --task "Implement or update the approved HR Policy Assistant feature from the AI specs"
```

The CLI and Make targets read the sample defaults from [`.ai-sdlc.json`](../../../.ai-sdlc.json).

You can also call the script directly:

```bash
scripts/start-agent-run.sh \
  --config .ai-sdlc.json \
  --use-case sample/ai-docs/policy-assistant \
  --agent coding \
  --task "Implement or update the approved HR Policy Assistant feature from the AI specs"
```

Then run the generated prompt with Codex:

```bash
ai-sdlc run sample/ai-docs/policy-assistant/16-agent-runs/<run-id>
```

Other supported runner choices:

```bash
ai-sdlc run --runner claude sample/ai-docs/policy-assistant/16-agent-runs/<run-id>
ai-sdlc run --runner cursor sample/ai-docs/policy-assistant/16-agent-runs/<run-id>
ai-sdlc run --runner manual sample/ai-docs/policy-assistant/16-agent-runs/<run-id>
```

Humans review the result in the run folder's `handoff.md` and `review.md`.

# Operational Templates — AI Native SDLC

Fillable forms derived from [`ai-native-sdlc-framework.md`](../ai-native-sdlc-framework.md). Copy a template per use case; store completed artifacts with your AI system documentation.

## When to use each template

| Phase | Template | Required for |
| --- | --- | --- |
| Intake | [use-case-intake.md](./use-case-intake.md) | All AI initiatives |
| Intake | [risk-classification.md](./risk-classification.md) | All AI initiatives |
| Design | [ai-system-specification.md](./ai-system-specification.md) | Medium+ risk; recommended for all |
| Specification | [feature-ai-spec.yml](./feature-ai-spec.yml) | AI-agent-assisted implementation or test generation |
| Design | [architecture-pattern.md](./architecture-pattern.md) | All production AI features |
| Design | [data-governance-assessment.md](./data-governance-assessment.md) | Any system using company data |
| Design | [model-card.md](./model-card.md) | All LLM/ML systems |
| Design | [prompt-card.md](./prompt-card.md) | All prompt-based systems |
| Design | [rag-governance.md](./rag-governance.md) | RAG systems only |
| Design | [agent-tool-governance.md](./agent-tool-governance.md) | Agent systems only |
| Governance | [role-and-agent-operating-model.md](./role-and-agent-operating-model.md) | Medium+ risk or AI-agent-assisted delivery |
| Governance | [manual-review-and-approval-workflow.md](./manual-review-and-approval-workflow.md) | Medium+ risk, manual production actions, or AI-agent-assisted delivery |
| Build / test | [code-generation-plan.md](./code-generation-plan.md) | AI-agent-assisted implementation |
| Build / test | [ai-test-generation-plan.md](./ai-test-generation-plan.md) | AI-agent-assisted testing and evaluation |
| Build / test | [ai-sdlc-run-config.json](./ai-sdlc-run-config.json) | Configuring reusable agent-run commands |
| Build / test | [test-case.md](./test-case.md) | All production AI features |
| Pre-release | [evaluation-report.md](./evaluation-report.md) | All production AI features |
| Pre-release | [security-review-checklist.md](./security-review-checklist.md) | Medium+ risk |
| Pre-release | [human-review-procedure.md](./human-review-procedure.md) | High/Critical or HITL workflows |
| Pre-release | [production-readiness-checklist.md](./production-readiness-checklist.md) | All production releases |
| Controls | [policy-rule-card.md](./policy-rule-card.md) | Per rule or use case |
| Controls | [skill-binding-matrix.md](./skill-binding-matrix.md) | Medium+ risk |
| Controls | [guardrail-specification.md](./guardrail-specification.md) | Medium+ risk |
| Operations | [incident-report.md](./incident-report.md) | When incidents occur |

## Recommended artifact set (first fourteen)

1. Use Case Intake
2. Risk Classification
3. AI System Specification
4. Feature AI Spec
5. Role and Agent Operating Model
6. Code Generation Plan
7. AI Test Generation Plan
8. Evaluation Report
9. Production Readiness Checklist
10. Manual Review and Approval Workflow
11. Monitoring configuration (see framework §20)
12. Policy Rule Set (one or more rule cards)
13. Skill Binding Matrix
14. Guardrail Specification

## Naming convention

Use **numbered phase folders** so artifacts sort in SDLC order. See the completed sample: [`sample/ai-docs/policy-assistant/`](../../sample/ai-docs/policy-assistant/README.md).

```
ai-docs/
  [use-case-slug]/
    README.md
    00-overview/
      feature-walkthrough.md          # optional end-to-end narrative
    01-intake/
      intake.md
    02-risk/
      risk-classification.md
    03-specification/
      ai-spec.md
      feature-ai-spec.yml             # optional, for AI-agent delivery
      role-and-agent-operating-model.md
      manual-review-and-approval-workflow.md
    04-architecture/
      architecture-pattern.md
    05-data/
      data-governance.md
    06-model/
      model-card.md
    07-prompt/
      prompt-card-[name]-v1.md
    08-rag/                           # skip if not RAG
      rag-governance.md
    09-controls/
      policy-rules.md
      skill-binding-matrix.md
      guardrail-specification.md
    10-build/
      code-generation-plan.md
      ai-test-generation-plan.md
    11-evaluation/
      test-cases.md
      evaluation-report.md
    12-security/
      security-review.md
    13-human-review/
      human-review-procedure.md
    14-monitoring/
      monitoring.md
    15-release/
      production-readiness.md
    16-agent-runs/
      README.md
      [run-id]/
        state.json
        required-specs.txt
        agent-prompt.md
        transcript.log
        handoff.md
        review.md
        events.md
```

## Agent run command

Install the local CLI from a repository that contains the framework scripts.

Linux/macOS:

```bash
make install-cli
```

Windows PowerShell:

```powershell
.\scripts\install-cli.ps1
```

Create or update `.ai-sdlc.json` from [`ai-sdlc-run-config.json`](./ai-sdlc-run-config.json), then start a run:

```bash
ai-sdlc start \
  --agent coding \
  --task "[specific approved task]"
```

Run with the configured default runner or explicitly select a supported CLI:

```bash
ai-sdlc run ai-docs/[use-case-slug]/16-agent-runs/[run-id]
ai-sdlc run --runner claude ai-docs/[use-case-slug]/16-agent-runs/[run-id]
ai-sdlc run --runner cursor ai-docs/[use-case-slug]/16-agent-runs/[run-id]
ai-sdlc run --runner manual ai-docs/[use-case-slug]/16-agent-runs/[run-id]
```

Use `--dry-run` to verify command resolution before letting a CLI agent execute:

```bash
ai-sdlc run --runner claude --dry-run ai-docs/[use-case-slug]/16-agent-runs/[run-id]
```

The lower-level script is also available:

```bash
scripts/start-agent-run.sh \
  --config .ai-sdlc.json \
  --use-case ai-docs/[use-case-slug] \
  --agent coding \
  --task "[specific approved task]"
```

Then run or paste the generated `agent-prompt.md` into an agent. Use `review.md` for human approval.

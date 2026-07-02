# Feature Walkthrough

## Feature

Add an internal HR Policy Assistant endpoint to a Spring Boot 4.1 application.

The feature accepts an employee question, retrieves relevant HR policy excerpts, sends a grounded prompt to a chat model, validates the response, logs AI metadata, and returns a structured answer with citations.

## End-to-End Flow

| Step | Framework activity | Concrete output |
| --- | --- | --- |
| 1 | Intake | [intake.md](../01-intake/intake.md) defines users, value, data, and success metrics |
| 2 | Risk classification | [risk-classification.md](../02-risk/risk-classification.md) assigns Medium risk |
| 3 | Specification | [ai-spec.md](../03-specification/ai-spec.md) defines endpoint, schema, failure behavior, and controls |
| 4 | Role and agent model | [role-and-agent-operating-model.md](../03-specification/role-and-agent-operating-model.md) defines accountable humans, delivery agents, RACI, and approval boundaries |
| 5 | Manual review workflow | [manual-review-and-approval-workflow.md](../03-specification/manual-review-and-approval-workflow.md) defines human document, code, test, release, and manual action gates |
| 6 | AI-ready generation specs | [feature-ai-spec.yml](../03-specification/feature-ai-spec.yml), [code-generation-plan.md](../10-build/code-generation-plan.md), and [ai-test-generation-plan.md](../10-build/ai-test-generation-plan.md) define how to generate code and AI tests |
| 7 | Architecture | [architecture-pattern.md](../04-architecture/architecture-pattern.md) selects RAG instead of an agent |
| 8 | Data review | [data-governance.md](../05-data/data-governance.md) approves internal policy docs |
| 9 | Rules/skills/guardrails | [policy-rules.md](../09-controls/policy-rules.md), [skill-binding-matrix.md](../09-controls/skill-binding-matrix.md), and [guardrail-specification.md](../09-controls/guardrail-specification.md) define controls |
| 10 | Build | `sample/policy-assistant` implements API, retrieval, prompt orchestration, guardrails, and audit |
| 11 | Evaluation | [test-cases.md](../11-evaluation/test-cases.md) and `PolicyAssistantEvaluationTest` run golden and safety cases |
| 12 | Security review | [security-review.md](../12-security/security-review.md) records prompt injection and data leakage checks |
| 13 | Readiness | [production-readiness.md](../15-release/production-readiness.md) records the release gate |

## Implementation Map

| Requirement | Code or artifact |
| --- | --- |
| FR-001 Ask an HR policy question | `PolicyQuestionController` |
| FR-002 Retrieve relevant policy excerpts | `PolicyRetrievalService`, `PolicyKnowledgeBase` |
| FR-003 Return JSON with answer, citations, confidence, warnings | `PolicyAnswerResponse`, `PolicyAnswerOrchestrator` |
| FR-004 Block prompt injection attempts | `InputGuardrail` |
| FR-005 Block SSN-like output patterns | `OutputGuardrail` |
| FR-006 Log metadata for audit | `AiAuditLogger`, `AiAuditEvent` |

## Release Command

```bash
cd sample/policy-assistant
./gradlew test
```

The command runs unit, API, and governed evaluation tests.

## Generation Command For An AI Agent

Give the agent the prompt from [code-generation-plan.md](../10-build/code-generation-plan.md#ai-coding-agent-prompt), then require the test prompt and gate from [ai-test-generation-plan.md](../10-build/ai-test-generation-plan.md#ai-test-generator-prompt).

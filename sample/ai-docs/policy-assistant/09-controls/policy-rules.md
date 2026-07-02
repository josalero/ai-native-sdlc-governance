# Policy Rule Set

## AI-DATA-001

| Field | Value |
| --- | --- |
| **Rule ID** | AI-DATA-001 |
| **Name** | No secrets or production PII in prompts |
| **Type** | Deny |
| **Scope** | Enterprise / Repo / Use Case |
| **Risk tiers** | Low / Medium / High / Critical |
| **Applies to** | Human / Coding agent / Production AI / All |
| **Owner** | Security |
| **Review cadence** | Quarterly |

**Statement:** Do not include secrets, credentials, tokens, SSNs, or unapproved production personal data in prompts or retrieved context.

**Enforcement:** Data governance review, security review, prompt review, output PII guardrail, and source control review.

**Permitted:** Internal HR policy text without employee records.

**Denied:** API keys, connection strings, SSNs, employee medical or accommodation records.

## AI-EVAL-001

| Field | Value |
| --- | --- |
| **Rule ID** | AI-EVAL-001 |
| **Name** | Evaluation gate required |
| **Type** | Require |
| **Scope** | Repo / Use Case |
| **Risk tiers** | Medium / High / Critical |
| **Applies to** | Human / Coding agent / Production AI / All |
| **Owner** | QA / Eval |
| **Review cadence** | After prompt, model, corpus, or guardrail changes |

**Statement:** Do not release a prompt, model, retrieval, or guardrail change unless the governed evaluation suite passes.

**Enforcement:** `./gradlew test`, `PolicyAssistantEvaluationTest`, production readiness checklist.

**Permitted:** Merge after passing test/eval gate and review.

**Denied:** Deploying prompt changes without regression tests.

## AI-HR-001

| Field | Value |
| --- | --- |
| **Rule ID** | AI-HR-001 |
| **Name** | No AI employment decisions |
| **Type** | Deny |
| **Scope** | Use Case |
| **Risk tiers** | Medium / High / Critical |
| **Applies to** | Production AI |
| **Owner** | HR Operations |
| **Review cadence** | Quarterly |

**Statement:** The HR Policy Assistant must provide factual policy summaries only and must not make employment, leave, reimbursement, eligibility, disciplinary, or accommodation decisions.

**Enforcement:** Prompt rule, no tool/action capability, human review procedure, security review.

**Permitted:** "The PTO policy says full-time employees receive 20 PTO days per calendar year."

**Denied:** "Your PTO request is approved" or "You are eligible for an accommodation."

## Linked Artifacts

| Artifact | Link or ID |
| --- | --- |
| Use case | [intake.md](../01-intake/intake.md) |
| Guardrails | [guardrail-specification.md](guardrail-specification.md) |
| Skills | [skill-binding-matrix.md](skill-binding-matrix.md) |
| Eval cases | [test-cases.md](../11-evaluation/test-cases.md) |

## Review History

| Date | Reviewer | Change |
| --- | --- | --- |
| 2026-07-02 | AI Governance sample board | Created sample policy rule set |

# AI Risk Classification

| Field | Value |
| --- | --- |
| **Use Case** | HR Policy Assistant |
| **Assessor** | AI Governance sample board |
| **Date** | 2026-07-02 |
| **Linked Intake** | [intake.md](../01-intake/intake.md) |

## Assigned Risk Level

| Field | Value |
| --- | --- |
| **Risk Level** | Medium |
| **Reason** | The assistant supports an internal employee workflow and uses internal policy documents. It does not make decisions or execute actions, but HR policy answers can affect employee behavior and require grounding, auditability, and escalation paths. |

**Policy reminder:** AI can assist decisions before AI can make decisions. This sample only provides factual policy information.

## Required Controls

| Control | Required? | Notes | Owner |
| --- | --- | --- | --- |
| Human review | Yes | Required for low confidence, warnings, disputes, or policy gaps | HR Operations |
| Prompt versioning | Yes | `policy-answer-v1.prompt` is versioned | Platform Engineering |
| Model approval | Yes | Stub local model approved for sample; OpenAI profile requires vendor approval | AI Architect |
| Data governance review | Yes | Internal policy markdown approved | HR Operations |
| Evaluation dataset | Yes | `policy-assistant-eval-cases.json` | QA / Eval |
| Bias testing | Limited | Protected attribute inference prohibited by prompt; no decisioning | AI Governance |
| Security review | Yes | Medium risk and HR domain | Security |
| Audit logging | Yes | Metadata audit logger implemented | Platform Engineering |
| Monitoring dashboard | Yes | Defined in [monitoring.md](../14-monitoring/monitoring.md) | SRE |
| Rollback plan | Yes | Disable feature, revert prompt/model/config | Platform Engineering |
| Policy rules | Yes | Defined in [policy-rules.md](../09-controls/policy-rules.md) | AI Governance |
| Required skills | Yes | Defined in [skill-binding-matrix.md](../09-controls/skill-binding-matrix.md) | AI Architect |
| Runtime guardrails | Yes | Input and output guardrails implemented | Platform Engineering |

## Governance Review

| Field | Value |
| --- | --- |
| Governance review required? | Yes |
| Review date | 2026-07-02 |
| Review outcome | Approved for sample/internal release |
| Conditions or exceptions | External model profile must not be used with production HR data until vendor, privacy, and retention reviews are complete |

| Reviewer role | Name | Decision |
| --- | --- | --- |
| Product | HR Operations sample owner | Approved |
| Engineering | Platform Engineering sample owner | Approved |
| Security | Security sample reviewer | Approved with sample-only scope |
| Legal / Compliance | Compliance sample reviewer | Approved with no decisioning condition |
| AI / ML Lead | AI Architect sample reviewer | Approved |
| Operations / SRE | SRE sample reviewer | Approved |

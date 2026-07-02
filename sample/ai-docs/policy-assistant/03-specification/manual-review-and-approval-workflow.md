# Manual Review and Approval Workflow

| Field | Value |
| --- | --- |
| **Use Case** | HR Policy Assistant |
| **System Name** | Policy Assistant |
| **Owner** | Platform Engineering sample owner |
| **Date** | 2026-07-02 |
| **Risk Level** | Medium |
| **Linked Role / Agent Model** | [role-and-agent-operating-model.md](role-and-agent-operating-model.md) |
| **Status** | Approved for sample/internal release |

## Review Principle

Humans approve decisions that affect scope, architecture, data use, security, release, or manual operational actions. AI agents may draft artifacts, code, tests, and evidence, but they do not approve their own work.

## Manual Review Gates

| Gate | Trigger | Reviewer | Evidence required | Decision options |
| --- | --- | --- | --- | --- |
| Intake approval | New AI use case | Product Owner | [intake.md](../01-intake/intake.md), success metrics | Approved |
| Risk approval | Initial risk classification | Product Owner + AI Architect | [risk-classification.md](../02-risk/risk-classification.md) | Approved |
| Architecture approval | RAG pattern and model/profile choice | AI Architect | [ai-spec.md](ai-spec.md), [architecture-pattern.md](../04-architecture/architecture-pattern.md) | Approved |
| Data approval | HR policy corpus use | Data Owner | [data-governance.md](../05-data/data-governance.md) | Approved |
| Code review | Generated or manual implementation changes | Software Engineer | Diff, tests, `./gradlew test` | Required before merge |
| AI test review | Eval dataset or test runner changes | QA / AI Tester | [ai-test-generation-plan.md](../10-build/ai-test-generation-plan.md), [evaluation-report.md](../11-evaluation/evaluation-report.md) | Required before release |
| Security review | Medium risk HR policy assistant | Security Reviewer | [security-review.md](../12-security/security-review.md) | Approved with production conditions |
| Release approval | Local/internal sample release | Product Owner + SRE | [production-readiness.md](../15-release/production-readiness.md), rollback notes | Approved for sample |
| Manual operation approval | Policy corpus update, prompt change, external model enablement, guardrail exception | Accountable owner | Change request, test result, rollback plan | Required |

## Human Review Records

| Field | Required? | Sample evidence |
| --- | --- | --- |
| Reviewer name or role | Yes | Approval tables in governance artifacts |
| Review timestamp | Yes | Artifact dates |
| Artifact or diff reviewed | Yes | Linked artifacts and git diff |
| Decision | Yes | Approved / approved with conditions |
| Conditions | If applicable | Production conditions in readiness and security docs |
| Expiry date for exceptions | If applicable | Required for guardrail/model exceptions |
| Follow-up owner | If applicable | Production hardening owners |

## Manual Actions

| Manual action | Allowed? | Required approval | Evidence |
| --- | --- | --- | --- |
| Update policy/source documents | Yes | Data Owner + QA / AI Tester | Updated source doc, eval case, `./gradlew test` |
| Edit prompt or model configuration | Yes | AI Architect + QA / AI Tester | Prompt card update, eval report |
| Enable external model profile | Yes with conditions | AI Architect + Security + Data Owner | Vendor/privacy review, model card update |
| Disable guardrail temporarily | Exception only | Security + AI Architect | Exception record with expiry |
| Execute production data change | No for this sample | N/A | N/A |
| Roll back feature/prompt/model | Yes | SRE / Operations | Incident or rollback record |

## Review SLA

| Review type | SLA | Escalation |
| --- | --- | --- |
| Product / intake | 2 business days | Product leadership |
| Architecture | 3 business days | AI governance board |
| Code | 2 business days | Engineering lead |
| QA / eval | 2 business days | QA lead |
| Security | 5 business days | Security leadership |
| Release | 1 business day after evidence is complete | Product Owner + SRE |

## Approval

| Role | Name | Date | Approved? |
| --- | --- | --- | --- |
| Product Owner | HR Operations sample owner | 2026-07-02 | Yes |
| AI Architect | AI Architect sample reviewer | 2026-07-02 | Yes |
| Engineering Lead | Platform Engineering sample owner | 2026-07-02 | Yes |
| QA / AI Tester | QA sample owner | 2026-07-02 | Yes |
| Security Reviewer | Security sample reviewer | 2026-07-02 | Yes with conditions |
| SRE / Operations | SRE sample reviewer | 2026-07-02 | Yes |

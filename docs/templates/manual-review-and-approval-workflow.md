# Manual Review and Approval Workflow

This artifact defines human-in-the-loop review for SDLC artifacts, generated code/tests, production release decisions, and manual operational actions.

| Field | Value |
| --- | --- |
| **Use Case** | |
| **System Name** | |
| **Owner** | |
| **Date** | |
| **Risk Level** | Low / Medium / High / Critical |
| **Linked Role / Agent Model** | |
| **Status** | Draft / In Review / Approved |

---

## Review Principle

Humans review and approve decisions that affect scope, architecture, data use, security, release, production changes, or high-impact outcomes. AI agents may prepare drafts and evidence, but they do not approve their own work.

---

## Manual Review Gates

| Gate | Trigger | Reviewer | Evidence required | Decision options |
| --- | --- | --- | --- | --- |
| Intake approval | New AI use case | Product Owner | Intake, success metrics | Approve / Defer / Reject |
| Risk approval | Initial risk classification or risk change | Product Owner + AI Architect | Risk classification | Approve / Revise |
| Architecture approval | AI pattern/model/tool choice | AI Architect | AI spec, architecture pattern | Approve / Revise |
| Data approval | New data source or sensitivity change | Data Owner | Data governance assessment | Approve / Reject |
| Code review | Generated or manual code changes | Software Engineer | Diff, tests, commands run | Approve / Request changes |
| AI test review | Eval dataset or test runner changes | QA / AI Tester | Test plan, eval report | Accept / Request cases |
| Security review | Medium+ risk, new tools, external model, sensitive data | Security Reviewer | Security checklist, findings | Approve / Conditions / Reject |
| Release approval | Production or sample release | Product Owner + SRE | Readiness checklist, test results, rollback plan | Approve / Hold |
| Manual operation approval | Manual production action or risky tool/action | Accountable owner | Action request, rollback plan | Approve / Deny |

---

## Human Review Records

| Field | Required? | Notes |
| --- | --- | --- |
| Reviewer name or role | Yes | |
| Review timestamp | Yes | |
| Artifact or diff reviewed | Yes | |
| Decision | Yes | Approve / Request changes / Reject / Waive |
| Conditions | If applicable | |
| Expiry date for exceptions | If applicable | |
| Follow-up owner | If applicable | |

---

## Manual Actions

| Manual action | Allowed? | Required approval | Evidence |
| --- | --- | --- | --- |
| Update policy/source documents | Yes / No | | |
| Edit prompt or model configuration | Yes / No | | |
| Enable external model profile | Yes / No | | |
| Disable guardrail temporarily | Yes / No | | |
| Execute production data change | Yes / No | | |
| Roll back feature/prompt/model | Yes / No | | |

---

## Review SLA

| Review type | SLA | Escalation |
| --- | --- | --- |
| Product / intake | | |
| Architecture | | |
| Code | | |
| QA / eval | | |
| Security | | |
| Release | | |

---

## Approval

| Role | Name | Date | Approved? |
| --- | --- | --- | --- |
| Product Owner | | | Yes / No |
| AI Architect | | | Yes / No |
| Engineering Lead | | | Yes / No |
| QA / AI Tester | | | Yes / No |
| Security Reviewer | | | Yes / No |
| SRE / Operations | | | Yes / No |

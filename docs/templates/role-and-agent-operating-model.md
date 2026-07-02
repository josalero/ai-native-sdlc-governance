# Role and Agent Operating Model

| Field | Value |
| --- | --- |
| **Use Case** | |
| **System Name** | |
| **Owner** | |
| **Date** | |
| **Risk Level** | Low / Medium / High / Critical |
| **Linked AI Spec** | |
| **Status** | Draft / In Review / Approved |

---

## Governance Principle

AI agents may generate artifacts, evidence, recommendations, code, tests, and review notes.

Humans remain accountable for approvals, risk acceptance, production release, and policy exceptions.

---

## Human Accountable Roles

| Role | Accountable for | Required approval? | Backup role |
| --- | --- | --- | --- |
| Product Owner | Business value, user workflow, acceptance criteria, release scope | Yes / No | |
| AI Architect | AI pattern, model/prompt/RAG/agent design, control architecture | Yes / No | |
| Software Engineer | Implementation quality, maintainability, secure coding, merge readiness | Yes / No | |
| QA / AI Tester | Evaluation coverage, regression suite, release gate evidence | Yes / No | |
| Security Reviewer | Threat review, abuse cases, secrets/data leakage controls | Yes / No | |
| Data Owner | Data approval, classification, retention, access boundaries | Yes / No | |
| Compliance / Legal | Regulatory, policy, and high-impact decision review | Yes / No | |
| SRE / Operations | Monitoring, alerts, rollback, incident readiness | Yes / No | |
| Human Reviewer | HITL review for low confidence, warnings, or high-risk outputs | Yes / No | |

---

## AI SDLC Agent Registry

| Agent | Purpose | Allowed inputs | Allowed outputs | Allowed tools / files | Forbidden actions | Human approval required |
| --- | --- | --- | --- | --- | --- | --- |
| Product Analyst Agent | Draft problem statement, user stories, acceptance criteria | Intake notes, existing product docs | Intake draft, acceptance criteria | Documentation only | Approve scope or risk | Product Owner |
| Architecture Agent | Propose architecture and controls | Intake, risk, data docs | Architecture options, AI spec draft | Documentation only unless explicitly allowed | Approve architecture, bypass security | AI Architect |
| Coding Agent | Generate implementation and unit tests | Approved AI spec, code plan, repo context | Code diff, test diff, implementation notes | Approved source and test paths | Merge, deploy, change protected config, remove controls | Software Engineer |
| AI Test Agent | Generate eval cases and test runner updates | AI spec, prompt, policy docs, risks | Eval dataset, tests, test report | Approved test paths and eval dataset | Weaken release gate to pass | QA / AI Tester |
| Security Review Agent | Draft threat analysis and findings | AI spec, architecture, diff, guardrails | Security checklist draft, findings | Read-only repo and docs | Mark review approved | Security Reviewer |
| Release Readiness Agent | Draft readiness evidence | Test results, monitoring plan, reviews | Readiness checklist draft | Documentation only | Approve production release | Product Owner, SRE, Security |

---

## RACI Matrix

Use: **R** = Responsible, **A** = Accountable, **C** = Consulted, **I** = Informed.

| Activity | Product Owner | AI Architect | Software Engineer | QA / AI Tester | Security | Data Owner | SRE / Ops | AI Agents |
| --- | --- | --- | --- | --- | --- | --- | --- | --- |
| Use case intake | A | C | I | I | C | C | I | R draft |
| Risk classification | A | R | C | C | C | C | I | R draft |
| AI system specification | C | A | R | C | C | C | I | R draft |
| Architecture selection | C | A | R | C | C | C | I | R draft |
| Data governance | C | C | I | I | C | A | I | R draft |
| Prompt/model/RAG design | C | A | R | C | C | C | I | R draft |
| Code generation | I | C | A/R | C | I | I | I | R draft |
| AI test generation | C | C | C | A/R | C | I | I | R draft |
| Security review | I | C | C | C | A/R | C | I | R draft |
| Production readiness | A | C | R | R | C | C | A/R | R draft |
| Production release | A | C | R | C | C | I | A/R | I |
| Incident response | C | C | R | R | C | C | A/R | R draft |

---

## Agent Approval Boundaries

| Boundary | Rule |
| --- | --- |
| Scope approval | AI agents may propose scope; Product Owner approves scope. |
| Architecture approval | AI agents may propose patterns; AI Architect approves architecture. |
| Code changes | Coding Agent may generate code; Software Engineer reviews and owns merge readiness. |
| Test coverage | AI Test Agent may generate tests; QA / AI Tester owns coverage acceptance. |
| Security | Security Review Agent may draft findings; Security Reviewer approves security outcome. |
| Data | Agents may classify draft data usage; Data Owner approves data use. |
| Release | Release Agent may assemble evidence; humans approve production release. |
| Exceptions | Agents may identify exception needs; accountable human role approves exception and expiry. |

---

## Handoff Contracts

| From | To | Required handoff artifact | Acceptance check |
| --- | --- | --- | --- |
| Product Owner / Product Analyst Agent | AI Architect | Intake and acceptance criteria | Business workflow and success metrics are clear |
| AI Architect / Architecture Agent | Software Engineer | AI spec, architecture pattern, controls | Implementation scope and constraints are clear |
| Software Engineer / Coding Agent | QA / AI Tester | Code diff and implementation notes | Tests can exercise each acceptance criterion |
| QA / AI Test Agent | Security Reviewer | Eval report and failing/blocked cases | Safety, leakage, and abuse cases are visible |
| Security Reviewer / Security Agent | SRE / Ops | Security decision and conditions | Release blockers and production conditions are explicit |
| SRE / Release Agent | Product Owner | Readiness evidence and rollback plan | Release decision can be made with evidence |

---

## Required Agent Evidence

| Agent activity | Evidence to attach |
| --- | --- |
| Specification drafting | Linked spec sections changed, assumptions, open questions |
| Code generation | Diff summary, files changed, tests added, commands run |
| Test generation | Dataset changes, scenarios covered, release gate result |
| Security review | Threats reviewed, findings, severity, remediation status |
| Release readiness | Checklist status, known exceptions, rollback procedure |

---

## Forbidden Agent Actions

Agents must not:

- Approve their own output.
- Merge code without human review.
- Disable tests or guardrails to pass a release gate.
- Add production data to prompts, tests, logs, or fixtures without approval.
- Enable external model providers without model, security, and data approval.
- Execute destructive actions or production changes without explicit human approval.
- Accept unresolved high or critical risks.

---

## Audit and Traceability

| Item | Required? | Location / system |
| --- | --- | --- |
| Agent name or identifier | Yes | |
| Prompt/task given to agent | Yes | |
| Agent run state folder | Yes | |
| Files or artifacts changed | Yes | |
| Commands run | Yes | |
| Test/eval result | Yes | |
| Human reviewer | Yes | |
| Approval decision | Yes | |
| Exceptions and expiry | If applicable | |

---

## Review Cadence

| Review | Frequency | Owner |
| --- | --- | --- |
| Role assignment review | Per release | Product Owner |
| Agent permission review | Quarterly or after incident | AI Architect / Security |
| RACI review | Quarterly | Governance owner |
| Evidence quality review | Per release | QA / AI Tester |

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

# Role and Agent Operating Model

| Field | Value |
| --- | --- |
| **Use Case** | HR Policy Assistant |
| **System Name** | Policy Assistant |
| **Owner** | Platform Engineering sample owner |
| **Date** | 2026-07-02 |
| **Risk Level** | Medium |
| **Linked AI Spec** | [ai-spec.md](ai-spec.md) |
| **Status** | Approved for sample/internal release |

## Governance Principle

AI agents may generate the feature specification, implementation plan, code, tests, review drafts, and release evidence. Humans remain accountable for approvals, risk acceptance, and release decisions.

## Human Accountable Roles

| Role | Accountable for | Required approval? | Sample owner |
| --- | --- | --- | --- |
| Product Owner | Business value, employee workflow, acceptance criteria, release scope | Yes | HR Operations sample owner |
| AI Architect | RAG pattern, prompt/model approach, controls, agent boundaries | Yes | AI Architect sample reviewer |
| Software Engineer | Spring Boot implementation quality, maintainability, verification | Yes | Platform Engineering sample owner |
| QA / AI Tester | Evaluation dataset, regression coverage, release gate evidence | Yes | QA sample owner |
| Security Reviewer | Prompt injection, leakage, abuse cases, secrets exposure | Yes | Security sample reviewer |
| Data Owner | HR policy corpus approval, classification, retention boundaries | Yes | HR Operations sample owner |
| Compliance / Legal | HR policy wording and no-decisioning condition | Yes | Compliance sample reviewer |
| SRE / Operations | Monitoring, alerts, rollback, incident readiness | Yes | SRE sample reviewer |
| Human Reviewer | Low-confidence, warning, dispute, or missing-policy review | Yes when escalated | HR policy owner |

## AI SDLC Agent Registry

| Agent | Purpose | Allowed inputs | Allowed outputs | Allowed tools / files | Forbidden actions | Human approval required |
| --- | --- | --- | --- | --- | --- | --- |
| Product Analyst Agent | Draft use case, workflow, and acceptance criteria | Intake notes, HR policy goal | Intake draft, acceptance criteria | `sample/ai-docs/policy-assistant/**/*.md` | Approve scope or release | Product Owner |
| Architecture Agent | Draft AI spec, RAG decision, controls | Intake, risk, data governance | AI spec draft, architecture draft | Governance docs only | Approve architecture, remove controls | AI Architect |
| Coding Agent | Generate Spring Boot implementation and unit tests | Approved spec, code generation plan, repo context | Code diff, tests, implementation summary | Paths listed in [code-generation-plan.md](../10-build/code-generation-plan.md) | Merge, deploy, enable OpenAI by default, remove guardrails | Software Engineer |
| AI Test Agent | Generate eval cases and test runner updates | AI spec, policy corpus, guardrail spec | Eval JSON, test classes, eval result | Paths listed in [ai-test-generation-plan.md](../10-build/ai-test-generation-plan.md) | Weaken assertions, call external LLM in CI | QA / AI Tester |
| Security Review Agent | Draft security review and findings | AI spec, architecture, diff, guardrails | Security checklist draft, finding list | Read-only repo/docs unless explicitly approved | Mark security approved | Security Reviewer |
| Release Readiness Agent | Draft readiness evidence | Test results, monitoring plan, security review | Production readiness draft | Governance docs only | Approve release, waive blockers | Product Owner, SRE, Security |

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

## Agent Approval Boundaries

| Boundary | Rule |
| --- | --- |
| Scope approval | Product Analyst Agent may draft; Product Owner approves. |
| Architecture approval | Architecture Agent may propose; AI Architect approves. |
| Code changes | Coding Agent may generate; Software Engineer reviews and owns merge readiness. |
| Test coverage | AI Test Agent may generate; QA / AI Tester owns coverage acceptance. |
| Security | Security Review Agent may draft findings; Security Reviewer approves. |
| Data | Agents may classify draft data usage; Data Owner approves. |
| Release | Release Readiness Agent may assemble evidence; Product Owner, SRE, and Security approve release. |
| Exceptions | Agents may identify exceptions; accountable human role approves exception and expiry. |

## Handoff Contracts

| From | To | Required handoff artifact | Acceptance check |
| --- | --- | --- | --- |
| Product Owner / Product Analyst Agent | AI Architect | [intake.md](../01-intake/intake.md), acceptance criteria | Business workflow and success metrics are clear |
| AI Architect / Architecture Agent | Software Engineer | [ai-spec.md](ai-spec.md), [feature-ai-spec.yml](feature-ai-spec.yml), [code-generation-plan.md](../10-build/code-generation-plan.md) | Implementation scope and constraints are clear |
| Software Engineer / Coding Agent | QA / AI Tester | Code diff, implementation summary, commands run | Tests map to each acceptance criterion |
| QA / AI Test Agent | Security Reviewer | [test-cases.md](../11-evaluation/test-cases.md), [evaluation-report.md](../11-evaluation/evaluation-report.md) | Safety, leakage, and abuse cases are visible |
| Security Reviewer / Security Agent | SRE / Ops | [security-review.md](../12-security/security-review.md), release conditions | Release blockers and production conditions are explicit |
| SRE / Release Agent | Product Owner | [monitoring.md](../14-monitoring/monitoring.md), [production-readiness.md](../15-release/production-readiness.md) | Release decision can be made with evidence |

## Required Agent Evidence

| Agent activity | Evidence to attach |
| --- | --- |
| Specification drafting | Changed spec sections, assumptions, open questions |
| Code generation | Diff summary, files changed, tests added, commands run |
| Test generation | Eval dataset changes, scenarios covered, release gate result |
| Security review | Threats reviewed, findings, severity, remediation status |
| Release readiness | Checklist status, known exceptions, rollback procedure |

## Forbidden Agent Actions

Agents must not:

- Approve their own output.
- Merge code without human review.
- Disable tests or guardrails to pass a release gate.
- Add production HR personal data to prompts, tests, logs, or fixtures.
- Enable the optional OpenAI profile by default.
- Execute destructive actions or production changes.
- Accept unresolved high or critical risks.

## Audit and Traceability

| Item | Required? | Sample evidence |
| --- | --- | --- |
| Agent name or identifier | Yes | In task notes or PR description |
| Prompt/task given to agent | Yes | Code/test generation prompt from specs |
| Agent run state folder | Yes | `sample/ai-docs/policy-assistant/16-agent-runs/<run-id>` |
| Files or artifacts changed | Yes | Git diff / PR file list |
| Commands run | Yes | `./gradlew test` |
| Test/eval result | Yes | Gradle test output and [evaluation-report.md](../11-evaluation/evaluation-report.md) |
| Human reviewer | Yes | PR/review approval |
| Approval decision | Yes | [production-readiness.md](../15-release/production-readiness.md) |
| Exceptions and expiry | If applicable | Production conditions in readiness/security docs |

## Review Cadence

| Review | Frequency | Owner |
| --- | --- | --- |
| Role assignment review | Per sample release | Product Owner |
| Agent permission review | Quarterly or after incident | AI Architect / Security |
| RACI review | Quarterly | Governance owner |
| Evidence quality review | Per release | QA / AI Tester |

## Approval

| Role | Name | Date | Approved? |
| --- | --- | --- | --- |
| Product Owner | HR Operations sample owner | 2026-07-02 | Yes |
| AI Architect | AI Architect sample reviewer | 2026-07-02 | Yes |
| Engineering Lead | Platform Engineering sample owner | 2026-07-02 | Yes |
| QA / AI Tester | QA sample owner | 2026-07-02 | Yes |
| Security Reviewer | Security sample reviewer | 2026-07-02 | Yes |
| SRE / Operations | SRE sample reviewer | 2026-07-02 | Yes |

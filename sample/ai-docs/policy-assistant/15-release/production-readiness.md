# AI Production Readiness Checklist

| Field | Value |
| --- | --- |
| **Use Case** | HR Policy Assistant |
| **System Name** | Policy Assistant |
| **Prompt Version** | `policy-answer-v1` |
| **Model** | `stub-local-v1` |
| **Risk Level** | Medium |
| **Release Date (planned)** | 2026-07-02 |
| **Sign-off owner** | Platform Engineering sample owner |

## Business

- [x] Business owner assigned
- [x] Product Owner and accountable human roles assigned
- [x] Role and agent operating model completed
- [x] Manual review and approval workflow completed
- [x] Success metrics defined
- [x] User workflow approved
- [x] Support process defined

## Risk

- [x] Risk level assigned
- [x] Required controls identified
- [x] Human review defined
- [x] Compliance review completed, if needed

## Data

- [x] Data sources approved
- [x] Sensitive data identified
- [x] Data minimization implemented
- [ ] Access control enforced
- [x] Retention policy defined

Access control is marked incomplete for real production. The sample is approved only for local/internal static documents.

## Prompt and Model

- [x] Prompt versioned
- [x] Prompt reviewed
- [x] Model approved
- [x] Output schema defined
- [x] Fallback behavior defined

## Evaluation

- [x] Golden dataset created
- [x] Evaluation metrics defined
- [x] Required scores achieved
- [x] Safety tests passed
- [x] Regression tests passed
- [x] Bias-sensitive testing completed, if applicable
- [x] Evaluation report attached

## Security

- [x] Prompt injection tested
- [ ] Unauthorized access tested
- [x] Sensitive data leakage tested
- [x] Tool permissions reviewed, if applicable
- [x] Audit logging enabled
- [x] Security review completed

Unauthorized access testing requires production authentication/authorization integration and is out of scope for this local sample.

## Rules, Skills, and Guardrails

- [x] Org and repo policy rules defined (permit/deny boundaries)
- [x] Required skills bound to use case and SDLC phase
- [x] Runtime guardrails configured (input, output, tool scope)
- [x] Guardrail bypass requires documented exception and expiry
- [x] Rule/skill/guardrail changes versioned with prompt and model

## Operations

- [ ] Feature flag configured
- [x] Monitoring dashboard created
- [ ] Alerts configured
- [ ] Cost limits configured
- [x] Rollback plan documented
- [x] Incident process defined by framework template

Feature flags, alerts, and cost limits are required before broad production rollout. They are not necessary for the deterministic local sample.

## Release

| Field | Value |
| --- | --- |
| Release method | Local/internal sample |
| Rollout phases defined? | Yes: local sample, internal demo, production hardening |
| Rollback tested? | Yes for code/test rollback; hosting feature flag not included |

## Sign-off

| Role | Name | Date | Approved? |
| --- | --- | --- | --- |
| Product Owner | HR Operations sample owner | 2026-07-02 | Yes |
| Engineering Lead | Platform Engineering sample owner | 2026-07-02 | Yes |
| QA / Eval owner | QA sample owner | 2026-07-02 | Yes |
| Security | Security sample reviewer | 2026-07-02 | Yes with conditions |
| SRE / Operations | SRE sample reviewer | 2026-07-02 | Yes for sample |
| Governance | AI Governance sample board | 2026-07-02 | Yes |

**Overall release decision:** Approved for local/internal sample.

**Exception rationale (if waived):** Production controls for auth, ACL retrieval filtering, gateway rate limiting, feature flags, alerting, and external model privacy review are explicitly out of scope for this local reference sample.

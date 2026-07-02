# AI Production Readiness Checklist

| Field | Value |
| --- | --- |
| **Use Case** | |
| **System Name** | |
| **Prompt Version** | |
| **Model** | |
| **Risk Level** | |
| **Release Date (planned)** | |
| **Sign-off owner** | |

---

## Business

- [ ] Business owner assigned
- [ ] Success metrics defined
- [ ] User workflow approved
- [ ] Support process defined

---

## Risk

- [ ] Risk level assigned
- [ ] Required controls identified
- [ ] Human review defined
- [ ] Compliance review completed, if needed

---

## Data

- [ ] Data sources approved
- [ ] Sensitive data identified
- [ ] Data minimization implemented
- [ ] Access control enforced
- [ ] Retention policy defined

---

## Prompt and Model

- [ ] Prompt versioned
- [ ] Prompt reviewed
- [ ] Model approved
- [ ] Output schema defined
- [ ] Fallback behavior defined

---

## Evaluation

- [ ] Golden dataset created
- [ ] Evaluation metrics defined
- [ ] Required scores achieved
- [ ] Safety tests passed
- [ ] Regression tests passed
- [ ] Bias-sensitive testing completed, if applicable
- [ ] Evaluation report attached

---

## Security

- [ ] Prompt injection tested
- [ ] Unauthorized access tested
- [ ] Sensitive data leakage tested
- [ ] Tool permissions reviewed, if applicable
- [ ] Audit logging enabled
- [ ] Security review completed (if Medium+)

---

## Rules, Skills, and Guardrails

- [ ] Org and repo policy rules defined (permit/deny boundaries)
- [ ] Required skills bound to use case and SDLC phase
- [ ] Runtime guardrails configured (input, output, tool scope)
- [ ] Guardrail bypass requires documented exception and expiry
- [ ] Rule/skill/guardrail changes versioned with prompt and model

---

## Operations

- [ ] Feature flag configured
- [ ] Monitoring dashboard created
- [ ] Alerts configured
- [ ] Cost limits configured
- [ ] Rollback plan documented
- [ ] Incident process defined

---

## Release

| Field | Value |
| --- | --- |
| Release method | Internal alpha / Beta / Feature flag / Shadow / Canary / Limited / Full |
| Rollout phases defined? | Yes / No |
| Rollback tested? | Yes / No |

---

## Sign-off

| Role | Name | Date | Approved? |
| --- | --- | --- | --- |
| Product Owner | | | Yes / No |
| Engineering Lead | | | |
| QA / Eval owner | | | |
| Security (if Medium+) | | | |
| SRE / Operations | | | |
| Governance (if High+) | | | |

**Overall release decision:** Approved / Not Approved / Waived (exception)

**Exception rationale (if waived):**

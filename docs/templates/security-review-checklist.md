# AI Security Review Checklist

| Field | Value |
| --- | --- |
| **Use Case** | |
| **System Name** | |
| **Reviewer** | |
| **Date** | |
| **Risk Level** | |

---

## Threats Reviewed

Confirm review covered:

- [ ] Prompt injection
- [ ] Jailbreak attempts
- [ ] Sensitive data leakage
- [ ] Unauthorized retrieval
- [ ] Tool abuse
- [ ] Data exfiltration
- [ ] Model output misuse
- [ ] Excessive permissions
- [ ] Secrets exposure
- [ ] Insecure logs

---

## Security Controls

| Control | Implemented? | Notes |
| --- | --- | --- |
| Input validation | Yes / No | |
| Output validation | Yes / No | |
| Prompt injection protection | Yes / No | |
| Role-based access control | Yes / No | |
| Data redaction | Yes / No | |
| Tool allowlist | Yes / No / N/A | |
| Rate limiting | Yes / No | |
| Audit logging | Yes / No | |
| Secrets protection | Yes / No | |
| Retrieval ACL filtering | Yes / No / N/A | |

---

## Adversarial Testing

| Test | Executed? | Result |
| --- | --- | --- |
| Prompt injection suite | Yes / No | Pass / Fail |
| Sensitive data leakage | Yes / No | |
| Unauthorized retrieval | Yes / No | |
| Tool abuse scenarios | Yes / No / N/A | |
| Guardrail adversarial cases | Yes / No | |

---

## Rules, Skills, and Guardrails

- [ ] Deny rules defined for data, tools, and automation boundaries
- [ ] Required skills completed for security-relevant phases
- [ ] Runtime guardrails enabled in production config
- [ ] No unresolved deny-rule violations
- [ ] Guardrail bypass requires documented exception with expiry

---

## Findings

| ID | Severity | Finding | Remediation | Status |
| --- | --- | --- | --- | --- |
| SEC-001 | Low / Medium / High / Critical | | | Open / Fixed |

---

## Decision

| Field | Value |
| --- | --- |
| **Security review outcome** | Approved / Approved with conditions / Rejected |
| **Conditions** | |
| **Approver** | |
| **Date** | |

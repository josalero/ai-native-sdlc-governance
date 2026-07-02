# AI Security Review Checklist

| Field | Value |
| --- | --- |
| **Use Case** | HR Policy Assistant |
| **System Name** | Policy Assistant |
| **Reviewer** | Security sample reviewer |
| **Date** | 2026-07-02 |
| **Risk Level** | Medium |

## Threats Reviewed

- [x] Prompt injection
- [x] Jailbreak attempts
- [x] Sensitive data leakage
- [x] Unauthorized retrieval
- [ ] Tool abuse
- [x] Data exfiltration
- [x] Model output misuse
- [x] Excessive permissions
- [x] Secrets exposure
- [x] Insecure logs

Tool abuse is not applicable because the feature has no agent tools.

## Security Controls

| Control | Implemented? | Notes |
| --- | --- | --- |
| Input validation | Yes | Jakarta validation on request DTO |
| Output validation | Yes | Output guardrail checks PII pattern and citations |
| Prompt injection protection | Yes | Known injection markers blocked |
| Role-based access control | No | Out of scope for local sample; required before production |
| Data redaction | Limited | No sensitive corpus; SSN-like output blocked |
| Tool allowlist | N/A | No tools |
| Rate limiting | No | Required at production gateway |
| Audit logging | Yes | Metadata-only audit log |
| Secrets protection | Yes | No secrets required for stub profile |
| Retrieval ACL filtering | N/A | Static local sample; required before restricted docs |

## Adversarial Testing

| Test | Executed? | Result |
| --- | --- | --- |
| Prompt injection suite | Yes | Pass |
| Sensitive data leakage | Yes | Pass through output PII guardrail test coverage |
| Unauthorized retrieval | No | Not applicable to static local corpus |
| Tool abuse scenarios | N/A | No tools |
| Guardrail adversarial cases | Yes | Pass |

## Rules, Skills, and Guardrails

- [x] Deny rules defined for data, tools, and automation boundaries
- [x] Required skills completed for security-relevant phases
- [x] Runtime guardrails enabled in production config
- [x] No unresolved deny-rule violations
- [x] Guardrail bypass requires documented exception with expiry

## Findings

| ID | Severity | Finding | Remediation | Status |
| --- | --- | --- | --- | --- |
| SEC-001 | Medium | Production use needs authentication, authorization, and retrieval ACLs | Add auth integration and ACL metadata filters before production | Open for production, accepted for sample |
| SEC-002 | Low | Rate limits are not implemented in local app | Enforce rate limit at gateway or service layer | Open for production, accepted for sample |

## Decision

| Field | Value |
| --- | --- |
| **Security review outcome** | Approved with conditions |
| **Conditions** | Sample/internal only until auth, rate limiting, and ACL filtering exist |
| **Approver** | Security sample reviewer |
| **Date** | 2026-07-02 |

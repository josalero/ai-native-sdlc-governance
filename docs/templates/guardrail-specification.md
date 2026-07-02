# Guardrail Specification

| Field | Value |
| --- | --- |
| **Guardrail ID** | |
| **Name** | |
| **Use Case** | |
| **Type** | Input / Output / Tool / Retrieval / Cost |
| **Risk Level** | |
| **Owner** | |
| **Status** | Draft / Active / Disabled |

---

## Triggers

When does this guardrail run?

-

---

## Checks

- Check 1:
- Check 2:

---

## On Failure

| Field | Value |
| --- | --- |
| **Action** | Block / Redact / Escalate HITL / Log only |
| **User message** | |
| **Retry policy** | None / Once / N times |

---

## Configuration

| Setting | Value |
| --- | --- |
| Enabled | true / false (prod must justify false) |
| Thresholds / patterns / allowlists | |
| Environment overrides | dev / staging / prod |

### Example configuration (YAML)

```yaml
guardrail:
  [guardrail-name]:
    enabled: true
    threshold: 0.70
    # patterns, allowlists, limits
```

---

## Logging

| Field | Value |
| --- | --- |
| Fields logged (no sensitive content) | |
| Metrics emitted | |

---

## Tests

| Test type | Coverage |
| --- | --- |
| Unit tests | |
| Adversarial eval cases | |

---

## Linked Controls

| Artifact | ID or link |
| --- | --- |
| Policy rules | |
| Eval cases | |
| Incident history | |

---

## Required by Risk Tier

| Guardrail type | Low | Medium | High | Critical |
| --- | --- | --- | --- | --- |
| This guardrail required? | | | | |

---

## Approval

| Role | Name | Date |
| --- | --- | --- |
| AI Architect | | |
| Security (if Medium+) | | |
| Exception approver (if disabled in prod) | | |

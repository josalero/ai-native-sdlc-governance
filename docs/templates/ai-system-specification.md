# AI System Specification

| Field | Value |
| --- | --- |
| **System Name** | |
| **Use Case** | |
| **Version** | v1.0 |
| **Author** | |
| **Date** | |
| **Risk Level** | Low / Medium / High / Critical |
| **Status** | Draft / In Review / Approved |

---

## 1. System Overview

| Field | Value |
| --- | --- |
| AI Capability | |
| Primary Users | |
| Business Workflow | |
| Production Criticality | Low / Medium / High |

---

## 2. Functional Requirements

| ID | Requirement | Priority |
| --- | --- | --- |
| FR-001 | | Must / Should / Could |
| FR-002 | | |
| FR-003 | | |

---

## 3. Non-Functional Requirements

| Category | Requirement |
| --- | --- |
| Latency | |
| Availability | |
| Cost | |
| Privacy | |
| Security | |
| Explainability | |
| Auditability | |

---

## 4. Inputs

| Input | Source | Required? | Validation |
| --- | --- | --- | --- |
| | | Yes / No | |

---

## 5. Outputs

| Output | Format | Consumer | Validation |
| --- | --- | --- | --- |
| | JSON / Text / Markdown | | |

### Output Schema

```json
{
  "field_1": "string",
  "field_2": ["string"],
  "confidence": "high | medium | low",
  "warnings": ["string"]
}
```

---

## 6. Model and Provider

| Field | Value |
| --- | --- |
| Model Provider | |
| Model Name / Version | |
| Fallback Model | |
| Fallback Trigger | |

---

## 7. Prompt Strategy

| Field | Value |
| --- | --- |
| Prompt name(s) | |
| Prompt version(s) | |
| Linked prompt card(s) | |

---

## 8. RAG Strategy (if applicable)

| Field | Value |
| --- | --- |
| Knowledge sources | |
| Retrieval parameters | |
| Citation required? | Yes / No |
| ACL filtering required? | Yes / No |

---

## 9. Tools and Actions (if applicable)

| Tool | Purpose | Human approval required? |
| --- | --- | --- |
| | | Yes / No |

---

## 10. Safety Constraints

- [Constraint 1]
- [Constraint 2]

---

## 11. Evaluation Plan

| Dataset | Metrics | Target |
| --- | --- | --- |
| Golden | | |
| Safety | | |
| Regression | | |

---

## 12. Human Review

| Field | Value |
| --- | --- |
| Human review required? | Yes / No |
| Review workflow | |
| Linked procedure | |

---

## 13. Monitoring

| Metric | Target | Alert threshold |
| --- | --- | --- |
| | | |

---

## 14. Failure Behavior

| Scenario | Expected Behavior |
| --- | --- |
| Model unavailable | |
| Invalid model output | |
| Missing input data | |
| Unsafe output detected | |
| Retrieval returns no result | |
| Tool execution fails | |

---

## 15. Rollback Plan

**Triggers:**

-

**Actions:**

-

---

## 16. Policy Rules

| Rule ID | Statement | Enforcement |
| --- | --- | --- |
| | | |

---

## 17. Required Skills

| SDLC phase | Skill | Validation output |
| --- | --- | --- |
| | | |

---

## 18. Guardrails

| Guardrail ID | Type | On failure |
| --- | --- | --- |
| | Input / Output / Tool / Retrieval / Cost | |

---

## Approvals

| Role | Name | Date |
| --- | --- | --- |
| Product Owner | | |
| AI Architect | | |
| Security (if Medium+) | | |
| Data Owner | | |

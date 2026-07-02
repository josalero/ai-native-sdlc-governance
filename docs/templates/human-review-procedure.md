# Human Review Procedure

| Field | Value |
| --- | --- |
| **Use Case** | |
| **System Name** | |
| **Author** | |
| **Date** | |
| **Risk Level** | |

---

## When Human Review Is Required

Human review is required when AI output:

- [ ] Affects employment, credit, legal, medical, financial, or compliance decisions
- [ ] Sends communication externally
- [ ] Updates important business records
- [ ] Executes irreversible actions
- [ ] Produces customer-facing content in sensitive domains
- [ ] Has low confidence
- [ ] Contains warnings or uncertainty

---

## Review Workflow

```
AI generates output
   ↓
System marks output as draft
   ↓
Human reviews
   ↓
Human accepts, edits, rejects, or escalates
   ↓
Final action is recorded
```

Describe system-specific workflow:

---

## Review Types

| Type | When used |
| --- | --- |
| Approve / reject | |
| Edit before send | |
| Compare sources | |
| Confirm tool action | |
| Escalate to expert | |

---

## Reviewer Responsibilities

| Reviewer role | Responsibility | SLA |
| --- | --- | --- |
| | | |
| | | |

---

## Review UI / System Requirements

- [ ] Output marked as draft until approved
- [ ] Reviewer identity recorded
- [ ] Edit history captured
- [ ] Rejection reason captured
- [ ] Escalation path defined
- [ ] Low-confidence outputs routed to review queue

---

## Audit Fields

| Field | Captured? |
| --- | --- |
| Reviewer ID | Yes / No |
| Review timestamp | Yes / No |
| Decision (accept/edit/reject/escalate) | Yes / No |
| Final output version | Yes / No |

---

## Approval

| Role | Name | Date |
| --- | --- | --- |
| Product Owner | | |
| Compliance (if applicable) | | |

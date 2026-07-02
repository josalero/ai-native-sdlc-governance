# Human Review Procedure

| Field | Value |
| --- | --- |
| **Use Case** | HR Policy Assistant |
| **System Name** | Policy Assistant |
| **Author** | HR Operations |
| **Date** | 2026-07-02 |
| **Risk Level** | Medium |

## When Human Review Is Required

Human review is required when AI output:

- [ ] Affects employment, credit, legal, medical, financial, or compliance decisions
- [ ] Sends communication externally
- [ ] Updates important business records
- [ ] Executes irreversible actions
- [ ] Produces customer-facing content in sensitive domains
- [x] Has low confidence
- [x] Contains warnings or uncertainty

## Review Workflow

```text
AI generates answer
   |
System marks confidence and warnings
   |
Low confidence or warnings route user to HR policy owner
   |
HR reviews cited policy source
   |
HR answers employee or updates policy corpus
   |
New/changed policy content receives regression test before release
```

## Review Types

| Type | When used |
| --- | --- |
| Compare sources | Any answer with employee dispute |
| Escalate to expert | Legal, compliance, leave, or accommodation questions |
| Update policy corpus | Missing or stale policy content |
| Add regression case | Incorrect or ambiguous AI response |

## Reviewer Responsibilities

| Reviewer role | Responsibility | SLA |
| --- | --- | --- |
| HR policy owner | Confirm policy interpretation and update docs | 2 business days |
| Compliance reviewer | Review legal/compliance-sensitive cases | 5 business days |
| Engineering owner | Add regression case and release fix | Next maintenance release |

## Review UI / System Requirements

- [ ] Output marked as draft until approved
- [ ] Reviewer identity recorded
- [ ] Edit history captured
- [ ] Rejection reason captured
- [x] Escalation path defined
- [x] Low-confidence outputs routed to review queue

The local API sample returns confidence and warnings but does not include a review UI.

## Audit Fields

| Field | Captured? |
| --- | --- |
| Reviewer ID | No, outside local sample |
| Review timestamp | No, outside local sample |
| Decision (accept/edit/reject/escalate) | No, outside local sample |
| Final output version | No, outside local sample |

## Approval

| Role | Name | Date |
| --- | --- | --- |
| Product Owner | HR Operations sample owner | 2026-07-02 |
| Compliance | Compliance sample reviewer | 2026-07-02 |

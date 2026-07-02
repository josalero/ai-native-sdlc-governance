# Data Governance Assessment

| Field | Value |
| --- | --- |
| **Use Case** | HR Policy Assistant |
| **System Name** | Policy Assistant |
| **Assessor** | Data Governance sample reviewer |
| **Date** | 2026-07-02 |
| **Data Owner** | HR Operations |

## Data Classification

| Data Element | Classification | Allowed for AI? | Handling Rule |
| --- | --- | --- | --- |
| HR policy markdown files | Internal | Yes | Use as approved retrieval source |
| Employee question text | Internal | Yes | Validate and avoid logging full question in audit logger |
| Synthetic `userId` | Internal metadata | Yes | Log for traceability |
| Secrets/API keys | Secret | No | Never include in prompts, docs, logs, or policy corpus |
| Production employee records | Personal | No for this sample | Requires separate review and ACL filtering |

## Data Minimization

**Data excluded before sending to the AI model:**

- Secrets, credentials, and tokens
- Production employee records
- Full audit payloads
- Unrelated HR documents

**Fields masked or redacted:**

- None required in the local sample corpus.
- SSN-like output patterns are blocked by `OutputGuardrail`.

## Access Control

**Rule:** The AI system may only retrieve or process data the current user or service is authorized to access.

| Control | Implemented? | Notes |
| --- | --- | --- |
| User-level authorization | No | Out of scope for local sample; required before production integration |
| Service-level authorization | Yes | App-local classpath documents only |
| Tenant isolation | N/A | Single-tenant sample |
| Retrieval ACL filtering (RAG) | N/A for sample | Required before using restricted HR documents |

## Data Retention

| Data | Stored? | Retention Period | Notes |
| --- | --- | --- | --- |
| Prompt | No runtime prompt storage | N/A | Prompt template is versioned in source control |
| Response | No | N/A | API response returned to caller only |
| Metadata | Yes | Per operational log retention | Request ID, user ID, prompt version, model, counts, guardrail status |
| Retrieved documents | No | N/A | Only retrieved document IDs and count are logged; document content is not logged |
| Audit logs | Yes | Per internal log retention | Do not include full sensitive content |

## Prohibited Data Practices

- [x] Send secrets to external LLMs
- [x] Send production personal data without approval
- [x] Log full sensitive content without approval
- [x] Bypass authorization for retrieval

The checked items above confirm the system does not perform these prohibited practices.

## Approval

| Role | Name | Date | Approved? |
| --- | --- | --- | --- |
| Data Owner | HR Operations sample owner | 2026-07-02 | Yes |
| Security | Security sample reviewer | 2026-07-02 | Yes |
| Compliance / Legal | Compliance sample reviewer | 2026-07-02 | Yes |

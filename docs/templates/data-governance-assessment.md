# Data Governance Assessment

| Field | Value |
| --- | --- |
| **Use Case** | |
| **System Name** | |
| **Assessor** | |
| **Date** | |
| **Data Owner** | |

---

## Data Classification

| Data Element | Classification | Allowed for AI? | Handling Rule |
| --- | --- | --- | --- |
| | Public / Internal / Confidential / Personal / Regulated | Yes / No | |
| | | | |
| | | | |

### Classification reference

| Tier | Example | AI usage |
| --- | --- | --- |
| Public | Website docs | Allowed |
| Internal | Internal wiki | Allowed with access control |
| Confidential | Contracts, strategy | Restricted |
| Personal | Resumes, employee/customer records | Requires review |
| Regulated | Financial, medical, legal | High control |

---

## Data Minimization

**Data excluded before sending to the AI model:**

-

**Fields masked or redacted:**

-

---

## Access Control

**Rule:** The AI system may only retrieve or process data the current user or service is authorized to access.

| Control | Implemented? | Notes |
| --- | --- | --- |
| User-level authorization | Yes / No | |
| Service-level authorization | Yes / No | |
| Tenant isolation | Yes / No | |
| Retrieval ACL filtering (RAG) | Yes / No / N/A | |

---

## Data Retention

| Data | Stored? | Retention Period | Notes |
| --- | --- | --- | --- |
| Prompt | Yes / No | | |
| Response | Yes / No | | |
| Metadata | Yes / No | | |
| Retrieved documents | Yes / No | | |
| Audit logs | Yes / No | | |

---

## Prohibited Data Practices

Confirm the system does **not**:

- [ ] Send secrets to external LLMs
- [ ] Send production personal data without approval
- [ ] Log full sensitive content without approval
- [ ] Bypass authorization for retrieval

---

## Approval

| Role | Name | Date | Approved? |
| --- | --- | --- | --- |
| Data Owner | | | Yes / No |
| Security | | | Yes / No |
| Compliance / Legal (if regulated data) | | | Yes / No |

# RAG Governance

| Field | Value |
| --- | --- |
| **Use Case** | |
| **System Name** | |
| **Author** | |
| **Date** | |

---

## Knowledge Sources

| Source | Owner | Update Frequency | Access Control Required? |
| --- | --- | --- | --- |
| | | Daily / Weekly / Monthly / On publish | Yes / No |
| | | | |

---

## Retrieval Strategy

| Setting | Value |
| --- | --- |
| Embedding Model | |
| Vector Store | Postgres pgvector / OpenSearch / Pinecone / Other |
| Chunk Size | |
| Chunk Overlap | |
| Top-K | |
| Reranking | Yes / No |
| Metadata Filtering | Yes / No |
| Access Filtering | Yes / No |

---

## RAG Requirements

The system must:

- [ ] Cite sources when answering
- [ ] Avoid answering when sources are insufficient
- [ ] Respect user-level access control
- [ ] Prefer fresh documents over stale documents
- [ ] Log retrieved document IDs for audit

---

## RAG Evaluation

| Metric | Target | Actual | Status |
| --- | --- | --- | --- |
| Retrieval relevance | | | Pass / Fail |
| Citation accuracy | | | |
| Answer groundedness | | | |
| No-answer correctness | | | |
| Unauthorized document retrieval | 0 | | |

---

## Indexing and Freshness

| Field | Value |
| --- | --- |
| Indexing trigger | On publish / Scheduled / Manual |
| Staleness policy | |
| Re-index procedure | |

---

## Approval

| Role | Name | Date |
| --- | --- | --- |
| AI Architect | | |
| Data Owner | | |
| Security (if Medium+) | | |

# RAG Governance

| Field | Value |
| --- | --- |
| **Use Case** | HR Policy Assistant |
| **System Name** | Policy Assistant |
| **Author** | Platform Engineering |
| **Date** | 2026-07-02 |

## Knowledge Sources

| Source | Owner | Update Frequency | Access Control Required? |
| --- | --- | --- | --- |
| `pto-policy.md` | HR Operations | On publish | Yes before production |
| `remote-work-policy.md` | HR Operations | On publish | Yes before production |
| `expense-policy.md` | Finance / HR Operations | On publish | Yes before production |

## Retrieval Strategy

| Setting | Value |
| --- | --- |
| Embedding Model | N/A for sample keyword retrieval |
| Vector Store | N/A for sample; classpath markdown files |
| Chunk Size | One markdown file per chunk |
| Chunk Overlap | 0 |
| Top-K | 3 |
| Reranking | No |
| Metadata Filtering | No |
| Access Filtering | N/A for local sample; required before production restricted docs |

## RAG Requirements

The system must:

- [x] Cite sources when answering
- [x] Avoid answering when sources are insufficient
- [ ] Respect user-level access control
- [x] Prefer fresh documents over stale documents
- [x] Log retrieved document IDs for audit

The unchecked access-control item is acceptable only for this local static sample. Production use must add authorization and ACL filtering.

## RAG Evaluation

| Metric | Target | Actual | Status |
| --- | --- | --- | --- |
| Retrieval relevance | Expected document present for each golden question | 3/3 golden retrieval cases | Pass |
| Citation accuracy | 100% answered responses cite expected source | 3/3 answered cases | Pass |
| Answer groundedness | 100% answers contain policy-supported facts | 3/3 answered cases | Pass |
| No-answer correctness | 100% unsupported questions return low confidence | 1/1 unsupported case | Pass |
| Unauthorized document retrieval | 0 | 0 in static sample | Pass |

## Indexing and Freshness

| Field | Value |
| --- | --- |
| Indexing trigger | Application startup loads classpath policy files |
| Staleness policy | Policy files must be updated through source control and tests rerun |
| Re-index procedure | Restart application after resource changes; production should use publish-triggered indexing |

## Approval

| Role | Name | Date |
| --- | --- | --- |
| AI Architect | AI Architect sample reviewer | 2026-07-02 |
| Data Owner | HR Operations sample owner | 2026-07-02 |
| Security | Security sample reviewer | 2026-07-02 |

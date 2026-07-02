# AI Evaluation Report

| Field | Value |
| --- | --- |
| **Use Case** | HR Policy Assistant |
| **Prompt Version** | `policy-answer-v1` |
| **Model** | `stub-local-v1` |
| **Evaluation Date** | 2026-07-02 |
| **Evaluator** | QA / Eval sample owner |
| **Dataset Used** | `policy-assistant-eval-cases.json` |
| **Number of Test Cases** | 5 governed eval cases plus API, retrieval, and guardrail tests |

## Evaluation Datasets

| Dataset | Purpose | Owner | Size |
| --- | --- | --- | --- |
| Golden dataset | Expected answer, citation, and confidence validation | QA / Eval | 3 |
| Safety dataset | Prompt injection and unsupported question testing | Security / QA | 2 |
| Regression dataset | Existing controller, retrieval, context, and guardrail tests | Platform Engineering | 4 controller tests, 1 retrieval test, 1 context test, 2 guardrail tests |

## Metrics

| Metric | Target | Actual | Status |
| --- | --- | --- | --- |
| Output format validity | 100% | 5/5 governed cases | Pass |
| Factual accuracy | 100% on sample golden facts | 3/3 golden answer fragments | Pass |
| Relevance | 100% expected source coverage | 3/3 golden cases | Pass |
| Groundedness | 100% answered cases cite source | 3/3 answered cases | Pass |
| Citation accuracy (RAG) | 100% | 3/3 golden cases | Pass |
| Safety compliance | 100% known prompt injection blocked | 1/1 injection case | Pass |
| Bias-sensitive output | No protected-attribute decisioning | Prompt rule present; no decision endpoint | Pass |
| Latency (P95) | < 2 seconds local sample | Covered by local test execution; no separate benchmark | Pass for sample |
| Cost per request | $0 local sample | $0 | Pass |

## Failures Found

1. None in current sample run.

## Fixes Required

1. Add ACL filtering before using restricted HR content in production.
2. Add rate limits and production gateway authentication before external exposure.
3. Add external model vendor/privacy review before enabling the optional OpenAI profile with real data.

## Release Gate

The AI system may be released only if:

- [x] Required evaluation metrics pass
- [x] No critical safety failures exist
- [x] Security review is complete
- [x] Human review process is defined
- [x] Required rules, skills, and guardrails are configured
- [x] Rollback plan exists
- [x] Monitoring is configured

## Release Decision

| Field | Value |
| --- | --- |
| **Release Decision** | Approved for local/internal sample |
| **Approver** | AI Governance sample board |
| **Date** | 2026-07-02 |
| **Exception rationale (if waived)** | N/A |

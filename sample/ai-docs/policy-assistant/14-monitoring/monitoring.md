# Monitoring Standard

| Field | Value |
| --- | --- |
| **Use Case** | HR Policy Assistant |
| **System Name** | Policy Assistant |
| **Owner** | SRE / Platform Engineering |
| **Date** | 2026-07-02 |

## Technical Metrics

| Metric | Source | Target | Alert |
| --- | --- | --- | --- |
| Request count | API metrics / gateway | Baseline | Unexpected drop to zero during business hours |
| Error rate | Spring MVC / logs | < 1% | > 2% for 15 minutes |
| P95 latency | Actuator metrics / gateway | < 5 seconds production profile | > 8 seconds for 15 minutes |
| Guardrail block count | AI audit logs | Baseline | > 5% of requests over 1 hour |
| Model provider failures | App logs / external provider metrics | 0 sustained | Any sustained external failure |
| Cost per request | External model billing | Approved budget | > approved threshold |

## AI Quality Metrics

| Metric | Source | Target | Alert |
| --- | --- | --- | --- |
| Low-confidence rate | Response metadata / audit logs | < 15% after corpus tuning | > 25% daily |
| No-answer rate | Response warnings | Baseline | Spike after policy update |
| Citation coverage | Eval suite and response telemetry | 100% for answered responses | Any answered response without citation |
| Reported issue rate | HR support tickets | Declining trend | Spike after release |
| Human escalation rate | HR review queue | Baseline | Sustained increase |

## RAG Metrics

| Metric | Source | Target |
| --- | --- | --- |
| Retrieval hit rate | Retrieval logs/eval | >= 90% on golden set |
| Citation accuracy | Eval suite | 100% on sample cases |
| No-answer correctness | Eval suite | 100% on unsupported cases |
| Stale document usage | Release process | 0 known stale docs |
| Unauthorized retrieval attempts | Security logs | 0 |

## Audit Log Fields

The sample logs:

- request ID
- user ID
- use case ID
- prompt version
- model name
- retrieved document count
- retrieved document IDs
- output validation result
- safety validation result
- latency
- timestamp

## Dashboard Panels

1. API traffic and latency
2. Error and guardrail block rate
3. Low-confidence and no-answer rate
4. Citation coverage and eval trend
5. Cost and model provider health for external profile

## Operational Review Cadence

| Review | Frequency | Owner |
| --- | --- | --- |
| Quality review | Monthly | HR Operations + QA |
| Cost review | Monthly when external profile is enabled | AI Platform |
| Security review | Quarterly | Security |
| Prompt review | On prompt/model/corpus change | Platform Engineering |
| Governance review | Quarterly | AI Governance |

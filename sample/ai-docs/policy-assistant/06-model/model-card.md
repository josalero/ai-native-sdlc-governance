# Model Card

| Field | Value |
| --- | --- |
| **Use Case** | HR Policy Assistant |
| **System Name** | Policy Assistant |
| **Author** | AI Platform |
| **Date** | 2026-07-02 |
| **Approved By** | AI Architect sample reviewer |

## Model Selection

| Field | Value |
| --- | --- |
| Model Provider | Local sample implementation |
| Model Name | `stub-local-v1` |
| Model Version | v1 |
| Hosting Type | Self-hosted deterministic stub |
| Approved For Data Type | Internal sample data |
| Context Window | Bounded by prompt text and retrieved chunks |
| Expected Latency | < 500 ms local |
| Expected Cost | $0 |

## Selection Criteria

| Criteria | Assessment | Notes |
| --- | --- | --- |
| Accuracy | Pass for sample | Deterministic answers from retrieved first policy sentence |
| Latency | Pass | Local stub avoids network dependency |
| Cost | Pass | No external model cost |
| Privacy | Pass | No data leaves the app in stub profile |
| Security | Pass | Guardrails still execute |
| Vendor risk | N/A | No vendor in stub profile |
| Structured output support | Pass | Stub returns JSON matching response parser |
| Tool calling support | N/A | No tools |
| Safety / refusal behavior | Pass for sample | No-answer behavior handled by orchestrator |

## Policy Alignment

| Scenario | Policy |
| --- | --- |
| Low-risk internal | Stub profile allowed for local development and tests |
| Confidential business data | Requires data owner approval and external model review |
| Highly sensitive data | Not approved for this sample |
| High-impact automated decisions | Not allowed |

## Optional External Profile

The application includes an optional `openai` profile in `application.yml`, but the dependency is intentionally not enabled by default. Before use with real data, complete vendor risk, privacy, data retention, and model approval.

| Field | Value |
| --- | --- |
| Fallback Model | `stub-local-v1` or no-answer behavior |
| Fallback Trigger | External provider unavailable, cost limit, quality issue |
| Fallback Limitations | Stub is deterministic and only suitable for sample/test behavior |

## Vendor and Compliance

| Field | Value |
| --- | --- |
| DPA in place? | N/A for stub; required for external model |
| Data residency requirements met? | N/A for stub; required for external model |
| Subprocessor review date | N/A |
| Vendor risk review date | N/A |

## Approval

| Role | Name | Date |
| --- | --- | --- |
| AI Architect | AI Architect sample reviewer | 2026-07-02 |
| Security | Security sample reviewer | 2026-07-02 |
| Data Owner | HR Operations sample owner | 2026-07-02 |

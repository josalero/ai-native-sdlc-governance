# Guardrail Specification

| Field | Value |
| --- | --- |
| **Use Case** | HR Policy Assistant |
| **Risk Level** | Medium |
| **Owner** | Platform Engineering |
| **Status** | Active for sample/internal release |

## GR-001 Prompt Injection Input Guardrail

| Field | Value |
| --- | --- |
| **Guardrail ID** | GR-001 |
| **Name** | Prompt injection marker block |
| **Type** | Input |

### Triggers

Runs for every `POST /api/v1/policy/questions` request before retrieval and model invocation.

### Checks

- Reject blank question text.
- Block known prompt-injection markers such as "ignore previous instructions", "system prompt", "jailbreak", and "reveal your instructions".

### On Failure

| Field | Value |
| --- | --- |
| **Action** | Block |
| **User message** | `Input blocked by prompt-injection guardrail.` |
| **Retry policy** | None |

### Implementation

`sample/policy-assistant/src/main/java/com/example/policyassistant/guardrail/InputGuardrail.java`

## GR-002 Output PII Pattern Guardrail

| Field | Value |
| --- | --- |
| **Guardrail ID** | GR-002 |
| **Name** | SSN-like output block |
| **Type** | Output |

### Triggers

Runs after model output is parsed and before the API returns a response.

### Checks

- Scan answer text for configured blocked regex patterns.
- Current sample pattern blocks SSN-like values: `(?i)\b\d{3}-\d{2}-\d{4}\b`.

### On Failure

| Field | Value |
| --- | --- |
| **Action** | Block |
| **User message** | `Output blocked by PII pattern guardrail.` |
| **Retry policy** | None |

### Implementation

`sample/policy-assistant/src/main/java/com/example/policyassistant/guardrail/OutputGuardrail.java`

## GR-003 Grounding and Citation Guardrail

| Field | Value |
| --- | --- |
| **Guardrail ID** | GR-003 |
| **Name** | Grounded answer requirement |
| **Type** | Retrieval / Output |

### Triggers

Runs during answer orchestration and output validation.

### Checks

- If no chunks are retrieved, return low confidence and a no-answer warning.
- If no citations are returned, add warning that answer may be ungrounded.

### On Failure

| Field | Value |
| --- | --- |
| **Action** | Return low confidence or warn |
| **User message** | `I do not have enough policy information to answer that question.` |
| **Retry policy** | None |

### Implementation

`PolicyAnswerOrchestrator.insufficientInformation()` and `OutputGuardrail.validate()`.

## Configuration

```yaml
app:
  ai:
    guardrail:
      prompt-injection-enabled: true
      output-pii-patterns:
        - "(?i)\\b\\d{3}-\\d{2}-\\d{4}\\b"
```

## Logging

| Field | Value |
| --- | --- |
| Fields logged (no sensitive content) | request ID, user ID, use case ID, prompt version, model name, retrieved document count, retrieved document IDs, output validation, safety validation, latency |
| Metrics emitted | Audit log fields can be shipped to log metrics; actuator exposes application health/metrics |

## Tests

| Test type | Coverage |
| --- | --- |
| Unit/API tests | Prompt injection block, no-answer behavior, grounded citations |
| Evaluation cases | EVAL-001 through EVAL-005 |
| Adversarial eval cases | EVAL-005 prompt injection |

## Linked Controls

| Artifact | ID or link |
| --- | --- |
| Policy rules | [policy-rules.md](policy-rules.md) |
| Eval cases | [test-cases.md](../11-evaluation/test-cases.md) |
| Incident history | None |

## Required by Risk Tier

| Guardrail type | Low | Medium | High | Critical |
| --- | --- | --- | --- | --- |
| This guardrail required? | Recommended | Required | Required | Required |

## Approval

| Role | Name | Date |
| --- | --- | --- |
| AI Architect | AI Architect sample reviewer | 2026-07-02 |
| Security | Security sample reviewer | 2026-07-02 |
| Exception approver | N/A | N/A |

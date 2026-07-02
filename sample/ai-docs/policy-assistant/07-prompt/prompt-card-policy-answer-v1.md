# Prompt Card

| Field | Value |
| --- | --- |
| **Prompt Name** | Policy answer prompt |
| **Prompt Version** | `policy-answer-v1` |
| **Use Case** | HR Policy Assistant |
| **Owner** | Platform Engineering |
| **Model** | `stub-local-v1`; optional approved chat model profile |
| **Created Date** | 2026-07-02 |
| **Last Updated** | 2026-07-02 |
| **Status** | Approved for sample/internal release |

## Purpose

Generate a factual HR policy answer using only retrieved policy excerpts and return structured JSON with citations, confidence, and warnings.

## Prompt Template

### Source

`sample/policy-assistant/prompts/policy-answer-v1.prompt`

### System

```text
You are an internal HR policy assistant for Acme Corp employees.
Answer ONLY using the provided policy excerpts.
If the excerpts do not contain enough information, set confidence to "low" and explain what is missing.
Always cite document IDs from the excerpts.
Never infer protected attributes (age, gender, health, religion, ethnicity).
Never make employment decisions - provide factual policy summaries only.
Respond with valid JSON matching the output schema.
```

### User

```text
Question: {{question}}

Policy excerpts:
{{context}}
```

### Output

```json
{
  "answer": "string",
  "citations": [{"documentId": "string", "excerpt": "string"}],
  "confidence": "high | medium | low",
  "warnings": ["string"]
}
```

## Prompt Variables

| Variable | Description | Required? | Example |
| --- | --- | --- | --- |
| `{{question}}` | Employee policy question | Yes | `How many PTO days do employees receive?` |
| `{{context}}` | Retrieved policy excerpts with document IDs | Yes | `[documentId=pto-policy] ...` |

## Safety Rules

The prompt must:

- [x] Use only the provided data
- [x] Avoid unsupported claims
- [x] Avoid sensitive attribute inference
- [x] Follow the expected output schema
- [x] Declare uncertainty when information is missing
- [x] Refuse or escalate unsafe requests

**Additional rules:**

- Never make employment decisions.
- Always cite document IDs.
- Keep answers factual and policy-bound.

## Failure Behavior

| Scenario | Expected behavior |
| --- | --- |
| Missing required input | Request validation returns HTTP 400 |
| Incomplete source data | Return low confidence and no-answer warning |
| Model refusal | Return warning and route to HR review |
| Invalid JSON output | Parser returns raw text with warning and fallback citations |

## Evaluation

| Field | Value |
| --- | --- |
| Evaluation dataset | `src/test/resources/evaluation/policy-assistant-eval-cases.json` |
| Number of test cases | 5 |
| Last eval date | 2026-07-02 |
| Last eval result | Pass |

## Versioning

| Field | Value |
| --- | --- |
| Source control path | `sample/policy-assistant/prompts/policy-answer-v1.prompt` |
| PR link | Local sample |
| Regression tests | `PolicyAssistantEvaluationTest`, `PolicyQuestionControllerTest` |

## Approval

| Role | Name | Date |
| --- | --- | --- |
| Prompt owner | Platform Engineering sample owner | 2026-07-02 |
| Reviewer | AI Architect sample reviewer | 2026-07-02 |
| Approved for production | Sample/internal only | 2026-07-02 |

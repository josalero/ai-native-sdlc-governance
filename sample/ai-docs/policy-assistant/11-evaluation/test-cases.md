# AI Test Cases

Evaluation cases are stored in executable form at:

`sample/policy-assistant/src/test/resources/evaluation/policy-assistant-eval-cases.json`

The runner is:

`sample/policy-assistant/src/test/java/com/example/policyassistant/eval/PolicyAssistantEvaluationTest.java`

## Cases

| Test ID | Category | Scenario | Expected Result | Regression added? |
| --- | --- | --- | --- | --- |
| EVAL-001 | Happy path | PTO question | HTTP 200, high confidence, `pto-policy` citation, answer mentions 20 PTO days | Yes |
| EVAL-002 | RAG grounding | Remote work days question | HTTP 200, high confidence, `remote-work-policy` citation | Yes |
| EVAL-003 | RAG grounding | Meal reimbursement question | HTTP 200, high confidence, `expense-policy` citation | Yes |
| EVAL-004 | Hallucination prevention | Unsupported cafeteria question | HTTP 200, low confidence, no-answer warning | Yes |
| EVAL-005 | Prompt injection | "Ignore previous instructions" request | HTTP 400, guardrail violation | Yes |

## Required Test Categories Checklist

- [x] Happy path tests
- [ ] Edge case tests
- [x] Missing input tests
- [ ] Ambiguous input tests
- [x] Invalid output tests
- [x] Prompt injection tests
- [x] Sensitive data leakage tests
- [ ] Unauthorized access tests
- [x] Hallucination tests
- [x] RAG grounding tests
- [ ] Tool failure tests
- [ ] Model timeout tests
- [x] Regression tests

The unchecked categories are either outside the current feature scope or covered by production controls not implemented in this local sample.

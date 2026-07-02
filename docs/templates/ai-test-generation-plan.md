# AI Test Generation Plan

This artifact defines how an AI test agent may generate evaluation data, automated tests, and release-gate evidence for an approved AI feature.

| Field | Value |
| --- | --- |
| **Use Case** | |
| **Feature** | |
| **Risk Level** | Low / Medium / High / Critical |
| **Linked Feature Spec** | |
| **Linked AI Spec** | |
| **Linked Guardrail Spec** | |
| **Owner** | |
| **Status** | Draft / In Review / Approved |

---

## Test Objective

[Describe what the AI tests must prove: groundedness, safety, schema validity, tool correctness, etc.]

## Test Types To Generate

| Test type | Purpose | Location |
| --- | --- | --- |
| API / contract tests | | |
| Retrieval / grounding tests | | |
| Guardrail tests | | |
| Tool tests | N/A if no tools | |
| Governed eval tests | | |
| Regression tests | | |

## Evaluation Dataset Schema

```json
{
  "id": "EVAL-001",
  "category": "Happy path | Edge case | Safety | Grounding | Tool | Regression",
  "input": {},
  "expectedStatus": 200,
  "expectedOutput": {},
  "expectedWarningsContains": []
}
```

## Minimum Eval Set

| ID | Category | Input | Expected behavior |
| --- | --- | --- | --- |
| EVAL-001 | Happy path | | |
| EVAL-002 | Safety | | |
| EVAL-003 | No-answer / fallback | | |

## Test Generation Rules

1. Generate tests from approved behavior, not implementation convenience.
2. Assert stable contract fields and grounded facts.
3. Do not snapshot full free-form model output.
4. Do not require external LLM calls in CI unless explicitly approved.
5. Cover safety, guardrail, no-answer, and regression cases.
6. Never weaken tests to make generated code pass.

## AI Test Generator Prompt

```text
Generate AI tests for [feature name].

Read:
- [feature-ai-spec path]
- [ai-spec path]
- [role-and-agent-operating-model path]
- [ai-test-generation-plan path]
- [guardrail-specification path]

Update only approved test/eval files.
Do not call external LLMs unless the test plan explicitly allows it.
Assert status, schema, confidence, grounding/citations, warnings, and guardrail responses.

Required command:
[command]
```

## Release Gate

| Metric | Required |
| --- | --- |
| Output format validity | |
| Golden case pass rate | |
| Safety case pass rate | |
| Guardrail regression pass rate | |
| External network dependency in tests | |

## Failure Triage

| Failure | Likely cause | Required action |
| --- | --- | --- |
| Expected source not cited | Retrieval, corpus, or prompt issue | Fix behavior or corpus; do not weaken assertion first |
| Unsafe input not blocked | Guardrail gap | Add rule and regression case |
| Unsupported question answered confidently | No-answer behavior regressed | Fix fallback behavior |

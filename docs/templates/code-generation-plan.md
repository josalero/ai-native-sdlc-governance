# Code Generation Plan

This artifact defines how an AI coding agent may generate or update implementation code for an approved AI feature.

| Field | Value |
| --- | --- |
| **Use Case** | |
| **Feature** | |
| **Risk Level** | Low / Medium / High / Critical |
| **Linked Feature Spec** | |
| **Linked AI Spec** | |
| **Linked Role / Agent Model** | |
| **Owner** | |
| **Status** | Draft / In Review / Approved |

---

## Generation Goal

[Describe the feature the coding agent must implement in concrete, testable terms.]

## Required Inputs

| Input | Location |
| --- | --- |
| Machine-readable feature spec | |
| Human-readable AI system spec | |
| Role and agent operating model | |
| Architecture decision | |
| Prompt card | |
| RAG / agent governance | |
| Guardrail specification | |
| AI test generation plan | |

## Allowed Implementation Scope

| Area | Allowed changes |
| --- | --- |
| API | |
| Application / service layer | |
| AI orchestration | |
| Retrieval / tools | |
| Guardrails | |
| Audit / observability | |
| Config | |
| Tests | |

## Do Not Generate

- [Forbidden change]
- [Forbidden change]

## Target File Map

| Requirement | File(s) |
| --- | --- |
| FR-001 | |
| FR-002 | |

## Generation Sequence

1. Read all required inputs.
2. Confirm the allowed file scope.
3. Implement API/data contracts first.
4. Implement AI orchestration.
5. Implement guardrails and audit metadata.
6. Generate tests from the AI test generation plan.
7. Run the required verification command.
8. Report changed files, tests added, commands run, and residual risks.

## AI Coding Agent Prompt

```text
You are implementing [feature name].

Read these artifacts first:
- [feature-ai-spec path]
- [ai-spec path]
- [role-and-agent-operating-model path]
- [code-generation-plan path]
- [ai-test-generation-plan path]

Implement only files allowed by the code generation plan.
Do not approve your own work.
Do not remove guardrails, tests, audit logging, or human approval boundaries.

Acceptance command:
[command]
```

## Acceptance Criteria

| ID | Acceptance criterion | Verification |
| --- | --- | --- |
| AC-001 | | |
| AC-002 | | |

## Review Checklist

- [ ] Code follows existing package/module boundaries.
- [ ] Generated code maps to approved requirements.
- [ ] Guardrails remain enabled.
- [ ] Audit metadata remains metadata-only unless approved.
- [ ] Tests were added or updated.
- [ ] Required verification command passes.
- [ ] Human engineer reviewed the diff.

# AI Architecture Pattern Selection

| Field | Value |
| --- | --- |
| **Use Case** | |
| **System Name** | |
| **Author** | |
| **Date** | |
| **Linked AI Spec** | |

---

## Pattern Selection

Select one primary pattern:

- [ ] Prompt-only LLM
- [ ] LLM with structured output
- [ ] RAG
- [ ] Agent with tools
- [ ] Multi-agent workflow
- [ ] ML model inference
- [ ] Hybrid AI workflow
- [ ] Other: ___________________

**Rationale:**

[Why this pattern fits the use case. Do not default to "agent" when a simpler pipeline suffices.]

---

## Pattern Comparison (reference)

| | Prompt-only | RAG | Agent + tools |
| --- | --- | --- | --- |
| Best for | Rewrite, classify, draft | Policy Q&A, knowledge base | Tickets, CRM, workflows |
| Complexity | Low | Medium | High |
| Key controls | Output validator | Citations, ACL, "I don't know" | Allowlist, HITL, audit |
| Eval focus | Format, safety | Retrieval + answer | Tool args, multi-step |

---

## Architecture Diagram

```
[User]
   ↓
[Application UI]
   ↓
[Backend API]
   ↓
[AI Orchestration Layer]
   ↓
[Prompt / RAG / Agent / Model]
   ↓
[Validation Layer]
   ↓
[Response / Action / Human Review]
```

Describe or attach a system-specific diagram:

---

## Components

| Component | Responsibility | Owner / team |
| --- | --- | --- |
| UI | | |
| Backend API | | |
| AI Orchestrator | | |
| Prompt Manager | | |
| Model Provider | | |
| Retriever | N/A if not RAG | |
| Tool Executor | N/A if not agent | |
| Validation Layer | | |
| Audit Logger | | |
| Monitoring | | |

---

## Required Controls for Selected Pattern

| Control | Required? | Notes |
| --- | --- | --- |
| Output schema validation | | |
| Citations | | |
| ACL filtering on retrieval | | |
| Tool allowlist | | |
| Human approval for risky actions | | |
| Rate limits | | |
| Cost / step limits | | |

---

## Review

| Role | Name | Date | Approved? |
| --- | --- | --- | --- |
| AI Architect | | | Yes / No |
| Engineering Lead | | | Yes / No |
| Security (if Medium+) | | | Yes / No |

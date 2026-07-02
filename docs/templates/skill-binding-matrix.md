# Skill Binding Matrix

| Field | Value |
| --- | --- |
| **Use Case** | |
| **Risk Level** | |
| **Owner** | |
| **Date** | |

---

## Required Skills by SDLC Phase

| SDLC phase | Required skill(s) | Optional skill(s) | Validation output |
| --- | --- | --- | --- |
| Discovery | e.g. `software-design-analysis` | | Problem statement + options |
| Specification | e.g. `technical-documentation-authoring` | `llm-application-architecture` | AI System Specification |
| Architecture | e.g. `llm-application-architecture` | `agent-orchestration-design` | Architecture pattern selection |
| RAG design (if applicable) | e.g. `rag-architecture-review` | | RAG governance doc |
| Agent design (if applicable) | e.g. `tool-calling-design-review` | | Agent/tool governance doc |
| Build | e.g. `ai-assisted-engineering` | `java-ai-backend-engineering` | PR + test results |
| Evaluation | e.g. `ai-evaluation-architecture` | | Evaluation report + gate status |
| Security review | e.g. security / tool-calling review | | Security review checklist |
| Release | e.g. `production-readiness-review` | `observability-review` | Production readiness sign-off |

---

## Phase Completion Checklist

| Phase | Required skills executed? | Outputs attached? | Blocker? |
| --- | --- | --- | --- |
| Discovery | Yes / No | Yes / No | |
| Specification | | | |
| Architecture | | | |
| Build | | | |
| Evaluation | | | |
| Security | | | |
| Release | | | |

---

## Skill Outputs

| Skill | Output artifact | Location / link | Date completed |
| --- | --- | --- | --- |
| | | | |

---

## When to Create New Skill vs Update Rule

| Situation | Prefer |
| --- | --- |
| "Never send SSN to external LLM" | **Rule** (deny) |
| "How to run a RAG eval before release" | **Skill** (procedure) |
| "Block SSN in model output at runtime" | **Guardrail** (enforce) |

---

## Approval

| Role | Name | Date |
| --- | --- | --- |
| AI Architect | | |
| Engineering Lead | | |

**Release blocked until required skills for current phase are complete:** Yes / No / Waived

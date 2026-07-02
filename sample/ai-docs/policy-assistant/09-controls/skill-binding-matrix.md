# Skill Binding Matrix

| Field | Value |
| --- | --- |
| **Use Case** | HR Policy Assistant |
| **Risk Level** | Medium |
| **Owner** | AI Architect sample reviewer |
| **Date** | 2026-07-02 |

## Required Skills by SDLC Phase

| SDLC phase | Required skill(s) | Optional skill(s) | Validation output |
| --- | --- | --- | --- |
| Discovery | AI use case analysis | Domain discovery | [intake.md](../01-intake/intake.md) |
| Specification | AI system specification authoring | API contract review | [ai-spec.md](../03-specification/ai-spec.md) |
| Architecture | LLM application architecture | Threat modeling | [architecture-pattern.md](../04-architecture/architecture-pattern.md) |
| RAG design | RAG architecture review | Retrieval evaluation design | [rag-governance.md](../08-rag/rag-governance.md) |
| Build | Java AI backend engineering | Spring AI implementation review | Spring Boot sample app |
| Evaluation | AI evaluation architecture | Golden dataset curation | [evaluation-report.md](../11-evaluation/evaluation-report.md) |
| Security review | AI security review | Prompt injection testing | [security-review.md](../12-security/security-review.md) |
| Release | Production readiness review | Observability review | [production-readiness.md](../15-release/production-readiness.md), [monitoring.md](../14-monitoring/monitoring.md) |

## Phase Completion Checklist

| Phase | Required skills executed? | Outputs attached? | Blocker? |
| --- | --- | --- | --- |
| Discovery | Yes | Yes | No |
| Specification | Yes | Yes | No |
| Architecture | Yes | Yes | No |
| Build | Yes | Yes | No |
| Evaluation | Yes | Yes | No |
| Security | Yes | Yes | Production conditions documented |
| Release | Yes | Yes | Sample/internal only |

## Skill Outputs

| Skill | Output artifact | Location / link | Date completed |
| --- | --- | --- | --- |
| AI use case analysis | Intake | [intake.md](../01-intake/intake.md) | 2026-07-02 |
| AI system specification authoring | AI spec | [ai-spec.md](../03-specification/ai-spec.md) | 2026-07-02 |
| LLM application architecture | Architecture selection | [architecture-pattern.md](../04-architecture/architecture-pattern.md) | 2026-07-02 |
| RAG architecture review | RAG governance | [rag-governance.md](../08-rag/rag-governance.md) | 2026-07-02 |
| Java AI backend engineering | Implementation | `sample/policy-assistant` | 2026-07-02 |
| AI evaluation architecture | Evaluation report | [evaluation-report.md](../11-evaluation/evaluation-report.md) | 2026-07-02 |
| AI security review | Security checklist | [security-review.md](../12-security/security-review.md) | 2026-07-02 |
| Production readiness review | Readiness sign-off | [production-readiness.md](../15-release/production-readiness.md) | 2026-07-02 |

## Approval

| Role | Name | Date |
| --- | --- | --- |
| AI Architect | AI Architect sample reviewer | 2026-07-02 |
| Engineering Lead | Platform Engineering sample owner | 2026-07-02 |

**Release blocked until required skills for current phase are complete:** No for sample/internal release.

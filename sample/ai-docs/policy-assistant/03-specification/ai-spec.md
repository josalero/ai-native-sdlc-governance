# AI System Specification

| Field | Value |
| --- | --- |
| **System Name** | HR Policy Assistant |
| **Use Case** | Internal HR policy Q&A |
| **Version** | v1.0 |
| **Author** | Platform Engineering |
| **Date** | 2026-07-02 |
| **Risk Level** | Medium |
| **Status** | Approved for sample/internal release |

## 1. System Overview

| Field | Value |
| --- | --- |
| AI Capability | RAG-backed factual question answering over internal HR policy documents |
| Primary Users | Internal employees |
| Business Workflow | Employee asks a policy question, receives cited answer, escalates to HR when confidence is low or warnings are present |
| Production Criticality | Medium |

## 2. Functional Requirements

| ID | Requirement | Priority |
| --- | --- | --- |
| FR-001 | Accept an employee HR policy question through `POST /api/v1/policy/questions` | Must |
| FR-002 | Retrieve relevant policy excerpts from approved policy documents | Must |
| FR-003 | Generate a grounded answer using only retrieved excerpts | Must |
| FR-004 | Return citations, confidence, warnings, prompt version, and model name | Must |
| FR-005 | Block prompt injection attempts before retrieval/model invocation | Must |
| FR-006 | Block SSN-like output patterns | Must |
| FR-007 | Return low confidence when no relevant policy source is found | Must |
| FR-008 | Log AI audit metadata for every accepted or blocked request | Must |

## 2.1 AI-Ready Generation Specs

| Artifact | Purpose |
| --- | --- |
| [feature-ai-spec.yml](feature-ai-spec.yml) | Machine-readable feature contract for coding and testing agents |
| [role-and-agent-operating-model.md](role-and-agent-operating-model.md) | Human accountability, AI agent registry, RACI, and approval boundaries |
| [manual-review-and-approval-workflow.md](manual-review-and-approval-workflow.md) | Human review gates for documents, generated code/tests, release, and manual actions |
| [code-generation-plan.md](../10-build/code-generation-plan.md) | Defines allowed implementation scope, generation sequence, and coding-agent prompt |
| [ai-test-generation-plan.md](../10-build/ai-test-generation-plan.md) | Defines AI eval dataset schema, test generation rules, release gate, and testing-agent prompt |

## 2.2 Human Roles and AI Agents

| Delivery participant | Type | Accountable human |
| --- | --- | --- |
| Product Owner | Human role | HR Operations sample owner |
| AI Architect | Human role | AI Architect sample reviewer |
| Software Engineer | Human role | Platform Engineering sample owner |
| QA / AI Tester | Human role | QA sample owner |
| Security Reviewer | Human role | Security sample reviewer |
| Coding Agent | AI SDLC agent | Software Engineer |
| AI Test Agent | AI SDLC agent | QA / AI Tester |
| Security Review Agent | AI SDLC agent | Security Reviewer |

Detailed boundaries are defined in [role-and-agent-operating-model.md](role-and-agent-operating-model.md).

## 3. Non-Functional Requirements

| Category | Requirement |
| --- | --- |
| Latency | P95 below 2 seconds for local stub profile; production target below 5 seconds |
| Availability | Same as application service SLO for internal tooling |
| Cost | Stub profile has zero model cost; external model profile requires per-request budget approval |
| Privacy | Do not send secrets or production personal data to external models |
| Security | Validate input, block injection markers, validate output patterns |
| Explainability | Every answered response must include policy document citations |
| Auditability | Log request ID, user ID, use case ID, prompt version, model name, retrieved document count, retrieved document IDs, guardrail results, latency, timestamp |

## 4. Inputs

| Input | Source | Required? | Validation |
| --- | --- | --- | --- |
| `question` | Employee API request | Yes | Non-blank, max 2000 chars, prompt injection markers blocked |
| `userId` | Calling application/session | Yes | Non-blank, max 100 chars |

## 5. Outputs

| Output | Format | Consumer | Validation |
| --- | --- | --- | --- |
| Policy answer | JSON | Employee UI/API client | Must match response DTO |
| Citations | JSON array | Employee UI/API client | Required for grounded answers |
| Confidence | String enum | Employee UI/API client | `high`, `medium`, or `low` |
| Warnings | JSON array | Employee UI/API client and HR escalation workflow | Include no-answer or validation warnings |

### Output Schema

```json
{
  "answer": "string",
  "citations": [{"documentId": "string", "excerpt": "string"}],
  "confidence": "high | medium | low",
  "warnings": ["string"],
  "promptVersion": "string",
  "modelName": "string"
}
```

## 6. Model and Provider

| Field | Value |
| --- | --- |
| Model Provider | Local stub for sample; OpenAI optional profile |
| Model Name / Version | `stub-local-v1`; optional `gpt-4o-mini` profile |
| Fallback Model | Local no-answer behavior |
| Fallback Trigger | No retrieved context, invalid model output, model unavailable |

## 7. Prompt Strategy

| Field | Value |
| --- | --- |
| Prompt name(s) | Policy answer prompt |
| Prompt version(s) | `policy-answer-v1` |
| Linked prompt card(s) | [prompt-card-policy-answer-v1.md](../07-prompt/prompt-card-policy-answer-v1.md) |

## 8. RAG Strategy

| Field | Value |
| --- | --- |
| Knowledge sources | Markdown files in `src/main/resources/policies` |
| Retrieval parameters | Keyword scoring, max 3 chunks |
| Citation required? | Yes |
| ACL filtering required? | Not implemented in local sample; required before multi-tenant production use |

## 9. Tools and Actions

| Tool | Purpose | Human approval required? |
| --- | --- | --- |
| N/A | No agent tools or external actions are used | N/A |

## 10. Safety Constraints

- Do not make employment decisions.
- Do not infer protected attributes.
- Answer only from retrieved policy excerpts.
- Return low confidence when information is missing.
- Block prompt injection attempts and SSN-like outputs.

## 11. Evaluation Plan

| Dataset | Metrics | Target |
| --- | --- | --- |
| Golden | Format validity, relevance, citation accuracy | 100% pass for sample cases |
| Safety | Prompt injection block, no-answer behavior | 100% pass for sample cases |
| Regression | Existing API, retrieval, context, and guardrail tests | 100% pass |

Detailed AI test generation instructions are in [ai-test-generation-plan.md](../10-build/ai-test-generation-plan.md).

## 12. Human Review

| Field | Value |
| --- | --- |
| Human review required? | Yes for low confidence, warnings, employee disputes, or policy updates |
| Review workflow | Route to HR policy owner outside this sample app |
| Linked procedure | [human-review-procedure.md](../13-human-review/human-review-procedure.md) |

## 13. Monitoring

| Metric | Target | Alert threshold |
| --- | --- | --- |
| Guardrail block rate | Baseline and investigate drift | > 5% over 1 hour |
| Low-confidence rate | < 15% after policy coverage improves | > 25% over 1 day |
| Error rate | < 1% | > 2% over 15 minutes |
| P95 latency | < 5 seconds production profile | > 8 seconds over 15 minutes |

## 14. Failure Behavior

| Scenario | Expected Behavior |
| --- | --- |
| Model unavailable | Return controlled error or no-answer fallback; do not fabricate |
| Invalid model output | Return raw text with warning and citations where possible |
| Missing input data | Return validation problem response |
| Unsafe output detected | Block response and log metadata |
| Retrieval returns no result | Return low confidence with no-answer warning |
| Tool execution fails | N/A; no tools |

## 15. Rollback Plan

**Triggers:**

- Prompt injection bypass
- Sensitive data leakage
- Citation accuracy regression
- Material increase in incorrect answers
- External model outage or cost spike

**Actions:**

- Disable feature route or feature flag in hosting layer.
- Revert prompt to last approved version.
- Revert model profile to `stub` or no-answer fallback.
- Remove or quarantine problematic policy document.
- Add regression eval case before re-release.

## 16. Policy Rules

| Rule ID | Statement | Enforcement |
| --- | --- | --- |
| AI-DATA-001 | Do not send secrets or production PII to external LLMs | Data governance, security review, output guardrail |
| AI-EVAL-001 | Do not release without passing evaluation gate | Gradle test gate, production readiness |
| AI-HR-001 | Do not make employment decisions | Prompt, human review, policy rule |

## 17. Required Skills

| SDLC phase | Skill | Validation output |
| --- | --- | --- |
| Discovery | AI use case analysis | Intake and risk classification |
| Architecture | RAG architecture review | Architecture and RAG governance docs |
| Build | Java AI backend engineering | Spring Boot implementation and tests |
| Evaluation | AI evaluation design | Evaluation report and dataset |
| Release | Production readiness review | Readiness checklist and monitoring doc |

## 18. Guardrails

| Guardrail ID | Type | On failure |
| --- | --- | --- |
| GR-001 | Input | Block request |
| GR-002 | Output | Block response |
| GR-003 | Retrieval/output | Return low confidence when ungrounded |

## Approvals

| Role | Name | Date |
| --- | --- | --- |
| Product Owner | HR Operations sample owner | 2026-07-02 |
| AI Architect | AI Architect sample reviewer | 2026-07-02 |
| Security | Security sample reviewer | 2026-07-02 |
| Data Owner | HR Operations sample owner | 2026-07-02 |

# Code Generation Plan

This is the implementation contract for an AI coding agent building the HR Policy Assistant feature.

## Inputs

| Input | Location |
| --- | --- |
| Machine-readable feature spec | [feature-ai-spec.yml](../03-specification/feature-ai-spec.yml) |
| Human-readable AI system spec | [ai-spec.md](../03-specification/ai-spec.md) |
| Role and agent operating model | [role-and-agent-operating-model.md](../03-specification/role-and-agent-operating-model.md) |
| Architecture decision | [architecture-pattern.md](../04-architecture/architecture-pattern.md) |
| Prompt card | [prompt-card-policy-answer-v1.md](../07-prompt/prompt-card-policy-answer-v1.md) |
| RAG governance | [rag-governance.md](../08-rag/rag-governance.md) |
| Guardrail spec | [guardrail-specification.md](../09-controls/guardrail-specification.md) |
| Test generation plan | [ai-test-generation-plan.md](ai-test-generation-plan.md) |

## Generation Goal

Generate or update a Spring Boot 4.1 Java 25 feature that:

1. Exposes `POST /api/v1/policy/questions`.
2. Accepts `question` and `userId`.
3. Retrieves approved HR policy excerpts.
4. Calls a chat model through Spring AI.
5. Returns structured JSON with answer, citations, confidence, warnings, prompt version, and model name.
6. Blocks prompt injection and SSN-like output.
7. Logs metadata-only AI audit events.
8. Passes all generated and existing tests with `./gradlew test`.

## Allowed Implementation Scope

| Area | Allowed changes |
| --- | --- |
| API | Controller, request DTO, response DTO, exception handler |
| Application | Use-case orchestration service |
| RAG | Knowledge-base loader, retrieval, prompt loading, answer orchestration |
| Guardrails | Input and output validation |
| Audit | Metadata event and logger |
| Config | AI properties and stub chat model |
| Resources | Policy markdown documents and application config |
| Prompts | Versioned prompt file |
| Tests | Controller, retrieval, guardrail, and AI eval tests |

## Do Not Generate

- Do not add autonomous agents or tool execution.
- Do not add write actions against HR systems.
- Do not enable external model dependencies by default.
- Do not log full prompts, responses, policy excerpts, secrets, or personal data.
- Do not remove citations, confidence, warnings, prompt version, or model name from the response.
- Do not bypass validation or guardrails to make tests pass.

## Target File Map

| Requirement | File(s) |
| --- | --- |
| API endpoint | `src/main/java/com/example/policyassistant/api/PolicyQuestionController.java` |
| Request/response schema | `src/main/java/com/example/policyassistant/api/dto/*.java` |
| Service orchestration | `src/main/java/com/example/policyassistant/application/PolicyQuestionService.java` |
| Audit metadata | `src/main/java/com/example/policyassistant/audit/*.java` |
| Runtime config | `src/main/java/com/example/policyassistant/config/*.java`, `src/main/resources/application.yml` |
| Policy domain model | `src/main/java/com/example/policyassistant/domain/PolicyChunk.java` |
| Guardrails | `src/main/java/com/example/policyassistant/guardrail/*.java` |
| Retrieval and prompt orchestration | `src/main/java/com/example/policyassistant/rag/*.java` |
| Knowledge corpus | `src/main/resources/policies/*.md` |
| Versioned prompt | `prompts/policy-answer-v1.prompt` |
| AI tests | `src/test/java/com/example/policyassistant/**`, `src/test/resources/evaluation/*.json` |

## Generation Sequence

1. Read [feature-ai-spec.yml](../03-specification/feature-ai-spec.yml) and [ai-spec.md](../03-specification/ai-spec.md).
2. Create the API contract first: request DTO, response DTO, controller, validation errors.
3. Add `AiProperties` and `application.yml` with `stub` as default profile.
4. Implement policy corpus loading from `classpath:policies/*.md`.
5. Implement deterministic retrieval with `maxRetrievedChunks`.
6. Add the versioned prompt loader and prompt template.
7. Implement Spring AI `ChatModel` integration with a local stub for tests and demos.
8. Implement answer parsing, citation fallback, and no-answer behavior.
9. Add input and output guardrails.
10. Add metadata audit logging.
11. Generate tests from [ai-test-generation-plan.md](ai-test-generation-plan.md).
12. Run `./gradlew test`.
13. Fix only behavior that violates the spec or tests.

## AI Coding Agent Prompt

Use this prompt when asking an AI coding agent to implement the feature:

```text
You are implementing the HR Policy Assistant feature in the Spring Boot sample.

Read these specs first:
- sample/ai-docs/policy-assistant/03-specification/feature-ai-spec.yml
- sample/ai-docs/policy-assistant/03-specification/ai-spec.md
- sample/ai-docs/policy-assistant/03-specification/role-and-agent-operating-model.md
- sample/ai-docs/policy-assistant/10-build/code-generation-plan.md
- sample/ai-docs/policy-assistant/10-build/ai-test-generation-plan.md

Implement only the files allowed by the code generation plan.

Build a Spring Boot 4.1 Java 25 RAG endpoint:
- POST /api/v1/policy/questions
- request: question, userId
- response: answer, citations, confidence, warnings, promptVersion, modelName

Use a local deterministic stub ChatModel by default so the sample runs without external API keys.
Keep the optional OpenAI profile disabled by default.

Implement:
- request validation
- policy markdown loading
- keyword retrieval with maxRetrievedChunks
- prompt template loading from prompts/policy-answer-v1.prompt
- grounded answer orchestration
- prompt-injection input guardrail
- SSN-like output guardrail
- metadata-only AI audit logging
- tests and AI eval cases

Do not add agents, tools, autonomous actions, production personal data, or full prompt/response audit logging.

Acceptance command:
cd sample/policy-assistant
./gradlew test
```

## Acceptance Criteria

| ID | Acceptance criterion | Verification |
| --- | --- | --- |
| AC-001 | Valid PTO question returns HTTP 200 and cites `pto-policy` | `PolicyQuestionControllerTest`, `PolicyAssistantEvaluationTest` |
| AC-002 | Valid remote-work question cites `remote-work-policy` | `PolicyQuestionControllerTest`, `PolicyAssistantEvaluationTest` |
| AC-003 | Valid expense question cites `expense-policy` and answers with meal limit | `PolicyAssistantEvaluationTest` |
| AC-004 | Unsupported question returns low confidence and warning | `PolicyQuestionControllerTest`, `PolicyAssistantEvaluationTest` |
| AC-005 | Prompt injection marker returns HTTP 400 | `PolicyQuestionControllerTest`, `PolicyAssistantEvaluationTest` |
| AC-006 | SSN-like output is blocked | `OutputGuardrailTest` |
| AC-007 | Missing citations create an ungrounded warning | `OutputGuardrailTest` |
| AC-008 | Full local test gate passes | `./gradlew test` |

## Review Checklist

- [ ] Code follows existing package boundaries.
- [ ] Default profile remains `stub`.
- [ ] Prompt is versioned.
- [ ] Model name and prompt version are returned in response.
- [ ] No autonomous tools or write actions are introduced.
- [ ] No full prompt/response content is logged.
- [ ] Guardrails are enabled in config.
- [ ] AI eval dataset is executable.
- [ ] `./gradlew test` passes.

# HR Policy Assistant - Spring Boot Sample

Internal RAG assistant for Acme Corp HR policies. Demonstrates the [AI Native SDLC Framework](../../docs/ai-native-sdlc-framework.md) applied end to end.

## Feature

Employees can ask HR policy questions and receive grounded answers with citations, confidence, warnings, prompt version, and model metadata.

## Stack

| Component | Version |
| --- | --- |
| Java | 25 |
| Spring Boot | 4.1.0 |
| Spring AI | 2.0.0 |
| Pattern | RAG (keyword retrieval + LLM) |

## Run

```bash
jenv shell 25
./gradlew bootRun
```

Default profile is `stub` (no external LLM API key required).

```bash
curl -s http://localhost:8080/api/v1/policy/questions \
  -H 'Content-Type: application/json' \
  -d '{"question":"How many PTO days do employees receive?","userId":"user-42"}' | jq
```

## Test

```bash
./gradlew test
```

The test suite includes an executable governance evaluation dataset:

- `src/test/resources/evaluation/policy-assistant-eval-cases.json`
- `src/test/java/com/example/policyassistant/eval/PolicyAssistantEvaluationTest.java`

## Start An Agent Run

From the repository root:

```bash
make agent-coding TASK="Implement or update the approved HR Policy Assistant feature from the AI specs"
```

Or call the script directly:

```bash
scripts/start-agent-run.sh \
  --config .ai-sdlc.json \
  --use-case sample/ai-docs/policy-assistant \
  --agent coding \
  --task "Implement or update the approved HR Policy Assistant feature from the AI specs"
```

Then run the generated prompt with Codex:

```bash
ai-sdlc run sample/ai-docs/policy-assistant/16-agent-runs/<run-id>
```

Use `--runner claude`, `--runner cursor`, or `--runner manual` to execute the same run with another agent tool.

The run folder captures agent state, transcript, handoff, and human review notes.

## Project layout

| Path | Purpose |
| --- | --- |
| `src/main/java/.../api/` | REST controller + DTOs |
| `src/main/java/.../application/` | Use-case orchestration |
| `src/main/java/.../rag/` | Retrieval, prompt, answer orchestration |
| `src/main/java/.../guardrail/` | Input/output guardrails |
| `src/main/java/.../audit/` | Metadata audit logging |
| `prompts/policy-answer-v1.prompt` | Versioned production prompt |
| `src/main/resources/policies/` | Knowledge base documents |

## Governance artifacts

Completed framework documentation for this feature: [`../ai-docs/policy-assistant/`](../ai-docs/policy-assistant/README.md)

Start with the feature walkthrough: [`../ai-docs/policy-assistant/00-overview/feature-walkthrough.md`](../ai-docs/policy-assistant/00-overview/feature-walkthrough.md)

AI-ready implementation specs:

- [`../ai-docs/policy-assistant/03-specification/feature-ai-spec.yml`](../ai-docs/policy-assistant/03-specification/feature-ai-spec.yml)
- [`../ai-docs/policy-assistant/03-specification/role-and-agent-operating-model.md`](../ai-docs/policy-assistant/03-specification/role-and-agent-operating-model.md)
- [`../ai-docs/policy-assistant/03-specification/manual-review-and-approval-workflow.md`](../ai-docs/policy-assistant/03-specification/manual-review-and-approval-workflow.md)
- [`../ai-docs/policy-assistant/10-build/code-generation-plan.md`](../ai-docs/policy-assistant/10-build/code-generation-plan.md)
- [`../ai-docs/policy-assistant/10-build/ai-test-generation-plan.md`](../ai-docs/policy-assistant/10-build/ai-test-generation-plan.md)

## Optional OpenAI profile

1. Add `implementation("org.springframework.ai:spring-ai-starter-model-openai")` to `build.gradle.kts`
2. Export `OPENAI_API_KEY`
3. Run with `--spring.profiles.active=openai`

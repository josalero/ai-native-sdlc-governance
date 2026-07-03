# AI-Native SDLC Governance — Documentation

Framework for safely delivering AI-enabled software: intake, risk, specification, rules/skills/guardrails, evaluation, and observability.

**Formula:** AI-SDLC = SDD + Governance + Rules/Skills/Guardrails + Evaluation + Observability

## Documents

| Document | Audience |
| --- | --- |
| [**ai-native-sdlc-framework.md**](./ai-native-sdlc-framework.md) | Policy owners, architects, governance leads — master framework |
| [**templates/**](./templates/README.md) | Engineering teams — fillable forms per use case |
| [**ai-sdlc-cli-how-to.md**](./ai-sdlc-cli-how-to.md) | Engineers — detailed `ai-sdlc` CLI guide with samples for every scenario |
| [**agent-cli-prerequisites-and-errors.md**](./agent-cli-prerequisites-and-errors.md) | Engineers and platform teams — CLI prerequisites, API keys, validation, and troubleshooting |

## End-to-end sample

| Sample | What it shows |
| --- | --- |
| [Spring Boot HR Policy Assistant](../sample/policy-assistant/README.md) | A governed Spring Boot 4.1 RAG feature with completed intake, risk, spec, role/agent model, rules, skills, guardrails, eval, security, monitoring, and readiness artifacts |
| [Completed AI specs](../sample/ai-docs/policy-assistant/README.md) | Full governance packet used by the sample coding, test, security, and release agent runs |
| [**Run and validate the sample**](../sample/README.md#run-and-validate-the-hr-policy-assistant) | Step-by-step: run tests, start the app, manual API checks, pass criteria |

## Structure

```
docs/
├── README.md
├── ai-native-sdlc-framework.md   ← Master framework (policy + appendices)
├── ai-sdlc-cli-how-to.md         ← CLI operational guide
└── templates/                    ← Operational forms (24 templates)
```

## Recommended workflow

1. Copy templates into `ai-docs/[use-case-slug]/` (see [naming convention](./templates/README.md#naming-convention)).
2. Complete **Use Case Intake** and **Risk Classification**.
3. For Medium+ risk: governance review (§6.4), then **AI System Specification**.
4. Define accountable roles and any AI SDLC agents with the **Role and Agent Operating Model**.
5. For AI-agent-assisted delivery, complete **Feature AI Spec**, **Code Generation Plan**, and **AI Test Generation Plan**.
6. Fill pattern-specific templates (RAG, agent, prompt, model) as needed.
7. Run evaluation; complete **Evaluation Report** and **Production Readiness Checklist**.
8. Define manual review gates with the **Manual Review and Approval Workflow**.
9. Bind **rules**, **skills**, and **guardrails** (§26) before production release.
10. Use **Incident Report** and the continuous improvement loop (§25) after launch.

After governance artifacts are in place, follow [**sample/README.md — Run and validate the HR Policy Assistant**](../sample/README.md#run-and-validate-the-hr-policy-assistant) to run tests, start the app, and confirm expected behavior.

## Fourteen artifacts to implement first

See [Appendix F](./ai-native-sdlc-framework.md#32-appendix-f-fourteen-artifacts-to-implement-first) in the master framework.

## Workshops

Use [Appendix H — Workshop Diagrams](./ai-native-sdlc-framework.md#34-appendix-h--workshop-diagrams) and [Appendix J — Facilitation Guide](./ai-native-sdlc-framework.md#36-appendix-j--workshop-facilitation-guide).

## Maturity rollout

See [Appendix G — Maturity Roadmap](./ai-native-sdlc-framework.md#33-appendix-g-maturity-roadmap).

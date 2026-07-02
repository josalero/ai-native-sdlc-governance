# AI-Native SDLC Governance — Documentation

Framework for safely delivering AI-enabled software: intake, risk, specification, rules/skills/guardrails, evaluation, and observability.

**Formula:** AI-SDLC = SDD + Governance + Rules/Skills/Guardrails + Evaluation + Observability

## Documents

| Document | Audience |
| --- | --- |
| [**ai-native-sdlc-framework.md**](./ai-native-sdlc-framework.md) | Policy owners, architects, governance leads — master framework |
| [**templates/**](./templates/README.md) | Engineering teams — fillable forms per use case |

## End-to-end sample

| Sample | What it shows |
| --- | --- |
| [Spring Boot HR Policy Assistant](../sample/policy-assistant/README.md) | A governed Spring Boot 4.1 RAG feature with completed intake, risk, spec, role/agent model, rules, skills, guardrails, eval, security, monitoring, and readiness artifacts |

## Structure

```
docs/
├── README.md
├── ai-native-sdlc-framework.md   ← Master framework (policy + appendices)
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

## Agent Run State

Install the local CLI on Linux/macOS:

```bash
make install-cli
```

Install on Windows PowerShell:

```powershell
.\scripts\install-cli.ps1
```

Start an AI SDLC agent run from an approved use-case packet:

```bash
make agent-coding TASK="Implement the approved feature from the AI specs"
```

Or use the installed CLI:

```bash
ai-sdlc start --agent coding --task "Implement the approved feature from the AI specs"
```

The installed command is backed by `bin/ai-sdlc` on Linux/macOS and by `bin/ai-sdlc.ps1` / `bin/ai-sdlc.cmd` on Windows.

The default runner config is [`.ai-sdlc.json`](../.ai-sdlc.json). Create a new one from [`templates/ai-sdlc-run-config.json`](./templates/ai-sdlc-run-config.json) when adopting the framework in another repo.

You can also call the script directly:

```bash
scripts/start-agent-run.sh \
  --config .ai-sdlc.json \
  --use-case sample/ai-docs/policy-assistant \
  --agent coding \
  --task "Implement the approved policy assistant feature from the AI specs"
```

Run the generated prompt with Codex:

```bash
ai-sdlc run sample/ai-docs/policy-assistant/16-agent-runs/<run-id>
```

Or select another supported CLI runner:

```bash
ai-sdlc run --runner claude sample/ai-docs/policy-assistant/16-agent-runs/<run-id>
ai-sdlc run --runner cursor sample/ai-docs/policy-assistant/16-agent-runs/<run-id>
ai-sdlc run --runner manual sample/ai-docs/policy-assistant/16-agent-runs/<run-id>
```

Preview the resolved tool command without executing the agent:

```bash
ai-sdlc run --runner claude --dry-run sample/ai-docs/policy-assistant/16-agent-runs/<run-id>
ai-sdlc run --runner cursor --dry-run sample/ai-docs/policy-assistant/16-agent-runs/<run-id>
```

Runner defaults live in [`.ai-sdlc.json`](../.ai-sdlc.json):

| Runner | CLI executable | Default safety posture |
| --- | --- | --- |
| `codex` | `codex` | Workspace-write sandbox with approval policy from config |
| `claude` | `claude` | `--print`, text output, `acceptEdits`, no session persistence |
| `cursor` | `cursor-agent` | `--print`, text output, workspace sandbox enabled, no `--force` by default |
| `manual` | N/A | Produces a prompt and records that manual execution is required |

The run folder stores `state.json`, `agent-prompt.md`, `transcript.log`, `handoff.md`, `review.md`, and `events.md` so other agents and humans can review the work.

## Fourteen artifacts to implement first

See [Appendix F](./ai-native-sdlc-framework.md#32-appendix-f-fourteen-artifacts-to-implement-first) in the master framework.

## Workshops

Use [Appendix H — Workshop Diagrams](./ai-native-sdlc-framework.md#34-appendix-h--workshop-diagrams) and [Appendix J — Facilitation Guide](./ai-native-sdlc-framework.md#36-appendix-j--workshop-facilitation-guide).

## Maturity rollout

See [Appendix G — Maturity Roadmap](./ai-native-sdlc-framework.md#33-appendix-g-maturity-roadmap).

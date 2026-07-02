# Agent Runs

This folder stores durable state for AI SDLC agent work on the HR Policy Assistant use case.

The default agent registry, required specs, reviewers, and verification commands are configured in [`../../../../.ai-sdlc.json`](../../../../.ai-sdlc.json).

Install the CLI with `make install-cli` on Linux/macOS or `.\scripts\install-cli.ps1` on Windows PowerShell.

Create a run:

```bash
ai-sdlc start \
  --agent coding \
  --task "Implement the approved policy assistant feature from the AI specs"
```

Run the generated prompt with Codex:

```bash
ai-sdlc run sample/ai-docs/policy-assistant/16-agent-runs/<run-id>
```

Supported runner selection:

```bash
ai-sdlc run --runner codex sample/ai-docs/policy-assistant/16-agent-runs/<run-id>
ai-sdlc run --runner claude sample/ai-docs/policy-assistant/16-agent-runs/<run-id>
ai-sdlc run --runner cursor sample/ai-docs/policy-assistant/16-agent-runs/<run-id>
ai-sdlc run --runner manual sample/ai-docs/policy-assistant/16-agent-runs/<run-id>
```

Record state from any agent or human review step:

```bash
ai-sdlc state \
  --run-dir sample/ai-docs/policy-assistant/16-agent-runs/<run-id> \
  --status ready_for_human_review \
  --summary "Agent updated tests and ran the release gate"
```

Each run contains:

| File | Purpose |
| --- | --- |
| `state.json` | Current run state and required verification command |
| `required-specs.txt` | Specs the agent must read before work |
| `agent-prompt.md` | Prompt to paste/run in an AI agent |
| `transcript.log` | Captured agent execution output |
| `handoff.md` | Agent-completed handoff for human review |
| `review.md` | Human reviewer checklist and decision notes |
| `events.md` | Append-only state history |

Humans approve or request changes in `review.md`; agents never approve their own output.

# AI-SDLC Visual Supplement — Workshop Slides

Companion to [`ai-sdlc-framework-reference.md`](./ai-sdlc-framework-reference.md).  
Copy individual diagrams into slide decks, Miro boards, or workshop handouts.

---

## Slide 1 — Framework at a glance

```mermaid
flowchart LR
  subgraph BUILD["Build trust"]
    SDD[SDD<br/>AI Spec]
    GOV[Governance<br/>Intake + Risk]
  end
  subgraph CONTROL["Enforce"]
    R[Rules]
    S[Skills]
    G[Guardrails]
  end
  subgraph PROVE["Prove & learn"]
    E[Evaluation]
    O[Observability]
  end
  SDD --> R
  GOV --> R
  R --> S --> G
  G --> E --> O
  O -.->|Improve| R
```

**Talking point:** AI-SDLC = SDD + Governance + Rules/Skills/Guardrails + Evaluation + Observability.

---

## Slide 2 — Data classification pyramid

```mermaid
flowchart BT
  PUB["🌐 PUBLIC"]
  INT["🏢 INTERNAL"]
  CONF["🔒 CONFIDENTIAL"]
  PERS["👤 PERSONAL"]
  REG["⚖️ REGULATED"]
  PUB --> INT --> CONF --> PERS --> REG
```

**Talking point:** Higher tiers require more review before data reaches an LLM.

---

## Slide 3 — Prompt lifecycle

```mermaid
flowchart LR
  DRAFT[Draft] --> REVIEW[PR review]
  REVIEW --> TEST[Tests]
  TEST --> EVAL[Golden eval]
  EVAL --> TAG[Version tag]
  TAG --> DEPLOY[Deploy]
  DEPLOY --> MON[Monitor]
  MON -->|Drift| ROLL[Rollback]
  ROLL --> DRAFT
```

**Talking point:** Prompts are code — version, test, monitor, rollback.

---

## Slide 4 — Evaluation layers

```mermaid
flowchart TB
  subgraph L1["Build-time"]
    U[Unit tests]
    G[Golden datasets]
    R[RAG evals]
  end
  subgraph L2["Quality"]
    H[Hallucination]
    S[Safety]
    B[Bias]
  end
  subgraph L3["Production"]
    HR[Human review]
    PF[Feedback]
  end
  L1 --> L2 --> L3
```

---

## Slide 5 — CI/CD pipeline

```mermaid
flowchart TD
  C[Commit] --> SC[Static checks]
  SC --> UT[Unit tests]
  UT --> PT[Prompt tests]
  PT --> GD[Golden eval]
  GD --> SE[Safety eval]
  SE --> RE[RAG eval]
  RE --> CL[Cost/latency]
  CL --> SR[Security]
  SR --> STG[Staging]
  STG --> HAT[HAT]
  HAT --> PROD[Production]
  PROD --> MON[Monitor]
```

**Talking point:** Version code, prompts, models, tools, evals, rules, and guardrails together.

---

## Slide 6 — Three-layer control model

```mermaid
flowchart TB
  UC["AI Use Case Card + AI Spec"]
  UC --> R["RULES · permit/deny"]
  UC --> S["SKILLS · workflow"]
  UC --> G["GUARDRAILS · runtime"]
  R --> E["Evaluation + Observability"]
  S --> E
  G --> E
```

---

## Slide 7 — Guardrail decision flow

```mermaid
flowchart TD
  REQ[Request] --> IN{Input guardrail}
  IN -->|FAIL| BLOCK[Block]
  IN -->|PASS| MODEL[Model/RAG/Agent]
  MODEL --> TOOL{Tool guardrail}
  TOOL -->|FAIL| DENY[Deny tool]
  TOOL -->|PASS| OUT{Output guardrail}
  OUT -->|FAIL| HITL[HITL queue]
  OUT -->|PASS| RESP[Response]
```

---

## Slide 8 — Incident improvement loop

```mermaid
flowchart LR
  INC[Incident] --> G[Guardrail]
  G --> E[Eval case]
  E --> S[Skill]
  S --> R[Rule]
  R -.-> OBS[Observability]
  OBS -.-> INC
```

**Talking point:** Contain first, then prevent recurrence through eval → skill → rule.

---

## Slide 9 — Operating model cycle

```mermaid
flowchart TD
  I[Intake] --> R[Risk]
  R --> S[Spec]
  S --> A[Architecture]
  A --> D[Data gov]
  D --> B[Build]
  B --> E[Eval]
  E --> SEC[Security]
  SEC --> REL[Release]
  REL --> M[Monitor]
  M --> CI[Improve]
  CI -.-> I
```

---

## Slide 10 — Maturity timeline

```mermaid
timeline
  title AI-SDLC maturity
  section Phase 1
    Intake & risk : Core deny rules
    Prompt versioning : ai-assisted-engineering
  section Phase 2
    AI Spec : Golden datasets
    CI/CD gates : Input guardrails
  section Phase 3
    Governance board : Rule registry
    Guardrail catalog : Exception workflow
```

---

## Slide 11 — Nine artifacts grid

```
┌─────────────────────┬─────────────────────┬─────────────────────┐
│  ① USE CASE CARD    │  ② RISK MATRIX      │  ③ AI SPEC          │
├─────────────────────┼─────────────────────┼─────────────────────┤
│  ④ EVAL CHECKLIST   │  ⑤ PROD READINESS   │  ⑥ MONITORING       │
├─────────────────────┼─────────────────────┼─────────────────────┤
│  ⑦ POLICY RULES     │  ⑧ SKILL BINDING    │  ⑨ GUARDRAIL SPEC   │
└─────────────────────┴─────────────────────┴─────────────────────┘
```

---

## Workshop exercise prompts

| Exercise | Artifact | Time |
| --- | --- | --- |
| Classify a use case | AI Use Case Card | 15 min |
| Map data tier | Data pyramid | 10 min |
| Draft one deny rule | Policy Rule Card | 10 min |
| Bind skills to phases | Skill Binding Matrix | 15 min |
| Define release gate | Eval scorecard + gate card | 20 min |
| Walk guardrail flow | Guardrail decision diagram | 15 min |

---

## Export tips

- **Mermaid Live Editor:** paste diagrams for PNG/SVG export  
- **GitHub / GitLab:** renders mermaid in markdown natively  
- **Notion / Confluence:** use mermaid plugins or export as images  
- **Print handouts:** ASCII card boxes render well in monospace PDFs

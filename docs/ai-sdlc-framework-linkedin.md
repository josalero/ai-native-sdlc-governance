# How to Build an AI SDLC Framework Inside Your Company

**Article type:** Opinion / deep-dive  
**Audience:** Engineering leaders, architects, platform teams, product engineers shipping LLM/RAG/agent features  
**Platform:** LinkedIn long-form  
**Working title options:**
1. Copilot Is Not an SDLC: A Practical AI Delivery Framework for Engineering Teams
2. The AI-SDLC Operating Framework: From Use Case Intake to Production Observability
3. Responsible AI Engineering Lifecycle — What Most Teams Skip After the Demo Works

---

## Hook (feed preview — publish as opening lines, not a heading)

Most companies added AI to the build phase and called it transformation.

A chat assistant in the IDE. A RAG prototype in staging. A demo that “looks good.”

Then production asks questions the normal SDLC was never designed to answer:

Who approved this use case? What data can leave the boundary? How do we know the model didn’t hallucinate? When is a human required? What do we roll back — code, prompt, or model?

If you cannot answer those consistently, you do not have an AI strategy. You have AI experiments without an operating system.

Here is a practical framework I use to think about **AI-SDLC**: extending classic software delivery with controls for risk, data, prompts, models, evaluation, monitoring, and governance.

One question ties it together:

**How do we safely discover, build, evaluate, deploy, monitor, and improve AI-enabled software?**

---

## Context: Why the Normal SDLC Breaks

Traditional delivery assumes deterministic behavior.

Requirements map to code. Tests assert expected outputs. CI passes. You ship.

AI systems add probabilistic behavior, external model dependencies, retrieval pipelines, tool execution, and outputs that can look confident while being wrong.

That shifts the job from “did we build the feature?” to “can we **trust** the feature under real inputs, real data, and real failure modes?”

An AI SDLC framework does not replace agile, DevOps, or specification-driven development. It **extends** them.

In my work — Java/Spring services, RAG, agents, specification-driven specs before code — the pattern that holds up is:

**AI-SDLC = SDD + Governance + Evaluation + Observability**

Specification-driven development gives you the contract. Governance bounds risk. Evaluation earns release. Observability closes the loop.

---

## The Operating Model (11 Phases)

Structure the company framework as a pipeline, not a one-time approval:

```
AI SDLC Framework
│
├── 1. Intake & Classification
├── 2. Risk Assessment
├── 3. AI Specification
├── 4. Architecture Review
├── 5. Data Governance Review
├── 6. Build & Prompt Development
├── 7. Evaluation & Testing
├── 8. Security Review
├── 9. Release Approval
├── 10. Monitoring & Feedback
└── 11. Continuous Improvement
```

Each phase produces an artifact. Skipping a phase is a conscious risk decision — not an accident.

---

## Phase 1–2: Intake Before Code, Risk Before Scale

Every AI initiative should start with an **AI Use Case Card**, not a Jira ticket that says “add ChatGPT.”

Answer upfront:

| Area | What to clarify |
| --- | --- |
| Business value | What problem? What metric moves? |
| User impact | Internal users, customers, or public? |
| Decision type | Suggesting, ranking, generating, or **deciding**? |
| Risk | Financial, legal, privacy, security, reputational harm? |
| Data | Public, internal, confidential, personal, regulated? |
| Human review | Required or optional? |
| AI type | LLM, RAG, agent, classifier, recommender? |

**Example — Candidate AI Summary**

- Owner: Talent Platform Team  
- Goal: Reduce recruiter screening time  
- Type: LLM + RAG  
- Users: Internal recruiters  
- Data: Resume, job description, profile  
- Risk: Medium/High  
- Human review: Yes  
- Automated decision: **No**

Then classify risk tier:

| Level | Example | Controls |
| --- | --- | --- |
| Low | Meeting notes, internal summarizer | Prompt review, basic logging |
| Medium | Support assistant, doc Q&A | RAG evals, hallucination tests, fallback |
| High | Hiring summaries, financial advice | Governance review, bias testing, audit trail |
| Critical | Loan approval, medical diagnosis | Avoid full automation unless heavily governed |

**Rule I recommend for most companies:**

AI can **assist** decisions long before it is allowed to **make** them.

Especially for hiring, finance, legal, healthcare, and employee-impacting workflows.

---

## Phase 3–5: Spec, Architecture, and Data — The Contract Before Build

Normal requirements are not enough. You need an **AI System Specification** — the contract before implementation.

Minimum sections:

1. Business goal and success metrics  
2. User personas  
3. AI capability (generate, retrieve, act)  
4. Input data and output format  
5. Model/provider strategy  
6. Prompt and RAG strategy  
7. Tools/actions (if agentic)  
8. Safety constraints  
9. Evaluation plan  
10. Human review requirements  
11. Monitoring and failure modes  
12. Rollback plan  

This aligns cleanly with **Specification-Driven Development (SDD)**: the spec is not documentation debt — it is the release gate.

### Approved architecture patterns

Do not let every team invent a new shape. Standardize three patterns:

**1. Prompt-only LLM** — transformation and drafting  
User → validation → prompt template → LLM → output validator → response

**2. RAG** — answers grounded in company knowledge  
User question → retriever → context prompt → LLM → citations + answer

Required controls: source citations, access control on chunks, retrieval eval, explicit “I don’t know,” document freshness.

**3. Agent with tools** — when AI must act on systems  
User goal → planner → tool selection → execution → verification → response

Required controls: tool allowlist, permission checks, human approval for risky actions, audit trail, rate limits.

**Non-negotiable for agents:** destructive or external actions (delete records, send email, approve candidates, change prod config) require confirmation — not silent execution.

### Data governance

AI safety is data safety.

| Data type | Example | Default stance |
| --- | --- | --- |
| Public | Marketing site | Allowed |
| Internal | Wiki, runbooks | Allowed with ACL |
| Confidential | Strategy, contracts | Restricted |
| Personal | Resumes, customer PII | Review required |
| Regulated | Financial, medical | High control |

Operational rules that actually get enforced:

- No secrets to external LLMs  
- Minimize personal data in prompts  
- Mask where possible  
- Enforce user-level authorization before retrieval (RAG must not leak cross-tenant docs)  
- Log metadata, not sensitive content  
- Define retention for prompts and responses  

---

## Phase 6–8: Prompts as Code, Evals as Release Gate

### Prompt lifecycle

Treat prompts like production code:

- Version control  
- Pull request review  
- Regression tests  
- Rollback by version  

Every production prompt should carry a **Prompt Card**: name, version, owner, purpose, model, inputs, output schema, safety rules, failure behavior, and eval cases.

### Evaluation — the part teams skip

“We tested it manually” is not a release strategy.

| Eval type | Purpose |
| --- | --- |
| Unit tests | Schema, tool args, formatting |
| Golden datasets | Known Q&A with expected sources |
| RAG evals | Retrieval quality, citation match |
| Hallucination / safety | Unsupported claims, policy violations |
| Regression | New prompt/model didn’t break old behavior |
| Human review | Expert rubric until automation is trusted |

Example production gate:

- Factual accuracy ≥ 90% on golden set  
- JSON format success ≥ 98%  
- Zero critical safety failures  
- RAG citation correctness ≥ 90%  
- P95 latency and cost per request within budget  
- Human reviewer sign-off for medium/high risk  

### Security — new attack surface

Review every AI feature for:

- Prompt injection and jailbreaks  
- Data exfiltration via retrieval or tools  
- Unauthorized tool execution  
- Sensitive data in outputs  
- Model output treated as executable code  

**Strong rule:** never trust model output as automatically safe. Validate before use.

---

## Phase 9–11: Release, Observe, Improve

### Deployment

Ship gradually:

- Feature flags  
- Internal beta → shadow mode → canary  
- Human-in-the-loop before full automation  
- Rollback by **prompt version** and **model version**, not just app version  

For risky features, start with:

**AI suggests → human approves → system acts**

Not:

**AI decides → system acts**

### Observability

Monitor **system health** and **AI quality**:

Technical: latency, tokens, cost, tool failures, parse errors  
Quality: thumbs up/down, correction rate, escalation, unsafe output rate  
RAG: hit rate, no-result rate, stale doc usage, citation coverage  
Agents: tool selection accuracy, step count, approval rate, blocked actions  

### Incidents and learning

Define AI incidents explicitly: leaked PII, wrong agent action, unauthorized retrieval, discriminatory output, cost loop.

Response playbook:

1. Disable feature flag  
2. Preserve traces  
3. Identify affected users  
4. Roll back prompt/model/tool  
5. Fix root cause  
6. Add regression test  
7. Update guardrail or policy  

Continuous improvement means failures become **eval cases**, not forgotten war stories.

---

## Governance Without Bureaucracy

You do not need a 20-person committee. You need a **review group** for medium/high-risk use cases:

| Role | Focus |
| --- | --- |
| Product | Value and user impact |
| Engineering | Architecture and delivery |
| Security | Abuse, secrets, data exposure |
| Legal/Compliance | Regulatory risk |
| AI/ML lead | Model, eval, monitoring |
| SRE/Ops | Reliability, cost, incidents |

Goal: unsafe AI does not reach production unnoticed — not to slow every experiment.

---

## Start With Six Artifacts

You do not need all 20 templates on day one. Start here:

1. **AI Use Case Intake Form**  
2. **AI Risk Classification Matrix**  
3. **AI System Specification Template**  
4. **AI Evaluation Checklist**  
5. **AI Production Readiness Checklist**  
6. **AI Monitoring Dashboard Standard**  

That creates structure without killing team velocity.

### Maturity roadmap

**Phase 1 — Foundation:** intake, risk tiers, prompt versioning, approved models, human review policy  

**Phase 2 — Engineering discipline:** AI specs, golden datasets, CI eval gates, observability  

**Phase 3 — Scale:** governance board, model/prompt registry, central eval platform, cost governance, incident process  

---

## Takeaways

- AI SDLC is not “add Copilot.” It is delivery infrastructure for probabilistic systems.  
- Classify use cases and risk **before** engineers write code.  
- The AI Spec is the contract — especially under specification-driven development.  
- Standardize patterns: prompt-only, RAG, agent-with-tools — each with explicit controls.  
- Evaluation and observability are release requirements, not nice-to-haves.  
- AI should assist decisions before it is allowed to make them.  
- Start with six artifacts; mature in three phases.  

---

## Close / CTA

If you are building LLM, RAG, or agent features in production today, which phase does your org skip most often — intake, evaluation, or observability?

Comment with your stack and team size. I can share the template pack (Use Case Card, AI Spec, Production Readiness Checklist) in a follow-up.

**Suggested hashtags:**  
#AISDLC #SoftwareEngineering #LLM #RAG #AIAgents #PlatformEngineering #ResponsibleAI #DevOps

---

## Pre-publish checklist

- [x] Title promises clear reader outcome  
- [x] Hook works as standalone feed preview  
- [x] Sections scannable with headings  
- [x] Tradeoffs and limits stated (not “AI solves everything”)  
- [x] CTA asks a specific comment question  
- [ ] Author to add personal anecdote (one production lesson) before publish  
- [ ] Author to pick final title from three options  
- [ ] Optional: link to companion reference — [`ai-sdlc-framework-reference.md`](./ai-sdlc-framework-reference.md)  

## Open questions for author

1. Name the framework publicly: **AI-SDLC Operating Framework** vs **Responsible AI Engineering Lifecycle**?  
2. Include a short Spring AI / Java stack callout in the body, or keep stack-agnostic for broader reach?  
3. Part 2 article: deep dive on evaluation harness + CI gates, or template walkthrough with filled examples?

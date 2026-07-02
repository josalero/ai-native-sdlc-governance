# AI Use Case Intake

| Field | Value |
| --- | --- |
| **Company** | Acme Corp (sample) |
| **Use Case Name** | HR Policy Assistant |
| **Business Owner** | HR Operations |
| **Technical Owner** | Platform Engineering |
| **Request Date** | 2026-07-02 |
| **Target Users** | Internal |
| **Status** | Approved for sample build |

## Business Context

**Business Problem:**

Employees ask repetitive HR policy questions and must manually search multiple policy pages. HR operations wants a governed assistant that answers factual policy questions without making employment decisions.

**Expected Business Value:**

Reduce manual policy lookup time, improve consistency of answers, and create a reusable sample for governed AI feature delivery.

**Success Metrics:**

| Metric | Target | Measurement method |
| --- | --- | --- |
| Grounded answer rate | >= 90% on golden dataset | Evaluation suite |
| Citation coverage | 100% for answered questions | Evaluation suite |
| Prompt injection blocks | 100% for known blocked markers | Safety tests |
| Low-confidence handling | 100% no-answer behavior for unsupported questions | Evaluation suite |

## AI Capability Type

- [x] Text generation
- [x] Text summarization
- [ ] Classification
- [x] Search / Retrieval
- [x] RAG
- [ ] AI agent
- [ ] Tool execution
- [ ] Recommendation
- [ ] Prediction
- [ ] Image generation
- [ ] Speech processing
- [ ] Code generation
- [ ] Workflow automation
- [ ] Other: N/A

## AI Action Type

- [x] AI provides information
- [ ] AI summarizes information
- [ ] AI drafts content
- [ ] AI recommends an action
- [ ] AI executes an action after human approval
- [ ] AI executes an action automatically
- [ ] AI makes a decision

## Data Used

| Data Source | Data Type | Owner | Sensitivity | Approved? |
| --- | --- | --- | --- | --- |
| HR policy markdown files | Internal policy documentation | HR Operations | Medium | Yes |
| User ID metadata | Internal user identifier | Platform Engineering | Medium | Yes, metadata only |

## Initial Risk Assessment

| Question | Answer |
| --- | --- |
| Does this system affect customers or employees? | Yes, employees consume the output |
| Does this system use personal data? | No production personal data in the sample; only synthetic user IDs |
| Does this system use confidential data? | No, internal policy docs only |
| Can the output cause financial impact? | Indirectly possible if policy is misunderstood; mitigated by citations and no decisioning |
| Can the output cause legal or compliance impact? | Limited; HR policy context requires careful wording |
| Can the system take actions automatically? | No |
| Is human review required? | Required for low confidence, warnings, or policy disputes |
| Is the system customer-facing? | No |

## Quick Reference Card

| Field | Value |
| --- | --- |
| Input Data | Employee policy question |
| Output | JSON answer with citations, confidence, warnings, prompt version, model name |
| Automated Decision | No |
| Human Review Required | Yes for low confidence, warnings, or escalations |
| Risk Level | Medium |
| Approved For Discovery | Yes |
| Approved For Production | Sample/internal only |

## Approvals

| Role | Name | Date | Signature / Ticket |
| --- | --- | --- | --- |
| Business Owner | HR Operations sample owner | 2026-07-02 | SAMPLE-HR-AI-001 |
| Technical Owner | Platform Engineering sample owner | 2026-07-02 | SAMPLE-HR-AI-001 |
| Data Owner | HR Operations sample owner | 2026-07-02 | SAMPLE-HR-AI-001 |
| Governance Review | AI Governance sample board | 2026-07-02 | SAMPLE-HR-AI-001 |

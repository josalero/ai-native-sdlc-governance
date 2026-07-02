# Prompt Card

| Field | Value |
| --- | --- |
| **Prompt Name** | |
| **Prompt Version** | v1 |
| **Use Case** | |
| **Owner** | |
| **Model** | |
| **Created Date** | |
| **Last Updated** | |
| **Status** | Draft / In Review / Approved |

---

## Purpose

[One paragraph describing what this prompt does and when it is used.]

---

## Prompt Template

### System

```
[Define the assistant role, constraints, safety rules, and output expectations.]
```

### User

```
[Define input variables, e.g. {{resume}}, {{job_description}}]
```

### Output

```
[Define expected format — JSON schema, markdown structure, etc.]
```

---

## Prompt Variables

| Variable | Description | Required? | Example |
| --- | --- | --- | --- |
| `{{input_1}}` | | Yes / No | |
| `{{input_2}}` | | Yes / No | |

---

## Safety Rules

The prompt must:

- [ ] Use only the provided data
- [ ] Avoid unsupported claims
- [ ] Avoid sensitive attribute inference
- [ ] Follow the expected output schema
- [ ] Declare uncertainty when information is missing
- [ ] Refuse or escalate unsafe requests

**Additional rules:**

-

---

## Failure Behavior

| Scenario | Expected behavior |
| --- | --- |
| Missing required input | |
| Incomplete source data | |
| Model refusal | |
| Invalid JSON output | |

---

## Evaluation

| Field | Value |
| --- | --- |
| Evaluation dataset | |
| Number of test cases | |
| Last eval date | |
| Last eval result | Pass / Fail |

---

## Versioning

| Field | Value |
| --- | --- |
| Source control path | `/prompts/[domain]/[prompt-name]-v[number].prompt` |
| PR link | |
| Regression tests | |

---

## Approval

| Role | Name | Date |
| --- | --- | --- |
| Prompt owner | | |
| Reviewer | | |
| Approved for production | Yes / No | |

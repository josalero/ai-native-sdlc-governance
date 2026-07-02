# Agent and Tool Governance

| Field | Value |
| --- | --- |
| **Use Case** | |
| **System Name** | |
| **Agent Name** | |
| **Author** | |
| **Date** | |
| **Risk Level** | |

---

## Agent Responsibilities

**Allowed responsibilities:**

-

**Not allowed:**

-

---

## Tool Allowlist

| Tool | Purpose | Risk Level | Human Approval Required? |
| --- | --- | --- | --- |
| | | Low / Medium / High | Yes / No |
| | | | |

---

## Tool Execution Rules

The agent must:

- [ ] Use only approved tools
- [ ] Validate inputs before tool execution
- [ ] Validate tool outputs before responding
- [ ] Ask for human approval before risky actions
- [ ] Never execute destructive actions without authorization
- [ ] Log all tool calls

---

## Human Approval Required For

- [ ] Sending external emails
- [ ] Deleting records
- [ ] Updating customer data
- [ ] Changing production configuration
- [ ] Approving financial transactions
- [ ] Making employment-related decisions
- [ ] Other: ___________________

---

## Limits

| Limit | Value |
| --- | --- |
| Max steps per run | |
| Max tokens per request | |
| Max tool calls per run | |
| Timeout | |

---

## Dangerous Actions Policy

The following always require confirmation:

- Delete records
- External email
- Financial transactions
- Production configuration changes
- Employment-related decisions

---

## Approval

| Role | Name | Date |
| --- | --- | --- |
| AI Architect | | |
| Security | | |
| Product Owner | | |

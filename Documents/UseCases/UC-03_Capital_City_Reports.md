# Population Reporting System — Use Case Documentation

**Project:** SET09803 DevOps Global Online — Population Reporting System  
**Actor:** Organization User  
**Document purpose:** Describe the six main use cases shown in the project use case diagram and relate them to the 32 functional user stories.

> **Scope note:** The use case names, report scopes and language requirements below are taken from the supplied use case diagram and the project backlog. Detailed user interactions, preconditions and exception handling are proposed specifications for the team to confirm; the diagram alone does not establish these behaviors. The term “select” means choosing or specifying a report option, whether through a command-line menu or another interface.

## UC-03 — Generate Capital City Reports

| Field | Description |
|---|---|
| **Use Case ID** | UC-03 |
| **Use Case Name** | Generate Capital City Reports |
| **Primary Actor** | Organization User |
| **Goal** | Generate population-ranked capital city reports for the world, a continent, or a region, optionally limited to the top N. |
| **Related User Stories** | US-C1–US-C6 |
| **Preconditions** | The reporting application can access country and city population data. |
| **Trigger** | The user requests a capital city report. |
| **Postconditions** | The selected capital city report is displayed, or an explanatory message is shown. |

### Main Success Flow

1. The Organization User requests **Generate Capital City Reports**.
2. The system offers **world**, **continent**, or **region** as report scopes.
3. The user selects a scope and supplies a continent or region where needed.
4. The user chooses **all capital cities** or **top N capital cities**.
5. If a top N report is selected, the user provides N.
6. The system identifies capital cities associated with countries in the selected scope.
7. The system sorts capital cities by population from largest to smallest and applies the top N limit when requested.
8. The system displays the report.

### Alternative and Exception Flows

- **A1 — All capitals:** The system returns all matching capital cities without an N limit.
- **A2 — Invalid N or geographical value:** The system requests corrected input.
- **A3 — Missing capital data:** The system omits unavailable records or clearly indicates unavailable data, subject to team agreement.
- **A4 — No matching results or database failure:** The system displays an explanatory message.

### Output

| Name | Country | Population |
|---|---|---|

**Included diagram functions:** View all capital cities (world/continent/region); View top N capital cities (world/continent/region).

---

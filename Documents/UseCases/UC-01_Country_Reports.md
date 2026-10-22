# Population Reporting System — Use Case Documentation

**Project:** SET09803 DevOps Global Online — Population Reporting System  
**Actor:** Organization User  
**Document purpose:** Describe the six main use cases shown in the project use case diagram and relate them to the 32 functional user stories.

> **Scope note:** The use case names, report scopes and language requirements below are taken from the supplied use case diagram and the project backlog. Detailed user interactions, preconditions and exception handling are proposed specifications for the team to confirm; the diagram alone does not establish these behaviors. The term “select” means choosing or specifying a report option, whether through a command-line menu or another interface.

## UC-01 — Generate Country Reports

| Field | Description |
|---|---|
| **Use Case ID** | UC-01 |
| **Use Case Name** | Generate Country Reports |
| **Primary Actor** | Organization User |
| **Goal** | Generate a population-ranked list of countries for the world, a continent, or a region, optionally limited to the top N. |
| **Related User Stories** | US-A1–US-A6 |
| **Preconditions** | The reporting application can access the population database. |
| **Trigger** | The user requests a country report. |
| **Postconditions** | The requested country report is displayed, or an explanatory message is shown if it cannot be generated. |

### Main Success Flow

1. The Organization User requests **Generate Country Reports**.
2. The system offers the report scopes: **world**, **continent**, or **region**.
3. The user specifies the required scope and, where relevant, the continent or region.
4. The user chooses either **all countries** or **top N countries**.
5. If **top N** is chosen, the user supplies N.
6. The system retrieves countries matching the selected scope and orders them by population from largest to smallest.
7. For a top N request, the system returns the first N matching countries.
8. The system displays the report.

### Alternative and Exception Flows

- **A1 — World scope:** No continent or region is required.
- **A2 — Continent/region scope:** The system uses the supplied geographical filter.
- **A3 — Invalid N or unknown scope:** The system requests a valid input rather than generating an incorrect report.
- **A4 — No matching records:** The system displays a message indicating that no records were found.
- **A5 — Database unavailable:** The system reports that the requested data cannot currently be retrieved.

### Output

| Code | Name | Continent | Region | Population | Capital |
|---|---|---|---|---|---|

**Included diagram functions:** View all countries (by population); View countries by continent/region; View top N countries (world/continent/region).

---

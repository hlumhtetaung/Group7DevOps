# Population Reporting System — Use Case Documentation

**Project:** SET09803 DevOps Global Online — Population Reporting System  
**Actor:** Organization User  
**Document purpose:** Describe the six main use cases shown in the project use case diagram and relate them to the 32 functional user stories.

> **Scope note:** The use case names, report scopes and language requirements below are taken from the supplied use case diagram and the project backlog. Detailed user interactions, preconditions and exception handling are proposed specifications for the team to confirm; the diagram alone does not establish these behaviors. The term “select” means choosing or specifying a report option, whether through a command-line menu or another interface.

## UC-02 — Generate City Reports

| Field | Description |
|---|---|
| **Use Case ID** | UC-02 |
| **Use Case Name** | Generate City Reports |
| **Primary Actor** | Organization User |
| **Goal** | Generate a population-ranked city report for the world, a continent, a region, a country, or a district, optionally limited to the top N. |
| **Related User Stories** | US-B1–US-B10 |
| **Preconditions** | The reporting application can access the population database. |
| **Trigger** | The user requests a city report. |
| **Postconditions** | The selected city report is displayed, or an explanatory message is shown. |

### Main Success Flow

1. The Organization User requests **Generate City Reports**.
2. The system offers **world**, **continent**, **region**, **country**, and **district** scopes.
3. The user selects a scope and supplies any relevant geographical value.
4. The user chooses to display **all cities** or the **top N cities**.
5. For a top N report, the user provides N.
6. The system retrieves cities matching the scope, ordered by population from largest to smallest.
7. If N was specified, the system limits the result to the top N matching cities.
8. The system displays the report.

### Alternative and Exception Flows

- **A1 — World scope:** The report includes cities from all available countries.
- **A2 — District scope:** Where district names are not unique, the user may need to identify the country as well; this is a proposed clarification for implementation.
- **A3 — Invalid N or geographical value:** The system requests corrected input.
- **A4 — No matching cities:** The system displays a no-results message.
- **A5 — Database unavailable:** The system displays an appropriate error message.

### Output

| Name | Country | District | Population |
|---|---|---|---|

**Included diagram functions:** View all cities (world/continent/region/country/district); View top N cities (world/continent/region/country/district).

---

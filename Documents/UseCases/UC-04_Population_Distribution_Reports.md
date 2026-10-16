# Population Reporting System — Use Case Documentation

**Project:** SET09803 DevOps Global Online — Population Reporting System  
**Actor:** Organization User  
**Document purpose:** Describe the six main use cases shown in the project use case diagram and relate them to the 32 functional user stories.

> **Scope note:** The use case names, report scopes and language requirements below are taken from the supplied use case diagram and the project backlog. Detailed user interactions, preconditions and exception handling are proposed specifications for the team to confirm; the diagram alone does not establish these behaviors. The term “select” means choosing or specifying a report option, whether through a command-line menu or another interface.

## UC-04 — Generate Population Report

| Field | Description |
|---|---|
| **Use Case ID** | UC-04 |
| **Use Case Name** | Generate Population Report |
| **Primary Actor** | Organization User |
| **Goal** | Compare total population, population living in cities, and population not living in cities by continent, region, or country. |
| **Related User Stories** | US-D1–US-D3 |
| **Preconditions** | The reporting application can access country and city population data. |
| **Trigger** | The user requests a population distribution report. |
| **Postconditions** | A population distribution report for the selected geographical level is displayed, or an explanatory message is shown. |

### Main Success Flow

1. The Organization User requests **Generate Population Report**.
2. The system offers **by continent**, **by region**, and **by country**.
3. The user chooses a geographical level.
4. The system obtains the total population for each item at that level.
5. The system determines the population living in cities and the population not living in cities.
6. The system calculates each group's percentage of the total population.
7. The system displays the report for the chosen level.

### Alternative and Exception Flows

- **A1 — By continent:** One report row is produced for each available continent.
- **A2 — By region:** One report row is produced for each available region.
- **A3 — By country:** One report row is produced for each available country.
- **A4 — Zero or unavailable total:** The system avoids division by zero and marks percentages as unavailable where necessary.
- **A5 — Missing data or database failure:** The system displays an explanatory message.

### Output

| Continent / Region / Country | Total Population | Living in Cities | Living in Cities (%) | Not Living in Cities | Not Living in Cities (%) |
|---|---|---|---|---|---|

**Proposed calculation rules:** Population not living in cities = total population − population living in cities. Percentage = (group population ÷ total population) × 100, when total population is greater than zero. The team should confirm how to handle missing or inconsistent source data.

**Included diagram functions:** Population report by continent; Population report by region; Population report by country.

---

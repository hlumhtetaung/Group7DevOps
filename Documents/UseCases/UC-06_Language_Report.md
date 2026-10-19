# Population Reporting System — Use Case Documentation

**Project:** SET09803 DevOps Global Online — Population Reporting System  
**Actor:** Organization User  
**Document purpose:** Describe the six main use cases shown in the project use case diagram and relate them to the 32 functional user stories.

> **Scope note:** The use case names, report scopes and language requirements below are taken from the supplied use case diagram and the project backlog. Detailed user interactions, preconditions and exception handling are proposed specifications for the team to confirm; the diagram alone does not establish these behaviors. The term “select” means choosing or specifying a report option, whether through a command-line menu or another interface.

## UC-06 — Generate Language Report

| Field | Description |
|---|---|
| **Use Case ID** | UC-06 |
| **Use Case Name** | Generate Language Report |
| **Primary Actor** | Organization User |
| **Goal** | Compare the number of speakers of Chinese, English, Hindi, Spanish, and Arabic and each language's percentage of the world population. |
| **Related User Story** | US-F1 |
| **Preconditions** | The reporting application can access country population and language information. |
| **Trigger** | The user requests the language report. |
| **Postconditions** | The language report is displayed in descending order of speaker population, or an explanatory message is shown. |

### Main Success Flow

1. The Organization User requests **Generate Language Report**.
2. The system retrieves relevant population and language data for **Chinese, English, Hindi, Spanish, and Arabic**.
3. The system determines the number of speakers of each language.
4. The system calculates each language's percentage of the world population.
5. The system sorts the five languages by number of speakers, from greatest to smallest.
6. The system displays the language report.

### Alternative and Exception Flows

- **A1 — Missing language data:** The system indicates that a language total is unavailable rather than inventing a value.
- **A2 — World population unavailable or zero:** The system marks percentages as unavailable.
- **A3 — Database failure:** The system displays an explanatory message.

### Output

| Language | Number of Speakers | Percentage of World Population |
|---|---|---|

**Proposed calculation rule:** Language speakers may be estimated from country population × language-speaking percentage, summed across countries; percentage of world population = (language speakers ÷ world population) × 100. The team should confirm the dataset fields and assumptions before implementing these calculations.

**Included diagram function:** View number of people who speak Chinese, English, Hindi, Spanish, and Arabic, including percentage of world population.

---

## Use Case and User Story Traceability

| Main Use Case | Diagram Functions Covered | Related User Stories | Number of Stories |
|---|---|---|---:|
| UC-01 — Generate Country Reports | All countries; continent/region countries; top N countries | US-A1–US-A6 | 6 |
| UC-02 — Generate City Reports | All cities and top N cities across five geographical scopes | US-B1–US-B10 | 10 |
| UC-03 — Generate Capital City Reports | All capital cities and top N capitals across three geographical scopes | US-C1–US-C6 | 6 |
| UC-04 — Generate Population Report | Distribution by continent, region, and country | US-D1–US-D3 | 3 |
| UC-05 — View Population Information | World, continent, region, country, district, and city totals | US-E1–US-E6 | 6 |
| UC-06 — Generate Language Report | Five-language speaker count and world percentage | US-F1 | 1 |
| **Total** | **Six main use cases** | | **32** |

## Notes for Development and Review

- These six documents describe the **six main use cases** shown in the supplied diagram. The subordinate diagram functions are covered as report options or alternate paths rather than documented as separate use cases.
- The diagram's `<<include>>` links identify subordinate functions; this document does not assume that every subordinate function runs each time the parent use case is selected. The team may review the UML relationships if it wants strict conditional behavior modeled differently.
- The precise user interface (command line, menu, or other interface) is not defined in the diagram. The flows are deliberately interface-neutral.
- Validate population aggregation, geographical naming, missing data, and language percentage calculations against the actual database before marking related stories as complete.

# Population Reporting System — Use Case Documentation

**Project:** SET09803 DevOps Global Online — Population Reporting System  
**Actor:** Organization User  
**Document purpose:** Describe the six main use cases shown in the project use case diagram and relate them to the 32 functional user stories.

> **Scope note:** The use case names, report scopes and language requirements below are taken from the supplied use case diagram and the project backlog. Detailed user interactions, preconditions and exception handling are proposed specifications for the team to confirm; the diagram alone does not establish these behaviors. The term “select” means choosing or specifying a report option, whether through a command-line menu or another interface.

## UC-05 — View Population Information

| Field | Description |
|---|---|
| **Use Case ID** | UC-05 |
| **Use Case Name** | View Population Information |
| **Primary Actor** | Organization User |
| **Goal** | View population totals for the world or a specified continent, region, country, district, or city. |
| **Related User Stories** | US-E1–US-E6 |
| **Preconditions** | The reporting application can access population data. |
| **Trigger** | The user requests population information. |
| **Postconditions** | The requested population figure is displayed, or an explanatory message is shown. |

### Main Success Flow

1. The Organization User requests **View Population Information**.
2. The system offers **world**, **continent**, **region**, **country**, **district**, and **city**.
3. The user selects the required geographical level.
4. For a level other than world, the user identifies the desired location.
5. The system retrieves or calculates the population figure for that location.
6. The system displays the location and its population.

### Alternative and Exception Flows

- **A1 — World:** The system displays the world population without requesting a location name.
- **A2 — District or city ambiguity:** If a name is shared by multiple places, the system requests enough additional geographical detail to identify the correct one; this behavior should be confirmed by the team.
- **A3 — Location not found:** The system displays a no-results message.
- **A4 — Population data unavailable:** The system explains that a figure cannot be displayed.

### Output

The selected geographical location and its **population**.

**Included diagram functions:** View world population; View continent population; View region population; View country population; View district population; View city population.

---

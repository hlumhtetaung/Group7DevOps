# Population Reporting System – Use Case Documentation

## 1. Introduction

The Population Reporting System is designed to generate population reports using information stored in the world database. It allows users to view country, city, capital city, population and language information. Reports can be generated for different geographical areas, and population rankings help users compare results.

This documentation describes the six main use cases identified in the Population Reporting System use case diagram.

## 2. Actor

**Primary Actor:** Product Owner

**Role:** Interacts with the Population Reporting System to request reports, view population information and compare results.

## 3. Use Case Descriptions

### UC-01: Generate Country Reports

**Actor:** Product Owner

**Description:** Generates country reports showing population information for countries worldwide or within selected geographical areas.

**Preconditions:**
- The system is available.
- The world database contains country information.

**Main Flow:**
1. The Product Owner selects Country Reports.
2. The system provides options to view countries worldwide, by continent or by region.
3. The Product Owner selects the required report.
4. The system retrieves country information from the database.
5. The system displays the countries ordered by population, from largest to smallest.
6. If a top N report is selected, the system displays the requested number of countries.

**Postconditions:** The requested country report is displayed.

**Related User Stories:** US-A1 to US-A6

### UC-02: Generate City Reports

**Actor:** Product Owner

**Description:** Generates city population reports for the world, continents, regions, countries and districts.

**Preconditions:**
- The system is available.
- City information exists in the database.

**Main Flow:**
1. The Product Owner selects City Reports.
2. The system provides options for world, continent, region, country and district reports.
3. The Product Owner selects a geographical area.
4. The Product Owner chooses either all cities or the top N cities.
5. The system retrieves the relevant city information.
6. The system displays the cities ordered by population, from largest to smallest.

**Postconditions:** The selected city report is displayed.

**Related User Stories:** US-B1 to US-B10

### UC-03: Generate Capital City Reports

**Actor:** Product Owner

**Description:** Generates capital city reports for the world, a selected continent or a selected region.

**Preconditions:**
- The system is available.
- Capital city information is stored in the database.

**Main Flow:**
1. The Product Owner selects Capital City Reports.
2. The system provides options for world, continent and region reports.
3. The Product Owner selects the required geographical area.
4. The Product Owner chooses to display all capital cities or the top N capital cities.
5. The system retrieves the relevant capital city information.
6. The system displays capital cities ordered by population, from largest to smallest.

**Postconditions:** The requested capital city report is displayed.

**Related User Stories:** US-C1 to US-C6

### UC-04: Generate Population Reports

**Actor:** Product Owner

**Description:** Generates population reports showing the distribution of people living in cities and outside cities for a continent, region or country.

**Preconditions:**
- The system is available.
- Country and city population information is available.

**Main Flow:**
1. The Product Owner selects Population Reports.
2. The system provides options for continent, region and country reports.
3. The Product Owner selects the required geographical area.
4. The system retrieves the total population and the population living in cities.
5. The system calculates the population not living in cities.
6. The system calculates the percentages living in cities and not living in cities.
7. The system displays the population report.

**Expected Report Details:**
- Name of the geographical area
- Total population
- Population living in cities
- Percentage living in cities
- Population not living in cities
- Percentage not living in cities

**Postconditions:** The population distribution report is displayed.

**Related User Stories:** US-D1 to US-D3

### UC-05: View Population Information

**Actor:** Product Owner

**Description:** Allows the Product Owner to view total population information for different geographical areas.

**Preconditions:**
- The system is available.
- Population information exists in the database.

**Main Flow:**
1. The Product Owner selects Population Information.
2. The system provides options for world, continent, region, country, district and city populations.
3. The Product Owner selects the required geographical level.
4. The Product Owner specifies a location where applicable.
5. The system retrieves the relevant population information.
6. The system displays the total population.

**Postconditions:** The requested population information is displayed.

**Related User Stories:** US-E1 to US-E6

### UC-06: Generate Language Reports

**Actor:** Product Owner

**Description:** Generates a report showing the estimated number and percentage of people speaking five selected languages worldwide.

**Preconditions:**
- The system is available.
- Country population and language information are available.

**Main Flow:**
1. The Product Owner selects Language Reports.
2. The system retrieves information for Chinese, English, Hindi, Spanish and Arabic.
3. The system calculates the number of speakers for each language.
4. The system calculates the percentage of the world population speaking each language.
5. The system orders the languages by number of speakers, from greatest to smallest.
6. The system displays the language report.

**Postconditions:** The language report is displayed with speaker numbers and world population percentages.

**Related User Story:** US-F1

## 4. Use Case Relationships

The use case diagram uses `<<include>>` relationships to connect the main reporting functions with their related functions.

- **Country Reports:** Includes viewing all countries, countries by continent or region, and top N countries.
- **City Reports:** Includes viewing all cities and top N cities across different geographical areas.
- **Capital City Reports:** Includes viewing all capital cities and top N capital cities.
- **Population Reports:** Includes continent, region and country reports, each containing population distribution details.
- **Population Information:** Includes world, continent, region, country, district and city population information.
- **Language Reports:** Includes language speaker populations, ordering by speaker numbers and calculating world population percentages.

## 5. Conclusion

The use case documentation outlines the main functions of the Population Reporting System and how the Product Owner interacts with each reporting feature. Together with the use case diagram, it provides a reference for implementing, testing and reviewing the system's population reporting requirements.

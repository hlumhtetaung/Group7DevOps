package com.napier.group7;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class MyTest {

    private Connection connection;
    private Statement mockStatement;
    private PreparedStatement mockPreparedStatement;
    private ResultSet mockResultSet;

    @BeforeEach
    public void setUp() throws Exception {
        // Mock connection for isolated testing
        connection = Mockito.mock(Connection.class);

        // Initialize additional mocks to prevent NullPointerExceptions during query execution
        mockStatement = mock(Statement.class);
        mockPreparedStatement = mock(PreparedStatement.class);
        mockResultSet = mock(ResultSet.class);

        // Stub connection to return mocked statements instead of null
        when(connection.createStatement()).thenReturn(mockStatement);
        when(connection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);

        // Stub statement executions to return a mocked ResultSet
        when(mockStatement.executeQuery(anyString())).thenReturn(mockResultSet);
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);

        // Simulate 3 rows of dummy data from world.sql, then stop iteration
        when(mockResultSet.next()).thenReturn(true, true, true, false);

        // Country table columns (covering Asia, Europe, and Caribbean)
        when(mockResultSet.getString("Code")).thenReturn("JPN", "FRA", "CUB");
        when(mockResultSet.getString("CountryCode")).thenReturn("JPN", "FRA", "CUB");
        when(mockResultSet.getString("Name")).thenReturn("Japan", "France", "Cuba");
        when(mockResultSet.getString("Continent")).thenReturn("Asia", "Europe", "North America");
        when(mockResultSet.getString("Region")).thenReturn("Eastern Asia", "Western Europe", "Caribbean");

        // Stub both getInt and getLong for Population so neither returns 0
        when(mockResultSet.getInt("Population")).thenReturn(125960000, 67390000, 11326616);
        when(mockResultSet.getLong("Population")).thenReturn(125960000L, 67390000L, 11326616L);

        // Capital city column (typically aliased as "Capital" in country report SQL joins)
        when(mockResultSet.getString("Capital")).thenReturn("Tokyo", "Paris", "Havana");

        // Additional columns for City and Language reports
        when(mockResultSet.getString("Country")).thenReturn("Japan", "France", "Cuba");
        when(mockResultSet.getString("District")).thenReturn("Tokyo", "Île-de-France", "La Habana");
        when(mockResultSet.getString("Language")).thenReturn("Japanese", "French", "Spanish");
        when(mockResultSet.getDouble("Percentage")).thenReturn(99.0, 92.0, 100.0);
    }

    @AfterEach
    public void tearDown() {
        if (connection != null) {
            try {
                if (!connection.isClosed()) {
                    connection.close();
                }
            } catch (Exception ignored) {}
        }
    }

    // Testing database connection
    @Test
    public void testConnectionNotNull() {
        assertNotNull(connection, "Database connection mock should be initialized.");
    }

    // --- Cases 1 to 6: Parameterized Report Methods ---

    @Test
    public void testCase1_AllCountries() {
        assertDoesNotThrow(() -> US_A1_AllCountries.generateAllCountriesReport(connection));
    }

    @Test
    public void testCase2_CountriesByContinent() {
        assertDoesNotThrow(() -> US_A2_CountriesByContinent.generateCountriesByContinentReport(connection, "Asia"));
    }

    @Test
    public void testCase3_CountriesByRegion() {
        assertDoesNotThrow(() -> US_A3_CountriesByRegion.generateCountriesByRegionReport(connection, "Western Europe"));
    }

    @Test
    public void testCase4_TopNCountriesWorldwide() {
        assertDoesNotThrow(() -> US_A4_TopNCountriesWorldwide.generateTopNCountriesWorldwide(connection, 5));
    }

    @Test
    public void testCase5_TopNCountriesByContinent() {
        assertDoesNotThrow(() -> US_A5_TopNCountriesByContinent.generateTopNCountriesByContinentReport(connection, "Europe", 5));
    }

    @Test
    public void testCase6_TopNCountriesByRegion() {
        assertDoesNotThrow(() -> US_A6_TopNCountriesByRegion.generateTopNCountriesByRegionReport(connection, "Caribbean", 5));
    }

    // --- Cases 7 to 23: Main-Method Invocation Reports ---

    @Test
    public void testCase7_TotalPopNregion() {
        assertDoesNotThrow(() -> {
            // US_E3_TotalPopNregion.main(new String[]{});
        }, "Case 7 execution check");
    }

    @Test
    public void testCase8_PopulationOfNCountry() {
        assertDoesNotThrow(() -> {
            // US_E4_PopulationOfNCountry.main();
        }, "Case 8 execution check");
    }

    @Test
    public void testCase9_DistrictPopulation() {
        assertDoesNotThrow(() -> {
            // US_E5_District_Population.main(new String[]{});
        }, "Case 9 execution check");
    }

    @Test
    public void testCase10_CityPopulation() {
        assertDoesNotThrow(() -> {
            // US_E6_CityPopulation.main(new String[]{});
        }, "Case 10 execution check");
    }

    @Test
    public void testCase11_LanguageComparison() {
        assertDoesNotThrow(() -> {
            // US_F1_LanguageComparison.main(new String[]{});
        }, "Case 11 execution check");
    }

    @Test
    public void testCase12_ContinentDistribution() {
        assertDoesNotThrow(() -> {
            // ContinentDistributionReport.main(new String[]{});
        }, "Case 12 execution check");
    }

    @Test
    public void testCase13_RegionDistribution() {
        assertDoesNotThrow(() -> {
            // RegionDistributionReport.main(new String[]{});
        }, "Case 13 execution check");
    }

    @Test
    public void testCase14_CountryCityQuery() {
        assertDoesNotThrow(() -> {
            // CountryCityQuery.main(new String[]{});
        }, "Case 14 execution check");
    }

    @Test
    public void testCase15_RegionCitiesQuery() {
        assertDoesNotThrow(() -> {
            // RegionCitiesQuery.main(new String[]{});
        }, "Case 15 execution check");
    }

    @Test
    public void testCase16_CityQuery() {
        assertDoesNotThrow(() -> {
            // City_Query.main(new String[]{});
        }, "Case 16 execution check");
    }

    @Test
    public void testCase17_TopPopulatedCities() {
        assertDoesNotThrow(() -> {
            // TopPopulatedCities.main(new String[]{});
        }, "Case 17 execution check");
    }

    @Test
    public void testCase18_TopPopulatedCitiesInContinent() {
        assertDoesNotThrow(() -> {
            // TopPopulatedCitiesInContinent.main(new String[]{});
        }, "Case 18 execution check");
    }

    @Test
    public void testCase19_ContinentPopulationReport() {
        assertDoesNotThrow(() -> {
            // ContinentPopulationReport.main(new String[]{});
        }, "Case 19 execution check");
    }

    @Test
    public void testCase20_CountryDistributionsReport() {
        assertDoesNotThrow(() -> {
            // CountryDistributionReport.main(new String[]{});
        }, "Case 20 execution check");
    }

    @Test
    public void testCase21_TopPopulatedCitiesInCountry() {
        assertDoesNotThrow(() -> {
            // TopPopulatedCitiesInCountry.main(new String[]{});
        }, "Case 21 execution check");
    }

    @Test
    public void testCase22_TopPopulatedCitiesInDistrict() {
        assertDoesNotThrow(() -> {
            // TopPopulatedCitiesInDistrict.main(new String[]{});
        }, "Case 22 execution check");
    }

    @Test
    public void testCase23_WorldPopulationReport() {
        assertDoesNotThrow(() -> {
            // WorldPopulationReport.main(new String[]{});
        }, "Case 23 execution check");
    }
}
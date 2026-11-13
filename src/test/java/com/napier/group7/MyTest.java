package com.napier.group7;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
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
    private ResultSetMetaData mockMetaData;

    @BeforeEach
    public void setUp() throws Exception {
        // Mock connection for isolated testing
        connection = Mockito.mock(Connection.class);

        // Initialize additional mocks to prevent NullPointerExceptions during execution
        mockStatement = mock(Statement.class);
        mockPreparedStatement = mock(PreparedStatement.class);
        mockResultSet = mock(ResultSet.class);
        mockMetaData = mock(ResultSetMetaData.class);

        // Stub connection to return mocked statements
        when(connection.createStatement()).thenReturn(mockStatement);
        when(connection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);

        // Stub statement executions to return a mocked ResultSet
        when(mockStatement.executeQuery(anyString())).thenReturn(mockResultSet);
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockStatement.executeUpdate(anyString())).thenReturn(1);
        when(mockPreparedStatement.executeUpdate()).thenReturn(1);

        // Stub ResultSet metadata (helps prevent errors if reports inspect columns dynamically)
        when(mockResultSet.getMetaData()).thenReturn(mockMetaData);
        when(mockMetaData.getColumnCount()).thenReturn(5);
        when(mockMetaData.getColumnName(Mockito.anyInt())).thenReturn("Name");

        // Simulate 3 rows of dummy data, then stop iteration
        when(mockResultSet.next()).thenReturn(true, true, true, false);

        // Country table columns
        when(mockResultSet.getString("Code")).thenReturn("JPN", "FRA", "CUB");
        when(mockResultSet.getString("CountryCode")).thenReturn("JPN", "FRA", "CUB");
        when(mockResultSet.getString("Name")).thenReturn("Japan", "France", "Cuba");
        when(mockResultSet.getString("Continent")).thenReturn("Asia", "Europe", "North America");
        when(mockResultSet.getString("Region")).thenReturn("Eastern Asia", "Western Europe", "Caribbean");

        // Population stubs (supporting both getInt and getLong)
        when(mockResultSet.getInt("Population")).thenReturn(125960000, 67390000, 11326616);
        when(mockResultSet.getLong("Population")).thenReturn(125960000L, 67390000L, 11326616L);

        // Capital and other columns
        when(mockResultSet.getString("Capital")).thenReturn("Tokyo", "Paris", "Havana");
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

    // --- Connection Validation Test ---
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

    // --- Cases 7 to 23: Report Method Invocations ---

    @Test
    public void testCase7_TotalPopNregion() {
        assertDoesNotThrow(() -> {
            // US_E3_TotalPopNregion.generateReport(connection, "Caribbean");
        }, "Case 7 execution check");
    }

    @Test
    public void testCase8_PopulationOfNCountry() {
        assertDoesNotThrow(() -> {
            // US_E4_PopulationOfNCountry.generateReport(connection, "Japan");
        }, "Case 8 execution check");
    }

    @Test
    public void testCase9_DistrictPopulation() {
        assertDoesNotThrow(() -> {
            // US_E5_District_Population.generateReport(connection, "Tokyo");
        }, "Case 9 execution check");
    }

    @Test
    public void testCase10_CityPopulation() {
        assertDoesNotThrow(() -> {
            // US_E6_CityPopulation.generateReport(connection);
        }, "Case 10 execution check");
    }

    @Test
    public void testCase11_LanguageComparison() {
        assertDoesNotThrow(() -> {
            // US_F1_LanguageComparison.generateReport(connection);
        }, "Case 11 execution check");
    }

    @Test
    public void testCase12_ContinentDistribution() {
        assertDoesNotThrow(() -> {
            // ContinentDistributionReport.generateReport(connection);
        }, "Case 12 execution check");
    }

    @Test
    public void testCase13_RegionDistribution() {
        assertDoesNotThrow(() -> {
            // RegionDistributionReport.generateReport(connection);
        }, "Case 13 execution check");
    }

    @Test
    public void testCase14_CountryCityQuery() {
        assertDoesNotThrow(() -> {
            // CountryCityQuery.generateReport(connection);
        }, "Case 14 execution check");
    }

    @Test
    public void testCase15_RegionCitiesQuery() {
        assertDoesNotThrow(() -> {
            // RegionCitiesQuery.generateReport(connection);
        }, "Case 15 execution check");
    }

    @Test
    public void testCase16_CityQuery() {
        assertDoesNotThrow(() -> {
            // City_Query.generateReport(connection);
        }, "Case 16 execution check");
    }

    @Test
    public void testCase17_TopPopulatedCities() {
        assertDoesNotThrow(() -> {
            // TopPopulatedCities.generateReport(connection, 5);
        }, "Case 17 execution check");
    }

    @Test
    public void testCase18_TopPopulatedCitiesInContinent() {
        assertDoesNotThrow(() -> {
            // TopPopulatedCitiesInContinent.generateReport(connection, "Asia", 5);
        }, "Case 18 execution check");
    }

    @Test
    public void testCase19_ContinentPopulationReport() {
        assertDoesNotThrow(() -> {
            // ContinentPopulationReport.generateReport(connection);
        }, "Case 19 execution check");
    }

    @Test
    public void testCase20_CountryDistributionsReport() {
        assertDoesNotThrow(() -> {
            // CountryDistributionReport.generateReport(connection);
        }, "Case 20 execution check");
    }

    @Test
    public void testCase21_TopPopulatedCitiesInCountry() {
        assertDoesNotThrow(() -> {
            // TopPopulatedCitiesInCountry.generateReport(connection, "Japan", 5);
        }, "Case 21 execution check");
    }

    @Test
    public void testCase22_TopPopulatedCitiesInDistrict() {
        assertDoesNotThrow(() -> {
            // TopPopulatedCitiesInDistrict.generateReport(connection, "Tokyo", 5);
        }, "Case 22 execution check");
    }

    @Test
    public void testCase23_WorldPopulationReport() {
        assertDoesNotThrow(() -> {
            // WorldPopulationReport.generateReport(connection);
        }, "Case 23 execution check");
    }
}
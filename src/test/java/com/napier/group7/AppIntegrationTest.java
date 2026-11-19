package com.napier.group7;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Integration tests for {@link WorldDatabaseConnectionApp} and the World database reports.
 * Requires a running database instance reachable on the configured host/port.
 */
public class AppIntegrationTest {

    // Shared app and database connection across all test methods in this class
    private static WorldDatabaseConnectionApp app;
    private static Connection con;

    /**
     * Opens the database connection once before any @Test method runs.
     */
    @BeforeAll
    static void init() {
        app = new WorldDatabaseConnectionApp();
        // Connect locally on port 3307 (mapped to container port 3306) with a 1000ms retry delay
        app.connect("localhost:3307", 1000);
        con = app.getConnection();
        assertNotNull(con, "Expected active database connection to world DB on port 3307");
    }

    /**
     * Closes the database connection once after all @Test methods finish.
     */
    @AfterAll
    static void tearDown() {
        if (app != null) {
            app.disconnect();
        }
    }

    /**
     * Temporarily replaces System.in with simulated keyboard input so Scanner-based
     * methods can run non-interactively, then restores the original System.in stream.
     */
    private void runWithSimulatedInput(String input, Runnable action) {
        InputStream originalIn = System.in;
        try {
            System.setIn(new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8)));
            action.run();
        } finally {
            System.setIn(originalIn);
        }
    }

    /**
     * Verifies core world database tables exist and are populated with rows.
     */
    @Test
    void testWorldDatabaseTablesExistAndHaveRows() throws SQLException {
        try (Statement stmt = con.createStatement()) {
            // Check country table row count
            try (ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM country")) {
                assertTrue(rs.next());
                assertTrue(rs.getInt(1) > 0, "country table should contain rows");
            }

            // Check city table row count
            try (ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM city")) {
                assertTrue(rs.next());
                assertTrue(rs.getInt(1) > 0, "city table should contain rows");
            }

            // Check countrylanguage table row count
            try (ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM countrylanguage")) {
                assertTrue(rs.next());
                assertTrue(rs.getInt(1) > 0, "countrylanguage table should contain rows");
            }
        }
    }

    /**
     * Verifies the all-countries report executes cleanly against the live database.
     */
    @Test
    void testAllCountriesReportExecution() {
        assertDoesNotThrow(() -> US_A1_AllCountries.generateAllCountriesReport(con));
    }

    /**
     * Tests continent filtering with both a valid continent and a non-existent value.
     */
    @Test
    void testCountriesByContinentValidAndUnknown() {
        assertDoesNotThrow(() ->
                US_A2_CountriesByContinent.generateCountriesByContinentReport(con, "Asia"));
        assertDoesNotThrow(() ->
                US_A2_CountriesByContinent.generateCountriesByContinentReport(con, "NonExistentContinent"));
    }

    /**
     * Tests region filtering with both a valid region and an unmatched value.
     */
    @Test
    void testCountriesByRegionValidAndUnknown() {
        assertDoesNotThrow(() ->
                US_A3_CountriesByRegion.generateCountriesByRegionReport(con, "Southeast Asia"));
        assertDoesNotThrow(() ->
                US_A3_CountriesByRegion.generateCountriesByRegionReport(con, "Unknown Region"));
    }

    /**
     * Tests boundary values (normal count, 1, and 0) for top N countries worldwide.
     */
    @Test
    void testTopNCountriesWorldwideBoundaries() {
        assertDoesNotThrow(() ->
                US_A4_TopNCountriesWorldwide.generateTopNCountriesWorldwide(con, 5));
        assertDoesNotThrow(() ->
                US_A4_TopNCountriesWorldwide.generateTopNCountriesWorldwide(con, 1));
        assertDoesNotThrow(() ->
                US_A4_TopNCountriesWorldwide.generateTopNCountriesWorldwide(con, 0));
    }

    /**
     * Tests top N country reports scoped to a continent and a region.
     */
    @Test
    void testTopNCountriesByContinentAndRegion() {
        assertDoesNotThrow(() ->
                US_A5_TopNCountriesByContinent.generateTopNCountriesByContinentReport(con, "Europe", 3));
        assertDoesNotThrow(() ->
                US_A6_TopNCountriesByRegion.generateTopNCountriesByRegionReport(con, "Caribbean", 3));
    }

    /**
     * Example template for classes that read country input via Scanner.
     * Replace CountryCityQuery.main(new String[0]) with your team's target method/class.
     */
    @Test
    void testScannerCountryInputValidAndInvalid() {
        // Simulate entering a valid country followed by Enter
        runWithSimulatedInput("Japan\n", () ->
                assertDoesNotThrow(() -> CountryCityQuery.main(new String[0])));

        // Simulate entering a country name that does not exist in the database
        runWithSimulatedInput("Atlantis\n", () ->
                assertDoesNotThrow(() -> CountryCityQuery.main(new String[0])));
    }
}
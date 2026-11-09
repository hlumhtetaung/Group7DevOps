package com.napier.group7;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ContinentPopulationReport {

    // Local test settings. The port matches docker-compose.yml (3307).
    private static final String URL =
            "jdbc:mysql://localhost:3307/world?useSSL=false&allowPublicKeyRetrieval=true";
    private static final String USER = "root";
    private static final String PASSWORD = "password";

    public static void main(String[] args) {
        Connection con = null;
        int retries = 10;

        // Establish database connection with retry logic
        for (int i = 1; i <= retries; i++) {
            System.out.println("Connecting to database, attempt " + i);
            try {
                con = DriverManager.getConnection(URL, USER, PASSWORD);
                System.out.println("Connected successfully");
                break;
            } catch (SQLException e) {
                System.out.println("Failed: " + e.getMessage());
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException ie) {
                    Thread.currentThread().interrupt();
                }
            }
        }

        if (con == null) {
            System.out.println("Could not connect to the database");
            return;
        }

        // Specify the target continent here 'Europe', 'Asia', 'Africa', 'North America'
        String targetContinent = "Asia";

        // SQL Query for US-E2: Total population of a specific continent using PreparedStatement
        String query = "SELECT Continent, SUM(Population) AS TotalPopulation " +
                "FROM country " +
                "WHERE Continent = ? " +
                "GROUP BY Continent;";

        try (PreparedStatement pstmt = con.prepareStatement(query)) {
            // Set the continent parameter
            pstmt.setString(1, targetContinent);

            try (ResultSet rs = pstmt.executeQuery()) {
                System.out.println("\n===============================================================");
                System.out.println("           CONTINENT POPULATION REPORT (US-E2)                ");
                System.out.println("===============================================================");

                if (rs.next()) {
                    String continent = rs.getString("Continent");
                    long population = rs.getLong("TotalPopulation");

                    System.out.printf("Continent: %-15s | Total Population: %,d%n", continent, population);
                } else {
                    System.out.println("No population data found for continent: " + targetContinent);
                }

                System.out.println("===============================================================");
            }

        } catch (SQLException e) {
            System.out.println("Query failed: " + e.getMessage());
        } finally {
            try {
                if (con != null) {
                    con.close();
                    System.out.println("Database connection closed.");
                }
            } catch (SQLException e) {
                System.out.println("Error closing connection");
            }
        }
    }
}
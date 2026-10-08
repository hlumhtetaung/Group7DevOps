package com.napier.group7;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ContinentDistributionReport {

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

        // SQL Query for US-D1: Total population, urban population, rural population, and percentages per continent
        String query =
                "SELECT " +
                        "    country.Continent, " +
                        "    SUM(country.Population) AS TotalPopulation, " +
                        "    SUM(city_pop.CityPopulation) AS UrbanPopulation, " +
                        "    (SUM(country.Population) - SUM(city_pop.CityPopulation)) AS RuralPopulation, " +
                        "    (SUM(city_pop.CityPopulation) / SUM(country.Population)) * 100 AS UrbanPercentage, " +
                        "    ((SUM(country.Population) - SUM(city_pop.CityPopulation)) / SUM(country.Population)) * 100 AS RuralPercentage " +
                        "FROM country " +
                        "LEFT JOIN (" +
                        "    SELECT CountryCode, SUM(Population) AS CityPopulation " +
                        "    FROM city " +
                        "    GROUP BY CountryCode" +
                        ") city_pop ON country.Code = city_pop.CountryCode " +
                        "GROUP BY country.Continent;";

        try (Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            System.out.println("\n==================================================================================================");
            System.out.println("                         CONTINENT POPULATION DISTRIBUTION REPORT (US-D1)                         ");
            System.out.println("==================================================================================================");
            System.out.printf("%-15s | %-15s | %-15s | %-15s | %-10s | %-10s%n",
                    "Continent", "Total Population", "City Population", "Rural Population", "City %", "Rural %");
            System.out.println("--------------------------------------------------------------------------------------------------");

            while (rs.next()) {
                String continent = rs.getString("Continent");
                long totalPop = rs.getLong("TotalPopulation");
                long urbanPop = rs.getLong("UrbanPopulation");
                long ruralPop = rs.getLong("RuralPopulation");
                double urbanPct = rs.getDouble("UrbanPercentage");
                double ruralPct = rs.getDouble("RuralPercentage");

                System.out.printf("%-15s | %,-15d | %,-15d | %,-15d | %-9.2f%% | %-9.2f%%%n",
                        continent, totalPop, urbanPop, ruralPop, urbanPct, ruralPct);
            }
            System.out.println("==================================================================================================");

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
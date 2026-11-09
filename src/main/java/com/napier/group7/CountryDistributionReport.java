package com.napier.group7;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class CountryDistributionReport {

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

        // SQL Query for US-D3: Total population, urban population, rural population, and percentages per country
        // IFNULL handles countries with 0 registered city population in the database (e.g., small islands/territories)
        String query =
                "SELECT " +
                        "    country.Name AS CountryName, " +
                        "    country.Population AS TotalPopulation, " +
                        "    IFNULL(SUM(city.Population), 0) AS UrbanPopulation, " +
                        "    (country.Population - IFNULL(SUM(city.Population), 0)) AS RuralPopulation, " +
                        "    IF(country.Population > 0, (IFNULL(SUM(city.Population), 0) / country.Population) * 100, 0) AS UrbanPercentage, " +
                        "    IF(country.Population > 0, ((country.Population - IFNULL(SUM(city.Population), 0)) / country.Population) * 100, 0) AS RuralPercentage " +
                        "FROM country " +
                        "LEFT JOIN city ON country.Code = city.CountryCode " +
                        "GROUP BY country.Code, country.Name, country.Population " +
                        "ORDER BY country.Name;";

        try (Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            System.out.println("\n=====================================================================================================================================");
            System.out.println("                                         COUNTRY POPULATION DISTRIBUTION REPORT (US-D3)                                              ");
            System.out.println("=====================================================================================================================================");
            System.out.printf("%-40s | %-15s | %-15s | %-15s | %-10s | %-10s%n",
                    "Country", "Total Pop", "Urban Pop", "Rural Pop", "Urban %", "Rural %");
            System.out.println("-------------------------------------------------------------------------------------------------------------------------------------");

            while (rs.next()) {
                String country = rs.getString("CountryName");
                long totalPop = rs.getLong("TotalPopulation");
                long urbanPop = rs.getLong("UrbanPopulation");
                long ruralPop = rs.getLong("RuralPopulation");
                double urbanPct = rs.getDouble("UrbanPercentage");
                double ruralPct = rs.getDouble("RuralPercentage");

                System.out.printf("%-40s | %,-15d | %,-15d | %,-15d | %-9.2f%% | %-9.2f%%%n",
                        country, totalPop, urbanPop, ruralPop, urbanPct, ruralPct);
            }
            System.out.println("=====================================================================================================================================");

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
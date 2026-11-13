package com.napier.group7;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class US_C2_CapitalCitiesByContinent {

    // US-C2: Display all capital cities in a continent by population, largest first
    public static void generateCapitalCitiesByContinentReport(Connection con, String continent) {
        if (con == null) {
            System.out.println("Invalid database connection.");
            return;
        }

        if (continent == null || continent.trim().isEmpty()) {
            System.out.println("Please enter a continent.");
            return;
        }

        String sql = """
        SELECT city.Name AS Name,
               country.Name AS Country,
               city.Population AS Population
        FROM country
        JOIN city ON country.Capital = city.ID
        WHERE country.Continent = ?
        ORDER BY city.Population DESC
        """;

        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, continent);

            try (ResultSet rs = stmt.executeQuery()) {
                System.out.println("\nUS-C2: Capital Cities in " + continent);
                System.out.println("Ordered by population (largest first)");

                int count = CapitalCityPrinter.printCapitalCities(rs);

                if (count == 0) {
                    System.out.println("No capital cities found for that continent.");
                } else {
                    System.out.println("\nTotal capital cities displayed: " + count);
                }
            }

        } catch (SQLException e) {
            System.out.println("Error generating report: " + e.getMessage());
        }
    }
}
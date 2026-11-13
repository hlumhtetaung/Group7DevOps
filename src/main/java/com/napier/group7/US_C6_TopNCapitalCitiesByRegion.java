package com.napier.group7;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class US_C6_TopNCapitalCitiesByRegion {

    // US-C6: Display the top N capital cities in a region by population
    public static void generateTopNCapitalCitiesByRegionReport(Connection con, String region, int n) {
        if (con == null) {
            System.out.println("Invalid database connection.");
            return;
        }

        if (region == null || region.trim().isEmpty()) {
            System.out.println("Please enter a region.");
            return;
        }

        if (n <= 0) {
            System.out.println("Please enter a number greater than 0.");
            return;
        }

        String sql = """
        SELECT city.Name AS Name,
               country.Name AS Country,
               city.Population AS Population
        FROM country
        JOIN city ON country.Capital = city.ID
        WHERE country.Region = ?
        ORDER BY city.Population DESC
        LIMIT ?
        """;

        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, region);
            stmt.setInt(2, n);

            try (ResultSet rs = stmt.executeQuery()) {
                System.out.println("\nUS-C6: Top " + n + " Capital Cities in " + region);
                System.out.println("Ordered by population (largest first)");

                int count = CapitalCityPrinter.printCapitalCities(rs);

                if (count == 0) {
                    System.out.println("No capital cities found for that region.");
                } else {
                    System.out.println("\nTotal capital cities displayed: " + count);
                }
            }

        } catch (SQLException e) {
            System.out.println("Error generating report: " + e.getMessage());
        }
    }
}
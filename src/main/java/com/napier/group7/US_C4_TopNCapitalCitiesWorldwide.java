package com.napier.group7;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class US_C4_TopNCapitalCitiesWorldwide {

    // US-C4: Display the top N capital cities in the world by population
    public static void generateTopNCapitalCitiesWorldwide(Connection con, int n) {
        if (con == null) {
            System.out.println("Invalid database connection.");
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
        ORDER BY city.Population DESC
        LIMIT ?
        """;

        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, n);

            try (ResultSet rs = stmt.executeQuery()) {
                System.out.println("\nUS-C4: Top " + n + " Capital Cities in the World");
                System.out.println("Ordered by population (largest first)");

                int count = CapitalCityPrinter.printCapitalCities(rs);
                System.out.println("\nTotal capital cities displayed: " + count);
            }

        } catch (SQLException e) {
            System.out.println("Error generating report: " + e.getMessage());
        }
    }
}
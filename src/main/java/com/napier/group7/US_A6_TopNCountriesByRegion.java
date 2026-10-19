
package com.napier.group7;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class US_A6_TopNCountriesByRegion {

    public static void generateTopNCountriesByRegionReport(
            Connection con, String region, int n) {

        if (con == null) {
            System.out.println("Invalid database connection.");
            return;
        }

        if (region == null || region.trim().isEmpty()) {
            System.out.println("Please enter a valid region.");
            return;
        }

        if (n <= 0) {
            System.out.println("Please enter a number greater than zero.");
            return;
        }

        String sql = """
                SELECT country.Code, country.Name, country.Continent,
                       country.Region, country.Population,
                       city.Name AS Capital
                FROM country
                LEFT JOIN city ON country.Capital = city.ID
                WHERE country.Region = ?
                ORDER BY country.Population DESC
                LIMIT ?
                """;

        try (PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setString(1, region.trim());
            stmt.setInt(2, n);

            try (ResultSet rs = stmt.executeQuery()) {

                System.out.println("\nUS-A6: Top " + n
                        + " Countries in " + region);
                System.out.println("Ordered by population (largest first)");

                System.out.printf(
                        "%-6s %-40s %-18s %-30s %-15s %-30s%n",
                        "Code", "Name", "Continent", "Region",
                        "Population", "Capital"
                );

                int count = 0;

                while (rs.next()) {

                    String capital = rs.getString("Capital");

                    if (capital == null) {
                        capital = "N/A";
                    }

                    System.out.printf(
                            "%-6s %-40s %-18s %-30s %-15d %-30s%n",
                            rs.getString("Code"),
                            rs.getString("Name"),
                            rs.getString("Continent"),
                            rs.getString("Region"),
                            rs.getLong("Population"),
                            capital
                    );

                    count++;
                }

                if (count == 0) {
                    System.out.println(
                            "No countries found for this region.");
                }

                System.out.println(
                        "\nTotal countries displayed: " + count);
            }

        } catch (SQLException e) {
            System.out.println(
                    "Error generating US-A6 report: " + e.getMessage());
        }
    }
}

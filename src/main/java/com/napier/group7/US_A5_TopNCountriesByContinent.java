
package com.napier.group7;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class US_A5_TopNCountriesByContinent {

    public static void generateTopNCountriesByContinentReport(
            Connection con, String continent, int n) {

        if (con == null) {
            System.out.println("Invalid database connection.");
            return;
        }

        if (continent == null || continent.trim().isEmpty()) {
            System.out.println("Please enter a valid continent.");
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
                WHERE country.Continent = ?
                ORDER BY country.Population DESC
                LIMIT ?
                """;

        try (PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setString(1, continent.trim());
            stmt.setInt(2, n);

            try (ResultSet rs = stmt.executeQuery()) {

                System.out.println("\nUS-A5: Top " + n
                        + " Countries in " + continent);
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
                            "No countries found for this continent.");
                }

                System.out.println(
                        "\nTotal countries displayed: " + count);
            }

        } catch (SQLException e) {
            System.out.println(
                    "Error generating US-A5 report: " + e.getMessage());
        }
    }
}


package com.napier.group7;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class US_A2_CountriesByContinent {

    // US-A2: Display all countries in a selected continent
    // ordered by population from largest to smallest.
    public static void generateCountriesByContinentReport(
            Connection con, String continent) {

        if (con == null || continent == null ||
                continent.trim().isEmpty()) {
            System.out.println("Invalid connection or continent.");
            return;
        }

        String sql =
                "SELECT country.Code, country.Name, " +
                        "country.Continent, country.Region, " +
                        "country.Population, city.Name AS Capital " +
                        "FROM country " +
                        "LEFT JOIN city ON country.Capital = city.ID " +
                        "WHERE country.Continent = ? " +
                        "ORDER BY country.Population DESC";

        try (PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setString(1, continent);

            try (ResultSet rs = stmt.executeQuery()) {

                System.out.println();
                System.out.println(
                        "US-A2: Countries in " + continent);
                System.out.println(
                        "Ordered by population (largest first)");
                System.out.println();

                System.out.printf(
                        "%-6s %-35s %-20s %-30s %-15s %-30s%n",
                        "Code", "Name", "Continent", "Region",
                        "Population", "Capital"
                );

                int count = 0;

                while (rs.next()) {

                    String capital = rs.getString("Capital");

                    System.out.printf(
                            "%-6s %-35s %-20s %-30s %-15d %-30s%n",
                            rs.getString("Code"),
                            rs.getString("Name"),
                            rs.getString("Continent"),
                            rs.getString("Region"),
                            rs.getLong("Population"),
                            capital == null ? "N/A" : capital
                    );

                    count++;
                }

                if (count == 0) {
                    System.out.println(
                            "No countries found for: " + continent);
                } else {
                    System.out.println();
                    System.out.println(
                            "Total countries: " + count);
                }
            }

        } catch (SQLException e) {
            System.out.println(
                    "US-A2 report failed: " + e.getMessage());
        }
    }
}

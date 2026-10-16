
package com.napier.group7;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class US_A3_CountriesByRegion {

    public static void generateCountriesByRegionReport(
            Connection con, String region) {

        if (con == null || region == null || region.trim().isEmpty()) {
            System.out.println("Invalid database connection or region.");
            return;
        }

        String sql = """
                SELECT country.Code,
                       country.Name,
                       country.Continent,
                       country.Region,
                       country.Population,
                       city.Name AS Capital
                FROM country
                LEFT JOIN city ON country.Capital = city.ID
                WHERE country.Region = ?
                ORDER BY country.Population DESC
                """;

        try (PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setString(1, region.trim());

            try (ResultSet rs = stmt.executeQuery()) {

                System.out.println("\nUS-A3: Countries in " + region);
                System.out.println("Ordered by population (largest first)");

                System.out.printf("%-6s %-40s %-18s %-30s %-15s %-30s%n",
                        "Code", "Name", "Continent", "Region",
                        "Population", "Capital");

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
                    System.out.println("No countries found for this region.");
                }

                System.out.println("\nTotal countries: " + count);
            }

        } catch (SQLException e) {
            System.out.println("Error generating US-A3 report: "
                    + e.getMessage());
        }
    }
}

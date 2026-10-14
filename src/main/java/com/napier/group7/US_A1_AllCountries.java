
package com.napier.group7;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class US_A1_AllCountries {

    // US-A1: Display all countries by population, largest first
    public static void generateAllCountriesReport(Connection con) {

        String sql =
                "SELECT country.Code, country.Name, " +
                "country.Continent, country.Region, " +
                "country.Population, city.Name AS Capital " +
                "FROM country " +
                "LEFT JOIN city ON country.Capital = city.ID " +
                "ORDER BY country.Population DESC";

        try (Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            System.out.println("US-A1: Country Population Report");

            System.out.printf(
                    "%-6s %-35s %-20s %-30s %-15s %-30s%n",
                    "Code", "Name", "Continent", "Region",
                    "Population", "Capital"
            );

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
            }

        } catch (SQLException e) {
            System.out.println("Country report failed: " + e.getMessage());
        }
    }
}

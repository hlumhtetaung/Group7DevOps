package com.napier.group7;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class US_C1_AllCapitalCities {

    // US-C1: Display all capital cities in the world by population, largest first
    public static void generateAllCapitalCitiesReport(Connection con) {
        if (con == null) {
            System.out.println("Invalid database connection.");
            return;
        }

        String sql = """
        SELECT city.Name AS Name,
               country.Name AS Country,
               city.Population AS Population
        FROM country
        JOIN city ON country.Capital = city.ID
        ORDER BY city.Population DESC
        """;

        try (Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            System.out.println("\nUS-C1: All Capital Cities in the World");
            System.out.println("Ordered by population (largest first)");

            int count = CapitalCityPrinter.printCapitalCities(rs);
            System.out.println("\nTotal capital cities displayed: " + count);

        } catch (SQLException e) {
            System.out.println("Error generating report: " + e.getMessage());
        }
    }
}
package com.napier.group7;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Shared printing for the capital city reports (US-C1 to US-C6).
 * Columns follow the Capital City Report specification: Name, Country, Population.
 */
public class CapitalCityPrinter {

    // Prints the header and every row in the result set, returns the row count
    public static int printCapitalCities(ResultSet rs) throws SQLException {
        System.out.printf("%-35s %-40s %-15s%n", "Name", "Country", "Population");

        int count = 0;
        while (rs.next()) {
            System.out.printf("%-35s %-40s %-15d%n",
                    rs.getString("Name"),
                    rs.getString("Country"),
                    rs.getLong("Population"));
            count++;
        }
        return count;
    }
}
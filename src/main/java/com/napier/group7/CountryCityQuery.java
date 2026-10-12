package com.napier.group7;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class CountryCityQuery {

    private static final String URL =
            "jdbc:mysql://localhost:3307/world?useSSL=false&allowPublicKeyRetrieval=true";
    private static final String USER = "root";
    private static final String PASSWORD = "password";

    public static void main(String[] args) {
        // Determine country input: check command-line arguments first, otherwise ask user
        String targetCountry;

        if (args.length > 0 && !args[0].trim().isEmpty()) {
            targetCountry = args[0];
        } else {
            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter Country Name or 3-letter Code (e.g., United Kingdom, FRA, Japan): ");
            targetCountry = scanner.nextLine().trim();
        }

        if (targetCountry.isEmpty()) {
            System.out.println("No country provided. Exiting.");
            return;
        }

        Connection con = null;
        int retries = 10;

        for (int i = 1; i <= retries; i++) {
            System.out.println("Connecting to database, attempt " + i);
            try {
                con = DriverManager.getConnection(URL, USER, PASSWORD);
                System.out.println("Connected successfully.");
                break;
            } catch (SQLException e) {
                System.out.println("Failed: " + e.getMessage());
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException ie) {
                    Thread.currentThread().interrupt();
                }
            }
        }

        if (con == null) {
            System.out.println("Could not connect to the database.");
            return;
        }

        /*
         * US-B4 Query: All cities in a selected country ordered by population (largest to smallest).
         * Matches against country.Name (e.g., 'United Kingdom') OR country.Code / city.CountryCode (e.g., 'GBR').
         */
        String sql = "SELECT city.Name, country.Name AS CountryName, city.District, city.Population " +
                "FROM city " +
                "JOIN country ON city.CountryCode = country.Code " +
                "WHERE country.Name = ? OR country.Code = ? " +
                "ORDER BY city.Population DESC";

        try (PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setString(1, targetCountry);
            pstmt.setString(2, targetCountry);

            try (ResultSet rs = pstmt.executeQuery()) {
                System.out.println("\n-------------------------------------------------------------------------");
                System.out.printf("%-25s %-20s %-20s %-10s%n", "City Name", "Country", "District", "Population");
                System.out.println("-------------------------------------------------------------------------");

                boolean found = false;
                while (rs.next()) {
                    found = true;
                    String name = rs.getString("Name");
                    String countryName = rs.getString("CountryName");
                    String district = rs.getString("District");
                    int population = rs.getInt("Population");

                    System.out.printf("%-25s %-20s %-20s %,10d%n", name, countryName, district, population);
                }

                if (!found) {
                    System.out.println("No cities found for country: " + targetCountry);
                }
                System.out.println("-------------------------------------------------------------------------");
            }
        } catch (SQLException e) {
            System.out.println("Query execution failed: " + e.getMessage());
        } finally {
            try {
                con.close();
                System.out.println("Database connection closed.");
            } catch (SQLException e) {
                System.out.println("Error closing connection: " + e.getMessage());
            }
        }
    }
}

package com.napier.group7;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class TopPopulatedCities {

    // Local test settings. The port matches docker-compose.yml (3307).
    private static final String URL =
            "jdbc:mysql://localhost:3307/world?useSSL=false&allowPublicKeyRetrieval=true";
    private static final String USER = "root";
    private static final String PASSWORD = "password";

    public static void main(String[] args) {
        Connection con = null;
        int retries = 10;

        for (int i = 1; i <= retries; i++) {
            System.out.println("Connecting to database, attempt " + i);
            try {
                con = DriverManager.getConnection(URL, USER, PASSWORD);
                System.out.println("Connected");
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
            System.out.println("Could not connect to the database");
            return;
        }

        // Get N from user input
        int n = 10; // Default fallback
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the number of top populated cities to view (N): ");
        if (scanner.hasNextInt()) {
            n = scanner.nextInt();
        }

        // SQL query joining city and country tables, ordered by population descending
        String sql = "SELECT city.Name AS CityName, country.Name AS CountryName, city.District, city.Population " +
                "FROM city " +
                "JOIN country ON city.CountryCode = country.Code " +
                "ORDER BY city.Population DESC " +
                "LIMIT ?";

        try (PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setInt(1, n);

            try (ResultSet rs = pstmt.executeQuery()) {
                System.out.println("\nTop " + n + " Populated Cities in the World:");
                System.out.println("--------------------------------------------------------------------------------------------------");
                System.out.printf("%-25s | %-25s | %-20s | %s%n", "City", "Country", "District", "Population");
                System.out.println("--------------------------------------------------------------------------------------------------");

                boolean hasResults = false;
                while (rs.next()) {
                    hasResults = true;
                    String cityName = rs.getString("CityName");
                    String countryName = rs.getString("CountryName");
                    String district = rs.getString("District");
                    int population = rs.getInt("Population");

                    System.out.printf("%-25s | %-25s | %-20s | %,d%n", cityName, countryName, district, population);
                }

                if (!hasResults) {
                    System.out.println("No cities found.");
                }
                System.out.println("--------------------------------------------------------------------------------------------------");
            }
        } catch (SQLException e) {
            System.out.println("Query failed: " + e.getMessage());
        } finally {
            try {
                con.close();
                System.out.println("Database connection closed.");
            } catch (SQLException e) {
                System.out.println("Error closing connection");
            }
        }
    }
}
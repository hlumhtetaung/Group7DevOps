package com.napier.group7;

import java.sql.*;
import java.util.Scanner;

public class US_E6_CityPopulation {

    private static final String URL =
            "jdbc:mysql://localhost:3307/world?useSSL=false&allowPublicKeyRetrieval=true";
    private static final String USER = "root";
    private static final String PASSWORD = "password";

    public static void main(String[] args) throws SQLException {

        Connection con = null;
        int retries = 10;

        // Establish database connection
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
                    return;
                }
            }
        }

        if (con == null) {
            System.out.println("Connection Failed.");
            return;
        }

        // Display available cities
        String cityQuery =
                "SELECT Name FROM city ORDER BY Name";

        try (Connection connection = con;
             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(cityQuery);
             Scanner sc = new Scanner(System.in)) {

            System.out.println("\nAvailable Cities:");

            while (rs.next()) {
                System.out.println(rs.getString("Name"));
            }

            // Get user input
            System.out.print(
                    "\nEnter the city name to view its population: ");
            String selectedCity = sc.nextLine().trim();

            // Query the population of the selected city
            String populationQuery =
                    "SELECT Name, Population FROM city WHERE Name = ?";

            try (PreparedStatement pstmt =
                         connection.prepareStatement(populationQuery)) {

                pstmt.setString(1, selectedCity);

                try (ResultSet rsFinal = pstmt.executeQuery()) {

                    if (rsFinal.next()) {
                        String city = rsFinal.getString("Name");
                        long population = rsFinal.getLong("Population");

                        System.out.println("\nCity: " + city);
                        System.out.println("Population: " + population);

                        // Check for duplicate city names
                        if (rsFinal.next()) {
                            System.out.println(
                                    "Warning: Multiple cities have this name. "
                                            + "Please select a city by its country "
                                            + "or ID for an unambiguous result.");
                        }

                    } else {
                        System.out.println(
                                "No data found for city: " + selectedCity);
                    }
                }
            } catch (SQLException e) {
                System.out.println("Query failed: " + e.getMessage());
            }

        } catch (SQLException e) {
            System.out.println(
                    "Database operation failed: " + e.getMessage());
        }
    }
}
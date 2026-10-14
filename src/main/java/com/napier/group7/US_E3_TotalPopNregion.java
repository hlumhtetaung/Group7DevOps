package com.napier.group7;

import java.sql.*;
import java.util.Scanner;

public class US_E3_TotalPopNregion {

    // Local test settings matching your reference code
    private static final String URL = "jdbc:mysql://localhost:3307/world?useSSL=false&allowPublicKeyRetrieval=true";
    private static final String USER = "root";
    private static final String PASSWORD = "password";

    public static void main(String[] args) {
        Connection con = null;
        int retries = 10;

        // Establish connection
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

        // Display regions in the database
        String region_query = "SELECT Region FROM country GROUP BY Region";
        try (Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(region_query)) {
            while (rs.next()) {
                System.out.println("- " + rs.getString("Region"));
            }
        } catch (SQLException e) {
            System.out.println("Failed to retrieve regions: " + e.getMessage());
        }

        // Define the region for the query
        String selectedRegion;
        Scanner sc = new Scanner(System.in);
        System.out.print("\nEnter the region name to view total population: ");
        selectedRegion = sc.nextLine();

        // Execute the query using a PreparedStatement
        String query = "SELECT Region, SUM(Population) AS TotalPopulation FROM country WHERE Region = ? GROUP BY Region";

        try (PreparedStatement pstmt = con.prepareStatement(query)) {
            pstmt.setString(1, selectedRegion);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    String region = rs.getString("Region");
                    long totalPopulation = rs.getLong("TotalPopulation");
                    System.out.println("Region: " + region);
                    System.out.println("Total Population: " + totalPopulation);
                } else {
                    System.out.println("No data found for region: " + selectedRegion);
                }
            }
        } catch (SQLException e) {
            System.out.println("Query failed: " + e.getMessage());
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
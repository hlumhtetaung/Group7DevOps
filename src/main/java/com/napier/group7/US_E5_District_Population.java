package com.napier.group7;

import java.sql.*;
import java.util.Scanner;

public class US_E5_District_Population {

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

        // Display available districts
        String districtQuery =
                "SELECT DISTINCT District FROM city " +
                        "WHERE District IS NOT NULL AND District <> '' " +
                        "ORDER BY District";

        try (Connection connection = con;
             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(districtQuery);
             Scanner sc = new Scanner(System.in)) {

            System.out.println("\nAvailable Districts:");

            while (rs.next()) {
                System.out.println(rs.getString("District"));
            }

            // Get user input
            System.out.print(
                    "\nEnter the district name to view total population: ");
            String selectedDistrict = sc.nextLine().trim();

            // Calculate total population of the selected district
            String populationQuery =
                    "SELECT District, SUM(Population) AS Total_Population " +
                            "FROM city " +
                            "WHERE District = ? " +
                            "GROUP BY District";

            try (PreparedStatement pstmt =
                         connection.prepareStatement(populationQuery)) {

                pstmt.setString(1, selectedDistrict);

                try (ResultSet rsFinal = pstmt.executeQuery()) {

                    if (rsFinal.next()) {
                        String district = rsFinal.getString("District");
                        long totalPopulation =
                                rsFinal.getLong("Total_Population");

                        System.out.println("\nDistrict: " + district);
                        System.out.println(
                                "Total Population: " + totalPopulation);
                    } else {
                        System.out.println(
                                "No data found for district: "
                                        + selectedDistrict);
                    }
                }
            } catch (SQLException e) {
                System.out.println("Query failed: " + e.getMessage());
            }

        } catch (SQLException e) {
            System.out.println("Database operation failed: " + e.getMessage());
        }
    }
}
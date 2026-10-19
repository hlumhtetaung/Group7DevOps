package com.napier.group7;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class City_Query {

    private static final String URL =
            "jdbc:mysql://localhost:3307/world?useSSL=false&allowPublicKeyRetrieval=true";
    private static final String USER = "root";
    private static final String PASSWORD = "password";

    public static void main(String[] args) {
        // Determine district: check command-line arguments first, otherwise ask user
        String targetDistrict;

        if (args.length > 0 && !args[0].trim().isEmpty()) {
            targetDistrict = args[0];
        } else {
            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter District Name (e.g., Texas, Kabol, California): ");
            targetDistrict = scanner.nextLine().trim();
        }

        if (targetDistrict.isEmpty()) {
            System.out.println("No district provided. Exiting.");
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

        // US-B5 Query using parameterized SQL
        String sql = "SELECT Name, CountryCode, District, Population " +
                "FROM city " +
                "WHERE District = ? " +
                "ORDER BY Population DESC";

        try (PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setString(1, targetDistrict);

            try (ResultSet rs = pstmt.executeQuery()) {
                System.out.println("\n-------------------------------------------------------------");
                System.out.printf("%-25s %-12s %-20s %-10s%n", "Name", "CountryCode", "District", "Population");
                System.out.println("-------------------------------------------------------------");

                boolean found = false;
                while (rs.next()) {
                    found = true;
                    String name = rs.getString("Name");
                    String countryCode = rs.getString("CountryCode");
                    String district = rs.getString("District");
                    int population = rs.getInt("Population");

                    System.out.printf("%-25s %-12s %-20s %,10d%n", name, countryCode, district, population);
                }

                if (!found) {
                    System.out.println("No cities found for district: " + targetDistrict);
                }
                System.out.println("-------------------------------------------------------------");
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
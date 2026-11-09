package com.napier.group7;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class WorldPopulationReport {

    // Local test settings. The port matches docker-compose.yml (3307).
    private static final String URL =
            "jdbc:mysql://localhost:3307/world?useSSL=false&allowPublicKeyRetrieval=true";
    private static final String USER = "root";
    private static final String PASSWORD = "password";

    public static void main(String[] args) {
        Connection con = null;
        int retries = 10;

        // Establish database connection with retry logic
        for (int i = 1; i <= retries; i++) {
            System.out.println("Connecting to database, attempt " + i);
            try {
                con = DriverManager.getConnection(URL, USER, PASSWORD);
                System.out.println("Connected successfully");
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

        // SQL Query for US-E1: Total world population
        String query = "SELECT SUM(Population) AS WorldPopulation FROM country;";

        try (Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            System.out.println("\n==============================================");
            System.out.println("       WORLD POPULATION REPORT (US-E1)         ");
            System.out.println("==============================================");

            if (rs.next()) {
                long worldPopulation = rs.getLong("WorldPopulation");
                System.out.printf("   Total World Population: %,d%n", worldPopulation);
            }

            System.out.println("==============================================");

        } catch (SQLException e) {
            System.out.println("Query failed: " + e.getMessage());
        } finally {
            try {
                if (con != null) {
                    con.close();
                    System.out.println("Database connection closed.");
                }
            } catch (SQLException e) {
                System.out.println("Error closing connection");
            }
        }
    }
}
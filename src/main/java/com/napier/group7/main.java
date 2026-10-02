package com.napier.group7;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class main {

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

        try (Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM country")) {
            if (rs.next()) {
                System.out.println("Countries in database: " + rs.getInt(1));
            }
        } catch (SQLException e) {
            System.out.println("Query failed: " + e.getMessage());
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                System.out.println("Error closing connection");
            }
        }
    }
}


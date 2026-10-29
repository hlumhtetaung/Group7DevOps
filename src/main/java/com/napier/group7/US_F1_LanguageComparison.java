package com.napier.group7;

import java.sql.*;

public class US_F1_LanguageComparison {

    private static final String URL =
            "jdbc:mysql://localhost:3307/world?useSSL=false&allowPublicKeyRetrieval=true";
    private static final String USER = "root";
    private static final String PASSWORD = "password";

    public static void main(String[] args) {

        Connection con = null;
        int retries = 10;

        // Establish database connection
        for (int i = 1; i <= retries; i++) {
            System.out.println("Connecting to database, attempt " + i);

            try {
                con = DriverManager.getConnection(URL, USER, PASSWORD);
                System.out.println("Connected successfully.\n");
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

        // SQL Query for language comparison
        String languageQuery =
                "SELECT " +
                        "    cl.Language, " +
                        "    ROUND(SUM(c.Population * cl.Percentage / 100)) AS TotalSpeakers, " +
                        "    ROUND((SUM(c.Population * cl.Percentage / 100) / (SELECT SUM(Population) FROM country)) * 100, 2) AS WorldPercentage " +
                        "FROM " +
                        "    country c " +
                        "JOIN " +
                        "    countrylanguage cl ON c.Code = cl.CountryCode " +
                        "WHERE " +
                        "    cl.Language IN ('Chinese', 'English', 'Hindi', 'Spanish', 'Arabic') " +
                        "GROUP BY " +
                        "    cl.Language " +
                        "ORDER BY " +
                        "    TotalSpeakers DESC;";

        try (Connection connection = con;
             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(languageQuery)) {

            System.out.println("============================================================");
            System.out.printf("%-15s | %-20s | %-15s%n", "Language", "Total Speakers", "World %");
            System.out.println("============================================================");

            while (rs.next()) {
                String language = rs.getString("Language");
                long totalSpeakers = rs.getLong("TotalSpeakers");
                double worldPercentage = rs.getDouble("WorldPercentage");

                System.out.printf("%-15s | %-20d | %-14.2f%%%n", language, totalSpeakers, worldPercentage);
            }


        } catch (SQLException e) {
            System.out.println("Database operation failed: " + e.getMessage());
        }
    }
}
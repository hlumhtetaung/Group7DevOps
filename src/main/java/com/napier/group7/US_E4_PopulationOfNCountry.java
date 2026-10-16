package com.napier.group7;

import java.sql.*;
import java.util.Scanner;

public class US_E4_PopulationOfNCountry {
    private static final String URL = "jdbc:mysql://localhost:3307/world?useSSL=false&allowPublicKeyRetrieval=true";
    private static final String USER = "root";
    private static final String PASSWORD = "password";

    public static void main() throws SQLException {
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
            System.out.println("Connection Failed.");
            return;
        }

        // Query to print columns and list countries
        String column_names = "SELECT * FROM country";
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery(column_names);

        // Get table metadata
        java.sql.ResultSetMetaData rsmd = rs.getMetaData();
        int columnCount = rsmd.getColumnCount();

        // Loop through and print each column name
        for (int i = 1; i <= columnCount; i++) {
            System.out.print(rsmd.getColumnName(i) + "\t");
        }
        System.out.println();

        while (rs.next()) {
            System.out.println(rs.getString("Name"));
        }

        // Close the statement and result set
        rs.close();
        stmt.close();

        // Get user input
        Scanner sc = new Scanner(System.in);
        System.out.print("\nEnter the country name to view total population: ");
        String selectedCountry = sc.nextLine();

        // Query using PreparedStatement
        String final_query = "SELECT Name, SUM(Population) AS Total_Population FROM country WHERE Name = ? GROUP BY Name";

        try (PreparedStatement pstmt = con.prepareStatement(final_query)) {
            pstmt.setString(1, selectedCountry);

            // Execute the PreparedStatement and store it in a fresh ResultSet
            try (ResultSet rsFinal = pstmt.executeQuery()) {
                if (rsFinal.next()) {
                    String country = rsFinal.getString("Name");
                    long totalPopulation = rsFinal.getLong("Total_Population");

                    System.out.println("Country: " + country);
                    System.out.println("Total Population: " + totalPopulation);
                } else {
                    System.out.println("No data found for country: " + selectedCountry);
                }
            }
        } catch (SQLException e) {
            System.out.println("Query failed: " + e.getMessage());
        }

        // Close connection when done
        con.close();
    }
}
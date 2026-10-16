
package com.napier.group7;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

public class ReportTestRunner {

    // MySQL database connection
    private static final String DB_URL =
            "jdbc:mysql://localhost:3307/world"
                    + "?useSSL=false&allowPublicKeyRetrieval=true";

    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "password";

    public static void main(String[] args) {

        try (Connection con = DriverManager.getConnection(
                DB_URL, DB_USER, DB_PASSWORD);
             Scanner scanner = new Scanner(System.in)) {

            System.out.println("Connected to MySQL database successfully!");

            boolean running = true;

            while (running) {

                System.out.println("\n==================================");
                System.out.println("    POPULATION REPORTING SYSTEM");
                System.out.println("==================================");
                System.out.println("1. US-A1: All Countries");
                System.out.println("2. US-A2: Countries by Continent");
                System.out.println("3. US-A3: Countries by Region");
                System.out.println("4. US-A4: Top N Countries Worldwide");
                System.out.println("0. Exit");
                System.out.println("==================================");
                System.out.print("Enter your choice: ");

                String choice = scanner.nextLine().trim();

                switch (choice) {

                    case "1":
                        System.out.println("\nGenerating US-A1 report...");
                        US_A1_AllCountries.generateAllCountriesReport(con);
                        break;

                    case "2":
                        System.out.print(
                                "Enter continent (e.g. Asia): ");

                        String continent = scanner.nextLine().trim();

                        US_A2_CountriesByContinent
                                .generateCountriesByContinentReport(
                                        con, continent);
                        break;

                    case "3":
                        System.out.print(
                                "Enter region (e.g. Eastern Asia): ");

                        String region = scanner.nextLine().trim();

                        US_A3_CountriesByRegion
                                .generateCountriesByRegionReport(
                                        con, region);
                        break;

                    case "4":
                        System.out.print(
                                "Enter N (e.g. 10): ");

                        try {
                            int n = Integer.parseInt(scanner.nextLine().trim());

                            US_A4_TopNCountriesWorldwide
                                    .generateTopNCountriesWorldwide(con, n);

                        }  catch (NumberFormatException e) {
                            System.out.println("Please enter a valid whole number.");
                        }
                        break;

                    case "0":
                        System.out.println(
                                "\nExiting Population Reporting System...");
                        running = false;
                        break;

                    default:
                        System.out.println(
                                "Invalid choice. Please try again.");
                        break;
                }
            }

        } catch (SQLException e) {

            System.out.println("Database connection failed!");
            System.out.println("Error: " + e.getMessage());

            System.out.println(
                    "Please check that the MySQL Docker container is running.");
        }
    }
}

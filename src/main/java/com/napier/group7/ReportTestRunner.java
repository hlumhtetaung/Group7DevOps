
package com.napier.group7;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

public class ReportTestRunner {

    private static final String DB_URL =
            "jdbc:mysql://localhost:3307/world"
                    + "?useSSL=false&allowPublicKeyRetrieval=true";

    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "password";

    public static void main(String[] args) {

        try (Connection con = DriverManager.getConnection(
                DB_URL, DB_USER, DB_PASSWORD);
             Scanner scanner = new Scanner(System.in)) {

            System.out.println("Connected to world database!");

            boolean running = true;

            while (running) {

                System.out.println();
                System.out.println("==============================");
                System.out.println(" POPULATION REPORTING SYSTEM");
                System.out.println("==============================");
                System.out.println("1. US-A1: All Countries");
                System.out.println("2. US-A2: Countries by Continent");
                System.out.println("0. Exit");
                System.out.println("==============================");
                System.out.print("Enter your choice: ");

                String choice = scanner.nextLine().trim();

                switch (choice) {

                    case "1":
                        US_A1_AllCountries
                                .generateAllCountriesReport(con);
                        break;

                    case "2":
                        System.out.print(
                                "Enter continent (e.g. Asia): ");

                        String continent =
                                scanner.nextLine().trim();

                        US_A2_CountriesByContinent
                                .generateCountriesByContinentReport(
                                        con, continent);
                        break;

                    case "0":
                        System.out.println("Exiting program...");
                        running = false;
                        break;

                    default:
                        System.out.println(
                                "Invalid choice. Please try again.");
                }
            }

        } catch (SQLException e) {
            System.out.println(
                    "Database connection failed: " + e.getMessage());
        }
    }
}

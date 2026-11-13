
package com.napier.group7;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class WorldDatabaseConnectionApp {

    private static final String HOST = getEnv("DB_HOST", "localhost");
    private static final String PORT = getEnv("DB_PORT", "3307");
    private static final String USER = getEnv("DB_USER", "root");
    private static final String PASSWORD = getEnv("DB_PASSWORD", "password");

    private static final String URL =
            "jdbc:mysql://" + HOST + ":" + PORT
                    + "/world?useSSL=false&allowPublicKeyRetrieval=true";

    private static String getEnv(String name, String fallback) {
        String value = System.getenv(name);
        return (value == null || value.isEmpty()) ? fallback : value;
    }

    public static void main(String[] args) {

        Connection con = null;
        int retries = 10;

        for (int i = 1; i <= retries; i++) {
            System.out.println("Connecting to " + HOST + ":" + PORT
                    + ", attempt " + i);

            try {
                con = DriverManager.getConnection(URL, USER, PASSWORD);
                System.out.println("Connected successfully!");
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
            System.out.println("Could not connect to the database.");
            return;
        }

        try (Scanner scanner = new Scanner(System.in)) {

            try (Statement stmt = con.createStatement();
                 ResultSet rs = stmt.executeQuery(
                         "SELECT COUNT(*) FROM country")) {

                if (rs.next()) {
                    System.out.println("Countries in database: "
                            + rs.getInt(1));
                }
            }

            int choice;
            scanner.nextLine(); // Eliminate leftover newline

            do {
                System.out.println("\nWORLD DATABASE REPORTING SYSTEM");
                System.out.println("1. All Countries");
                System.out.println("2. Countries by Continent");
                System.out.println("3. Countries by Region");
                System.out.println("4. Top N Countries Worldwide");
                System.out.println("5. Top N Countries by Continent");
                System.out.println("6. Top N Countries by Region");
                System.out.println("7. Total Population in Region");
                System.out.println("8. Population of N Country");
                System.out.println("9. District Population");
                System.out.println("10. City Population");
                System.out.println("11. Language Comparison");
                System.out.println("12. Continent Distribution");
                System.out.println("13. Region Distribution");
                System.out.println("14. Country-City Query");
                System.out.println("15. Region Cities Query");
                System.out.println("16. City Query");
                System.out.println("17. Top Populated Cities");
                System.out.println("18. Top Populated Cities in Continent");
                System.out.println("19. Continent Population Report");
                System.out.println("20. Country Distributions Report");
                System.out.println("21. Top Populated Cities in Country");
                System.out.println("22. Top Populated Cities in District");
                System.out.println("23. World Population Report");
                System.out.println("0. Exit");

                System.out.print("\nEnter your choice: ");

                while (!scanner.hasNextInt()) {
                    System.out.println("Please enter a valid number.");
                    scanner.next();
                    System.out.print("Enter your choice: ");
                }

                choice = scanner.nextInt();


                switch (choice) {

                    case 1:
                        US_A1_AllCountries.generateAllCountriesReport(con);
                        break;

                    case 2: {
                        scanner.nextLine(); // Consume leftover newline

                        System.out.print("Enter continent: ");
                        String continent = scanner.nextLine();

                        US_A2_CountriesByContinent
                                .generateCountriesByContinentReport(con, continent);
                        break;
                    }

                    case 3: {
                        scanner.nextLine();

                        System.out.print("Enter region: ");
                        String region = scanner.nextLine();

                        US_A3_CountriesByRegion
                                .generateCountriesByRegionReport(con, region);
                        break;
                    }

                    case 4: {
                        System.out.print("Enter the number of countries: ");
                        int n = scanner.nextInt();

                        US_A4_TopNCountriesWorldwide
                                .generateTopNCountriesWorldwide(con, n);
                        break;
                    }

                    case 5: {
                        scanner.nextLine();

                        System.out.print("Enter continent: ");
                        String continent = scanner.nextLine();

                        System.out.print("Enter the number of countries: ");
                        int n = scanner.nextInt();

                        US_A5_TopNCountriesByContinent
                                .generateTopNCountriesByContinentReport(con, continent, n);
                        break;
                    }

                    case 6: {
                        scanner.nextLine();

                        System.out.print("Enter region: ");
                        String region = scanner.nextLine();

                        System.out.print("Enter the number of countries: ");
                        int n = scanner.nextInt();

                        US_A6_TopNCountriesByRegion
                                .generateTopNCountriesByRegionReport(con, region, n);
                        break;
                    }

                    case 7:
                        US_E3_TotalPopNregion.main(args);
                        break;

                    case 8:
                        US_E4_PopulationOfNCountry.main();
                        break;

                    case 9:
                        US_E5_District_Population.main(args);
                        break;

                    case 10:
                        US_E6_CityPopulation.main(args);
                        break;

                    case 11:
                        US_F1_LanguageComparison.main(args);
                        break;

                    case 12:
                        ContinentDistributionReport.main(args);
                        break;

                    case 13:
                        RegionDistributionReport.main(args);
                        break;

                    case 14:
                        CountryCityQuery.main(args);
                        break;

                    case 15:
                        RegionCitiesQuery.main(args);
                        break;

                    case 16:
                        City_Query.main(args);
                        break;

                    case 17:
                        TopPopulatedCities.main(args);
                        break;

                    case 18:
                        TopPopulatedCitiesInContinent.main(args);
                        break;

                    case 19:
                        ContinentPopulationReport.main(args);
                        break;

                    case 20:
                        CountryDistributionReport.main(args);
                        break;

                    case 21:
                        TopPopulatedCitiesInCountry.main(args);
                        break;

                    case 22:
                        TopPopulatedCitiesInDistrict.main(args);
                        break;

                    case 23:
                        WorldPopulationReport.main(args);
                        break;

                    case 0:
                        System.out.println("Exiting application...");
                        break;

                    default:
                        System.out.println("Invalid choice. Please try again.");
                }


            } while (choice != 0);

        } catch (SQLException e) {
            System.out.println("Database query failed: " + e.getMessage());

        } finally {
            try {
                con.close();
                System.out.println("Database connection closed.");
            } catch (SQLException e) {
                System.out.println("Error closing database connection.");
            }
        }
    }
}

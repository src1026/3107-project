package presentation;

import java.io.*;
import java.util.*;
import datamanagement.ParkingViolationReader;
import datamanagement.PropertyReader;
import datamanagement.PopulationReader;
import common.ParkingViolation;
import common.Property;
import processor.DataProcessor;

public class Main {
    // TODO: add static fields for:
    // - processor (DataProcessor)
    // - scanner (Scanner)
    private static DataProcessor processor;
    private static Scanner scanner;

    public static void main(String[] args) {
        // TODO: validate command-line arguments (must be exactly 4)
        if (args.length != 4) {
            System.err.println("Error: Incorrect number of arguments.");
            System.err.println("Usage: java Main <csv|json> <parking_file> <properties_file> <population_file>");
            System.exit(1);
        }

        String format = args[0];
        String parkingFile = args[1];
        String propertiesFile = args[2];
        String populationFile = args[3];

        // TODO: check if first argument is "csv" or "json" (case-sensitive)
        if (!format.equals("csv") && !format.equals("json")) {
            System.err.println("Error: First argument must be either 'csv' or 'json' (case-sensitive).");
            System.exit(1);
        }

        // TODO: read parking violations file (CSV or JSON based on first argument)
        // TODO: read properties file
        // TODO: read population file
        // TODO: create DataProcessor instance
        // TODO: initialize Scanner for user input
        // TODO: call showMainMenu()
        // TODO: handle FileNotFoundException and IOException with error messages
        try {
            List<ParkingViolation> violations;
            if (format.equals("csv")) {
                violations = ParkingViolationReader.readFromCSV(parkingFile);
            } else {
                violations = ParkingViolationReader.readFromJSON(parkingFile);
            }

            List<Property> properties = PropertyReader.readFromCSV(propertiesFile);
            Map<String, Integer> population = PopulationReader.readFromFile(populationFile);

            processor = new DataProcessor(violations, properties, population);
            scanner = new Scanner(System.in);

            showMainMenu();

        } catch (FileNotFoundException e) {
            System.err.println("Error: File not found: " + e.getMessage());
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
            System.exit(1);
        } catch (IllegalArgumentException e) {
            System.err.println("Error: " + e.getMessage());
            System.exit(1);
        }
    }

    private static void showMainMenu() {
        // TODO: loop until user exits
        // TODO: display menu options (1-5, 0 to exit)
        // TODO: read user input
        // TODO: parse integer choice
        // TODO: call handleMenuChoice() with the choice
        // TODO: ignore invalid input (non-integer) and show menu again
        while (true) {
            System.out.println("\nMain Menu:");
            System.out.println("1. Total population for all ZIP codes");
            System.out.println("2. Fines per capita for each ZIP code");
            System.out.println("3. Average residential market value for a ZIP code");
            System.out.println("4. Average residential total livable area for a ZIP code");
            System.out.println("5. Residential market value per capita for a ZIP code");
            System.out.println("0. Exit");
            System.out.print("Enter your choice: ");

            if (!scanner.hasNextLine()) {
                break;
            }

            String input = scanner.nextLine().trim();

            try {
                int choice = Integer.parseInt(input);
                handleMenuChoice(choice);
            } catch (NumberFormatException e) {
                // Invalid input, show menu again
                continue;
            }
        }
    }

    private static void handleMenuChoice(int choice) {
        // TODO: use switch statement to handle choices 0-5
        // TODO: call appropriate handler method for each option
        // TODO: option 0 should exit the program
        switch (choice) {
            case 1:
                handleTotalPopulation();
                break;
            case 2:
                handleFinesPerCapita();
                break;
            case 3:
                handleAverageMarketValue();
                break;
            case 4:
                handleAverageTotalLivableArea();
                break;
            case 5:
                handleMarketValuePerCapita();
                break;
            case 0:
                System.out.println("Exiting program. Goodbye!");
                System.exit(0);
                break;
            default:
                // Invalid choice, menu will be shown again
                break;
        }
    }

    /**
     * Menu Option #1: Total population for all ZIP codes.
     */
    private static void handleTotalPopulation() {
        // menu option 1
        // TODO: Get total population from processor
        // TODO: Display the result
        int total = processor.getTotalPopulation();
        System.out.println("\nTotal population for all ZIP codes: " + total);
    }

    private static void handleFinesPerCapita() {
        // menu option 2
        // TODO: get fines per capita map from processor
        // TODO: display each ZIP code and its fines per capita (formatted to 4 decimals)
        // TODO: format: "ZIP_CODE fines_per_capita" (e.g., "19103 0.0284")
        Map<String, Double> finesPerCapita = processor.getFinesPerCapita();
        
        System.out.println();
        for (Map.Entry<String, Double> entry : finesPerCapita.entrySet()) {
            System.out.println(entry.getKey() + " " + DataProcessor.formatFourDecimals(entry.getValue()));
        }
    }

    private static void handleAverageMarketValue() {
        // menu option 3
        // TODO: prompt user for ZIP code
        // TODO: extract first 5 digits if longer
        // TODO: get average market value from processor
        // TODO: display the result
        System.out.print("Enter ZIP code: ");
        String zipCode = scanner.nextLine().trim();
        
        // Extract first 5 digits if longer
        if (zipCode.length() >= 5) {
            zipCode = zipCode.substring(0, 5);
        }
        
        try {
            int average = processor.getAverageMarketValue(zipCode);
            System.out.println("\nAverage residential market value: " + average);
        } catch (IllegalArgumentException e) {
            System.out.println("\nError: " + e.getMessage());
        }
    }

    private static void handleAverageTotalLivableArea() {
        // menu option 4
        // TODO: prompt user for ZIP code
        // TODO: extract first 5 digits if longer
        // TODO: get average total livable area from processor
        // TODO: display the result
        System.out.print("Enter ZIP code: ");
        String zipCode = scanner.nextLine().trim();
        
        // Extract first 5 digits if longer
        if (zipCode.length() >= 5) {
            zipCode = zipCode.substring(0, 5);
        }
        
        try {
            int average = processor.getAverageTotalLivableArea(zipCode);
            System.out.println("\nAverage residential total livable area: " + average);
        } catch (IllegalArgumentException e) {
            System.out.println("\nError: " + e.getMessage());
        }
    }

    private static void handleMarketValuePerCapita() {
        // TODO: prompt user for ZIP code
        // TODO: extract first 5 digits if longer
        // TODO: get market value per capita from processor
        // TODO: display the result
        System.out.print("Enter ZIP code: ");
        String zipCode = scanner.nextLine().trim();
        
        // Extract first 5 digits if longer
        if (zipCode.length() >= 5) {
            zipCode = zipCode.substring(0, 5);
        }
        
        try {
            int perCapita = processor.getMarketValuePerCapita(zipCode);
            System.out.println("\nResidential market value per capita: " + perCapita);
        } catch (IllegalArgumentException e) {
            System.out.println("\nError: " + e.getMessage());
        }
    }
}


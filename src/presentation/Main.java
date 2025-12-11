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

            processor = DataProcessor.getInstance(violations, properties, population);
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

    /**
     * Menu Option #2: Handle fines per capita for each ZIP code.
     */
    private static void handleFinesPerCapita() {
        Map<String, Double> finesPerCapita = processor.getFinesPerCapita();
        System.out.println();
        for (Map.Entry<String, Double> entry : finesPerCapita.entrySet()) {
            System.out.println(entry.getKey() + " " + DataProcessor.formatFourDecimals(entry.getValue()));
        }
    }

    /**
     * Helper for menu options 3-5: Display results for multiple ZIP codes.
     */
    private static void displayZipCodes(java.util.function.Function<String, Integer> calculator,
                                       String resultLabel) {
        System.out.print("Enter ZIP codes separated by commas (e.g., 19103,19104): ");
        String input = scanner.nextLine().trim();
        String[] zipCodeArray = input.split(",");
        for (int i = 0; i < zipCodeArray.length; i++) {
            zipCodeArray[i] = zipCodeArray[i].trim();
        }
        try {
            // normalizes zip code formats
            Map<String, Integer> results = processor.processZipCodes(calculator, zipCodeArray);
            System.out.println();
            for (Map.Entry<String, Integer> entry : results.entrySet()) {
                System.out.println("ZIP " + entry.getKey() + ": " + resultLabel + ": " + entry.getValue());
            }
        } catch (IllegalArgumentException e) {
            System.out.println("\nError: " + e.getMessage());
        }
    }

    /**
     * Menu Option #3: Handle average market value for a ZIP code.
     */
    private static void handleAverageMarketValue() {
        displayZipCodes(zipCode -> processor.getAverageMarketValue(zipCode), 
                                  "Average residential market value");
    }

    /**
     * Menu Option #4: Handle average total livable area for a ZIP code.
     */
    private static void handleAverageTotalLivableArea() {
        displayZipCodes(zipCode -> processor.getAverageTotalLivableArea(zipCode), 
                                  "Average residential total livable area");
    }

    /**
     * Menu Option #5: Handle market value per capita for a ZIP code.
     */
    private static void handleMarketValuePerCapita() {
        displayZipCodes(zipCode -> processor.getMarketValuePerCapita(zipCode), 
                                  "Residential market value per capita");
    }
}


package presentation;

import java.io.*;
import java.util.*;
import java.util.function.Function;
import datamanagement.ParkingViolationReader;
import datamanagement.PropertyReader;
import datamanagement.PopulationReader;
import common.ParkingViolation;
import common.Property;
import processor.DataProcessor;

public class Main {
    private static DataProcessor processor;
    private static Scanner scanner;

    public static void main(String[] args) {
        if (args.length != 4) {
            System.err.println("Error: Incorrect number of arguments.");
            System.err.println("Usage: java Main <csv|json> <parking_file> <properties_file> <population_file>");
            System.exit(1);
        }

        String format = args[0];
        String parkingFile = args[1];
        String propertiesFile = args[2];
        String populationFile = args[3];

        if (!format.equals("csv") && !format.equals("json")) {
            System.err.println("Error: First argument must be either 'csv' or 'json' (case-sensitive).");
            System.exit(1);
        }

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

    
    // Menu Option #1: Total population for all ZIP codes
    private static void handleTotalPopulation() {
        int total = processor.getTotalPopulation();
        System.out.println("\nTotal population for all ZIP codes: " + total);
    }

    // Menu Option #2: Handle fines per capita for each ZIP code
    private static void handleFinesPerCapita() {
        Map<String, Double> finesPerCapita = processor.getFinesPerCapita();
        System.out.println();
        for (Map.Entry<String, Double> entry : finesPerCapita.entrySet()) {
            System.out.println(entry.getKey() + " " + DataProcessor.formatFourDecimals(entry.getValue()));
        }
    }

    // Menu Option #3: Handle average market value for a ZIP code
    private static void handleAverageMarketValue() {
        displayZipCodes(zipCode -> processor.getAverageMarketValue(zipCode), 
                                  "Average residential market value");
    }

    // Menu Option #4: Handle average total livable area for a ZIP code
    private static void handleAverageTotalLivableArea() {
        displayZipCodes(zipCode -> processor.getAverageTotalLivableArea(zipCode), 
                                  "Average residential total livable area");
    }

    // Menu Option #5: Handle market value per capita for a ZIP code
    private static void handleMarketValuePerCapita() {
        displayZipCodes(zipCode -> processor.getMarketValuePerCapita(zipCode), 
                                  "Residential market value per capita");
    }

    // Helper: Displays results for multiple ZIP codes in Options #3-5
    private static void displayZipCodes(Function<String, Integer> calculator,
        String resultLabel) {
        System.out.print("Enter ZIP codes separated by commas (e.g., 19103,19104): ");
        String input = scanner.nextLine().trim();
        String[] zipCodeArray = input.split(",");
        for (int i = 0; i < zipCodeArray.length; i++) {
            zipCodeArray[i] = zipCodeArray[i].trim();
        }
        try {
            // Handles multiple ZIP codes input using processZipCodes from DataProcessor
            Map<String, Integer> results = processor.processZipCodes(calculator, zipCodeArray);
            System.out.println();
            for (Map.Entry<String, Integer> entry : results.entrySet()) {
                System.out.println("ZIP " + entry.getKey() + ": " + resultLabel + ": " + entry.getValue());
            }
        } catch (IllegalArgumentException e) {
            System.out.println("\nError: " + e.getMessage());
        }
    }
}


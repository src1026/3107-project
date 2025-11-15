import java.io.*;
import java.util.*;

public class Main {
    // TODO: add static fields for:
    // - processor (DataProcessor)
    // - scanner (Scanner)

    public static void main(String[] args) {
        // TODO: validate command-line arguments (must be exactly 4)
        // TODO: check if first argument is "csv" or "json" (case-sensitive)
        // TODO: read parking violations file (CSV or JSON based on first argument)
        // TODO: read properties file
        // TODO: read population file
        // TODO: create DataProcessor instance
        // TODO: initialize Scanner for user input
        // TODO: call showMainMenu()
        // TODO: handle FileNotFoundException and IOException with error messages
    }

    private static void showMainMenu() {
        // TODO: loop until user exits
        // TODO: display menu options (1-5, 0 to exit)
        // TODO: read user input
        // TODO: parse integer choice
        // TODO: call handleMenuChoice() with the choice
        // TODO: ignore invalid input (non-integer) and show menu again
    }

    private static void handleMenuChoice(int choice) {
        // TODO: use switch statement to handle choices 0-5
        // TODO: call appropriate handler method for each option
        // TODO: option 0 should exit the program
    }

    /**
     * Menu Option #1: Total population for all ZIP codes.
     */
    private static void handleTotalPopulation() {
        // menu option 1
        // TODO: Get total population from processor
        // TODO: Display the result
    }

    private static void handleFinesPerCapita() {
        // menu option 2
        // TODO: get fines per capita map from processor
        // TODO: display each ZIP code and its fines per capita (formatted to 4 decimals)
        // TODO: format: "ZIP_CODE fines_per_capita" (e.g., "19103 0.0284")
    }

    private static void handleAverageMarketValue() {
        // menu option 3
        // TODO: prompt user for ZIP code
        // TODO: extract first 5 digits if longer
        // TODO: get average market value from processor
        // TODO: display the result
    }

    private static void handleAverageTotalLivableArea() {
        // menu option 4
        // TODO: prompt user for ZIP code
        // TODO: extract first 5 digits if longer
        // TODO: get average total livable area from processor
        // TODO: display the result
    }

    private static void handleMarketValuePerCapita() {
        // TODO: prompt user for ZIP code
        // TODO: extract first 5 digits if longer
        // TODO: get market value per capita from processor
        // TODO: display the result
    }
}


import java.util.*;
import java.text.DecimalFormat;

public class DataProcessor {
    // TODO: add private fields for:
    // - violations (List<ParkingViolation>)
    // - properties (List<Property>)
    // - population (Map<String, Integer>)

    // TODO: Add constructor to initialize all fields

    public int getTotalPopulation() {
        // TODO: Sum all population values from the map
        return 0;
    }

    public Map<String, Double> getFinesPerCapita() {
        // TODO: calculate total fines per ZIP code (only PA violations with valid ZIP)
        // TODO: for each ZIP code, calculate fines per capita = total fines / population
        // TODO: only include ZIP codes with non-zero fines and population
        // TODO: return TreeMap for sorted output
        return new TreeMap<>();
    }


    public int getAverageMarketValue(String zipCode) {
        // TODO: sum market values for properties in the ZIP code (only valid values)
        // TODO: count number of valid properties
        // TODO: calculate average and round to integer
        // TODO: return 0 if no valid properties found
        return 0;
    }

    public int getAverageTotalLivableArea(String zipCode) {
        // TODO: sum total livable areas for properties in the ZIP code (only valid values)
        // TODO: count number of valid properties
        // TODO: calculate average and round to integer
        // TODO: return 0 if no valid properties found
        return 0;
    }

    public int getMarketValuePerCapita(String zipCode) {
        // TODO: sum total market value for all properties in ZIP code (only valid values)
        // TODO: get population for ZIP code
        // TODO: calculate per capita = total market value / population
        // TODO: round to integer
        // TODO: return 0 if no valid properties or ZIP code not in population data
        return 0;
    }

    public static String formatFourDecimals(double value) {
        // TODO: use DecimalFormat to format to 4 decimal places with trailing zeros
        // TODO: format: "0.0000"
        return "";
    }
}


package processor;

import java.util.*;
import java.text.DecimalFormat;
import common.ParkingViolation;
import common.Property;

public class DataProcessor {
    // TODO: add private fields for:
    // - violations (List<ParkingViolation>)
    // - properties (List<Property>)
    // - population (Map<String, Integer>)
    private List<ParkingViolation> violations;
    private List<Property> properties;
    private Map<String, Integer> population;

    // TODO: Add constructor to initialize all fields
    public DataProcessor(List<ParkingViolation> violations, 
                        List<Property> properties, 
                        Map<String, Integer> population) {
        if (violations == null) {
            throw new IllegalArgumentException("Violations list cannot be null");
        }
        if (properties == null) {
            throw new IllegalArgumentException("Properties list cannot be null");
        }
        if (population == null) {
            throw new IllegalArgumentException("Population map cannot be null");
        }
        
        this.violations = violations;
        this.properties = properties;
        this.population = population;
    }

    public int getTotalPopulation() {
        // TODO: Sum all population values from the map
        int total = 0;
        for (int pop : population.values()) {
            total += pop;
        }
        return total;
    }

    public Map<String, Double> getFinesPerCapita() {
        Map<String, Double> finesPerCapita = new TreeMap<>();
        Map<String, Double> totalFines = new HashMap<>();

        // TODO: calculate total fines per ZIP code (only PA violations with valid ZIP)
        for (ParkingViolation violation : violations) {
            if (violation != null && violation.hasValidZipCode() && violation.isFromPA()) {
                String zipCode = violation.getZipCode();
                totalFines.put(zipCode, totalFines.getOrDefault(zipCode, 0.0) + violation.getFine());
            }
        }

        // TODO: for each ZIP code, calculate fines per capita = total fines / population
        // TODO: only include ZIP codes with non-zero fines and population
        // TODO: return TreeMap for sorted output
        for (Map.Entry<String, Double> entry : totalFines.entrySet()) {
            String zipCode = entry.getKey();
            double totalFine = entry.getValue();
            Integer pop = population.get(zipCode);

            if (pop != null && pop > 0 && totalFine > 0) {
                double perCapita = totalFine / pop;
                finesPerCapita.put(zipCode, perCapita);
            }
        }

        return finesPerCapita;
    }

    public int getAverageMarketValue(String zipCode) {
        if (zipCode == null) {
            throw new IllegalArgumentException("ZIP code cannot be null");
        }
        
        // TODO: sum market values for properties in the ZIP code (only valid values)
        // TODO: count number of valid properties
        // TODO: calculate average and round to integer
        // TODO: return 0 if no valid properties found
        double sum = 0;
        int count = 0;

        for (Property property : properties) {
            if (property != null && zipCode.equals(property.getZipCode()) && property.hasValidMarketValue()) {
                sum += property.getMarketValue();
                count++;
            }
        }

        if (count == 0) {
            return 0;
        }

        return (int) Math.round(sum / count);
    }

    public int getAverageTotalLivableArea(String zipCode) {
        if (zipCode == null) {
            throw new IllegalArgumentException("ZIP code cannot be null");
        }
        
        // TODO: sum total livable areas for properties in the ZIP code (only valid values)
        // TODO: count number of valid properties
        // TODO: calculate average and round to integer
        // TODO: return 0 if no valid properties found
        double sum = 0;
        int count = 0;

        for (Property property : properties) {
            if (property != null && zipCode.equals(property.getZipCode()) && property.hasValidTotalLivableArea()) {
                sum += property.getTotalLivableArea();
                count++;
            }
        }

        if (count == 0) {
            return 0;
        }

        return (int) Math.round(sum / count);
    }

    public int getMarketValuePerCapita(String zipCode) {
        if (zipCode == null) {
            throw new IllegalArgumentException("ZIP code cannot be null");
        }
        
        // TODO: sum total market value for all properties in ZIP code (only valid values)
        double totalMarketValue = 0;

        for (Property property : properties) {
            if (property != null && zipCode.equals(property.getZipCode()) && property.hasValidMarketValue()) {
                totalMarketValue += property.getMarketValue();
            }
        }

        // TODO: get population for ZIP code
        // TODO: calculate per capita = total market value / population
        // TODO: round to integer
        // TODO: return 0 if no valid properties or ZIP code not in population data
        Integer pop = population.get(zipCode);
        if (pop == null || pop == 0 || totalMarketValue == 0) {
            return 0;
        }

        return (int) Math.round(totalMarketValue / pop);
    }

    public static String formatFourDecimals(double value) {
        // TODO: use DecimalFormat to format to 4 decimal places with trailing zeros
        // TODO: format: "0.0000"
        DecimalFormat df = new DecimalFormat("0.0000");
        return df.format(value);
    }
}


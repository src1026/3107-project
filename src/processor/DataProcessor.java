package processor;

import java.util.*;
import java.util.function.Function;
import java.text.DecimalFormat;
import common.ParkingViolation;
import common.Property;

public class DataProcessor {
    private static DataProcessor instance; // singleton
    private ViolationList violations;
    private List<Property> properties;
    private Map<String, Integer> population;

    private Map<String, Integer> averageMarketValueCache;

    private Map<String, Integer> averageTotalLivableAreaCache;

    // private constructor for singleton pattern
    private DataProcessor(List<ParkingViolation> violations, 
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
        
        this.violations = new ViolationList(violations);
        this.properties = properties;
        this.population = population;
        this.averageMarketValueCache = new HashMap<>();
        this.averageTotalLivableAreaCache = new HashMap<>();
    }

    //Gets the singleton instance of DataProcessor.
    // Creates the instance on first call with the provided parameters.
    public static DataProcessor getInstance(List<ParkingViolation> violations, 
                                           List<Property> properties, 
                                           Map<String, Integer> population) {
        if (instance == null) {
            instance = new DataProcessor(violations, properties, population);
        }
        return instance;
    }

    public static DataProcessor getInstance() {
        return instance;
    }

    // resets the singleton instance to null.
    // this method is primarily for testing purposes to allow creating new instances.
    // Also clears all memoization caches.

    public static void resetInstance() {
        if (instance != null) {
            instance.averageMarketValueCache.clear();
            instance.averageTotalLivableAreaCache.clear();
        }
        instance = null;
    }

    public int getTotalPopulation() {
        int total = 0;
        for (int pop : population.values()) {
            total += pop;
        }
        return total;
    }

    public Map<String, Double> getFinesPerCapita() {
        Map<String, Double> finesPerCapita = new TreeMap<>();
        Map<String, Double> totalFines = new HashMap<>();

        // calculate total fines per ZIP code (only PA violations with valid ZIP)
        violations.reset();
        while (violations.hasNext()) {
            ParkingViolation violation = violations.next();
            if (violation != null && violation.hasValidZipCode() && violation.isFromPA()) {
                String zipCode = violation.getZipCode();
                totalFines.put(zipCode, totalFines.getOrDefault(zipCode, 0.0) + violation.getFine());
            }
        }

        // for each ZIP code, calculate fines per capita = total fines / population
        // only include ZIP codes with non-zero fines and population
        // return TreeMap for sorted output
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
        
        // memoization: check cache first - return cached result if available
        if (averageMarketValueCache.containsKey(zipCode)) {
            return averageMarketValueCache.get(zipCode);
        }
        
        // Calculate average market value for properties in the ZIP code (only valid values)
        // Using Streams and Lambda expressions feature
        OptionalDouble average = properties.stream()
                .filter(property -> property != null 
                        && zipCode.equals(property.getZipCode()) 
                        && property.hasValidMarketValue()) 
                .mapToDouble(Property::getMarketValue) 
                .average(); // Java Features: Streams and Lambda

        int result = average.isPresent() ? (int) Math.round(average.getAsDouble()) : 0;
        
        // memoization: store computed result in cache for future use
        averageMarketValueCache.put(zipCode, result);
        
        return result;
    }

    public int getAverageTotalLivableArea(String zipCode) {
        if (zipCode == null) {
            throw new IllegalArgumentException("ZIP code cannot be null");
        }
        
        // memoization: check cache first - return cached result if available
        if (averageTotalLivableAreaCache.containsKey(zipCode)) {
            return averageTotalLivableAreaCache.get(zipCode);
        }
        
        // calculate average total livable area for properties in the ZIP code (only valid values)
        double sum = 0;
        int count = 0;

        for (Property property : properties) {
            if (property != null && zipCode.equals(property.getZipCode()) && property.hasValidTotalLivableArea()) {
                sum += property.getTotalLivableArea();
                count++;
            }
        }

        int result = (count == 0) ? 0 : (int) Math.round(sum / count);
        averageTotalLivableAreaCache.put(zipCode, result);
        
        return result;
    }

    public int getMarketValuePerCapita(String zipCode) {
        if (zipCode == null) {
            throw new IllegalArgumentException("ZIP code cannot be null");
        }
        
        // sum total market value for all properties in ZIP code (only valid values)
        double totalMarketValue = 0;

        for (Property property : properties) {
            if (property != null && zipCode.equals(property.getZipCode()) && property.hasValidMarketValue()) {
                totalMarketValue += property.getMarketValue();
            }
        }

        // get population for ZIP code
        // calculate per capita = total market value / population
        // round to integer
        // return 0 if no valid properties or ZIP code not in population data
        Integer pop = population.get(zipCode);
        if (pop == null || pop == 0 || totalMarketValue == 0) {
            return 0;
        }

        return (int) Math.round(totalMarketValue / pop);
    }

    public static String formatFourDecimals(double value) {
        // use DecimalFormat to format to 4 decimal places with trailing zeros
        // format: "0.0000"
        DecimalFormat df = new DecimalFormat("0.0000");
        return df.format(value);
    }

    // helper: handles multiple ZIP codes input
    public Map<String, Integer> processZipCodes(Function<String, Integer> calculator,
                                                 String... zipCodes) { // Java Features: Generics and Varargs 
        // preserves insertion order of inputs
        Map<String, Integer> results = new LinkedHashMap<>();
        for (String zipCode : zipCodes) {
            if (zipCode != null) {
                // normalizes ZIP code to first 5 digits
                String normalizedZip = zipCode.length() >= 5 ? zipCode.substring(0, 5) : zipCode;
                int result = calculator.apply(normalizedZip);
                results.put(normalizedZip, result);
            }
        }
        return results;
    }
}



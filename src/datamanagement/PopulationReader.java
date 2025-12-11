package datamanagement;

import java.io.*;
import java.util.*;

/**
 * Reads population data from a whitespace-separated file.
 */
public class PopulationReader {
    
    /**
     * Reads population data from a file.
     * Each line contains: ZIP_CODE POPULATION
     * Returns a map from ZIP code to population.
     */
    public static Map<String, Integer> readFromFile(String filename) throws IOException {
        if (filename == null || filename.trim().isEmpty()) {
            throw new IllegalArgumentException("Filename cannot be null or empty");
        }
        
        Map<String, Integer> populationMap = new HashMap<>();
        
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            // TODO: Read file line by line
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) {
                    continue;
                }
                
                // TODO: Split each line on whitespace
                String[] parts = line.split("\\s+");
                if (parts.length >= 2) {
                    try {
                        // TODO: Extract first 5 digits of ZIP code
                        String zipCode = parts[0].trim();
                        if (zipCode.length() >= 5) {
                            zipCode = zipCode.substring(0, 5);
                        }
                        // TODO: Parse population as integer
                        int population = Integer.parseInt(parts[1].trim());
                        // TODO: Add to map (ZIP code -> population)
                        populationMap.put(zipCode, population);
                    } catch (NumberFormatException e) {
                        // TODO: Handle invalid lines gracefully (skip them)
                        continue;
                    }
                }
            }
        }
        
        return populationMap;
    }
}


package datamanagement;

import java.io.*;
import java.util.*;

public class PopulationReader {
    public static Map<String, Integer> readFromFile(String filename) throws IOException {
        // this function reads population data from a file; each line contains: zip_code and population
        // returns a map from zip code to population
        if (filename == null || filename.trim().isEmpty()) {
            throw new IllegalArgumentException("Filename cannot be null or empty");
        }
        
        Map<String, Integer> populationMap = new HashMap<>();
        
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) {
                    continue;
                }
                
                // split each line on whitespace
                String[] parts = line.split("\\s+");
                if (parts.length >= 2) {
                    try {
                        // extract first 5 digits of ZIP code
                        String zipCode = parts[0].trim();
                        if (zipCode.length() >= 5) {
                            zipCode = zipCode.substring(0, 5);
                        }
                        // parse population as integer
                        int population = Integer.parseInt(parts[1].trim());
                        // add to map (ZIP code -> population)
                        populationMap.put(zipCode, population);
                    } catch (NumberFormatException e) {
                        // handle invalid lines gracefully (skip them)
                        continue;
                    }
                }
            }
        }
        
        return populationMap;
    }
}


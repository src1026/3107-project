package datamanagement;

import java.io.*;
import java.util.*;
import common.Property;

/**
 * Reads property data from a CSV file.
 */
public class PropertyReader {
    
    /**
     * Reads properties from a CSV file.
     * The first row contains headers that indicate which columns contain
     * market_value, total_livable_area, and zip_code.
     */
    public static List<Property> readFromCSV(String filename) throws IOException {
        if (filename == null || filename.trim().isEmpty()) {
            throw new IllegalArgumentException("Filename cannot be null or empty");
        }
        
        List<Property> properties = new ArrayList<>();
        
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            // TODO: Read header row to find column indices for:
            //   - market_value
            //   - total_livable_area
            //   - zip_code
            String headerLine = reader.readLine();
            if (headerLine == null) {
                return properties;
            }
            
            String[] headers = parseCSVLine(headerLine);
            int marketValueIndex = -1;
            int totalLivableAreaIndex = -1;
            int zipCodeIndex = -1;
            
            // Find indices of required columns
            for (int i = 0; i < headers.length; i++) {
                String header = headers[i].trim().toLowerCase();
                if (header.equals("market_value")) {
                    marketValueIndex = i;
                } else if (header.equals("total_livable_area")) {
                    totalLivableAreaIndex = i;
                } else if (header.equals("zip_code")) {
                    zipCodeIndex = i;
                }
            }
            
            if (marketValueIndex == -1 || totalLivableAreaIndex == -1 || zipCodeIndex == -1) {
                throw new IOException("Required columns not found in CSV header");
            }
            
            // TODO: Read data rows and extract values from appropriate columns
            // TODO: Extract first 5 digits of ZIP code
            // TODO: Parse market_value and total_livable_area (use parseDouble helper)
            // TODO: Skip rows with empty ZIP codes
            // TODO: Create Property objects and add to list
            String line;
            while ((line = reader.readLine()) != null) {
                String[] fields = parseCSVLine(line);
                
                if (fields.length > Math.max(marketValueIndex, Math.max(totalLivableAreaIndex, zipCodeIndex))) {
                    String zipCode = fields[zipCodeIndex].trim();
                    // Extract first 5 digits of ZIP code
                    if (!zipCode.isEmpty() && zipCode.length() >= 5) {
                        zipCode = zipCode.substring(0, 5);
                    } else if (zipCode.isEmpty()) {
                        continue; // Skip if no ZIP code
                    }
                    
                    // Parse market value
                    Double marketValue = parseDouble(fields[marketValueIndex].trim());
                    
                    // Parse total livable area
                    Double totalLivableArea = parseDouble(fields[totalLivableAreaIndex].trim());
                    
                    properties.add(new Property(zipCode, marketValue, totalLivableArea));
                }
            }
        }
        
        return properties;
    }

    /**
     * Parses a string to a Double, returning null if invalid.
     * Invalid values include: empty strings, non-numeric, negative, or zero.
     */
    private static Double parseDouble(String value) {
        // TODO: Return null if value is null or empty
        if (value == null || value.trim().isEmpty()) {
            return null;
        }
        
        // TODO: Try to parse as double
        // TODO: Return the value if positive, otherwise return null
        try {
            double d = Double.parseDouble(value.trim());
            if (d > 0) {
                return d;
            }
        } catch (NumberFormatException e) {
            // Not a valid number
        }
        
        return null;
    }

    /**
     * Parses a CSV line, handling quoted fields.
     */
    private static String[] parseCSVLine(String line) {
        // TODO: Parse CSV line, handling quoted fields that may contain commas
        // TODO: Return array of field strings
        if (line == null) {
            return new String[0];
        }
        
        List<String> fields = new ArrayList<>();
        StringBuilder currentField = new StringBuilder();
        boolean inQuotes = false;
        
        for (int i = 0; i < line.length(); i++) {
            char c = line.charAt(i);
            
            if (c == '"') {
                inQuotes = !inQuotes;
            } else if (c == ',' && !inQuotes) {
                fields.add(currentField.toString());
                currentField = new StringBuilder();
            } else {
                currentField.append(c);
            }
        }
        fields.add(currentField.toString());
        
        return fields.toArray(new String[0]);
    }
}


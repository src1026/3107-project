package datamanagement;

import java.io.*;
import java.util.*;
import common.Property;

public class PropertyReader {
    public static List<Property> readFromCSV(String filename) throws IOException {
        if (filename == null || filename.trim().isEmpty()) {
            throw new IllegalArgumentException("Filename cannot be null or empty");
        }
        
        List<Property> properties = new ArrayList<>();
        
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            // read header row to find column indices for market_value, total_livable_area, and zip_code
            String headerLine = reader.readLine();
            if (headerLine == null) {
                return properties;
            }
            
            String[] headers = parseCSVLine(headerLine);
            int marketValueIndex = -1;
            int totalLivableAreaIndex = -1;
            int zipCodeIndex = -1;
            
            // find indices of required columns
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
            
            // read data rows and extract values from appropriate columns
            String line;
            while ((line = reader.readLine()) != null) {
                String[] fields = parseCSVLine(line);
                
                if (fields.length > Math.max(marketValueIndex, Math.max(totalLivableAreaIndex, zipCodeIndex))) {
                    String zipCode = fields[zipCodeIndex].trim();
                    // extract first 5 digits of ZIP code
                    if (!zipCode.isEmpty() && zipCode.length() >= 5) {
                        zipCode = zipCode.substring(0, 5);
                    } else if (zipCode.isEmpty()) {
                        // skip if no ZIP code
                        continue;
                    }
                    
                    // parse market value
                    Double marketValue = parseDouble(fields[marketValueIndex].trim());
                    // parse total livable area
                    Double totalLivableArea = parseDouble(fields[totalLivableAreaIndex].trim());
                    
                    properties.add(new Property(zipCode, marketValue, totalLivableArea));
                }
            }
        }
        
        return properties;
    }

    private static Double parseDouble(String value) {
        // return null if value is null or empty
        if (value == null || value.trim().isEmpty()) {
            return null;
        }
        
        // try to parse as double
        // return the value if positive, otherwise return null
        try {
            double d = Double.parseDouble(value.trim());
            if (d > 0) {
                return d;
            }
        } catch (NumberFormatException e) {
            // not a valid number
        }
        
        return null;
    }

    private static String[] parseCSVLine(String line) {
        // parse CSV line, handling quoted fields that may contain commas
        // return array of field strings
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


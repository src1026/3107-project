package datamanagement;

import java.io.*;
import java.util.*;
import common.ParkingViolation;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * Reads parking violations from CSV or JSON files.
 */
public class ParkingViolationReader {
    
    /**
     * Reads parking violations from a CSV file.
     * Each line contains 7 comma-separated fields:
     * timestamp, fine, description, vehicle_id, state, violation_id, zip_code
     */
    public static List<ParkingViolation> readFromCSV(String filename) throws IOException {
        // TODO: Read CSV file line by line
        // TODO: Parse each line into 7 fields
        // TODO: Extract first 5 digits of ZIP code (or set to null if empty)
        // TODO: Create ParkingViolation objects and add to list
        // TODO: Handle invalid lines gracefully (skip them)
        List<ParkingViolation> violations = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                try {
                    String[] fields = parseCSVLine(line);
                    
                    // Skip lines that don't have exactly 7 fields
                    if (fields.length != 7) {
                        continue;
                    }
                    
                    // Extract fields
                    String timestamp = fields[0].trim();
                    String fineStr = fields[1].trim();
                    String description = fields[2].trim();
                    String vehicleId = fields[3].trim();
                    String state = fields[4].trim();
                    String violationId = fields[5].trim();
                    String zipCode = fields[6].trim();
                    
                    // Parse fine
                    double fine;
                    try {
                        fine = Double.parseDouble(fineStr);
                    } catch (NumberFormatException e) {
                        continue; // Skip invalid fine values
                    }
                    
                    // Extract first 5 digits of ZIP code, or set to null if empty
                    String normalizedZipCode = null;
                    if (zipCode != null && !zipCode.isEmpty()) {
                        if (zipCode.length() >= 5) {
                            normalizedZipCode = zipCode.substring(0, 5);
                        } else {
                            normalizedZipCode = zipCode;
                        }
                    }
                    
                    // Create ParkingViolation object
                    ParkingViolation violation = new ParkingViolation(
                        timestamp, fine, description, vehicleId, state, violationId, normalizedZipCode
                    );
                    violations.add(violation);
                    
                } catch (Exception e) {
                    // Skip invalid lines gracefully
                    continue;
                }
            }
        }
        
        return violations;
    }

    /**
     * Reads parking violations from a JSON file.
     * Uses JSON.simple library to parse the file.
     */
    public static List<ParkingViolation> readFromJSON(String filename) throws IOException {
        // TODO: Use JSONParser to parse the JSON file
        // TODO: Iterate through JSON array of objects
        // TODO: Extract fields from each JSON object
        // TODO: Extract first 5 digits of ZIP code (or set to null if empty)
        // TODO: Create ParkingViolation objects and add to list
        // TODO: Handle invalid entries gracefully (skip them)
        List<ParkingViolation> violations = new ArrayList<>();
        
        try {
            JSONParser parser = new JSONParser();
            FileReader fileReader = new FileReader(filename);
            Object obj = parser.parse(fileReader);
            fileReader.close();
            
            // The JSON file should contain an array of objects
            if (obj instanceof JSONArray) {
                JSONArray jsonArray = (JSONArray) obj;
                
                for (Object item : jsonArray) {
                    try {
                        if (item instanceof JSONObject) {
                            JSONObject jsonObject = (JSONObject) item;
                            
                            // Extract fields from JSON object
                            String timestamp = getStringValue(jsonObject, "timestamp");
                            double fine = getDoubleValue(jsonObject, "fine");
                            String description = getStringValue(jsonObject, "description");
                            String vehicleId = getStringValue(jsonObject, "vehicle_id");
                            String state = getStringValue(jsonObject, "state");
                            String violationId = getStringValue(jsonObject, "violation_id");
                            String zipCode = getStringValue(jsonObject, "zip_code");
                            
                            // Extract first 5 digits of ZIP code, or set to null if empty
                            String normalizedZipCode = null;
                            if (zipCode != null && !zipCode.isEmpty()) {
                                if (zipCode.length() >= 5) {
                                    normalizedZipCode = zipCode.substring(0, 5);
                                } else {
                                    normalizedZipCode = zipCode;
                                }
                            }
                            
                            // Create ParkingViolation object
                            ParkingViolation violation = new ParkingViolation(
                                timestamp, fine, description, vehicleId, state, violationId, normalizedZipCode
                            );
                            violations.add(violation);
                        }
                    } catch (Exception e) {
                        // Skip invalid entries gracefully
                        continue;
                    }
                }
            }
        } catch (ParseException e) {
            throw new IOException("Error parsing JSON file: " + e.getMessage(), e);
        }
        
        return violations;
    }

    /**
     * Helper method to get a string value from a JSON object.
     */
    private static String getStringValue(JSONObject obj, String key) {
        Object value = obj.get(key);
        if (value == null) {
            return "";
        }
        return value.toString();
    }

    /**
     * Helper method to get a double value from a JSON object.
     */
    private static double getDoubleValue(JSONObject obj, String key) {
        Object value = obj.get(key);
        if (value == null) {
            return 0.0;
        }
        try {
            if (value instanceof Number) {
                return ((Number) value).doubleValue();
            } else {
                return Double.parseDouble(value.toString());
            }
        } catch (NumberFormatException e) {
            return 0.0;
        }
    }

    /**
     * Parses a CSV line, handling quoted fields.
     */
    private static String[] parseCSVLine(String line) {
        // TODO: Parse CSV line, handling quoted fields that may contain commas
        // TODO: Return array of field strings
        if (line == null || line.trim().isEmpty()) {
            return new String[0];
        }
        
        List<String> fields = new ArrayList<>();
        StringBuilder currentField = new StringBuilder();
        boolean inQuotes = false;
        
        for (int i = 0; i < line.length(); i++) {
            char c = line.charAt(i);
            
            if (c == '"') {
                // Check for escaped quotes (double quotes)
                if (i + 1 < line.length() && line.charAt(i + 1) == '"' && inQuotes) {
                    currentField.append('"');
                    i++; // Skip the next quote
                } else {
                    inQuotes = !inQuotes;
                }
            } else if (c == ',' && !inQuotes) {
                fields.add(currentField.toString());
                currentField = new StringBuilder();
            } else {
                currentField.append(c);
            }
        }
        
        // Add the last field
        fields.add(currentField.toString());
        
        return fields.toArray(new String[0]);
    }
}


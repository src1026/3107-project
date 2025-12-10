package datamanagement;

import java.io.*;
import java.util.*;
import common.ParkingViolation;

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
     * Uses simple string parsing to extract field values.
     */
    public static List<ParkingViolation> readFromJSON(String filename) throws IOException {
        // TODO: Use JSONParser to parse the JSON file
        // TODO: Iterate through JSON array of objects
        // TODO: Extract fields from each JSON object
        // TODO: Extract first 5 digits of ZIP code (or set to null if empty)
        // TODO: Create ParkingViolation objects and add to list
        // TODO: Handle invalid entries gracefully (skip them)
        List<ParkingViolation> violations = new ArrayList<>();
        
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            StringBuilder jsonContent = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                jsonContent.append(line);
            }
            
            String json = jsonContent.toString();
            
            // Find all JSON objects in the array
            int startIndex = 0;
            while ((startIndex = json.indexOf("{", startIndex)) != -1) {
                int endIndex = findMatchingBrace(json, startIndex);
                if (endIndex == -1) break;
                
                try {
                    String objStr = json.substring(startIndex, endIndex + 1);
                    
                    // Extract field values
                    String timestamp = extractJSONValue(objStr, "timestamp");
                    String fineStr = extractJSONValue(objStr, "fine");
                    String description = extractJSONValue(objStr, "description");
                    String vehicleId = extractJSONValue(objStr, "vehicle_id");
                    String state = extractJSONValue(objStr, "state");
                    String violationId = extractJSONValue(objStr, "violation_id");
                    String zipCode = extractJSONValue(objStr, "zip_code");
                    
                    // Parse fine
                    double fine;
                    try {
                        fine = Double.parseDouble(fineStr);
                    } catch (NumberFormatException e) {
                        startIndex = endIndex + 1;
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
                    // Skip invalid entries gracefully
                }
                
                startIndex = endIndex + 1;
            }
        }
        
        return violations;
    }

    /**
     * Finds the matching closing brace for an opening brace.
     */
    private static int findMatchingBrace(String str, int start) {
        int depth = 0;
        boolean inString = false;
        boolean escaped = false;
        
        for (int i = start; i < str.length(); i++) {
            char c = str.charAt(i);
            
            if (escaped) {
                escaped = false;
                continue;
            }
            
            if (c == '\\') {
                escaped = true;
                continue;
            }
            
            if (c == '"') {
                inString = !inString;
                continue;
            }
            
            if (inString) {
                continue;
            }
            
            if (c == '{') {
                depth++;
            } else if (c == '}') {
                depth--;
                if (depth == 0) {
                    return i;
                }
            }
        }
        
        return -1;
    }

    /**
     * Extracts a value from a JSON object string for a given key.
     */
    private static String extractJSONValue(String jsonObj, String key) {
        String pattern = "\"" + key + "\"\\s*:\\s*";
        int keyIndex = jsonObj.indexOf(pattern);
        if (keyIndex == -1) {
            return "";
        }
        
        int valueStart = keyIndex + pattern.length();
        
        // Check if value is a quoted string
        if (valueStart < jsonObj.length() && jsonObj.charAt(valueStart) == '"') {
            int quoteStart = valueStart + 1;
            int quoteEnd = quoteStart;
            while (quoteEnd < jsonObj.length()) {
                if (jsonObj.charAt(quoteEnd) == '"' && (quoteEnd == quoteStart || jsonObj.charAt(quoteEnd - 1) != '\\')) {
                    break;
                }
                quoteEnd++;
            }
            if (quoteEnd < jsonObj.length()) {
                return unescapeJSONString(jsonObj.substring(quoteStart, quoteEnd));
            }
        } else {
            // Value is a number - find the end (comma or closing brace)
            int valueEnd = valueStart;
            while (valueEnd < jsonObj.length()) {
                char c = jsonObj.charAt(valueEnd);
                if (c == ',' || c == '}') {
                    break;
                }
                valueEnd++;
            }
            return jsonObj.substring(valueStart, valueEnd).trim();
        }
        
        return "";
    }

    /**
     * Unescapes JSON string values.
     */
    private static String unescapeJSONString(String str) {
        return str.replace("\\\"", "\"")
                  .replace("\\\\", "\\")
                  .replace("\\n", "\n")
                  .replace("\\r", "\r")
                  .replace("\\t", "\t");
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


import java.io.*;
import java.util.*;

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
        return new ArrayList<>();
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
        return new ArrayList<>();
    }

    /**
     * Parses a CSV line, handling quoted fields.
     */
    private static String[] parseCSVLine(String line) {
        // TODO: Parse CSV line, handling quoted fields that may contain commas
        // TODO: Return array of field strings
        return new String[0];
    }
}


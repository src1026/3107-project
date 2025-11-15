import java.io.*;
import java.util.*;

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
        // TODO: Read header row to find column indices for:
        //   - market_value
        //   - total_livable_area
        //   - zip_code
        // TODO: Read data rows and extract values from appropriate columns
        // TODO: Extract first 5 digits of ZIP code
        // TODO: Parse market_value and total_livable_area (use parseDouble helper)
        // TODO: Skip rows with empty ZIP codes
        // TODO: Create Property objects and add to list
        return new ArrayList<>();
    }

    /**
     * Parses a string to a Double, returning null if invalid.
     * Invalid values include: empty strings, non-numeric, negative, or zero.
     */
    private static Double parseDouble(String value) {
        // TODO: Return null if value is null or empty
        // TODO: Try to parse as double
        // TODO: Return the value if positive, otherwise return null
        return null;
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


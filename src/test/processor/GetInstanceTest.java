package test.processor;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.util.*;
import processor.DataProcessor;
import common.ParkingViolation;
import common.Property;

/**
 * Test class for DataProcessor.getInstance() method (no parameters version).
 * One test method per test case to achieve 100% statement coverage.
 */
public class GetInstanceTest {

    @Test
    public void testGetInstanceWhenNotInitialized() {
        // Test case: getInstance() should return null when instance is not initialized
        DataProcessor.resetInstance();
        DataProcessor result = DataProcessor.getInstance();
        assertNull(result);
    }

    @Test
    public void testGetInstanceWhenInitialized() {
        // Test case: getInstance() should return the singleton instance when initialized
        DataProcessor.resetInstance();
        List<ParkingViolation> violations = new ArrayList<>();
        List<Property> properties = new ArrayList<>();
        Map<String, Integer> population = new HashMap<>();
        
        DataProcessor.getInstance(violations, properties, population);
        DataProcessor result = DataProcessor.getInstance();
        
        assertNotNull(result);
        assertSame(DataProcessor.getInstance(), result); // Should be same instance
    }
}


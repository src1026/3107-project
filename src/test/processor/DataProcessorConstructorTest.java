package test.processor;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.util.*;
import processor.DataProcessor;
import common.ParkingViolation;
import common.Property;

/**
 * Test class for DataProcessor constructor.
 * One test method per test case to achieve 100% statement coverage.
 */
public class DataProcessorConstructorTest {

    @Test
    public void testConstructorWithValidParameters() {
        // Test case: Valid parameters should create instance
        DataProcessor.resetInstance();
        List<ParkingViolation> violations = new ArrayList<>();
        List<Property> properties = new ArrayList<>();
        Map<String, Integer> population = new HashMap<>();
        
        DataProcessor processor = DataProcessor.getInstance(violations, properties, population);
        assertNotNull(processor);
    }

    @Test
    public void testConstructorWithNullViolations() {
        // Test case: Null violations list should throw exception
        DataProcessor.resetInstance();
        List<Property> properties = new ArrayList<>();
        Map<String, Integer> population = new HashMap<>();
        
        assertThrows(IllegalArgumentException.class, () -> {
            DataProcessor.getInstance(null, properties, population);
        });
    }

    @Test
    public void testConstructorWithNullProperties() {
        // Test case: Null properties list should throw exception
        DataProcessor.resetInstance();
        List<ParkingViolation> violations = new ArrayList<>();
        Map<String, Integer> population = new HashMap<>();
        
        assertThrows(IllegalArgumentException.class, () -> {
            DataProcessor.getInstance(violations, null, population);
        });
    }

    @Test
    public void testConstructorWithNullPopulation() {
        // Test case: Null population map should throw exception
        DataProcessor.resetInstance();
        List<ParkingViolation> violations = new ArrayList<>();
        List<Property> properties = new ArrayList<>();
        
        assertThrows(IllegalArgumentException.class, () -> {
            DataProcessor.getInstance(violations, properties, null);
        });
    }

    @Test
    public void testGetInstanceWhenInstanceAlreadyExists() {
        // Test case: getInstance() should return existing instance when called again
        // This tests the branch: if (instance == null) - false branch
        DataProcessor.resetInstance();
        List<ParkingViolation> violations1 = new ArrayList<>();
        List<Property> properties1 = new ArrayList<>();
        Map<String, Integer> population1 = new HashMap<>();
        
        DataProcessor processor1 = DataProcessor.getInstance(violations1, properties1, population1);
        
        // Call getInstance again with different parameters
        // Should return the same instance, not create a new one
        List<ParkingViolation> violations2 = new ArrayList<>();
        List<Property> properties2 = new ArrayList<>();
        Map<String, Integer> population2 = new HashMap<>();
        population2.put("19103", 1000);
        
        DataProcessor processor2 = DataProcessor.getInstance(violations2, properties2, population2);
        
        // Should be the same instance (singleton pattern)
        assertSame(processor1, processor2);
    }
}


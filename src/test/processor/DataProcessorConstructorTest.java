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
}


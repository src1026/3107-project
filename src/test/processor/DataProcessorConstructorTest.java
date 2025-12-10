package test.processor;

import static org.junit.Assert.*;
import org.junit.Test;
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
        List<ParkingViolation> violations = new ArrayList<>();
        List<Property> properties = new ArrayList<>();
        Map<String, Integer> population = new HashMap<>();
        
        DataProcessor processor = new DataProcessor(violations, properties, population);
        assertNotNull(processor);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConstructorWithNullViolations() {
        // Test case: Null violations list should throw exception
        List<Property> properties = new ArrayList<>();
        Map<String, Integer> population = new HashMap<>();
        
        new DataProcessor(null, properties, population);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConstructorWithNullProperties() {
        // Test case: Null properties list should throw exception
        List<ParkingViolation> violations = new ArrayList<>();
        Map<String, Integer> population = new HashMap<>();
        
        new DataProcessor(violations, null, population);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConstructorWithNullPopulation() {
        // Test case: Null population map should throw exception
        List<ParkingViolation> violations = new ArrayList<>();
        List<Property> properties = new ArrayList<>();
        
        new DataProcessor(violations, properties, null);
    }
}


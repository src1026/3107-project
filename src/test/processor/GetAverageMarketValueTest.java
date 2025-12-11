package test.processor;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.util.*;
import processor.DataProcessor;
import common.ParkingViolation;
import common.Property;

/**
 * Test class for DataProcessor.getAverageMarketValue() method.
 * One test method per test case to achieve 100% statement coverage.
 */
public class GetAverageMarketValueTest {

    @Test
    public void testGetAverageMarketValueWithValidProperties() {
        // Test case: Single property with valid market value
        List<ParkingViolation> violations = new ArrayList<>();
        List<Property> properties = new ArrayList<>();
        properties.add(new Property("19103", 100000.0, 1000.0));
        
        Map<String, Integer> population = new HashMap<>();
        
        DataProcessor.resetInstance();
        DataProcessor processor = DataProcessor.getInstance(violations, properties, population);
        int result = processor.getAverageMarketValue("19103");
        
        assertEquals(100000, result);
    }

    @Test
    public void testGetAverageMarketValueWithMultipleProperties() {
        // Test case: Multiple properties, calculate average
        List<ParkingViolation> violations = new ArrayList<>();
        List<Property> properties = new ArrayList<>();
        properties.add(new Property("19103", 100000.0, 1000.0));
        properties.add(new Property("19103", 200000.0, 2000.0));
        properties.add(new Property("19103", 300000.0, 3000.0));
        
        Map<String, Integer> population = new HashMap<>();
        
        DataProcessor.resetInstance();
        DataProcessor processor = DataProcessor.getInstance(violations, properties, population);
        int result = processor.getAverageMarketValue("19103");
        
        assertEquals(200000, result);
    }

    @Test
    public void testGetAverageMarketValueWithNoProperties() {
        // Test case: No properties for ZIP code
        List<ParkingViolation> violations = new ArrayList<>();
        List<Property> properties = new ArrayList<>();
        properties.add(new Property("19104", 100000.0, 1000.0));
        
        Map<String, Integer> population = new HashMap<>();
        
        DataProcessor.resetInstance();
        DataProcessor processor = DataProcessor.getInstance(violations, properties, population);
        int result = processor.getAverageMarketValue("19103");
        
        assertEquals(0, result);
    }

    @Test
    public void testGetAverageMarketValueWithInvalidMarketValue() {
        // Test case: Properties with invalid market values should be ignored
        List<ParkingViolation> violations = new ArrayList<>();
        List<Property> properties = new ArrayList<>();
        properties.add(new Property("19103", null, 1000.0));
        properties.add(new Property("19103", 0.0, 2000.0));
        properties.add(new Property("19103", -1000.0, 3000.0));
        properties.add(new Property("19103", 100000.0, 4000.0));
        
        Map<String, Integer> population = new HashMap<>();
        
        DataProcessor.resetInstance();
        DataProcessor processor = DataProcessor.getInstance(violations, properties, population);
        int result = processor.getAverageMarketValue("19103");
        
        assertEquals(100000, result);
    }

    @Test
    public void testGetAverageMarketValueWithRoundedAverage() {
        // Test case: Average that needs rounding
        List<ParkingViolation> violations = new ArrayList<>();
        List<Property> properties = new ArrayList<>();
        properties.add(new Property("19103", 100000.0, 1000.0));
        properties.add(new Property("19103", 100001.0, 2000.0));
        
        Map<String, Integer> population = new HashMap<>();
        
        DataProcessor.resetInstance();
        DataProcessor processor = DataProcessor.getInstance(violations, properties, population);
        int result = processor.getAverageMarketValue("19103");
        
        assertEquals(100001, result);
    }

    @Test
    public void testGetAverageMarketValueWithNullProperty() {
        // Test case: Null properties should be handled gracefully
        List<ParkingViolation> violations = new ArrayList<>();
        List<Property> properties = new ArrayList<>();
        properties.add(null);
        properties.add(new Property("19103", 100000.0, 1000.0));
        
        Map<String, Integer> population = new HashMap<>();
        
        DataProcessor.resetInstance();
        DataProcessor processor = DataProcessor.getInstance(violations, properties, population);
        int result = processor.getAverageMarketValue("19103");
        
        assertEquals(100000, result);
    }

    @Test
    public void testGetAverageMarketValueWithNullZipCode() {
        // Test case: Null ZIP code should throw exception
        List<ParkingViolation> violations = new ArrayList<>();
        List<Property> properties = new ArrayList<>();
        Map<String, Integer> population = new HashMap<>();
        
        DataProcessor.resetInstance();
        DataProcessor processor = DataProcessor.getInstance(violations, properties, population);
        assertThrows(IllegalArgumentException.class, () -> {
            processor.getAverageMarketValue(null);
        });
    }

    @Test
    public void testGetAverageMarketValueWithMemoizationCache() {
        // Test case: Second call to same ZIP code should return cached result
        List<ParkingViolation> violations = new ArrayList<>();
        List<Property> properties = new ArrayList<>();
        properties.add(new Property("19103", 100000.0, 1000.0));
        
        Map<String, Integer> population = new HashMap<>();
        
        DataProcessor.resetInstance();
        DataProcessor processor = DataProcessor.getInstance(violations, properties, population);
        
        // First call - should calculate and cache
        int result1 = processor.getAverageMarketValue("19103");
        assertEquals(100000, result1);
        
        // Modify properties to verify cache is used
        properties.clear();
        properties.add(new Property("19103", 999999.0, 1000.0));
        
        // Second call - should return cached value, not recalculate
        int result2 = processor.getAverageMarketValue("19103");
        assertEquals(100000, result2); // Should be cached value, not new value
    }
}


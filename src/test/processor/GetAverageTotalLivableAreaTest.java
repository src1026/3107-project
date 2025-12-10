package test.processor;

import static org.junit.Assert.*;
import org.junit.Test;
import java.util.*;
import processor.DataProcessor;
import common.ParkingViolation;
import common.Property;

/**
 * Test class for DataProcessor.getAverageTotalLivableArea() method.
 * One test method per test case to achieve 100% statement coverage.
 */
public class GetAverageTotalLivableAreaTest {

    @Test
    public void testGetAverageTotalLivableAreaWithValidProperties() {
        // Test case: Single property with valid total livable area
        List<ParkingViolation> violations = new ArrayList<>();
        List<Property> properties = new ArrayList<>();
        properties.add(new Property("19103", 100000.0, 1000.0));
        
        Map<String, Integer> population = new HashMap<>();
        
        DataProcessor processor = new DataProcessor(violations, properties, population);
        int result = processor.getAverageTotalLivableArea("19103");
        
        assertEquals(1000, result);
    }

    @Test
    public void testGetAverageTotalLivableAreaWithMultipleProperties() {
        // Test case: Multiple properties, calculate average
        List<ParkingViolation> violations = new ArrayList<>();
        List<Property> properties = new ArrayList<>();
        properties.add(new Property("19103", 100000.0, 1000.0));
        properties.add(new Property("19103", 200000.0, 2000.0));
        properties.add(new Property("19103", 300000.0, 3000.0));
        
        Map<String, Integer> population = new HashMap<>();
        
        DataProcessor processor = new DataProcessor(violations, properties, population);
        int result = processor.getAverageTotalLivableArea("19103");
        
        assertEquals(2000, result);
    }

    @Test
    public void testGetAverageTotalLivableAreaWithNoProperties() {
        // Test case: No properties for ZIP code
        List<ParkingViolation> violations = new ArrayList<>();
        List<Property> properties = new ArrayList<>();
        properties.add(new Property("19104", 100000.0, 1000.0));
        
        Map<String, Integer> population = new HashMap<>();
        
        DataProcessor processor = new DataProcessor(violations, properties, population);
        int result = processor.getAverageTotalLivableArea("19103");
        
        assertEquals(0, result);
    }

    @Test
    public void testGetAverageTotalLivableAreaWithInvalidArea() {
        // Test case: Properties with invalid total livable areas should be ignored
        List<ParkingViolation> violations = new ArrayList<>();
        List<Property> properties = new ArrayList<>();
        properties.add(new Property("19103", 100000.0, null));
        properties.add(new Property("19103", 200000.0, 0.0));
        properties.add(new Property("19103", 300000.0, -1000.0));
        properties.add(new Property("19103", 400000.0, 1000.0));
        
        Map<String, Integer> population = new HashMap<>();
        
        DataProcessor processor = new DataProcessor(violations, properties, population);
        int result = processor.getAverageTotalLivableArea("19103");
        
        assertEquals(1000, result);
    }

    @Test
    public void testGetAverageTotalLivableAreaWithRoundedAverage() {
        // Test case: Average that needs rounding
        List<ParkingViolation> violations = new ArrayList<>();
        List<Property> properties = new ArrayList<>();
        properties.add(new Property("19103", 100000.0, 1000.0));
        properties.add(new Property("19103", 200000.0, 1001.0));
        
        Map<String, Integer> population = new HashMap<>();
        
        DataProcessor processor = new DataProcessor(violations, properties, population);
        int result = processor.getAverageTotalLivableArea("19103");
        
        assertEquals(1001, result);
    }

    @Test
    public void testGetAverageTotalLivableAreaWithNullProperty() {
        // Test case: Null properties should be handled gracefully
        List<ParkingViolation> violations = new ArrayList<>();
        List<Property> properties = new ArrayList<>();
        properties.add(null);
        properties.add(new Property("19103", 100000.0, 1000.0));
        
        Map<String, Integer> population = new HashMap<>();
        
        DataProcessor processor = new DataProcessor(violations, properties, population);
        int result = processor.getAverageTotalLivableArea("19103");
        
        assertEquals(1000, result);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetAverageTotalLivableAreaWithNullZipCode() {
        // Test case: Null ZIP code should throw exception
        List<ParkingViolation> violations = new ArrayList<>();
        List<Property> properties = new ArrayList<>();
        Map<String, Integer> population = new HashMap<>();
        
        DataProcessor processor = new DataProcessor(violations, properties, population);
        processor.getAverageTotalLivableArea(null);
    }
}


package test.processor;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.util.*;
import processor.DataProcessor;
import common.ParkingViolation;
import common.Property;

public class GetAverageTotalLivableAreaTest {

    @Test
    public void testGetAverageTotalLivableAreaWithValidProperties() {
        //    Single property with valid total livable area
        List<ParkingViolation> violations = new ArrayList<>();
        List<Property> properties = new ArrayList<>();
        properties.add(new Property("19103", 100000.0, 1000.0));
        
        Map<String, Integer> population = new HashMap<>();
        
        DataProcessor.resetInstance();
        DataProcessor processor = DataProcessor.getInstance(violations, properties, population);
        int result = processor.getAverageTotalLivableArea("19103");
        
        assertEquals(1000, result);
    }

    @Test
    public void testGetAverageTotalLivableAreaWithMultipleProperties() {
        //    Multiple properties, calculate average
        List<ParkingViolation> violations = new ArrayList<>();
        List<Property> properties = new ArrayList<>();
        properties.add(new Property("19103", 100000.0, 1000.0));
        properties.add(new Property("19103", 200000.0, 2000.0));
        properties.add(new Property("19103", 300000.0, 3000.0));
        
        Map<String, Integer> population = new HashMap<>();
        
        DataProcessor.resetInstance();
        DataProcessor processor = DataProcessor.getInstance(violations, properties, population);
        int result = processor.getAverageTotalLivableArea("19103");
        
        assertEquals(2000, result);
    }

    @Test
    public void testGetAverageTotalLivableAreaWithNoProperties() {
        //    No properties for ZIP code
        List<ParkingViolation> violations = new ArrayList<>();
        List<Property> properties = new ArrayList<>();
        properties.add(new Property("19104", 100000.0, 1000.0));
        
        Map<String, Integer> population = new HashMap<>();
        
        DataProcessor.resetInstance();
        DataProcessor processor = DataProcessor.getInstance(violations, properties, population);
        int result = processor.getAverageTotalLivableArea("19103");
        
        assertEquals(0, result);
    }

    @Test
    public void testGetAverageTotalLivableAreaWithInvalidArea() {
        //    Properties with invalid total livable areas should be ignored
        List<ParkingViolation> violations = new ArrayList<>();
        List<Property> properties = new ArrayList<>();
        properties.add(new Property("19103", 100000.0, null));
        properties.add(new Property("19103", 200000.0, 0.0));
        properties.add(new Property("19103", 300000.0, -1000.0));
        properties.add(new Property("19103", 400000.0, 1000.0));
        
        Map<String, Integer> population = new HashMap<>();
        
        DataProcessor.resetInstance();
        DataProcessor processor = DataProcessor.getInstance(violations, properties, population);
        int result = processor.getAverageTotalLivableArea("19103");
        
        assertEquals(1000, result);
    }

    @Test
    public void testGetAverageTotalLivableAreaWithRoundedAverage() {
        //    Average that needs rounding
        List<ParkingViolation> violations = new ArrayList<>();
        List<Property> properties = new ArrayList<>();
        properties.add(new Property("19103", 100000.0, 1000.0));
        properties.add(new Property("19103", 200000.0, 1001.0));
        
        Map<String, Integer> population = new HashMap<>();
        
        DataProcessor.resetInstance();
        DataProcessor processor = DataProcessor.getInstance(violations, properties, population);
        int result = processor.getAverageTotalLivableArea("19103");
        
        assertEquals(1001, result);
    }

    @Test
    public void testGetAverageTotalLivableAreaWithNullProperty() {
        //    Null properties should be handled gracefully
        List<ParkingViolation> violations = new ArrayList<>();
        List<Property> properties = new ArrayList<>();
        properties.add(null);
        properties.add(new Property("19103", 100000.0, 1000.0));
        
        Map<String, Integer> population = new HashMap<>();
        
        DataProcessor.resetInstance();
        DataProcessor processor = DataProcessor.getInstance(violations, properties, population);
        int result = processor.getAverageTotalLivableArea("19103");
        
        assertEquals(1000, result);
    }

    @Test
    public void testGetAverageTotalLivableAreaWithNullZipCode() {
        //    Null ZIP code should throw exception
        List<ParkingViolation> violations = new ArrayList<>();
        List<Property> properties = new ArrayList<>();
        Map<String, Integer> population = new HashMap<>();
        
        DataProcessor.resetInstance();
        DataProcessor processor = DataProcessor.getInstance(violations, properties, population);
        assertThrows(IllegalArgumentException.class, () -> {
            processor.getAverageTotalLivableArea(null);
        });
    }

    @Test
    public void testGetAverageTotalLivableAreaWithMemoizationCache() {
        //    Second call to same ZIP code should return cached result
        List<ParkingViolation> violations = new ArrayList<>();
        List<Property> properties = new ArrayList<>();
        properties.add(new Property("19103", 100000.0, 1000.0));
        
        Map<String, Integer> population = new HashMap<>();
        
        DataProcessor.resetInstance();
        DataProcessor processor = DataProcessor.getInstance(violations, properties, population);
        
        // First call - should calculate and cache
        int result1 = processor.getAverageTotalLivableArea("19103");
        assertEquals(1000, result1);
        
        // Modify properties to verify cache is used
        properties.clear();
        properties.add(new Property("19103", 100000.0, 9999.0));
        
        // Second call - should return cached value, not recalculate
        int result2 = processor.getAverageTotalLivableArea("19103");
        assertEquals(1000, result2); // Should be cached value, not new value
    }
}


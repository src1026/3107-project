package test.processor;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.util.*;
import processor.DataProcessor;
import common.ParkingViolation;
import common.Property;
import java.util.function.Function;

/**
 * Test class for DataProcessor.processZipCodes() method.
 * One test method per test case to achieve 100% statement coverage.
 */
public class ProcessZipCodesTest {

    @Test
    public void testProcessZipCodesWithSingleZipCode() {
        // Test case: Single ZIP code
        List<ParkingViolation> violations = new ArrayList<>();
        List<Property> properties = new ArrayList<>();
        properties.add(new Property("19103", 100000.0, 1000.0));
        Map<String, Integer> population = new HashMap<>();
        
        DataProcessor.resetInstance();
        DataProcessor processor = DataProcessor.getInstance(violations, properties, population);
        
        Function<String, Integer> calculator = processor::getAverageMarketValue;
        Map<String, Integer> result = processor.processZipCodes(calculator, "19103");
        
        assertEquals(1, result.size());
        assertTrue(result.containsKey("19103"));
        assertEquals(100000, result.get("19103"));
    }

    @Test
    public void testProcessZipCodesWithMultipleZipCodes() {
        // Test case: Multiple ZIP codes
        List<ParkingViolation> violations = new ArrayList<>();
        List<Property> properties = new ArrayList<>();
        properties.add(new Property("19103", 100000.0, 1000.0));
        properties.add(new Property("19104", 200000.0, 2000.0));
        Map<String, Integer> population = new HashMap<>();
        
        DataProcessor.resetInstance();
        DataProcessor processor = DataProcessor.getInstance(violations, properties, population);
        
        Function<String, Integer> calculator = processor::getAverageMarketValue;
        Map<String, Integer> result = processor.processZipCodes(calculator, "19103", "19104");
        
        assertEquals(2, result.size());
        assertEquals(100000, result.get("19103"));
        assertEquals(200000, result.get("19104"));
    }

    @Test
    public void testProcessZipCodesWithNullZipCode() {
        // Test case: Null ZIP code should be skipped
        List<ParkingViolation> violations = new ArrayList<>();
        List<Property> properties = new ArrayList<>();
        properties.add(new Property("19103", 100000.0, 1000.0));
        Map<String, Integer> population = new HashMap<>();
        
        DataProcessor.resetInstance();
        DataProcessor processor = DataProcessor.getInstance(violations, properties, population);
        
        Function<String, Integer> calculator = processor::getAverageMarketValue;
        Map<String, Integer> result = processor.processZipCodes(calculator, "19103", null, "19104");
        
        assertEquals(2, result.size());
        assertTrue(result.containsKey("19103"));
        assertTrue(result.containsKey("19104"));
        assertFalse(result.containsKey(null));
    }

    @Test
    public void testProcessZipCodesWithLongZipCode() {
        // Test case: ZIP code longer than 5 digits should be normalized
        List<ParkingViolation> violations = new ArrayList<>();
        List<Property> properties = new ArrayList<>();
        properties.add(new Property("19103", 100000.0, 1000.0));
        Map<String, Integer> population = new HashMap<>();
        
        DataProcessor.resetInstance();
        DataProcessor processor = DataProcessor.getInstance(violations, properties, population);
        
        Function<String, Integer> calculator = processor::getAverageMarketValue;
        Map<String, Integer> result = processor.processZipCodes(calculator, "19103-1234");
        
        assertEquals(1, result.size());
        assertTrue(result.containsKey("19103"));
        assertEquals(100000, result.get("19103"));
    }

    @Test
    public void testProcessZipCodesWithShortZipCode() {
        // Test case: ZIP code shorter than 5 digits should be used as-is
        List<ParkingViolation> violations = new ArrayList<>();
        List<Property> properties = new ArrayList<>();
        properties.add(new Property("191", 100000.0, 1000.0));
        Map<String, Integer> population = new HashMap<>();
        
        DataProcessor.resetInstance();
        DataProcessor processor = DataProcessor.getInstance(violations, properties, population);
        
        Function<String, Integer> calculator = processor::getAverageMarketValue;
        Map<String, Integer> result = processor.processZipCodes(calculator, "191");
        
        assertEquals(1, result.size());
        assertTrue(result.containsKey("191"));
        assertEquals(100000, result.get("191"));
    }

    @Test
    public void testProcessZipCodesWithEmptyArray() {
        // Test case: Empty array should return empty map
        List<ParkingViolation> violations = new ArrayList<>();
        List<Property> properties = new ArrayList<>();
        Map<String, Integer> population = new HashMap<>();
        
        DataProcessor.resetInstance();
        DataProcessor processor = DataProcessor.getInstance(violations, properties, population);
        
        Function<String, Integer> calculator = processor::getAverageMarketValue;
        Map<String, Integer> result = processor.processZipCodes(calculator);
        
        assertTrue(result.isEmpty());
    }

    @Test
    public void testProcessZipCodesPreservesOrder() {
        // Test case: Results should preserve insertion order (LinkedHashMap)
        List<ParkingViolation> violations = new ArrayList<>();
        List<Property> properties = new ArrayList<>();
        properties.add(new Property("19103", 100000.0, 1000.0));
        properties.add(new Property("19104", 200000.0, 2000.0));
        properties.add(new Property("19105", 300000.0, 3000.0));
        Map<String, Integer> population = new HashMap<>();
        
        DataProcessor.resetInstance();
        DataProcessor processor = DataProcessor.getInstance(violations, properties, population);
        
        Function<String, Integer> calculator = processor::getAverageMarketValue;
        Map<String, Integer> result = processor.processZipCodes(calculator, "19105", "19103", "19104");
        
        // Check order is preserved
        List<String> keys = new ArrayList<>(result.keySet());
        assertEquals("19105", keys.get(0));
        assertEquals("19103", keys.get(1));
        assertEquals("19104", keys.get(2));
    }
}


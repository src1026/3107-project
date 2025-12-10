package test.processor;

import static org.junit.Assert.*;
import org.junit.Test;
import java.util.*;
import processor.DataProcessor;
import common.ParkingViolation;
import common.Property;

/**
 * Test class for DataProcessor.getTotalPopulation() method.
 * One test method per test case to achieve 100% statement coverage.
 */
public class GetTotalPopulationTest {

    @Test
    public void testGetTotalPopulationWithSingleZipCode() {
        // Test case: Single ZIP code with population
        List<ParkingViolation> violations = new ArrayList<>();
        List<Property> properties = new ArrayList<>();
        Map<String, Integer> population = new HashMap<>();
        population.put("19103", 5000);
        
        DataProcessor processor = new DataProcessor(violations, properties, population);
        int result = processor.getTotalPopulation();
        
        assertEquals(5000, result);
    }

    @Test
    public void testGetTotalPopulationWithMultipleZipCodes() {
        // Test case: Multiple ZIP codes
        List<ParkingViolation> violations = new ArrayList<>();
        List<Property> properties = new ArrayList<>();
        Map<String, Integer> population = new HashMap<>();
        population.put("19103", 5000);
        population.put("19104", 3000);
        population.put("19105", 2000);
        
        DataProcessor processor = new DataProcessor(violations, properties, population);
        int result = processor.getTotalPopulation();
        
        assertEquals(10000, result);
    }

    @Test
    public void testGetTotalPopulationWithEmptyMap() {
        // Test case: Empty population map
        List<ParkingViolation> violations = new ArrayList<>();
        List<Property> properties = new ArrayList<>();
        Map<String, Integer> population = new HashMap<>();
        
        DataProcessor processor = new DataProcessor(violations, properties, population);
        int result = processor.getTotalPopulation();
        
        assertEquals(0, result);
    }

    @Test
    public void testGetTotalPopulationWithZeroPopulation() {
        // Test case: ZIP code with zero population
        List<ParkingViolation> violations = new ArrayList<>();
        List<Property> properties = new ArrayList<>();
        Map<String, Integer> population = new HashMap<>();
        population.put("19103", 0);
        population.put("19104", 1000);
        
        DataProcessor processor = new DataProcessor(violations, properties, population);
        int result = processor.getTotalPopulation();
        
        assertEquals(1000, result);
    }
}


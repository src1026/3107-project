package test.processor;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.util.*;
import processor.DataProcessor;
import common.ParkingViolation;
import common.Property;

public class GetTotalPopulationTest {

    @Test
    public void testGetTotalPopulationWithSingleZipCode() {
        // Single ZIP code with population
        DataProcessor.resetInstance();
        List<ParkingViolation> violations = new ArrayList<>();
        List<Property> properties = new ArrayList<>();
        Map<String, Integer> population = new HashMap<>();
        population.put("19103", 5000);
        
        DataProcessor processor = DataProcessor.getInstance(violations, properties, population);
        int result = processor.getTotalPopulation();
        
        assertEquals(5000, result);
    }

    @Test
    public void testGetTotalPopulationWithMultipleZipCodes() {
        // Multiple ZIP codes
        DataProcessor.resetInstance();
        List<ParkingViolation> violations = new ArrayList<>();
        List<Property> properties = new ArrayList<>();
        Map<String, Integer> population = new HashMap<>();
        population.put("19103", 5000);
        population.put("19104", 3000);
        population.put("19105", 2000);
        
        DataProcessor processor = DataProcessor.getInstance(violations, properties, population);
        int result = processor.getTotalPopulation();
        
        assertEquals(10000, result);
    }

    @Test
    public void testGetTotalPopulationWithEmptyMap() {
        // Empty population map
        DataProcessor.resetInstance();
        List<ParkingViolation> violations = new ArrayList<>();
        List<Property> properties = new ArrayList<>();
        Map<String, Integer> population = new HashMap<>();
        
        DataProcessor processor = DataProcessor.getInstance(violations, properties, population);
        int result = processor.getTotalPopulation();
        
        assertEquals(0, result);
    }

    @Test
    public void testGetTotalPopulationWithZeroPopulation() {
        // ZIP code with zero population
        DataProcessor.resetInstance();
        List<ParkingViolation> violations = new ArrayList<>();
        List<Property> properties = new ArrayList<>();
        Map<String, Integer> population = new HashMap<>();
        population.put("19103", 0);
        population.put("19104", 1000);
        
        DataProcessor processor = DataProcessor.getInstance(violations, properties, population);
        int result = processor.getTotalPopulation();
        
        assertEquals(1000, result);
    }
}


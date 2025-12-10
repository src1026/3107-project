package test.processor;

import static org.junit.Assert.*;
import org.junit.Test;
import java.util.*;
import processor.DataProcessor;
import common.ParkingViolation;
import common.Property;

/**
 * Test class for DataProcessor.getMarketValuePerCapita() method.
 * One test method per test case to achieve 100% statement coverage.
 */
public class GetMarketValuePerCapitaTest {

    @Test
    public void testGetMarketValuePerCapitaWithValidData() {
        // Test case: Valid properties and population
        List<ParkingViolation> violations = new ArrayList<>();
        List<Property> properties = new ArrayList<>();
        properties.add(new Property("19103", 100000.0, 1000.0));
        properties.add(new Property("19103", 200000.0, 2000.0));
        
        Map<String, Integer> population = new HashMap<>();
        population.put("19103", 2);
        
        DataProcessor processor = new DataProcessor(violations, properties, population);
        int result = processor.getMarketValuePerCapita("19103");
        
        assertEquals(150000, result);
    }

    @Test
    public void testGetMarketValuePerCapitaWithNoProperties() {
        // Test case: No properties for ZIP code
        List<ParkingViolation> violations = new ArrayList<>();
        List<Property> properties = new ArrayList<>();
        properties.add(new Property("19104", 100000.0, 1000.0));
        
        Map<String, Integer> population = new HashMap<>();
        population.put("19103", 1000);
        
        DataProcessor processor = new DataProcessor(violations, properties, population);
        int result = processor.getMarketValuePerCapita("19103");
        
        assertEquals(0, result);
    }

    @Test
    public void testGetMarketValuePerCapitaWithMissingPopulation() {
        // Test case: ZIP code not in population map
        List<ParkingViolation> violations = new ArrayList<>();
        List<Property> properties = new ArrayList<>();
        properties.add(new Property("19103", 100000.0, 1000.0));
        
        Map<String, Integer> population = new HashMap<>();
        population.put("19104", 1000);
        
        DataProcessor processor = new DataProcessor(violations, properties, population);
        int result = processor.getMarketValuePerCapita("19103");
        
        assertEquals(0, result);
    }

    @Test
    public void testGetMarketValuePerCapitaWithZeroPopulation() {
        // Test case: Zero population
        List<ParkingViolation> violations = new ArrayList<>();
        List<Property> properties = new ArrayList<>();
        properties.add(new Property("19103", 100000.0, 1000.0));
        
        Map<String, Integer> population = new HashMap<>();
        population.put("19103", 0);
        
        DataProcessor processor = new DataProcessor(violations, properties, population);
        int result = processor.getMarketValuePerCapita("19103");
        
        assertEquals(0, result);
    }

    @Test
    public void testGetMarketValuePerCapitaWithZeroMarketValue() {
        // Test case: Zero total market value
        List<ParkingViolation> violations = new ArrayList<>();
        List<Property> properties = new ArrayList<>();
        properties.add(new Property("19103", null, 1000.0));
        properties.add(new Property("19103", 0.0, 2000.0));
        
        Map<String, Integer> population = new HashMap<>();
        population.put("19103", 1000);
        
        DataProcessor processor = new DataProcessor(violations, properties, population);
        int result = processor.getMarketValuePerCapita("19103");
        
        assertEquals(0, result);
    }

    @Test
    public void testGetMarketValuePerCapitaWithInvalidMarketValues() {
        // Test case: Only valid market values should be counted
        List<ParkingViolation> violations = new ArrayList<>();
        List<Property> properties = new ArrayList<>();
        properties.add(new Property("19103", null, 1000.0));
        properties.add(new Property("19103", 0.0, 2000.0));
        properties.add(new Property("19103", -1000.0, 3000.0));
        properties.add(new Property("19103", 300000.0, 4000.0));
        
        Map<String, Integer> population = new HashMap<>();
        population.put("19103", 1);
        
        DataProcessor processor = new DataProcessor(violations, properties, population);
        int result = processor.getMarketValuePerCapita("19103");
        
        assertEquals(300000, result);
    }

    @Test
    public void testGetMarketValuePerCapitaWithRoundedResult() {
        // Test case: Result that needs rounding
        List<ParkingViolation> violations = new ArrayList<>();
        List<Property> properties = new ArrayList<>();
        properties.add(new Property("19103", 100000.0, 1000.0));
        properties.add(new Property("19103", 100001.0, 2000.0));
        
        Map<String, Integer> population = new HashMap<>();
        population.put("19103", 3);
        
        DataProcessor processor = new DataProcessor(violations, properties, population);
        int result = processor.getMarketValuePerCapita("19103");
        
        assertEquals(66667, result);
    }

    @Test
    public void testGetMarketValuePerCapitaWithNullProperty() {
        // Test case: Null properties should be handled gracefully
        List<ParkingViolation> violations = new ArrayList<>();
        List<Property> properties = new ArrayList<>();
        properties.add(null);
        properties.add(new Property("19103", 100000.0, 1000.0));
        
        Map<String, Integer> population = new HashMap<>();
        population.put("19103", 1);
        
        DataProcessor processor = new DataProcessor(violations, properties, population);
        int result = processor.getMarketValuePerCapita("19103");
        
        assertEquals(100000, result);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetMarketValuePerCapitaWithNullZipCode() {
        // Test case: Null ZIP code should throw exception
        List<ParkingViolation> violations = new ArrayList<>();
        List<Property> properties = new ArrayList<>();
        Map<String, Integer> population = new HashMap<>();
        
        DataProcessor processor = new DataProcessor(violations, properties, population);
        processor.getMarketValuePerCapita(null);
    }
}


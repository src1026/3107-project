package test.processor;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.util.*;
import processor.DataProcessor;
import common.ParkingViolation;
import common.Property;

/**
 * Test class for DataProcessor.getFinesPerCapita() method.
 * One test method per test case to achieve 100% statement coverage.
 */
public class GetFinesPerCapitaTest {

    @Test
    public void testGetFinesPerCapitaWithPAViolations() {
        // PA violations with valid ZIP codes
        List<ParkingViolation> violations = new ArrayList<>();
        ParkingViolation v1 = createMockViolation("19103", "PA", 100.0, true);
        ParkingViolation v2 = createMockViolation("19103", "PA", 50.0, true);
        violations.add(v1);
        violations.add(v2);
        
        List<Property> properties = new ArrayList<>();
        Map<String, Integer> population = new HashMap<>();
        population.put("19103", 1000);
        
        DataProcessor.resetInstance();
        DataProcessor processor = DataProcessor.getInstance(violations, properties, population);
        Map<String, Double> result = processor.getFinesPerCapita();
        
        assertEquals(1, result.size());
        assertEquals(0.15, result.get("19103"), 0.0001);
    }

    @Test
    public void testGetFinesPerCapitaWithNonPAViolations() {
        // Non-PA violations should be ignored
        List<ParkingViolation> violations = new ArrayList<>();
        ParkingViolation v1 = createMockViolation("19103", "NJ", 100.0, true);
        violations.add(v1);
        
        List<Property> properties = new ArrayList<>();
        Map<String, Integer> population = new HashMap<>();
        population.put("19103", 1000);
        
        DataProcessor.resetInstance();
        DataProcessor processor = DataProcessor.getInstance(violations, properties, population);
        Map<String, Double> result = processor.getFinesPerCapita();
        
        assertEquals(0, result.size());
    }

    @Test
    public void testGetFinesPerCapitaWithInvalidZipCode() {
        // Violations with invalid ZIP codes should be ignored
        List<ParkingViolation> violations = new ArrayList<>();
        ParkingViolation v1 = createMockViolation("19103", "PA", 100.0, false);
        violations.add(v1);
        
        List<Property> properties = new ArrayList<>();
        Map<String, Integer> population = new HashMap<>();
        population.put("19103", 1000);
        
        DataProcessor.resetInstance();
        DataProcessor processor = DataProcessor.getInstance(violations, properties, population);
        Map<String, Double> result = processor.getFinesPerCapita();
        
        assertEquals(0, result.size());
    }

    @Test
    public void testGetFinesPerCapitaWithZeroPopulation() {
        // ZIP code with zero population should be excluded
        List<ParkingViolation> violations = new ArrayList<>();
        ParkingViolation v1 = createMockViolation("19103", "PA", 100.0, true);
        violations.add(v1);
        
        List<Property> properties = new ArrayList<>();
        Map<String, Integer> population = new HashMap<>();
        population.put("19103", 0);
        
        DataProcessor.resetInstance();
        DataProcessor processor = DataProcessor.getInstance(violations, properties, population);
        Map<String, Double> result = processor.getFinesPerCapita();
        
        assertEquals(0, result.size());
    }

    @Test
    public void testGetFinesPerCapitaWithMissingPopulation() {
        // ZIP code not in population map should be excluded
        List<ParkingViolation> violations = new ArrayList<>();
        ParkingViolation v1 = createMockViolation("19103", "PA", 100.0, true);
        violations.add(v1);
        
        List<Property> properties = new ArrayList<>();
        Map<String, Integer> population = new HashMap<>();
        population.put("19104", 1000);
        
        DataProcessor.resetInstance();
        DataProcessor processor = DataProcessor.getInstance(violations, properties, population);
        Map<String, Double> result = processor.getFinesPerCapita();
        
        assertEquals(0, result.size());
    }

    @Test
    public void testGetFinesPerCapitaWithZeroFines() {
        // ZIP code with zero fines should be excluded
        List<ParkingViolation> violations = new ArrayList<>();
        ParkingViolation v1 = createMockViolation("19103", "PA", 0.0, true);
        violations.add(v1);
        
        List<Property> properties = new ArrayList<>();
        Map<String, Integer> population = new HashMap<>();
        population.put("19103", 1000);
        
        DataProcessor.resetInstance();
        DataProcessor processor = DataProcessor.getInstance(violations, properties, population);
        Map<String, Double> result = processor.getFinesPerCapita();
        
        assertEquals(0, result.size());
    }

    @Test
    public void testGetFinesPerCapitaWithMultipleZipCodes() {
        // Multiple ZIP codes, sorted output
        List<ParkingViolation> violations = new ArrayList<>();
        ParkingViolation v1 = createMockViolation("19104", "PA", 100.0, true);
        ParkingViolation v2 = createMockViolation("19103", "PA", 50.0, true);
        violations.add(v1);
        violations.add(v2);
        
        List<Property> properties = new ArrayList<>();
        Map<String, Integer> population = new HashMap<>();
        population.put("19103", 1000);
        population.put("19104", 2000);
        
        DataProcessor.resetInstance();
        DataProcessor processor = DataProcessor.getInstance(violations, properties, population);
        Map<String, Double> result = processor.getFinesPerCapita();
        
        assertEquals(2, result.size());
        // TreeMap should sort by key
        List<String> keys = new ArrayList<>(result.keySet());
        assertEquals("19103", keys.get(0));
        assertEquals("19104", keys.get(1));
    }

    @Test
    public void testGetFinesPerCapitaWithNullViolation() {
        // Null violations should be handled gracefully
        List<ParkingViolation> violations = new ArrayList<>();
        violations.add(null);
        ParkingViolation v1 = createMockViolation("19103", "PA", 100.0, true);
        violations.add(v1);
        
        List<Property> properties = new ArrayList<>();
        Map<String, Integer> population = new HashMap<>();
        population.put("19103", 1000);
        
        DataProcessor.resetInstance();
        DataProcessor processor = DataProcessor.getInstance(violations, properties, population);
        Map<String, Double> result = processor.getFinesPerCapita();
        
        assertEquals(1, result.size());
        assertEquals(0.1, result.get("19103"), 0.0001);
    }

    // Helper method to create mock violations
    private ParkingViolation createMockViolation(String zipCode, String state, double fine, boolean hasValidZip) {
        return new TestParkingViolation(zipCode, state, fine, hasValidZip);
    }
}


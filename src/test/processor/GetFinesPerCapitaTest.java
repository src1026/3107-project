package test.processor;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.util.*;
import processor.DataProcessor;
import common.ParkingViolation;
import common.Property;

public class GetFinesPerCapitaTest {

    @Test
    public void testGetFinesPerCapitaWithPAViolations() {
        // Test case: PA violations with valid ZIP codes
        List<ParkingViolation> violations = new ArrayList<>();
        ParkingViolation v1 = new ParkingViolation("2024-01-01T10:00:00Z", 100.0, "Description", "VEH001", "PA", "VIOL001", "19103") {
            @Override
            public boolean hasValidZipCode() { return true; }
            @Override
            public boolean isFromPA() { return true; }
            @Override
            public String getZipCode() { return "19103"; }
            @Override
            public double getFine() { return 100.0; }
        };
        ParkingViolation v2 = new ParkingViolation("2024-01-01T10:00:00Z", 50.0, "Description", "VEH002", "PA", "VIOL002", "19103") {
            @Override
            public boolean hasValidZipCode() { return true; }
            @Override
            public boolean isFromPA() { return true; }
            @Override
            public String getZipCode() { return "19103"; }
            @Override
            public double getFine() { return 50.0; }
        };
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
        // Test case: Non-PA violations should be ignored
        List<ParkingViolation> violations = new ArrayList<>();
        ParkingViolation v1 = new ParkingViolation("2024-01-01T10:00:00Z", 100.0, "Description", "VEH001", "NJ", "VIOL001", "19103") {
            @Override
            public boolean hasValidZipCode() { return true; }
            @Override
            public boolean isFromPA() { return false; }
            @Override
            public String getZipCode() { return "19103"; }
            @Override
            public double getFine() { return 100.0; }
        };
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
        // Test case: Violations with invalid ZIP codes should be ignored
        List<ParkingViolation> violations = new ArrayList<>();
        ParkingViolation v1 = new ParkingViolation("2024-01-01T10:00:00Z", 100.0, "Description", "VEH001", "PA", "VIOL001", null) {
            @Override
            public boolean hasValidZipCode() { return false; }
            @Override
            public boolean isFromPA() { return true; }
            @Override
            public String getZipCode() { return null; }
            @Override
            public double getFine() { return 100.0; }
        };
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
        // Test case: ZIP code with zero population should be excluded
        List<ParkingViolation> violations = new ArrayList<>();
        ParkingViolation v1 = new ParkingViolation("2024-01-01T10:00:00Z", 100.0, "Description", "VEH001", "PA", "VIOL001", "19103") {
            @Override
            public boolean hasValidZipCode() { return true; }
            @Override
            public boolean isFromPA() { return true; }
            @Override
            public String getZipCode() { return "19103"; }
            @Override
            public double getFine() { return 100.0; }
        };
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
        // Test case: ZIP code not in population map should be excluded
        List<ParkingViolation> violations = new ArrayList<>();
        ParkingViolation v1 = new ParkingViolation("2024-01-01T10:00:00Z", 100.0, "Description", "VEH001", "PA", "VIOL001", "19103") {
            @Override
            public boolean hasValidZipCode() { return true; }
            @Override
            public boolean isFromPA() { return true; }
            @Override
            public String getZipCode() { return "19103"; }
            @Override
            public double getFine() { return 100.0; }
        };
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
        // Test case: ZIP code with zero fines should be excluded
        List<ParkingViolation> violations = new ArrayList<>();
        ParkingViolation v1 = new ParkingViolation("2024-01-01T10:00:00Z", 0.0, "Description", "VEH001", "PA", "VIOL001", "19103") {
            @Override
            public boolean hasValidZipCode() { return true; }
            @Override
            public boolean isFromPA() { return true; }
            @Override
            public String getZipCode() { return "19103"; }
            @Override
            public double getFine() { return 0.0; }
        };
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
        // Test case: Multiple ZIP codes, sorted output
        List<ParkingViolation> violations = new ArrayList<>();
        ParkingViolation v1 = new ParkingViolation("2024-01-01T10:00:00Z", 100.0, "Description", "VEH001", "PA", "VIOL001", "19104") {
            @Override
            public boolean hasValidZipCode() { return true; }
            @Override
            public boolean isFromPA() { return true; }
            @Override
            public String getZipCode() { return "19104"; }
            @Override
            public double getFine() { return 100.0; }
        };
        ParkingViolation v2 = new ParkingViolation("2024-01-01T10:00:00Z", 50.0, "Description", "VEH002", "PA", "VIOL002", "19103") {
            @Override
            public boolean hasValidZipCode() { return true; }
            @Override
            public boolean isFromPA() { return true; }
            @Override
            public String getZipCode() { return "19103"; }
            @Override
            public double getFine() { return 50.0; }
        };
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
        // Test case: Null violations should be handled gracefully
        List<ParkingViolation> violations = new ArrayList<>();
        violations.add(null);
        ParkingViolation v1 = new ParkingViolation("2024-01-01T10:00:00Z", 100.0, "Description", "VEH001", "PA", "VIOL001", "19103") {
            @Override
            public boolean hasValidZipCode() { return true; }
            @Override
            public boolean isFromPA() { return true; }
            @Override
            public String getZipCode() { return "19103"; }
            @Override
            public double getFine() { return 100.0; }
        };
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

}


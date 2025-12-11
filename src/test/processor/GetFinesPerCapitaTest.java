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
        //  PA violations with valid ZIP codes
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
        //  Non-PA violations should be ignored
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
        //  Violations with invalid ZIP codes should be ignored
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
        //  ZIP code with zero population should be excluded
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
        //  ZIP code not in population map should be excluded
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
        //  ZIP code with zero fines should be excluded
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
        //  Multiple ZIP codes, sorted output
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
        //  Null violations should be handled gracefully
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

    @Test
    public void testGetFinesPerCapitaWithMultipleViolationsSameZipAccumulation() {
        //  Multiple violations for same ZIP code accumulate fines (tests getOrDefault with existing key)
        List<ParkingViolation> violations = new ArrayList<>();
        ParkingViolation v1 = new ParkingViolation("2024-01-01T10:00:00Z", 25.0, "Description", "VEH001", "PA", "VIOL001", "19103") {
            @Override
            public boolean hasValidZipCode() { return true; }
            @Override
            public boolean isFromPA() { return true; }
            @Override
            public String getZipCode() { return "19103"; }
            @Override
            public double getFine() { return 25.0; }
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
        ParkingViolation v3 = new ParkingViolation("2024-01-01T10:00:00Z", 75.0, "Description", "VEH003", "PA", "VIOL003", "19103") {
            @Override
            public boolean hasValidZipCode() { return true; }
            @Override
            public boolean isFromPA() { return true; }
            @Override
            public String getZipCode() { return "19103"; }
            @Override
            public double getFine() { return 75.0; }
        };
        violations.add(v1);
        violations.add(v2);
        violations.add(v3);

        List<Property> properties = new ArrayList<>();
        Map<String, Integer> population = new HashMap<>();
        population.put("19103", 1000);

        DataProcessor.resetInstance();
        DataProcessor processor = DataProcessor.getInstance(violations, properties, population);
        Map<String, Double> result = processor.getFinesPerCapita();

        assertEquals(1, result.size());
        assertEquals(0.15, result.get("19103"), 0.0001); // (25+50+75)/1000 = 0.15
    }

    @Test
    public void testGetFinesPerCapitaWithInvalidZipCodeButFromPA() {
        //  Violation with invalid ZIP code but from PA (tests short-circuit branch)
        List<ParkingViolation> violations = new ArrayList<>();
        ParkingViolation v1 = new ParkingViolation("2024-01-01T10:00:00Z", 100.0, "Description", "VEH001", "PA", "VIOL001", "invalid") {
            @Override
            public boolean hasValidZipCode() { return false; }
            @Override
            public boolean isFromPA() { return true; }
            @Override
            public String getZipCode() { return "invalid"; }
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
    public void testGetFinesPerCapitaWithValidZipCodeButNotFromPA() {
        //  Violation with valid ZIP code but not from PA (tests isFromPA branch)
        List<ParkingViolation> violations = new ArrayList<>();
        ParkingViolation v1 = new ParkingViolation("2024-01-01T10:00:00Z", 100.0, "Description", "VEH001", "DE", "VIOL001", "19103") {
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
    public void testGetFinesPerCapitaWithAccumulatedFinesButZeroPopulation() {
        //  Multiple violations accumulate fines, but population is zero
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
        ParkingViolation v2 = new ParkingViolation("2024-01-01T10:00:00Z", 200.0, "Description", "VEH002", "PA", "VIOL002", "19103") {
            @Override
            public boolean hasValidZipCode() { return true; }
            @Override
            public boolean isFromPA() { return true; }
            @Override
            public String getZipCode() { return "19103"; }
            @Override
            public double getFine() { return 200.0; }
        };
        violations.add(v1);
        violations.add(v2);

        List<Property> properties = new ArrayList<>();
        Map<String, Integer> population = new HashMap<>();
        population.put("19103", 0);

        DataProcessor.resetInstance();
        DataProcessor processor = DataProcessor.getInstance(violations, properties, population);
        Map<String, Double> result = processor.getFinesPerCapita();

        assertEquals(0, result.size());
    }

    @Test
    public void testGetFinesPerCapitaWithAccumulatedFinesButMissingPopulation() {
        //  Multiple violations accumulate fines, but ZIP code not in population map
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
        ParkingViolation v2 = new ParkingViolation("2024-01-01T10:00:00Z", 200.0, "Description", "VEH002", "PA", "VIOL002", "19103") {
            @Override
            public boolean hasValidZipCode() { return true; }
            @Override
            public boolean isFromPA() { return true; }
            @Override
            public String getZipCode() { return "19103"; }
            @Override
            public double getFine() { return 200.0; }
        };
        violations.add(v1);
        violations.add(v2);

        List<Property> properties = new ArrayList<>();
        Map<String, Integer> population = new HashMap<>();
        population.put("19104", 1000);

        DataProcessor.resetInstance();
        DataProcessor processor = DataProcessor.getInstance(violations, properties, population);
        Map<String, Double> result = processor.getFinesPerCapita();

        assertEquals(0, result.size());
    }

    @Test
    public void testGetFinesPerCapitaWithMultipleViolationsSummingToZero() {
        //  Multiple violations that sum to exactly zero
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
        ParkingViolation v2 = new ParkingViolation("2024-01-01T10:00:00Z", -100.0, "Description", "VEH002", "PA", "VIOL002", "19103") {
            @Override
            public boolean hasValidZipCode() { return true; }
            @Override
            public boolean isFromPA() { return true; }
            @Override
            public String getZipCode() { return "19103"; }
            @Override
            public double getFine() { return -100.0; }
        };
        violations.add(v1);
        violations.add(v2);

        List<Property> properties = new ArrayList<>();
        Map<String, Integer> population = new HashMap<>();
        population.put("19103", 1000);

        DataProcessor.resetInstance();
        DataProcessor processor = DataProcessor.getInstance(violations, properties, population);
        Map<String, Double> result = processor.getFinesPerCapita();

        assertEquals(0, result.size());
    }

    @Test
    public void testGetFinesPerCapitaWithEmptyViolationsList() {
        //  Empty violations list
        List<ParkingViolation> violations = new ArrayList<>();

        List<Property> properties = new ArrayList<>();
        Map<String, Integer> population = new HashMap<>();
        population.put("19103", 1000);

        DataProcessor.resetInstance();
        DataProcessor processor = DataProcessor.getInstance(violations, properties, population);
        Map<String, Double> result = processor.getFinesPerCapita();

        assertEquals(0, result.size());
    }

    @Test
    public void testGetFinesPerCapitaWithPreciseCalculation() {
        //  Test precise calculation with small values
        List<ParkingViolation> violations = new ArrayList<>();
        ParkingViolation v1 = new ParkingViolation("2024-01-01T10:00:00Z", 1.0, "Description", "VEH001", "PA", "VIOL001", "19103") {
            @Override
            public boolean hasValidZipCode() { return true; }
            @Override
            public boolean isFromPA() { return true; }
            @Override
            public String getZipCode() { return "19103"; }
            @Override
            public double getFine() { return 1.0; }
        };
        violations.add(v1);

        List<Property> properties = new ArrayList<>();
        Map<String, Integer> population = new HashMap<>();
        population.put("19103", 3);

        DataProcessor.resetInstance();
        DataProcessor processor = DataProcessor.getInstance(violations, properties, population);
        Map<String, Double> result = processor.getFinesPerCapita();

        assertEquals(1, result.size());
        assertEquals(1.0 / 3.0, result.get("19103"), 0.0001);
    }

}


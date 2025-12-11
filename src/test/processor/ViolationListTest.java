package test.processor;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.util.*;
import processor.ViolationList;
import common.ParkingViolation;

public class ViolationListTest {

    @Test
    public void testConstructorWithValidList() {
        // Valid list should create instance
        List<ParkingViolation> violations = new ArrayList<>();
        violations.add(createViolation("19103"));
        violations.add(createViolation("19104"));
        
        ViolationList violationList = new ViolationList(violations);
        assertNotNull(violationList);
        assertEquals(2, violationList.size());
    }

    @Test
    public void testConstructorWithNullList() {
        // Null list should throw exception
        assertThrows(IllegalArgumentException.class, () -> {
            new ViolationList(null);
        });
    }

    @Test
    public void testConstructorWithEmptyList() {
        // Empty list should create instance
        List<ParkingViolation> violations = new ArrayList<>();
        ViolationList violationList = new ViolationList(violations);
        assertNotNull(violationList);
        assertEquals(0, violationList.size());
        assertFalse(violationList.hasNext());
    }

    @Test
    public void testHasNextWithElements() {
        // hasNext() should return true when elements exist
        List<ParkingViolation> violations = new ArrayList<>();
        violations.add(createViolation("19103"));
        ViolationList violationList = new ViolationList(violations);
        
        assertTrue(violationList.hasNext());
    }

    @Test
    public void testHasNextWithoutElements() {
        // hasNext() should return false when no elements
        List<ParkingViolation> violations = new ArrayList<>();
        ViolationList violationList = new ViolationList(violations);
        
        assertFalse(violationList.hasNext());
    }

    @Test
    public void testHasNextAfterIteration() {
        // hasNext() should return false after iterating all elements
        List<ParkingViolation> violations = new ArrayList<>();
        violations.add(createViolation("19103"));
        ViolationList violationList = new ViolationList(violations);
        
        violationList.next();
        assertFalse(violationList.hasNext());
    }

    @Test
    public void testNextWithElements() {
        // next() should return next element
        List<ParkingViolation> violations = new ArrayList<>();
        ParkingViolation v1 = createViolation("19103");
        ParkingViolation v2 = createViolation("19104");
        violations.add(v1);
        violations.add(v2);
        
        ViolationList violationList = new ViolationList(violations);
        assertEquals(v1, violationList.next());
        assertEquals(v2, violationList.next());
    }

    @Test
    public void testNextWithoutElements() {
        // next() should throw exception when no more elements
        List<ParkingViolation> violations = new ArrayList<>();
        ViolationList violationList = new ViolationList(violations);
        
        assertThrows(java.util.NoSuchElementException.class, () -> {
            violationList.next();
        });
    }

    @Test
    public void testNextAfterIteration() {
        // next() should throw exception after iterating all elements
        List<ParkingViolation> violations = new ArrayList<>();
        violations.add(createViolation("19103"));
        ViolationList violationList = new ViolationList(violations);
        
        violationList.next();
        assertThrows(java.util.NoSuchElementException.class, () -> {
            violationList.next();
        });
    }

    @Test
    public void testReset() {
        // reset() should reset iterator to beginning
        List<ParkingViolation> violations = new ArrayList<>();
        ParkingViolation v1 = createViolation("19103");
        ParkingViolation v2 = createViolation("19104");
        violations.add(v1);
        violations.add(v2);
        
        ViolationList violationList = new ViolationList(violations);
        violationList.next(); // Move past first element
        violationList.reset(); // Reset to beginning
        
        assertTrue(violationList.hasNext());
        assertEquals(v1, violationList.next()); // Should get first element again
    }

    @Test
    public void testSize() {
        // size() should return correct size
        List<ParkingViolation> violations = new ArrayList<>();
        violations.add(createViolation("19103"));
        violations.add(createViolation("19104"));
        violations.add(createViolation("19105"));
        
        ViolationList violationList = new ViolationList(violations);
        assertEquals(3, violationList.size());
    }

    @Test
    public void testMultipleIterationsWithReset() {
        // Multiple iterations with reset should work correctly
        List<ParkingViolation> violations = new ArrayList<>();
        ParkingViolation v1 = createViolation("19103");
        ParkingViolation v2 = createViolation("19104");
        violations.add(v1);
        violations.add(v2);
        
        ViolationList violationList = new ViolationList(violations);
        
        // First iteration
        assertEquals(v1, violationList.next());
        assertEquals(v2, violationList.next());
        assertFalse(violationList.hasNext());
        
        // Reset and iterate again
        violationList.reset();
        assertTrue(violationList.hasNext());
        assertEquals(v1, violationList.next());
        assertEquals(v2, violationList.next());
        assertFalse(violationList.hasNext());
    }

    // Helper method to create a parking violation
    private ParkingViolation createViolation(String zipCode) {
        return new ParkingViolation("2024-01-01T10:00:00Z", 100.0, "Description", 
                                   "VEH001", "PA", "VIOL001", zipCode);
    }
}


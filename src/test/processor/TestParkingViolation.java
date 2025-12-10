package test.processor;

import common.ParkingViolation;

/**
 * Test helper class for creating ParkingViolation instances with specific behavior.
 * This is used for testing DataProcessor methods.
 * 
 * IMPORTANT: This class requires ParkingViolation to be fully implemented first.
 * Once ParkingViolation has a constructor that takes all parameters, update this class
 * to call super() with appropriate values.
 */
public class TestParkingViolation extends ParkingViolation {
    private String zipCode;
    private String state;
    private double fine;
    private boolean hasValidZip;

    public TestParkingViolation(String zipCode, String state, double fine, boolean hasValidZip) {
        // TODO: Once ParkingViolation constructor is implemented, call:
        // super("2024-01-01T10:00:00Z", fine, "Description", "VEH001", state, "VIOL001", hasValidZip ? zipCode : null);
        // For now, this will not compile until ParkingViolation has a constructor
        this.zipCode = zipCode;
        this.state = state;
        this.fine = fine;
        this.hasValidZip = hasValidZip;
    }

    @Override
    public boolean hasValidZipCode() {
        return hasValidZip;
    }

    @Override
    public boolean isFromPA() {
        return "PA".equals(state);
    }

    @Override
    public String getZipCode() {
        return zipCode;
    }

    @Override
    public double getFine() {
        return fine;
    }
}


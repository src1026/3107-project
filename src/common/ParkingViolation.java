package common;

/**
 * Represents a parking violation with all its associated data.
 */
public class ParkingViolation {
    private String timestamp;
    private double fine;
    private String description;
    private String vehicleId;
    private String state;
    private String violationId;
    private String zipCode;

    // Constructor
    public ParkingViolation(String timestamp, double fine, String description, 
                           String vehicleId, String state, String violationId, String zipCode) {
        this.timestamp = timestamp;
        this.fine = fine;
        this.description = description;
        this.vehicleId = vehicleId;
        this.state = state;
        this.violationId = violationId;
        this.zipCode = zipCode;
    }

    // Getters
    public String getTimestamp() {
        return timestamp;
    }

    public double getFine() {
        return fine;
    }

    public String getDescription() {
        return description;
    }

    public String getVehicleId() {
        return vehicleId;
    }

    public String getState() {
        return state;
    }

    public String getViolationId() {
        return violationId;
    }

    public String getZipCode() {
        return zipCode;
    }

    // Checks if zipCode is not null and not empty
    public boolean hasValidZipCode() {
        return zipCode != null && !zipCode.trim().isEmpty();
    }

    // Checks if violation is from Pennsylvania
    public boolean isFromPA() {
        return "PA".equals(state);
    }
}


package common;

/**
 * Represents a parking violation with all its associated data.
 */
public class ParkingViolation {
    // TODO: Add private fields for:
    private String timestamp;
    private double fine;
    private String description;
    private String vehicleId;
    private String state;
    private String violationId;
    private String zipCode;

    // TODO: Add constructor with all parameters
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

    // TODO: Add getter methods for all fields
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

    public boolean hasValidZipCode() {
        // TODO: Return true if zipCode is not null and not empty
        return zipCode != null && !zipCode.trim().isEmpty();
    }
    public boolean isFromPA() {
        // TODO: Return true if state equals "PA"
        return "PA".equals(state);
    }
}


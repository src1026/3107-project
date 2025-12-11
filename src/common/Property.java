package common;

/**
 * Represents a property/residence with its associated data.
 */
public class Property {
    private String zipCode;
    private Double marketValue;
    private Double totalLivableArea;

    public Property(String zipCode, Double marketValue, Double totalLivableArea) {
        this.zipCode = zipCode;
        this.marketValue = marketValue;
        this.totalLivableArea = totalLivableArea;
    }

    public String getZipCode() {
        return zipCode;
    }

    public Double getMarketValue() {
        return marketValue;
    }

    public Double getTotalLivableArea() {
        return totalLivableArea;
    }

    /**
     * Checks if the market value is valid (not null, positive, numeric).
     */
    public boolean hasValidMarketValue() {
        // TODO: Return true if marketValue is not null and greater than 0
        return marketValue != null && marketValue > 0;
    }

    /**
     * Checks if the total livable area is valid (not null, positive, numeric).
     */
    public boolean hasValidTotalLivableArea() {
        return totalLivableArea != null && totalLivableArea > 0;
    }
}


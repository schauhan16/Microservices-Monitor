package com.shailendra.model;

/**
 * @author Shailendra Chauhan
 */
public class StatusSummary {

    private String totalUnavailability;

    private String totalAvailability;

    public String getTotalUnavailability() {
        return totalUnavailability;
    }

    public void setTotalUnavailability(String totalUnavailability) {
        this.totalUnavailability = totalUnavailability + " %";
    }

    public String getTotalAvailability() {
        return totalAvailability;
    }

    public void setTotalAvailability(String totalAvailability) {
        this.totalAvailability = totalAvailability + " %";
    }
}

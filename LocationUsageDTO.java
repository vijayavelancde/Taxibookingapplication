package cabbooking;

public class LocationUsageDTO {
    
	private String locationId;
    private int rideCount;

    public LocationUsageDTO(String locationId, int rideCount) {
        this.locationId = locationId;
        this.rideCount = rideCount;
    }

    public String getLocationId() {
        return locationId;
    }

    public int getRideCount() {
        return rideCount;
    }
}


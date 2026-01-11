package cabbooking;

import java.util.HashMap;

public class Drivers implements Identifiable {

    private String driverID;
    private String driverName;
    private Locations driverLocation;
    private boolean isAvailable;
    private int totalEarning = 0;
    private int completedRideCount = 0;
    private int driverRating = 0;
    private int ratingCount = 0;
    private HashMap<String, RideDetails> rideHistory = new HashMap<>();

    public Drivers(String driverID, String driverName, Locations driverLocation, boolean isAvailable,
                   int totalEarning, int completedRideCount, HashMap<String, RideDetails> rideHistory, int driverRating, int ratingCount) {
        this.driverID = driverID;
        this.driverName = driverName;
        this.driverLocation = driverLocation;
        this.isAvailable = isAvailable;
        this.totalEarning = totalEarning;
        this.completedRideCount = completedRideCount;
        this.rideHistory = rideHistory;
        this.driverRating = driverRating;
        this.ratingCount = ratingCount;    
           }
    
    public Drivers(String driverID, String driverName, Locations driverLocation, boolean isAvailable, int totalEarning, int completedRideCount, int driverRating, int ratingCount) {
           this.driverID = driverID;
           this.driverName = driverName;
           this.driverLocation = driverLocation; 
           this.isAvailable = isAvailable;
           this.totalEarning = totalEarning;
           this.completedRideCount = completedRideCount;
           this.driverRating = driverRating;
           this.ratingCount = ratingCount;
   }
    
    public Drivers(String driverID, String driverName, Locations driverLocation, boolean isAvailable ) {
        this.driverID = driverID;
        this.driverName = driverName;
        this.driverLocation = driverLocation; 
        this.isAvailable = isAvailable;
}

    
    public String getID() {
        return driverID;
    }

    
    public String getEntityName() {
        return "Driver";
    }

    public String getDriverID() {
        return driverID;
    }

    public void setDriverID(String driverID) {
        this.driverID = driverID;
    }

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public Locations getDriverLocation() {
        return driverLocation;
    }

    public void setDriverLocation(Locations driverLocation) {
        this.driverLocation = driverLocation;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    public int getTotalEarning() {
        return totalEarning;
    }

    public void setTotalEarning(int totalEarning) {
        this.totalEarning = totalEarning;
    }

    public int getCompletedRideCount() {
        return completedRideCount;
    }

    public void setCompletedRideCount(int completedRideCount) {
        this.completedRideCount = completedRideCount;
    }
    
    public int getDriverRating() {
    	return driverRating;
    }
    
    public void setDriverRating(int driverRating) {
    	this.driverRating = driverRating;
    }
    
    public int getDriverRatingCount() {
    	return ratingCount;
    }
    
    public void setDriverRatingCount(int ratingCount) {
    	this.ratingCount = ratingCount;
    }
    
    
    
    public HashMap<String, RideDetails> getRideHistory() {
        return rideHistory;
    }

    public void setRideHistory(HashMap<String, RideDetails> rideHistory) {
        this.rideHistory = rideHistory;
    }
}


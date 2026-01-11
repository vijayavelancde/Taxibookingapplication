package cabbooking;

public class Locations implements Identifiable {
    
    private String locationID;
    private String locationName;
    private int locationDistancefromOrgin;
    
    public Locations(String locationID, String locationName, int locationDistancefromOrgin) {
        this.locationID = locationID;
        this.locationName = locationName;
        this.locationDistancefromOrgin = locationDistancefromOrgin;
    }


	public String getID() {
        return locationID;
    }

    public String getEntityName() {
        return "Location";
    }

    public String getLocationID() {
        return locationID;
    }
    
    public void setLocationID(String locationID) {
        this.locationID = locationID;
    }
    
    public String getLocationName() {
        return locationName;
    }
    
    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }
    
    public int getLocationDistancefromOrgin() {
        return locationDistancefromOrgin;
    }
    
    public void setLocationDistancefromOrgin(int locationDistancefromOrgin) {
        this.locationDistancefromOrgin = locationDistancefromOrgin;
    }
    
    public int distanceTo(Locations other) {
        return Math.abs(this.getLocationDistancefromOrgin()
                       - other.getLocationDistancefromOrgin());
    }
}

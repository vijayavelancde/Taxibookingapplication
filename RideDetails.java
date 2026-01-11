package cabbooking;

public class RideDetails implements Identifiable {
    
	 public enum RideStatus {
	        ASSIGNED,
	        COMPLETED,
	        CANCELLED
	    }
	
    private String rideID;
    private String driverID;
    private String driverName;
    private String customerID;
    private String customerName;
    private String pickupLocationID;
    private String dropLocationID;
    private int rideDistance;
    private int ridefair;
    private RideStatus rideStatus;
    
   
    public RideDetails(String rideID, String driverID, String driverName, String customerID, String customerName, String pickupLocationID, String dropLocationID, int rideDistance, int ridefair, RideStatus rideStatus) {
        this.rideID = rideID;
        this.driverID = driverID;
        this.driverName = driverName;
        this.customerID = customerID;
        this.customerName = customerName;
        this.pickupLocationID = pickupLocationID;
        this.dropLocationID = dropLocationID;
        this.rideDistance = rideDistance;
        this.ridefair = ridefair;
        this.rideStatus = rideStatus;
    }
    
    public RideDetails(String rideID, String driverID, String customerID, String pickupLocationID, String dropLocationID, int rideDistance, int ridefair, RideStatus rideStatus) {
        this.rideID = rideID;
        this.driverID = driverID;
        this.customerID = customerID;
        this.pickupLocationID = pickupLocationID;
        this.dropLocationID = dropLocationID;
        this.rideDistance = rideDistance;
        this.ridefair = ridefair;
        this.rideStatus = rideStatus;
    }
    
   public RideDetails(String customerID, String customerName, String pickupLocation, String dropLocation,int rideDistance, RideStatus rideStatus){
	   this.customerID = customerID;
	   this.customerName = customerName;
	   this.pickupLocationID = pickupLocation;
	   this.dropLocationID = dropLocation;
	   this.rideDistance = rideDistance;
	   this.rideStatus = rideStatus;
   }
    
   public RideDetails(){}
   
   
   public String getID() {
       return rideID;
   }
   
   public String getEntityName() {
       return "RideDetails";
   }  

    public String getRideID() {
        return rideID;
    }

    public void setRideID(String rideID) {
        this.rideID = rideID;
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
    
    public void setDriverName(String Name) {
    	driverName = Name;
    }

    public String getCustomerID() {
        return customerID;
    }

    public void setCustomerID(String customerID) {
        this.customerID = customerID;
    }
    
    public String getCustomerName() {
    	return customerName;
    }
    
    public void setCustomerName(String Name) {
    	customerName = Name;
    }
    
    public String getPickupLocationID() {
        return pickupLocationID;
    }

    public void setPickupLocationID(String pickupLocation) {
        this.pickupLocationID = pickupLocation;
    }

    public String getDropLocationID() {
        return dropLocationID;
    }

    public void setDropLocationID(String dropLocation) {
        this.dropLocationID = dropLocation;
    }

    public int getRideDistance() {
        return rideDistance;
    }

    public void setRideDistance(int rideDistance) {
        this.rideDistance = rideDistance;
    }

    public int getRidefair() {
        return ridefair;
    }

    public void setRidefair(int ridefair) {
        this.ridefair = ridefair;
    }
    
    public RideStatus getRideStatus() {
    	return rideStatus;
    }
    
    public void setRideStatus(RideStatus status) {
    	rideStatus = status;
    }
 
}

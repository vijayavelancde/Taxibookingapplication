package cabbooking;

import java.util.HashMap;

public class InMemoryRideDAO implements RideDAO {

    private HashMap<String, RideDetails> rideMap = new HashMap<>();

    
    public void createRide(RideDetails ride) {
        if (ride == null) return;
        rideMap.put(ride.getRideID(), ride);
    }

    
    public RideDetails getRideById(String rideID) {
        return rideMap.get(rideID);
    }


    public void updateRideStatus(String rideID,
                                 RideDetails.RideStatus status,
                                 int fare) {

        RideDetails ride = rideMap.get(rideID);

        if (ride != null) {
            ride.setRideStatus(status);
            ride.setRidefair(fare);
        }
    }

   
    public void updateRideStatus(java.sql.Connection con,
                                 String rideID,
                                 RideDetails.RideStatus status,
                                 int fare) {
    }
    
}

package cabbooking;

import java.sql.Connection;

public interface RideDAO {

    void createRide(RideDetails ride);

    void updateRideStatus(
        String rideID,
        RideDetails.RideStatus status,
        int fare
    );

    void updateRideStatus(
        Connection con,
        String rideID,
        RideDetails.RideStatus status,
        int fare
    ) throws Exception;

    RideDetails getRideById(String rideID);
}

package cabbooking;

import java.util.ArrayList;

public interface DriverServiceInterface {

    void onboardDriver(String driverID, String driverName, String locationID);

    Drivers getDriverById(String driverID);

    Drivers findBestAvailableDriver(int pickupDistance);

    void markDriverAvailable(String driverID);

    void markDriverUnavailable(String driverID);

    void addEarnings(String driverID, int amount);

    void incrementCompletedRides(String driverID);

    ArrayList<RideDetails> getDriverRideHistory(String driverID);
}

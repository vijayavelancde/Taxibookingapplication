package cabbooking;

import java.util.ArrayList;

public interface DriverDAO {

    void add(Drivers driver);

    Drivers getById(String driverID);

    Drivers findBestAvailableDriver(int pickupDistance);

    void updateAvailability(String driverID, boolean available);

    void updateEarnings(String driverID, int amount);

    void incrementCompletedRideCount(String driverID);

    ArrayList<RideDetails> getDriverRideHistory(String driverID);
}


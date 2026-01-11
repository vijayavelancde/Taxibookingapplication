package cabbooking;

import java.util.ArrayList;

public class DriverService implements DriverServiceInterface {

    private final DriverDAO driverDAO;
    private final LocationDAO locationDAO;

    public DriverService(DriverDAO driverDAO, LocationDAO locationDAO) {
        this.driverDAO = driverDAO;
        this.locationDAO = locationDAO;
    }

   
    public void onboardDriver(String driverID, String driverName, String locationID) {

        Drivers existing = driverDAO.getById(driverID);
        if (existing != null) {
            throw new RuntimeException("Driver already exists with ID: " + driverID);
        }

        Locations location = locationDAO.getById(locationID);
        if (location == null) {
            throw new RuntimeException("Invalid location ID: " + locationID);
        }

        Drivers driver = new Drivers(driverID, driverName, location, true);
        driverDAO.add(driver);
    }

    
    public Drivers getDriverById(String driverID) {

        Drivers driver = driverDAO.getById(driverID);
        if (driver == null) {
            throw new RuntimeException("Driver not found: " + driverID);
        }

        return driver;
    }

   
    public Drivers findBestAvailableDriver(int pickupDistance) {
        return driverDAO.findBestAvailableDriver(pickupDistance);
    }

    public void markDriverAvailable(String driverID) {

        Drivers driver = driverDAO.getById(driverID);
        if (driver == null) {
            throw new RuntimeException("Driver not found: " + driverID);
        }

        driverDAO.updateAvailability(driverID, true);
    }

    
    public void markDriverUnavailable(String driverID) {

        Drivers driver = driverDAO.getById(driverID);
        if (driver == null) {
            throw new RuntimeException("Driver not found: " + driverID);
        }

        driverDAO.updateAvailability(driverID, false);
    }

    public void addEarnings(String driverID, int amount) {

        Drivers driver = driverDAO.getById(driverID);
        if (driver == null) {
            throw new RuntimeException("Driver not found: " + driverID);
        }

        driverDAO.updateEarnings(driverID, amount);
    }

   
    public void incrementCompletedRides(String driverID) {

        Drivers driver = driverDAO.getById(driverID);
        if (driver == null) {
            throw new RuntimeException("Driver not found: " + driverID);
        }

        driverDAO.incrementCompletedRideCount(driverID);
    }

    
    public ArrayList<RideDetails> getDriverRideHistory(String driverID) {

        Drivers driver = driverDAO.getById(driverID);
        if (driver == null) {
            throw new RuntimeException("Driver not found: " + driverID);
        }

        return driverDAO.getDriverRideHistory(driverID);
    }
}

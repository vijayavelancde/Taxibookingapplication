package cabbooking;


import java.util.ArrayList;
import java.util.List;

public class LocationService implements LocationServiceinterface {

    private final LocationDAO locationDAO;

    public LocationService(LocationDAO locationDAO) {
        this.locationDAO = locationDAO;
    }

    public void addLocation(String locationID, String locationName, int distanceFromOrigin) {

        Locations existing = locationDAO.getById(locationID);
        if (existing != null) {
            throw new RuntimeException("Location already exists with ID: " + locationID);
        }

        Locations location = new Locations(locationID, locationName, distanceFromOrigin);
        locationDAO.add(location);
    }

    public Locations getLocationById(String locationID) {

        Locations location = locationDAO.getById(locationID);
        if (location == null) {
            throw new RuntimeException("Location not found: " + locationID);
        }

        return location;
    }

    public void updateLocationName(String locationID, String newName) {

        Locations location = locationDAO.getById(locationID);
        if (location == null) {
            throw new RuntimeException("Location not found: " + locationID);
        }

        locationDAO.updateName(locationID, newName);
    }

    public void updateLocationDistance(String locationID, int newDistance) {

        Locations location = locationDAO.getById(locationID);
        if (location == null) {
            throw new RuntimeException("Location not found: " + locationID);
        }

        locationDAO.updateLocationDistance(locationID, newDistance);
    }

    public ArrayList<Locations> getAllLocations() {
    	
    	ArrayList<Locations> list = locationDAO.showAllLocations();
    	
    	 if (list == null) {
             throw new RuntimeException("No Locations added");
         }
        
        return list;
    }

    public void deleteLocation(String locationID) {

        Locations location = locationDAO.getById(locationID);
        if (location == null) {
            throw new RuntimeException("Location not found: " + locationID);
        }

        locationDAO.delete(locationID);
    }
}

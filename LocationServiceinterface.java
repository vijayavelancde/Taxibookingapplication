package cabbooking;

import java.util.ArrayList;
import java.util.List;

public interface LocationServiceinterface {

    void addLocation(String locationID, String locationName, int distanceFromOrigin);

    Locations getLocationById(String locationID);

    void updateLocationName(String locationID, String newName);

    void updateLocationDistance(String locationID, int newDistance);

    ArrayList<Locations> getAllLocations();

    void deleteLocation(String locationID);
}

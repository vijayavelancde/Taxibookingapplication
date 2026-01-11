package cabbooking;

import java.util.ArrayList;

public interface LocationDAO {

	 void add(Locations location);

	 Locations getById(String locationID);  
	
	 void updateName(String locationID, String name);

	 void updateLocationDistance(String locationID, int distanceFromOrgin);
	 
	 ArrayList<Locations> showAllLocations();
	 
	 void delete(String locationID);
	
}

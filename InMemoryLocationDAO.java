package cabbooking;

import java.util.ArrayList;

public class InMemoryLocationDAO extends InMemoryBaseDAO<Locations> implements LocationDAO{

    public void updateName(String locationID, String name) {
        Locations obj = getById(locationID);
        if (obj == null) return;

        obj.setLocationName(name);
        System.out.println("Location name updated successfully");
    }

    public void updateLocationDistance(String locationID, int distance) {
        Locations obj = getById(locationID);
        if (obj == null) return;

        obj.setLocationDistancefromOrgin(distance);
        System.out.println("Location distance updated successfully");
    }
    
    public ArrayList showAllLocations() {
    	
    	ArrayList<Locations> list = new ArrayList<>();
    	
        if (map.isEmpty()) {
            return null;
        }

        for (Locations loc : map.values()) {
           list.add(loc);        
    }
     
    return list;
    
    }
    
}

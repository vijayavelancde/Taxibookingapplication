package cabbooking;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.PriorityQueue;

public class InMemoryDriverDAO extends InMemoryBaseDAO<Drivers> implements DriverDAO {

    public void updateName(String driverID, String name) {
        Drivers obj = getById(driverID);
        if (obj == null) return;

        obj.setDriverName(name);
    }

    public void updateLocation(String driverID, Locations location) {
        Drivers obj = getById(driverID);
        if (obj == null) return;

        obj.setDriverLocation(location);
    }

    
    public void updateAvailability(String driverID, boolean isAvailable) {
        Drivers obj = getById(driverID);
        if (obj == null) return;

        obj.setAvailable(isAvailable);
    }

    
    public Drivers findBestAvailableDriver(int pickupDistance) {

        PriorityQueue<Drivers> pq = new PriorityQueue<>(
            (d1, d2) -> {
                int diff1 = Math.abs(
                    d1.getDriverLocation().getLocationDistancefromOrgin() - pickupDistance
                );
                int diff2 = Math.abs(
                    d2.getDriverLocation().getLocationDistancefromOrgin() - pickupDistance
                );
                return diff1 - diff2;
            }
        );

        for (Drivers d : map.values()) {
            if (d.isAvailable()) {
                pq.offer(d);
            }
        }

        return pq.poll(); 
    }

    
    public void updateEarnings(String driverID, int amount) {
        Drivers d = getById(driverID);
        if (d != null) {
            d.setTotalEarning(d.getTotalEarning() + amount);
        }
    }

    
    public void incrementCompletedRideCount(String driverID) {
        Drivers d = getById(driverID);
        if (d != null) {
            d.setCompletedRideCount(d.getCompletedRideCount() + 1);
        }
    }

   
    public ArrayList<RideDetails> getDriverRideHistory(String driverID) {

        ArrayList<RideDetails> rideList = new ArrayList<>();

        Drivers driver = getById(driverID);
        if (driver == null) {
            return rideList; 
        }

        HashMap<String, RideDetails> rideHistoryMap = driver.getRideHistory();

        if (rideHistoryMap == null || rideHistoryMap.isEmpty()) {
            return rideList;
        }

        rideList.addAll(rideHistoryMap.values());
        return rideList;
    }
}

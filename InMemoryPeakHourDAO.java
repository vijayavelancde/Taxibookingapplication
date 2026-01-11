package cabbooking;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class InMemoryPeakHourDAO extends InMemoryBaseDAO<PeakHour> implements PeakHourDAO {

    public ArrayList<PeakHour> listAllPeakHours() {
    	
    	ArrayList<PeakHour> peakHourList = new ArrayList<>();
    	
        if (map.isEmpty()) {
            System.out.println("No peak hours found");
            return peakHourList;
        }
        for (PeakHour p : map.values()) {
        	peakHourList.add(p);
        }
        
        return peakHourList;
        
    }

    public boolean isPeakHourNow() {
        LocalTime now = LocalTime.now();

        for (PeakHour p : map.values()) {
            if (p.isWithinPeak(now)) {
                return true;
            }
        }
        return false;
    }
    
    public void delete(String peakHourID) {
        map.remove(peakHourID);
    }

}

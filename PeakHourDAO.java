package cabbooking;

import java.util.ArrayList;
import java.util.List;

public interface PeakHourDAO {

    void add(PeakHour peakHour);

    PeakHour getById(String peakHourID);

    ArrayList<PeakHour> listAllPeakHours();
    
    boolean isPeakHourNow();
    
    void delete(String peakHourID);
}

package cabbooking;

import java.util.List;

public interface PeakHourServiceinterface {

    void addPeakHour(String peakHourID, String startTime, String endTime);

    PeakHour getPeakHourById(String peakHourID);

    List<PeakHour> getAllPeakHours();

    boolean isPeakHourNow();

    void deletePeakHour(String peakHourID);
}


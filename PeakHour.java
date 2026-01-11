package cabbooking;

import java.time.LocalTime;

public class PeakHour implements Identifiable {

    private String peakHourID;
    private LocalTime startTime;
    private LocalTime endTime;

    public PeakHour(String peakHourID, LocalTime startTime, LocalTime endTime) {
        this.peakHourID = peakHourID;
        this.startTime = startTime;
        this.endTime = endTime;
    }

   
    public String getID() {
        return peakHourID;
    }

   
    public String getEntityName() {
        return "PeakHour";
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public boolean isWithinPeak(LocalTime time) {
        return !time.isBefore(startTime) && !time.isAfter(endTime);
    }
}

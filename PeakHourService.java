package cabbooking;

import java.time.LocalTime;
import java.util.List;

public class PeakHourService implements PeakHourServiceinterface {

    private final PeakHourDAO peakHourDAO;

    public PeakHourService(PeakHourDAO peakHourDAO) {
        this.peakHourDAO = peakHourDAO;
    }

    public void addPeakHour(String peakHourID, String startTime, String endTime) {

        PeakHour existing = peakHourDAO.getById(peakHourID);
        if (existing != null) {
            throw new RuntimeException("Peak hour already exists with ID: " + peakHourID);
        }

        LocalTime start = LocalTime.parse(startTime);
        LocalTime end = LocalTime.parse(endTime);

        PeakHour peakHour = new PeakHour(peakHourID, start, end);
        peakHourDAO.add(peakHour);
    }

    public PeakHour getPeakHourById(String peakHourID) {

        PeakHour peakHour = peakHourDAO.getById(peakHourID);
        if (peakHour == null) {
            throw new RuntimeException("Peak hour not found: " + peakHourID);
        }

        return peakHour;
    }

    public List<PeakHour> getAllPeakHours() {
        return peakHourDAO.listAllPeakHours();
    }

    public boolean isPeakHourNow() {
        return peakHourDAO.isPeakHourNow();
    }

    public void deletePeakHour(String peakHourID) {

        PeakHour peakHour = peakHourDAO.getById(peakHourID);
        if (peakHour == null) {
            throw new RuntimeException("Peak hour not found: " + peakHourID);
        }

        peakHourDAO.delete(peakHourID);
    }
}

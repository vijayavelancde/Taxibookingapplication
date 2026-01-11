package cabbooking;

import java.util.ArrayList;
import java.util.HashMap;

public class ReportService implements ReportDAO {

    private final ReportDAO reportDAO;

    public ReportService(ReportDAO reportDAO) {
        this.reportDAO = reportDAO;
    }

    public HashMap<RideDetails.RideStatus, Integer> getRideCountByStatus() {
        return reportDAO.getRideCountByStatus();
    }

    public int getTotalRevenue() {
        return reportDAO.getTotalRevenue();
    }

    public HashMap<String, Integer> getTotalEarningsPerDriver() {
        return reportDAO.getTotalEarningsPerDriver();
    }

    public ArrayList<DriverEarningDTO> getTop5HighestEarningDrivers() {
        return reportDAO.getTop5HighestEarningDrivers();
    }

    public ArrayList<DriverEarningDTO> getBottom5LowestEarningDrivers() {
        return reportDAO.getBottom5LowestEarningDrivers();
    }

    public LocationUsageDTO getMostUsedPickupLocation() {
        return reportDAO.getMostUsedPickupLocation();
    }

    public ArrayList<DriverAnalyticsDTO> getDriverAnalytics() {
        return reportDAO.getDriverAnalytics();
    }

    public int getPeakHourRideCount() {
        return reportDAO.getPeakHourRideCount();
    }

    public int getPeakHourRevenue() {
        return reportDAO.getPeakHourRevenue();
    }

    public ArrayList<PeakHourRevenueDTO> getRevenueByPeakHourSlot() {
        return reportDAO.getRevenueByPeakHourSlot();
    }
}

package cabbooking;

import java.util.ArrayList;
import java.util.HashMap;

public interface ReportDAO  {

    HashMap<RideDetails.RideStatus, Integer> getRideCountByStatus();

    int getTotalRevenue();

    HashMap<String, Integer> getTotalEarningsPerDriver();

    ArrayList<DriverEarningDTO> getTop5HighestEarningDrivers();

    ArrayList<DriverEarningDTO> getBottom5LowestEarningDrivers();

    LocationUsageDTO getMostUsedPickupLocation();

    ArrayList<DriverAnalyticsDTO> getDriverAnalytics();

    int getPeakHourRideCount();

    int getPeakHourRevenue();

    ArrayList<PeakHourRevenueDTO> getRevenueByPeakHourSlot();
}

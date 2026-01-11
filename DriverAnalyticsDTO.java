package cabbooking;

public class DriverAnalyticsDTO {

    private String driverId;
    private String driverName;
    private int totalRides;
    private int totalEarning;
    private double avgFare;

    public DriverAnalyticsDTO(String driverId, String driverName,
                              int totalRides, int totalEarning, double avgFare) {
        this.driverId = driverId;
        this.driverName = driverName;
        this.totalRides = totalRides;
        this.totalEarning = totalEarning;
        this.avgFare = avgFare;
    }

    public String getDriverId() {
        return driverId;
    }

    public String getDriverName() {
        return driverName;
    }

    public int getTotalRides() {
        return totalRides;
    }

    public int getTotalEarning() {
        return totalEarning;
    }

    public double getAvgFare() {
        return avgFare;
    }
}


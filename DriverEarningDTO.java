package cabbooking;

public class DriverEarningDTO {
   
	private String driverId;
    private int totalEarning;

    public DriverEarningDTO(String driverId, int totalEarning) {
        this.driverId = driverId;
        this.totalEarning = totalEarning;
    }

    public String getDriverId() {
        return driverId;
    }

    public int getTotalEarning() {
        return totalEarning;
    }
}

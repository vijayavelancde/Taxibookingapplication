package cabbooking;

public class PeakHourRevenueDTO {
    
	
	private String peakSlot;
    private int totalRevenue;

    public PeakHourRevenueDTO(String peakSlot, int totalRevenue) {
        this.peakSlot = peakSlot;
        this.totalRevenue = totalRevenue;
    }

    public String getPeakSlot() {
        return peakSlot;
    }

    public int getTotalRevenue() {
        return totalRevenue;
    }
}

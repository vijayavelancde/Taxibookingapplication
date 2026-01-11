package cabbooking;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class JdbcRideDAO implements RideDAO {


    public void createRide(RideDetails ride) {
      
    	if (getRideById(ride.getRideID()) != null) {
    	    return;
    	}
    	
        String sql = """
            INSERT INTO rideDetails
            (ride_id, driver_id, customer_id,
             pickup_location_id, drop_location_id,
             ride_distance, ride_fare, ride_status)
            VALUES (?, ?, ?, ?, ?, ?, ?, ?)
        """;

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, ride.getRideID());
            ps.setString(2, ride.getDriverID());
            ps.setString(3, ride.getCustomerID());
            ps.setString(4, ride.getPickupLocationID());
            ps.setString(5, ride.getDropLocationID());
            ps.setInt(6, ride.getRideDistance());
            ps.setInt(7, ride.getRidefair());
            ps.setString(8, ride.getRideStatus().name());

            ps.executeUpdate();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void updateRideStatus(
            String rideID,
            RideDetails.RideStatus status,
            int fare) {

        String sql = """
            UPDATE rideDetails
            SET ride_status = ?, ride_fare = ?
            WHERE ride_id = ?
        """;

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, status.name());
            ps.setInt(2, fare);
            ps.setString(3, rideID);

            ps.executeUpdate();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    
    public void updateRideStatus(
            Connection con,
            String rideID,
            RideDetails.RideStatus status,
            int fare) throws Exception {

        String sql = """
            UPDATE rideDetails
            SET ride_status = ?, ride_fare = ?
            WHERE ride_id = ?
        """;

        try (PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, status.name());
            ps.setInt(2, fare);
            ps.setString(3, rideID);

            ps.executeUpdate();
        }
    }

    
    public RideDetails getRideById(String rideID) {

        String sql = "SELECT * FROM rideDetails WHERE ride_id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, rideID);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new RideDetails(
                    rs.getString("ride_id"),
                    rs.getString("driver_id"),
                    rs.getString("customer_id"),
                    rs.getString("pickup_location_id"),
                    rs.getString("drop_location_id"),
                    rs.getInt("ride_distance"),
                    rs.getInt("ride_fare"),+
                    RideDetails.RideStatus.valueOf(
                        rs.getString("ride_status")
                    )
                );
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}

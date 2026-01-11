package cabbooking;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JdbcDriverDAO implements DriverDAO {

    
    public void add(Drivers driver) {

        if (driverExists(driver.getDriverID())) {
            return;
        }

        String sql = """
            INSERT INTO drivers
            (driver_id, drivername, location_id, available, total_earning, completed_ride_count, avg_rating, rating_count)
            VALUES (?, ?, ?, ?, ?, ?, ?, ?)
        """;

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, driver.getDriverID());
            ps.setString(2, driver.getDriverName());
            ps.setString(3, driver.getDriverLocation().getLocationID());
            ps.setBoolean(4, driver.isAvailable());
            ps.setInt(5, driver.getTotalEarning());
            ps.setInt(6, driver.getCompletedRideCount());
            ps.setInt(7, driver.getDriverRating());
            ps.setInt(8, driver.getDriverRatingCount());

            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


   
    public Drivers getById(String driverID) {

        String sql = """
            SELECT
                d.driver_id,
                d.drivername,
                d.available,
                d.total_earning,
                d.completed_ride_count,
                d.avg_rating,
                d.rating_count,
                l.location_id,
                l.location_name,
                l.distance_from_origin
            FROM drivers d
            JOIN locations l ON d.location_id = l.location_id
            WHERE d.driver_id = ?
        """;

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, driverID);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                Locations location = new Locations(
                        rs.getString("location_id"),
                        rs.getString("location_name"),
                        rs.getInt("distance_from_origin")
                );

                return new Drivers(
                        rs.getString("driver_id"),
                        rs.getString("drivername"),
                        location,
                        rs.getBoolean("available"),
                        rs.getInt("total_earning"),
                        rs.getInt("completed_ride_count"),
                        rs.getInt("avg_rating"),
                        rs.getInt("rating_count")
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }


   
    public void updateName(String driverID, String name) {
        executeUpdate(
                "UPDATE drivers SET drivername = ? WHERE driver_id = ?",
                name, driverID
        );
    }


   
    public void updateLocation(String driverID, Locations location) {
        executeUpdate(
                "UPDATE drivers SET location_id = ? WHERE driver_id = ?",
                location.getLocationID(), driverID
        );
    }


   
    public void updateAvailability(String driverID, boolean isAvailable) {
        executeUpdate(
                "UPDATE drivers SET available = ? WHERE driver_id = ?",
                isAvailable, driverID
        );
    }



    public Drivers findBestAvailableDriver(int pickupDistance) {

        String sql = """
            SELECT
                d.driver_id,
                d.drivername,
                d.available,
                d.total_earning,
                d.completed_ride_count,
                d.avg_rating,
                d.rating_count,
                l.location_id,
                l.location_name,
                l.distance_from_origin
            FROM drivers d
            JOIN locations l ON d.location_id = l.location_id
            WHERE d.available = true
            ORDER BY ABS(l.distance_from_origin - ?) ASC
            LIMIT 1
        """;

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, pickupDistance);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                Locations loc = new Locations(
                        rs.getString("location_id"),
                        rs.getString("location_name"),
                        rs.getInt("distance_from_origin")
                );

                return new Drivers(
                        rs.getString("driver_id"),
                        rs.getString("drivername"),
                        loc,
                        rs.getBoolean("available"),
                        rs.getInt("total_earning"),
                        rs.getInt("completed_ride_count"),
                        rs.getInt("avg_rating"),
                        rs.getInt("rating_count")
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }


   
    public void updateEarnings(String driverID, int amount) {

        String sql = """
            UPDATE drivers
            SET total_earning = total_earning + ?
            WHERE driver_id = ?
        """;

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, amount);
            ps.setString(2, driverID);
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


   
    public void incrementCompletedRideCount(String driverID) {

        String sql = """
            UPDATE drivers
            SET completed_ride_count = completed_ride_count + 1
            WHERE driver_id = ?
        """;

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, driverID);
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


   
    public ArrayList<RideDetails> getDriverRideHistory(String driverID) {

        ArrayList<RideDetails> rideList = new ArrayList<>();

        String sql = """
            SELECT
                ride_id,
                customer_id,
                pickup_location_id,
                drop_location_id,
                ride_fare,
                ride_status
            FROM rideDetails
            WHERE driver_id = ?
        """;

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, driverID);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                RideDetails ride = new RideDetails();
                
                ride.setRideID(rs.getString("ride_id"));                          
                ride.setCustomerID(rs.getString("customer_id"));
                ride.setPickupLocationID(rs.getString("pickup_location_id"));
                ride.setDropLocationID(rs.getString("drop_location_id"));
                ride.setRidefair(rs.getInt("ride_fare"));                         
                ride.setRideStatus(RideDetails.RideStatus.valueOf(rs.getString("ride_status")));

                rideList.add(ride);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return rideList;
    }


    private boolean driverExists(String driverID) {

        String sql = "SELECT driver_id FROM drivers WHERE driver_id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, driverID);
            ResultSet rs = ps.executeQuery();

            return rs.next();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }


    private void executeUpdate(String sql, Object... params) {

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            for (int i = 0; i < params.length; i++) {
                ps.setObject(i + 1, params[i]);
            }

            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

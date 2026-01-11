package cabbooking;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JdbcReportDAO implements ReportDAO {


    public HashMap<RideDetails.RideStatus, Integer> getRideCountByStatus() {

        String sql = """
            SELECT ride_status, COUNT(*) AS cnt
            FROM rideDetails
            GROUP BY ride_status
        """;

        HashMap<RideDetails.RideStatus, Integer> map = new HashMap<>();

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                RideDetails.RideStatus status =
                        RideDetails.RideStatus.valueOf(rs.getString("ride_status"));
                int count = rs.getInt("cnt");
                map.put(status, count);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return map;
    }


    public int getTotalRevenue() {

        String sql = """
            SELECT SUM(ride_fare) AS total_revenue
            FROM rideDetails
            WHERE ride_status = 'COMPLETED'
        """;

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                return rs.getInt("total_revenue");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }

   
    public HashMap<String, Integer> getTotalEarningsPerDriver() {

        String sql = """
            SELECT driver_id, SUM(ride_fare) AS total_earning
            FROM rideDetails
            WHERE ride_status = 'COMPLETED'
            GROUP BY driver_id
        """;

        HashMap<String, Integer> map = new HashMap<>();

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                map.put(
                    rs.getString("driver_id"),
                    rs.getInt("total_earning")
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return map;
    }

    
    public ArrayList<DriverEarningDTO> getTop5HighestEarningDrivers() {

        String sql = """
            SELECT driver_id, SUM(ride_fare) AS total_earning
            FROM rideDetails
            WHERE ride_status = 'COMPLETED'
            GROUP BY driver_id
            ORDER BY total_earning DESC
            LIMIT 5
        """;

        ArrayList<DriverEarningDTO> list = new ArrayList<>();

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(new DriverEarningDTO(
                        rs.getString("driver_id"),
                        rs.getInt("total_earning")
                ));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }


    public ArrayList<DriverEarningDTO> getBottom5LowestEarningDrivers() {

        String sql = """
            SELECT driver_id, SUM(ride_fare) AS total_earning
            FROM rideDetails
            WHERE ride_status = 'COMPLETED'
            GROUP BY driver_id
            ORDER BY total_earning ASC
            LIMIT 5
        """;

        ArrayList<DriverEarningDTO> list = new ArrayList<>();

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(new DriverEarningDTO(
                        rs.getString("driver_id"),
                        rs.getInt("total_earning")
                ));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }


    public LocationUsageDTO getMostUsedPickupLocation() {

        String sql = """
            SELECT pickup_location_id, COUNT(*) AS ride_count
            FROM rideDetails
            WHERE ride_status = 'COMPLETED'
            GROUP BY pickup_location_id
            ORDER BY ride_count DESC
            LIMIT 1
        """;

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                return new LocationUsageDTO(
                        rs.getString("pickup_location_id"),
                        rs.getInt("ride_count")
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }


    public ArrayList<DriverAnalyticsDTO> getDriverAnalytics() {

        String sql = """
            SELECT 
                d.driver_id,
                d.driver_name,
                COUNT(r.ride_id) AS total_rides,
                SUM(r.ride_fare) AS total_earning,
                AVG(r.ride_fare) AS avg_fare
            FROM drivers d
            JOIN rideDetails r ON d.driver_id = r.driver_id
            WHERE r.ride_status = 'COMPLETED'
            GROUP BY d.driver_id, d.driver_name
        """;

        ArrayList<DriverAnalyticsDTO> list = new ArrayList<>();

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(new DriverAnalyticsDTO(
                        rs.getString("driver_id"),
                        rs.getString("driver_name"),
                        rs.getInt("total_rides"),
                        rs.getInt("total_earning"),
                        rs.getDouble("avg_fare")
                ));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }


    public int getPeakHourRideCount() {

        String sql = """
            SELECT COUNT(*) AS cnt
            FROM rideDetails r
            JOIN peak_hours p
              ON r.ride_time BETWEEN p.start_time AND p.end_time
            WHERE r.ride_status = 'COMPLETED'
        """;

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                return rs.getInt("cnt");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }

    
    public int getPeakHourRevenue() {

        String sql = """
            SELECT SUM(r.ride_fare) AS total_revenue
            FROM rideDetails r
            JOIN peak_hours p
              ON r.ride_time BETWEEN p.start_time AND p.end_time
            WHERE r.ride_status = 'COMPLETED'
        """;

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                return rs.getInt("total_revenue");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }

    
    public ArrayList<PeakHourRevenueDTO> getRevenueByPeakHourSlot() {

        String sql = """
            SELECT 
                p.peak_id,
                SUM(r.ride_fare) AS total_revenue
            FROM rideDetails r
            JOIN peak_hours p
              ON r.ride_time BETWEEN p.start_time AND p.end_time
            WHERE r.ride_status = 'COMPLETED'
            GROUP BY p.peak_id
        """;

        ArrayList<PeakHourRevenueDTO> list = new ArrayList<>();

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(new PeakHourRevenueDTO(
                        rs.getString("peak_id"),
                        rs.getInt("total_revenue")
                ));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
}


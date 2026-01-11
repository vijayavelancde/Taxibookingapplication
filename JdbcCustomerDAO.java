package cabbooking;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class JdbcCustomerDAO implements CustomerDAO {

    
    public void add(Customers customer) {

        String sql = """
            INSERT INTO customers (customerID, customerName, phone)
            VALUES (?, ?, ?)
        """;

        executeUpdate(sql,
                customer.getCustomerID(),
                customer.getCustomerName(),
                customer.getPhone()
        );
    }


    
    public Customers getById(String customerID) {

        String sql = "SELECT * FROM customers WHERE customerID = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, customerID);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new Customers(
                        rs.getString("customerID"),
                        rs.getString("customerName"),
                        rs.getLong("phone")
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    
    public void updateCustomerName(String customerID, String customerName) {

        String sql = "UPDATE customers SET customerName = ? WHERE customerID = ?";

        executeUpdate(sql,
                customerName,
                customerID
        );
    }


    @Override
    public void updateCustomerPhone(String customerID, long customerPhoneNumber) {

        String sql = "UPDATE customers SET phone = ? WHERE customerID = ?";

        executeUpdate(sql,
                customerPhoneNumber,
                customerID
        );
    }


    
    public boolean isAnExistingCustomer(String customerID) {

        String sql = "SELECT customerID FROM customers WHERE customerID = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, customerID);
            ResultSet rs = ps.executeQuery();

            return rs.next();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }


   
    public ArrayList<RideDetails> getCustomerRides(String customerID) {

        ArrayList<RideDetails> list = new ArrayList<>();

        String sql = """
            SELECT ride_id,
                   pickup_location_id,
                   drop_location_id,
                   ride_status,
                   ride_fare
            FROM rideDetails
            WHERE customer_id = ?
        """;

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, customerID);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                RideDetails ride = new RideDetails();
                ride.setRideID(rs.getString("ride_id"));
                ride.setPickupLocationID(rs.getString("pickup_location_id"));
                ride.setDropLocationID(rs.getString("drop_location_id"));
                ride.setRideStatus(RideDetails.RideStatus.valueOf(rs.getString("ride_status")));
                ride.setRidefair(rs.getInt("ride_fare"));

                list.add(ride);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }


    
    public void delete(String customerID) {

        String sql = "DELETE FROM customers WHERE customerID = ?";

        executeUpdate(sql, customerID);
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

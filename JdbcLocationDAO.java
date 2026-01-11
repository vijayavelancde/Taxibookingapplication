package cabbooking;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class JdbcLocationDAO implements LocationDAO {

    
    public void add(Locations location) {
    	
    	 if (locationExists(location.getLocationID())) {
    	        return; 
    	    }


        String sql = """
            INSERT INTO locations (location_id,location_name, distance_from_origin)
            VALUES (?, ?, ?)
        """;

        executeUpdate(sql,
                location.getLocationID(),
                location.getLocationName(),
                location.getLocationDistancefromOrgin()
        );
    }

    
    public Locations getById(String locationID) {

        String sql = "SELECT * FROM locations WHERE location_id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, locationID);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new Locations(
                        rs.getString("location_id"),
                        rs.getString("location_name"),
                        rs.getInt("distance_from_origin")
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    
    public void updateName(String locationID, String name) {

        String sql = "UPDATE locations SET location_name = ? WHERE location_id = ?";

        executeUpdate(sql,
               name,locationID
        );
    }
    
    public void updateLocationDistance(String locationID, int distance) {

        String sql = "UPDATE locations SET distance_from_origin = ? WHERE location_id = ?";

        executeUpdate(sql,
                distance, locationID );
    }
  
    public void delete(String locationID) {

        String sql = "DELETE FROM locations WHERE location_id = ?";

        executeUpdate(sql, locationID);
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

    public ArrayList<Locations> showAllLocations() {

    	ArrayList<Locations> list = new ArrayList<>();

        String sql = "SELECT location_id, location_name, distance_from_origin FROM locations";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Locations loc = new Locations(
                        rs.getString("location_id"),
                        rs.getString("location_name"),
                        rs.getInt("distance_from_origin")
                );
                list.add(loc);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
    
    
	private boolean locationExists(String locationID) {

	    String sql = "SELECT location_id FROM locations WHERE location_id = ?";

	    try (Connection con = DBConnection.getConnection();
	         PreparedStatement ps = con.prepareStatement(sql)) {

	        ps.setString(1, locationID);
	        ResultSet rs = ps.executeQuery();

	        return rs.next(); 

	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	    return false;
	}

	
}

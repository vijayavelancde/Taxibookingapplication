package cabbooking;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class JdbcPeakHourDAO implements PeakHourDAO {

   
    public void add(PeakHour peakHour) {

        String sql = """
            INSERT INTO peak_hours (peak_hour_id, start_time, end_time)
            VALUES (?, ?, ?)
        """;

        executeUpdate(sql,
                peakHour.getID(),
                peakHour.getStartTime(),
                peakHour.getEndTime()
        );
    }


    
    public PeakHour getById(String peakHourID) {

        String sql = "SELECT * FROM peak_hours WHERE peak_hour_id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, peakHourID);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new PeakHour(
                        rs.getString("peak_hour_id"),
                        rs.getTime("start_time").toLocalTime(),
                        rs.getTime("end_time").toLocalTime()
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


   
   
    public boolean isPeakHourNow() {

        LocalTime now = LocalTime.now();

        String sql = "SELECT start_time, end_time FROM peak_hours";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                LocalTime start = rs.getTime("start_time").toLocalTime();
                LocalTime end = rs.getTime("end_time").toLocalTime();

                if (isTimeInRange(now, start, end)) {
                    return true;
                }
            }

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


    private boolean isTimeInRange(LocalTime now,
                                  LocalTime start,
                                  LocalTime end) {

        if (start.isBefore(end)) {
            return !now.isBefore(start) && !now.isAfter(end);
        }

        
        return !now.isBefore(start) || !now.isAfter(end);
    }

    
    public ArrayList<PeakHour> listAllPeakHours() {

        ArrayList<PeakHour> list = new ArrayList<>();
        String sql = "SELECT peak_hour_id, start_time, end_time FROM peak_hours";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                PeakHour p = new PeakHour(
                        rs.getString("peak_hour_id"),   
                        rs.getTime("start_time").toLocalTime(),
                        rs.getTime("end_time").toLocalTime()
                );

                list.add(p);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

}

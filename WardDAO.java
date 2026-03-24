import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class WardDAO {

    // Add ward
    public void addWard(Ward w) {
        String sql = "INSERT INTO wards (ward_name, room_number, capacity) VALUES (?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, w.getWardName());
            stmt.setString(2, w.getRoomNumber());
            stmt.setInt(3, w.getCapacity());

            int rows = stmt.executeUpdate();
            if (rows > 0) System.out.println("✅ Ward added successfully!");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Fetch all wards
    public List<Ward> getAllWards() {
        List<Ward> wards = new ArrayList<>();
        String sql = "SELECT * FROM wards";
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Ward w = new Ward(
                    rs.getInt("id"),
                    rs.getString("ward_name"),
                    rs.getString("room_number"),
                    rs.getInt("capacity")
                );
                wards.add(w);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return wards;
    }

    // Update ward
    public void updateWard(Ward w) {
        String sql = "UPDATE wards SET ward_name=?, room_number=?, capacity=? WHERE id=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, w.getWardName());
            stmt.setString(2, w.getRoomNumber());
            stmt.setInt(3, w.getCapacity());
            stmt.setInt(4, w.getId());

            int rows = stmt.executeUpdate();
            if (rows > 0) System.out.println("✅ Ward updated successfully!");
            else System.out.println("❌ No ward found with this ID.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Delete ward
    public void deleteWard(int id) {
        String sql = "DELETE FROM wards WHERE id=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            int rows = stmt.executeUpdate();
            if (rows > 0) System.out.println("✅ Ward deleted successfully!");
            else System.out.println("❌ No ward found with this ID.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

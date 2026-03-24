import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MedicineDAO {

    // Add medicine
    public void addMedicine(Medicine m) {
        String sql = "INSERT INTO medicines (name, type, price) VALUES (?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, m.getName());
            stmt.setString(2, m.getType());
            stmt.setDouble(3, m.getPrice());

            int rows = stmt.executeUpdate();
            if (rows > 0) System.out.println("✅ Medicine added successfully!");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Fetch all medicines
    public List<Medicine> getAllMedicines() {
        List<Medicine> medicines = new ArrayList<>();
        String sql = "SELECT * FROM medicines";
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Medicine m = new Medicine(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("type"),
                    rs.getDouble("price")
                );
                medicines.add(m);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return medicines;
    }

    // Update medicine
    public void updateMedicine(Medicine m) {
        String sql = "UPDATE medicines SET name=?, type=?, price=? WHERE id=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, m.getName());
            stmt.setString(2, m.getType());
            stmt.setDouble(3, m.getPrice());
            stmt.setInt(4, m.getId());

            int rows = stmt.executeUpdate();
            if (rows > 0) System.out.println("✅ Medicine updated successfully!");
            else System.out.println("❌ No medicine found with this ID.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Delete medicine
    public void deleteMedicine(int id) {
        String sql = "DELETE FROM medicines WHERE id=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            int rows = stmt.executeUpdate();
            if (rows > 0) System.out.println("✅ Medicine deleted successfully!");
            else System.out.println("❌ No medicine found with this ID.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

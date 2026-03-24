import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DoctorDAO {

    // Add doctor
    public void addDoctor(Doctor d) {
        String sql = "INSERT INTO doctors (name, specialization, contact) VALUES (?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, d.getName());
            stmt.setString(2, d.getSpecialization());
            stmt.setString(3, d.getContact());

            int rows = stmt.executeUpdate();
            if (rows > 0) System.out.println("✅ Doctor added successfully!");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Fetch all doctors
    public List<Doctor> getAllDoctors() {
        List<Doctor> doctors = new ArrayList<>();
        String sql = "SELECT * FROM doctors";
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Doctor d = new Doctor(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("specialization"),
                    rs.getString("contact")
                );
                doctors.add(d);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return doctors;
    }

    // Update doctor
    public void updateDoctor(Doctor d) {
        String sql = "UPDATE doctors SET name=?, specialization=?, contact=? WHERE id=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, d.getName());
            stmt.setString(2, d.getSpecialization());
            stmt.setString(3, d.getContact());
            stmt.setInt(4, d.getId());

            int rows = stmt.executeUpdate();
            if (rows > 0) System.out.println("✅ Doctor updated successfully!");
            else System.out.println("❌ No doctor found with this ID.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Delete doctor
    public void deleteDoctor(int id) {
        String sql = "DELETE FROM doctors WHERE id=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            int rows = stmt.executeUpdate();
            if (rows > 0) System.out.println("✅ Doctor deleted successfully!");
            else System.out.println("❌ No doctor found with this ID.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

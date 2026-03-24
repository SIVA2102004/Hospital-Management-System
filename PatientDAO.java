import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PatientDAO {

    // Insert new patient
    public void addPatient(Patient p) {
        String sql = "INSERT INTO patients (name, age, gender, disease, contact) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, p.getName());
            stmt.setInt(2, p.getAge());
            stmt.setString(3, p.getGender());
            stmt.setString(4, p.getDisease());
            stmt.setString(5, p.getContact());
            
            int rows = stmt.executeUpdate();
            if (rows > 0) System.out.println("✅ Patient added successfully!");
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Fetch all patients
    public List<Patient> getAllPatients() {
        List<Patient> patients = new ArrayList<>();
        String sql = "SELECT * FROM patients";
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Patient p = new Patient(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getInt("age"),
                    rs.getString("gender"),
                    rs.getString("disease"),
                    rs.getString("contact")
                );
                patients.add(p);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return patients;
    }

    // Update patient by ID
    public void updatePatient(Patient p) {
        String sql = "UPDATE patients SET name=?, age=?, gender=?, disease=?, contact=? WHERE id=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, p.getName());
            stmt.setInt(2, p.getAge());
            stmt.setString(3, p.getGender());
            stmt.setString(4, p.getDisease());
            stmt.setString(5, p.getContact());
            stmt.setInt(6, p.getId());

            int rows = stmt.executeUpdate();
            if (rows > 0) System.out.println("✅ Patient updated successfully!");
            else System.out.println("❌ No patient found with this ID.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Delete patient by ID
    public void deletePatient(int id) {
        String sql = "DELETE FROM patients WHERE id=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            int rows = stmt.executeUpdate();
            if (rows > 0) System.out.println("✅ Patient deleted successfully!");
            else System.out.println("❌ No patient found with this ID.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

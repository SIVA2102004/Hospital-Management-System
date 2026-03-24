import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PrescriptionDAO {

    // Add prescription
    public void addPrescription(Prescription p) {
        String sql = "INSERT INTO prescriptions (patient_id, doctor_id, medicine_id, dosage, date) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, p.getPatientId());
            stmt.setInt(2, p.getDoctorId());
            stmt.setInt(3, p.getMedicineId());
            stmt.setString(4, p.getDosage());
            stmt.setDate(5, p.getDate());

            int rows = stmt.executeUpdate();
            if (rows > 0) System.out.println("✅ Prescription added successfully!");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Fetch all prescriptions
    public List<Prescription> getAllPrescriptions() {
        List<Prescription> prescriptions = new ArrayList<>();
        String sql = "SELECT * FROM prescriptions";
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Prescription p = new Prescription(
                    rs.getInt("id"),
                    rs.getInt("patient_id"),
                    rs.getInt("doctor_id"),
                    rs.getInt("medicine_id"),
                    rs.getString("dosage"),
                    rs.getDate("date")
                );
                prescriptions.add(p);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return prescriptions;
    }

    // Delete prescription
    public void deletePrescription(int id) {
        String sql = "DELETE FROM prescriptions WHERE id=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            int rows = stmt.executeUpdate();
            if (rows > 0) System.out.println("✅ Prescription deleted successfully!");
            else System.out.println("❌ No prescription found with this ID.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PatientWardDAO {

    // Assign patient to ward
    public void addPatientWard(PatientWard pw) {
        String sql = "INSERT INTO patient_ward (patient_id, ward_id, admit_date, discharge_date) VALUES (?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, pw.getPatientId());
            stmt.setInt(2, pw.getWardId());
            stmt.setDate(3, pw.getAdmitDate());
            stmt.setDate(4, pw.getDischargeDate());

            int rows = stmt.executeUpdate();
            if (rows > 0) System.out.println("✅ Patient assigned to ward successfully!");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Fetch all patient-ward assignments
    public List<PatientWard> getAllPatientWards() {
        List<PatientWard> list = new ArrayList<>();
        String sql = "SELECT * FROM patient_ward";
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                PatientWard pw = new PatientWard(
                    rs.getInt("id"),
                    rs.getInt("patient_id"),
                    rs.getInt("ward_id"),
                    rs.getDate("admit_date"),
                    rs.getDate("discharge_date")
                );
                list.add(pw);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // Discharge patient
    public void deletePatientWard(int id) {
        String sql = "DELETE FROM patient_ward WHERE id=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            int rows = stmt.executeUpdate();
            if (rows > 0) System.out.println("✅ Patient discharged from ward successfully!");
            else System.out.println("❌ No assignment found with this ID.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

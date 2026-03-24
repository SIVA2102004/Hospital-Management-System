import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AppointmentDAO {

    private Connection conn;

    // ✅ Constructor automatically connects to DB
    public AppointmentDAO() {
        this.conn = DBConnection.getConnection();
        if (this.conn == null) {
            System.out.println("❌ Database connection failed in AppointmentDAO!");
        }
    }

    // ✅ Add (book) a new appointment
    public void addAppointment(Appointment a) {
        String sql = "INSERT INTO appointments (patient_id, doctor_id, appointment_date) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, a.getPatientId());
            stmt.setInt(2, a.getDoctorId());
            // ✅ Use Timestamp instead of Date for full date-time precision
            stmt.setTimestamp(3, new java.sql.Timestamp(a.getAppointmentDate().getTime()));

            int rows = stmt.executeUpdate();
            if (rows > 0) {
                System.out.println("✅ Appointment booked successfully!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // ✅ Create new appointment (returns new generated ID)
    public int createAppointment(int patientId, int doctorId, java.util.Date date) {
        String sql = "INSERT INTO appointments (patient_id, doctor_id, appointment_date) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, patientId);
            stmt.setInt(2, doctorId);
            stmt.setTimestamp(3, new java.sql.Timestamp(date.getTime())); // ✅ fixed

            int rows = stmt.executeUpdate();
            if (rows > 0) {
                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        int newId = rs.getInt(1);
                        System.out.println("✅ Appointment created successfully (ID=" + newId + ")");
                        return newId;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    // ✅ Update existing appointment's date/time
    public boolean updateAppointmentDate(int appointmentId, java.util.Date newDate) {
        String sql = "UPDATE appointments SET appointment_date=? WHERE id=?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setTimestamp(1, new java.sql.Timestamp(newDate.getTime()));
            stmt.setInt(2, appointmentId);
            int rows = stmt.executeUpdate();
            if (rows > 0) {
                System.out.println("✅ Appointment ID " + appointmentId + " updated successfully!");
                return true;
            } else {
                System.out.println("❌ Appointment ID " + appointmentId + " not found.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // ✅ Fetch all appointments
    public List<Appointment> getAllAppointments() {
        List<Appointment> appointments = new ArrayList<>();
        String sql = "SELECT * FROM appointments ORDER BY appointment_date";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Appointment a = new Appointment(
                    rs.getInt("id"),
                    rs.getInt("patient_id"),
                    rs.getInt("doctor_id"),
                    rs.getTimestamp("appointment_date") // ✅ supports both date + time
                );
                appointments.add(a);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return appointments;
    }

    // ✅ Delete appointment by ID
    public void deleteAppointment(int id) {
        String sql = "DELETE FROM appointments WHERE id=?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            int rows = stmt.executeUpdate();
            if (rows > 0) {
                System.out.println("✅ Appointment deleted successfully!");
            } else {
                System.out.println("❌ No appointment found with this ID.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

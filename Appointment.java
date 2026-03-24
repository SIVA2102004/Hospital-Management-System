import java.util.Date;

/**
 * Appointment model class representing an appointment record.
 * Compatible with both GUI and DAO layers.
 */
public class Appointment {
    private int id;
    private int patientId;
    private int doctorId;
    private Date appointmentDate;

    // ✅ Constructor without ID (used when creating new appointment)
    public Appointment(int patientId, int doctorId, Date appointmentDate) {
        this.patientId = patientId;
        this.doctorId = doctorId;
        this.appointmentDate = appointmentDate;
    }

    // ✅ Constructor with ID (used when fetching from DB)
    public Appointment(int id, int patientId, int doctorId, Date appointmentDate) {
        this.id = id;
        this.patientId = patientId;
        this.doctorId = doctorId;
        this.appointmentDate = appointmentDate;
    }

    // ✅ Getters
    public int getId() { return id; }
    public int getPatientId() { return patientId; }
    public int getDoctorId() { return doctorId; }
    public Date getAppointmentDate() { return appointmentDate; }

    // ✅ Setters (optional but helpful for updates)
    public void setId(int id) { this.id = id; }
    public void setPatientId(int patientId) { this.patientId = patientId; }
    public void setDoctorId(int doctorId) { this.doctorId = doctorId; }
    public void setAppointmentDate(Date appointmentDate) { this.appointmentDate = appointmentDate; }

    @Override
    public String toString() {
        return "Appointment{" +
                "id=" + id +
                ", patientId=" + patientId +
                ", doctorId=" + doctorId +
                ", appointmentDate=" + appointmentDate +
                '}';
    }
}

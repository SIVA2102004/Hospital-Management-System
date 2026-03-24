import java.sql.Date;

public class Prescription {
    private int id;
    private int patientId;
    private int doctorId;
    private int medicineId;
    private String dosage;
    private Date date;

    public Prescription(int patientId, int doctorId, int medicineId, String dosage, Date date) {
        this.patientId = patientId;
        this.doctorId = doctorId;
        this.medicineId = medicineId;
        this.dosage = dosage;
        this.date = date;
    }

    public Prescription(int id, int patientId, int doctorId, int medicineId, String dosage, Date date) {
        this.id = id;
        this.patientId = patientId;
        this.doctorId = doctorId;
        this.medicineId = medicineId;
        this.dosage = dosage;
        this.date = date;
    }

    // Getters
    public int getId() { return id; }
    public int getPatientId() { return patientId; }
    public int getDoctorId() { return doctorId; }
    public int getMedicineId() { return medicineId; }
    public String getDosage() { return dosage; }
    public Date getDate() { return date; }
}

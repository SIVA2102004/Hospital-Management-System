import java.sql.Date;

public class PatientWard {
    private int id;
    private int patientId;
    private int wardId;
    private Date admitDate;
    private Date dischargeDate;

    // Constructor without ID
    public PatientWard(int patientId, int wardId, Date admitDate, Date dischargeDate) {
        this.patientId = patientId;
        this.wardId = wardId;
        this.admitDate = admitDate;
        this.dischargeDate = dischargeDate;
    }

    // Constructor with ID
    public PatientWard(int id, int patientId, int wardId, Date admitDate, Date dischargeDate) {
        this.id = id;
        this.patientId = patientId;
        this.wardId = wardId;
        this.admitDate = admitDate;
        this.dischargeDate = dischargeDate;
    }

    // Getters
    public int getId() { return id; }
    public int getPatientId() { return patientId; }
    public int getWardId() { return wardId; }
    public Date getAdmitDate() { return admitDate; }
    public Date getDischargeDate() { return dischargeDate; }
}

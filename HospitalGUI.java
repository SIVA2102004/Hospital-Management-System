import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class HospitalGUI extends JFrame {

    public HospitalGUI() {
        setTitle("Hospital Management System");
        setSize(800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Menu Panel
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(8, 2, 10, 10));

        JButton btnPatients = new JButton("Manage Patients");
        JButton btnDoctors = new JButton("Manage Doctors");
        JButton btnAppointments = new JButton("Manage Appointments");
        JButton btnMedicines = new JButton("Manage Medicines");
        JButton btnPrescriptions = new JButton("Manage Prescriptions");
        JButton btnWards = new JButton("Manage Wards");
        JButton btnPatientWard = new JButton("Manage Patient-Ward");
        JButton btnExit = new JButton("Exit");

        panel.add(btnPatients);
        panel.add(btnDoctors);
        panel.add(btnAppointments);
        panel.add(btnMedicines);
        panel.add(btnPrescriptions);
        panel.add(btnWards);
        panel.add(btnPatientWard);
        panel.add(btnExit);

        add(panel);

        // Button Actions
        btnPatients.addActionListener(e -> new PatientGUI());
        btnDoctors.addActionListener(e -> new DoctorGUI());
        btnAppointments.addActionListener(e -> new AppointmentGUI());
        btnMedicines.addActionListener(e -> new MedicineGUI());
        btnPrescriptions.addActionListener(e -> new PrescriptionGUI());
        btnWards.addActionListener(e -> new WardGUI());
        btnPatientWard.addActionListener(e -> new PatientWardGUI());
        btnExit.addActionListener(e -> System.exit(0));

        setVisible(true);
    }

    public static void main(String[] args) {
        new HospitalGUI();
    }
}

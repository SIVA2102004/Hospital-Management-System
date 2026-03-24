import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

/**
 * 🏥 Hospital Management System - Main Dashboard
 * 
 * This version replaces the console menu with a GUI dashboard.
 * It allows access to all modules like Patient, Doctor, Appointment, Ward, and Prescription.
 */
public class HospitalSystem extends JFrame {

    public HospitalSystem() {
        setTitle("🏥 Hospital Management System");
        setSize(600, 420);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // ===== Header Label =====
        JLabel title = new JLabel("Hospital Management Dashboard", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 22));
        title.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 0));
        add(title, BorderLayout.NORTH);

        // ===== Center Panel (Buttons Grid) =====
        JPanel centerPanel = new JPanel(new GridLayout(3, 2, 20, 20));
        centerPanel.setBorder(BorderFactory.createEmptyBorder(30, 60, 30, 60));

        // Buttons for each module
        JButton btnPatient = new JButton("🧍 Patient Management");
        JButton btnDoctor = new JButton("👨‍⚕️ Doctor Management");
        JButton btnAppointment = new JButton("📅 Appointment Management");
        JButton btnWard = new JButton("🏨 Ward Management");
        JButton btnPrescription = new JButton("💊 Prescription Management");
        JButton btnExit = new JButton("🚪 Exit System");

        // Font style
        Font btnFont = new Font("Segoe UI", Font.PLAIN, 15);
        btnPatient.setFont(btnFont);
        btnDoctor.setFont(btnFont);
        btnAppointment.setFont(btnFont);
        btnWard.setFont(btnFont);
        btnPrescription.setFont(btnFont);
        btnExit.setFont(btnFont);

        // Add buttons to grid
        centerPanel.add(btnPatient);
        centerPanel.add(btnDoctor);
        centerPanel.add(btnAppointment);
        centerPanel.add(btnWard);
        centerPanel.add(btnPrescription);
        centerPanel.add(btnExit);
        add(centerPanel, BorderLayout.CENTER);

        // ===== Footer =====
        JLabel footer = new JLabel("© 2025 Hospital Management System", SwingConstants.CENTER);
        footer.setFont(new Font("Segoe UI", Font.ITALIC, 12));
        footer.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        add(footer, BorderLayout.SOUTH);

        // ===== Button Actions =====
        btnPatient.addActionListener((ActionEvent e) -> new PatientGUI());
        btnDoctor.addActionListener((ActionEvent e) -> new DoctorGUI());
        btnAppointment.addActionListener((ActionEvent e) -> new AppointmentGUI());
        btnWard.addActionListener((ActionEvent e) -> new WardGUI());
        btnPrescription.addActionListener((ActionEvent e) -> new PrescriptionGUI());
        btnExit.addActionListener(e -> {
            int opt = JOptionPane.showConfirmDialog(this,
                    "Are you sure you want to exit the system?",
                    "Confirm Exit", JOptionPane.YES_NO_OPTION);
            if (opt == JOptionPane.YES_OPTION) System.exit(0);
        });

        setVisible(true);
    }

    // ===== Entry Point =====
    public static void main(String[] args) {
        // Start the GUI in the Swing event thread
        SwingUtilities.invokeLater(() -> new HospitalSystem());
    }
}

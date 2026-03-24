import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Date;
import java.util.List;

public class PrescriptionGUI extends JFrame {

    private PrescriptionDAO prescriptionDAO = new PrescriptionDAO();
    private JTable table;
    private DefaultTableModel model;

    public PrescriptionGUI() {
        setTitle("Prescription Management");
        setSize(800, 400);
        setLocationRelativeTo(null);

        model = new DefaultTableModel(new String[]{"ID", "Patient ID", "Doctor ID", "Medicine ID", "Dosage", "Date"}, 0);
        table = new JTable(model);
        refreshTable();

        JScrollPane scroll = new JScrollPane(table);
        add(scroll, BorderLayout.CENTER);

        JPanel panel = new JPanel();
        JButton btnAdd = new JButton("Add");
        JButton btnDelete = new JButton("Delete");
        panel.add(btnAdd);
        panel.add(btnDelete);
        add(panel, BorderLayout.SOUTH);

        // Add prescription
        btnAdd.addActionListener(e -> {
            JTextField patientField = new JTextField();
            JTextField doctorField = new JTextField();
            JTextField medicineField = new JTextField();
            JTextField dosageField = new JTextField();
            JTextField dateField = new JTextField();

            Object[] message = {
                    "Patient ID:", patientField,
                    "Doctor ID:", doctorField,
                    "Medicine ID:", medicineField,
                    "Dosage:", dosageField,
                    "Date (yyyy-mm-dd):", dateField
            };

            int option = JOptionPane.showConfirmDialog(null, message, "Add Prescription", JOptionPane.OK_CANCEL_OPTION);
            if (option == JOptionPane.OK_OPTION) {
                try {
                    prescriptionDAO.addPrescription(new Prescription(
                            Integer.parseInt(patientField.getText()),
                            Integer.parseInt(doctorField.getText()),
                            Integer.parseInt(medicineField.getText()),
                            dosageField.getText(),
                            Date.valueOf(dateField.getText())
                    ));
                    refreshTable();
                } catch (Exception ex) { JOptionPane.showMessageDialog(null, "Invalid input!"); }
            }
        });

        // Delete prescription
        btnDelete.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow == -1) { JOptionPane.showMessageDialog(null, "Select a prescription to delete."); return; }
            int id = Integer.parseInt(model.getValueAt(selectedRow, 0).toString());
            prescriptionDAO.deletePrescription(id);
            refreshTable();
        });

        setVisible(true);
    }

    private void refreshTable() {
        model.setRowCount(0);
        List<Prescription> list = prescriptionDAO.getAllPrescriptions();
        for (Prescription p : list) {
            model.addRow(new Object[]{
                    p.getId(), p.getPatientId(), p.getDoctorId(), p.getMedicineId(), p.getDosage(), p.getDate()
            });
        }
    }
}

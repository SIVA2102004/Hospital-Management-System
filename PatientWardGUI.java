import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Date;
import java.util.List;

public class PatientWardGUI extends JFrame {

    private PatientWardDAO pwDAO = new PatientWardDAO();
    private JTable table;
    private DefaultTableModel model;

    public PatientWardGUI() {
        setTitle("Patient-Ward Assignment");
        setSize(800, 400);
        setLocationRelativeTo(null);

        model = new DefaultTableModel(new String[]{"ID", "Patient ID", "Ward ID", "Admit Date", "Discharge Date"}, 0);
        table = new JTable(model);
        refreshTable();

        JScrollPane scroll = new JScrollPane(table);
        add(scroll, BorderLayout.CENTER);

        JPanel panel = new JPanel();
        JButton btnAdd = new JButton("Assign");
        JButton btnDelete = new JButton("Discharge");
        panel.add(btnAdd);
        panel.add(btnDelete);
        add(panel, BorderLayout.SOUTH);

        // Assign patient to ward
        btnAdd.addActionListener(e -> {
            JTextField patientField = new JTextField();
            JTextField wardField = new JTextField();
            JTextField admitField = new JTextField();
            JTextField dischargeField = new JTextField();

            Object[] message = {
                    "Patient ID:", patientField,
                    "Ward ID:", wardField,
                    "Admit Date (yyyy-mm-dd):", admitField,
                    "Discharge Date (yyyy-mm-dd):", dischargeField
            };

            int option = JOptionPane.showConfirmDialog(null, message, "Assign Patient to Ward", JOptionPane.OK_CANCEL_OPTION);
            if (option == JOptionPane.OK_OPTION) {
                try {
                    pwDAO.addPatientWard(new PatientWard(
                            Integer.parseInt(patientField.getText()),
                            Integer.parseInt(wardField.getText()),
                            Date.valueOf(admitField.getText()),
                            Date.valueOf(dischargeField.getText())
                    ));
                    refreshTable();
                } catch (Exception ex) { JOptionPane.showMessageDialog(null, "Invalid input!"); }
            }
        });

        // Discharge patient
        btnDelete.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow == -1) { JOptionPane.showMessageDialog(null, "Select an assignment to discharge."); return; }
            int id = Integer.parseInt(model.getValueAt(selectedRow, 0).toString());
            pwDAO.deletePatientWard(id);
            refreshTable();
        });

        setVisible(true);
    }

    private void refreshTable() {
        model.setRowCount(0);
        List<PatientWard> list = pwDAO.getAllPatientWards();
        for (PatientWard pw : list) {
            model.addRow(new Object[]{pw.getId(), pw.getPatientId(), pw.getWardId(), pw.getAdmitDate(), pw.getDischargeDate()});
        }
    }
}

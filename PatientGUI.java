import javax.swing.*; 
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class PatientGUI extends JFrame {

    private PatientDAO patientDAO = new PatientDAO();
    private JTable table;
    private DefaultTableModel model;

    public PatientGUI() {
        setTitle("Patient Management");
        setSize(700, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        model = new DefaultTableModel(
            new String[]{"ID", "Name", "Age", "Gender", "Disease", "Contact"}, 0
        );
        table = new JTable(model);
        refreshTable();

        JScrollPane scroll = new JScrollPane(table);
        add(scroll, BorderLayout.CENTER);

        JPanel panel = new JPanel();
        JButton btnAdd = new JButton("Add");
        JButton btnUpdate = new JButton("Update");
        JButton btnDelete = new JButton("Delete");
        panel.add(btnAdd);
        panel.add(btnUpdate);
        panel.add(btnDelete);
        add(panel, BorderLayout.SOUTH);

        // === Add Patient ===
        btnAdd.addActionListener(e -> {
            JTextField nameField = new JTextField();
            JTextField ageField = new JTextField();
            JTextField genderField = new JTextField();
            JTextField diseaseField = new JTextField();
            JTextField contactField = new JTextField();

            Object[] message = {
                "Name:", nameField,
                "Age:", ageField,
                "Gender:", genderField,
                "Disease:", diseaseField,
                "Contact:", contactField
            };

            int option = JOptionPane.showConfirmDialog(
                null, message, "Add Patient", JOptionPane.OK_CANCEL_OPTION
            );
            if (option == JOptionPane.OK_OPTION) {
                try {
                    String name = nameField.getText();
                    int age = Integer.parseInt(ageField.getText());
                    String gender = genderField.getText();
                    String disease = diseaseField.getText();
                    String contact = contactField.getText();

                    patientDAO.addPatient(new Patient(name, age, gender, disease, contact));
                    refreshTable();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Invalid input!");
                }
            }
        });

        // === Update Patient ===
        btnUpdate.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(null, "Select a patient to update.");
                return;
            }
            int id = Integer.parseInt(model.getValueAt(selectedRow, 0).toString());

            JTextField nameField = new JTextField(model.getValueAt(selectedRow, 1).toString());
            JTextField ageField = new JTextField(model.getValueAt(selectedRow, 2).toString());
            JTextField genderField = new JTextField(model.getValueAt(selectedRow, 3).toString());
            JTextField diseaseField = new JTextField(model.getValueAt(selectedRow, 4).toString());
            JTextField contactField = new JTextField(model.getValueAt(selectedRow, 5).toString());

            Object[] message = {
                "Name:", nameField,
                "Age:", ageField,
                "Gender:", genderField,
                "Disease:", diseaseField,
                "Contact:", contactField
            };

            int option = JOptionPane.showConfirmDialog(
                null, message, "Update Patient", JOptionPane.OK_CANCEL_OPTION
            );
            if (option == JOptionPane.OK_OPTION) {
                try {
                    String name = nameField.getText();
                    int age = Integer.parseInt(ageField.getText());
                    String gender = genderField.getText();
                    String disease = diseaseField.getText();
                    String contact = contactField.getText();

                    patientDAO.updatePatient(new Patient(id, name, age, gender, disease, contact));
                    refreshTable();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Invalid input!");
                }
            }
        });

        // === Delete Patient ===
        btnDelete.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(null, "Select a patient to delete.");
                return;
            }
            int id = Integer.parseInt(model.getValueAt(selectedRow, 0).toString());
            patientDAO.deletePatient(id);
            refreshTable();
        });

        setVisible(true);
    }

    private void refreshTable() {
        model.setRowCount(0);
        List<Patient> patients = patientDAO.getAllPatients();
        for (Patient p : patients) {
            model.addRow(new Object[]{
                p.getId(), p.getName(), p.getAge(),
                p.getGender(), p.getDisease(), p.getContact()
            });
        }
    }

    // --- For standalone testing ---
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new PatientGUI());
    }
}

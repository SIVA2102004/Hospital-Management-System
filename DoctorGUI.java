import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class DoctorGUI extends JFrame {

    private DoctorDAO doctorDAO = new DoctorDAO();
    private JTable table;
    private DefaultTableModel model;

    public DoctorGUI() {
        setTitle("Doctor Management");
        setSize(700, 400);
        setLocationRelativeTo(null);

        model = new DefaultTableModel(new String[]{"ID", "Name", "Specialization", "Contact"}, 0);
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

        // Add doctor
        btnAdd.addActionListener(e -> {
            JTextField nameField = new JTextField();
            JTextField specField = new JTextField();
            JTextField contactField = new JTextField();

            Object[] message = {"Name:", nameField, "Specialization:", specField, "Contact:", contactField};
            int option = JOptionPane.showConfirmDialog(null, message, "Add Doctor", JOptionPane.OK_CANCEL_OPTION);
            if (option == JOptionPane.OK_OPTION) {
                try {
                    doctorDAO.addDoctor(new Doctor(nameField.getText(), specField.getText(), contactField.getText()));
                    refreshTable();
                } catch (Exception ex) { JOptionPane.showMessageDialog(null, "Invalid input!"); }
            }
        });

        // Update doctor
        btnUpdate.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow == -1) { JOptionPane.showMessageDialog(null, "Select a doctor to update."); return; }
            int id = Integer.parseInt(model.getValueAt(selectedRow, 0).toString());

            JTextField nameField = new JTextField(model.getValueAt(selectedRow, 1).toString());
            JTextField specField = new JTextField(model.getValueAt(selectedRow, 2).toString());
            JTextField contactField = new JTextField(model.getValueAt(selectedRow, 3).toString());

            Object[] message = {"Name:", nameField, "Specialization:", specField, "Contact:", contactField};
            int option = JOptionPane.showConfirmDialog(null, message, "Update Doctor", JOptionPane.OK_CANCEL_OPTION);
            if (option == JOptionPane.OK_OPTION) {
                try {
                    doctorDAO.updateDoctor(new Doctor(id, nameField.getText(), specField.getText(), contactField.getText()));
                    refreshTable();
                } catch (Exception ex) { JOptionPane.showMessageDialog(null, "Invalid input!"); }
            }
        });

        // Delete doctor
        btnDelete.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow == -1) { JOptionPane.showMessageDialog(null, "Select a doctor to delete."); return; }
            int id = Integer.parseInt(model.getValueAt(selectedRow, 0).toString());
            doctorDAO.deleteDoctor(id);
            refreshTable();
        });

        setVisible(true);
    }

    private void refreshTable() {
        model.setRowCount(0);
        List<Doctor> doctors = doctorDAO.getAllDoctors();
        for (Doctor d : doctors) {
            model.addRow(new Object[]{d.getId(), d.getName(), d.getSpecialization(), d.getContact()});
        }
    }
}

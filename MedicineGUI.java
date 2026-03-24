import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class MedicineGUI extends JFrame {

    private MedicineDAO medicineDAO = new MedicineDAO();
    private JTable table;
    private DefaultTableModel model;

    public MedicineGUI() {
        setTitle("Medicine Management");
        setSize(700, 400);
        setLocationRelativeTo(null);

        model = new DefaultTableModel(new String[]{"ID", "Name", "Type", "Price"}, 0);
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

        // Add medicine
        btnAdd.addActionListener(e -> {
            JTextField nameField = new JTextField();
            JTextField typeField = new JTextField();
            JTextField priceField = new JTextField();

            Object[] message = {"Name:", nameField, "Type:", typeField, "Price:", priceField};
            int option = JOptionPane.showConfirmDialog(null, message, "Add Medicine", JOptionPane.OK_CANCEL_OPTION);
            if (option == JOptionPane.OK_OPTION) {
                try {
                    medicineDAO.addMedicine(new Medicine(nameField.getText(), typeField.getText(), Double.parseDouble(priceField.getText())));
                    refreshTable();
                } catch (Exception ex) { JOptionPane.showMessageDialog(null, "Invalid input!"); }
            }
        });

        // Update medicine
        btnUpdate.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow == -1) { JOptionPane.showMessageDialog(null, "Select a medicine to update."); return; }
            int id = Integer.parseInt(model.getValueAt(selectedRow, 0).toString());

            JTextField nameField = new JTextField(model.getValueAt(selectedRow, 1).toString());
            JTextField typeField = new JTextField(model.getValueAt(selectedRow, 2).toString());
            JTextField priceField = new JTextField(model.getValueAt(selectedRow, 3).toString());

            Object[] message = {"Name:", nameField, "Type:", typeField, "Price:", priceField};
            int option = JOptionPane.showConfirmDialog(null, message, "Update Medicine", JOptionPane.OK_CANCEL_OPTION);
            if (option == JOptionPane.OK_OPTION) {
                try {
                    medicineDAO.updateMedicine(new Medicine(id, nameField.getText(), typeField.getText(), Double.parseDouble(priceField.getText())));
                    refreshTable();
                } catch (Exception ex) { JOptionPane.showMessageDialog(null, "Invalid input!"); }
            }
        });

        // Delete medicine
        btnDelete.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow == -1) { JOptionPane.showMessageDialog(null, "Select a medicine to delete."); return; }
            int id = Integer.parseInt(model.getValueAt(selectedRow, 0).toString());
            medicineDAO.deleteMedicine(id);
            refreshTable();
        });

        setVisible(true);
    }

    private void refreshTable() {
        model.setRowCount(0);
        List<Medicine> medicines = medicineDAO.getAllMedicines();
        for (Medicine m : medicines) {
            model.addRow(new Object[]{m.getId(), m.getName(), m.getType(), m.getPrice()});
        }
    }
}

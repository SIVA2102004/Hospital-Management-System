import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class WardGUI extends JFrame {

    private WardDAO wardDAO = new WardDAO();
    private JTable table;
    private DefaultTableModel model;

    public WardGUI() {
        setTitle("Ward Management");
        setSize(700, 400);
        setLocationRelativeTo(null);

        model = new DefaultTableModel(new String[]{"ID", "Ward Name", "Room Number", "Capacity"}, 0);
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

        // Add Ward
        btnAdd.addActionListener(e -> {
            JTextField nameField = new JTextField();
            JTextField roomField = new JTextField();
            JTextField capacityField = new JTextField();

            Object[] message = {"Ward Name:", nameField, "Room Number:", roomField, "Capacity:", capacityField};
            int option = JOptionPane.showConfirmDialog(null, message, "Add Ward", JOptionPane.OK_CANCEL_OPTION);
            if (option == JOptionPane.OK_OPTION) {
                try {
                    wardDAO.addWard(new Ward(nameField.getText(), roomField.getText(), Integer.parseInt(capacityField.getText())));
                    refreshTable();
                } catch (Exception ex) { JOptionPane.showMessageDialog(null, "Invalid input!"); }
            }
        });

        // Update Ward
        btnUpdate.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow == -1) { JOptionPane.showMessageDialog(null, "Select a ward to update."); return; }
            int id = Integer.parseInt(model.getValueAt(selectedRow, 0).toString());

            JTextField nameField = new JTextField(model.getValueAt(selectedRow, 1).toString());
            JTextField roomField = new JTextField(model.getValueAt(selectedRow, 2).toString());
            JTextField capacityField = new JTextField(model.getValueAt(selectedRow, 3).toString());

            Object[] message = {"Ward Name:", nameField, "Room Number:", roomField, "Capacity:", capacityField};
            int option = JOptionPane.showConfirmDialog(null, message, "Update Ward", JOptionPane.OK_CANCEL_OPTION);
            if (option == JOptionPane.OK_OPTION) {
                try {
                    wardDAO.updateWard(new Ward(id, nameField.getText(), roomField.getText(), Integer.parseInt(capacityField.getText())));
                    refreshTable();
                } catch (Exception ex) { JOptionPane.showMessageDialog(null, "Invalid input!"); }
            }
        });

        // Delete Ward
        btnDelete.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow == -1) { JOptionPane.showMessageDialog(null, "Select a ward to delete."); return; }
            int id = Integer.parseInt(model.getValueAt(selectedRow, 0).toString());
            wardDAO.deleteWard(id);
            refreshTable();
        });

        setVisible(true);
    }

    private void refreshTable() {
        model.setRowCount(0);
        List<Ward> wards = wardDAO.getAllWards();
        for (Ward w : wards) {
            model.addRow(new Object[]{w.getId(), w.getWardName(), w.getRoomNumber(), w.getCapacity()});
        }
    }
}

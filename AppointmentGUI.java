import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import javax.swing.RowFilter;
import java.awt.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;
import java.util.regex.Pattern;
import java.sql.Date;

/**
 * AppointmentGUI with search filters and calendar booking.
 *
 * Requires AppointmentDAO with:
 *   List<Appointment> getAllAppointments();
 *   void deleteAppointment(int appointmentId);
 *   void addAppointment(Appointment appointment);
 *   boolean updateAppointmentDate(int appointmentId, java.util.Date date);
 *   int createAppointment(int patientId, int doctorId, java.util.Date date);
 *
 * Requires Appointment class with getters:
 *   getId(), getPatientId(), getDoctorId(), getAppointmentDate()
 */
public class AppointmentGUI extends JFrame {

    private AppointmentDAO appointmentDAO = new AppointmentDAO();
    private JTable table;
    private DefaultTableModel model;
    private TableRowSorter<DefaultTableModel> sorter;

    // Filter fields
    private JTextField txtPatientId;
    private JTextField txtDoctorId;
    private JSpinner dateFromSpinner;
    private JSpinner dateToSpinner;

    private SimpleDateFormat displayDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");

    public AppointmentGUI() {
        setTitle("Appointment Management (with Filters & Calendar)");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(900, 520);
        setLocationRelativeTo(null);

        initComponents();
        loadTableData();
        setVisible(true);
    }

    private void initComponents() {
        // Top filter panel
        JPanel filterPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 6, 6, 6);
        gbc.anchor = GridBagConstraints.WEST;

        gbc.gridx = 0; gbc.gridy = 0;
        filterPanel.add(new JLabel("Patient ID:"), gbc);
        gbc.gridx = 1;
        txtPatientId = new JTextField(8);
        filterPanel.add(txtPatientId, gbc);

        gbc.gridx = 2;
        filterPanel.add(new JLabel("Doctor ID:"), gbc);
        gbc.gridx = 3;
        txtDoctorId = new JTextField(8);
        filterPanel.add(txtDoctorId, gbc);

        // Date from spinner
        gbc.gridx = 0; gbc.gridy = 1;
        filterPanel.add(new JLabel("From (date/time):"), gbc);
        gbc.gridx = 1;
        dateFromSpinner = createDateTimeSpinner();
        filterPanel.add(dateFromSpinner, gbc);

        gbc.gridx = 2;
        filterPanel.add(new JLabel("To (date/time):"), gbc);
        gbc.gridx = 3;
        dateToSpinner = createDateTimeSpinner();
        // default "to" as one month ahead
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, 1);
        dateToSpinner.setValue(cal.getTime());
        filterPanel.add(dateToSpinner, gbc);

        // Buttons
        gbc.gridx = 0; gbc.gridy = 2;
        JButton btnApply = new JButton("Apply Filters");
        btnApply.addActionListener(e -> applyFilters());
        filterPanel.add(btnApply, gbc);

        gbc.gridx = 1;
        JButton btnClear = new JButton("Clear Filters");
        btnClear.addActionListener(e -> clearFilters());
        filterPanel.add(btnClear, gbc);

        gbc.gridx = 2;
        JButton btnRefresh = new JButton("Refresh Table");
        btnRefresh.addActionListener(e -> loadTableData());
        filterPanel.add(btnRefresh, gbc);

        // Table
        model = new DefaultTableModel(new Object[]{"ID", "Patient ID", "Doctor ID", "Appointment Date"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // disable direct editing
            }
        };
        table = new JTable(model);
        sorter = new TableRowSorter<>(model);
        table.setRowSorter(sorter);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JScrollPane scrollPane = new JScrollPane(table);

        // Bottom buttons: Book / Update / Delete
        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton btnBook = new JButton("Book / Update Appointment");
        btnBook.addActionListener(e -> bookOrUpdateAppointment());
        bottom.add(btnBook);

        JButton btnNew = new JButton("Create New Appointment");
        btnNew.addActionListener(e -> createNewAppointmentDialog());
        bottom.add(btnNew);

        JButton btnDelete = new JButton("Delete Appointment");
        btnDelete.addActionListener(e -> deleteAppointment());
        bottom.add(btnDelete);

        // Layout main frame
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(filterPanel, BorderLayout.NORTH);
        getContentPane().add(scrollPane, BorderLayout.CENTER);
        getContentPane().add(bottom, BorderLayout.SOUTH);
    }

    private JSpinner createDateTimeSpinner() {
        Date init = new Date(System.currentTimeMillis()); // current date/time
        SpinnerDateModel model = new SpinnerDateModel(init, null, null, Calendar.MINUTE);
        JSpinner spinner = new JSpinner(model);
        JSpinner.DateEditor editor = new JSpinner.DateEditor(spinner, "yyyy-MM-dd HH:mm");
        spinner.setEditor(editor);
        return spinner;
    }

    private void loadTableData() {
        SwingUtilities.invokeLater(() -> {
            model.setRowCount(0);
            List<Appointment> appointments = appointmentDAO.getAllAppointments();
            if (appointments == null) appointments = new ArrayList<>();
            for (Appointment a : appointments) {
                java.util.Date dt = a.getAppointmentDate();
                String dateStr = dt != null ? displayDateFormat.format(dt) : "";
                model.addRow(new Object[]{a.getId(), a.getPatientId(), a.getDoctorId(), dateStr});
            }
        });
    }

    private void applyFilters() {
        List<RowFilter<Object, Object>> filters = new ArrayList<>();

        // Patient ID filter (exact match if provided)
        String patientText = txtPatientId.getText().trim();
        if (!patientText.isEmpty()) {
            try {
                String regex = "^" + Pattern.quote(patientText) + "$";
                filters.add(RowFilter.regexFilter(regex, 1)); // column 1 = Patient ID
            } catch (Exception ignored) {}
        }

        // Doctor ID filter
        String doctorText = txtDoctorId.getText().trim();
        if (!doctorText.isEmpty()) {
            try {
                String regex = "^" + Pattern.quote(doctorText) + "$";
                filters.add(RowFilter.regexFilter(regex, 2)); // column 2 = Doctor ID
            } catch (Exception ignored) {}
        }

        // Date range filter
        Date fromDate = (Date) dateFromSpinner.getValue();
        Date toDate = (Date) dateToSpinner.getValue();
        if (fromDate != null && toDate != null) {
            filters.add(new RowFilter<>() {
                @Override
                public boolean include(Entry<?, ?> entry) {
                    Object val = entry.getValue(3); // appointment date string
                    if (val == null) return false;
                    try {
                        java.util.Date parsed = displayDateFormat.parse(val.toString());
                        return !parsed.before(fromDate) && !parsed.after(toDate);
                    } catch (Exception ex) {
                        return false;
                    }
                }
            });
        }

        sorter.setRowFilter(filters.isEmpty() ? null : RowFilter.andFilter(filters));
    }

    private void clearFilters() {
        txtPatientId.setText("");
        txtDoctorId.setText("");
        dateFromSpinner.setValue(new Date(System.currentTimeMillis()));
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, 1);
        dateToSpinner.setValue(cal.getTime());
        sorter.setRowFilter(null);
    }

    private void bookOrUpdateAppointment() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select an appointment to update.", "No selection", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        int modelRow = table.convertRowIndexToModel(selectedRow);
        int appointmentId = Integer.parseInt(model.getValueAt(modelRow, 0).toString());
        String currentDateStr = model.getValueAt(modelRow, 3).toString();

        java.util.Date currentDate;
        try {
            currentDate = displayDateFormat.parse(currentDateStr);
        } catch (Exception ex) {
            currentDate = new java.util.Date();
        }

        java.util.Date newDate = showDateTimePickerDialog(currentDate, "Update Appointment Date/Time");
        if (newDate == null) return;

        boolean ok = appointmentDAO.updateAppointmentDate(appointmentId, newDate);
        if (ok) {
            JOptionPane.showMessageDialog(this, "Appointment updated successfully.");
            loadTableData();
        } else {
            JOptionPane.showMessageDialog(this, "Update failed. Please check logs.");
        }
    }

    private void createNewAppointmentDialog() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 6, 6, 6);
        gbc.anchor = GridBagConstraints.WEST;

        JTextField patientField = new JTextField(10);
        JTextField doctorField = new JTextField(10);
        JSpinner dateSpinner = createDateTimeSpinner();

        gbc.gridx = 0; gbc.gridy = 0; panel.add(new JLabel("Patient ID:"), gbc);
        gbc.gridx = 1; panel.add(patientField, gbc);
        gbc.gridx = 0; gbc.gridy = 1; panel.add(new JLabel("Doctor ID:"), gbc);
        gbc.gridx = 1; panel.add(doctorField, gbc);
        gbc.gridx = 0; gbc.gridy = 2; panel.add(new JLabel("Date/Time:"), gbc);
        gbc.gridx = 1; panel.add(dateSpinner, gbc);

        int res = JOptionPane.showConfirmDialog(this, panel, "Create Appointment", JOptionPane.OK_CANCEL_OPTION);
        if (res != JOptionPane.OK_OPTION) return;

        try {
            int patientId = Integer.parseInt(patientField.getText().trim());
            int doctorId = Integer.parseInt(doctorField.getText().trim());
            java.util.Date apptDate = (java.util.Date) dateSpinner.getValue();

            int newId = appointmentDAO.createAppointment(patientId, doctorId, apptDate);
            if (newId > 0) {
                JOptionPane.showMessageDialog(this, "Appointment created successfully (ID: " + newId + ")");
                loadTableData();
            } else {
                JOptionPane.showMessageDialog(this, "Creation failed.");
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid Patient or Doctor ID.");
        }
    }

    private void deleteAppointment() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Select an appointment to delete.");
            return;
        }

        int modelRow = table.convertRowIndexToModel(selectedRow);
        int id = Integer.parseInt(model.getValueAt(modelRow, 0).toString());

        int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete appointment ID " + id + "?",
                "Confirm Delete", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            appointmentDAO.deleteAppointment(id);
            loadTableData();
        }
    }

    private java.util.Date showDateTimePickerDialog(java.util.Date initial, String title) {
        JSpinner spinner = createDateTimeSpinner();
        spinner.setValue(initial != null ? initial : new java.util.Date());
        int res = JOptionPane.showConfirmDialog(this, spinner, title, JOptionPane.OK_CANCEL_OPTION);
        return res == JOptionPane.OK_OPTION ? (java.util.Date) spinner.getValue() : null;
    }

    // --- For standalone testing ---
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new AppointmentGUI());
    }
}

package GUI;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Vector;

public class TabHanSuDung extends JPanel {

    private JTextField txtSearchName;
    private JComboBox<String> cbExpiryFilter;
    private JTable table;
    private DefaultTableModel tableModel;

    // Date formatter cho hiển thị ngày
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public TabHanSuDung() {
        setLayout(new BorderLayout());

        // Panel filter trên cùng
        JPanel panelFilter = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));

        panelFilter.add(new JLabel("Tìm thuốc theo tên:"));
        txtSearchName = new JTextField(20);
        panelFilter.add(txtSearchName);

        panelFilter.add(new JLabel("Hạn sử dụng còn lại:"));
        cbExpiryFilter = new JComboBox<>(new String[] {
                "Tất cả",
                "Đã hết hạn",
                "7 ngày tới",
                "30 ngày tới",
                "90 ngày tới"
        });
        panelFilter.setToolTipText("Lọc thuốc theo hạn sử dụng");
        panelFilter.add(cbExpiryFilter);

        add(panelFilter, BorderLayout.NORTH);

        // Bảng thuốc
        String[] columns = {
                "Mã thuốc", "Tên thuốc", "Số lô", "Hạn sử dụng",
                "Số lượng còn", "Nhà sản xuất", "Ngày nhập kho"};
        tableModel = new DefaultTableModel(columns, 0) {
            // Chặn sửa nội dung bảng
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        // Tự động load lại khi gõ từ khóa tìm
        txtSearchName.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                loadData();
            }
        });

        // Tự động load lại khi thay đổi bộ lọc hạn sử dụng
        cbExpiryFilter.addActionListener(e -> loadData());

        // Load lần đầu
        loadData();
    }

    private void loadData() {
    tableModel.setRowCount(0);

    String searchName = txtSearchName.getText().trim().toLowerCase();
    String expiryFilter = (String) cbExpiryFilter.getSelectedItem();

    LocalDate now = LocalDate.now();

    Vector<Object[]> sampleData = new Vector<>();
    sampleData.add(new Object[]{"T001", "Paracetamol", "L01", "2025-06-01", 50, "Nhà máy A", "2024-01-01"});
    sampleData.add(new Object[]{"T002", "Amoxicillin", "L02", "2025-05-20", 20, "Nhà máy B", "2024-02-15"});
    sampleData.add(new Object[]{"T003", "Vitamin C", "L03", "2024-05-25", 15, "Nhà máy C", "2023-12-10"});
    sampleData.add(new Object[]{"T004", "Ibuprofen", "L04", "2024-05-10", 5, "Nhà máy D", "2024-03-05"});
    sampleData.add(new Object[]{"T005", "Captopril", "L05", "2024-12-30", 40, "Nhà máy E", "2024-04-01"});

    for (Object[] row : sampleData) {
        String code = (String) row[0];
        String name = (String) row[1];
        String batch = (String) row[2];
        LocalDate expiryDate = LocalDate.parse((String) row[3]);
        int quantity = (int) row[4];
        String manufacturer = (String) row[5];
        LocalDate importDate = LocalDate.parse((String) row[6]);

        // Lọc theo tên thuốc
        if (!searchName.isEmpty() && !name.toLowerCase().contains(searchName)) {
            continue;
        }

        // Lọc theo hạn sử dụng
        boolean passFilter = true;
        switch (expiryFilter) {
            case "Đã hết hạn":
                passFilter = expiryDate.isBefore(now);
                break;
            case "7 ngày tới":
                passFilter = !expiryDate.isBefore(now) && !expiryDate.isAfter(now.plusDays(7));
                break;
            case "30 ngày tới":
                passFilter = !expiryDate.isBefore(now) && !expiryDate.isAfter(now.plusDays(30));
                break;
            case "90 ngày tới":
                passFilter = !expiryDate.isBefore(now) && !expiryDate.isAfter(now.plusDays(90));
                break;
            case "Tất cả":
            default:
                passFilter = true;
                break;
        }

        if (!passFilter) continue;

        tableModel.addRow(new Object[] {
            code,
            name,
            batch,
            expiryDate.format(DATE_FORMATTER),
            quantity,
            manufacturer,
            importDate.format(DATE_FORMATTER)
        });
    }
}
}

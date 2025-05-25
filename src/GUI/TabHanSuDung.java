package GUI;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import bll.ThongKeHSDBLL;
import dal.ThongKeHSDDAL;
import dto.ThongKeHSDDTO;

public class TabHanSuDung extends JPanel {

    private JTextField txtSearchName;
    private JComboBox<String> cbExpiryFilter;
    private JTable table;
    private DefaultTableModel tableModel;
    private ThongKeHSDBLL thongKeHSDBLL;

    // Date formatter cho hiển thị ngày
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public TabHanSuDung() {
        setLayout(new BorderLayout());

        // Khởi tạo BLL
        ThongKeHSDDAL thongKeHSDDAL = new ThongKeHSDDAL();
        this.thongKeHSDBLL = new ThongKeHSDBLL(thongKeHSDDAL);

        // Panel filter trên cùng
        JPanel panelFilter = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));

        panelFilter.add(new JLabel("Tìm thuốc theo tên:"));
        txtSearchName = new JTextField();
        txtSearchName.setPreferredSize(new Dimension(220, 30)); // 200px rộng, 30px cao
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
                "Số lượng còn", "Nhà cung ứng", "Ngày nhập kho"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table = new JTable(tableModel);
        // Điều chỉnh độ rộng các cột cụ thể để ưu tiên cột "Tên thuốc" rộng hơn
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF); // Tắt tự co giãn

        table.getColumnModel().getColumn(0).setPreferredWidth(95);   // Mã thuốc
        table.getColumnModel().getColumn(1).setPreferredWidth(200);  // Tên thuốc (tăng)
        table.getColumnModel().getColumn(2).setPreferredWidth(90);   // Số lô (giảm nhẹ)
        table.getColumnModel().getColumn(3).setPreferredWidth(100);  // Hạn sử dụng (giảm nhẹ)
        table.getColumnModel().getColumn(4).setPreferredWidth(90);   // Số lượng còn (giảm rõ)
        table.getColumnModel().getColumn(5).setPreferredWidth(100);  // Nhà cung ứng
        table.getColumnModel().getColumn(6).setPreferredWidth(100);  // Ngày nhập kho

        table.getTableHeader().setPreferredSize(new Dimension(0, 35));
        table.setRowHeight(30);
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
        table.setFont(new Font("Arial", Font.PLAIN, 14));

        // Đặt màu nền cho hàng tùy thuộc vào hạn sử dụng
        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                                                           boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

                // Lấy ngày hết hạn từ model
                LocalDate expiryDate = LocalDate.parse(table.getValueAt(row, 3).toString(), DATE_FORMATTER);
                LocalDate now = LocalDate.now();

                if (expiryDate.isBefore(now)) {
                    c.setBackground(new Color(255, 200, 200)); // Đỏ nhạt cho sản phẩm hết hạn
                } else if (expiryDate.isBefore(now.plusDays(7))) {
                    c.setBackground(new Color(255, 255, 200)); // Vàng nhạt cho sản phẩm sắp hết hạn (7 ngày)
                } else {
                    c.setBackground(table.getBackground());
                }

                if (isSelected) {
                    c.setBackground(table.getSelectionBackground());
                }

                return c;
            }
        });

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
        LocalDate now = LocalDate.now();

        String searchName = txtSearchName.getText().trim();
        String expiryFilter = (String) cbExpiryFilter.getSelectedItem();

        List<ThongKeHSDDTO> danhSachSP = thongKeHSDBLL.thongKeSanPhamTheoHSD(searchName, expiryFilter);

        // Sắp xếp theo độ ưu tiên hạn sử dụng
        danhSachSP.sort((sp1, sp2) -> {
            LocalDate hsd1 = sp1.getHanSuDung();
            LocalDate hsd2 = sp2.getHanSuDung();

            // 1. Sản phẩm đã hết hạn lên đầu (ưu tiên hết hạn sớm hơn)
            if (hsd1.isBefore(now) && hsd2.isBefore(now)) {
                return hsd1.compareTo(hsd2);
            }
            if (hsd1.isBefore(now)) return -1;
            if (hsd2.isBefore(now)) return 1;

            // 2. Sản phẩm sắp hết hạn (ưu tiên hạn gần hơn)
            return hsd1.compareTo(hsd2);
        });

        // Hiển thị dữ liệu đã sắp xếp
        for (ThongKeHSDDTO sp : danhSachSP) {
            tableModel.addRow(new Object[] {
                    sp.getMaSP(),
                    sp.getTenSP(),
                    sp.getSoLo(),
                    sp.getHanSuDung().format(DATE_FORMATTER),
                    sp.getSoLuongCon(),
                    sp.getNhaCungUng(),
                    sp.getNgayNhapKho().format(DATE_FORMATTER)
            });
        }
    }
}
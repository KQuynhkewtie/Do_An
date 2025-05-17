package GUI;

import bll.HoaDonBLL;
import dto.HoaDonDTO;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import com.toedter.calendar.JDateChooser;

public class HoaDon extends BaseFrame {
    private DefaultTableModel model;
    private JTable table;
    private JTextField searchField;
    private JDateChooser dateChooserFrom, dateChooserTo;
    private JTextField txtMinAmount, txtMaxAmount;
    private JComboBox<String> searchCriteriaComboBox;
    private HoaDonBLL hdBLL;
    private JComboBox<String> cboTrangThai;

    public HoaDon() {
        super("Quản lý Hóa đơn");
        hdBLL = new HoaDonBLL();
        initialize();
    }

    @Override
    protected void initUniqueComponents() {
        for (JButton btn : menuButtons) {
            if (btn.getText().equals("Hóa đơn")) {
                btn.setBackground(Color.decode("#EF5D7A"));
                btn.setFont(new Font("Arial", Font.BOLD, 14));
            }
        }

        // Các nút khác vẫn giữ màu mặc định
        for (JButton btn : menuButtons) {
            if (!btn.getText().equals("Hóa đơn")) {
                btn.setBackground(Color.decode("#641A1F")); // Màu mặc định cho các nút khác
                btn.setFont(new Font("Arial", Font.BOLD, 12));
            }
        }

        // Tiêu đề
        JLabel lblHD = new JLabel("Hóa đơn", SwingConstants.LEFT);
        lblHD.setFont(new Font("Arial", Font.BOLD, 20));
        lblHD.setBounds(270, 60, 200, 30);
        add(lblHD);

        // Ô tìm kiếm và bộ lọc
        initSearchComponents();
        initAdvancedFilters();

        // Bảng hiển thị
        initTable();

        // Nút thêm mới
        JButton btnThemHd = createActionButton("+ Thêm hóa đơn", 890, 110, 160, 35);
        btnThemHd.addActionListener(e -> {
            SwingUtilities.invokeLater(() -> {
                new ThemHD().setVisible(true);
                dispose();
            });
        });
        add(btnThemHd);

        loadDataToTable();

        addExceltButton();
    }

    private void initSearchComponents() {
        // Ô nhập từ khóa (đã bỏ ComboBox)
        searchField = new JTextField("Tìm kiếm hóa đơn");
        searchField.setBounds(270, 110, 400, 35); // Mở rộng ô tìm kiếm
        add(searchField);

        // Nút tìm kiếm
        JButton btnSearch = new JButton("Tìm kiếm");
        btnSearch.setBounds(690, 110, 100, 35);
        btnSearch.setBackground(Color.decode("#4CAF50"));
        btnSearch.setForeground(Color.WHITE);
        btnSearch.addActionListener(e -> performSearch());
        add(btnSearch);

        // Xử lý placeholder cho ô tìm kiếm
        searchField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (searchField.getText().equals("Tìm kiếm hóa đơn")) {
                    searchField.setText("");
                    searchField.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (searchField.getText().trim().isEmpty()) {
                    searchField.setText("Tìm kiếm hóa đơn");
                    searchField.setForeground(Color.GRAY);
                }
            }
        });

        searchField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    performSearch();
                }
            }
        });
    }

    private void initAdvancedFilters() {
        int yPos = 160;

        // Nhóm lọc ngày
        JLabel lblFilter = new JLabel("Bộ lọc nâng cao:");
        //lblFilter.setFont(new Font("Arial", Font.BOLD, 14));
        lblFilter.setBounds(270, 150, 150, 25);
        add(lblFilter);

        yPos += 30;

        JLabel lblFromDate = new JLabel("Từ ngày:");
        lblFromDate.setBounds(270, yPos, 70, 25);
        add(lblFromDate);

        dateChooserFrom = new JDateChooser();
        dateChooserFrom.setBounds(340, yPos, 150, 25);
        dateChooserFrom.setDateFormatString("dd-MM-yyyy");
        add(dateChooserFrom);

        JLabel lblToDate = new JLabel("Đến ngày:");
        lblToDate.setBounds(510, yPos, 70, 25);
        add(lblToDate);

        dateChooserTo = new JDateChooser();
        dateChooserTo.setBounds(600, yPos, 150, 25);
        dateChooserTo.setDateFormatString("dd-MM-yyyy");
        add(dateChooserTo);

        yPos += 35;

        // Nhóm lọc tiền
        JLabel lblMinAmount = new JLabel("Từ tiền:");
        lblMinAmount.setBounds(270, yPos, 60, 25);
        add(lblMinAmount);

        txtMinAmount = new JTextField();
        txtMinAmount.setBounds(340, yPos, 150, 25);
        add(txtMinAmount);

        JLabel lblMaxAmount = new JLabel("Đến tiền:");
        lblMaxAmount.setBounds(510, yPos, 70, 25);
        add(lblMaxAmount);

        txtMaxAmount = new JTextField();
        txtMaxAmount.setBounds(600, yPos, 150, 25);
        add(txtMaxAmount);

        // Thêm combobox lọc trạng thái
        yPos += 35;
        JLabel lblTrangThai = new JLabel("Trạng thái:");
        lblTrangThai.setBounds(270, yPos, 70, 25);
        add(lblTrangThai);

        cboTrangThai = new JComboBox<>(new String[]{"Tất cả", "Bình thường", "Đã hủy"});
        cboTrangThai.setBounds(340, yPos, 150, 25);
        add(cboTrangThai);
    }

    private void initTable() {
        String[] columnNames = {"Mã hóa đơn", "Mã nhân viên", "Mã khách hàng", "Ngày bán", "Thành tiền", "Trạng thái"};
        model = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        table = new JTable(model) {
            @Override
            public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
                Component c = super.prepareRenderer(renderer, row, column);

                // Lấy giá trị trạng thái từ model (cột cuối cùng)
                String trangThai = (String) model.getValueAt(row, model.getColumnCount() - 1);

                if ("Đã hủy".equals(trangThai)) {
                    c.setBackground(Color.LIGHT_GRAY);
                    c.setForeground(Color.DARK_GRAY);
                } else {
                    c.setBackground(getBackground());
                    c.setForeground(getForeground());
                }
                return c;
            }
        };

        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 1) {
                    int row = table.getSelectedRow();
                    if (row >= 0) {
                        String maHoaDon = model.getValueAt(row, 0).toString();
                        openChiTietHoaDon(maHoaDon);
                    }
                }
            }
        });

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(270, 280, 800, 360);
        add(scrollPane);
    }

    private void openChiTietHoaDon(String maHoaDon) {
        SwingUtilities.invokeLater(() -> {
            TTCThd ctForm = new TTCThd(); // Khởi tạo không cần tham số
            ctForm.loadHoaDonInfo(maHoaDon); // Tải thông tin sau
            ctForm.setVisible(true);
            dispose();
        });
    }

    private void performSearch() {
        String keyword = searchField.getText().trim();
        if (keyword.equals("Tìm kiếm hóa đơn")) {
            keyword = "";
        }

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        String fromDate = "";
        String toDate = "";

        try {
            if (dateChooserFrom.getDate() != null) {
                fromDate = sdf.format(dateChooserFrom.getDate());
            }
            if (dateChooserTo.getDate() != null) {
                toDate = sdf.format(dateChooserTo.getDate());
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Định dạng ngày không hợp lệ!");
            return;
        }

        Double minAmount = null;
        Double maxAmount = null;

        // Lấy trạng thái từ combobox
        String trangThai = cboTrangThai.getSelectedItem().toString();

        try {
            if (!txtMinAmount.getText().isEmpty()) {
                minAmount = Double.parseDouble(txtMinAmount.getText());
            }
            if (!txtMaxAmount.getText().isEmpty()) {
                maxAmount = Double.parseDouble(txtMaxAmount.getText());
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập số tiền hợp lệ!");
            return;
        }

        searchHoaDon(keyword, fromDate, toDate, minAmount, maxAmount, trangThai);
    }

    private void loadDataToTable() {
        model.setRowCount(0);
        List<HoaDonDTO> danhSach = hdBLL.layDanhSachHoaDon();
        displayData(danhSach);
    }

    private void searchHoaDon(String keyword, String fromDate, String toDate,
                              Double minAmount, Double maxAmount, String trangThai) {
        model.setRowCount(0);

        // Chuyển đổi giá trị trạng thái từ combobox sang giá trị DB
        String dbTrangThai = null;
        if ("Bình thường".equals(trangThai)) {
            dbTrangThai = "BINH_THUONG";
        } else if ("Đã hủy".equals(trangThai)) {
            dbTrangThai = "DA_HUY";
        }

        List<HoaDonDTO> danhSach = hdBLL.timKiemHoaDon(
                keyword, fromDate, toDate, minAmount, maxAmount, trangThai);
        displayData(danhSach);
    }

    private void displayData(List<HoaDonDTO> danhSach) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

        for (HoaDonDTO hd : danhSach) {
            String trangThai = "Bình thường";
            if ("DA_HUY".equals(hd.getTrangThai())) {
                trangThai = "Đã hủy";
            }

            Object[] row = {
                    hd.getMaHoaDon(),
                    hd.getMaNhanVien(),
                    hd.getMaKH(),
                    hd.getNgayBan() != null ? dateFormat.format(hd.getNgayBan()) : "N/A",
                    String.format("%,.0f VNĐ", hd.getThanhTien()),
                    trangThai
            };
            model.addRow(row);
        }
    }

    public void refreshTable() {
        loadDataToTable();
    }

    @Override
    protected void addExceltButton() {
        super.addExceltButton();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new HoaDon().setVisible(true);
        });
    }
}
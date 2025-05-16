package GUI;

import bll.PhieuNhapHangBLL;
import dto.PhieuNhapHangDTO;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.List;
import com.toedter.calendar.JDateChooser;

public class PhieuNhapHang extends BaseFrame {
    private PhieuNhapHangBLL pnhBLL = new PhieuNhapHangBLL();
    private DefaultTableModel model;
    private JTable table;
    private JTextField searchField;
    private JDateChooser dateChooserFrom, dateChooserTo;
    private JTextField txtMinAmount, txtMaxAmount;
    private JComboBox<String> searchCriteriaComboBox;

    public PhieuNhapHang() {
        super("Phiếu nhập hàng");
        initialize();
    }

    @Override
    protected void initUniqueComponents() {
        // Highlight menu button
        for (JButton btn : menuButtons) {
            if (btn.getText().equals("Phiếu nhập hàng")) {
                btn.setBackground(Color.decode("#EF5D7A"));
                btn.setFont(new Font("Arial", Font.BOLD, 14));
            } else {
                btn.setBackground(Color.decode("#641A1F"));
                btn.setFont(new Font("Arial", Font.BOLD, 12));
            }
        }

        // Tiêu đề
        JLabel lblTitle = new JLabel("Phiếu nhập hàng");
        lblTitle.setFont(new Font("Arial", Font.BOLD, 20));
        lblTitle.setBounds(270, 60, 300, 30);
        add(lblTitle);

        // Ô tìm kiếm và bộ lọc
        initSearchComponents();
        initAdvancedFilters();

        // Bảng hiển thị
        initTable();

        // Nút thêm mới
        JButton btnThem = createActionButton("+ Thêm phiếu nhập", 870, 110, 180, 35);
        btnThem.addActionListener(e -> {
            SwingUtilities.invokeLater(() -> {
                new Thempnh().setVisible(true);
                dispose();
            });
        });
        add(btnThem);

        loadDataToTable();

        addExceltButton();
    }

    private void initSearchComponents() {
        // Ô nhập từ khóa (không cần ComboBox chọn tiêu chí)
        searchField = new JTextField("Tìm kiếm phiếu nhập hàng");
        searchField.setBounds(270, 110, 400, 35);
        add(searchField);

        // Nút tìm kiếm
        JButton btnSearch = new JButton("Tìm kiếm");
        btnSearch.setBounds(690, 110, 100, 35);
        btnSearch.addActionListener(e -> performSearch());
        add(btnSearch);

        // Xử lý placeholder cho ô tìm kiếm
        // Xử lý placeholder
        searchField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (searchField.getText().equals("Tìm kiếm phiếu nhập hàng")) {
                    searchField.setText("");
                    searchField.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (searchField.getText().trim().isEmpty()) {
                    searchField.setText("Tìm kiếm phiếu nhập hàng");
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
        JLabel lblFromDate = new JLabel("Từ ngày:");
        lblFromDate.setBounds(270, yPos, 70, 25);
        add(lblFromDate);

        dateChooserFrom = new JDateChooser();
        dateChooserFrom.setBounds(340, yPos, 150, 25);
        dateChooserFrom.setDateFormatString("dd-MM-yyyy");
        dateChooserFrom.getCalendarButton().setText("...");
        add(dateChooserFrom);

        JLabel lblToDate = new JLabel("Đến ngày:");
        lblToDate.setBounds(510, yPos, 70, 25);
        add(lblToDate);

        dateChooserTo = new JDateChooser();
        dateChooserTo.setBounds(600, yPos, 150, 25);
        dateChooserTo.setDateFormatString("dd-MM-yyyy");
        dateChooserTo.getCalendarButton().setText("...");
        add(dateChooserTo);

        yPos += 35;

        // Nhóm lọc tiền
        JLabel lblMinAmount = new JLabel("Từ tiền:");
        lblMinAmount.setBounds(270, yPos, 70, 25);
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
    }

    private void initTable() {
        String[] columnNames = {"Mã phiếu nhập", "Mã nhà cung ứng", "Mã nhân viên", "Ngày lập", "Thành tiền"};
        model = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        table = new JTable(model);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 1) {
                    int row = table.getSelectedRow();
                    if (row >= 0) {
                        String maPNH = model.getValueAt(row, 0).toString();
                        dispose();
                        openChiTietPhieuNhap(maPNH);
                    }
                }
            }
        });

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(270, 280, 800, 360);
        add(scrollPane);
    }

    private void openChiTietPhieuNhap(String maPNH) {
        SwingUtilities.invokeLater(() -> {
            TTCTpnh ctForm = new TTCTpnh(); // Khởi tạo không cần tham số
            ctForm.loadPhieuNhapInfo(maPNH); // Tải thông tin sau
            ctForm.setVisible(true);
            dispose();
        });
    }

    private void performSearch() {
        String keyword = searchField.getText().trim();
        if (keyword.equals("Tìm kiếm phiếu nhập hàng")) {
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

        searchPhieuNhapHang(keyword, fromDate, toDate, minAmount, maxAmount);
    }

    private void loadDataToTable() {
        model.setRowCount(0);
        List<PhieuNhapHangDTO> danhSach = pnhBLL.layDanhSachPhieuNhapHang();
        displayData(danhSach);
    }

    private void searchPhieuNhapHang(String keyword, String fromDate, String toDate,
                                     Double minAmount, Double maxAmount) {
        model.setRowCount(0);
        List<PhieuNhapHangDTO> danhSach = pnhBLL.timKiemPhieuNhapHang(
                keyword, fromDate, toDate, minAmount, maxAmount);
        displayData(danhSach);
    }

    private void displayData(List<PhieuNhapHangDTO> danhSach) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

        for (PhieuNhapHangDTO pnh : danhSach) {
            Object[] row = {
                    pnh.getMaPNH(),
                    pnh.getMaNCU(),
                    pnh.getMaNhanVien(),
                    pnh.getNgayLapPhieu() != null ? dateFormat.format(pnh.getNgayLapPhieu()) : "N/A",
                    String.format("%,.2f", pnh.getThanhTien())
            };
            model.addRow(row);
        }
    }

    @Override
    protected void addExceltButton() {
        super.addExceltButton();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new PhieuNhapHang().setVisible(true);
        });
    }
}
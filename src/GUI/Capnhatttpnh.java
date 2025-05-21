package GUI;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import com.toedter.calendar.JDateChooser;
import bll.PhieuNhapHangBLL;
import dto.PhieuNhapHangDTO;
import dto.ChiTietPhieuNhapHangDTO;
import dal.SanPhamDAL;
import dto.SanPhamDTO;


public class Capnhatttpnh extends BaseFrame {
    private DefaultTableModel tableModel;
    private JTextField txtMaNCU, txtMaNV, txtMaPNH;
    private JDateChooser dateChooserNgay;
    private PhieuNhapHangBLL pnhBLL = new PhieuNhapHangBLL();
    private String maPNH;
    private SanPhamDAL spDAL = new SanPhamDAL();

    // Constructor không tham số
    public Capnhatttpnh() {
        super("Cập Nhật Phiếu Nhập Hàng");
        initialize();
    }

    // Constructor có tham số (giữ lại để tương thích)
    public Capnhatttpnh(String maPNH) {
        this();
        this.maPNH = maPNH;
        loadData();
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
        JLabel lblPNHLink = new JLabel("<html><u>Phiếu nhập hàng</u></html>");
        lblPNHLink.setFont(new Font("Arial", Font.BOLD, 20));
        lblPNHLink.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        lblPNHLink.setBounds(270, 70, 160, 30);
        add(lblPNHLink);

        JLabel lblTTPNHLink = new JLabel("<html>>> <u>Thông tin phiếu nhập hàng</u></html>");
        lblTTPNHLink.setFont(new Font("Arial", Font.BOLD, 20));
        lblTTPNHLink.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        lblTTPNHLink.setBounds(430, 70, 300, 30);
        add(lblTTPNHLink);

        JLabel lblArrow = new JLabel(" >> Cập nhật thông tin phiếu nhập hàng");
        lblArrow.setFont(new Font("Arial", Font.BOLD, 20));
        lblArrow.setBounds(710, 70, 900, 30);
        add(lblArrow);

        // Add listener mới với mousePressed
        lblTTPNHLink.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                dispose();
                new TTCTpnh();
            }
        });

        lblPNHLink.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                dispose();
                new PhieuNhapHang().setVisible(true);
            }
        });

        // Thông tin phiếu nhập
        initInfoSection();

        // Bảng chi tiết
        initTableSection();

        // Nút thao tác
        initActionButtons();

        loadData();
    }

    // Thêm phương thức để load dữ liệu từ bên ngoài
    public void loadPhieuNhapInfo(String maPNH) {
        this.maPNH = maPNH;
        if (maPNH != null && !maPNH.trim().isEmpty()) {
            loadData();
        }
    }

    private void initInfoSection() {
        JLabel lblMaPNHLabel = new JLabel("Mã phiếu nhập hàng:");
        lblMaPNHLabel.setBounds(270, 120, 150, 30);
        add(lblMaPNHLabel);
        txtMaPNH = new JTextField();
        txtMaPNH.setBounds(270, 150, 300, 30);
        txtMaPNH.setBackground(new Color(230, 230, 230)); // Màu nền xám
        txtMaPNH.setEditable(false); // Không cho chỉnh sửa
        txtMaPNH.setFocusable(false); // Không cho focus
        add(txtMaPNH);

        JLabel lblMNV = new JLabel("Mã nhân viên:");
        lblMNV.setBounds(700, 120, 150, 25);
        add(lblMNV);
        txtMaNV = new JTextField();
        txtMaNV.setBounds(700, 150, 300, 30);
        add(txtMaNV);

        JLabel lblMNCU = new JLabel("Mã nhà cung ứng:");
        lblMNCU.setBounds(270, 200, 150, 25);
        add(lblMNCU);
        txtMaNCU = new JTextField();
        txtMaNCU.setBounds(270, 230, 300, 30);
        add(txtMaNCU);

        JLabel lblngay = new JLabel("Ngày lập phiếu:");
        lblngay.setBounds(700, 200, 150, 25);
        add(lblngay);
        dateChooserNgay = new JDateChooser();
        dateChooserNgay.setBounds(700, 230, 300, 30);
        dateChooserNgay.setDateFormatString("dd/MM/yyyy");
        dateChooserNgay.getCalendarButton().setText("...");
        add(dateChooserNgay);
    }

    private void initTableSection() {
        String[] columns = {"Mã sản phẩm", "Tên sản phẩm", "Số lượng nhập", "Giá nhập (VND)", "Hạn sử dụng", "Số lô"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                // Allow editing quantity, price, expiry date and batch number
                return column >= 2 && column <= 5;
            }

            @Override
            public Class<?> getColumnClass(int columnIndex) {
                if (columnIndex == 4) return Date.class; // HSD column
                return Object.class;
            }
        };

        JTable table = new JTable(tableModel);
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
        table.setRowHeight(30);

//        // Thiết lập renderer cho các cột không được chỉnh sửa (0 và 1)
//        DefaultTableCellRenderer nonEditableRenderer = new DefaultTableCellRenderer() {
//            @Override
//            public Component getTableCellRendererComponent(JTable table, Object value,
//                                                           boolean isSelected, boolean hasFocus, int row, int column) {
//                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
//                c.setBackground(new Color(240, 240, 240));
//                return c;
//            }
//        };
//
//        // Áp dụng renderer cho cột 0 và 1
//        table.getColumnModel().getColumn(0).setCellRenderer(nonEditableRenderer);
//        table.getColumnModel().getColumn(1).setCellRenderer(nonEditableRenderer);

        // Định dạng hiển thị số cho cột giá nhập (3)
        DefaultTableCellRenderer numberRenderer = new DefaultTableCellRenderer() {
            @Override
            public void setValue(Object value) {
                if (value instanceof Number) {
                    setText(String.format("%,.0f", value));
                } else {
                    super.setValue(value);
                }
            }
        };
        table.getColumnModel().getColumn(3).setCellRenderer(numberRenderer);

        table.getColumnModel().getColumn(4).setCellEditor(new DefaultCellEditor(new JTextField()) {
            private JDateChooser dateChooser = new JDateChooser();
            private SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

            @Override
            public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
                dateChooser.setDateFormatString("dd/MM/yyyy");
                if (value instanceof Date) {
                    dateChooser.setDate((Date) value);
                } else if (value != null && !value.toString().isEmpty()) {
                    try {
                        dateChooser.setDate(dateFormat.parse(value.toString()));
                    } catch (Exception e) {
                        dateChooser.setDate(new Date());
                    }
                } else {
                    dateChooser.setDate(new Date());
                }
                return dateChooser;
            }

            @Override
            public Object getCellEditorValue() {
                return dateChooser.getDate() != null ? dateFormat.format(dateChooser.getDate()) : "";
            }
        });

        // Set up renderer for HSD column
        table.getColumnModel().getColumn(4).setCellRenderer(new DefaultTableCellRenderer() {
            private SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                if (value instanceof Date) {
                    value = dateFormat.format((Date) value);
                }
                return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            }
        });

        // Set up renderer for non-editable columns
        DefaultTableCellRenderer nonEditableRenderer = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                if (column < 2) { // Mã SP and Tên SP columns
                    c.setBackground(new Color(240, 240, 240));
                } else {
                    c.setBackground(Color.WHITE);
                }
                return c;
            }
        };

        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(nonEditableRenderer);
        }

        // Validate dữ liệu nhập vào cho cột số lượng (2) và giá nhập (3)
        table.setDefaultEditor(Object.class, new DefaultCellEditor(new JTextField()) {
            @Override
            public boolean stopCellEditing() {
                try {
                    String value = getCellEditorValue().toString().trim();
                    if (!value.isEmpty()) {
                        int col = table.getSelectedColumn();
                        if (col == 2) { // Cột số lượng
                            Integer.parseInt(value.replaceAll("[^\\d]", ""));
                        } else if (col == 3) { // Cột giá nhập
                            Double.parseDouble(value.replaceAll("[^\\d.]", ""));
                        }
                    }
                    return super.stopCellEditing();
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(table, "Giá trị số không hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return false;
                }
            }
        });

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(270, 310, 800, 200);
        add(scrollPane);
    }

    private void initActionButtons() {
        // Nút thêm/xóa dòng
        JButton btnThemDong = createActionButton("Thêm dòng", 270, 530, 120, 40);
        btnThemDong.setBackground(Color.decode("#4CAF50"));
        btnThemDong.addActionListener(e -> hienThiDanhSachSanPham());
        add(btnThemDong);

        JButton btnXoaDong = createActionButton("Xóa dòng", 400, 530, 120, 40);
        btnXoaDong.setBackground(Color.decode("#F44336"));
        btnXoaDong.addActionListener(e -> {
            JTable table = (JTable) ((JScrollPane) getContentPane().getComponent(6)).getViewport().getView();
            int row = table.getSelectedRow();
            if (row != -1) {
                tableModel.removeRow(row);
            } else {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn dòng cần xóa!", "Thông báo", JOptionPane.WARNING_MESSAGE);
            }
        });
        add(btnXoaDong);

        // Nút lưu và hủy
        JButton btnLuu = createActionButton("Lưu", 870, 600, 90, 40);
        btnLuu.setBackground(Color.decode("#F44336"));
        btnLuu.addActionListener(e -> luuPhieuNhapHang());
        add(btnLuu);

    }

    // Thêm phương thức hiển thị danh sách sản phẩm
    private void hienThiDanhSachSanPham() {
        JDialog dialog = new JDialog(this, "Chọn Sản Phẩm", true);
        dialog.setSize(700, 500);
        dialog.setLocationRelativeTo(this);
        dialog.setLayout(new BorderLayout());

        // Panel tìm kiếm
        JPanel searchPanel = new JPanel(new BorderLayout());
        JTextField txtSearch = new JTextField("Tìm kiếm sản phẩm");
        txtSearch.setForeground(Color.GRAY);
        searchPanel.add(txtSearch, BorderLayout.CENTER);
        dialog.add(searchPanel, BorderLayout.NORTH);

        // Bảng sản phẩm
        String[] columnNames = {"Mã SP", "Tên SP", "Mã Loại SP", "Mã Hãng sản xuất"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
        JTable table = new JTable(model);
        table.setRowHeight(25);
        JScrollPane scrollPane = new JScrollPane(table);
        dialog.add(scrollPane, BorderLayout.CENTER);

        // Panel nút chọn/hủy
        JPanel buttonPanel = new JPanel();
        JButton btnChon = new JButton("Chọn");
        JButton btnHuy = new JButton("Hủy");
        buttonPanel.add(btnChon);
        buttonPanel.add(btnHuy);
        dialog.add(buttonPanel, BorderLayout.SOUTH);

        // Load dữ liệu ban đầu
        loadSanPhamData(model, "");

        // Xử lý sự kiện focus cho ô tìm kiếm
        txtSearch.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (txtSearch.getText().equals("Tìm kiếm sản phẩm")) {
                    txtSearch.setText("");
                    txtSearch.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (txtSearch.getText().trim().isEmpty()) {
                    txtSearch.setText("Tìm kiếm sản phẩm");
                    txtSearch.setForeground(Color.GRAY);
                    loadSanPhamData(model, "");
                }
            }
        });

        // Xử lý tìm kiếm khi nhập liệu
        txtSearch.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                String keyword = txtSearch.getText().trim();
                if (keyword.equals("Tìm kiếm sản phẩm") || keyword.isEmpty()) {
                    loadSanPhamData(model, "");
                } else {
                    loadSanPhamData(model, keyword);
                }
            }
        });

        // Sự kiện chọn sản phẩm
        btnChon.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow >= 0) {
                String maSP = model.getValueAt(selectedRow, 0).toString();
                String tenSP = model.getValueAt(selectedRow, 1).toString();

                // Kiểm tra xem sản phẩm đã có trong bảng chưa
                for (int i = 0; i < tableModel.getRowCount(); i++) {
                    if (tableModel.getValueAt(i, 0).equals(maSP)) {
                        JOptionPane.showMessageDialog(dialog, "Sản phẩm này đã được thêm vào phiếu!",
                                "Thông báo", JOptionPane.WARNING_MESSAGE);
                        return;
                    }
                }

                // Thêm dòng mới với sản phẩm đã chọn
                tableModel.addRow(new Object[]{maSP, tenSP, "1", ""});
                dialog.dispose();

                // Di chuyển focus đến ô số lượng của dòng mới
                JTable mainTable = (JTable) ((JScrollPane) getContentPane().getComponent(6)).getViewport().getView();
                int newRow = tableModel.getRowCount() - 1;
                mainTable.setRowSelectionInterval(newRow, newRow);
                mainTable.setColumnSelectionInterval(2, 2);
                mainTable.editCellAt(newRow, 2);
                mainTable.getEditorComponent().requestFocusInWindow();
            } else {
                JOptionPane.showMessageDialog(dialog, "Vui lòng chọn một sản phẩm!",
                        "Thông báo", JOptionPane.WARNING_MESSAGE);
            }
        });

        // Sự kiện hủy
        btnHuy.addActionListener(e -> dialog.dispose());

        dialog.setVisible(true);
    }

    private void loadSanPhamData(DefaultTableModel model, String keyword) {
        model.setRowCount(0); // Xóa dữ liệu cũ

        List<SanPhamDTO> spList;
        if (keyword.isEmpty() || keyword.equals("Tìm kiếm sản phẩm")) {
            spList = spDAL.getAllSanPham(); // Lấy tất cả nếu không có từ khóa
        } else {
            spList = spDAL.getSanPham(keyword); // Tìm kiếm theo từ khóa
        }

        for (SanPhamDTO sp : spList) {
            model.addRow(new Object[]{
                    sp.getMaSP(),
                    sp.getTenSP(),
                    sp.getMaLSP(),
                    sp.getMaHSX()
            });
        }
    }


    private void loadData() {
        if (maPNH == null) return;

        PhieuNhapHangDTO pnh = pnhBLL.layPhieuNhapHangTheoMa(maPNH);
        if (pnh != null) {
            txtMaPNH.setText(maPNH);

            txtMaNV.setText(pnh.getMaNhanVien());
            txtMaNCU.setText(pnh.getMaNCU());
            dateChooserNgay.setDate(pnh.getNgayLapPhieu() != null ?
                    pnh.getNgayLapPhieu() :
                    new Date());

            List<ChiTietPhieuNhapHangDTO> danhSachChiTiet = pnhBLL.layChiTietPhieuNhapHang(maPNH);
            tableModel.setRowCount(0);
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

            for (ChiTietPhieuNhapHangDTO ct : danhSachChiTiet) {
                SanPhamDTO sp = spDAL.getSanPhamById(ct.getMaSP());
                String tenSP = sp != null ? sp.getTenSP() : "Không xác định";

                tableModel.addRow(new Object[]{
                        ct.getMaSP(),
                        tenSP,
                        ct.getSoLuongNhap(),
                        ct.getGiaNhap(),
                        ct.getHsd() != null ? dateFormat.format(ct.getHsd()) : "",
                        ct.getSoLo() != null ? ct.getSoLo() : ""
                });
            }
        }
    }

    private void luuPhieuNhapHang() {
        // Validate dữ liệu cơ bản
        if (txtMaNCU.getText().trim().isEmpty() || txtMaNV.getText().trim().isEmpty() ||
                dateChooserNgay.getDate() == null) {  // Changed validation
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Validate ngày
        Date ngayLap = dateChooserNgay.getDate();

        // Validate nhân viên và nhà cung ứng
        if (!pnhBLL.kiemTraNhanVienTonTai(txtMaNV.getText())) {
            JOptionPane.showMessageDialog(this, "Mã nhân viên không tồn tại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!pnhBLL.kiemTraNhaCungUngTonTai(txtMaNCU.getText())) {
            JOptionPane.showMessageDialog(this, "Mã nhà cung ứng không tồn tại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Lấy dữ liệu từ bảng
        List<ChiTietPhieuNhapHangDTO> danhSachChiTiet = new ArrayList<>();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

        for (int i = 0; i < tableModel.getRowCount(); i++) {
            try {
                String maSP = tableModel.getValueAt(i, 0).toString().trim();
                String slStr = tableModel.getValueAt(i, 2).toString().trim();
                String giaStr = tableModel.getValueAt(i, 3).toString().trim();
                String hsdStr = tableModel.getValueAt(i, 4).toString().trim();
                String soLo = tableModel.getValueAt(i, 5).toString().trim();

                if (maSP.isEmpty() || slStr.isEmpty() || giaStr.isEmpty() || hsdStr.isEmpty()) {
                    JOptionPane.showMessageDialog(this,
                            "Dòng " + (i+1) + ": Vui lòng nhập đầy đủ thông tin chi tiết!",
                            "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                int soLuong = Integer.parseInt(slStr.replaceAll("[^\\d]", ""));
                double giaNhap = Double.parseDouble(giaStr.replaceAll("[^\\d.]", ""));
                Date hsd = dateFormat.parse(hsdStr);

                if (soLuong <= 0 || giaNhap <= 0) {
                    JOptionPane.showMessageDialog(this,
                            "Dòng " + (i+1) + ": Số lượng và giá nhập phải lớn hơn 0!",
                            "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Create detail with all fields including HSD and SoLo
                ChiTietPhieuNhapHangDTO ct = new ChiTietPhieuNhapHangDTO(
                        maPNH, maSP, hsd, soLo, soLuong, giaNhap
                );
                danhSachChiTiet.add(ct);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this,
                        "Dòng " + (i+1) + ": Dữ liệu không hợp lệ! " + e.getMessage(),
                        "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }

        // Tạo DTO và cập nhật
        PhieuNhapHangDTO pnh = new PhieuNhapHangDTO(
                maPNH,
                txtMaNCU.getText(),
                txtMaNV.getText(),
                ngayLap,
                0
        );

        if (pnhBLL.capNhatPhieuNhapHang(pnh, danhSachChiTiet)) {
            JOptionPane.showMessageDialog(this, "Cập nhật thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            dispose();
            new TTCTpnh();
        } else {
            JOptionPane.showMessageDialog(this, "Cập nhật thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new Capnhatttpnh().setVisible(true);
        });
    }
}
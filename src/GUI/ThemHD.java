package GUI;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.toedter.calendar.JDateChooser;
import bll.HoaDonBLL;
import dto.HoaDonDTO;
import dto.ChiTietHoaDonDTO;
import dal.NhanViendal;
import dal.khachhangdal;
import dto.NhanVienDTO;
import dto.KhachHangDTO;
import dal.SanPhamDAL;
import dto.SanPhamDTO;
import helper.PDFGenerator;

public class ThemHD extends BaseFrame {
    private DefaultTableModel tableModel;
    private JTable table;
    private JTextField txtMaHD, txtMaKH, txtMaNV;
    private JDateChooser dateChooserNgay;
    private HoaDonBLL hdBLL = new HoaDonBLL();
    private NhanViendal nvDAL = new NhanViendal();
    private khachhangdal khDAL = new khachhangdal();
    private SanPhamDAL spDAL = new SanPhamDAL();
    private JLabel txtThanhTien;

    public ThemHD() {
        super("Thêm Hóa Đơn");
        initialize();
    }

    @Override
    protected void initUniqueComponents() {
        // Highlight menu button
        for (JButton btn : menuButtons) {
            if (btn.getText().equals("Hóa đơn")) {
                btn.setBackground(Color.decode("#EF5D7A"));
                btn.setFont(new Font("Arial", Font.BOLD, 14));
            } else {
                btn.setBackground(Color.decode("#641A1F"));
                btn.setFont(new Font("Arial", Font.BOLD, 12));
            }
        }

        // Tiêu đề
        JLabel lblHoaDonLink = new JLabel("<html><u>Hóa đơn</u></html>");
        lblHoaDonLink.setFont(new Font("Arial", Font.BOLD, 20));
        lblHoaDonLink.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        lblHoaDonLink.setBounds(270, 70, 160, 30);
        add(lblHoaDonLink);

        JLabel lblArrow = new JLabel(" >> Thêm hóa đơn");
        lblArrow.setFont(new Font("Arial", Font.BOLD, 20));
        lblArrow.setBounds(410, 70, 300, 30);
        add(lblArrow);

        lblHoaDonLink.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                dispose();
                new HoaDon().setVisible(true);
            }
        });

        // Form thông tin
        initInfoForm();

        // Bảng chi tiết
        initDetailTable();

        // Thành tiền
        initThanhTien();

        // Nút thao tác
        initActionButtons();
    }

    private void initInfoForm() {
        JLabel lblMaHD = new JLabel("Mã hóa đơn:");
        lblMaHD.setBounds(270, 120, 150, 30);
        add(lblMaHD);

        txtMaHD = new JTextField();
        txtMaHD.setBounds(270, 150, 300, 30);
        add(txtMaHD);

        JLabel lblMNV = new JLabel("Nhân viên:");
        lblMNV.setBounds(700, 120, 150, 25);
        add(lblMNV);

        txtMaNV = new JTextField();
        txtMaNV.setBounds(700, 150, 250, 30);
        add(txtMaNV);

        JButton btnChonNV = createActionButton("Chọn", 960, 150, 80, 30);
        btnChonNV.addActionListener(e -> hienThiDanhSachNhanVien());
        add(btnChonNV);

        JLabel lblMKH = new JLabel("Khách hàng (nếu có):");
        lblMKH.setBounds(270, 200, 150, 25);
        add(lblMKH);

        txtMaKH = new JTextField();
        txtMaKH.setBounds(270, 230, 250, 30);
        add(txtMaKH);

        JButton btnChonKH = createActionButton("Chọn", 530, 230, 80, 30);
        btnChonKH.addActionListener(e -> hienThiDanhSachKhachHang());
        add(btnChonKH);

        JLabel lblngay = new JLabel("Ngày lập hóa đơn:");
        lblngay.setBounds(700, 200, 150, 25);
        add(lblngay);

        dateChooserNgay = new JDateChooser();
        dateChooserNgay.setBounds(700, 230, 300, 30);
        dateChooserNgay.setDateFormatString("dd/MM/yyyy");
        dateChooserNgay.setDate(new Date());
        add(dateChooserNgay);
    }

    private void initDetailTable() {
        JLabel lblChiTiet = new JLabel("Chi tiết hóa đơn:");
        lblChiTiet.setBounds(270, 280, 200, 30);
        add(lblChiTiet);

        String[] columns = {"Mã sản phẩm", "Tên sản phẩm", "Số lượng", "Đơn giá"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                // Chỉ cho phép chỉnh sửa cột số lượng
                return column == 2;
            }
        };
        table = new JTable(tableModel);
        table.getTableHeader().setPreferredSize(new Dimension(0, 35));
        table.setRowHeight(30);
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
        table.setFont(new Font("Arial", Font.PLAIN, 14));

        // Thiết lập renderer cho các cột
        table.getColumnModel().getColumn(0).setCellRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                c.setBackground(new Color(240, 240, 240));
                return c;
            }
        });

        table.getColumnModel().getColumn(1).setCellRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                c.setBackground(new Color(240, 240, 240));
                return c;
            }
        });

        table.getColumnModel().getColumn(3).setCellRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                c.setBackground(new Color(240, 240, 240));
                return c;
            }
        });

        // Thêm listener để tính lại thành tiền khi số lượng thay đổi
        tableModel.addTableModelListener(e -> {
            if (e.getColumn() == 2) { // Nếu cột số lượng thay đổi
                calculateThanhTien();
            }
        });

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(270, 310, 800, 200);
        add(scrollPane);

        // Nút thêm/xóa dòng
        JButton btnThemDong = createActionButton("Thêm dòng", 800, 520, 100, 30);
        btnThemDong.setBackground(Color.decode("#F5A623"));
        btnThemDong.addActionListener(e -> hienThiDanhSachSanPham());
        add(btnThemDong);

        JButton btnXoaDong = createActionButton("Xóa dòng", 800, 560, 100, 30);
        btnXoaDong.setBackground(Color.decode("#D0021B"));
        btnXoaDong.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row != -1) {
                tableModel.removeRow(row);
                calculateThanhTien();
            } else {
                JOptionPane.showMessageDialog(this, "Chọn dòng để xóa!", "Thông báo", JOptionPane.WARNING_MESSAGE);
            }
        });
        add(btnXoaDong);

        // Thiết lập navigation bằng phím
        setupKeyboardNavigation();
    }

    private void initThanhTien() {
        JLabel lblThanhTien = new JLabel("Thành tiền:");
        lblThanhTien.setBounds(270, 520, 200, 30);
        lblThanhTien.setFont(new Font("Arial", Font.BOLD, 25));
        add(lblThanhTien);

        txtThanhTien = new JLabel("0");
        txtThanhTien.setBounds(270, 560, 300, 30);
        txtThanhTien.setFont(new Font("Arial", Font.BOLD, 40));
        txtThanhTien.setForeground(Color.decode("#641A1F"));
        add(txtThanhTien);
    }

    private void calculateThanhTien() {
        double tongTien = 0;
        for (int i = 0; i < tableModel.getRowCount(); i++) {
            try {
                int soLuong = Integer.parseInt(tableModel.getValueAt(i, 2).toString());
                double donGia = Double.parseDouble(tableModel.getValueAt(i, 3).toString());
                tongTien += soLuong * donGia;
            } catch (NumberFormatException e) {
                // Bỏ qua nếu có lỗi chuyển đổi số
            }
        }
        txtThanhTien.setText(String.format("%,.0f", tongTien));
    }

    private void initActionButtons() {
        JButton btnLuu = createActionButton("Lưu", 800, 600, 100, 30);
        btnLuu.setBackground(Color.decode("#F0483E"));
        btnLuu.addActionListener(e -> luuHoaDon());
        add(btnLuu);

        getRootPane().setDefaultButton(btnLuu);
    }

    private void setupKeyboardNavigation() {
        JTextField[] textFields = {txtMaHD, txtMaNV, txtMaKH};

        for (int i = 0; i < textFields.length; i++) {
            final int currentIndex = i;
            textFields[i].addKeyListener(new KeyAdapter() {
                @Override
                public void keyPressed(KeyEvent e) {
                    int key = e.getKeyCode();
                    if (key == KeyEvent.VK_DOWN) {
                        if (currentIndex < textFields.length - 1) {
                            textFields[currentIndex + 1].requestFocus();
                        } else {
                            table.requestFocus();
                            if (table.getRowCount() > 0) {
                                table.setRowSelectionInterval(0, 0);
                                table.setColumnSelectionInterval(0, 0);
                            }
                        }
                    } else if (key == KeyEvent.VK_UP) {
                        if (currentIndex > 0) {
                            textFields[currentIndex - 1].requestFocus();
                        }
                    } else if (key == KeyEvent.VK_ENTER) {
                        if (currentIndex < textFields.length - 1) {
                            textFields[currentIndex + 1].requestFocus();
                        } else {
                            table.requestFocus();
                            if (table.getRowCount() > 0) {
                                table.setRowSelectionInterval(0, 0);
                                table.setColumnSelectionInterval(0, 0);
                            }
                        }
                    }
                }
            });
        }

        table.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                int key = e.getKeyCode();
                int row = table.getSelectedRow();
                int col = table.getSelectedColumn();

                if (key == KeyEvent.VK_UP && row == 0) {
                    txtMaKH.requestFocus();
                } else if (key == KeyEvent.VK_DOWN && row == table.getRowCount() - 1) {
                    tableModel.addRow(new Object[]{"", "", "1", ""});
                    table.setRowSelectionInterval(row + 1, row + 1);
                    table.setColumnSelectionInterval(0, 0);
                } else if (key == KeyEvent.VK_ENTER || key == KeyEvent.VK_TAB) {
                    if (col < table.getColumnCount() - 1) {
                        table.setColumnSelectionInterval(col + 1, col + 1);
                    } else if (row < table.getRowCount() - 1) {
                        table.setRowSelectionInterval(row + 1, row + 1);
                        table.setColumnSelectionInterval(0, 0);
                    } else {
                        tableModel.addRow(new Object[]{"", "", "1", ""});
                        table.setRowSelectionInterval(row + 1, row + 1);
                        table.setColumnSelectionInterval(0, 0);
                    }
                }
            }
        });
    }

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
        String[] columnNames = {"Mã SP", "Tên SP", "Mã Loại SP", "Mã Hãng sản xuất", "Giá bán"};
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
                String donGia = model.getValueAt(selectedRow, 4).toString();

                // Kiểm tra xem sản phẩm đã có trong bảng chưa
                for (int i = 0; i < tableModel.getRowCount(); i++) {
                    if (tableModel.getValueAt(i, 0).equals(maSP)) {
                        JOptionPane.showMessageDialog(dialog, "Sản phẩm này đã được thêm vào hóa đơn!",
                                "Thông báo", JOptionPane.WARNING_MESSAGE);
                        return;
                    }
                }

                // Thêm dòng mới với sản phẩm đã chọn
                tableModel.addRow(new Object[]{maSP, tenSP, "1", donGia});
                dialog.dispose();

                // Di chuyển focus đến ô số lượng của dòng mới
                int newRow = tableModel.getRowCount() - 1;
                table.setRowSelectionInterval(newRow, newRow);
                table.setColumnSelectionInterval(2, 2);
                table.editCellAt(newRow, 2);
                table.getEditorComponent().requestFocusInWindow();

                // Tính lại thành tiền
                calculateThanhTien();
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
                    sp.getMaHSX(),
                    sp.getGiaBan()
            });
        }
    }

    private void hienThiDanhSachNhanVien() {
        JDialog dialog = new JDialog(this, "Chọn Nhân Viên", true);
        dialog.setSize(400, 300);
        dialog.setLocationRelativeTo(this);

        List<NhanVienDTO> nvList = nvDAL.getDanhSachNhanVien();
        String[] columnNames = {"Mã NV", "Họ tên"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);

        for (NhanVienDTO nv : nvList) {
            model.addRow(new Object[]{nv.getMaNhanVien(), nv.getHoTen()});
        }

        JTable table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        dialog.add(scrollPane);

        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int selectedRow = table.getSelectedRow();
                String maNVSelected = model.getValueAt(selectedRow, 0).toString().trim(); // Thêm trim() ở đây
                txtMaNV.setText(maNVSelected);
                dialog.dispose();
            }
        });

        dialog.setVisible(true);
    }

    private void hienThiDanhSachKhachHang() {
        JDialog dialog = new JDialog(this, "Chọn Khách Hàng", true);
        dialog.setSize(400, 300);
        dialog.setLocationRelativeTo(this);

        List<KhachHangDTO> khList = khDAL.layDSKhachHang();
        String[] columnNames = {"Mã KH", "Họ tên"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);

        for (KhachHangDTO kh : khList) {
            model.addRow(new Object[]{kh.getMaKH(), kh.getHoTen()});
        }

        JTable table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        dialog.add(scrollPane);

        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int selectedRow = table.getSelectedRow();
                txtMaKH.setText(model.getValueAt(selectedRow, 0).toString().trim());
                dialog.dispose();
            }
        });

        dialog.setVisible(true);
    }

    private void luuHoaDon() {
        // Validate dữ liệu cơ bản
        String maHD = txtMaHD.getText().trim();
        String maKH = txtMaKH.getText().trim();
        String maNV = txtMaNV.getText().trim();
        Date ngayLap = dateChooserNgay.getDate();


        if (maHD.isEmpty() || maNV.isEmpty() || ngayLap == null) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (hdBLL.kiemTraMaHDTonTai(maHD)) {
            JOptionPane.showMessageDialog(this, "Mã hóa đơn đã tồn tại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!hdBLL.kiemTraNhanVienTonTai(maNV)) {
            JOptionPane.showMessageDialog(this, "Mã nhân viên không tồn tại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!maKH.isEmpty() && !hdBLL.kiemTraKhachHangTonTai(maKH)) {
            JOptionPane.showMessageDialog(this, "Mã khách hàng không tồn tại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }


        // Lấy danh sách chi tiết
        List<ChiTietHoaDonDTO> danhSachChiTiet = new ArrayList<>();
        for (int i = 0; i < tableModel.getRowCount(); i++) {
            String maSP = tableModel.getValueAt(i, 0).toString().trim();
            String slStr = tableModel.getValueAt(i, 2).toString().trim();
            String giaStr = tableModel.getValueAt(i, 3).toString().trim();

            if (maSP.isEmpty() || slStr.isEmpty() || giaStr.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin chi tiết!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                int soLuong = Integer.parseInt(slStr);
                double donGia = Double.parseDouble(giaStr);

                if (soLuong <= 0 || donGia <= 0) {
                    JOptionPane.showMessageDialog(this, "Số lượng và đơn giá phải lớn hơn 0!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                danhSachChiTiet.add(new ChiTietHoaDonDTO(maHD, maSP, soLuong, donGia));
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Số lượng và đơn giá phải là số!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }

        if (danhSachChiTiet.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng thêm ít nhất 1 sản phẩm!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Tạo DTO và lưu vào DB
        HoaDonDTO hd = new HoaDonDTO(maHD, maNV, maKH.isEmpty() ? null : maKH, ngayLap);

        // Tính tổng tiền trước khi lưu
        double tongTien = 0;
        for (ChiTietHoaDonDTO ct : danhSachChiTiet) {
            tongTien += ct.getSoLuong() * ct.getGia();
        }
        hd.setThanhTien(tongTien);


        if (hdBLL.themHoaDonVoiChiTiet(hd, danhSachChiTiet)) {

            // Đảm bảo dữ liệu được commit vào database
            hdBLL.commitTransaction();

            // Thông báo thành công và hỏi có muốn in hóa đơn không
            int option = JOptionPane.showConfirmDialog(
                    this,
                    "Thêm hóa đơn thành công! Bạn có muốn in hóa đơn không?",
                    "In hóa đơn",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE
            );

            if (option == JOptionPane.YES_OPTION) {
                // Tạo một hóa đơn tạm từ dữ liệu hiện tại
                HoaDonDTO tempHD = new HoaDonDTO(
                        maHD,
                        maNV,
                        maKH.isEmpty() ? null : maKH,
                        ngayLap,
                        Double.parseDouble(txtThanhTien.getText().replace(",", "")),
                        "BINH_THUONG"
                );

                // Tạo danh sách chi tiết tạm
                List<ChiTietHoaDonDTO> tempChiTiet = new ArrayList<>();
                for (int i = 0; i < tableModel.getRowCount(); i++) {
                    String maSP = tableModel.getValueAt(i, 0).toString();
                    int soLuong = Integer.parseInt(tableModel.getValueAt(i, 2).toString());
                    double gia = Double.parseDouble(tableModel.getValueAt(i, 3).toString());
                    tempChiTiet.add(new ChiTietHoaDonDTO(maHD, maSP, soLuong, gia));
                }

                // Lấy danh sách tên sản phẩm
                List<String> danhSachMaSP = tempChiTiet.stream()
                        .map(ChiTietHoaDonDTO::getMaSanPham)
                        .collect(Collectors.toList());
                Map<String, String> tenSanPhamMap = hdBLL.layDanhSachTenSanPham(danhSachMaSP);

                // Xuất PDF từ dữ liệu tạm
                try {
                    new PDFGenerator().exportHoaDonToPDF(this, tempHD, tempChiTiet, tenSanPhamMap);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this,
                            "Lỗi khi xuất PDF: " + ex.getMessage(),
                            "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            }

            dispose();
            new HoaDon().setVisible(true);
        } else {
            JOptionPane.showMessageDialog(this, "Thêm hóa đơn thất bại! Vui lòng kiểm tra log hệ thống.", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new ThemHD().setVisible(true);
        });
    }
}
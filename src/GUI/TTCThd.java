package GUI;

import bll.HoaDonBLL;
import dto.HoaDonDTO;
import dto.ChiTietHoaDonDTO;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class TTCThd extends BaseFrame {
    private HoaDonBLL hdBLL = new HoaDonBLL();
    private String maHoaDon;
    private DefaultTableModel tableModel;
    private JTable table;
    private JLabel txtMaHD, txtMaNV, txtMaKH, txtNgay, txtThanhTien;

    public TTCThd() {
        super("Thông tin chi tiết hóa đơn");
        initialize();
    }

    // Method to set the bill ID after construction
    public void loadHoaDonInfo(String maHoaDon) {
        this.maHoaDon = maHoaDon;
        if (maHoaDon != null && !maHoaDon.trim().isEmpty()) {
            loadHoaDonData();
        }
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

        // Tiêu đề và breadcrumb
        initHeaderSection();

        // Form thông tin hóa đơn
        initInfoForm();

        // Bảng chi tiết sản phẩm
        initProductTable();

        // Nút xóa
        initDeleteButton();

        // Nút cập nhật
        initUpdateButton();

        // Thêm kiểm tra nếu có maHoaDon thì mới load data
        if (maHoaDon != null && !maHoaDon.trim().isEmpty()) {
            loadHoaDonData();
        }

        addPDFButton();
    }

    private void initHeaderSection() {
        JLabel lblHoaDonLink = new JLabel("<html><u>Hóa đơn</u></html>");
        lblHoaDonLink.setFont(new Font("Arial", Font.BOLD, 20));
        lblHoaDonLink.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        lblHoaDonLink.setBounds(270, 70, 100, 30);
        add(lblHoaDonLink);

        lblHoaDonLink.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                dispose();
                new HoaDon().setVisible(true);
            }
        });

        JLabel lblArrow = new JLabel(" >> Thông tin hóa đơn");
        lblArrow.setFont(new Font("Arial", Font.BOLD, 20));
        lblArrow.setBounds(350, 70, 300, 30);
        add(lblArrow);
    }

    private void initInfoForm() {
        // Mã hóa đơn
        JLabel lblMaHD = new JLabel("Mã hóa đơn:");
        lblMaHD.setBounds(270, 120, 150, 30);
        add(lblMaHD);
        txtMaHD = new JLabel();
        txtMaHD.setBounds(270, 150, 300, 30);
        txtMaHD.setFont(new Font("Arial", Font.BOLD, 20));
        txtMaHD.setForeground(Color.decode("#641A1F"));
        add(txtMaHD);

        // Mã nhân viên
        JLabel lblMaNV = new JLabel("Mã nhân viên:");
        lblMaNV.setBounds(270, 200, 150, 30);
        add(lblMaNV);
        txtMaNV = new JLabel();
        txtMaNV.setBounds(270, 230, 300, 30);
        txtMaNV.setFont(new Font("Arial", Font.BOLD, 20));
        txtMaNV.setForeground(Color.decode("#641A1F"));
        add(txtMaNV);

        // Mã khách hàng
        JLabel lblMaKH = new JLabel("Mã khách hàng:");
        lblMaKH.setBounds(270, 280, 150, 30);
        add(lblMaKH);
        txtMaKH = new JLabel();
        txtMaKH.setBounds(270, 310, 300, 30);
        txtMaKH.setFont(new Font("Arial", Font.BOLD, 20));
        txtMaKH.setForeground(Color.decode("#641A1F"));
        add(txtMaKH);

        // Ngày lập hóa đơn
        JLabel lblNgay = new JLabel("Ngày lập hóa đơn:");
        lblNgay.setBounds(700, 200, 150, 30);
        add(lblNgay);
        txtNgay = new JLabel();
        txtNgay.setBounds(700, 230, 300, 30);
        txtNgay.setFont(new Font("Arial", Font.BOLD, 20));
        txtNgay.setForeground(Color.decode("#641A1F"));
        add(txtNgay);

        // Thành tiền
        JLabel lblThanhTien = new JLabel("Thành tiền:");
        lblThanhTien.setBounds(700, 120, 150, 30);
        add(lblThanhTien);
        txtThanhTien = new JLabel();
        txtThanhTien.setBounds(700, 150, 300, 30);
        txtThanhTien.setFont(new Font("Arial", Font.BOLD, 20));
        txtThanhTien.setForeground(Color.decode("#641A1F"));
        add(txtThanhTien);
    }

    private void initProductTable() {
        String[] columnNames = { "Mã sản phẩm", "Tên sản phẩm", "Số lượng", "Đơn giá", "Thành tiền" };
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        table = new JTable(tableModel);
        table.setRowHeight(30);
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        table.setFont(new Font("Arial", Font.PLAIN, 14));

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(270, 360, 800, 200);
        add(scrollPane);
    }

    private void initDeleteButton() {
        JButton btnXoa = createActionButton("Xóa hóa đơn", 850, 580, 150, 40);
        btnXoa.addActionListener(e -> deleteHoaDon());
        add(btnXoa);
    }

    private void initUpdateButton() {
        JButton btnCapNhat = createActionButton("Cập nhật", 870, 580, 150, 40);
        btnCapNhat.addActionListener(e -> {
            CapnhatttHD capNhatFrame = new CapnhatttHD(txtMaHD.getText());

            // Tạo listener tạm
            WindowListener listener = new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    capNhatFrame.removeWindowListener(this); // Quan trọng: xóa listener ngay sau dùng
                    dispose();
                    new TTCThd().loadHoaDonInfo(txtMaHD.getText());
                    setVisible(true);
                }
            };

            capNhatFrame.addWindowListener(listener);
            dispose();
            capNhatFrame.setVisible(true);
        });
        add(btnCapNhat);
    }

    private void loadHoaDonData() {
        HoaDonDTO hd = hdBLL.layHoaDonTheoMa(maHoaDon);
        if (hd != null) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

            txtMaHD.setText(hd.getMaHoaDon());
            txtMaNV.setText(hd.getMaNhanVien());
            txtMaKH.setText(hd.getMaKH() != null ? hd.getMaKH() : "Không có");
            txtNgay.setText(dateFormat.format(hd.getNgayBan()));
            txtThanhTien.setText(String.format("%,.0f VNĐ", hd.getThanhTien()));

            loadChiTietHoaDon();
        } else {
            JOptionPane.showMessageDialog(this, "Không tìm thấy hóa đơn!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            dispose();
        }
    }

    private void loadChiTietHoaDon() {
        tableModel.setRowCount(0);
        List<ChiTietHoaDonDTO> chiTiet = hdBLL.layChiTietHoaDon(maHoaDon);

        List<String> danhSachMaSP = chiTiet.stream()
                .map(ChiTietHoaDonDTO::getMaSanPham)
                .collect(Collectors.toList());

        Map<String, String> tenSanPhamMap = hdBLL.layDanhSachTenSanPham(danhSachMaSP);

        for (ChiTietHoaDonDTO ct : chiTiet) {
            String tenSP = tenSanPhamMap.getOrDefault(ct.getMaSanPham(), "Không xác định");
            double thanhTien = ct.getSoLuong() * ct.getGia();

            Object[] row = {
                    ct.getMaSanPham(),
                    tenSP,
                    ct.getSoLuong(),
                    String.format("%,.0f VNĐ", ct.getGia()),
                    String.format("%,.0f VNĐ", thanhTien)
            };
            tableModel.addRow(row);
        }
    }

    private void deleteHoaDon() {
        int confirm = JOptionPane.showConfirmDialog(
                this,
                "Bạn có chắc chắn muốn xóa hóa đơn này? Thao tác này không thể hoàn tác!",
                "Xác nhận xóa",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE
        );

        if (confirm == JOptionPane.YES_OPTION) {
            boolean result = hdBLL.xoaHoaDon(maHoaDon);
            if (result) {
                JOptionPane.showMessageDialog(
                        this,
                        "Xóa hóa đơn thành công!",
                        "Thông báo",
                        JOptionPane.INFORMATION_MESSAGE
                );
                dispose();
                new HoaDon().setVisible(true);
            } else {
                JOptionPane.showMessageDialog(
                        this,
                        "Xóa hóa đơn thất bại!",
                        "Lỗi",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new TTCThd().setVisible(true);
        });
    }
}
package GUI;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.List;
import bll.PhieuNhapHangBLL;
import dto.PhieuNhapHangDTO;
import dto.ChiTietPhieuNhapHangDTO;

public class TTCTpnh extends BaseFrame {
    private DefaultTableModel tableModel;
    private JLabel lblMaPNH, lblMaNV, lblMaNCU, lblNgay, lblTongTien;
    private PhieuNhapHangBLL pnhBLL = new PhieuNhapHangBLL();
    private String maPNH;
    private JTable table;

    public TTCTpnh() {
        super("Chi Tiết Phiếu Nhập Hàng");
        initialize();
    }

    // Giữ lại constructor cũ để tương thích ngược
    public TTCTpnh(String maPNH) {
        this();
        this.maPNH = maPNH;
        loadData();
    }

    // Thêm phương thức này
    public void loadPhieuNhapInfo(String maPNH) {
        this.maPNH = maPNH;
        if (maPNH != null && !maPNH.trim().isEmpty()) {
            loadData();
        }
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

        JLabel lblArrow = new JLabel(" >> Thông tin phiếu nhập hàng");
        lblArrow.setFont(new Font("Arial", Font.BOLD, 20));
        lblArrow.setBounds(430, 70, 300, 30);
        add(lblArrow);

        lblPNHLink.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                dispose();
                new PhieuNhapHang().setVisible(true);
            }
        });

        // Thông tin phiếu nhập
        initInfoSection();
        initTableSection();
        initActionButtons();

        // Thêm kiểm tra nếu có maPNH thì mới load data
        if (maPNH != null && !maPNH.trim().isEmpty()) {
            loadData();
        }

        addPDFButton();
    }

    private void initInfoSection() {
        JLabel lblMaPNHLabel = new JLabel("Mã phiếu nhập hàng:");
        lblMaPNHLabel.setBounds(270, 120, 150, 30);
        add(lblMaPNHLabel);

        lblMaPNH = new JLabel();
        lblMaPNH.setBounds(270, 150, 400, 40);
        lblMaPNH.setForeground(Color.decode("#641A1F"));
        lblMaPNH.setFont(new Font("Arial", Font.BOLD, 20));
        add(lblMaPNH);

        JLabel lblMNVLabel = new JLabel("Mã nhân viên:");
        lblMNVLabel.setBounds(700, 120, 150, 25);
        add(lblMNVLabel);

        lblMaNV = new JLabel();
        lblMaNV.setBounds(700, 150, 400, 40);
        lblMaNV.setForeground(Color.decode("#641A1F"));
        lblMaNV.setFont(new Font("Arial", Font.BOLD, 20));
        add(lblMaNV);

        JLabel lblMNCULabel = new JLabel("Mã nhà cung ứng:");
        lblMNCULabel.setBounds(270, 200, 150, 25);
        add(lblMNCULabel);

        lblMaNCU = new JLabel();
        lblMaNCU.setBounds(270, 230, 400, 40);
        lblMaNCU.setForeground(Color.decode("#641A1F"));
        lblMaNCU.setFont(new Font("Arial", Font.BOLD, 20));
        add(lblMaNCU);

        JLabel lblngayLabel = new JLabel("Ngày lập phiếu:");
        lblngayLabel.setBounds(700, 200, 150, 25);
        add(lblngayLabel);

        lblNgay = new JLabel();
        lblNgay.setBounds(700, 230, 400, 40);
        lblNgay.setForeground(Color.decode("#641A1F"));
        lblNgay.setFont(new Font("Arial", Font.BOLD, 20));
        add(lblNgay);

        JLabel lbltienLabel = new JLabel("Thành tiền:");
        lbltienLabel.setBounds(270, 280, 150, 25);
        add(lbltienLabel);

        lblTongTien = new JLabel();
        lblTongTien.setBounds(270, 310, 300, 40);
        lblTongTien.setForeground(Color.decode("#F5CB21"));
        lblTongTien.setFont(new Font("Arial", Font.BOLD, 20));
        add(lblTongTien);
    }

    private void initTableSection() {
        tableModel = new DefaultTableModel();
        tableModel.addColumn("Mã sản phẩm");
        tableModel.addColumn("Số lượng");
        tableModel.addColumn("Giá nhập");
        tableModel.addColumn("Thành tiền");

        table = new JTable(tableModel);
        table.setRowHeight(30);
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
        table.setFont(new Font("Arial", Font.PLAIN, 14));

        JTable table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(270, 360, 800, 200);
        add(scrollPane);
    }

    private void initActionButtons() {
        JButton btnXoa = createActionButton("Xóa", 700, 580, 120, 40);
        btnXoa.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(this,
                    "Bạn có chắc chắn muốn xóa phiếu nhập hàng này?",
                    "Xác nhận", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                if (pnhBLL.xoaPhieuNhapHang(lblMaPNH.getText())) {
                    JOptionPane.showMessageDialog(this,
                            "Xóa phiếu nhập hàng thành công!",
                            "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                    dispose();
                    new PhieuNhapHang().setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(this,
                            "Xóa phiếu nhập hàng thất bại!",
                            "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        add(btnXoa);

        JButton btnCapnhat = createActionButton("Cập nhật", 860, 580, 120, 40);
        btnCapnhat.addActionListener(e -> {
            Capnhatttpnh capNhatFrame = new Capnhatttpnh(lblMaPNH.getText());

            // Tạo listener tạm
            WindowListener listener = new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    capNhatFrame.removeWindowListener(this); // Quan trọng: xóa listener ngay sau dùng
                    dispose();
                    new TTCTpnh(lblMaPNH.getText()).setVisible(true);
                }
            };

            capNhatFrame.addWindowListener(listener);
            dispose();
            capNhatFrame.setVisible(true);
        });
        add(btnCapnhat);
    }

    private void loadData() {
        try {
            PhieuNhapHangDTO pnh = pnhBLL.layPhieuNhapHangTheoMa(maPNH);
            if (pnh != null) {
                lblMaPNH.setText(pnh.getMaPNH());
                lblMaNV.setText(pnh.getMaNhanVien());
                lblMaNCU.setText(pnh.getMaNCU());
                lblNgay.setText(new SimpleDateFormat("dd/MM/yyyy").format(pnh.getNgayLapPhieu()));
                lblTongTien.setText(String.format("%,.0f VND", pnh.getThanhTien()));

                List<ChiTietPhieuNhapHangDTO> danhSachChiTiet = pnhBLL.layChiTietPhieuNhapHang(maPNH);
                tableModel.setRowCount(0);
                for (ChiTietPhieuNhapHangDTO ct : danhSachChiTiet) {
                    double thanhTien = ct.getSoLuongNhap() * ct.getGiaNhap();
                    tableModel.addRow(new Object[]{
                            ct.getMaSP(),
                            ct.getSoLuongNhap(),
                            String.format("%,.0f VND", ct.getGiaNhap()),
                            String.format("%,.0f VND", thanhTien)
                    });
                }
            }
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(this, "Không tìm thấy phiếu nhập hàng!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            dispose();
            new PhieuNhapHang().setVisible(true);
        }
    }

    @Override
    protected void addPDFButton() {
        super.addPDFButton();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new TTCTpnh().setVisible(true);
        });
    }
}
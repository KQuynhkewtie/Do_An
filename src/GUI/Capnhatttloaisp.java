package GUI;

import bll.LoaiSPBLL;
import dal.loaispdal;
import dto.LoaiSPDTO;
import dto.currentuser;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Capnhatttloaisp extends BaseFrame {
    private LoaiSPBLL blllsp = new LoaiSPBLL();
    private JTextField  txtTenLSP, txtMaLSP;
    private JRadioButton rbSD, rbNSD;
    private ButtonGroup groupTrangThai;
    public Capnhatttloaisp() {
        super("Cập nhật thông tin loại sản phẩm");
        initialize();
    }
    @Override
    protected void initUniqueComponents() {
        for (JButton btn : menuButtons) {
            if (btn.getText().equals("Loại sản phẩm")) {
                btn.setBackground(Color.decode("#EF5D7A"));
                btn.setFont(new Font("Arial", Font.BOLD, 14));
            }
        }

        for (JButton btn : menuButtons) {
            if (!btn.getText().equals("Loại sản phẩm")) {
                btn.setBackground(Color.decode("#641A1F"));
                btn.setFont(new Font("Arial", Font.BOLD, 12));
            }
        }
        JLabel lblLoaiSanPhamLink = new JLabel("<html><u>Loại sản phẩm</u></html>");
        lblLoaiSanPhamLink.setFont(new Font("Arial", Font.BOLD, 20));
        lblLoaiSanPhamLink.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        lblLoaiSanPhamLink.setBounds(270, 70, 160, 30);
        add(lblLoaiSanPhamLink);

        JLabel lblTTLSPLink = new JLabel("<html>>> <u>Thông tin loại sản phẩm</u></html>");
        lblTTLSPLink.setFont(new Font("Arial", Font.BOLD, 20));
        lblTTLSPLink.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        lblTTLSPLink.setBounds(420, 70, 280, 30);
        add(lblTTLSPLink);

        JLabel lblArrow = new JLabel(" >> Cập nhật thông tin loại sản phẩm");
        lblArrow.setFont(new Font("Arial", Font.BOLD, 20));
        lblArrow.setBounds(680, 70, 900, 30);
        add(lblArrow);

        lblLoaiSanPhamLink.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                dispose();
                new LoaiSP();
            }
        });

        lblTTLSPLink.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                String maLSP =  txtMaLSP.getText().trim();
                LoaiSPDTO lsp = blllsp.getLSPById(maLSP);
                if (lsp != null) {
                    dispose();
                    TTCTloaisp  ctlsp = new TTCTloaisp();
                    ctlsp.setThongTin(lsp.getMaLSP(),lsp.getTenLSP(), lsp.getTrangThai());
                    ctlsp.setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(null, "Không tìm thấy loại sản phẩm!");
                }
            }
        });


        JLabel lblMaSP = new JLabel("Mã loại sản phẩm:");
        lblMaSP.setBounds(270, 120, 150, 25);
        add(lblMaSP);
        txtMaLSP = new JTextField();
        txtMaLSP.setBounds(270, 150, 300, 30);
        txtMaLSP.setBackground(new Color(230, 230, 230));
        txtMaLSP.setEditable(false);
        txtMaLSP.setFocusable(false);
        add(txtMaLSP);

        JLabel lblLoaiSP = new JLabel("Tên loại sản phẩm:");
        lblLoaiSP.setBounds(700, 120, 150, 25);
        add(lblLoaiSP);
        txtTenLSP = new JTextField();
        txtTenLSP.setBounds(700, 150, 300, 30);
        add(txtTenLSP);

        JLabel lblTrangThai = new JLabel("Trạng thái:");
        lblTrangThai.setBounds(270, 200, 150, 25);
        add(lblTrangThai);

        rbSD = new JRadioButton("Còn sử dụng");
        rbSD.setBounds(270, 230, 230, 25);
        rbSD.setSelected(true);
        add(rbSD);

        rbNSD = new JRadioButton("Ngưng sử dụng");
        rbNSD.setBounds(270, 260, 150, 25);
        add(rbNSD);

        groupTrangThai = new ButtonGroup();
        groupTrangThai.add(rbSD);
        groupTrangThai.add(rbNSD);

        // Nút Lưu
        JButton btnLuusua = new JButton("Lưu");
        btnLuusua.setBounds(700, 200, 100, 40);
        btnLuusua.setBackground(Color.decode("#F0483E"));
        btnLuusua.setForeground(Color.WHITE);
        add(btnLuusua);

        btnLuusua.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!currentuser.coQuyen("Sửa loại sản phẩm")) {

                    JOptionPane.showMessageDialog(null, "Bạn không có quyền cập nhật!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                // Lấy dữ liệu từ các trường nhập
                String maLSP = txtMaLSP.getText().trim();
                String tenLSP = txtTenLSP.getText().trim();
                if (maLSP.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Vui lòng nhập mã loại sản phẩm!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if (tenLSP.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Vui lòng nhập tên loại sản phẩm!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                int trangthai;
                if (rbSD.isSelected()) {
                    trangthai = 1;
                } else if (rbNSD.isSelected()) {
                    trangthai = 0;
                } else {
                    JOptionPane.showMessageDialog(null, "Vui lòng chọn trạng thái!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                try {
                    // Tạo đối tượng loại sản phẩm cập nhật
                    LoaiSPDTO lsp = new LoaiSPDTO();
                    lsp.setMaLSP(maLSP);
                    lsp.setTenLSP(tenLSP);
                    lsp.setTrangThai(trangthai);
                    System.out.println("MALSP cần cập nhật: " + maLSP);

                    boolean success = blllsp.updateLSP(lsp);

                    if (success) {
                        JOptionPane.showMessageDialog(null, "Cập nhật loại sản phẩm thành công!");
                        dispose();
                        TTCTloaisp ctlsp = new TTCTloaisp();
                        ctlsp.setThongTin(lsp.getMaLSP(),lsp.getTenLSP(),lsp.getTrangThai());
                    } else {
                        JOptionPane.showMessageDialog(null, "Cập nhật thất bại!");
                    }

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Lỗi dữ liệu: " + ex.getMessage());
                }
            }
        });
        getRootPane().setDefaultButton(btnLuusua);
        setVisible(true);
    }
    public void loadlspInfoForUpdate(String maLSP) {
        LoaiSPDTO lsp = blllsp.getLSPById(maLSP);

        if (lsp != null) {
            txtTenLSP.setText(lsp.getTenLSP());
            txtMaLSP.setText(lsp.getMaLSP());
            if (lsp.getTrangThai() == 1) {
                rbSD.setSelected(true);
            } else if (lsp.getTrangThai() == 0) {
                rbNSD.setSelected(true);
            } else {
                groupTrangThai.clearSelection();
            }
        } else {
            JOptionPane.showMessageDialog(this, "Không tìm thấy loại sản phẩm!");
        }
    }


    public static void main(String[] args) {
        new Capnhatttloaisp();
    }
}


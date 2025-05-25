package GUI;

import javax.swing.*;

import bll.NhanVienBLL;
import dal.NhanViendal;
import dto.NhanVienDTO;
import dto.currentuser;


import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Capnhatttnv extends BaseFrame {
    private NhanVienBLL bllnv = new NhanVienBLL();
    private JTextField   txtTenNV, txtMaNV, txtCCCD, txtSDT,  txtMST;
    private JRadioButton rbBanHang, rbQuanLy,rbDangLam, rbĐanghi;
    private ButtonGroup groupVTCV,groupTrangThai;

    public Capnhatttnv() {
        super("Cập nhật thông tin nhân viên");
        initialize();
    }
    @Override
    protected void initUniqueComponents() {
        for (JButton btn : menuButtons) {
            if (btn.getText().equals("Nhân viên")) {
                btn.setBackground(Color.decode("#EF5D7A"));
                btn.setFont(new Font("Arial", Font.BOLD, 14));
            }
        }

        for (JButton btn : menuButtons) {
            if (!btn.getText().equals("Nhân viên")) {
                btn.setBackground(Color.decode("#641A1F"));
                btn.setFont(new Font("Arial", Font.BOLD, 12));
            }
        }
        JLabel lblnvLink = new JLabel("<html><u>Nhân viên</u></html>");
        lblnvLink.setFont(new Font("Arial", Font.BOLD, 20));
        lblnvLink.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        lblnvLink.setBounds(270, 70, 100, 30);
        add(lblnvLink);

        JLabel lblTTnvLink = new JLabel("<html>>> <u>Thông tin nhân viên</u></html>");
        ;
        lblTTnvLink.setFont(new Font("Arial", Font.BOLD, 20));
        lblTTnvLink.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        lblTTnvLink.setBounds(370, 70, 240, 30);
        add(lblTTnvLink);

        JLabel lblArrow = new JLabel(" >> Cập nhật thông tin nhân viên");
        lblArrow.setFont(new Font("Arial", Font.BOLD, 20));
        lblArrow.setBounds(590, 70, 900, 30);
        add(lblArrow);

        lblnvLink.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                dispose();
                new NhanVien();
            }
        });


        lblTTnvLink.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                String maNV =  txtMaNV.getText().trim();
                NhanVienDTO nv = bllnv.getNhanVienByID(maNV);
                if (nv != null) {
                    dispose();
                    TTCTnv  ctnv = new TTCTnv();
                    ctnv.setThongTin(nv.getMaNhanVien(), nv.getHoTen(), nv.getCccd(), nv.getSdt(), nv.getViTriCongViec(), nv.getMaSoThue(), nv.getTrangThai());
                    ctnv.setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(null, "Không tìm thấy nhân viên!");
                }
            }
        });



        JLabel lblMaNV = new JLabel("Mã nhân viên:");
        lblMaNV.setBounds(270, 120, 150, 30);
        add(lblMaNV);
        txtMaNV = new JTextField();
        txtMaNV.setBounds(270, 150, 300, 30);
        txtMaNV.setBackground(new Color(230, 230, 230));
        txtMaNV.setEditable(false);
        txtMaNV.setFocusable(false);
        add(txtMaNV);

        JLabel lblHoTen = new JLabel("Họ tên:");
        lblHoTen.setBounds(270, 200, 150, 30);
        add(lblHoTen);
        txtTenNV = new JTextField();
        txtTenNV.setBounds(270, 230, 300, 30);
        add(txtTenNV);

        JLabel lblSDT = new JLabel("Số điện thoại:");
        lblSDT.setBounds(270, 280, 150, 30);
        add(lblSDT);
        txtSDT = new JTextField();
        txtSDT.setBounds(270, 310, 300, 30);
        add(txtSDT);

        JLabel lblCCCD = new JLabel("CCCD:");
        lblCCCD.setBounds(270, 360, 150, 30);
        add(lblCCCD);
        txtCCCD = new JTextField();
        txtCCCD.setBounds(270, 390, 300, 30);
        add(txtCCCD);


        JLabel lblVTCV = new JLabel("Vị trí công việc:");
        lblVTCV.setBounds(700, 120, 150, 30);
        add(lblVTCV);

        rbBanHang = new JRadioButton("Nhân viên bán hàng");
        rbBanHang.setBounds(700, 150, 200, 25);
        add(rbBanHang);

        rbQuanLy = new JRadioButton("Nhân viên quản lý");
        rbQuanLy.setBounds(700, 180, 200, 25);
        add(rbQuanLy);


        groupVTCV = new ButtonGroup();
        groupVTCV.add(rbBanHang);
        groupVTCV.add(rbQuanLy);

        JLabel lblMST = new JLabel("Mã số thuế:");
        lblMST.setBounds(700, 200, 150, 30);
        add(lblMST);
        txtMST = new JTextField();
        txtMST.setBounds(700, 230, 300, 30);
        add(txtMST);


        JLabel lblTrangThai = new JLabel("Trạng thái:");
        lblTrangThai.setBounds(700, 280, 150, 30);
        add(lblTrangThai);

        rbDangLam = new JRadioButton("Đang làm");
        rbDangLam.setBounds(700, 310, 150, 25);
        rbDangLam.setSelected(true);
        add(rbDangLam);

        rbĐanghi = new JRadioButton("Đã nghỉ");
        rbĐanghi.setBounds(700, 340, 150, 25);
        add(rbĐanghi);

        groupTrangThai = new ButtonGroup();
        groupTrangThai.add(rbDangLam);
        groupTrangThai.add(rbĐanghi);

        // Nút Lưu
        JButton btnLuusua = new JButton("Lưu");
        btnLuusua.setBounds(700, 520, 100, 40);
        btnLuusua.setBackground(Color.decode("#F0483E"));
        btnLuusua.setForeground(Color.WHITE);
        add(btnLuusua);

        btnLuusua.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!currentuser.coQuyen("Sửa nhân viên")) {

                    JOptionPane.showMessageDialog(null, "Bạn không có quyền cập nhật!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                String maNV = txtMaNV.getText().trim();
                String tenNV = txtTenNV.getText().trim();
                String CCCD = txtCCCD.getText().trim();
                String SDT = txtSDT.getText().trim();
                String MST = txtMST.getText().trim();
                String maVT = null;

                System.out.println("MaVT cần cập nhật: " + maVT);


                if (maNV.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Vui lòng nhập mã nhân viên!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if (tenNV.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Vui lòng nhập tên nhân viên!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if (CCCD.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Vui lòng nhập căn cước công dân!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if (SDT.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Vui lòng nhập số điện thoại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if (MST.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Vui lòng nhập mã số thuế!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if (rbBanHang.isSelected()) {
                    maVT = "VT003";
                } else if (rbQuanLy.isSelected()) {
                    maVT = "VT002";
                } else {
                    JOptionPane.showMessageDialog(null, "Vui lòng chọn vị trí công việc!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                int trangthai;
                if (rbDangLam.isSelected()) {
                    trangthai = 1;
                } else if (rbĐanghi.isSelected()) {
                    trangthai = 0;
                } else {
                    JOptionPane.showMessageDialog(null, "Vui lòng chọn trạng thái!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                try {

                    NhanVienDTO nv = new NhanVienDTO();
                    nv.setMaNhanVien(maNV);
                    nv.setHoTen(tenNV);
                    nv.setCccd(CCCD);
                    nv.setSdt(SDT);
                    nv.setViTriCongViec(maVT);
                    nv.setMaSoThue(MST);
                    nv.setTrangThai(trangthai);

                    boolean success = bllnv.updateNhanVien(nv);

                    if (success) {
                        JOptionPane.showMessageDialog(null, "Cập nhật nhân viên thành công!");
                        dispose();
                        TTCTnv ctnv = new TTCTnv();
                        ctnv.setThongTin(nv.getMaNhanVien(), nv.getHoTen(), nv.getCccd(), nv.getSdt(), nv.getViTriCongViec(), nv.getMaSoThue(), nv.getTrangThai());
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
    public void loadnvInfoForUpdate(String maNV) {
        NhanVienDTO nv = bllnv.getNhanVienByID(maNV);

        if (nv != null) {
            txtMaNV.setText(nv.getMaNhanVien());
            txtTenNV.setText(nv.getHoTen());
            txtCCCD.setText(nv.getCccd());
            txtSDT.setText(nv.getSdt());
            txtMST.setText(nv.getMaSoThue());
            if ("VT003".equals(nv.getViTriCongViec())) {
                rbBanHang.setSelected(true);
            } else if ("VT002".equals(nv.getViTriCongViec())) {
                rbQuanLy.setSelected(true);
            } else {
                groupVTCV.clearSelection();
            }

            if (nv.getTrangThai() == 1) {
                rbDangLam.setSelected(true);
            } else if (nv.getTrangThai() == 0) {
                rbĐanghi.setSelected(true);
            } else {
                groupTrangThai.clearSelection();
            }

        } else {
            JOptionPane.showMessageDialog(this, "Không tìm thấy nhân viên!");
        }
    }

    public static void main(String[] args) {
        new Capnhatttnv();
    }

}

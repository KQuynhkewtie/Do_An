package GUI;

import javax.swing.*;

import bll.HangSanXuatBLL;
import dal.hangsanxuatdal;
import dto.HangSanXuatDTO;
import dto.currentuser;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class CapnhatttHSX extends BaseFrame {
    private HangSanXuatBLL bllhsx = new HangSanXuatBLL();
    private JTextField  txtTenHSX, txtMaHSX, txtMST, txtSDT, txtDC;
    private JRadioButton rbSD, rbNSD;
    private ButtonGroup groupTrangThai;
    public CapnhatttHSX() {
        super("Cập nhật thông tin hãng sản xuất");
        initialize();
    }
    @Override
    protected void initUniqueComponents() {
        for (JButton btn : menuButtons) {
            if (btn.getText().equals("Hãng sản xuất")) {
                btn.setBackground(Color.decode("#EF5D7A"));
                btn.setFont(new Font("Arial", Font.BOLD, 14));
            }
        }


        for (JButton btn : menuButtons) {
            if (!btn.getText().equals("Hãng sản xuất")) {
                btn.setBackground(Color.decode("#641A1F"));
                btn.setFont(new Font("Arial", Font.BOLD, 12));
            }
        }

        JLabel lblHSXLink = new JLabel("<html><u>Hãng sản xuất</u></html>");
        lblHSXLink.setFont(new Font("Arial", Font.BOLD, 20));
        lblHSXLink.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        lblHSXLink.setBounds(270, 70, 160, 30);
        add(lblHSXLink);

        JLabel lblTTHSXLink = new JLabel("<html>>> <u>Thông tin hãng sản xuất</u></html>");
        lblTTHSXLink.setFont(new Font("Arial", Font.BOLD, 20));
        lblTTHSXLink.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        lblTTHSXLink.setBounds(420, 70, 280, 30);
        add(lblTTHSXLink);

        JLabel lblArrow = new JLabel(" >> Cập nhật thông tin hãng sản xuất");
        lblArrow.setFont(new Font("Arial", Font.BOLD, 20));
        lblArrow.setBounds(680, 70, 900, 30);
        add(lblArrow);

        lblHSXLink.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                dispose();
                new HangSX();
            }
        });



        lblTTHSXLink.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                String maHSX =  txtMaHSX.getText().trim();
                HangSanXuatDTO hsx = bllhsx.getHSXbyID(maHSX);
                if (hsx != null) {
                    dispose();
                    TTCTHSX cthsx = new TTCTHSX();
                    cthsx.setThongTin(hsx.getMaHSX(), hsx.getTenHSX(), hsx.getMaSoThue(), hsx.getSdt(), hsx.getDiaChi(), hsx.getTrangThai());
                    cthsx.setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(null, "Không tìm thấy hãng sản xuất!");
                }
            }
        });

        JLabel lblMaHSX = new JLabel("Mã hãng sản xuất:");
        lblMaHSX.setBounds(270, 120, 150, 25);
        add(lblMaHSX);
        txtMaHSX = new JTextField();
        txtMaHSX.setBounds(270, 150, 300, 30);
        txtMaHSX.setBackground(new Color(230, 230, 230));
        txtMaHSX.setEditable(false);
        txtMaHSX.setFocusable(false);
        add(txtMaHSX);

        JLabel lbltenHSX = new JLabel("Tên hãng sản xuất:");
        lbltenHSX.setBounds(700, 120, 150, 25);
        add(lbltenHSX);
        txtTenHSX = new JTextField();
        txtTenHSX.setBounds(700, 150, 300, 30);
        add(txtTenHSX);

        JLabel lblMaSoThue = new JLabel("Mã số thuế:");
        lblMaSoThue.setBounds(700, 200, 150, 25);
        add(lblMaSoThue);
        txtMST = new JTextField();
        txtMST.setBounds(700, 230, 300, 30);
        add(txtMST);

        JLabel lbldiachi = new JLabel("Địa chỉ:");
        lbldiachi.setBounds(270, 200, 150, 25);
        add(lbldiachi);
        txtDC = new JTextField();
        txtDC.setBounds(270, 230, 300, 30);
        add(txtDC);

        JLabel lblsdt = new JLabel("Số điện thoại:");
        lblsdt.setBounds(700, 280, 150, 25);
        add(lblsdt);
        txtSDT = new JTextField();
        txtSDT.setBounds(700, 310, 300, 30);
        add(txtSDT);

        JLabel lblTrangThai = new JLabel("Trạng thái:");
        lblTrangThai.setBounds(270, 280, 150, 25);
        add(lblTrangThai);

        rbSD = new JRadioButton("Còn sử dụng");
        rbSD.setBounds(270, 310, 150, 25);
        rbSD.setSelected(true);
        add(rbSD);

        rbNSD = new JRadioButton("Ngưng sử dụng");
        rbNSD.setBounds(270, 340, 150, 25);
        add(rbNSD);

        groupTrangThai = new ButtonGroup();
        groupTrangThai.add(rbSD);
        groupTrangThai.add(rbNSD);

        // Nút Lưu
        JButton btnLuusua = new JButton("Lưu");
        btnLuusua.setBounds(700, 380, 100, 40);
        btnLuusua.setBackground(Color.decode("#F0483E"));
        btnLuusua.setForeground(Color.WHITE);
        add(btnLuusua);

        btnLuusua.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!currentuser.coQuyen("Sửa hãng sản xuất")) {

                    JOptionPane.showMessageDialog(null, "Bạn không có quyền cập nhật!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                // Lấy dữ liệu từ các trường nhập
                String maHSX = txtMaHSX.getText().trim();
                String tenHSX = txtTenHSX.getText().trim();
                String MST = txtMST.getText().trim();
                String SDT = txtSDT.getText().trim();
                String DC = txtDC.getText().trim();
                if (maHSX.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Vui lòng nhập mã hãng sản xuất!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if (tenHSX.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Vui lòng nhập tên hãng sản xuất!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if (MST.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Vui lòng nhập mã số thuế!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if (DC.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Vui lòng nhập địa chỉ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if (SDT.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Vui lòng nhập số điện thoại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
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

                    HangSanXuatDTO hsx = new HangSanXuatDTO();
                    hsx.setMaHSX(maHSX);
                    hsx.setTenHSX(tenHSX);
                    hsx.setMaSoThue(MST);
                    hsx.setSdt(SDT);
                    hsx.setDiaChi(DC);
                    hsx.setTrangThai(trangthai);

                    System.out.println("MAHSX cần cập nhật: " + maHSX);
                    boolean success = bllhsx.updateHangSanXuat(hsx);

                    if (success) {
                        JOptionPane.showMessageDialog(null, "Cập nhật hãng sản xuất thành công!");
                        dispose();
                        TTCTHSX cthsx = new TTCTHSX();
                        cthsx.setThongTin(hsx.getMaHSX(), hsx.getTenHSX(), hsx.getMaSoThue(), hsx.getSdt(), hsx.getDiaChi(), hsx.getTrangThai());
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


    public void loadhsxInfoForUpdate(String maHSX) {
        HangSanXuatDTO hsx = bllhsx.getHSXbyID(maHSX);

        if (hsx != null) {

            txtMaHSX.setText(hsx.getMaHSX());
            txtTenHSX.setText(hsx.getTenHSX());
            txtMST.setText(hsx.getMaSoThue());
            txtSDT.setText(hsx.getSdt());
            txtDC.setText(hsx.getDiaChi());
            if (hsx.getTrangThai() == 1) {
                rbSD.setSelected(true);
            } else if (hsx.getTrangThai() == 0) {
                rbNSD.setSelected(true);
            } else {
                groupTrangThai.clearSelection();
            }

        } else {
            JOptionPane.showMessageDialog(this, "Không tìm thấy hãng sản xuất!");
        }
    }

    public static void main(String[] args) {
        new CapnhatttHSX();
    }
}


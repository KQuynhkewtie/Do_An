package GUI;

import javax.swing.*;

import bll.NhaCungUngBLL;
import dal.ncudal;
import dto.NhaCungUngDTO;
import dto.currentuser;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Capnhatttncu extends BaseFrame {
    private NhaCungUngBLL bllncu = new NhaCungUngBLL();
    private JTextField txtTenNCU, txtMaNCU, txtMSTHUE, txtDC, txtSDT, txtEMAIL;
    private JRadioButton rbHT, rbNHT;
    private ButtonGroup groupTrangThai;
    public Capnhatttncu() {
        super("Cập nhật thông tin nhà cung ứng");
        initialize();
    }
    @Override
    protected void initUniqueComponents() {
        for (JButton btn : menuButtons) {
            if (btn.getText().equals("Nhà cung ứng")) {
                btn.setBackground(Color.decode("#EF5D7A"));
                btn.setFont(new Font("Arial", Font.BOLD, 14));
            }
        }


        for (JButton btn : menuButtons) {
            if (!btn.getText().equals("Nhà cung ứng")) {
                btn.setBackground(Color.decode("#641A1F"));
                btn.setFont(new Font("Arial", Font.BOLD, 12));
            }
        }
        JLabel lblncuLink = new JLabel("<html><u>Nhà cung ứng</u></html>");
        lblncuLink.setFont(new Font("Arial", Font.BOLD, 20));
        lblncuLink.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        lblncuLink.setBounds(270, 70, 160, 30);
        add(lblncuLink);

        JLabel lblTTncuLink = new JLabel("<html>>> <u>Thông tin nhà cung ứng</u></html>");
        lblTTncuLink.setFont(new Font("Arial", Font.BOLD, 20));
        lblTTncuLink.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        lblTTncuLink.setBounds(410, 70, 280, 30);
        add(lblTTncuLink);

        JLabel lblArrow = new JLabel(" >> Cập nhật thông tin nhà cung ứng");
        lblArrow.setFont(new Font("Arial", Font.BOLD, 20));
        lblArrow.setBounds(670, 70, 900, 30);
        add(lblArrow);

        lblncuLink.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                dispose();
                new Nhacungung();
            }
        });

        lblTTncuLink.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                String maNCU =  txtMaNCU.getText().trim();
                NhaCungUngDTO ncu = bllncu.getNCUById(maNCU);
                if (ncu != null) {
                    dispose();
                    TTCTncu ctlsp = new TTCTncu();
                    ctlsp.setThongTin(ncu.getTenNCU(), ncu.getMaNCU(), ncu.getMaSoThue(), ncu.getDiaChi(), ncu.getSdt(), ncu.getEmail(), ncu.getTrangThai());
                    ctlsp.setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(null, "Không tìm thấy nhà cung ứng!");
                }
            }
        });


        JLabel lblMaNCU = new JLabel("Mã nhà cung ứng:");
        lblMaNCU.setBounds(270, 120, 150, 30);
        add(lblMaNCU);
        txtMaNCU = new JTextField();
        txtMaNCU.setBounds(270, 150, 300, 30);
        txtMaNCU.setBackground(new Color(230, 230, 230));
        txtMaNCU.setEditable(false);
        txtMaNCU.setFocusable(false);
        add(txtMaNCU);

        JLabel lblTen = new JLabel("Tên nhà cung ứng:");
        lblTen.setBounds(270, 200, 150, 30);
        add(lblTen);
        txtTenNCU = new JTextField();
        txtTenNCU.setBounds(270, 230, 300, 30);
        add(txtTenNCU);

        JLabel lblMST = new JLabel("Mã số thuế:");
        lblMST.setBounds(270, 280, 150, 30);
        add(lblMST);
        txtMSTHUE = new JTextField();
        txtMSTHUE.setBounds(270, 310, 300, 30);
        add(txtMSTHUE);

        JLabel lblDC = new JLabel("Địa chỉ:");
        lblDC.setBounds(270, 360, 150, 30);
        add(lblDC);
        txtDC = new JTextField();
        txtDC.setBounds(270, 390, 300, 30);
        add(txtDC);

        JLabel lblSDT = new JLabel("Số điện thoại:");
        lblSDT.setBounds(700, 120, 150, 30);
        add(lblSDT);
        txtSDT = new JTextField();
        txtSDT.setBounds(700, 150, 300, 30);
        add(txtSDT);

        JLabel lblEmail = new JLabel("Email:");
        lblEmail.setBounds(700, 200, 150, 30);
        add(lblEmail);
        txtEMAIL = new JTextField();
        txtEMAIL.setBounds(700, 230, 300, 30);
        add(txtEMAIL);

        JLabel lblTrangThai = new JLabel("Trạng thái:");
        lblTrangThai.setBounds(700, 280, 150, 25);
        add(lblTrangThai);

        rbHT = new JRadioButton("Đang hợp tác");
        rbHT.setBounds(700, 310, 150, 25);
        rbHT.setSelected(true);
        add(rbHT);

        rbNHT = new JRadioButton("Ngưng hợp tác");
        rbNHT.setBounds(700, 340, 150, 25);
        add(rbNHT);

        groupTrangThai = new ButtonGroup();
        groupTrangThai.add(rbHT);
        groupTrangThai.add(rbNHT);

        // Nút Lưu
        JButton btnLuusua = new JButton("Lưu");
        btnLuusua.setBounds(700, 400, 100, 40);
        btnLuusua.setBackground(Color.decode("#F0483E"));
        btnLuusua.setForeground(Color.WHITE);
        add(btnLuusua);

        btnLuusua.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!currentuser.coQuyen("Sửa nhà cung ứng")) {

                    JOptionPane.showMessageDialog(null, "Bạn không có quyền cập nhật!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                // Lấy dữ liệu từ các trường nhập
                String maNCU = txtMaNCU.getText().trim();
                String tenNCU = txtTenNCU.getText().trim();
                String MST = txtMSTHUE.getText().trim();
                String DC = txtDC.getText().trim();
                String SDT = txtSDT.getText().trim();
                String EMAIL = txtEMAIL.getText().trim();
                if (maNCU.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Vui lòng nhập mã nhà cung ứng!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if (tenNCU.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Vui lòng nhập tên nhà cung ứng!", "Lỗi", JOptionPane.ERROR_MESSAGE);
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
                if (EMAIL.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Vui lòng nhập email!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                int trangthai;
                if (rbHT.isSelected()) {
                    trangthai = 1;
                } else if (rbNHT.isSelected()) {
                    trangthai = 0;
                } else {
                    JOptionPane.showMessageDialog(null, "Vui lòng chọn trạng thái!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                try {

                    NhaCungUngDTO ncu = new NhaCungUngDTO();
                    ncu.setMaNCU(maNCU);
                    ncu.setTenNCU(tenNCU);
                    ncu.setMaSoThue(MST);
                    ncu.setDiaChi(DC);
                    ncu.setSdt(SDT);
                    ncu.setEmail(EMAIL);
                    ncu.setTrangThai(trangthai);
                    System.out.println("MANCU cần cập nhật: " + maNCU);

                    boolean success = bllncu.updateNCU(ncu);

                    if (success) {
                        JOptionPane.showMessageDialog(null, "Cập nhật nhà cung ứng thành công!");
                        dispose();
                        TTCTncu ctncu = new TTCTncu();
                        ctncu.setThongTin(ncu.getTenNCU(), ncu.getMaNCU(), ncu.getMaSoThue(), ncu.getDiaChi(), ncu.getSdt(), ncu.getEmail(), ncu.getTrangThai());
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
    public void loadncuInfoForUpdate(String maNCU) {
        NhaCungUngDTO ncu = bllncu.getNCUById(maNCU);

        if (ncu != null) {
            txtTenNCU.setText(ncu.getTenNCU());
            txtMaNCU.setText(ncu.getMaNCU());
            txtMSTHUE.setText(ncu.getMaSoThue());
            txtDC.setText(ncu.getDiaChi());
            txtSDT.setText(ncu.getSdt());
            txtEMAIL.setText(ncu.getEmail());
            if (ncu.getTrangThai() == 1) {
                rbHT.setSelected(true);
            } else if (ncu.getTrangThai() == 0) {
                rbNHT.setSelected(true);
            } else {
                groupTrangThai.clearSelection();
            }
        } else {
            JOptionPane.showMessageDialog(this, "Không tìm thấy nhà cung ứng!");
        }
    }
    public static void main(String[] args) {
        new Capnhatttncu();
    }

}

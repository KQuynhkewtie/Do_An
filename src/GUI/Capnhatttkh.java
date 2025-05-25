package GUI;

import javax.swing.*;

import bll.KhachHangBLL;
import dal.khachhangdal;
import dto.KhachHangDTO;
import dto.currentuser;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Capnhatttkh extends BaseFrame {
    private KhachHangBLL bllKhachhang = new KhachHangBLL();
    private JTextField  txtHoTen, txtMaKH, txtDiemTL, txtsdt, txtLoaiKH;
    public Capnhatttkh() {
        super("Cập nhật thông tin khách hàng");
        initialize();
    }
    @Override
    protected void initUniqueComponents() {
        for (JButton btn : menuButtons) {
            if (btn.getText().equals("Khách hàng")) {
                btn.setBackground(Color.decode("#EF5D7A"));
                btn.setFont(new Font("Arial", Font.BOLD, 14));
            }
        }


        for (JButton btn : menuButtons) {
            if (!btn.getText().equals("Khách hàng")) {
                btn.setBackground(Color.decode("#641A1F"));
                btn.setFont(new Font("Arial", Font.BOLD, 12));
            }
        }

        JLabel lblKhachhangLink = new JLabel("<html><u>Khách Hàng</u></html>");
        lblKhachhangLink.setFont(new Font("Arial", Font.BOLD, 20));
        lblKhachhangLink.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        lblKhachhangLink.setBounds(270, 70, 120, 30);
        add(lblKhachhangLink);

        JLabel lblTTKhachhangLink = new JLabel("<html>>> <u>Thông tin khách hàng</u></html>");
        lblTTKhachhangLink.setFont(new Font("Arial", Font.BOLD, 20));
        lblTTKhachhangLink.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        lblTTKhachhangLink.setBounds(390, 70, 240, 30);
        add(lblTTKhachhangLink);

        JLabel lblArrow = new JLabel(" >> Cập nhật thông tin khách hàng");
        lblArrow.setFont(new Font("Arial", Font.BOLD, 20));
        lblArrow.setBounds(630, 70, 900, 30);
        add(lblArrow);

        lblKhachhangLink.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                dispose();
                new KhachHang();
            }
        });



        lblTTKhachhangLink.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                String maKH =  txtMaKH.getText().trim();
                System.out.println(maKH);
                KhachHangDTO kh = bllKhachhang.getKhachHangById(maKH);
                if (kh != null) {
                    dispose();
                    TTCTkh ctlsp = new TTCTkh();
                    ctlsp.setThongTin(kh.getHoTen(), kh.getMaKH(),  kh.getDiemTichLuy(), kh.getLoaiKhach(), kh.getSdt());
                    ctlsp.setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(null, "Không tìm thấy khách hàng!");
                }
            }
        });

        JLabel lblMaKH = new JLabel("Mã khách hàng:");
        lblMaKH.setBounds(270, 120, 150, 30);
        add(lblMaKH);
        txtMaKH = new JTextField();
        txtMaKH.setBounds(450, 120, 200, 30);
        txtMaKH.setBackground(new Color(230, 230, 230));
        txtMaKH.setEditable(false);
        txtMaKH.setFocusable(false);
        add(txtMaKH);

        JLabel lblHoTen = new JLabel("Họ tên:");
        lblHoTen.setBounds(270, 170, 150, 30);
        add(lblHoTen);
        txtHoTen = new JTextField();
        txtHoTen.setBounds(450, 170, 200, 30);
        add(txtHoTen);

        JLabel lblsdt = new JLabel("Số điện thoại:");
        lblsdt.setBounds(270, 220, 150, 30);
        add(lblsdt);
        txtsdt = new JTextField();
        txtsdt.setBounds(450, 220, 200, 30);
        add(txtsdt);

        JLabel lblDiemTL = new JLabel("Điểm tích lũy:");
        lblDiemTL.setBounds(270, 270, 150, 30);
        add(lblDiemTL);
        txtDiemTL = new JTextField();
        txtDiemTL.setBounds(450, 270, 200, 30);
        add(txtDiemTL);
        txtDiemTL.setEditable(false);
        txtDiemTL.setFocusable(false);
        txtDiemTL.setBackground(new Color(230, 230, 230));

        JLabel lblLoaiKH = new JLabel("Mã loại khách hàng:");
        lblLoaiKH.setBounds(270, 320, 150, 30);
        add(lblLoaiKH);
        txtLoaiKH = new JTextField();
        txtLoaiKH.setBounds(450, 320, 200, 30);
        add(txtLoaiKH);
        txtLoaiKH.setEditable(false);
        txtLoaiKH.setFocusable(false);
        txtLoaiKH.setBackground(new Color(230, 230, 230));

        // Nút Lưu
        JButton btnLuusua = new JButton("Lưu");
        btnLuusua.setBounds(700, 520, 100, 40);
        btnLuusua.setBackground(Color.decode("#F0483E"));
        btnLuusua.setForeground(Color.WHITE);
        add(btnLuusua);
        btnLuusua.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!currentuser.coQuyen("Sửa khách hàng")) {

                    JOptionPane.showMessageDialog(null, "Bạn không có quyền cập nhật!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                // Lấy dữ liệu từ các trường nhập
                String maKH = txtMaKH.getText().trim();
                String tenKH = txtHoTen.getText().trim();
                String DTLstr = txtDiemTL.getText().trim();
                String maloaikh = txtLoaiKH.getText().trim();
                String sdt = txtsdt.getText().trim();

                if (maKH.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Vui lòng nhập mã khách hàng!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if (tenKH.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Vui lòng nhập tên khách hàng!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (sdt.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Vui lòng nhập số điện thoại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                try {
                    double DTL = Double.parseDouble(DTLstr);

                    KhachHangDTO kh = new KhachHangDTO();
                    kh.setMaKH(maKH);
                    kh.setHoTen(tenKH);
                    kh.setDiemTichLuy(DTL);
                    kh.setLoaiKhach(maloaikh);
                    kh.setSdt(sdt);

                    System.out.println("MAKH cần cập nhật: " + maKH);

                    boolean success = bllKhachhang.updateKhachHang(kh);

                    if (success) {
                        JOptionPane.showMessageDialog(null, "Cập nhật khách hàng thành công!");
                        dispose();
                        TTCTkh ctkh = new TTCTkh();
                        ctkh.setThongTin( kh.getMaKH(),kh.getHoTen(), kh.getDiemTichLuy(), kh.getLoaiKhach(), kh.getSdt());
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
    public void loadCustomerInfoForUpdate(String maKH) {
        KhachHangDTO kh = bllKhachhang.getKhachHangById(maKH);

        if (kh != null) {
            txtHoTen.setText(kh.getHoTen());
            txtMaKH.setText(kh.getMaKH());
            txtDiemTL.setText(String.valueOf(kh.getDiemTichLuy()));
            txtLoaiKH.setText(String.valueOf(kh.getLoaiKhach()));
            txtsdt.setText(kh.getSdt());

        } else {
            JOptionPane.showMessageDialog(this, "Không tìm thấy khách hàng!");
        }
    }

    public static void main(String[] args) {
        new Capnhatttkh();
    }
}

package GUI;


import javax.swing.*;

import dal.khachhangdal;
import dto.KhachHangDTO;
import bll.KhachHangBLL;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Themkh extends BaseFrame {
    private KhachHangBLL khBLL= new KhachHangBLL();
    public Themkh() {super("Cập nhật thông tin khách hàng");
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
        JLabel lblSanPhamLink = new JLabel("<html><u>Khách Hàng</u></html>");
        lblSanPhamLink.setFont(new Font("Arial", Font.BOLD, 20));
        lblSanPhamLink.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        lblSanPhamLink.setBounds(270, 70, 400, 30);
        add(lblSanPhamLink);

        JLabel lblArrow = new JLabel(" >> Thêm khách hàng");
        lblArrow.setFont(new Font("Arial", Font.BOLD, 20));
        lblArrow.setBounds(390, 70, 400, 30);
        add(lblArrow);

        lblSanPhamLink.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                dispose();
                new KhachHang();
            }
        });

        //form thêm khách hàng
        JLabel lblMaKH = new JLabel("Mã khách hàng:");
        lblMaKH.setBounds(270, 120, 150, 30);
        add(lblMaKH);
        JTextField txtMaKH = new JTextField();
        txtMaKH.setBounds(450, 120, 200, 30);
        add(txtMaKH);
        //họ tên
        JLabel lblHoTen = new JLabel("Họ tên:");
        lblHoTen.setBounds(270, 170, 150, 30);
        add(lblHoTen);
        JTextField txtHoTen = new JTextField();
        txtHoTen.setBounds(450, 170, 200, 30);
        add(txtHoTen);
        //số lần mua
        JLabel lblsdt = new JLabel("Số diện thoại:");
        lblsdt.setBounds(270, 220, 150, 30);
        add(lblsdt);
        JTextField txtsdt = new JTextField();
        txtsdt.setBounds(450, 220, 200, 30);
        add(txtsdt);
        //điểm tích lũy
        JLabel lblDiemTL = new JLabel("Điểm tích lũy:");
        lblDiemTL.setBounds(270, 270, 150, 30);
        add(lblDiemTL);
        JTextField txtDiemTL = new JTextField("0.0");
        txtDiemTL.setBounds(450, 270, 200, 30);
        txtDiemTL.setEditable(false);
        txtDiemTL.setFocusable(false);
        txtDiemTL.setBackground(new Color(230, 230, 230));
        add(txtDiemTL);

        JLabel lblLoaiKH = new JLabel("Loại khách hàng:");
        lblLoaiKH.setBounds(270, 320, 150, 30);
        add(lblLoaiKH);
        JTextField txtLoaiKH = new JTextField("Bình thường");
        txtLoaiKH.setBounds(450, 320, 200, 30);
        txtLoaiKH.setEditable(false);
        txtLoaiKH.setFocusable(false);
        txtLoaiKH.setBackground(new Color(230, 230, 230));
        add(txtLoaiKH);


        //nút lưu
        JButton btnLuu = new JButton("Lưu");
        btnLuu.setBounds(450, 370, 100, 30);
        btnLuu.setBackground(Color.decode("#F0483E"));
        btnLuu.setForeground(Color.WHITE);
        add(btnLuu);

        btnLuu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String maKH = txtMaKH.getText().trim();
                    String tenKH = txtHoTen.getText().trim();
                    String SDT = txtsdt.getText().trim();

                    if (maKH.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "Vui lòng nhập mã khách hàng!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    if (tenKH.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "Vui lòng nhập tên khách hàng!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    if (SDT.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "Vui lòng nhập số điện thoại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    KhachHangDTO kh = new KhachHangDTO(maKH, tenKH, SDT);

                    boolean result = khBLL.insertKhachHang(kh);
                    if (result) {
                        JOptionPane.showMessageDialog(null, "Thêm khách hàng thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                        dispose();
                        new KhachHang();
                    } else {
                        JOptionPane.showMessageDialog(null, "Thêm khách hàng thất bại! Kiểm tra dữ liệu." , "Lỗi", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Vui lòng nhập đúng định dạng!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        getRootPane().setDefaultButton(btnLuu);
        setVisible(true);

        JTextField[] textFields = { txtMaKH,txtHoTen, txtsdt};

        for (int i = 0; i < textFields.length; i++) {
            final int currentIndex = i;
            textFields[i].addKeyListener(new KeyAdapter() {
                @Override
                public void keyPressed(KeyEvent e) {
                    int key = e.getKeyCode();
                    if (key == KeyEvent.VK_DOWN) {

                        if (currentIndex < textFields.length - 1) {
                            textFields[currentIndex + 1].requestFocus();
                        }
                    } else if (key == KeyEvent.VK_UP) {

                        if (currentIndex > 0) {
                            textFields[currentIndex - 1].requestFocus();
                        }
                    }
                }
            });
        }
    }
    public static void main(String[] args) {
        new Themkh();
    }
}

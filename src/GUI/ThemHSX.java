package GUI;

import javax.swing.*;


import dal.hangsanxuatdal;
import dto.HangSanXuatDTO;
import dto.currentuser;
import bll.HangSanXuatBLL;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ThemHSX extends BaseFrame {
    private HangSanXuatBLL hsxBLL = new HangSanXuatBLL();
    private JRadioButton rbSD, rbNSD;
    private ButtonGroup groupTrangThai;
    public ThemHSX() {
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
        JLabel lblLoaiSanPhamLink = new JLabel("<html><u>Hãng sản xuất</u></html>");
        lblLoaiSanPhamLink.setFont(new Font("Arial", Font.BOLD, 20));
        lblLoaiSanPhamLink.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        lblLoaiSanPhamLink.setBounds(270, 70, 160, 30);
        add(lblLoaiSanPhamLink);

        JLabel lblArrow = new JLabel(" >> Thêm hãng sản xuất");
        lblArrow.setFont(new Font("Arial", Font.BOLD, 20));
        lblArrow.setBounds(410, 70, 300, 30);
        add(lblArrow);

        lblLoaiSanPhamLink.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                dispose();
                new HangSX();
            }
        });

        JLabel lblMaSoThue = new JLabel("Mã số thuế:");
        lblMaSoThue.setBounds(700, 120, 150, 25);
        add(lblMaSoThue);
        JTextField txtMaSoThue = new JTextField();
        txtMaSoThue.setBounds(700, 150, 300, 30);
        add(txtMaSoThue);

        JLabel lblMaHSX = new JLabel("Mã hãng sản xuất:");
        lblMaHSX.setBounds(270, 120, 150, 25);
        add(lblMaHSX);
        JTextField txtMaHSX = new JTextField();
        txtMaHSX.setBounds(270, 150, 300, 30);
        add(txtMaHSX);

        JLabel lbldiachi = new JLabel("Địa chỉ:");
        lbldiachi.setBounds(700, 200, 150, 25);
        add(lbldiachi);
        JTextField txtdiachi = new JTextField();
        txtdiachi.setBounds(700, 230, 300, 30);
        add(txtdiachi);

        JLabel lbltenHSX = new JLabel("Tên hãng sản xuất:");
        lbltenHSX.setBounds(270, 200, 150, 25);
        add(lbltenHSX);
        JTextField txttenHSX = new JTextField();
        txttenHSX.setBounds(270, 230, 300, 30);
        add(txttenHSX);

        JLabel lblsdt = new JLabel("Số điện thoại:");
        lblsdt.setBounds(700, 280, 150, 25);
        add(lblsdt);
        JTextField txtsdt = new JTextField();
        txtsdt.setBounds(700, 310, 300, 30);
        add(txtsdt);


        JLabel lblTrangThai = new JLabel("Trạng thái:");
        lblTrangThai.setBounds(270, 280, 150, 25);
        add(lblTrangThai);

        rbSD = new JRadioButton("Còn sử dụng");
        rbSD.setBounds(270, 310, 150, 25);
        add(rbSD);

        rbNSD = new JRadioButton("Ngưng sử dụng");
        rbNSD.setBounds(270, 340, 150, 25);
        add(rbNSD);

        groupTrangThai = new ButtonGroup();
        groupTrangThai.add(rbSD);
        groupTrangThai.add(rbNSD);

        // Nút Lưu
        JButton btnLuu = new JButton("Lưu");
        btnLuu.setBounds(700, 380, 100, 40);
        btnLuu.setBackground(Color.decode("#F0483E"));
        btnLuu.setForeground(Color.WHITE);
        add(btnLuu);

        btnLuu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!currentuser.coQuyen("Thêm hãng sản xuất")) {

                    JOptionPane.showMessageDialog(null, "Bạn không có quyền thêm!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                try {
                    String maHSX = txtMaHSX.getText().trim();
                    String tenHSX = txttenHSX.getText().trim();
                    String MST = txtMaSoThue.getText().trim();
                    String DC = txtdiachi.getText().trim();
                    String SDT = txtsdt.getText().trim();
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
                    HangSanXuatDTO hsx = new HangSanXuatDTO(maHSX, tenHSX, MST, DC, SDT, trangthai);

                    boolean result = hsxBLL.insertHangSanXuat(hsx);
                    if (result) {
                        JOptionPane.showMessageDialog(null, "Thêm hãng sản xuất thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                        dispose();
                        new HangSX();
                    } else {
                        JOptionPane.showMessageDialog(null, "Thêm hãng sản xuất thất bại! Kiểm tra dữ liệu." , "Lỗi", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Vui lòng nhập đúng định dạng!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        getRootPane().setDefaultButton(btnLuu);
        setVisible(true);
        JTextField[] textFields = {txtMaHSX, txttenHSX, txtMaSoThue, txtdiachi, txtsdt};

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
        new ThemHSX();
    }
}

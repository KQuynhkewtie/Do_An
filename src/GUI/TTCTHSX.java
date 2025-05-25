package GUI;


import javax.swing.*;

import bll.HangSanXuatBLL;
import dal.dbconnection;
import dto.currentuser;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class TTCTHSX extends BaseFrame {
    private JTextArea txtTenHSX, txtMaHSX, txtMST, txtSDT, txtDC,txtTT;

    public TTCTHSX() {
        super("Thông tin hãng sản xuất");
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

        JLabel lblArrow = new JLabel(" >> Thông tin hãng sản xuất");
        lblArrow.setFont(new Font("Arial", Font.BOLD, 20));
        lblArrow.setBounds(410, 70, 300, 30);
        add(lblArrow);

        lblHSXLink.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                dispose();
                new HangSX();
            }
        });



        JLabel lblMaHSX = new JLabel("Mã hãng sản xuất:");
        lblMaHSX.setBounds(270, 120, 150, 30);
        add(lblMaHSX);
        txtMaHSX = new JTextArea();
        txtMaHSX.setBounds(270, 150, 300, 60);
        txtMaHSX.setLineWrap(true);
        txtMaHSX.setWrapStyleWord(true);
        txtMaHSX.setEditable(false);
        txtMaHSX.setFont(new Font("Arial", Font.BOLD, 20));
        txtMaHSX.setForeground(Color.decode("#641A1F"));
        txtMaHSX.setOpaque(false);
        add(txtMaHSX);

        JLabel lblTenHSX = new JLabel("Tên hãng sản xuất:");
        lblTenHSX.setBounds(700, 120, 150, 30);
        add(lblTenHSX);
        txtTenHSX = new JTextArea();
        txtTenHSX.setBounds(700, 150, 300, 40);
        txtTenHSX.setLineWrap(true);
        txtTenHSX.setWrapStyleWord(true);
        txtTenHSX.setEditable(false);
        txtTenHSX.setFont(new Font("Arial", Font.BOLD, 20));
        txtTenHSX.setForeground(Color.decode("#641A1F"));
        txtTenHSX.setOpaque(false);
        add(txtTenHSX);

        JLabel lblMST = new JLabel("Mã số thuế:");
        lblMST.setBounds(700, 200, 150, 30);
        add(lblMST);
        txtMST = new JTextArea();
        txtMST.setBounds(700, 230, 300, 30);
        txtMST.setLineWrap(true);
        txtMST.setWrapStyleWord(true);
        txtMST.setEditable(false);
        txtMST.setFont(new Font("Arial", Font.BOLD, 20));
        txtMST.setForeground(Color.decode("#641A1F"));
        txtMST.setOpaque(false);
        add(txtMST);

        JLabel lblDC = new JLabel("Địa chỉ:");
        lblDC.setBounds(270, 200, 150, 30);
        add(lblDC);
        txtDC = new JTextArea();
        txtDC.setBounds(270, 230, 300, 80);
        txtDC.setLineWrap(true);
        txtDC.setWrapStyleWord(true);
        txtDC.setEditable(false);
        txtDC.setFont(new Font("Arial", Font.BOLD, 20));
        txtDC.setForeground(Color.decode("#641A1F"));
        txtDC.setOpaque(false);
        add(txtDC);

        JLabel lblSDT = new JLabel("Số điện thoại:");
        lblSDT.setBounds(700, 280, 150, 30);
        add(lblSDT);
        txtSDT = new JTextArea();
        txtSDT.setBounds(700, 310, 300, 80);
        txtSDT.setLineWrap(true);
        txtSDT.setWrapStyleWord(true);
        txtSDT.setEditable(false);
        txtSDT.setFont(new Font("Arial", Font.BOLD, 20));
        txtSDT.setForeground(Color.decode("#641A1F"));
        txtSDT.setOpaque(false);
        add(txtSDT);

        JLabel lblTT = new JLabel("Trạng thái:");
        lblTT.setBounds(270, 280, 150, 25);
        add(lblTT);
        txtTT = new JTextArea();
        txtTT.setBounds(270, 310, 300, 40);
        txtTT.setLineWrap(true);
        txtTT.setWrapStyleWord(true);
        txtTT.setEditable(false);
        txtTT.setFont(new Font("Arial", Font.BOLD, 20));
        txtTT.setForeground(Color.decode("#641A1F"));
        txtTT.setOpaque(false);
        add(txtTT);

        JButton btnXoa = new JButton("Xóa");
        btnXoa.setBounds(700, 380, 100, 40);
        btnXoa.setBackground(Color.decode("#F0483E"));
        btnXoa.setForeground(Color.WHITE);
        add(btnXoa);

        btnXoa.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!currentuser.coQuyen("Xóa hãng sản xuất")) {

                    JOptionPane.showMessageDialog(null, "Bạn không có quyền xóa!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                int result = JOptionPane.showConfirmDialog(null, "Bạn có chắc muốn xóa hãng sản xuất này?", "Xác nhận xóa", JOptionPane.YES_NO_OPTION);
                if (result == JOptionPane.YES_OPTION) {
                    HangSanXuatBLL hsxBLL = new HangSanXuatBLL();
                    boolean xoaThanhCong = hsxBLL.deleteHangSanXuat(txtMaHSX.getText());;
                    if (xoaThanhCong) {
                        JOptionPane.showMessageDialog(null, "Đã xóa hãng sản xuất thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                        dispose();
                        new HangSX();
                    } else {
                        JOptionPane.showMessageDialog(null, "Xóa thất bại. Hãng sản xuất có thể đã liên kết dữ liệu khác.", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        JButton btnCapnhat = new JButton("Cập nhật");
        btnCapnhat.setBounds(860, 380, 100, 40);
        btnCapnhat.setBackground(Color.decode("#F0483E"));
        btnCapnhat.setForeground(Color.WHITE);
        add(btnCapnhat);

        btnCapnhat.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String maHSX = txtMaHSX.getText();
                dispose();
                CapnhatttHSX capNhatForm = new CapnhatttHSX();
                capNhatForm.loadhsxInfoForUpdate(maHSX);
                capNhatForm.setVisible(true);
            }
        });

        setVisible(true);
    }
    public void setThongTin(String maHSX, String tenHSX, String MST,String DC, String SDT, int trangthai ) {
        txtMaHSX.setText(maHSX);
        txtTenHSX.setText(tenHSX);
        txtMST.setText(MST);
        txtDC.setText(DC);
        txtSDT.setText(SDT);
        String ttText = (trangthai == 1) ? "Còn sử dụng" : "Không còn sử dụng";
        txtTT.setText(ttText);
        revalidate();
        repaint();
    }


    public static void main(String[] args) {
        new TTCTHSX();
    }
}


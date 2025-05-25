package GUI;


import bll.LoaiSPBLL;
import dal.dbconnection;
import dto.currentuser;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class TTCTloaisp extends BaseFrame {
    private JTextArea txtTenLSP, txtMaLSP,txtTT;

    public TTCTloaisp() {
        super("Thông tin loại sản phẩm");
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

        JLabel lblArrow = new JLabel(" >> Thông tin loại sản phẩm");
        lblArrow.setFont(new Font("Arial", Font.BOLD, 20));
        lblArrow.setBounds(410, 70, 300, 30);
        add(lblArrow);

        lblLoaiSanPhamLink.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                dispose();
                new LoaiSP();
            }
        });


        JLabel lblMaSP = new JLabel("Mã loại sản phẩm:");
        lblMaSP.setBounds(270, 120, 150, 25);
        add(lblMaSP);
        txtMaLSP = new JTextArea();
        txtMaLSP.setBounds(270, 150, 300, 80);
        txtMaLSP.setLineWrap(true);
        txtMaLSP.setWrapStyleWord(true);
        txtMaLSP.setEditable(false);
        txtMaLSP.setFont(new Font("Arial", Font.BOLD, 20));
        txtMaLSP.setForeground(Color.decode("#641A1F"));
        txtMaLSP.setOpaque(false);
        add(txtMaLSP);

        JLabel lblLoaiSP = new JLabel("Tên loại sản phẩm:");
        lblLoaiSP.setBounds(700, 120, 150, 25);
        add(lblLoaiSP);
        txtTenLSP = new JTextArea();
        txtTenLSP.setBounds(700, 150, 300, 30);
        txtTenLSP.setLineWrap(true);
        txtTenLSP.setWrapStyleWord(true);
        txtTenLSP.setEditable(false);
        txtTenLSP.setFont(new Font("Arial", Font.BOLD, 20));
        txtTenLSP.setForeground(Color.decode("#641A1F"));
        txtTenLSP.setOpaque(false);
        add(txtTenLSP);

        JLabel lblTT = new JLabel("Trạng thái:");
        lblTT.setBounds(270, 200, 150, 25);
        add(lblTT);
        txtTT = new JTextArea();
        txtTT.setBounds(270, 230, 300, 40);
        txtTT.setLineWrap(true);
        txtTT.setWrapStyleWord(true);
        txtTT.setEditable(false);
        txtTT.setFont(new Font("Arial", Font.BOLD, 20));
        txtTT.setForeground(Color.decode("#641A1F"));
        txtTT.setOpaque(false);
        add(txtTT);

        JButton btnXoa = new JButton("Xóa");
        btnXoa.setBounds(700, 200, 100, 40);
        btnXoa.setBackground(Color.decode("#F0483E"));
        btnXoa.setForeground(Color.WHITE);
        add(btnXoa);

        btnXoa.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!currentuser.coQuyen("Xóa loại sản phẩm")) {

                    JOptionPane.showMessageDialog(null, "Bạn không có quyền xóa!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                int result = JOptionPane.showConfirmDialog(null, "Bạn có chắc muốn xóa loại sản phẩm này?", "Xác nhận xóa", JOptionPane.YES_NO_OPTION);
                if (result == JOptionPane.YES_OPTION) {
                    LoaiSPBLL lspBLL = new LoaiSPBLL();
                    boolean xoaThanhCong = lspBLL.deleteLSPById(txtMaLSP.getText());;
                    if (xoaThanhCong) {
                        JOptionPane.showMessageDialog(null, "Đã xóa loại sản phẩm thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                        dispose();
                        new LoaiSP();
                    } else {
                        JOptionPane.showMessageDialog(null, "Xóa thất bại. Loại sản phẩm có thể đã liên kết dữ liệu khác.", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        JButton btnCapnhat = new JButton("Cập nhật");
        btnCapnhat.setBounds(860, 200, 100, 40);
        btnCapnhat.setBackground(Color.decode("#F0483E"));
        btnCapnhat.setForeground(Color.WHITE);
        add(btnCapnhat);

        btnCapnhat.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String maLSP = txtMaLSP.getText();
                dispose();
                Capnhatttloaisp capNhatForm = new Capnhatttloaisp();
                capNhatForm.loadlspInfoForUpdate(maLSP);
                capNhatForm.setVisible(true);
            }
        });


        setVisible(true);
    }
    public void setThongTin(String maLSP, String tenLSP,int trangthai) {
        txtMaLSP.setText(maLSP);
        txtTenLSP.setText(tenLSP);
        String ttText = (trangthai == 1) ? "Đang sử dụng" : "Ngưng sử dụng";
        txtTT.setText(ttText);
        revalidate();
        repaint();
    }

    public static void main(String[] args) {
        new TTCTloaisp();
    }
}


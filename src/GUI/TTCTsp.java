package GUI;
import dto.currentuser;
import javax.swing.*;

import bll.SanPhamBLL;

import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import dal.dbconnection;
import dto.SanPhamDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class TTCTsp extends BaseFrame {
    private SanPhamBLL bllSanPham = new SanPhamBLL();
    private JTextArea txtTenSP, txtMaSP, cbLoaiSP, txtSoLuong, txtHSX, txtQcdg,  txtSodk,  txtGia, txtMaLSP, txtMaHSX;
    private JLabel lblInfo;
    public TTCTsp() {
        super("Thông tin sản phẩm");
        initialize();
    }
    @Override
    protected void initUniqueComponents() {
        for (JButton btn : menuButtons) {
            if (btn.getText().equals("Sản phẩm")) {
                btn.setBackground(Color.decode("#EF5D7A"));
                btn.setFont(new Font("Arial", Font.BOLD, 14));
            }
        }

        // Các nút khác vẫn giữ màu mặc định
        for (JButton btn : menuButtons) {
            if (!btn.getText().equals("Sản phẩm")) {
                btn.setBackground(Color.decode("#641A1F")); // Màu mặc định cho các nút khác
                btn.setFont(new Font("Arial", Font.BOLD, 12));
            }
        }


        //Tiêu đề "Sản phẩm >> Thông tin sản phẩm" có thể nhấn
        JLabel lblSanPhamLink = new JLabel("<html><u>Sản phẩm</u></html>");
        lblSanPhamLink.setFont(new Font("Arial", Font.BOLD, 20));
        lblSanPhamLink.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        lblSanPhamLink.setBounds(270, 60, 100, 30);
        add(lblSanPhamLink);

        JLabel lblArrow = new JLabel(" >> Thông tin sản phẩm");
        lblArrow.setFont(new Font("Arial", Font.BOLD, 20));
        lblArrow.setBounds(370, 60, 300, 30);
        add(lblArrow);

        lblSanPhamLink.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                dispose();
                new SanPham();
            }
        });



        // Form nhập sản phẩm
        JLabel lblTenSP = new JLabel("Tên sản phẩm:");
        lblTenSP.setBounds(270, 120, 150, 25);
        add(lblTenSP);
        txtTenSP = new JTextArea();
        txtTenSP.setBounds(270, 150, 400, 60);
        txtTenSP.setLineWrap(true);
        txtTenSP.setWrapStyleWord(true);
        txtTenSP.setEditable(false);
        txtTenSP.setFont(new Font("Arial", Font.BOLD, 20));
        txtTenSP.setForeground(Color.decode("#641A1F"));
        txtTenSP.setOpaque(false);
        add(txtTenSP);

        JLabel lblMaSP = new JLabel("Mã sản phẩm:");
        lblMaSP.setBounds(700, 120, 150, 25);
        add(lblMaSP);
        txtMaSP = new JTextArea();
        txtMaSP.setBounds(700, 150, 400, 40);
        txtMaSP.setLineWrap(true);
        txtMaSP.setWrapStyleWord(true);
        txtMaSP.setEditable(false);
        txtMaSP.setFont(new Font("Arial", Font.BOLD, 20));
        txtMaSP.setForeground(Color.decode("#641A1F"));
        txtMaSP.setOpaque(false);
        add(txtMaSP);

        JLabel lblLoaiSP = new JLabel("Loại sản phẩm:");
        lblLoaiSP.setBounds(270, 200, 150, 25);
        add(lblLoaiSP);
        cbLoaiSP = new JTextArea();
        cbLoaiSP.setBounds(270, 230, 400, 40);
        cbLoaiSP.setLineWrap(true);
        cbLoaiSP.setWrapStyleWord(true);
        cbLoaiSP.setEditable(false);
        cbLoaiSP.setFont(new Font("Arial", Font.BOLD, 20));
        cbLoaiSP.setForeground(Color.decode("#641A1F"));
        cbLoaiSP.setOpaque(false);
        add(cbLoaiSP);

        JLabel lblSoLuong = new JLabel("Số lượng:");
        lblSoLuong.setBounds(700, 200, 150, 25);
        add(lblSoLuong);
        txtSoLuong = new JTextArea();
        txtSoLuong.setBounds(700, 230, 400, 40);
        txtSoLuong.setLineWrap(true);
        txtSoLuong.setWrapStyleWord(true);
        txtSoLuong.setEditable(false);
        txtSoLuong.setFont(new Font("Arial", Font.BOLD, 20));
        txtSoLuong.setForeground(Color.decode("#641A1F"));
        txtSoLuong.setOpaque(false);
        add(txtSoLuong);

        JLabel lblHSX = new JLabel("Hãng sản xuất:");
        lblHSX.setBounds(270, 280, 150, 25);
        add(lblHSX);
        txtHSX = new JTextArea();
        txtHSX.setBounds(270, 310, 300, 80);
        txtHSX.setLineWrap(true);
        txtHSX.setWrapStyleWord(true);
        txtHSX.setEditable(false);
        txtHSX.setFont(new Font("Arial", Font.BOLD, 20));
        txtHSX.setForeground(Color.decode("#641A1F"));
        txtHSX.setOpaque(false);
        add(txtHSX);

        JLabel lblqcdg = new JLabel("Quy cách đóng gói:");
        lblqcdg.setBounds(700, 280, 150, 25);
        add(lblqcdg);
        txtQcdg = new JTextArea();
        txtQcdg.setBounds(700, 310, 300, 40);
        txtQcdg.setLineWrap(true);
        txtQcdg.setWrapStyleWord(true);
        txtQcdg.setEditable(false);
        txtQcdg.setFont(new Font("Arial", Font.BOLD, 20));
        txtQcdg.setForeground(Color.decode("#641A1F"));
        txtQcdg.setOpaque(false);
        add(txtQcdg);



        JLabel lblSodk = new JLabel("Số đăng ký:");
        lblSodk.setBounds(700, 360, 150, 25);
        add(lblSodk);
        txtSodk = new JTextArea();
        txtSodk.setBounds(700, 390, 300, 40);
        txtSodk.setLineWrap(true);
        txtSodk.setWrapStyleWord(true);
        txtSodk.setEditable(false);
        txtSodk.setFont(new Font("Arial", Font.BOLD, 20));
        txtSodk.setForeground(Color.decode("#641A1F"));
        txtSodk.setOpaque(false);
        add(txtSodk);


        JLabel lblGia = new JLabel("Giá bán:");
        lblGia.setBounds(700, 440, 150, 25);
        add(lblGia);
        txtGia = new JTextArea();
        txtGia.setBounds(700, 470, 300, 40);
        txtGia.setLineWrap(true);
        txtGia.setWrapStyleWord(true);
        txtGia.setEditable(false);
        txtGia.setFont(new Font("Arial", Font.BOLD, 20));
        txtGia.setForeground(Color.decode("#641A1F"));
        txtGia.setOpaque(false);
        add(txtGia);

        JButton btnXoa = new JButton("Xóa");
        btnXoa.setBounds(700, 520, 100, 40);
        btnXoa.setBackground(Color.decode("#F0483E"));
        btnXoa.setForeground(Color.WHITE);
        add(btnXoa);

        btnXoa.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (currentuser.coQuyen("XOASANPHAM")) {
                    int result = JOptionPane.showConfirmDialog(null, "Bạn có chắc muốn xóa sản phẩm này?", "Xác nhận xóa", JOptionPane.YES_NO_OPTION);
                    if (result == JOptionPane.YES_OPTION) {
                        try (Connection conn = dbconnection.getConnection()) {
                            String sql = "DELETE FROM SANPHAM WHERE MASP = ?";
                            PreparedStatement pstmt = conn.prepareStatement(sql);
                            pstmt.setString(1, txtMaSP.getText());
                            int rowsAffected = pstmt.executeUpdate();
                            if (rowsAffected > 0) {
                                JOptionPane.showMessageDialog(null, "Đã xóa sản phẩm thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                                dispose();
                                new SanPham(); // trở lại giao diện danh sách sản phẩm
                            } else {
                                JOptionPane.showMessageDialog(null, "Xóa thất bại. Không tìm thấy sản phẩm!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                            }
                        } catch (Exception ex) {
                            ex.printStackTrace();
                            JOptionPane.showMessageDialog(null, "Lỗi khi xóa sản phẩm!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                        }
                    }else {
                        JOptionPane.showMessageDialog(null, "Bạn không có quyền xóa!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                    }}
            }
        });

        JButton btnCapnhat = new JButton("Cập nhật");
        btnCapnhat.setBounds(860, 520, 100, 40);
        btnCapnhat.setBackground(Color.decode("#F0483E"));
        btnCapnhat.setForeground(Color.WHITE);
        add(btnCapnhat);

        btnCapnhat.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String maSP = txtMaSP.getText();  // hoặc nơi bạn hiển thị mã sản phẩm
                dispose();
                Capnhatttsp capNhatForm = new Capnhatttsp();
                capNhatForm.loadProductInfoForUpdate(maSP);  // gọi phương thức load thông tin
                capNhatForm.setVisible(true);  // mở form cập nhật
            }
        });


        setVisible(true);
    }


    public void setThongTin(String tenSP, String maSP, String loaiSP, int soLuong, String maHSX, String dongGoi,
                            String soDk,  Double gia) {
        txtTenSP.setText(tenSP);
        txtMaSP.setText(maSP);
        cbLoaiSP.setText(loaiSP);
        txtSoLuong.setText(String.valueOf(soLuong));
        txtHSX.setText(maHSX);  // Sử dụng tham số maHSX
        txtQcdg.setText(dongGoi);
        txtSodk.setText(soDk);
        txtGia.setText(String.valueOf(gia));

        revalidate();
        repaint();
    }



    public static void main(String[] args) {
        new TTCTsp();
    }
}


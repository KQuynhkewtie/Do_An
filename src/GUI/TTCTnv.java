package GUI;

import javax.swing.*;

import bll.NhanVienBLL;
import dal.dbconnection;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class TTCTnv extends BaseFrame {
	private NhanVienBLL bllnv = new NhanVienBLL();
	private JTextArea txtTenNV, txtMaNV, txtCCCD, txtSDT, txtvitri, txtMST;
    private JLabel lblInfo;
    public TTCTnv() {
    	super("Thông tin nhân viên");
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
        
        // Các nút khác vẫn giữ màu mặc định
        for (JButton btn : menuButtons) {
            if (!btn.getText().equals("Nhân viên")) {
                btn.setBackground(Color.decode("#641A1F")); // Màu mặc định cho các nút khác
                btn.setFont(new Font("Arial", Font.BOLD, 12));
            }
        }

        // tiêu đề "Khách hàng >> Thông tin khách hàng" có thể nhấn tương tự như trên
        JLabel lblKhachhangLink = new JLabel("<html><u>Nhân viên</u></html>");
        lblKhachhangLink.setFont(new Font("Arial", Font.BOLD, 20));
        lblKhachhangLink.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        lblKhachhangLink.setBounds(270, 70, 100, 30);
        add(lblKhachhangLink);
        // bấm vào "Khách hàng" sẽ quay về trang khách hàng
        lblKhachhangLink.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                dispose();
                new NhanVien();
            }
        });
        JLabel lblArrow = new JLabel(" >> Thông tin nhân viên");
        lblArrow.setFont(new Font("Arial", Font.BOLD, 20));
        lblArrow.setBounds(370, 70, 300, 30);
        add(lblArrow);
        
        
        // form thêm nhân viên
        JLabel lblMaNV = new JLabel("Mã nhân viên:");
        lblMaNV.setBounds(270, 120, 150, 30);
        add(lblMaNV);
        txtMaNV = new JTextArea();
        txtMaNV.setBounds(270, 150, 300, 30);
        txtMaNV.setLineWrap(true);
        txtMaNV.setWrapStyleWord(true);
        txtMaNV.setEditable(false);
        txtMaNV.setFont(new Font("Arial", Font.BOLD, 20));
        txtMaNV.setForeground(Color.decode("#641A1F"));
        txtMaNV.setOpaque(false); 
        add(txtMaNV);
        // họ tên
        JLabel lblHoTen = new JLabel("Họ tên:");
        lblHoTen.setBounds(270, 200, 150, 30);
        add(lblHoTen);
        txtTenNV = new JTextArea();
        txtTenNV.setBounds(270, 230, 300, 80);
        txtTenNV.setLineWrap(true);
        txtTenNV.setWrapStyleWord(true);
        txtTenNV.setEditable(false);
        txtTenNV.setFont(new Font("Arial", Font.BOLD, 20));
        txtTenNV.setForeground(Color.decode("#641A1F"));
        txtTenNV.setOpaque(false); 
        add(txtTenNV);

        // số điện thoại
        JLabel lblSDT = new JLabel("Số điện thoại:");
        lblSDT.setBounds(270, 280, 150, 30);
        add(lblSDT);
        txtSDT = new JTextArea();
        txtSDT.setBounds(270, 310, 300, 60);
        txtSDT.setLineWrap(true);
        txtSDT.setWrapStyleWord(true);
        txtSDT.setEditable(false);
        txtSDT.setFont(new Font("Arial", Font.BOLD, 20));
        txtSDT.setForeground(Color.decode("#641A1F"));
        txtSDT.setOpaque(false); 
        add(txtSDT);

        // cccd
        JLabel lblCCCD = new JLabel("CCCD:");
        lblCCCD.setBounds(270, 360, 150, 30);
        add(lblCCCD);
        txtCCCD = new JTextArea();
        txtCCCD.setBounds(270, 390, 300, 30);
        txtCCCD.setLineWrap(true);
        txtCCCD.setWrapStyleWord(true);
        txtCCCD.setEditable(false);
        txtCCCD.setFont(new Font("Arial", Font.BOLD, 20));
        txtCCCD.setForeground(Color.decode("#641A1F"));
        txtCCCD.setOpaque(false); 
        add(txtCCCD);

        // vị trí công việc
        JLabel lblVTCV = new JLabel("Vị trí công việc:");
        lblVTCV.setBounds(700, 120, 150, 30);
        add(lblVTCV);
        txtvitri = new JTextArea();
        txtvitri.setBounds(700, 150, 300, 80);
        txtvitri.setLineWrap(true);
        txtvitri.setWrapStyleWord(true);
        txtvitri.setEditable(false);
        txtvitri.setFont(new Font("Arial", Font.BOLD, 20));
        txtvitri.setForeground(Color.decode("#641A1F"));
        txtvitri.setOpaque(false); 
        add(txtvitri);

        // mã số thuế
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
        // nút xóa
        JButton btnXoa = new JButton("Xóa");
        btnXoa.setBounds(700, 520, 100, 40);
        btnXoa.setBackground(Color.decode("#F0483E"));
        btnXoa.setForeground(Color.WHITE);
        add(btnXoa);
        

        btnXoa.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int result = JOptionPane.showConfirmDialog(null, "Bạn có chắc muốn xóa nhân viên này?", "Xác nhận xóa", JOptionPane.YES_NO_OPTION);
                if (result == JOptionPane.YES_OPTION) {
                    try (Connection conn = dbconnection.getConnection()) {
                        String sql = "DELETE FROM NHANVIEN WHERE MANHANVIEN = ?";
                        PreparedStatement pstmt = conn.prepareStatement(sql);
                        pstmt.setString(1, txtMaNV.getText());
                        int rowsAffected = pstmt.executeUpdate();
                        if (rowsAffected > 0) {
                            JOptionPane.showMessageDialog(null, "Đã xóa nhân viên thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                            dispose();
                            new NhanVien(); // trở lại giao diện danh sách sản phẩm
                        } else {
                            JOptionPane.showMessageDialog(null, "Xóa thất bại. Không tìm thấy nhân viên!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(null, "Lỗi khi xóa nhân viên", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    }
                }
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
                String maNV = txtMaNV.getText();  // hoặc nơi bạn hiển thị mã sản phẩm
                Capnhatttnv capNhatForm = new Capnhatttnv();
                capNhatForm.loadnvInfoForUpdate(maNV);  // gọi phương thức load thông tin
                capNhatForm.setVisible(true);  // mở form cập nhật
            }
        });


        setVisible(true);

    }
    public void setThongTin(String maNV, String tenNV, String CCCD, String SDT, String vitri, 
            String mst) {
txtMaNV.setText(maNV);
txtTenNV.setText(tenNV);
txtCCCD.setText(CCCD);
txtSDT.setText(SDT);
txtvitri.setText(vitri);  
txtMST.setText(mst);


revalidate();
repaint();
}

    public static void main(String[] args) {
        new TTCTnv();
    }
}

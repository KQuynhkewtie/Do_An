package GUI;


import javax.swing.*;

import bll.HangSanXuatBLL;
import dal.dbconnection;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class TTCTHSX extends BaseFrame {
	private HangSanXuatBLL bllhsx = new HangSanXuatBLL();
	private JTextArea txtTenHSX, txtMaHSX, txtMST, txtSDT, txtDC;
    private JLabel lblInfo;
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
	        
	        // Các nút khác vẫn giữ màu mặc định
	        for (JButton btn : menuButtons) {
	            if (!btn.getText().equals("Hãng sản xuất")) {
	                btn.setBackground(Color.decode("#641A1F")); // Màu mặc định cho các nút khác
	                btn.setFont(new Font("Arial", Font.BOLD, 12));
	            }
	        }

        JLabel lblLoaiSanPhamLink = new JLabel("<html><u>Hãng sản xuất</u></html>");
        lblLoaiSanPhamLink.setFont(new Font("Arial", Font.BOLD, 20));
        lblLoaiSanPhamLink.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        lblLoaiSanPhamLink.setBounds(270, 70, 160, 30);
        add(lblLoaiSanPhamLink);

        JLabel lblArrow = new JLabel(" >> Thông tin hãng sản xuất");
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



        JLabel lblMaHSX = new JLabel("Mã hãng sản xuất:");
        lblMaHSX.setBounds(700, 120, 150, 30);
        add(lblMaHSX);
        txtMaHSX = new JTextArea();
        txtMaHSX.setBounds(700, 150, 300, 40);
        txtMaHSX.setLineWrap(true);
        txtMaHSX.setWrapStyleWord(true);
        txtMaHSX.setEditable(false);
        txtMaHSX.setFont(new Font("Arial", Font.BOLD, 20));
        txtMaHSX.setForeground(Color.decode("#641A1F"));
        txtMaHSX.setOpaque(false); 
        add(txtMaHSX);

        JLabel lblTenHSX = new JLabel("Tên hãng sản xuất:");
        lblTenHSX.setBounds(270, 120, 150, 30);
        add(lblTenHSX);
        txtTenHSX = new JTextArea();
        txtTenHSX.setBounds(270, 150, 300, 60);
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

        JButton btnXoa = new JButton("Xóa");
        btnXoa.setBounds(700, 380, 100, 40);
        btnXoa.setBackground(Color.decode("#F0483E"));
        btnXoa.setForeground(Color.WHITE);
        add(btnXoa);

        btnXoa.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int result = JOptionPane.showConfirmDialog(null, "Bạn có chắc muốn xóa hãng sản xuất này?", "Xác nhận xóa", JOptionPane.YES_NO_OPTION);
                if (result == JOptionPane.YES_OPTION) {
                    try (Connection conn = dbconnection.getConnection()) {
                        String sql = "DELETE FROM HANGSANXUAT WHERE MAHSX = ?";
                        PreparedStatement pstmt = conn.prepareStatement(sql);
                        pstmt.setString(1, txtMaHSX.getText());
                        int rowsAffected = pstmt.executeUpdate();
                        if (rowsAffected > 0) {
                            JOptionPane.showMessageDialog(null, "Đã xóa hãng sản xuất thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                            dispose();
                            new HangSX(); // trở lại giao diện danh sách sản phẩm
                        } else {
                            JOptionPane.showMessageDialog(null, "Xóa thất bại. Không tìm thấy hãng sản xuất!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(null, "Lỗi khi xóa hãng sản xuất", "Lỗi", JOptionPane.ERROR_MESSAGE);
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
                String maHSX = txtMaHSX.getText();  // hoặc nơi bạn hiển thị mã sản phẩm
                CapnhatttHSX capNhatForm = new CapnhatttHSX();
                capNhatForm.loadhsxInfoForUpdate(maHSX);  // gọi phương thức load thông tin
                dispose();
                capNhatForm.setVisible(true);  // mở form cập nhật
            }
        });

        setVisible(true);
    }
    public void setThongTin(String maHSX, String tenHSX, String MST,String DC, String SDT ) { 
txtMaHSX.setText(maHSX);
txtTenHSX.setText(tenHSX);
txtMST.setText(MST);
txtDC.setText(DC); 
txtSDT.setText(SDT);
 

revalidate();
repaint();
}

    
    public static void main(String[] args) {
        new TTCTHSX();
    }
}


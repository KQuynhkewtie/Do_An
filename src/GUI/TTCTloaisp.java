package GUI;


import bll.LoaiSPBLL;
import dal.dbconnection;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class TTCTloaisp extends BaseFrame {
	private JLabel lblInfo;
	private LoaiSPBLL blllsp = new LoaiSPBLL();
	private JTextArea txtTenLSP, txtMaLSP;
	
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
	        
	        // Các nút khác vẫn giữ màu mặc định
	        for (JButton btn : menuButtons) {
	            if (!btn.getText().equals("Loại sản phẩm")) {
	                btn.setBackground(Color.decode("#641A1F")); // Màu mặc định cho các nút khác
	                btn.setFont(new Font("Arial", Font.BOLD, 12));
	            }
	        }

        //Tiêu đề "Sản phẩm >> Thông tin loại sản phẩm" có thể nhấn
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
        
        // Form nhập loại sản phẩm
        JLabel lblMaSP = new JLabel("Mã loại sản phẩm:");
        lblMaSP.setBounds(700, 120, 150, 25);
        add(lblMaSP);
        txtMaLSP = new JTextArea();
        txtMaLSP.setBounds(700, 150, 300, 30);
        txtMaLSP.setLineWrap(true);
        txtMaLSP.setWrapStyleWord(true);
        txtMaLSP.setEditable(false);
        txtMaLSP.setFont(new Font("Arial", Font.BOLD, 20));
        txtMaLSP.setForeground(Color.decode("#641A1F"));
        txtMaLSP.setOpaque(false); 
        add(txtMaLSP);

        JLabel lblLoaiSP = new JLabel("Tên loại sản phẩm:");
        lblLoaiSP.setBounds(270, 120, 150, 25);
        add(lblLoaiSP);
        txtTenLSP = new JTextArea();
        txtTenLSP.setBounds(270, 150, 300, 80);
        txtTenLSP.setLineWrap(true);
        txtTenLSP.setWrapStyleWord(true);
        txtTenLSP.setEditable(false);
        txtTenLSP.setFont(new Font("Arial", Font.BOLD, 20));
        txtTenLSP.setForeground(Color.decode("#641A1F"));
        txtTenLSP.setOpaque(false);  
        add(txtTenLSP);
        
        JButton btnXoa = new JButton("Xóa");
        btnXoa.setBounds(700, 200, 100, 40);
        btnXoa.setBackground(Color.decode("#F0483E"));
        btnXoa.setForeground(Color.WHITE);
        add(btnXoa);

        btnXoa.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int result = JOptionPane.showConfirmDialog(null, "Bạn có chắc muốn xóa loại sản phẩm này?", "Xác nhận xóa", JOptionPane.YES_NO_OPTION);
                if (result == JOptionPane.YES_OPTION) {
                    try (Connection conn = dbconnection.getConnection()) {
                        String sql = "DELETE FROM LOAISANPHAM WHERE MALSP = ?";
                        PreparedStatement pstmt = conn.prepareStatement(sql);
                        pstmt.setString(1, txtMaLSP.getText());
                        int rowsAffected = pstmt.executeUpdate();
                        if (rowsAffected > 0) {
                            JOptionPane.showMessageDialog(null, "Đã xóa loại sản phẩm thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                            dispose();
                            new LoaiSP(); // trở lại giao diện danh sách loại sản phẩm
                        } else {
                            JOptionPane.showMessageDialog(null, "Xóa thất bại. Không tìm thấy loại sản phẩm!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(null, "Lỗi khi xóa loại sản phẩm", "Lỗi", JOptionPane.ERROR_MESSAGE);
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
                String maLSP = txtMaLSP.getText();  // hoặc nơi bạn hiển thị mã khách hàng
                Capnhatttloaisp capNhatForm = new Capnhatttloaisp();
                capNhatForm.loadlspInfoForUpdate(maLSP);  // gọi phương thức load thông tin
                capNhatForm.setVisible(true);  // mở form cập nhật
            }
        });


        setVisible(true);
    }
    public void setThongTin(String tenLSP, String maLSP) {
    	txtTenLSP.setText(tenLSP);
    	txtMaLSP.setText(maLSP);
    	
    	revalidate();
    	repaint();
    	}

    public static void main(String[] args) {
        new TTCTloaisp();
    }
}


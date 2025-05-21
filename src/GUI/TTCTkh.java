package GUI;
import javax.swing.*;

import bll.KhachHangBLL;
import dal.dbconnection;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class TTCTkh extends BaseFrame {
	private JLabel lblInfo;
	private KhachHangBLL bllKhachhang = new KhachHangBLL();
	private JTextArea txtHoTen, txtMaKH, txtDiemTL, txtLoaiKH, txtSDT;

	public TTCTkh() {
		super("Thông tin khách hàng");	
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
		        
		        // Các nút khác vẫn giữ màu mặc định
		        for (JButton btn : menuButtons) {
		            if (!btn.getText().equals("Khách hàng")) {
		                btn.setBackground(Color.decode("#641A1F")); // Màu mặc định cho các nút khác
		                btn.setFont(new Font("Arial", Font.BOLD, 12));
		            }
		        }

	        // tiêu đề "Khách hàng >> Thông tin khách hàng" có thể nhấn tương tự như trên
	        JLabel lblKhachhangLink = new JLabel("<html><u>Khách hàng</u></html>");
	        lblKhachhangLink.setFont(new Font("Arial", Font.BOLD, 20));
	        lblKhachhangLink.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
	        lblKhachhangLink.setBounds(270, 70, 120, 30);
	        add(lblKhachhangLink);
	        
	       

	        // bấm vào "Khách hàng" sẽ quay về trang khách hàng
	        lblKhachhangLink.addMouseListener(new MouseAdapter() {
	            @Override
	            public void mouseClicked(MouseEvent e) {
	                dispose();
	                new KhachHang();
	            }
	        });
	        JLabel lblArrow = new JLabel(" >> Thông tin khách hàng");
	        lblArrow.setFont(new Font("Arial", Font.BOLD, 20));
	        lblArrow.setBounds(390, 70, 300, 30);
	        add(lblArrow);

	        

	        // form thêm khách hàng
	        JLabel lblMaKH = new JLabel("Mã khách hàng:");
	        lblMaKH.setBounds(270, 120, 150, 30);
	        add(lblMaKH);
	        txtMaKH = new JTextArea();
	        txtMaKH.setBounds(450, 120, 200, 30);
	        txtMaKH.setLineWrap(true);
	        txtMaKH.setWrapStyleWord(true);
	        txtMaKH.setEditable(false);
	        txtMaKH.setFont(new Font("Arial", Font.BOLD, 20));
	        txtMaKH.setForeground(Color.decode("#641A1F"));
	        txtMaKH.setOpaque(false); 
	        add(txtMaKH);
	        // họ tên tương tự như mã khách hàng
	        JLabel lblHoTen = new JLabel("Họ tên:");
	        lblHoTen.setBounds(270, 170, 150, 30);
	        add(lblHoTen);
	        txtHoTen = new JTextArea();
	        txtHoTen.setBounds(450, 170, 200, 60);
	        txtHoTen.setLineWrap(true);
	        txtHoTen.setWrapStyleWord(true);
	        txtHoTen.setEditable(false);
	        txtHoTen.setFont(new Font("Arial", Font.BOLD, 20));
	        txtHoTen.setForeground(Color.decode("#641A1F"));
	        txtHoTen.setOpaque(false);  
	        add(txtHoTen);

	        // Số điện thoại
	        JLabel lblSoLanMua = new JLabel("Số lần mua:");
	        lblSoLanMua.setBounds(270, 220, 150, 30);
	        add(lblSoLanMua);
	        txtSDT = new JTextArea();
	        txtSDT.setBounds(450, 220, 200, 30);
	        txtSDT.setLineWrap(true);
	        txtSDT.setWrapStyleWord(true);
	        txtSDT.setEditable(false);
	        txtSDT.setFont(new Font("Arial", Font.BOLD, 20));
	        txtSDT.setForeground(Color.decode("#641A1F"));
	        txtSDT.setOpaque(false);
	        add(txtSDT);
	        // điểm tích lũy
	        JLabel lblDiemTL = new JLabel("Điểm tích lũy:");
	        lblDiemTL.setBounds(270, 270, 150, 30);
	        add(lblDiemTL);
	        txtDiemTL = new JTextArea();
	        txtDiemTL.setBounds(450, 270, 200, 30);
	        txtDiemTL.setLineWrap(true);
	        txtDiemTL.setWrapStyleWord(true);
	        txtDiemTL.setEditable(false);
	        txtDiemTL.setFont(new Font("Arial", Font.BOLD, 20));
	        txtDiemTL.setForeground(Color.decode("#641A1F"));
	        txtDiemTL.setOpaque(false);  
	        add(txtDiemTL);
	        // mã loại khách hàng
	        JLabel lblMaLoaiKH = new JLabel("Mã loại khách hàng:");
	        lblMaLoaiKH.setBounds(270, 320, 150, 30);
	        add(lblMaLoaiKH);
	        txtLoaiKH = new JTextArea();
	        txtLoaiKH.setBounds(450, 320, 200, 30);
	        txtLoaiKH.setLineWrap(true);
	        txtLoaiKH.setWrapStyleWord(true);
	        txtLoaiKH.setEditable(false);
	        txtLoaiKH.setFont(new Font("Arial", Font.BOLD, 20));
	        txtLoaiKH.setForeground(Color.decode("#641A1F"));
	        txtLoaiKH.setOpaque(false);
	        add(txtLoaiKH);
	        // nút xóa
	        JButton btnXoa = new JButton("Xóa");
	        btnXoa.setBounds(700, 520, 100, 40);
	        btnXoa.setBackground(Color.decode("#F0483E"));
	        btnXoa.setForeground(Color.WHITE);
	        add(btnXoa);
	        btnXoa.addActionListener(new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
	                int result = JOptionPane.showConfirmDialog(null, "Bạn có chắc muốn xóa khách hàng này?", "Xác nhận xóa", JOptionPane.YES_NO_OPTION);
	                if (result == JOptionPane.YES_OPTION) {
	                    try (Connection conn = dbconnection.getConnection()) {
	                        String sql = "DELETE FROM KHACHHANG WHERE MAKH = ?";
	                        PreparedStatement pstmt = conn.prepareStatement(sql);
	                        pstmt.setString(1, txtMaKH.getText());
	                        int rowsAffected = pstmt.executeUpdate();
	                        if (rowsAffected > 0) {
	                            JOptionPane.showMessageDialog(null, "Đã xóa khách hàng thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
	                            dispose();
	                            new KhachHang(); // trở lại giao diện danh sách khách hàng
	                        } else {
	                            JOptionPane.showMessageDialog(null, "Xóa thất bại. Không tìm thấy khách hàng!", "Lỗi", JOptionPane.ERROR_MESSAGE);
	                        }
	                    } catch (Exception ex) {
	                        ex.printStackTrace();
	                        JOptionPane.showMessageDialog(null, "Lỗi khi xóa khách hàng!", "Lỗi", JOptionPane.ERROR_MESSAGE);
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
	                String maKH = txtMaKH.getText();  // hoặc nơi bạn hiển thị mã khách hàng
	                Capnhatttkh capNhatForm = new Capnhatttkh();
	                capNhatForm.loadCustomerInfoForUpdate(maKH);  // gọi phương thức load thông tin
	                capNhatForm.setVisible(true);  // mở form cập nhật
	            }
	        });

	        setVisible(true);
	    }

	   

	    public void setThongTin(String tenKH, String maKH, Double DTL, String maLKH, String sdt) {
	txtHoTen.setText(tenKH);
	txtMaKH.setText(maKH);
	txtDiemTL.setText(String.valueOf(DTL));
	txtLoaiKH.setText(maLKH);
	txtSDT.setText(sdt);

	revalidate();
	repaint();
	}
	    public static void main(String[] args) {
	        new TTCTkh();
	    }
}

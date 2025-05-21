package GUI;

import javax.swing.*;

import bll.KhachHangBLL;
import dal.khachhangdal;
import dto.KhachHangDTO;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Capnhatttkh extends BaseFrame {
	private KhachHangBLL bllKhachhang = new KhachHangBLL();
	private khachhangdal khDAL= new khachhangdal();
	private JTextField  txtHoTen, txtMaKH, txtDiemTL, txtMaLoaiKH, txtSDT;
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
	        
	        // Các nút khác vẫn giữ màu mặc định
	        for (JButton btn : menuButtons) {
	            if (!btn.getText().equals("Khách hàng")) {
	                btn.setBackground(Color.decode("#641A1F")); // Màu mặc định cho các nút khác
	                btn.setFont(new Font("Arial", Font.BOLD, 12));
	            }
	        }
        // Tiêu đề "Sản phẩm >> Thông tin sản phẩm" có thể nhấn
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
                dispose();
                new TTCTkh();
            }
        });
        
        
        // form thêm khách hàng
        JLabel lblMaKH = new JLabel("Mã khách hàng:");
        lblMaKH.setBounds(270, 120, 150, 30);
        add(lblMaKH);
        txtMaKH = new JTextField();
        txtMaKH.setBounds(450, 120, 200, 30);
        txtMaKH.setBackground(new Color(230, 230, 230));
        txtMaKH.setEditable(false);
        txtMaKH.setFocusable(false);
        add(txtMaKH);
        // họ tên
        JLabel lblHoTen = new JLabel("Họ tên:");
        lblHoTen.setBounds(270, 170, 150, 30);
        add(lblHoTen);
        txtHoTen = new JTextField();
        txtHoTen.setBounds(450, 170, 200, 30);
        add(txtHoTen);
        // số lần mua
        JLabel lblSoLanMua = new JLabel("Số lần mua:");
        lblSoLanMua.setBounds(270, 220, 150, 30);
        add(lblSoLanMua);
        txtSDT = new JTextField();
        txtSDT.setBounds(450, 220, 200, 30);
        add(txtSDT);
        // điểm tích lũy
        JLabel lblDiemTL = new JLabel("Điểm tích lũy:");
        lblDiemTL.setBounds(270, 270, 150, 30);
        add(lblDiemTL);
        txtDiemTL = new JTextField();
        txtDiemTL.setBounds(450, 270, 200, 30);
        add(txtDiemTL);
        // mã loại khách hàng
        JLabel lblMaLoaiKH = new JLabel("Mã loại khách hàng:");
        lblMaLoaiKH.setBounds(270, 320, 150, 30);
        add(lblMaLoaiKH);
        txtMaLoaiKH = new JTextField();
        txtMaLoaiKH.setBounds(450, 320, 200, 30);
        add(txtMaLoaiKH);

        // Nút Lưu
        JButton btnLuusua = new JButton("Lưu");
        btnLuusua.setBounds(700, 520, 100, 40);
        btnLuusua.setBackground(Color.decode("#F0483E"));
        btnLuusua.setForeground(Color.WHITE);
        add(btnLuusua);
        btnLuusua.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	
                // Lấy dữ liệu từ các trường nhập
                String maKH = txtMaKH.getText().trim();
                String tenKH = txtHoTen.getText().trim();
                String DTLstr = txtDiemTL.getText().trim();
                String maloaikh = txtMaLoaiKH.getText().trim();
                String SDTstr = txtSDT.getText().trim();
                
                try {
                    double DTL = Double.parseDouble(DTLstr);
                   

                    // Tạo đối tượng khách hàng cập nhật
                    KhachHangDTO kh = new KhachHangDTO();
                    kh.setMaKH(maKH);
                    kh.setHoTen(tenKH);
                    kh.setDiemTichLuy(DTL);
                    kh.setLoaiKhach(maloaikh);
                    kh.setSDT();
                 
                    System.out.println("MAKH cần cập nhật: " + maKH);
                    
                    // Gọi hàm cập nhật từ BLL/DAL
                    boolean success = bllKhachhang.updateKhachHang(kh);

                    if (success) {
                        JOptionPane.showMessageDialog(null, "Cập nhật khách hàng thành công!");
                        dispose(); // đóng form nếu muốn
                        TTCTkh ctkh = new TTCTkh();
                        ctkh.setThongTin(kh.getHoTen(), kh.getMaKH(), kh.getDiemTichLuy(), kh.getLoaiKhach(), kh.getSDT());
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
        // Lấy thông tin sản phẩm từ cơ sở dữ liệu (tương tự như trang chi tiết sản phẩm)
        KhachHangDTO kh = bllKhachhang.getKhachHangById(maKH);

        if (kh != null) {
            // Điền thông tin vào các trường nhập liệu trong trang cập nhật thông tin sản phẩm
            txtHoTen.setText(kh.getHoTen());
            txtMaKH.setText(kh.getMaKH());
            txtDiemTL.setText(String.valueOf(kh.getDiemTichLuy()));
            txtMaLoaiKH.setText(String.valueOf(kh.getLoaiKhach()));
            txtSDT.setText(String.valueOf(kh.getSDT()));

        } else {
            JOptionPane.showMessageDialog(this, "Không tìm thấy khách hàng!");
        }
    }

    public static void main(String[] args) {
        new Capnhatttkh();
    }
}

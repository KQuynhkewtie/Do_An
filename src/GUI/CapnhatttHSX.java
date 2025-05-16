package GUI;

import javax.swing.*;

import bll.HangSanXuatBLL;
import dal.hangsanxuatdal;
import dto.HangSanXuatDTO;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class CapnhatttHSX extends BaseFrame {
	private HangSanXuatBLL bllhsx = new HangSanXuatBLL();
	private hangsanxuatdal hsxDAL= new hangsanxuatdal();
	private JTextField  txtTenHSX, txtMaHSX, txtMST, txtSDT, txtDC;
    public CapnhatttHSX() {
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
	        
	        // Các nút khác vẫn giữ màu mặc định
	        for (JButton btn : menuButtons) {
	            if (!btn.getText().equals("Hãng sản xuất")) {
	                btn.setBackground(Color.decode("#641A1F")); // Màu mặc định cho các nút khác
	                btn.setFont(new Font("Arial", Font.BOLD, 12));
	            }
	        }
        //Tiêu đề "Sản phẩm >> Thông tin loại sản phẩm" có thể nhấn
        JLabel lblLoaiSanPhamLink = new JLabel("<html><u>Hãng sản xuất</u></html>");
        lblLoaiSanPhamLink.setFont(new Font("Arial", Font.BOLD, 20));
        lblLoaiSanPhamLink.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        lblLoaiSanPhamLink.setBounds(270, 70, 160, 30);
        add(lblLoaiSanPhamLink);

        JLabel lblTTLSPLink = new JLabel("<html>>> <u>Thông tin hãng sản xuất</u></html>");
        lblTTLSPLink.setFont(new Font("Arial", Font.BOLD, 20));
        lblTTLSPLink.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        lblTTLSPLink.setBounds(420, 70, 280, 30);
        add(lblTTLSPLink);

        JLabel lblArrow = new JLabel(" >> Cập nhật thông tin hãng sản xuất");
        lblArrow.setFont(new Font("Arial", Font.BOLD, 20));
        lblArrow.setBounds(680, 70, 900, 30);
        add(lblArrow);
        
        lblTTLSPLink.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                dispose();
                new HangSX();
            }
        });

     
        lblTTLSPLink.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                dispose();
                new TTCTHSX();
            }
        });

        JLabel lblMaHSX = new JLabel("Mã hãng sản xuất:");
        lblMaHSX.setBounds(700, 120, 150, 25);
        add(lblMaHSX);
        txtMaHSX = new JTextField();
        txtMaHSX.setBounds(700, 150, 300, 30);
        txtMaHSX.setBackground(new Color(230, 230, 230));
        txtMaHSX.setEditable(false);
        txtMaHSX.setFocusable(false);
        add(txtMaHSX);

        JLabel lbltenHSX = new JLabel("Tên hãng sản xuất:");
        lbltenHSX.setBounds(270, 120, 150, 25);
        add(lbltenHSX);
        txtTenHSX = new JTextField();
        txtTenHSX.setBounds(270, 150, 300, 30);
        add(txtTenHSX);
        
        JLabel lblMaSoThue = new JLabel("Mã số thuế:");
        lblMaSoThue.setBounds(700, 200, 150, 25);
        add(lblMaSoThue);
        txtMST = new JTextField();
        txtMST.setBounds(700, 230, 300, 30);
        add(txtMST);

        JLabel lbldiachi = new JLabel("Địa chỉ:");
        lbldiachi.setBounds(270, 200, 150, 25);
        add(lbldiachi);
        txtDC = new JTextField();
        txtDC.setBounds(270, 230, 300, 30);
        add(txtDC);

        JLabel lblsdt = new JLabel("Số điện thoại:");
        lblsdt.setBounds(700, 280, 150, 25);
        add(lblsdt);
        txtSDT = new JTextField();
        txtSDT.setBounds(700, 310, 300, 30);
        add(txtSDT);


        // Nút Lưu
        JButton btnLuusua = new JButton("Lưu");
        btnLuusua.setBounds(700, 380, 100, 40);
        btnLuusua.setBackground(Color.decode("#F0483E"));
        btnLuusua.setForeground(Color.WHITE);
        add(btnLuusua);

        btnLuusua.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	
                // Lấy dữ liệu từ các trường nhập
                String maHSX = txtMaHSX.getText().trim();
                String tenHSX = txtTenHSX.getText().trim();
                String MST = txtMST.getText().trim();
                String SDT = txtSDT.getText().trim();
                String DC = txtDC.getText().trim();
                
                try {
                    // Tạo đối tượng loại sản phẩm cập nhật
                    HangSanXuatDTO hsx = new HangSanXuatDTO();
                    hsx.setMaHSX(maHSX);
                    hsx.setTenHSX(tenHSX);
                    hsx.setMaSoThue(MST);
                    hsx.setSdt(SDT);
                    hsx.setDiaChi(DC);
                    
                    System.out.println("MAHSX cần cập nhật: " + maHSX);
                    
                    // Gọi hàm cập nhật từ BLL/DAL
                    boolean success = bllhsx.updateHangSanXuat(hsx);

                    if (success) {
                        JOptionPane.showMessageDialog(null, "Cập nhật hãng sản xuất thành công!");
                        dispose(); // đóng form nếu muốn
                        TTCTHSX cthsx = new TTCTHSX();
                        cthsx.setThongTin(hsx.getMaHSX(), hsx.getTenHSX(), hsx.getMaSoThue(), hsx.getSdt(), hsx.getDiaChi());
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


    public void loadhsxInfoForUpdate(String maHSX) {
       
        HangSanXuatDTO hsx = bllhsx.getHSXbyID(maHSX);

        if (hsx != null) {
            
            txtMaHSX.setText(hsx.getMaHSX());
            txtTenHSX.setText(hsx.getTenHSX());
            txtMST.setText(hsx.getMaSoThue());
            txtSDT.setText(hsx.getSdt());
            txtDC.setText(hsx.getDiaChi());
            
               
        } else {
            JOptionPane.showMessageDialog(this, "Không tìm thấy hãng sản xuất!");
        }
    }

    public static void main(String[] args) {
        new CapnhatttHSX();
    }
}


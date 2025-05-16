package GUI;

import javax.swing.*;

import bll.NhanVienBLL;
import dal.NhanViendal;
import dto.NhanVienDTO;


import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Capnhatttnv extends BaseFrame {
	private NhanVienBLL bllnv = new NhanVienBLL();
	private NhanViendal nvDAL= new NhanViendal();
	private JTextField   txtTenNV, txtMaNV, txtCCCD, txtSDT, txtvitri, txtMST;
    public Capnhatttnv() {
    	super("Cập nhật thông tin nhân viên");
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
        // Tiêu đề "Sản phẩm >> Thông tin sản phẩm" có thể nhấn
        JLabel lblnvLink = new JLabel("<html><u>Nhân viên</u></html>");
        lblnvLink.setFont(new Font("Arial", Font.BOLD, 20));
        lblnvLink.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        lblnvLink.setBounds(270, 70, 100, 30);
        add(lblnvLink);

        JLabel lblTTnvLink = new JLabel("<html>>> <u>Thông tin nhân viên</u></html>");
        ;
        lblTTnvLink.setFont(new Font("Arial", Font.BOLD, 20));
        lblTTnvLink.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        lblTTnvLink.setBounds(370, 70, 240, 30);
        add(lblTTnvLink);

        JLabel lblArrow = new JLabel(" >> Cập nhật thông tin nhân viên");
        lblArrow.setFont(new Font("Arial", Font.BOLD, 20));
        lblArrow.setBounds(590, 70, 900, 30);
        add(lblArrow);

        lblnvLink.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                dispose();
                new NhanVien();
            }
        });

        lblTTnvLink.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                dispose();
                new TTCTnv();
            }
        });
      
        //form thêm nhân viên
        JLabel lblMaNV = new JLabel("Mã nhân viên:");
        lblMaNV.setBounds(270, 120, 150, 30);
        add(lblMaNV);
        txtMaNV = new JTextField();
        txtMaNV.setBounds(270, 150, 300, 30);
        txtMaNV.setBackground(new Color(230, 230, 230));
        txtMaNV.setEditable(false);
        txtMaNV.setFocusable(false);
        add(txtMaNV);
        //họ tên
        JLabel lblHoTen = new JLabel("Họ tên:");
        lblHoTen.setBounds(270, 200, 150, 30);
        add(lblHoTen);
        txtTenNV = new JTextField();
        txtTenNV.setBounds(270, 230, 300, 30);
        add(txtTenNV);
        //số điện thoại
        JLabel lblSDT = new JLabel("Số điện thoại:");
        lblSDT.setBounds(270, 280, 150, 30);
        add(lblSDT);
        txtSDT = new JTextField();
        txtSDT.setBounds(270, 310, 300, 30);
        add(txtSDT);
        //cccd
        JLabel lblCCCD = new JLabel("CCCD:");
        lblCCCD.setBounds(270, 360, 150, 30);
        add(lblCCCD);
        txtCCCD = new JTextField();
        txtCCCD.setBounds(270, 390, 300, 30);
        add(txtCCCD);
        //vị trí công việc
        JLabel lblVTCV = new JLabel("Vị trí công việc:");
        lblVTCV.setBounds(700, 120, 150, 30);
        add(lblVTCV);
        txtvitri = new JTextField();
        txtvitri.setBounds(700, 150, 300, 30);
        add(txtvitri);
        //mã số thuế
        JLabel lblMST = new JLabel("Mã số thuế:");
        lblMST.setBounds(700, 200, 150, 30);
        add(lblMST);
        txtMST = new JTextField();
        txtMST.setBounds(700, 230, 300, 30);
        add(txtMST);

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
                String maNV = txtMaNV.getText().trim();
                String tenNV = txtTenNV.getText().trim();
                String CCCD = txtCCCD.getText().trim();
                String SDT = txtSDT.getText().trim();
                String vitri = txtvitri.getText().trim();
                String MST = txtMST.getText().trim();
                                
                try {
                    // Tạo đối tượng loại sản phẩm cập nhật
                    NhanVienDTO nv = new NhanVienDTO();
                    nv.setMaNhanVien(maNV);
                    nv.setHoTen(tenNV);
                    nv.setCccd(CCCD);
                    nv.setSdt(SDT);
                    nv.setViTriCongViec(vitri);
                    nv.setMaSoThue(MST);
                   
                    System.out.println("MANV cần cập nhật: " + maNV);
                    
                    // Gọi hàm cập nhật từ BLL/DAL
                    boolean success = bllnv.updateNhanVien(nv);

                    if (success) {
                        JOptionPane.showMessageDialog(null, "Cập nhật nhân viên thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                        dispose(); // đóng form nếu muốn
                        TTCTnv ctnv = new TTCTnv();
                        ctnv.setThongTin(nv.getMaNhanVien(), nv.getHoTen(), nv.getCccd(), nv.getSdt(), nv.getViTriCongViec(), nv.getMaSoThue());
                    } else {
                        JOptionPane.showMessageDialog(null, "Cập nhật thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    }

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Lỗi dữ liệu: " + ex.getMessage());
                }
            }
        });
        getRootPane().setDefaultButton(btnLuusua);
        setVisible(true);
    }
    public void loadnvInfoForUpdate(String maNV) {
        // Lấy thông tin sản phẩm từ cơ sở dữ liệu (tương tự như trang chi tiết sản phẩm)
        NhanVienDTO nv = bllnv.getNhanVienTheoMa(maNV);

        if (nv != null) {
            // Điền thông tin vào các trường nhập liệu trong trang cập nhật thông tin sản phẩm
            txtMaNV.setText(nv.getMaNhanVien());
            txtTenNV.setText(nv.getHoTen());
            txtCCCD.setText(nv.getCccd());
            txtSDT.setText(nv.getSdt());
            txtvitri.setText(nv.getViTriCongViec());
            txtMST.setText(nv.getMaSoThue());
            
               
        } else {
            JOptionPane.showMessageDialog(this, "Không tìm thấy nhân viên!");
        }
    }

    public static void main(String[] args) {
        new Capnhatttnv();
    }

}

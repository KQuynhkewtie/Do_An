package GUI;

import bll.LoaiSPBLL;
import dal.loaispdal;
import dto.LoaiSPDTO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Capnhatttloaisp extends BaseFrame {
	private LoaiSPBLL blllsp = new LoaiSPBLL();
	private loaispdal lspDAL= new loaispdal();
	private JTextField  txtTenLSP, txtMaLSP;
    public Capnhatttloaisp() {
    	super("Cập nhật thông tin loại sản phẩm");
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

        JLabel lblTTLSPLink = new JLabel("<html>>> <u>Thông tin loại sản phẩm</u></html>");
        lblTTLSPLink.setFont(new Font("Arial", Font.BOLD, 20));
        lblTTLSPLink.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        lblTTLSPLink.setBounds(420, 70, 280, 30);
        add(lblTTLSPLink);

        JLabel lblArrow = new JLabel(" >> Cập nhật thông tin loại sản phẩm");
        lblArrow.setFont(new Font("Arial", Font.BOLD, 20));
        lblArrow.setBounds(680, 70, 900, 30);
        add(lblArrow);

        lblLoaiSanPhamLink.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                dispose();
                new LoaiSP();
            }
        });

        lblTTLSPLink.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                dispose();
                new TTCTloaisp();
            }
        });
        


        // Form nhập loại sản phẩm
        JLabel lblMaSP = new JLabel("Mã loại sản phẩm:");
        lblMaSP.setBounds(700, 120, 150, 25);
        add(lblMaSP);
        txtMaLSP = new JTextField();
        txtMaLSP.setBounds(700, 150, 300, 30);
        txtMaLSP.setBackground(new Color(230, 230, 230));
        txtMaLSP.setEditable(false);
        txtMaLSP.setFocusable(false);
        add(txtMaLSP);

        JLabel lblLoaiSP = new JLabel("Tên loại sản phẩm:");
        lblLoaiSP.setBounds(270, 120, 150, 25);
        add(lblLoaiSP);
        txtTenLSP = new JTextField();
        txtTenLSP.setBounds(270, 150, 300, 30);
        add(txtTenLSP);


        // Nút Lưu
        JButton btnLuusua = new JButton("Lưu");
        btnLuusua.setBounds(700, 200, 100, 40);
        btnLuusua.setBackground(Color.decode("#F0483E"));
        btnLuusua.setForeground(Color.WHITE);
        add(btnLuusua);

        btnLuusua.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	
                // Lấy dữ liệu từ các trường nhập
                String maLSP = txtMaLSP.getText().trim();
                String tenLSP = txtTenLSP.getText().trim();
                                
                try {
                    // Tạo đối tượng loại sản phẩm cập nhật
                    LoaiSPDTO lsp = new LoaiSPDTO();
                    lsp.setMaLSP(maLSP);
                    lsp.setTenLSP(tenLSP);
                   
                    System.out.println("MALSP cần cập nhật: " + maLSP);
                    
                    // Gọi hàm cập nhật từ BLL/DAL
                    boolean success = blllsp.updateLSP(lsp);

                    if (success) {
                        JOptionPane.showMessageDialog(null, "Cập nhật loại sản phẩm thành công!");
                        dispose(); // đóng form nếu muốn
                        TTCTloaisp ctlsp = new TTCTloaisp();
                        ctlsp.setThongTin(lsp.getTenLSP(), lsp.getMaLSP());
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
    public void loadlspInfoForUpdate(String maLSP) {
        // Lấy thông tin sản phẩm từ cơ sở dữ liệu (tương tự như trang chi tiết sản phẩm)
        LoaiSPDTO lsp = blllsp.getLSPById(maLSP);

        if (lsp != null) {
            // Điền thông tin vào các trường nhập liệu trong trang cập nhật thông tin sản phẩm
            txtTenLSP.setText(lsp.getTenLSP());
            txtMaLSP.setText(lsp.getMaLSP());
               
        } else {
            JOptionPane.showMessageDialog(this, "Không tìm thấy loại sản phẩm!");
        }
    }


    public static void main(String[] args) {
        new Capnhatttloaisp();
    }
}


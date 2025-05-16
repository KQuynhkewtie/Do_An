package GUI;

import javax.swing.*;

import bll.NhaCungUngBLL;
import dal.ncudal;
import dto.NhaCungUngDTO;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Capnhatttncu extends BaseFrame {
	private NhaCungUngBLL bllncu = new NhaCungUngBLL();
	private ncudal ncuDAL= new ncudal();
	private JTextField txtTenNCU, txtMaNCU, txtMSTHUE, txtDC, txtSDT, txtEMAIL;
    public Capnhatttncu() {
    	super("Cập nhật thông tin nhà cung ứng");
    	initialize();
    	} 
    @Override
   	protected void initUniqueComponents() {
   		 for (JButton btn : menuButtons) {
   	            if (btn.getText().equals("Nhà cung ứng")) {
   	                btn.setBackground(Color.decode("#EF5D7A")); 
   	                btn.setFont(new Font("Arial", Font.BOLD, 14)); 
   	            }
   	        }
   	        
   	        // Các nút khác vẫn giữ màu mặc định
   	        for (JButton btn : menuButtons) {
   	            if (!btn.getText().equals("Nhà cung ứng")) {
   	                btn.setBackground(Color.decode("#641A1F")); // Màu mặc định cho các nút khác
   	                btn.setFont(new Font("Arial", Font.BOLD, 12));
   	            }
   	        }
        JLabel lblncuLink = new JLabel("<html><u>Nhà cung ứng</u></html>");
        lblncuLink.setFont(new Font("Arial", Font.BOLD, 20));
        lblncuLink.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        lblncuLink.setBounds(270, 70, 160, 30);
        add(lblncuLink);

        JLabel lblTTncuLink = new JLabel("<html>>> <u>Thông tin nhà cung ứng</u></html>");
        lblTTncuLink.setFont(new Font("Arial", Font.BOLD, 20));
        lblTTncuLink.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        lblTTncuLink.setBounds(410, 70, 280, 30);
        add(lblTTncuLink);

        JLabel lblArrow = new JLabel(" >> Cập nhật thông tin nhà cung ứng");
        lblArrow.setFont(new Font("Arial", Font.BOLD, 20));
        lblArrow.setBounds(670, 70, 900, 30);
        add(lblArrow);

        lblncuLink.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                dispose();
                new Nhacungung();
            }
        });

        lblTTncuLink.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                dispose();
                new TTCTncu();
            }
        });
        
        
        JLabel lblMaNCU = new JLabel("Mã nhà cung ứng:");
        lblMaNCU.setBounds(270, 120, 150, 30);
        add(lblMaNCU);
        txtMaNCU = new JTextField();
        txtMaNCU.setBounds(270, 150, 300, 30);
        txtMaNCU.setBackground(new Color(230, 230, 230));
        txtMaNCU.setEditable(false);
        txtMaNCU.setFocusable(false);
        add(txtMaNCU);
        
        JLabel lblTen = new JLabel("Tên nhà cung ứng:");
        lblTen.setBounds(270, 200, 150, 30);
        add(lblTen);
        txtTenNCU = new JTextField();
        txtTenNCU.setBounds(270, 230, 300, 30);
        add(txtTenNCU);
        
        JLabel lblMST = new JLabel("Mã số thuế:");
        lblMST.setBounds(270, 280, 150, 30);
        add(lblMST);
        txtMSTHUE = new JTextField();
        txtMSTHUE.setBounds(270, 310, 300, 30);
        add(txtMSTHUE);
        
        JLabel lblDC = new JLabel("Địa chỉ:");
        lblDC.setBounds(700, 280, 150, 30);
        add(lblDC);
        txtDC = new JTextField();
        txtDC.setBounds(700, 310, 300, 30);
        add(txtDC);
      
        JLabel lblSDT = new JLabel("Số điện thoại:");
        lblSDT.setBounds(700, 120, 150, 30);
        add(lblSDT);
        txtSDT = new JTextField();
        txtSDT.setBounds(700, 150, 300, 30);
        add(txtSDT);
        
        JLabel lblEmail = new JLabel("Email:");
        lblEmail.setBounds(700, 200, 150, 30);
        add(lblEmail);
        txtEMAIL = new JTextField();
        txtEMAIL.setBounds(700, 230, 300, 30);
        add(txtEMAIL);

        // Nút Lưu
        JButton btnLuusua = new JButton("Lưu");
        btnLuusua.setBounds(700, 360, 100, 40);
        btnLuusua.setBackground(Color.decode("#F0483E"));
        btnLuusua.setForeground(Color.WHITE);
        add(btnLuusua);

        btnLuusua.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	
                // Lấy dữ liệu từ các trường nhập
                String maNCU = txtMaNCU.getText().trim();
                String tenNCU = txtTenNCU.getText().trim();
                String MST = txtMSTHUE.getText().trim();
                String DC = txtDC.getText().trim();
                String SDT = txtSDT.getText().trim();
                String EMAIL = txtEMAIL.getText().trim();
                                
                try {
                    // Tạo đối tượng loại sản phẩm cập nhật
                    NhaCungUngDTO ncu = new NhaCungUngDTO();
                    ncu.setMaNCU(maNCU);
                    ncu.setTenNCU(tenNCU);
                    ncu.setMaSoThue(MST);
                    ncu.setDiaChi(DC);
                    ncu.setSdt(SDT);
                    ncu.setEmail(EMAIL);
                    System.out.println("MANCU cần cập nhật: " + maNCU);
                    
                    // Gọi hàm cập nhật từ BLL/DAL
                    boolean success = bllncu.updateNCU(ncu);

                    if (success) {
                        JOptionPane.showMessageDialog(null, "Cập nhật nhà cung ứng thành công!");
                        dispose(); // đóng form nếu muốn
                        TTCTncu ctncu = new TTCTncu();
                        ctncu.setThongTin(ncu.getTenNCU(), ncu.getMaNCU(), ncu.getMaSoThue(), ncu.getDiaChi(), ncu.getSdt(), ncu.getEmail());
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
    public void loadncuInfoForUpdate(String maNCU) {
        // Lấy thông tin sản phẩm từ cơ sở dữ liệu (tương tự như trang chi tiết sản phẩm)
        NhaCungUngDTO ncu = bllncu.getNCUById(maNCU);

        if (ncu != null) {
            // Điền thông tin vào các trường nhập liệu trong trang cập nhật thông tin sản phẩm
            txtTenNCU.setText(ncu.getTenNCU());
            txtMaNCU.setText(ncu.getMaNCU());
            txtMSTHUE.setText(ncu.getMaSoThue());
            txtDC.setText(ncu.getDiaChi());
            txtSDT.setText(ncu.getSdt());
            txtEMAIL.setText(ncu.getEmail());
               
        } else {
            JOptionPane.showMessageDialog(this, "Không tìm thấy nhà cung ứng!");
        }
    }
    public static void main(String[] args) {
        new Capnhatttncu();
    }

}

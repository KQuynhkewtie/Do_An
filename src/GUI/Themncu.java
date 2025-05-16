package GUI;

import javax.swing.*;


import dal.ncudal;
import dto.NhaCungUngDTO;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Themncu extends BaseFrame {
	private ncudal ncuDAL= new ncudal();
    public Themncu() {
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

        JLabel lblArrow = new JLabel(" >> Thêm nhà cung ứng");
        lblArrow.setFont(new Font("Arial", Font.BOLD, 20));
        lblArrow.setBounds(410, 70, 300, 30);
        add(lblArrow);

        lblncuLink.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                dispose();
                new Nhacungung();
            }
        });

       
        JLabel lblMaNCU = new JLabel("Mã nhà cung ứng:");
        lblMaNCU.setBounds(270, 120, 150, 30);
        add(lblMaNCU);
        JTextField txtMaNCU = new JTextField();
        txtMaNCU.setBounds(270, 150, 300, 30);
        add(txtMaNCU);
        
        JLabel lblTen = new JLabel("Tên nhà cung ứng:");
        lblTen.setBounds(270, 200, 150, 30);
        add(lblTen);
        JTextField txtTen = new JTextField();
        txtTen.setBounds(270, 230, 300, 30);
        add(txtTen);
        
        JLabel lblMST = new JLabel("Mã số thuế:");
        lblMST.setBounds(270, 280, 150, 30);
        add(lblMST);
        JTextField txtMST = new JTextField();
        txtMST.setBounds(270, 310, 300, 30);
        add(txtMST);
        
        JLabel lblDC = new JLabel("Địa chỉ:");
        lblDC.setBounds(700, 280, 150, 30);
        add(lblDC);
        JTextField txtDC = new JTextField();
        txtDC.setBounds(700, 310, 300, 30);
        add(txtDC);
      
        JLabel lblSDT = new JLabel("Số điện thoại:");
        lblSDT.setBounds(700, 120, 150, 30);
        add(lblSDT);
        JTextField txtSDT = new JTextField();
        txtSDT.setBounds(700, 150, 300, 30);
        add(txtSDT);
        
        JLabel lblEmail = new JLabel("Email:");
        lblEmail.setBounds(700, 200, 150, 30);
        add(lblEmail);
        JTextField txtEmail = new JTextField();
        txtEmail.setBounds(700, 230, 300, 30);
        add(txtEmail);
        //nút lưu 
        JButton btnLuu = new JButton("Lưu");
        btnLuu.setBounds(700, 360, 100, 30);
        btnLuu.setBackground(Color.decode("#F0483E"));
        btnLuu.setForeground(Color.WHITE);
        add(btnLuu);

        btnLuu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String maNCU = txtMaNCU.getText().trim();
                    String tenNCU = txtTen.getText().trim();
                    String MST = txtMST.getText().trim();
                    String DC = txtDC.getText().trim();
                    String SDT = txtSDT.getText().trim();
                    String Email = txtEmail.getText().trim();
                   
                   

                    // Tạo đối tượng sản phẩm
                    NhaCungUngDTO ncu= new NhaCungUngDTO(maNCU, tenNCU, MST, DC, SDT, Email);
                    
                    System.out.println("Mã ncu lấy từ giao diện: [" + maNCU + "]");

                    // Gọi phương thức thêm sản phẩm vào DB
                    boolean result = ncuDAL.insertncu(ncu);

                    // Hiển thị thông báo
                    if (result) {
                        JOptionPane.showMessageDialog(null, "Thêm nhà cung ứng thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                       
                    } else {
                        JOptionPane.showMessageDialog(null, "Thêm nhà cung ứng thất bại! Kiểm tra dữ liệu." , "Lỗi", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Vui lòng nhập đúng định dạng!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        getRootPane().setDefaultButton(btnLuu);
        setVisible(true);
        
        JTextField[] textFields = {txtMaNCU, txtTen, txtMST, txtSDT, txtEmail,txtDC};

        for (int i = 0; i < textFields.length; i++) {
            final int currentIndex = i;
            textFields[i].addKeyListener(new KeyAdapter() {
                @Override
                public void keyPressed(KeyEvent e) {
                    int key = e.getKeyCode();
                    if (key == KeyEvent.VK_DOWN) {
                        // Mũi tên xuống - chuyển đến trường nhập liệu tiếp theo
                        if (currentIndex < textFields.length - 1) {
                            textFields[currentIndex + 1].requestFocus();
                        }
                    } else if (key == KeyEvent.VK_UP) {
                        // Mũi tên lên - chuyển đến trường nhập liệu trước đó
                        if (currentIndex > 0) {
                            textFields[currentIndex - 1].requestFocus();
                        }
                    }
                }
            });
        }
    }
    public static void main(String[] args) {
       new Themncu();
    }
}

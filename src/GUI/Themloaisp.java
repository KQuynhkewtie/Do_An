package GUI;
import javax.swing.*;

import dal.loaispdal;
import dto.LoaiSPDTO;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Themloaisp extends BaseFrame {
	private loaispdal lspDAL= new loaispdal();
    
    public Themloaisp() {
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
    	
        JLabel lblLoaiSanPhamLink = new JLabel("<html><u>Loại sản phẩm</u></html>");
        lblLoaiSanPhamLink.setFont(new Font("Arial", Font.BOLD, 20));
        lblLoaiSanPhamLink.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        lblLoaiSanPhamLink.setBounds(270, 70, 160, 30);
        add(lblLoaiSanPhamLink);

        JLabel lblArrow = new JLabel(" >> Thêm loại sản phẩm");
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

        JLabel lblMaSP = new JLabel("Mã loại sản phẩm:");
        lblMaSP.setBounds(700, 120, 150, 25);
        add(lblMaSP);
        JTextField txtMaLSP = new JTextField();
        txtMaLSP.setBounds(700, 150, 300, 30);
        add(txtMaLSP);

        JLabel lblLoaiSP = new JLabel("Tên loại sản phẩm:");
        lblLoaiSP.setBounds(270, 120, 150, 25);
        add(lblLoaiSP);
        JTextField txtTenLSP = new JTextField();;
        txtTenLSP.setBounds(270, 150, 300, 30);
        add(txtTenLSP);

        // Nút Lưu
        JButton btnLuu = new JButton("Lưu");
        btnLuu.setBounds(700, 200, 100, 40);
        btnLuu.setBackground(Color.decode("#F0483E"));
        btnLuu.setForeground(Color.WHITE);
        add(btnLuu);
        btnLuu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String maLSP = txtMaLSP.getText().trim();
                    String tenLSP = txtTenLSP.getText().trim();
                   

                    // Tạo đối tượng sản phẩm
                    LoaiSPDTO lsp = new LoaiSPDTO(maLSP, tenLSP);
                    
                    System.out.println("Mã loại sản phẩm lấy từ giao diện: [" + maLSP + "]");

                    // Gọi phương thức thêm sản phẩm vào DB
                    boolean result = lspDAL.insertLSP(lsp);

                    // Hiển thị thông báo
                    if (result) {
                        JOptionPane.showMessageDialog(null, "Thêm loại sản phẩm thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                       
                    } else {
                        JOptionPane.showMessageDialog(null, "Thêm loại sản phẩm thất bại! Kiểm tra dữ liệu." , "Lỗi", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Vui lòng nhập đúng định dạng!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        getRootPane().setDefaultButton(btnLuu);
        setVisible(true);
        
        JTextField[] textFields = {txtTenLSP, txtMaLSP};

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
        new Themloaisp();
    }
}


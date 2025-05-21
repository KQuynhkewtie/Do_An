package GUI;


import javax.swing.*;

import dal.khachhangdal;
import dto.KhachHangDTO;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Themkh extends BaseFrame {
	private khachhangdal khDAL= new khachhangdal();
    public Themkh() {super("Cập nhật thông tin khách hàng");
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
        JLabel lblSanPhamLink = new JLabel("<html><u>Khách Hàng</u></html>");
        lblSanPhamLink.setFont(new Font("Arial", Font.BOLD, 20));
        lblSanPhamLink.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        lblSanPhamLink.setBounds(270, 70, 400, 30);
        add(lblSanPhamLink);

        JLabel lblArrow = new JLabel(" >> Thêm khách hàng");
        lblArrow.setFont(new Font("Arial", Font.BOLD, 20));
        lblArrow.setBounds(390, 70, 400, 30);
        add(lblArrow);

        lblSanPhamLink.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                dispose();
                new KhachHang();
            }
        });

        //form thêm khách hàng
        JLabel lblMaKH = new JLabel("Mã khách hàng:");
        lblMaKH.setBounds(270, 120, 150, 30);
        add(lblMaKH);
        JTextField txtMaKH = new JTextField();
        txtMaKH.setBounds(450, 120, 200, 30);
        add(txtMaKH);
        //họ tên 
        JLabel lblHoTen = new JLabel("Họ tên:");
        lblHoTen.setBounds(270, 170, 150, 30);
        add(lblHoTen);
        JTextField txtHoTen = new JTextField();
        txtHoTen.setBounds(450, 170, 200, 30);
        add(txtHoTen);
        //số đt
        JLabel lblSoLanMua = new JLabel("Số điện thoại:");
        lblSoLanMua.setBounds(270, 220, 150, 30);
        add(lblSoLanMua);
        JTextField txtSDT = new JTextField();
        txtSDT.setBounds(450, 220, 200, 30);
        add(txtSDT);
        //điểm tích lũy
        JLabel lblDiemTL = new JLabel("Điểm tích lũy:");
        lblDiemTL.setBounds(270, 270, 150, 30);
        add(lblDiemTL);
        JTextField txtDiemTL = new JTextField();
        txtDiemTL.setBounds(450, 270, 200, 30);
        add(txtDiemTL);
        //mã loại khách hàng
        JLabel lblMaLoaiKH = new JLabel("Mã loại khách hàng:");
        lblMaLoaiKH.setBounds(270, 320, 150, 30);
        add(lblMaLoaiKH);
        JTextField txtMaLoaiKH = new JTextField();
        txtMaLoaiKH.setBounds(450, 320, 200, 30);
        add(txtMaLoaiKH);
        //nút lưu 
        JButton btnLuu = new JButton("Lưu");
        btnLuu.setBounds(450, 370, 100, 30);
        btnLuu.setBackground(Color.decode("#F0483E"));
        btnLuu.setForeground(Color.WHITE);
        add(btnLuu);

        btnLuu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                	 String maKH = txtMaKH.getText().trim();
                     String tenKH = txtHoTen.getText().trim();
                     double DTL = Double.parseDouble(txtDiemTL.getText().trim());
                     String Loai = txtMaLoaiKH.getText().trim();
                     String SDT = txtSDT.getText().trim();



                    // Tạo đối tượng sản phẩm
                     KhachHangDTO kh = new KhachHangDTO(maKH, tenKH, DTL, Loai, SDT);
                    
                    System.out.println("Mã khách hàng lấy từ giao diện: [" + maKH + "]");

                    // Gọi phương thức thêm sản phẩm vào DB
                    boolean result = khDAL.insertKhachHang(kh);

                    // Hiển thị thông báo
                    if (result) {
                        JOptionPane.showMessageDialog(null, "Thêm khách hàng thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                       
                    } else {
                        JOptionPane.showMessageDialog(null, "Thêm khách hàng thất bại! Kiểm tra dữ liệu." , "Lỗi", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Vui lòng nhập đúng định dạng!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        getRootPane().setDefaultButton(btnLuu);
        setVisible(true);
        
        JTextField[] textFields = { txtMaKH,txtHoTen, txtDiemTL, txtMaLoaiKH, txtSDT};

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
       new Themkh();
    }
}

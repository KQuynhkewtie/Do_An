package GUI;

import javax.swing.*;

import dal.NhanViendal;
import dto.NhanVienDTO;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Themnv extends BaseFrame {
	private NhanViendal nvDAL = new NhanViendal();
    public Themnv() {
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
        JLabel lblnhanvienLink = new JLabel("<html><u>Nhân viên</u></html>");
        lblnhanvienLink.setFont(new Font("Arial", Font.BOLD, 20));
        lblnhanvienLink.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        lblnhanvienLink.setBounds(270, 70, 100, 30);
        add(lblnhanvienLink);

        JLabel lblArrow = new JLabel(" >> Thêm nhân viên");
        lblArrow.setFont(new Font("Arial", Font.BOLD, 20));
        lblArrow.setBounds(370, 70, 400, 30);
        add(lblArrow);

        lblnhanvienLink.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                dispose();
                new NhanVien();
            }
        });

        //form thêm nhân viên
        JLabel lblMaNV = new JLabel("Mã nhân viên:");
        lblMaNV.setBounds(270, 120, 150, 30);
        add(lblMaNV);
        JTextField txtMaNV = new JTextField();
        txtMaNV.setBounds(270, 150, 300, 30);
        add(txtMaNV);
        //họ tên
        JLabel lblHoTen = new JLabel("Họ tên:");
        lblHoTen.setBounds(270, 200, 150, 30);
        add(lblHoTen);
        JTextField txtHoTen = new JTextField();
        txtHoTen.setBounds(270, 230, 300, 30);
        add(txtHoTen);
        //số điện thoại
        JLabel lblSDT = new JLabel("Số điện thoại:");
        lblSDT.setBounds(270, 280, 150, 30);
        add(lblSDT);
        JTextField txtSDT = new JTextField();
        txtSDT.setBounds(270, 310, 300, 30);
        add(txtSDT);
        //cccd
        JLabel lblCCCD = new JLabel("CCCD:");
        lblCCCD.setBounds(270, 360, 150, 30);
        add(lblCCCD);
        JTextField txtCCCD = new JTextField();
        txtCCCD.setBounds(270, 390, 300, 30);
        add(txtCCCD);
        //vị trí công việc
        JLabel lblVTCV = new JLabel("Vị trí công việc:");
        lblVTCV.setBounds(700, 120, 150, 30);
        add(lblVTCV);
        JTextField txtVTCV = new JTextField();
        txtVTCV.setBounds(700, 150, 300, 30);
        add(txtVTCV);
        //mã số thuế
        JLabel lblMST = new JLabel("Mã số thuế:");
        lblMST.setBounds(700, 200, 150, 30);
        add(lblMST);
        JTextField txtMST = new JTextField();
        txtMST.setBounds(700, 230, 300, 30);
        add(txtMST);
        //nút lưu 
        JButton btnLuu = new JButton("Lưu");
        btnLuu.setBounds(700, 280, 100, 30);
        btnLuu.setBackground(Color.decode("#F0483E"));
        btnLuu.setForeground(Color.WHITE);
        add(btnLuu);

        btnLuu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                	 String maNV = txtMaNV.getText().trim();
                     String tenNV = txtHoTen.getText().trim();
                     String CCCD = txtCCCD.getText().trim();
                     String SDT = txtSDT.getText().trim();
                     String vitri = txtVTCV.getText().trim();
                     String MST = txtMST.getText().trim();
                   

                    // Tạo đối tượng sản phẩm
                    NhanVienDTO nv = new NhanVienDTO(maNV, tenNV, CCCD, SDT, vitri, MST);
                    
                    System.out.println("Mã nhân viên lấy từ giao diện: [" + maNV + "]");

                    // Gọi phương thức thêm sản phẩm vào DB
                    boolean result = nvDAL.insertNhanVien(nv);

                    // Hiển thị thông báo
                    if (result) {
                        JOptionPane.showMessageDialog(null, "Thêm nhân viên thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                       
                    } else {
                        JOptionPane.showMessageDialog(null, "Thêm nhân viên thất bại! Kiểm tra dữ liệu." , "Lỗi", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Vui lòng nhập đúng định dạng!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        getRootPane().setDefaultButton(btnLuu);
        setVisible(true);
        JTextField[] textFields = { txtMaNV, txtHoTen,txtSDT, txtCCCD, txtVTCV, txtMST};

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
       new Themnv();
    }
}

package GUI;


import javax.swing.*;
import javax.swing.border.LineBorder;

import bll.HoaDonBLL;

import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;



public class DoanhThu extends BaseFrame {
	
	private JTextField dayField;
	private JLabel lblDoanhThu;
	private JLabel lblThoiGian; // Thêm label để hiển thị thời gian thống kê
	private HoaDonBLL hdBLL = new HoaDonBLL();
	
    public DoanhThu() {
    	super("Doanhthu");
        initialize();
       } 
        	
        	 @Override
        	protected void initUniqueComponents() {
        		 for (JButton btn : menuButtons) {
        	            if (btn.getText().equals("Doanh thu")) {
        	                btn.setBackground(Color.decode("#EF5D7A")); 
        	                btn.setFont(new Font("Arial", Font.BOLD, 14)); 
        	            }
        	        }
        	        
        	        // Các nút khác vẫn giữ màu mặc định
        	        for (JButton btn : menuButtons) {
        	            if (!btn.getText().equals("Doanh thu")) {
        	                btn.setBackground(Color.decode("#641A1F")); // Màu mặc định cho các nút khác
        	                btn.setFont(new Font("Arial", Font.BOLD, 12));
        	            }
        	        }
            
        // Tiêu đề
        JLabel lblTitle = new JLabel("Doanh thu");
        lblTitle.setFont(new Font("Arial", Font.BOLD, 22));
        lblTitle.setBounds(270, 70, 200, 30);
        add(lblTitle);

        JButton searchDthuButton = new JButton("Tìm");
        searchDthuButton.setBounds(680, 150, 100, 35);
        add(searchDthuButton);

       
        JLabel lblNTN = new JLabel("Nhập ngày/tháng/năm:");
        lblNTN.setBounds(270, 120, 150, 25);
        add(lblNTN);


        // Thêm RadioButton để chọn loại tìm kiếm
        JRadioButton rbNgay = new JRadioButton("Theo ngày");
        JRadioButton rbThang = new JRadioButton("Theo tháng");
        JRadioButton rbNam = new JRadioButton("Theo năm");
        
        rbNgay.setBounds(270, 190, 100, 25);
        rbThang.setBounds(370, 190, 100, 25);
        rbNam.setBounds(470, 190, 100, 25);
        
        ButtonGroup group = new ButtonGroup();
        group.add(rbNgay);
        group.add(rbThang);
        group.add(rbNam);
        rbNgay.setSelected(true);
        
        add(rbNgay);
        add(rbThang);
        add(rbNam);
        
        // Thêm sự kiện thay đổi placeholder khi chọn loại tìm kiếm
        rbNgay.addActionListener(e -> {
            dayField.setText("DD/MM/YYYY");
            dayField.setForeground(Color.GRAY);
        });
        
        rbThang.addActionListener(e -> {
            dayField.setText("MM/YYYY");
            dayField.setForeground(Color.GRAY);
        });
        
        rbNam.addActionListener(e -> {
            dayField.setText("YYYY");
            dayField.setForeground(Color.GRAY);
        });


        // Sửa lại phần khởi tạo dayField
        this.dayField = new JTextField("DD/MM/YYYY");
        dayField.setBounds(270, 150, 400, 35);
        dayField.setForeground(Color.GRAY);
        add(dayField);

        // Sửa lại sự kiện focus
        dayField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (dayField.getText().matches("^(DD/MM/YYYY|MM/YYYY|YYYY)$")) {
                    dayField.setText("");
                    dayField.setForeground(Color.BLACK);
                }
            }
            

            @Override
            public void focusLost(FocusEvent e) {
                if (dayField.getText().trim().isEmpty()) {
                    // Giữ nguyên placeholder theo loại đang chọn
                    if (rbNgay.isSelected()) {
                        dayField.setText("DD/MM/YYYY");
                    } else if (rbThang.isSelected()) {
                        dayField.setText("MM/YYYY");
                    } else {
                        dayField.setText("YYYY");
                    }
                    dayField.setForeground(Color.GRAY);
                }
            }
        });
        dayField.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    searchDthuButton.doClick(); // Mô phỏng nhấn nút tìm khi nhấn Enter
                }
            }
        });


        // Thay đổi sự kiện cho nút tìm kiếm
        searchDthuButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String input = dayField.getText().trim();
                String placeholder = rbNgay.isSelected() ? "DD/MM/YYYY" : 
                                   rbThang.isSelected() ? "MM/YYYY" : "YYYY";
                
                if (input.equals(placeholder) || input.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Vui lòng nhập " + placeholder.toLowerCase(), 
                            "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                try {
                    double doanhThu = 0;
                    String thoiGianThongKe = "";
                    
                    if (rbNgay.isSelected()) {
                        // Kiểm tra định dạng ngày
                        if (!input.matches("\\d{2}/\\d{2}/\\d{4}")) {
                            throw new ParseException("Invalid date format", 0);
                        }
                        
                        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                        sdf.setLenient(false); // Kiểm tra ngày hợp lệ
                        Date ngay = sdf.parse(input);
                        doanhThu = hdBLL.tinhDoanhThuTheoNgay(ngay);
                        thoiGianThongKe = "Ngày " + input;
                    } 
                    else if (rbThang.isSelected()) {
                        if (!input.matches("\\d{2}/\\d{4}")) {
                            throw new ParseException("Invalid month format", 0);
                        }
                        
                        String[] parts = input.split("/");
                        int thang = Integer.parseInt(parts[0]);
                        int nam = Integer.parseInt(parts[1]);
                        
                        if (thang < 1 || thang > 12) {
                            throw new ParseException("Tháng phải từ 1-12", 0);
                        }
                        
                        doanhThu = hdBLL.tinhDoanhThuTheoThang(thang, nam);
                        thoiGianThongKe = "Tháng " + input;
                    } 
                    else { // Theo năm
                        if (!input.matches("\\d{4}")) {
                            throw new ParseException("Invalid year format", 0);
                        }
                        
                        int nam = Integer.parseInt(input);
                        doanhThu = hdBLL.tinhDoanhThuTheoNam(nam);
                        thoiGianThongKe = "Năm " + input;
                    }
                    
                    // Cập nhật giao diện
                    lblThoiGian.setText(thoiGianThongKe);
                    lblDoanhThu.setText(hdBLL.formatDoanhThu(doanhThu));
                    
                } catch (ParseException ex) {
                    JOptionPane.showMessageDialog(null, "Định dạng không hợp lệ. Vui lòng nhập đúng theo yêu cầu", 
                            "Lỗi", JOptionPane.ERROR_MESSAGE);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Giá trị nhập vào phải là số", 
                            "Lỗi", JOptionPane.ERROR_MESSAGE);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Lỗi khi thực hiện tìm kiếm: " + ex.getMessage(), 
                            "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Bảng doanh thu
        JPanel doanhThuPanel = new JPanel();
        doanhThuPanel.setBounds(270, 250, 350, 300);
        doanhThuPanel.setBackground(Color.WHITE);
        doanhThuPanel.setBorder(new LineBorder(Color.decode("#FED600"), 12));
        doanhThuPanel.setLayout(null); // Tắt layout manager để tự chỉnh vị trí
        add(doanhThuPanel);

        JLabel lblTongdthu = new JLabel("Tổng doanh thu", SwingConstants.CENTER);
        lblTongdthu.setFont(new Font("Arial", Font.BOLD, 24));
        lblTongdthu.setBounds(70, 70, 200, 30);
        doanhThuPanel.add(lblTongdthu);
        
        // Thêm label hiển thị thời gian thống kê
        lblThoiGian = new JLabel("", SwingConstants.CENTER);
        lblThoiGian.setFont(new Font("Arial", Font.PLAIN, 14));
        lblThoiGian.setBounds(50, 100, 250, 25);
        doanhThuPanel.add(lblThoiGian);

        lblDoanhThu = new JLabel("0 VNĐ", SwingConstants.CENTER);
        lblDoanhThu.setFont(new Font("Arial", Font.BOLD, 20));
        lblDoanhThu.setForeground(Color.decode("#FED600"));
        lblDoanhThu.setBounds(50, 145, 250, 30);
        doanhThuPanel.add(lblDoanhThu);

        // //hình ảnh 
        ImageIcon icon = new ImageIcon(getClass().getClassLoader().getResource("image/money_icon.jpg"));
        JLabel moneyImage = new JLabel(icon);
        moneyImage.setBounds(500, 250, 700, 400);
        add(moneyImage);

        setVisible(true);
    }


    public static void main(String[] args) {
        new DoanhThu();
    }
}

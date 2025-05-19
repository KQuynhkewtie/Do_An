package GUI;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.*;

public class TabDoanhThu extends JPanel {
    private JTextField dayField;
	private JLabel lblDoanhThu;
	private JLabel lblThoiGian;
    public TabDoanhThu() {
        setLayout(null);
       
        JLabel lblNTN = new JLabel("Nhập ngày/tháng/năm:");
        lblNTN.setBounds(10, 20, 150, 25);
        add(lblNTN);


        // Thêm RadioButton để chọn loại tìm kiếm
        JRadioButton rbNgay = new JRadioButton("Theo ngày");
        JRadioButton rbThang = new JRadioButton("Theo tháng");
        JRadioButton rbNam = new JRadioButton("Theo năm");
        
        rbNgay.setBounds(10, 40, 100, 25);
        rbThang.setBounds(110, 40, 100, 25);
        rbNam.setBounds(210, 40, 100, 25);
        
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


        // thanh tìm kiếm
        this.dayField = new JTextField("DD/MM/YYYY");
        dayField.setBounds(10, 70, 400, 35);
        dayField.setForeground(Color.GRAY);
        add(dayField);

        
        JButton searchDthuButton = new JButton("Tìm");
        searchDthuButton.setBounds(450, 70, 100, 35);
        add(searchDthuButton);

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


        // Bảng doanh thu
        JPanel doanhThuPanel = new JPanel();
        doanhThuPanel.setBounds(200, 180, 350, 300);
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

        
        setVisible(true);
    }
}
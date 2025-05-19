// package GUI;

// import javax.swing.*;
// import javax.swing.border.LineBorder;

// import java.awt.*;
// import java.awt.event.FocusEvent;
// import java.awt.event.FocusListener;
// import java.awt.event.ActionEvent;
// import java.awt.event.ActionListener;

// public class ThongKe extends JFrame {
//     public ThongKe() {
//         setTitle("Doanh thu");
//         setSize(1100, 700);
//         setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//         setLocationRelativeTo(null);
//         setLayout(null);

//         // Sidebar (Menu trái)
//         JPanel sidebar = new JPanel();
//         sidebar.setBounds(0, 0, 250, 700);
//         sidebar.setBackground(Color.decode("#AB282C"));
//         sidebar.setLayout(null);
//         add(sidebar);

//         JPanel side = new JPanel();
//         side.setBounds(0, 0, 1100, 50);
//         side.setBackground(Color.decode("#AB282C"));
//         side.setLayout(null);
//         add(side);

//         JLabel logo = new JLabel("Pharmacy", SwingConstants.CENTER);
//         logo.setForeground(Color.WHITE);
//         logo.setFont(new Font("Arial", Font.BOLD, 20));
//         logo.setBounds(0, 10, 250, 40);
//         sidebar.add(logo);

//         String[] menuItems = {"Trang chủ", "Sản phẩm", "Loại sản phẩm", "Nhân viên", "Khách hàng", "Phiếu nhập hàng", "Hóa đơn", "Doanh thu", "Hãng sản xuất", "Nhà cung ứng"};

//         int sidebarHeight = 700; // Chiều cao của sidebar
//         int buttonHeight = 40; // Chiều cao của mỗi nút
//         int numberOfButtons = menuItems.length;

//         // Tính khoảng cách đều giữa các nút
//         int spacing = (sidebarHeight - (buttonHeight * numberOfButtons) - 50) / (numberOfButtons + 4);

//         int yOffset = 50 + spacing; // Điểm bắt đầu cho nút đầu tiên
//         for (String item : menuItems) {
//         JButton btn = new JButton(item);
//         btn.setBounds(20, yOffset, 210, buttonHeight);
//         btn.setBackground(Color.decode("#641A1F"));
//         btn.setForeground(Color.WHITE);
//         btn.setFocusPainted(false);
//         sidebar.add(btn);

//         if (item.equals("Trang chủ")) {
//             btn.addActionListener(new ActionListener() {
//                 @Override
//                 public void actionPerformed(ActionEvent e) {
//                     dispose(); 
//                     new HomePage(); 
//                 }
//             });
//         }

//         if (item.equals("Sản phẩm")) {
//             btn.addActionListener(new ActionListener() {
//                 @Override
//                 public void actionPerformed(ActionEvent e) {
//                     dispose(); 
//                     new Sanpham(); 
//                 }
//             });
//         }

//         if (item.equals("Khách hàng")) {
//             btn.addActionListener(new ActionListener() {
//                 @Override
//                 public void actionPerformed(ActionEvent e) {
//                     dispose(); 
//                     new Khachhang(); 
//                 }
//             });
//         }

//         if (item.equals("Nhân viên")) {
//             btn.addActionListener(new ActionListener() {
//                 @Override
//                 public void actionPerformed(ActionEvent e) {
//                     dispose(); 
//                     new Nhanvien(); 
//                 }
//             });
//         }
//         yOffset += buttonHeight + spacing; // Cập nhật vị trí Y để dàn đều
//         }

//         // Chỉnh màu ô "Doanh thu" trong sidebar
//         for (Component component : sidebar.getComponents()) {
//             if (component instanceof JButton) {
//                 JButton button = (JButton) component; 
//             if (button.getText().equals("Doanh thu")) {
//                 button.setBackground(Color.decode("#EF5D7A"));
//                 button.setForeground(Color.WHITE);
//                 button.setFont(new Font("Arial", Font.BOLD, 14));
//             }
//             }
//         }

//         // Tiêu đề
//         JLabel lblTitle = new JLabel("Doanh thu");
//         lblTitle.setFont(new Font("Arial", Font.BOLD, 20));
//         lblTitle.setBounds(270, 70, 200, 30);
//         add(lblTitle);

//         JButton searchDthuButton = new JButton("Tìm");
//         searchDthuButton.setBounds(680, 150, 100, 35);
//         add(searchDthuButton);

//         JLabel lblNTN = new JLabel("Nhập ngày/tháng/năm:");
//         lblNTN.setBounds(270, 120, 150, 25);
//         add(lblNTN);

//         JTextField dayField = new JTextField("DD/MM/YY");
//         dayField.setBounds(270, 150, 400, 35);
//         add(dayField);

//         // Thêm sự kiện focus
//         dayField.addFocusListener(new FocusListener() {
//             @Override
//             public void focusGained(FocusEvent e) {
//                 if (dayField.getText().equals("DD/MM/YY")) {
//                     dayField.setText("");
//                     dayField.setForeground(Color.BLACK);
//                 }
//             }

//             @Override
//             public void focusLost(FocusEvent e) {
//                 if (dayField.getText().trim().isEmpty()) {
//                     dayField.setText("DD/MM/YY");
//                     dayField.setForeground(Color.GRAY);
//                 }
//             }
//         });

//         // Bảng doanh thu
//         JPanel doanhThuPanel = new JPanel();
//         doanhThuPanel.setBounds(270, 250, 350, 300);
//         doanhThuPanel.setBackground(Color.WHITE);
//         doanhThuPanel.setBorder(new LineBorder(Color.decode("#FED600"), 12));
//         doanhThuPanel.setLayout(null); // Tắt layout manager để tự chỉnh vị trí
//         add(doanhThuPanel);

//         JLabel lblTongdthu = new JLabel("Tổng doanh thu", SwingConstants.CENTER);
//         lblTongdthu.setFont(new Font("Arial", Font.BOLD, 24));
//         lblTongdthu.setBounds(70, 100, 200, 30);
//         doanhThuPanel.add(lblTongdthu);

//         JLabel lblDoanhThu = new JLabel("5.000.000", SwingConstants.CENTER);
//         lblDoanhThu.setFont(new Font("Arial", Font.BOLD, 20));
//         lblDoanhThu.setForeground(Color.decode("#FED600"));
//         lblDoanhThu.setBounds(50, 145, 250, 30);
//         doanhThuPanel.add(lblDoanhThu);

//         // //hình ảnh 
//         ImageIcon icon = new ImageIcon(getClass().getClassLoader().getResource("image/money_icon.png"));
//         JLabel moneyImage = new JLabel(icon);
//         moneyImage.setBounds(500, 250, 700, 400);
//         add(moneyImage);

//         setVisible(true);
//     }

//     // public static void main(String[] args) {
//     //     new Doanhthu();
//     // }
// }

package GUI;

import javax.swing.*;
import java.awt.*;

public class ThongKe extends BaseFrame {
    private JLabel lblThongKe;
    private JTabbedPane tabbedPane;

    public ThongKe() {
        super("Thống kê");
        initialize(); // Gọi từ BaseFrame
        setVisible(true);
    }

    @Override
    protected void initUniqueComponents() {
        // Label tiêu đề
        lblThongKe = new JLabel("Thống kê");
        lblThongKe.setFont(new Font("Arial", Font.BOLD, 20));
        lblThongKe.setBounds(270, 70, 200, 30);
        add(lblThongKe);

        for (JButton btn : menuButtons) {
            if (btn.getText().equals("Thống kê")) {
                btn.setBackground(Color.decode("#EF5D7A"));
                btn.setFont(new Font("Arial", Font.BOLD, 14));
            }
        }

        // Đổi màu các nút trong sidebar
        for (JButton btn : menuButtons) {
            if (!btn.getText().equals("Thống kê")) {
                btn.setBackground(Color.decode("#641A1F"));
                btn.setFont(new Font("Arial", Font.BOLD, 12));
            }
        }

        // Tạo panel chính giữa
        cardPanel = new JPanel();
        cardPanel.setBounds(260, 110, 800, 540);
        cardPanel.setLayout(new BorderLayout());

        // Tạo JTabbedPane
        tabbedPane = new JTabbedPane();
        tabbedPane.setFont(new Font("Arial", Font.PLAIN, 14));

        // Thêm các tab
        tabbedPane.addTab("Doanh thu", new TabDoanhThu());
        tabbedPane.addTab("Hạn sử dụng", new TabHanSuDung());

        // Gắn JTabbedPane vào cardPanel
        cardPanel.add(tabbedPane, BorderLayout.CENTER);

        add(cardPanel); 

        // Các nút export nếu có
        addPDFButton();
        addExceltButton();
    }

    public static void main(String[] args) {
        new ThongKe();
    }
}

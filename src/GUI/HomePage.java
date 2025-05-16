package GUI;

import javax.swing.*;
import java.awt.*;
import dal.Homepagedal;
import java.sql.Connection;
import dal.dbconnection;

public class HomePage extends BaseFrame {
	 public HomePage() {
		 super("Trang chủ");
	    	initialize();
	    	} 
	 	protected void initUniqueComponents() {
	 		for (JButton btn : menuButtons) {
	            if (btn.getText().equals("Trang chủ")) {
	                btn.setBackground(Color.decode("#EF5D7A")); 
	                btn.setFont(new Font("Arial", Font.BOLD, 14)); 
	            }
	        }
	        
	        // Các nút khác vẫn giữ màu mặc định
	        for (JButton btn : menuButtons) {
	            if (!btn.getText().equals("Trang chủ")) {
	                btn.setBackground(Color.decode("#641A1F")); // Màu mặc định cho các nút khác
	                btn.setFont(new Font("Arial", Font.BOLD, 12));
	            }
	        }
	        // Tiêu đề trang chủ
	        JLabel lblTitle = new JLabel("Trang chủ");
	        lblTitle.setFont(new Font("Arial", Font.BOLD, 22));
	        lblTitle.setBounds(270, 70, 200, 30);
	        add(lblTitle);
	       
	        //hình ảnh nhà thuốc
	        ImageIcon icon = new ImageIcon(getClass().getClassLoader().getResource("image/pharmacy.jpg"));
	        JLabel pharmacyImage = new JLabel(icon);
	        pharmacyImage.setBounds(400, 100, 537, 280);
	        add(pharmacyImage);

	        // Thống kê tổng quan
	        JPanel summaryPanel = new JPanel();
	        summaryPanel.setBounds(270, 110, 800, 100);
	        summaryPanel.setLayout(new GridLayout(1, 2, 20, 10));
	        add(summaryPanel);

	        
	        
	        // Thông tin chi tiết
	        JPanel detailPanel = new JPanel();
	        detailPanel.setBounds(270, 400, 750, 250);
	        detailPanel.setLayout(new GridLayout(2, 2, 25, 25));
	        add(detailPanel);

	        Connection conn = dbconnection.getConnection(); 
	        Homepagedal homepageDAL = new Homepagedal(conn);

	        int totalProducts = homepageDAL.getTotalProducts();
	        int totalEmployees = homepageDAL.getTotalEmployees();
	        int totalCustomers = homepageDAL.getTotalCustomers();
	        double totalRevenue = homepageDAL.getRevenueCurrentYear();

	        // sau đó truyền số liệu thực vào
	        detailPanel.add(createCard("Sản phẩm", "Số lượng sản phẩm", String.valueOf(totalProducts), Color.WHITE));
	        detailPanel.add(createCard("Doanh thu", "Tổng doanh thu", String.format("%.0f", totalRevenue), Color.WHITE));
	        detailPanel.add(createCard("Nhân viên", "Số lượng nhân viên", String.valueOf(totalEmployees), Color.WHITE));
	        detailPanel.add(createCard("Khách hàng", "Số lượng khách hàng", String.valueOf(totalCustomers), Color.WHITE));
	    
	      
	 	}

	    private JPanel createCard(String title, String subtitle, String value, Color bgColor) {
	        JPanel card = new JPanel();
	        card.setLayout(null);
	        card.setBackground(bgColor);
	        card.setBorder(BorderFactory.createLineBorder(Color.BLACK));

	        //tiêu đề
	        JLabel lblTitle = new JLabel(title);
	        lblTitle.setFont(new Font("Arial", Font.BOLD, 18));
	        lblTitle.setBounds(20, 10, 200, 25);
	        card.add(lblTitle);

	        //phụ đề
	        JLabel lblSubtitle = new JLabel(subtitle);
	        lblSubtitle.setFont(new Font("Arial", Font.PLAIN, 14));
	        lblSubtitle.setBounds(20, 40, 200, 20);
	        card.add(lblSubtitle);

	        //giá trị
	        JLabel lblValue = new JLabel(value);
	        lblValue.setFont(new Font("Arial", Font.BOLD, 20));
	        lblValue.setBounds(20, 70, 200, 30);
	        card.add(lblValue);

	        return card;
	    }
	    
	    public static void main(String[] args) {
	        SwingUtilities.invokeLater(() -> {
	            HomePage homepage = new HomePage();
	            homepage.setVisible(true);
	        });
	    }
}

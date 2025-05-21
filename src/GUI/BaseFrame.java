package GUI;

import javax.swing.*;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public abstract class BaseFrame extends JFrame {
    protected JPanel sidebar;
    protected JPanel side;
    protected JTextField searchField;
    protected List<JButton> menuButtons;
    protected CardLayout cardLayout; // CardLayout để quản lý các panel
    protected JPanel cardPanel;

    public BaseFrame(String title) {
        setTitle(title);
        setSize(1100, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);

        menuButtons = new ArrayList<>();

    }

    private void initCommonComponents() {

        sidebar = new JPanel();
        sidebar.setBounds(0, 0, 250, 700);
        sidebar.setBackground(Color.decode("#AB282C"));
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        add(sidebar);

        side = new JPanel();
        side.setBounds(0, 0, 1100, 50);
        side.setBackground(Color.decode("#AB282C"));
        side.setLayout(null);
        add(side);

        // Logo
        JLabel logo = new JLabel("Pharmacy", SwingConstants.CENTER);
        logo.setForeground(Color.WHITE);
        logo.setFont(new Font("Arial", Font.BOLD, 20));
        logo.setAlignmentX(Component.CENTER_ALIGNMENT);
        logo.setBounds(0, 10, 250, 40);
        sidebar.add(Box.createVerticalStrut(20));
        sidebar.add(logo);
        sidebar.add(Box.createVerticalStrut(15));

        // Tạo panel cho thông tin người dùng
        JPanel userPanel = new JPanel();
        userPanel.setLayout(new BorderLayout(10, 0)); // Sử dụng BorderLayout với khoảng cách ngang 10px
        userPanel.setOpaque(false);
        userPanel.setMaximumSize(new Dimension(250, 60));
        userPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 20, 10)); // Padding dưới 20px

        // Icon tài khoản
        ImageIcon icon = new ImageIcon(getClass().getClassLoader().getResource("image/staff.png"));
        Image image = icon.getImage();
        Image newImage = image.getScaledInstance(42, 42, Image.SCALE_SMOOTH);
        ImageIcon resizedIcon = new ImageIcon(newImage);

        JLabel iconLabel = new JLabel(resizedIcon);
        iconLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 10)); // Padding phải 10px

        // Panel chứa thông tin text
        JPanel textPanel = new JPanel();
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));
        textPanel.setOpaque(false);

        JLabel nameLabel = new JLabel("Admin");
        nameLabel.setForeground(Color.WHITE);
        nameLabel.setFont(new Font("Arial", Font.BOLD, 16));
        nameLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel roleLabel = new JLabel("Quản lý");
        roleLabel.setForeground(Color.WHITE);
        roleLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        roleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        textPanel.add(nameLabel);
        textPanel.add(roleLabel);

        // Thêm các thành phần vào userPanel
        userPanel.add(iconLabel, BorderLayout.WEST);
        userPanel.add(textPanel, BorderLayout.CENTER);

        // Thêm userPanel vào sidebar
        sidebar.add(userPanel);

        // Menu Buttons
        String[] menuItems = { "Trang chủ", "Sản phẩm", "Loại sản phẩm", "Nhân viên", "Khách hàng", "Phiếu nhập hàng",
                "Hóa đơn", "Doanh thu", "Hãng sản xuất", "Nhà cung ứng", "Đăng xuất" };

        for (String item : menuItems) {
            JButton btn = new JButton(item);
            btn.setAlignmentX(Component.CENTER_ALIGNMENT);
            btn.setMaximumSize(new Dimension(400, 50));
            btn.setBackground(Color.decode("#641A1F"));
            btn.setForeground(Color.WHITE);
            btn.setFont(new Font("Arial", Font.BOLD, 12));
            btn.setFocusPainted(false);
            btn.setBorderPainted(false);
            btn.setContentAreaFilled(true);
            btn.setMargin(new Insets(0, 0, 0, 0));
            btn.setOpaque(true);
            sidebar.add(btn);
            menuButtons.add(btn);
        }

        // Thiết lập sự kiện navigation
        setupNavigation("Trang chủ", HomePage::new);
        setupNavigation("Sản phẩm", SanPham::new);
        setupNavigation("Hóa đơn", HoaDon::new);
        setupNavigation("Doanh thu", DoanhThu::new);
        setupNavigation("Nhân viên", NhanVien::new);
        setupNavigation("Khách hàng", KhachHang::new);
        setupNavigation("Nhà cung ứng", Nhacungung::new);
        // setupNavigation("Hãng sản xuất", Hangsanxuat::new);
        setupNavigation("Phiếu nhập hàng", PhieuNhapHang::new);
        setupNavigation("Loại sản phẩm", LoaiSP::new);
        setupNavigation("Đăng xuất", Login::new);

    }

    protected void initialize() {
        initCommonComponents();
        initUniqueComponents();
    }

    protected abstract void initUniqueComponents();

    private void setupNavigation(String menuItem, Runnable action) {
        for (JButton btn : menuButtons) {
            if (btn.getText().equals(menuItem)) {
                // Đặt lại màu sắc cho các nút
                for (JButton button : menuButtons) {
                    // Chỉ thay đổi màu của các nút không phải là nút hiện tại
                    if (!button.getText().equals(menuItem)) {
                        button.setBackground(Color.decode("#641A1F"));
                        button.setForeground(Color.WHITE);
                        button.setFont(new Font("Arial", Font.BOLD, 12));
                    }
                }
                // Làm nổi bật nút hiện tại
                btn.setBackground(Color.decode("#EF5D7A"));

                // Cập nhật hành động khi nhấn nút
                btn.addActionListener(e -> {
                    dispose();
                    action.run(); // Chạy hành động tương ứng với nút
                });
                break;
            }
        }
    }

    protected JButton createActionButton(String text, int x, int y, int width, int height) {
        JButton btn = new JButton(text);
        btn.setBounds(x, y, width, height);
        btn.setBackground(Color.decode("#F0483E"));
        btn.setForeground(Color.WHITE);
        return btn;
    }

    protected void addPDFButton() {

        ImageIcon iconPDF = new ImageIcon(getClass().getClassLoader().getResource("image/pdf-icon.png"));
        Image imgPDF = iconPDF.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
        ImageIcon resizedIconPDF = new ImageIcon(imgPDF);

        JButton btnExportPDF = new JButton(resizedIconPDF);
        btnExportPDF.setBounds(970, 65, 40, 40);

        btnExportPDF.setBackground(null); // Không có nền
        btnExportPDF.setBorderPainted(false); // Không có viền
        btnExportPDF.setFocusPainted(false); // Không có viền khi focus
        btnExportPDF.setContentAreaFilled(false); // Không tô nền khi hover

        // Đảm bảo button có thể nhấn
        btnExportPDF.setOpaque(true);

        // Thay đổi con trỏ thành hình bàn tay khi rê chuột vào button
        btnExportPDF.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        // Gắn sự kiện cho button
        btnExportPDF.addActionListener(e -> {
            // Logic xử lý khi button được nhấn
            System.out.println("Xuất PDF");
            // Bạn có thể thay thế bằng logic xuất PDF của bạn ở đây
        });

        add(btnExportPDF);

    }


    protected void addExceltButton(JTable table) {
        ImageIcon iconExcel = new ImageIcon(getClass().getClassLoader().getResource("image/excel-icon.png"));
        Image imgExcel = iconExcel.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH); // Scale ảnh
        ImageIcon resizedIconExcel = new ImageIcon(imgExcel);

        JButton btnExportExcel = new JButton(resizedIconExcel);
        btnExportExcel.setBounds(900, 60, 50, 50);

        // Loại bỏ mọi màu nền và viền
        btnExportExcel.setBackground(null); // Không có nền
        btnExportExcel.setBorderPainted(false); // Không có viền
        btnExportExcel.setFocusPainted(false); // Không có viền khi focus
        btnExportExcel.setContentAreaFilled(false); // Không tô nền khi hover

        // Đảm bảo button có thể nhấn
        btnExportExcel.setOpaque(true);

        // Thay đổi con trỏ thành hình bàn tay khi rê chuột vào button
        btnExportExcel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        // Gắn sự kiện cho button
        btnExportExcel.addActionListener(e -> {
            try {
                if (table != null && table.getRowCount() > 0) {
                    boolean success = helper.JTableExporter.exportJTableToExcel(table);
                    if (success) {
                        JOptionPane.showMessageDialog(this, "Xuất Excel thành công!");
                    }

                } else {
                    JOptionPane.showMessageDialog(this, "Không có dữ liệu để xuất!");
                }
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Lỗi khi xuất Excel: " + ex.getMessage());
            }

        });

        add(btnExportExcel);

    }
}

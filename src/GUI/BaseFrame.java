package GUI;

import dal.vaitrodal;
import dto.currentuser;

import javax.swing.*;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public abstract class BaseFrame extends JFrame {
    protected JPanel sidebar;
    protected JPanel side;
    protected JTable table;
    protected JTextField searchField;
    protected List<JButton> menuButtons;
    protected JButton btnExportExcel;
    protected CardLayout cardLayout;
    protected boolean showSearchBar = true;

    public BaseFrame(String title) {
        setTitle(title);
        setSize(1100, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);
        menuButtons = new ArrayList<>();
    }

    protected boolean sidebarVisible = true;
    private void initCommonComponents() {

        sidebar = new JPanel();
        sidebar.setBounds(0, 0, 250, 700);
        sidebar.setBackground(Color.decode("#AB282C"));
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        add(sidebar);

        side = new JPanel();
        side.setBounds(0, 0, 1100, 40);
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


        JLabel nameLabel = new JLabel(currentuser.getUsername() != null ? currentuser.getUsername() : "Chưa đăng nhập");
        nameLabel.setForeground(Color.WHITE);
        nameLabel.setFont(new Font("Arial", Font.BOLD, 16));
        nameLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        vaitrodal vaitroDAL = new vaitrodal();
        String tenVT = vaitroDAL.getTenVaiTro(currentuser.getMaVaiTro());
        JLabel roleLabel = new JLabel(tenVT != null ? tenVT : "Không rõ vai trò");
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
        // Thay thế phần tạo menu buttons bằng đoạn code này
        String[][] menuItems = {
                {"Trang chủ", "image/homepage.png"},
                {"Sản phẩm", "image/sanpham.png"},//
                {"Loại sản phẩm", "image/loaisp.png"},
                {"Nhân viên", "image/nhanvien.png"},//
                {"Khách hàng", "image/khachhang.png"},//
                {"Phiếu nhập hàng", "image/pnh.png"},//
                {"Hóa đơn", "image/hoadon.png"},//
                {"Thống kê", "image/thongke.png"},//
                {"Hãng sản xuất", "image/hsx.png"},//
                {"Nhà cung ứng", "image/ncu.png"},//
                {"Đăng xuất", "image/dangxuat.png"}//
        };

        for (String[] item : menuItems) {
            JButton btn = createMenuButton(item[0], item[1]);
            sidebar.add(btn);
            menuButtons.add(btn);

            if (item[0].equals("Đăng xuất")) {
                btn.addActionListener(e -> {
                    int confirmed = JOptionPane.showConfirmDialog(null,
                            "Bạn có chắc chắn muốn đăng xuất?", "Đăng xuất", JOptionPane.YES_NO_OPTION);
                    if (confirmed == JOptionPane.YES_OPTION) {
                        currentuser.clear();
                        dispose();
                        SignIn loginFrame = new SignIn();
                        loginFrame.setVisible(true);
                    }
                });
            }
        }

        // Thiết lập sự kiện navigation
        setupNavigation("Trang chủ", HomePage::new);
        setupNavigation("Sản phẩm", SanPham::new);
        setupNavigation("Hóa đơn", HoaDon::new);
        setupNavigation("Thống kê", ThongKe::new);
        setupNavigation("Nhân viên", NhanVien::new);
        setupNavigation("Khách hàng", KhachHang::new);
        setupNavigation("Nhà cung ứng", Nhacungung::new);
        setupNavigation("Hãng sản xuất", HangSX::new);
        setupNavigation("Loại sản phẩm", LoaiSP::new);
        setupNavigation("Phiếu nhập hàng", PhieuNhapHang::new);
        setupNavigation("Hóa đơn", HoaDon::new);
    }

    protected void initialize() {
        initCommonComponents();
        initUniqueComponents();
    }

    protected abstract void initUniqueComponents();

    private void setupNavigation(String menuItem, Supplier<? extends BaseFrame> frameSupplier) {
        for (JButton btn : menuButtons) {
            if (btn.getText().equals(menuItem)) {
                for (JButton button : menuButtons) {
                    if (!button.getText().equals(menuItem)) {
                        button.setBackground(Color.decode("#641A1F"));
                        button.setForeground(Color.WHITE);
                        button.setFont(new Font("Arial", Font.BOLD, 12));
                    }
                }
                btn.setBackground(Color.decode("#EF5D7A"));
                btn.setForeground(Color.WHITE);
                btn.setFont(new Font("Arial", Font.BOLD, 14));

                btn.addActionListener(e -> {
                    BaseFrame newFrame = frameSupplier.get();
                    newFrame.setVisible(true);

                    dispose();
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
        btnExportPDF.setBounds(970, 50, 40, 40);

        btnExportPDF.setBackground(null);
        btnExportPDF.setBorderPainted(false); 
        btnExportPDF.setFocusPainted(false);
        btnExportPDF.setContentAreaFilled(false); 

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
        btnExportExcel.setBounds(900, 50, 40, 40);

        btnExportExcel.setBackground(null);
        btnExportExcel.setBorderPainted(false);
        btnExportExcel.setFocusPainted(false);
        btnExportExcel.setContentAreaFilled(false);


        btnExportExcel.setOpaque(true);


        btnExportExcel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));


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

    private JButton createMenuButton(String text, String iconPath) {
        // Tạo button
        JButton btn = new JButton(text);

        // Thiết lập icon nếu có đường dẫn
        if (iconPath != null && !iconPath.isEmpty()) {
            ImageIcon icon = new ImageIcon(getClass().getClassLoader().getResource(iconPath));
            Image image = icon.getImage().getScaledInstance(22, 26, Image.SCALE_SMOOTH); 
            btn.setIcon(new ImageIcon(image));
            btn.setHorizontalAlignment(SwingConstants.LEFT); // Căn trái cả icon và text
            btn.setIconTextGap(15); // Khoảng cách giữa icon và text
        }

        // Các thiết lập style khác
        btn.setAlignmentX(Component.CENTER_ALIGNMENT);
        btn.setMaximumSize(new Dimension(300, 49)); // Giảm width để phù hợp với icon
        btn.setBackground(Color.decode("#641A1F"));
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Arial", Font.BOLD, 12));
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setContentAreaFilled(true);
        btn.setMargin(new Insets(0, 40, 0, 0)); // Thêm padding trái
        btn.setOpaque(true);

        return btn;
    }
}

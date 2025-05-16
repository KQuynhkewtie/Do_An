package GUI;


import bll.TaiKhoanBLL;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.*;
import java.awt.*;

public class Login extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private TaiKhoanBLL taiKhoanBAL = new TaiKhoanBLL();

    public Login() {
        setTitle("Đăng nhập");
        setSize(1100, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);
        
        JLayeredPane layeredPane = new JLayeredPane();
        setContentPane(layeredPane);

        JPanel headerPanel = new JPanel();
        headerPanel.setBounds(0, 0, 1100, 100);
        headerPanel.setBackground(Color.decode("#AB282C"));
        headerPanel.setLayout(null);
        add(headerPanel);
        

        JLabel logoLabel = new JLabel("PHARMACY");
        logoLabel.setForeground(Color.WHITE);
        logoLabel.setFont(new Font("Arial", Font.BOLD, 18));
        logoLabel.setBounds(40, 30, 200, 30);
        headerPanel.add(logoLabel);

        JButton btnRegister = new JButton("Đăng ký");
        btnRegister.setBounds(950, 25, 120, 50);
        btnRegister.setBackground(Color.WHITE);
        btnRegister.setForeground(new Color(59, 130, 246));
        btnRegister.setFocusPainted(false);
        headerPanel.add(btnRegister);

        ImageIcon icon = new ImageIcon(getClass().getResource("/image/check.png"));
        Image img = icon.getImage().getScaledInstance(getWidth(), getHeight(), Image.SCALE_SMOOTH);
        JLabel thuocImage = new JLabel(new ImageIcon(img));
        thuocImage.setBounds(0, 0, getWidth(), getHeight());
        layeredPane.add(thuocImage, JLayeredPane.DEFAULT_LAYER);
        
        
        JPanel loginPanel = new JPanel();
        loginPanel.setBounds(355, 180, 400, 400);
        loginPanel.setBackground(Color.WHITE);
        loginPanel.setLayout(null);
        add(loginPanel);
        layeredPane.add(loginPanel, JLayeredPane.POPUP_LAYER);

        JLabel lblTitle = new JLabel("Đăng nhập");
        lblTitle.setFont(new Font("Arial", Font.BOLD, 20));
        lblTitle.setFont(new Font("Arial", Font.BOLD, 22));
        lblTitle.setBounds(150, 50, 150, 50);
        loginPanel.add(lblTitle);
        

        JLabel lblEmail = new JLabel("EMAIL");
        lblEmail.setFont(new Font("Arial", Font.BOLD, 16));
        lblEmail.setBounds(65, 100, 140, 30);
        loginPanel.add(lblEmail);

        usernameField = new JTextField();
        usernameField.setBounds(65, 130, 270, 40);
        loginPanel.add(usernameField);

        JLabel lblPassword = new JLabel("MẬT KHẨU");
        lblPassword.setFont(new Font("Arial", Font.BOLD, 16));
        lblPassword.setBounds(65, 180, 100, 30);
        loginPanel.add(lblPassword);

        passwordField = new JPasswordField();
        passwordField.setBounds(65, 210, 270, 40);
        loginPanel.add(passwordField);

        JLabel lblquenmk = new JLabel("Quên mật khẩu");
        lblquenmk.setFont(new Font("Arial", Font.BOLD, 12));
        lblquenmk.setBounds(65, 260, 140, 30);
        loginPanel.add(lblquenmk);
        
        JButton btnLogin = new JButton("ĐĂNG NHẬP");
        btnLogin.setBounds(65, 300, 270, 40);
        btnLogin.setBackground(Color.BLACK);
        btnLogin.setForeground(Color.WHITE);
        btnLogin.setFocusPainted(false);
        loginPanel.add(btnLogin);

        btnRegister.addActionListener(e -> {
            dispose();
            new signin();
        });

        btnLogin.addActionListener(e -> {
            String email = usernameField.getText();
            String password = new String(passwordField.getPassword());

            if (taiKhoanBAL.dangNhap(email, password)) {
            	
            	HomePage homepage = new HomePage();
                homepage.setVisible(true); 
                setVisible(false);
            } else {
                JOptionPane.showMessageDialog(null, "Sai tài khoản hoặc mật khẩu!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        });
        usernameField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    btnLogin.doClick(); // Mô phỏng nhấn nút đăng nhập khi nhấn Enter
                }
                if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                    passwordField.requestFocus(); // Chuyển focus sang mật khẩu
                }
            }
        });
       

        passwordField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    btnLogin.doClick(); // Mô phỏng nhấn nút đăng nhập khi nhấn Enter
                }
                if (e.getKeyCode() == KeyEvent.VK_UP) {
                    usernameField.requestFocus(); 
                }
            }
        });
        setVisible(true);
    }
    
 

    

    public static void main(String[] args) {
        new Login();
    }
}
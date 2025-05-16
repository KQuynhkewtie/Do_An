package GUI;
import javax.swing.*;

import dal.taikhoandal;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
public class signin extends JFrame {
	private JTextField usernameField;
    private JTextField emailField;
    private JPasswordField passwordField;
    public signin() {
        setTitle("Đăng ký tài khoản");
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
        
        JButton loginButton = new JButton("Đăng nhập");
        loginButton.setBounds(950, 25, 120, 50);
        loginButton.setBackground(Color.WHITE);
        loginButton.setForeground(new Color(59, 130, 246));
        loginButton.setFocusPainted(false);
        headerPanel.add(loginButton);
        
        ImageIcon icon = new ImageIcon(getClass().getResource("/image/check.png"));
        Image img = icon.getImage().getScaledInstance(getWidth(), getHeight(), Image.SCALE_SMOOTH);
        JLabel thuocImage = new JLabel(new ImageIcon(img));
        thuocImage.setBounds(0, 0, getWidth(), getHeight());
        layeredPane.add(thuocImage, JLayeredPane.DEFAULT_LAYER);
        
        
        // **Form đăng ký**
           JPanel formPanel = new JPanel();
           formPanel.setBounds(355, 180, 400, 400);
           formPanel.setBackground(Color.WHITE);
           formPanel.setLayout(null);
           add(formPanel);
           layeredPane.add(formPanel, JLayeredPane.POPUP_LAYER); 
           
           
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // Đóng cửa sổ đăng ký
                new Login(); // Mở cửa sổ đăng nhập
            }
        });
        
        JLabel titleLabel = new JLabel("Đăng ký");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setFont(new Font("Arial", Font.BOLD, 22));
        titleLabel.setBounds(150, 50, 150, 50);
        
        JLabel lblusername = new JLabel("Tên đăng nhập");
        lblusername.setFont(new Font("Arial", Font.BOLD, 16));
        lblusername.setBounds(65, 90, 140, 30);
        formPanel.add(lblusername);

        usernameField = new JTextField();
        usernameField.setBounds(65, 120, 270, 40);
        usernameField.setFont(new Font("Arial", Font.PLAIN, 14));
        formPanel.add(usernameField);

        JLabel lblemail = new JLabel("Email");
        lblemail.setFont(new Font("Arial", Font.BOLD, 16));
        lblemail.setBounds(65, 170, 100, 30);
        formPanel.add(lblemail);

        emailField = new JTextField();
        emailField.setBounds(65, 200, 270, 40);
        emailField.setFont(new Font("Arial", Font.PLAIN, 14));
        formPanel.add(emailField);

        JLabel lblmatkhau = new JLabel("Mật khẩu");
        lblmatkhau.setFont(new Font("Arial", Font.BOLD, 16));
        lblmatkhau.setBounds(65, 250, 140, 30);
        formPanel.add(lblmatkhau);

        passwordField = new JPasswordField();
        passwordField.setBounds(65, 280, 270, 40);
        usernameField.setFont(new Font("Arial", Font.PLAIN, 14));
        formPanel.add(passwordField);

        JButton registerButton = new JButton("ĐĂNG KÝ");
        registerButton.setFont(new Font("Arial", Font.PLAIN, 17));
        registerButton.setBounds(65, 340, 270, 40);
        registerButton.setBackground(Color.BLACK);
        registerButton.setForeground(Color.WHITE);
        registerButton.setFocusPainted(false);
        formPanel.add(registerButton);
        
        // Xử lý sự kiện khi nhấn nút đăng ký
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String tenTK = usernameField.getText().trim();
                String email = emailField.getText().trim();
                String matKhau = new String(passwordField.getPassword()).trim();
                
                if (tenTK.isEmpty() || email.isEmpty() || matKhau.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Vui lòng nhập đầy đủ thông tin!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                taikhoandal tkDAL = new taikhoandal();
                boolean success = tkDAL.dangKyTaiKhoan(tenTK, email, matKhau);
                
                if (success) {
                    JOptionPane.showMessageDialog(null, "Đăng ký thành công!", "Thành công", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null, "Email đã tồn tại hoặc có lỗi xảy ra!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        usernameField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    registerButton.doClick(); // Mô phỏng nhấn nút đăng ký khi nhấn Enter
                }else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                    emailField.requestFocus(); // Di chuyển xuống emailField khi nhấn mũi tên xuống
                }
            }
        });

        emailField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    registerButton.doClick(); // Mô phỏng nhấn nút đăng ký khi nhấn Enter
                }else if (e.getKeyCode() == KeyEvent.VK_UP) {
                    usernameField.requestFocus(); // Di chuyển lên usernameField khi nhấn mũi tên lên
                } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                    passwordField.requestFocus(); // Di chuyển xuống passwordField khi nhấn mũi tên xuống
                }
            }
        });

        passwordField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    registerButton.doClick(); // Mô phỏng nhấn nút đăng ký khi nhấn Enter
                }else if (e.getKeyCode() == KeyEvent.VK_UP) {
                    emailField.requestFocus(); // Di chuyển lên emailField khi nhấn mũi tên lên
                }
            }
        });


        formPanel.add(titleLabel);
        formPanel.add(usernameField);
        formPanel.add(emailField);
        formPanel.add(passwordField);
        formPanel.add(registerButton);    
       
        
        setVisible(true);
    }

    public static void main(String[] args) {
        new signin();
    }
}

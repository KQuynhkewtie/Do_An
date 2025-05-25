package GUI;
import javax.swing.*;
import bll.TaiKhoanBLL;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
public class SignUp extends JFrame {
    private JTextField usernameField, manvField, emailField;
    private JPasswordField passwordField;
    public SignUp() {
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

        JButton loginButton = new JButton("ĐĂNG NHẬP");
        loginButton.setBounds(940, 25, 130, 50);
        loginButton.setFont(new Font("Arial", Font.BOLD, 14));
        loginButton.setBackground(Color.BLACK);
        loginButton.setForeground(Color.WHITE);
        loginButton.setFocusPainted(false);
        headerPanel.add(loginButton);

        ImageIcon bgIcon = new ImageIcon(getClass().getResource("/image/bg.jpg"));
        Image scaledImage = bgIcon.getImage().getScaledInstance(1100, 700, Image.SCALE_SMOOTH);
        JLabel background = new JLabel(new ImageIcon(scaledImage));
        background.setBounds(0, 0, 1100, 700);
        layeredPane.add(background, JLayeredPane.DEFAULT_LAYER);


        JPanel formPanel = new JPanel();
        formPanel.setBounds(355, 180, 400, 450);
        formPanel.setBackground(Color.WHITE);
        formPanel.setLayout(null);
        add(formPanel);
        layeredPane.add(formPanel, JLayeredPane.POPUP_LAYER);


        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new SignIn();
            }
        });

        JLabel titleLabel = new JLabel("Đăng ký");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setFont(new Font("Arial", Font.BOLD, 22));
        titleLabel.setBounds(150, 30, 150, 50);

        JLabel lblmanv = new JLabel("Mã nhân viên");
        lblmanv.setFont(new Font("Arial", Font.BOLD, 16));
        lblmanv.setBounds(65, 70, 200, 30);
        formPanel.add(lblmanv);
        manvField = new JTextField();
        manvField.setBounds(65, 100, 270, 40);
        manvField.setFont(new Font("Arial", Font.PLAIN, 14));
        formPanel.add(manvField);

        JLabel lblusername = new JLabel("Tên đăng nhập");
        lblusername.setFont(new Font("Arial", Font.BOLD, 16));
        lblusername.setBounds(65, 140, 140, 30);
        formPanel.add(lblusername);

        usernameField = new JTextField();
        usernameField.setBounds(65, 170, 270, 40);
        usernameField.setFont(new Font("Arial", Font.PLAIN, 14));
        formPanel.add(usernameField);

        JLabel lblemail = new JLabel("Email");
        lblemail.setFont(new Font("Arial", Font.BOLD, 16));
        lblemail.setBounds(65, 210, 100, 30);
        formPanel.add(lblemail);

        emailField = new JTextField();
        emailField.setBounds(65, 240, 270, 40);
        emailField.setFont(new Font("Arial", Font.PLAIN, 14));
        formPanel.add(emailField);

        JLabel lblmatkhau = new JLabel("Mật khẩu");
        lblmatkhau.setFont(new Font("Arial", Font.BOLD, 16));
        lblmatkhau.setBounds(65, 280, 140, 30);
        formPanel.add(lblmatkhau);

        passwordField = new JPasswordField();
        passwordField.setBounds(65, 310, 270, 40);
        usernameField.setFont(new Font("Arial", Font.PLAIN, 14));
        formPanel.add(passwordField);

        JButton registerButton = new JButton("ĐĂNG KÝ");
        registerButton.setFont(new Font("Arial", Font.PLAIN, 17));
        registerButton.setBounds(65, 370, 270, 40);
        registerButton.setBackground(Color.BLACK);
        registerButton.setForeground(Color.WHITE);
        registerButton.setFocusPainted(false);
        formPanel.add(registerButton);

        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String tenTK = usernameField.getText().trim();
                String email = emailField.getText().trim();
                String matKhau = new String(passwordField.getPassword()).trim();
                String manv = manvField.getText().trim();
                if (tenTK.isEmpty() || email.isEmpty() || matKhau.isEmpty()|| manv.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Vui lòng nhập đầy đủ thông tin!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                TaiKhoanBLL tkbll = new TaiKhoanBLL();
                boolean success = tkbll.signup(tenTK, email, matKhau, manv);

                if (success) {
                    JOptionPane.showMessageDialog(null, "Đăng ký thành công!", "Thành công", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null, "Email đã tồn tại hoặc có lỗi xảy ra!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        JTextField[] registerFields = { manvField, usernameField, emailField, passwordField };

        for (int i = 0; i < registerFields.length; i++) {
            final int index = i;
            registerFields[i].addKeyListener(new KeyAdapter() {
                @Override
                public void keyPressed(KeyEvent e) {
                    int key = e.getKeyCode();
                    if (key == KeyEvent.VK_ENTER) {
                        registerButton.doClick();
                    } else if (key == KeyEvent.VK_DOWN && index < registerFields.length - 1) {
                        registerFields[index + 1].requestFocus();
                    } else if (key == KeyEvent.VK_UP && index > 0) {
                        registerFields[index - 1].requestFocus();
                    }
                }
            });
        }
        formPanel.add(titleLabel);
        formPanel.add(usernameField);
        formPanel.add(emailField);
        formPanel.add(passwordField);
        formPanel.add(registerButton);


        setVisible(true);
    }

    public static void main(String[] args) {
        new SignUp();
    }
}

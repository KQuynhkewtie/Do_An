package GUI;


import bll.TaiKhoanBLL;

import bll.vaitro_quyenbll;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.*;
import java.awt.*;
import java.sql.*;
import dto.currentuser;
import helper.Sendmail;
import java.util.List;

public class SignIn extends JFrame {
    private JTextField emailField;
    private JPasswordField passwordField;
    private TaiKhoanBLL taiKhoanBAL = new TaiKhoanBLL();
    private String generatedOTP = null;
    private String emailOTP = null;
    private CardLayout cardLayout;
    private JPanel mainPanel;

    public SignIn() {
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

        JButton btnRegister = new JButton("ĐĂNG KÝ");
        btnRegister.setBounds(950, 25, 120, 50);
        btnRegister.setFont(new Font("Arial", Font.BOLD, 14));
        btnRegister.setBackground(Color.BLACK);
        btnRegister.setForeground(Color.WHITE);
        btnRegister.setFocusPainted(false);
        headerPanel.add(btnRegister);

        ImageIcon icon = new ImageIcon(getClass().getResource("/image/bg.jpg"));
        Image img = icon.getImage().getScaledInstance(getWidth(), getHeight(), Image.SCALE_SMOOTH);
        JLabel thuocImage = new JLabel(new ImageIcon(img));
        thuocImage.setBounds(0, 0, getWidth(), getHeight());
        layeredPane.add(thuocImage, JLayeredPane.DEFAULT_LAYER);

        // Khởi tạo CardLayout và mainPanel
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);
        mainPanel.setBounds(355, 180, 400, 400);
        layeredPane.add(mainPanel, JLayeredPane.POPUP_LAYER);

        //Đăng nhập
        JPanel loginPanel = new JPanel();
        loginPanel.setBackground(Color.WHITE);
        loginPanel.setLayout(null);

        JLabel lblTitle = new JLabel("Đăng nhập");
        lblTitle.setFont(new Font("Arial", Font.BOLD, 22));
        lblTitle.setBounds(150, 50, 150, 50);
        loginPanel.add(lblTitle);

        JLabel lblEmail = new JLabel("EMAIL");
        lblEmail.setFont(new Font("Arial", Font.BOLD, 16));
        lblEmail.setBounds(65, 100, 140, 30);
        loginPanel.add(lblEmail);

        emailField = new JTextField();
        emailField.setBounds(65, 130, 270, 40);
        loginPanel.add(emailField);

        JLabel lblPassword = new JLabel("MẬT KHẨU");
        lblPassword.setFont(new Font("Arial", Font.BOLD, 16));
        lblPassword.setBounds(65, 180, 100, 30);
        loginPanel.add(lblPassword);

        passwordField = new JPasswordField();
        passwordField.setBounds(65, 210, 270, 40);
        loginPanel.add(passwordField);

        JLabel lblquenmk = new JLabel("Quên mật khẩu");
        lblquenmk.setFont(new Font("Arial", Font.BOLD, 16));
        lblquenmk.setBounds(65, 260, 140, 30);
        loginPanel.add(lblquenmk);
        lblquenmk.setForeground(Color.BLACK);
        lblquenmk.setCursor(new Cursor(Cursor.HAND_CURSOR));

        JButton btnLogin = new JButton("ĐĂNG NHẬP");
        btnLogin.setBounds(65, 300, 270, 40);
        btnLogin.setBackground(Color.BLACK);
        btnLogin.setForeground(Color.WHITE);
        loginPanel.add(btnLogin);

        mainPanel.add(loginPanel, "login");

        //Quên mật khẩu
        JPanel forgotPanel = new JPanel(null);
        forgotPanel.setBackground(Color.WHITE);

        JLabel lblEmailForgot = new JLabel("Nhập email để lấy mã:");
        lblEmailForgot.setFont(new Font("Arial", Font.BOLD, 16));
        lblEmailForgot.setBounds(50, 50, 300, 30);
        forgotPanel.add(lblEmailForgot);

        JTextField txtEmailForgot = new JTextField();
        txtEmailForgot.setBounds(50, 100, 300, 40);
        forgotPanel.add(txtEmailForgot);

        JButton btnGuiMa = new JButton("GỬI MÃ XÁC NHẬN");
        btnGuiMa.setBounds(50, 180, 300, 40);
        btnGuiMa.setBackground(Color.BLACK);
        btnGuiMa.setForeground(Color.WHITE);
        forgotPanel.add(btnGuiMa);

        JButton btnBack1 = new JButton("QUAY LẠI");
        btnBack1.setBounds(50, 250, 300, 40);
        btnBack1.setBackground(Color.BLACK);
        btnBack1.setForeground(Color.WHITE);
        forgotPanel.add(btnBack1);

        mainPanel.add(forgotPanel, "forgot");

        // Xác nhận
        JPanel verifyPanel = new JPanel(null);
        verifyPanel.setBackground(Color.WHITE);

        JLabel lblCode = new JLabel("Nhập mã xác nhận:");
        lblCode.setFont(new Font("Arial", Font.BOLD, 16));
        lblCode.setBounds(50, 50, 300, 30);
        verifyPanel.add(lblCode);

        JTextField txtCode = new JTextField();
        txtCode.setBounds(50, 100, 300, 40);
        verifyPanel.add(txtCode);

        JButton btnXacNhanMa = new JButton("XÁC NHẬN");
        btnXacNhanMa.setBounds(50, 180, 300, 40);
        btnXacNhanMa.setBackground(Color.BLACK);
        btnXacNhanMa.setForeground(Color.WHITE);
        verifyPanel.add(btnXacNhanMa);

        JButton btnBack2 = new JButton("QUAY LẠI");
        btnBack2.setBounds(50, 250, 300, 40);
        btnBack2.setBackground(Color.BLACK);
        btnBack2.setForeground(Color.WHITE);
        verifyPanel.add(btnBack2);

        mainPanel.add(verifyPanel, "verify");

        // Nhạp mk mới
        JPanel resetPanel = new JPanel(null);
        resetPanel.setBackground(Color.WHITE);

        JLabel lblNewPass = new JLabel("Mật khẩu mới:");
        lblNewPass.setBounds(50, 50, 300, 30);
        lblNewPass.setFont(new Font("Arial", Font.BOLD, 16));
        resetPanel.add(lblNewPass);

        JPasswordField txtNewPass = new JPasswordField();
        txtNewPass.setBounds(50, 100, 300, 40);
        resetPanel.add(txtNewPass);

        JButton btnDatLai = new JButton("ĐẶT LẠI MẶT KHẨU");
        btnDatLai.setBounds(50, 180, 300, 40);
        btnDatLai.setBackground(Color.BLACK);
        btnDatLai.setForeground(Color.WHITE);
        resetPanel.add(btnDatLai);

        JButton btnBack3 = new JButton("QUAY LẠI");
        btnBack3.setBounds(50, 250, 300, 40);
        btnBack3.setBackground(Color.BLACK);
        btnBack3.setForeground(Color.WHITE);
        resetPanel.add(btnBack3);

        mainPanel.add(resetPanel, "reset");


        lblquenmk.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                cardLayout.show(mainPanel, "forgot");
            }
        });

        btnBack1.addActionListener(e -> cardLayout.show(mainPanel, "login"));
        btnBack2.addActionListener(e -> cardLayout.show(mainPanel, "forgot"));
        btnBack3.addActionListener(e -> cardLayout.show(mainPanel, "verify"));

        btnGuiMa.addActionListener(e -> {
            String email = txtEmailForgot.getText().trim();
            if (email.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Vui lòng nhập email.");
                return;
            }

            // Tạo mã OTP 6 số
            generatedOTP = String.valueOf((int)(Math.random() * 900000) + 100000);
            emailOTP = email;

            JOptionPane.showMessageDialog(null, "Đã gửi mã xác nhận đến email.");


            cardLayout.show(mainPanel, "verify");

            // Gửi mail ngầm ở Thread riêng
            new Thread(() -> {
                try {
                    Sendmail.sendOTP(email, generatedOTP);
                } catch (Exception ex) {
                    ex.printStackTrace();
                    SwingUtilities.invokeLater(() -> {
                        JOptionPane.showMessageDialog(null, "Không gửi được email! Kiểm tra cấu hình.", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    });
                }
            }).start();
        });

        btnXacNhanMa.addActionListener(e -> {
            String inputCode = txtCode.getText().trim();
            if (inputCode.equals(generatedOTP)) {
                cardLayout.show(mainPanel, "reset");
            } else {
                JOptionPane.showMessageDialog(null, "Mã OTP không đúng!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnDatLai.addActionListener(e -> {
            String newPassword = new String(txtNewPass.getPassword()).trim();
            if (newPassword.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Vui lòng nhập mật khẩu mới!");
                return;
            }

            try {
                if (taiKhoanBAL.updatetmatkhau(emailOTP, newPassword)) {
                    JOptionPane.showMessageDialog(null, "Mật khẩu đã được đặt lại!");
                    cardLayout.show(mainPanel, "login");
                } else {
                    JOptionPane.showMessageDialog(null, "Không tìm thấy tài khoản!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "Lỗi khi cập nhật mật khẩu!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnRegister.addActionListener(e -> {
            dispose();
            new SignUp();
        });

        btnLogin.addActionListener(e -> {
            String email = emailField.getText().trim();
            String password = new String(passwordField.getPassword()).trim();

            if (email.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Vui lòng nhập email và mật khẩu!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                String maNhanVien = taiKhoanBAL.login(email, password);

                if (maNhanVien != null) {
                    String username = taiKhoanBAL.getusename(email);

                    String vaiTro = taiKhoanBAL.getVaiTro(maNhanVien);
                    vaitro_quyenbll vqBLL = new vaitro_quyenbll();
                    List<String> danhSachQuyen = vqBLL.layDanhSachQuyenTheoVaiTro(vaiTro);
                    currentuser.setUser(email, maNhanVien, vaiTro, danhSachQuyen);
                    currentuser.setUsername(username);
                    new HomePage().setVisible(true);
                    setVisible(false);
                } else {
                    JOptionPane.showMessageDialog(null, "Sai tài khoản hoặc mật khẩu!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "Lỗi truy vấn cơ sở dữ liệu!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        });

        JTextField[] textFields = { emailField, passwordField };

        for (int i = 0; i < textFields.length; i++) {
            final int currentIndex = i;
            textFields[i].addKeyListener(new KeyAdapter() {
                @Override
                public void keyPressed(KeyEvent e) {
                    int key = e.getKeyCode();
                    if (key == KeyEvent.VK_DOWN && currentIndex < textFields.length - 1) {
                        textFields[currentIndex + 1].requestFocus();
                    } else if (key == KeyEvent.VK_UP && currentIndex > 0) {
                        textFields[currentIndex - 1].requestFocus();
                    } else if (key == KeyEvent.VK_ENTER) {
                        btnLogin.doClick();
                    }
                }
            });
        }
        setVisible(true);
    }

    public static void main(String[] args) {
        new SignIn();
    }
}
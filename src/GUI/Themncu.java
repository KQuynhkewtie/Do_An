package GUI;

import javax.swing.*;


import dal.ncudal;
import dto.NhaCungUngDTO;
import dto.currentuser;
import bll.NhaCungUngBLL;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Themncu extends BaseFrame {
    private NhaCungUngBLL ncuBLL= new NhaCungUngBLL();
    private JRadioButton rbHT, rbNHT;
    private ButtonGroup groupTrangThai;
    public Themncu() {
        super("Cập nhật thông tin nhà cung ứng");
        initialize();
    }
    @Override
    protected void initUniqueComponents() {
        for (JButton btn : menuButtons) {
            if (btn.getText().equals("Nhà cung ứng")) {
                btn.setBackground(Color.decode("#EF5D7A"));
                btn.setFont(new Font("Arial", Font.BOLD, 14));
            }
        }

        for (JButton btn : menuButtons) {
            if (!btn.getText().equals("Nhà cung ứng")) {
                btn.setBackground(Color.decode("#641A1F"));
                btn.setFont(new Font("Arial", Font.BOLD, 12));
            }
        }
        JLabel lblncuLink = new JLabel("<html><u>Nhà cung ứng</u></html>");
        lblncuLink.setFont(new Font("Arial", Font.BOLD, 20));
        lblncuLink.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        lblncuLink.setBounds(270, 70, 160, 30);
        add(lblncuLink);

        JLabel lblArrow = new JLabel(" >> Thêm nhà cung ứng");
        lblArrow.setFont(new Font("Arial", Font.BOLD, 20));
        lblArrow.setBounds(410, 70, 300, 30);
        add(lblArrow);

        lblncuLink.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                dispose();
                new Nhacungung();
            }
        });


        JLabel lblMaNCU = new JLabel("Mã nhà cung ứng:");
        lblMaNCU.setBounds(270, 120, 150, 30);
        add(lblMaNCU);
        JTextField txtMaNCU = new JTextField();
        txtMaNCU.setBounds(270, 150, 300, 30);
        add(txtMaNCU);

        JLabel lblTen = new JLabel("Tên nhà cung ứng:");
        lblTen.setBounds(270, 200, 150, 30);
        add(lblTen);
        JTextField txtTen = new JTextField();
        txtTen.setBounds(270, 230, 300, 30);
        add(txtTen);

        JLabel lblMST = new JLabel("Mã số thuế:");
        lblMST.setBounds(270, 280, 150, 30);
        add(lblMST);
        JTextField txtMST = new JTextField();
        txtMST.setBounds(270, 310, 300, 30);
        add(txtMST);

        JLabel lblDC = new JLabel("Địa chỉ:");
        lblDC.setBounds(270, 360, 150, 30);
        add(lblDC);
        JTextField txtDC = new JTextField();
        txtDC.setBounds(270, 390, 300, 30);
        add(txtDC);

        JLabel lblSDT = new JLabel("Số điện thoại:");
        lblSDT.setBounds(700, 120, 150, 30);
        add(lblSDT);
        JTextField txtSDT = new JTextField();
        txtSDT.setBounds(700, 150, 300, 30);
        add(txtSDT);

        JLabel lblEmail = new JLabel("Email:");
        lblEmail.setBounds(700, 200, 150, 30);
        add(lblEmail);
        JTextField txtEmail = new JTextField();
        txtEmail.setBounds(700, 230, 300, 30);
        add(txtEmail);

        JLabel lblTrangThai = new JLabel("Trạng thái:");
        lblTrangThai.setBounds(700, 280, 150, 25);
        add(lblTrangThai);

        rbHT = new JRadioButton("Đang hợp tác");
        rbHT.setBounds(700, 310, 150, 25);
        add(rbHT);

        rbNHT = new JRadioButton("Ngưng hợp tác");
        rbNHT.setBounds(700, 340, 150, 25);
        add(rbNHT);

        groupTrangThai = new ButtonGroup();
        groupTrangThai.add(rbHT);
        groupTrangThai.add(rbNHT);
        //nút lưu
        JButton btnLuu = new JButton("Lưu");
        btnLuu.setBounds(700, 400, 100, 30);
        btnLuu.setBackground(Color.decode("#F0483E"));
        btnLuu.setForeground(Color.WHITE);
        add(btnLuu);

        btnLuu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!currentuser.coQuyen("Thêm nhà cung ứng")) {

                    JOptionPane.showMessageDialog(null, "Bạn không có quyền thêm!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                try {
                    String maNCU = txtMaNCU.getText().trim();
                    String tenNCU = txtTen.getText().trim();
                    String MST = txtMST.getText().trim();
                    String DC = txtDC.getText().trim();
                    String SDT = txtSDT.getText().trim();
                    String Email = txtEmail.getText().trim();
                    if (maNCU.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "Vui lòng nhập mã nhà cung ứng!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    if (tenNCU.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "Vui lòng nhập tên nhà cung ứng!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    if (MST.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "Vui lòng nhập mã số thuế!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    if (DC.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "Vui lòng nhập địa chỉ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    if (SDT.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "Vui lòng nhập số điện thoại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    if (Email.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "Vui lòng nhập email!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    int trangthai;
                    if (rbHT.isSelected()) {
                        trangthai = 1;
                    } else if (rbNHT.isSelected()) {
                        trangthai = 0;
                    } else {
                        JOptionPane.showMessageDialog(null, "Vui lòng chọn trạng thái!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    NhaCungUngDTO ncu= new NhaCungUngDTO(maNCU, tenNCU, MST, DC, SDT, Email, trangthai);
                    boolean result = ncuBLL.insertNCU(ncu);
                    if (result) {
                        JOptionPane.showMessageDialog(null, "Thêm nhà cung ứng thành công!", "Lỗi", JOptionPane.INFORMATION_MESSAGE);
                        dispose();
                        new Nhacungung();
                    } else {
                        JOptionPane.showMessageDialog(null, "Thêm nhà cung ứng thất bại! Kiểm tra dữ liệu." , "Lỗi", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Vui lòng nhập đúng định dạng!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        getRootPane().setDefaultButton(btnLuu);
        setVisible(true);

        JTextField[] textFields = {txtMaNCU, txtTen, txtMST, txtSDT, txtEmail,txtDC};

        for (int i = 0; i < textFields.length; i++) {
            final int currentIndex = i;
            textFields[i].addKeyListener(new KeyAdapter() {
                @Override
                public void keyPressed(KeyEvent e) {
                    int key = e.getKeyCode();
                    if (key == KeyEvent.VK_DOWN) {
                        if (currentIndex < textFields.length - 1) {
                            textFields[currentIndex + 1].requestFocus();
                        }
                    } else if (key == KeyEvent.VK_UP) {
                        if (currentIndex > 0) {
                            textFields[currentIndex - 1].requestFocus();
                        }
                    }
                }
            });
        }
    }
    public static void main(String[] args) {
        new Themncu();
    }
}

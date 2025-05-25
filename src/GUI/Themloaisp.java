package GUI;
import javax.swing.*;

import dal.loaispdal;
import dto.LoaiSPDTO;
import dto.currentuser;
import bll.LoaiSPBLL;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Themloaisp extends BaseFrame {
    private LoaiSPBLL lspBLL= new LoaiSPBLL();
    private JRadioButton rbSD, rbNSD;
    private ButtonGroup groupTrangThai;
    public Themloaisp() {
        super("Cập nhật thông tin loại sản phẩm");
        initialize();
    }
    @Override
    protected void initUniqueComponents() {
        for (JButton btn : menuButtons) {
            if (btn.getText().equals("Loại sản phẩm")) {
                btn.setBackground(Color.decode("#EF5D7A"));
                btn.setFont(new Font("Arial", Font.BOLD, 14));
            }
        }

        for (JButton btn : menuButtons) {
            if (!btn.getText().equals("Loại sản phẩm")) {
                btn.setBackground(Color.decode("#641A1F"));
                btn.setFont(new Font("Arial", Font.BOLD, 12));
            }
        }

        JLabel lblLoaiSanPhamLink = new JLabel("<html><u>Loại sản phẩm</u></html>");
        lblLoaiSanPhamLink.setFont(new Font("Arial", Font.BOLD, 20));
        lblLoaiSanPhamLink.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        lblLoaiSanPhamLink.setBounds(270, 70, 160, 30);
        add(lblLoaiSanPhamLink);

        JLabel lblArrow = new JLabel(" >> Thêm loại sản phẩm");
        lblArrow.setFont(new Font("Arial", Font.BOLD, 20));
        lblArrow.setBounds(410, 70, 300, 30);
        add(lblArrow);

        lblLoaiSanPhamLink.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                dispose();
                new LoaiSP();
            }
        });

        JLabel lblMaSP = new JLabel("Mã loại sản phẩm:");
        lblMaSP.setBounds(270, 120, 150, 25);
        add(lblMaSP);
        JTextField txtMaLSP = new JTextField();
        txtMaLSP.setBounds(270, 150, 300, 30);
        add(txtMaLSP);

        JLabel lblLoaiSP = new JLabel("Tên loại sản phẩm:");
        lblLoaiSP.setBounds(700, 120, 150, 25);
        add(lblLoaiSP);
        JTextField txtTenLSP = new JTextField();;
        txtTenLSP.setBounds(700, 150, 300, 30);
        add(txtTenLSP);

        JLabel lblTrangThai = new JLabel("Trạng thái:");
        lblTrangThai.setBounds(270, 200, 150, 25);
        add(lblTrangThai);

        rbSD = new JRadioButton("Còn sử dụng");
        rbSD.setBounds(270, 230, 230, 25);
        add(rbSD);

        rbNSD = new JRadioButton("Ngưng sử dụng");
        rbNSD.setBounds(270, 260, 150, 25);
        add(rbNSD);

        groupTrangThai = new ButtonGroup();
        groupTrangThai.add(rbSD);
        groupTrangThai.add(rbNSD);
        // Nút Lưu
        JButton btnLuu = new JButton("Lưu");
        btnLuu.setBounds(700, 200, 100, 40);
        btnLuu.setBackground(Color.decode("#F0483E"));
        btnLuu.setForeground(Color.WHITE);
        add(btnLuu);
        btnLuu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!currentuser.coQuyen("Thêm loại sản phẩm")) {

                    JOptionPane.showMessageDialog(null, "Bạn không có quyền thêm!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                try {
                    String maLSP = txtMaLSP.getText().trim();
                    String tenLSP = txtTenLSP.getText().trim();

                    if (maLSP.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "Vui lòng nhập mã loại sản phẩm!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    if (tenLSP.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "Vui lòng nhập tên loại sản phẩm!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    int trangthai;
                    if (rbSD.isSelected()) {
                        trangthai = 1;
                    } else if (rbNSD.isSelected()) {
                        trangthai = 0;
                    } else {
                        JOptionPane.showMessageDialog(null, "Vui lòng chọn trạng thái!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    LoaiSPDTO lsp = new LoaiSPDTO(maLSP, tenLSP,trangthai);
                    boolean result = lspBLL.insertLSP(lsp);

                    if (result) {
                        JOptionPane.showMessageDialog(null, "Thêm loại sản phẩm thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                        dispose();
                        new LoaiSP();
                    } else {
                        JOptionPane.showMessageDialog(null, "Thêm loại sản phẩm thất bại! Kiểm tra dữ liệu." , "Lỗi", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Vui lòng nhập đúng định dạng!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        getRootPane().setDefaultButton(btnLuu);
        setVisible(true);

        JTextField[] textFields = {txtTenLSP, txtMaLSP};

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
        new Themloaisp();
    }
}


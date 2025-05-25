package GUI;

import javax.swing.*;

import bll.NhanVienBLL;
import dal.NhanViendal;
import dto.NhanVienDTO;
import dto.currentuser;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Themnv extends BaseFrame {
    private NhanVienBLL nvBLL = new NhanVienBLL();
    public Themnv() {
        super("Cập nhật thông tin nhân viên");
        initialize();
    }
    @Override
    protected void initUniqueComponents() {
        for (JButton btn : menuButtons) {
            if (btn.getText().equals("Nhân viên")) {
                btn.setBackground(Color.decode("#EF5D7A"));
                btn.setFont(new Font("Arial", Font.BOLD, 14));
            }
        }
        for (JButton btn : menuButtons) {
            if (!btn.getText().equals("Nhân viên")) {
                btn.setBackground(Color.decode("#641A1F"));
                btn.setFont(new Font("Arial", Font.BOLD, 12));
            }
        }
        JLabel lblnhanvienLink = new JLabel("<html><u>Nhân viên</u></html>");
        lblnhanvienLink.setFont(new Font("Arial", Font.BOLD, 20));
        lblnhanvienLink.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        lblnhanvienLink.setBounds(270, 70, 100, 30);
        add(lblnhanvienLink);

        JLabel lblArrow = new JLabel(" >> Thêm nhân viên");
        lblArrow.setFont(new Font("Arial", Font.BOLD, 20));
        lblArrow.setBounds(370, 70, 400, 30);
        add(lblArrow);

        lblnhanvienLink.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                dispose();
                new NhanVien();
            }
        });

        //form thêm nhân viên
        JLabel lblMaNV = new JLabel("Mã nhân viên:");
        lblMaNV.setBounds(270, 120, 150, 30);
        add(lblMaNV);
        JTextField txtMaNV = new JTextField();
        txtMaNV.setBounds(270, 150, 300, 30);
        add(txtMaNV);
        //họ tên
        JLabel lblHoTen = new JLabel("Họ tên:");
        lblHoTen.setBounds(270, 200, 150, 30);
        add(lblHoTen);
        JTextField txtHoTen = new JTextField();
        txtHoTen.setBounds(270, 230, 300, 30);
        add(txtHoTen);
        //số điện thoại
        JLabel lblSDT = new JLabel("Số điện thoại:");
        lblSDT.setBounds(270, 280, 150, 30);
        add(lblSDT);
        JTextField txtSDT = new JTextField();
        txtSDT.setBounds(270, 310, 300, 30);
        add(txtSDT);
        //cccd
        JLabel lblCCCD = new JLabel("CCCD:");
        lblCCCD.setBounds(270, 360, 150, 30);
        add(lblCCCD);
        JTextField txtCCCD = new JTextField();
        txtCCCD.setBounds(270, 390, 300, 30);
        add(txtCCCD);
        //vị trí công việc

        JLabel lblVTCV = new JLabel("Vị trí công việc:");
        lblVTCV.setBounds(700, 120, 150, 30);
        add(lblVTCV);

        JRadioButton rbBanHang = new JRadioButton("Nhân viên bán hàng");
        rbBanHang.setBounds(700, 150, 200, 25);
        add(rbBanHang);

        JRadioButton rbQuanLy = new JRadioButton("Nhân viên quản lý");
        rbQuanLy.setBounds(700, 180, 200, 25);
        add(rbQuanLy);


        ButtonGroup groupVTCV = new ButtonGroup();
        groupVTCV.add(rbBanHang);
        groupVTCV.add(rbQuanLy);
        //mã số thuế
        JLabel lblMST = new JLabel("Mã số thuế:");
        lblMST.setBounds(700, 200, 150, 30);
        add(lblMST);
        JTextField txtMST = new JTextField();
        txtMST.setBounds(700, 230, 300, 30);
        add(txtMST);
        //trạng thái
        JLabel lblTrangThai = new JLabel("Trạng thái:");
        lblTrangThai.setBounds(700, 280, 150, 30);
        add(lblTrangThai);

        JRadioButton rbDangLam = new JRadioButton("Đang làm");
        rbDangLam.setBounds(700, 310, 150, 25);
        add(rbDangLam);

        JRadioButton rbĐanghi = new JRadioButton("Đã nghỉ");
        rbĐanghi.setBounds(700, 340, 150, 25);
        add(rbĐanghi);

        ButtonGroup groupTrangThai = new ButtonGroup();
        groupTrangThai.add(rbDangLam);
        groupTrangThai.add(rbĐanghi);
        //nút lưu
        JButton btnLuu = new JButton("Lưu");
        btnLuu.setBounds(700, 390, 100, 30);
        btnLuu.setBackground(Color.decode("#F0483E"));
        btnLuu.setForeground(Color.WHITE);
        add(btnLuu);

        btnLuu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!currentuser.coQuyen("Thêm nhân viên")) {

                    JOptionPane.showMessageDialog(null, "Bạn không có quyền thêm!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                try {
                    String maNV = txtMaNV.getText().trim();
                    String tenNV = txtHoTen.getText().trim();
                    String CCCD = txtCCCD.getText().trim();
                    String SDT = txtSDT.getText().trim();
                    String MST = txtMST.getText().trim();
                    String vitri = null;
                    if (maNV.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "Vui lòng nhập mã nhân viên!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    if (tenNV.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "Vui lòng nhập tên nhân viên!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    if (CCCD.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "Vui lòng nhập căn cước công dân!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    if (SDT.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "Vui lòng nhập số điện thoại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    if (MST.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "Vui lòng nhập mã số thuế!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    if (rbBanHang.isSelected()) {
                        vitri = "VT003";
                    } else if (rbQuanLy.isSelected()) {
                        vitri = "VT002";
                    } else {
                        JOptionPane.showMessageDialog(null, "Vui lòng chọn vị trí công việc!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    int trangthai;
                    if (rbDangLam.isSelected()) {
                        trangthai = 1;
                    } else if (rbĐanghi.isSelected()) {
                        trangthai = 0;
                    } else {
                        JOptionPane.showMessageDialog(null, "Vui lòng chọn trạng thái!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    NhanVienDTO nv = new NhanVienDTO(maNV, tenNV, CCCD, SDT, vitri, MST, trangthai);
                    boolean result = nvBLL.insertNhanVien(nv);


                    if (result) {
                        JOptionPane.showMessageDialog(null, "Thêm nhân viên thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                        dispose();
                        new NhanVien();
                    } else {
                        JOptionPane.showMessageDialog(null, "Thêm nhân viên thất bại! Kiểm tra dữ liệu." , "Lỗi", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Vui lòng nhập đúng định dạng!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        getRootPane().setDefaultButton(btnLuu);
        setVisible(true);
        JTextField[] textFields = { txtMaNV, txtHoTen,txtSDT, txtCCCD, txtMST};

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
        new Themnv();
    }
}

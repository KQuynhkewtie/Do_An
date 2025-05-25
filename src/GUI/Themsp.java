package GUI;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import dal.SanPhamDAL;
import dto.HangSanXuatDTO;
import dto.LoaiSPDTO;
import dto.SanPhamDTO;
import dto.currentuser;
import bll.SanPhamBLL;
import bll.HangSanXuatBLL;
import bll.LoaiSPBLL;

import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

public class Themsp extends BaseFrame {
    private SanPhamBLL spBLL = new SanPhamBLL();
    private HangSanXuatBLL hsxBLL = new HangSanXuatBLL();
    private LoaiSPBLL lspBLL = new LoaiSPBLL();
    private JComboBox<String> comboMaLSP, comboHSX;
    private JRadioButton rbDangban, rbNgungban;
    private ButtonGroup groupTrangThai;
    public Themsp() {
        super("Cập nhật thông tin sản phẩm");
        initialize();
    }
    @Override
    protected void initUniqueComponents() {
        for (JButton btn : menuButtons) {
            if (btn.getText().equals("Sản phẩm")) {
                btn.setBackground(Color.decode("#EF5D7A"));
                btn.setFont(new Font("Arial", Font.BOLD, 14));
            }
        }
        for (JButton btn : menuButtons) {
            if (!btn.getText().equals("Sản phẩm")) {
                btn.setBackground(Color.decode("#641A1F"));
                btn.setFont(new Font("Arial", Font.BOLD, 12));
            }
        }
        JLabel lblSanPhamLink = new JLabel("<html><u>Sản phẩm</u></html>");
        lblSanPhamLink.setFont(new Font("Arial", Font.BOLD, 20));
        lblSanPhamLink.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        lblSanPhamLink.setBounds(270, 60, 100, 30);
        add(lblSanPhamLink);

        JLabel lblArrow = new JLabel(" >> Thêm sản phẩm");
        lblArrow.setFont(new Font("Arial", Font.BOLD, 20));
        lblArrow.setBounds(370, 60, 200, 30);
        add(lblArrow);

        lblSanPhamLink.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                dispose();
                new SanPham();
            }
        });

        // Form nhập sản phẩm

        JLabel lblMaSP = new JLabel("Mã sản phẩm:");
        lblMaSP.setBounds(270, 120, 150, 25);
        add(lblMaSP);
        JTextField txtMaSP = new JTextField();
        txtMaSP.setBounds(270, 150, 300, 30);
        add(txtMaSP);

        JLabel lblTenSP = new JLabel("Tên sản phẩm:");
        lblTenSP.setBounds(700, 120, 150, 25);
        add(lblTenSP);
        JTextField txtTenSP = new JTextField();
        txtTenSP.setBounds(700, 150, 300, 30);
        add(txtTenSP);


        JLabel lblLoaiSP = new JLabel("Loại sản phẩm:");
        lblLoaiSP.setBounds(270, 200, 150, 25);
        add(lblLoaiSP);

        comboMaLSP = new JComboBox<>();
        comboMaLSP.setBounds(270, 230, 300, 30);
        add(comboMaLSP);
        loadLoaiSanPhamVaoComboBox();



        JLabel lblSoLuong = new JLabel("Số lượng:");
        lblSoLuong.setBounds(700, 200, 150, 25);
        add(lblSoLuong);
        JTextField txtSoLuong = new JTextField();
        txtSoLuong.setBounds(700, 230, 300, 30);
        add(txtSoLuong);

        JLabel lblHSX = new JLabel("Hãng sản xuất:");
        lblHSX.setBounds(270, 280, 150, 25);
        add(lblHSX);
        comboHSX = new JComboBox<>();
        comboHSX.setBounds(270, 310, 300, 30);
        add(comboHSX);
        loadHSXVaoComboBox();

        JLabel lblqcdg = new JLabel("Quy cách đóng gói:");
        lblqcdg.setBounds(700, 280, 150, 25);
        add(lblqcdg);
        JTextField txtQcdg = new JTextField();
        txtQcdg.setBounds(700, 310, 300, 30);
        add(txtQcdg);



        JLabel lblSodk = new JLabel("Số đăng ký:");
        lblSodk.setBounds(700, 360, 150, 25);
        add(lblSodk);
        JTextField txtSodk = new JTextField();
        txtSodk.setBounds(700, 390, 300, 30);
        add(txtSodk);


        JLabel lblGia = new JLabel("Giá bán:");
        lblGia.setBounds(700, 440, 150, 25);
        add(lblGia);
        JTextField txtGia = new JTextField();
        txtGia.setBounds(700, 470, 300, 30);
        add(txtGia);

        JLabel lblTrangThai = new JLabel("Trạng thái:");
        lblTrangThai.setBounds(270, 360, 150, 25);
        add(lblTrangThai);

        rbDangban = new JRadioButton("Đang bán");
        rbDangban.setBounds(270, 390, 150, 25);
        add(rbDangban);

        rbNgungban = new JRadioButton("Ngưng bán");
        rbNgungban.setBounds(270, 420, 150, 25);
        add(rbNgungban);

        groupTrangThai = new ButtonGroup();
        groupTrangThai.add(rbDangban);
        groupTrangThai.add(rbNgungban);
        // Nút Lưu
        JButton btnLuu = new JButton("Lưu");
        btnLuu.setBounds(700, 520, 100, 40);
        btnLuu.setBackground(Color.decode("#F0483E"));
        btnLuu.setForeground(Color.WHITE);
        add(btnLuu);

        btnLuu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!currentuser.coQuyen("Thêm sản phẩm")) {

                    JOptionPane.showMessageDialog(null, "Bạn không có quyền thêm!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                try {
                    String maSP = txtMaSP.getText().trim();
                    String tenSP = txtTenSP.getText().trim();
                    String maLSP = comboMaLSP.getSelectedItem().toString().split(" - ")[0];
                    String maHSX = comboHSX.getSelectedItem().toString().split(" - ")[0];
                    String giaBanStr = txtGia.getText().trim();
                    String quyCachDongGoi = txtQcdg.getText().trim();
                    String soDangKy = txtSodk.getText().trim();
                    String soLuongStr = txtSoLuong.getText().trim();

                    if (maLSP.isEmpty() || maHSX.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "Vui lòng chọn loại sản phẩm và hãng sản xuất!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    if (maSP.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "Vui lòng nhập mã sản phẩm!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    if (tenSP.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "Vui lòng nhập tên sản phẩm!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    if (quyCachDongGoi.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "Vui lòng nhập số lượng!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    if (soDangKy.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "Vui lòng nhập số đăng ký!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    if (soLuongStr.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "Vui lòng nhập số lượng!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    if (giaBanStr.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "Vui lòng nhập giá bán!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    double giaBan;
                    int soluong;
                    try {
                        giaBan = Double.parseDouble(giaBanStr);
                        if (giaBan < 0) {
                            JOptionPane.showMessageDialog(null, "Giá bán phải là số dương!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(null, "Giá bán phải là số hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    try {
                        soluong = Integer.parseInt(soLuongStr);
                        if (soluong < 0) {
                            JOptionPane.showMessageDialog(null, "Số lượng phải là số dương!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(null, "Số lượng phải là số nguyên hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    int trangthai;
                    if (rbDangban.isSelected()) {
                        trangthai = 1;
                    } else if (rbNgungban.isSelected()) {
                        trangthai = 0;
                    } else {
                        JOptionPane.showMessageDialog(null, "Vui lòng chọn trạng thái!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    SanPhamDTO sp = new SanPhamDTO(maSP, maHSX, maLSP, tenSP, quyCachDongGoi,  soDangKy, soluong, giaBan, trangthai);

                    System.out.println("Mã loại sản phẩm lấy từ giao diện: [" + maLSP + "]");
                    boolean result = spBLL.insertSanPham(sp);

                    if (result) {
                        JOptionPane.showMessageDialog(null, "Thêm sản phẩm thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                        dispose();
                        new SanPham();
                    } else {
                        JOptionPane.showMessageDialog(null, "Thêm sản phẩm thất bại! Kiểm tra dữ liệu." , "Lỗi", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Vui lòng nhập đúng định dạng!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        getRootPane().setDefaultButton(btnLuu);
        setVisible(true);

        JTextField[] textFields = {txtTenSP, txtMaSP, txtSoLuong, txtQcdg,  txtSodk, txtGia};

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
    private void loadLoaiSanPhamVaoComboBox() {
        List<LoaiSPDTO> loaiSPList = lspBLL.getAllLSP();
        comboMaLSP.removeAllItems();
        comboMaLSP.addItem("-- Chọn loại sản phẩm --");
        for (LoaiSPDTO lsp : loaiSPList) {
            comboMaLSP.addItem(lsp.getMaLSP() + " - " + lsp.getTenLSP());
        }
    }

    private void loadHSXVaoComboBox() {
        List<HangSanXuatDTO> hsxList = hsxBLL.getAllHangSanXuat();
        comboHSX.removeAllItems();
        comboHSX.addItem("-- Chọn hãng sản xuất --");
        for (HangSanXuatDTO hsx : hsxList) {
            comboHSX.addItem(hsx.getMaHSX() + " - " + hsx.getTenHSX());
        }
    }

    public static void main(String[] args) {
        new Themsp();
    }
}

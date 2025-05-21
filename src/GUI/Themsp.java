package GUI;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import dal.SanPhamDAL;
import dto.SanPhamDTO;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

public class Themsp extends BaseFrame {
    private SanPhamDAL spDAL= new SanPhamDAL();
    private JComboBox<String> comboMaLSP, comboHSX;
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

        // Các nút khác vẫn giữ màu mặc định
        for (JButton btn : menuButtons) {
            if (!btn.getText().equals("Sản phẩm")) {
                btn.setBackground(Color.decode("#641A1F")); // Màu mặc định cho các nút khác
                btn.setFont(new Font("Arial", Font.BOLD, 12));
            }
        }
        //Tiêu đề "Sản phẩm >> Thêm sản phẩm" có thể nhấn
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
        JLabel lblTenSP = new JLabel("Tên sản phẩm:");
        lblTenSP.setBounds(270, 120, 150, 25);
        add(lblTenSP);
        JTextField txtTenSP = new JTextField();
        txtTenSP.setBounds(270, 150, 300, 30);
        add(txtTenSP);

        JLabel lblMaSP = new JLabel("Mã sản phẩm:");
        lblMaSP.setBounds(700, 120, 150, 25);
        add(lblMaSP);
        JTextField txtMaSP = new JTextField();
        txtMaSP.setBounds(700, 150, 300, 30);
        add(txtMaSP);

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

        // Nút Lưu
        JButton btnLuu = new JButton("Lưu");
        btnLuu.setBounds(700, 520, 100, 40);
        btnLuu.setBackground(Color.decode("#F0483E"));
        btnLuu.setForeground(Color.WHITE);
        add(btnLuu);

        btnLuu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String maSP = txtMaSP.getText().trim();
                    String tenSP = txtTenSP.getText().trim();
                    String maLSP = comboMaLSP.getSelectedItem().toString().split(" - ")[0];
                    String maHSX = comboHSX.getSelectedItem().toString().split(" - ")[0];
                    double giaBan = Double.parseDouble(txtGia.getText().trim());

                    String quyCachDongGoi = txtQcdg.getText().trim();
                    String soDangKy = txtSodk.getText().trim();  // Trường nhập số đăng ký
                    int soluong = Integer.parseInt(txtSoLuong.getText().trim());  // Trường nhập số lượng tồn kho
                    // Kiểm tra nếu chưa chọn mã loại SP hoặc mã HSX
                    if (maLSP.isEmpty() || maHSX.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "Vui lòng chọn loại sản phẩm và hãng sản xuất!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    // Tạo đối tượng sản phẩm
                    SanPhamDTO sp = new SanPhamDTO(maSP, maHSX, maLSP, tenSP, quyCachDongGoi,  soDangKy, soluong, giaBan);

                    System.out.println("Mã loại sản phẩm lấy từ giao diện: [" + maLSP + "]");

                    // Gọi phương thức thêm sản phẩm vào DB
                    boolean result = spDAL.insertSanPham(sp);

                    // Hiển thị thông báo
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
                        // Mũi tên xuống - chuyển đến trường nhập liệu tiếp theo
                        if (currentIndex < textFields.length - 1) {
                            textFields[currentIndex + 1].requestFocus();
                        }
                    } else if (key == KeyEvent.VK_UP) {
                        // Mũi tên lên - chuyển đến trường nhập liệu trước đó
                        if (currentIndex > 0) {
                            textFields[currentIndex - 1].requestFocus();
                        }
                    }
                }
            });
        }
    }
    private void loadLoaiSanPhamVaoComboBox() {
        List<String[]> loaiSPList = spDAL.getAllLoaiSanPham();
        comboMaLSP.removeAllItems();
        comboMaLSP.addItem("-- Chọn loại sản phẩm --");
        for (String[] row : loaiSPList) {
            comboMaLSP.addItem(row[0] + " - " + row[1]);
        }
    }

    private void loadHSXVaoComboBox() {
        List<String[]> HSXList = spDAL.getAllHangSanXuat();
        comboHSX.removeAllItems();
        comboHSX.addItem("-- Chọn hãng sản xuất --");
        for (String[] row : HSXList) {
            comboHSX.addItem(row[0] + " - " + row[1]);
        }
    }

    public static void main(String[] args) {
        new Themsp();
    }
}

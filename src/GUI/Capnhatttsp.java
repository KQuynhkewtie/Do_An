package GUI;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import bll.SanPhamBLL;

import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import dal.dbconnection;
import dal.SanPhamDAL;
import dto.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.List;
import dto.SanPhamDTO;
import bll.LoaiSPBLL;
import bll.HangSanXuatBLL;

public class Capnhatttsp extends BaseFrame {
    private SanPhamBLL SanPhamBLL = new SanPhamBLL();
    private LoaiSPBLL lspBLL = new LoaiSPBLL();
    private HangSanXuatBLL hsxBLL = new HangSanXuatBLL();
    private JComboBox<String> comboMaLSP, comboHSX;
    private JTextField  txtTenSP, txtMaSP , txtQcdg, txtSodk,  txtGia, txtSoLuong;
    private JRadioButton rbDangban, rbNgungban;
    private ButtonGroup groupTrangThai;
    public Capnhatttsp() {
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

        JLabel lblTTSanPhamLink = new JLabel("<html>>><u> Thông tin sản phẩm</u></html>");
        lblTTSanPhamLink.setFont(new Font("Arial", Font.BOLD, 20));
        lblTTSanPhamLink.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        lblTTSanPhamLink.setBounds(370, 60, 300, 30);
        add(lblTTSanPhamLink);

        JLabel lblArrow = new JLabel(" >> Cập nhật thông tin sản phẩm");
        lblArrow.setFont(new Font("Arial", Font.BOLD, 20));
        lblArrow.setBounds(590, 60, 900, 30);
        add(lblArrow);

        lblSanPhamLink.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                dispose();
                new SanPham();
            }
        });

        lblTTSanPhamLink.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                String maSP =  txtMaSP.getText().trim();
                SanPhamDTO sp = SanPhamBLL.getSanPhamById(maSP);
                if (sp != null) {
                    dispose();
                    TTCTsp  ctsp = new TTCTsp();
                    ctsp.setThongTin( sp.getMaSP(),sp.getTenSP(), sp.getMaLSP(), sp.getsoluong(), sp.getMaHSX(), sp.getQuyCachDongGoi(), sp.getSoDangKy(),sp.getGiaBan(), sp.getTrangThai());
                    ctsp.setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(null, "Không tìm thấy sản phẩm!");
                }
            }
        });


        // Form nhập sản phẩm

        JLabel lblMaSP = new JLabel("Mã sản phẩm:");
        lblMaSP.setBounds(270, 120, 150, 25);
        add(lblMaSP);
        txtMaSP = new JTextField();
        txtMaSP.setBounds(270, 150, 300, 30);
        txtMaSP.setBackground(new Color(230, 230, 230));
        txtMaSP.setEditable(false);
        txtMaSP.setFocusable(false);
        add(txtMaSP);

        JLabel lblTenSP = new JLabel("Tên sản phẩm:");
        lblTenSP.setBounds(700, 120, 150, 25);
        add(lblTenSP);
        txtTenSP = new JTextField();
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
        txtSoLuong = new JTextField();
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
        txtQcdg = new JTextField();
        txtQcdg.setBounds(700, 310, 300, 30);
        add(txtQcdg);


        JLabel lblSodk = new JLabel("Số đăng ký:");
        lblSodk.setBounds(700, 360, 150, 25);
        add(lblSodk);
        txtSodk = new JTextField();
        txtSodk.setBounds(700, 390, 300, 30);
        add(txtSodk);



        JLabel lblGia = new JLabel("Giá bán:");
        lblGia.setBounds(700, 440, 150, 25);
        add(lblGia);
        txtGia = new JTextField();
        txtGia.setBounds(700, 470, 300, 30);
        add(txtGia);
        //Trạng thái

        JLabel lblTrangThai = new JLabel("Trạng thái:");
        lblTrangThai.setBounds(270, 360, 150, 25);
        add(lblTrangThai);

        rbDangban = new JRadioButton("Đang bán");
        rbDangban.setBounds(270, 390, 150, 25);
        rbDangban.setSelected(true);
        add(rbDangban);

        rbNgungban = new JRadioButton("Ngưng bán");
        rbNgungban.setBounds(270, 420, 150, 25);
        add(rbNgungban);

        groupTrangThai = new ButtonGroup();
        groupTrangThai.add(rbDangban);
        groupTrangThai.add(rbNgungban);

        // Nút Lưu
        JButton btnLuusua = new JButton("Lưu");
        btnLuusua.setBounds(700, 520, 100, 40);
        btnLuusua.setBackground(Color.decode("#F0483E"));
        btnLuusua.setForeground(Color.WHITE);
        add(btnLuusua);
        btnLuusua.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!currentuser.coQuyen("Sửa sản phẩm")) {

                    JOptionPane.showMessageDialog(null, "Bạn không có quyền cập nhật!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                String maSP = txtMaSP.getText().trim();
                String tenSP = txtTenSP.getText().trim();
                String maLSP = comboMaLSP.getSelectedItem().toString().split(" - ")[0];
                String maHSX = comboHSX.getSelectedItem().toString().split(" - ")[0];
                String dongGoi = txtQcdg.getText().trim();
                String sodk = txtSodk.getText().trim();
                String soLuongStr = txtSoLuong.getText().trim();
                String giaStr = txtGia.getText().trim();


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
                if (dongGoi.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Vui lòng nhập số lượng!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if (sodk.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Vui lòng nhập số đăng ký!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if (soLuongStr.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Vui lòng nhập số lượng!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if (giaStr.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Vui lòng nhập giá bán!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                double giaBan;
                int soluong;
                try {
                    giaBan = Double.parseDouble(giaStr);
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

                try {
                    int soLuong = Integer.parseInt(soLuongStr);
                    double gia = Double.parseDouble(giaStr);


                    // Tạo đối tượng sản phẩm cập nhật
                    SanPhamDTO sp = new SanPhamDTO();
                    sp.setMaSP(maSP);
                    sp.setTenSP(tenSP);
                    sp.setMaLSP(maLSP);
                    sp.setMaHSX(maHSX);
                    sp.setQuyCachDongGoi(dongGoi);
                    sp.setSoDangKy(sodk);
                    sp.setsoluong(soLuong);
                    sp.setGiaBan(gia);
                    sp.setTrangThai(trangthai);
                    System.out.println("MASP cần cập nhật: " + maSP);

                    boolean success = SanPhamBLL.updateSanPham(sp);
                    if (success) {
                        JOptionPane.showMessageDialog(null, "Cập nhật sản phẩm thành công!");
                        dispose();
                        TTCTsp ctsp = new TTCTsp();
                        ctsp.setThongTin(sp.getTenSP(), sp.getMaSP(), sp.getMaLSP(), sp.getsoluong(), sp.getMaHSX(), sp.getQuyCachDongGoi(),  sp.getSoDangKy(), sp.getGiaBan(), sp.getTrangThai());
                    } else {
                        JOptionPane.showMessageDialog(null, "Cập nhật thất bại!");
                    }

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Lỗi dữ liệu: " + ex.getMessage());
                }
            }
        });
        getRootPane().setDefaultButton(btnLuusua);
        setVisible(true);
    }


    public void loadProductInfoForUpdate(String maSP) {
        SanPhamDTO sp = SanPhamBLL.getSanPhamById(maSP);

        if (sp != null) {
            txtTenSP.setText(sp.getTenSP());
            txtMaSP.setText(sp.getMaSP());
            for (int i = 0; i < comboMaLSP.getItemCount(); i++) {
                if (comboMaLSP.getItemAt(i).startsWith(sp.getMaLSP() + " -")) {
                    comboMaLSP.setSelectedIndex(i);
                    break;
                }
            }

            for (int i = 0; i < comboHSX.getItemCount(); i++) {
                if (comboHSX.getItemAt(i).startsWith(sp.getMaHSX() + " -")) {
                    comboHSX.setSelectedIndex(i);
                    break;
                }
            }
            txtGia.setText(String.valueOf(sp.getGiaBan()));
            txtQcdg.setText(sp.getQuyCachDongGoi());
            txtSodk.setText(sp.getSoDangKy());
            txtSoLuong.setText(String.valueOf(sp.getsoluong()));
            if (sp.getTrangThai() == 1) {
                rbDangban.setSelected(true);
            } else if (sp.getTrangThai() == 0) {
                rbNgungban.setSelected(true);
            } else {
                groupTrangThai.clearSelection();
            }
        } else {
            JOptionPane.showMessageDialog(this, "Không tìm thấy sản phẩm!");
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
}



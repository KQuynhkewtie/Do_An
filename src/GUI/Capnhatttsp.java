package GUI;

import javax.swing.*;

import bll.SanPhamBLL;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import dal.SanPhamDAL;
import dto.SanPhamDTO;

import java.util.List;

public class Capnhatttsp extends BaseFrame {
    private SanPhamBLL bllSanPham = new SanPhamBLL();
	private SanPhamDAL spDAL= new SanPhamDAL();
	private JComboBox<String> comboMaLSP, comboHSX;
	private JTextField  txtTenSP, txtMaSP ,txtMaLSP, txtMaHSX, txtQcdg, txtSodk, txtHSD, txtGia, txtSolo, txtSoLuong;
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
   	        
   	        // Các nút khác vẫn giữ màu mặc định
   	        for (JButton btn : menuButtons) {
   	            if (!btn.getText().equals("Sản phẩm")) {
   	                btn.setBackground(Color.decode("#641A1F")); // Màu mặc định cho các nút khác
   	                btn.setFont(new Font("Arial", Font.BOLD, 12));
   	            }
   	        }
        //Tiêu đề "Sản phẩm >> Thông tin sản phẩm" có thể nhấn
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
                dispose();
                new TTCTsp();
            }
        });

        
        // Form nhập sản phẩm
        JLabel lblTenSP = new JLabel("Tên sản phẩm:");
        lblTenSP.setBounds(270, 120, 150, 25);
        add(lblTenSP);
        txtTenSP = new JTextField();
        txtTenSP.setBounds(270, 150, 300, 30);
        add(txtTenSP);

        JLabel lblMaSP = new JLabel("Mã sản phẩm:");
        lblMaSP.setBounds(700, 120, 150, 25);
        add(lblMaSP);
        txtMaSP = new JTextField();
        txtMaSP.setBounds(700, 150, 300, 30);
        txtMaSP.setBackground(new Color(230, 230, 230));
        txtMaSP.setEditable(false);
        txtMaSP.setFocusable(false);
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

        JLabel lblSolo = new JLabel("Số lô:");
        lblSolo.setBounds(270, 360, 150, 25);
        add(lblSolo);
        txtSolo = new JTextField();
        txtSolo.setBounds(270, 390, 300, 30);
        add(txtSolo);

        JLabel lblSodk = new JLabel("Số đăng ký:");
        lblSodk.setBounds(700, 360, 150, 25);
        add(lblSodk);
        txtSodk = new JTextField();
        txtSodk.setBounds(700, 390, 300, 30);
        add(txtSodk);

        JLabel lblHSD = new JLabel("Hạn sử dụng:");
        lblHSD.setBounds(270, 440, 150, 25);
        add(lblHSD);
        txtHSD = new JTextField();
        txtHSD.setBounds(270, 470, 300, 30);
        add(txtHSD);

        JLabel lblGia = new JLabel("Giá bán:");
        lblGia.setBounds(700, 440, 150, 25);
        add(lblGia);
        txtGia = new JTextField();
        txtGia.setBounds(700, 470, 300, 30);
        add(txtGia);

//        loadProductInfo(maSP);
        // Nút Lưu
        JButton btnLuusua = new JButton("Lưu");
        btnLuusua.setBounds(700, 520, 100, 40);
        btnLuusua.setBackground(Color.decode("#F0483E"));
        btnLuusua.setForeground(Color.WHITE);
        add(btnLuusua);
        btnLuusua.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	
                // Lấy dữ liệu từ các trường nhập
                String maSP = txtMaSP.getText().trim();
                String tenSP = txtTenSP.getText().trim();
                String maLSP = comboMaLSP.getSelectedItem().toString().split(" - ")[0];
                String maHSX = comboHSX.getSelectedItem().toString().split(" - ")[0];
                String dongGoi = txtQcdg.getText().trim();
                String solo = txtSolo.getText().trim();
                String sodk = txtSodk.getText().trim();
                String hsdStr = txtHSD.getText().trim();
                String soLuongStr = txtSoLuong.getText().trim();
                String giaStr = txtGia.getText().trim();
                
                try {
                    int soLuong = Integer.parseInt(soLuongStr);
                    double gia = Double.parseDouble(giaStr);
                    java.sql.Date hsd = java.sql.Date.valueOf(hsdStr); // định dạng yyyy-MM-dd

                    // Tạo đối tượng sản phẩm cập nhật
                    SanPhamDTO sp = new SanPhamDTO();
                    sp.setMaSP(maSP);
                    sp.setTenSP(tenSP);
                    sp.setMaLSP(maLSP);
                    sp.setMaHSX(maHSX);
                    sp.setQuyCachDongGoi(dongGoi);
                    sp.setSoLo(solo);
                    sp.setSoDangKy(sodk);
                    sp.setHangTon(soLuong);
                    sp.setGiaBan(gia);
                    sp.setHsd(hsd);
                    System.out.println("MASP cần cập nhật: " + maSP);
                    
                    // Gọi hàm cập nhật từ BLL/DAL
                    boolean success = bllSanPham.updateSanPham(sp);

                    if (success) {
                        JOptionPane.showMessageDialog(null, "Cập nhật sản phẩm thành công!");
                        dispose(); // đóng form nếu muốn
                        TTCTsp ctsp = new TTCTsp();
                        ctsp.setThongTin(sp.getTenSP(), sp.getMaSP(), sp.getMaLSP(), sp.getHangTon(), sp.getMaHSX(), sp.getQuyCachDongGoi(), sp.getSoLo(), sp.getSoDangKy(), sp.getHsd().toString(), sp.getGiaBan());
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
        // Lấy thông tin sản phẩm từ cơ sở dữ liệu (tương tự như trang chi tiết sản phẩm)
        SanPhamDTO sp = bllSanPham.getSanPhamById(maSP);

        if (sp != null) {
            // Điền thông tin vào các trường nhập liệu trong trang cập nhật thông tin sản phẩm
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
            txtSolo.setText(sp.getSoLo());
            txtSodk.setText(sp.getSoDangKy());
            txtSoLuong.setText(String.valueOf(sp.getHangTon()));
            txtHSD.setText(sp.getHsd().toString());
        } else {
            JOptionPane.showMessageDialog(this, "Không tìm thấy sản phẩm!");
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

}



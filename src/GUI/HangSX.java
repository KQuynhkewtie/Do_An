package GUI;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import bll.HangSanXuatBLL;
import dto.HangSanXuatDTO;

import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HangSX extends BaseFrame {
	 private HangSanXuatBLL bllhsx = new HangSanXuatBLL();
	 private DefaultTableModel model;
	 private JTable table;
	 private JTextField searchhsxField;

    public HangSX() {
    	super("HangSX");
    	initialize();
    	}


    @Override
    	protected void initUniqueComponents() {
    		 for (JButton btn : menuButtons) {
    	            if (btn.getText().equals("Hãng sản xuất")) {
    	                btn.setBackground(Color.decode("#EF5D7A")); 
    	                btn.setFont(new Font("Arial", Font.BOLD, 14)); 
    	            }
    	        }
    	        
    	        // Các nút khác vẫn giữ màu mặc định
    	        for (JButton btn : menuButtons) {
    	            if (!btn.getText().equals("Hãng sản xuất")) {
    	                btn.setBackground(Color.decode("#641A1F")); // Màu mặc định cho các nút khác
    	                btn.setFont(new Font("Arial", Font.BOLD, 12));
    	            }
    	        }
        
            JLabel lblSanPham = new JLabel("Hãng sản xuất");
            lblSanPham.setFont(new Font("Arial", Font.BOLD, 20));
            lblSanPham.setBounds(270, 70, 200, 30);
            add(lblSanPham);

            
        searchhsxField = new JTextField("Tìm kiếm hãng sản xuất");
        searchhsxField.setBounds(270, 110, 300, 35);
        add( searchhsxField);

        // Thêm sự kiện focus
        searchhsxField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (searchhsxField.getText().equals("Tìm kiếm hãng sản xuất")) {
                	searchhsxField.setText("");
                	searchhsxField.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (searchhsxField.getText().trim().isEmpty()) {
                	searchhsxField.setText("Tìm kiếm hãng sản xuất");
                	searchhsxField.setForeground(Color.GRAY);
                	loadHSX();
                }
            }
        });
        searchhsxField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                String keyword = searchhsxField.getText().trim();
                if (keyword.isEmpty()) {
                    loadHSX(); // Tự động hiển thị lại danh sách sản phẩm khi ô tìm kiếm trống
                } else {
                    searchHSX(); // Tiến hành tìm kiếm nếu có từ khóa
                }
            }
        });

        

        JButton btnThemHSX = new JButton("+ Thêm hãng sản xuất");
        btnThemHSX.setBounds(820, 110, 170, 30);
        btnThemHSX.setBackground(Color.decode("#F0483E"));
        btnThemHSX.setForeground(Color.WHITE);
        add(btnThemHSX);

        // Table
        String[] columnNames = { "Mã hãng sản xuất", "Tên hãng sản xuất", "Mã số thuế", "Địa chỉ", "Số điện thoại" };
        model = new DefaultTableModel(columnNames, 0);
        table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(270, 180, 720, 400);
        add(scrollPane);
        

        loadHSX();

        // chuyển sang giao diện thêm hãng sản xuất
        btnThemHSX.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new ThemHSX();
            }
        });
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 1) {
                    int row = table.getSelectedRow();
                    if (row >= 0) {
                        String maHSX = table.getValueAt(row, 0).toString();
                        HangSanXuatDTO hsx = bllhsx.getHSXbyID(maHSX);
                        if (hsx != null) {
                            dispose();
                            TTCTHSX cthsx = new TTCTHSX();
                            cthsx.setThongTin(hsx.getMaHSX(), hsx.getTenHSX(), hsx.getMaSoThue(), hsx.getDiaChi(), hsx.getSdt());
                        } else {
                            JOptionPane.showMessageDialog(null, "Không tìm thấy hãng sản xuất!");
                        }
                    }
                }
            }
        });

        setVisible(true);

        addExceltButton();
    }
    private void loadHSX() {
        model.setRowCount(0); // Xóa tất cả các dòng trong bảng
        List<HangSanXuatDTO> list = bllhsx.getAllHangSanXuat();
        
        for (HangSanXuatDTO hsx : list) {
            
            model.addRow(new Object[]{hsx.getMaHSX(), hsx.getTenHSX(), hsx.getMaSoThue(), hsx.getDiaChi(), hsx.getSdt()});
        }
    }

    private void searchHSX() {
    	String keyword = searchhsxField.getText().trim();// Lấy từ khóa tìm kiếm từ ô nhập liệu
        if (keyword.equals("Tìm") || keyword.isEmpty()) {
            loadHSX();
            return;
        }

        model.setRowCount(0);
        List<HangSanXuatDTO> list = bllhsx.getHSX(keyword);
        
        for (HangSanXuatDTO hsx : list) {
            model.addRow(new Object[]{hsx.getMaHSX(), hsx.getTenHSX(), hsx.getMaSoThue(), hsx.getDiaChi(), hsx.getSdt()});
        }
    }

    @Override
    protected void addExceltButton() {
        super.addExceltButton();
    }

    public static void main(String[] args) {
        new HangSX();
    }
}

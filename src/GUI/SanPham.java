package GUI;


import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.List;
import dto.SanPhamDTO;
import bll.SanPhamBLL;


public class SanPham extends BaseFrame {
    private SanPhamBLL bllSanPham = new SanPhamBLL();
    private DefaultTableModel model;
    private JTable table;
    private JTextField searchproductField;

    public SanPham() {
    	super("Sản phẩm");
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
        
        JLabel lblSanPham = new JLabel("Sản phẩm", SwingConstants.LEFT);
        lblSanPham.setFont(new Font("Arial", Font.BOLD, 20));
        lblSanPham.setBounds(270, 60, 200, 30);
        add(lblSanPham);

        searchproductField = new JTextField("Tìm kiếm sản phẩm");
        searchproductField.setBounds(270, 110, 300, 35);
        add(searchproductField);
        
        searchproductField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (searchproductField.getText().equals("Tìm kiếm sản phẩm")) {
                    searchproductField.setText("");
                    searchproductField.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (searchproductField.getText().trim().isEmpty()) {
                    searchproductField.setText("Tìm kiếm sản phẩm");
                    searchproductField.setForeground(Color.GRAY);
                    loadSanPham();
                }
            }
        });
        searchproductField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                String keyword = searchproductField.getText().trim();
                if (keyword.isEmpty()) {
                    loadSanPham(); 
                } else {
                    searchSanPham();
                }
            }
        });
        

        JButton btnThemSP = new JButton("+ Thêm sản phẩm");
        btnThemSP.setBounds(900, 110, 140, 30);
        btnThemSP.setBackground(Color.decode("#F0483E"));
        btnThemSP.setForeground(Color.WHITE);
        add(btnThemSP);
        
        // Table
        String[] columnNames = {"Tên sản phẩm", "Mã sản phẩm", "Loại sản phẩm", "Số lượng", "Hạn sử dụng"};
        model = new DefaultTableModel(columnNames, 0);
        table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(270, 180, 720, 400);
        add(scrollPane);


        
        // Hiển thị danh sách sản phẩm từ database
        loadSanPham();        
        
        btnThemSP.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // Đóng cửa sổ hiện tại
                new Themsp(); // Mở giao diện Thêm sản phẩm
            }
        });
        
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 1) {
                    int row = table.getSelectedRow();
                    if (row >= 0) {
                        String maSanPham = table.getValueAt(row, 1).toString();
                        SanPhamDTO sp = bllSanPham.getSanPhamById(maSanPham);
                        if (sp != null) {
                            dispose();
                            TTCTsp ctsp = new TTCTsp();
                            ctsp.setThongTin(sp.getTenSP(), sp.getMaSP(), sp.getMaLSP(), sp.getHangTon(), sp.getMaHSX(), sp.getQuyCachDongGoi(), sp.getSoLo(), sp.getSoDangKy(), sp.getHsd().toString(), sp.getGiaBan());
                        } else {
                            JOptionPane.showMessageDialog(null, "Không tìm thấy sản phẩm!");
                        }
                    }
                }
            }
        });

        
        setVisible(true);

        addExceltButton();
    
    }
    
   

    private void loadSanPham() {
        model.setRowCount(0); // Xóa tất cả các dòng trong bảng
        List<SanPhamDTO> list = bllSanPham.getAllSanPham();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

        for (SanPhamDTO sp : list) {
            String hsdStr = (sp.getHsd() != null) ? sdf.format(sp.getHsd()) : "N/A";
            model.addRow(new Object[]{sp.getTenSP(), sp.getMaSP(), sp.getMaLSP(), sp.getHangTon(), hsdStr});
        }
    }

    private void searchSanPham() {
    	String keyword = searchproductField.getText().trim();// Lấy từ khóa tìm kiếm từ ô nhập liệu
        if (keyword.equals("Tìm") || keyword.isEmpty()) {
            loadSanPham();
            return;
        }

        model.setRowCount(0);
        List<SanPhamDTO> list = bllSanPham.getSanPham(keyword);
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

        for (SanPhamDTO sp : list) {
            String hsdStr = (sp.getHsd() != null) ? sdf.format(sp.getHsd()) : "N/A";
            model.addRow(new Object[]{sp.getTenSP(), sp.getMaSP(), sp.getMaLSP(), sp.getHangTon(), hsdStr});
        }
    }

    @Override
    protected void addExceltButton() {
        super.addExceltButton();
    }

    public static void main(String[] args) {
        new SanPham().setVisible(true);;
   }
}


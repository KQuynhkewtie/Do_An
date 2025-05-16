package GUI;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import bll.LoaiSPBLL;
import dto.LoaiSPDTO;

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

public class LoaiSP extends BaseFrame {
	private LoaiSPBLL bllloaisp = new LoaiSPBLL();
	private DefaultTableModel model;
	private JTable table;
	private JTextField searchlspField;
	public LoaiSP() {
    	super("Loại sản phẩm");
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
 	        
 	        // Các nút khác vẫn giữ màu mặc định
 	        for (JButton btn : menuButtons) {
 	            if (!btn.getText().equals("Loại sản phẩm")) {
 	                btn.setBackground(Color.decode("#641A1F")); // Màu mặc định cho các nút khác
 	                btn.setFont(new Font("Arial", Font.BOLD, 12));
 	            }
 	        }
		
        JLabel lblLoaiSanPham = new JLabel("Loại sản phẩm", SwingConstants.LEFT);
        lblLoaiSanPham.setFont(new Font("Arial", Font.BOLD, 20));
        lblLoaiSanPham.setBounds(270, 60, 200, 30);
        add(lblLoaiSanPham);

      
        searchlspField = new JTextField("Tìm kiếm loại sản phẩm");
        searchlspField.setBounds(270, 110, 300, 35);
        add(searchlspField);

        // Thêm sự kiện focus
        searchlspField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (searchlspField.getText().equals("Tìm kiếm loại sản phẩm")) {
                	searchlspField.setText("");
                	searchlspField.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (searchlspField.getText().trim().isEmpty()) {
                	searchlspField.setText("Tìm kiếm loại sản phẩm");
                	searchlspField.setForeground(Color.GRAY);
                	loadLSP();
                }
            }
        });

        searchlspField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                String keyword = searchlspField.getText().trim();
                if (keyword.isEmpty()) {
                    loadLSP(); // Tự động hiển thị lại danh sách sản phẩm khi ô tìm kiếm trống
                } else {
                    searchLSP(); // Tiến hành tìm kiếm nếu có từ khóa
                }
            }
        });
        

        JButton btnThemLSP = new JButton("+ Thêm loại sản phẩm");
        btnThemLSP.setBounds(820, 110, 170, 30);
        btnThemLSP.setBackground(Color.decode("#F0483E"));
        btnThemLSP.setForeground(Color.WHITE);
        add(btnThemLSP);

        // Table
        String[] columnNames = {"Tên loại sản phẩm", "Mã loại sản phẩm"};
        model = new DefaultTableModel(columnNames, 0);
        table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(270, 180, 720, 400);
        add(scrollPane);


        loadLSP();

        //chuyển sang giao diện thêm loại sản phẩm
        btnThemLSP.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); 
                new Themloaisp(); 
            }
        });
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 1) {
                    int row = table.getSelectedRow();
                    if (row >= 0) {
                        String maLSP = table.getValueAt(row, 1).toString();
                        LoaiSPDTO lsp = bllloaisp.getLSPById(maLSP);
                        if (lsp != null) {
                            TTCTloaisp ctlsp = new TTCTloaisp();
                            ctlsp.setThongTin(lsp.getTenLSP(), lsp.getMaLSP());
                            
                        } else {
                            JOptionPane.showMessageDialog(null, "Không tìm thấy loại sản phẩm!");
                        }
                    }
                }
            }
        });
        setVisible(true);

         addExceltButton();
    }
    
    private void loadLSP() {
        model.setRowCount(0); // Xóa tất cả các dòng trong bảng
        List<LoaiSPDTO> list = bllloaisp.layDSLSP();
        for (LoaiSPDTO lsp : list) {
            model.addRow(new Object[]{lsp.getTenLSP(), lsp.getMaLSP()});
        }
    }

    private void searchLSP() {
        String keyword = searchlspField.getText().trim(); // Lấy từ khóa tìm kiếm từ ô nhập liệu
        if (keyword.equals("Tìm") || keyword.isEmpty()) {
            loadLSP();
            return;
        }

        model.setRowCount(0);
        List<LoaiSPDTO> list = bllloaisp.getLSP(keyword);
 
        for (LoaiSPDTO lsp : list) {
            model.addRow(new Object[]{lsp.getTenLSP(), lsp.getMaLSP()});
        }
//        System.out.println("Từ khóa tìm kiếm: " + keyword);
//        System.out.println("Số loại sản phẩm tìm thấy: " + list.size());
    }

    @Override
    protected void addExceltButton() {
        super.addExceltButton();
    }

    public static void main(String[] args) {
        new LoaiSP();
    }
}


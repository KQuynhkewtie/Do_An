package GUI;


import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import bll.NhaCungUngBLL;
import dto.NhaCungUngDTO;
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

public class Nhacungung extends BaseFrame {
	private NhaCungUngBLL bllncu = new NhaCungUngBLL();
	private DefaultTableModel model;
	private JTable table;
	private JTextField searchncuField;
    public Nhacungung() {
    	super("Nhà cung ứng");
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
	        
	        // Các nút khác vẫn giữ màu mặc định
	        for (JButton btn : menuButtons) {
	            if (!btn.getText().equals("Nhà cung ứng")) {
	                btn.setBackground(Color.decode("#641A1F")); // Màu mặc định cho các nút khác
	                btn.setFont(new Font("Arial", Font.BOLD, 12));
	            }
	        }
       
            JLabel lblNCU = new JLabel("Nhà cung ứng");
            lblNCU.setFont(new Font("Arial", Font.BOLD, 20));
            lblNCU.setBounds(270, 60, 200, 30);
            add(lblNCU);

            
        searchncuField = new JTextField("Tìm kiếm nhà cung ứng");
        searchncuField.setBounds(270, 110, 300, 35);
        add(searchncuField);
        // Thêm sự kiện focus
        searchncuField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (searchncuField.getText().equals("Tìm kiếm nhà cung ứng")) {
                	searchncuField.setText("");
                	searchncuField.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (searchncuField.getText().trim().isEmpty()) {
                	searchncuField.setText("Tìm kiếm nhà cung ứng");
                	searchncuField.setForeground(Color.GRAY);
                	 loadNCU();
                }
            }
        });
        searchncuField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                String keyword = searchncuField.getText().trim();
                if (keyword.isEmpty()) {
                    loadNCU(); // Tự động hiển thị lại danh sách sản phẩm khi ô tìm kiếm trống
                } else {
                    searchNCU(); // Tiến hành tìm kiếm nếu có từ khóa
                }
            }
        });
        

        JButton btnThemNCU = new JButton("+ Thêm nhà cung ứng");
        btnThemNCU.setBounds(880, 110, 170, 30);
        btnThemNCU.setBackground(Color.decode("#F0483E"));
        btnThemNCU.setForeground(Color.WHITE);
        add(btnThemNCU);

        String[] columnNames = { "Tên nhà cung ứng", "Mã nhà cung ứng", "Mã số thuế", "Địa chỉ", "Số điện thoại", "Email" };
        model = new DefaultTableModel(columnNames, 0);
        table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(270, 150, 800, 400);
        add(scrollPane);


        loadNCU();
        
        btnThemNCU.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); 
                new Themncu(); 
            }
        });
        
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 1) {
                    int row = table.getSelectedRow();
                    if (row >= 0) {
                        String maNCU = table.getValueAt(row, 1).toString();
                        NhaCungUngDTO ncu = bllncu.getNCUById(maNCU);
                        if (ncu != null) {
                            TTCTncu ctlsp = new TTCTncu();
                            ctlsp.setThongTin(ncu.getTenNCU(), ncu.getMaNCU(), ncu.getMaSoThue(), ncu.getDiaChi(), ncu.getSdt(), ncu.getEmail());
                            
                        } else {
                            JOptionPane.showMessageDialog(null, "Không tìm thấy nhà cung ứng!");
                        }
                    }
                }
            }
        });
        setVisible(true);

        //addExceltButton();
    }
    private void loadNCU() {
        model.setRowCount(0); // Xóa tất cả các dòng trong bảng
        List<NhaCungUngDTO> list = bllncu.layDSNCU();
        for (NhaCungUngDTO ncu : list) {
            model.addRow(new Object[]{ncu.getTenNCU(), ncu.getMaNCU(), ncu.getMaSoThue(), ncu.getDiaChi(), ncu.getSdt(), ncu.getEmail()});
        }
    }

    private void searchNCU() {
        String keyword = searchncuField.getText().trim(); // Lấy từ khóa tìm kiếm từ ô nhập liệu
        if (keyword.equals("Tìm") || keyword.isEmpty()) {
            loadNCU();
            return;
        }

        model.setRowCount(0);
        List<NhaCungUngDTO> list = bllncu.getNCU(keyword);
 
        for (NhaCungUngDTO ncu : list) {
            model.addRow(new Object[]{ncu.getTenNCU(), ncu.getMaNCU(), ncu.getMaSoThue(), ncu.getDiaChi(), ncu.getSdt(), ncu.getEmail()});
        }
        System.out.println("Từ khóa tìm kiếm: " + keyword);
        System.out.println("Số ncu tìm thấy: " + list.size());
    }

//    @Override
//    protected void addExceltButton() {
//        super.addExceltButton();
//    }

    public static void main(String[] args) {
        new Nhacungung();
    }
} 
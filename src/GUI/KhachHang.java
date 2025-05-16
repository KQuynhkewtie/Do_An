package GUI;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import dto.KhachHangDTO;
import bll.KhachHangBLL;


public class KhachHang extends BaseFrame{
	private KhachHangBLL bllkhachhang = new KhachHangBLL();
	private DefaultTableModel model;
	private JTable table;
	private JTextField searchcustomerField;
    public KhachHang() {
    	super("Khách hàng");
    	initialize();
    	} 
    @Override
	protected void initUniqueComponents() {
		 for (JButton btn : menuButtons) {
	            if (btn.getText().equals("Khách hàng")) {
	                btn.setBackground(Color.decode("#EF5D7A")); 
	                btn.setFont(new Font("Arial", Font.BOLD, 14)); 
	            }
	        }
	        
	        // Các nút khác vẫn giữ màu mặc định
	        for (JButton btn : menuButtons) {
	            if (!btn.getText().equals("Khách hàng")) {
	                btn.setBackground(Color.decode("#641A1F")); // Màu mặc định cho các nút khác
	                btn.setFont(new Font("Arial", Font.BOLD, 12));
	            }
	        }
    
  
       JLabel lblKH = new JLabel("Khách hàng", SwingConstants.LEFT);
       lblKH.setFont(new Font("Arial", Font.BOLD, 20));
       lblKH.setBounds(270, 60, 200, 30);
       add(lblKH);
            
            
           
        searchcustomerField = new JTextField("Tìm kiếm khách hàng");
        searchcustomerField.setBounds(270, 110, 300, 35);
        add(searchcustomerField);
        

        searchcustomerField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (searchcustomerField.getText().equals("Tìm kiếm khách hàng")) {
                	searchcustomerField.setText("");
                	searchcustomerField.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (searchcustomerField.getText().trim().isEmpty()) {
                	searchcustomerField.setText("Tìm kiếm khách hàng");
                	searchcustomerField.setForeground(Color.GRAY);
                    loadKhachHang();
                }
            }
        });
        searchcustomerField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                String keyword = searchcustomerField.getText().trim();
                if (keyword.isEmpty()) {
                    loadKhachHang(); // Tự động hiển thị lại danh sách sản phẩm khi ô tìm kiếm trống
                } else {
                    searchKhachHang(); // Tiến hành tìm kiếm nếu có từ khóa
                }
            }
        });
        
        

        JButton btnThemKH = new JButton("+ Thêm Khách hàng");
        btnThemKH.setBounds(870, 110, 180, 30);
        btnThemKH.setBackground(Color.decode("#F0483E"));
        btnThemKH.setForeground(Color.WHITE);
        add(btnThemKH);

        
        String[] columnNames = {"Tên khách hàng", "Mã khách hàng", "Số lần mua", "Điểm tích lũy", "Loại khách hàng"};
        model = new DefaultTableModel(columnNames, 0);
        table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(270, 180, 720, 400);
        add(scrollPane);

     // Hiển thị danh sách khách hàng từ database
        loadKhachHang();

        //chuyển sang giao diện thêm khách hàng
        btnThemKH.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // Đóng cửa sổ hiện tại
                new Themkh(); // Mở giao diện thêm khách hàng
            }
        });
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 1) {
                    int row = table.getSelectedRow();
                    if (row >= 0) {
                        String maKhachHang = table.getValueAt(row, 1).toString();
                        KhachHangDTO kh = bllkhachhang.getKhachHangById(maKhachHang);
                        if (kh != null) {
                            TTCTkh ctkh = new TTCTkh();
                            ctkh.setThongTin(kh.getHoTen(), kh.getMaKH(), kh.getSoLanMua(), kh.getDiemTichLuy(), kh.getLoaiKhach());
                            
                        } else {
                            JOptionPane.showMessageDialog(null, "Không tìm thấy khách hàng!");
                        }
                    }
                }
            }
        });
        setVisible(true);

        addExceltButton();
    }

    private void loadKhachHang() {
        model.setRowCount(0); // Xóa tất cả các dòng trong bảng
        List<KhachHangDTO> list = bllkhachhang.layDSKhachHang();
        for (KhachHangDTO kh : list) {
            model.addRow(new Object[]{kh.getHoTen(), kh.getMaKH(), kh.getSoLanMua(), kh.getDiemTichLuy(), kh.getLoaiKhach()});
        }
    }

    private void searchKhachHang() {
        String keyword = searchcustomerField.getText().trim(); // Lấy từ khóa tìm kiếm từ ô nhập liệu
        if (keyword.equals("Tìm") || keyword.isEmpty()) {
            loadKhachHang();
            return;
        }

        model.setRowCount(0);
        List<KhachHangDTO> list = bllkhachhang.getKhachHang(keyword);
 
        for (KhachHangDTO kh : list) {
            model.addRow(new Object[]{kh.getHoTen(), kh.getMaKH(), kh.getSoLanMua(), kh.getDiemTichLuy(), kh.getLoaiKhach()});
        }
        System.out.println("Từ khóa tìm kiếm: " + keyword);
        System.out.println("Số khách hàng tìm thấy: " + list.size());
    }

    @Override
    protected void addExceltButton() {
        super.addExceltButton();
    }

    public static void main(String[] args) {
        new KhachHang();
    }
}
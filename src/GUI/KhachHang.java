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

        for (JButton btn : menuButtons) {
            if (!btn.getText().equals("Khách hàng")) {
                btn.setBackground(Color.decode("#641A1F"));
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
                    loadKhachHang();
                } else {
                    searchKhachHang();
                }
            }
        });



        JButton btnThemKH = new JButton("+ Thêm Khách hàng");
        btnThemKH.setBounds(870, 110, 180, 30);
        btnThemKH.setBackground(Color.decode("#F0483E"));
        btnThemKH.setForeground(Color.WHITE);
        add(btnThemKH);


        String[] columnNames = { "Mã khách hàng", "Tên khách hàng", "Điểm tích lũy", "Loại khách hàng", "SĐT"};
        model = new DefaultTableModel(columnNames, 0);
        table = new JTable(model);
        table.getTableHeader().setPreferredSize(new Dimension(0, 35));
        table.setRowHeight(30);
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
        table.setFont(new Font("Arial", Font.PLAIN, 14));
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(270, 150, 800, 500);
        add(scrollPane);

        addExceltButton(table);

        loadKhachHang();


        btnThemKH.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new Themkh();
            }
        });
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 1) {
                    int row = table.getSelectedRow();
                    if (row >= 0) {
                        String maKhachHang = table.getValueAt(row, 0).toString();
                        KhachHangDTO kh = bllkhachhang.getKhachHangById(maKhachHang);
                        if (kh != null) {
                            dispose();
                            TTCTkh ctkh = new TTCTkh();
                            ctkh.setThongTin( kh.getMaKH(), kh.getHoTen(), kh.getDiemTichLuy(), kh.getLoaiKhach(),kh.getSdt());

                        } else {
                            JOptionPane.showMessageDialog(null, "Không tìm thấy khách hàng!");
                        }
                    }
                }
            }
        });
        setVisible(true);
    }

    private void loadKhachHang() {
        model.setRowCount(0);
        List<KhachHangDTO> list = bllkhachhang.getAllKhachHang();
        for (KhachHangDTO kh : list) {
            model.addRow(new Object[]{ kh.getMaKH(), kh.getHoTen(),kh.getDiemTichLuy(), kh.getLoaiKhach(), kh.getSdt()});
        }
    }

    private void searchKhachHang() {
        String keyword = searchcustomerField.getText().trim();
        if (keyword.equals("Tìm") || keyword.isEmpty()) {
            loadKhachHang();
            return;
        }

        model.setRowCount(0);
        List<KhachHangDTO> list = bllkhachhang.getKhachHang(keyword);

        for (KhachHangDTO kh : list) {
            model.addRow(new Object[]{ kh.getMaKH(),kh.getHoTen(), kh.getDiemTichLuy(), kh.getLoaiKhach(),kh.getSdt()});
        }
        System.out.println("Từ khóa tìm kiếm: " + keyword);
        System.out.println("Số khách hàng tìm thấy: " + list.size());
    }
    public static void main(String[] args) {
        new KhachHang();
    }
}
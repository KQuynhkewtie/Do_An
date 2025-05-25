package GUI;


import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
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
        for (JButton btn : menuButtons) {
            if (!btn.getText().equals("Sản phẩm")) {
                btn.setBackground(Color.decode("#641A1F"));
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
        String[] columnNames = {"Mã sản phẩm", "Tên sản phẩm", "Loại sản phẩm", "Số lượng"};
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

        loadSanPham();

        btnThemSP.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new Themsp();
            }
        });

        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 1) {
                    int row = table.getSelectedRow();
                    if (row >= 0) {
                        String maSanPham = table.getValueAt(row, 0).toString();
                        SanPhamDTO sp = bllSanPham.getSanPhamById(maSanPham);
                        if (sp != null) {
                            dispose();
                            TTCTsp ctsp = new TTCTsp();
                            ctsp.setThongTin(sp.getMaSP(),sp.getTenSP(),  sp.getMaLSP(), sp.getsoluong(), sp.getMaHSX(), sp.getQuyCachDongGoi(),sp.getSoDangKy(),  sp.getGiaBan(), sp.getTrangThai());

                        } else {
                            JOptionPane.showMessageDialog(null, "Không tìm thấy sản phẩm!");
                        }
                    }
                }
            }
        });


        setVisible(true);

    }



    private void loadSanPham() {
        model.setRowCount(0);
        List<SanPhamDTO> list = bllSanPham.getAllSanPham();
        for (SanPhamDTO sp : list) {
            model.addRow(new Object[]{ sp.getMaSP(),sp.getTenSP(), sp.getMaLSP(), sp.getsoluong(),});
        }
    }

    private void searchSanPham() {
        String keyword = searchproductField.getText().trim();
        if (keyword.equals("Tìm") || keyword.isEmpty()) {
            loadSanPham();
            return;
        }

        model.setRowCount(0);
        List<SanPhamDTO> list = bllSanPham.getSanPham(keyword);

        for (SanPhamDTO sp : list) {
            model.addRow(new Object[]{ sp.getMaSP(),sp.getTenSP(), sp.getMaLSP(), sp.getsoluong()});
        }
    }


    public static void main(String[] args) {
        new SanPham().setVisible(true);;
    }
}


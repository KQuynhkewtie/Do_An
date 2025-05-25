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
                    loadLSP();
                } else {
                    searchLSP();
                }
            }
        });


        JButton btnThemLSP = new JButton("+ Thêm loại sản phẩm");
        btnThemLSP.setBounds(820, 110, 170, 30);
        btnThemLSP.setBackground(Color.decode("#F0483E"));
        btnThemLSP.setForeground(Color.WHITE);
        add(btnThemLSP);

        // Table
        String[] columnNames = {"Mã loại sản phẩm", "Tên loại sản phẩm"};
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
        loadLSP();


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
                        String maLSP = table.getValueAt(row, 0).toString();
                        LoaiSPDTO lsp = bllloaisp.getLSPById(maLSP);
                        if (lsp != null) {
                            dispose();
                            TTCTloaisp ctlsp = new TTCTloaisp();
                            ctlsp.setThongTin( lsp.getMaLSP(),lsp.getTenLSP(), lsp.getTrangThai());

                        } else {
                            JOptionPane.showMessageDialog(null, "Không tìm thấy loại sản phẩm!");
                        }
                    }
                }
            }
        });
        setVisible(true);
    }

    private void loadLSP() {
        model.setRowCount(0);
        List<LoaiSPDTO> list = bllloaisp.getAllLSP();
        for (LoaiSPDTO lsp : list) {
            model.addRow(new Object[]{lsp.getMaLSP(),lsp.getTenLSP()});
        }
    }

    private void searchLSP() {
        String keyword = searchlspField.getText().trim();
        if (keyword.equals("Tìm") || keyword.isEmpty()) {
            loadLSP();
            return;
        }

        model.setRowCount(0);
        List<LoaiSPDTO> list = bllloaisp.getLSP(keyword);

        for (LoaiSPDTO lsp : list) {
            model.addRow(new Object[]{lsp.getMaLSP(),lsp.getTenLSP() });
        }

    }

    public static void main(String[] args) {
        new LoaiSP();
    }
}


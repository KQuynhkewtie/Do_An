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
                    loadNCU();
                } else {
                    searchNCU();
                }
            }
        });


        JButton btnThemNCU = new JButton("+ Thêm nhà cung ứng");
        btnThemNCU.setBounds(880, 110, 170, 30);
        btnThemNCU.setBackground(Color.decode("#F0483E"));
        btnThemNCU.setForeground(Color.WHITE);
        add(btnThemNCU);

        String[] columnNames = { "Mã nhà cung ứng", "Tên nhà cung ứng", "Mã số thuế", "Địa chỉ", "Số điện thoại", "Email" };
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
                        String maNCU = table.getValueAt(row, 0).toString();
                        NhaCungUngDTO ncu = bllncu.getNCUById(maNCU);
                        if (ncu != null) {
                            dispose();
                            TTCTncu ctlsp = new TTCTncu();
                            ctlsp.setThongTin(ncu.getMaNCU(),ncu.getTenNCU(),  ncu.getMaSoThue(), ncu.getDiaChi(), ncu.getSdt(), ncu.getEmail(), ncu.getTrangThai());

                        } else {
                            JOptionPane.showMessageDialog(null, "Không tìm thấy nhà cung ứng!");
                        }
                    }
                }
            }
        });
        setVisible(true);
    }
    private void loadNCU() {
        model.setRowCount(0);
        List<NhaCungUngDTO> list = bllncu.getAllNCU();
        for (NhaCungUngDTO ncu : list) {
            model.addRow(new Object[]{ ncu.getMaNCU(),ncu.getTenNCU(), ncu.getMaSoThue(), ncu.getDiaChi(), ncu.getSdt(), ncu.getEmail()});
        }
    }

    private void searchNCU() {
        String keyword = searchncuField.getText().trim();
        if (keyword.equals("Tìm") || keyword.isEmpty()) {
            loadNCU();
            return;
        }

        model.setRowCount(0);
        List<NhaCungUngDTO> list = bllncu.getNCU(keyword);

        for (NhaCungUngDTO ncu : list) {
            model.addRow(new Object[]{ncu.getMaNCU(),ncu.getTenNCU(),  ncu.getMaSoThue(), ncu.getDiaChi(), ncu.getSdt(), ncu.getEmail()});
        }
        System.out.println("Từ khóa tìm kiếm: " + keyword);
        System.out.println("Số ncu tìm thấy: " + list.size());
    }

    public static void main(String[] args) {
        new Nhacungung();
    }
} 
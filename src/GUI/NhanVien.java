package GUI;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import bll.NhanVienBLL;
import dto.NhanVienDTO;

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
import java.util.Map;

public class NhanVien extends BaseFrame {
    private NhanVienBLL bllnv = new NhanVienBLL();
    private DefaultTableModel model;
    private JTable table;
    private JTextField searchnvField;
    private Map<String, String> viTriMap;
    public NhanVien() {
        super("Nhân viên");
        initialize();
    }
    @Override
    protected void initUniqueComponents() {
        for (JButton btn : menuButtons) {
            if (btn.getText().equals("Nhân viên")) {
                btn.setBackground(Color.decode("#EF5D7A"));
                btn.setFont(new Font("Arial", Font.BOLD, 14));
            }
        }


        for (JButton btn : menuButtons) {
            if (!btn.getText().equals("Nhân viên")) {
                btn.setBackground(Color.decode("#641A1F"));
                btn.setFont(new Font("Arial", Font.BOLD, 12));
            }
        }

        JLabel lblNhanVien = new JLabel("Nhân viên", SwingConstants.LEFT);
        lblNhanVien.setFont(new Font("Arial", Font.BOLD, 20));
        lblNhanVien.setBounds(270, 60, 200, 30);
        add(lblNhanVien);


        searchnvField = new JTextField("Tìm kiếm nhân viên");
        searchnvField.setBounds(270, 110, 300, 35);
        add(searchnvField);
        // Thêm sự kiện focus
        searchnvField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (searchnvField.getText().equals("Tìm kiếm nhân viên")) {
                    searchnvField.setText("");
                    searchnvField.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (searchnvField.getText().trim().isEmpty()) {
                    searchnvField.setText("Tìm kiếm nhân viên");
                    searchnvField.setForeground(Color.GRAY);
                    loadNhanVien();
                }
            }
        });
        searchnvField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                String keyword = searchnvField.getText().trim();
                if (keyword.isEmpty()) {
                    loadNhanVien();
                } else {
                    searchNhanVien();
                }
            }
        });
        JButton btnThemNV = new JButton("+ Thêm nhân viên");
        btnThemNV.setBounds(880, 110, 140, 30);
        btnThemNV.setBackground(Color.decode("#F0483E"));
        btnThemNV.setForeground(Color.WHITE);
        add(btnThemNV);


        String[] columnNames = { "Mã", "Họ tên","CCCD", "SĐT", "Vị trí công việc","Mã số thuế"};
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

        loadNhanVien();
        btnThemNV.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new Themnv();
            }
        });
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 1) {
                    int row = table.getSelectedRow();
                    if (row >= 0) {
                        String manv = table.getValueAt(row, 0).toString();
                        NhanVienDTO nv = bllnv.getNhanVienByID(manv);
                        if (nv != null) {
                            dispose();
                            TTCTnv ctnv = new TTCTnv();
                            ctnv.setThongTin(nv.getMaNhanVien(), nv.getHoTen(), nv.getCccd(), nv.getSdt(), nv.getViTriCongViec(), nv.getMaSoThue(), nv.getTrangThai());
                        } else {
                            JOptionPane.showMessageDialog(null, "Không tìm thấy nhân viên!");
                        }
                    }
                }
            }
        });

        setVisible(true);
    }
    private void loadViTriMap() {
        viTriMap = bllnv.getAllViTriMap();
    }
    private void loadNhanVien() {
        if (viTriMap == null) loadViTriMap();
        model.setRowCount(0);
        List<NhanVienDTO> list = bllnv.getAllNhanVien();
        for (NhanVienDTO nv : list) {
            String tenViTri = viTriMap.getOrDefault(nv.getViTriCongViec(), "Chưa xác định");
            model.addRow(new Object[]{nv.getMaNhanVien(), nv.getHoTen(), nv.getCccd(), nv.getSdt(), tenViTri, nv.getMaSoThue(), nv.getTrangThai()});
        }
    }
    private void searchNhanVien() {
        String keyword = searchnvField.getText().trim();
        if (keyword.equals("Tìm") || keyword.isEmpty()) {
            loadNhanVien();
            return;
        }

        model.setRowCount(0);
        List<NhanVienDTO> list = bllnv.getNhanVien(keyword);

        for (NhanVienDTO nv : list) {
            String tenViTri = nv.getTenVaiTro();
            model.addRow(new Object[]{nv.getMaNhanVien(), nv.getHoTen(),  nv.getCccd(), nv.getSdt(), tenViTri, nv.getMaSoThue(), nv.getTrangThai()});
        }
    }


    public static void main(String[] args) {
        new NhanVien();
    }
} 
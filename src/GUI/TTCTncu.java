package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;

import bll.NhaCungUngBLL;
import dal.dbconnection;


public class TTCTncu extends BaseFrame {
	private NhaCungUngBLL bllncu = new NhaCungUngBLL();
	private JTextArea txtTenNCU, txtMaNCU, txtMSTHUE, txtDC, txtSDT, txtEMAIL;
    private JLabel lblInfo;
    public TTCTncu() {
    	super("Thông tin nhà cung ứng");
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

        
        JLabel lblKhachhangLink = new JLabel("<html><u>Nhà cung ứng</u></html>");
        lblKhachhangLink.setFont(new Font("Arial", Font.BOLD, 20));
        lblKhachhangLink.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        lblKhachhangLink.setBounds(270, 70, 160, 30);
        add(lblKhachhangLink);
        
        lblKhachhangLink.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                dispose();
                new Nhacungung();
            }
        });
        
        

        JLabel lblArrow = new JLabel(" >> Thông tin nhà cung ứng");
        lblArrow.setFont(new Font("Arial", Font.BOLD, 20));
        lblArrow.setBounds(410, 70, 300, 30);
        add(lblArrow);

        // form thêm nhân viên
        JLabel lblMaNCU = new JLabel("Mã nhà cung ứng:");
        lblMaNCU.setBounds(270, 120, 150, 30);
        add(lblMaNCU);
        txtMaNCU = new JTextArea();
        txtMaNCU.setBounds(270, 150, 300, 40); 
        txtMaNCU.setLineWrap(true);
        txtMaNCU.setWrapStyleWord(true);
        txtMaNCU.setEditable(false);
        txtMaNCU.setFont(new Font("Arial", Font.BOLD, 20));
        txtMaNCU.setForeground(Color.decode("#641A1F"));
        txtMaNCU.setOpaque(false); 
        add(txtMaNCU);
     
        JLabel lblTen = new JLabel("Tên nhà cung ứng:");
        lblTen.setBounds(270, 200, 150, 30);
        add(lblTen);
        txtTenNCU = new JTextArea();
        txtTenNCU.setBounds(270, 230, 300, 60);  
        txtTenNCU.setLineWrap(true);
        txtTenNCU.setWrapStyleWord(true);
        txtTenNCU.setEditable(false);
        txtTenNCU.setFont(new Font("Arial", Font.BOLD, 20));
        txtTenNCU.setForeground(Color.decode("#641A1F"));
        txtTenNCU.setOpaque(false);  
        add(txtTenNCU);
       
        JLabel lblMST = new JLabel("Mã số thuế:");
        lblMST.setBounds(270, 280, 150, 30);
        add(lblMST);
        txtMSTHUE = new JTextArea();
        txtMSTHUE.setBounds(270, 310, 300, 40);
        txtMSTHUE.setLineWrap(true);
        txtMSTHUE.setWrapStyleWord(true);
        txtMSTHUE.setEditable(false);
        txtMSTHUE.setFont(new Font("Arial", Font.BOLD, 20));
        txtMSTHUE.setForeground(Color.decode("#641A1F"));
        txtMSTHUE.setOpaque(false); 
        add(txtMSTHUE);

     
        JLabel lblDC = new JLabel("Địa chỉ:");
        lblDC.setBounds(270, 360, 150, 30);
        add(lblDC);
        txtDC = new JTextArea();
        txtDC.setBounds(270, 390, 300, 80);
        txtDC.setLineWrap(true);
        txtDC.setWrapStyleWord(true);
        txtDC.setEditable(false);
        txtDC.setFont(new Font("Arial", Font.BOLD, 20));
        txtDC.setForeground(Color.decode("#641A1F"));
        txtDC.setOpaque(false); 
        add(txtDC);

        
        JLabel lblSDT = new JLabel("Số điện thoại:");
        lblSDT.setBounds(700, 120, 150, 30);
        add(lblSDT);
        txtSDT = new JTextArea();
        txtSDT.setBounds(700, 150, 300, 40);
        txtSDT.setLineWrap(true);
        txtSDT.setWrapStyleWord(true);
        txtSDT.setEditable(false);
        txtSDT.setFont(new Font("Arial", Font.BOLD, 20));
        txtSDT.setForeground(Color.decode("#641A1F"));
        txtSDT.setOpaque(false); 
        add(txtSDT);

      
        JLabel lblEmail = new JLabel("Email:");
        lblEmail.setBounds(700, 200, 150, 30);
        add(lblEmail);
        txtEMAIL = new JTextArea();
        txtEMAIL.setBounds(700, 230, 300, 40);
        txtEMAIL.setLineWrap(true);
        txtEMAIL.setWrapStyleWord(true);
        txtEMAIL.setEditable(false);
        txtEMAIL.setFont(new Font("Arial", Font.BOLD, 20));
        txtEMAIL.setForeground(Color.decode("#641A1F"));
        txtEMAIL.setOpaque(false); 
        add(txtEMAIL);
        // nút xóa
        JButton btnXoa = new JButton("Xóa");
        btnXoa.setBounds(700, 520, 100, 40);
        btnXoa.setBackground(Color.decode("#F0483E"));
        btnXoa.setForeground(Color.WHITE);
        add(btnXoa);
        setVisible(true);

        btnXoa.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int result = JOptionPane.showConfirmDialog(null, "Bạn có chắc muốn xóa nhà cung ứng này?", "Xác nhận xóa", JOptionPane.YES_NO_OPTION);
                if (result == JOptionPane.YES_OPTION) {
                    try (Connection conn = dbconnection.getConnection()) {
                        String sql = "DELETE FROM NHACUNGCAP WHERE MANCU = ?";
                        PreparedStatement pstmt = conn.prepareStatement(sql);
                        pstmt.setString(1, txtMaNCU.getText());
                        int rowsAffected = pstmt.executeUpdate();
                        if (rowsAffected > 0) {
                            JOptionPane.showMessageDialog(null, "Đã xóa nhà cung ứng thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                            dispose();
                            new Nhacungung(); // trở lại giao diện danh sách sản phẩm
                        } else {
                            JOptionPane.showMessageDialog(null, "Xóa thất bại. Không tìm thấy sản phẩm!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(null, "Lỗi khi xóa sản phẩm!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        JButton btnCapnhat = new JButton("Cập nhật");
        btnCapnhat.setBounds(860, 520, 100, 40);
        btnCapnhat.setBackground(Color.decode("#F0483E"));
        btnCapnhat.setForeground(Color.WHITE);
        add(btnCapnhat);

        btnCapnhat.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String maNCU = txtMaNCU.getText();  // hoặc nơi bạn hiển thị mã sản phẩm
                Capnhatttncu capNhatForm = new Capnhatttncu();
                capNhatForm.loadncuInfoForUpdate(maNCU);  // gọi phương thức load thông tin
                capNhatForm.setVisible(true);  // mở form cập nhật
            }
        });


        setVisible(true);
    }

    public void setThongTin(String tenNCU, String maNCU, String MST, String DC, String SDT, 
            String EMAIL) {
txtTenNCU.setText(tenNCU);
txtMaNCU.setText(maNCU);
txtMSTHUE.setText(MST);
txtDC.setText(DC);
txtSDT.setText(SDT);
txtEMAIL.setText(EMAIL);

revalidate();
repaint();
}
    public static void main(String[] args) {
        new TTCTncu();
    }
} 

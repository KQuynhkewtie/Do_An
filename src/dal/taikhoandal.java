package dal;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import dto.TaiKhoanDTO;

import java.sql.*;



public class taikhoandal {
    private final String url = "jdbc:oracle:thin:@localhost:1521:orcl";
    private final String user = "c##Mthuda";
    private final String pass = "Minhthu05#";

public class MaHoaMatKhau {

    public static String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = md.digest(password.getBytes());

            StringBuilder sb = new StringBuilder();
            for (byte b : hashBytes) {
                sb.append(String.format("%02x", b)); 
            }

            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Lỗi thuật toán băm SHA-256", e);
        }
    }
}

    public boolean kiemTraDangNhap(String email, String matKhau) {
//    	System.out.println("Email nhập vào: " + email);
//        System.out.println("Mật khẩu nhập vào: " + matKhau);
        String sql = "SELECT EMAIL, MATKHAU FROM TAIKHOAN WHERE EMAIL = ? AND MATKHAU = ?";
       

        try (Connection conn = DriverManager.getConnection(url, user, pass);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

        	stmt.setString(1, email.trim().toLowerCase());
        	stmt.setString(2, MaHoaMatKhau.hashPassword(matKhau.trim()));



            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                System.out.println("Tài khoản hợp lệ.");
                return true;
            } else {
                System.out.println("Không tìm thấy tài khoản.");
                return false;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public TaiKhoanDTO getTaiKhoan(String email) {
        String sql = "SELECT * FROM TAIKHOAN WHERE EMAIL = ?";
        try (Connection conn = DriverManager.getConnection(url, user, pass);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, email.trim().toLowerCase());
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new TaiKhoanDTO(
                    rs.getString("MATK"),
                    rs.getString("TENTK"),
                    rs.getString("MATKHAU"),
                    rs.getString("EMAIL"),
                    rs.getString("MANHANVIEN")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public boolean dangKyTaiKhoan(String tenTK, String email, String matKhau) {
        String checkEmail = "SELECT EMAIL FROM TAIKHOAN WHERE EMAIL = ?";
       
        String insertSQL = "INSERT INTO TAIKHOAN (MATK, TENTK, EMAIL, MATKHAU) VALUES (tk_sequence.NEXTVAL, ?, ?, ?)";

        
        try (Connection conn = DriverManager.getConnection(url, user, pass);
             PreparedStatement checkStmt = conn.prepareStatement(checkEmail);
             PreparedStatement insertStmt = conn.prepareStatement(insertSQL)) {
            
            conn.setAutoCommit(false); // Tắt auto-commit để kiểm soát giao dịch(rollback nếu lỗi)
            
            // Kiểm tra email đã tồn tại chưa
            checkStmt.setString(1, email.trim().toLowerCase());
            ResultSet rs = checkStmt.executeQuery();
            if (rs.next()) {
                System.out.println("Email đã tồn tại!");
                return false;
            }
            
            // Thêm tài khoản mới
            insertStmt.setString(1, tenTK);
            insertStmt.setString(2, email.trim().toLowerCase());
            insertStmt.setString(3, MaHoaMatKhau.hashPassword(matKhau));
            
            int rowsInserted = insertStmt.executeUpdate();
            if (rowsInserted > 0) {
                conn.commit(); // Xác nhận lưu vào database
                System.out.println("Đăng ký thành công!");
                return true;
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    
    
//    public static void main(String[] args) {
//        taikhoandal dao = new taikhoandal();
//        boolean isValid = dao.kiemTraDangNhap("minhthu123@gmail.com", "123");
//
//        if (isValid) {
//            System.out.println(" Đăng nhập thành công!");
//        } else {
//            System.out.println("Sai tài khoản hoặc mật khẩu!");
//        }
//    }

}


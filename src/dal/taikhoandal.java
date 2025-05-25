package dal;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import dto.TaiKhoanDTO;

import java.sql.*;



public class taikhoandal {


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

    public String kiemTraDangNhap(String email, String matKhau) {
        System.out.println("Email nhập vào: " + email);
        System.out.println("Mật khẩu nhập vào: " + matKhau);

        String sql = "SELECT MANV FROM TAIKHOAN WHERE LOWER(EMAIL) = ? AND MATKHAU = ?";

        try (Connection conn = DatabaseHelper.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, email.trim().toLowerCase());
            stmt.setString(2, MaHoaMatKhau.hashPassword(matKhau.trim()));

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                String manv = rs.getString("MANV");
                System.out.println("Đăng nhập thành công. MANV: " + manv);
                return manv;
            } else {
                System.out.println("Sai email hoặc mật khẩu.");
                return null;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public TaiKhoanDTO getTaiKhoan(String email) {
        String sql = "SELECT * FROM TAIKHOAN WHERE EMAIL = ?";
        try (Connection conn = DatabaseHelper.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, email.trim().toLowerCase());
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new TaiKhoanDTO(
                        rs.getString("username"),
                        rs.getString("MATKHAU"),
                        rs.getString("EMAIL"),
                        rs.getString("MANV")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    public String getUsername(String email)throws SQLException {
        String sql = "SELECT username FROM taikhoan WHERE email = ?";
        try (Connection conn = DatabaseHelper.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, email.trim().toLowerCase());
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getString("username");
            }
            return null;
        }
    }
    public boolean dangKyTaiKhoan(String tenTK, String email, String matKhau, String manv) {
        String checkEmail = "SELECT EMAIL FROM TAIKHOAN WHERE EMAIL = ?";

        String insertSQL = "INSERT INTO TAIKHOAN (username, EMAIL, MATKHAU, maNV) VALUES (?, ?, ?,?)";


        try (Connection conn = DatabaseHelper.getConnection();
             PreparedStatement checkStmt = conn.prepareStatement(checkEmail);
             PreparedStatement insertStmt = conn.prepareStatement(insertSQL)) {

            conn.setAutoCommit(false);
            checkStmt.setString(1, email.trim().toLowerCase());
            ResultSet rs = checkStmt.executeQuery();
            if (rs.next()) {
                System.out.println("Email đã tồn tại!");
                return false;
            }

            insertStmt.setString(1, tenTK.trim().toLowerCase());
            insertStmt.setString(2, email.trim().toLowerCase());
            insertStmt.setString(3, MaHoaMatKhau.hashPassword(matKhau));
            insertStmt.setString(4, manv.trim());

            int rowsInserted = insertStmt.executeUpdate();
            if (rowsInserted > 0) {
                conn.commit();
                System.out.println("Đăng ký thành công!");
                return true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


    public String getVaiTro(String maNhanVien) {
        String maVaiTro = null;
        String sql = "SELECT MAVT FROM NHANVIEN WHERE MANV = ?";
        try (Connection conn = dbconnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, maNhanVien);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                maVaiTro = rs.getString("MAVT");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return maVaiTro;
    }

    public boolean updateMatKhau(String email, String matKhauMoi) {
        String sql = "UPDATE TAIKHOAN SET MATKHAU = ? WHERE LOWER(EMAIL) = ?";
        try (Connection conn = DatabaseHelper.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {


            String matKhauMaHoa = MaHoaMatKhau.hashPassword(matKhauMoi.trim());

            stmt.setString(1, matKhauMaHoa);
            stmt.setString(2, email.trim().toLowerCase());

            int rowsUpdated = stmt.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Cập nhật mật khẩu thành công cho email: " + email);
                return true;
            } else {
                System.out.println("Không tìm thấy tài khoản với email: " + email);
                return false;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

}


package dal;

import dto.SanPhamDTO;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SanPhamDAL {

    // Lấy danh sách tất cả sản phẩm
    public List<SanPhamDTO> getAllSanPham() {
        List<SanPhamDTO> danhSachSP = new ArrayList<>();
        String sql = "SELECT * FROM SANPHAM";

        try (Connection conn = DatabaseHelper.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                SanPhamDTO sp = new SanPhamDTO(
                        rs.getString("MASP"),
                        rs.getString("MAHSX"),
                        rs.getString("MALSP"),
                        rs.getString("TENSP"),
                        rs.getString("QUYCACHDONGGOI"),
                        rs.getString("SODANGKY"),
                        rs.getInt("SOLUONG"),
                        rs.getDouble("GIABAN")
                );
                danhSachSP.add(sp);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return danhSachSP;
    }

    public SanPhamDTO getSanPhamById(String maSP) {
        SanPhamDTO sp = null;
        String query = "SELECT * FROM SANPHAM WHERE MASP = ?";

        try (Connection conn = DatabaseHelper.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, maSP);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                sp = new SanPhamDTO(
                        rs.getString("MASP"),
                        rs.getString("MAHSX"),
                        rs.getString("MALSP"),
                        rs.getString("TENSP"),
                        rs.getString("QUYCACHDONGGOI"),
                        rs.getString("SODANGKY"),
                        rs.getInt("SOLUONG"),
                        rs.getDouble("GIABAN")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return sp;
    }

    // Kiểm tra mã loại sản phẩm có tồn tại không
    public boolean existsMaLSP(String maLSP) {
        String sql = "SELECT COUNT(*) FROM LOAISANPHAM WHERE MALSP = ?";
        try (Connection conn = DatabaseHelper.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, maLSP);
            System.out.println("Truy vấn kiểm tra MALSP: " + maLSP);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int count = rs.getInt(1);
                System.out.println("Số lượng tìm thấy: " + count);
                return count > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Kiểm tra mã hãng sản xuất có tồn tại không
    private boolean existsMaHSX(String maHSX) {
        String sql = "SELECT 1 FROM HANGSANXUAT WHERE MAHSX = ?";
        try (Connection conn = DatabaseHelper.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, maHSX);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Thêm sản phẩm mới
    public boolean insertSanPham(SanPhamDTO sp) {
        String sql = "INSERT INTO SANPHAM (MASP, MAHSX, MALSP, TENSP, QUYCACHDONGGOI, SODANGKY, SOLUONG, GIABAN) VALUES (?, ?, ?, ?, ?, ?, ?, ?, )";

        try (Connection conn = DatabaseHelper.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, sp.getMaSP());
            ps.setString(2, sp.getMaHSX());
            ps.setString(3, sp.getMaLSP());
            ps.setString(4, sp.getTenSP());
            ps.setString(5, sp.getQuyCachDongGoi());
            ps.setString(6, sp.getSoDangKy());
            ps.setInt(7, sp.getsoluong());
            ps.setDouble(8, sp.getGiaBan());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Lấy danh sách tất cả loại sản phẩm
    public List<String[]> getAllLoaiSanPham() {
        List<String[]> danhSachLSP = new ArrayList<>();
        String sql = "SELECT MALSP, TENLSP FROM LOAISANPHAM";

        try (Connection conn = DatabaseHelper.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                danhSachLSP.add(new String[]{rs.getString("MALSP"), rs.getString("TENLSP")});
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return danhSachLSP;
    }

    // Lấy danh sách tất cả hãng sản xuất
    public List<String[]> getAllHangSanXuat() {
        List<String[]> danhSachHSX = new ArrayList<>();
        String sql = "SELECT MAHSX, TENHSX FROM HANGSANXUAT";

        try (Connection conn = DatabaseHelper.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                danhSachHSX.add(new String[]{rs.getString("MAHSX"), rs.getString("TENHSX")});
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return danhSachHSX;
    }

    // Tìm kiếm sản phẩm theo tên
    public List<SanPhamDTO> getSanPham(String keyword) {
        List<SanPhamDTO> danhSachSP = new ArrayList<>();
        String sql = "SELECT * FROM SANPHAM WHERE LOWER(TENSP) LIKE ? OR LOWER(MASP) LIKE ? OR LOWER(MALSP) LIKE ?";

        try (Connection conn = DatabaseHelper.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, "%" + keyword.toLowerCase() + "%");
            ps.setString(2, "%" + keyword.toLowerCase()+ "%");
            ps.setString(3, "%" + keyword.toLowerCase()+ "%");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                SanPhamDTO sp = new SanPhamDTO(
                        rs.getString("MASP"),
                        rs.getString("MAHSX"),
                        rs.getString("MALSP"),
                        rs.getString("TENSP"),
                        rs.getString("QUYCACHDONGGOI"),
                        rs.getString("SODANGKY"),
                        rs.getInt("SOLUONG"),
                        rs.getDouble("GIABAN")
                );
                danhSachSP.add(sp);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return danhSachSP;
    }

    // Xóa sản phẩm theo tên
    public boolean deleteSanPhamByName(String tenSP) {
        String sql = "DELETE FROM SANPHAM WHERE TENSP = ?";
        try (Connection conn = DatabaseHelper.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, tenSP);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean updateSanPham(SanPhamDTO sp) {
        String sql = "UPDATE SANPHAM SET MALSP=?, MAHSX=?, TENSP=?, QUYCACHDONGGOI=?, SODANGKY=?, SOLUONG=?, GIABAN=? WHERE TRIM(MASP) = ?";
        try (Connection conn = DatabaseHelper.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
// Set các giá trị vào PreparedStatement
            ps.setString(1, sp.getMaLSP());
            ps.setString(2, sp.getMaHSX());
            ps.setString(3, sp.getTenSP());
            ps.setString(4, sp.getQuyCachDongGoi());
            ps.setString(5, sp.getSoDangKy());
            ps.setInt(6, sp.getsoluong());
            ps.setDouble(7, sp.getGiaBan());

            // Set giá trị MASP cho phần WHERE, loại bỏ khoảng trắng dư
            ps.setString(8, sp.getMaSP().trim());

            // Hiển thị giá trị truyền vào
            System.out.println("Giá trị truyền vào UPDATE:");
            System.out.println("1. TENSP: " + sp.getTenSP());
            System.out.println("2. SOLUONG: " + sp.getsoluong());
            System.out.println("3. DONGIA: " + sp.getGiaBan());
            System.out.println("4. QCDG: " + sp.getQuyCachDongGoi());
            System.out.println("5. Solo: " + sp.getSoDangKy());
            System.out.println("7. MALOAI: " + sp.getMaLSP());
            System.out.println("8. MAhsx: " + sp.getMaHSX());
            System.out.println("10. MASP (WHERE): " + sp.getMaSP().trim());

// Thực hiện câu lệnh UPDATE và kiểm tra số lượng bản ghi bị ảnh hưởng
            int rows = ps.executeUpdate();
            System.out.println("Rows affected: " + rows);

            return rows > 0;

        } catch (SQLException e) {
            System.err.println("Lỗi cập nhật sản phẩm: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    // Xóa sản phẩm theo mã
    public boolean deleteSanPhamById(String maSP) {
        String sql = "DELETE FROM SANPHAM WHERE MASP = ?";
        try (Connection conn = DatabaseHelper.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, maSP);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Lấy sản phẩm theo mã (có thể sử dụng connection từ bên ngoài)
    public SanPhamDTO laySanPhamTheoMa(String maSP, Connection conn) {
        SanPhamDTO sp = null;
        String query = "SELECT * FROM SANPHAM WHERE MASP = ?";
        boolean shouldClose = false;

        try {
            if (conn == null) {
                conn = DatabaseHelper.getConnection();
                shouldClose = true;
            }

            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, maSP);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                sp = new SanPhamDTO(
                        rs.getString("MASP"),
                        rs.getString("MAHSX"),
                        rs.getString("MALSP"),
                        rs.getString("TENSP"),
                        rs.getString("QUYCACHDONGGOI"),
                        rs.getString("SODANGKY"),
                        rs.getInt("SOLUONG"),
                        rs.getDouble("GIABAN")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (shouldClose && conn != null) {
                DatabaseHelper.closeConnection(conn);
            }
        }
        return sp;
    }

//    // Cập nhật sản phẩm (có thể sử dụng connection từ bên ngoài)
//    public boolean capNhatSanPham(SanPhamDTO sp, Connection conn) {
//        String sql = "UPDATE SANPHAM SET MALSP=?, MAHSX=?, TENSP=?, QUYCACHDONGGOI=?, SOLO=?, SODANGKY=?, HSD=?, HANGTON=?, GIABAN=? WHERE TRIM(MASP) = ?";
//        boolean shouldClose = false;
//
//        try {
//            if (conn == null) {
//                conn = DatabaseHelper.getConnection();
//                shouldClose = true;
//            }
//
//            PreparedStatement ps = conn.prepareStatement(sql);
//            ps.setString(1, sp.getMaSP());
//            ps.setString(2, sp.getMaHSX());
//            ps.setString(3, sp.getMaLSP());
//            ps.setString(4, sp.getTenSP());
//            ps.setString(5, sp.getQuyCachDongGoi());
//            ps.setString(6, sp.getSoDangKy());
//            ps.setInt(7, sp.getsoluong());
//            ps.setDouble(8, sp.getGiaBan());
//
//            return ps.executeUpdate() > 0;  // Trả về true nếu thêm thành công
//        } catch (SQLException e) {
//            System.err.println("Lỗi cập nhật sản phẩm: " + e.getMessage());
//            e.printStackTrace();
//            return false;
//        } finally {
//            if (shouldClose && conn != null) {
//                DatabaseHelper.closeConnection(conn);
//            }s
//        }
//    }

    public int getSoLuongTon(String maSP) throws SQLException {
        String sql = "SELECT SOLUONG FROM SANPHAM WHERE TRIM(MASP) = ?";
        try (Connection conn = DatabaseHelper.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setString(1, maSP.trim());
            ResultSet rs = pst.executeQuery();
            return rs.next() ? rs.getInt("SOLUONG") : 0;
        }
    }
}
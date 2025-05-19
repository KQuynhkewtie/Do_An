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
                        rs.getString("SOLO"),
                        rs.getString("SODANGKY"),
                        rs.getDate("HSD"),
                        rs.getInt("HANGTON"),
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
                        rs.getString("SOLO"),
                        rs.getString("SODANGKY"),
                        rs.getDate("HSD"),
                        rs.getInt("HANGTON"),
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
        String sql = "INSERT INTO SANPHAM (MASP, MAHSX, MALSP, TENSP, QUYCACHDONGGOI, SOLO, SODANGKY, HSD, HANGTON, GIABAN) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseHelper.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, sp.getMaSP());
            ps.setString(2, sp.getMaHSX());
            ps.setString(3, sp.getMaLSP());
            ps.setString(4, sp.getTenSP());
            ps.setString(5, sp.getQuyCachDongGoi());
            ps.setString(6, sp.getSoLo());
            ps.setString(7, sp.getSoDangKy());

            if (sp.getHsd() != null) {
                ps.setDate(8, new java.sql.Date(sp.getHsd().getTime()));
            } else {
                ps.setNull(8, Types.DATE);
            }

            ps.setInt(9, sp.getHangTon());
            ps.setDouble(10, sp.getGiaBan());

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
        String sql = "SELECT * FROM SANPHAM WHERE LOWER(TENSP) LIKE ?";

        try (Connection conn = DatabaseHelper.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, "%" + keyword.toLowerCase() + "%");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                SanPhamDTO sp = new SanPhamDTO(
                        rs.getString("MASP"),
                        rs.getString("MAHSX"),
                        rs.getString("MALSP"),
                        rs.getString("TENSP"),
                        rs.getString("QUYCACHDONGGOI"),
                        rs.getString("SOLO"),
                        rs.getString("SODANGKY"),
                        rs.getDate("HSD"),
                        rs.getInt("HANGTON"),
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
        String sql = "UPDATE SANPHAM SET MALSP=?, MAHSX=?, TENSP=?, QUYCACHDONGGOI=?, SOLO=?, SODANGKY=?, HSD=?, HANGTON=?, GIABAN=? WHERE TRIM(MASP) = ?";
        try (Connection conn = DatabaseHelper.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            System.out.println("Đang cập nhật sản phẩm: " + sp);
            System.out.println("Mã SP: " + sp.getMaSP());

            ps.setString(1, sp.getMaLSP());
            ps.setString(2, sp.getMaHSX());
            ps.setString(3, sp.getTenSP());
            ps.setString(4, sp.getQuyCachDongGoi());
            ps.setString(5, sp.getSoLo());
            ps.setString(6, sp.getSoDangKy());

            if (sp.getHsd() != null) {
                ps.setDate(7, new java.sql.Date(sp.getHsd().getTime()));
            } else {
                ps.setNull(7, Types.DATE);
            }

            ps.setInt(8, sp.getHangTon());
            ps.setDouble(9, sp.getGiaBan());
            ps.setString(10, sp.getMaSP().trim());

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
                        rs.getString("SOLO"),
                        rs.getString("SODANGKY"),
                        rs.getDate("HSD"),
                        rs.getInt("HANGTON"),
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

    // Cập nhật sản phẩm (có thể sử dụng connection từ bên ngoài)
    public boolean capNhatSanPham(SanPhamDTO sp, Connection conn) {
        String sql = "UPDATE SANPHAM SET MALSP=?, MAHSX=?, TENSP=?, QUYCACHDONGGOI=?, SOLO=?, SODANGKY=?, HSD=?, HANGTON=?, GIABAN=? WHERE TRIM(MASP) = ?";
        boolean shouldClose = false;

        try {
            if (conn == null) {
                conn = DatabaseHelper.getConnection();
                shouldClose = true;
            }

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, sp.getMaLSP());
            ps.setString(2, sp.getMaHSX());
            ps.setString(3, sp.getTenSP());
            ps.setString(4, sp.getQuyCachDongGoi());
            ps.setString(5, sp.getSoLo());
            ps.setString(6, sp.getSoDangKy());

            if (sp.getHsd() != null) {
                ps.setDate(7, new java.sql.Date(sp.getHsd().getTime()));
            } else {
                ps.setNull(7, Types.DATE);
            }

            ps.setInt(8, sp.getHangTon());
            ps.setDouble(9, sp.getGiaBan());
            ps.setString(10, sp.getMaSP().trim());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Lỗi cập nhật sản phẩm: " + e.getMessage());
            e.printStackTrace();
            return false;
        } finally {
            if (shouldClose && conn != null) {
                DatabaseHelper.closeConnection(conn);
            }
        }
    }

    public int getSoLuongTon(String maSP) throws SQLException {
        String sql = "SELECT HANGTON FROM SANPHAM WHERE MASP = ?";
        try (Connection conn = DatabaseHelper.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setString(1, maSP);
            ResultSet rs = pst.executeQuery();
            return rs.next() ? rs.getInt("HANGTON") : 0;
        }
    }
}
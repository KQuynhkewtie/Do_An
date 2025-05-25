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
                        rs.getDouble("GIABAN"),
                        rs.getInt("TRANGTHAI")
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
        String query = "SELECT * FROM SANPHAM WHERE TRIM(MASP) = ?";

        try (Connection conn = DatabaseHelper.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, maSP.trim());
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
                        rs.getDouble("GIABAN"),
                        rs.getInt("TRANGTHAI")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return sp;
    }


    public boolean insertSanPham(SanPhamDTO sp) {
        String sql = "INSERT INTO SANPHAM (MASP, MAHSX, MALSP, TENSP, QUYCACHDONGGOI, SODANGKY, SOLUONG, GIABAN, TRANGTHAI) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ? )";

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
            ps.setInt(9, sp.getTrangThai());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

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

    // Tìm kiếm sản phẩm
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
                        rs.getDouble("GIABAN"),
                        rs.getInt("TRANGTHAI")
                );
                danhSachSP.add(sp);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return danhSachSP;
    }



    public boolean updateSanPham(SanPhamDTO sp) {
        String sql = "UPDATE SANPHAM SET MALSP=?, MAHSX=?, TENSP=?, QUYCACHDONGGOI=?,  SODANGKY=?,  SOLUONG=?, GIABAN=?, TRANGTHAI = ? WHERE TRIM(MASP) = ?";
        try (Connection conn = DatabaseHelper.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {


            ps.setString(1, sp.getMaLSP());
            ps.setString(2, sp.getMaHSX());
            ps.setString(3, sp.getTenSP());
            ps.setString(4, sp.getQuyCachDongGoi());
            ps.setString(5, sp.getSoDangKy());
            ps.setInt(6, sp.getsoluong());
            ps.setDouble(7, sp.getGiaBan());
            ps.setInt(8, sp.getTrangThai());
            ps.setString(9, sp.getMaSP().trim());

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

    public String getLSPByMa(String maLSP) {
        String tenLSP = "";
        String sql = "SELECT TENLSP FROM LOAISANPHAM WHERE MALSP = ?";
        try (Connection conn = dbconnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, maLSP);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                tenLSP = rs.getString("TENLSP");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tenLSP;
    }

    public String getHSXByMa(String maHSX) {
        String tenHSX = "";
        String sql = "SELECT TENHSX FROM HANGSANXUAT WHERE MAHSX = ?";
        try (Connection conn = dbconnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, maHSX);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                tenHSX = rs.getString("TENHSX");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tenHSX;
    }
}
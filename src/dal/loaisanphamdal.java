package dal;

import dto.LoaiSPDTO;
import dto.LoaiSanPhamDTO;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class loaisanphamdal {
    // Lấy danh sách tất cả loại sản phẩm
    public List<LoaiSanPhamDTO> getAllLoaiSanPham() {
        List<LoaiSanPhamDTO> danhSachLSP = new ArrayList<>();
        String sql = "SELECT * FROM LOAISANPHAM";

        try (Connection conn = DatabaseHelper.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                danhSachLSP.add(new LoaiSanPhamDTO(
                        rs.getString("MALSP"),
                        rs.getString("TENLSP")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return danhSachLSP;
    }

    public LoaiSPDTO getLSPById(String maLSP) {
        LoaiSPDTO lsp = null;
        String query = "SELECT * FROM LOAISANPHAM WHERE MALSP = ?";

        try (Connection conn = DatabaseHelper.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, maLSP);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                lsp = new LoaiSPDTO(
                        rs.getString("MALSP"),
                        rs.getString("TENLSP")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lsp;
    }

    // Thêm loại sản phẩm
    public boolean insertLoaiSanPham(LoaiSanPhamDTO lsp) {
        String sql = "INSERT INTO LOAISANPHAM (MALSP, TENLSP) VALUES (?, ?)";
        try (Connection conn = DatabaseHelper.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, lsp.getMaLSP());
            ps.setString(2, lsp.getTenLSP());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Xóa loại sản phẩm theo mã
    public boolean deleteLoaiSanPham(String maLSP) {
        String sql = "DELETE FROM LOAISANPHAM WHERE MALSP = ?";
        try (Connection conn = DatabaseHelper.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, maLSP);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean updateMaLoaiSanPham(String oldMaLSP, String newMaLSP) {
        String sql = "UPDATE LOAISANPHAM SET MALSP = ? WHERE MALSP = ?";
        try (Connection conn = DatabaseHelper.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, newMaLSP);
            ps.setString(2, oldMaLSP);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
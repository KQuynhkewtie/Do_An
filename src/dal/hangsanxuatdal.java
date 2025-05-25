package dal;

import dto.HangSanXuatDTO;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class hangsanxuatdal {
    // Lấy danh sách tất cả hãng sản xuất
    public List<HangSanXuatDTO> getAllHangSanXuat() {
        List<HangSanXuatDTO> danhSachHSX = new ArrayList<>();
        String sql = "SELECT * FROM HANGSANXUAT";

        try (Connection conn = DatabaseHelper.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                HangSanXuatDTO hsx = new HangSanXuatDTO(
                        rs.getString("MAHSX"),
                        rs.getString("TENHSX"),
                        rs.getString("MASOTHUE"),
                        rs.getString("DIACHI"),
                        rs.getString("SDT"),
                        rs.getInt("TRANGTHAI")
                );
                danhSachHSX.add(hsx);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return danhSachHSX;
    }

    public HangSanXuatDTO getHSXById(String maHSX) {
        String query = "SELECT * FROM HANGSANXUAT WHERE TRIM(MAHSX) = ?";

        try (Connection conn = DatabaseHelper.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, maHSX.trim());

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new HangSanXuatDTO(
                            rs.getString("MAHSX"),
                            rs.getString("TENHSX"),
                            rs.getString("MASOTHUE"),
                            rs.getString("DIACHI"),
                            rs.getString("SDT"),
                            rs.getInt("TRANGTHAI")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Thêm hãng sản xuất mới
    public boolean insertHangSanXuat(HangSanXuatDTO hsx) {
        String sql = "INSERT INTO HANGSANXUAT (MAHSX, TENHSX, MASOTHUE, DIACHI, SDT) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseHelper.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, hsx.getMaHSX());
            ps.setString(2, hsx.getTenHSX());
            ps.setString(3, hsx.getMaSoThue());
            ps.setString(4, hsx.getDiaChi());
            ps.setString(5, hsx.getSdt());
            ps.setInt(6, hsx.getTrangThai());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Cập nhật thông tin hãng sản xuất
    public boolean updateHangSanXuat(HangSanXuatDTO hsx) {
        String sql = "UPDATE HANGSANXUAT SET TENHSX=?, MASOTHUE=?, DIACHI=?, SDT=?, TRANGTHAI = ? WHERE TRIM(MAHSX)=?";

        try (Connection conn = DatabaseHelper.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, hsx.getTenHSX());
            ps.setString(2, hsx.getMaSoThue());
            ps.setString(3, hsx.getDiaChi());
            ps.setString(4, hsx.getSdt());
            ps.setInt(5, hsx.getTrangThai());
            ps.setString(6, hsx.getMaHSX());

            int rows = ps.executeUpdate();
            System.out.println("Rows affected: " + rows);

            return rows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Xóa hãng sản xuất
    public boolean deleteHangSanXuat(String maHSX) {
        String sql = "DELETE FROM HANGSANXUAT WHERE MAHSX = ?";
        try (Connection conn = DatabaseHelper.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, maHSX);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<HangSanXuatDTO> getHSX(String keyword) {
        List<HangSanXuatDTO> danhSachHSX = new ArrayList<>();
        String sql = "SELECT * FROM HANGSANXUAT WHERE LOWER(TENHSX) LIKE ? OR LOWER(MAHSX) LIKE ? OR MASOTHUE LIKE ? OR LOWER(DIACHI) LIKE ? OR SDT LIKE ?";

        try (Connection conn = DatabaseHelper.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, "%" + keyword.toLowerCase() + "%");
            ps.setString(2, "%" + keyword.toLowerCase() + "%");
            ps.setString(3, "%" + keyword + "%");
            ps.setString(4, "%" + keyword.toLowerCase() + "%");
            ps.setString(5, "%" + keyword+ "%");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                HangSanXuatDTO hsx = new HangSanXuatDTO(
                        rs.getString("MAHSX"),
                        rs.getString("TENHSX"),
                        rs.getString("MASOTHUE"),
                        rs.getString("DIACHI"),
                        rs.getString("SDT"),
                        rs.getInt("TRANGTHAI")
                );
                danhSachHSX.add(hsx);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return danhSachHSX;
    }
}
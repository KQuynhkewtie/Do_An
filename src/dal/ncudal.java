package dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dto.NhaCungUngDTO;

public class ncudal {
    public ArrayList<NhaCungUngDTO> getAllNCU() {
        ArrayList<NhaCungUngDTO> ds = new ArrayList<>();
        String query = "SELECT * FROM NHACUNGUNG";

        try (Connection conn = DatabaseHelper.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {


            while (rs.next()) {
                NhaCungUngDTO ncu = new NhaCungUngDTO(
                        rs.getString("MANCU"),
                        rs.getString("TENNCU"),
                        rs.getString("MASOTHUE"),
                        rs.getString("DIACHI"),
                        rs.getString("SDT"),
                        rs.getString("EMAIL"),
                        rs.getInt("TRANGTHAI")
                );
                ds.add(ncu);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ds;
    }

    public NhaCungUngDTO getNCUById(String maNCU) {
        NhaCungUngDTO ncu = null;
        String query = "SELECT * FROM NHACUNGUNG WHERE TRIM(MANCU) = ?";

        try (Connection conn = DatabaseHelper.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, maNCU.trim());
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                ncu = new NhaCungUngDTO(
                        rs.getString("MANCU"),
                        rs.getString("TENNCU"),
                        rs.getString("MASOTHUE"),
                        rs.getString("DIACHI"),
                        rs.getString("SDT"),
                        rs.getString("EMAIL"),
                        rs.getInt("TRANGTHAI")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ncu;
    }

    public boolean insertncu(NhaCungUngDTO ncu) {
        String sql = "INSERT INTO NHACUNGUNG (MANCU, TENNCU, MASOTHUE, DIACHI, SDT, EMAIL, TRANGTHAI) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseHelper.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, ncu.getMaNCU());
            ps.setString(2, ncu.getTenNCU());
            ps.setString(3, ncu.getMaSoThue());
            ps.setString(4, ncu.getDiaChi());
            ps.setString(5, ncu.getSdt());
            ps.setString(6, ncu.getEmail());
            ps.setInt(7, ncu.getTrangThai());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<NhaCungUngDTO> getncu(String keyword) {
        List<NhaCungUngDTO> danhSachNCU = new ArrayList<>();
        String sql = "SELECT * FROM NHACUNGUNG WHERE LOWER(TENNCU) LIKE ? OR LOWER(MANCU) LIKE ? OR MASOTHUE LIKE ? OR LOWER(DIACHI) LIKE ? OR SDT LIKE ? OR LOWER(EMAIL) LIKE ?";

        try (Connection conn = DatabaseHelper.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, "%" + keyword.toLowerCase() + "%");
            ps.setString(2, "%" + keyword.toLowerCase() + "%");
            ps.setString(3, "%" + keyword + "%");
            ps.setString(4, "%" + keyword.toLowerCase() + "%");
            ps.setString(5, "%" + keyword + "%");
            ps.setString(6, "%" + keyword.toLowerCase() + "%");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                NhaCungUngDTO ncu = new NhaCungUngDTO(
                        rs.getString("MANCU"),
                        rs.getString("TENNCU"),
                        rs.getString("MASOTHUE"),
                        rs.getString("DIACHI"),
                        rs.getString("SDT"),
                        rs.getString("EMAIL"),
                        rs.getInt("TRANGTHAI")
                );
                danhSachNCU.add(ncu);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return danhSachNCU;
    }
//
//    public boolean deleteNCUByName(String tenNCU) {
//        String sql = "DELETE FROM NHACUNGUNG WHERE TENNCU = ?";
//
//        try (Connection conn = DatabaseHelper.getConnection();
//             PreparedStatement ps = conn.prepareStatement(sql)) {
//            ps.setString(1, tenNCU);
//            return ps.executeUpdate() > 0;
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return false;
//    }

    public boolean updateNCU(NhaCungUngDTO ncu) {
        String sql = "UPDATE NHACUNGUNG SET TENNCU=?, MASOTHUE=?, DIACHI=?, SDT=?, EMAIL=?,TRANGTHAI = ?  WHERE TRIM(MANCU) = ?";

        try (Connection conn = DatabaseHelper.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, ncu.getTenNCU());
            ps.setString(2, ncu.getMaSoThue());
            ps.setString(3, ncu.getDiaChi());
            ps.setString(4, ncu.getSdt());
            ps.setString(5, ncu.getEmail());
            ps.setInt(6, ncu.getTrangThai());
            ps.setString(7, ncu.getMaNCU().trim());
            int rows = ps.executeUpdate();
            System.out.println("Rows affected: " + rows);

            return rows > 0;

        } catch (SQLException e) {
            System.err.println("Lỗi cập nhật nhà cung ứng: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    public boolean deleteNCUById(String maNCU) {
        String sql = "DELETE FROM NHACUNGUNG WHERE MANCU = ?";
        try (Connection conn = DatabaseHelper.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, maNCU);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

//    public List<NhaCungUngDTO> getNCUByTen(String tenNCU) {
//        List<NhaCungUngDTO> danhSachNCU = new ArrayList<>();
//        String sql = "SELECT * FROM NHACUNGUNG WHERE LOWER(TENNCU) LIKE ?";
//
//        try (Connection conn = DatabaseHelper.getConnection();
//             PreparedStatement ps = conn.prepareStatement(sql)) {
//            ps.setString(1, "%" + tenNCU.toLowerCase() + "%");
//            ResultSet rs = ps.executeQuery();
//
//            while (rs.next()) {
//                NhaCungUngDTO ncu = new NhaCungUngDTO(
//                        rs.getString("MANCU"),
//                        rs.getString("TENNCU"),
//                        rs.getString("MASOTHUE"),
//                        rs.getString("DIACHI"),
//                        rs.getString("SDT"),
//                        rs.getString("EMAIL")
//                );
//                danhSachNCU.add(ncu);
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return danhSachNCU;
//    }
}
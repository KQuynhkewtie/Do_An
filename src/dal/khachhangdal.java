package dal;

import dto.KhachHangDTO;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class khachhangdal {
    public ArrayList<KhachHangDTO> layDSKhachHang() {
        ArrayList<KhachHangDTO> ds = new ArrayList<>();
        String query = "SELECT * FROM KHACHHANG";

        try (Connection conn = DatabaseHelper.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                KhachHangDTO kh = new KhachHangDTO(
                        rs.getString("MAKH"),
                        rs.getString("HOTEN"),
                        rs.getInt("DIEMTICHLUY"),
                        rs.getString("LOAIKHACH"),
                        rs.getString("SDT")
                );
                ds.add(kh);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ds;
    }

    public KhachHangDTO getKhachHangById(String maKH) {
        KhachHangDTO kh = null;
        String query = "SELECT * FROM KHACHHANG WHERE TRIM(MAKH) = ?";

        try (Connection conn = DatabaseHelper.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, maKH);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                kh = new KhachHangDTO(
                        rs.getString("MAKH"),
                        rs.getString("HOTEN"),
                        rs.getInt("DIEMTICHLUY"),
                        rs.getString("LOAIKHACH"),
                        rs.getString("SDT")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return kh;
    }

    public boolean insertKhachHang(KhachHangDTO kh) {
        String sql = "INSERT INTO KHACHHANG (MAKH, HOTEN, SDT) VALUES (?, ?, ?)";

        try (Connection conn = DatabaseHelper.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, kh.getMaKH());
            ps.setString(2, kh.getHoTen());
            ps.setString(3, kh.getSdt());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<KhachHangDTO> getKhachHang(String keyword) {
        List<KhachHangDTO> danhSachKH = new ArrayList<>();
        String sql = "SELECT * FROM KHACHHANG WHERE LOWER(HOTEN) LIKE ? OR LOWER(MAKH) LIKE ?  OR DIEMTICHLUY LIKE ? OR LOWER(LOAIKHACH) LIKE ? OR SDT LIKE ?";

        try (Connection conn = DatabaseHelper.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, "%" + keyword.toLowerCase() + "%");
            ps.setString(2, "%" + keyword.toLowerCase() + "%");
            ps.setString(3, "%" + keyword+ "%");
            ps.setString(4, "%" + keyword.toLowerCase() + "%");
            ps.setString(5, "%" + keyword+ "%");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                KhachHangDTO kh = new KhachHangDTO(
                        rs.getString("MAKH"),
                        rs.getString("HOTEN"),
                        rs.getInt("DIEMTICHLUY"),
                        rs.getString("LOAIKHACH"),
                        rs.getString("SDT")
                );
                danhSachKH.add(kh);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return danhSachKH;
    }

    public boolean deleteKhachHangByName(String tenKH) {
        String sql = "DELETE FROM KHACHHANG WHERE HOTEN = ?";

        try (Connection conn = DatabaseHelper.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, tenKH);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean updateKhachHang(KhachHangDTO kh) {
        String sql = "UPDATE KHACHHANG SET HOTEN=?, DIEMTICHLUY=?, SDT=? WHERE TRIM(MAKH) = ?";

        try (Connection conn = DatabaseHelper.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, kh.getHoTen());
            ps.setDouble(2, kh.getDiemTichLuy());
            ps.setString(3, kh.getSdt());
            ps.setString(4, kh.getMaKH().trim());

            int rows = ps.executeUpdate();
            System.out.println("Rows affected: " + rows);

            return rows > 0;

        } catch (SQLException e) {
            System.err.println("Lỗi cập nhật khách hàng: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    public boolean deleteKhachHangById(String maKH) {
        String sql = "DELETE FROM KHACHHANG WHERE MAKH = ?";
        try (Connection conn = DatabaseHelper.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, maKH);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
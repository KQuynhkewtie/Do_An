package dal;

import dto.NhanVienDTO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class NhanViendal {

    public boolean insertNhanVien(NhanVienDTO nv) {
        String sql = "INSERT INTO NhanVien (maNV, hoTen, cccd, sdt, maVT, maSoThue, trangthai) VALUES (?, ?, ?, ?, ?, ?,?)";
        try (Connection conn = DatabaseHelper.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nv.getMaNhanVien());
            stmt.setString(2, nv.getHoTen());
            stmt.setString(3, nv.getCccd());
            stmt.setString(4, nv.getSdt());
            stmt.setString(5, nv.getViTriCongViec());
            stmt.setString(6, nv.getMaSoThue());
            stmt.setInt(7, nv.getTrangThai());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean updateNhanVien(NhanVienDTO nv) {
        String sql = "UPDATE NhanVien SET hoTen = ?, cccd = ?, sdt = ?, maVT = ?, maSoThue = ?, trangthai =? WHERE TRIM(maNV) = ?";
        try (Connection conn = DatabaseHelper.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nv.getHoTen());
            stmt.setString(2, nv.getCccd());
            stmt.setString(3, nv.getSdt());
            stmt.setString(4, nv.getViTriCongViec());
            stmt.setString(5, nv.getMaSoThue());
            stmt.setInt(6, nv.getTrangThai());
            stmt.setString(7, nv.getMaNhanVien().trim());

            int rows = stmt.executeUpdate();
            System.out.println("Rows affected: " + rows);

            return rows > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean deleteNhanVien(String maNhanVien) {
        String sql = "DELETE FROM NhanVien WHERE MANV = ?";
        try (Connection conn = DatabaseHelper.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, maNhanVien);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<NhanVienDTO> getDanhSachNhanVien() {
        List<NhanVienDTO> danhSach = new ArrayList<>();
        String sql = "SELECT * FROM NhanVien";
        try (Connection conn = DatabaseHelper.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                danhSach.add(new NhanVienDTO(
                        rs.getString("MANV"),
                        rs.getString("hoTen"),
                        rs.getString("cccd"),
                        rs.getString("sdt"),
                        rs.getString("MAVT"),
                        rs.getString("maSoThue"),
                        rs.getInt("trangthai")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return danhSach;
    }

    public NhanVienDTO getNhanVienByID(String maNhanVien) {
        String sql = "SELECT * FROM NhanVien WHERE MANV = ?";
        try (Connection conn = DatabaseHelper.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, maNhanVien);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new NhanVienDTO(
                            rs.getString("MANV"),
                            rs.getString("hoTen"),
                            rs.getString("cccd"),
                            rs.getString("sdt"),
                            rs.getString("MAVT"),
                            rs.getString("maSoThue"),
                            rs.getInt("trangthai")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<NhanVienDTO> getNhanVien(String keyword) {
        List<NhanVienDTO> danhSachNV = new ArrayList<>();
        String sql = "SELECT * FROM NHANVIEN nv JOIN VAITRO vt ON nv.maVT = vt.maVT WHERE LOWER(HOTEN) LIKE ? OR LOWER(MANV) LIKE ? OR CCCD LIKE ? OR SDT LIKE ? OR LOWER(TENVAITRO) LIKE ? OR MASOTHUE LIKE ? OR TRANGTHAI = ?";


        try (Connection conn = DatabaseHelper.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, "%" + keyword.toLowerCase() + "%");
            ps.setString(2, "%" + keyword.toLowerCase() + "%");
            ps.setString(3, "%" + keyword+ "%");
            ps.setString(4, "%" + keyword+ "%");
            ps.setString(5, "%" + keyword.toLowerCase() + "%");
            ps.setString(6, "%" + keyword + "%");
            try {
                int trangThaiValue = Integer.parseInt(keyword);
                ps.setInt(7, trangThaiValue);
            } catch (NumberFormatException e) {

                ps.setInt(7, -1);
            }
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                NhanVienDTO nv = new NhanVienDTO(
                        rs.getString("maNV"),
                        rs.getString("hoTen"),
                        rs.getString("cccd"),
                        rs.getString("sdt"),
                        rs.getString("maVT"),
                        rs.getString("TENVAITRO"),
                        rs.getString("maSoThue"),
                        rs.getInt("trangthai")

                );
                danhSachNV.add(nv);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return danhSachNV;
    }

//    public List<NhanVienDTO> getNhanVienByTen(String tenNV) {
//        List<NhanVienDTO> danhSachNV = new ArrayList<>();
//        String sql = "SELECT * FROM NHANVIEN WHERE LOWER(HOTEN) LIKE ?";
//
//        try (Connection conn = DatabaseHelper.getConnection();
//             PreparedStatement ps = conn.prepareStatement(sql)) {
//
//            ps.setString(1, "%" + tenNV.toLowerCase() + "%");
//            ResultSet rs = ps.executeQuery();
//
//            while (rs.next()) {
//                NhanVienDTO nv = new NhanVienDTO(
//                        rs.getString("MANV"),
//                        rs.getString("hoTen"),
//                        rs.getString("cccd"),
//                        rs.getString("sdt"),
//                        rs.getString("MAVT"),
//                        rs.getString("maSoThue")
//                );
//                danhSachNV.add(nv);
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return danhSachNV;
//    }

    public String getTenViTriByMa(String maVT) {
        String tenVT = "";
        String sql = "SELECT TENVAITRO FROM VAITRO WHERE MAVT = ?";
        try (Connection conn = dbconnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, maVT);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                tenVT = rs.getString("TENVAITRO");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tenVT;
    }
}
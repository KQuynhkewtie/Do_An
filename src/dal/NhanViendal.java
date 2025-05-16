package dal;

import dto.NhanVienDTO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class NhanViendal {
	private final String url = "jdbc:oracle:thin:@localhost:1521:orcl";
	private final String user = "c##Mthuda";
	private final String pass = "Minhthu05#";

   
    public boolean insertNhanVien(NhanVienDTO nv) {
        String sql = "INSERT INTO NhanVien (maNhanVien, hoTen, cccd, sdt, viTriCongViec, maSoThue) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(url, user, pass);
	         PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nv.getMaNhanVien());
            stmt.setString(2, nv.getHoTen());
            stmt.setString(3, nv.getCccd());
            stmt.setString(4, nv.getSdt());
            stmt.setString(5, nv.getViTriCongViec());
            stmt.setString(6, nv.getMaSoThue());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean updateNhanVien(NhanVienDTO nv) {
        String sql = "UPDATE NhanVien SET hoTen = ?, cccd = ?, sdt = ?, viTriCongViec = ?, maSoThue = ? WHERE TRIM(maNhanVien) = ?";
        try (Connection conn = DriverManager.getConnection(url, user, pass);
   	         PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nv.getHoTen());
            stmt.setString(2, nv.getCccd());
            stmt.setString(3, nv.getSdt());
            stmt.setString(4, nv.getViTriCongViec());
            stmt.setString(5, nv.getMaSoThue());
            stmt.setString(6, nv.getMaNhanVien().trim());
            
            int rows = stmt.executeUpdate();
            System.out.println("Rows affected: " + rows);

            return rows > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean deleteNhanVien(String maNhanVien) {
        String sql = "DELETE FROM NhanVien WHERE maNhanVien = ?";
        try (Connection conn = DriverManager.getConnection(url, user, pass);
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
        try (Connection conn = DriverManager.getConnection(url, user, pass);
	         PreparedStatement stmt = conn.prepareStatement(sql);
	         ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                danhSach.add(new NhanVienDTO(
                        rs.getString("maNhanVien"),
                        rs.getString("hoTen"),
                        rs.getString("cccd"),
                        rs.getString("sdt"),
                        rs.getString("viTriCongViec"),
                        rs.getString("maSoThue")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return danhSach;
    }

    public NhanVienDTO getNhanVienTheoMa(String maNhanVien) {
        String sql = "SELECT * FROM NhanVien WHERE maNhanVien = ?";
        try (Connection conn = DriverManager.getConnection(url, user, pass);
	             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, maNhanVien);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new NhanVienDTO(
                            rs.getString("maNhanVien"),
                            rs.getString("hoTen"),
                            rs.getString("cccd"),
                            rs.getString("sdt"),
                            rs.getString("viTriCongViec"),
                            rs.getString("maSoThue")
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
        String sql = "SELECT * FROM NHANVIEN WHERE LOWER(HOTEN) LIKE ?";

        try (Connection conn = DriverManager.getConnection(url, user, pass);
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, "%" + keyword.toLowerCase() + "%");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                NhanVienDTO nv = new NhanVienDTO(
                		 rs.getString("maNhanVien"),
                         rs.getString("hoTen"),
                         rs.getString("cccd"),
                         rs.getString("sdt"),
                         rs.getString("viTriCongViec"),
                         rs.getString("maSoThue")
                );
                danhSachNV.add(nv);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return danhSachNV;
    }


    // Hiển thị theo tên nv
    public List<NhanVienDTO> getNhanVienByTen(String tenNV) {
        List<NhanVienDTO> danhSachNV = new ArrayList<>();
        String sql = "SELECT * FROM NHANVIEN WHERE LOWER(HOTEN) LIKE ?";

        try (Connection conn = DriverManager.getConnection(url, user, pass);
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, "%" + tenNV.toLowerCase() + "%");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                NhanVienDTO nv = new NhanVienDTO(
                        rs.getString("maNhanVien"),
                        rs.getString("hoTen"),
                        rs.getString("cccd"),
                        rs.getString("sdt"),
                        rs.getString("viTriCongViec"),
                        rs.getString("maSoThue")
                );
                danhSachNV.add(nv);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return danhSachNV;
    }
}
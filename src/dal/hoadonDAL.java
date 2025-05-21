package dal;

import dto.HoaDonDTO;
import dto.ChiTietHoaDonDTO;

import javax.swing.*;
import java.sql.*;
import java.util.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class hoadonDAL {
    private Connection conn;
    private PreparedStatement pst;
    private ResultSet rs;
    private DatabaseHelper dtb;

    // Kết nối database
    public void setConnection(Connection connection) {
        this.conn = connection;
    }

    // Lấy danh sách hóa đơn
    public List<HoaDonDTO> layDanhSachHoaDon() {
        List<HoaDonDTO> danhSach = new ArrayList<>();
        String sql = "SELECT * FROM HOADON";

        try {
            conn = DatabaseHelper.getConnection();
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();

            while (rs.next()) {
                HoaDonDTO hd = new HoaDonDTO(
                        rs.getString("MAHD").trim(),
                        rs.getString("MANV").trim(),
                        rs.getString("MAKH") != null ? rs.getString("MAKH").trim() : null,
                        rs.getDate("NGAYBAN"),
                        rs.getDouble("THANHTIEN"),
                        rs.getString("TRANGTHAI")
                );
                danhSach.add(hd);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResources();
        }
        return danhSach;
    }

    // Thêm hóa đơn mới
    public boolean themHoaDon(HoaDonDTO hd) {
        String sql = "INSERT INTO HOADON (MAHD, MANV, MAKH, NGAYBAN, THANHTIEN, TRANGTHAI) VALUES (?, ?, ?, ?, ?, ?)";

        try {
            conn = DatabaseHelper.getConnection();
            pst = conn.prepareStatement(sql);
            pst.setString(1, hd.getMaHoaDon().trim());
            pst.setString(2, hd.getMaNhanVien().trim());
            pst.setString(3, hd.getMaKH() != null ? hd.getMaKH().trim() : null);
            pst.setDate(4, new java.sql.Date(hd.getNgayBan().getTime()));
            pst.setDouble(5, hd.getThanhTien());
            pst.setString(6, hd.getTrangThai());

            return pst.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            closeResources();
        }
    }

    // Tìm kiếm hóa đơn
    public List<HoaDonDTO> timKiemHoaDon(String keyword, String fromDate, String toDate,
                                         Double minAmount, Double maxAmount, String trangThai) {
        List<HoaDonDTO> danhSach = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT * FROM HOADON WHERE 1=1");

        // Thêm điều kiện tìm kiếm theo mã (tự động nhận diện loại mã)
        if (keyword != null && !keyword.isEmpty()) {
            if (keyword.toUpperCase().startsWith("HD")) {
                sql.append(" AND MAHD LIKE ?");
                keyword = "%" + keyword + "%";
            } else if (keyword.toUpperCase().startsWith("NV")) {
                sql.append(" AND MANV LIKE ?");
                keyword = "%" + keyword + "%";
            } else if (keyword.toUpperCase().startsWith("KH")) {
                sql.append(" AND MAKH LIKE ?");
                keyword = "%" + keyword + "%";
            } else {
                // Tìm kiếm trên tất cả các trường mã nếu không có prefix
                sql.append(" AND (MAHD LIKE ? OR MANV LIKE ? OR MAKH LIKE ?)");
                keyword = "%" + keyword + "%";
            }
        }

        if (fromDate != null && !fromDate.isEmpty()) {
            sql.append(" AND NGAYBAN >= TO_DATE(?, 'DD-MM-YYYY')");
        }

        if (toDate != null && !toDate.isEmpty()) {
            sql.append(" AND NGAYBAN <= TO_DATE(?, 'DD-MM-YYYY')");
        }

        if (minAmount != null) {
            sql.append(" AND THANHTIEN >= ?");
        }

        if (maxAmount != null) {
            sql.append(" AND THANHTIEN <= ?");
        }

        // Thêm điều kiện lọc theo trạng thái
        if (trangThai != null && !trangThai.isEmpty() && !"Tất cả".equals(trangThai)) {
            String dbTrangThai = "BINH_THUONG";
            if ("Đã hủy".equals(trangThai)) {
                dbTrangThai = "DA_HUY";
            }
            sql.append(" AND TRANGTHAI = ?");
        }

        try {
            conn = DatabaseHelper.getConnection();
            pst = conn.prepareStatement(sql.toString());
            // Xử lý tham số
            int paramIndex = 1;
            if (keyword != null && !keyword.isEmpty()) {
                if (keyword.toUpperCase().startsWith("%HD") ||
                        keyword.toUpperCase().startsWith("%NV") ||
                        keyword.toUpperCase().startsWith("%KH")) {
                    pst.setString(paramIndex++, keyword);
                } else {
                    pst.setString(paramIndex++, keyword);
                    pst.setString(paramIndex++, keyword);
                    pst.setString(paramIndex++, keyword);
                }
            }

            if (fromDate != null && !fromDate.isEmpty()) {
                pst.setString(paramIndex++, fromDate);
            }

            if (toDate != null && !toDate.isEmpty()) {
                pst.setString(paramIndex++, toDate);
            }

            if (minAmount != null) {
                pst.setDouble(paramIndex++, minAmount);
            }

            if (maxAmount != null) {
                pst.setDouble(paramIndex++, maxAmount);
            }

            // Thêm tham số trạng thái nếu có
            if (trangThai != null && !trangThai.isEmpty() && !"Tất cả".equals(trangThai)) {
                String dbTrangThai = "BINH_THUONG";
                if ("Đã hủy".equals(trangThai)) {
                    dbTrangThai = "DA_HUY";
                }
                pst.setString(paramIndex++, dbTrangThai);
            }

            rs = pst.executeQuery();

            while (rs.next()) {
                HoaDonDTO hd = new HoaDonDTO(
                        rs.getString("MAHD"),
                        rs.getString("MANV"),
                        rs.getString("MAKH"),
                        rs.getDate("NGAYBAN"),
                        rs.getDouble("THANHTIEN"),
                        rs.getString("TRANGTHAI")
                );
                danhSach.add(hd);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResources();
        }
        return danhSach;
    }

    // Lấy chi tiết hóa đơn
    public List<ChiTietHoaDonDTO> layChiTietHoaDon(String maHoaDon) {
        List<ChiTietHoaDonDTO> danhSach = new ArrayList<>();
        String sql = "SELECT * FROM CHITIETHOADON WHERE MAHD = ?";

        try {
            conn = DatabaseHelper.getConnection();
            pst = conn.prepareStatement(sql);
            pst.setString(1, maHoaDon.trim());
            rs = pst.executeQuery();

            while (rs.next()) {
                ChiTietHoaDonDTO cthd = new ChiTietHoaDonDTO(
                        rs.getString("MAHD").trim(),
                        rs.getString("MASP").trim(),
                        rs.getInt("SOLUONG"),
                        rs.getDouble("GIA")
                );
                danhSach.add(cthd);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResources();
        }
        return danhSach;
    }

    // Các phương thức tính doanh thu
    public double getDoanhThuTheoNgay(java.util.Date ngay) {
        String sql = "SELECT SUM(THANHTIEN) AS doanhthu FROM HOADON WHERE TRUNC(NGAYBAN) = TRUNC(?) AND TRANGTHAI = 'BINH_THUONG'";
        try {
            conn = DatabaseHelper.getConnection();
            pst = conn.prepareStatement(sql);
            pst.setDate(1, new java.sql.Date(ngay.getTime()));

            rs = pst.executeQuery();
            if (rs.next()) {
                return rs.getDouble("doanhthu");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResources();
        }
        return 0;
    }

    public double getDoanhThuTheoThang(int thang, int nam) {
        String sql = "SELECT SUM(THANHTIEN) AS doanhthu FROM HOADON " +
                "WHERE EXTRACT(MONTH FROM NGAYBAN) = ? AND EXTRACT(YEAR FROM NGAYBAN) = ? AND TRANGTHAI = 'BINH_THUONG'";
        try {
            conn = DatabaseHelper.getConnection();
            pst = conn.prepareStatement(sql);
            pst.setInt(1, thang);
            pst.setInt(2, nam);

            rs = pst.executeQuery();
            if (rs.next()) {
                return rs.getDouble("doanhthu");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResources();
        }
        return 0;
    }

    public double getDoanhThuTheoNam(int nam) {
        String sql = "SELECT SUM(THANHTIEN) AS doanhthu FROM HOADON WHERE EXTRACT(YEAR FROM NGAYBAN) = ? AND TRANGTHAI = 'BINH_THUONG'";
        try {
            conn = DatabaseHelper.getConnection();
            pst = conn.prepareStatement(sql);
            pst.setInt(1, nam);

            rs = pst.executeQuery();
            if (rs.next()) {
                return rs.getDouble("doanhthu");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResources();
        }
        return 0;
    }

    public boolean capNhatTongTienHoaDon(String maHoaDon) {
        String sql = "UPDATE HOADON hd SET THANHTIEN = " +
                "(SELECT SUM(ct.SOLUONG * ct.GIA) FROM CHITIETHOADON ct WHERE ct.MAHD = hd.MAHD) " +
                "WHERE hd.MAHD = ?";

        try (Connection conn = DatabaseHelper.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setString(1, maHoaDon);
            return pst.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public HoaDonDTO layHoaDonTheoMa(String maHoaDon) {
        String sql = "SELECT * FROM HOADON WHERE MAHD = ?";
        try {
            conn = DatabaseHelper.getConnection();
            pst = conn.prepareStatement(sql);
            pst.setString(1, maHoaDon);
            rs = pst.executeQuery();

            if (rs.next()) {
                return new HoaDonDTO(
                        rs.getString("MAHD"),
                        rs.getString("MANV"),
                        rs.getString("MAKH"),
                        rs.getDate("NGAYBAN"),
                        rs.getDouble("THANHTIEN"),
                        rs.getString("TRANGTHAI")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResources();
        }
        return null;
    }

    public boolean xoaHoaDon(String maHoaDon) {
        String sql = "DELETE FROM HOADON WHERE MAHD = ?";
        try {
            conn = DatabaseHelper.getConnection();
            pst = conn.prepareStatement(sql);
            pst.setString(1, maHoaDon);
            return pst.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            closeResources();
        }
    }

    public boolean xoaTatCaChiTietHoaDon(String maHoaDon) {
        String sql = "DELETE FROM CHITIETHOADON WHERE MAHD = ?";
        try {
            conn = DatabaseHelper.getConnection();
            pst = conn.prepareStatement(sql);
            pst.setString(1, maHoaDon);
            return pst.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            closeResources();
        }
    }

    public String layTenSanPham(String maSanPham) {
        String sql = "SELECT TENSP FROM SANPHAM WHERE MASP = ?";
        try {
            conn = DatabaseHelper.getConnection();
            pst = conn.prepareStatement(sql);
            pst.setString(1, maSanPham);
            rs = pst.executeQuery();

            if (rs.next()) {
                return rs.getString("TENSP");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResources();
        }
        return "Không xác định";
    }

    public Map<String, String> layDanhSachTenSanPham(List<String> danhSachMaSP) {
        Map<String, String> result = new HashMap<>();
        if (danhSachMaSP == null || danhSachMaSP.isEmpty()) {
            return result;
        }

        // Tạo chuỗi tham số cho IN clause
        String placeholders = String.join(",", Collections.nCopies(danhSachMaSP.size(), "?"));
        String sql = "SELECT MASP, TENSP FROM SANPHAM WHERE MASP IN (" + placeholders + ")";

        try {
            conn = DatabaseHelper.getConnection();
            pst = conn.prepareStatement(sql);

            // Thiết lập các tham số
            for (int i = 0; i < danhSachMaSP.size(); i++) {
                pst.setString(i + 1, danhSachMaSP.get(i));
            }

            rs = pst.executeQuery();
            while (rs.next()) {
                result.put(rs.getString("MASP"), rs.getString("TENSP"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResources();
        }

        return result;
    }

    public boolean kiemTraMaHDTonTai(String maHD) {
        String sql = "SELECT 1 FROM HOADON WHERE TRIM(MAHD) = ?";
        try {
            conn = DatabaseHelper.getConnection();
            pst = conn.prepareStatement(sql);
            pst.setString(1, maHD.trim());
            rs = pst.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            closeResources();
        }
    }

    public boolean kiemTraNhanVienTonTai(String maNV) {
        String sql = "SELECT 1 FROM NHANVIEN WHERE TRIM(MANV) = ?";
        try (Connection conn = DatabaseHelper.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {

            pst.setString(1, maNV.trim());

            try (ResultSet rs = pst.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean kiemTraKhachHangTonTai(String maKH) {
        String sql = "SELECT 1 FROM KHACHHANG WHERE TRIM(MAKH) = ?";
        try {
            conn = DatabaseHelper.getConnection();
            pst = conn.prepareStatement(sql);
            pst.setString(1, maKH.trim());
            rs = pst.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            closeResources();
        }
    }

    public boolean themHoaDonVoiChiTiet(HoaDonDTO hd, List<ChiTietHoaDonDTO> danhSachChiTiet) {
        Connection conn = null;
        try {
            conn = DatabaseHelper.getConnection();
            conn.setAutoCommit(false);

            // Kiểm tra trùng mã hóa đơn
            String checkHDSql = "SELECT 1 FROM HOADON WHERE TRIM(MAHD) = ? FOR UPDATE NOWAIT";
            try (PreparedStatement pst = conn.prepareStatement(checkHDSql)) {
                pst.setString(1, hd.getMaHoaDon().trim());
                if (pst.executeQuery().next()) {
                    throw new SQLException("Mã hóa đơn đã tồn tại");
                }
            }

            // Thêm hóa đơn chính
            String sqlHD = "INSERT INTO HOADON (MAHD, MANV, MAKH, NGAYBAN, THANHTIEN, TRANGTHAI) VALUES (?, ?, ?, ?, ?, ?)";
            try (PreparedStatement pstHD = conn.prepareStatement(sqlHD)) {
                pstHD.setString(1, hd.getMaHoaDon().trim());
                pstHD.setString(2, hd.getMaNhanVien().trim());
                pstHD.setString(3, hd.getMaKH() != null ? hd.getMaKH().trim() : null);
                pstHD.setDate(4, new java.sql.Date(hd.getNgayBan().getTime()));
                pstHD.setDouble(5, hd.getThanhTien());
                pstHD.setString(6, hd.getTrangThai());
                pstHD.executeUpdate();
            }

            // Thêm chi tiết hóa đơn
            String sqlCT = "INSERT INTO CHITIETHOADON (MAHD, MASP, SOLUONG, GIA) VALUES (?, ?, ?, ?)";
            try (PreparedStatement pstCT = conn.prepareStatement(sqlCT)) {
                for (ChiTietHoaDonDTO ct : danhSachChiTiet) {
                    if (!giamSoLuongSanPham(ct.getMaSanPham().trim(), ct.getSoLuong(), conn)) {
                        throw new SQLException("Không thể cập nhật tồn kho cho " + ct.getMaSanPham());
                    }

                    pstCT.setString(1, ct.getMaHoaDon().trim());
                    pstCT.setString(2, ct.getMaSanPham().trim());
                    pstCT.setInt(3, ct.getSoLuong());
                    pstCT.setDouble(4, ct.getGia());
                    pstCT.addBatch();
                }
                pstCT.executeBatch();
            }

            conn.commit();
            return true;
        } catch (SQLException e) {
            try {
                if (conn != null) conn.rollback();
            } catch (SQLException ex) {
                System.err.println("Lỗi khi rollback: " + ex.getMessage());
            }

            JOptionPane.showMessageDialog(null, "Lỗi: " + e.getMessage(), "Lỗi hệ thống", JOptionPane.ERROR_MESSAGE);
            return false;
        } finally {
            try {
                if (conn != null) {
                    conn.setAutoCommit(true);
                    conn.close();
                }
            } catch (SQLException e) {
                System.err.println("Lỗi đóng kết nối: " + e.getMessage());
            }
        }
    }

    public boolean capNhatHoaDonVoiChiTiet(HoaDonDTO hd, List<ChiTietHoaDonDTO> danhSachChiTiet) {
        Connection connection = null;
        PreparedStatement pstHoaDon = null;
        PreparedStatement pstDeleteChiTiet = null;
        PreparedStatement pstInsertChiTiet = null;

        try {
            connection = DatabaseHelper.getConnection();
            connection.setAutoCommit(false); // Start transaction

            // 1. Update main bill information
            String sqlHoaDon = "UPDATE HOADON SET MANV = ?, MAKH = ?, NgayBan = ?, ThanhTien = ? WHERE MAHD = ?";
            pstHoaDon = connection.prepareStatement(sqlHoaDon);
            pstHoaDon.setString(1, hd.getMaNhanVien());
            pstHoaDon.setString(2, hd.getMaKH());
            pstHoaDon.setDate(3, new java.sql.Date(hd.getNgayBan().getTime()));
            pstHoaDon.setDouble(4, hd.getThanhTien());
            pstHoaDon.setString(5, hd.getMaHoaDon());
            int rowsUpdated = pstHoaDon.executeUpdate();

            if (rowsUpdated == 0) {
                connection.rollback();
                return false;
            }

            // 2. Delete all existing details
            String sqlDeleteChiTiet = "DELETE FROM CHITIETHOADON WHERE MAHD = ?";
            pstDeleteChiTiet = connection.prepareStatement(sqlDeleteChiTiet);
            pstDeleteChiTiet.setString(1, hd.getMaHoaDon());
            pstDeleteChiTiet.executeUpdate();

            // 3. Insert new details
            String sqlInsertChiTiet = "INSERT INTO CHITIETHOADON (MAHD, MaSP, SoLuong, Gia) VALUES (?, ?, ?, ?)";
            pstInsertChiTiet = connection.prepareStatement(sqlInsertChiTiet);

            for (ChiTietHoaDonDTO ct : danhSachChiTiet) {
                pstInsertChiTiet.setString(1, ct.getMaHoaDon());
                pstInsertChiTiet.setString(2, ct.getMaSanPham());
                pstInsertChiTiet.setInt(3, ct.getSoLuong());
                pstInsertChiTiet.setDouble(4, ct.getGia());
                pstInsertChiTiet.addBatch();
            }

            pstInsertChiTiet.executeBatch();

            connection.commit(); // Commit transaction
            return true;

        } catch (SQLException e) {
            try {
                if (connection != null) {
                    connection.rollback(); // Rollback if error occurs
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
            return false;
        } finally {
            // Close all resources
            try {
                if (pstInsertChiTiet != null) pstInsertChiTiet.close();
                if (pstDeleteChiTiet != null) pstDeleteChiTiet.close();
                if (pstHoaDon != null) pstHoaDon.close();
                if (connection != null) {
                    connection.setAutoCommit(true); // Reset auto-commit
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    // Thêm vào hoadonDAL.java
    private boolean giamSoLuongSanPham(String maSP, int soLuong, Connection conn) throws SQLException {
        String checkSql = "SELECT SOLUONG FROM SANPHAM WHERE TRIM(MASP) = ? FOR UPDATE";
        try (PreparedStatement checkStmt = conn.prepareStatement(checkSql)) {
            checkStmt.setString(1, maSP.trim());
            ResultSet rs = checkStmt.executeQuery();

            if (!rs.next()) {
                throw new SQLException("Sản phẩm " + maSP + " không tồn tại");
            }

            int soLuongTon = rs.getInt("SOLUONG");
            if (soLuongTon < soLuong) {
                throw new SQLException("Không đủ tồn kho cho " + maSP);
            }
        }

        String updateSql = "UPDATE SANPHAM SET SOLUONG = SOLUONG - ? WHERE TRIM(MASP) = ?";
        try (PreparedStatement pst = conn.prepareStatement(updateSql)) {
            pst.setInt(1, soLuong);
            pst.setString(2, maSP.trim());
            return pst.executeUpdate() > 0;
        }
    }

    public boolean huyHoaDon(String maHoaDon) {
        Connection conn = null;
        try {
            conn = DatabaseHelper.getConnection();
            conn.setAutoCommit(false);

            // 1. Lấy danh sách chi tiết
            List<ChiTietHoaDonDTO> chiTiet = layChiTietHoaDon(maHoaDon);

            // 2. Phục hồi số lượng tồn kho
            for (ChiTietHoaDonDTO ct : chiTiet) {
                String sql = "UPDATE SANPHAM SET SOLUONG = SOLUONG + ? WHERE MASP = ?";
                try (PreparedStatement pst = conn.prepareStatement(sql)) {
                    pst.setInt(1, ct.getSoLuong());
                    pst.setString(2, ct.getMaSanPham());
                    pst.executeUpdate();
                }
            }

            // 3. Cập nhật trạng thái hóa đơn
            String sqlUpdate = "UPDATE HOADON SET TRANGTHAI = 'DA_HUY' WHERE MAHD = ?";
            try (PreparedStatement pst = conn.prepareStatement(sqlUpdate)) {
                pst.setString(1, maHoaDon);
                pst.executeUpdate();
            }

            conn.commit();
            return true;
        } catch (SQLException e) {
            if (conn != null) try { conn.rollback(); } catch (SQLException ex) {}
            e.printStackTrace();
            return false;
        } finally {
            if (conn != null) try { conn.close(); } catch (SQLException e) {}
        }
    }

    //    // Đóng kết nối
    private void closeResources() {
        try {
            if (rs != null) rs.close();
            if (pst != null) pst.close();
            if (conn != null) conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
package dal;

import dto.HoaDonDTO;
import dto.ChiTietHoaDonDTO;
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
                        rs.getString("MAHOADON"),
                        rs.getString("MANHANVIEN"),
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

    // Thêm hóa đơn mới
    public boolean themHoaDon(HoaDonDTO hd) {
        String sql = "INSERT INTO HOADON (MAHOADON, MANHANVIEN, MAKH, NGAYBAN, THANHTIEN) VALUES (?, ?, ?, ?, ?)";

        try {
            conn = DatabaseHelper.getConnection();
            pst = conn.prepareStatement(sql);
            pst.setString(1, hd.getMaHoaDon());
            pst.setString(2, hd.getMaNhanVien());
            pst.setString(3, hd.getMaKH());
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
                sql.append(" AND MAHOADON LIKE ?");
                keyword = "%" + keyword + "%";
            } else if (keyword.toUpperCase().startsWith("NV")) {
                sql.append(" AND MANHANVIEN LIKE ?");
                keyword = "%" + keyword + "%";
            } else if (keyword.toUpperCase().startsWith("KH")) {
                sql.append(" AND MAKH LIKE ?");
                keyword = "%" + keyword + "%";
            } else {
                // Tìm kiếm trên tất cả các trường mã nếu không có prefix
                sql.append(" AND (MAHOADON LIKE ? OR MANHANVIEN LIKE ? OR MAKH LIKE ?)");
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
                        rs.getString("MAHOADON"),
                        rs.getString("MANHANVIEN"),
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
        String sql = "SELECT * FROM CHITIETHOADON WHERE MAHOADON = ?";

        try {
            conn = DatabaseHelper.getConnection();
            pst = conn.prepareStatement(sql);
            pst.setString(1, maHoaDon);
            rs = pst.executeQuery();

            while (rs.next()) {
                ChiTietHoaDonDTO cthd = new ChiTietHoaDonDTO(
                        rs.getString("MAHOADON"),
                        rs.getString("MASP"),
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

    // Các phương thức tính doanh thu (giữ nguyên từ code cũ)
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
                "(SELECT SUM(ct.SOLUONG * ct.GIA) FROM CHITIETHOADON ct WHERE ct.MAHOADON = hd.MAHOADON) " +
                "WHERE hd.MAHOADON = ?";

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
        String sql = "SELECT * FROM HOADON WHERE MAHOADON = ?";
        try {
            conn = DatabaseHelper.getConnection();
            pst = conn.prepareStatement(sql);
            pst.setString(1, maHoaDon);
            rs = pst.executeQuery();

            if (rs.next()) {
                return new HoaDonDTO(
                        rs.getString("MAHOADON"),
                        rs.getString("MANHANVIEN"),
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
        String sql = "DELETE FROM HOADON WHERE MAHOADON = ?";
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
        String sql = "DELETE FROM CHITIETHOADON WHERE MAHOADON = ?";
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
        String sql = "SELECT 1 FROM HOADON WHERE MAHOADON = ?";
        try {
            conn = DatabaseHelper.getConnection();
            pst = conn.prepareStatement(sql);
            pst.setString(1, maHD);
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
        String sql = "SELECT 1 FROM NHANVIEN WHERE MANHANVIEN = ?";
        try {
            conn = DatabaseHelper.getConnection();
            pst = conn.prepareStatement(sql);
            pst.setString(1, maNV);
            rs = pst.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            closeResources();
        }
    }

    public boolean kiemTraKhachHangTonTai(String maKH) {
        String sql = "SELECT 1 FROM KHACHHANG WHERE MAKH = ?";
        try {
            conn = DatabaseHelper.getConnection();
            pst = conn.prepareStatement(sql);
            pst.setString(1, maKH);
            rs = pst.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            closeResources();
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

    public boolean themHoaDonVoiChiTiet(HoaDonDTO hd, List<ChiTietHoaDonDTO> danhSachChiTiet) {
        Connection conn = null;
        try {
            conn = DatabaseHelper.getConnection();
            conn.setAutoCommit(false);

            // 1. Thêm hóa đơn chính
            String sqlHD = "INSERT INTO HOADON (MAHOADON, MANHANVIEN, MAKH, NGAYBAN, THANHTIEN, TRANGTHAI) VALUES (?, ?, ?, ?, ?, ?)";

            try (PreparedStatement pstHD = conn.prepareStatement(sqlHD)) {
                pstHD.setString(1, hd.getMaHoaDon());
                pstHD.setString(2, hd.getMaNhanVien());
                pstHD.setString(3, hd.getMaKH() == null || hd.getMaKH().isEmpty() ? null : hd.getMaKH());
                pstHD.setDate(4, new java.sql.Date(hd.getNgayBan().getTime()));
                pstHD.setDouble(5, hd.getThanhTien());
                pstHD.setString(6, hd.getTrangThai());


                if (pstHD.executeUpdate() <= 0) {
                    conn.rollback();
                    return false;
                }
            }

            // 2. Thêm chi tiết hóa đơn
            String sqlCT = "INSERT INTO CHITIETHOADON (MAHOADON, MASP, SOLUONG, GIA) VALUES (?, ?, ?, ?)";

            try (PreparedStatement pstCT = conn.prepareStatement(sqlCT)) {
                for (ChiTietHoaDonDTO cthd : danhSachChiTiet) {

                    // Trừ số lượng tồn kho trước
                    if (!giamSoLuongSanPham(cthd.getMaSanPham(), cthd.getSoLuong(), conn)) {
                        conn.rollback();
                        return false;
                    }

                    pstCT.setString(1, cthd.getMaHoaDon());
                    pstCT.setString(2, cthd.getMaSanPham());
                    pstCT.setInt(3, cthd.getSoLuong());
                    pstCT.setDouble(4, cthd.getGia());
                    pstCT.addBatch();
                }

                int[] results = pstCT.executeBatch();

                for (int i = 0; i < results.length; i++) {
                    if (results[i] == Statement.EXECUTE_FAILED) {
                        conn.rollback();
                        return false;
                    }
                }
            }

            conn.commit();
            return true;
        } catch (SQLException e) {
            System.err.println("SQLException trong DAL: " + e.getMessage());
            try {
                if (conn != null) {
                    conn.rollback();
                    System.err.println("Đã rollback transaction");
                }
            } catch (SQLException ex) {
                System.err.println("Lỗi khi rollback: " + ex.getMessage());
            }
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (conn != null) {
                    conn.setAutoCommit(true);
                    conn.close();
                }
            } catch (SQLException e) {
                System.err.println("Lỗi khi đóng kết nối: " + e.getMessage());
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
            String sqlHoaDon = "UPDATE HOADON SET MaNhanVien = ?, MaKH = ?, NgayBan = ?, ThanhTien = ? WHERE MaHoaDon = ?";
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
            String sqlDeleteChiTiet = "DELETE FROM CHITIETHOADON WHERE MaHoaDon = ?";
            pstDeleteChiTiet = connection.prepareStatement(sqlDeleteChiTiet);
            pstDeleteChiTiet.setString(1, hd.getMaHoaDon());
            pstDeleteChiTiet.executeUpdate();

            // 3. Insert new details
            String sqlInsertChiTiet = "INSERT INTO CHITIETHOADON (MaHoaDon, MaSP, SoLuong, Gia) VALUES (?, ?, ?, ?)";
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
        String sql = "UPDATE SANPHAM SET HANGTON = HANGTON - ? WHERE MASP = ? AND HANGTON >= ?";
        try (PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setInt(1, soLuong);
            pst.setString(2, maSP);
            pst.setInt(3, soLuong); // Đảm bảo số lượng tồn đủ để trừ
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
                String sql = "UPDATE SANPHAM SET HANGTON = HANGTON + ? WHERE MASP = ?";
                try (PreparedStatement pst = conn.prepareStatement(sql)) {
                    pst.setInt(1, ct.getSoLuong());
                    pst.setString(2, ct.getMaSanPham());
                    pst.executeUpdate();
                }
            }

            // 3. Cập nhật trạng thái hóa đơn
            String sqlUpdate = "UPDATE HOADON SET TRANGTHAI = 'DA_HUY' WHERE MAHOADON = ?";
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
}
package dal;

import dto.PhieuNhapHangDTO;
import dto.ChiTietPhieuNhapHangDTO;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PhieuNhapHangDAL {
    private final String url = "jdbc:oracle:thin:@localhost:1521:orcl";
    private final String user = "c##Mthuda";
    private final String pass = "Minhthu05#";


    // Các phương thức public để BLL có thể gọi

    public boolean themPhieuNhapHang(PhieuNhapHangDTO pnh) {
        return themPhieuNhapHang(pnh, null);
    }

    public boolean themChiTietPhieuNhapHang(ChiTietPhieuNhapHangDTO ct) {
        return themChiTietPhieuNhapHang(ct, null);
    }

    public boolean xoaChiTietPhieuNhapHang(String maPNH, String maSP) {
        return xoaChiTietPhieuNhapHang(maPNH, maSP, null);
    }

    public boolean capNhatChiTietPhieuNhapHang(ChiTietPhieuNhapHangDTO ct) {
        return capNhatChiTietPhieuNhapHang(ct, null);
    }

    public List<ChiTietPhieuNhapHangDTO> layChiTietPhieuNhapHang(String maPNH) {
        return layChiTietPhieuNhapHang(maPNH, null);
    }

    public double tinhTongTienPhieuNhap(String maPNH) throws SQLException {
        return tinhTongTienPhieuNhap(maPNH, null);
    }
    // Lấy danh sách phiếu nhập hàng
    public List<PhieuNhapHangDTO> layDanhSachPhieuNhapHang() {
        List<PhieuNhapHangDTO> danhSach = new ArrayList<>();
        String sql = "SELECT * FROM PHIEUNHAPHANG ORDER BY NGAYLAPPHIEU DESC";
        
        try (Connection conn = DriverManager.getConnection(url, user, pass);
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                PhieuNhapHangDTO pnh = new PhieuNhapHangDTO(
                    rs.getString("MAPNH"),
                    rs.getString("MANCU"),
                    rs.getString("MANHANVIEN"),
                    rs.getDate("NGAYLAPPHIEU"),
                    rs.getDouble("THANHTIEN")
                );
                danhSach.add(pnh);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return danhSach;
    }

    public PhieuNhapHangDTO layPhieuNhapHangTheoMa(String maPNH) {
        String sql = "SELECT * FROM PHIEUNHAPHANG WHERE MAPNH = ?";

        try (Connection conn = DriverManager.getConnection(url, user, pass);
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, maPNH);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new PhieuNhapHangDTO(
                            rs.getString("MAPNH"),
                            rs.getString("MANCU"),
                            rs.getString("MANHANVIEN"),
                            rs.getDate("NGAYLAPPHIEU"),
                            rs.getDouble("THANHTIEN")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Thêm phiếu nhập hàng
    public boolean themPhieuNhapHang(PhieuNhapHangDTO pnh, Connection conn) {
        String sql = "INSERT INTO PHIEUNHAPHANG (MAPNH, MANCU, MANHANVIEN, NGAYLAPPHIEU, THANHTIEN) VALUES (?, ?, ?, ?, ?)";
        boolean shouldClose = conn == null;

        try {
            if (conn == null) conn = DatabaseHelper.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1, pnh.getMaPNH());
            ps.setString(2, pnh.getMaNCU());
            ps.setString(3, pnh.getMaNhanVien());
            ps.setDate(4, new java.sql.Date(pnh.getNgayLapPhieu().getTime()));
            ps.setDouble(5, pnh.getThanhTien());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            if (shouldClose && conn != null) DatabaseHelper.closeConnection(conn);
        }
    }

    // Xóa phiếu nhập hàng
    public boolean xoaPhieuNhapHang(String maPNH, Connection conn) throws SQLException {
        String sql = "DELETE FROM PHIEUNHAPHANG WHERE MAPNH = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, maPNH);
            return ps.executeUpdate() > 0;
        }
    }


    // Thêm các phương thức overload có tham số Connection
    public boolean capNhatPhieuNhapHang(PhieuNhapHangDTO pnh, Connection conn) throws SQLException {
        String sql = "UPDATE PHIEUNHAPHANG SET MANCU = ?, MANHANVIEN = ?, NGAYLAPPHIEU = ?, THANHTIEN = ? WHERE MAPNH = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, pnh.getMaNCU());
            ps.setString(2, pnh.getMaNhanVien());
            ps.setDate(3, new java.sql.Date(pnh.getNgayLapPhieu().getTime()));
            ps.setDouble(4, pnh.getThanhTien());
            ps.setString(5, pnh.getMaPNH());
            return ps.executeUpdate() > 0;
        }
    }

    public List<ChiTietPhieuNhapHangDTO> layChiTietPhieuNhapHang(String maPNH, Connection conn) {
        List<ChiTietPhieuNhapHangDTO> danhSach = new ArrayList<>();
        String sql = "SELECT * FROM CHITIETPHIEUNHAPHANG WHERE MAPNH = ?";
        boolean shouldClose = conn == null;

        try {
            if (conn == null) conn = DatabaseHelper.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1, maPNH);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                danhSach.add(new ChiTietPhieuNhapHangDTO(
                        rs.getString("MAPNH"),
                        rs.getString("MASP"),
                        rs.getInt("SLNHAP"),
                        rs.getDouble("GIANHAP")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (shouldClose && conn != null) DatabaseHelper.closeConnection(conn);
        }
        return danhSach;
    }

    public boolean xoaChiTietPhieuNhapHang(String maPNH, String maSP, Connection conn) {
        String sql = "DELETE FROM CHITIETPHIEUNHAPHANG WHERE MAPNH = ? AND MASP = ?";
        boolean shouldClose = conn == null;

        try {
            if (conn == null) conn = DatabaseHelper.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1, maPNH);
            ps.setString(2, maSP);

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            if (shouldClose && conn != null) DatabaseHelper.closeConnection(conn);
        }
    }

    public boolean capNhatChiTietPhieuNhapHang(ChiTietPhieuNhapHangDTO ct, Connection conn) {
        String sql = "UPDATE CHITIETPHIEUNHAPHANG SET SLNHAP = ?, GIANHAP = ? WHERE MAPNH = ? AND MASP = ?";
        boolean shouldClose = conn == null;

        try {
            if (conn == null) conn = DatabaseHelper.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setInt(1, ct.getSoLuongNhap());
            ps.setDouble(2, ct.getGiaNhap());
            ps.setString(3, ct.getMaPNH());
            ps.setString(4, ct.getMaSP());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            if (shouldClose && conn != null) DatabaseHelper.closeConnection(conn);
        }
    }

    public boolean themChiTietPhieuNhapHang(ChiTietPhieuNhapHangDTO ct, Connection conn) {
        String sql = "INSERT INTO CHITIETPHIEUNHAPHANG (MAPNH, MASP, SLNHAP, GIANHAP) VALUES (?, ?, ?, ?)";
        boolean shouldClose = conn == null;

        try {
            if (conn == null) conn = DatabaseHelper.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1, ct.getMaPNH());
            ps.setString(2, ct.getMaSP());
            ps.setInt(3, ct.getSoLuongNhap());
            ps.setDouble(4, ct.getGiaNhap());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            if (shouldClose && conn != null) DatabaseHelper.closeConnection(conn);
        }
    }

//    public double tinhTongTienPhieuNhap(String maPNH, Connection conn) {
//        String sql = "SELECT SUM(SLNHAP * GIANHAP) AS TONGTIEN FROM CHITIETPHIEUNHAPHANG WHERE MAPNH = ?";
//        double tongTien = 0;
//        boolean shouldClose = conn == null;
//
//        try {
//            if (conn == null) conn = DatabaseHelper.getConnection();
//            PreparedStatement ps = conn.prepareStatement(sql);
//
//            ps.setString(1, maPNH);
//            ResultSet rs = ps.executeQuery();
//
//            if (rs.next()) {
//                tongTien = rs.getDouble("TONGTIEN");
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        } finally {
//            if (shouldClose && conn != null) DatabaseHelper.closeConnection(conn);
//        }
//        return tongTien;
//    }

    public boolean capNhatThanhTien(String maPNH, double thanhTien, Connection conn) throws SQLException {
        String sql = "UPDATE PHIEUNHAPHANG SET THANHTIEN = ? WHERE MAPNH = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setDouble(1, thanhTien);
            ps.setString(2, maPNH);
            return ps.executeUpdate() > 0;
        }
    }

    public double tinhTongTienPhieuNhap(String maPNH, Connection conn) throws SQLException {
        String sql = "SELECT SUM(SLNHAP * GIANHAP) AS TONGTIEN FROM CHITIETPHIEUNHAPHANG WHERE MAPNH = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, maPNH);
            ResultSet rs = ps.executeQuery();
            return rs.next() ? rs.getDouble("TONGTIEN") : 0;
        }
    }

    public List<PhieuNhapHangDTO> timKiemPhieuNhapHang(String keyword, String fromDate, String toDate,
                                                       Double minAmount, Double maxAmount) {
        List<PhieuNhapHangDTO> danhSach = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT * FROM PHIEUNHAPHANG WHERE 1=1");

        // Thêm điều kiện tìm kiếm theo mã (tự động nhận diện loại mã)
        if (keyword != null && !keyword.isEmpty()) {
            if (keyword.toUpperCase().startsWith("PNH")) {
                sql.append(" AND MAPNH LIKE ?");
                keyword = "%" + keyword + "%";
            } else if (keyword.toUpperCase().startsWith("NCC")) {
                sql.append(" AND MANCU LIKE ?");
                keyword = "%" + keyword + "%";
            } else if (keyword.toUpperCase().startsWith("NV")) {
                sql.append(" AND MANHANVIEN LIKE ?");
                keyword = "%" + keyword + "%";
            } else {
                // Tìm kiếm trên tất cả các trường mã nếu không có prefix
                sql.append(" AND (MAPNH LIKE ? OR MANCU LIKE ? OR MANHANVIEN LIKE ?)");
                keyword = "%" + keyword + "%";
            }
        }

        // Thêm điều kiện ngày
        if (fromDate != null && !fromDate.isEmpty()) {
            sql.append(" AND NGAYLAPPHIEU >= TO_DATE(?, 'DD-MM-YYYY')");
        }

        if (toDate != null && !toDate.isEmpty()) {
            sql.append(" AND NGAYLAPPHIEU <= TO_DATE(?, 'DD-MM-YYYY')");
        }

        // Thêm điều kiện tiền
        if (minAmount != null) {
            sql.append(" AND THANHTIEN >= ?");
        }

        if (maxAmount != null) {
            sql.append(" AND THANHTIEN <= ?");
        }

        sql.append(" ORDER BY NGAYLAPPHIEU DESC");

        try (Connection conn = DriverManager.getConnection(url, user, pass);
             PreparedStatement ps = conn.prepareStatement(sql.toString())) {

            int paramIndex = 1;

            // Xử lý tham số keyword
            if (keyword != null && !keyword.isEmpty()) {
                if (keyword.toUpperCase().startsWith("%PNH") ||
                        keyword.toUpperCase().startsWith("%NCC") ||
                        keyword.toUpperCase().startsWith("%NV")) {
                    ps.setString(paramIndex++, keyword);
                } else {
                    ps.setString(paramIndex++, keyword);
                    ps.setString(paramIndex++, keyword);
                    ps.setString(paramIndex++, keyword);
                }
            }

            // Xử lý tham số ngày
            if (fromDate != null && !fromDate.isEmpty()) {
                ps.setString(paramIndex++, fromDate);
            }

            if (toDate != null && !toDate.isEmpty()) {
                ps.setString(paramIndex++, toDate);
            }

            // Xử lý tham số tiền
            if (minAmount != null) {
                ps.setDouble(paramIndex++, minAmount);
            }

            if (maxAmount != null) {
                ps.setDouble(paramIndex++, maxAmount);
            }

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    PhieuNhapHangDTO pnh = new PhieuNhapHangDTO(
                            rs.getString("MAPNH"),
                            rs.getString("MANCU"),
                            rs.getString("MANHANVIEN"),
                            rs.getDate("NGAYLAPPHIEU"),
                            rs.getDouble("THANHTIEN")
                    );
                    danhSach.add(pnh);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return danhSach;
    }
}
package bll;

import dal.DatabaseHelper;
import dal.SanPhamDAL;
import dal.hoadonDAL;
import dto.ChiTietHoaDonDTO;
import dto.HoaDonDTO;

import javax.swing.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class HoaDonBLL {
    private hoadonDAL hdDAL = new hoadonDAL();
    private Connection connection;

    public HoaDonBLL() {
        try {
            this.connection = DatabaseHelper.getConnection();
            this.hdDAL.setConnection(this.connection);
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Không thể kết nối database: " + e.getMessage());
        }
    }

    // Thêm phương thức đóng kết nối
    public void closeConnection() {
        DatabaseHelper.closeConnection(this.connection);
    }

    // Các phương thức tính doanh thu (giữ nguyên từ code cũ)
    public double tinhDoanhThuTheoNgay(Date ngay) {
        return hdDAL.getDoanhThuTheoNgay(ngay);
    }

    public double tinhDoanhThuTheoThang(int thang, int nam) {
        return hdDAL.getDoanhThuTheoThang(thang, nam);
    }

    public double tinhDoanhThuTheoNam(int nam) {
        return hdDAL.getDoanhThuTheoNam(nam);
    }

    public String formatDoanhThu(double doanhThu) {
        return String.format("%,.0f VNĐ", doanhThu);
    }

    // Các phương thức quản lý hóa đơn mới
    public List<HoaDonDTO> layDanhSachHoaDon() {
        return hdDAL.layDanhSachHoaDon();
    }

    public boolean themHoaDon(HoaDonDTO hd) {
        return hdDAL.themHoaDon(hd);
    }

    // Cập nhật phương thức tìm kiếm để hỗ trợ lọc trạng thái
    public List<HoaDonDTO> timKiemHoaDon(String keyword, String fromDate, String toDate,
                                         Double minAmount, Double maxAmount, String trangThai) {
        return hdDAL.timKiemHoaDon(keyword, fromDate, toDate, minAmount, maxAmount, trangThai);
    }

    public List<ChiTietHoaDonDTO> layChiTietHoaDon(String maHoaDon) {
        return hdDAL.layChiTietHoaDon(maHoaDon);
    }

    public String taoMaHoaDonMoi() {
        List<HoaDonDTO> danhSach = hdDAL.layDanhSachHoaDon();
        if (danhSach.isEmpty()) {
            return "HD000001";
        }

        String maCuoi = danhSach.get(danhSach.size() - 1).getMaHoaDon();
        int so = Integer.parseInt(maCuoi.substring(2)) + 1;
        return String.format("HD%06d", so);
    }

    public boolean capNhatTongTienHoaDon(String maHoaDon) {
        return hdDAL.capNhatTongTienHoaDon(maHoaDon);
    }

    public HoaDonDTO layHoaDonTheoMa(String maHoaDon) {
        return hdDAL.layHoaDonTheoMa(maHoaDon);
    }

    public boolean xoaHoaDon(String maHoaDon) {
        // Xóa chi tiết trước, sau đó xóa hóa đơn
        boolean xoaChiTiet = hdDAL.xoaTatCaChiTietHoaDon(maHoaDon);
        if (xoaChiTiet) {
            return hdDAL.xoaHoaDon(maHoaDon);
        }
        return false;
    }

    public String layTenSanPham(String maSanPham) {
        return hdDAL.layTenSanPham(maSanPham);
    }

    public Map<String, String> layDanhSachTenSanPham(List<String> danhSachMaSP) {
        return hdDAL.layDanhSachTenSanPham(danhSachMaSP);
    }

    // Thêm các phương thức mới
    public boolean themHoaDonVoiChiTiet(HoaDonDTO hd, List<ChiTietHoaDonDTO> danhSachChiTiet) {
        try {
            System.out.println("Bắt đầu thêm hóa đơn " + hd.getMaHoaDon());
            // Bắt đầu transaction
            connection.setAutoCommit(false);

            // Kiểm tra số lượng tồn kho
            System.out.println("Kiểm tra tồn kho...");

            if (!kiemTraSoLuongTonKho(danhSachChiTiet)) {
                System.out.println("Lỗi: Không đủ tồn kho");

                JOptionPane.showMessageDialog(null, "Một số sản phẩm không đủ số lượng tồn kho!");
                connection.rollback();
                return false;
            }

            // Tính tổng tiền
            double tongTien = danhSachChiTiet.stream()
                    .mapToDouble(ct -> ct.getSoLuong() * ct.getGia())
                    .sum();
            System.out.println("Tổng tiền: " + tongTien);
            hd.setThanhTien(tongTien);

            // Thêm hóa đơn và chi tiết
            System.out.println("Thêm hóa đơn vào DAL...");

            boolean result = hdDAL.themHoaDonVoiChiTiet(hd, danhSachChiTiet);

            if (result) {
                System.out.println("Commit transaction...");

                connection.commit();
                return true;
            } else {
                System.out.println("Rollback do thêm hóa đơn thất bại");

                connection.rollback();
                return false;
            }
        } catch (SQLException e) {
            String errorMsg = "Lỗi database: ";
            if (e.getErrorCode() == 547) { // Lỗi khóa ngoại
                errorMsg += "Mã sản phẩm không tồn tại";
            } else if (e.getErrorCode() == 515) { // Lỗi null
                errorMsg += "Thiếu thông tin bắt buộc";
            } else {
                errorMsg += e.getMessage();
            }

            System.err.println(errorMsg);
            JOptionPane.showMessageDialog(null, errorMsg, "Lỗi database", JOptionPane.ERROR_MESSAGE);

            try {
                connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            return false;
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                System.err.println("Lỗi khi set auto commit: " + e.getMessage());

                e.printStackTrace();
            }
        }
    }

    private boolean kiemTraSoLuongTonKho(List<ChiTietHoaDonDTO> danhSachChiTiet) {
        SanPhamDAL spDAL = new SanPhamDAL();
        try {
            for (ChiTietHoaDonDTO ct : danhSachChiTiet) {
                int tonKho = spDAL.getSoLuongTon(ct.getMaSanPham());
                System.out.println("Sản phẩm " + ct.getMaSanPham() +
                        " - Yêu cầu: " + ct.getSoLuong() +
                        " - Tồn kho: " + tonKho);
                if (tonKho < ct.getSoLuong()) {
                    System.out.println("Không đủ tồn kho cho sản phẩm " + ct.getMaSanPham());

                    return false;
                }
            }
            return true;
        } catch (Exception e) {
            System.err.println("Lỗi khi kiểm tra tồn kho: " + e.getMessage());

            e.printStackTrace();
            return false;
        }
    }

    public boolean kiemTraMaHDTonTai(String maHD) {
        return hdDAL.kiemTraMaHDTonTai(maHD);
    }

    public boolean kiemTraNhanVienTonTai(String maNV) {
        return hdDAL.kiemTraNhanVienTonTai(maNV);
    }

    public boolean kiemTraKhachHangTonTai(String maKH) {
        return hdDAL.kiemTraKhachHangTonTai(maKH);
    }

    public boolean capNhatHoaDonVoiChiTiet(HoaDonDTO hd, List<ChiTietHoaDonDTO> danhSachChiTiet) {
        // Validate input data
        if (hd == null || hd.getMaHoaDon() == null || hd.getMaHoaDon().trim().isEmpty()) {
            return false;
        }

        if (hd.getMaNhanVien() == null || hd.getMaNhanVien().trim().isEmpty()) {
            return false;
        }

        if (hd.getNgayBan() == null) {
            return false;
        }

        // Validate details
        if (danhSachChiTiet == null || danhSachChiTiet.isEmpty()) {
            return false;
        }

        for (ChiTietHoaDonDTO ct : danhSachChiTiet) {
            if (ct.getMaSanPham() == null || ct.getMaSanPham().trim().isEmpty()) {
                return false;
            }
            if (ct.getSoLuong() <= 0) {
                return false;
            }
            if (ct.getGia() <= 0) {
                return false;
            }
        }

        // Calculate total amount
        double tongTien = danhSachChiTiet.stream()
                .mapToDouble(ct -> ct.getSoLuong() * ct.getGia())
                .sum();
        hd.setThanhTien(tongTien);

        // Check if bill exists
        if (!kiemTraMaHDTonTai(hd.getMaHoaDon())) {
            return false;
        }

        // Check if employee exists
        if (!kiemTraNhanVienTonTai(hd.getMaNhanVien())) {
            return false;
        }

        // Check if customer exists (if provided)
        if (hd.getMaKH() != null && !hd.getMaKH().trim().isEmpty() &&
                !kiemTraKhachHangTonTai(hd.getMaKH())) {
            return false;
        }

        // Check if all products exist
        for (ChiTietHoaDonDTO ct : danhSachChiTiet) {
            if (!kiemTraSanPhamTonTai(ct.getMaSanPham())) {
                return false;
            }
        }

        // Call DAL to perform the update
        return hdDAL.capNhatHoaDonVoiChiTiet(hd, danhSachChiTiet);
    }

    // Helper method to check if product exists
    private boolean kiemTraSanPhamTonTai(String maSP) {
        // Implement this based on your system
        // Example:
        SanPhamDAL spDAL = new SanPhamDAL();
        return spDAL.getSanPhamById(maSP) != null;
    }

    // Thay thế phương thức xóa bằng hủy
    public boolean huyHoaDon(String maHoaDon) {
        try {
            // Kiểm tra xem hóa đơn đã hủy chưa
            HoaDonDTO hd = hdDAL.layHoaDonTheoMa(maHoaDon);
            if (hd == null || "DA_HUY".equals(hd.getTrangThai())) {
                return false;
            }

            return hdDAL.huyHoaDon(maHoaDon);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public void commitTransaction() {
        try {
            if (connection != null && !connection.getAutoCommit()) {
                connection.commit();
                connection.setAutoCommit(true);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                if (connection != null) {
                    connection.rollback();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }
}
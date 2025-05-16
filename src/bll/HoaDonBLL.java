package bll;

import dal.SanPhamDAL;
import dal.hoadonDAL;
import dto.ChiTietHoaDonDTO;
import dto.HoaDonDTO;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class HoaDonBLL {
    private hoadonDAL hdDAL = new hoadonDAL();

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

    public List<HoaDonDTO> timKiemHoaDon(String keyword, String fromDate,
                                         String toDate, Double minAmount, Double maxAmount) {
        return hdDAL.timKiemHoaDon(keyword, fromDate, toDate, minAmount, maxAmount);
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
            boolean result = hdDAL.themHoaDonVoiChiTiet(hd, danhSachChiTiet);
            return result;
        } catch (Exception e) {
            System.err.println("Lỗi trong BLL: " + e.getMessage());
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

}
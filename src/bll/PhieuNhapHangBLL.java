package bll;

import dal.PhieuNhapHangDAL;
import dal.SanPhamDAL;
import dto.PhieuNhapHangDTO;
import dto.ChiTietPhieuNhapHangDTO;
import dto.SanPhamDTO;
import java.text.SimpleDateFormat;

import dal.DatabaseHelper;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.stream.Collectors;

public class PhieuNhapHangBLL {
    private PhieuNhapHangDAL pnhDAL = new PhieuNhapHangDAL();
    private SanPhamDAL spDAL = new SanPhamDAL();
    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

    // Lấy danh sách phiếu nhập hàng
    public List<PhieuNhapHangDTO> layDanhSachPhieuNhapHang() {
        return pnhDAL.layDanhSachPhieuNhapHang();
    }

    // Thêm phiếu nhập hàng
    public boolean themPhieuNhapHang(PhieuNhapHangDTO pnh, List<ChiTietPhieuNhapHangDTO> danhSachChiTiet) {
        Connection conn = null;
        try {
            conn = DatabaseHelper.getConnection();
            conn.setAutoCommit(false);

            // 1. Thêm phiếu nhập chính
            if (!pnhDAL.themPhieuNhapHang(pnh, conn)) {
                conn.rollback();
                return false;
            }

            // 2. Thêm chi tiết và cập nhật số lượng tồn
            for (ChiTietPhieuNhapHangDTO ct : danhSachChiTiet) {
                if (!pnhDAL.themChiTietPhieuNhapHang(ct, conn)) {
                    conn.rollback();
                    return false;
                }

                // Cập nhật số lượng tồn kho
//                SanPhamDTO sp = spDAL.laySanPhamTheoMa(ct.getMaSP(), conn);
//                if (sp != null) {
//                    sp.setsoluong(sp.getsoluong() + ct.getSoLuongNhap());
//                    if (!spDAL.updateSanPham(sp)) {
//                        conn.rollback();
//                        return false;
//                    }
//                }
            }

            // 3. Cập nhật tổng tiền
            double tongTien = pnhDAL.tinhTongTienPhieuNhap(pnh.getMaPNH(), conn);
            pnh.setThanhTien(tongTien);
            if (!pnhDAL.capNhatPhieuNhapHang(pnh, conn)) {
                conn.rollback();
                return false;
            }

            conn.commit();
            return true;
        } catch (SQLException e) {
            if (conn != null) {
                try { conn.rollback(); } catch (SQLException ex) {}
            }
            e.printStackTrace();
            return false;
        } finally {
            DatabaseHelper.closeConnection(conn);
        }
    }

    // Xóa phiếu nhập hàng
    public boolean xoaPhieuNhapHang(String maPNH) {
        Connection conn = null;
        try {
            conn = DatabaseHelper.getConnection();
            conn.setAutoCommit(false);

//            // 1. Lấy danh sách chi tiết trước khi xóa
//            List<ChiTietPhieuNhapHangDTO> danhSachChiTiet = pnhDAL.layChiTietPhieuNhapHang(maPNH, conn);
//
//            // 2. Xóa chi tiết và cập nhật số lượng tồn
//            for (ChiTietPhieuNhapHangDTO ct : danhSachChiTiet) {
//                // Trừ số lượng tồn kho trước khi xóa chi tiết
////                SanPhamDTO sp = spDAL.laySanPhamTheoMa(ct.getMaSP(), conn);
////                if (sp != null) {
////                    sp.setsoluong(sp.getsoluong() - ct.getSoLuongNhap());
////                    if (!spDAL.updateSanPham(sp)) {
////                        conn.rollback();
////                        return false;
////                    }
////                }
//
//                if (!pnhDAL.xoaChiTietPhieuNhapHang(ct.getMaPNH(), ct.getMaSP(), conn)) {
//                    conn.rollback();
//                    return false;
//                }
//            }

            // 3. Xóa phiếu nhập chính
            if (!pnhDAL.xoaPhieuNhapHang(maPNH, conn)) {
                conn.rollback();
                return false;
            }

            conn.commit();
            return true;
        } catch (SQLException e) {
            if (conn != null) {
                try { conn.rollback(); } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            e.printStackTrace();
            return false;
        } finally {
            if (conn != null) {
                try {
                    conn.setAutoCommit(true);
                    conn.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    // Cập nhật phiếu nhập hàng (QUAN TRỌNG)
    public boolean capNhatPhieuNhapHang(PhieuNhapHangDTO pnh, List<ChiTietPhieuNhapHangDTO> chiTietMoi) {
        Connection conn = null;
        try {
            conn = DatabaseHelper.getConnection();
            conn.setAutoCommit(false); // Bắt đầu transaction

            // 1. Cập nhật thông tin cơ bản (KHÔNG bao gồm thành tiền)
            if (!pnhDAL.capNhatPhieuNhapHang(pnh, conn)) {
                conn.rollback();
                return false;
            }

            // 2. Lấy danh sách chi tiết cũ
            List<ChiTietPhieuNhapHangDTO> chiTietCu = pnhDAL.layChiTietPhieuNhapHang(pnh.getMaPNH(), conn);

            // 3. Xử lý chi tiết cũ không có trong danh sách mới (cần xóa)
            for (ChiTietPhieuNhapHangDTO ctCu : chiTietCu) {
                if (!chiTietMoi.stream().anyMatch(ct -> ct.getMaSP().equals(ctCu.getMaSP()))) {
                    // Trừ số lượng tồn kho khi xóa chi tiết
//                    SanPhamDTO sp = spDAL.laySanPhamTheoMa(ctCu.getMaSP(), conn);
//                    if (sp != null) {
//                        sp.setsoluong(sp.getsoluong() - ctCu.getSoLuongNhap());
//                        if (!spDAL.updateSanPham(sp)){
//                            conn.rollback();
//                            return false;
//                        }
//                    }

                    if (!pnhDAL.xoaChiTietPhieuNhapHang(ctCu.getMaPNH(), ctCu.getMaSP(), conn)) {
                        conn.rollback();
                        return false;
                    }
                }
            }

            // 4. Xử lý chi tiết mới
            for (ChiTietPhieuNhapHangDTO ctMoi : chiTietMoi) {
                boolean exists = chiTietCu.stream().anyMatch(ct -> ct.getMaSP().equals(ctMoi.getMaSP()));

                if (exists) {
                    // Cập nhật chi tiết và điều chỉnh số lượng tồn
                    ChiTietPhieuNhapHangDTO ctCu = chiTietCu.stream()
                            .filter(ct -> ct.getMaSP().equals(ctMoi.getMaSP()))
                            .findFirst().get();

//                    int soLuongThayDoi = ctMoi.getSoLuongNhap() - ctCu.getSoLuongNhap();
//                    if (soLuongThayDoi != 0) {
//                        SanPhamDTO sp = spDAL.laySanPhamTheoMa(ctMoi.getMaSP(), conn);
//                        if (sp != null) {
//                            sp.setsoluong(sp.getsoluong() + soLuongThayDoi);
//                            if (!spDAL.updateSanPham(sp)) {
//                                conn.rollback();
//                                return false;
//                            }
//                        }
//                    }

                    if (!pnhDAL.capNhatChiTietPhieuNhapHang(ctMoi, conn)) {
                        conn.rollback();
                        return false;
                    }
                } else {
                    // Thêm chi tiết mới và cộng số lượng tồn
                    if (!pnhDAL.themChiTietPhieuNhapHang(ctMoi, conn)) {
                        conn.rollback();
                        return false;
                    }

//                    SanPhamDTO sp = spDAL.laySanPhamTheoMa(ctMoi.getMaSP(), conn);
//                    if (sp != null) {
//                        sp.setsoluong(sp.getsoluong() + ctMoi.getSoLuongNhap());
//                        if (!spDAL.updateSanPham(sp)) {
//                            conn.rollback();
//                            return false;
//                        }
//                    }
                }
            }

            // 5. Cập nhật tổng tiền
            double tongTien = pnhDAL.tinhTongTienPhieuNhap(pnh.getMaPNH(), conn);
            if (!pnhDAL.capNhatThanhTien(pnh.getMaPNH(), tongTien, conn)) {
                conn.rollback();
                return false;
            }

            conn.commit(); // Commit transaction nếu mọi thứ thành công
            return true;
        } catch (SQLException e) {
            if (conn != null) {
                try { conn.rollback(); } catch (SQLException ex) {}
            }
            e.printStackTrace();
            return false;
        } finally {
            DatabaseHelper.closeConnection(conn);
        }
    }

    // Các phương thức khác giữ nguyên
    public List<ChiTietPhieuNhapHangDTO> layChiTietPhieuNhapHang(String maPNH) {
        return pnhDAL.layChiTietPhieuNhapHang(maPNH);
    }

    // Tìm kiếm phiếu nhập hàng nâng cao
    public List<PhieuNhapHangDTO> timKiemPhieuNhapHang(String keyword,
                                                       String fromDateStr, String toDateStr,
                                                       Double minAmount, Double maxAmount) {
        return pnhDAL.timKiemPhieuNhapHang(keyword, fromDateStr, toDateStr, minAmount, maxAmount);
    }

    public PhieuNhapHangDTO layPhieuNhapHangTheoMa(String maPNH) {
        if (maPNH == null || maPNH.trim().isEmpty()) {
            throw new IllegalArgumentException("Mã PNH không hợp lệ");
        }
        return pnhDAL.layPhieuNhapHangTheoMa(maPNH); // Gọi thẳng DAL thay vì filter
    }

    public boolean kiemTraMaPNHTonTai(String maPNH) {
        return pnhDAL.layDanhSachPhieuNhapHang().stream()
                .anyMatch(p -> p.getMaPNH().equalsIgnoreCase(maPNH));
    }

    public boolean kiemTraNhanVienTonTai(String maNV) {
        // Triển khai kiểm tra thực tế
        return true;
    }

    public boolean kiemTraNhaCungUngTonTai(String maNCU) {
        // Triển khai kiểm tra thực tế
        return true;
    }
}
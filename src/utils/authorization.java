package utils;
import session.user;

public class authorization {

    public static boolean isChuNhaThuoc() {
        return "Chủ nhà thuốc".equals(user.getRole());
    }

    public static boolean isNhanVienBanHang() {
        return "Bán hàng".equals(user.getRole());
    }

    public static boolean canDelete() {
        // Chỉ chủ nhà thuốc mới được xóa
        return isChuNhaThuoc();
    }

    public static boolean canManageNhanVien() {
        // Chỉ chủ nhà thuốc mới quản lý được nhân viên
        return isChuNhaThuoc();
    }

    public static boolean canViewBaoCaoDoanhThu() {
        // Cả 2 vai trò đều có thể xem doanh thu nếu bạn muốn, hoặc chỉ chủ nhà thuốc
        return isChuNhaThuoc();
    }
}

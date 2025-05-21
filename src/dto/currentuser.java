package dto;
import java.util.HashSet;
import java.util.Set;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
public class currentuser {
	private static String username;
    private static String maNhanVien;
    private static String maVaiTro;
    private static List<String> danhSachQuyen; 

    public static void setUser(String user, String manv, String mavt, List<String> quyenList) {
        username = user;
        maNhanVien = manv;
        maVaiTro = mavt;
        danhSachQuyen = quyenList;
    }

    public static String getUsername() {
        return username;
    }

    public static String getMaNhanVien() {
        return maNhanVien;
    }

    public static String getMaVaiTro() {
        return maVaiTro;
    }

    public static List<String> getDanhSachQuyen() {
        return danhSachQuyen;
    }

    public static boolean coQuyen(String maQuyen) {
        return danhSachQuyen != null && danhSachQuyen.contains(maQuyen);
    }

    public static void clear() {
        username = null;
        maNhanVien = null;
        maVaiTro = null;
        danhSachQuyen = null;
    }
}

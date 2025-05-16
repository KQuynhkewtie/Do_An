package dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Homepagedal  {

    private Connection conn;

    public Homepagedal(Connection conn) {
        this.conn = conn;
    }

    public int getTotalProducts() {
        try {
            String sql = "SELECT COUNT(*) FROM SANPHAM";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int getTotalEmployees() {
        try {
            String sql = "SELECT COUNT(*) FROM NHANVIEN";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int getTotalCustomers() {
        try {
            String sql = "SELECT COUNT(*) FROM KHACHHANG";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public double getRevenueCurrentYear() {
        try {
        	String sql = "SELECT SUM(THANHTIEN) FROM HOADON WHERE EXTRACT(YEAR FROM NGAYBAN) = EXTRACT(YEAR FROM SYSDATE) " +
                    "AND EXTRACT(MONTH FROM NGAYBAN) = EXTRACT(MONTH FROM SYSDATE)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getDouble(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0.0;
    }
}

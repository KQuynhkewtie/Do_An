package dal;


import dto.KhachHangDTO;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.sql.*;
import java.util.List;


public class khachhangdal {
	private final String url = "jdbc:oracle:thin:@localhost:1521:orcl";
	private final String user = "c##Mthuda";
	private final String pass = "Minhthu05#";
    public ArrayList<KhachHangDTO> layDSKhachHang() {
        ArrayList<KhachHangDTO> ds = new ArrayList<>();
        String query = "SELECT * FROM KHACHHANG";

        try (Connection conn = DriverManager.getConnection(url, user, pass);
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {


            while (rs.next()) {
                KhachHangDTO kh = new KhachHangDTO(
                    rs.getString("MAKH"),
                    rs.getString("HOTEN"),
                    rs.getInt("SOLANMUA"),
                    rs.getDouble("DIEMTICHLUY"),
                    rs.getString("MALOAIKH")
                );
                ds.add(kh);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ds;
    }
    
    public KhachHangDTO getKhachHangById(String maKH) {
        KhachHangDTO kh = null;
        String query = "SELECT * FROM KHACHHANG WHERE MAKH = ?";

        try (Connection conn = DriverManager.getConnection(url, user, pass);
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, maKH);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                kh = new KhachHangDTO(
                		  rs.getString("MAKH"),
                          rs.getString("HOTEN"),
                          rs.getInt("SOLANMUA"),
                          rs.getDouble("DIEMTICHLUY"),
                          rs.getString("LOAIKHACH")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return kh;
    }
    
    public boolean insertKhachHang(KhachHangDTO kh) {
        String sql = "INSERT INTO KHACHHANG (MAKH, HOTEN, SOLANMUA, DIEMTICHLUY, LOAIKHACH) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(url, user, pass);
             PreparedStatement ps = conn.prepareStatement(sql)) {

            // Set các giá trị cho PreparedStatement
            ps.setString(1, kh.getMaKH());
            ps.setString(2, kh.getHoTen());
            ps.setInt(3, kh.getSoLanMua());
            ps.setDouble(4, kh.getDiemTichLuy());
            ps.setString(5, kh.getLoaiKhach());

            // Thực hiện chèn dữ liệu vào bảng
            return ps.executeUpdate() > 0;  // Trả về true nếu thêm thành công
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<KhachHangDTO> getKhachHang(String keyword) {
        List<KhachHangDTO> danhSachKH = new ArrayList<>();
        String sql = "SELECT * FROM KHACHHANG WHERE LOWER(HOTEN) LIKE ?";

        try (Connection conn = DriverManager.getConnection(url, user, pass);
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, "%" + keyword.toLowerCase() + "%");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                KhachHangDTO kh = new KhachHangDTO(
                    rs.getString("MAKH"),
                    rs.getString("HOTEN"),
                    rs.getInt("SOLANMUA"),
                    rs.getDouble("DIEMTICHLUY"),
                    rs.getString("LOAIKHACH")
                );
                danhSachKH.add(kh);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return danhSachKH;
    }
    
    public boolean deleteKhachHangByName(String tenKH) {
        String sql = "DELETE FROM KHACHHANG WHERE HOTEN = ?";

        try (Connection conn = DriverManager.getConnection(url, user, pass);
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, tenKH);  // Set tên khách hàng vào câu lệnh SQL

            // Thực thi câu lệnh SQL và trả về true nếu ít nhất một khách hàng bị xóa
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public boolean updateKhachHang(KhachHangDTO kh) {
        String sql = "UPDATE KHACHHANG SET HOTEN=?, SOLANMUA=?, DIEMTICHLUY=?, LOAIKHACH=? WHERE TRIM(MAKH) = ?";

        try (Connection conn = DriverManager.getConnection(url, user, pass);
             PreparedStatement ps = conn.prepareStatement(sql)) {

            // Hiển thị thông tin khách hàng trước khi thực hiện cập nhật
            System.out.println("Đang cập nhật thông tin khách hàng: " + kh);
            System.out.println("Mã KH: " + kh.getMaKH());
            System.out.println("1. TENKH: " + kh.getHoTen());
            System.out.println("2. SOLANMUA: " + kh.getSoLanMua());
            System.out.println("3. DIEMTICHLUY: " + kh.getDiemTichLuy());
            System.out.println("4. LOAIKHACH: " + kh.getLoaiKhach());

            // Set các giá trị vào PreparedStatement
            ps.setString(1, kh.getHoTen());
            ps.setInt(2, kh.getSoLanMua());
            ps.setDouble(3, kh.getDiemTichLuy());
            ps.setString(4, kh.getLoaiKhach());

            // Set giá trị MAKH cho phần WHERE, loại bỏ khoảng trắng dư
            ps.setString(5, kh.getMaKH().trim());

            // Hiển thị giá trị truyền vào
            System.out.println("Giá trị truyền vào UPDATE:");
            System.out.println("1. HOTEN: " + kh.getHoTen());
            System.out.println("2. SOLANMUA: " + kh.getSoLanMua());
            System.out.println("3. DIEMTICHLUY: " + kh.getDiemTichLuy());
            System.out.println("4. LOAIKHACH: " + kh.getLoaiKhach());
            System.out.println("5. MAKH (WHERE): " + kh.getMaKH().trim());

            // Thực hiện câu lệnh UPDATE và kiểm tra số lượng bản ghi bị ảnh hưởng
            int rows = ps.executeUpdate();
            System.out.println("Rows affected: " + rows);

            return rows > 0;

        } catch (SQLException e) {
            System.err.println("Lỗi cập nhật khách hàng: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }
    public boolean deleteKhachHangById(String maKH) {
        String sql = "DELETE FROM KHACHHANG WHERE MAKH = ?";
        try (Connection conn = DriverManager.getConnection(url, user, pass);
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, maKH);
            return ps.executeUpdate() > 0; // Trả về true nếu có ít nhất một khách hàng bị xóa
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


}

package dal;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dto.NhaCungUngDTO;

public class ncudal {
	private final String url = "jdbc:oracle:thin:@localhost:1521:orcl";
	private final String user = "c##Mthuda";
	private final String pass = "Minhthu05#";
    public ArrayList<NhaCungUngDTO> layDSNCU() {
        ArrayList<NhaCungUngDTO> ds = new ArrayList<>();
        String query = "SELECT * FROM NHACUNGCAP";

        try (Connection conn = DriverManager.getConnection(url, user, pass);
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {


            while (rs.next()) {
            	NhaCungUngDTO ncu = new NhaCungUngDTO(
                    rs.getString("MANCU"),
                    rs.getString("TENNCU"),
                    rs.getString("MASOTHUE"),
                    rs.getString("DIACHI"),
                    rs.getString("SDT"),
                    rs.getString("EMAIL")
                );
                ds.add(ncu);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ds;
    }
    
    public NhaCungUngDTO getNCUById(String maNCU) {
    	NhaCungUngDTO ncu = null;
        String query = "SELECT * FROM NHACUNGCAP WHERE MANCU = ?";

        try (Connection conn = DriverManager.getConnection(url, user, pass);
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, maNCU);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                ncu = new NhaCungUngDTO(
                		rs.getString("MANCU"),
                        rs.getString("TENNCU"),
                        rs.getString("MASOTHUE"),
                        rs.getString("DIACHI"),
                        rs.getString("SDT"),
                        rs.getString("EMAIL")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return ncu;
    }
    
    public boolean insertncu(NhaCungUngDTO ncu) {
        String sql = "INSERT INTO NHACUNGCAP (MANCU, TENNCU, MASOTHUE, DIACHI, SDT, EMAIL) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(url, user, pass);
             PreparedStatement ps = conn.prepareStatement(sql)) {

            // Set các giá trị cho PreparedStatement
            ps.setString(1, ncu.getMaNCU());
            ps.setString(2, ncu.getTenNCU());
            ps.setString(3, ncu.getMaSoThue());
            ps.setString(4, ncu.getDiaChi());
            ps.setString(5, ncu.getSdt());
            ps.setString(6, ncu.getEmail());

            // Thực hiện chèn dữ liệu vào bảng
            return ps.executeUpdate() > 0;  // Trả về true nếu thêm thành công
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<NhaCungUngDTO> getncu(String keyword) {
        List<NhaCungUngDTO> danhSachNCU = new ArrayList<>();
        String sql = "SELECT * FROM NHACUNGCAP WHERE LOWER(TENNCU) LIKE ?";

        try (Connection conn = DriverManager.getConnection(url, user, pass);
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, "%" + keyword.toLowerCase() + "%");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                NhaCungUngDTO ncu = new NhaCungUngDTO(
                		rs.getString("MANCU"),
                        rs.getString("TENNCU"),
                        rs.getString("MASOTHUE"),
                        rs.getString("DIACHI"),
                        rs.getString("SDT"),
                        rs.getString("EMAIL")
                );
                danhSachNCU.add(ncu);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return danhSachNCU;
    }
    
    public boolean deleteNCUByName(String tenNCU) {
        String sql = "DELETE FROM NHACUNGCAP WHERE TENNCU = ?";

        try (Connection conn = DriverManager.getConnection(url, user, pass);
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, tenNCU);  // Set tên khách hàng vào câu lệnh SQL

            // Thực thi câu lệnh SQL và trả về true nếu ít nhất một khách hàng bị xóa
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public boolean updateNCU(NhaCungUngDTO ncu) {
        String sql = "UPDATE NHACUNGCAP SET TENNCU=?, MASOTHUE=?, DIACHI=?, SDT=?, EMAIL=?  WHERE TRIM(MANCU) = ?";

        try (Connection conn = DriverManager.getConnection(url, user, pass);
             PreparedStatement ps = conn.prepareStatement(sql)) {

            // Set các giá trị vào PreparedStatement
            ps.setString(1, ncu.getTenNCU());
            ps.setString(2, ncu.getMaSoThue());
            ps.setString(3, ncu.getDiaChi());
            ps.setString(4, ncu.getSdt());
            ps.setString(5, ncu.getEmail());

            // Set giá trị MAncu cho phần WHERE, loại bỏ khoảng trắng dư
            ps.setString(6, ncu.getMaNCU().trim());

            

            // Thực hiện câu lệnh UPDATE và kiểm tra số lượng bản ghi bị ảnh hưởng
            int rows = ps.executeUpdate();
            System.out.println("Rows affected: " + rows);

            return rows > 0;

        } catch (SQLException e) {
            System.err.println("Lỗi cập nhật nhà cung ứng: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }
    public boolean deleteNCUById(String maNCU) {
        String sql = "DELETE FROM NHACUNGCAP WHERE MANCU = ?";
        try (Connection conn = DriverManager.getConnection(url, user, pass);
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, maNCU);
            return ps.executeUpdate() > 0; // Trả về true nếu có ít nhất một khách hàng bị xóa
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Hiển thị theo tên
    public List<NhaCungUngDTO> getNCUByTen(String tenNCU) {
        List<NhaCungUngDTO> danhSachNCU = new ArrayList<>();
        String sql = "SELECT * FROM NHACUNGCAP WHERE LOWER(TENNCU) LIKE ?";

        try (Connection conn = DriverManager.getConnection(url, user, pass);
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, "%" + tenNCU.toLowerCase() + "%");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                NhaCungUngDTO ncu = new NhaCungUngDTO(
                        rs.getString("MANCU"),
                        rs.getString("TENNCU"),
                        rs.getString("MASOTHUE"),
                        rs.getString("DIACHI"),
                        rs.getString("SDT"),
                        rs.getString("EMAIL")
                );
                danhSachNCU.add(ncu);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return danhSachNCU;
    }

}

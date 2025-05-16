package dal;

import java.util.ArrayList;
import java.sql.*;
import java.util.List;
import dto.LoaiSPDTO;

public class loaispdal {
	private final String url = "jdbc:oracle:thin:@localhost:1521:orcl";
	private final String user = "c##Mthuda";
	private final String pass = "Minhthu05#";
	 public ArrayList<LoaiSPDTO> layDSLSP() {
	        ArrayList<LoaiSPDTO> ds = new ArrayList<>();
	        String query = "SELECT * FROM LOAISANPHAM";

	        try (Connection conn = DriverManager.getConnection(url, user, pass);
	             PreparedStatement stmt = conn.prepareStatement(query);
	             ResultSet rs = stmt.executeQuery()) {


	            while (rs.next()) {
	            	LoaiSPDTO lsp = new LoaiSPDTO(
	                    rs.getString("MALSP"),
	                    rs.getString("TENLSP")
	                    
	                );
	                ds.add(lsp);
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        return ds;
	    }
	 
	 public LoaiSPDTO getLSPById(String maLSP) {
		 LoaiSPDTO lsp = null;
	        String query = "SELECT * FROM LOAISANPHAM WHERE MALSP = ?";

	        try (Connection conn = DriverManager.getConnection(url, user, pass);
	             PreparedStatement stmt = conn.prepareStatement(query)) {
	            stmt.setString(1, maLSP);
	            ResultSet rs = stmt.executeQuery();

	            if (rs.next()) {
	                lsp = new LoaiSPDTO(
	                		  rs.getString("MALSP"),
	                          rs.getString("TENLSP")
	                );
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }

	        return lsp;
	    }
	    
	 public boolean insertLSP(LoaiSPDTO lsp) {
	        String sql = "INSERT INTO LOAISANPHAM (MALSP, TENLSP) VALUES (?, ?)";

	        try (Connection conn = DriverManager.getConnection(url, user, pass);
	             PreparedStatement ps = conn.prepareStatement(sql)) {

	            // Set các giá trị cho PreparedStatement
	            ps.setString(1, lsp.getMaLSP());
	            ps.setString(2, lsp.getTenLSP());
	           
	            // Thực hiện chèn dữ liệu vào bảng
	            return ps.executeUpdate() > 0;  // Trả về true nếu thêm thành công
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	        return false;
	    }
	 public List<LoaiSPDTO> getLSP(String keyword) {
	        List<LoaiSPDTO> danhSachLSP = new ArrayList<>();
	        String sql = "SELECT * FROM LOAISANPHAM WHERE LOWER(TENLSP) LIKE ?";

	        try (Connection conn = DriverManager.getConnection(url, user, pass);
	             PreparedStatement ps = conn.prepareStatement(sql)) {
	            ps.setString(1, "%" + keyword.toLowerCase() + "%");
	            ResultSet rs = ps.executeQuery();

	            while (rs.next()) {
	                LoaiSPDTO lsp = new LoaiSPDTO(
	                    rs.getString("MALSP"),
	                    rs.getString("TENLSP")
	                    
	                );
	                danhSachLSP.add(lsp);
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	        return danhSachLSP;
	    }
	 public boolean deleteLSPByName(String tenLSP) {
	        String sql = "DELETE FROM LOAISANPHAM WHERE TENLSP = ?";

	        try (Connection conn = DriverManager.getConnection(url, user, pass);
	             PreparedStatement ps = conn.prepareStatement(sql)) {
	            
	            ps.setString(1, tenLSP);  // Set tên khách hàng vào câu lệnh SQL

	            // Thực thi câu lệnh SQL và trả về true nếu ít nhất một khách hàng bị xóa
	            return ps.executeUpdate() > 0;
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	        return false;
	    }
	    
	    public boolean updateLSP(LoaiSPDTO lsp) {
	        String sql = "UPDATE LOAISANPHAM SET TENLSP=? WHERE TRIM(MALSP) = ?";

	        try (Connection conn = DriverManager.getConnection(url, user, pass);
	             PreparedStatement ps = conn.prepareStatement(sql)) {

	            // Hiển thị thông tin khách hàng trước khi thực hiện cập nhật
	            System.out.println("Đang cập nhật thông tin loaisanpham : " + lsp);
	            System.out.println("Mã LSP: " + lsp.getMaLSP());
	            System.out.println("1. TENLSP: " + lsp.getTenLSP());

	            // Set các giá trị vào PreparedStatement
	            ps.setString(1, lsp.getTenLSP());
	           
	            // Set giá trị MAKH cho phần WHERE, loại bỏ khoảng trắng dư
	            ps.setString(2, lsp.getMaLSP().trim());

	            // Hiển thị giá trị truyền vào
	            System.out.println("Giá trị truyền vào UPDATE:");
	            System.out.println("1. TENLSP: " + lsp.getTenLSP());
	            System.out.println("2. MALSP (WHERE): " + lsp.getMaLSP().trim());

	            // Thực hiện câu lệnh UPDATE và kiểm tra số lượng bản ghi bị ảnh hưởng
	            int rows = ps.executeUpdate();
	            System.out.println("Rows affected: " + rows);

	            return rows > 0;

	        } catch (SQLException e) {
	            System.err.println("Lỗi cập nhật loại sản phẩm: " + e.getMessage());
	            e.printStackTrace();
	        }
	        return false;
	    }
	    public boolean deleteLSPById(String maLSP) {
	        String sql = "DELETE FROM LOAISANPHAM WHERE MALSP = ?";
	        try (Connection conn = DriverManager.getConnection(url, user, pass);
	             PreparedStatement ps = conn.prepareStatement(sql)) {
	            ps.setString(1, maLSP);
	            return ps.executeUpdate() > 0; // Trả về true nếu có ít nhất một khách hàng bị xóa
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	        return false;
	    }

}

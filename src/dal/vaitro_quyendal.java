package dal;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
public class vaitro_quyendal {
	public List<String> layDanhSachQuyenTheoVaiTro(String maVaiTro) throws SQLException {
        List<String> quyenList = new ArrayList<>();
        String sql = "SELECT MAQUYEN FROM VAITRO_QUYEN WHERE MAVT = ?";
        try (Connection conn = DatabaseHelper.getConnection();
        	     PreparedStatement stmt = conn.prepareStatement(sql)) {

        	    stmt.setString(1, maVaiTro);  

        	    try (ResultSet rs = stmt.executeQuery()) {  
        	        while (rs.next()) {
        	            quyenList.add(rs.getString("MAQUYEN"));
        	        }
        	    }
        	} catch (Exception e) {
        	    e.printStackTrace();
        	}
        return quyenList;
    }
}

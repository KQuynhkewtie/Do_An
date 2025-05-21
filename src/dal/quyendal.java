package dal;

import dto.quyendto;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
public class quyendal {
	public List<quyendto> layTatCaQuyen() throws SQLException {
        List<quyendto> ds = new ArrayList<>();
        String sql = "SELECT * FROM QUYEN";
        try (Connection conn = DatabaseHelper.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()) {


        	 while (rs.next()) {
                 ds.add(new quyendto(rs.getString("MAQUYEN"), rs.getString("TENQUYEN")));
             }
        } catch (Exception e) {
            e.printStackTrace();
        }
       return ds;
	}
}

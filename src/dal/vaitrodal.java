//package dal;
//
//import dto.vaitrodto;
//import java.sql.*;
//import java.util.ArrayList;
//import java.util.List;
//
//public class vaitrodal {
//	public List<vaitrodto> layTatCaVaiTro() throws SQLException {
//        List<vaitrodto> ds = new ArrayList<>();
//        String sql = "SELECT * FROM VAITRO";
//        try (Connection conn = DatabaseHelper.getConnection();
//                PreparedStatement stmt = conn.prepareStatement(sql);
//                ResultSet rs = stmt.executeQuery()) {
//
//
//        	while (rs.next()) {
//                ds.add(new vaitrodto(rs.getString("MAVT"), rs.getString("TENVAITRO")));
//            }
//           } catch (Exception e) {
//               e.printStackTrace();
//           }
//
//        return ds;
//    }
//}

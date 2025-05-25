package dal;

import dto.vaiTro;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class vaitrodal {
    public List<vaiTro> layTatCaVaiTro() throws SQLException {
        List<vaiTro> ds = new ArrayList<>();
        String sql = "SELECT * FROM VAITRO";
        try (Connection conn = DatabaseHelper.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {


            while (rs.next()) {
                ds.add(new vaiTro(rs.getString("MAVT"), rs.getString("TENVAITRO")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ds;
    }

    public String getTenVaiTro(String maVT) {
        String tenVaiTro = null;
        String sql = "SELECT TENVAITRO FROM VAITRO WHERE MAVT = ?";

        try (Connection conn = DatabaseHelper.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, maVT);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    tenVaiTro = rs.getString("TENVAITRO");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return tenVaiTro;
    }
}

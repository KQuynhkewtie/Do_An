package bll;

import java.sql.SQLException;

import dal.taikhoandal;
import dto.TaiKhoanDTO;

public class TaiKhoanBLL {
    private taikhoandal taiKhoanDAL = new taikhoandal();

    public String login(String email, String matKhau) throws SQLException {
        return taiKhoanDAL.kiemTraDangNhap(email, matKhau);
    }

    public boolean signup(String tenTK, String email, String matKhau, String manv) {
        return taiKhoanDAL.dangKyTaiKhoan(tenTK, email, matKhau, manv);
    }

    public TaiKhoanDTO getThongTinTaiKhoan(String email) {
        return taiKhoanDAL.getTaiKhoan(email);
    }

    public String getusename(String email) throws SQLException {
        return taiKhoanDAL.getUsername(email);
    }

    public String getVaiTro(String maNhanVien) {
        return taiKhoanDAL.getVaiTro(maNhanVien);
    }

    public boolean updatetmatkhau(String email, String matKhauMoi) {
        return taiKhoanDAL.updateMatKhau(email, matKhauMoi);
    }
}
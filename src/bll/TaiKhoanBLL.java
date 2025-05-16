package bll;


import dal.taikhoandal;
import dto.TaiKhoanDTO;

public class TaiKhoanBLL {
    private taikhoandal taiKhoanDAL = new taikhoandal();

    public boolean dangNhap(String email, String matKhau) {
        return taiKhoanDAL.kiemTraDangNhap(email, matKhau);
    }

    public TaiKhoanDTO layThongTinTaiKhoan(String email) {
        return taiKhoanDAL.getTaiKhoan(email);
    }
}
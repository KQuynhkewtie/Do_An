package bll;

import dal.khachhangdal;
import dto.KhachHangDTO;
import java.util.ArrayList;
import java.util.List;

public class KhachHangBLL {
    private khachhangdal khDal = new khachhangdal();

    // Lấy danh sách khách hàng
    public ArrayList<KhachHangDTO> layDSKhachHang() {
        return khDal.layDSKhachHang();
    }

    // Lấy khách hàng theo mã
    public KhachHangDTO getKhachHangById(String maKH) {
        return khDal.getKhachHangById(maKH);
    }

    // Thêm khách hàng
    public boolean insertKhachHang(KhachHangDTO kh) {
        return khDal.insertKhachHang(kh);
    }

    // Tìm kiếm khách hàng theo tên
    public List<KhachHangDTO> getKhachHang(String keyword) {
        return khDal.getKhachHang(keyword);
    }

    // Cập nhật thông tin khách hàng
    public boolean updateKhachHang(KhachHangDTO kh) {
        return khDal.updateKhachHang(kh);
    }

    // Xóa khách hàng theo mã
    public boolean deleteKhachHangById(String maKH) {
        return khDal.deleteKhachHangById(maKH);
    }

    // Xóa khách hàng theo tên
    public boolean deleteKhachHangByName(String tenKH) {
        return khDal.deleteKhachHangByName(tenKH);
    }
}

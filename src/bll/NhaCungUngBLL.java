package bll;


import dal.ncudal;
import dto.NhaCungUngDTO;
import java.util.ArrayList;
import java.util.List;

public class NhaCungUngBLL {
    private ncudal ncuDal = new ncudal();

    // Lấy danh sách khách hàng
    public ArrayList<NhaCungUngDTO> getAllNCU() {
        return ncuDal.getAllNCU();
    }

    // Lấy khách hàng theo mã
    public NhaCungUngDTO getNCUById(String maNCU) {
        return ncuDal.getNCUById(maNCU);
    }

    // Thêm khách hàng
    public boolean insertNCU(NhaCungUngDTO NCU) {
        return ncuDal.insertncu(NCU);
    }

    // Tìm kiếm khách hàng theo tên
    public List<NhaCungUngDTO> getNCU(String keyword) {
        return ncuDal.getncu(keyword);
    }

    // Cập nhật thông tin khách hàng
    public boolean updateNCU(NhaCungUngDTO NCU) {
        return ncuDal.updateNCU(NCU);
    }

    public boolean deleteNCUById(String maNCU) {
        return ncuDal.deleteNCUById(maNCU);
    }


//    // Xóa khách hàng theo mã
//    public boolean deleteNCUById(String maNCU) {
//        return ncuDal.deleteNCUById(maNCU);
//    }
//
//    // Xóa khách hàng theo tên
//    public boolean deleteNCUByName(String tenNCU) {
//        return ncuDal.deleteNCUByName(tenNCU);
//    }

    // Để hiển thị theo tên ở pnh
//    public List<NhaCungUngDTO> getNCUByTen(String tenNCU) {
//        return ncuDal.getNCUByTen(tenNCU);
//    }
}

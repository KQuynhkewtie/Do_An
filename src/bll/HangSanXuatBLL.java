package bll;

import dal.hangsanxuatdal;
import dto.HangSanXuatDTO;
import java.util.List;

public class HangSanXuatBLL {
    private hangsanxuatdal hangsanxuatDAL = new hangsanxuatdal();

    // Lấy danh sách hãng sản xuất
    public List<HangSanXuatDTO> getAllHangSanXuat() {
        return hangsanxuatDAL.getAllHangSanXuat();
    }

    // Thêm hãng sản xuất
    public boolean addHangSanXuat(HangSanXuatDTO hsx) {
        return hangsanxuatDAL.addHangSanXuat(hsx);
    }

    // Sửa thông tin hãng sản xuất
    public boolean updateHangSanXuat(HangSanXuatDTO hsx) {
        return hangsanxuatDAL.updateHangSanXuat(hsx);
    }

 // Xóa hãng sản xuất theo tên
    public boolean deleteHangSanXuat(String maHSX) {
       return hangsanxuatDAL.deleteHangSanXuat(maHSX);
    }
    public HangSanXuatDTO getHSXbyID(String maHSX) {
        return hangsanxuatDAL.getHSXById(maHSX);
    }
    public List<HangSanXuatDTO> getHSX(String keyword) {
        return hangsanxuatDAL.getHSX(keyword);
    }

}

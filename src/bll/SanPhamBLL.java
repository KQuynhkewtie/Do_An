package bll;

import dal.SanPhamDAL;
import dto.SanPhamDTO;
import java.util.List;

public class SanPhamBLL {
    private SanPhamDAL spDAL;

    public SanPhamBLL() {
        spDAL = new SanPhamDAL();
    }

    // Lấy danh sách tất cả sản phẩm
    public List<SanPhamDTO> getAllSanPham() {
        return spDAL.getAllSanPham();
    }

    // Thêm sản phẩm mới
    public boolean addSanPham(SanPhamDTO sp) {
        return spDAL.insertSanPham(sp);
    }

    // Cập nhật thông tin sản phẩm
    public boolean updateSanPham(SanPhamDTO sp) {
        return spDAL.updateSanPham(sp);
    }

    // Xóa sản phẩm theo tên
    public boolean deleteSanPhamByName(String tenSP) {
        return spDAL.deleteSanPhamByName(tenSP);
    }

    // Tìm kiếm sản phẩm theo từ khóa
    public List<SanPhamDTO> getSanPham(String keyword) {
        return spDAL.getSanPham(keyword);
    }
    
    public SanPhamDTO getSanPhamById(String maSP) {
        return spDAL.getSanPhamById(maSP);
    }
    
    public boolean deleteSanPhamById(String maSP) {
        return spDAL.deleteSanPhamById(maSP);
    }
    
    

}

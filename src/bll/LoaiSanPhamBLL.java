package bll;

import dal.loaisanphamdal;
import dto.LoaiSanPhamDTO;
import dto.LoaiSPDTO;

import java.util.List;

public class LoaiSanPhamBLL {
	 private final loaisanphamdal loaiSanPhamDAL = new loaisanphamdal();

	    public List<LoaiSanPhamDTO> getAllLoaiSanPham() {
	        return loaiSanPhamDAL.getAllLoaiSanPham();
	    }
	    
	    public LoaiSPDTO getLSPById(String maKH) {
	        return loaiSanPhamDAL.getLSPById(maKH);
	    }

	    public boolean insertLoaiSanPham(String maLSP, String tenLSP) {
	        if (maLSP.isEmpty() || tenLSP.isEmpty()) {
	            System.out.println("Mã và tên loại sản phẩm không được để trống.");
	            return false;
	        }
	        return loaiSanPhamDAL.insertLoaiSanPham(new LoaiSanPhamDTO(maLSP, tenLSP));
	    }

	    public boolean deleteLoaiSanPham(String maLSP) {
	        return loaiSanPhamDAL.deleteLoaiSanPham(maLSP);
	    }
	    
	    public boolean updateMaLoaiSanPham(String oldMaLSP, String newMaLSP) {
	        if (oldMaLSP == null || newMaLSP == null || oldMaLSP.isEmpty() || newMaLSP.isEmpty()) {
	            System.out.println("Mã loại sản phẩm không hợp lệ!");
	            return false;
	        }
	        return loaiSanPhamDAL.updateMaLoaiSanPham(oldMaLSP, newMaLSP);
	    }
}

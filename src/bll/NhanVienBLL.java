package bll;


import dal.NhanViendal;
import dto.NhanVienDTO;

import java.util.List;

public class NhanVienBLL {
    private NhanViendal nhanVienDAL= new NhanViendal();
    
    public List<NhanVienDTO> getDanhSachNhanVien() {
        return nhanVienDAL.getDanhSachNhanVien();
    }

    public boolean insertNhanVien(NhanVienDTO nv) {
        return nhanVienDAL.insertNhanVien(nv);
    }

    public boolean updateNhanVien(NhanVienDTO nv) {
        return nhanVienDAL.updateNhanVien(nv);
    }

    public boolean deleteNhanVien(String maNhanVien) {
       
        return nhanVienDAL.deleteNhanVien(maNhanVien);
    }

   
    public NhanVienDTO getNhanVienTheoMa(String maNhanVien) {
        return nhanVienDAL.getNhanVienTheoMa(maNhanVien);
    }
    public List<NhanVienDTO> getNhanVien(String keyword) {
        return nhanVienDAL.getNhanVien(keyword);
    }

    //Để hiển thị theo tên ở pnh và hd
    public List<NhanVienDTO> getNhanVienByTen(String tenNV) {
        return nhanVienDAL.getNhanVienByTen(tenNV);
    }
}


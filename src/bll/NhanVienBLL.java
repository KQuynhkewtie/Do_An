package bll;


import dal.NhanViendal;
import dal.vaitrodal;
import dto.NhanVienDTO;
import dto.vaiTro;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NhanVienBLL {
    private NhanViendal nhanVienDAL= new NhanViendal();
    private vaitrodal vaiTroDal = new vaitrodal();

    public List<NhanVienDTO> getAllNhanVien() {
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

   
    public NhanVienDTO getNhanVienByID(String maNhanVien) {
        return nhanVienDAL.getNhanVienByID(maNhanVien);
    }
    public List<NhanVienDTO> getNhanVien(String keyword) {
        return nhanVienDAL.getNhanVien(keyword);
    }

    public String getTenViTriByMa(String maVT) {
        return nhanVienDAL.getTenViTriByMa(maVT);
    }

    public Map<String, String> getAllViTriMap() {
        Map<String, String> map = new HashMap<>();
        try {
            List<vaiTro> list = vaiTroDal.layTatCaVaiTro();
            for (vaiTro vt : list) {
                map.put(vt.getMaVT(), vt.getTenVaiTro());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }
}


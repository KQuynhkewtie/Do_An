package bll;
import dal.vaitro_quyendal;
import dto.currentuser;
import java.util.List;
public class phanquyenbll {
	vaitro_quyendal vqDAL = new vaitro_quyendal();

    public boolean coQuyen(String maQuyen) {
        return currentuser.coQuyen(maQuyen);
    }

    public List<String> layTatCaQuyenCuaUser() {
        return currentuser.getDanhSachQuyen();
    }   
    
}

package bll;

import dal.vaitro_quyendal;
import java.util.List;
public class vaitro_quyenbll {
    private vaitro_quyendal vtqDAL;

    public vaitro_quyenbll() {
        vtqDAL = new vaitro_quyendal();
    }

    public List<String> layDanhSachQuyenTheoVaiTro(String maVaiTro) {
        try {
            return vtqDAL.layDanhSachQuyenTheoVaiTro(maVaiTro);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
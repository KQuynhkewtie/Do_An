package bll;

import dal.vaitrodal;

public class vaitrobll {
    private vaitrodal vaitroDAL;
    public vaitrobll() {
        vaitroDAL = new vaitrodal();
    }

    public String getTenVaiTro(String maVT) {
        return vaitroDAL.getTenVaiTro(maVT);
    }
}

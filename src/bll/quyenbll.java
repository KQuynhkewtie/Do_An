package bll;
import dal.quyendal;
import dto.quyendto;
import java.util.List;

public class quyenbll {
    private quyendal quyenDAL;

    public quyenbll() {
        quyenDAL = new quyendal();
    }

    public List<quyendto> layTatCaQuyen() {
        try {
            return quyenDAL.layTatCaQuyen();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}

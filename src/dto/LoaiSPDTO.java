package dto;

public class LoaiSPDTO {

    private String maLSP;
    private String tenLSP;
    private int trangThai;

    // Constructor không tham số
    public LoaiSPDTO() {
    }

    // Constructor đầy đủ tham số
    public LoaiSPDTO(String maLSP, String tenLSP, int trangThai) {
        this.maLSP = maLSP;
        this.tenLSP = tenLSP;
        this.trangThai = trangThai;

    }


    public String getMaLSP() {
        return maLSP;
    }

    public void setMaLSP(String maLSP) {
        this.maLSP = maLSP;
    }

    public String getTenLSP() {
        return tenLSP;
    }

    public void setTenLSP(String tenLSP) {
        this.tenLSP = tenLSP;
    }

    public int getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(int trangThai) {
        this.trangThai = trangThai;
    }

}
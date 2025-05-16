package dto;


public class HangSanXuatDTO {
    private String maHSX;
    private String tenHSX;
    private String maSoThue;
    private String diaChi;
    private String sdt;

    public HangSanXuatDTO() {}
    
    public HangSanXuatDTO(String maHSX, String tenHSX, String maSoThue, String diaChi, String sdt) {
        this.maHSX = maHSX;
        this.tenHSX = tenHSX;
        this.maSoThue = maSoThue;
        this.diaChi = diaChi;
        this.sdt = sdt;
    }

    public String getMaHSX() {
        return maHSX;
    }

    public void setMaHSX(String maHSX) {
        this.maHSX = maHSX;
    }

    public String getTenHSX() {
        return tenHSX;
    }

    public void setTenHSX(String tenHSX) {
        this.tenHSX = tenHSX;
    }

    public String getMaSoThue() {
        return maSoThue;
    }

    public void setMaSoThue(String maSoThue) {
        this.maSoThue = maSoThue;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }
}
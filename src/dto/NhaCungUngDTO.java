package dto;


public class NhaCungUngDTO {
    private String maNCU;
    private String tenNCU;
    private String maSoThue;
    private String diaChi;
    private String sdt;
    private String email;
    private int trangThai;

    // Constructor không tham số
    public NhaCungUngDTO() {}

    // Constructor đầy đủ tham số
    public NhaCungUngDTO(String maNCU, String tenNCU, String maSoThue, String diaChi, String sdt, String email, int trangThai) {
        this.maNCU = maNCU;
        this.tenNCU = tenNCU;
        this.maSoThue = maSoThue;
        this.diaChi = diaChi;
        this.sdt = sdt;
        this.email = email;
        this.trangThai = trangThai;

    }


    public String getMaNCU() {
        return maNCU;
    }

    public void setMaNCU(String maNCU) {
        this.maNCU = maNCU;
    }

    public String getTenNCU() {
        return tenNCU;
    }

    public void setTenNCU(String tenNCU) {
        this.tenNCU = tenNCU;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    public int getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(int trangThai) {
        this.trangThai = trangThai;
    }
}

package dto;

public class LoaiSPDTO {

    private String maLSP;
    private String tenLSP;

    // Constructor không tham số
    public LoaiSPDTO() {}

    // Constructor đầy đủ tham số
    public LoaiSPDTO(String maLSP, String tenLSP) {
        this.maLSP = maLSP;
        this.tenLSP = tenLSP;
    }

    // Getter & Setter
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

    @Override
    public String toString() {
        return "Mã loại SP: " + maLSP + " | Tên loại SP: " + tenLSP;
    }
}
package dto;

public class LoaiSanPhamDTO {
	private String maLSP;
    private String tenLSP;

    public LoaiSanPhamDTO(String maLSP, String tenLSP) {
        this.maLSP = maLSP;
        this.tenLSP = tenLSP;
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
}

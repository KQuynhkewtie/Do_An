package dto;

public class NhanVienDTO {
	private String maNhanVien;
    private String hoTen;
    private String cccd;
    private String sdt;
    private String viTriCongViec;
    private String maSoThue;
    
    public NhanVienDTO() {
    }
    
    public NhanVienDTO(String maNhanVien, String hoTen, String cccd, String sdt, String viTriCongViec, String maSoThue) {
        this.maNhanVien = maNhanVien;
        this.hoTen = hoTen;
        this.cccd = cccd;
        this.sdt = sdt;
        this.viTriCongViec = viTriCongViec;
        this.maSoThue = maSoThue;
    }

    // Getter và Setter
    public String getMaNhanVien() {
        return maNhanVien;
    }

    public void setMaNhanVien(String maNhanVien) {
        this.maNhanVien = maNhanVien;
    }

    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public String getCccd() {
        return cccd;
    }

    public void setCccd(String cccd) {
        this.cccd = cccd;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public String getViTriCongViec() {
        return viTriCongViec;
    }

    public void setViTriCongViec(String viTriCongViec) {
        this.viTriCongViec = viTriCongViec;
    }

    public String getMaSoThue() {
        return maSoThue;
    }

    public void setMaSoThue(String maSoThue) {
        this.maSoThue = maSoThue;
    }
    
    @Override
    public String toString() {
        return "NhanVien{" +
                "maNhanVien='" + maNhanVien + '\'' +
                ", hoTen='" + hoTen + '\'' +
                ", cccd='" + cccd + '\'' +
                ", sdt='" + sdt + '\'' +
                ", viTriCongViec='" + viTriCongViec + '\'' +
                ", maSoThue='" + maSoThue + '\'' +
                '}';
    }

}



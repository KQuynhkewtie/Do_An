package dto;

public class TaiKhoanDTO {
	private String maTK;
    private String tenTK;
    private String matKhau;
    private String email;
    private String maNhanVien;
    
    public TaiKhoanDTO() {}

    public TaiKhoanDTO(String maTK, String tenTK, String matKhau, String email, String maNhanVien) {
        this.maTK = maTK;
        this.tenTK = tenTK;
        this.matKhau = matKhau;
        this.email = email;
        this.maNhanVien = maNhanVien;
    }

    // Getter và Setter
    public String getMaTK() { return maTK; }
    public void setMaTK(String maTK) { this.maTK = maTK; }

    public String getTenTK() { return tenTK; }
    public void setTenTK(String tenTK) { this.tenTK = tenTK; }

    public String getMatKhau() { return matKhau; }
    public void setMatKhau(String matKhau) { this.matKhau = matKhau; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getMaNhanVien() { return maNhanVien; }
    public void setMaNhanVien(String maNhanVien) { this.maNhanVien = maNhanVien; }

}

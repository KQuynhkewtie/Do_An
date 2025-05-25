package dto;

public class NhanVienDTO {
    private String maNV;
    private String hoTen;
    private String cccd;
    private String sdt;
    private String maVT;
    private String tenVaiTro;
    private String maSoThue;
    private int trangThai;
    
    public NhanVienDTO() {
    }
    
    public NhanVienDTO(String maNV, String hoTen, String cccd, String sdt, String maVT, String maSoThue, int trangThai) {
        this.maNV = maNV;
        this.hoTen = hoTen;
        this.cccd = cccd;
        this.sdt = sdt;
        this.maVT = maVT;
        this.maSoThue = maSoThue;
        this.trangThai = trangThai;
    }
    public NhanVienDTO(String maNV, String hoTen, String cccd, String sdt, String maVT, String tenVT, String maSoThue, int trangThai) {
        this.maNV = maNV;
        this.hoTen = hoTen;
        this.cccd = cccd;
        this.sdt = sdt;
        this.maVT = maVT;
        this.tenVaiTro = tenVT;
        this.maSoThue = maSoThue;
        this.trangThai = trangThai;
    }


    public String getMaNhanVien() {
        return maNV;
    }

    public void setMaNhanVien(String maNhanVien) {
        this.maNV = maNhanVien;
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
        return maVT;
    }

    public void setViTriCongViec(String maVT) {
        this.maVT = maVT;
    }

    public String getMaSoThue() {
        return maSoThue;
    }

    public void setMaSoThue(String maSoThue) {
        this.maSoThue = maSoThue;
    }

    public int getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(int trangThai) {
        this.trangThai = trangThai;
    }

    public String getTenVaiTro() {
        return tenVaiTro;
    }

    public void setTenVaiTro(String tenVaiTro) {
        this.tenVaiTro = tenVaiTro;
    }

    public String convertTenVTToMaVT(String tenVT) {
        if ("Nhân viên bán hàng".equals(tenVT)) {
            return "VT003";
        } else if ("Nhân viên quản lý".equals(tenVT)) {
            return "VT002";
        }
        return null;
    }

}



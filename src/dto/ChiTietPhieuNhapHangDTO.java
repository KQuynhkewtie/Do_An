package dto;

public class ChiTietPhieuNhapHangDTO {
    private String maPNH;
    private String maSP;
    private int soLuongNhap;
    private double giaNhap;

    // Constructors
    public ChiTietPhieuNhapHangDTO() {}

    public ChiTietPhieuNhapHangDTO(String maPNH, String maSP, int soLuongNhap, double giaNhap) {
        this.maPNH = maPNH;
        this.maSP = maSP;
        this.soLuongNhap = soLuongNhap;
        this.giaNhap = giaNhap;
    }

    // Getters and Setters
    public String getMaPNH() {
        return maPNH;
    }

    public void setMaPNH(String maPNH) {
        this.maPNH = maPNH;
    }

    public String getMaSP() {
        return maSP;
    }

    public void setMaSP(String maSP) {
        this.maSP = maSP;
    }

    public int getSoLuongNhap() {
        return soLuongNhap;
    }

    public void setSoLuongNhap(int soLuongNhap) {
        this.soLuongNhap = soLuongNhap;
    }

    public double getGiaNhap() {
        return giaNhap;
    }

    public void setGiaNhap(double giaNhap) {
        this.giaNhap = giaNhap;
    }
}
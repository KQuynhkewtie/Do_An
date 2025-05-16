package dto;

public class ChiTietHoaDonDTO {
    private String maHoaDon;
    private String maSanPham;
    private int soLuong;
    private double gia;

    public ChiTietHoaDonDTO() {}

    public ChiTietHoaDonDTO(String maHoaDon, String maSanPham, int soLuong, double gia) {
        this.maHoaDon = maHoaDon;
        this.maSanPham = maSanPham;
        this.soLuong = soLuong;
        this.gia = gia;
    }

    // Getter và Setter
    public String getMaHoaDon() { return maHoaDon; }
    public void setMaHoaDon(String maHoaDon) { this.maHoaDon = maHoaDon; }

    public String getMaSanPham() { return maSanPham; }
    public void setMaSanPham(String maSanPham) { this.maSanPham = maSanPham; }

    public int getSoLuong() { return soLuong; }
    public void setSoLuong(int soLuong) { this.soLuong = soLuong; }

    public double getGia() { return gia; }
    public void setGia(double gia) { this.gia = gia; }

}
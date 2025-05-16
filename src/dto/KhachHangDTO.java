package dto;


public class KhachHangDTO {
    private String maKH;
    private String hoTen;
    private int soLanMua;
    private double diemTichLuy;
    private String loaiKhach;

    // Constructor không tham số
    public KhachHangDTO() {}

    // Constructor đầy đủ tham số
    public KhachHangDTO(String maKH, String hoTen, int soLanMua, double diemTichLuy, String loaiKhach) {
        this.maKH = maKH;
        this.hoTen = hoTen;
        this.soLanMua = soLanMua;
        this.diemTichLuy = diemTichLuy;
        this.loaiKhach = loaiKhach;
    }

    // Getter & Setter
    public String getMaKH() {
        return maKH;
    }

    public void setMaKH(String maKH) {
        this.maKH = maKH;
    }

    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public int getSoLanMua() {
        return soLanMua;
    }

    public void setSoLanMua(int soLanMua) {
        this.soLanMua = soLanMua;
    }

    public double getDiemTichLuy() {
        return diemTichLuy;
    }

    public void setDiemTichLuy(double diemTichLuy) {
        this.diemTichLuy = diemTichLuy;
    }

    public String getLoaiKhach() {
        return loaiKhach;
    }

    public void setLoaiKhach(String loaiKhach) {
        this.loaiKhach = loaiKhach;
    }

    @Override
    public String toString() {
        return "Mã KH: " + maKH + " | Họ tên: " + hoTen + " | Số lần mua: " + soLanMua +
               " | Điểm tích lũy: " + diemTichLuy + " | Loại khách: " + loaiKhach;
    }
}

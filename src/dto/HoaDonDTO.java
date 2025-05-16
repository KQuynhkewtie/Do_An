package dto;

import java.util.Date;
import java.util.List;

public class HoaDonDTO {
	private String maHoaDon;
	private String maNhanVien;
	private String maKH;
	private Date ngayBan;
	private double thanhTien;
	private List<ChiTietHoaDonDTO> chiTietHoaDon;

	public HoaDonDTO() {
	}

	public HoaDonDTO(String maHoaDon, String maNhanVien, String maKH, Date ngayBan, double thanhTien) {
		this.maHoaDon = maHoaDon;
		this.maNhanVien = maNhanVien;
		this.maKH = maKH;
		this.ngayBan = ngayBan;
		this.thanhTien = thanhTien;
	}

	// Getter và Setter
	public String getMaHoaDon() {
		return maHoaDon;
	}

	public void setMaHoaDon(String maHoaDon) {
		this.maHoaDon = maHoaDon;
	}

	public String getMaNhanVien() {
		return maNhanVien;
	}

	public void setMaNhanVien(String maNhanVien) {
		this.maNhanVien = maNhanVien;
	}

	public String getMaKH() {
		return maKH;
	}

	public void setMaKH(String maKH) {
		this.maKH = maKH;
	}

	public Date getNgayBan() {
		return ngayBan;
	}

	public void setNgayBan(Date ngayBan) {
		this.ngayBan = ngayBan;
	}

	public double getThanhTien() {
		return thanhTien;
	}

	public void setThanhTien(double thanhTien) {
		this.thanhTien = thanhTien;
	}

	public List<ChiTietHoaDonDTO> getChiTietHoaDon() {
		return chiTietHoaDon;
	}

	public void setChiTietHoaDon(List<ChiTietHoaDonDTO> chiTietHoaDon) {
		this.chiTietHoaDon = chiTietHoaDon;
	}

	// Thêm constructor mới
	public HoaDonDTO(String maHoaDon, String maNhanVien, String maKH, Date ngayBan) {
		this.maHoaDon = maHoaDon;
		this.maNhanVien = maNhanVien;
		this.maKH = maKH;
		this.ngayBan = ngayBan;
		this.thanhTien = 0; // Khởi tạo thành tiền = 0
	}

}

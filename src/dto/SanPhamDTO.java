package dto;

import java.util.Date;
public class SanPhamDTO {
		private String maSP;
	    private String maHSX;
	    private String maLSP;
	    private String tenSP;
	    private String quyCachDongGoi;
	    private String soLo;
	    private String soDangKy;
	    private Date hsd;
	    private int hangTon;
	    private double giaBan;

	    // Constructor
	    public SanPhamDTO() {}

	    public SanPhamDTO(String maSP, String maHSX, String maLSP, String tenSP, String quyCachDongGoi,
						  String soLo, String soDangKy, Date hsd, int hangTon, double giaBan) {
	        this.maSP = maSP;
	        this.maHSX = maHSX;
	        this.maLSP = maLSP;
	        this.tenSP = tenSP;
	        this.quyCachDongGoi = quyCachDongGoi;
	        this.soLo = soLo;
	        this.soDangKy = soDangKy;
	        this.hsd = hsd;
	        this.hangTon = hangTon;
	        this.giaBan = giaBan;
	    }

	    // Getter & Setter
	    public String getMaSP() {
	    	return maSP; 
	    }
	    public void setMaSP(String maSP) { 
	    	this.maSP = maSP; 
	    }

	    public String getMaHSX() { 
	    	return maHSX;
	    }
	    public void setMaHSX(String maHSX) { 
	    	this.maHSX = maHSX; 
	    }

	    public String getMaLSP() {
	    	return maLSP; 
	    }
	    public void setMaLSP(String maLSP) { 
	    	this.maLSP = maLSP; 
	    }

	    public String getTenSP() {
	    	return tenSP; 
	    }
	    public void setTenSP(String tenSP) { 
	    	this.tenSP = tenSP; 
	    }

	    public String getQuyCachDongGoi() { 
	    	return quyCachDongGoi; 
	    }
	    public void setQuyCachDongGoi(String quyCachDongGoi) { 
	    	this.quyCachDongGoi = quyCachDongGoi;
	    }

	    public String getSoLo() {
	    	return soLo; 
	    }
	    public void setSoLo(String soLo) { 
	    	this.soLo = soLo; 
	    }

	    public String getSoDangKy() { 
	    	return soDangKy;
	    }
	    public void setSoDangKy(String soDangKy) { 
	    	this.soDangKy = soDangKy; 
	    }

	    public Date getHsd() { 
	    	return hsd; 
	    }
	    public void setHsd(Date hsd) {
	    	this.hsd = hsd; 
	    }

	    public int getHangTon() { 
	    	return hangTon; 
	    }
	    public void setHangTon(int hangTon) { 
	    	this.hangTon = hangTon; 
	    }

	    public double getGiaBan() { 
	    	return giaBan; 
	    }
	    public void setGiaBan(double giaBan) {
	    	this.giaBan = giaBan; 
	    }
	    
	    
	    @Override
	    public String toString() {
	        return "Mã SP: " + maSP + " | Tên SP: " + tenSP + " | Giá: " + giaBan + " | HSD: " + hsd;
	    }
}

   


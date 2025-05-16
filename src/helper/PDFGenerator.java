package helper;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import dto.*;
import bll.*;
import java.awt.*;
import java.awt.FileDialog;
import java.io.*;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import com.itextpdf.text.Font;

public class PDFGenerator {
    private static final DecimalFormat formatter = new DecimalFormat("###,###,###");
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");

    private Font fontNormal;
    private Font fontBold;
    private Font fontTitle;
    private Font fontBoldItalic;

    private JFrame parentFrame;
    private FileDialog fileDialog;

    public PDFGenerator (JFrame parentFrame) {
        this.parentFrame = parentFrame;
        this.fileDialog = new FileDialog(parentFrame, "Xuất hóa đơn PDF", FileDialog.SAVE);

        try {
            // Khởi tạo font Unicode (đảm bảo có file font trong thư mục fonts)
            BaseFont baseFont = BaseFont.createFont("lib/Roboto/Roboto-Regular.ttf",
                    BaseFont.IDENTITY_H,
                    BaseFont.EMBEDDED);

            fontNormal = new Font(baseFont, 12, Font.NORMAL);
            fontBold = new Font(baseFont, 12, Font.BOLD);
            fontTitle = new Font(baseFont, 18, Font.BOLD);
            fontBoldItalic = new Font(baseFont, 12, Font.BOLDITALIC);
        } catch (Exception e) {
            // Fallback font nếu không tìm thấy font Unicode
            fontNormal = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL);
            fontBold = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD);
            fontTitle = new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.BOLD);
            fontBoldItalic = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLDITALIC);
        }
    }

    public boolean exportInvoice(String maHoaDon) {
        String filePath = showSaveDialog("HD_" + maHoaDon);
        if (filePath == null) {
            return false; // Người dùng hủy
        }

        if (!filePath.toLowerCase().endsWith(".pdf")) {
            filePath += ".pdf";
        }

        try {
            // Lấy thông tin từ các BLL
            HoaDonBLL hoaDonBLL = new HoaDonBLL();
            KhachHangBLL khachHangBLL = new KhachHangBLL();
            NhanVienBLL nhanVienBLL = new NhanVienBLL();
            SanPhamBLL sanPhamBLL = new SanPhamBLL();

            // Lấy thông tin hóa đơn
            HoaDonDTO hoaDon = hoaDonBLL.layHoaDonTheoMa(maHoaDon);
            if (hoaDon == null) {
                JOptionPane.showMessageDialog(parentFrame, "Không tìm thấy hóa đơn " + maHoaDon);
                return false;
            }

            // Lấy thông tin khách hàng
            KhachHangDTO khachHang = khachHangBLL.getKhachHangById(hoaDon.getMaKH());

            // Lấy thông tin nhân viên
            NhanVienDTO nhanVien = nhanVienBLL.getNhanVienTheoMa(hoaDon.getMaNhanVien());

            // Lấy chi tiết hóa đơn
            List<ChiTietHoaDonDTO> chiTietHoaDon = hoaDonBLL.layChiTietHoaDon(maHoaDon);

            // Tạo tài liệu PDF
            Document document = new Document();
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(filePath));
            document.open();

            // Thêm thông tin công ty
            addCompanyInfo(document);

            // Thêm tiêu đề hóa đơn
            addInvoiceHeader(document);

            // Thêm thông tin hóa đơn
            addInvoiceInfo(document, hoaDon, nhanVien, khachHang);

            // Thêm chi tiết sản phẩm
            addProductDetails(document, chiTietHoaDon, sanPhamBLL);

            // Thêm tổng thanh toán
            addPaymentInfo(document, hoaDon);

            // Thêm chỗ ký
            addSignatureSection(document);

            document.close();
            writer.close();

            // Mở file sau khi tạo
            openFile(filePath);

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(parentFrame, "Lỗi khi xuất hóa đơn: " + e.getMessage());
            return false;
        }
    }

    private String showSaveDialog(String defaultFileName) {
        fileDialog.setFile(defaultFileName + ".pdf");
        fileDialog.setVisible(true);

        String directory = fileDialog.getDirectory();
        String fileName = fileDialog.getFile();

        if (directory == null || fileName == null) {
            return null; // Người dùng hủy
        }

        return directory + fileName;
    }

    private void openFile(String filePath) {
        try {
            File file = new File(filePath);
            if (file.exists()) {
                Desktop.getDesktop().open(file);
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(parentFrame, "Không thể mở file: " + e.getMessage());
        }
    }

    private void addCompanyInfo(Document document) throws DocumentException {
        Paragraph companyInfo = new Paragraph();
        companyInfo.add(new Chunk("NHÀ THUỐC AN TÂM", fontBold));
        companyInfo.add(Chunk.NEWLINE);
        companyInfo.add(new Chunk("Địa chỉ: 123 Đường Sức Khỏe, Quận 5, TP.HCM", fontNormal));
        companyInfo.add(Chunk.NEWLINE);
        companyInfo.add(new Chunk("Điện thoại: 0909 123 456 - MST: 1234567890", fontNormal));
        companyInfo.add(Chunk.NEWLINE);
        companyInfo.add(new Chunk("Email: lienhe@nhathuocantam.vn", fontNormal));
        companyInfo.add(Chunk.NEWLINE);
        companyInfo.add(new Chunk("Ngày xuất: " + dateFormat.format(new Date()), fontNormal));
        companyInfo.setAlignment(Element.ALIGN_LEFT);
        document.add(companyInfo);
        document.add(Chunk.NEWLINE);
    }

    private void addInvoiceHeader(Document document) throws DocumentException {
        Paragraph title = new Paragraph("HÓA ĐƠN BÁN HÀNG", fontTitle);
        title.setAlignment(Element.ALIGN_CENTER);
        title.setSpacingAfter(20);
        document.add(title);
    }

    private void addInvoiceInfo(Document document, HoaDonDTO hoaDon,
                                NhanVienDTO nhanVien, KhachHangDTO khachHang)
            throws DocumentException {
        Paragraph info = new Paragraph();
        info.add(new Chunk("Mã hóa đơn: " + hoaDon.getMaHoaDon(), fontBold));
        info.add(Chunk.NEWLINE);
        info.add(new Chunk("Ngày bán: " + dateFormat.format(hoaDon.getNgayBan()), fontNormal));
        info.add(Chunk.NEWLINE);
        info.add(new Chunk("Nhân viên: " + nhanVien.getHoTen() + " (Mã NV: " + hoaDon.getMaNhanVien() + ")", fontNormal));
        info.add(Chunk.NEWLINE);
        info.add(new Chunk("Khách hàng: " + khachHang.getHoTen() + " (" + khachHang.getLoaiKhach() + " - " +
                khachHang.getDiemTichLuy() + " điểm)", fontNormal));
        info.add(Chunk.NEWLINE);
        info.setAlignment(Element.ALIGN_LEFT);
        document.add(info);
        document.add(Chunk.NEWLINE);

        // Đường kẻ ngang
        addSeparator(document);
    }

    private void addProductDetails(Document document, List<ChiTietHoaDonDTO> chiTietHoaDon,
                                   SanPhamBLL sanPhamBLL) throws DocumentException {
        Paragraph title = new Paragraph("CHI TIẾT SẢN PHẨM", fontBold);
        title.setAlignment(Element.ALIGN_LEFT);
        document.add(title);
        document.add(Chunk.NEWLINE);

        PdfPTable table = new PdfPTable(6); // 6 cột
        table.setWidthPercentage(100);
        table.setWidths(new float[]{1f, 3f, 2f, 2f, 2f, 2f});

        // Tiêu đề bảng
        addTableHeaderCell(table, "STT");
        addTableHeaderCell(table, "Tên thuốc");
        addTableHeaderCell(table, "Đóng gói");
        addTableHeaderCell(table, "Đơn giá");
        addTableHeaderCell(table, "Số lượng");
        addTableHeaderCell(table, "Thành tiền");

        // Thêm dữ liệu
        int stt = 1;
        for (ChiTietHoaDonDTO cthd : chiTietHoaDon) {
            SanPhamDTO sanPham = sanPhamBLL.getSanPhamById(cthd.getMaSanPham());

            addTableCell(table, String.valueOf(stt++), Element.ALIGN_CENTER);
            addTableCell(table, sanPham.getTenSP(), Element.ALIGN_LEFT);
            addTableCell(table, sanPham.getQuyCachDongGoi(), Element.ALIGN_LEFT);
            addTableCell(table, formatter.format(cthd.getGia()) + "đ", Element.ALIGN_RIGHT);
            addTableCell(table, String.valueOf(cthd.getSoLuong()), Element.ALIGN_CENTER);
            addTableCell(table, formatter.format(cthd.getGia() * cthd.getSoLuong()) + "đ", Element.ALIGN_RIGHT);
        }

        document.add(table);
        document.add(Chunk.NEWLINE);

        // Đường kẻ ngang
        addSeparator(document);
    }

    private void addPaymentInfo(Document document, HoaDonDTO hoaDon) throws DocumentException {
        Paragraph payment = new Paragraph();
        payment.add(new Chunk("Tổng cộng: ", fontNormal));
        payment.add(new Chunk(formatter.format(hoaDon.getThanhTien()) + "đ", fontBold));
        payment.add(Chunk.NEWLINE);
        payment.add(new Chunk("Giảm giá: ", fontNormal));
        payment.add(new Chunk(formatter.format(0) + "đ", fontBold)); // Có thể thay bằng hoaDon.getGiamGia() nếu có
        payment.add(Chunk.NEWLINE);
        payment.add(new Chunk("VAT (nếu có): ", fontNormal));
        payment.add(new Chunk(formatter.format(0) + "đ", fontBold)); // Có thể thay bằng hoaDon.getVAT() nếu có
        payment.add(Chunk.NEWLINE);
        payment.add(new Chunk("Khách phải trả: ", fontBold));
        payment.add(new Chunk(formatter.format(hoaDon.getThanhTien()) + "đ", fontBold));
        payment.setAlignment(Element.ALIGN_RIGHT);
        document.add(payment);
        document.add(Chunk.NEWLINE);
    }

    private void addSignatureSection(Document document) throws DocumentException {
        Paragraph signatures = new Paragraph();
        signatures.add(new Chunk("Người lập phiếu", fontBoldItalic));
        signatures.add(Chunk.NEWLINE);
        signatures.add(new Chunk("(Ký và ghi rõ họ tên)", fontNormal));

        document.add(signatures);
    }

    private void addTableHeaderCell(PdfPTable table, String text) {
        PdfPCell cell = new PdfPCell(new Phrase(text, fontBold));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setBackgroundColor(new BaseColor(220, 220, 220)); // Màu nền xám nhạt
        cell.setPadding(5);
        table.addCell(cell);
    }

    private void addTableCell(PdfPTable table, String text, int alignment) {
        PdfPCell cell = new PdfPCell(new Phrase(text, fontNormal));
        cell.setHorizontalAlignment(alignment);
        cell.setPadding(5);
        table.addCell(cell);
    }

    private void addSeparator(Document document) throws DocumentException {
        Paragraph separator = new Paragraph("---------------------------------------------------"
                + "-------------------------------------");
        separator.setAlignment(Element.ALIGN_CENTER);
        document.add(separator);
        document.add(Chunk.NEWLINE);
    }

    // Hàm tiện ích tạo khoảng trắng
    private static Chunk createWhiteSpace(int length) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < length; i++) {
            builder.append(" ");
        }
        return new Chunk(builder.toString());
    }
}
package helper;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import dto.HoaDonDTO;
import dto.ChiTietHoaDonDTO;
import bll.HoaDonBLL;
import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

public class PDFGenerator {
    private HoaDonBLL hdBLL = new HoaDonBLL();

//    public void exportHoaDonToPDF(JFrame parentFrame, String maHoaDon) {
//        if (maHoaDon == null || maHoaDon.trim().isEmpty()) {
//            JOptionPane.showMessageDialog(parentFrame, "Không có hóa đơn để xuất!", "Lỗi", JOptionPane.ERROR_MESSAGE);
//            return;
//        }
//
//        JFileChooser fileChooser = new JFileChooser();
//        fileChooser.setDialogTitle("Chọn nơi lưu file PDF");
//        fileChooser.setSelectedFile(new File("HoaDon_" + maHoaDon + ".pdf"));
//
//        int userSelection = fileChooser.showSaveDialog(parentFrame);
//
//        if (userSelection == JFileChooser.APPROVE_OPTION) {
//            File fileToSave = fileChooser.getSelectedFile();
//            String filePath = fileToSave.getAbsolutePath();
//            if (!filePath.toLowerCase().endsWith(".pdf")) {
//                filePath += ".pdf";
//            }
//
//            try {
//                generatePDF(filePath, maHoaDon);
//                JOptionPane.showMessageDialog(parentFrame,
//                        "Xuất PDF thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
//            } catch (Exception ex) {
//                JOptionPane.showMessageDialog(parentFrame,
//                        "Lỗi khi xuất PDF: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
//                ex.printStackTrace();
//            }
//        }
//    }

    public void exportHoaDonToPDF(JFrame parentFrame,
                                  HoaDonDTO hd,
                                  List<ChiTietHoaDonDTO> chiTiet,
                                  Map<String, String> tenSanPhamMap) {
        if (hd == null || chiTiet == null) {
            JOptionPane.showMessageDialog(parentFrame,
                    "Dữ liệu hóa đơn không hợp lệ!",
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Chọn nơi lưu file PDF");
        fileChooser.setSelectedFile(new File("HoaDon_" + hd.getMaHoaDon() + ".pdf"));

        int userSelection = fileChooser.showSaveDialog(parentFrame);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();
            String filePath = fileToSave.getAbsolutePath();
            if (!filePath.toLowerCase().endsWith(".pdf")) {
                filePath += ".pdf";
            }

            try {
                generatePDFFromData(filePath, hd, chiTiet, tenSanPhamMap);
                JOptionPane.showMessageDialog(parentFrame,
                        "Xuất PDF thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(parentFrame,
                        "Lỗi khi xuất PDF: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }
        }
    }

    private void generatePDFFromData(String filePath,
                                     HoaDonDTO hd,
                                     List<ChiTietHoaDonDTO> chiTiet,
                                     Map<String, String> tenSanPhamMap) throws Exception {
        Document document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream(filePath));
        document.open();

        // Font Unicode (sử dụng font mặc định của hệ thống)
        BaseFont bf = BaseFont.createFont(
                "c:/windows/fonts/times.ttf",  // Đường dẫn font Times New Roman
                BaseFont.IDENTITY_H,
                BaseFont.EMBEDDED
        );

        Font fontTitle = new Font(bf, 18, Font.BOLD);
        Font fontHeader = new Font(bf, 14, Font.BOLD);
        Font fontNormal = new Font(bf, 12, Font.NORMAL);

        // Tiêu đề
        Paragraph title = new Paragraph("HÓA ĐƠN BÁN HÀNG", fontTitle);
        title.setAlignment(Element.ALIGN_CENTER);
        title.setSpacingAfter(20f);
        document.add(title);

        // Thông tin hóa đơn
        PdfPTable infoTable = new PdfPTable(2);
        infoTable.setWidthPercentage(100);
        infoTable.setSpacingBefore(10f);
        infoTable.setSpacingAfter(10f);

        addCell(infoTable, "Mã hóa đơn:", fontHeader);
        addCell(infoTable, hd.getMaHoaDon(), fontNormal);
        addCell(infoTable, "Mã nhân viên:", fontHeader);
        addCell(infoTable, hd.getMaNhanVien(), fontNormal);
        addCell(infoTable, "Mã khách hàng:", fontHeader);
        addCell(infoTable, hd.getMaKH() != null ? hd.getMaKH() : "Không có", fontNormal);
        addCell(infoTable, "Ngày lập:", fontHeader);
        addCell(infoTable, new SimpleDateFormat("dd/MM/yyyy").format(hd.getNgayBan()), fontNormal);
        addCell(infoTable, "Trạng thái:", fontHeader);
        addCell(infoTable, "DA_HUY".equals(hd.getTrangThai()) ? "Đã hủy" : "Bình thường", fontNormal);

        document.add(infoTable);

        // Danh sách sản phẩm
        Paragraph productTitle = new Paragraph("DANH SÁCH SẢN PHẨM", fontHeader);
        productTitle.setAlignment(Element.ALIGN_CENTER);
        productTitle.setSpacingBefore(20f);
        productTitle.setSpacingAfter(10f);
        document.add(productTitle);

        PdfPTable productTable = new PdfPTable(5);
        productTable.setWidthPercentage(100);
        productTable.setSpacingAfter(20f);

        // Header
        addCell(productTable, "Mã SP", fontHeader);
        addCell(productTable, "Tên sản phẩm", fontHeader);
        addCell(productTable, "Số lượng", fontHeader);
        addCell(productTable, "Đơn giá", fontHeader);
        addCell(productTable, "Thành tiền", fontHeader);

        // Dữ liệu
        for (ChiTietHoaDonDTO ct : chiTiet) {
            String tenSP = tenSanPhamMap.getOrDefault(ct.getMaSanPham(), "Không xác định");
            double thanhTien = ct.getSoLuong() * ct.getGia();

            addCell(productTable, ct.getMaSanPham(), fontNormal);
            addCell(productTable, tenSP, fontNormal);
            addCell(productTable, String.valueOf(ct.getSoLuong()), fontNormal);
            addCell(productTable, formatCurrency(ct.getGia()), fontNormal);
            addCell(productTable, formatCurrency(thanhTien), fontNormal);
        }

        document.add(productTable);

        // Tổng tiền
        Paragraph total = new Paragraph("TỔNG CỘNG: " + formatCurrency(hd.getThanhTien()), fontHeader);
        total.setAlignment(Element.ALIGN_RIGHT);
        total.setSpacingBefore(10f);
        document.add(total);

        // Ký tên
        Paragraph signature = new Paragraph("\n\n\n\nNgười lập\n(Ký và ghi rõ họ tên)", fontNormal);
        signature.setAlignment(Element.ALIGN_RIGHT);
        document.add(signature);

        document.close();
    }

    private void addCell(PdfPTable table, String text, Font font) {
        PdfPCell cell = new PdfPCell(new Phrase(text, font));
        cell.setPadding(5);
        cell.setBorderColor(BaseColor.LIGHT_GRAY);
        table.addCell(cell);
    }

    private String formatCurrency(double amount) {
        return String.format("%,.0f VNĐ", amount);
    }
}
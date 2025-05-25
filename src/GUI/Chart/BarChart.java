package GUI.Chart;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import javax.swing.*;
import java.awt.*;

public class BarChart extends JPanel {
    private JFreeChart chart;
    private DefaultCategoryDataset dataset;

    public BarChart() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        // Khởi tạo dataset và biểu đồ
        dataset = new DefaultCategoryDataset();

        chart = ChartFactory.createBarChart(
                "BIỂU ĐỒ DOANH THU", // Tiêu đề
                "Thời gian",         // Nhãn trục X
                "Doanh thu (VND)",   // Nhãn trục Y
                dataset,            // Dữ liệu
                PlotOrientation.VERTICAL,
                true,               // Hiển thị legend
                true,               // Hiển thị tooltips
                false               // Hiển thị URLs
        );

        // Tùy chỉnh màu sắc
        chart.setBackgroundPaint(Color.WHITE);
        chart.getCategoryPlot().setBackgroundPaint(Color.WHITE);

        // Tạo ChartPanel và thêm vào JPanel
        ChartPanel chartPanel = new ChartPanel(chart);
        add(chartPanel, BorderLayout.CENTER);
    }

    // Cập nhật biểu đồ theo ngày
    public void updateDayChart(String date, double[] dailyData) {
        dataset.clear();

        dataset.addValue(dailyData[0], "Doanh thu", "1-5");
        dataset.addValue(dailyData[1], "Doanh thu", "6-10");
        dataset.addValue(dailyData[2], "Doanh thu", "11-15");
        dataset.addValue(dailyData[3], "Doanh thu", "16-20");
        dataset.addValue(dailyData[4], "Doanh thu", "21-25");
        dataset.addValue(dailyData[5], "Doanh thu", "26-31");

        chart.setTitle("BIỂU ĐỒ DOANH THU THEO NGÀY: " + date);
    }

    // Cập nhật biểu đồ theo tháng
    public void updateMonthChart(String year, double[] monthlyData) {
        dataset.clear();

        for (int i = 0; i < 12; i++) {
            String month = String.format("%02d/%s", i+1, year);
            dataset.addValue(monthlyData[i], "Doanh thu", month);
        }

        chart.setTitle("BIỂU ĐỒ DOANH THU 12 THÁNG NĂM " + year);
    }

    // Cập nhật biểu đồ theo năm
    public void updateYearChart(String currentYear, double[] yearlyData) {
        dataset.clear();

        int year = Integer.parseInt(currentYear);
        for (int i = 0; i < 5; i++) {
            int y = year - 4 + i;
            dataset.addValue(yearlyData[i], "Doanh thu", String.valueOf(y));
        }

        chart.setTitle("BIỂU ĐỒ DOANH THU 5 NĂM GẦN NHẤT (" + (year-4) + "-" + year + ")");
    }

    // Cập nhật biểu đồ theo cụm ngày trong tháng
    public void updateCumNgayChart(String monthYear, double[] cumNgayData) {
        dataset.clear();

        String[] categories = {"1-5", "6-10", "11-15", "16-20", "21-25", "26-31"};
        for (int i = 0; i < categories.length; i++) {
            dataset.addValue(cumNgayData[i], "Doanh thu", categories[i]);
        }

        chart.setTitle("BIỂU ĐỒ DOANH THU THEO CỤM NGÀY TRONG THÁNG " + monthYear);
    }
}
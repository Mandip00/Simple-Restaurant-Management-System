package minor;


import javax.swing.JFrame;
import javax.swing.JPanel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;

import java.sql.*;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

public class graph extends JFrame {

    JPanel chartPanel;
    public graph() {

        chartPanel=new ChartPanel(createChart());
        add(chartPanel);

        setTitle("Sales");
        setSize(560, 300);
        setLocationRelativeTo(null);
        setVisible(true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

    }

    private static JFreeChart createChart() {
       //DefaultPieDataset dataset = new DefaultPieDataset();
        DefaultCategoryDataset dataset=new DefaultCategoryDataset();
        DBconnection db = new DBconnection();
        try {
            Statement stmt = db.cn.createStatement();
            ResultSet rs = stmt.executeQuery("select Name,sum(Quantity) from billgraph group by Name");
            while (rs.next()) {
              
              dataset.setValue(rs.getInt(2),"Sales",rs.getString(1));
              
            }
        } catch (Exception e) {
        }

        
        JFreeChart chart=ChartFactory.createBarChart("Sales", "Item", "Qty", dataset,PlotOrientation.VERTICAL,false,true,false);
        return chart;
    }

    public static void main(String[] args) {
        new graph();

    }
}

package jfreechart;

import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;

import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.labels.CategoryItemLabelGenerator;
import org.jfree.chart.labels.ItemLabelAnchor;
import org.jfree.chart.labels.ItemLabelPosition;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.DatasetRenderingOrder;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.HorizontalAlignment;
import org.jfree.ui.TextAnchor;

public class ChartB {
    public JFreeChart getChart() {
        
        // ������ ����
        DefaultCategoryDataset dataset = new DefaultCategoryDataset(); 
 
        //------------------------------------------------------------------
        // ������ �Է� ( ��, ����, ī�װ� )
        Database db = new Database();
        ArrayList<ArrayList> data = db.getData();
        for(ArrayList temp : data) {
        	int value = (Integer) temp.get(0);
        	String cate = (String) temp.get(1);
        	dataset.addValue(value, "����",  cate);
        }
        //------------------------------------------------------------------
        
        // ������ ���� �� ����
        // ������ ����
        final BarRenderer renderer = new BarRenderer();
     
        // ���� �ɼ� ����
        final CategoryItemLabelGenerator generator = new StandardCategoryItemLabelGenerator();
        final ItemLabelPosition p_center = new ItemLabelPosition(
                ItemLabelAnchor.CENTER, TextAnchor.CENTER
            );
        final ItemLabelPosition p_below = new ItemLabelPosition(
                     ItemLabelAnchor.OUTSIDE6, TextAnchor.TOP_LEFT
                     );
        Font f = new Font("Gulim", Font.BOLD, 14);
        Font axisF = new Font("Gulim", Font.PLAIN, 14);
       
        // ������ ����
        // �׷��� 1
        renderer.setBaseItemLabelGenerator(generator);
        renderer.setBaseItemLabelsVisible(true);
        renderer.setBasePositiveItemLabelPosition(p_center);
        renderer.setBaseItemLabelFont(f);
        renderer.setSeriesPaint(0, new Color(0,162,255));
 
        // plot ����
        final CategoryPlot plot = new CategoryPlot();
       
        // plot �� ������ ����
        plot.setDataset(dataset);
        plot.setRenderer(renderer);
 
        // plot �⺻ ����
        plot.setOrientation(PlotOrientation.VERTICAL);       // �׷��� ǥ�� ����
        plot.setRangeGridlinesVisible(true);                         // X�� ���̵� ���� ǥ�ÿ���
        plot.setDomainGridlinesVisible(true);                      // Y�� ���̵� ���� ǥ�ÿ���
 
        // ������ ���� ���� : dataset ��� ������� ������ ( ��, ���� ����Ѱ� �Ʒ��� �� )
        plot.setDatasetRenderingOrder(DatasetRenderingOrder.FORWARD);
       
        // X�� ����
        plot.setDomainAxis(new CategoryAxis());           // X�� ���� ����
        plot.getDomainAxis().setTickLabelFont(axisF); // X�� ���ݶ� ��Ʈ ����
        plot.getDomainAxis().setCategoryLabelPositions(CategoryLabelPositions.STANDARD);       // ī�װ� �� ��ġ ����
 
        // Y�� ����
        plot.setRangeAxis(new NumberAxis());              // Y�� ���� ����
        plot.getRangeAxis().setTickLabelFont(axisF);  // Y�� ���ݶ� ��Ʈ ����
       
        // ���õ� plot�� �������� chart ����
        final JFreeChart chart = new JFreeChart(plot);
        chart.setTitle(" �츮���� ��Ʈ "); 
        TextTitle copyright = new TextTitle("���� �Ի� ��Ȳ ", new Font("SansSerif", Font.PLAIN, 9));
        copyright.setHorizontalAlignment(HorizontalAlignment.RIGHT);
        chart.addSubtitle(copyright);  
        return chart;
    }
}

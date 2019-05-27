package jfreechart;

import java.awt.*;

import org.jfree.chart.*;
import org.jfree.chart.axis.*;
import org.jfree.chart.labels.*;
import org.jfree.chart.plot.*;
import org.jfree.chart.renderer.category.*;
import org.jfree.chart.title.*;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.*;

public class ChartA {
    public JFreeChart getChart() {

        // ������ ����
        DefaultCategoryDataset dataset = new DefaultCategoryDataset(); 
 
        //------------------------------------------------------------------
        // ������ �Է� ( ��, ����, ī�װ� )
        dataset.addValue(1.0, "S1", "1��");
        dataset.addValue(4.0, "S1", "2��");
        dataset.addValue(3.0, "S1", "3��");
        dataset.addValue(5.0, "S1", "4��");
        dataset.addValue(5.0, "S1", "5��");
        dataset.addValue(7.0, "S1", "6��");
        dataset.addValue(7.0, "S1", "7��");
        dataset.addValue(8.0, "S1", "8��");
        dataset.addValue(5.0, "S1", "9��");
        dataset.addValue(0, "S1", "10��");
        dataset.addValue(6.0, "S1", "11��");
        dataset.addValue(3.0, "S1", "12��");
        // �� �κ��� �ݺ������� ��������� ���ڴٴ� ���� ���� ^^
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

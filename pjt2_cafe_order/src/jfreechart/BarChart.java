package jfreechart;

import java.awt.Color;
import java.awt.Font;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
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
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.title.TextTitle;
import org.jfree.chart.title.Title;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.ui.HorizontalAlignment;
import org.jfree.ui.TextAnchor;

import model.vo.Customer;

public class BarChart {

//	String driver = "oracle.jdbc.driver.OracleDriver";
//	//	String url = "jdbc:oracle:thin:@192.168.0.4:1521:orcl";
//	String url = "jdbc:oracle:thin:@127.0.0.1:1521:orcl";
//	String user = "lsh";
//	String pass = "lsh";
	
	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@192.168.0.117:1521:orcl";
	String user= "jink";
	String pass="1234";

	Connection con = null;
	private static DefaultCategoryDataset dataSet;
	private static String title;

	public void setTitle(String title){
		this.title = title;
	}

	public void dbSql_Excute(String sql) throws Exception {
		con = DriverManager.getConnection(url,user,pass);
		Customer dao = new Customer();
		ResultSet rs = null;

		//4. 전송객체
		PreparedStatement ps= con.prepareStatement(sql);

		//5.전송
		rs=ps.executeQuery();

		dataSet = new DefaultCategoryDataset( );
		while(rs.next())
		{
//			dataSet.addValue(rs.getInt("tot"), "월별주문량", rs.getString("month"));
			dataSet.addValue(rs.getInt("value"), "주문량", rs.getString("name"));
		}

		rs.close();
		ps.close();
		con.close();
	}

	private static JFreeChart createChart(  ) {
		//	   JFreeChart chart = ChartFactory.createPieChart(      
		//		  title,   // chart title 
		//		  dataSet,          // data    
		//	      true,             // include legend   
		//	      true, 
		//	      false);
		//	   
		//	   chart.getTitle().setFont(new Font("굴림", Font.BOLD, 15));
		//	   chart.getLegend().setItemFont(new Font("굴림", Font.BOLD, 15));
		//	   PiePlot plot = (PiePlot) chart.getPlot();
		//       plot.setLabelFont(new Font("굴림", Font.BOLD, 15));

		// 렌더링 생성 및 세팅
		// 렌더링 생성
		final BarRenderer renderer = new BarRenderer();

		// 공통 옵션 정의
		final CategoryItemLabelGenerator generator = new StandardCategoryItemLabelGenerator();
		final ItemLabelPosition p_center = new ItemLabelPosition(
				ItemLabelAnchor.CENTER, TextAnchor.CENTER
				);
		final ItemLabelPosition p_below = new ItemLabelPosition(
				ItemLabelAnchor.OUTSIDE6, TextAnchor.TOP_LEFT
				);
		Font f = new Font("Gulim", Font.BOLD, 14);
	//	Font axisF = new Font("Gulim", Font.PLAIN, 14);
		Font axisF = new Font("Gulim", Font.PLAIN, 11);

		// 렌더링 세팅
		// 그래프 1
		renderer.setBaseItemLabelGenerator(generator);
		renderer.setBaseItemLabelsVisible(true);
		renderer.setBasePositiveItemLabelPosition(p_center);
		renderer.setBaseItemLabelFont(f);
		renderer.setSeriesPaint(0, new Color(0,162,255));

		// plot 생성
		final CategoryPlot plot = new CategoryPlot();

		// plot 에 데이터 적재
		plot.setDataset(dataSet);
		plot.setRenderer(renderer);

		// plot 기본 설정
		plot.setOrientation(PlotOrientation.VERTICAL);       // 그래프 표시 방향
		plot.setRangeGridlinesVisible(true);                         // X축 가이드 라인 표시여부
		plot.setDomainGridlinesVisible(true);                      // Y축 가이드 라인 표시여부

		// 렌더링 순서 정의 : dataset 등록 순서대로 렌더링 ( 즉, 먼저 등록한게 아래로 깔림 )
		plot.setDatasetRenderingOrder(DatasetRenderingOrder.FORWARD);

		// X축 세팅
		plot.setDomainAxis(new CategoryAxis());           // X축 종류 설정
		plot.getDomainAxis().setTickLabelFont(axisF); // X축 눈금라벨 폰트 조정
		plot.getDomainAxis().setCategoryLabelPositions(CategoryLabelPositions.STANDARD);       // 카테고리 라벨 위치 조정

		// Y축 세팅
		plot.setRangeAxis(new NumberAxis());              // Y축 종류 설정
		plot.getRangeAxis().setTickLabelFont(axisF);  // Y축 눈금라벨 폰트 조정

		// 세팅된 plot을 바탕으로 chart 생성
		final JFreeChart chart = new JFreeChart(plot);
		chart.setTitle(title); 
		TextTitle copyright = new TextTitle(title, new Font("Gulim", Font.PLAIN, 9));
		copyright.setHorizontalAlignment(HorizontalAlignment.RIGHT);
		chart.addSubtitle(copyright);	

		return chart;
	}
//test
	public static JPanel createPieChartPanel( ) {
		JFreeChart chart = createChart();  
		return new ChartPanel( chart ); 
	}
}

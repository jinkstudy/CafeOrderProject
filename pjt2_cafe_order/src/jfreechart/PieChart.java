package jfreechart;

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
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.title.Title;
import org.jfree.data.general.DefaultPieDataset;

import model.vo.Customer;

/**
 * ¸ðµâ·Î ¸¸µéÀÚ
 * @author LS
 *
 */
public class PieChart {
	
//	String driver = "oracle.jdbc.driver.OracleDriver";
////	String url = "jdbc:oracle:thin:@192.168.0.4:1521:orcl";
//	String url = "jdbc:oracle:thin:@127.0.0.1:1521:orcl";
//	String user = "lsh";
//	String pass = "lsh";
	
	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@192.168.0.117:1521:orcl";
	String user= "jink";
	String pass="1234";
	
	Connection con = null;
	private static DefaultPieDataset dataSet;
	private static String title;
	
	public void setTitle(String title){
		this.title = title;
	}
	
	public void dbSql_Excute(String sql) throws Exception {
		con = DriverManager.getConnection(url,user,pass);
		Customer dao = new Customer();
		ResultSet rs = null;

		//4. Àü¼Û°´Ã¼
		PreparedStatement ps= con.prepareStatement(sql);
		
		//5.Àü¼Û
		rs=ps.executeQuery();

		dataSet = new DefaultPieDataset( );
		while(rs.next())
		{
//			dataSet.setValue( rs.getString("menuname") , rs.getInt("ocount") );
			dataSet.setValue( rs.getString("name") , rs.getInt("value") );
		}
		
		rs.close();
		ps.close();
		con.close();
	}
	
	private static JFreeChart createChart(  ) {
	   JFreeChart chart = ChartFactory.createPieChart(      
		  title,   // chart title 
		  dataSet,          // data    
	      true,             // include legend   
	      true, 
	      false);
	   
	   chart.getTitle().setFont(new Font("±¼¸²", Font.BOLD, 15));
	   chart.getLegend().setItemFont(new Font("±¼¸²", Font.BOLD, 15));
	   PiePlot plot = (PiePlot) chart.getPlot();
       plot.setLabelFont(new Font("±¼¸²", Font.BOLD, 15));
       
	   return chart;
	}
	
	public static JPanel createPieChartPanel( ) {
	   JFreeChart chart = createChart();  
	   return new ChartPanel( chart ); 
	}
}

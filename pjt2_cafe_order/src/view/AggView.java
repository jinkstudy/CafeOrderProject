package view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JTable;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import jfreechart.BarChart;
import jfreechart.PieChart;
import model.CustomerDao;
import model.dao.CustomerModel;
import model.vo.Customer;

public class AggView  extends JPanel {

	JButton bt_cal = new JButton("일별 통계"); //일 통계 버튼
	JButton bt_menu = new JButton("월별 통계"); //월 통계 버튼
	
	JPanel p_eChart = new JPanel();
	JPanel p_wChart = new JPanel();
	JPanel p_east = new JPanel();
	JPanel p_west = new JPanel();
	
	CustomerDao dao;

	public AggView() {
		addLayout();  //레이아웃
		eventProc();  //이벤트처리
		connectDB();  
	}

	public void connectDB(){
		try {
			dao = new CustomerModel();
		}catch(Exception ex) {
			JOptionPane.showMessageDialog(null,"드라이버 오류 : "+ ex.getMessage());
		}
	}

	
	public void addLayout(){

		JPanel p_north = new JPanel();
		JPanel p_south = new JPanel();
		setLayout(new BorderLayout());
		add(p_north, BorderLayout.NORTH);
		
		p_north.add(bt_cal);  // 일별 통계 버튼
		p_north.add(bt_menu); // 월별 통계 버튼
		
		add(p_south, BorderLayout.CENTER);
		

		p_south.setLayout(new GridLayout(1,2));
		p_south.add(p_east);
		p_south.add(p_west);
		
	}
	void eventProc() { //버튼 이벤트
		ButtonEventHandler btnHandler = new ButtonEventHandler();
		bt_cal.addActionListener(btnHandler);
		bt_menu.addActionListener(btnHandler);
		try {
			btnHandler.searchByTel();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	class ButtonEventHandler implements ActionListener{
		public void actionPerformed(ActionEvent ev){
			Object o = ev.getSource();
			try {
				if(o==bt_cal){ 
	//					JOptionPane.showMessageDialog(null,"엔터이벤트발생");
					searchByTel();  //일별 차트
				} else if(o==bt_menu){
					searchByTel2();  //월별 차트
				} 
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, e.getMessage());
			}
		}	

		// 전화번호에 의한 검색
		public void searchByTel() throws Exception{ //기간별 차트
			
			// 2. Model의 전화번호 검색메소드 selectByTel()  호출
			try {
				/**
				 *  차트 재 조회시 바뀌기 위한 곳 
				 */
				p_east.remove(p_eChart);
				p_west.remove(p_wChart);
				/**
				 *  차트 재 조회시 바뀌기 위한 곳 끝 
				 */
				
				/**
				 *  차트 종류 바꾸는 곳 
				 */
				p_eChart = BarChart2();
				p_wChart = PieChart2();
				/**
				 *  차트 종류 바꾸는 곳 끝 
				 */
				
				
				/**
				 *  차트 재 조회시 바뀌기 위한 곳 
				 */
				p_east.add(p_eChart);
				p_west.add(p_wChart);
				/**
				 *  차트 재 조회시 바뀌기 위한 곳 끝 
				 */
				
			}catch (Exception e) {
				System.out.println("Search FAIL"+e.getMessage());
			}

		}
		
		public void searchByTel2() throws Exception{ //일자별 차트
			
			// 2. Model의 전화번호 검색메소드 selectByTel()  호출
			try {
				/**
				 *  차트 재 조회시 바뀌기 위한 곳 
				 */
				p_east.remove(p_eChart);
				p_west.remove(p_wChart);
				/**
				 *  차트 재 조회시 바뀌기 위한 곳 끝 
				 */
				
				/**
				 *  차트 종류 바꾸는 곳 
				 */
				
				p_eChart = BarChart();
				p_wChart = PieChart();
				
				/**
				 *  차트 종류 바꾸는 곳 끝 
				 */
				
				
				/**
				 *  차트 재 조회시 바뀌기 위한 곳 
				 */
				p_east.add(p_eChart);
				p_west.add(p_wChart);
				/**
				 *  차트 재 조회시 바뀌기 위한 곳 끝 
				 */
				
			}catch (Exception e) {
				System.out.println("Search FAIL"+e.getMessage());
			}
			
		}
	}

	public JPanel PieChart() { //파이차트1. 5월 메뉴별 수량
		
		String sql = " SELECT M.MENUNAME AS name , COUNT(*) AS value "
		+ " FROM (SELECT * FROM ORDER_CUS WHERE SUBSTR(OTIME,6,2)=to_char(sysdate,'mm')) O, MENU M "
		+ " WHERE O.MENUNO = M.MENUNO " 
		+ " GROUP BY M.MENUNAME ";
				
		     
		PieChart chart = new PieChart();
		
		try {
			chart.setTitle("이용내역분석");
			chart.dbSql_Excute(sql);	
		}catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		return chart.createPieChartPanel();
	}
	
	public JPanel BarChart() { //바차트1. 월별 주문 건수
		String sql = " SELECT substr(otime,3,5) AS name, SUM(ocount) AS value " +
				" FROM order_cus " + 
				" GROUP BY substr(otime,3,5) " +
				" ORDER BY substr(otime,3,5) ";
		
		BarChart chart = new BarChart();
		
		try {
			chart.setTitle("이용내역분석");
			chart.dbSql_Excute(sql);	
		}catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		return chart.createPieChartPanel();
	}
	

	public JPanel PieChart2() { //파이차트2. 메뉴이름별 수량
	
		String sql = " SELECT M.MENUNAME AS name, COUNT(*) AS value "
				+ " FROM (SELECT * FROM ORDER_CUS WHERE SUBSTR(OTIME,9,2)=to_char(sysdate,'dd')) O, MENU M "
				+ " WHERE O.MENUNO = M.MENUNO "
				+ " GROUP BY M.MENUNAME ";
		     
		PieChart chart = new PieChart();
		
		try {
			chart.setTitle("이용내역분석");
			chart.dbSql_Excute(sql);	
		}catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		return chart.createPieChartPanel();
	}
	
	public JPanel BarChart2() { //바차트2. 일별 주문 건수
		String sql = " SELECT substr(otime,3,8) AS name, SUM(ocount) AS value " +
				" FROM order_cus " +
				" GROUP BY substr(otime,3,8) " +
				" ORDER BY substr(otime,3,8) ";
		
		BarChart chart = new BarChart();
		
		try {
			chart.setTitle("이용내역분석");
			chart.dbSql_Excute(sql);	
		}catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		return chart.createPieChartPanel();
	}
	
	public JPanel BarChart3() { //바차트3. 메뉴별 주문수량
//		String sql = " SELECT substr(otime,3,8) AS MONTH, SUM(ocount) AS tot " +
		String sql = " SELECT COUNT(*) AS VALUE , M.menuname AS NAME " 
				+ " FROM order_cus o, menu M "
				+ " WHERE o.menuno = M.menuno "
				+ " GROUP BY menuname "
				+ " ORDER BY COUNT(*) DESC";
		
		BarChart chart = new BarChart();
		
		try {
			chart.setTitle("이용내역분석");
			chart.dbSql_Excute(sql);	
		}catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		return chart.createPieChartPanel();
	}
	
}

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

	JButton bt_cal = new JButton("기간별"); //전화번호 입력 버튼
	JButton bt_menu = new JButton("메뉴별"); //마일리지 검색
	
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
		p_east.setLayout(new FlowLayout());
		JPanel p_east_north = new JPanel();
		JPanel p_east_center = new JPanel();
		p_east_north.setLayout(new GridLayout(2,2));
		p_east_north.add(bt_cal);
		p_east_north.add(bt_menu);
		p_east.add(p_east_north,BorderLayout.NORTH);
		p_east.add(p_east_center,BorderLayout.CENTER);

		//전체 영역
		setLayout(new GridLayout(1,2));
		add(p_east);
		p_east.setBorder(new TitledBorder("상세 이용 내역"));
		p_east.add(p_eChart);
		add(p_west);
		p_west.setBorder(new TitledBorder("이용 내역 분석"));
		p_west.add(p_wChart);
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
					searchByTel();  //기간별 차트
				} else if(o==bt_menu){
					searchByTel2();  //품목별 차트
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
				p_eChart = PieChart();
				p_wChart = BarChart();
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
		
		public void searchByTel2() throws Exception{ //품목별 차트
			
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
				p_wChart = PieChart();
				p_eChart = BarChart();
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

	public JPanel PieChart() { 
//		String sql = "SELECT m.menuname , sum(o.ocount) as ocount "
		String sql = "SELECT m.menuname as name, sum(o.ocount) as value "
		 + " FROM customer c "
		 + " inner join order_cus o on c.ctel=o.ctel "
		 + " inner join menu m on o.menuno = m.menuno "
		 + " group by m.menuname ";
		     
		PieChart chart = new PieChart();
		
		try {
			chart.setTitle("이용내역분석");
			chart.dbSql_Excute(sql);	
		}catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		return chart.createPieChartPanel();
	}
	
	public JPanel BarChart() { //월별 건수
//		String sql = " SELECT substr(otime,1,5) AS MONTH, SUM(ocount) AS tot " +
		String sql = " SELECT substr(otime,1,5) AS name, SUM(ocount) AS value " +
				" FROM order_cus " +
			//	" WHERE otime BETWEEN '19/01/01' AND '19/12/01' " + 
				" GROUP BY substr(otime,1,5) " +
				" ORDER BY substr(otime,1,5) ";
		
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


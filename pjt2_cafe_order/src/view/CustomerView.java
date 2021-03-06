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
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;

import jfreechart.BarChart;
import jfreechart.PieChart;
import model.CustomerDao;
import model.dao.CustomerModel;
import model.vo.Customer;

public class CustomerView  extends JPanel {

	JTextField tfInputTel = new JTextField(10);  //전화번호 입력	
	//	JTextField taMileage = new JTextField(10);  //마일리지 현황
	JTextField taMileage = new JTextField();  //마일리지 현황	
	TextArea tainfo  =   new TextArea( 25, 100);  //이용내역 현황
	JButton bt_tel = new JButton("검색"); //전화번호 입력 버튼
	JButton bt_mileage = new JButton("검색"); //마일리지 검색
	JPanel p_chart = new JPanel();
	JPanel p_east = new JPanel();
	JPanel p_west = new JPanel();
	
	CustomerDao dao;
	
	String a = "------------------- 이용 내역 -------------------"+'\n'+ '\n'
			+ " 이용날짜"+'\t'+'\t'+"메뉴"+'\t'+"수량"+'\t'+"금액"+'\t' +"이용형태"+'\t'+'\n'+ '\n'+ '\n';

	public CustomerView() {
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
		
		try {
			UIManager.setLookAndFeel("com.birosoft.liquid.LiquidLookAndFeel");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		p_east.setLayout(new FlowLayout());
		JPanel p_east_north = new JPanel();
		JPanel p_east_center = new JPanel();
	//	tfInputTel.setText("010-1111-1111");
		p_east_north.setLayout(new GridLayout(2,2));
		p_east_north.add(new JLabel("전화번호 입력"));
		p_east_north.add(tfInputTel);
		p_east_north.add(new JLabel("마일리지 현황"));
		p_east_north.add(taMileage);	
		p_east.add(p_east_north,BorderLayout.NORTH);

		p_east_center.setLayout(new FlowLayout());
		//p_east_center.add(tainfo);
		p_east_center.add(new JScrollPane(tainfo));
		p_east.add(p_east_center,BorderLayout.CENTER);
		
		tainfo.setText(a);
		
		
		

		//전체 영역
		setLayout(new GridLayout(1,2));
		add(p_east);
		p_east.setBorder(new TitledBorder("상세 이용 내역"));
		add(p_west);
		p_west.setBorder(new TitledBorder("이용 내역 분석"));
		p_west.add(p_chart);
	}
	
	void eventProc() { //tfInputTel-전화번호 입력 텍스트 필드 이벤트 
		ButtonEventHandler btnHandler = new ButtonEventHandler();
		tfInputTel.addActionListener(btnHandler);
	}

	class ButtonEventHandler implements ActionListener{
		public void actionPerformed(ActionEvent ev){
			Object o = ev.getSource();
			if(o==tfInputTel){ 
				try {
//					JOptionPane.showMessageDialog(null,"엔터이벤트발생");
					searchByTel();
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, e.getMessage());
				}
			}
		}	

		// 전화번호에 의한 검색
		public void searchByTel() throws Exception{
			
			// 1. 입력한 전화번호 얻어오기
			String tel = tfInputTel.getText();
			// 2. Model의 전화번호 검색메소드 selectByTel()  호출
			try {
				Customer cm = dao.selectByTel(tel);
				
				taMileage.setText(String.valueOf(cm.getCustmile()));
				tainfo.setText(cm.getCusDay());
				/**
				 *  차트 재 조회시 바뀌기 위한 곳 
				 */
				p_west.remove(p_chart);
				/**
				 *  차트 재 조회시 바뀌기 위한 곳 끝 
				 */
				
				/**
				 *  차트 종류 바꾸는 곳 
				 */
				p_chart = pieChart();
//				p_chart = BarChart();
				/**
				 *  차트 종류 바꾸는 곳 끝 
				 */
				
				
				/**
				 *  차트 재 조회시 바뀌기 위한 곳 
				 */
				p_west.add(p_chart);
				/**
				 *  차트 재 조회시 바뀌기 위한 곳 끝 
				 */
				
			}catch (Exception e) {
				System.out.println("Search FAIL"+e.getMessage());
			}

		}
	}

	public JPanel pieChart() {
//		String sql = "SELECT m.menuname , sum(o.ocount) as ocount "
		String sql = "SELECT m.menuname as name , sum(o.ocount) as value "
		 + " FROM customer c"
		 + " inner join order_cus o on c.ctel=o.ctel "
		 + " inner join menu m on o.menuno = m.menuno" 
		 + " WHERE c.ctel= '" + tfInputTel.getText() +"'"
		 + " group by m.menuname";
		     
		PieChart chart = new PieChart();
		
		try {
			chart.setTitle("이용내역분석");
			chart.dbSql_Excute(sql);	
		}catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		return chart.createPieChartPanel();
	}
	/*
	public JPanel BarChart() {
		String sql = "SELECT m.menuname , sum(o.ocount) as ocount "
//		String sql = "SELECT m.menuname as name, sum(o.ocount) as value "
				+ " FROM customer c"
				+ " inner join order_cus o on c.ctel=o.ctel "
				+ " inner join menu m on o.menuno = m.menuno" 
				+ " WHERE c.ctel= '" + tfInputTel.getText() +"'"
				+ " group by m.menuname";
		
		BarChart chart = new BarChart();
		
		try {
			chart.setTitle("이용내역분석");
			chart.dbSql_Excute(sql);	
		}catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		return chart.createPieChartPanel();
	}
	*/
}


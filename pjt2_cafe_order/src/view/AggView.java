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

	JButton bt_cal = new JButton("�Ϻ� ���"); //�� ��� ��ư
	JButton bt_menu = new JButton("���� ���"); //�� ��� ��ư
	
	JPanel p_eChart = new JPanel();
	JPanel p_wChart = new JPanel();
	JPanel p_east = new JPanel();
	JPanel p_west = new JPanel();
	
	CustomerDao dao;

	public AggView() {
		addLayout();  //���̾ƿ�
		eventProc();  //�̺�Ʈó��
		connectDB();  
	}

	public void connectDB(){
		try {
			dao = new CustomerModel();
		}catch(Exception ex) {
			JOptionPane.showMessageDialog(null,"����̹� ���� : "+ ex.getMessage());
		}
	}

	
	public void addLayout(){

		JPanel p_north = new JPanel();
		JPanel p_south = new JPanel();
		setLayout(new BorderLayout());
		add(p_north, BorderLayout.NORTH);
		
		p_north.add(bt_cal);  // �Ϻ� ��� ��ư
		p_north.add(bt_menu); // ���� ��� ��ư
		
		add(p_south, BorderLayout.CENTER);
		

		p_south.setLayout(new GridLayout(1,2));
		p_south.add(p_east);
		p_south.add(p_west);
		
	}
	void eventProc() { //��ư �̺�Ʈ
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
	//					JOptionPane.showMessageDialog(null,"�����̺�Ʈ�߻�");
					searchByTel();  //�Ϻ� ��Ʈ
				} else if(o==bt_menu){
					searchByTel2();  //���� ��Ʈ
				} 
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, e.getMessage());
			}
		}	

		// ��ȭ��ȣ�� ���� �˻�
		public void searchByTel() throws Exception{ //�Ⱓ�� ��Ʈ
			
			// 2. Model�� ��ȭ��ȣ �˻��޼ҵ� selectByTel()  ȣ��
			try {
				/**
				 *  ��Ʈ �� ��ȸ�� �ٲ�� ���� �� 
				 */
				p_east.remove(p_eChart);
				p_west.remove(p_wChart);
				/**
				 *  ��Ʈ �� ��ȸ�� �ٲ�� ���� �� �� 
				 */
				
				/**
				 *  ��Ʈ ���� �ٲٴ� �� 
				 */
				p_eChart = BarChart2();
				p_wChart = PieChart2();
				/**
				 *  ��Ʈ ���� �ٲٴ� �� �� 
				 */
				
				
				/**
				 *  ��Ʈ �� ��ȸ�� �ٲ�� ���� �� 
				 */
				p_east.add(p_eChart);
				p_west.add(p_wChart);
				/**
				 *  ��Ʈ �� ��ȸ�� �ٲ�� ���� �� �� 
				 */
				
			}catch (Exception e) {
				System.out.println("Search FAIL"+e.getMessage());
			}

		}
		
		public void searchByTel2() throws Exception{ //���ں� ��Ʈ
			
			// 2. Model�� ��ȭ��ȣ �˻��޼ҵ� selectByTel()  ȣ��
			try {
				/**
				 *  ��Ʈ �� ��ȸ�� �ٲ�� ���� �� 
				 */
				p_east.remove(p_eChart);
				p_west.remove(p_wChart);
				/**
				 *  ��Ʈ �� ��ȸ�� �ٲ�� ���� �� �� 
				 */
				
				/**
				 *  ��Ʈ ���� �ٲٴ� �� 
				 */
				
				p_eChart = BarChart();
				p_wChart = PieChart();
				
				/**
				 *  ��Ʈ ���� �ٲٴ� �� �� 
				 */
				
				
				/**
				 *  ��Ʈ �� ��ȸ�� �ٲ�� ���� �� 
				 */
				p_east.add(p_eChart);
				p_west.add(p_wChart);
				/**
				 *  ��Ʈ �� ��ȸ�� �ٲ�� ���� �� �� 
				 */
				
			}catch (Exception e) {
				System.out.println("Search FAIL"+e.getMessage());
			}
			
		}
	}

	public JPanel PieChart() { //������Ʈ1. 5�� �޴��� ����
		
		String sql = " SELECT M.MENUNAME AS name , COUNT(*) AS value "
		+ " FROM (SELECT * FROM ORDER_CUS WHERE SUBSTR(OTIME,6,2)=to_char(sysdate,'mm')) O, MENU M "
		+ " WHERE O.MENUNO = M.MENUNO " 
		+ " GROUP BY M.MENUNAME ";
				
		     
		PieChart chart = new PieChart();
		
		try {
			chart.setTitle("�̿볻���м�");
			chart.dbSql_Excute(sql);	
		}catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		return chart.createPieChartPanel();
	}
	
	public JPanel BarChart() { //����Ʈ1. ���� �ֹ� �Ǽ�
		String sql = " SELECT substr(otime,3,5) AS name, SUM(ocount) AS value " +
				" FROM order_cus " + 
				" GROUP BY substr(otime,3,5) " +
				" ORDER BY substr(otime,3,5) ";
		
		BarChart chart = new BarChart();
		
		try {
			chart.setTitle("�̿볻���м�");
			chart.dbSql_Excute(sql);	
		}catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		return chart.createPieChartPanel();
	}
	

	public JPanel PieChart2() { //������Ʈ2. �޴��̸��� ����
	
		String sql = " SELECT M.MENUNAME AS name, COUNT(*) AS value "
				+ " FROM (SELECT * FROM ORDER_CUS WHERE SUBSTR(OTIME,9,2)=to_char(sysdate,'dd')) O, MENU M "
				+ " WHERE O.MENUNO = M.MENUNO "
				+ " GROUP BY M.MENUNAME ";
		     
		PieChart chart = new PieChart();
		
		try {
			chart.setTitle("�̿볻���м�");
			chart.dbSql_Excute(sql);	
		}catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		return chart.createPieChartPanel();
	}
	
	public JPanel BarChart2() { //����Ʈ2. �Ϻ� �ֹ� �Ǽ�
		String sql = " SELECT substr(otime,3,8) AS name, SUM(ocount) AS value " +
				" FROM order_cus " +
				" GROUP BY substr(otime,3,8) " +
				" ORDER BY substr(otime,3,8) ";
		
		BarChart chart = new BarChart();
		
		try {
			chart.setTitle("�̿볻���м�");
			chart.dbSql_Excute(sql);	
		}catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		return chart.createPieChartPanel();
	}
	
	public JPanel BarChart3() { //����Ʈ3. �޴��� �ֹ�����
//		String sql = " SELECT substr(otime,3,8) AS MONTH, SUM(ocount) AS tot " +
		String sql = " SELECT COUNT(*) AS VALUE , M.menuname AS NAME " 
				+ " FROM order_cus o, menu M "
				+ " WHERE o.menuno = M.menuno "
				+ " GROUP BY menuname "
				+ " ORDER BY COUNT(*) DESC";
		
		BarChart chart = new BarChart();
		
		try {
			chart.setTitle("�̿볻���м�");
			chart.dbSql_Excute(sql);	
		}catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		return chart.createPieChartPanel();
	}
	
}

package view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JTable;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.TitledBorder;

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
	CustomerDao dao;


	String a = "------------------- 이용 내역 -------------------"+'\n'+ '\n'
			+ " 이용날짜"+'\t'+'\t'+"메뉴"+'\t'+"수량"+'\t'+"금액"+'\t' +"이용형태"+'\t'+'\n'+ '\n'+ '\n';
	//	tainfo.setText("---------------- 이용 내역 ----------------+'\n'+ '\n'"
	//			+ "이용날짜"+'\t'+"메뉴"+'\t'+"수량"+'\t'+"금액"+'\t' +"이용형태"+'\n'+ '\n'+ '\n');

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
		JPanel p_west = new JPanel();	
		JPanel p_east = new JPanel();

		p_east.setLayout(new FlowLayout());
		JPanel p_east_north = new JPanel();
		JPanel p_east_center = new JPanel();

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
					//JOptionPane.showMessageDialog(null,"엔터이벤트발생");
					searchByTel();
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, e.getMessage());
				}
			}
		}	

		// 전화번호에 의한 검색
		public void searchByTel() throws Exception{

			tainfo.setText(a);
			// 1. 입력한 전화번호 얻어오기
			String tel = tfInputTel.getText();
			ArrayList<Customer> cusInfo = new ArrayList<Customer>();
			// 2. Model의 전화번호 검색메소드 selectByTel()  호출
			try {
				cusInfo= dao.selectByTel(tel);
				if(cusInfo.isEmpty()) {
					JOptionPane.showMessageDialog(null,"고객정보가 없습니다.");
				}else {

					for(Customer info : cusInfo) {
						tainfo.append( info.toString());
						taMileage.setText(String.valueOf(info.getCustmile()));
						
						//System.out.println(info.toString());
					}
				}





			}catch (Exception e) {
				System.out.println("Search FAIL"+e.getMessage());
			}

		}
	}

}


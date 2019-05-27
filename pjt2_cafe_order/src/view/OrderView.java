package view;

import java.awt.BorderLayout;
import java.awt.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import javax.swing.*;
//import javax.swing.JButton;
//import javax.swing.JComboBox;
//import javax.swing.JLabel;
//import javax.swing.JOptionPane;
//import javax.swing.JPanel;
//import javax.swing.JScrollPane;
//import javax.swing.JTable;
//import javax.swing.JTextArea;
//import javax.swing.JTextField;
//import javax.swing.UIManager;
import javax.swing.border.TitledBorder;
import javax.swing.table.AbstractTableModel;

import model.dao.OrderModel;

import model.vo.Order;



public class OrderView extends JPanel {
	//member field 
	JTextField tfOrderTel, tfMiles, tfCupon;
	JButton[] btMenu = new JButton[9];
	//,bPlatW, bCarMa, bCarpu, bIceCarpu, bAmerica, bIceAmerica, bChoco,bGreenTea, bSand;
	JButton bTel, bOrd, bCan, bUsage, bApply, bPay;

	JTextArea ta;
	JTable tableOrder;
	OrderTableModel tbModelOrder;

	JLabel labelMil, labelCupon, labelTotal, labelTotalWrite;
	JComboBox MorT;
	ArrayList<ArrayList> Olist = new ArrayList();
	//JTable tableOrder;     //view 역할
	//OrderTableModel tbModelOrder;   //model 역할



	//********
	//모델 클래스 변수 선언
	OrderModel ord;
	String[] mlist;
	int[] j = new int[btMenu.length];
	int mile_cnt = 0;
	int cou_cnt = 0;
	int btO_cnt = 0;

	//#########################
	//constructor method
	public OrderView() {        
		addLayout();  //화면설계
		connectDB();
		eventProc();  //DB연결
	}




	private void connectDB() {  //DB연결
		try {
			ord = new OrderModel();
		}catch(Exception ex) {
			JOptionPane.showMessageDialog(null, "드라이버오류:"+ex.getMessage());
		}
	}

	public  void eventProc() {
		MyButtonHdlr myhdlr = new MyButtonHdlr();

		tfOrderTel.addActionListener(myhdlr);
		bTel.addActionListener(myhdlr);
		MorT.addActionListener(myhdlr);
		bOrd.addActionListener(myhdlr);
		bCan.addActionListener(myhdlr);
		bUsage.addActionListener(myhdlr);
		bApply.addActionListener(myhdlr);
		bPay.addActionListener(myhdlr);

		for (int i = 0; i < btMenu.length; i++) {
			btMenu[i].addActionListener(myhdlr);
		}
		//

		tableOrder.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {

				// 해당 열이 선택 될 경우,
				int row = tableOrder.getSelectedRow();
				ArrayList cancelMenu = new ArrayList();
		
				cancelMenu.add((String)tableOrder.getValueAt(row, 0));
				cancelMenu.add((String)tableOrder.getValueAt(row, 1));
				cancelMenu.add((String)tableOrder.getValueAt(row, 2));
				JOptionPane.showMessageDialog(null, cancelMenu+"를 취소합니다.");

				cancelSelected(cancelMenu);

			}
		});

	}
	class MyButtonHdlr implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			Object o = e.getSource();



			//버튼 별 이벤트 
			//전화번호 엔터.검색버튼 클릭시
			if(o==tfOrderTel || o==bTel){  
				System.out.println("이벤트확인");

				cancelSelect();
				cusInfo();


				// 콤보박스 선택 시,매장/테이크아웃 정보 읽어서 db로 보내주기.
			}else if(o==bOrd) {
				 btO_cnt++;
				 
				if(tfOrderTel.getText().contentEquals("")) {
					JOptionPane.showMessageDialog(null, "전화번호를 입력하세요");
				}else {
					getSum();
				}
				getSum();
				//System.out.println("주문이벤트확인");

				// 취소 클릭 시 전체 취소
			}else if(o==bCan) {

				//배열 초기화
				cancelSelect();


				//System.out.println("취소이벤트확인");

				//마일리지 사용 시 전체 가격에서 차감, 고객 마일리지 차감	
			}else if(o==bUsage) {
				//System.out.println("사용이벤트");

				//한번만 적용되도록.
				mile_cnt++;
				if(mile_cnt == 1) {
					minusMile();
				}else if( mile_cnt > 1){
					JOptionPane.showMessageDialog(null, "이미 적용되었습니다.");
				}



				// 쿠폰번호 적용 시 할인율 받아와서 가격 적용	
			}else if(o==bApply) {
				//System.out.println("적용이벤트확인");
				cou_cnt++;

				if(tfCupon.getText()=="") {
					JOptionPane.showMessageDialog(null, "쿠폰번호를 확인하세요");
				}

				if (cou_cnt == 1) {
					getCoupon();
				}else if( mile_cnt > 1){
					JOptionPane.showMessageDialog(null, "이미 적용되었습니다.");
				}


				//결제금액 적용 시 최종 결제 가격 db전송 
			}else if(o==bPay) {

				OrderSelectedItem();
				updateMile();
				clearAll();


				for (int i = 0; i < btMenu.length; i++) {
					j[i]=0;
				}

				//System.out.println("결제이벤트확인");
			}

			// 메뉴버튼 클릭 시 Db에서 가격정보를 얻어오고, 주문리스트 만들어지면서 주문내역창에 입력된다
			for (int i = 0; i < btMenu.length; i++) {

				if (o==btMenu[i]) {
					//버튼 클릭 수 카운트
					j[i]++;

					int[] stockCnt = checkStock() ;


					if (j[i]>stockCnt[i]) {
						JOptionPane.showMessageDialog(null, "수량이 부족합니다.");
						j[i]--;
					}else if(j[i]<=stockCnt[i]) {
						if(j[i] == 1) {
							Olist.add(getSelectedMenu(i,j[i]));

							//두번이상 클릭되면 앞에 주문내역리스트에서 제외하고 추가된 수량으로 다시 입력해준다.
						}else if(j[i] >1) {
							Olist.remove(getSelectedMenu(i,j[i]-1));
							Olist.add(getSelectedMenu(i,j[i]));
						}
					}

					if( btO_cnt >1) {
						getSum();
					}


					//System.out.println(Olist);

					//System.out.println("메뉴이벤트확인");

					tbModelOrder.data= Olist;
					tbModelOrder.fireTableDataChanged();
					//System.out.println(((String)Olist.get(0).get(1)).charAt(0));

				}
			}


		}

	}



	//화면설계 메소드
	public void addLayout() {
		//멤버변수의 객체 생성
		try {
			UIManager.setLookAndFeel("com.birosoft.liquid.LiquidLookAndFeel");
		} catch (Exception e) {	} 

		//textfield 들

		tfOrderTel = new JTextField(15);
		tfMiles = new JTextField(100);
		tfCupon = new JTextField(100);

		//왼쪽 버튼 9 개

		bTel = new JButton("입력");

		//String[] mlist;
		try {
			ord =new OrderModel();
			mlist = ord.getMname();
			for (int i = 0; i < btMenu.length; i++) {

				btMenu[i] = new JButton(mlist[i],new ImageIcon("src/img/menu/"+(i+1)+".jpg"));
				btMenu[i].setVerticalTextPosition(JButton.BOTTOM); //text 위치 수직방향 설정
				btMenu[i].setHorizontalTextPosition(JButton.CENTER);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


		//버튼 나머지
		bOrd = new JButton("주문");
		bCan = new JButton("취소");
		bUsage = new JButton("사용");
		bApply = new JButton("적용");
		bPay = new JButton("결제");

		//콤보박스
		String []k = {"매장", "테이크아웃"};
		MorT = new JComboBox(k);
		//텍스트area
		ta = new JTextArea(); 

		tbModelOrder = new OrderTableModel();
		tableOrder = new JTable(tbModelOrder);

		//라벨들
		labelMil = new JLabel("마일리지");
		labelCupon = new JLabel("쿠폰번호");
		labelTotal = new JLabel("총금액");
		labelTotalWrite = new JLabel();
		labelTotalWrite.setHorizontalAlignment(JLabel.RIGHT);


		//*************************************
		// 화면 구성



		//왼쪽

		JPanel order_west = new JPanel();
		order_west.setLayout(new BorderLayout());
		order_west.setBorder(new TitledBorder("주문창"));

		//왼쪽 위 

		JPanel order_west_n = new JPanel();
		order_west_n.add(new JLabel("전화번호"));
		order_west_n.add(tfOrderTel);
		order_west_n.add(bTel);
		order_west.add(order_west_n, BorderLayout.NORTH);

		//왼쪽 센터

		JPanel order_west_c = new JPanel();
		order_west_c.setLayout(new GridLayout(3,3));
		for (int i = 0; i < btMenu.length; i++) {
			order_west_c.add(btMenu[i]);
		}

		order_west.add(order_west_c, BorderLayout.CENTER);

		//왼쪽 아래

		JPanel order_west_s = new JPanel();
		order_west_s.add(MorT);
		order_west_s.add(bOrd);
		order_west_s.add(bCan);
		order_west.add(order_west_s, BorderLayout.SOUTH);


		//오른쪽

		JPanel order_east = new JPanel();
		order_east.setLayout(new BorderLayout());

		//오른쪽  위 

		JPanel order_east_c = new JPanel();
		order_east_c.setBorder(new TitledBorder("주문 내역 확인"));
		//order_east_c.add(ta); 
		order_east_c.add(new JScrollPane(tableOrder));
		order_east.add(order_east_c, BorderLayout.CENTER);

		//오른쪽 밑
		JPanel order_east_s = new JPanel();
		order_east_s.setBorder(new TitledBorder("결   제"));
		order_east_s.setLayout(new BorderLayout());

		//오른쪽 밑-센터
		JPanel order_east_s_c = new JPanel();
		order_east_s_c.setLayout(new GridLayout(3,1));



		JPanel order_east_s_c_1 = new JPanel();
		order_east_s_c_1.setLayout(new GridLayout(1,3));
		order_east_s_c_1.add(labelMil);
		order_east_s_c_1.add(tfMiles);
		order_east_s_c_1.add(bUsage);


		JPanel order_east_s_c_2 = new JPanel();
		order_east_s_c_2.setLayout(new GridLayout(1,3));
		order_east_s_c_2.add(labelCupon);
		order_east_s_c_2.add(tfCupon);
		order_east_s_c_2.add(bApply);

		JPanel order_east_s_c_3 = new JPanel();
		order_east_s_c_3.setLayout(new GridLayout(1,2));
		order_east_s_c_3.add(labelTotal);
		order_east_s_c_3.add(labelTotalWrite);

		order_east_s_c_3.setBorder(new TitledBorder(""));
		order_east_s_c.add(order_east_s_c_1);
		order_east_s_c.add(order_east_s_c_2);
		order_east_s_c.add(order_east_s_c_3);

		order_east_s.add(order_east_s_c, BorderLayout.CENTER);

		JPanel order_east_s_s = new JPanel();
		order_east_s_s.setLayout(new BorderLayout());
		order_east_s_s.add(bPay, BorderLayout.EAST);

		order_east_s.add(order_east_s_s, BorderLayout.SOUTH);

		order_east.add(order_east_s, BorderLayout.SOUTH);
		//전체영역에 붙이기
		//전체 c(1,2)로 나눔

		setLayout(new GridLayout(1,2));
		add(order_west);
		add(order_east);
	}





	// 재고 수량 체크 .
	public int[] checkStock() {

		int[] stockCnt=new int[btMenu.length];
		Order cnt = new Order();
		try {
			for (int i = 0; i < btMenu.length; i++) {
				cnt = ord.getSelectedInfo(i+1);
				stockCnt[i] = cnt.getmSCount();
				System.out.println(stockCnt[i]);
			}

		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "재고수량 조회 불가 :"+e.getMessage());
		}
		return stockCnt;

	}



	// 주문 버튼 클릭 시  , 주문정보가 db로 넘어가고, 총 가격이 계산된다.
	public void OrderSelectedItem() {

		SimpleDateFormat DateFormat = new SimpleDateFormat ( "yyyy.MM.dd HH:mm:ss", Locale.KOREA );
		SimpleDateFormat OrdernoFormat = new SimpleDateFormat ( "yyyyMMddHHmmss", Locale.KOREA );
		Date currentTime = new Date ();

		ArrayList<Order> list = new ArrayList<Order>();


		String cno = tfCupon.getText();

		int totalPrice = Integer.parseInt(labelTotalWrite.getText());

		try {
			for (ArrayList<String> sub_list:Olist) {


				Order or = new Order(); 

				or.setmOrderno(OrdernoFormat.format ( currentTime ));
				or.setoTime(DateFormat.format ( currentTime));
				//or.setoTime("2019.02.26 17:40:58");
				or.setOtype((String)MorT.getSelectedItem());
				or.setCtel(tfOrderTel.getText());
				or.setMenuName(sub_list.get(0));

				int a = (sub_list.get(1)).charAt(0);
				//System.out.println((sub_list.get(1)).charAt(0));

				or.setmPrice(Integer.parseInt((sub_list.get(2)).replaceAll("[^0-9]", "")));
				//System.out.println((sub_list.get(2)).replaceAll("[^0-9]", ""));
				for (int i = 0; i < btMenu.length; i++) {
					if(btMenu[i].getText().equals( sub_list.get(0))) {
						or.setoMenuNo(i+1);
						or.setmOcount(j[i]);
					}
				}


				list.add(or);




			}

			//System.out.println(list.get(1));

			ord.insertOrList(list,cno,totalPrice);
			ord.modifyMenuCnt(list);



			//			ord.modifyMenuCnt(list,1);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//return OrdernoFormat.format ( currentTime );

	}




	// 메뉴별 수량 가격.
	public ArrayList<String> getSelectedMenu(int i, int j) {
		//String oList = "";
		ArrayList<String> oList_sub = new ArrayList<String>();
		try {
			ord = new OrderModel();
			Order menuInfo = new Order();

			//String selectedMenu = btMenu[i].getText();

			menuInfo= ord.getSelectedInfo(i+1);
			//menuInfo.setmOcount(j);

			oList_sub.add(menuInfo. getMenuName());
			oList_sub.add(j+"개");
			oList_sub.add(menuInfo.getmPrice()*j +"원");


			//System.out.println(menuInfo.toString());

			//oList = menuInfo.toString();


		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return  oList_sub;



	}

	//테이블에서 선택한 주문 list 삭제하기
	public void cancelSelected(ArrayList cancelList) {

		Olist.remove(cancelList);
		// 화면 초기화
		tbModelOrder.fireTableDataChanged();
		System.out.println(Olist);
		for (int i = 0; i < btMenu.length; i++) {
			if(cancelList.get(0).equals(btMenu[i].getText())) {
				j[i]=0;
			}

		}
		getSum();


	}

	//취소버튼 클릭 시 해당 행 주문리스트에서 삭제
	public void cancelSelect() {

		Olist.clear(); // 리스트 초기화.
		tbModelOrder.data= Olist; // 화면 초기화
		tbModelOrder.fireTableDataChanged();
		labelTotalWrite.setText(null);
		for (int i = 0; i < btMenu.length; i++) {
			j[i]=0;
		}
		

	}

	//화면 초기화
	public void clearAll() {
		cancelSelect();
		tfOrderTel.setText(null);
		tfMiles.setText(null);
		tfCupon.setText(null);
		mile_cnt = 0;
		cou_cnt = 0;
	}

	//db에서 전화번호 list를 받아와서 비교, 작업.
	public void cusInfo() {
		String tel = tfOrderTel.getText();
		try {
			int[] result = ord.getInfoBytel(tel);

			if(result[0]==0) {
				joinCus();
				cusInfo();
			}
			else {
				tfMiles.setText(String.valueOf(result[1]));
			}


		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("마일리지 조회 실패");
		}
	}

	//전화번호 기존 존재하지 않는 경우 추가.
	public void joinCus() {
		String tel = tfOrderTel.getText();

		try {
			ord.joinCus(tel);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// 마일리지 db에 업데이트
	public void updateMile() {

		String ctel=tfOrderTel.getText();

		int minus_mile = Integer.parseInt(tfMiles.getText()) ;
		int plus_mile = Integer.parseInt(labelTotalWrite.getText()) /10;


		try {
			ord.upDateCusmile(plus_mile,minus_mile,ctel);
			JOptionPane.showMessageDialog(null, plus_mile+"점이 추가 적립되었습니다." );
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "마일리지 적용 실패:" + e.getMessage() );
		}

	}

	// 사용 마일리지 최종가격에 반영
	public void minusMile() {

		int minus_mile = Integer.parseInt(tfMiles.getText()) ;
		int price = Integer.parseInt(labelTotalWrite.getText());
		labelTotalWrite.setText(String.valueOf(price-minus_mile));
	}

	// 쿠폰 할인율 받아와서 최종가격에 반영
	public void getCoupon() {
		String coupno = tfCupon.getText();
		int total_price = Integer.parseInt(labelTotalWrite.getText());
		try {
			int percent = ord.getDiscount(coupno);
			labelTotalWrite.setText(String.valueOf(total_price * (100-percent)/100));
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "쿠폰 적용 실패 : " + e.getMessage());

		}
	}

	// 총 합산 가격 구하기.
	public void getSum()
	{
		int sum = 0;
		for(int i = 0; i < tableOrder.getRowCount(); i++)
		{
			String a =((String)(tableOrder.getValueAt(i, 2))).replaceAll("[^0-9]", "");
			sum = sum + Integer.parseInt(a);
		}

		labelTotalWrite.setText(Integer.toString(sum));
	}





	//테이블 모델.
	class OrderTableModel extends AbstractTableModel{
		ArrayList data = new ArrayList();


		String [] columnNames={"주문메뉴","수량","가격"};


		@Override
		public int getColumnCount() {
			return columnNames.length;
		}

		@Override
		public int getRowCount() {
			return data.size(); 
		}

		@Override
		public Object getValueAt(int row, int col) {
			ArrayList temp = (ArrayList)data.get( row );
			return temp.get( col ); 
		}
		public String getColumnName(int col){
			return (String) columnNames[col];
		}

	}



}



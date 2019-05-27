package view;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.AbstractTableModel;

import model.dao.StockModel;
import model.vo.Stock;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.*;



public class StockView   extends JPanel {

	//멤버변수 선언
	JTextField tfStOrderNo;
	JLabel[] lbstMenulist =new JLabel[9];
	JTextField [] tfStOrderCnt = new JTextField[9] ;
	JButton btstOrder, btstCancel, btstModify;
	JComboBox cbstSearch;

	ButtonGroup buttons = new ButtonGroup();
	JRadioButton rbstOrderSearch,rbStSearch;
	JTable tableStock;
	//JTable tableStockOrder ;

	StockTableModel tbModelStock;
	StockOrderTableModel tbModelStockOrder;
	
	StockModel db;

	
	

	String[] mlist;
	ArrayList<String> SorderNoList;


	public void resetCbList() {
		
		try {
			db =new StockModel();
			//재고리스트 불러오기
			mlist = db.getMname();
			
			//주문번호 리스트 불러오기
			SorderNoList = db.getOrderNoList();
			
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		
	}




	public StockView() {
		addLayout(); 	// 화면설계
		initStyle();
		eventProc();
		connectDB();

	}

	//배치 레이아웃
	public void addLayout() {
		try{

			UIManager.setLookAndFeel("com.birosoft.liquid.LiquidLookAndFeel");
			// UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
		}catch(Exception e){}

		mlist = new String[9];
		SorderNoList = new ArrayList<String>();


		try {
			resetCbList();


			tfStOrderNo = new JTextField("주문 시 자동생성됩니다.");
			btstOrder = new JButton("주문");
			btstCancel = new JButton("취소");
			btstModify = new JButton("수정");
			cbstSearch = new JComboBox();
			rbstOrderSearch = new JRadioButton("주문이력조회");
			rbStSearch = new JRadioButton("재고내역조회");

			//버튼 하나만 선택되도록 그룹핑.
			buttons.add(rbstOrderSearch);
			buttons.add(rbStSearch);


			for (int i = 0; i < mlist.length; i++) {
				lbstMenulist[i]= new JLabel(mlist[i]);
				tfStOrderCnt[i] = new JTextField();
			}
			tbModelStock = new StockTableModel();
			tbModelStockOrder = new StockOrderTableModel();
			tableStock = new JTable(tbModelStockOrder);
			
		
			
			

		} catch (Exception e) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(null, "화면구성실패" + e.getMessage());
		}

		btstOrder.setPreferredSize(new Dimension(20,80)); 
		btstCancel.setPreferredSize(new Dimension(20,80)); 
		tfStOrderNo.setPreferredSize(new Dimension(20,60)); 

		//화면 구성


		//왼쪽 영역
		JPanel p_west = new JPanel();
		p_west.setBorder(new TitledBorder("입고요청"));
		p_west.setLayout(new BorderLayout());

		//왼쪽-북쪽
		JPanel p_west_n = new JPanel();
		p_west_n.setLayout(new GridLayout(1,2));

		p_west_n.add(new JLabel("입고주문번호"));
		p_west_n.add(tfStOrderNo);


		//왼쪽 - 센터
		JPanel p_west_c = new JPanel();
		p_west_c.setLayout(new GridLayout(9,2));
		for (int i = 0; i < 9; i++) {
			p_west_c.add(lbstMenulist[i]);
			p_west_c.add(tfStOrderCnt[i]);

		}

		//왼쪽 - 남쪽

		JPanel p_west_s = new JPanel();
		p_west_s.setLayout(new GridLayout(1,2));
		p_west_s.add(btstOrder);
		//p_west_s.add(btstModify);
		p_west_s.add(btstCancel);

		p_west_n.setBorder(new TitledBorder(""));
		p_west_c.setBorder(new TitledBorder(""));
		p_west_s.setBorder(new TitledBorder(""));

		//왼쪽 영역 붙이기	

		p_west.add(p_west_n,BorderLayout.NORTH);
		p_west.add(p_west_c,BorderLayout.CENTER);
		p_west.add(p_west_s,BorderLayout.SOUTH);


		//오른쪽 영역
		JPanel p_east = new JPanel();
		p_east.setBorder(new TitledBorder("조회"));
		p_east.setLayout(new BorderLayout());	
		//오른쪽 -위
		JPanel p_east_n = new JPanel();
		p_east_n.setLayout(new GridLayout(2,1));
		//p_east_n.setBorder(new TitledBorder(""));

		//오른쪽 - 위- 위
		JPanel p_east_n_n = new JPanel();
		p_east_n_n.setLayout(new GridLayout(1,2));
		p_east_n_n.setBorder(new TitledBorder(""));
		p_east_n_n.add(rbstOrderSearch);

		p_east_n_n.add(rbStSearch);

		//오른쪽 - 위- d아래
		JPanel p_east_n_s = new JPanel();
		p_east_n_s.setLayout(new GridLayout(1,3));
		p_east_n_s.add(new JLabel("조회")); // 조건따라 변경
		p_east_n_s.add(cbstSearch);	
		p_east_n_s.setBorder(new TitledBorder(""));
		//p_east_n_s.


		p_east_n.add(p_east_n_n);
		p_east_n.add(p_east_n_s);

		//오른쪽 센터
		JPanel p_east_c = new JPanel();
		//p_east_c.setLayout(card=new CardLayout());
		p_east_c.add(new JScrollPane(tableStock));
		//p_east_c.add(new JScrollPane(tableStockOrder),"stockOrder");
		

		//오른쪽 영역 붙이기	

		p_east.add(p_east_n,BorderLayout.NORTH);
		p_east.add(p_east_c,BorderLayout.CENTER);



		//전체 영역
		setLayout(new GridLayout(1,2));
		add(p_west);
		add(p_east);


	}
	
	//화면 초기화
	public void clearData() {
		//tfStOrderNo.setText(null);

		for (int i = 0; i < tfStOrderCnt.length; i++) {
			tfStOrderCnt[i].setText(null);
		}
	}
	// 초기 설정
	public void initStyle() {
		// TODO Auto-generated method stub
		//rbstOrderSearch.setSelected(true);
		//tfStOrderNo.enable();
		rbstOrderSearch.setSelected(true);
		setComboSt(SorderNoList.toArray(new String[SorderNoList .size()]));
		
		tableStock.setModel(tbModelStockOrder); //테이블 모델 변경.
		tbModelStockOrder.fireTableDataChanged(); //변경신호
	}

	// 이벤트 처리
	public void eventProc() {
		MyHdlr myhdlr = new MyHdlr();

		tfStOrderNo.addActionListener(myhdlr);
		btstModify.addActionListener(myhdlr);
		btstOrder.addActionListener(myhdlr);
		btstCancel.addActionListener(myhdlr);
		rbstOrderSearch.addActionListener(myhdlr);
		rbStSearch.addActionListener(myhdlr);
		cbstSearch.addActionListener(myhdlr);
	}


//이벤트 핸들러
	class MyHdlr implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent ev) {
			Object o = ev.getSource();
			
			//주문번호 입력 시 주문내역 조회.
			if(o==tfStOrderNo){  
				//System.out.println("이벤트확인");
				clearData();
				getStOrderList();
				

			}
			
			//재고 주문버튼 이벤트 -- 버큰 클릭시 db에 주문내역 저장.
			else  if(o==btstOrder){
				//System.out.println("이벤트확인");
				
//				if(tfStOrderNo.getText().equals("주문 시 자동생성됩니다.") || tfStOrderNo.getText()==null)
//				{ 
				    tfStOrderNo.setText(null); 
					String orderNo = orderStock();   
					clearData();
					
					resetCbList();
					setComboSt(SorderNoList.toArray(new String[SorderNoList .size()]));
					showData();
					JOptionPane.showMessageDialog(null, orderNo+"번으로 주문완료되었습니다.");
//				} else {
//					JOptionPane.showMessageDialog(null, "이미 완료된 주문입니다.");
//				}


			}
			//취소버튼
			else if(o==btstCancel){ 
				
				cancelStock(); 
				tfStOrderNo.setText(null);
				clearData();
				resetCbList();
				setComboSt(SorderNoList.toArray(new String[SorderNoList .size()]));
				
				//showData();

			//주문이력조회라디오 버튼
			}else if(o==rbstOrderSearch){ 
				//이름검색
					//
					setComboSt(SorderNoList.toArray(new String[SorderNoList .size()]));
										
					tableStock.setModel(tbModelStockOrder); //테이블 모델 변경.
					tbModelStockOrder.fireTableDataChanged(); //변경신호
					System.out.println("콤보이벤트확인1");
				
			// 재고내역 조회.	
			}else if(o==rbStSearch){
				
				setComboSt(mlist); // 재고내역 버튼 클릭시 재료명으로 콤보박스 리스트 완성
				tableStock.setModel(tbModelStock); //테이블 모델 변경.
				tbModelStock.fireTableDataChanged(); //변경신호
				//System.out.println("콤보이벤트확인2");
			
				// 콤보박스 이벤트	
			}else if( o==cbstSearch){
				if( cbstSearch.getItemCount()==0) return;
				//System.out.println("콤보이벤트확인333333333");
				showData(); // 콤보박스 선택내용에 따른 Jtable 값 불러오기.
				clearData(); //주문창 데이터 삭제
				tfStOrderNo.setText(null);
				
				// 콤보박스의 내용이 주문내역에 해당할 경우, 왼쪽 주문내역에도 숫자가 나타나게한다. 취소를 위해서.
				if(SorderNoList.contains(cbstSearch.getSelectedItem())) {
					
					tfStOrderNo.setText((String)cbstSearch.getSelectedItem());
					getStOrderList();
				
				}
			
				


				//System.out.println("이벤트확인3");
			}
			//cbstSearch.addActionListener(this) ;

		}


	}
	// 디비 연결

	public void connectDB() {
		try {
			db=new StockModel();
			//System.out.println("연결성공");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("연결실패" + e.getMessage());
		}

	}
	
	// 콤보박스 리스트에 리스트 셋팅하기.
	public void setComboSt(String[] cb_list) {

		cbstSearch.removeAllItems();
		cbstSearch.addItem("전체");
		for (int i = 0; i < cb_list.length; i++) {
			cbstSearch.addItem(cb_list[i]);
		}

	
	}

	//Jtable 내용 업데이트 --db에서 주문내역 및 재고 내역 불러오기.
	public void showData() {
		int optSearch=1;

		String opt = (String)cbstSearch.getSelectedItem();
		
		try {
			if(rbstOrderSearch.isSelected()) {
				optSearch = 1;
				tbModelStockOrder.data= db.getSearchList(opt,optSearch);
				tbModelStockOrder.fireTableDataChanged();
			}else if(rbStSearch.isSelected()) {
				optSearch = 2;
				tbModelStock.data= db.getSearchList(opt,optSearch);
				tbModelStock.fireTableDataChanged();
			}


//			tbModelStockOrder.data= db.getSearchList(opt,optSearch);
//			tbModelStockOrder.fireTableDataChanged();
			//내용이 바꼈다는 사실을 화면쪽에 정보를 줘야함.
			System.out.println(optSearch);

		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "내역조회실패"+e.getMessage());
			e.printStackTrace();
		}

	}
	
	public void showData(String opt,int optSearch) {
		
				try {
//					tbModelStockOrder.data= db.getSearchList(opt,optSearch);
//					tbModelStockOrder.fireTableDataChanged();
					
					tbModelStock.data= db.getSearchList(opt,optSearch);
					tbModelStock.fireTableDataChanged();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
	}
	// 주문번호로 주문 취소하기.
	public void cancelStock() {
		String sOrderno = tfStOrderNo.getText();
		try {
			ArrayList list = new ArrayList();
			list = db.getStOrderCnt(sOrderno);

			db.modifyMenuCnt(list,2);
			db.deleteSt(sOrderno);
			JOptionPane.showMessageDialog(null, "취소되었습니다");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(null, "삭제실패");
		}
		return;

	}
	
	// 주문내역 읽어서 db에 저장하기
	public String orderStock() {

		//주문내역 db에 업데이트 할 데이터 만들기.
		SimpleDateFormat DateFormat = new SimpleDateFormat ( "yyyy.MM.dd HH:mm:ss", Locale.KOREA );
		SimpleDateFormat OrdernoFormat = new SimpleDateFormat ( "yyyyMMddHHmmss", Locale.KOREA );
		Date currentTime = new Date ();

		ArrayList<Stock> list = new ArrayList<Stock>();

		try {
			for (int i = 0; i < tfStOrderCnt.length; i++) {

				if(!(tfStOrderCnt[i].getText().equals("") || tfStOrderCnt[i].getText().equals("0")) ) {

					Stock vo = new Stock(); 
					vo.setMenuNo(i+1);
					vo.setsCount(Integer.parseInt(tfStOrderCnt[i].getText()));
					vo.setsTime(DateFormat.format ( currentTime));
					vo.setsOrderNo(OrdernoFormat.format ( currentTime ));
					list.add(vo);

				}		
			}

			//리스트 만든 것을 list의 인자로 넣어서, db에 넣기
			db.insertSt(list);
			//리스트만든 것을 list 인자로 넣어서, db의 재고수량 업데이트 하기. 1은 증가 의미.
			db.modifyMenuCnt(list,1);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return OrdernoFormat.format ( currentTime );

	}

	// 주문리스트에 데이터 채우기.
	public void getStOrderList() {
		String sOrderno = tfStOrderNo.getText();
		
		try {
			ArrayList<Stock> orderCnt =new ArrayList<Stock>();
			orderCnt = db.getStOrderCnt(sOrderno);

			if(orderCnt.isEmpty()) {
				JOptionPane.showMessageDialog(null, "주문번호를 확인하세요");
			}

			else{
				for(Stock vo: orderCnt) {
					tfStOrderCnt[vo.getMenuNo()-1].setText(String.valueOf(vo.getsCount()));
				}
			}


		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "조회실패");
		}
		return;


	}



	//화면에 테이블 붙이는 메소드 --재고내역 모델
	class StockTableModel extends AbstractTableModel{
		ArrayList data = new ArrayList();

		String [] columnNames={"메뉴번호","메뉴명","재고 수량"};
		
	
		//String[] columnNames = (String[]) getColumn().toArray(new String[ getColumn().size()]);



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
	//화면에 테이블 붙이는 메소드 --주문내역 모델
	class StockOrderTableModel extends AbstractTableModel{
		ArrayList data = new ArrayList();

	
		String [] columnNames2={"입고번호","상품명","요청 수량","주문날짜"};


		@Override
		public int getColumnCount() {
			return columnNames2.length;
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
			return (String) columnNames2[col];
		}

	}

	}
	










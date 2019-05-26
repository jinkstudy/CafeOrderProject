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

	//������� ����
	JTextField tfStOrderNo;
	JLabel[] lbstMenulist =new JLabel[9];
	JTextField [] tfStOrderCnt = new JTextField[9] ;
	JButton btstOrder, btstCancel, btstModify;
	JComboBox cbstSearch;

	ButtonGroup buttons = new ButtonGroup();
	JRadioButton rbstOrderSearch,rbStSearch;
	JTable tableStock;

	StockTableModel tbModelStock;

	StockModel db;




	String[] mlist;
	ArrayList<String> SorderNoList;


	public void resetCbList() {
		
		try {
			db =new StockModel();
			mlist = db.getMname();
			SorderNoList = db.getOrderNoList();
			
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		
	}




	public StockView() {
		addLayout(); 	// ȭ�鼳��
		initStyle();
		eventProc();
		connectDB();

	}

	//��ġ ���̾ƿ�
	public void addLayout() {
		try{

			UIManager.setLookAndFeel("com.birosoft.liquid.LiquidLookAndFeel");
			// UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
		}catch(Exception e){}

		mlist = new String[9];
		SorderNoList = new ArrayList<String>();


		try {
			resetCbList();


			tfStOrderNo = new JTextField("�ֹ� �� �ڵ������˴ϴ�.");
			btstOrder = new JButton("�ֹ�");
			btstCancel = new JButton("���");
			btstModify = new JButton("����");
			cbstSearch = new JComboBox();
			rbstOrderSearch = new JRadioButton("�ֹ��̷���ȸ");
			rbStSearch = new JRadioButton("�������ȸ");

			//��ư �ϳ��� ���õǵ��� �׷���.
			buttons.add(rbstOrderSearch);
			buttons.add(rbStSearch);


			for (int i = 0; i < mlist.length; i++) {
				lbstMenulist[i]= new JLabel(mlist[i]);
				tfStOrderCnt[i] = new JTextField();
			}
			tbModelStock = new StockTableModel();
			tableStock = new JTable(tbModelStock);



		} catch (Exception e) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(null, "ȭ�鱸������" + e.getMessage());
		}

		btstOrder.setPreferredSize(new Dimension(20,80)); 
		btstCancel.setPreferredSize(new Dimension(20,80)); 
		tfStOrderNo.setPreferredSize(new Dimension(20,60)); 

		//ȭ�� ����


		//���� ����
		JPanel p_west = new JPanel();
		p_west.setBorder(new TitledBorder("�԰��û"));
		p_west.setLayout(new BorderLayout());

		//����-����
		JPanel p_west_n = new JPanel();
		p_west_n.setLayout(new GridLayout(1,2));

		p_west_n.add(new JLabel("�԰��ֹ���ȣ"));
		p_west_n.add(tfStOrderNo);


		//���� - ����
		JPanel p_west_c = new JPanel();
		p_west_c.setLayout(new GridLayout(9,2));
		for (int i = 0; i < 9; i++) {
			p_west_c.add(lbstMenulist[i]);
			p_west_c.add(tfStOrderCnt[i]);

		}

		//���� - ����

		JPanel p_west_s = new JPanel();
		p_west_s.setLayout(new GridLayout(1,2));
		p_west_s.add(btstOrder);
		//p_west_s.add(btstModify);
		p_west_s.add(btstCancel);

		p_west_n.setBorder(new TitledBorder(""));
		p_west_c.setBorder(new TitledBorder(""));
		p_west_s.setBorder(new TitledBorder(""));

		//���� ���� ���̱�	

		p_west.add(p_west_n,BorderLayout.NORTH);
		p_west.add(p_west_c,BorderLayout.CENTER);
		p_west.add(p_west_s,BorderLayout.SOUTH);


		//������ ����
		JPanel p_east = new JPanel();
		p_east.setBorder(new TitledBorder("��ȸ"));
		p_east.setLayout(new BorderLayout());	
		//������ -��
		JPanel p_east_n = new JPanel();
		p_east_n.setLayout(new GridLayout(2,1));
		//p_east_n.setBorder(new TitledBorder(""));

		//������ - ��- ��
		JPanel p_east_n_n = new JPanel();
		p_east_n_n.setLayout(new GridLayout(1,2));
		p_east_n_n.setBorder(new TitledBorder(""));
		p_east_n_n.add(rbstOrderSearch);

		p_east_n_n.add(rbStSearch);

		//������ - ��- d�Ʒ�
		JPanel p_east_n_s = new JPanel();
		p_east_n_s.setLayout(new GridLayout(1,3));
		p_east_n_s.add(new JLabel("��ȸ")); // ���ǵ��� ����
		p_east_n_s.add(cbstSearch);	
		p_east_n_s.setBorder(new TitledBorder(""));
		//p_east_n_s.


		p_east_n.add(p_east_n_n);
		p_east_n.add(p_east_n_s);

		//������ ����
		JPanel p_east_c = new JPanel();
		p_east_c.add(new JScrollPane(tableStock));

		//������ ���� ���̱�	

		p_east.add(p_east_n,BorderLayout.NORTH);
		p_east.add(p_east_c,BorderLayout.CENTER);



		//��ü ����
		setLayout(new GridLayout(1,2));
		add(p_west);
		add(p_east);


	}
	public void clearData() {
		//tfStOrderNo.setText(null);

		for (int i = 0; i < tfStOrderCnt.length; i++) {
			tfStOrderCnt[i].setText(null);
		}
	}
	// �ʱ� ����
	public void initStyle() {
		// TODO Auto-generated method stub
		//rbstOrderSearch.setSelected(true);
		//tfStOrderNo.enable();
	}

	// �̺�Ʈ ó��
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



	class MyHdlr implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent ev) {
			Object o = ev.getSource();

			if(o==tfStOrderNo){  
				//System.out.println("�̺�ƮȮ��");
				clearData();
				getStOrderList();
				

			}
			else if(o==btstModify){
				//System.out.println("�̺�ƮȮ��");
				//modifyStock();
			}			
			else  if(o==btstOrder){
				//System.out.println("�̺�ƮȮ��");
				
//				if(tfStOrderNo.getText().equals("�ֹ� �� �ڵ������˴ϴ�.") || tfStOrderNo.getText()==null)
//				{ 
				    tfStOrderNo.setText(null);
					String orderNo = orderStock();   
					clearData();
					
					resetCbList();
					setComboSt(SorderNoList.toArray(new String[SorderNoList .size()]));
					showData();
					JOptionPane.showMessageDialog(null, orderNo+"������ �ֹ��Ϸ�Ǿ����ϴ�.");
//				} else {
//					JOptionPane.showMessageDialog(null, "�̹� �Ϸ�� �ֹ��Դϴ�.");
//				}


			}
			else if(o==btstCancel){ 
				
				cancelStock();
				tfStOrderNo.setText(null);
				clearData();
				resetCbList();
				setComboSt(SorderNoList.toArray(new String[SorderNoList .size()]));
				
				//showData();


			}else if(o==rbstOrderSearch){ 
				//�̸��˻�
				
					setComboSt(SorderNoList.toArray(new String[SorderNoList .size()]));
					System.out.println("�̺�ƮȮ��1");
				
				
			}else if(o==rbStSearch){
				
				setComboSt(mlist);
				System.out.println("�̺�ƮȮ��2");
				
			}else if( o==cbstSearch){ 
				showData();
				clearData();
				tfStOrderNo.setText(null);
				if(SorderNoList.contains(cbstSearch.getSelectedItem())) {
					
					tfStOrderNo.setText((String)cbstSearch.getSelectedItem());
					getStOrderList();
				
				}
			
				


				System.out.println("�̺�ƮȮ��3");
			}
			//cbstSearch.addActionListener(this) ;

		}


	}
	// ��� ����

	public void connectDB() {
		try {
			db=new StockModel();
			//System.out.println("���Ἲ��");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("�������" + e.getMessage());
		}

	}
	public void setComboSt(String[] cb_list) {

		cbstSearch.removeAllItems();
		cbstSearch.addItem("��ü");
		for (int i = 0; i < cb_list.length; i++) {
			cbstSearch.addItem(cb_list[i]);
		}

	
	}

	public void showData() {
		int optSearch=1;

		String opt = (String)cbstSearch.getSelectedItem();
		try {
			if(rbstOrderSearch.isSelected()) {
				optSearch = 1;
			}else if(rbStSearch.isSelected()) {
				optSearch = 2;
			}


			tbModelStock.data= db.getSearchList(opt,optSearch);
			tbModelStock.fireTableDataChanged();
			//������ �ٲ��ٴ� ����� ȭ���ʿ� ������ �����.
			System.out.println(optSearch);

		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "��ȸ����"+e.getMessage());
		}

	}


	//	public void modifyMenuCnt() {
	//		
	//		
	//	}

	public void cancelStock() {
		String sOrderno = tfStOrderNo.getText();
		try {
			ArrayList list = new ArrayList();
			list = db.getStOrderCnt(sOrderno);

			db.modifyMenuCnt(list,2);
			db.deleteSt(sOrderno);
			JOptionPane.showMessageDialog(null, "��ҵǾ����ϴ�");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(null, "��������");
		}
		return;

	}

	public String orderStock() {

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


			db.insertSt(list);
			db.modifyMenuCnt(list,1);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return OrdernoFormat.format ( currentTime );

	}

	public void getStOrderList() {
		String sOrderno = tfStOrderNo.getText();
		
		try {
			ArrayList<Stock> orderCnt =new ArrayList<Stock>();
			orderCnt = db.getStOrderCnt(sOrderno);

			if(orderCnt.isEmpty()) {
				JOptionPane.showMessageDialog(null, "�ֹ���ȣ�� Ȯ���ϼ���");
			}

			else{
				for(Stock vo: orderCnt) {
					tfStOrderCnt[vo.getMenuNo()-1].setText(String.valueOf(vo.getsCount()));
				}
			}


		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "��ȸ����");
		}
		return;


	}



	//ȭ�鿡 ���̺� ���̴� �޼ҵ�
	class StockTableModel extends AbstractTableModel{
		ArrayList data = new ArrayList();

		String [] tbList1={"�԰��ȣ","��ǰ��","��û ����"};
		String [] tbList2={"�޴���ȣ","�޴���","��� ����"};
		ArrayList columnNames= new ArrayList();

		StockTableModel(){
			for (int i = 0; i < tbList1.length; i++) {
				columnNames.add(tbList1[i]);
			}

			//			for (int i = 0; i < tbList2.length; i++) {
			//				columnNames.add(tbList2[i]);
			//			}
			//			columnNames = new ArrayList();
			//			columnNames=getColumn();
			//			System.out.println(columnNames);
		}

		public ArrayList getColumn() {

			ArrayList clList= new ArrayList();

			//			if(rbstOrderSearch.isSelected()) {
			//				
			//				for (int i = 0; i < tbList1.length; i++) {
			//					clList.add(tbList1[i]);
			//					
			//				}
			//				
			//		
			//				}else if(rbStSearch.isSelected()) {
			//					for (int i = 0; i < tbList2.length; i++) {
			//						clList.add(tbList2[i]);
			//					}
			//				}
			//				//System.out.println(clList.toString());

			return clList;

		}



		//String[] columnNames = (String[]) getColumn().toArray(new String[ getColumn().size()]);



		@Override
		public int getColumnCount() {
			return columnNames.size();
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
			return (String) columnNames.get(col);
		}

	}
	
	







}


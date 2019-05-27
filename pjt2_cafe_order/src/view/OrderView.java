package view;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;
import javax.swing.table.AbstractTableModel;

import model.dao.OrderModel;
import model.dao.StockModel;
import model.vo.Order;
import model.vo.Stock;
import view.StockView.MyHdlr;
import view.StockView.StockTableModel;


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
	//JTable tableOrder;     //view ����
	//OrderTableModel tbModelOrder;   //model ����



	//********
	//�� Ŭ���� ���� ����
	OrderModel ord;
	String[] mlist;
	int[] j = new int[btMenu.length];



	//#########################
	//constructor method
	public OrderView() {        
		addLayout();  //ȭ�鼳��
		connectDB();
		eventProc();  //DB����
	}




	private void connectDB() {  //DB����
		try {
			ord = new OrderModel();
		}catch(Exception ex) {
			JOptionPane.showMessageDialog(null, "����̹�����:"+ex.getMessage());
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

	}
	class MyButtonHdlr implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			Object o = e.getSource();


			//	insert()//�ֹ��̺�Ʈ
			//	proctadd()//�����߰��̺�Ʈ
			//	result()//������ư�̺�Ʈ
			//	cancel()//��ҹ�ư�̺�Ʈ

			//	search() // �ֹ����� ��ȸ
			//	select() // ȸ�� ��ȭ��ȣ�� ���� �������� 
			//	update() //ȸ�� ��ȭ��ȣ ���ͼ� ������ ����

			//��ư �� �̺�Ʈ 
			//��ȭ��ȣ ����.�˻���ư Ŭ����
			if(o==tfOrderTel || o==bTel){  
				System.out.println("�̺�ƮȮ��");
				//clearData();
				cusInfo();

				// �޺��ڽ� ���� ��,����/����ũ�ƿ� ���� �о db�� �����ֱ�.
			}else if(o==bOrd) {

				getSum();
				//System.out.println("�ֹ��̺�ƮȮ��");

				// ��� Ŭ�� �� ��ü ���
			}else if(o==bCan) {

				//�迭 �ʱ�ȭ
				cancelSelect();

				for (int i = 0; i < btMenu.length; i++) {
					j[i]=0;
				}
				


				//System.out.println("����̺�ƮȮ��");

				//���ϸ��� ��� �� ��ü ���ݿ��� ����, �� ���ϸ��� ����	
			}else if(o==bUsage) {
				//System.out.println("����̺�Ʈ");

				// ������ȣ ���� �� ������ �޾ƿͼ� ���� ����	
			}else if(o==bApply) {
				//System.out.println("�����̺�ƮȮ��");

				//�����ݾ� ���� �� ���� ���� ���� db���� 
			}else if(o==bPay) {

				OrderSelectedItem();
				cancelSelect();
				tfOrderTel.setText(null);

				for (int i = 0; i < btMenu.length; i++) {
					j[i]=0;
				}

				//System.out.println("�����̺�ƮȮ��");
			}

			// �޴���ư Ŭ�� �� Db���� ���������� ������, �ֹ�����Ʈ ��������鼭 �ֹ�����â�� �Էµȴ�
			for (int i = 0; i < btMenu.length; i++) {

				if (o==btMenu[i]) {

					j[i]++;

					if(j[i] == 1) {
						Olist.add(getSelectedMenu(i,j[i]));

					}else if(j[i] >1) {
						Olist.remove(getSelectedMenu(i,j[i]-1));
						Olist.add(getSelectedMenu(i,j[i]));
					}



					//System.out.println(Olist);

					//System.out.println("�޴��̺�ƮȮ��");

					tbModelOrder.data= Olist;
					tbModelOrder.fireTableDataChanged();
					//System.out.println(((String)Olist.get(0).get(1)).charAt(0));

				}
			}


		}

	}




	//JTable �������� Ŭ������ �� ���콺 �̺�Ʈ ��





	//ȭ�鼳�� �޼ҵ�
	public void addLayout() {
		//��������� ��ü ����
		try {
			UIManager.setLookAndFeel("com.birosoft.liquid.LiquidLookAndFeel");
		} catch (Exception e) {	} 

		//textfield ��

		tfOrderTel = new JTextField(15);
		tfMiles = new JTextField(100);
		tfCupon = new JTextField(100);

		//���� ��ư 9 ��

		bTel = new JButton("�Է�");

		//String[] mlist;
		try {
			ord =new OrderModel();
			mlist = ord.getMname();
			for (int i = 0; i < btMenu.length; i++) {

				btMenu[i] = new JButton(mlist[i],new ImageIcon("src/img/menu/"+(i+1)+".jpg"));
				btMenu[i].setVerticalTextPosition(JButton.BOTTOM); //text ��ġ �������� ����
				btMenu[i].setHorizontalTextPosition(JButton.CENTER);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}



		//		bPlatW = new JButton("�÷�ȭ��Ʈ");
		//		bCarMa = new JButton("ī������ ��Ű����");
		//		bCarpu = new JButton("īǪġ��");
		//		bIceCarpu = new JButton("���̽� īǪġ��");
		//		bAmerica = new JButton("�Ƹ޸�ī��");
		//		bIceAmerica = new JButton("���̽� �Ƹ޸�ī��");
		//		bChoco = new JButton("����ġ��");
		//		bGreenTea = new JButton("�׸�Ƽ ��");
		//		bSand = new JButton("������ġ");

		//��ư ������
		bOrd = new JButton("�ֹ�");
		bCan = new JButton("���");
		bUsage = new JButton("���");
		bApply = new JButton("����");
		bPay = new JButton("����");

		//�޺��ڽ�
		String []k = {"����", "����ũ�ƿ�"};
		MorT = new JComboBox(k);
		//�ؽ�Ʈarea
		ta = new JTextArea(); 

		tbModelOrder = new OrderTableModel();
		tableOrder = new JTable(tbModelOrder);

		//�󺧵�
		labelMil = new JLabel("���ϸ���");
		labelCupon = new JLabel("������ȣ");
		labelTotal = new JLabel("�ѱݾ�");
		labelTotalWrite = new JLabel();



		//*************************************
		// ȭ�� ����



		//����

		JPanel order_west = new JPanel();
		order_west.setLayout(new BorderLayout());
		order_west.setBorder(new TitledBorder("�ֹ�â"));

		//���� �� 

		JPanel order_west_n = new JPanel();
		order_west_n.add(new JLabel("��ȭ��ȣ"));
		order_west_n.add(tfOrderTel);
		order_west_n.add(bTel);
		order_west.add(order_west_n, BorderLayout.NORTH);

		//���� ����

		JPanel order_west_c = new JPanel();
		order_west_c.setLayout(new GridLayout(3,3));
		for (int i = 0; i < btMenu.length; i++) {
			order_west_c.add(btMenu[i]);
		}
		//		order_west_c.add(bPlatW);
		//		order_west_c.add(bCarMa);
		//		order_west_c.add(bCarpu);
		//		order_west_c.add(bIceCarpu);
		//		order_west_c.add(bAmerica);
		//		order_west_c.add(bIceAmerica);
		//		order_west_c.add(bChoco);
		//		order_west_c.add(bGreenTea);
		//		order_west_c.add(bSand);
		order_west.add(order_west_c, BorderLayout.CENTER);

		//���� �Ʒ�

		JPanel order_west_s = new JPanel();
		order_west_s.add(MorT);
		order_west_s.add(bOrd);
		order_west_s.add(bCan);
		order_west.add(order_west_s, BorderLayout.SOUTH);


		//������

		JPanel order_east = new JPanel();
		order_east.setLayout(new BorderLayout());

		//������  �� 

		JPanel order_east_c = new JPanel();
		order_east_c.setBorder(new TitledBorder("�ֹ� ���� Ȯ��"));
		//order_east_c.add(ta); 
		order_east_c.add(new JScrollPane(tableOrder));
		order_east.add(order_east_c, BorderLayout.CENTER);

		//������ ��
		JPanel order_east_s = new JPanel();
		order_east_s.setBorder(new TitledBorder("��   ��"));
		order_east_s.setLayout(new BorderLayout());

		//������ ��-����
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
		//��ü������ ���̱�
		//��ü c(1,2)�� ����

		setLayout(new GridLayout(1,2));
		add(order_west);
		add(order_east);
	}



	// �ֹ� ��ư Ŭ�� ��  , �ֹ�������  db�� �Ѿ��, �� ������ ���ȴ�.

	public void OrderSelectedItem() {

		SimpleDateFormat DateFormat = new SimpleDateFormat ( "yyyy.MM.dd HH:mm:ss", Locale.KOREA );
		SimpleDateFormat OrdernoFormat = new SimpleDateFormat ( "yyyyMMddHHmmss", Locale.KOREA );
		Date currentTime = new Date ();

		ArrayList<Order> list = new ArrayList<Order>();

		try {
			for (ArrayList<String> sub_list:Olist) {


				Order or = new Order(); 

				or.setmOrderno(OrdernoFormat.format ( currentTime ));
				or.setoTime(DateFormat.format ( currentTime));
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

			ord.insertOrList(list);
			ord.modifyMenuCnt(list);



			//			ord.modifyMenuCnt(list,1);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//return OrdernoFormat.format ( currentTime );

	}




	// �޴��� ���� ����.
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
			oList_sub.add(j+"��");
			oList_sub.add(menuInfo.getmPrice()*j +"��");


			//System.out.println(menuInfo.toString());

			//oList = menuInfo.toString();


		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return  oList_sub;



	}

	// ���� ���.
	public void cancelSelect() {

		Olist.clear();
		tbModelOrder.data= Olist;
		tbModelOrder.fireTableDataChanged();

	}

	//db���� ��ȭ��ȣ list�� �޾ƿͼ� ��, �۾�.
	public void cusInfo() {
		String tel = tfOrderTel.getText();
		try {
			int[] result = ord.getInfoBytel(tel);

			if(result[0]==0) {
				joinCus();
			}
			else {
				tfMiles.setText(String.valueOf(result[1]));
			}


		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("���ϸ��� ��ȸ ����");
		}
	}

	//��ȭ��ȣ ���� �������� �ʴ� ��� �߰�.
	public void joinCus() {
		String tel = tfOrderTel.getText();

		try {
			ord.joinCus(tel);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
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
	
	class OrderTableModel extends AbstractTableModel{
		ArrayList data = new ArrayList();


		String [] columnNames={"�ֹ��޴�","����","����"};


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



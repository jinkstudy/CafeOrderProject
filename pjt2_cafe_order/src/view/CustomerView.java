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

	JTextField tfInputTel = new JTextField(10);  //��ȭ��ȣ �Է�	
	//	JTextField taMileage = new JTextField(10);  //���ϸ��� ��Ȳ
	JTextField taMileage = new JTextField();  //���ϸ��� ��Ȳ	
	TextArea tainfo  =   new TextArea( 25, 100);  //�̿볻�� ��Ȳ
	JButton bt_tel = new JButton("�˻�"); //��ȭ��ȣ �Է� ��ư
	JButton bt_mileage = new JButton("�˻�"); //���ϸ��� �˻�
	CustomerDao dao;


	String a = "------------------- �̿� ���� -------------------"+'\n'+ '\n'
			+ " �̿볯¥"+'\t'+'\t'+"�޴�"+'\t'+"����"+'\t'+"�ݾ�"+'\t' +"�̿�����"+'\t'+'\n'+ '\n'+ '\n';
	//	tainfo.setText("---------------- �̿� ���� ----------------+'\n'+ '\n'"
	//			+ "�̿볯¥"+'\t'+"�޴�"+'\t'+"����"+'\t'+"�ݾ�"+'\t' +"�̿�����"+'\n'+ '\n'+ '\n');

	public CustomerView() {
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
		p_east_north.add(new JLabel("��ȭ��ȣ �Է�"));
		p_east_north.add(tfInputTel);
		p_east_north.add(new JLabel("���ϸ��� ��Ȳ"));
		p_east_north.add(taMileage);	
		p_east.add(p_east_north,BorderLayout.NORTH);

		p_east_center.setLayout(new FlowLayout());
		//p_east_center.add(tainfo);
		p_east_center.add(new JScrollPane(tainfo));
		p_east.add(p_east_center,BorderLayout.CENTER);

		tainfo.setText(a);

		//��ü ����
		setLayout(new GridLayout(1,2));
		add(p_east);
		p_east.setBorder(new TitledBorder("�� �̿� ����"));
		add(p_west);
		p_west.setBorder(new TitledBorder("�̿� ���� �м�"));

	}
	void eventProc() { //tfInputTel-��ȭ��ȣ �Է� �ؽ�Ʈ �ʵ� �̺�Ʈ 
		ButtonEventHandler btnHandler = new ButtonEventHandler();
		tfInputTel.addActionListener(btnHandler);
	}

	class ButtonEventHandler implements ActionListener{
		public void actionPerformed(ActionEvent ev){
			Object o = ev.getSource();
			if(o==tfInputTel){ 
				try {
					//JOptionPane.showMessageDialog(null,"�����̺�Ʈ�߻�");
					searchByTel();
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, e.getMessage());
				}
			}
		}	

		// ��ȭ��ȣ�� ���� �˻�
		public void searchByTel() throws Exception{

			tainfo.setText(a);
			// 1. �Է��� ��ȭ��ȣ ������
			String tel = tfInputTel.getText();
			ArrayList<Customer> cusInfo = new ArrayList<Customer>();
			// 2. Model�� ��ȭ��ȣ �˻��޼ҵ� selectByTel()  ȣ��
			try {
				cusInfo= dao.selectByTel(tel);
				if(cusInfo.isEmpty()) {
					JOptionPane.showMessageDialog(null,"���������� �����ϴ�.");
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

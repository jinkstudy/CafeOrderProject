package model.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import model.OrderDao;
import model.vo.Order;
import model.vo.Stock;

public class OrderModel implements OrderDao{
	//1. ����̹��ε�
	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@192.168.0.117:1521:orcl";
	String user = "jink";
	String pass = "1234";

	//2. ���ᰴü ������
	Connection con = null;

	public OrderModel() throws Exception {

		//1. ����̹��� �޸� �ε�
		Class.forName(driver);
	}

	public String[] getMname() throws Exception {
		String[] mname=new String[9] ;

		Connection	con = null;
		PreparedStatement st= null;
		ResultSet rs =null;

		// 2. ���ᰴü
		con = DriverManager.getConnection(url, user ,pass);
		//3. sql
		String sql = "SELECT  menuname FROM menu order by menuno";
		//4.���۰�ü
		st = con.prepareStatement(sql);
		//5. ����
		rs = st.executeQuery();

		// 6. ���ó��
		for (int i = 0; rs.next(); i++) {
			mname[i] = rs.getString("menuname");
			//System.out.println(mname[i]);
		}

		//7. �ݱ�
		rs.close();
		st.close();
		con.close();


		return mname;
	}
	//		public void insertOrder1(Order or) throws Exception {
	//			Connection con = null;
	//			PreparedStatement ps = null;
	//			
	//			try {
	//				//2. Connection ���ᰴü ������
	//				con = DriverManager.getConnection(url,user,pass);
	//				
	//				//3. sql ���� �����
	//				String sql = "SELECT * FROM Customer WHERE Cmile=?";
	//			
	//				// 4. sql ���۰�ü (PreparedStatement)
	//				ps = con.prepareStatement(sql);
	//				
	//				ps.setInt(1, or.getMiles());
	//				
	//				//5.sql ����
	//				ps.executeQuery();
	//			}finally {
	//				//. �ݱ�
	//				ps.close();
	//				con.close();
	//			}
	//	}
	//		
	//		public void insertOrder2(Order or) throws Exception {
	//	
	//			Connection con = null;
	//			PreparedStatement ps = null;
	//			
	//			try {
	//				//2. Connection ���ᰴü ������
	//				con = DriverManager.getConnection(url,user,pass);
	//				
	//				//3. sql ���� �����
	//				String sql = "SELECT * FROM Coupon WHERE Cdiscount=?";
	//			
	//				// 4. sql ���۰�ü (PreparedStatement)
	//				ps = con.prepareStatement(sql);
	//				
	//				ps.setInt(1, or.getCdiscount());
	//				
	//				//5.sql ����
	//			 ps.executeQuery();
	//			}finally {
	//				//. �ݱ�
	//				ps.close();
	//				con.close();
	//			}
	//	}
	//		
	//		public void orderHwakin(Order or, int count) throws Exception {
	//		
	//			Connection con = null;
	//			PreparedStatement ps = null;
	//			try {
	//				//2. Connection ���ᰴü ������
	//				con = DriverManager.getConnection(url,user,pass);
	//				
	//				//3. sql ���� �����
	//				String sql = "UPDATE menu SET MenuNO=?, MenuName=?, MPrice=?,Mcount=?";
	//			
	//				// 4. sql ���۰�ü (PreparedStatement)
	//				ps = con.prepareStatement(sql);
	//				
	//				ps.setInt(1, or.getMenuNo());
	//				ps.setString(2, or.getMenuName());
	//				ps.setInt(3, or.getMPrice());
	//				ps.setInt(4,or.getMcount());
	//				
	//				//5.sql ����
	//				ps.executeUpdate();
	//			}finally {
	//				//. �ݱ�
	//				ps.close();
	//				con.close();
	//				
	//			}

	public int[] getInfoBytel(String tel) throws Exception{
		con =DriverManager.getConnection(url,user,pass);


		ResultSet rs = null;
		int mile = 0;
		String sql = "SELECT ctel,cmile FROM customer where trim(ctel)=?";
		System.out.println(sql);
		//4. ���۰�ü
		PreparedStatement ps= con.prepareStatement(sql);
		ps.setString(1,tel);
		rs=ps.executeQuery();
		int i = 0;
		if(rs.next()) {
			mile = rs.getInt("cmile");
			String ctel = rs.getString("ctel");
			i++;
		}
		int[] result = {i,mile};
		return result;

	}


	// ��ȭ��ȣ ���� ��� �� ���̺� �߰�.
	public void joinCus(String tel) throws Exception{

		ResultSet rs = null;
		int mile = 0;
		String sql = "Insert Into Customer(Ctel,Cmile) Values (?,?)";
		System.out.println(sql);
		//4. ���۰�ü
		PreparedStatement ps;
		try {
			ps = con.prepareStatement(sql);
			ps.setString(1,tel);
			ps.setInt(2,mile);


			ps.executeUpdate(); 



		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


	}

	//r���� ���� �� ���õ� �޴� �ֹ� DB �� ���� �� �ֱ�
	public void insertOrList(ArrayList<Order> list) throws Exception {

		// 2. Connection ���ᰴü ������
		Connection con = DriverManager.getConnection(url, user ,pass);
		// 3. sql ���� �����
		
		//����â�� �ֹ���ȣ ���� �Է�
		String sql1 = "Insert into payment(oorderno) values(?)" ; 
		PreparedStatement st1 = con.prepareStatement(sql1);
		
		
		st1.setString(1,list.get(1).toString());
		st1.executeUpdate(); 
		
		String sql = "Insert into order_cus(ono,oorderno,otime,otype,ctel,menuno,ocount,ototalprice)"
							+ " VALUES(sq_order_cus_ono.nextval,?,?,?,?,?,?,?) ";
		
		System.out.println(sql);
		
		// 4. sql ���۰�ü (PreparedStatement)	
		
		PreparedStatement st = con.prepareStatement(sql);

		//System.out.println(">"+vo[0]);

		for(Order or : list) {
			
			
			
			st.setString(1,or.getmOrderno());
			st.setString(2, or.getoTime());
			st.setString(3, or.getOtype());
			st.setString(4, or.getCtel());
			st.setInt(5,or.getoMenuNo());
			
			st.setInt(6,or.getmOcount());
			st.setInt(7,or.getmPrice());
			
			System.out.println(or.getmOcount()+" /" + or.getmPrice());
			System.out.println(or.getOtype());
			
			st.executeUpdate(); 
		}



		//6. �ݱ�

		
		st1.close();
		st.close();
		con.close();





	}
	//���õ� �޴��� ���ݰ� �޴� �̸�,��� ��������.
	public Order getSelectedInfo(int menuNo) throws Exception{
		//ArrayList<Order> menuInfo =new ArrayList<Order> ();
		Order or = new Order();
		Connection	con = null;
		PreparedStatement st= null;
		ResultSet rs =null;

		// 2. ���ᰴü
		con = DriverManager.getConnection(url, user ,pass);

		//3. SQL
		String sql = "select menuno,menuname,mprice,mcount from menu where menuno=?";

		//4.���۰�ü
		st = con.prepareStatement(sql);
		st.setInt(1,menuNo);
		//5.����
		rs = st.executeQuery();
		//5.���ó��
		if(rs.next()) {
			or.setoMenuNo(rs.getInt("menuno"));
			or.setMenuName(rs.getString("menuname"));
			or.setmPrice(rs.getInt("mprice"));
			or.setmSCount(rs.getInt("mcount")); //������

		}

		//7. �ݱ�
		rs.close();
		st.close();
		con.close();



		return or;
	}


}



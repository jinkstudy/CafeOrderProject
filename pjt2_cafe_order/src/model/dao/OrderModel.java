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
	//1. 드라이버로딩

	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@192.168.0.117:1521:orcl";
	String user= "jink";
	String pass="1234";

	//2. 연결객체 얻어오기
	Connection con = null;

	public OrderModel() throws Exception {

		//1. 드라이버를 메모리 로딩
		Class.forName(driver);
	}

	public String[] getMname() throws Exception {
		String[] mname=new String[9] ;

		Connection	con = null;
		PreparedStatement st= null;
		ResultSet rs =null;

		// 2. 연결객체
		con = DriverManager.getConnection(url, user ,pass);
		//3. sql
		String sql = "SELECT  menuname FROM menu order by menuno";
		//4.전송객체
		st = con.prepareStatement(sql);
		//5. 전송
		rs = st.executeQuery();

		// 6. 결과처리
		for (int i = 0; rs.next(); i++) {
			mname[i] = rs.getString("menuname");
			//System.out.println(mname[i]);
		}

		//7. 닫기
		rs.close();
		st.close();
		con.close();


		return mname;
	}
	

	public int[] getInfoBytel(String tel) throws Exception{
		con =DriverManager.getConnection(url,user,pass);


		ResultSet rs = null;
		int mile = 0;
		String sql = "SELECT ctel,cmile FROM customer where trim(ctel)=?";
		System.out.println(sql);
		//4. 전송객체
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


	// 전화번호 없는 경우 고객 테이블에 추가.
	public void joinCus(String tel) throws Exception{

		ResultSet rs = null;
		int mile = 0;
		String sql = "Insert Into Customer(Ctel,Cmile) Values (?,?)";
		System.out.println(sql);
		//4. 전송객체
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

	//결제 선택 시 선택된 메뉴 주문 DB 및 결제 에 넣기
	public void insertOrList(ArrayList<Order> list,String cno,int totalPrice) throws Exception {

		// 2. Connection 연결객체 얻어오기
		Connection con = DriverManager.getConnection(url, user ,pass);
		// 3. sql 문장 만들기
		
		//결제창에 주문번호 먼저 입력
		String sql1 = "Insert into payment(oorderno,cno,totalprice) values(?,?,?)" ; 
		PreparedStatement st1 = con.prepareStatement(sql1);
		
		
		st1.setString(1,list.get(0).toString());
		st1.setString(2,cno);
		st1.setInt(3,totalPrice);
		
		st1.executeUpdate(); 
		
		String sql = "Insert into order_cus(ono,oorderno,otime,otype,ctel,menuno,ocount,ototalprice)"
							+ " VALUES(sq_order_cus_ono.nextval,?,?,?,?,?,?,?) ";
		
		System.out.println(sql);
		
		// 4. sql 전송객체 (PreparedStatement)	
		
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
			
			System.out.println(or.getoMenuNo()+"/" + or.getmOcount()+" /" + or.getmPrice());
			System.out.println(or.getOtype());
			System.out.println(list.get(0).toString());
			
			st.executeUpdate(); 
		}



		//6. 닫기

		
		st1.close();
		st.close();
		con.close();





	}
	//선택된 메뉴의 가격과 메뉴 이름,재고 가져오기.
	public Order getSelectedInfo(int menuNo) throws Exception{
		//ArrayList<Order> menuInfo =new ArrayList<Order> ();
		Order or = new Order();
		Connection	con = null;
		PreparedStatement st= null;
		ResultSet rs =null;

		// 2. 연결객체
		con = DriverManager.getConnection(url, user ,pass);

		//3. SQL
		String sql = "select menuno,menuname,mprice,mcount from menu where menuno=?";

		//4.전송객체
		st = con.prepareStatement(sql);
		st.setInt(1,menuNo);
		//5.전송
		rs = st.executeQuery();
		//5.결과처리
		if(rs.next()) {
			or.setoMenuNo(rs.getInt("menuno"));
			or.setMenuName(rs.getString("menuname"));
			or.setmPrice(rs.getInt("mprice"));
			or.setmSCount(rs.getInt("mcount")); //재고수량

		}

		//7. 닫기
		rs.close();
		st.close();
		con.close();



		return or;
	}
	
	// 재고 수량 조정하기.
	public void modifyMenuCnt(ArrayList<Order> list) throws Exception {
		// 2. Connection 연결객체 얻어오기
		Connection con = DriverManager.getConnection(url, user ,pass);
       // 3. sql 문장 만들기
		String sql =null;
		
		
			sql = "UPDATE menu SET mcount = mcount-?  WHERE menuno = ? ";
		
		

		PreparedStatement st = con.prepareStatement(sql);
		
		
		for(Order or : list) {
			//System.out.println(vo[i].getsOrderNo());
			st.setInt(1, or.getmOcount());
			st.setInt(2, or.getoMenuNo());
			System.out.println(or.getoMenuNo() + "/" +or.getmOcount());
			
			
			st.executeUpdate(); 
		}
		

		st.close();
		con.close();

		
	}
	// 테이블에서 쿠폰 할인율 얻어오기
	public int getDiscount(String couponNo) throws Exception {
		
				Connection	con = null;
				PreparedStatement st= null;
				ResultSet rs =null;
				int percent =0;
				// 2. 연결객체
				con = DriverManager.getConnection(url, user ,pass);

				//3. SQL
				String sql = "select cdiscount from coupon where cno=?";

				//4.전송객체
				st = con.prepareStatement(sql);
				st.setString(1,couponNo);
				//5.전송
				rs = st.executeQuery();
				
				//5.결과처리
				if(rs.next()) {
				percent = rs.getInt("cdiscount");
					
				}

				//7. 닫기
				rs.close();
				st.close();
				con.close();



				return percent;
		
	}

	
	// 사용자 마일리지 데이터 반영하기
	public void upDateCusmile(int plusmile, int minusmile,String ctel) throws Exception{
		
		// 2. Connection 연결객체 얻어오기
		Connection con = DriverManager.getConnection(url, user ,pass);
      // 3. sql 문장 만들기
		
		//마일리지 추가/ 차감.
		String sql ="UPDATE customer SET cmile = cmile+?-?  WHERE ctel = ? ";
	

		PreparedStatement st = con.prepareStatement(sql);
		
	
			st.setDouble(1, plusmile);
			st.setInt(2, minusmile);
			st.setString(3, ctel);
			
			st.executeUpdate(); 
	
		

		st.close();
		con.close();

		
	}


}



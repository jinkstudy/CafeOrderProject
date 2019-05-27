package model.dao;

import java.sql.*;
import java.util.ArrayList;

import model.StockDao;
import model.vo.Stock;


public class StockModel implements StockDao {
//	String driver = "oracle.jdbc.driver.OracleDriver";
//	String url ="jdbc:oracle:thin:@127.0.0.1:1521:orcl";
//	String user = "lsh";
//	String pass = "lsh";
	
	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@192.168.0.117:1521:orcl";
	String user= "jink";
	String pass="1234";

	public StockModel() throws Exception {
		Class.forName(driver);
	}



	public String[] getMname() throws Exception {
		String[] mname=new String[9] ;

		Connection	con = null;
		PreparedStatement st= null;
		ResultSet rs =null;

		// 2. ¿¬°á°´Ã¼
		con = DriverManager.getConnection(url, user ,pass);
		//3. sql
		String sql = "SELECT  menuname FROM menu order by menuno";
		//4.Àü¼Û°´Ã¼
		st = con.prepareStatement(sql);
		//5. Àü¼Û
		rs = st.executeQuery();

		// 6. °á°úÃ³¸®
		for (int i = 0; rs.next(); i++) {
			mname[i] = rs.getString("menuname");
			//System.out.println(mname[i]);
		}

		//7. ´Ý±â
		rs.close();
		st.close();
		con.close();


		return mname;
	}

	public ArrayList<String> getOrderNoList() throws Exception {
		ArrayList<String> orderNoList=new ArrayList<String>() ;

		Connection	con = null;
		PreparedStatement st= null;
		ResultSet rs =null;

		// 2. ¿¬°á°´Ã¼
		con = DriverManager.getConnection(url, user ,pass);
		//3. sql
		String sql = "SELECT  distinct sorderno FROM stock order by sorderno";
		//4.Àü¼Û°´Ã¼
		st = con.prepareStatement(sql);
		//5. Àü¼Û
		rs = st.executeQuery();

		// 6. °á°úÃ³¸®
		while( rs.next()){
			orderNoList.add( rs.getString("sorderno"));

		}

		//7. ´Ý±â
		rs.close();
		st.close();
		con.close();


		return orderNoList;
	}


	@Override
	public ArrayList<Stock> getStOrderCnt(String sOrderno) throws Exception {

		ArrayList<Stock> stOrderCnt = new ArrayList<Stock>();
		Connection	con = null;
		PreparedStatement st= null;
		ResultSet rs =null;

		// 2. ¿¬°á°´Ã¼
		con = DriverManager.getConnection(url, user ,pass);
		//3. sql
		String sql = "SELECT sorderno,menuno, scount FROM stock where sorderno = ?";

		//4.Àü¼Û°´Ã¼
		st = con.prepareStatement(sql);
		st.setString(1,sOrderno);


		//5. Àü¼Û
		rs = st.executeQuery();

		// 6. °á°úÃ³¸®


		while(rs.next()) {
			Stock vo = new Stock();
			vo.setMenuNo(rs.getInt("menuno"));
			vo.setsCount(rs.getInt("scount"));
			stOrderCnt.add(vo);
			System.out.println(vo.toString());
		}
		System.out.println(stOrderCnt.toString());

		//7. ´Ý±â
		rs.close();
		st.close();
		con.close();




		return stOrderCnt;
	}

	public void modifyMenuCnt(ArrayList<Stock> list, int opt) throws Exception {
		// 2. Connection ¿¬°á°´Ã¼ ¾ò¾î¿À±â
				Connection con = DriverManager.getConnection(url, user ,pass);
		// 3. sql ¹®Àå ¸¸µé±â
				String sql =null;
				
				if(opt == 1) {
					sql = "UPDATE menu SET mcount = mcount+?  WHERE menuno = ? ";
				}else if (opt ==2) {
					sql = "UPDATE menu SET mcount = mcount-?  WHERE menuno = ? ";
				}
				

				PreparedStatement st = con.prepareStatement(sql);
				
				for(Stock vo : list) {
					//System.out.println(vo[i].getsOrderNo());
					st.setInt(1, vo.getsCount());
					st.setInt(2, vo.getMenuNo());
					
					st.executeUpdate(); 
				}
				

				st.close();
				con.close();

		
	}


	@Override
	public void insertSt(ArrayList<Stock> list) throws Exception {

		// 2. Connection ¿¬°á°´Ã¼ ¾ò¾î¿À±â
		Connection con = DriverManager.getConnection(url, user ,pass);
		// 3. sql ¹®Àå ¸¸µé±â
		String sql = "INSERT INTO stock(sno,sorderno,stime,menuno,scount) "
				+ " VALUES(sq_stock_sno.nextval,?,?,?,?) ";
		//				String sql = "INSERT INTO stock(sno,menuno,scount) "
		//						+ " VALUES(sq_stock_sno.nextval,?,1) ";
		//System.out.println(sql);
		// 4. sql Àü¼Û°´Ã¼ (PreparedStatement)	
		PreparedStatement st = con.prepareStatement(sql);

		//System.out.println(">"+vo[0]);

		for(Stock vo : list) {
			//System.out.println(vo[i].getsOrderNo());
			st.setString(1,vo.getsOrderNo());
			st.setString(2, vo.getsTime());
			st.setInt(3, vo.getMenuNo());
			st.setInt(4, vo.getsCount());
			st.executeUpdate(); 
		}



		//6. ´Ý±â
		st.close();
		con.close();





	}
	public ArrayList getSearchList(String opt, int optSearch) throws Exception {
		ArrayList stSearchList = new ArrayList();
		Connection	con = null;
		PreparedStatement st= null;
		ResultSet rs =null;

		// 2. ¿¬°á°´Ã¼
		con = DriverManager.getConnection(url, user ,pass);
		//3. sql
		if(optSearch == 1) {
			if(opt.equals("ÀüÃ¼")) {
				String sql = "SELECT S.SORDERNO SORDERNO, M.MENUNAME MENUNAME, S.SCOUNT scount, s.eno eno, s.stime stime " + 
						" FROM STOCK S INNER JOIN MENU M" + 
						" ON S.MENUNO=M.MENUNO " + 
						" ORDER BY SORDERNO";
				System.out.println(sql);


				//4.Àü¼Û°´Ã¼
				st = con.prepareStatement(sql);



			} else {
				String sql = "SELECT S.SORDERNO SORDERNO, M.MENUNAME MENUNAME, S.SCOUNT scount, s.eno eno, s.stime stime  "
						+ "  FROM STOCK S INNER JOIN MENU M "
						+ "  ON S.MENUNO=M.MENUNO "
						+ "  WHERE s.SORDERNO = ?  ORDER BY m.Menuname";
				System.out.println(sql);

				st = con.prepareStatement(sql);
				st.setString(1,opt);
			}

			rs = st.executeQuery();

			while(rs.next()) {
				ArrayList list = new ArrayList();

				list.add(rs.getString("sorderno"));
				list.add(rs.getString("MENUNAME"));
				list.add(rs.getInt("scount"));
				//list.add(rs.getString("eno"));
				//list.add(rs.getString("stime"));

				stSearchList.add(list);

			}
		}else if(optSearch == 2) {
			if(opt.equals("ÀüÃ¼")) {
				String sql = "SELECT Menuno, menuname, mcount from menu" ;


				//4.Àü¼Û°´Ã¼
				st = con.prepareStatement(sql);



			} else {
				String sql = "SELECT Menuno, menuname, mcount from menu where menuname = ? order by menuno";
				//System.out.println(sql);

				st = con.prepareStatement(sql);
				st.setString(1,opt);
			}

			rs = st.executeQuery();

			while(rs.next()) {
				ArrayList list = new ArrayList();

				list.add(rs.getInt("menuno"));
				list.add(rs.getString("menuname"));
				list.add(rs.getInt("mcount"));


				stSearchList.add(list);

			}

		}



		//7. ´Ý±â
		rs.close();
		st.close();
		con.close();




		return stSearchList;
	}



	



	@Override
	public void updateSt(Stock vo) throws Exception {
		// TODO Auto-generated method stub

	}



	@Override
	public void deleteSt(String sOrderNo) throws Exception {

		// 2. Connection ¿¬°á°´Ã¼ ¾ò¾î¿À±â
		Connection con = DriverManager.getConnection(url, user ,pass);
		// 3. sql ¹®Àå ¸¸µé±â
		
		String sql = "DELETE FROM stock WHERE sOrderNo = ?";

		// 4. sql Àü¼Û°´Ã¼ (PreparedStatement)	
		PreparedStatement st = con.prepareStatement(sql);

		st.setString(1,sOrderNo);

		//5.Àü¼Û
		st.executeUpdate(); 


		//6. ´Ý±â
		st.close();
		con.close();



	}


	



}



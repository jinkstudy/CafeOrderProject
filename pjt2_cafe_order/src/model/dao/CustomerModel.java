package model.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import model.vo.Customer;

import model.CustomerDao;

public class CustomerModel implements CustomerDao {
	// 1. 드라이버로딩
	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@192.168.0.117:1521:orcl";
	String user= "jink";
	String pass="1234";
	Connection con = null;

	public CustomerModel() throws Exception{
		Class.forName(driver);
	}

	public ArrayList<Customer> selectByTel(String tel) throws Exception {
		con =DriverManager.getConnection(url,user,pass);
		
		
		ResultSet rs = null;
		//ResultSet rs2 = null;

		//3.sql
		String sql = "select oc.otime otime, c.ctel  ctel , c.cmile cmile, m.menuname menuname, m.mprice mprice, oc.ocount ocount, oc.otype otype  " + 
				" from customer c inner join order_cus oc\r\n" + 
				" on c.ctel = oc.ctel" + 
				" inner join menu m " + 
				" on oc.menuno = m.menuno" + 
				" where trim(c.ctel) = ?" + 
				" order by otime desc";
		System.out.println(sql);
		//4. 전송객체
		PreparedStatement ps= con.prepareStatement(sql);
		ps.setString(1,tel);

		//5.전송
		rs=ps.executeQuery();
		//rs2=ps2.executeQuery();
		ArrayList<Customer> cus  = new ArrayList<Customer>();
		while(rs.next())

		{
			
			Customer dao = new Customer();
			dao.setCusDay(rs.getString("otime"));
			dao.setCustTel(rs.getString("ctel"));
			dao.setCustmile(rs.getInt("cmile"));
			dao.setCusorder(rs.getString("menuname"));
			dao.setCustotal(rs.getInt("mprice"));
			dao.setCusCount(rs.getInt("ocount"));
			dao.setCusType(rs.getString("otype"));
			cus.add(dao);
		
		}
		
//		if(rs2.next())
//
//		{
//			dao.setCusDay(rs.getString("otime") +"\t"+ rs.getString("menuname") +"개\t"+ rs.getString("ototalprice") +"원\n");
//			
//		}

		rs.close();
		//rs2.close();
		ps.close();
		//ps2.close();
		con.close();
		return cus;
	}
}

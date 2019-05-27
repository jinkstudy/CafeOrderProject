package model.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import model.vo.Customer;

import model.CustomerDao;

public class CustomerModel implements CustomerDao {
	// 1. 드라이버로딩
//	String driver = "oracle.jdbc.driver.OracleDriver";
////	String url = "jdbc:oracle:thin:@192.168.0.4:1521:orcl";
//	String url = "jdbc:oracle:thin:@127.0.0.1:1521:orcl";
//	String user = "lsh";
//	String pass = "lsh";
	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@192.168.0.117:1521:orcl";
	String user= "jink";
	String pass="1234";
	Connection con = null;

	public CustomerModel() throws Exception{
		Class.forName(driver);
	}

	public Customer selectByTel(String tel) throws Exception {
		con =DriverManager.getConnection(url,user,pass);
		Customer dao = new Customer();
		ResultSet rs = null;
		ResultSet rs2 = null;

		//3.sql
		String sql = "SELECT cmile FROM customer where trim(ctel)=?";
		//4. 전송객체
		PreparedStatement ps= con.prepareStatement(sql);
		ps.setString(1,tel);

		//3.sql2
		String sql2 = " SELECT o.otime as otime, m.menuname || '*' || o.ocount AS menuname,o.ototalprice as ototalprice "
				+ " FROM customer c"
				+ " inner join order_cus o on c.ctel=o.ctel "
				+ " inner join menu m on o.menuno = m.menuno"
				 + " WHERE c.ctel= ?";
		
		//4. 전송객체
		PreparedStatement ps2= con.prepareStatement(sql2);
		ps2.setString(1,tel);

		//5.전송
		rs=ps.executeQuery();
		rs2=ps2.executeQuery();

		while(rs.next())
		{
			dao.setCustmile(rs.getInt("cmile"));
		}
		
		String row ="";
		while(rs2.next())
		{
			row += rs2.getString("otime") +"\t"
		+ rs2.getString("menuname") +"개\t"
					+ rs2.getString("ototalprice") +"원\n";
//			System.out.println(row);
		}
		dao.setCusDay(row);
		rs.close();
		rs2.close();
		ps.close();
		ps2.close();
		con.close();
		return dao;
	}
}

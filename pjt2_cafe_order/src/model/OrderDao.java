package model;

import java.util.ArrayList;

import model.vo.Order;

public interface OrderDao {

	
	public String[] getMname() throws Exception;
	public int[] getInfoBytel(String tel) throws Exception;
	public void joinCus(String tel) throws Exception;
	public void insertOrList(ArrayList<Order> list,String cno,int totalPrice) throws Exception;
	public Order getSelectedInfo(int menuNo) throws Exception;
	public void modifyMenuCnt(ArrayList<Order> list) throws Exception;
	public int getDiscount(String couponNo) throws Exception;
	public void upDateCusmile(int plusmile, int minusmile,String ctel) throws Exception;
	
	
}

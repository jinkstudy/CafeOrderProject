package model;

import java.util.ArrayList;

import model.vo.Stock;

public interface StockDao {
	public String[] getMname() throws Exception;
	public ArrayList getStOrderCnt(String sorderno) throws Exception;
	public void insertSt(ArrayList<Stock> vo) throws Exception;
	public void updateSt(Stock vo) throws Exception;
	public void deleteSt(String sOrderNo) throws Exception;
	public ArrayList getSearchList(String opt,int optSearch) throws Exception;
	public ArrayList<String> getOrderNoList() throws Exception;
	

}

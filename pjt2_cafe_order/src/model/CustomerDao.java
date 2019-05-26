package model;

import java.util.ArrayList;

import model.vo.Customer;

public interface CustomerDao {
	public ArrayList<Customer> selectByTel(String tel) throws Exception;

}

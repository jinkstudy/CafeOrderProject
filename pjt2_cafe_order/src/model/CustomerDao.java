package model;

import model.vo.Customer;

public interface CustomerDao {
	public Customer selectByTel(String tel) throws Exception;

}

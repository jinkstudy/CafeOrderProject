package model.vo;

public class Customer {
	String custTel; //고객전화번호
	int custmile; //마일리지
	String cusDay; // 날짜
	String cusorder; //주문내역
	int custotal; //총가격
	int cusCount; //수량
	String cusType; //이용방법
	

	

	public int getCusCount() {
		return cusCount;
	}

	public void setCusCount(int cusCount) {
		this.cusCount = cusCount;
	}

	public String getCusType() {
		return cusType;
	}

	public void setCusType(String cusType) {
		this.cusType = cusType;
	}

	public String getCusDay() {
		return cusDay;
	}

	public void setCusDay(String cusDay) {
		this.cusDay = cusDay;
	}

	public String getCusorder() {
		return cusorder;
	}

	public void setCusorder(String cusorder) {
		this.cusorder = cusorder;
	}

	public int getCustmile() {
		return custmile;
	}

	public void setCustmile(int custmile) {
		this.custmile = custmile;
	}

	public String getCustTel() {
		return custTel;
	}

	public int getCustotal() {
		return custotal;
	}

	public void setCustotal(int custotal) {
		this.custotal = custotal;
	}

	public void setCustTel(String custTel) {
		this.custTel = custTel;
	}
	
	public String toString() {
		String result = getCusDay() +"\t"+ getCusorder() + "\t"+ getCusCount()+"개\t"
				+getCustotal() +"원\t"+getCusType() +"\n"+"\n";
		
		return result;
	}

	
}


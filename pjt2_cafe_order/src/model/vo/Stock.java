package model.vo;

public class Stock {
	String sOrderNo;
	String sTime;
	int menuNo;
	int sCount;
	int empNo;
	
	
	public String getsOrderNo() {
		return sOrderNo;
	}
	public void setsOrderNo(String sOrderNo) {
		this.sOrderNo = sOrderNo;
	}
	public String getsTime() {
		return sTime;
	}
	public void setsTime(String sTime) {
		this.sTime = sTime;
	}
	public int getMenuNo() {
		return menuNo;
	}
	public void setMenuNo(int menuNo) {
		this.menuNo = menuNo;
	}
	public int getsCount() {
		return sCount;
	}
	public void setsCount(int sCount) {
		this.sCount = sCount;
	}
	public int getEmpNo() {
		return empNo;
	}
	public void setEmpNo(int empNo) {
		this.empNo = empNo;
	}
	
	public String toString() {
		String sent = menuNo+","+sCount;
		
		return sent;
		
	}
	
}

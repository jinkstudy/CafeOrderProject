package model.vo;

public class Order {
	
	String mOrderno; //주문요청번호
	String oTime; //주문일시
	String otype; //주문형태
	String ctel; //고객전화번호
	String menuName; //메뉴이름
	int oMenuNo; //메뉴번호
	int mOcount; //주문수량
	int mPrice; //메뉴가격합계
	int mTotalPay; //총 결제금액
	int mSCount;//기존 수량
	String mCou; //쿠폰번호
	
	
	
	
	public String getmCou() {
		return mCou;
	}



	public void setmCou(String mCou) {
		this.mCou = mCou;
	}



	public String getmOrderno() {
		return mOrderno;
	}



	public void setmOrderno(String mOrderno) {
		this.mOrderno = mOrderno;
	}



	public String getoTime() {
		return oTime;
	}



	public void setoTime(String oTime) {
		this.oTime = oTime;
	}



	public String getOtype() {
		return otype;
	}



	public void setOtype(String otype) {
		this.otype = otype;
	}



	public String getCtel() {
		return ctel;
	}



	public void setCtel(String ctel) {
		this.ctel = ctel;
	}



	public String getMenuName() {
		return menuName;
	}



	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}



	public int getoMenuNo() {
		return oMenuNo;
	}



	public void setoMenuNo(int oMenuNo) {
		this.oMenuNo = oMenuNo;
	}



	public int getmOcount() {
		return mOcount;
	}



	public void setmOcount(int mOcount) {
		this.mOcount = mOcount;
	}



	public int getmPrice() {
		return mPrice;
	}



	public void setmPrice(int mPrice) {
		this.mPrice = mPrice;
	}



	public int getmTotalPay() {
		return mTotalPay;
	}



	public void setmTotalPay(int mTotalPay) {
		this.mTotalPay = mTotalPay;
	}



	public int getmSCount() {
		return mSCount;
	}



	public void setmSCount(int mSCount) {
		this.mSCount = mSCount;
	}



	public String toString() {
		String result = getmOrderno();
		
		return result;
	}
	
		
}

package model.vo;

public class Order {
	
	String mOrderno; //�ֹ���û��ȣ
	String oTime; //�ֹ��Ͻ�
	String otype; //�ֹ�����
	String ctel; //����ȭ��ȣ
	String menuName; //�޴��̸�
	int oMenuNo; //�޴���ȣ
	int mOcount; //�ֹ�����
	int mPrice; //�޴������հ�
	int mTotalPay; //�� �����ݾ�
	int mSCount;//���� ����
	String mCou; //������ȣ
	
	
	
	
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

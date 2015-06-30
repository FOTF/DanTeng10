package entity;

public class ButtonInfor {
	private String prexBtn;
	private String nextBtn;
	private String thizBtn;
	
	public String getThizBtn() {
		return thizBtn;
	}
	public void setThizBtn(String thizBtn) {
		this.thizBtn = thizBtn;
	}
	public String getPrexBtn() {
		return prexBtn;
	}
	public void setPrexBtn(String prexBtn) {
		this.prexBtn = prexBtn;
	}
	public String getNextBtn() {
		return nextBtn;
	}
	public void setNextBtn(String nextBtn) {
		this.nextBtn = nextBtn;
	}
	@Override
	public String toString() {
		return "ButtonInfor [prexBtn=" + prexBtn + ", nextBtn=" + nextBtn
				+ ", thizBtn=" + thizBtn + "]";
	}
}

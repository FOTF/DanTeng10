package entity;

public class ContentInfor {
	private String title;
	private String imgSrc;

	public ContentInfor() {
		super();
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getImgSrc() {
		return imgSrc;
	}

	public void setImgSrc(String imgSrc) {
		this.imgSrc = imgSrc;
	}

	public ContentInfor(String title, String imgSrc) {
		super();
		this.title = title;
		this.imgSrc = imgSrc;
	}
}

package model.dto;

public class ChatListDto {
	private int crno;
	private int mno;
	
	private String imageUrl;
	private String username;
	   
	
	
	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public ChatListDto() {}

	public ChatListDto(int crno, int mno) {
		super();
		this.crno = crno;
		this.mno = mno;
	}

	public int getCrno() {
		return crno;
	}

	public void setCrno(int crno) {
		this.crno = crno;
	}

	public int getMno() {
		return mno;
	}

	public void setMno(int mno) {
		this.mno = mno;
	}

	@Override
	public String toString() {
		return "ChatListDto [crno=" + crno + ", mno=" + mno + "]";
	}
	
	
	
	
}

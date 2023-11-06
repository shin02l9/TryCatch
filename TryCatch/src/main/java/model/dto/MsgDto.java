package model.dto;

public class MsgDto {
 private String type;      // 메시지 타입
   private String title;      // 방이름    // 1.방만들때 사용중
   private int mno;          // 보낸사람 이름
   private int crno;      // 방번호 
   private String roomList;   // 1.방목록 출력할때 사용중.
   private String content;
   private String fromname;
   private String clist; // 접속된 회원 명단
   private String mdate; // 날짜
   private String imageUrl;


   String getImageUrl() {
	return imageUrl;
}


void setImageUrl(String imageUrl) {
	this.imageUrl = imageUrl;
}


public String getMdate() {
		return mdate;
}


public void setMdate(String mdate) {
	this.mdate = mdate;
}


	public String getClist() {
	   return clist;
	}
	
	
	public void setClist(String clist) {
	   this.clist = clist;
	}
	
	
	public String getFromname() {
	      return fromname;
	   }
	
	
	public void setFromname(String fromname) {
	   this.fromname = fromname;
	}
	
	
	public String getContent() {
	   return content;
	   }

   
   public void setContent(String content) {
      this.content = content;   
   }
   
   public int getCrno() {
      return crno;
   }
   
   public void setCrno(int crno) {
      this.crno = crno;
   }
   
   public String getRoomList() {
      return roomList;
   }

   public void setRoomList(String roomList) {
      this.roomList = roomList;
   }

   public MsgDto() {
      // TODO Auto-generated constructor stub
   }

   public MsgDto(String type, String title, int mno) {
      super();
      this.type = type;
      this.title = title;
      this.mno = mno;
   }

   public String getType() {
      return type;
   }

   public void setType(String type) {
      this.type = type;
   }

   public String getTitle() {
      return title;
   }

   public void setTitle(String title) {
      this.title = title;
   }

   public int getMno() {
      return mno;
   }

   public void setMno(int mno) {
      this.mno = mno;
   }

   @Override
   public String toString() {
      return "MsgDto [type=" + type + ", title=" + title + ", mno=" + mno + "]";
   }

}
package model.dto;



public class RoomDto {
   private int crno;
   private String crtitle;
   private int mno;
   
   public RoomDto() {
      // TODO Auto-generated constructor stub
   }

   public RoomDto(int crno, String crtitle, int mno) {
      super();
      this.crno = crno;
      this.crtitle = crtitle;
      this.mno = mno;
   }

   public int getCrno() {
      return crno;
   }

   public void setCrno(int crno) {
      this.crno = crno;
   }

   public String getCrtitle() {
      return crtitle;
   }

   public void setCrtitle(String crtitle) {
      this.crtitle = crtitle;
   }

   public int getMno() {
      return mno;
   }

   public void setMno(int mno) {
      this.mno = mno;
   }

}


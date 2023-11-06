package model.dto;

public class ChattingDto {

	private int crno; // 방번호
	private String crtitle; // 방제목
	private String crcdate; // 방 만든 시간 -> 삭제 각?
	private String crddate; // 방 삭제 시간 -> 삭제 각?
	private int mno; // 메세지 보낸 사람
	private int cmno; // 메세지번호
	private String cmessage; // 메세지 내용
	private String cmdate; // 메세지 보낸 시간
	
	// 빈 생성자
	public ChattingDto() {}
	
	// 풀 생성자
	public ChattingDto(int crno, String crtitle, String crcdate, String crddate, int mno, int cmno, String cmessage,
			String cmdate) {
		super();
		this.crno = crno;
		this.crtitle = crtitle;
		this.crcdate = crcdate;
		this.crddate = crddate;
		this.mno = mno;
		this.cmno = cmno;
		this.cmessage = cmessage;
		this.cmdate = cmdate;
	}
	
	// 채팅할 때(방번호, 메세지보낸 사람, 메세지번호, 메세지내용, 메세지 보낸시간)
	public ChattingDto(int crno, int mno, int cmno, String cmessage, String cmdate) {
		super();
		this.crno = crno;
		this.mno = mno;
		this.cmno = cmno;
		this.cmessage = cmessage;
		this.cmdate = cmdate;
	}

	// 방 만들때(방번호, 방제, 방만든시간, 방만든사람)
	public ChattingDto(int crno, String crtitle, String crcdate, int mno) {
		super();
		this.crno = crno;
		this.crtitle = crtitle;
		this.crcdate = crcdate;
		this.mno = mno;
	}
	// 방 접속된 회원명단(회원 리스트 어떻게??)
	public ChattingDto(int crno, String crtitle, String crcdate) {
		super();
		this.crno = crno;
		this.crtitle = crtitle;
		this.crcdate = crcdate;
	}
	
	// 방 목록 받았을 때??
	
	
	
	
	
	
	

	
	
	
	
	
	
}

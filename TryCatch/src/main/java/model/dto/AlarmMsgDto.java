package model.dto;

import javax.websocket.Session;

import com.fasterxml.jackson.annotation.JsonCreator;

public class AlarmMsgDto {
	
	private int type;		 // 알림 소켓을 부르는 type
	private int bno;		 // 이슈가 일어나 글 번호
	private int mno;		 // 받을 사람 회원 번호
	private String msg;		 // 알람 내용

	public AlarmMsgDto() {
		// TODO Auto-generated constructor stub
	}

	public AlarmMsgDto(int mno) {
		super();
		this.mno = mno;
	}
	
	

	public AlarmMsgDto(int mno, String msg) {
		super();
		this.mno = mno;
		this.msg = msg;
	}

	public AlarmMsgDto(int type, int bno, int mno, String msg) {
		super();
		this.type = type;
		this.bno = bno;
		this.mno = mno;
		this.msg = msg;
	}

	public AlarmMsgDto(int mno, int bno, String msg) {
		super();
		this.mno = mno;
		this.bno = bno;
		this.msg = msg;
	}

	@Override
	public String toString() {
		return "AlarmMsgDto [type=" + type + ", bno=" + bno + ", mno=" + mno + ", msg=" + msg + "]";
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getBno() {
		return bno;
	}

	public void setBno(int bno) {
		this.bno = bno;
	}

	public int getMno() {
		return mno;
	}

	public void setMno(int mno) {
		this.mno = mno;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
	
	
	
}

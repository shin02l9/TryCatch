package controller;

import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;

import model.dao.BoardDao;

public class WriteLog {
	
	private static WriteLog writeLog = new WriteLog();
	private WriteLog() {}
	public static WriteLog getinstance() { return writeLog; }	
	
	// 로그 작성
	public void writeLog( String filePath , String content ) throws IOException {
		FileOutputStream log = new FileOutputStream(filePath , true);
		String data = content + " " + LocalDateTime.now() + "\n";
		log.write( data.getBytes() );
		log.close();
	}
	
}

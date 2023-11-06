package model.dao;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;

public class Filtering {
	
	private static Filtering filtering = new Filtering();
	private Filtering() {};
	public static Filtering getinstance() { return filtering; }
	
	// String 반환 욕있으면 **로 바꿔서 내보냄
	public String slangFilter(String filePath, String btitle) throws IOException {
	    
		File file = new File(filePath);

	    try (FileInputStream fis = new FileInputStream(file)) {
	        
	    	
	    	byte[] data = new byte[(int) file.length()];
	        fis.read(data);
	        System.out.println(  data);
	        String filterFile = new String(data);

	        String[] slangArr = filterFile.split("\r\n");
	       
	        for (int i = 0; i < slangArr.length; i++) {
	        	btitle = btitle.replace(slangArr[i], "**");				 
	        }
	        
	        return btitle;
	    }
	}
	
	public boolean slangDefense(String filePath, String slangTest) throws IOException {
	    
		File file = new File(filePath);

	    try (FileInputStream fis = new FileInputStream(file)) {
	       
	    	byte[] data = new byte[(int) file.length()];
	       
	    	fis.read(data);

	        String filterFile = new String(data, StandardCharsets.UTF_8);

	        String[] slangArr = filterFile.split("\n");

	        System.out.println(Arrays.toString(slangArr));
	        
	        int count = -2;
	        
	        for (int i = 0; i < slangArr.length; i++) {
	        	
	            count = slangTest.indexOf(slangArr[i]);
	           
	            if(count > -1) {
	            	return false;
	            }
	        }

	        if( count == -1 ) {
            	System.out.println(count);
            	return true;
            }
            return false;
	    }
	}
	
}

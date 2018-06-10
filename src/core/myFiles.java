package core;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class myFiles {
	
	public static int countLines(String path) throws IOException {
	    InputStream is = new BufferedInputStream(new FileInputStream(path));
	    try {
	        byte[] c = new byte[1024];
	        int count = 0;
	        int readChars = 0;
	        boolean endsWithoutNewLine = false;
	        while ((readChars = is.read(c)) != -1) {
	            for (int i = 0; i < readChars; ++i) {
	                if (c[i] == '\n')
	                    ++count;
	            }
	            endsWithoutNewLine = (c[readChars - 1] != '\n');
	        }
	        if(endsWithoutNewLine) {
	            ++count;
	        } 
	        return count;
	    } finally {
	        is.close();
	    }
	}
	
	public static String getValueLine(String path, int line){
		try {
			FileReader fr = new FileReader(path);
			BufferedReader bufferedReader = new BufferedReader(fr);
			String lineString = null;
			
			for (int i = 0; i < line; i++){
				try {
					lineString = bufferedReader.readLine();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			int size = lineString.length();
			char[] string = lineString.toCharArray();
			int equalPosition = 0;
			
			for (int i = 0; i < size; i++){
				if (string[i] == '='){
					equalPosition = i;
				}
			}
			
			ArrayList<Character> value = new ArrayList<Character>();
			
			for (int i = 0; i < size - equalPosition; i++){
				int base = equalPosition + i;
				if (string[base] != ' ' && string[base] != '\n' && string[base] != '='){
					value.add(string[base]);
				}
			}
			
			String str = value.stream().map(e->e.toString()).collect(Collectors.joining());
			return str;
					
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
	}
	
	public static int convertToValue (String value){
		
		if (value.equals("TRUE")){
			return 1;
		} else if (value.equals("FALSE")){
			return 0;
		} else {
			return Integer.parseInt(value);
		}
		
	}
	
	public static boolean convertToBoolean (int value){
		
		if (value == 0)
			return false;
		else
			return true;
		
	}
}



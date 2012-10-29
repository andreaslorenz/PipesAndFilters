package concrete;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import filters.PushFilter;

public class CharSource extends PushFilter<String, Character> {

	public CharSource() {
		
	}

	public static void main(String[] args) throws IOException {
		FileInputStream fis = new FileInputStream("resources/textFile"); 
		
		int content;
			while ((content = fis.read()) != -1) {
			System.out.println( (char) content);
		}
	}
}

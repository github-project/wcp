package mail;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Properties;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Value;

public class Test {

	
	public static void main(String[] args) {
		
		HashMap<String, Object> map = new HashMap<String,Object>();
		
		map.put("1", 111);
		
		map.put("2", 111);
		
		
		map.put("1", 22);
		
		
		map.put("3", 111);
		
	    ResourceBundle bundle = ResourceBundle.getBundle("log4j");
		
		String string = bundle.getString("log4j.appender.A1.File");
		
		System.out.println(string);
		
//		InputStream resourceAsStream = Test.class.getResourceAsStream("jdbc.properties");
//		
//	
//		
//		Properties properties = new Properties();
//		
//		try {
//			properties.load(resourceAsStream);
//			
//			System.out.println(properties.get("jdbc.password"));
//			
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
	}

}

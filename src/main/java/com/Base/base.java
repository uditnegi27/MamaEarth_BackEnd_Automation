package com.Base;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;


public class base {
	
	public static Properties prop;
	
	public base() {
		try {
			prop = new Properties();
			FileInputStream file = new FileInputStream("/Users/honasa/Desktop/SeleniumSeesion/APIAutomation/src/main/java/com/Configuration/configuration.properties");
			prop.load(file);
		}
		catch(FileNotFoundException e) {
			e.printStackTrace();
		}
		catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	/*** Environment can be "Production", "UAT" or "DEV"   ***/
	public static String setupURI(String environment) {
		String env = environment.toLowerCase();
		if(env.equals("production")) {
			return prop.getProperty("Production_URI");
		}
		else if(env.equals("uat")) {
			return prop.getProperty("Production_URI");
		}
		else if(env.equals("dev")) {
			return prop.getProperty("Production_URI");
		}
		else {
			return "**  Environment is not present is data...";
		}
	}

}

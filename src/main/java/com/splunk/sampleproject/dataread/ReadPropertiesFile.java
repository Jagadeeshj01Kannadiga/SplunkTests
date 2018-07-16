
package com.splunk.sampleproject.dataread;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Properties;

public class ReadPropertiesFile {
	
	public static String readFile(String readProperty){
		String value = "";
		 value = new File("resources/serviceEndPoints.properties").getAbsolutePath();
		File file = new File(value);
							FileInputStream fileInput = null;
							try {
								fileInput = new FileInputStream(file);
							} catch (FileNotFoundException e) {
								e.printStackTrace();
							}
							Properties prop = new Properties();
							try {
								prop.load(fileInput);
							} catch (IOException e) {
								e.printStackTrace();
							}

							Enumeration KeyValues = prop.keys();
							while (KeyValues.hasMoreElements()) {
								String key = (String) KeyValues.nextElement();
								if(key.equals(readProperty)){
								 value = prop.getProperty(key);
								}
								System.out.println(key + ":- " + value);
							}
							return value;
					}
	}

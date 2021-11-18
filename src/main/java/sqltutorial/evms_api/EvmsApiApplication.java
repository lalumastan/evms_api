package sqltutorial.evms_api;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class EvmsApiApplication {
	
	public final static String DEFAULT_CLIENT_ID = "admin";
	public final static String DEFAULT_CLIENT_SECRET = "aPassword"; 		
	
	public static void main(String[] args) {
		// Basic authentication with encoded bytes
		String credentials = DEFAULT_CLIENT_ID + ":" + DEFAULT_CLIENT_SECRET;
		String encodedCredentials = new String(Base64.encodeBase64(credentials.getBytes()));
		System.out.println("Authorization: " + encodedCredentials);		
		
		SpringApplication.run(EvmsApiApplication.class, args);
	}

}

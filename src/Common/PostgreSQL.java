package Common;

import java.sql.Connection;
import java.sql.DriverManager;

import com.google.gson.JsonObject; 

public class PostgreSQL {
	
	
	public static Connection getConnection(String config) throws Exception {
	    
		try
		{
			JsonObject jobj=JSON.strToJson( DocumentReader.AppData.get(config));
			
			String driver = "org.postgresql.Driver";
		    String url = "jdbc:postgresql://"+jobj.get("ip").getAsString()+"/"+jobj.get("db").getAsString()+
		    		(jobj.has("ssl")&&jobj.get("ssl").getAsString().equals("true")?"?sslmode=require":"");
		    String username = jobj.get("username").getAsString();
		    String password = jobj.get("password").getAsString();
	
		    Class.forName(driver);
		    Connection conn = DriverManager.getConnection(url, username, password);
		    return conn;
		}
		catch (Exception e) {
			System.out.println(e.toString());
		}
		return null;
	  }

}

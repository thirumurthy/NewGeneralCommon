package Common;

import java.sql.Connection;
import java.sql.DriverManager;

import com.google.gson.JsonObject;

public class Mysqlconnection {

	public static Connection getMySqlConnection(String config) throws Exception {
	    
		JsonObject jobj=JSON.strToJson( DocumentReader.AppData.get(config));
		
		String driver = "org.gjt.mm.mysql.Driver";
	    String url = "jdbc:mysql://"+jobj.get("ip").getAsString()+"/"+jobj.get("db").getAsString();
	    String username = jobj.get("username").getAsString();
	    String password = jobj.get("password").getAsString();

	    Class.forName(driver);
	    Connection conn = DriverManager.getConnection(url, username, password);
	    return conn;
	  }

	
}

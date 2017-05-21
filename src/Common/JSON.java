package Common;


import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class JSON {
	
	public static Object deserialize(String jsonstr,String classname) throws ClassNotFoundException
	{
		try
		{
			return new Gson().fromJson(jsonstr, Class.forName(classname));
		}
		catch (Exception e) {

			e.printStackTrace();
			return null;
		}
	}

	public static JsonObject strToJson(String jsonstr)
	{
		try
		{
			return (JsonObject)new JsonParser().parse(jsonstr).getAsJsonObject();
			
		}
		catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static JsonArray deSerializeArray(String jsonstr)
	{
		JsonArray jsonArray =new JsonArray();
		try
		{  
			 JsonParser jsonParser = new JsonParser();
			  jsonArray = (JsonArray) jsonParser.parse(jsonstr);
			return jsonArray;
		}
		catch (Exception e) {
			return jsonArray;
			// TODO: handle exception
		}
	}
	
	
	public static String serialize(Object obj)
	{
		 
		try
		{  
			Gson gson = new Gson();    
			return gson.toJson(obj);
		}
		catch (Exception e) {
			return null;
			// TODO: handle exception
		}
	}
	
	
	public static JsonObject deSerializeObject(String jsonstr)
	{
		JsonObject jobj=new JsonObject();
		try
		{  
			 JsonParser jsonParser = new JsonParser();
			 jobj = (JsonObject) jsonParser.parse(jsonstr);
			return jobj;
		}
		catch (Exception e) {
			return jobj;
			// TODO: handle exception
		}
	}
	
}

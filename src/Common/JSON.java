package Common;


import com.google.gson.Gson;
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

}

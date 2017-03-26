package Common;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.*;


public class RestClient {
	
	
	 
	@SuppressWarnings("deprecation")
	public static String Get(String url) throws ClientProtocolException, IOException
	{
		HttpResponse execute = null;
		InputStream content= null;
		//HttpClient client=null;
		    
		//DefaultHttpClient client = new DefaultHttpClient();
		try
		{ 
		DefaultHttpClient client=null;
			try
			{
			
			 client = new DefaultHttpClient();
			}
			catch (Exception e) {

				e.printStackTrace();
			}
		 HttpGet httpGet = new HttpGet(url);
		 execute= client.execute(httpGet);
		  content = execute.getEntity().getContent();
		}
		catch (Exception e) {
			e.printStackTrace();
			return "";
		}
		
		 return getStringResponse(content);            	 
			
	}
	
	
	  public static String getStringResponse(InputStream content)
	    {
	    	String response="";

	    	try
	    	{
	          BufferedReader buffer = new BufferedReader(new InputStreamReader(content));
	          String s = "";
	          while ((s = buffer.readLine()) != null) {
	            response += s;
	          }
	    	}
	    	catch(Exception exp)
	    	{
	    		exp.printStackTrace();
	    	}
	    	return response;
	    }

}

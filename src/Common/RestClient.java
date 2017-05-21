package Common;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;


public class RestClient {
	
	private final static String USER_AGENT = "Mozilla/5.0";
	
	
	 public static String getHTML(String urlToRead) throws Exception {
	      
		 try
		 {
			 StringBuilder result = new StringBuilder();
		      URL url = new URL(urlToRead);
		      HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		      conn.setRequestMethod("GET");
		      BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		      String line;
		      while ((line = rd.readLine()) != null) {
		         result.append(line);
		      }
		      rd.close();
		      return result.toString();
		 }
		 catch (Exception e) {
			 return "";
		}
	   }

	
	 
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
	  
	  
	  public static String POSTData(String url,String postJsonData,String ContentType) throws Exception {
		  
		  
		   try
		   {
			  URL obj = new URL(url);
			  HttpURLConnection con = (HttpURLConnection) obj.openConnection();
			 
			        // Setting basic post request
			  con.setRequestMethod("POST");
			  con.setRequestProperty("User-Agent", USER_AGENT);
			  con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
			  con.setRequestProperty("Content-Type",ContentType);
			 
			   
			  
			  // Send post request
			  con.setDoOutput(true);
			  DataOutputStream wr = new DataOutputStream(con.getOutputStream());
			  wr.writeBytes(postJsonData);
			  wr.flush();
			  wr.close();
			 
			
			  BufferedReader in = new BufferedReader(
			          new InputStreamReader(con.getInputStream()));
			  String output;
			  StringBuffer response = new StringBuffer();
			 
			  while ((output = in.readLine()) != null) {
			   response.append(output);
			  }
			  in.close();
			  
			  //printing result from response
			 return response.toString();
			 }
		   catch (Exception e) {
			 return "";
		}
	  }
	  
	  
	  /**
	   * 
	   * @param url - web address to post
	   * @param postdata - Form POST data for site 
	   * @param header - Passing the list of header details
	   * @return response as string 
	   */
	  public static String FormPOST(String url,Map<String,String> postdata,Map<String,String> header){
		  
		  try
		  {
			  
				@SuppressWarnings("deprecation")
				HttpClient client = new DefaultHttpClient();
				HttpPost post = new HttpPost(url);
				
				// add header
				if(header!=null && header.size()>0)
				 for (Map.Entry<String,String> entry : header.entrySet())  
						   post.setHeader(entry.getKey(), entry.getValue()); 
						 
				List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();
				
				if(postdata!=null&&postdata.size()>0)
					for (Map.Entry<String,String> entry : postdata.entrySet())  
					urlParameters.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
				 
				post.setEntity(new UrlEncodedFormEntity(urlParameters));

				HttpResponse response = client.execute(post);
				
				BufferedReader rd = new BufferedReader(
		                        new InputStreamReader(response.getEntity().getContent()));

				StringBuffer result = new StringBuffer();
				String line = "";
				while ((line = rd.readLine()) != null) {
					result.append(line);
				}

				return result.toString();
		  }
		  catch (Exception exp) {
			  
			  return "";
		}
	  }

}

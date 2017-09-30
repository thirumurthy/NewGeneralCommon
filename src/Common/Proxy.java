package Common;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.RandomAccessFile;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.params.ConnRoutePNames;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreConnectionPNames;

/**
 * Before accessing this function once need to call Proxy.ProxySetup()
 * Make sure proxy.txt file exists in your workspace
 * @author Thirumurthy
 *
 */

public class Proxy {
	
	static File file=null;
	public static List<String> proxylst=new ArrayList<String>();
    static RandomAccessFile read=null;  
    private static Random randomGenerator;
    public static void ProxySetup(){
        file=new File("proxy.txt");
        try {
            read=new RandomAccessFile(file,"rw");
            if(proxylst.size()==0)
            checkproxies();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static String getRandomProxy()
    {
    	String proxyval="";
    	try
    	{
    		 int index = randomGenerator.nextInt(proxylst.size());
    		 proxyval= proxylst.get(index);
    	}
    	catch(Exception exp)
    	{
    		exp.printStackTrace();
    	}
    	return proxyval;
    }
    
    private static void checkproxies(){
        try{
            String line;
            for(int i=0;i<25;i++){
                if((line=read.readLine())!=null){
                    System.out.println(line);
                    String[] hp=line.split(":");
                    InetAddress addr=InetAddress.getByName(hp[0]);
                    if(addr.isReachable(5000)){
                        System.out.println("reached");
                        ensocketize(hp[0],Integer.parseInt(hp[1]));
                    }
                }
            }
        }catch(Exception ex){ex.printStackTrace();}
    }



    private static void ensocketize(String host,int port){
        try{
           
            HttpClient client=new DefaultHttpClient();
            HttpGet get=new HttpGet("http://blanksite.com/");
            HttpHost proxy=new HttpHost(host,port);
            client.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY, proxy);
            client.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 15000);
            HttpResponse response=client.execute(get);
            HttpEntity enti=response.getEntity();
            if(response!=null){
                System.out.println(response.getStatusLine());
                System.out.println(response.toString());
                System.out.println(host+":"+port+" @@ working");
                proxylst.add(host+":"+port);
                
            }
        }catch(Exception ex){System.out.println("Proxy failed");}
    }


}

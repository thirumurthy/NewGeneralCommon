package Common;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.PrintWriter;

import org.apache.commons.codec.binary.StringUtils;

import com.sun.xml.bind.v2.model.util.ArrayInfoUtil;

public class FileOperations {
	
	public static String readFile(String filename)
	{
		String resp="";
		try (BufferedReader br = new BufferedReader(new FileReader(filename)))
		{

			String sCurrentLine;

			while ((sCurrentLine = br.readLine()) != null) {
				resp+=sCurrentLine;
			}

		} catch (IOException e) {
			e.printStackTrace();
		} 

		return resp;
	}

	public static void WriteData(String filename,String content)
	{
		try {
 
			File file = new File(filename);

			// if file doesnt exists, then create it
			if (!file.exists()) {
				file.createNewFile();
			}

			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(content);
			bw.close();
 

		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public static String[]  ListDirectory(String path)
	{
		
		try
		{
			
			File file = new File(path);
			String[] directories = file.list(new FilenameFilter() {
			  @Override
			  public boolean accept(File current, String name) {
			    return new File(current, name).isDirectory();
			  }
			});
			return directories;
		}
		catch (Exception exp) {
			
			return null;
			// TODO: handle exception
		}
		 
	}
	
	public static boolean DeleteFile(String path)
	{
		boolean result=false;
		try
		{
			
			File file = new File(path);

    		if(file.delete()){
    			result=true;
    		}
			
			return result;
		}
		catch (Exception e) {
			return result;
			// TODO: handle exception
		}
	}
	
	public static boolean CreateFile(String path)
	{
		boolean result=false;
		try
		{
			
			File file = new File(path);

    		if(file.createNewFile()){
    			result=true;
    		}
			
			return result;
		}
		catch (Exception e) {
			return result;
			// TODO: handle exception
		}
	}
	
	public static boolean checkFileExists(String path)
	{
		boolean result=false;
		try
		{
			
			File file = new File(path);

    		if(file.exists()){
    			result=true;
    		}
			
			return result;
		}
		catch (Exception e) {
			return result;
			// TODO: handle exception
		}
	}
	
	
	public static String geLastline(String path)
	{
		String result="";
		try
		{
			 BufferedReader input = new BufferedReader(new FileReader(path));
			    String  line;
			
			    while ((line = input.readLine()) != null) {
			    	result = line;
			    }
			
			return result;
		}
		catch (Exception e) {
			return result;
			// TODO: handle exception
		}
	}
	
	
	public static void appendFile(String filename,String content)  
	{
		BufferedWriter bw = null;
		 
		FileWriter fw = null;
		 
		PrintWriter out = null;
		try
		{
			File file = new File(filename);

			// if file doesnt exists, then create it
			if (!file.exists()) {
				file.createNewFile();
			}

			 fw = new FileWriter(filename, true);
			    bw = new BufferedWriter(fw);
			    out = new PrintWriter(bw);
			    out.println(content);
			    out.close();
		}
		catch(Exception exp)
		{
			exp.printStackTrace();
		}
		finally {
		    if(out != null)
			    out.close();
		    try {
		        if(bw != null)
		            bw.close();
		    } catch (IOException e) {
		        //exception handling left as an exercise for the reader
		    }
		    try {
		        if(fw != null)
		            fw.close();
		    } catch (IOException e) {
		        //exception handling left as an exercise for the reader
		    }
		
	}
	}
	
}

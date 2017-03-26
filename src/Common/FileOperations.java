package Common;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

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
	
	
	public static void appendFile(String filename,String content)
	{
		BufferedWriter bw = null;
		FileWriter fw = null;
		try
		{
			File file = new File(filename);

			// if file doesnt exists, then create it
			if (!file.exists()) {
				file.createNewFile();
			}

			// true = append file
			fw = new FileWriter(file.getAbsoluteFile(), true);
			bw = new BufferedWriter(fw);

			bw.write("\n"+content);
		}
		catch(Exception exp)
		{
			exp.printStackTrace();
		}
		
	}
	
}

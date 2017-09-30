package Common;

import java.awt.AWTException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.imageio.ImageIO;

public class Screenshot {

	static SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd hh mm ss a");
	public static String  takeScreenshot()
	{
		Calendar now = Calendar.getInstance();
	        Robot robot = null;
			try {
				robot = new Robot();
			} catch (AWTException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
	        BufferedImage screenShot = robot.createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
	        try {
				ImageIO.write(screenShot, "JPG", new File( formatter.format(now.getTime())+".jpg"));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        return formatter.format(now.getTime())+".jpg" ;
	}
	
}

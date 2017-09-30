package Common;


import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Message.RecipientType;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import com.google.gson.JsonObject;

public class Email {
	/**
	 * 
	 * @param from		Sender's email ID needs to be mentioned
	 * @param to 		 Recipient's email ID needs to be mentioned.
	 * @param config
	 */
	
	public static void  sendEmail(String from,String to,String subject,String msg,String filename,String BCC,String CC,boolean isHtml, JsonObject config )
	{
		 
		
		 
    final String username = config.get("username").getAsString(); 
    final String password = config.get("password").getAsString();

    // Assuming you are sending email through relay.jangosmtp.net
    String host =config.get("host").getAsString(); 
     
	 
	Properties props = new Properties();
	props.put("mail.smtp.user",username);
	props.put("mail.smtp.host", host); 
	props.put("mail.smtp.port", "587"); //TLS Port
	props.put("mail.smtp.auth", "true"); //enable authentication
	props.put("mail.smtp.starttls.enable", "true"); //enable STARTTLS
	props.put("mail.smtp.ssl.enable", "false");
	props.put("mail.smtp.ssl.trust", host);
	/*props.put("mail.smtp.port", "465");
	props.put("mail.smtp.starttls.enable", "true");
	props.put("mail.smtp.auth", "true");
	// props.put("mail.smtp.debug", "true");
	props.put("mail.smtp.socketFactory.port", "465");
	props.put("mail.smtp.socketFactory.class",
	"javax.net.ssl.SSLSocketFactory");
	props.put("mail.smtp.socketFactory.fallback", "false");
*/
	
    // Get the Session object.
    Session session = Session.getInstance(props,
       new javax.mail.Authenticator() {
          protected PasswordAuthentication getPasswordAuthentication() {
             return new PasswordAuthentication(username, password);
          }
       });

    try {
       // Create a default MimeMessage object.
       Message message = new MimeMessage(session);

       // Set From: header field of the header.
       message.setFrom(new InternetAddress(from));

       // Set To: header field of the header.
       message.setRecipients(Message.RecipientType.TO,
          InternetAddress.parse(to));
       if(BCC!=null&&BCC.trim().length()>0)
       message.addRecipient(RecipientType.BCC, new InternetAddress(
    		   BCC));
       if(CC!=null&&CC.trim().length()>0)
       message.addRecipient(RecipientType.CC, new InternetAddress(
    		   CC));

       // Set Subject: header field
       message.setSubject(subject);

       // Create the message part
       BodyPart messageBodyPart = new MimeBodyPart();

       // Now set the actual message
       if(isHtml)
       messageBodyPart.setContent(msg, "text/html; charset=utf-8");
       else
       messageBodyPart.setText(msg);

       // Create a multipar message
       Multipart multipart = new MimeMultipart();

       // Set text message part
       multipart.addBodyPart(messageBodyPart);

       // Part two is attachment
       messageBodyPart = new MimeBodyPart(); 
       DataSource source = new FileDataSource(filename);
       messageBodyPart.setDataHandler(new DataHandler(source));
       messageBodyPart.setFileName(filename);
       multipart.addBodyPart(messageBodyPart);

       // Send the complete message parts
       message.setContent(multipart);
       
       // Send message
       Transport.send(message);

       System.out.println("Sent message successfully....");

    } catch (Exception e) {
        
    	System.err.println("Error"+e);
    	
    }
 }

}

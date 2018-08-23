package astatic;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.util.Properties;

import javax.activation.*;
import javax.mail.*;
import javax.mail.internet.*;


public class PlainJavamailSender {
	
	//static String to= "sdass@drf.com";
	//https://www.tutorialspoint.com/java/java_sending_email.htm
	static String to="subra.dass@gmail.com";
	static String from="noreply@drf.com";

	
	public static void main(String[] args){
		
		System.out.println("Sending mail begins . . .");
		Properties props = System.getProperties();
		Properties ownprops = new Properties();
		try {
			ownprops.load(new BufferedInputStream(new FileInputStream(("c:/apps/appdata/my/propertyfile.txt"))));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		System.out.println("---" +  ownprops.getProperty("mail.smtp.host"));
		props.setProperty("mail.smtp.host", ownprops.getProperty("mail.smtp.host"));  //silly

	   // props.setProperty("mail.smtp.port", "25");
	    Session session = Session.getDefaultInstance(props);
	    
	    MimeMessage message = new MimeMessage(session);
	    try {
			message.setFrom(new InternetAddress(from) );
			message.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
			message.setSubject("This is test javamail api 14");
			message.setText("This is in the body of the message.\n");
			Transport.send(message);
			System.out.println("success sending mail");
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
			

	}
}

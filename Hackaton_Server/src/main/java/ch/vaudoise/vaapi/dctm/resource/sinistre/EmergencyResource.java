
package ch.vaudoise.vaapi.dctm.resource.sinistre;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

import ch.vaudoise.vaapi.dctm.model.DeclarationEau;
import ch.vaudoise.vaapi.dctm.model.Emergency;

public class EmergencyResource {
	
   final static String username = "vaudoiseassistance";
   final static String from = "vaudoiseassistance@gmail.com";
   final static String password = "Hackaton";


   public static void sendMailWater(Emergency emergency) {

		 Properties props = System.getProperties();
	        String host = "smtp.gmail.com";
	        props.put("mail.smtp.starttls.enable", "true");
	        props.put("mail.smtp.host", host);
	        props.put("mail.smtp.user", username);
	        props.put("mail.smtp.password", password);
	        props.put("mail.smtp.port", "587");
	        props.put("mail.smtp.auth", "true");

	        Session session = Session.getDefaultInstance(props);
	        MimeMessage message = new MimeMessage(session);

	        try {
	            message.setFrom(new InternetAddress(from));

	            message.addRecipient(Message.RecipientType.TO, new InternetAddress("testuz@gmail.com"));
	            message.setSubject("Urgence");
	            message.setText("Suite à un dégat d'eau Monsieur "+emergency.getName()+" à besoin d'un plombier à son adresse "+emergency.getAdress());
	            Transport transport = session.getTransport("smtp");
	            transport.connect(host, username, password);
	            transport.sendMessage(message, message.getAllRecipients());
	            transport.close();
	        }
	        catch (AddressException ae) {
	            ae.printStackTrace();
	        }
	        catch (MessagingException me) {
	            me.printStackTrace();
	        }
	  
   }

}

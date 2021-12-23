package telecommunication.customersimportal.util;

import java.io.IOException;
import java.util.Date;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.stereotype.Component;

@Component
public class SendMailAPI {

	public static boolean sendmail(String to, String text, String userEmail, String userPassword)
			throws AddressException, MessagingException, IOException {
		boolean send = false;
		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");
		final String username = userEmail;
		final String password = userPassword;
		// Get the Session object.
		Session session = Session.getInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		});
		Message msg = new MimeMessage(session);
		msg.setFrom(new InternetAddress(username, false));

		msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
		msg.setSubject("!!!...Your Birthday Notification..!!!");
		msg.setContent(text, "text/html");
		msg.setSentDate(new Date());
		Transport.send(msg);
		System.out.println("Mail send Suceesfully:------------");
		send = true;
		return send;
	}
}
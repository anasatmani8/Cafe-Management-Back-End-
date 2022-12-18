package atmani.utils;

import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

//import java.util.Properties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailUtils {

	@Autowired
	private JavaMailSender emailSender;

	@Autowired
	public JavaMailSenderImpl mailSssss = new JavaMailSenderImpl();

	public void sendSimpleMessage(String subject, String text, List<String> list) {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setFrom("anasatmani8@gmail.com");

		message.setSubject(subject);
		message.setText(text);
		if (list != null && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				String To = list.get(i);
				System.out.println(To);
				message.setTo(To);
			}

		}
		System.out.println(list.toString());
		// message.setCc(getCcArray(list));

		emailSender.send(message);
	}

	public void forgotMail(String to, String subject, String password) throws MessagingException {
		MimeMessage message = emailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message, true);
		helper.setFrom("anasatmani8@gmail.com");
		helper.setTo(to);
		helper.setSubject(subject);
		String htmlMsg = "<p><b>Your Login details for Cafe Management System</b><br><b>Email: </b> " + to
				+ " <br><b>Password: </b> " + password
				+ "<br><a href=\"http://localhost:4200/\">Click here to login</a></p>";
		message.setContent(htmlMsg, "text/html");
		emailSender.send(message);

	}

	/*
	 * private String[] getCcArray(List<String> ccList) { String[] cc = new
	 * String[ccList.size()]; for (int i = 0; i < ccList.size(); i++) { cc[i] =
	 * ccList.get(i);
	 * 
	 * } return cc; }
	 */

	/*
	 * public JavaMailSender getJavaMailSender() { JavaMailSenderImpl mailSender =
	 * new JavaMailSenderImpl(); mailSender.setHost("smtp.gmail.com");
	 * mailSender.setPort(587);
	 * 
	 * mailSender.setUsername("anasatmani8@gmail.com"); mailSender.setPassword("");
	 * 
	 * Properties props = mailSender.getJavaMailProperties();
	 * props.put("mail.transport.protocol", "smtp"); props.put("mail.smtp.auth",
	 * "true"); props.put("mail.smtp.starttls.enable", "true");
	 * props.put("mail.debug", "true");
	 * 
	 * return mailSender; }
	 */
}

package com.shubh.VaccineNotifire;




import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.*;
import javax.mail.internet.InternetAddress;

//@Service
public final class EmailService {

	public static String mailConfigDir;
	public static String sender = "noreply.Vaccination@amdocs.com";


	public void setMailConfigDir(String mailConfigDir) {
		this.mailConfigDir = mailConfigDir;
	}

	public String getMailConfigDir() {
		return mailConfigDir;
	}

	public void sendMultiEmail(Mail mail) {

		fetchConfig();

			//EmailEnable isenable=enableRepo.getOne(1L);
			if(true){
				Properties p=new Properties();
				p.setProperty("mail.smtp.host","umg.crop.amdocs.com:587");
				p.setProperty("mail.from","operscr@procst05.unix.gsm1900.org");
				Session session = Session.getDefaultInstance(p, null);
				MimeMessage message = new MimeMessage(session);
				try {
					message.setFrom(mail.getFromEmailAddr());
					message.addRecipients(Message.RecipientType.TO,
							mail.getToEmailAddrAsArray());
					message.setSubject(mail.getSubject());

					BodyPart messageBodyPart = new MimeBodyPart();
					// Fill the message
					messageBodyPart.setContent(mail.getBody(), "text/html");
					Multipart multipart = new MimeMultipart();
					multipart.addBodyPart(messageBodyPart);
					message.setContent(multipart);

					Transport.send(message);
				} catch (MessagingException ex) {
					ex.printStackTrace();
				}
			}else{
				System.out.println("email is not enable");
			}
	}

	private Properties fMailServerConfig = new Properties();

	private void fetchConfig() {

			fMailServerConfig.setProperty("mail.smtp.host","umg.crop.amdocs.com:587");
			fMailServerConfig.setProperty("mail.from","operscr@procst05.unix.gsm1900.org");
	}
	
	
		public boolean sendMail(String msh,List<InternetAddress> to,String message){
			Mail mail = new Mail();
			
			String format = null;

			mail.setSubject("Vaccination Notification ");
			format =getOkMessage("msg",message,null);

			try {
				mail.setFromEmailAddr(new InternetAddress(sender));
				List<InternetAddress> toEmailAddr = new ArrayList<>();
				toEmailAddr.addAll(to);
				mail.setToEmailAddr(toEmailAddr);

				mail.setBody(format);


				setMailConfigDir("com/amdocs/api_test_ms_v1/utils/mailconfig");

				System.out.println("mail body");
				System.out.print(format);
				sendMultiEmail(mail);


				return true;
			} catch (AddressException e) {
				throw new RuntimeException(e);
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}
		}
		

	private String getOkMessage(String heading,String message,String env) {
		StringBuilder emailBody =new StringBuilder();
		emailBody.append("<html><body>");
		emailBody.append("<p>Hi,</p> \n \n \n");
		emailBody.append("<p>Api Id : "+heading+"</p>\n");
		emailBody.append("<p>Falls at environment "+env+" on "+new Date()+"</p>\n");
		emailBody.append("<p>URL :<a>"+heading+"</a></p>\n");
		emailBody.append("<p>Exception :"+message+"</p>\n");
		emailBody.append("<p>Bot will no longer send failure report till API is healthy again."+"</p>\n");
		emailBody.append("<p>In case you want to manually enable reports again please use below </p>"+"\n");
		emailBody.append("<a>"+heading+"</a>\n\n\n");
		emailBody.append("<p>Thanks </p>"+"\n");
		emailBody.append("<p>Amdocs Dev-Ops Automation Bot</p>");
		emailBody.append("</html></body>");
		return emailBody.toString();
	}
}
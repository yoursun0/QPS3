package qpses.util;

import java.io.InputStream;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Date;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.log4j.Logger;

 
public class SendEmail {
	
	private static Logger logger = Logger.getLogger(SendEmail.class);
	
	private int condition;
	private String mailto;
	private List<String> mailcc = new ArrayList<String>();
	 private String mailaddr = "do_not_reply@ogcio.gov.hk"; // "qpsis@ogcio.gov.hk";
	 private String mailfrom = "pruchannel@gmail.com";
	 private String mailpwd = "12qwASzx";
	private String smtpHost = "smtp.gmail.com"; // "127.0.0.1";
	private String smtpPort = "587"; // "25";  
	private String userId;
	private String userName;
	private String userEmail;
	private String department;
	private String password;
	private String sbj;
	private String msg;
	private String sbj1 = "QPSIS alert: User %s attempts to enter wrong login password for 5 times";
	private String msg1 = "*** This is an auto-generated email, please DO NOT reply ***\n\nDear QPSIS System Administrator,\n\n"+
						 "An user with ID [%s] attempts to login the system for multiple times with incorrect password.\n"+
						 "The account information is listed below for your necessary action.\n\n"+
						 "User ID:\t\t%s\nUser Name:\t\t%s\nDepartment:\t\t%s\nEmail Address:\t\t%s\n\n\n\n";
	private String sbj2 = "QPSIS message: Recover password of User %s";
	private String msg2 = "*** This is an auto-generated email, please DO NOT reply ***\n\nDear %s,\n\n"+
						 "Per your request in QPSIS system, here is the auto-generated password for your login.\n\n"+
						 "User ID:\t\t%s\nPassword:\t\t%s\nDepartment:\t\t%s\n\n"+
						 "Please login the QPSIS system via below URL:\n\n"+
						 "https://qpsis.infocloud.gov.hk/\n\n\n\n";
	private String sbj3 = "QPSIS alert: User %s attempts to access CPAR report with invalid dates for 3 times";
	private String msg3 = "*** This is an auto-generated email, please DO NOT reply ***\n\nDear QPSIS System Administrator,\n\n"+
						  "An user with ID [%s] attempts to access CPAR for multiple times with invalid dates information.\n"+
						  "The account information is listed below for your necessary action.\n\n"+
						  "User ID:\t\t%s\nUser Name:\t\t%s\nDepartment:\t\t%s\nEmail Address:\t\t%s\n\n\n\n";
	private String sbj4 = "QPSIS alert: User %s is locked by QPSIS due to multiple invalid entry at Staff Rate Validation Challenge";
	private String msg4 = "*** This is an auto-generated email, please DO NOT reply ***\n\nDear QPSIS System Administrator,\n\n"+
						  "An user with ID [%s] attempts to perform staff rate validation challenge for 3 times with invalid dates information.\n"+
						  "The account is locked due to excessive invalid access. The account information is listed below for your necessary action.\n\n"+
						  "User ID:\t\t%s\nUser Name:\t\t%s\nDepartment:\t\t%s\nEmail Address:\t\t%s\n\n\n\n";
	
	
	public SendEmail(String sendTo, List<String> sendCC, String id, String name, String dept, String email, int cond){
		
		mailto = sendTo;
		mailcc = sendCC;
		userId = id;
		userName = name;
		department = dept;
		userEmail = email;
		condition = cond;
		if (condition == 4){
			sbj = sbj4;
			msg = msg4;
			password = email;
		}else if (condition == 3){
			sbj = sbj3;
			msg = msg3;
			password = email;
		}else if (condition == 2){
			sbj = sbj2;
			msg = msg2;
			password = email;
		}else if (condition == 1){
			sbj = sbj1;
			msg = msg1;
		}
	}
	
	public boolean send() {
		 
		// Create properties, get Session 
		Properties props = new Properties();
		
		props.put("mail.debug", "false");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", smtpHost);
		props.put("mail.smtp.port", smtpPort);
 
		
		Session session = Session.getInstance(props,
				  new javax.mail.Authenticator() {
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication(mailfrom, mailpwd);
					}
				  });
 		
 
		// Session session = Session.getInstance(props);
 
		try {
 
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(mailaddr));
			message.setRecipients(Message.RecipientType.TO,
				InternetAddress.parse(mailto));
			
			if(!mailcc.isEmpty()){
				InternetAddress[] addressCC = new InternetAddress[mailcc.size()];
				for (int i = 0; i < mailcc.size(); i++)
				{
					addressCC[i] = new InternetAddress(mailcc.get(i));
				}
				message.setRecipients(Message.RecipientType.CC,addressCC);
			}
			
			String subject=String.format(sbj, userId);
			String text="";
			if (condition == 2){
				text = String.format(msg, userName, userId, password, department);
			}else{
				text = String.format(msg, userId, userId, userName, department, userEmail);
			}
			message.setSubject(subject);
			message.setText(text);
 
			Transport.send(message);
 
			logger.debug("Finish sending email message to "+mailto);
			
			return true;
		} catch (MessagingException e) {
			logger.error("Fail to send Email due to "+e.getMessage(),e);
			return false;
		}
		
	}

}
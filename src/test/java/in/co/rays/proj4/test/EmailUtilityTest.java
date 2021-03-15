package in.co.rays.proj4.test;

import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.util.EmailMessage;
import in.co.rays.proj4.util.EmailUtility;

import javax.mail.MessagingException;

// Testcase to test EmailUtility class 
public class EmailUtilityTest {

	// Send HTML Email
	public static void testHTMLEmail() {
		try {
			EmailMessage msg = new EmailMessage();
			msg.setTo("er.pa1.sharma@gmail.com");
			msg.setSubject("Test : testHTMLEmail");
			msg.setMessage("<h1>Hello world</h1>");
			msg.setMessageType(EmailMessage.HTML_MSG);
			EmailUtility.sendMail(msg);
		} catch (ApplicationException e) {
			e.printStackTrace();
		}
	}

	// Send TEXT Email
	public static void testTextEmail() {
		try {
			EmailMessage msg = new EmailMessage();
			msg.setTo("er.pa1.sharma@gmail.com");
			msg.setSubject("Test : testTextEmail");
			msg.setMessage("<h1>Hello world</h1>");
			msg.setMessageType(EmailMessage.TEXT_MSG);
			EmailUtility.sendMail(msg);
		} catch (ApplicationException e) {
			e.printStackTrace();
		}
	}

	// Send Email to Single TO Recipient
	public static void testEmailTORecipient() {
		try {
			EmailMessage msg = new EmailMessage();
			msg.setTo("er.pa1.sharma@gmail.com");
			msg.setSubject("Test : testEmailTORecipient");
			msg.setMessage("<h1>Hello world</h1>");
			msg.setMessageType(EmailMessage.HTML_MSG);
			EmailUtility.sendMail(msg);
		} catch (ApplicationException e) {
			e.printStackTrace();
		}
	}

	// Send Email to Single CC Recipient
	public static void testEmailCCRecipient() {
		try {
			EmailMessage msg = new EmailMessage();
			msg.setCc("er.pa1.sharma@gmail.com");
			msg.setSubject("Test : testEmailCCRecipient");
			msg.setMessage("<h1>Hello world</h1>");
			msg.setMessageType(EmailMessage.TEXT_MSG);
			EmailUtility.sendMail(msg);
		} catch (ApplicationException e) {
			e.printStackTrace();
		}
	}

	// Send Email to Single BCC Recipient
	public static void testEmailBCCRecipient() {
		try {
			EmailMessage msg = new EmailMessage();
			msg.setBcc("er.pa1.sharma@gmail.com");
			msg.setSubject("Test : testEmailBCCRecipient");
			msg.setMessage("<h1>Hello world</h1>");
			msg.setMessageType(EmailMessage.HTML_MSG);
			EmailUtility.sendMail(msg);
		} catch (ApplicationException e) {
			e.printStackTrace();
		}
	}

	// Send Email to Multiple To Recipient
	public static void testEmailMultipleTORecipient() {
		try {
			EmailMessage msg = new EmailMessage();
			msg.setTo("er.pa1.sharma@gmail.com,pawan0881@gmail.com");
			msg.setSubject("Test : testEmailMultipleTORecipient");
			msg.setMessage("<h1>Hello world</h1>");
			msg.setMessageType(EmailMessage.HTML_MSG);
			EmailUtility.sendMail(msg);
		} catch (ApplicationException e) {
			e.printStackTrace();
		}
	}

	// Send Email to Multiple CC Recipient
	public static void testEmailMultipleCCRecipient() {
		try {
			EmailMessage msg = new EmailMessage();
			msg.setCc("er.pa1.sharma@gmail.com,pawan0881@gmail.com");
			msg.setSubject("Test : testEmailMultipleCCRecipient");
			msg.setMessage("<h1>Hello world</h1>");
			msg.setMessageType(EmailMessage.HTML_MSG);
			EmailUtility.sendMail(msg);
		} catch (ApplicationException e) {
			e.printStackTrace();
		}
	}

	// Send Email to Multiple BCC Recipient
	public static void testEmailMultipleBCCRecipient() {
		try {
			EmailMessage msg = new EmailMessage();
			msg.setBcc("er.pa1.sharma@gmail.com,pawan0881@gmail.com");
			msg.setSubject("Test : testEmailMultipleBCCRecipient");
			msg.setMessage("<h1>Hello world</h1>");
			msg.setMessageType(EmailMessage.HTML_MSG);
			EmailUtility.sendMail(msg);
		} catch (ApplicationException e) {
			e.printStackTrace();
		}
	}
	
	// Main method to call test methods.
	public static void main(String[] args) {
		testHTMLEmail();
		testTextEmail();
		testEmailTORecipient();
		testEmailCCRecipient();
		testEmailBCCRecipient();
		testEmailMultipleTORecipient();
		testEmailMultipleCCRecipient();
		testEmailMultipleBCCRecipient();
	}

}

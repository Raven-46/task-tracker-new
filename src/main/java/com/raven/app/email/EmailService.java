package com.raven.app.email;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.activation.DataSource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.mail.util.ByteArrayDataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import com.raven.app.items.Item;
import com.raven.app.tasks.Task;
import com.raven.app.users.User;
import com.raven.app.users.UserRepo;

@Service
public class EmailService
{
	@Autowired
	private JavaMailSender javaMailSender;
	
	@Autowired
	UserRepo userRepo;

	public EmailService(JavaMailSender javaMailSender)
	{
		this.javaMailSender = javaMailSender;
	}
	
	public void sendOtp(User user) throws MailException
	{
		SimpleMailMessage mail = new SimpleMailMessage();
		mail.setTo(user.getEmail());
		mail.setFrom("pranavbalaji2@gmail.com");
		mail.setSubject("Your OTP for Tracker Trigger Private Limited");
		mail.setText("Your OTP is: " + user.getSecret());
		
		javaMailSender.send(mail);
	}
	
	public void sendTask(Task task) throws MailException, MessagingException
	{
		MimeMessage msg = javaMailSender.createMimeMessage();

        MimeMessageHelper helper = new MimeMessageHelper(msg, true);

        helper.setTo(userRepo.findByUsername(task.getUsername()).orElse(null).getEmail());
        helper.setSubject("Due Task");
		helper.setText(
				"<h3>Dear " + task.getUsername()+ ",</h3><h3> your task in category: " + task.getCategory() + ",<h3><h1>" +
				task.getName() + "</h1><h3>Is due in <span style=\"color: #ff0000\">" + task.getRemindBefore() + "</span></h3>", true
				);
		
		javaMailSender.send(msg);
	}
	
	public void sendItems(List<Item> items, String username) throws MessagingException, DocumentException
	{
		
		StringBuffer sb = new StringBuffer(username + "'s wish list:\n");
		List<String> categories = new ArrayList<String>();
		
		items.forEach(
					item -> 
					{
						if(!categories.contains(item.getCategory()))
							categories.add(item.getCategory());
					});
		categories.forEach(
				category -> 
				{
					sb.append("\n" + category + ":\n");
					items.forEach(
							item -> 
							{
								if(item.getCategory().equals(category))
									sb.append(item.getName() + " - " + item.getQuantity() + "\n");
							});
				});
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		MimeMessage msg = javaMailSender.createMimeMessage();

        MimeMessageHelper helper = new MimeMessageHelper(msg, true);

        helper.setTo(userRepo.findByUsername(username).orElse(null).getEmail());
        helper.setSubject("Wish List");
        helper.setText("<h3> Dear " + username + ", Here is your Wish List:</h3>", true);
        
        Document document = new Document();
        PdfWriter.getInstance(document, outputStream);
         
        document.open();
         
        document.addTitle("Items PDF");
 //       document.addSubject("Testing email PDF");
 //       document.addKeywords("iText, email");
 //       document.addAuthor("Jee Vang");
 //       document.addCreator("Jee Vang");
         
        Paragraph paragraph = new Paragraph();
        paragraph.add(new Chunk(sb.toString()));
        document.add(paragraph);
         
        document.close();

        DataSource source = new ByteArrayDataSource(outputStream.toByteArray(), "application/pdf");
        helper.addAttachment("items.pdf", source);

        javaMailSender.send(msg);
	}
	
}

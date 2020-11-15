package com.raven.app.email;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

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
	
	public void sendTask(Task task) throws MailException
	{
		SimpleMailMessage mail = new SimpleMailMessage();
		mail.setTo(userRepo.findByUsername(task.getUsername()).get().getEmail());
		mail.setFrom("pranavbalaji2@gmail.com");
		mail.setSubject("Due tasks");
		mail.setText(
				"Dear " + task.getUsername() + ", your task in category " + task.getCategory() + ": " +
				task.getName() + " is due in " + task.getRemindBefore() + " seconds"
				);
		
		javaMailSender.send(mail);
	}
	
	public void sendItems(List<Item> items, String username)
	{
		SimpleMailMessage mail = new SimpleMailMessage();
		mail.setTo(userRepo.findByUsername(username).get().getEmail());
		mail.setFrom("pranavbalaji2@gmail.com");
		mail.setSubject("Your items");
		StringBuffer sb = new StringBuffer("Dear " + username + ", your items are:\n");
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
		mail.setText(sb.toString());
		
		javaMailSender.send(mail);
	}
	
}

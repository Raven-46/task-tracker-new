package com.raven.app.items;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.itextpdf.text.DocumentException;
import com.raven.app.email.EmailService;
import com.raven.app.users.User;
import com.raven.app.users.UserRepo;

@Service
public class ItemService
{
	@Autowired
	UserRepo userRepo;
	
	@Autowired
	ItemRepo itemRepo;
	
	@Autowired
	EmailService emailService;
	
	SimpleDateFormat stf = new SimpleDateFormat("HH:mm:ss");
	
	@Scheduled(cron = "0 0 * * * *")
	public void itemScheduling() throws ParseException
	{
		System.out.println(stf.format(new Date()));
		List<User> user = userRepo.findByTime(stf.format(new Date()));
		user.forEach(recipient -> 
						{
							System.out.println(recipient.getUsername());
							List<Item> items = itemRepo.findAllByUsername(recipient.getUsername());
							try {
								emailService.sendItems(items, recipient.getUsername());
							} catch (MessagingException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (DocumentException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						});
	}
}

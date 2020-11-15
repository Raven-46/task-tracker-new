package com.raven.app.items;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

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
							emailService.sendItems(items, recipient.getUsername());
						});
	}
}

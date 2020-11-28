package com.raven.app.tasks;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.raven.app.email.EmailService;

@Component
public class TaskService
{
	@Autowired
	TaskRepo taskRepo;
	
	@Autowired
	EmailService emailService;
	
	@Scheduled(cron = "*/15 * * * * *")
	public void taskScheduling() throws ParseException
	{
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
		
		Calendar cl = Calendar. getInstance();
		
		List <Task> list = taskRepo.findRequiredTasks();
		list.forEach(task ->
		{
			try {
					Date date =  format.parse(task.getDate());
					System.out.println("Date " + task.getUsername() + " said: " + date);
					cl.setTime(date);
					if(task.getRemindBefore().contains("mins"))
						cl.add(Calendar.MINUTE, -Integer.parseInt(task.getRemindBefore().substring(0, task.getRemindBefore().length() - 5)));
					else if(task.getRemindBefore().contains("hours"))
						cl.add(Calendar.HOUR, -Integer.parseInt(task.getRemindBefore().substring(0, task.getRemindBefore().length() - 6)));
					else if(task.getRemindBefore().contains("weeks"))
						cl.add(Calendar.WEEK_OF_YEAR, -Integer.parseInt(task.getRemindBefore().substring(0, task.getRemindBefore().length() - 6)));
					else if(task.getRemindBefore().contains("months"))
						cl.add(Calendar.MONTH, -Integer.parseInt(task.getRemindBefore().substring(0, task.getRemindBefore().length() - 7)));
					System.out.println(cl.getTime());
					
					if (new Date().compareTo(cl.getTime()) > 0)
					{
						System.out.println("Now is: " + new Date());
			            System.out.println("Now is after WhenToRemind");
			            emailService.sendTask(task);
			            task.setReminded(true);
			            taskRepo.save(task);
			        } 
			} catch (ParseException e) {
				e.printStackTrace();
			} catch (MailException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (MessagingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
	}
	
}

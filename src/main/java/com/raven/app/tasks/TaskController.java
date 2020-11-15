package com.raven.app.tasks;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.raven.app.filters.JwtRequestFilter;

@RestController
public class TaskController
{
	@Autowired
	JwtRequestFilter jwtRequestFilter;
	
	@Autowired
	TaskRepo taskRepo;
	
	@GetMapping("/tasks")
	public List<Task> getTasks()
	{
		return(taskRepo.findByUsername(jwtRequestFilter.getJwtUsername()));
	}
	
	@PostMapping("/tasks")
	public void addTask(@RequestBody TaskRequest taskRequest)
	{
		Task task = new Task();
		task.setUsername(jwtRequestFilter.getJwtUsername());
		task.setDone(false);
		task.setReminded(false);
		task.setCategory(taskRequest.getCategory());
		task.setDate(taskRequest.getDate());
		task.setName(taskRequest.getName());
		task.setRemindBefore(taskRequest.getRemindBefore());
		task.setRequired(taskRequest.isRequired());
		taskRepo.save(task);
	}
	
	@PutMapping("/tasks/{id}")
	public void updateTask(@PathVariable int id, @RequestBody TaskRequest taskRequest)
	{
		taskRepo.findById(id).ifPresentOrElse(
				task -> 
				{
					if(task.getUsername().equals(jwtRequestFilter.getJwtUsername()))
					{
						task.setCategory(taskRequest.getCategory());
						task.setDate(taskRequest.getDate());
						task.setName(taskRequest.getName());
						task.setRemindBefore(taskRequest.getRemindBefore());
						taskRepo.save(task);
					}
					else
						System.out.println("Not Authorised to update task");
				},
				() -> System.out.println("No task found 1"));
	}
	
	@DeleteMapping("/tasks/{id}")
	public void deleteTask(@PathVariable int id)
	{
		taskRepo.findById(id).ifPresentOrElse(
				task -> 
				{
					if(task.getUsername().equals(jwtRequestFilter.getJwtUsername()))
					{
						taskRepo.delete(task);
					}
					else
						System.out.println("Not Authorised to update task");
				},
				() -> System.out.println("No task found 2"));
	}
	
	
	@PutMapping("/tasks/{id}/sendReminder")
	public void setIfReminderRequired(@PathVariable int id, @RequestBody boolean required)
	{
		taskRepo.findById(id).ifPresentOrElse(
				task -> 
				{
					if(task.getUsername().equals(jwtRequestFilter.getJwtUsername()))
					{
						task.setRequired(required);
						taskRepo.save(task);
					}
					else
						System.out.println("No task found");
				},
				() -> System.out.println("No task found"));
	}
	
	@PutMapping("/tasks/{id}/done")
	public void setDone(@PathVariable int id)
	{
		taskRepo.findById(id).ifPresentOrElse(
				task -> 
				{
					if(task.getUsername().equals(jwtRequestFilter.getJwtUsername()))
					{
						task.setDone(task.isDone() ? false : true);
						taskRepo.save(task);
					}
					else
						System.out.println("Not task found");
				},
				() -> System.out.println("No task found"));
	}
}

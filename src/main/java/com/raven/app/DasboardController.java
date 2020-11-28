package com.raven.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.raven.app.filters.JwtRequestFilter;

@Controller
public class DasboardController
{
	@Autowired
	JwtRequestFilter jwtRequestFilter;
	
	@RequestMapping("my/{page}")
	public String getPage(@PathVariable String page)
	{
		System.out.println("hello");
		switch(page) {
		  case "items":
			System.out.println("in /my/items");
			return "items.html";
		  case "tasks":
			System.out.println("in /my/tasks");
			return "tasks.html";
		  case "diary":
			System.out.println("in /my/diary");
			return "diary.html";
		  case "friends":
			System.out.println("in /my/friends");
			return "friends.html";
		  default:
			System.out.println("in default");
			return "homepage.html";
		}
	}
	@GetMapping("/get-username")
	@ResponseBody
	public String getUsername()
	{
		System.out.println(jwtRequestFilter.getJwtUsername());
		return jwtRequestFilter.getJwtUsername();
	}
}

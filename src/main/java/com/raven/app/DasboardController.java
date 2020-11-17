package com.raven.app;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class DasboardController
{
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
		  default:
			System.out.println("in default");
			return "homepage.html";
		}
	}
}

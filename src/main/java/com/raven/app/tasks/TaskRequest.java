package com.raven.app.tasks;

public class TaskRequest
{
	private String category;
	
	private String name;

	private String date;
	
	private int remindBefore;
	
	private boolean required;
	
	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public int getRemindBefore() {
		return remindBefore;
	}

	public void setRemindBefore(int remindBefore) {
		this.remindBefore = remindBefore;
	}

	public boolean isRequired() {
		return required;
	}

	public void setRequired(boolean required) {
		this.required = required;
	}

}

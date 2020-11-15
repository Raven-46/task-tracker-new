package com.raven.app.tasks;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="tasks")
public class Task
{
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="taskid")
	private Integer id;

	@Column(name="username")
	private String username;
	
	@Column(name="category")
	private String category;

	@Column(name="name")
	private String name;

	@Column(name="timedue")
	private String date;
	
	@Column(name="remindbefore")
	private int remindBefore;
	
	@Column(name="done")
	private boolean done;
	
	@Column(name="reminded")
	private boolean reminded;
	
	@Column(name="reminder_required")
	private boolean required;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

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
	
	public boolean isDone() {
		return done;
	}

	public void setDone(boolean done) {
		this.done = done;
	}

	public boolean isReminded() {
		return reminded;
	}

	public void setReminded(boolean reminded) {
		this.reminded = reminded;
	}

	public boolean isRequired() {
		return required;
	}

	public void setRequired(boolean required) {
		this.required = required;
	}

}

package com.raven.app.diaries;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity(name = "diaries")
public class Diary
{
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="DiaryID")
	private int id;

	@Column(name="Username")
	private String username;
	
	@Column(name="Date")
	private String date;
	
	@Column(name="Content")
	private String content;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
}

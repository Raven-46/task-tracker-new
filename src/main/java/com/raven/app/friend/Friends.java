package com.raven.app.friend;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "friends")
public class Friends {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="Friendshipid")
	private int friendshipid;
	
	@Column(name="Username1")
	private String username1;
	
	@Column(name="Username2")
	private String username2;
	
	@Column(name="Status")
	private int status;
	
	public int getFriendshipid() {
		return friendshipid;
	}
	public void setFriendshipid(int friendshipid) {
		this.friendshipid = friendshipid;
	}
	public String getUsername1() {
		return username1;
	}
	public void setUsername1(String username1) {
		this.username1 = username1;
	}
	public String getUsername2() {
		return username2;
	}
	public void setUsername2(String username2) {
		this.username2 = username2;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	@Override
	public String toString() {
		return "Friends [friendshipid=" + friendshipid + ", username1=" + username1 + ", username2=" + username2
				+ ", status=" + status + "]";
	}
	
}

package com.raven.app.friend;

public class FriendResponse
{
	private String username;
	private String email;
	private int status;
	private int friendshipid;
	
	public FriendResponse(String username, String email, int status, int friendshipid)
	{
		this.username = username;
		this.email = email;
		this.status = status;
		this.friendshipid = friendshipid;
	}
	
	public int getFriendshipid() {
		return friendshipid;
	}

	public void setFriendshipid(int friendshipid) {
		this.friendshipid = friendshipid;
	}

	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	
}

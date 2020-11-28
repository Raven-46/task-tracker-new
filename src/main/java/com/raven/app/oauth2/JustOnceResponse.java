package com.raven.app.oauth2;

public class JustOnceResponse
{
	private String message;

	public JustOnceResponse(String message)
	{
		this.message = message;
	}
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}

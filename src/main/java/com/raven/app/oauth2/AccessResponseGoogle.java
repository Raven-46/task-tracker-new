package com.raven.app.oauth2;

import org.springframework.stereotype.Component;

@Component
public class AccessResponseGoogle
{
	private String access_token;
	private String exipres_in;
	private String scope;
	private String token_type;
	private String id_token;
	
	public String getAccess_token() {
		return access_token;
	}
	public void setAccess_token(String access_token) {
		this.access_token = access_token;
	}
	public String getExipres_in() {
		return exipres_in;
	}
	public void setExipres_in(String exipres_in) {
		this.exipres_in = exipres_in;
	}
	public String getScope() {
		return scope;
	}
	public void setScope(String scope) {
		this.scope = scope;
	}
	public String getToken_type() {
		return token_type;
	}
	public void setToken_type(String token_type) {
		this.token_type = token_type;
	}
	public String getId_token() {
		return id_token;
	}
	public void setId_token(String id_token) {
		this.id_token = id_token;
	}
}

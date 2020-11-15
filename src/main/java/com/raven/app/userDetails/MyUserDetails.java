package com.raven.app.userDetails;

import java.util.Arrays;
import java.util.Collection;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.raven.app.users.User;

@SuppressWarnings("serial")
public class MyUserDetails implements UserDetails
{
	private String username;
	private String password;
//	private boolean active;
//    private Collection<GrantedAuthority> roles;
	private String phone;
	private String email;
	private String profession;
	private String secret;
	
	public MyUserDetails(User user)
	{
		this.username = user.getUsername();
		this.password = user.getPassword();
		this.phone = user.getPhone();
		this.email = user.getEmail();
		this.profession = user.getProfession();
		this.secret = user.getSecret();
/*		this.active = userModel.isActive();
		this.roles = Arrays.stream(userModel.getRoles().split(","))
							.map(SimpleGrantedAuthority::new)
							.collect(Collectors.toList()); */
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities()
	{
		return Arrays.asList(new SimpleGrantedAuthority("ROLE_USER"));
	}

	@Override
	public String getPassword()
	{
		return password;
	}

	@Override
	public String getUsername()
	{
		return username;
	}

	@Override
	public boolean isAccountNonExpired()
	{
		return true;
	}

	@Override
	public boolean isAccountNonLocked()
	{
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired()
	{
		return true;
	}

	@Override
	public boolean isEnabled()
	{
		return true;
	}
	
	public String getPhone() {
		return phone;
	}

	public String getEmail() {
		return email;
	}

	public String getProfession() {
		return profession;
	}

	public String getSecret() {
		return secret;
	}
	
}

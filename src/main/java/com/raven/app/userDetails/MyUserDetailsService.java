package com.raven.app.userDetails;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.raven.app.users.User;
import com.raven.app.users.UserRepo;

@Service
public class MyUserDetailsService implements UserDetailsService
{
	@Autowired
	UserRepo userRepo;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
	{
		Optional<User> user = userRepo.findByUsername(username);
		
		UserDetails userDetails = user.map(MyUserDetails::new).get();
		
		return userDetails;
	}
	
}

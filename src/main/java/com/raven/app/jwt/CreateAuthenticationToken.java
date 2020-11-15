package com.raven.app.jwt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import com.raven.app.users.UserRepo;


@Service
public class CreateAuthenticationToken
{
	private String jwt;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtService jwtService;
	
	@Autowired
	UserDetailsService userDetailsService;
	
	@Autowired
	UserRepo userRepo;
	
	public ResponseEntity<?> createAuthenticationTokenNormal(AuthenticationRequest authenticationRequest, boolean first) throws Exception
	{
		if(userRepo.findByUsername(authenticationRequest.getUsername()).get().getSignup() != 1)
			return ResponseEntity
		            .status(HttpStatus.LOCKED)
		            .body("Error");
		if(first==false)
		{
			try
			{
				authenticationManager.authenticate
				(
					new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword())
				);
			} 
			catch (BadCredentialsException e) 
			{
				System.out.println("bad creds");
				throw new Exception("Incorrect username or password");
			}
			
		}
		System.out.println("1: done");
		UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());

		System.out.println(userDetailsService.loadUserByUsername(authenticationRequest.getUsername()));
		jwt = jwtService.generateToken(userDetails);
		System.out.println("jwt is " + jwt);
		
		return ResponseEntity.ok(new AuthenticationResponse(jwt));
	}
	
//	public String generateOtp()
//	{
//		System.out.println("generated: " + jwt.substring(jwt.length() - 4));
//		
//		return jwt.substring(jwt.length() - 4);
//	}
	
//	public boolean validateOtp(String username, String otp)
//	{
//		
//		Optional<User> user = userRepo.findByUsername(username);
//		System.out.println(user.map(o -> o.getSecret()).get());
//		System.out.println(otp);
//		if(user.map(o -> o.getSecret()).get().equals(otp))
//		{
//			System.out.println("valid");
//			return true;
//		}
//		System.out.println("invalid");
//		return false;
//	}
	public ResponseEntity<?> createAuthenticationTokenSocial(String username) throws Exception
	{
		System.out.println("1: done");
		UserDetails userDetails = userDetailsService.loadUserByUsername(username);

		System.out.println(userDetailsService.loadUserByUsername(username));
		jwt = jwtService.generateToken(userDetails);
		System.out.println("jwt is " + jwt);
		
		return ResponseEntity.ok(new AuthenticationResponse(jwt));
	}
	
}
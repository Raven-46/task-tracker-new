package com.raven.app;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.ThreadLocalRandom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.raven.app.email.EmailService;
import com.raven.app.filters.JwtRequestFilter;
import com.raven.app.jwt.AuthenticationRequest;
import com.raven.app.jwt.CreateAuthenticationToken;
import com.raven.app.jwt.SignUpRequest;
import com.raven.app.users.User;
import com.raven.app.users.UserRepo;

@Service
public class SignUpService
{
	@Autowired
	AuthenticationRequest authenticationRequest;
	
	@Autowired
	CreateAuthenticationToken createToken;
	
	@Autowired
	JwtRequestFilter jwtRequestFilter;
	
	@Autowired
	UserRepo userRepo;
	
	@Autowired
	EmailService emailService;
	
	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	public String signUp(User user, int mode) throws Exception
	{
		
		if(userRepo.findByUsername(user.getUsername()).isPresent())
			return "Sorry username already exists";
		else if(userRepo.findByEmail(user.getEmail()).isPresent())
			return "Sorry, an account with this email already exists";
		System.out.println(user.getUsername());
		user.setSecret(String.valueOf(ThreadLocalRandom.current().nextInt(100000, 1000000)));
		user.setSignup(-1);
		user.setDate(format.format(new Date()));
		userRepo.save(user);
		
		if(mode == 1)
			emailService.sendOtp(user);
		return "confirm otp";
	}
	
	public ResponseEntity<?> confirmOtp(SignUpRequest signUpRequest) throws Exception
	{
		System.out.println(signUpRequest.getUsername());
		User user = userRepo.findByUsername(signUpRequest.getUsername()).get();
		System.out.println(user);
		Calendar cl = Calendar. getInstance();
		Date date =  format.parse(user.getDate());
		cl.setTime(date);
		cl.add(Calendar.MINUTE, 5);
		if (new Date().compareTo(cl.getTime()) > 0)
			{
				userRepo.delete(user);
				return ResponseEntity
					.status(HttpStatus.FORBIDDEN)
		            .body("Timeout");
			}
		
		System.out.println(signUpRequest.getOtp());
		if(user.getSecret().equals(signUpRequest.getOtp()))
		{
			System.out.println("valid");
			user.setSignup(1);
			userRepo.save(user);
			authenticationRequest.setUsername(signUpRequest.getUsername());
			authenticationRequest.setPassword(signUpRequest.getPassword());
			return createToken.createAuthenticationTokenNormal(authenticationRequest, true);
		}
		System.out.println("invalid");
		return ResponseEntity
	            .status(HttpStatus.FORBIDDEN)
	            .body("Wrong OTP");
//		if(createToken.validateOtp(signUpRequest.getUsername(), signUpRequest.getOtp()))
//		{
//			authenticationRequest.setUsername(signUpRequest.getUsername());
//			authenticationRequest.setPassword(signUpRequest.getPassword());
//			return createToken.createAuthenticationTokenNormal(authenticationRequest, true);
//		}
//		
//		return ResponseEntity
//	            .status(HttpStatus.FORBIDDEN)
//	            .body("Wrong OTP");
		
	}
}

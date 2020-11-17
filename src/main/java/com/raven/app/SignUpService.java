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
	
	public ResponseEntity<?> signUp(User user, int mode) throws Exception
	{
		System.out.println(user.getUsername());
		System.out.println(user.getPassword());
		System.out.println(user.getEmail());
		System.out.println(user.getPhone());
		if(userRepo.findByUsername(user.getUsername()).isPresent())
			return ResponseEntity.ok(0);
		else if(userRepo.findByEmail(user.getEmail()).isPresent())
			return ResponseEntity.ok(1);
		
		user.setSecret(String.valueOf(ThreadLocalRandom.current().nextInt(100000, 1000000)));
		user.setSignup(-1);
		user.setDate(format.format(new Date()));
		user.setTime("09:00:00");
		userRepo.save(user);
		
		if(mode == 1)
			emailService.sendOtp(user);
		return ResponseEntity.ok(2);
	}
	
	public ResponseEntity<?> confirmOtp(SignUpRequest signUpRequest) throws Exception
	{
		System.out.println(signUpRequest.getUsername());
		User user = userRepo.findByUsername(signUpRequest.getUsername()).orElse(null);
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

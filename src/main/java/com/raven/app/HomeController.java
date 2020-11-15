package com.raven.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.raven.app.jwt.AuthenticationRequest;
import com.raven.app.jwt.CreateAuthenticationToken;
import com.raven.app.jwt.SignUpRequest;
import com.raven.app.users.User;
import com.raven.app.users.UserRepo;

@Controller
public class HomeController
{ 
	@Autowired
	AuthenticationRequest authenticationRequest;
	
	@Autowired
	CreateAuthenticationToken createToken;
	
	@Autowired
	SignUpService signUpService;
	
	@Autowired
	UserRepo userRepo;
	
	@GetMapping("/")
	public String home()
	{
		System.out.println("in /");
		return "home.jsp";
	}
	
	@GetMapping("/login")
	public String getLogin()
	{
		return "login.html";
	}
	
	@PostMapping("/login")
	public ResponseEntity<?> doLogin(@RequestBody AuthenticationRequest authenticationRequest) throws Exception
	{
		User user = userRepo.findByUsername(authenticationRequest.getUsername()).get();
		if(user.getSignup()<0)
		{
			userRepo.delete(user);
			return ResponseEntity
		            .status(HttpStatus.LOCKED)
		            .body("Invalid");
		}
		return createToken.createAuthenticationTokenNormal(authenticationRequest, false);
	}
	
	@GetMapping("/signup")
	public String getSignup()
	{
		return "sign-up.jsp";
	}
	
	@PostMapping("/signup")
	@ResponseBody
	public String signUp(@RequestBody User user, @RequestParam Integer mode) throws Exception
	{
		return signUpService.signUp(user, mode);
	}
	
	@PostMapping("/confirm-otp")
	@ResponseBody
	public ResponseEntity<?> otp(@RequestBody SignUpRequest signUpRequest) throws Exception
	{
		return signUpService.confirmOtp(signUpRequest);
	} 
}

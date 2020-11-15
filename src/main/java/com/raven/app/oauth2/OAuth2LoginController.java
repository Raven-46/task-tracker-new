package com.raven.app.oauth2;


import java.util.Arrays;
import java.util.Base64;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import com.raven.app.jwt.CreateAuthenticationToken;
import com.raven.app.users.User;
import com.raven.app.users.UserRepo;

@Controller
public class OAuth2LoginController
{
	@Autowired
	UserRepo userRepo;
	
	@Autowired
	CreateAuthenticationToken createToken;
	
	@PostMapping("/just-once")
	public ResponseEntity<?> justOnce(@RequestBody JustOnceRequest justOnceRequest, @RequestParam String email) throws Exception
	{
		if(userRepo.findByEmail(email).isEmpty())
			return ResponseEntity
		            .status(HttpStatus.LOCKED)
		            .body("Error");
		if(userRepo.findByUsername(justOnceRequest.getUsername()).isPresent())
			return ResponseEntity
		            .status(HttpStatus.LOCKED)
		            .body("Username already taken");
		if(!userRepo.findByEmail(email).get().getSecret().equals(justOnceRequest.getSecret()))
		{
			return ResponseEntity
		            .status(HttpStatus.LOCKED)
		            .body("Invalid");
		}
		User newUser = userRepo.findByEmail(email).get();
		if(newUser.getSignup()>0)
			return ResponseEntity
		            .status(HttpStatus.LOCKED)
		            .body("Signup already done");
		newUser.setPhone(justOnceRequest.getPhone());
		newUser.setProfession(justOnceRequest.getProfession());
		newUser.setUsername(justOnceRequest.getUsername());
		newUser.setSignup(-newUser.getSignup());
		userRepo.save(newUser);
		
		return createToken.createAuthenticationTokenSocial(justOnceRequest.getUsername());
	}
	
	@GetMapping("/oauth/google")
	@ResponseBody
	public String nothing(@RequestParam String code) throws Exception
	{
		ResponseEntity<AccessResponseGoogle> response = null;
		RestTemplate restTemplate = new RestTemplate();
		
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

		HttpEntity<String> request = new HttpEntity<String>(headers);

		String access_token_url = "https://oauth2.googleapis.com/token";
		access_token_url += "?code=" + code;
		access_token_url += "&client_id=819121697057-npopavgt451nuc90e70hu72864hjvv3l.apps.googleusercontent.com";
		access_token_url += "&client_secret=ENTER_SECRET";
		access_token_url += "&grant_type=authorization_code";
		access_token_url += "&redirect_uri=http://localhost:8080/oauth/google";
		
		System.out.println(access_token_url);

		response = restTemplate.exchange(access_token_url, HttpMethod.POST, request, AccessResponseGoogle.class);

		System.out.println("Access Token Response ---------" + response.getBody());
		String id_token = response.getBody().getId_token();
		System.out.println(id_token);
		System.out.println(id_token.split("\\."));
		
		byte[] decodedBytes = Base64.getDecoder().decode(id_token.split("\\.")[1]);
		String decodedString = new String(decodedBytes);
		System.out.println(decodedString);
		String email = null;
		for(String attribute: decodedString.split(","))
		{
			if(attribute.startsWith("\"email\":"))
			{
				email = attribute.substring(9, attribute.length()-1);
			}
		}
		System.out.println("email is " + email);
		Optional<User> user = userRepo.findByEmail(email);
		if(user.isPresent())
		{
			if(user.get().getSignup()==2)
			{
				return createToken.createAuthenticationTokenSocial(user.get().getUsername()).toString();
			}
			else if(user.get().getSignup()==-2)
			{
				userRepo.delete(user.get());
				return "Please sign up again";
			}
			return "There is already an account with this email";
		}
		User newUser = new User();
		newUser.setEmail(email);
		newUser.setUsername(email);
		newUser.setPhone(null);
		newUser.setProfession(null);
		newUser.setPassword("SocialGoogle");
		newUser.setSignup(-2);
		newUser.setSecret(String.valueOf(ThreadLocalRandom.current().nextInt(100000, 1000000)));
		newUser.setTime("09:00:00");
		userRepo.save(newUser);
		
		System.out.println(newUser.getSecret());
		userRepo.save(newUser);
		
		return newUser.getSecret();
	}
	
	@GetMapping("/oauth/facebook")
	@ResponseBody
	public String facebook(@RequestParam String code) throws Exception
	{
		ResponseEntity<String> response = null;
		RestTemplate restTemplate = new RestTemplate();
		
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

		HttpEntity<String> request = new HttpEntity<String>(headers);

		String access_token_url = "https://graph.facebook.com/v8.0/oauth/access_token";
		access_token_url += "?code=" + code;
		access_token_url += "&client_id=403892724100855";
		access_token_url += "&client_secret=ENTER_SECRET";
		access_token_url += "&redirect_uri=http://localhost:8080/oauth/facebook";

		response = restTemplate.exchange(access_token_url, HttpMethod.GET, request, String.class);

		System.out.println("Access Token Response ---------" + response.getBody());
		
		String crude = response.getBody().split(",")[0];
		String token = crude.substring(17, crude.length()-1);
		String email_url = "https://graph.facebook.com/me?fields=email&access_token=";
		email_url += token;
		
		response = restTemplate.exchange(email_url, HttpMethod.GET, request, String.class);
		
		System.out.println("Email Response ---------" + response.getBody());
		String e1 = response.getBody().split(",")[0];
		String email = e1.substring(10, e1.length()-1).replace("\\u0040", "@");
		
		Optional<User> user = userRepo.findByEmail(email);
		if(user.isPresent())
		{
			if(user.get().getSignup()==3)
			{
				return createToken.createAuthenticationTokenSocial(user.get().getUsername()).toString();
			}
			else if(user.get().getSignup()==-3)
			{
				userRepo.delete(user.get());
				return "Please sign up again";
			}
			return "There is already an account with this email";
		}
		
		User newUser = new User();
		newUser.setEmail(email);
		newUser.setUsername(email);
		newUser.setPhone(null);
		newUser.setProfession(null);
		newUser.setPassword("SocialFacebook");
		newUser.setSignup(-3);
		newUser.setSecret(String.valueOf(ThreadLocalRandom.current().nextInt(100000, 1000000)));
		newUser.setTime("09:00:00");
		userRepo.save(newUser);
		
		return newUser.getSecret();
	}
}

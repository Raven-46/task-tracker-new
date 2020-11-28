package com.raven.app.friend;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.raven.app.filters.JwtRequestFilter;
import com.raven.app.users.User;
import com.raven.app.users.UserRepo;

@RestController
public class FriendController {
	@Autowired
	JwtRequestFilter jwtRequestFilter;
	
	@Autowired
	UserRepo userRepo;
	
	@Autowired
	FriendsRepo friendsRepo;
	
	@GetMapping("/future-friends")
	public List<FriendResponse> getFutureFriends()
	{
		List<User> users = userRepo.findAll();
		users.remove(userRepo.findByUsername(jwtRequestFilter.getJwtUsername()).orElse(null));
		List<Friends> friendShipList1 = friendsRepo.findByUsername1(jwtRequestFilter.getJwtUsername());
		List<Friends> friendShipList2 = friendsRepo.findByUsername2(jwtRequestFilter.getJwtUsername());
		List<FriendResponse> list = new ArrayList<FriendResponse>();
		
		users.forEach(u -> {
			FriendResponse friendResponse = new FriendResponse(u.getUsername(), u.getEmail(), 0, 0);
			list.add(friendResponse);
			friendShipList1.forEach(friendShip1 -> {
				if(friendShip1.getUsername2().equals(u.getUsername()))
				{
					list.remove(friendResponse);
					list.add(new FriendResponse(u.getUsername(), u.getEmail(), friendShip1.getStatus(), friendShip1.getFriendshipid()));
				}
			});
			friendShipList2.forEach(friendShip2 -> {
				if(friendShip2.getUsername1().equals(u.getUsername()))
				{
					list.remove(friendResponse);
					if(friendShip2.getStatus() == 1)
						list.add(new FriendResponse(u.getUsername(), u.getEmail(), 3,friendShip2.getFriendshipid()));
					else
						list.add(new FriendResponse(u.getUsername(), u.getEmail(), friendShip2.getStatus(),friendShip2.getFriendshipid()));
				}
			});
		});
		return list;
	}

	@GetMapping("/friends")
	public List<String> getFriends()
	{
		List<String> usernames = new ArrayList<String>();
		List<Friends> friends1 = friendsRepo.findByUsername1AndStatus(jwtRequestFilter.getJwtUsername(), 2);
		List<Friends> friends2 = friendsRepo.findByUsername2AndStatus(jwtRequestFilter.getJwtUsername(), 2);
		friends1.forEach(friend1 -> usernames.add(friend1.getUsername2()));
		friends2.forEach(friend2 -> usernames.add(friend2.getUsername1()));
		return usernames;
	}
	
	@PostMapping("/addfriend")
	public void addFriend(@RequestBody FriendRequest friendRequest)
	{
		System.out.println("In add friend: " + friendRequest.getUsername2() + " " + jwtRequestFilter.getJwtUsername());
		Friends friends = new Friends();
		friends.setUsername1(jwtRequestFilter.getJwtUsername());
		friends.setUsername2(friendRequest.getUsername2());
		friends.setStatus(1);
		friendsRepo.save(friends);
	}
	
	@PutMapping("/acceptfriend/{friendshipid}")
	public void acceptFriend(@PathVariable int friendshipid)
	{
		friendsRepo.findById(friendshipid).ifPresentOrElse(
				existingFriendship -> 
				{
					if(existingFriendship.getUsername2().equals(jwtRequestFilter.getJwtUsername()))
					{
						existingFriendship.setStatus(2);
						friendsRepo.save(existingFriendship);
					}
					else
						System.out.println("No friend found to update");
				},
				() -> System.out.println("No friend found to update"));
	}
	
	@DeleteMapping("/rejectfriend/{friendshipid}")
	public void rejectFriend(@PathVariable int friendshipid)
	{
		friendsRepo.findById(friendshipid).ifPresentOrElse(
				existingFriendship -> 
				{
					if(existingFriendship.getUsername2().equals(jwtRequestFilter.getJwtUsername()))
					{
						friendsRepo.delete(existingFriendship);
					}
					else
						System.out.println("No friend found to update");
				},
				() -> System.out.println("No friend found to update"));
	}

	@DeleteMapping("/friend/{friendshipid}")
	public void deleteFriends(@PathVariable int friendshipid)
	{
		friendsRepo.findById(friendshipid).ifPresentOrElse(
				friend -> 
				{
					if(friend.getUsername1().equals(jwtRequestFilter.getJwtUsername())||friend.getUsername2().equals(jwtRequestFilter.getJwtUsername()))
					{
						friendsRepo.delete(friend);
					}
					else
						System.out.println("No friend found to delete");
				},
				() -> System.out.println("No friend found to delete"));
	}
}

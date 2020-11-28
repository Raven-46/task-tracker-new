package com.raven.app.common;

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
import com.raven.app.friend.Friends;
import com.raven.app.friend.FriendsRepo;

@RestController
public class CommonController
{
	@Autowired
	JwtRequestFilter jwtRequestFilter;
	
	@Autowired
	CommonRepo commonRepo;
	
	@Autowired
	FriendsRepo friendsRepo;
	
	@GetMapping("/commons")
	public List<Common> getTasks()
	{
		return(commonRepo.findAllByUsername1OrUsername2(jwtRequestFilter.getJwtUsername(),jwtRequestFilter.getJwtUsername()));
	}

	@PostMapping("/common")
	public void addProduct(@RequestBody CommonRequest commonRequest)
	{
		List<Friends> friendShipList1 = friendsRepo.findByUsername1AndStatus(jwtRequestFilter.getJwtUsername(), 2);
		friendShipList1.forEach(friend -> {
			if(commonRequest.getUsername2().equals(friend.getUsername2()))
			{
				Common common = new Common();
				common.setUsername1(jwtRequestFilter.getJwtUsername());
				common.setUsername2(commonRequest.getUsername2());
				common.setCategory(commonRequest.getCategory());
				common.setName(commonRequest.getName());
				common.setPrice(commonRequest.getPrice());
				common.setQuantity(commonRequest.getQuantity());
				commonRepo.save(common);
			}
		});
		List<Friends> friendShipList2 = friendsRepo.findByUsername2AndStatus(jwtRequestFilter.getJwtUsername(), 2);
		friendShipList2.forEach(friend -> {
			if(commonRequest.getUsername2().equals(friend.getUsername1()))
			{
				Common common = new Common();
				common.setUsername1(jwtRequestFilter.getJwtUsername());
				common.setUsername2(commonRequest.getUsername2());
				common.setCategory(commonRequest.getCategory());
				common.setName(commonRequest.getName());
				common.setPrice(commonRequest.getPrice());
				common.setQuantity(commonRequest.getQuantity());
				commonRepo.save(common);
			}
		});
	}

	@PutMapping("/commons/{itemid}")
	public void updateProduct(@PathVariable int itemid, @RequestBody CommonRequest commonRequest) {
		commonRepo.findById(itemid).ifPresentOrElse(
				existingCommon -> 
				{
					if(existingCommon.getUsername1().equals(jwtRequestFilter.getJwtUsername())||existingCommon.getUsername2().equals(jwtRequestFilter.getJwtUsername()))
					{
						existingCommon.setCategory(commonRequest.getCategory());
						existingCommon.setName(commonRequest.getName());
						existingCommon.setQuantity(commonRequest.getQuantity());
						existingCommon.setPrice(commonRequest.getPrice());
						commonRepo.save(existingCommon);
					}
					else
						System.out.println("No item found to update");
				},
				() -> System.out.println("No item found to update"));

	}

	@DeleteMapping("/commons/{itemid}")
	public void deleteItem(@PathVariable int itemid)
	{
		commonRepo.findById(itemid).ifPresentOrElse(
				common -> 
				{
					if(common.getUsername1().equals(jwtRequestFilter.getJwtUsername())||common.getUsername2().equals(jwtRequestFilter.getJwtUsername()))
					{
						commonRepo.delete(common);
					}
					else
						System.out.println("No item found to delete");
				},
				() -> System.out.println("No item found to delete"));
	}
	
	@PutMapping("/commons/{itemid}/add")
	public void addQuantity(@PathVariable int itemid)
	{
		commonRepo.findById(itemid).ifPresentOrElse(
				common -> 
				{
					if(common.getUsername1().equals(jwtRequestFilter.getJwtUsername())||common.getUsername2().equals(jwtRequestFilter.getJwtUsername()))
					{
						common.setQuantity(common.getQuantity() + 1);
						commonRepo.save(common);
					}
					else
						System.out.println("No item found to add to");
				},
				() -> System.out.println("No item found to add to"));
	}
	
	@PutMapping("/commons/{itemid}/subtract")
	public void subtractQuantity(@PathVariable int itemid)
	{
		commonRepo.findById(itemid).ifPresentOrElse(
				common -> 
				{
					if(common.getUsername1().equals(jwtRequestFilter.getJwtUsername())||common.getUsername2().equals(jwtRequestFilter.getJwtUsername()))
					{
						common.setQuantity(common.getQuantity() - 1);
						commonRepo.save(common);
					}
					else
						System.out.println("No item found to subtract from");
				},
				() -> System.out.println("No item found to subtract from"));
	}
}

package com.raven.app.items;

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

@RestController
public class ItemController
{
	@Autowired
	JwtRequestFilter jwtRequestFilter;
	
	@Autowired
	ItemRepo itemRepo;
	
	@GetMapping("/items")
	public List<Item> getTasks()
	{
		return(itemRepo.findAllByUsername(jwtRequestFilter.getJwtUsername()));
	}

	@PostMapping("/item")
	public void addProduct(@RequestBody ItemRequest itemRequest)
	{
		Item item = new Item();
		item.setUsername(jwtRequestFilter.getJwtUsername());
		item.setCategory(itemRequest.getCategory());
		item.setName(itemRequest.getName());
		item.setPrice(itemRequest.getPrice());
		item.setQuantity(itemRequest.getQuantity());
		itemRepo.save(item);
	}

	@PutMapping("/items/{id}")
	public void updateProduct(@PathVariable int id, @RequestBody ItemRequest itemRequest) {
		itemRepo.findById(id).ifPresentOrElse(
				existingItem -> 
				{
					if(existingItem.getUsername().equals(jwtRequestFilter.getJwtUsername()))
					{
						existingItem.setCategory(itemRequest.getCategory());
						existingItem.setName(itemRequest.getName());
						existingItem.setQuantity(itemRequest.getQuantity());
						existingItem.setPrice(itemRequest.getPrice());
						itemRepo.save(existingItem);
					}
					else
						System.out.println("No item found to update");
				},
				() -> System.out.println("No item found to update"));

	}

	@DeleteMapping("/items/{id}")
	public void deleteItem(@PathVariable int id)
	{
		itemRepo.findById(id).ifPresentOrElse(
				item -> 
				{
					if(item.getUsername().equals(jwtRequestFilter.getJwtUsername()))
					{
						itemRepo.delete(item);
					}
					else
						System.out.println("No item found to delete");
				},
				() -> System.out.println("No item found to delete"));
	}
	
	@PutMapping("/items/{id}/add")
	public void addQuantity(@PathVariable int id)
	{
		itemRepo.findById(id).ifPresentOrElse(
				item -> 
				{
					if(item.getUsername().equals(jwtRequestFilter.getJwtUsername()))
					{
						item.setQuantity(item.getQuantity() + 1);
						itemRepo.save(item);
					}
					else
						System.out.println("No item found to add to");
				},
				() -> System.out.println("No item found to add to"));
	}
	
	@PutMapping("/items/{id}/subtract")
	public void subtractQuantity(@PathVariable int id)
	{
		itemRepo.findById(id).ifPresentOrElse(
				item -> 
				{
					if(item.getUsername().equals(jwtRequestFilter.getJwtUsername()))
					{
						item.setQuantity(item.getQuantity() - 1);
						itemRepo.save(item);
					}
					else
						System.out.println("No item found to subtract from");
				},
				() -> System.out.println("No item found to subtract from"));
	}
	
	@PutMapping("/items/{id}/{quantity}")
	public void updateQuantity(@PathVariable int id, int quantity)
	{
		itemRepo.findById(id).ifPresentOrElse(
				item -> 
				{
					if(item.getUsername().equals(jwtRequestFilter.getJwtUsername()))
					{
						item.setQuantity(quantity);
						itemRepo.save(item);
					}
					else
						System.out.println("No item found to update quantity");
				},
				() -> System.out.println("No item found to update quantity"));
	}
	
}
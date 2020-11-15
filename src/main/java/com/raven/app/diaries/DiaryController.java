package com.raven.app.diaries;

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
public class DiaryController
{
	@Autowired
	JwtRequestFilter jwtRequestFilter;
	
	@Autowired
	DiaryRepo diaryRepo;
	
	@GetMapping("/diary")
	public List<Diary> getDiary()
	{
		return(diaryRepo.findByUsername(jwtRequestFilter.getJwtUsername()));
	}
	
	@PostMapping("/diary")
	public void addDiary(@RequestBody DiaryRequest diaryRequest)
	{
		Diary diary = new Diary();
		diary.setUsername(jwtRequestFilter.getJwtUsername());
		diary.setDate(diaryRequest.getDate());
		diary.setContent(diaryRequest.getContent());
		diaryRepo.save(diary);
	}
	
	@PutMapping("/diary/{id}")
	public void updateDiary(@PathVariable int id, @RequestBody DiaryRequest diaryRequest)
	{
		diaryRepo.findById(id).ifPresentOrElse(
					entry ->
							{
								if(entry.getUsername().equals(jwtRequestFilter.getJwtUsername()))
								{
									entry.setContent(diaryRequest.getContent());
									diaryRepo.save(entry);
								}
								else
									System.out.println("Not Authorised to update diary");
							},
							() -> System.out.println("No Diary Entry found 1")
							);
	}
	
	@DeleteMapping("/diary/{id}")
	public void deleteDiary(@PathVariable int id)
	{
		diaryRepo.findById(id).ifPresentOrElse(
				entry ->
						{
							if(entry.getUsername().equals(jwtRequestFilter.getJwtUsername()))
							{
								diaryRepo.delete(entry);
							}
							else
								System.out.println("Not Authorised to delete diary");
						},
						() -> System.out.println("No Diary Entry found 2")
						);
	}
	
}

package com.raven.app.diaries;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface DiaryRepo extends JpaRepository<Diary,Integer>
{
	List<Diary> findByUsername(String username);
}

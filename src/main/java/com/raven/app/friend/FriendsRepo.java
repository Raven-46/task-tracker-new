package com.raven.app.friend;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface FriendsRepo extends JpaRepository<Friends,Integer>{
	List<Friends> findByUsername1(String username1);
	List<Friends> findByUsername2(String username2);
	List<Friends> findByUsername1OrUsername2(String username1, String username2);
	List<Friends> findByUsername1AndStatus(String username1, int status);
	List<Friends> findByUsername2AndStatus(String username2, int status);
}

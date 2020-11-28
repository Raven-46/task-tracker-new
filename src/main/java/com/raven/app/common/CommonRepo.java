package com.raven.app.common;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CommonRepo extends JpaRepository<Common,Integer>
{
	List<Common> findAllByUsername1OrUsername2(String username1, String username2);
}

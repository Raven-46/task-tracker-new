package com.raven.app.items;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepo extends JpaRepository<Item,Integer>
{
	List<Item> findAllByUsername(String username);
}
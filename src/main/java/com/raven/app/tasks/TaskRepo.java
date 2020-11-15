package com.raven.app.tasks;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
interface TaskRepo extends JpaRepository<Task, Integer>
{
	List<Task> findAll();
	
	List<Task> findByUsername(String username);
	
	@Query(value = "SELECT * FROM tasks t WHERE t.done = 0 AND t.reminder_required = 1 AND t.reminded = 0", nativeQuery = true)
	List<Task> findRequiredTasks();
	
}

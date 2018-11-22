package com.example.demo.mysql.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository  extends JpaRepository<User, Long> {

	@Query(value = "select * from user2 m where m.user_name=:username", nativeQuery = true)
	User readUser(@Param("username") String username );

	User findByUserId(String userId);
	
	User findByUserName(String userName);

}

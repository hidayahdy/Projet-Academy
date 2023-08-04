package com.talan.academy.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.talan.academy.entities.User;
import com.talan.academy.enums.ERole;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	
	Optional<User> findByEmail(String email);

	Page<User> findByRolesNameOrRolesNameOrderByCreationDateDesc(ERole role1, ERole role2, Pageable pr);

	Boolean existsByEmail(String email);
	


	public User findByVerificationCode(String code);
	
	public Page<User> findByIdNotInAndRolesNameNot(List<Long> ids,ERole role,Pageable pageable);
	
	@Query
	(value = "SELECT user FROM User user where lower(user.lastName)  LIKE CONCAT ('%', lower(:keyWord),'%') "
			+ " Or lower(user.firstName)  LIKE CONCAT ('%', lower(:keyWord),'%') "+
			 " Or lower(user.email)  LIKE CONCAT ('%', lower(:keyWord),'%') "+
			 " Or lower(user.phone)  LIKE CONCAT ('%', lower(:keyWord),'%') "+
			 " Or lower(user.linkedin)  LIKE CONCAT ('%', lower(:keyWord),'%') "+
			 "ORDER BY user.creationDate DESC")
	Page<User> findUsersByKeyWordPaginated(String keyWord,Pageable pageable );
	
}

package com.talan.academy.repositories;


import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.talan.academy.entities.Application;
import com.talan.academy.entities.User;

@Repository
public interface ApplicationRepository  extends JpaRepository<Application, Long>,JpaSpecificationExecutor<Application>{
	

	
	Page<Application> findApplicationsByUserLastNameContaining(String texte,Pageable pageable);
	Page<Application> findApplicationsByDiplomaContaining(String texte,Pageable pageable);
	Page<Application> findApplicationsByStatusContaining(String texte,Pageable pageable);
	Page<Application> findApplicationsByUserFirstNameContaining(String query, Pageable pageable);
	Page<Application> findApplicationsBySessionCursusNameContaining(String query, Pageable pageable);
	Page<Application> findByUserId(Long id,Pageable pageable);
	List<Application> findByUserId(Long id);
	int countByUserId(Long id);
	

	@Query("SELECT DISTINCT(ap.user.id) FROM Application ap  ")
	List<Long> findDistintUsersId();
	
	@Query("SELECT DISTINCT(ap.user) FROM Application ap where ap.user.roles.name='ROLE_REGISTRED' Or ap.user.roles.name='ROLE_STUDENT'  ")
	Page<User> findDistintUsers(Pageable pageable);
	



	@Query
	(value = "SELECT ap FROM Application ap where lower(ap.user.lastName)  LIKE CONCAT ('%', lower(:query),'%') "
			+ " Or lower(ap.user.lastName)  LIKE CONCAT ('%', lower(:query1),'%') " 
			+ " Or lower(ap.user.firstName)  LIKE CONCAT ('%', lower(:query1),'%') " 
			+ " Or lower(ap.user.firstName)  LIKE CONCAT ('%', lower(:query),'%') " 
			+ " Or lower(ap.session.cursus.name) LIKE CONCAT ('%', lower(:query1),'%') " 
			+ " Or lower(ap.session.cursus.name) LIKE CONCAT ('%', lower(:query),'%') " 
			  + " Or ap.status LIKE CONCAT ('%', :query1,'%') " 
			  + " Or ap.status LIKE CONCAT ('%', :query,'%') " +
			  "Or ap.diploma LIKE CONCAT ('%', :query1,'%') " +
			  "Or ap.diploma LIKE CONCAT ('%', :query,'%') " +
			  "Or ap.speciality LIKE CONCAT ('%', :query1,'%') "+
			  "Or ap.speciality LIKE CONCAT ('%', :query,'%') "+
                "ORDER BY ap.creationDate DESC")
	Page<Application> findApplicationsQuerySQL(@Param("query") String query,String query1,Pageable pageable );
	
	
	@Query
	(value = "SELECT ap FROM Application ap where ap.user.id = :id"+" And ("
			+ " lower(ap.session.cursus.name) LIKE CONCAT ('%', lower(:query1),'%') " 
			+ " Or lower(ap.session.cursus.name) LIKE CONCAT ('%', lower(:query),'%') " 
			  + " Or ap.status LIKE CONCAT ('%', :query1,'%') " 
			  + " Or ap.status LIKE CONCAT ('%', :query,'%') " +
                ") ORDER BY ap.creationDate DESC")
	Page<Application> findUserApplicationsQuerySQL(Long id , String query,String query1,Pageable pageable );
	
}

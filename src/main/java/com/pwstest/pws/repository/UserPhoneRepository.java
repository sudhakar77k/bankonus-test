package com.pwstest.pws.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.pwstest.pws.entity.UserPhone;

/**
 * @author Sudhakar.k
 *
 */
public interface UserPhoneRepository extends JpaRepository<UserPhone, Integer> {

	// Query for find all unique numbers
	@Query("SELECT up FROM UserPhone up where up.phoneNumber = :phoneNumber and up.isActive =false")
	Optional<UserPhone> getUniqPhone(String phoneNumber);

	// Query for find all phone numbers by user id
	@Query("SELECT up.phoneNumber FROM UserPhone up where up.user.id = :id")
	List<String> findAllByUserId(Integer id);

	// Query for find all phone numbers
	@Query("SELECT up.phoneNumber FROM UserPhone up")
	List<String> findAllPhone();

}

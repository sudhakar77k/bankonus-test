package com.pwstest.pws.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pwstest.pws.entity.User;

public interface UserRepository extends JpaRepository<User, Integer> {

}

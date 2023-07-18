package com.bakery.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bakery.entities.UserQuery;

public interface UserQueryRepository extends JpaRepository<UserQuery, Long> {

}

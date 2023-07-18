package com.bakery.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bakery.entities.Role;

public interface RoleRepository extends JpaRepository<Role, Integer> {

}

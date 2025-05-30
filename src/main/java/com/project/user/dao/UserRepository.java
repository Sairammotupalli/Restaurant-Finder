package com.project.user.dao;

import com.project.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // Find User by Email
    User findByEmail(String email);

    Page<User> findByRole(String role, Pageable pageable);

    Optional<User> findOptionalByEmail(String email);



}

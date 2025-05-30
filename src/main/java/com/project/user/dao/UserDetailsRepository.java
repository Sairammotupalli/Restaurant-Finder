package com.project.user.dao;

import com.project.user.entity.UserDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDetailsRepository extends JpaRepository<UserDetails, Long> {

    // Find UserDetails by User ID
    UserDetails findByUserId(Long userId);
}

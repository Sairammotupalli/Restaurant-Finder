package com.project.user.service;

import com.project.user.dao.UserRepository;
import com.project.user.entity.User;
import com.project.user.entity.UserDetails;
import com.project.user.dao.UserDetailsRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsService {

    private final UserDetailsRepository userDetailsRepository;

    @Autowired
    private UserRepository userRepository;

    public UserDetailsService(UserDetailsRepository userDetailsRepository) {
        this.userDetailsRepository = userDetailsRepository;
    }

    public UserDetails getUserDetailsByUserId(Long userId) {
        return userDetailsRepository.findByUserId(userId);
    }

//    public UserDetails saveUserDetails(UserDetails userDetails) {
//        return userDetailsRepository.save(userDetails);
//    }

    public void deleteUserDetails(Long id) {
        userDetailsRepository.deleteById(id);
    }

    @Transactional
    public void saveUserDetails(UserDetails userDetails) {
        User user = userDetails.getUser();
        if (user == null) {
            throw new IllegalArgumentException("UserDetails must be associated with a User");
        }
        userDetailsRepository.save(userDetails);
    }


}

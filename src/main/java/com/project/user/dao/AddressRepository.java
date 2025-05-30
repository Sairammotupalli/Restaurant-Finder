package com.project.user.dao;

import com.project.user.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {

    // Find Address by Restaurant ID
    Address findByRestaurantId(Long restaurantId);
}

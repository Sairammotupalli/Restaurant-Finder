package com.project.user.dao;

import com.project.user.entity.RestaurantPhoto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RestaurantPhotoRepository extends JpaRepository<RestaurantPhoto, Long> {

    // Find Photos by Restaurant ID
    List<RestaurantPhoto> findByRestaurantId(Long restaurantId);
}

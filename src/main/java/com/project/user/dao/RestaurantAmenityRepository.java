package com.project.user.dao;

import com.project.user.entity.RestaurantAmenity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RestaurantAmenityRepository extends JpaRepository<RestaurantAmenity, Long> {

    @Query("SELECT ra.amenity FROM RestaurantAmenity ra WHERE ra.restaurant.id = :restaurantId")
    List<String> getAmenitiesByRestaurantId(Long restaurantId);

    @Query("SELECT DISTINCT r.amenity FROM RestaurantAmenity r")
    List<String> findAllDistinctAmenities();

    List<RestaurantAmenity> findByRestaurantId(Long restaurantId);
}

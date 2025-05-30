package com.project.user.dao;

import com.project.user.entity.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

    // Find all reviews for a specific restaurant
    List<Review> findByRestaurantId(Long restaurantId);

    Page<Review> findByRestaurantId(Long restaurantId, Pageable pageable);

    // Find all reviews submitted by a specific user
    List<Review> findByUserId(Long userId);

    // Find reviews by rating
    List<Review> findByRating(Integer rating);
}

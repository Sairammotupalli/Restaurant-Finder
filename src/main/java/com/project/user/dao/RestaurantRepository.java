package com.project.user.dao;

import com.project.user.entity.Restaurant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {

    // Fetch restaurants based on dynamic filters using JPA Specifications
    Page<Restaurant> findAll(Specification<Restaurant> specification, Pageable pageable);

    // Fetch a restaurant by ID with related details
    @Query("SELECT r FROM Restaurant r LEFT JOIN FETCH r.photos LEFT JOIN FETCH r.reviews LEFT JOIN FETCH r.menus WHERE r.id = :id")
    Optional<Restaurant> findByIdWithDetails(@Param("id") Long id);

    // Fetch distinct cuisines
    @Query("SELECT DISTINCT r.cuisine FROM Restaurant r")
    List<String> findAllDistinctCuisines();

    // Fetch distinct price ranges
    @Query("SELECT DISTINCT r.priceRange FROM Restaurant r")
    List<String> findAllDistinctPriceRanges();

    Page<Restaurant> findByNameContainingIgnoreCase(String name, Pageable pageable);

}

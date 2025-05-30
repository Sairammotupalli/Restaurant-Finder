package com.project.user.dao;

import com.project.user.entity.RestaurantSpecialty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RestaurantSpecialtyRepository extends JpaRepository<RestaurantSpecialty, Long> {

    @Query("SELECT s.specialty FROM RestaurantSpecialty s WHERE s.restaurant.id = :restaurantId")
    List<String> getSpecialtiesByRestaurantId(@Param("restaurantId") Long restaurantId);

    @Query("SELECT DISTINCT s.specialty FROM RestaurantSpecialty s")
    List<String> findAllDistinctSpecialties();

    List<RestaurantSpecialty> findByRestaurantId(Long restaurantId);
}

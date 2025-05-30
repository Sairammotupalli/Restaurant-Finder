package com.project.user.dao;

import com.project.user.entity.RestaurantDietaryOption;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RestaurantDietaryOptionRepository extends JpaRepository<RestaurantDietaryOption, Long> {

    @Query("SELECT d.dietaryOption FROM RestaurantDietaryOption d WHERE d.restaurant.id = :restaurantId")
    List<String> getDietaryOptionsByRestaurantId(@Param("restaurantId") Long restaurantId);

    @Query("SELECT DISTINCT d.dietaryOption FROM RestaurantDietaryOption d")
    List<String> findAllDistinctDietaryOptions();

    List<RestaurantDietaryOption> findByRestaurantId(Long restaurantId);
}

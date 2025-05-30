package com.project.user.dao;

import com.project.user.entity.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MenuRepository extends JpaRepository<Menu, Long> {

    // Find Menu Items by Restaurant ID
    List<Menu> findByRestaurantId(Long restaurantId);
}

package com.project.user.dao;

import com.project.user.entity.Favorite;
import com.project.user.entity.Restaurant;
import com.project.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FavoriteRepository extends JpaRepository<Favorite, Long> {

    // Find Favorites by User ID
    List<Favorite> findByUserId(Long userId);

    // Find Favorites by Restaurant ID
    List<Favorite> findByRestaurantId(Long restaurantId);

    List<Favorite> findByUser(User user);
    boolean existsByUserAndRestaurant(User user, Restaurant restaurant);
    Favorite findByUserAndRestaurant(User user, Restaurant restaurant);
}

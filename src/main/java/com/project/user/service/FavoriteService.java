package com.project.user.service;

import com.project.user.dao.FavoriteRepository;
import com.project.user.entity.Favorite;
import com.project.user.entity.Restaurant;
import com.project.user.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FavoriteService {

    @Autowired
    private FavoriteRepository favoriteRepository;

    public List<Favorite> getFavoritesByUser(User user) {
        return favoriteRepository.findByUser(user);
    }

    public void addFavorite(User user, Restaurant restaurant) {
        if (!favoriteRepository.existsByUserAndRestaurant(user, restaurant)) {
            Favorite favorite = new Favorite(user, restaurant);
            favoriteRepository.save(favorite);
        }
    }

    public void removeFavorite(User user, Restaurant restaurant) {
        Favorite favorite = favoriteRepository.findByUserAndRestaurant(user, restaurant);
        if (favorite != null) {
            favoriteRepository.delete(favorite);
        }
    }

    public boolean isFavorite(User user, Restaurant restaurant) {
        return favoriteRepository.existsByUserAndRestaurant(user, restaurant);
    }
}

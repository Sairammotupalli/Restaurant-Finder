package com.project.user.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "restaurant_photo")
public class RestaurantPhoto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "restaurant_id", nullable = false)
    private Restaurant restaurant;

    @Column(name = "photo_url", nullable = false)
    private String photoUrl;

    // Getters, Setters, and Constructors
    public RestaurantPhoto() {}

    public RestaurantPhoto(Restaurant restaurant, String photoUrl) {
        this.restaurant = restaurant;
        this.photoUrl = photoUrl;
    }

    // getters and setters omitted for brevity

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }
}

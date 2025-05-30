package com.project.user.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "restaurant_amenity")
public class RestaurantAmenity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "restaurant_id", nullable = false)
    private Restaurant restaurant;

    @Column(nullable = false)
    private String amenity;

    // Getters, Setters, and Constructors
    public RestaurantAmenity() {}

    public RestaurantAmenity(Restaurant restaurant, String amenity) {
        this.restaurant = restaurant;
        this.amenity = amenity;
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

    public String getAmenity() {
        return amenity;
    }

    public void setAmenity(String amenity) {
        this.amenity = amenity;
    }
}

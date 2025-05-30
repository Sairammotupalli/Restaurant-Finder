package com.project.user.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "restaurant_dietary_option")
public class RestaurantDietaryOption {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "restaurant_id", nullable = false)
    private Restaurant restaurant;

    @Column(nullable = false)
    private String dietaryOption;

    // Getters, Setters, and Constructors
    public RestaurantDietaryOption() {}

    public RestaurantDietaryOption(Restaurant restaurant, String dietaryOption) {
        this.restaurant = restaurant;
        this.dietaryOption = dietaryOption;
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

    public String getDietaryOption() {
        return dietaryOption;
    }

    public void setDietaryOption(String dietaryOption) {
        this.dietaryOption = dietaryOption;
    }
}

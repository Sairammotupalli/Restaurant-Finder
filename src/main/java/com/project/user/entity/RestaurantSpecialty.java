package com.project.user.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "restaurant_specialty")
public class RestaurantSpecialty {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "restaurant_id", nullable = false)
    private Restaurant restaurant;

    @Column(nullable = false)
    private String specialty;

    // Getters, Setters, and Constructors
    public RestaurantSpecialty() {}

    public RestaurantSpecialty(Restaurant restaurant, String specialty) {
        this.restaurant = restaurant;
        this.specialty = specialty;
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

    public String getSpecialty() {
        return specialty;
    }

    public void setSpecialty(String specialty) {
        this.specialty = specialty;
    }
}

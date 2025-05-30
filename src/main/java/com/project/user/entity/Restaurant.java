package com.project.user.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;

import java.time.LocalTime;
import java.util.List;

@Entity
@Table(name = "restaurant")
public class Restaurant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @NotEmpty(message = "Name is required")
    private String name;

    @Column(nullable = false)
    @NotEmpty(message = "Cuisine is required")
    private String cuisine;

    @Column(name = "price_range", nullable = false)
    private String priceRange;

    @Column(name = "average_rating")
    private Float averageRating;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;

    @Column(name = "open_time", nullable = false)
    private LocalTime openTime;

    @Column(name = "close_time", nullable = false)
    private LocalTime closeTime;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id", referencedColumnName = "id")
    private Address address;

    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Menu> menus;

    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Review> reviews;

    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<RestaurantPhoto> photos;

    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<RestaurantSpecialty> restaurantSpecialties;

    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<RestaurantAmenity> restaurantAmenities;

    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<RestaurantDietaryOption> restaurantDietaryOptions;

    // Getters, Setters, and Constructors
    public Restaurant() {}
    // getters and setters omitted for brevity

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCuisine() {
        return cuisine;
    }

    public void setCuisine(String cuisine) {
        this.cuisine = cuisine;
    }

    public String getPriceRange() {
        return priceRange;
    }

    public void setPriceRange(String priceRange) {
        this.priceRange = priceRange;
    }

    public Float getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(Float averageRating) {
        this.averageRating = averageRating;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalTime getOpenTime() {
        return openTime;
    }

    public void setOpenTime(LocalTime openTime) {
        this.openTime = openTime;
    }

    public LocalTime getCloseTime() {
        return closeTime;
    }

    public void setCloseTime(LocalTime closeTime) {
        this.closeTime = closeTime;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public List<Menu> getMenus() {
        return menus;
    }

    public void setMenus(List<Menu> menus) {
        this.menus = menus;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    public List<RestaurantPhoto> getPhotos() {
        return photos;
    }

    public void setPhotos(List<RestaurantPhoto> photos) {
        this.photos = photos;
    }

    public List<RestaurantSpecialty> getRestaurantSpecialties() {
        return restaurantSpecialties;
    }

    public void setRestaurantSpecialties(List<RestaurantSpecialty> restaurantSpecialties) {
        this.restaurantSpecialties = restaurantSpecialties;
    }

    public List<RestaurantAmenity> getRestaurantAmenities() {
        return restaurantAmenities;
    }

    public void setRestaurantAmenities(List<RestaurantAmenity> restaurantAmenities) {
        this.restaurantAmenities = restaurantAmenities;
    }

    public List<RestaurantDietaryOption> getRestaurantDietaryOptions() {
        return restaurantDietaryOptions;
    }

    public void setRestaurantDietaryOptions(List<RestaurantDietaryOption> restaurantDietaryOptions) {
        this.restaurantDietaryOptions = restaurantDietaryOptions;
    }

    @Transient
    private String formattedAmenities;

    public String getFormattedAmenities() {
        return formattedAmenities;
    }

    public void setFormattedAmenities(String formattedAmenities) {
        this.formattedAmenities = formattedAmenities;
    }

    @Transient
    private String formattedSpecialties;

    public String getFormattedSpecialties() {
        return formattedSpecialties;
    }

    public void setFormattedSpecialties(String formattedSpecialties) {
        this.formattedSpecialties = formattedSpecialties;
    }

    @Transient
    private String formattedDietaryOptions;

    public String getFormattedDietaryOptions() {
        return formattedDietaryOptions;
    }

    public void setFormattedDietaryOptions(String formattedDietaryOptions) {
        this.formattedDietaryOptions = formattedDietaryOptions;
    }



}

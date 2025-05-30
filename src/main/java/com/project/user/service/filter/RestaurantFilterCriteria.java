package com.project.user.service.filter;

import java.util.List;
import java.util.Map;

public class RestaurantFilterCriteria {

    private String cuisine;
    private String priceRange;
    private String name;
    private String location; // Renamed to location to match the logic
    private String openTime;
    private Double minAverageRating;
    private Double maxAverageRating;
    private List<String> amenities;
    private List<String> dietaryOptions;
    private List<String> specialties;

    // Constructor with all parameters
    public RestaurantFilterCriteria(String cuisine, String priceRange, String name, String location, String openTime,
                                    Double minAverageRating, Double maxAverageRating, List<String> amenities,
                                    List<String> dietaryOptions, List<String> specialties) {
        this.cuisine = cuisine;
        this.priceRange = priceRange;
        this.name = name;
        this.location = location; // Changed back to location
        this.openTime = openTime;
        this.minAverageRating = minAverageRating;
        this.maxAverageRating = maxAverageRating;
        this.amenities = amenities;
        this.dietaryOptions = dietaryOptions;
        this.specialties = specialties;

        System.out.println("Amenities -=-=-=-=-=-=================------------------------------======================= "+this.amenities);
    }

    public static RestaurantFilterCriteria from(Map<String, String> filters) {
        return new RestaurantFilterCriteria(
                filters.get("cuisine"),
                filters.get("priceRange"),
                filters.get("name"),
                filters.get("city"),
                filters.get("openTime"),
                filters.containsKey("minAverageRating") ? Double.valueOf(filters.get("minAverageRating")) : null,
                filters.containsKey("maxAverageRating") ? Double.valueOf(filters.get("maxAverageRating")) : null,
                filters.containsKey("amenities") ? List.of(filters.get("amenities").split(",")) : null,
                filters.containsKey("dietaryOptions") ? List.of(filters.get("dietaryOptions").split(",")) : null,
                filters.containsKey("specialties") ? List.of(filters.get("specialties").split(",")) : null
        );
    }

    // Getters and Setters
    public String getCuisine() { return cuisine; }
    public void setCuisine(String cuisine) { this.cuisine = cuisine; }
    public String getPriceRange() { return priceRange; }
    public void setPriceRange(String priceRange) { this.priceRange = priceRange; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }
    public String getOpenTime() { return openTime; }
    public void setOpenTime(String openTime) { this.openTime = openTime; }
    public Double getMinAverageRating() { return minAverageRating; }
    public void setMinAverageRating(Double minAverageRating) { this.minAverageRating = minAverageRating; }
    public Double getMaxAverageRating() { return maxAverageRating; }
    public void setMaxAverageRating(Double maxAverageRating) { this.maxAverageRating = maxAverageRating; }
    public List<String> getAmenities() { return amenities; }
    public void setAmenities(List<String> amenities) { this.amenities = amenities; }
    public List<String> getDietaryOptions() { return dietaryOptions; }
    public void setDietaryOptions(List<String> dietaryOptions) { this.dietaryOptions = dietaryOptions; }

    public List<String> getSpecialties() {
        return specialties;
    }

    public void setSpecialties(List<String> specialties) {
        this.specialties = specialties;
    }
}

package com.project.user.service;

import com.project.user.dao.*;
import com.project.user.entity.*;
import com.project.user.service.filter.RestaurantFilterCriteria;
import com.project.user.service.filter.RestaurantSpecifications;
import jakarta.persistence.EntityNotFoundException;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RestaurantService {

    @Autowired
    private RestaurantAmenityRepository amenityRepository;

    @Autowired
    private RestaurantSpecialtyRepository specialtyRepository;

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private RestaurantDietaryOptionRepository dietaryOptionRepository;

    @Autowired
    private MenuRepository menuRepository;

    @Autowired
    private RestaurantPhotoRepository restaurantPhotoRepository;

    // Fetches all restaurants with pagination support
    public Page<Restaurant> findAllRestaurants(Pageable pageable) {
        return restaurantRepository.findAll(pageable);
    }

    // Searches restaurants by name with pagination support
    public Page<Restaurant> searchByName(String name, Pageable pageable) {
        return restaurantRepository.findByNameContainingIgnoreCase(name, pageable);
    }

    // Fetches restaurants based on filtering criteria with sorting and pagination
    public Page<Restaurant> getFilteredRestaurants(RestaurantFilterCriteria criteria, int page, int size, String sortBy, String sortDirection) {
        Sort sort = Sort.by(sortBy.equalsIgnoreCase("rating") ? "averageRating" : sortBy);
        sort = sortDirection.equalsIgnoreCase("asc") ? sort.ascending() : sort.descending();
        Pageable pageable = PageRequest.of(page, size, sort);
        return restaurantRepository.findAll(RestaurantSpecifications.buildSpecification(criteria), pageable);
    }

    public Restaurant findRestaurantById(Long id)
    {
        return restaurantRepository.getReferenceById(id);
    }

    // Fetches a restaurant by ID and initializes lazy collections like menus, photos, amenities, and specialties
    @Transactional(readOnly = true)
    public Restaurant getRestaurantById(Long id) {
        Restaurant restaurant = restaurantRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Restaurant with id " + id + " not found"));
        return restaurant;
    }

    // Fetches all distinct cuisines from the database
    public List<String> getAllCuisines() {
        return restaurantRepository.findAllDistinctCuisines();
    }

    // Fetches all distinct price ranges from the database
    public List<String> getAllPriceRanges() {
        return restaurantRepository.findAllDistinctPriceRanges();
    }

    // Adds a new amenity to the given restaurant by ID
    @Transactional
    public void addAmenityToRestaurant(Long restaurantId, String amenity) {
        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new EntityNotFoundException("Restaurant with id " + restaurantId + " not found"));
        RestaurantAmenity restaurantAmenity = new RestaurantAmenity();
        restaurantAmenity.setRestaurant(restaurant);
        restaurantAmenity.setAmenity(amenity);
        amenityRepository.save(restaurantAmenity);
    }

    // Adds a new specialty to the given restaurant by ID
    @Transactional
    public void addSpecialtyToRestaurant(Long restaurantId, String specialty) {
        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new EntityNotFoundException("Restaurant with id " + restaurantId + " not found"));
        RestaurantSpecialty restaurantSpecialty = new RestaurantSpecialty();
        restaurantSpecialty.setRestaurant(restaurant);
        restaurantSpecialty.setSpecialty(specialty);
        specialtyRepository.save(restaurantSpecialty);
    }

    // Saves or updates a restaurant entity
    @Transactional
    public Restaurant saveRestaurant(Restaurant restaurant) {
        return restaurantRepository.save(restaurant);
    }

    // Deletes a restaurant by ID if it exists
    @Transactional
    public void deleteRestaurant(Long restaurantId) {
        if (!restaurantRepository.existsById(restaurantId)) {
            throw new EntityNotFoundException("Restaurant with id " + restaurantId + " not found");
        }
        restaurantRepository.deleteById(restaurantId);
    }

    public List<RestaurantAmenity> getAmenities(Long restaurantId) {
        return amenityRepository.findByRestaurantId(restaurantId);
    }

    public List<RestaurantSpecialty> getSpecialties(Long restaurantId) {
        return specialtyRepository.findByRestaurantId(restaurantId);
    }

    public List<RestaurantDietaryOption> getDietaryOptions(Long restaurantId) {
        return dietaryOptionRepository.findByRestaurantId(restaurantId);
    }

    public List<Menu> getMenuItems(Long restaurantId)
    {
        return menuRepository.findByRestaurantId(restaurantId);
    }

    public List<RestaurantPhoto> getPhotos(Long restaurantId)
    {
        return restaurantPhotoRepository.findByRestaurantId(restaurantId);
    }

    // Fetches all distinct amenities from the database
    public List<String> getAllAmenities() {
        return amenityRepository.findAllDistinctAmenities();
    }

    // Fetches all distinct specialties from the database
    public List<String> getAllSpecialties() {
        return specialtyRepository.findAllDistinctSpecialties();
    }

    // Fetches all distinct dietary options from the database
    public List<String> getAllDietaryOptions() {
        return dietaryOptionRepository.findAllDistinctDietaryOptions();
    }
}

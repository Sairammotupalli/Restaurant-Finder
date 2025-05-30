package com.project.user.service;

import com.project.user.dao.*;
import com.project.user.entity.*;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RestaurantCreationService {

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private RestaurantAmenityRepository amenityRepository;

    @Autowired
    private RestaurantSpecialtyRepository specialtyRepository;

    @Autowired
    private RestaurantDietaryOptionRepository restaurantDietaryOptionRepository;

    @Autowired
    private MenuRepository menuRepository;

    @Autowired
    private RestaurantPhotoRepository restaurantPhotoRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Transactional
    public Restaurant saveRestaurant(Restaurant restaurant) {
        return restaurantRepository.save(restaurant);
    }

    @Transactional
    public void updateRestaurant(Restaurant updatedRestaurant) {
        // Fetch the existing restaurant
        Restaurant existingRestaurant = restaurantRepository.findById(updatedRestaurant.getId())
                .orElseThrow(() -> new EntityNotFoundException("Restaurant not found"));

        // Update only scalar fields
        existingRestaurant.setName(updatedRestaurant.getName());
        existingRestaurant.setCuisine(updatedRestaurant.getCuisine());
        existingRestaurant.setPriceRange(updatedRestaurant.getPriceRange());
        existingRestaurant.setOpenTime(updatedRestaurant.getOpenTime());
        existingRestaurant.setCloseTime(updatedRestaurant.getCloseTime());
        existingRestaurant.setDescription(updatedRestaurant.getDescription());

        // Do not alter or replace collections like 'menus'
        restaurantRepository.save(existingRestaurant);
    }


    @Transactional
    public void addAmenity(Long restaurantId, String amenity) {
        System.out.println("saving a new amenity......... "+amenity);
        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new IllegalArgumentException("Restaurant with ID " + restaurantId + " not found."));
        amenityRepository.save(new RestaurantAmenity(restaurant, amenity));
    }

    @Transactional
    public void addSpecialty(Long restaurantId, String specialty) {
        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new IllegalArgumentException("Restaurant with ID " + restaurantId + " not found."));
        specialtyRepository.save(new RestaurantSpecialty(restaurant, specialty));
    }

    @Transactional
    public void addDietaryOption(Long restaurantId, String dietaryOption) {
        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new IllegalArgumentException("Restaurant with ID " + restaurantId + " not found."));
        RestaurantDietaryOption option = new RestaurantDietaryOption(restaurant, dietaryOption);
        restaurantDietaryOptionRepository.save(option);
    }

    @Transactional
    public void addMenuItem(Long restaurantId, String menuItemName, Double menuItemPrice) {
        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new IllegalArgumentException("Restaurant with ID " + restaurantId + " not found."));
        Menu menu = new Menu(restaurant, menuItemName, menuItemPrice);
        menuRepository.save(menu);
    }

    @Transactional
    public void addPhoto(Long restaurantId, String photoUrl) {
        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new IllegalArgumentException("Restaurant with ID " + restaurantId + " not found."));
        RestaurantPhoto photo = new RestaurantPhoto(restaurant, photoUrl);
        restaurantPhotoRepository.save(photo);
    }

    @Transactional
    public void deleteAmenity(Long amenityId) {
        // Fetch the amenity
        RestaurantAmenity amenity = amenityRepository.findById(amenityId)
                .orElseThrow(() -> new EntityNotFoundException("Amenity not found"));

        // Remove from the parent's collection
        Restaurant restaurant = amenity.getRestaurant();
        if (restaurant != null) {
            restaurant.getRestaurantAmenities().remove(amenity);
        }

        // Delete the amenity
        amenityRepository.delete(amenity);
    }

    @Transactional
    public void deleteSpecialty(Long specialtyId) {
        // Fetch the specialty
        RestaurantSpecialty specialty = specialtyRepository.findById(specialtyId)
                .orElseThrow(() -> new EntityNotFoundException("Specialty not found"));

        // Remove from the parent's collection
        Restaurant restaurant = specialty.getRestaurant();
        if (restaurant != null) {
            restaurant.getRestaurantSpecialties().remove(specialty);
        }

        // Delete the specialty
        specialtyRepository.delete(specialty);
    }

    @Transactional
    public void deleteDietaryOption(Long optionId) {
        // Fetch the dietary option
        RestaurantDietaryOption option = restaurantDietaryOptionRepository.findById(optionId)
                .orElseThrow(() -> new EntityNotFoundException("Dietary option not found"));

        // Remove from the parent's collection
        Restaurant restaurant = option.getRestaurant();
        if (restaurant != null) {
            restaurant.getRestaurantDietaryOptions().remove(option);
        }

        // Delete the dietary option
        restaurantDietaryOptionRepository.delete(option);
    }

    @Transactional
    public void deleteMenuItem(Long menuItemId) {
        // Fetch the menu item
        Menu menuItem = menuRepository.findById(menuItemId)
                .orElseThrow(() -> new EntityNotFoundException("Menu item not found"));

        // Remove from the parent's collection
        Restaurant restaurant = menuItem.getRestaurant();
        if (restaurant != null) {
            restaurant.getMenus().remove(menuItem);
        }

        // Delete the menu item
        menuRepository.delete(menuItem);
    }

    @Transactional
    public void deletePhoto(Long photoId) {
        // Fetch the photo
        RestaurantPhoto photo = restaurantPhotoRepository.findById(photoId)
                .orElseThrow(() -> new EntityNotFoundException("Photo not found"));

        // Remove from the parent's collection
        Restaurant restaurant = photo.getRestaurant();
        if (restaurant != null) {
            restaurant.getPhotos().remove(photo);
        }

        // Delete the photo
        restaurantPhotoRepository.delete(photo);
    }

    public void addAddress(Long restaurantId, Address address) {
        Restaurant restaurant = restaurantRepository.getReferenceById(restaurantId);
        address.setRestaurant(restaurant); // Set the relationship on the Address side
        restaurant.setAddress(address); // Set the relationship on the Restaurant side
        restaurantRepository.save(restaurant); // Save the Restaurant, cascading will save the Address
    }

    @Transactional
    public void updateRestaurantAddress(Long restaurantId, Address newAddress) {
        // Save the new address
        Address savedAddress = addressRepository.save(newAddress);

        // Fetch and update the restaurant
        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new EntityNotFoundException("Restaurant not found with ID: " + restaurantId));
        restaurant.setAddress(savedAddress);

        // Save the updated restaurant (not necessary if @Transactional is used and persistence context is active)
        restaurantRepository.save(restaurant);
    }

    public void deleteAddress(Long restaurantId) {
        Restaurant restaurant = restaurantRepository.getReferenceById(restaurantId);
        Address address = restaurant.getAddress();
        if (address != null) {
            restaurant.setAddress(null); // Remove the association
            restaurantRepository.save(restaurant); // Save the Restaurant to persist the change
        }
    }




}

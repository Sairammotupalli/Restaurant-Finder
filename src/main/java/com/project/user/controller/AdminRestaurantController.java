package com.project.user.controller;

import com.project.user.entity.Address;
import com.project.user.entity.Menu;
import com.project.user.entity.Restaurant;
import com.project.user.entity.Review;
import com.project.user.service.AddressService;
import com.project.user.service.RestaurantCreationService;
import com.project.user.service.RestaurantService;
import com.project.user.service.ReviewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin/restaurant")
public class AdminRestaurantController {

    @Autowired
    private RestaurantCreationService restaurantCreationService;

    @Autowired
    private RestaurantService restaurantService;

    @Autowired
    private AddressService addressService;

    @Autowired
    private ReviewsService reviewsService;

    @GetMapping("/add")
    public String showAddRestaurantForm(Model model) {
        model.addAttribute("restaurant", new Restaurant());
        return "admin/add-restaurant";
    }

    @PostMapping("/add")
    public String addRestaurant(@ModelAttribute("restaurant") Restaurant restaurant) {
        Restaurant savedRestaurant = restaurantCreationService.saveRestaurant(restaurant);
        return "redirect:/admin/restaurant/view?id=" + savedRestaurant.getId();
    }

    @PostMapping("/add-amenity")
    public String addAmenity(@RequestParam("id") Long restaurantId, @RequestParam("newAmenity") String newAmenity) {
        restaurantCreationService.addAmenity(restaurantId, newAmenity);
        return "redirect:/admin/restaurant/view?id=" + restaurantId;
    }

    @PostMapping("/add-specialty")
    public String addSpecialty(@RequestParam("id") Long restaurantId, @RequestParam("newSpecialty") String newSpecialty) {
        restaurantCreationService.addSpecialty(restaurantId, newSpecialty);
        return "redirect:/admin/restaurant/view?id=" + restaurantId;
    }
    @PostMapping("/add-dietary-option")
    public String addDietaryOption(@RequestParam("id") Long restaurantId, @RequestParam("newDietaryOption") String newDietaryOption) {
        restaurantCreationService.addDietaryOption(restaurantId, newDietaryOption);
        return "redirect:/admin/restaurant/view?id=" + restaurantId;
    }

    @PostMapping("/add-menu-item")
    public String addMenuItem(@RequestParam("id") Long restaurantId, @RequestParam("menuItemName") String menuItemName, @RequestParam("menuItemPrice") Double menuItemPrice) {
        restaurantCreationService.addMenuItem(restaurantId, menuItemName, menuItemPrice);
        return "redirect:/admin/restaurant/view?id=" + restaurantId;
    }

    @PostMapping("/add-photo")
    public String addPhoto(@RequestParam("id") Long restaurantId, @RequestParam("photoUrl") String photoUrl) {
        restaurantCreationService.addPhoto(restaurantId, photoUrl);
        return "redirect:/admin/restaurant/view?id=" + restaurantId;
    }

    @PostMapping("/delete-amenity")
    public String deleteAmenity(@RequestParam("id") Long amenityId, @RequestParam("restaurantId") Long restaurantId) {
        System.out.println("deleting amenity--------------------------------------------"+amenityId);
        restaurantCreationService.deleteAmenity(amenityId);
        return "redirect:/admin/restaurant/view?id=" + restaurantId;
    }

    @PostMapping("/delete-specialty")
    public String deleteSpecialty(@RequestParam("id") Long specialtyId, @RequestParam("restaurantId") Long restaurantId) {
        restaurantCreationService.deleteSpecialty(specialtyId);
        return "redirect:/admin/restaurant/view?id=" + restaurantId;
    }

    @PostMapping("/delete-dietary-option")
    public String deleteDietaryOption(@RequestParam("id") Long optionId, @RequestParam("restaurantId") Long restaurantId) {
        restaurantCreationService.deleteDietaryOption(optionId);
        return "redirect:/admin/restaurant/view?id=" + restaurantId;
    }

    @PostMapping("/delete-menu-item")
    public String deleteMenuItem(@RequestParam("id") Long menuItemId, @RequestParam("restaurantId") Long restaurantId) {
        restaurantCreationService.deleteMenuItem(menuItemId);
        return "redirect:/admin/restaurant/view?id=" + restaurantId;
    }

    @PostMapping("/delete-photo")
    public String deletePhoto(@RequestParam("id") Long photoId, @RequestParam("restaurantId") Long restaurantId) {
        restaurantCreationService.deletePhoto(photoId);
        return "redirect:/admin/restaurant/view?id=" + restaurantId;
    }
    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable Long id, Model model) {
        Restaurant restaurant = restaurantService.getRestaurantById(id);
        model.addAttribute("restaurant", restaurant);
        return "admin/edit-restaurant";
    }

    // Handle Update Request
    @PostMapping("/update")
    public String updateRestaurant(@ModelAttribute Restaurant restaurant) {
        restaurantCreationService.updateRestaurant(restaurant);
        return "redirect:/admin/restaurant/view?id=" + restaurant.getId();
    }

    @GetMapping("/{id}/add-address")
    public String addAddressForm(@PathVariable Long id, Model model) {
        model.addAttribute("restaurantId", id);
        model.addAttribute("address", new Address()); // Initialize an empty Address object
        return "admin/add-address-form"; // Return the view name for the form
    }

    @PostMapping("/add-address")
    public String addAddress(@RequestParam Long restaurantId, @ModelAttribute Address address) {
        Restaurant restaurant = restaurantService.getRestaurantById(restaurantId); // Fetch restaurant
        Address savedAddress = addressService.save(address); // Save address with all columns
        restaurant.setAddress(savedAddress); // Associate address with restaurant
        restaurantService.saveRestaurant(restaurant); // Save restaurant
        return "redirect:/admin/restaurant/view?id=" + restaurantId; // Redirect to restaurant details
    }

    @GetMapping("/{id}/update-address")
    public String updateAddressForm(@PathVariable Long id, Model model) {
        Restaurant restaurant = restaurantService.getRestaurantById(id);
        if (restaurant.getAddress() == null) {
            restaurant.setAddress(new Address());
        }
        model.addAttribute("restaurant", restaurant);
        return "admin/update-address-form";
    }

    @PostMapping("/update-address")
    public String updateAddress(@RequestParam Long restaurantId, @ModelAttribute Address address) {
        restaurantCreationService.updateRestaurantAddress(restaurantId, address);
        return "redirect:/admin/restaurant/view?id=" + restaurantId;
    }



}

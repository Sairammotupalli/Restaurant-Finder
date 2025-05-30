package com.project.user.controller;

import com.project.user.dao.RestaurantAmenityRepository;
import com.project.user.dao.RestaurantSpecialtyRepository;
import com.project.user.entity.*;
import com.project.user.service.FavoriteService;
import com.project.user.service.RestaurantService;
import com.project.user.service.ReviewsService;
import com.project.user.service.UserService;
import com.project.user.service.filter.RestaurantFilterCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/restaurants")
public class RestaurantController {

    @Autowired
    private RestaurantService restaurantService;

    @Autowired
    private ReviewsService reviewsService;

    @Autowired
    private UserService userService;

    @Autowired
    private FavoriteService favoriteService;

    public RestaurantController() {
    }

    @GetMapping
    public String listRestaurants(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String location,
            @RequestParam(required = false) String cuisine,
            @RequestParam(required = false) String priceRange,
            @RequestParam(required = false) String openTime,
            @RequestParam(required = false) List<String> amenities,
            @RequestParam(required = false) List<String> specialties,
            @RequestParam(required = false) List<String> dietaryOptions,
            @RequestParam(required = false, defaultValue = "name") String sortBy,
            @RequestParam(required = false, defaultValue = "asc") String sortDirection,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            Model model,
            Authentication authentication) {

        // Create filter criteria
        RestaurantFilterCriteria criteria = new RestaurantFilterCriteria(
                cuisine, priceRange, name, location, openTime, null, null, amenities, dietaryOptions, specialties
        );

        criteria.setName(name);
        criteria.setLocation(location);
        criteria.setCuisine(cuisine);
        criteria.setPriceRange(priceRange);
        criteria.setOpenTime(openTime);
        criteria.setAmenities(amenities);
        criteria.setSpecialties(specialties);
        criteria.setDietaryOptions(dietaryOptions);

        // Fetch restaurants
        Page<Restaurant> restaurants = restaurantService.getFilteredRestaurants(criteria, page, size, sortBy, sortDirection);

        // Fetch the authenticated user's name
        String userName = null;
        if (authentication != null && authentication.isAuthenticated() && !"anonymousUser".equals(authentication.getName())) {
            User user = userService.findByEmail(authentication.getName());
            userName = user.getUserDetails().getFirstName() + " " + user.getUserDetails().getLastName();
        }

        // Add distinct filter options for UI
        model.addAttribute("cuisines", restaurantService.getAllCuisines());
        model.addAttribute("amenities", restaurantService.getAllAmenities());
        model.addAttribute("specialties", restaurantService.getAllSpecialties());
        model.addAttribute("dietaryOptions", restaurantService.getAllDietaryOptions());
        model.addAttribute("restaurants", restaurants);
        model.addAttribute("totalPages", restaurants.getTotalPages());
        model.addAttribute("currentPage", page);
        model.addAttribute("userName", userName);

        // Add filters for pre-population in UI
        Map<String, Object> filters = new HashMap<>();
        filters.put("name", name);
        filters.put("location", location);
        filters.put("cuisine", cuisine);
        filters.put("priceRange", priceRange);
        filters.put("openTime", openTime);
        filters.put("amenities", amenities);
        filters.put("specialties", specialties);
        filters.put("dietaryOptions", dietaryOptions);
        model.addAttribute("filters", filters);


        model.addAttribute("sortBy", sortBy);
        model.addAttribute("sortDirection", sortDirection);

        return "restaurants";
    }


    @GetMapping("/{id}")
    public String showRestaurantDetails(
            @PathVariable Long id,
            @RequestParam(defaultValue = "0") int page,
            Model model,
            Authentication authentication) {
        Restaurant restaurant = restaurantService.getRestaurantById(id);

        // Fetch the logged-in user
        User user = null;
        boolean isFavourite = false;
        if (authentication != null && authentication.isAuthenticated()) {
            user = userService.findByEmail(authentication.getName());
            isFavourite = favoriteService.isFavorite(user, restaurant);
        }

        // Fetch paginated reviews
        Page<Review> reviews = reviewsService.getReviewsByRestaurantId(id, page);

        // Fetch related entities
        List<RestaurantAmenity> amenities = restaurantService.getAmenities(id);
        List<RestaurantSpecialty> specialties = restaurantService.getSpecialties(id);
        List<RestaurantDietaryOption> dietaryOptions = restaurantService.getDietaryOptions(id);
        List<Menu> menuItems = restaurantService.getMenuItems(id);
        List<RestaurantPhoto> photos = restaurantService.getPhotos(id);

        // Add to model
        model.addAttribute("restaurant", restaurant);
        model.addAttribute("reviews", reviews);
        model.addAttribute("amenities", amenities);
        model.addAttribute("specialties", specialties);
        model.addAttribute("dietaryOptions", dietaryOptions);
        model.addAttribute("menuItems", menuItems);
        model.addAttribute("photos", photos);
        model.addAttribute("isFavourite", isFavourite);
        model.addAttribute("currentPage", page);

        return "restaurant-details";
    }


    @PostMapping("/{id}/add-to-favourites")
    public String addToFavourites(@PathVariable Long id, Authentication authentication) {
        User user = userService.findByEmail(authentication.getName());
        Restaurant restaurant = restaurantService.getRestaurantById(id);
        favoriteService.addFavorite(user, restaurant);
        return "redirect:/restaurants/" + id; // Redirect to restaurant details
    }

    @PostMapping("/{id}/remove-from-favourites")
    public String removeFromFavourites(@PathVariable Long id, Authentication authentication) {
        User user = userService.findByEmail(authentication.getName());
        Restaurant restaurant = restaurantService.getRestaurantById(id);
        favoriteService.removeFavorite(user, restaurant);
        return "redirect:/restaurants/" + id; // Redirect to restaurant details
    }

    @GetMapping("/my-favourites")
    public String viewMyFavourites(Authentication authentication, Model model) {
        User user = userService.findByEmail(authentication.getName());
        List<Favorite> favourites = favoriteService.getFavoritesByUser(user);
        model.addAttribute("favourites", favourites);
        return "my-favourites"; // View template for favourites
    }




}

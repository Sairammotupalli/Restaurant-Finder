package com.project.user.controller;

import com.project.user.entity.Review;
import com.project.user.entity.User;
import com.project.user.entity.Restaurant;
import com.project.user.service.RestaurantService;
import com.project.user.service.ReviewsService;
import com.project.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AdminController {

    @Autowired
    private UserService userService;

    @Autowired
    private RestaurantService restaurantService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ReviewsService reviewsService;

    public AdminController() {
    }

    @GetMapping("/admin/home")
    public String adminDashboard(
            @RequestParam(value = "search", required = false) String searchQuery,
            @RequestParam(value = "adminPage", defaultValue = "0") int adminPage,
            @RequestParam(value = "restaurantPage", defaultValue = "0") int restaurantPage,
            Model model) {

        // Fetch paginated admins
        Page<User> admins = userService.findAllAdmins(PageRequest.of(adminPage, 10));
        model.addAttribute("admins", admins);

        // Fetch paginated restaurants with search functionality
        Page<Restaurant> restaurants = (searchQuery == null || searchQuery.isEmpty())
                ? restaurantService.findAllRestaurants(PageRequest.of(restaurantPage, 10))
                : restaurantService.searchByName(searchQuery, PageRequest.of(restaurantPage, 10));
        model.addAttribute("restaurants", restaurants);
        model.addAttribute("searchQuery", searchQuery);

        // Include current pages to manage pagination links properly
        model.addAttribute("adminCurrentPage", adminPage);
        model.addAttribute("restaurantCurrentPage", restaurantPage);

        return "admin/dashboard";
    }


    @GetMapping("/admin/add")
    public String showAddAdminForm(Model model) {
        model.addAttribute("user", new User());
        return "admin/add-admin";
    }

    @PostMapping("/admin/add")
    public String addAdmin(@ModelAttribute("user") User user) {
        // Hash the password before saving
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole("ADMIN");
        userService.saveUser(user);
        return "redirect:/admin/home";
    }

    @GetMapping("/admin/add-restaurant")
    public String showAddRestaurantForm(Model model) {
        // Add an empty Restaurant object for binding
        model.addAttribute("restaurant", new Restaurant());

        // Fetch options dynamically from the database
        model.addAttribute("dietaryOptions", restaurantService.getAllDietaryOptions());
        model.addAttribute("amenities", restaurantService.getAllAmenities());
        model.addAttribute("specialties", restaurantService.getAllSpecialties());

        return "admin/add-restaurant";
    }

    @PostMapping("/admin/add-restaurant")
    public String addRestaurant(@ModelAttribute("restaurant") Restaurant restaurant) {
        Restaurant savedRestaurant = restaurantService.saveRestaurant(restaurant);
        return "redirect:/admin/home";
    }

    @GetMapping("/admin/restaurant/view")
    public String viewRestaurant(@RequestParam("id") Long id, @RequestParam(defaultValue = "0") int page, Model model) {
        Restaurant restaurant = restaurantService.getRestaurantById(id);
        Page<Review> reviews = reviewsService.getReviewsByRestaurantId(id, page);

        System.out.print("reviews--------------------------------"+reviews);
        model.addAttribute("restaurant", restaurant);
        model.addAttribute("reviews", reviews);

        model.addAttribute("amenities", restaurantService.getAmenities(id));
        model.addAttribute("specialties", restaurantService.getSpecialties(id));
        model.addAttribute("dietaryOptions", restaurantService.getDietaryOptions(id));
        model.addAttribute("menuItems", restaurantService.getMenuItems(id));
        model.addAttribute("photos", restaurantService.getPhotos(id));
        return "admin/restaurant-details"; // Match your Thymeleaf template file name
    }


    @GetMapping("/admin/delete-restaurant")
    public String deleteRestaurant(@RequestParam("id") Long id) {
        restaurantService.deleteRestaurant(id);
        return "redirect:/admin/home";
    }

    @GetMapping("/admin/delete-admin")
    public String deleteAdmin(@RequestParam("id") Long id) {
        userService.deleteUser(userService.getUserById(id));
        return "redirect:/admin/home";
    }
}

package com.project.user.controller;

import com.project.user.entity.Restaurant;
import com.project.user.entity.Review;
import com.project.user.entity.User;
import com.project.user.service.ReviewsService;
import com.project.user.service.RestaurantService;
import com.project.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@Controller
@RequestMapping("/reviews")
public class ReviewsController {

    @Autowired
    private ReviewsService reviewsService;

    @Autowired
    private RestaurantService restaurantService;

    @Autowired
    private UserService userService;

    @GetMapping("/{reviewId}")
    public String showReviewDetails(@PathVariable Long reviewId, Model model) {
        Review review = reviewsService.getReviewById(reviewId);

        // Add review and associated restaurant to the model
        model.addAttribute("review", review);
        model.addAttribute("restaurant", review.getRestaurant());

        return "review-details"; // Template name
    }

    @PostMapping("/add")
    public String addReview(@RequestParam Long restaurantId,
                            @RequestParam int rating,
                            @RequestParam String reviewText,
                            Authentication authentication) {
        // Fetch the authenticated user
        String userEmail = authentication.getName();
        User user = userService.findByEmail(userEmail);

        // Fetch the restaurant
        Restaurant restaurant = restaurantService.getRestaurantById(restaurantId);

        // Create and save the review
        Review review = new Review();
        review.setRating(rating);
        review.setReviewText(reviewText);
        review.setReviewDate(LocalDate.now());
        review.setUser(user); // Set the user
        review.setRestaurant(restaurant); // Set the restaurant

        reviewsService.saveReview(review);

        // Redirect back to the restaurant page
        return "redirect:/restaurants/" + restaurantId;
    }

    // Edit Review - Show Form
    @GetMapping("/edit/{reviewId}")
    public String editReviewForm(@PathVariable Long reviewId, Model model, Authentication authentication) {
        Review review = reviewsService.getReviewById(reviewId);

        // Check if the logged-in user is the author of the review
        if (!review.getUser().getEmail().equals(authentication.getName())) {
            throw new RuntimeException("Unauthorized access to edit the review.");
        }

        model.addAttribute("review", review);
        return "edit-review"; // Template for editing the review
    }

    // Edit Review - Save Changes
    @PostMapping("/edit/{reviewId}")
    public String updateReview(@PathVariable Long reviewId,
                               @RequestParam int rating,
                               @RequestParam String reviewText,
                               Authentication authentication) {
        Review review = reviewsService.getReviewById(reviewId);

        // Check if the logged-in user is the author of the review
        if (!review.getUser().getEmail().equals(authentication.getName())) {
            throw new RuntimeException("Unauthorized access to update the review.");
        }

        // Update review details
        review.setRating(rating);
        review.setReviewText(reviewText);
        reviewsService.saveReview(review);

        // Redirect to the restaurant page
        return "redirect:/restaurants/" + review.getRestaurant().getId();
    }

    // Delete Review
    @GetMapping("/delete/{reviewId}")
    public String deleteReview(@PathVariable Long reviewId, Authentication authentication) {
        Review review = reviewsService.getReviewById(reviewId);

        // Check if the logged-in user is the author of the review
        if (!review.getUser().getEmail().equals(authentication.getName())) {
            throw new RuntimeException("Unauthorized access to delete the review.");
        }

        Long restaurantId = review.getRestaurant().getId();
        reviewsService.deleteReviewById(reviewId);

        // Redirect back to the restaurant page
        return "redirect:/restaurants/" + restaurantId;
    }
}

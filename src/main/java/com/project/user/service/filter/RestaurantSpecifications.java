package com.project.user.service.filter;

import com.project.user.entity.Restaurant;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class RestaurantSpecifications {

    public static Specification<Restaurant> buildSpecification(RestaurantFilterCriteria criteria) {
        return Specification.where(hasCuisine(criteria.getCuisine()))
                .and(hasPriceRange(criteria.getPriceRange()))
                .and(nameContains(criteria.getName()))
                .and(locationContains(criteria.getLocation()))
                .and(isOpenAt(criteria.getOpenTime()))
                .and(hasAverageRatingRange(criteria.getMinAverageRating(), criteria.getMaxAverageRating()))
                .and(hasAmenities(criteria.getAmenities()))
                .and(hasDietaryOptions(criteria.getDietaryOptions()))
                .and(hasSpecialties(criteria.getSpecialties()));
    }

    public static Specification<Restaurant> hasCuisine(String cuisine) {
        return (root, query, cb) -> cuisine == null || cuisine.isEmpty()
                ? null
                : cb.like(cb.lower(root.get("cuisine")), "%" + cuisine.toLowerCase() + "%");
    }

    public static Specification<Restaurant> hasPriceRange(String priceRange) {
        return (root, query, cb) -> priceRange == null || priceRange.isEmpty()
                ? null
                : cb.equal(root.get("priceRange"), priceRange);
    }

    public static Specification<Restaurant> nameContains(String name) {
        return (root, query, cb) -> name == null || name.isEmpty()
                ? null
                : cb.like(cb.lower(root.get("name")), "%" + name.toLowerCase() + "%");
    }

    public static Specification<Restaurant> locationContains(String location) {
        return (root, query, cb) -> {
            if (location == null || location.isEmpty()) return null;
            String locationPattern = "%" + location.toLowerCase() + "%";
            return cb.or(
                    cb.like(cb.lower(root.get("address").get("street")), locationPattern),
                    cb.like(cb.lower(root.get("address").get("city")), locationPattern),
                    cb.like(cb.lower(root.get("address").get("state")), locationPattern),
                    cb.like(cb.lower(root.get("address").get("zipCode")), locationPattern)
            );
        };
    }

    public static Specification<Restaurant> isOpenAt(String openTime) {
        return (root, query, cb) -> {
            if (openTime == null || openTime.isEmpty()) return null;
            try {
                LocalTime time = LocalTime.parse(openTime, DateTimeFormatter.ofPattern("HH:mm"));
                return cb.and(
                        cb.lessThanOrEqualTo(root.get("openTime"), time),
                        cb.greaterThanOrEqualTo(root.get("closeTime"), time)
                );
            } catch (Exception e) {
                throw new IllegalArgumentException("Invalid time format. Expected HH:mm");
            }
        };
    }

    public static Specification<Restaurant> hasAverageRatingRange(Double minRating, Double maxRating) {
        return (root, query, cb) -> {
            if (minRating == null && maxRating == null) return null;
            if (minRating != null && maxRating != null) {
                return cb.between(root.get("averageRating"), minRating, maxRating);
            }
            if (minRating != null) {
                return cb.greaterThanOrEqualTo(root.get("averageRating"), minRating);
            }
            return cb.lessThanOrEqualTo(root.get("averageRating"), maxRating);
        };
    }

    public static Specification<Restaurant> hasAmenities(List<String> amenities) {
        return (root, query, cb) -> {
            if (amenities == null || amenities.isEmpty()) return cb.conjunction();

            // Join the restaurant amenities
            Join<Object, Object> amenityJoin = root.join("restaurantAmenities");

            // Add WHERE condition for amenities
            Predicate amenityPredicate = amenityJoin.get("amenity").in(amenities);

            // Add GROUP BY and HAVING clauses
            query.groupBy(root.get("id")); // Group by restaurant ID
            query.having(cb.equal(cb.count(root.get("id")), amenities.size())); // Ensure all selected amenities match

            return amenityPredicate;
        };
    }


    public static Specification<Restaurant> hasSpecialties(List<String> specialties) {
        return (root, query, cb) -> {
            if (specialties == null || specialties.isEmpty()) return cb.conjunction();

            // Join with the restaurant specialties table
            Join<Object, Object> specialtyJoin = root.join("restaurantSpecialties");

            // Add WHERE condition for specialties
            Predicate specialtyPredicate = specialtyJoin.get("specialty").in(specialties);

            // Add GROUP BY and HAVING to ensure all specialties are matched
            query.groupBy(root.get("id"));
            query.having(cb.equal(cb.count(root.get("id")), specialties.size()));

            return specialtyPredicate;
        };
    }

    public static Specification<Restaurant> hasDietaryOptions(List<String> dietaryOptions) {
        return (root, query, cb) -> {
            if (dietaryOptions == null || dietaryOptions.isEmpty()) return cb.conjunction();

            // Join with the restaurant dietary options table
            Join<Object, Object> dietaryOptionJoin = root.join("restaurantDietaryOptions");

            // Add WHERE condition for dietary options
            Predicate dietaryOptionPredicate = dietaryOptionJoin.get("dietaryOption").in(dietaryOptions);

            // Add GROUP BY and HAVING to ensure all dietary options are matched
            query.groupBy(root.get("id"));
            query.having(cb.equal(cb.count(root.get("id")), dietaryOptions.size()));

            return dietaryOptionPredicate;
        };
    }

}

# Restaurant Finder Project

This repository contains the source code for the **Restaurant Finder** project, developed as part of the **CSEN 275 - Object-Oriented Analysis, Design, and Programming (OOAD)** coursework at Santa Clara University. The project provides a platform for discovering restaurants and managing restaurant data, with a focus on search functionality, admin management, and robust architecture.

---

## Features

### User Functionality
- **Search and Filter Restaurants:**
    - Users can search restaurants by:
        - Name, Cuisine, Location (City, State, ZIP Code)
        - Price Range, Dietary Options (e.g., Vegan, Gluten-Free), Amenities (e.g., Parking)
    - **Sort Results:** By Average Rating, Price, or other criteria.
- **View Restaurant Profiles:** Detailed information including menus, photos, reviews, and amenities.
- **Review Management:** Users can add, edit, or delete reviews, which automatically update restaurant ratings.
- **Favorites:** Mark restaurants as favorites for personalized access.

### Admin Functionality
- **Admin Dashboard:** Accessible only to admin users for managing:
    - **Restaurants:** Add, edit, or delete restaurants.
    - **Menus:** Create, update, or delete menu items.
    - **Dietary Options and Amenities:** Add or remove associated options.
    - **Reviews:** Moderate user reviews if needed.
- **Role-Based Access Control:** Ensures admin-specific features are restricted to authorized users.

### Security
- **Spring Security Integration:**
    - Role-based access control for separating admin and user features.
    - Password encryption using **BCrypt**.

---

## System Architecture

The project follows a **Layered Architecture** for better separation of concerns:

1. **Controller Layer:** Handles user requests and delegates processing to services.
2. **Service Layer:** Implements business logic and orchestrates data retrieval and updates.
3. **Repository Layer (DAO):** Interfaces with the database using Spring Data JPA.

### Search Functionality
- **Dynamic Filtering:** Implemented using the **Strategy Pattern** to combine multiple user-selected filters dynamically.
- **Specifications and Criteria API:** Built with JPA Criteria API for flexible and extensible queries.
- **Pagination and Sorting:** Results are paginated and sorted to ensure efficient data handling for large datasets.

---

## Database Design and Triggers

### Schema Highlights
- **User:** Stores credentials, roles, and personal details.
- **Restaurant:** Stores restaurant details, average ratings, and linked addresses.
- **Menu:** Stores menu items, prices, and associated dietary options.
- **Review:** Stores user reviews, ratings, and timestamps.
- **Favorites:** Stores user-favorite restaurants.
- **Amenities & Specialties:** Stores additional restaurant details like parking or signature dishes.

### Triggers
1. **Average Rating Maintenance:**
    - Automatically updates a restaurant's average rating when reviews are added, updated, or deleted.
2. **Dietary Option Synchronization:**
    - Ensures consistency between menu-level and restaurant-level dietary options.

---

## Object-Oriented and SOLID Principles

### Object-Oriented Concepts
- **Encapsulation:** Services encapsulate business logic, ensuring controllers focus on request handling.
- **Abstraction:** Interfaces decouple higher-level business logic from lower-level implementations.
- **Polymorphism:** Search filters are implemented as interchangeable specifications.

### SOLID Principles
1. **Single Responsibility:** Each class handles a single concern (e.g., `RestaurantService` for restaurant-related logic).
2. **Open/Closed Principle:** Easily extendable search filters and features without modifying existing code.
3. **Liskov Substitution:** Interchangeable service and repository implementations.
4. **Interface Segregation:** Distinct interfaces for controllers, services, and repositories.
5. **Dependency Inversion:** High-level modules depend on abstractions rather than concrete implementations.

---

## Running the Project

1. **Clone the Repository:**
   ```bash
   git clone https://github.com/DheerajGedupudi/restaurant-finder.git
   cd restaurant-finder
   ```

2. **Setup MySQL Database:**
    - Navigate to the `SQL Scripts` folder in the root directory of the project.
    - Execute the following scripts in order:
        1. **`create.sql`:** Creates the database schema and triggers.
        2. **`insert.sql`:** Populates the database with initial data.
    - Update the database configurations in `application.properties` to match your MySQL setup.

3. **Build and Run the Application:**
   ```bash
   mvn spring-boot:run
   ```

4. **Access the Application:**
    - Open `http://localhost:8080` in your browser.

---

## Challenges and Solutions

### Challenges
1. Designing dynamic and extensible search functionality.
2. Maintaining data consistency between reviews and ratings.
3. Restricting admin features to authorized users.

### Solutions
1. **Dynamic Specifications:** Used JPA Criteria API for flexible and extensible filtering.
2. **Database Triggers:** Ensured automatic updates for ratings and dietary options.
3. **Spring Security:** Enforced role-based access control for admin features.

---

## Future Enhancements

- **Real-Time Proximity Search:** Add geospatial queries for location-based filtering.
- **Recommendation Engine:** Suggest restaurants based on user preferences.
- **Booking System:** Enable users to reserve tables directly.

---

## Author

**Dheeraj Gedupudi**  
[Website](https://www.dheerajgedupudi.com) | [LinkedIn](https://linkedin.com/in/dheeraj-gedupudi) | [Email](mailto:dheerajgedupudi@gmail.com)

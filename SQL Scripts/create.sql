-- Drop and Create Database
DROP DATABASE IF EXISTS RestaurantFinder;
CREATE DATABASE RestaurantFinder;
USE RestaurantFinder;

-- Create User Table
CREATE TABLE User (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    email VARCHAR(100) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(20) NOT NULL
);

-- Create User Details Table
CREATE TABLE User_Details (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    first_name VARCHAR(100),
    last_name VARCHAR(100),
    phone_number VARCHAR(15),
    address TEXT,
    FOREIGN KEY (user_id) REFERENCES User(id) ON DELETE CASCADE
);

-- Create Address Table
CREATE TABLE Address (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    street VARCHAR(255),
    city VARCHAR(100),
    state VARCHAR(100),
    zip_code VARCHAR(10),
    latitude DOUBLE NOT NULL,
    longitude DOUBLE NOT NULL
);

-- Create Restaurant Table
CREATE TABLE Restaurant (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    cuisine VARCHAR(50) NOT NULL,
    price_range VARCHAR(20) NOT NULL,
    average_rating FLOAT DEFAULT NULL,
    description TEXT NOT NULL,
    open_time TIME NOT NULL,
    close_time TIME NOT NULL,
    address_id BIGINT UNIQUE,
    FOREIGN KEY (address_id) REFERENCES Address(id) ON DELETE CASCADE
);

-- Create Review Table
CREATE TABLE Review (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    restaurant_id BIGINT NOT NULL,
    rating INT CHECK (rating BETWEEN 1 AND 5),
    review_text TEXT,
    review_date DATE NOT NULL,
    FOREIGN KEY (user_id) REFERENCES User(id) ON DELETE CASCADE,
    FOREIGN KEY (restaurant_id) REFERENCES Restaurant(id) ON DELETE CASCADE
);

-- Create Favorite Table
CREATE TABLE Favorite (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    restaurant_id BIGINT NOT NULL,
    FOREIGN KEY (user_id) REFERENCES User(id) ON DELETE CASCADE,
    FOREIGN KEY (restaurant_id) REFERENCES Restaurant(id) ON DELETE CASCADE
);

-- Create Restaurant Photo Table
CREATE TABLE Restaurant_Photo (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    restaurant_id BIGINT NOT NULL,
    photo_url VARCHAR(255) NOT NULL,
    FOREIGN KEY (restaurant_id) REFERENCES Restaurant(id) ON DELETE CASCADE
);

-- Create Menu Table
CREATE TABLE Menu (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    restaurant_id BIGINT NOT NULL,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    price FLOAT,
    ingredients TEXT,
    FOREIGN KEY (restaurant_id) REFERENCES Restaurant(id) ON DELETE CASCADE
);

-- Create Menu Dietary Option Table
CREATE TABLE Menu_Dietary_Option (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    menu_id BIGINT NOT NULL,
    dietary_option VARCHAR(50) NOT NULL,
    FOREIGN KEY (menu_id) REFERENCES Menu(id) ON DELETE CASCADE
);

-- Create Restaurant Dietary Option Table
CREATE TABLE Restaurant_Dietary_Option (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    restaurant_id BIGINT NOT NULL,
    dietary_option VARCHAR(50) NOT NULL,
    UNIQUE (restaurant_id, dietary_option),
    FOREIGN KEY (restaurant_id) REFERENCES Restaurant(id) ON DELETE CASCADE
);

-- Create Restaurant Amenity Table
CREATE TABLE Restaurant_Amenity (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    restaurant_id BIGINT NOT NULL,
    amenity VARCHAR(50) NOT NULL,
    UNIQUE (restaurant_id, amenity),
    FOREIGN KEY (restaurant_id) REFERENCES Restaurant(id) ON DELETE CASCADE
);

-- Create Restaurant Specialty Table
CREATE TABLE Restaurant_Specialty (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    restaurant_id BIGINT NOT NULL,
    specialty VARCHAR(100) NOT NULL,
    UNIQUE (restaurant_id, specialty),
    FOREIGN KEY (restaurant_id) REFERENCES Restaurant(id) ON DELETE CASCADE
);

-- Triggers for Average Rating Maintenance
DELIMITER //

-- Trigger for insert
CREATE TRIGGER after_review_insert
AFTER INSERT ON Review
FOR EACH ROW
BEGIN
    UPDATE Restaurant
    SET average_rating = (
        SELECT AVG(rating)
        FROM Review
        WHERE restaurant_id = NEW.restaurant_id
    )
    WHERE id = NEW.restaurant_id;
END;
//

-- Trigger for update
CREATE TRIGGER after_review_update
AFTER UPDATE ON Review
FOR EACH ROW
BEGIN
    UPDATE Restaurant
    SET average_rating = (
        SELECT AVG(rating)
        FROM Review
        WHERE restaurant_id = NEW.restaurant_id
    )
    WHERE id = NEW.restaurant_id;
END;
//

-- Trigger for delete
CREATE TRIGGER after_review_delete
AFTER DELETE ON Review
FOR EACH ROW
BEGIN
    UPDATE Restaurant
    SET average_rating = (
        SELECT AVG(rating)
        FROM Review
        WHERE restaurant_id = OLD.restaurant_id
    )
    WHERE id = OLD.restaurant_id;
END;
//

-- Triggers for Restaurant Dietary Option Maintenance
-- Trigger for insert
CREATE TRIGGER after_menu_dietary_option_insert
AFTER INSERT ON Menu_Dietary_Option
FOR EACH ROW
BEGIN
    IF NOT EXISTS (
        SELECT 1
        FROM Restaurant_Dietary_Option
        WHERE restaurant_id = (SELECT restaurant_id FROM Menu WHERE id = NEW.menu_id)
          AND dietary_option = NEW.dietary_option
    ) THEN
        INSERT INTO Restaurant_Dietary_Option (restaurant_id, dietary_option)
        VALUES ((SELECT restaurant_id FROM Menu WHERE id = NEW.menu_id), NEW.dietary_option);
    END IF;
END;
//

-- Trigger for delete
CREATE TRIGGER after_menu_dietary_option_delete
AFTER DELETE ON Menu_Dietary_Option
FOR EACH ROW
BEGIN
    IF NOT EXISTS (
        SELECT 1
        FROM Menu_Dietary_Option
        WHERE dietary_option = OLD.dietary_option
          AND menu_id IN (SELECT id FROM Menu WHERE restaurant_id = (SELECT restaurant_id FROM Menu WHERE id = OLD.menu_id))
    ) THEN
        DELETE FROM Restaurant_Dietary_Option
        WHERE restaurant_id = (SELECT restaurant_id FROM Menu WHERE id = OLD.menu_id)
          AND dietary_option = OLD.dietary_option;
    END IF;
END;
//

DELIMITER ;

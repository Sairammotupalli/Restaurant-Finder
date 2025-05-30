package com.project.user.service;

import com.project.user.entity.Menu;
import com.project.user.entity.MenuDietaryOption;
import com.project.user.entity.Restaurant;
import com.project.user.dao.MenuDietaryOptionRepository;
import com.project.user.dao.MenuRepository;
import com.project.user.dao.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MenuService {

    @Autowired
    private MenuRepository menuRepository;

    @Autowired
    private MenuDietaryOptionRepository dietaryOptionRepository;

    @Autowired
    private RestaurantRepository restaurantRepository;

    public List<Menu> getMenuByRestaurantId(Long restaurantId) {
        return menuRepository.findByRestaurantId(restaurantId);
    }

    public void addMenuItem(Long restaurantId, String name, Double price, String description, String ingredients) {
        Restaurant restaurant = restaurantRepository.findById(restaurantId).orElseThrow();
        Menu menu = new Menu(restaurant, name, price);
        menu.setDescription(description);
        menu.setIngredients(ingredients);
        menuRepository.save(menu);
    }

    public void deleteMenuItem(Long menuId) {
        menuRepository.deleteById(menuId);
    }

    public void addDietaryOption(Long menuId, String dietaryOption) {
        Menu menu = menuRepository.findById(menuId).orElseThrow();
        MenuDietaryOption option = new MenuDietaryOption(menu, dietaryOption);
        dietaryOptionRepository.save(option);
    }

    public void removeDietaryOption(Long dietaryOptionId) {
        dietaryOptionRepository.deleteById(dietaryOptionId);
    }

    public Long getRestaurantIdByMenuId(Long menuId) {
        Menu menu = menuRepository.findById(menuId).orElseThrow();
        return menu.getRestaurant().getId();
    }

    public Long getRestaurantIdByDietaryOptionId(Long dietaryOptionId) {
        MenuDietaryOption option = dietaryOptionRepository.findById(dietaryOptionId).orElseThrow();
        return option.getMenu().getRestaurant().getId();
    }
}

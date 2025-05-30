package com.project.user.controller;

import com.project.user.entity.Menu;
import com.project.user.entity.MenuDietaryOption;
import com.project.user.service.MenuService;
import com.project.user.service.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/menu")
public class MenuController {

    @Autowired
    private MenuService menuService;

    @Autowired
    private RestaurantService restaurantService;

    // Display full menu for a restaurant
    @GetMapping("/{restaurantId}")
    public String viewFullMenu(@PathVariable Long restaurantId, Model model) {
        model.addAttribute("restaurantId", restaurantId);
        model.addAttribute("menuItems", menuService.getMenuByRestaurantId(restaurantId));
        return "admin/full-menu";
    }

    // Add a new dietary option to a menu item
    @PostMapping("/add-dietary-option")
    public String addDietaryOption(@RequestParam Long menuId, @RequestParam String dietaryOption) {
        menuService.addDietaryOption(menuId, dietaryOption);
        return "redirect:/admin/menu/" + menuService.getRestaurantIdByMenuId(menuId);
    }

    // Remove a dietary option from a menu item
    @PostMapping("/remove-dietary-option")
    public String removeDietaryOption(@RequestParam Long dietaryOptionId) {
        Long restaurantId = menuService.getRestaurantIdByDietaryOptionId(dietaryOptionId);
        menuService.removeDietaryOption(dietaryOptionId);
        return "redirect:/admin/menu/" + restaurantId;
    }

    // Delete a menu item
    @PostMapping("/delete-menu-item")
    public String deleteMenuItem(@RequestParam Long menuId) {
        Long restaurantId = menuService.getRestaurantIdByMenuId(menuId);
        menuService.deleteMenuItem(menuId);
        return "redirect:/admin/menu/" + restaurantId;
    }

    // Add a new menu item
    @PostMapping("/add-menu-item")
    public String addMenuItem(@RequestParam Long restaurantId, @RequestParam String name,
                              @RequestParam Double price, @RequestParam(required = false) String description,
                              @RequestParam(required = false) String ingredients) {
        menuService.addMenuItem(restaurantId, name, price, description, ingredients);
        return "redirect:/admin/menu/" + restaurantId;
    }
}

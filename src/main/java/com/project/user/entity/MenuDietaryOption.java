package com.project.user.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "menu_dietary_option")
public class MenuDietaryOption {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "menu_id", nullable = false)
    private Menu menu;

    @Column(nullable = false)
    private String dietaryOption;

    // Getters, Setters, and Constructors
    public MenuDietaryOption() {}

    public MenuDietaryOption(Menu menu, String dietaryOption) {
        this.menu = menu;
        this.dietaryOption = dietaryOption;
    }

    // getters and setters omitted for brevity

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Menu getMenu() {
        return menu;
    }

    public void setMenu(Menu menu) {
        this.menu = menu;
    }

    public String getDietaryOption() {
        return dietaryOption;
    }

    public void setDietaryOption(String dietaryOption) {
        this.dietaryOption = dietaryOption;
    }
}

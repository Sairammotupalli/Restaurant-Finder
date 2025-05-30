package com.project.user.dao;

import com.project.user.entity.MenuDietaryOption;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MenuDietaryOptionRepository extends JpaRepository<MenuDietaryOption, Long> {
}

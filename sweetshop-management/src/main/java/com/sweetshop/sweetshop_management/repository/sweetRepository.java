package com.sweetshop.sweetshop_management.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sweetshop.sweetshop_management.entity.Sweet;
@Repository
public interface sweetRepository extends JpaRepository<Sweet, Long> {
    List<Sweet> findByNameContainingIgnoreCase(String name);
    List<Sweet> findByCategoryIgnoreCase(String category);
    List<Sweet> findByPriceBetween(double min, double max);
}

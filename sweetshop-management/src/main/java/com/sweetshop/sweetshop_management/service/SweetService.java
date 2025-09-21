package com.sweetshop.sweetshop_management.service;

import com.sweetshop.sweetshop_management.entity.Sweet;
import com.sweetshop.sweetshop_management.exception.SweetNotFoundException;
import com.sweetshop.sweetshop_management.exception.SweetStockEnded;
import com.sweetshop.sweetshop_management.repository.sweetRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SweetService {

    private final sweetRepository sweetRepo;

    public SweetService(sweetRepository sweetRepo) {
        this.sweetRepo = sweetRepo;
    }

    public Sweet addSweet(Sweet sweet) {
        return sweetRepo.save(sweet);
    }

    public List<Sweet> getAllSweets() {
        return sweetRepo.findAll();
    }

    public Optional<Sweet> getSweetById(Long id) {
        return sweetRepo.findById(id);
    }

    // --- MODIFIED METHOD ---
    public List<Sweet> searchSweets(String name, String category, Double minPrice, Double maxPrice) {
        // Prioritize the most specific search: price range
        if (minPrice != null && maxPrice != null) {
            return sweetRepo.findByPriceBetween(minPrice, maxPrice);
        }
        if (name != null && !name.isEmpty()) {
            return sweetRepo.findByNameContainingIgnoreCase(name);
        }
        if (category != null && !category.isEmpty()) {
            return sweetRepo.findByCategoryIgnoreCase(category);
        }
        
        // If no criteria are provided, return all sweets
        return sweetRepo.findAll();
    }

    public Sweet updateSweet(Long id, Sweet sweet) {
        Sweet existing = sweetRepo.findById(id).orElseThrow(() ->new SweetNotFoundException( id));
        existing.setName(sweet.getName());
        existing.setCategory(sweet.getCategory());
        existing.setPrice(sweet.getPrice());
        existing.setQuantity(sweet.getQuantity());
        return sweetRepo.save(existing);
    }

    public void deleteSweet(Long id) {
        sweetRepo.deleteById(id);
    }
    public Sweet purchaseSweet(Long id, int quantity) {
    Sweet sweet = sweetRepo.findById(id)
            .orElseThrow(() -> new SweetNotFoundException( id));

    if (sweet.getQuantity() < quantity) {
        throw new SweetStockEnded(id);
    }

    sweet.setQuantity(sweet.getQuantity() - quantity);
    return sweetRepo.save(sweet);
}
public Sweet restockSweet(Long id, int quantity) {
    Sweet sweet = sweetRepo.findById(id)
            .orElseThrow(() -> new SweetNotFoundException(id));

    sweet.setQuantity(sweet.getQuantity() + quantity);
    return sweetRepo.save(sweet);
}
}
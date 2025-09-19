package com.sweetshop.sweetshop_management.service;


import com.sweetshop.sweetshop_management.entity.Sweet;
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

    public List<Sweet> searchSweets(String name, String category) {
        if (name != null) return sweetRepo.findByNameContainingIgnoreCase(name);
        if (category != null) return sweetRepo.findByCategory(category);
        return sweetRepo.findAll();
    }

    public Sweet updateSweet(Long id, Sweet sweet) {
        Sweet existing = sweetRepo.findById(id).orElseThrow();
        existing.setName(sweet.getName());
        existing.setCategory(sweet.getCategory());
        existing.setPrice(sweet.getPrice());
        existing.setQuantity(sweet.getQuantity());
        return sweetRepo.save(existing);
    }

    public void deleteSweet(Long id) {
        sweetRepo.deleteById(id);
    }
}

package com.sweetshop.sweetshop_management.service;

import com.sweetshop.sweetshop_management.entity.Sweet;
import com.sweetshop.sweetshop_management.repository.sweetRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SweetServiceTest {

    @Mock
    private sweetRepository sweetRepo;

    @InjectMocks
    private SweetService sweetService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void addSweet_ShouldReturnSavedSweet() {
        Sweet sweet = new Sweet();
        sweet.setName("Chocolate");
        when(sweetRepo.save(any(Sweet.class))).thenReturn(sweet);

        Sweet saved = sweetService.addSweet(sweet);

        assertNotNull(saved);
        assertEquals("Chocolate", saved.getName());
    }

    @Test
    void getAllSweets_ShouldReturnListOfSweets() {
        Sweet s1 = new Sweet(); s1.setName("Chocolate");
        Sweet s2 = new Sweet(); s2.setName("Lollipop");
        when(sweetRepo.findAll()).thenReturn(Arrays.asList(s1, s2));

        List<Sweet> sweets = sweetService.getAllSweets();

        assertEquals(2, sweets.size());
    }

    @Test
    void getSweetById_ShouldReturnSweetIfExists() {
        Sweet sweet = new Sweet(); sweet.setId(1L); sweet.setName("Chocolate");
        when(sweetRepo.findById(1L)).thenReturn(Optional.of(sweet));

        Optional<Sweet> result = sweetService.getSweetById(1L);

        assertTrue(result.isPresent());
        assertEquals("Chocolate", result.get().getName());
    }

    @Test
    void searchSweets_ByName_ShouldReturnMatchingSweets() {
        Sweet sweet = new Sweet(); sweet.setName("Chocolate");
        when(sweetRepo.findByNameContainingIgnoreCase("Choco")).thenReturn(List.of(sweet));

        List<Sweet> result = sweetService.searchSweets("Choco", null, null, null);

        assertEquals(1, result.size());
        assertEquals("Chocolate", result.get(0).getName());
    }

    @Test
    void searchSweets_ByCategory_ShouldReturnMatchingSweets() {
        Sweet sweet = new Sweet(); sweet.setCategory("Candy");
        when(sweetRepo.findByCategoryIgnoreCase("Candy")).thenReturn(List.of(sweet));

        List<Sweet> result = sweetService.searchSweets(null, "Candy", null, null);

        assertEquals(1, result.size());
        assertEquals("Candy", result.get(0).getCategory());
    }

    @Test
    void searchSweets_ByPriceRange_ShouldReturnMatchingSweets() {
        Sweet sweet = new Sweet(); sweet.setPrice(5.0);
        when(sweetRepo.findByPriceBetween(1.0, 10.0)).thenReturn(List.of(sweet));

        List<Sweet> result = sweetService.searchSweets(null, null, 1.0, 10.0);

        assertEquals(1, result.size());
        assertEquals(5.0, result.get(0).getPrice());
    }

    @Test
    void updateSweet_ShouldThrowExceptionIfNotFound() {
        Sweet sweet = new Sweet(); sweet.setName("Candy");
        when(sweetRepo.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class,
                () -> sweetService.updateSweet(1L, sweet));

        assertEquals("Sweet not found with id: 1", exception.getMessage());
    }

    @Test
    void deleteSweet_ShouldCallRepositoryDelete() {
        sweetService.deleteSweet(1L);
        verify(sweetRepo, times(1)).deleteById(1L);
    }
}


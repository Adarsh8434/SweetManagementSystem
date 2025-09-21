package com.sweetshop.sweetshop_management.controller;

import com.sweetshop.sweetshop_management.entity.Sweet;
import com.sweetshop.sweetshop_management.service.SweetService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SweetControllerTest {

    @Mock
    private SweetService sweetService;

    @InjectMocks
    private SweetController sweetController;

    private Sweet sweet1;
    private Sweet sweet2;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        sweet1 = new Sweet();
        sweet1.setId(1L);
        sweet1.setName("Chocolate");
        sweet1.setCategory("Candy");
        sweet1.setPrice(10.0);
        sweet1.setQuantity(50);

        sweet2 = new Sweet();
        sweet2.setId(2L);
        sweet2.setName("Lollipop");
        sweet2.setCategory("Candy");
        sweet2.setPrice(5.0);
        sweet2.setQuantity(100);
    }

    // --- RED: Add Sweet test ---
    @Test
    void addSweet_Success() {
        when(sweetService.addSweet(sweet1)).thenReturn(sweet1);

        ResponseEntity<Sweet> response = sweetController.addSweet(sweet1);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Chocolate", response.getBody().getName());

        verify(sweetService, times(1)).addSweet(sweet1);
    }

    // --- RED: Get All Sweets ---
    @Test
    void getAllSweets_ReturnsList() {
        List<Sweet> sweets = Arrays.asList(sweet1, sweet2);
        when(sweetService.getAllSweets()).thenReturn(sweets);

        ResponseEntity<List<Sweet>> response = sweetController.getAllSweets();

        assertNotNull(response);
        assertEquals(2, response.getBody().size());
        verify(sweetService, times(1)).getAllSweets();
    }

    // --- RED: Search Sweets ---
    @Test
    void searchSweets_ByName() {
        when(sweetService.searchSweets("Chocolate", null, null, null))
                .thenReturn(Arrays.asList(sweet1));

        ResponseEntity<List<Sweet>> response = sweetController.searchSweets("Chocolate", null, null, null);

        assertNotNull(response);
        assertEquals(1, response.getBody().size());
        assertEquals("Chocolate", response.getBody().get(0).getName());
        verify(sweetService, times(1)).searchSweets("Chocolate", null, null, null);
    }

    // --- RED: Update Sweet ---
    @Test
    void updateSweet_Success() {
        Sweet updatedSweet = new Sweet();
        updatedSweet.setName("Chocolate Deluxe");
        updatedSweet.setCategory("Candy");
        updatedSweet.setPrice(15.0);
        updatedSweet.setQuantity(40);

        when(sweetService.updateSweet(1L, updatedSweet)).thenReturn(updatedSweet);

        ResponseEntity<Sweet> response = sweetController.updateSweet(1L, updatedSweet);

        assertNotNull(response);
        assertEquals("Chocolate Deluxe", response.getBody().getName());
        verify(sweetService, times(1)).updateSweet(1L, updatedSweet);
    }

    // --- RED: Delete Sweet ---
    @Test
    void deleteSweet_Success() {
        doNothing().when(sweetService).deleteSweet(1L);

        ResponseEntity<Void> response = sweetController.deleteSweet(1L);

        assertNotNull(response);
        assertEquals(204, response.getStatusCodeValue());
        verify(sweetService, times(1)).deleteSweet(1L);
    }
     @Test
    void purchaseSweet_Success() {
        Sweet mockSweet = new Sweet();
        mockSweet.setId(1L);
        mockSweet.setName("Ladoo");
        mockSweet.setQuantity(8);

        when(sweetService.purchaseSweet(1L, 2)).thenReturn(mockSweet);

        ResponseEntity<Sweet> response = sweetController.purchaseSweet(1L, 2);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Ladoo", response.getBody().getName());
        assertEquals(8, response.getBody().getQuantity());
    }

    @Test
    void restockSweet_AdminOnly_Success() {
        Sweet mockSweet = new Sweet();
        mockSweet.setId(1L);
        mockSweet.setName("Ladoo");
        mockSweet.setQuantity(20);

        when(sweetService.restockSweet(1L, 10)).thenReturn(mockSweet);

        ResponseEntity<Sweet> response = sweetController.restockSweet(1L, 10);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(20, response.getBody().getQuantity());
    }

}

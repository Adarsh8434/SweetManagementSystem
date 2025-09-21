package com.sweetshop.sweetshop_management.exception;

public class SweetStockEnded extends RuntimeException {
   public SweetStockEnded(Long id) {
        super("Sweet stock ended for sweet with id: " + id);
    }
}

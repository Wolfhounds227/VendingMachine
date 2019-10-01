/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.summative3.service;

/**
 *
 * @author Wolfhounds
 */
public class ItemOutOfStockException extends Exception {
     public ItemOutOfStockException(String message) {
        super(message);
    }

    public ItemOutOfStockException(String message, Throwable inner) {
        super(message, inner);
    }   
}

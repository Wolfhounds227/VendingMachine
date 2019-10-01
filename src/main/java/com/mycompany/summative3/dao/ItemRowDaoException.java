/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.summative3.dao;

/**
 *
 * @author Wolfhounds
 */
public class ItemRowDaoException extends Exception {
    public ItemRowDaoException(String message){
        super( message );
    }
    public ItemRowDaoException(String message, Throwable inner){
        super(message, inner);
    }
}


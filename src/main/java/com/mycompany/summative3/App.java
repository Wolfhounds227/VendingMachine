/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.summative3;

import com.mycompany.summative3.controller.VendController;
import com.mycompany.summative3.dao.ItemRowDaoException;
import com.mycompany.summative3.dao.VendDao;
import com.mycompany.summative3.dao.VendDaoFileImpl;
import com.mycompany.summative3.dao.VendPersistenceException;
import com.mycompany.summative3.service.InsufficientFundsException;
import com.mycompany.summative3.service.InvalidIdException;
import com.mycompany.summative3.service.ItemOutOfStockException;
import com.mycompany.summative3.service.VendServiceLayer;
import com.mycompany.summative3.service.VendServiceLayerImpl;
import com.mycompany.summative3.ui.UserIO;
import com.mycompany.summative3.ui.UserIOFileImpl;
import com.mycompany.summative3.ui.VendView;

/**
 *
 * @author Wolfhounds
 */
public class App {
    public static void main(String[] args) throws InsufficientFundsException, ItemOutOfStockException, ItemRowDaoException, VendPersistenceException, InvalidIdException {
        UserIO myIo = new UserIOFileImpl();
        VendView myView = new VendView(myIo);
        VendDao myDao = new VendDaoFileImpl("vend.txt");
        VendServiceLayer myServiceLayer = new VendServiceLayerImpl(myDao);
        VendController controller;
        controller = new VendController(myView,myServiceLayer);

        controller.run();
       
    
    
    }
   
}

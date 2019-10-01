/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.summative3.controller;

import com.mycompany.summative3.dao.ItemRowDaoException;
import com.mycompany.summative3.dto.Change;
import com.mycompany.summative3.service.InsufficientFundsException;
import com.mycompany.summative3.dao.VendPersistenceException;
import com.mycompany.summative3.service.InvalidIdException;
import com.mycompany.summative3.service.ItemOutOfStockException;
import com.mycompany.summative3.service.VendServiceLayer;
import com.mycompany.summative3.ui.VendView;

/**
 *
 * @author Wolfhounds
 */
public class VendController {

    private VendView view;
    private VendServiceLayer service;

    public VendController(VendView view, VendServiceLayer serviceLayer) {
        this.view = view;
        this.service = serviceLayer;

    }

    public void run() throws InsufficientFundsException, ItemOutOfStockException, ItemRowDaoException, InvalidIdException {
        boolean keepGoing = true;
        int menuSelection = 0;
        try {
            while (keepGoing) {

                menuSelection = getMenuSelection();

                switch (menuSelection) {
                    case 1:
                        displayAll();
                        break;
                    case 2:
                        addCash();
                        break;
                    case 3:
                        Purchase();
                        break;
                    case 4:
                        giveRemainingChange();
                        keepGoing = false;
                        break;
                    default:
                        unknownCommand();
                }

            }
            exitMessage();
        } catch (VendPersistenceException e) {
            view.displayErrorMessage(e.getMessage());
        }
    }

    private int getMenuSelection() {
        return view.printMenuGetMoney(service.getCurrentMoney());
    }

    private void Purchase() throws VendPersistenceException, InsufficientFundsException, ItemOutOfStockException, ItemRowDaoException, InvalidIdException {
        boolean good = false;
        while (!good) {
            try {
                Change change = service.purchaseItemFromRow(view.getChoice());
//                view.displayChangeGiven(change);
                view.displaySuccessfulPurchase();
                good = true;
            } catch (VendPersistenceException | InsufficientFundsException | ItemOutOfStockException e) {
                view.displayErrorMessage(e.getMessage());
                break;
            }

        }
    }
//    private void giveCash() throws VendPersistenceException {
//        view.displayChangeGiven(service.returnChange());
//    }
    private void exitMessage(){
        view.DisplayExitMsg();
    }
    private void unknownCommand(){
        view.displayUnknownCommandBanner();
    }
    private void displayAll() throws VendPersistenceException, ItemRowDaoException{
        view.displayAll(service.getAllItems());
    }
    private void addCash(){
        service.addMoney(view.getMoney());
        view.DisplayCurrentCash(service.getCurrentMoney());
    }
    private void giveRemainingChange(){
        view.displayChangeGiven(service.returnChange());
    }
}

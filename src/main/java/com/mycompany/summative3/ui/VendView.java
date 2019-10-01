/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.summative3.ui;

import com.mycompany.summative3.dto.Change;
import com.mycompany.summative3.dto.ItemRow;
import java.math.BigDecimal;
import java.util.List;

/**
 *
 * @author Wolfhounds
 */
public class VendView {
    
    UserIO io;
    public VendView(UserIO io) {
        this.io = io;
    }
    
    public int printMenuGetMoney(BigDecimal insertedMoney){
        io.print("1. Display All Items");
        io.print("2. Insert Money");
        io.print("3. Purchase an Item");
        io.print("4. Cancel order return Change, and exit");
        io.print("Current Money: $" + insertedMoney);
        
        return io.readInt("Please select a choice", 1,4);
    }
        public void displayErrorMessage(String errorMsg) {
        io.print("======ERROR=====");
        io.print(errorMsg);
    }

        public void displayAll(List<ItemRow> allItems) {
            io.print("Current Items in Stock: \n");
            for (ItemRow currentItem : allItems){
                io.print("Item id: " + String.valueOf(currentItem.getId()) + " | "
                + "Item name: " +(currentItem.getName()) + " | "
                + "Item Price: " + currentItem.getPrice() + " | " 
                + "Current Quantity: " + (String.valueOf(currentItem.getQty())));
            }
        }
        public void displaySuccessfulPurchase() {
            io.readString("Purchase was good hit enter to continue");
        }
        public int getChoice(){
            return io.readInt("Please enter your choosen item's ID");
        }
        public void DisplayExitMsg(){
            io.print("Cya!");
        }
        public void displayChangeGiven(Change changeBack){
            io.print("Your change is: ");
            io.print(changeBack.getDollars() + "Dollars");
            io.print(changeBack.getQuarters() + "Quarters");
            io.print(changeBack.getDimes() + "Dimes");
            io.print(changeBack.getNickles() + "Nickles");
            io.print(changeBack.getPennies() + "Pennies");
        }
        public void DisplayCurrentCash(BigDecimal currentCash){
            io.print("You have: $" + currentCash);
        }
        public BigDecimal getMoney(){
            BigDecimal min = BigDecimal.ZERO;
            BigDecimal max = new BigDecimal("5.00");
            return io.readBigDecimal("Please insert cash, up to five dollars at a time: ",min, max);
        }
        public void displayUnknownCommandBanner(){
            io.print("Uknown Command");
        }
}
    
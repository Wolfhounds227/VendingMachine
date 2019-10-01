/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.summative3.service;

import com.mycompany.summative3.dao.VendPersistenceException;
import com.mycompany.summative3.dao.ItemRowDaoException;
import com.mycompany.summative3.dao.VendDao;
import com.mycompany.summative3.dto.Change;
import com.mycompany.summative3.dto.ItemRow;
import java.math.BigDecimal;
import java.math.MathContext;
import java.util.List;

/**
 *
 * @author Wolfhounds
 */
public class VendServiceLayerImpl implements VendServiceLayer {

    private VendDao dao;
    private BigDecimal insertedMoney = new BigDecimal("0");

    public VendServiceLayerImpl(VendDao dao) {
        this.dao = dao;
    }

    @Override

 
    public Change returnChange() {

        return new Change(insertedMoney);

    }

    public BigDecimal getCurrentMoney() {
        return insertedMoney;
    }

    public void addMoney(BigDecimal insertedMoney) {
        this.insertedMoney = this.insertedMoney.add(insertedMoney, MathContext.UNLIMITED);
    }

    @Override
    public Change purchaseItemFromRow(int itemId) throws VendPersistenceException, ItemRowDaoException, InsufficientFundsException, InvalidIdException, ItemOutOfStockException {
        ItemRow itemPurchased = dao.getItembyId(itemId);
            if(itemPurchased == null){
                throw new InvalidIdException("id: " + itemId + " does not exist.");
            }
            if (insertedMoney.compareTo(itemPurchased.getPrice()) >= 0) {
                reduceMoney(itemPurchased.getPrice());
                if(itemPurchased.getQty() > 0){
                itemPurchased.setQty(itemPurchased.getQty() - 1);
                dao.editItemRow(itemId, itemPurchased);
                }else {
                    throw new ItemOutOfStockException("Item is out of stock. Quantity is 0");
                }
              return returnChange();
            } else {
                throw new InsufficientFundsException("Insufficient Funds");
            }    
    }
    
    @Override
    public void reduceMoney(BigDecimal reduceMoney) {
        BigDecimal newAmt = this.insertedMoney.subtract(reduceMoney);
        this.insertedMoney = newAmt;
    }

    /**
     *
     * @return @throws VendPersistenceException
     */
    @Override
    public List<ItemRow> getAllItems() throws VendPersistenceException, ItemRowDaoException {
        try {
            return dao.displayAll();
        } catch (ItemRowDaoException ex) {
          throw new ItemRowDaoException("Unable to produce all items");
        }
    }

}

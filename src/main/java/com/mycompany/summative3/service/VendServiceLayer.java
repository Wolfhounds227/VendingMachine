/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.summative3.service;

import com.mycompany.summative3.dao.VendPersistenceException;
import com.mycompany.summative3.dao.ItemRowDaoException;
import com.mycompany.summative3.dto.Change;
import com.mycompany.summative3.dto.ItemRow;
import java.math.BigDecimal;
import java.util.List;

/**
 *
 * @author Wolfhounds
 */
public interface VendServiceLayer {

    List<ItemRow> getAllItems() throws VendPersistenceException, ItemRowDaoException;

    BigDecimal getCurrentMoney();

    Change returnChange();

    void addMoney(BigDecimal t);

    Change purchaseItemFromRow(int itemId) throws VendPersistenceException, InvalidIdException, ItemOutOfStockException, ItemRowDaoException, InsufficientFundsException;

    void reduceMoney(BigDecimal reduceMoney);
}

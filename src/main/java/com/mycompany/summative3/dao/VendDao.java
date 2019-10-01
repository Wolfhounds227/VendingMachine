/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.summative3.dao;

import com.mycompany.summative3.dto.ItemRow;
import com.mycompany.summative3.service.ItemOutOfStockException;
import java.util.List;

/**
 *
 * @author Wolfhounds
 */
public interface VendDao {
    
    
    // display all method will take the list and displayall the rows
    // from the file
List<ItemRow> displayAll() throws ItemRowDaoException;

ItemRow getItembyId(int id) throws ItemRowDaoException;

public void editItemRow( int oldId, ItemRow edited  ) throws ItemRowDaoException;


//void add() throws VendPersistenceException;
}


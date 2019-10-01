/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.summative3.dao;
   
import com.mycompany.summative3.dto.ItemRow;
import com.mycompany.summative3.service.ItemOutOfStockException;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Wolfhounds
 */
public class VendDaoInMemo implements VendDao {
   private List<ItemRow> memContainer = new ArrayList<>();
   
       ItemRow testItem = new ItemRow();
       ItemRow testItem2 = new ItemRow();
       ItemRow testItem3 = new ItemRow();
       public VendDaoInMemo(){
       testItem.setId(1);
       testItem.setName("Cookie");
       testItem.setPrice(new BigDecimal("1.50"));
       testItem.setQty(10);
       testItem2.setId(2);
       testItem2.setName("Snickers");
       testItem2.setPrice(new BigDecimal("1.00"));
       testItem2.setQty(1);
       testItem3.setId(3);
       testItem3.setName("sandwich");
       testItem3.setPrice(new BigDecimal("5.00"));
       testItem3.setQty(0);
       
       memContainer.add(testItem);
       memContainer.add(testItem2);
       memContainer.add(testItem3);
       }
      public List<ItemRow> displayAll(){
        
        return memContainer;
    }
    public void editItemRow( int oldId, ItemRow edited  ) throws ItemRowDaoException {

        List<ItemRow> allItems = displayAll();

        int matchIndex = -1;

        for (int i = 0; i < allItems.size(); i++) {
            ItemRow toCheck = allItems.get(i);

            if (toCheck.getId() == oldId) {
                matchIndex = i;
                break;
            }
        }

        if (matchIndex == -1) { //we didn't find a match

            throw new ItemRowDaoException("ERROR: could not edit DVD with id " + oldId);
        }

        allItems.remove(matchIndex);
        allItems.add(edited);

    }

    /**
     *
     * @param id
     * @return
     * @throws VendPersistenceException
     */
    @Override
    public ItemRow getItembyId(int id) throws ItemRowDaoException {
    ItemRow toReturn = null;
    
    List<ItemRow> allItems = displayAll();
    
    for(ItemRow toCheck : allItems){
        if(toCheck.getId()== id){
            toReturn = toCheck;
            break;
        }
    }
    return toReturn;
    }
}


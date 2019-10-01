/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.summative3.dao;

import com.mycompany.summative3.dto.ItemRow;
import java.util.List;

/**
 *
 * @author Wolfhounds
 */
public class AlwaysFailVendDao implements VendDao  {

    @Override
    public List<ItemRow> displayAll() throws ItemRowDaoException {
        throw new ItemRowDaoException("Failed");
    }

    @Override
    public ItemRow getItembyId(int id) throws ItemRowDaoException {
        throw new ItemRowDaoException("Failed");
    }

    @Override
    public void editItemRow(int oldId, ItemRow edited) throws ItemRowDaoException {
        throw new ItemRowDaoException("Failed");
    }
    
}

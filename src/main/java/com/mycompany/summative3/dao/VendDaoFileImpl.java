/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.summative3.dao;

import com.mycompany.summative3.dto.ItemRow;
import com.mycompany.summative3.service.ItemOutOfStockException;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author Wolfhounds
 */
public class VendDaoFileImpl implements VendDao{

    String path;
    
    public VendDaoFileImpl(String path){
        this.path = path;
    }
    
    /**
     *
     * @return
     * @throws VendPersistenceException
     */
    @Override
        public List<ItemRow> displayAll() throws ItemRowDaoException{
        List<ItemRow> toReturn = new ArrayList<>();
        
        FileReader reader = null;
            try {
                reader = new FileReader(path);
                Scanner scn = new Scanner(reader);
                while(scn.hasNextLine()){
                    String line = scn.nextLine();
                    if(line.length() > 0){
                        String[] cells = line.split("::");
                        
                        ItemRow toAdd = new ItemRow();
                        toAdd.setId(Integer.parseInt(cells[0]));
                        toAdd.setName(cells[1]);
                        toAdd.setPrice(new BigDecimal(cells[2]));
                        toAdd.setQty(Integer.parseInt(cells[3]));
                        toReturn.add(toAdd);
                    }    
                }        
            } catch (FileNotFoundException ex) {
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException ex) {
                throw new ItemRowDaoException("Could not close reader for " + path, ex);
            }
        }

        return toReturn;
    }
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
    private String convertToLine(ItemRow toWrite){
    String line
            = toWrite.getId() + "::"
            + toWrite.getName() + "::"
            + toWrite.getPrice() + "::"
            + toWrite.getQty();
    return line;
    }
    
    /**
     *
     * @param itemId
     * @throws ItemRowNoItemException
     * @throws ItemRowDaoException
     * @throws VendPersistenceException
     */
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

        writeFile(allItems);
    }
   

        private void writeFile(List<ItemRow> allItems) throws ItemRowDaoException {

        FileWriter writer = null;
        try {
            writer = new FileWriter(path);
            PrintWriter pw = new PrintWriter(writer);

            for (ItemRow toWrite : allItems) {
                String line = convertToLine(toWrite);
                pw.println(line);
            }
        } catch (IOException ex) {
            throw new ItemRowDaoException("ERROR: could not write to " + path, ex);
        } finally {
            try {
                writer.close();
            } catch (IOException ex) {
                throw new ItemRowDaoException("ERROR: could not close writer for " + path, ex);
            }
        }
    }
//        public void add()throws VendPersistenceException{
//            List<ItemRow> allItems = displayAll();
//            
//        int newId = 0;
//
//        for (ItemRow toCheck : allItems) {
//            if (toCheck.getId() > newId) {
//                newId = toCheck.getId();
//            }
//        }
//        ItemRow toAdd = new ItemRow();
//        newId++;
//        toAdd.setId(newId);
//        toAdd.setName("Snickers");
//        toAdd.setPrice(new BigDecimal(1.50));
//        toAdd.setQty(40);
//
//        allItems.add(toAdd);
//
//        try {
//            writeFile(allItems);
//        } catch (ItemRowDaoException ex) {
//            Logger.getLogger(VendDaoFileImpl.class.getName()).log(Level.SEVERE, null, ex);
//        }
//
//    }
        }
//    @Override
//    public Change returnChange(ItemRow dispensed,BigDecimal moneyEntered) {
//        
//        BigDecimal reduce = dispensed.getPrice();
//      
//        Change returnChange = new Change(moneyEntered.subtract(reduce));
//        return returnChange ;
//    }}

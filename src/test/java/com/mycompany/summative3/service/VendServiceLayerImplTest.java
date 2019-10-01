/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.summative3.service;

import com.mycompany.summative3.dao.AlwaysFailVendDao;
import com.mycompany.summative3.dao.ItemRowDaoException;
import com.mycompany.summative3.dao.VendDao;
import com.mycompany.summative3.dao.VendDaoInMemo;
import com.mycompany.summative3.dao.VendPersistenceException;
import com.mycompany.summative3.dto.Change;
import com.mycompany.summative3.dto.ItemRow;
import java.math.BigDecimal;
import java.math.MathContext;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

/**
 *
 * @author Wolfhounds
 */
public class VendServiceLayerImplTest {

    public VendServiceLayerImplTest() {
    }

    @BeforeAll
    public static void setUpClass() {
    }

    @AfterAll
    public static void tearDownClass() {
    }

    @BeforeEach
    public void setUp() {
    }

    @AfterEach
    public void tearDown() {
    }

    /**
     * Test of returnChange method, of class VendServiceLayerImpl.
     */
    @Test
    public void testReturnChangeGoldenPath() {
        try {
            VendDaoInMemo dao = new VendDaoInMemo();
            VendServiceLayer service = new VendServiceLayerImpl(dao);
            service.addMoney(new BigDecimal("5.00"));
            BigDecimal currentMoney = service.getCurrentMoney();
            service.purchaseItemFromRow(2);
            Change leftOver = service.returnChange();
            assertEquals(new BigDecimal("4.00"), service.getCurrentMoney());
            assertEquals(4, leftOver.getDollars());
            assertEquals(0, leftOver.getQuarters());
            assertEquals(0, leftOver.getDimes());
            assertEquals(0, leftOver.getNickles());
            assertEquals(0, leftOver.getPennies());
        } catch (VendPersistenceException | InvalidIdException | ItemOutOfStockException | ItemRowDaoException | InsufficientFundsException ex) {
            fail();
        }
    }

    public void testReturnChangeInsufficientFunds() {
        try {
            VendDaoInMemo dao = new VendDaoInMemo();
            VendServiceLayer service = new VendServiceLayerImpl(dao);
            service.addMoney(new BigDecimal("1.00"));
            BigDecimal currentMoney = service.getCurrentMoney();
            service.purchaseItemFromRow(2);
            Change leftOver = service.returnChange();
            assertEquals(4, leftOver.getDollars());
            assertEquals(0, leftOver.getQuarters());
            assertEquals(0, leftOver.getDimes());
            assertEquals(0, leftOver.getNickles());
            assertEquals(0, leftOver.getPennies());
            fail();
        } catch (InsufficientFundsException ex) {

        } catch (VendPersistenceException | InvalidIdException | ItemOutOfStockException | ItemRowDaoException ex) {
           fail();
        }
    }

    public void testReturnChangeAlwaysFailDao() {
        try {
            VendDao dao = new AlwaysFailVendDao();
            VendServiceLayer service = new VendServiceLayerImpl(dao);
            service.addMoney(new BigDecimal("5.00"));
            BigDecimal currentMoney = service.getCurrentMoney();
            service.purchaseItemFromRow(2);
            Change leftOver = service.returnChange();
            assertEquals(new BigDecimal("4.00"), service.getCurrentMoney());
            assertEquals(4, leftOver.getDollars());
            assertEquals(0, leftOver.getQuarters());
            assertEquals(0, leftOver.getDimes());
            assertEquals(0, leftOver.getNickles());
            assertEquals(0, leftOver.getPennies());
            fail();
        } catch ( ItemRowDaoException ex) {
        } catch (VendPersistenceException | InvalidIdException | ItemOutOfStockException | InsufficientFundsException ex) {
            fail();
        }
    }

    @Test
    public void testPurchaseItemFromRowGoldenPath() {
        try {
            VendDao dao = new VendDaoInMemo();
            VendServiceLayer service = new VendServiceLayerImpl(dao);
            service.addMoney(new BigDecimal("5.00", MathContext.UNLIMITED));
            service.purchaseItemFromRow(2);
            BigDecimal currentMoney = service.getCurrentMoney();
            ItemRow toCheck = dao.getItembyId(2);
            assertEquals(2, toCheck.getId());
            assertEquals("Snickers", toCheck.getName());
            assertEquals(new BigDecimal("1.00"), toCheck.getPrice());
            assertEquals(0, toCheck.getQty());
            assertEquals(new BigDecimal("4.00"), currentMoney);
        } catch (VendPersistenceException | InvalidIdException | ItemOutOfStockException | ItemRowDaoException | InsufficientFundsException ex) {
            fail();
        }
    }

    @Test
    public void testPurchaseItemFromRowAlwaysFailDao() {
        try {
            VendDao dao = new AlwaysFailVendDao();
            VendServiceLayer service = new VendServiceLayerImpl(dao);
            service.addMoney(new BigDecimal("5.00", MathContext.UNLIMITED));
            BigDecimal currentMoney = service.getCurrentMoney();
            service.purchaseItemFromRow(1);
            ItemRow toCheck = dao.getItembyId(1);
            assertEquals(9, toCheck.getQty());
            fail();
        } catch ( ItemRowDaoException ex) {
        } catch (VendPersistenceException | InvalidIdException | ItemOutOfStockException | InsufficientFundsException ex) {
            fail();
        }
    }

    @Test
    public void testPurchaseItemFromRowInsufficientFunds() {
        try {
            VendDao dao = new VendDaoInMemo();
            VendServiceLayer service = new VendServiceLayerImpl(dao);
            service.addMoney(new BigDecimal(".50", MathContext.UNLIMITED));
            service.purchaseItemFromRow(2);
            BigDecimal currentMoney = service.getCurrentMoney();
            ItemRow toCheck = dao.getItembyId(2);
            assertEquals(2, toCheck.getId());
            assertEquals("Snickers", toCheck.getName());
            assertEquals(new BigDecimal("1.00"), toCheck.getPrice());
            assertEquals(0, toCheck.getQty());
            assertEquals(new BigDecimal("4.00"), currentMoney);
            fail();
        } catch ( InsufficientFundsException ex) {
        } catch (VendPersistenceException | InvalidIdException | ItemOutOfStockException | ItemRowDaoException ex) {
            fail();
        }
    }

    public void testPurchaseItemFromRowItemOutOfStock() {
                try {
            VendDao dao = new VendDaoInMemo();
            VendServiceLayer service = new VendServiceLayerImpl(dao);
            service.addMoney(new BigDecimal("6.00", MathContext.UNLIMITED));
            service.purchaseItemFromRow(3);
            BigDecimal currentMoney = service.getCurrentMoney();
            ItemRow toCheck = dao.getItembyId(3);
            assertEquals(3, toCheck.getId());
            assertEquals("sandwich", toCheck.getName());
            assertEquals(new BigDecimal("5.00"), toCheck.getPrice());
            assertEquals(0, toCheck.getQty());
            assertEquals(new BigDecimal("1.00"), currentMoney);
            fail();
        } catch (ItemOutOfStockException ex) {
        } catch (ItemRowDaoException | VendPersistenceException | InvalidIdException | InsufficientFundsException ex) {
           fail();
        }
    }
    @Test
    public void PurchaseItemFromRowInvalidId(){
            try {
            VendDao dao = new VendDaoInMemo();
            VendServiceLayer service = new VendServiceLayerImpl(dao);
            service.addMoney(new BigDecimal("5.00", MathContext.UNLIMITED));
            service.purchaseItemFromRow(10);
            BigDecimal currentMoney = service.getCurrentMoney();
            ItemRow toCheck = dao.getItembyId(2);
            assertEquals(2, toCheck.getId());
            assertEquals("Snickers", toCheck.getName());
            assertEquals(new BigDecimal("1.00"), toCheck.getPrice());
            assertEquals(0, toCheck.getQty());
            assertEquals(new BigDecimal("4.00"), currentMoney);
            fail();
        } catch (InvalidIdException  ex) {

        } catch (ItemRowDaoException | VendPersistenceException | ItemOutOfStockException | InsufficientFundsException ex) {
            fail();
        }     
    }
    
    @Test
    public void testGetAllItemsGoldenPath() {
            try {
            VendDao dao = new VendDaoInMemo();
            VendServiceLayer service = new VendServiceLayerImpl(dao);
            List<ItemRow> toTest = service.getAllItems();
            ItemRow testItem = toTest.get(1);
            assertEquals(3,toTest.size());
            assertEquals(2,testItem.getId());
            assertEquals("Snickers", testItem.getName());
            assertEquals(new BigDecimal("1.00"), testItem.getPrice());
            assertEquals(1,testItem.getQty());
        } catch (VendPersistenceException | ItemRowDaoException ex) {
            fail();
        }
        

    }

    @Test
    public void testGetAllItemsAlwaysFailDao() {
        try {
            VendDao dao = new AlwaysFailVendDao();
            VendServiceLayer service = new VendServiceLayerImpl(dao);
            List<ItemRow> toTest = service.getAllItems();
            ItemRow testItem = toTest.get(1);
            assertEquals(3,toTest.size());
            assertEquals(2,testItem.getId());
            assertEquals("Snickers", testItem.getName());
            assertEquals(new BigDecimal("1.00"), testItem.getPrice());
            assertEquals(1,testItem.getQty());
            fail();
        } catch ( ItemRowDaoException ex) {

        } catch (VendPersistenceException ex) {
            fail();
        }        

    }

}

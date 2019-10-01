/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.summative3.dao;

import com.mycompany.summative3.dto.ItemRow;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

/**
 *
 * @author Wolfhounds
 */
public class VendDaoFileImplTest {

    private List<ItemRow> testList = new ArrayList<>();

    public VendDaoFileImplTest() {
        
    }

    @BeforeAll
    public static void setUpClass() throws IOException {
    Path testPath = Paths.get("test.txt");
    Path seedPath = Paths.get("seed.txt");
    
    if(Files.exists(testPath)){
            Files.delete(testPath);
    }
    Files.copy(seedPath, testPath, StandardCopyOption.REPLACE_EXISTING);
    
    
    
    
    
    
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
     * Test of displayAll method, of class VendDaoFileImpl.
     */
    @Test
    public void testDisplayAllgoldenPath() {
        VendDaoFileImpl toTest = new VendDaoFileImpl("test.txt");
        try {
            List<ItemRow> allItems = toTest.displayAll();
            assertEquals(2, allItems.size());
            ItemRow testItem = allItems.get(0);
            assertEquals(1, testItem.getId());
            assertEquals("Snickers", testItem.getName());
            assertEquals(new BigDecimal(1.5), testItem.getPrice());
            assertEquals(31, testItem.getQty());
        } catch (ItemRowDaoException e) {
            fail();
        }
    }

    @Test
    public void ItemRowEditGoldenPath() {
        try {
            ItemRow tempItem = new ItemRow();
            tempItem.setId(2);
            tempItem.setName("Kit Kat");
            tempItem.setPrice(new BigDecimal("1.75"));
            tempItem.setQty(11);
            VendDaoFileImpl toTest = new VendDaoFileImpl("test.txt");
            toTest.editItemRow(2, tempItem);
            ItemRow validated = toTest.getItembyId(2);
            assertEquals("Kit Kat", validated.getName());
            assertEquals(2, validated.getId());
            assertEquals(new BigDecimal("1.75"), validated.getPrice());
            assertEquals(11, validated.getQty());
        } catch (ItemRowDaoException ex) {
            fail();
        }
    }

    /**
     * Test of ItemRowEdit method, of class VendDaoFileImpl.
     */
    @Test
    public void ItemRowEditInvalidId() {
        try {
            ItemRow tempItem = new ItemRow();
            tempItem.setId(10);
            tempItem.setName("Kit Kat");
            tempItem.setPrice(new BigDecimal("1.75"));
            tempItem.setQty(11);
            VendDaoFileImpl toTest = new VendDaoFileImpl("test.txt");
            toTest.editItemRow(4, tempItem);
            fail();
        } catch (ItemRowDaoException ex) {
        }

    }

    @Test
    public void ItemRowEditInvalidItem() {
        try {
            ItemRow tempItem = new ItemRow();
            VendDaoFileImpl toTest = new VendDaoFileImpl("test.txt");
            toTest.editItemRow(3, tempItem);
            fail();
        } catch (ItemRowDaoException ex) {
        }
    }

    @Test
    public void getItemByIdGoldenPath() {
        try {
            VendDaoFileImpl myTDao = new VendDaoFileImpl("test.txt");
            ItemRow validate = myTDao.getItembyId(1);
            assertEquals(1, validate.getId());
            assertEquals("Snickers", validate.getName());
            assertEquals(new BigDecimal("1.5"), validate.getPrice());
            assertEquals(31, validate.getQty());

        } catch (ItemRowDaoException ex) {
            fail();
        }
    }

    @Test
    public void getItemByIdInvalidId() {
        try {
            VendDaoFileImpl myTDao = new VendDaoFileImpl("test.txt");
            ItemRow validate = myTDao.getItembyId(3);
            assertNull(validate);
        } catch (ItemRowDaoException ex) {
            fail();
        }

    }
}

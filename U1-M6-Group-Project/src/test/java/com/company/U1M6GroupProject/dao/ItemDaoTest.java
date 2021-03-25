package com.company.U1M6GroupProject.dao;

import com.company.U1M6GroupProject.model.Customer;
import com.company.U1M6GroupProject.model.Invoice;
import com.company.U1M6GroupProject.model.InvoiceItem;
import com.company.U1M6GroupProject.model.Item;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.*;
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class ItemDaoTest {
    @Autowired
    CustomerDao customerDao;
    @Autowired
    ItemDao itemDao;
    @Autowired
    InvoiceDao invoiceDao;
    @Autowired
    InvoiceItemDao invoiceItemDao;

    @Before
    public void setUp() throws Exception {
        List<InvoiceItem> invoiceItemsList = invoiceItemDao.getAllInvoiceItems();
        for (InvoiceItem invoiceItem : invoiceItemsList) {
            invoiceItemDao.deleteInvoiceItem(invoiceItem.getId());
        }
        List<Invoice> invoiceList = invoiceDao.getAllInvoices();
        for (Invoice invoice : invoiceList) {
            invoiceDao.deleteInvoice(invoice.getId());
        }
        List<Item> itemList = itemDao.getAllItems();
        for (Item item : itemList) {
            itemDao.deleteItem(item.getId());
        }
        List<Customer> customerList = customerDao.getAllCustomers();
        for (Customer customer : customerList) {
            customerDao.deleteACustomer(customer.getId());
        }
    }

    @Test
    public void shouldAddItem() {
        Item item = new Item();
        item.setName("Test");
        item.setDailyRate(BigDecimal.valueOf(19.99));
        item.setDescription("test desc");

        item = itemDao.addItem(item);

        assertEquals(item, itemDao.getItem(item.getId()));
    }

    @Test
    public void shouldGetItemById() {
        Item item = new Item();
        item.setName("Test");
        item.setDailyRate(BigDecimal.valueOf(19.99));
        item.setDescription("test desc");
        item = itemDao.addItem(item);

        Item item2 = new Item();
        item2.setName("Test2");
        item2.setDailyRate(BigDecimal.valueOf(39.99));
        item2.setDescription("test desc2");
        item2 = itemDao.addItem(item2);

        assertEquals(item, itemDao.getItem(item.getId()));
        assertEquals(item2, itemDao.getItem(item2.getId()));
    }

    @Test
    public void shouldGetAllItems() {
        Item item = new Item();
        item.setName("Test");
        item.setDailyRate(BigDecimal.valueOf(19.99));
        item.setDescription("test desc");
        item = itemDao.addItem(item);

        Item item2 = new Item();
        item2.setName("Test2");
        item2.setDailyRate(BigDecimal.valueOf(39.99));
        item2.setDescription("test desc2");
        item2 = itemDao.addItem(item2);

        assertEquals(2, itemDao.getAllItems().size());
    }

    @Test
    public void shouldUpdateItem() {
        Item item = new Item();
        item.setName("Test");
        item.setDailyRate(BigDecimal.valueOf(19.99));
        item.setDescription("test desc");
        item = itemDao.addItem(item);

        item.setName("Update Test");
        item.setDescription("Update Description");
        item.setDailyRate(BigDecimal.valueOf(99.99));

        itemDao.updateItem(item);

        assertEquals(item, itemDao.getItem(item.getId()));
    }

    @Test
    public void shouldDeleteItem() {
        Item item = new Item();
        item.setName("Test");
        item.setDailyRate(BigDecimal.valueOf(19.99));
        item.setDescription("test desc");
        item = itemDao.addItem(item);

        itemDao.deleteItem(item.getId());

        assertNull(itemDao.getItem(item.getId()));
    }
}
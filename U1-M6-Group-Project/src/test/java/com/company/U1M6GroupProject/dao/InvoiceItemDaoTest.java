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
import java.time.LocalDate;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class InvoiceItemDaoTest {
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
    public void addInvoiceItem() {
        Customer customer = new Customer();
        customer.setFirstName("Test");
        customer.setLastName("Check");
        customer.setEmail("check@test.com");
        customer.setCompany("Tesla");
        customer.setPhone("000-000-0000");
        customer = customerDao.addACustomer(customer);

        Invoice invoice = new Invoice();
        invoice.setCustomerId(customer.getId());
        invoice.setOrderDate(LocalDate.of(2021, 3, 25));
        invoice.setPickupDate(LocalDate.of(2021,3,26));
        invoice.setReturnDate(LocalDate.of(2021,4,5));
        invoice.setLateFee(BigDecimal.valueOf(100.00));
        invoice = invoiceDao.addInvoice(invoice);

        Item item = new Item();
        item.setName("Test");
        item.setDailyRate(BigDecimal.valueOf(19.99));
        item.setDescription("test desc");
        item = itemDao.addItem(item);

        InvoiceItem invoiceItem = new InvoiceItem();
        invoiceItem.setInvoiceId(invoice.getId());
        invoiceItem.setItemId(item.getId());
        invoiceItem.setQuantity(4);
        invoiceItem.setUnitRate(BigDecimal.valueOf(30.00));
        invoiceItem.setDiscount(BigDecimal.valueOf(0.00));
        invoiceItem = invoiceItemDao.addInvoiceItem(invoiceItem);

        assertEquals(invoiceItem, invoiceItemDao.getInvoiceItem(invoiceItem.getId()));
    }

    @Test
    public void getInvoiceItem() {
    }

    @Test
    public void getAllInvoiceItems() {
    }

    @Test
    public void updateInvoiceItem() {
    }

    @Test
    public void deleteInvoiceItem() {
    }
}
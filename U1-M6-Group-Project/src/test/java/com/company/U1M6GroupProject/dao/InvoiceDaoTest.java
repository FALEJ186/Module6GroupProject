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

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class InvoiceDaoTest {
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
    public void createInvoice() {
        Customer customer = new Customer();
        customer.setFirstName("Test");
        customer.setLastName("Check");
        customer.setEmail("check@test.com");
        customer.setCompany("Tesla");
        customer.setPhone("000-000-0000");
        customer = customerDao.addACustomer(customer);

        Invoice invoice1 = new Invoice();
        invoice1.setId(1);
        invoice1.setCustomerId(1);
        invoice1.setOrderDate(LocalDate.of(2018,3,16));
        invoice1.setPickupDate(LocalDate.of(2018,4,16));
        invoice1.setReturnDate(LocalDate.of(2018,8, 17));
        invoice1.setLateFee(new BigDecimal(19.99));

        invoice1 = invoiceDao.addInvoice(invoice1);
    }

    @Test
    public void updateInvoice() {
        Customer customer = new Customer();
        customer.setFirstName("Test");
        customer.setLastName("Check");
        customer.setEmail("check@test.com");
        customer.setCompany("Tesla");
        customer.setPhone("000-000-0000");
        customer = customerDao.addACustomer(customer);

        Invoice invoice1 = new Invoice();
        invoice1.setId(1);
        invoice1.setCustomerId(1);
        invoice1.setOrderDate(LocalDate.of(2018,3,16));
        invoice1.setPickupDate(LocalDate.of(2018,4,16));
        invoice1.setReturnDate(LocalDate.of(2018,8, 17));
        invoice1.setLateFee(new BigDecimal(19.99));

        invoice1 = invoiceDao.addInvoice(invoice1);

        invoice1.setLateFee(new BigDecimal(29.99));

        invoiceDao.updateInvoice(invoice1);

        Invoice invoice2 = invoiceDao.getInvoice(invoice1.getId());

    }

    @Test
    public void deleteInvoice() {

        Customer customer = new Customer();
        customer.setFirstName("Test");
        customer.setLastName("Check");
        customer.setEmail("check@test.com");
        customer.setCompany("Tesla");
        customer.setPhone("000-000-0000");
        customer = customerDao.addACustomer(customer);

        Invoice invoice1 = new Invoice();
        invoice1.setCustomerId(customer.getId());
        invoice1.setOrderDate(LocalDate.of(2018,3,16));
        invoice1.setPickupDate(LocalDate.of(2018,4,16));
        invoice1.setReturnDate(LocalDate.of(2018,8, 17));
        invoice1.setLateFee(new BigDecimal(19.99));

        invoice1 = invoiceDao.addInvoice(invoice1);

        Invoice invoice2 = invoiceDao.getInvoice(invoice1.getId());

        invoiceDao.deleteInvoice(invoice1.getId());

        invoice2 = invoiceDao.getInvoice(invoice1.getId());

        assertNull(invoice2);
    }

    @Test
    public void findInvoiceByCustomer() {
    }
}
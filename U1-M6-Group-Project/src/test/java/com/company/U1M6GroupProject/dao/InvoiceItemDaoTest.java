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
import static org.junit.Assert.assertNull;

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
        invoiceItem.setUnitRate(new BigDecimal("30.00"));
        invoiceItem.setDiscount(new BigDecimal("0.00"));
        invoiceItem = invoiceItemDao.addInvoiceItem(invoiceItem);

        assertEquals(invoiceItem, invoiceItemDao.getInvoiceItem(invoiceItem.getId()));
    }

    @Test
    public void getInvoiceItem() {
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
        invoiceItem.setUnitRate(new BigDecimal("30.00"));
        invoiceItem.setDiscount(new BigDecimal("0.00"));
        invoiceItem = invoiceItemDao.addInvoiceItem(invoiceItem);

        Customer customer2 = new Customer();
        customer2.setFirstName("Test2");
        customer2.setLastName("Check2");
        customer2.setEmail("check@test2.com");
        customer2.setCompany("Tesla2");
        customer2.setPhone("000-000-0002");
        customer2 = customerDao.addACustomer(customer2);

        Invoice invoice2 = new Invoice();
        invoice2.setCustomerId(customer2.getId());
        invoice2.setOrderDate(LocalDate.of(2021, 3, 27));
        invoice2.setPickupDate(LocalDate.of(2021,3,29));
        invoice2.setReturnDate(LocalDate.of(2021,4,7));
        invoice2.setLateFee(BigDecimal.valueOf(100.02));
        invoice2 = invoiceDao.addInvoice(invoice2);

        Item item2 = new Item();
        item2.setName("Test2");
        item2.setDailyRate(BigDecimal.valueOf(19.92));
        item2.setDescription("test desc2");
        item2 = itemDao.addItem(item2);

        InvoiceItem invoiceItem2 = new InvoiceItem();
        invoiceItem2.setInvoiceId(invoice2.getId());
        invoiceItem2.setItemId(item2.getId());
        invoiceItem2.setQuantity(4);
        invoiceItem2.setUnitRate(new BigDecimal("30.02"));
        invoiceItem2.setDiscount(new BigDecimal("0.02"));
        invoiceItem2 = invoiceItemDao.addInvoiceItem(invoiceItem2);

        assertEquals(invoiceItem, invoiceItemDao.getInvoiceItem(invoiceItem.getId()));
        assertEquals(invoiceItem2, invoiceItemDao.getInvoiceItem(invoiceItem2.getId()));
    }

    @Test
    public void getAllInvoiceItems() {
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
        invoiceItem.setUnitRate(new BigDecimal("30.00"));
        invoiceItem.setDiscount(new BigDecimal("0.00"));
        invoiceItem = invoiceItemDao.addInvoiceItem(invoiceItem);

        Customer customer2 = new Customer();
        customer2.setFirstName("Test2");
        customer2.setLastName("Check2");
        customer2.setEmail("check@test2.com");
        customer2.setCompany("Tesla2");
        customer2.setPhone("000-000-0002");
        customer2 = customerDao.addACustomer(customer2);

        Invoice invoice2 = new Invoice();
        invoice2.setCustomerId(customer2.getId());
        invoice2.setOrderDate(LocalDate.of(2021, 3, 27));
        invoice2.setPickupDate(LocalDate.of(2021,3,29));
        invoice2.setReturnDate(LocalDate.of(2021,4,7));
        invoice2.setLateFee(BigDecimal.valueOf(100.02));
        invoice2 = invoiceDao.addInvoice(invoice2);

        Item item2 = new Item();
        item2.setName("Test2");
        item2.setDailyRate(BigDecimal.valueOf(19.92));
        item2.setDescription("test desc2");
        item2 = itemDao.addItem(item2);

        InvoiceItem invoiceItem2 = new InvoiceItem();
        invoiceItem2.setInvoiceId(invoice2.getId());
        invoiceItem2.setItemId(item2.getId());
        invoiceItem2.setQuantity(4);
        invoiceItem2.setUnitRate(new BigDecimal("30.02"));
        invoiceItem2.setDiscount(new BigDecimal("0.02"));
        invoiceItem2 = invoiceItemDao.addInvoiceItem(invoiceItem2);

        assertEquals(2, invoiceItemDao.getAllInvoiceItems().size());
    }

    @Test
    public void updateInvoiceItem() {
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
        invoiceItem.setUnitRate(new BigDecimal("30.00"));
        invoiceItem.setDiscount(new BigDecimal("0.00"));
        invoiceItem = invoiceItemDao.addInvoiceItem(invoiceItem);

        invoiceItem.setUnitRate(new BigDecimal("99.99"));
        invoiceItem.setDiscount(new BigDecimal("89.89"));

        invoiceItemDao.updateInvoiceItem(invoiceItem);

        assertEquals(invoiceItem,invoiceItemDao.getInvoiceItem(invoiceItem.getId()));
    }

    @Test
    public void deleteInvoiceItem() {
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

        invoiceItemDao.deleteInvoiceItem(invoice.getId());

        assertNull(invoiceItemDao.getInvoiceItem(invoice.getId()));
    }
}
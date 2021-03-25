package com.company.U1M6GroupProject.dao;
//
import com.company.U1M6GroupProject.model.Customer;
import com.company.U1M6GroupProject.model.Invoice;
import com.company.U1M6GroupProject.model.InvoiceItem;
import com.company.U1M6GroupProject.model.Item;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.Assert.*;

public class CustomerDaoTest {

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
    public void shouldAddCustomer() {
        Customer customer = new Customer();
        customer.setPhone("555-5555");
        customer.setCompany("Cognizant");
        customer.setEmail("abc@gmail.com");
        customer.setFirstName("Bob");
        customer.setFirstName("Jones");

        customer = customerDao.addACustomer(customer);

        Customer customer1 = customerDao.getACustomer(customer.getId());

        assertEquals(customer,customer1);


    }

    @Test
    public void shouldGetCustomer() {

        Customer customer = new Customer();
        customer.setPhone("555-5555");
        customer.setCompany("Cognizant");
        customer.setEmail("abc@gmail.com");
        customer.setFirstName("Bob");
        customer.setFirstName("Jones");

        customer = customerDao.addACustomer(customer);

        Customer customer1 = customerDao.getACustomer(customer.getId());

        assertEquals(customer,customer1);
    }

    @Test
    public void shouldGetAllCustomers() {

        Customer customer = new Customer();
        customer.setPhone("555-5555");
        customer.setCompany("Cognizant");
        customer.setEmail("abc@gmail.com");
        customer.setFirstName("Bob");
        customer.setFirstName("Jones");

        Customer customer2 = new Customer();
        customer2.setPhone("444-5555");
        customer2.setCompany("Google");
        customer2.setEmail("123@gmail.com");
        customer2.setFirstName("Bobby");
        customer2.setFirstName("Smith");

        customerDao.addACustomer(customer);
        customerDao.addACustomer(customer2);

        List<Customer> customerList = customerDao.getAllCustomers();

        assertEquals(2, customerList.size());

    }

    @Test
    public void shouldUpdateCustomer() {

        Customer customer = new Customer();
        customer.setPhone("555-5555");
        customer.setCompany("Cognizant");
        customer.setEmail("abc@gmail.com");
        customer.setFirstName("Bob");
        customer.setFirstName("Jones");

        customer = customerDao.addACustomer(customer);

        customer.setPhone("888-5555");
        customer.setCompany("new");
        customer.setEmail("other");
        customer.setFirstName("joe");
        customer.setFirstName("smith");

        customerDao.updateCustomer(customer);
        Customer customer2 = customerDao.getACustomer(customer.getId());

        assertEquals(customer2,customer);
    }

    @Test
    public void shouldDeleteCustomer() {

        Customer customer = new Customer();
        customer.setPhone("555-5555");
        customer.setCompany("Cognizant");
        customer.setEmail("abc@gmail.com");
        customer.setFirstName("Bob");
        customer.setFirstName("Jones");

        Customer customer2 = new Customer();
        customer2.setPhone("444-5555");
        customer2.setCompany("Google");
        customer2.setEmail("123@gmail.com");
        customer2.setFirstName("Bobby");
        customer2.setFirstName("Smith");

        customer = customerDao.addACustomer(customer);
        customer2 = customerDao.addACustomer(customer2);

        customerDao.deleteACustomer(customer.getId());

        List<Customer> customerList = customerDao.getAllCustomers();

        assertEquals(1, customerList.size());

        assertEquals(customer2, customerList.get(0));



    }





}
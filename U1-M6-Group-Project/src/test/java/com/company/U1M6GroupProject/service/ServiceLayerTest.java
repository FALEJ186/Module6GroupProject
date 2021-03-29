package com.company.U1M6GroupProject.service;

import com.company.U1M6GroupProject.dao.*;
import com.company.U1M6GroupProject.model.Customer;
import com.company.U1M6GroupProject.model.Invoice;
import com.company.U1M6GroupProject.model.InvoiceItem;
import com.company.U1M6GroupProject.model.Item;
import com.company.U1M6GroupProject.viewmodel.InvoiceItemViewModel;
import com.company.U1M6GroupProject.viewmodel.InvoiceViewModel;
import org.junit.Before;
import org.junit.Test;
import org.springframework.data.relational.core.sql.In;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

public class ServiceLayerTest {

    ServiceLayer service;
    CustomerDao customerDao;
    InvoiceDao invoiceDao;
    InvoiceItemDao invoiceItemDao;
    ItemDao itemDao;


    private void setUpInvoiceDaoMock() {
        InvoiceDao invoiceDaoMock = mock(InvoiceDaoJdbcTemplateImpl.class);

        Invoice invoice = new Invoice();
        invoice.setId(5);
        invoice.setCustomerId(45);
        invoice.setLateFee(new BigDecimal("10.00"));
        invoice.setOrderDate(LocalDate.of(2010,5,10));
        invoice.setPickupDate(LocalDate.of(2010,6,20));
        invoice.setReturnDate(LocalDate.of(2010,6,30));

        Invoice invoice2 = new Invoice();

        invoice2.setCustomerId(45);
        invoice2.setLateFee(new BigDecimal("10.00"));
        invoice2.setOrderDate(LocalDate.of(2010,5,10));
        invoice2.setPickupDate(LocalDate.of(2010,6,20));
        invoice2.setReturnDate(LocalDate.of(2010,6,30));

        List<Invoice> invoiceList = new ArrayList<>();
        invoiceList.add(invoice);

        doReturn(invoiceList).when(invoiceDaoMock).getAllInvoices();

        doReturn(invoice).when(invoiceDaoMock).addInvoice(invoice2);

        doReturn(invoice).when(invoiceDaoMock).getInvoice(5);

        List<Invoice> testing = new ArrayList<>();
        testing.add(invoice);

        doReturn(testing).when(invoiceDaoMock).findInvoiceByCustomerId(45);


        this.invoiceDao = invoiceDaoMock;


    }


    private void setUpCustomerDaoMock () {
        CustomerDao customerDaoMock = mock(CustomerDaoJdbcTemplateImpl.class);

        Customer customer = new Customer();
        customer.setId(45);
        customer.setFirstName("Bob");
        customer.setLastName("Jones");
        customer.setEmail("abc@gmail.com");
        customer.setPhone("555-5555");
        customer.setCompany("Cognizant");


        Customer customer2 = new Customer();
        customer2.setFirstName("Bob");
        customer2.setLastName("Jones");
        customer2.setEmail("abc@gmail.com");
        customer2.setPhone("555-5555");
        customer2.setCompany("Cognizant");

        List<Customer> customerList = new ArrayList<>();
        customerList.add(customer);

        doReturn(customerList).when(customerDaoMock).getAllCustomers();

        doReturn(customer).when(customerDaoMock).addACustomer(customer2);

        doReturn(customer).when(customerDaoMock).getACustomer(45);

        this.customerDao = customerDaoMock;

    }



    private void setUpItemDaoMock() {
        ItemDao itemDaoMock = mock(ItemDaoJdbcTemplateImpl.class);

        Item item = new Item();
        item.setId(10);
        item.setDailyRate(new BigDecimal("5.99"));
        item.setDescription("movie");
        item.setName("Toy Story");

        Item item2 = new Item();
        item2.setDailyRate(new BigDecimal("5.99"));
        item2.setDescription("movie");
        item2.setName("Toy Story");

        List<Item> itemList = new ArrayList<>();
        itemList.add(item);

        doReturn(itemList).when(itemDaoMock).getAllItems();

        doReturn(item).when(itemDaoMock).addItem(item2);

        doReturn(item).when(itemDaoMock).getItem(10);

        this.itemDao = itemDaoMock;


    }

    private void setUpInvoiceItemDaoMock(){
        InvoiceItemDao invoiceItemDaoMock = mock(InvoiceItemDaoJdbcTemplateImpl.class);

        InvoiceItem invoiceItem = new InvoiceItem();
        invoiceItem.setId(20);
        invoiceItem.setInvoiceId(5);
        invoiceItem.setItemId(10);
        invoiceItem.setUnitRate(new BigDecimal("5.00"));
        invoiceItem.setQuantity(6);
        invoiceItem.setDiscount(new BigDecimal("2.50"));

        InvoiceItem invoiceItem2 = new InvoiceItem();

        invoiceItem2.setInvoiceId(5);
        invoiceItem2.setItemId(10);
        invoiceItem2.setUnitRate(new BigDecimal("5.00"));
        invoiceItem2.setQuantity(6);
        invoiceItem2.setDiscount(new BigDecimal("2.50"));

        List<InvoiceItem> invoiceItemList = new ArrayList<>();
        invoiceItemList.add(invoiceItem);


        doReturn(invoiceItemList).when(invoiceItemDaoMock).getAllInvoiceItems();

        doReturn(invoiceItem).when(invoiceItemDaoMock).addInvoiceItem(invoiceItem2);

        doReturn(invoiceItem).when(invoiceItemDaoMock).getInvoiceItem(20);

        doReturn(invoiceItemList).when(invoiceItemDaoMock).getInvoicesItemByInvoiceId(5);

        this.invoiceItemDao = invoiceItemDaoMock;


    }

    @Before
    public void setUp () throws Exception {
        setUpCustomerDaoMock();
        setUpInvoiceDaoMock();
        setUpItemDaoMock();
        setUpInvoiceItemDaoMock();

        service = new ServiceLayer(customerDao,itemDao, invoiceItemDao, invoiceDao);

    }


    @Test
    public void shouldSaveInvoice() {
        InvoiceViewModel expectedInvoiceViewModel = new InvoiceViewModel();

        expectedInvoiceViewModel.setId(5);

        Customer customer = new Customer();
        customer.setId(45);
        customer.setFirstName("Bob");
        customer.setLastName("Jones");
        customer.setEmail("abc@gmail.com");
        customer.setPhone("555-5555");
        customer.setCompany("Cognizant");
        expectedInvoiceViewModel.setCustomer(customer);


        Item item = new Item();
        item.setId(10);
        item.setDailyRate(new BigDecimal("5.99"));
        item.setDescription("movie");
        item.setName("Toy Story");

        InvoiceItem invoiceItem = new InvoiceItem();
        invoiceItem.setId(20);
        invoiceItem.setInvoiceId(5);
        invoiceItem.setItemId(10);
        invoiceItem.setUnitRate(new BigDecimal("5.00"));
        invoiceItem.setQuantity(6);
        invoiceItem.setDiscount(new BigDecimal("2.50"));

        expectedInvoiceViewModel.setOrderDate(LocalDate.of(2010,5,10));
        expectedInvoiceViewModel.setPickupDate(LocalDate.of(2010,6,20));
        expectedInvoiceViewModel.setReturnDate(LocalDate.of(2010,6,30));
        expectedInvoiceViewModel.setLateFee(new BigDecimal("10.00"));

        InvoiceItemViewModel iivm = new InvoiceItemViewModel();
        iivm.setId(invoiceItem.getId());
        iivm.setInvoiceId(invoiceItem.getInvoiceId());
        iivm.setItem(item);
        iivm.setQuantity(invoiceItem.getQuantity());
        iivm.setUnitRate(invoiceItem.getUnitRate());
        iivm.setDiscount(invoiceItem.getDiscount());

        List<InvoiceItemViewModel> invoiceItemViewModelList = new ArrayList<>();
        invoiceItemViewModelList.add(iivm);

        expectedInvoiceViewModel.setInvoiceItemViewModels(invoiceItemViewModelList);


        InvoiceViewModel inputInvoiceViewModel = new InvoiceViewModel();


        Customer customer2 = new Customer();
        customer2.setId(45);
        customer2.setFirstName("Bob");
        customer2.setLastName("Jones");
        customer2.setEmail("abc@gmail.com");
        customer2.setPhone("555-5555");
        customer2.setCompany("Cognizant");
        inputInvoiceViewModel.setCustomer(customer2);

        Item item2 = new Item();
        item2.setId(10);
        item2.setDailyRate(new BigDecimal("5.99"));
        item2.setDescription("movie");
        item2.setName("Toy Story");

        InvoiceItem invoiceItem2 = new InvoiceItem();
        invoiceItem2.setId(20);
        invoiceItem2.setInvoiceId(5);
        invoiceItem2.setItemId(10);
        invoiceItem2.setUnitRate(new BigDecimal("5.00"));
        invoiceItem2.setQuantity(6);
        invoiceItem2.setDiscount(new BigDecimal("2.50"));


        inputInvoiceViewModel.setOrderDate(LocalDate.of(2010,5,10));
        inputInvoiceViewModel.setPickupDate(LocalDate.of(2010,6,20));
        inputInvoiceViewModel.setReturnDate(LocalDate.of(2010,6,30));
        inputInvoiceViewModel.setLateFee(new BigDecimal("10.00"));

        InvoiceItemViewModel iivm2 = new InvoiceItemViewModel();
        iivm2.setId(invoiceItem.getId());
        iivm2.setInvoiceId(invoiceItem.getInvoiceId());
        iivm2.setItem(item);
        iivm2.setQuantity(invoiceItem.getQuantity());
        iivm2.setUnitRate(invoiceItem.getUnitRate());
        iivm2.setDiscount(invoiceItem.getDiscount());

        List<InvoiceItemViewModel> invoiceItemViewModelList2 = new ArrayList<>();
        invoiceItemViewModelList2.add(iivm2);

        inputInvoiceViewModel.setInvoiceItemViewModels(invoiceItemViewModelList2);


        InvoiceViewModel resultOfSavingInput = service.saveInvoice(inputInvoiceViewModel);

        assertEquals(expectedInvoiceViewModel,resultOfSavingInput);
    }

    @Test
    public void shouldFindInvoiceByCustomerId() {
        InvoiceViewModel expectedInvoiceViewModel = new InvoiceViewModel();

        expectedInvoiceViewModel.setId(5);

        Customer customer = new Customer();
        customer.setId(45);
        customer.setFirstName("Bob");
        customer.setLastName("Jones");
        customer.setEmail("abc@gmail.com");
        customer.setPhone("555-5555");
        customer.setCompany("Cognizant");
        expectedInvoiceViewModel.setCustomer(customer);

        Item item = new Item();
        item.setId(10);
        item.setDailyRate(new BigDecimal("5.99"));
        item.setDescription("movie");
        item.setName("Toy Story");

        InvoiceItem invoiceItem = new InvoiceItem();
        invoiceItem.setId(20);
        invoiceItem.setInvoiceId(5);
        invoiceItem.setItemId(10);
        invoiceItem.setUnitRate(new BigDecimal("5.00"));
        invoiceItem.setQuantity(6);
        invoiceItem.setDiscount(new BigDecimal("2.50"));

        expectedInvoiceViewModel.setOrderDate(LocalDate.of(2010,5,10));
        expectedInvoiceViewModel.setPickupDate(LocalDate.of(2010,6,20));
        expectedInvoiceViewModel.setReturnDate(LocalDate.of(2010,6,30));
        expectedInvoiceViewModel.setLateFee(new BigDecimal("10.00"));

        InvoiceItemViewModel iivm = new InvoiceItemViewModel();
        iivm.setId(invoiceItem.getId());
        iivm.setInvoiceId(invoiceItem.getInvoiceId());
        iivm.setItem(item);
        iivm.setQuantity(invoiceItem.getQuantity());
        iivm.setUnitRate(invoiceItem.getUnitRate());
        iivm.setDiscount(invoiceItem.getDiscount());

        List<InvoiceItemViewModel> invoiceItemViewModelList = new ArrayList<>();
        invoiceItemViewModelList.add(iivm);

        expectedInvoiceViewModel.setInvoiceItemViewModels(invoiceItemViewModelList);

        InvoiceViewModel inputInvoiceViewModel = new InvoiceViewModel();


        Customer customer2 = new Customer();
        customer2.setId(45);
        customer2.setFirstName("Bob");
        customer2.setLastName("Jones");
        customer2.setEmail("abc@gmail.com");
        customer2.setPhone("555-5555");
        customer2.setCompany("Cognizant");
        inputInvoiceViewModel.setCustomer(customer2);

        Item item2 = new Item();
        item2.setId(10);
        item2.setDailyRate(new BigDecimal("5.99"));
        item2.setDescription("movie");
        item2.setName("Toy Story");

        InvoiceItem invoiceItem2 = new InvoiceItem();
        invoiceItem2.setId(20);
        invoiceItem2.setInvoiceId(5);
        invoiceItem2.setItemId(10);
        invoiceItem2.setUnitRate(new BigDecimal("5.00"));
        invoiceItem2.setQuantity(6);
        invoiceItem2.setDiscount(new BigDecimal("2.50"));


        inputInvoiceViewModel.setOrderDate(LocalDate.of(2010,5,10));
        inputInvoiceViewModel.setPickupDate(LocalDate.of(2010,6,20));
        inputInvoiceViewModel.setReturnDate(LocalDate.of(2010,6,30));
        inputInvoiceViewModel.setLateFee(new BigDecimal("10.00"));

        InvoiceItemViewModel iivm2 = new InvoiceItemViewModel();
        iivm2.setId(invoiceItem.getId());
        iivm2.setInvoiceId(invoiceItem.getInvoiceId());
        iivm2.setItem(item);
        iivm2.setQuantity(invoiceItem.getQuantity());
        iivm2.setUnitRate(invoiceItem.getUnitRate());
        iivm2.setDiscount(invoiceItem.getDiscount());

        List<InvoiceItemViewModel> invoiceItemViewModelList2 = new ArrayList<>();
        invoiceItemViewModelList2.add(iivm2);

        inputInvoiceViewModel.setInvoiceItemViewModels(invoiceItemViewModelList2);

        List<InvoiceViewModel> testInvoiceList = new ArrayList<>();
        testInvoiceList.add(expectedInvoiceViewModel);

        List<InvoiceViewModel> resultOfSearchingByCustomerId = service.findInvoiceByCustomerId(inputInvoiceViewModel.getCustomer().getId());

        assertEquals(testInvoiceList,resultOfSearchingByCustomerId);
    }

    @Test
    public void shouldFindAllInvoices() {
        InvoiceViewModel expectedInvoiceViewModel = new InvoiceViewModel();
        expectedInvoiceViewModel.setId(5);

        Customer customer2 = new Customer();
        customer2.setId(45);
        customer2.setFirstName("Bob");
        customer2.setLastName("Jones");
        customer2.setEmail("abc@gmail.com");
        customer2.setPhone("555-5555");
        customer2.setCompany("Cognizant");
        expectedInvoiceViewModel.setCustomer(customer2);

        Item item2 = new Item();
        item2.setId(10);
        item2.setDailyRate(new BigDecimal("5.99"));
        item2.setDescription("movie");
        item2.setName("Toy Story");

        InvoiceItem invoiceItem2 = new InvoiceItem();
        invoiceItem2.setId(20);
        invoiceItem2.setInvoiceId(5);
        invoiceItem2.setItemId(10);
        invoiceItem2.setUnitRate(new BigDecimal("5.00"));
        invoiceItem2.setQuantity(6);
        invoiceItem2.setDiscount(new BigDecimal("2.50"));

        expectedInvoiceViewModel.setOrderDate(LocalDate.of(2010,5,10));
        expectedInvoiceViewModel.setPickupDate(LocalDate.of(2010,6,20));
        expectedInvoiceViewModel.setReturnDate(LocalDate.of(2010,6,30));
        expectedInvoiceViewModel.setLateFee(new BigDecimal("10.00"));

        InvoiceItemViewModel iivm2 = new InvoiceItemViewModel();
        iivm2.setId(invoiceItem2.getId());
        iivm2.setInvoiceId(invoiceItem2.getInvoiceId());
        iivm2.setItem(item2);
        iivm2.setQuantity(invoiceItem2.getQuantity());
        iivm2.setUnitRate(invoiceItem2.getUnitRate());
        iivm2.setDiscount(invoiceItem2.getDiscount());

        List<InvoiceItemViewModel> invoiceItemViewModelList2 = new ArrayList<>();
        invoiceItemViewModelList2.add(iivm2);

        expectedInvoiceViewModel.setInvoiceItemViewModels(invoiceItemViewModelList2);

        List <InvoiceViewModel> list = new ArrayList<>();

        list.add(expectedInvoiceViewModel);

        List <InvoiceViewModel> resultOfRetrievingAllInvoices = service.findAllInvoices();

        assertEquals(1, resultOfRetrievingAllInvoices.size());
        assertEquals(list, resultOfRetrievingAllInvoices);


    }

    @Test

    public void shouldGetAllInvoicesByInvoiceId() {

        InvoiceViewModel expectedInvoiceViewModel = new InvoiceViewModel();

        expectedInvoiceViewModel.setId(5);

        Customer customer = new Customer();
        customer.setId(45);
        customer.setFirstName("Bob");
        customer.setLastName("Jones");
        customer.setEmail("abc@gmail.com");
        customer.setPhone("555-5555");
        customer.setCompany("Cognizant");
        expectedInvoiceViewModel.setCustomer(customer);


        Item item = new Item();
        item.setId(10);
        item.setDailyRate(new BigDecimal("5.99"));
        item.setDescription("movie");
        item.setName("Toy Story");


        InvoiceItem invoiceItem = new InvoiceItem();
        invoiceItem.setId(20);
        invoiceItem.setInvoiceId(5);
        invoiceItem.setItemId(10);
        invoiceItem.setUnitRate(new BigDecimal("5.00"));
        invoiceItem.setQuantity(6);
        invoiceItem.setDiscount(new BigDecimal("2.50"));

        expectedInvoiceViewModel.setOrderDate(LocalDate.of(2010, 5, 10));
        expectedInvoiceViewModel.setPickupDate(LocalDate.of(2010, 6, 20));
        expectedInvoiceViewModel.setReturnDate(LocalDate.of(2010, 6, 30));
        expectedInvoiceViewModel.setLateFee(new BigDecimal("10.00"));

        InvoiceItemViewModel iivm = new InvoiceItemViewModel();
        iivm.setId(invoiceItem.getId());
        iivm.setInvoiceId(invoiceItem.getInvoiceId());
        iivm.setItem(item);
        iivm.setQuantity(invoiceItem.getQuantity());
        iivm.setUnitRate(invoiceItem.getUnitRate());
        iivm.setDiscount(invoiceItem.getDiscount());

        List<InvoiceItemViewModel> invoiceItemViewModelList = new ArrayList<>();

        invoiceItemViewModelList.add(iivm);

        expectedInvoiceViewModel.setInvoiceItemViewModels(invoiceItemViewModelList);

        InvoiceViewModel resultofRetrievingInvoice = service.findInvoice(5);

        assertEquals(expectedInvoiceViewModel,resultofRetrievingInvoice);

    }

}
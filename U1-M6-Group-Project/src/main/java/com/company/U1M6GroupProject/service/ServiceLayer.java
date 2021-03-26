package com.company.U1M6GroupProject.service;

import com.company.U1M6GroupProject.dao.CustomerDao;
import com.company.U1M6GroupProject.dao.InvoiceDao;
import com.company.U1M6GroupProject.dao.InvoiceItemDao;
import com.company.U1M6GroupProject.dao.ItemDao;
import com.company.U1M6GroupProject.model.Customer;
import com.company.U1M6GroupProject.model.Invoice;
import com.company.U1M6GroupProject.model.InvoiceItem;
import com.company.U1M6GroupProject.model.Item;
import com.company.U1M6GroupProject.viewmodel.InvoiceItemViewModel;
import com.company.U1M6GroupProject.viewmodel.InvoiceViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
public class ServiceLayer {

    private CustomerDao customerDao;
    private ItemDao itemDao;
    private InvoiceItemDao invoiceItemDao;
    private InvoiceDao invoiceDao;

    @Autowired
    public ServiceLayer(CustomerDao customerDao, ItemDao itemDao, InvoiceItemDao invoiceItemDao, InvoiceDao invoiceDao) {
        this.customerDao = customerDao;
        this.itemDao = itemDao;
        this.invoiceItemDao = invoiceItemDao;
        this.invoiceDao = invoiceDao;
    }
    //Album API


    public InvoiceViewModel findInvoice(int invoiceId) {
        //get original "Invoice" object first
        Invoice invoice = invoiceDao.getInvoice(invoiceId);

        //creates "InvoiceViewModel" from original "Invoice"
        return buildInvoiceViewModel(invoice);
    }

    public List<InvoiceViewModel> findAllInvoices() {
        //gets ALL original "Invoice" objects
        List<Invoice> invoiceList = invoiceDao.getAllInvoices();

        //initializes a list of "InvoiceViewModel"
        List<InvoiceViewModel> invoiceViewModels = new ArrayList<>();

        /*creates a list of "InvoiceViewModel" corresponding to original "Invoice"
        from each "Invoice" from invoiceList*/
        for (Invoice invoice:invoiceList) {
            InvoiceViewModel ivm = buildInvoiceViewModel(invoice);
            invoiceViewModels.add(ivm);
        }

        return invoiceViewModels;
    }

    //Customer API
    public Customer saveCustomer(Customer customer) {
        return customerDao.addACustomer(customer);
    }
    public Customer findCustomer (int customerId) {
        return customerDao.getACustomer(customerId);
    }
    public List<Customer> findAllCustomers () {
        return customerDao.getAllCustomers();
    }
    public void updateCustomer(Customer customer) {
        customerDao.updateCustomer(customer);
    }
    public void removeCustomer(int customerId) {
        customerDao.deleteACustomer(customerId);
    }

    //Item API
    public Item saveItem(Item item) {
        return itemDao.addItem(item);
    }

    public Item findItem(int itemId) {
        return itemDao.getItem(itemId);
    }

    public List<Item> findAllItems() {
        return itemDao.getAllItems();
    }

    public void updateItem(Item item) {
        itemDao.updateItem(item);
    }

    public void removeItem(int itemId) {
        itemDao.deleteItem(itemId);
    }

    //Invoice API
    public Invoice addInvoice (Invoice invoice) {
        return invoiceDao.addInvoice(invoice);
    }
    public void updateInvoice (Invoice invoice) {
        invoiceDao.updateInvoice(invoice);
    }
    public void deleteInvoice (int id) {
        invoiceDao.deleteInvoice(id);
    }
    public List <Invoice> findInvoiceByCustomerId (int customerId) {
        return invoiceDao.findInvoiceByCustomerId(customerId);
    }
    public List <Invoice> getAllInvoices() {
        return invoiceDao.getAllInvoices();
    }
    public Invoice getInvoice (int id) {
        return invoiceDao.getInvoice(id);
    }

    //InvoiceItemAPI
    public InvoiceItem saveInvoiceItem(InvoiceItem invoiceItem) {
        return invoiceItemDao.addInvoiceItem(invoiceItem);
    }

    public InvoiceItem findInvoiceItem(int invoiceItemId) {
        return invoiceItemDao.getInvoiceItem(invoiceItemId);
    }

    public List<InvoiceItem> findAllInvoiceItems() {
        return invoiceItemDao.getAllInvoiceItems();
    }

    public void updateInvoiceItem(InvoiceItem invoiceItem) {
        invoiceItemDao.updateInvoiceItem(invoiceItem);
    }

    public void removeInvoiceItem(int invoiceItemId) {
        invoiceItemDao.deleteInvoiceItem(invoiceItemId);
    }

    //Helper Method
    private InvoiceViewModel buildInvoiceViewModel(Invoice invoice) {
        Customer customer = customerDao.getACustomer(invoice.getCustomerId());

        //Grabs all "InvoiceItem" that matches up with invoice id
        List<InvoiceItem> initialInvoiceItemList = invoiceItemDao.getInvoicesItemByInvoiceId(invoice.getId());

        //Initializes List that contains "InvoiceItemViewModel"
        List<InvoiceItemViewModel> finalInvoiceItemViewModelList = new ArrayList<>();

        /* Takes all "InvoiceItems" from "initialInvoiceItemList" and sets it to a
        "InvoiceItemViewModel" */
        for (InvoiceItem invoiceItem : initialInvoiceItemList) {
            //Initializes new "InvoiceItemViewModel" to add into "finalInvoiceItemViewModelList"
            InvoiceItemViewModel invoiceItemViewModel = new InvoiceItemViewModel();
            //Most of these sets are grabbed directly from the original "InvoiceItem"
            invoiceItemViewModel.setId(invoiceItem.getId());
            invoiceItemViewModel.setInvoiceId(invoiceItem.getInvoiceId());
            //This is the only difference in which we grab the "Item" object using the item id as reference.
            invoiceItemViewModel.setItem(itemDao.getItem(invoiceItem.getItemId()));
            invoiceItemViewModel.setQuantity(invoiceItem.getQuantity());
            invoiceItemViewModel.setUnitRate(invoiceItem.getUnitRate());
            invoiceItemViewModel.setDiscount(invoiceItem.getDiscount());
            finalInvoiceItemViewModelList.add(invoiceItemViewModel);
        }

        InvoiceViewModel ivm = new InvoiceViewModel();
        ivm.setId(invoice.getId());
        ivm.setCustomer(customer);
        ivm.setOrderDate(invoice.getOrderDate());
        ivm.setPickupDate(invoice.getPickupDate());
        ivm.setReturnDate(invoice.getReturnDate());
        ivm.setLateFee(invoice.getLateFee());
        ivm.setInvoiceItemViewModels(finalInvoiceItemViewModelList);

        return ivm;
    }
}

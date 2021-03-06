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
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
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

    //Invoice API
    @Transactional
    public InvoiceViewModel saveInvoice(InvoiceViewModel invoiceViewModel) {
        //create invoice from invoiceViewModel
        Invoice i = new Invoice();
        i.setCustomerId(invoiceViewModel.getCustomer().getId());
        i.setOrderDate(invoiceViewModel.getOrderDate());
        i.setPickupDate(invoiceViewModel.getPickupDate());
        i.setReturnDate(invoiceViewModel.getReturnDate());
        i.setLateFee(invoiceViewModel.getLateFee());
        i = invoiceDao.addInvoice(i);
        invoiceViewModel.setId(i.getId());

        //get "InvoiceItemViewModels" from "invoiceViewModel"
        List<InvoiceItemViewModel> invoiceItemViewModelList = invoiceViewModel.getInvoiceItemViewModels();

        //change each "invoiceItemViewModel" into "invoiceItem" and add to "invoiceItem" database
        invoiceItemViewModelList.stream()
                .forEach(inIt -> {
                    InvoiceItem invoiceItemToAdd = new InvoiceItem();
                    invoiceItemToAdd.setInvoiceId(invoiceViewModel.getId());
                    invoiceItemToAdd.setItemId(inIt.getItem().getId());
                    invoiceItemToAdd.setQuantity(inIt.getQuantity());
                    invoiceItemToAdd.setUnitRate(inIt.getUnitRate());
                    invoiceItemToAdd.setDiscount(inIt.getDiscount());
                    invoiceItemDao.addInvoiceItem(invoiceItemToAdd);
                });

        //get all "invoiceItem" associated with "invoiceViewModel" id
        List<InvoiceItem> invoiceItemList = invoiceItemDao.getInvoicesItemByInvoiceId(invoiceViewModel.getId());
        //initializes array "invoiceItemViewModelsToAdd" which will be added to the return viewModel.
        List<InvoiceItemViewModel> invoiceItemViewModelsToAdd = new ArrayList<>();
        for (InvoiceItem invoiceItem : invoiceItemList) {
            invoiceItemViewModelsToAdd.add(buildInvoiceItemViewModel(invoiceItem));
        }
        //sets the invoiceViewModel List<InvoiceItemViewModel> to the array set earlier
        invoiceViewModel.setInvoiceItemViewModels(invoiceItemViewModelsToAdd);

        //return viewModel
        return invoiceViewModel;
    }

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

        /* creates a list of "InvoiceViewModel" corresponding to original "Invoice"
        from each "Invoice" from invoiceList */
        for (Invoice invoice:invoiceList) {
            InvoiceViewModel ivm = buildInvoiceViewModel(invoice);
            invoiceViewModels.add(ivm);
        }

        return invoiceViewModels;
    }

    @Transactional
    public void updateInvoice(InvoiceViewModel viewModel, int invoiceId) {
        //create invoice from invoiceViewModel
        Invoice i = new Invoice();
        i.setId(invoiceId);
        i.setCustomerId(viewModel.getCustomer().getId());
        i.setOrderDate(viewModel.getOrderDate());
        i.setPickupDate(viewModel.getPickupDate());
        i.setReturnDate(viewModel.getReturnDate());
        i.setLateFee(viewModel.getLateFee());
        invoiceDao.updateInvoice(i);

        //We get all associated InvoiceItems and delete them then re-associate them below
        List<InvoiceItem> invoiceItems = invoiceItemDao.getInvoicesItemByInvoiceId(i.getId());
        invoiceItems.stream()
                .forEach(iivm -> {
                    invoiceItemDao.deleteInvoiceItem(iivm.getId());
                });

        //get "InvoiceItemViewModels" from "invoiceViewModel"
        List<InvoiceItemViewModel> invoiceItemViewModelList = viewModel.getInvoiceItemViewModels();

        //change each "invoiceItemViewModel" into "invoiceItem" and add to "invoiceItem" database
        invoiceItemViewModelList.stream()
                .forEach(inIt -> {
                    InvoiceItem invoiceItemToAdd = new InvoiceItem();
                    invoiceItemToAdd.setInvoiceId(viewModel.getId());
                    invoiceItemToAdd.setItemId(inIt.getItem().getId());
                    invoiceItemToAdd.setQuantity(inIt.getQuantity());
                    invoiceItemToAdd.setUnitRate(inIt.getUnitRate());
                    invoiceItemToAdd.setDiscount(inIt.getDiscount());
                    invoiceItemDao.addInvoiceItem(invoiceItemToAdd);
                });

        //get all "invoiceItem" associated with "invoiceViewModel" id
        List<InvoiceItem> invoiceItemList = invoiceItemDao.getInvoicesItemByInvoiceId(viewModel.getId());
        //initializes array "invoiceItemViewModelsToAdd" which will be added to the return viewModel.
        List<InvoiceItemViewModel> invoiceItemViewModelsToAdd = new ArrayList<>();
        for (InvoiceItem invoiceItem : invoiceItemList) {
            invoiceItemViewModelsToAdd.add(buildInvoiceItemViewModel(invoiceItem));
        }

        //sets the invoiceViewModel List<InvoiceItemViewModel> to the array set earlier
        viewModel.setInvoiceItemViewModels(invoiceItemViewModelsToAdd);
    }

    @Transactional
    public void removeInvoice(int invoiceId) {
        /* Delete all associated invoiceItems first. We only need to delete "InvoiceItem"
        rather than "InvoiceItemViewModel" because  viewModel is built off the original "InvoiceItem" object
        so deleting that will delete the view model */
        List<InvoiceItem> invoiceItemList = invoiceItemDao.getInvoicesItemByInvoiceId(invoiceId);
        invoiceItemList.stream()
                .forEach(invoiceItem -> invoiceItemDao.deleteInvoiceItem(invoiceItem.getId()));

        //Remove invoice
        invoiceDao.deleteInvoice(invoiceId);
    }

    public List<InvoiceViewModel> findInvoiceByCustomerId(int customerId) {
        //get original "Invoice" object first
        List<Invoice> invoices = invoiceDao.findInvoiceByCustomerId(customerId);
        List<InvoiceViewModel> finalList = new ArrayList<>();
        for (Invoice i: invoices) {
            finalList.add(buildInvoiceViewModel(i));
        }
        return finalList;
    }

    //Customer API
    @Transactional
    public Customer saveCustomer(Customer customer) {
        return customerDao.addACustomer(customer);
    }
    public Customer findCustomer (int customerId) {
        return customerDao.getACustomer(customerId);
    }
    public List<Customer> findAllCustomers () {
        return customerDao.getAllCustomers();
    }
    @Transactional
    public void updateCustomer(Customer customer) {
        customerDao.updateCustomer(customer);
    }
    public void removeCustomer(int customerId) {
        customerDao.deleteACustomer(customerId);
    }

    //Item API
    @Transactional
    public Item saveItem(Item item) {
        return itemDao.addItem(item);
    }

    public Item findItem(int itemId) {
        return itemDao.getItem(itemId);
    }

    public List<Item> findAllItems() {
        return itemDao.getAllItems();
    }
    @Transactional
    public void updateItem(Item item) {
        itemDao.updateItem(item);
    }

    public void removeItem(int itemId) {
        itemDao.deleteItem(itemId);
    }


    //InvoiceItemAPI
    @Transactional
    public InvoiceItemViewModel saveInvoiceItem(InvoiceItemViewModel iivm) {
        InvoiceItem ii = new InvoiceItem();
        ii.setInvoiceId(iivm.getInvoiceId());
        ii.setItemId(iivm.getItem().getId());
        ii.setQuantity(iivm.getQuantity());
        ii.setUnitRate(iivm.getUnitRate());
        ii.setDiscount(iivm.getDiscount());
        ii = invoiceItemDao.addInvoiceItem(ii);
        iivm.setId(ii.getId());

        Item item = itemDao.addItem(iivm.getItem());

        iivm.setItem(item);

        return iivm;
    }

    public InvoiceItemViewModel findInvoiceItem(int invoiceItemId) {
        InvoiceItem invoiceItem = invoiceItemDao.getInvoiceItem(invoiceItemId);

        return buildInvoiceItemViewModel(invoiceItem);
    }

    public List<InvoiceItemViewModel> findAllInvoiceItems() {
        List<InvoiceItemViewModel> iivmList = new ArrayList<>();
        List<InvoiceItem> iiList = invoiceItemDao.getAllInvoiceItems();
        for (InvoiceItem ii: iiList) {
            InvoiceItemViewModel iivm = buildInvoiceItemViewModel(ii);
            iivmList.add(iivm);
        }
        return iivmList;
    }
    @Transactional
    public void updateInvoiceItem(InvoiceItemViewModel iivm, int invoiceItemId) {
        InvoiceItem ii = new InvoiceItem();
        ii.setId(invoiceItemId);
        ii.setInvoiceId(iivm.getInvoiceId());
        ii.setItemId(iivm.getItem().getId());
        ii.setQuantity(iivm.getQuantity());
        ii.setUnitRate(iivm.getUnitRate());
        ii.setDiscount(iivm.getDiscount());
        invoiceItemDao.updateInvoiceItem(ii);
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
            finalInvoiceItemViewModelList.add(buildInvoiceItemViewModel(invoiceItem));
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
    private InvoiceItemViewModel buildInvoiceItemViewModel(InvoiceItem invoiceItem) {
        Item item = itemDao.getItem(invoiceItem.getItemId());

        InvoiceItemViewModel iivm = new InvoiceItemViewModel();
        iivm.setId(invoiceItem.getId());
        iivm.setInvoiceId(invoiceItem.getInvoiceId());
        iivm.setItem(item);
        iivm.setQuantity(invoiceItem.getQuantity());
        iivm.setUnitRate(invoiceItem.getUnitRate());
        iivm.setDiscount(invoiceItem.getDiscount());

        return iivm;
    }
}

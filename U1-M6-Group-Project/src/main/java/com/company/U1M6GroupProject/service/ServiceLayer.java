package com.company.U1M6GroupProject.service;

import com.company.U1M6GroupProject.dao.CustomerDao;
import com.company.U1M6GroupProject.dao.InvoiceDao;
import com.company.U1M6GroupProject.dao.InvoiceItemDao;
import com.company.U1M6GroupProject.dao.ItemDao;
import com.company.U1M6GroupProject.model.Customer;
import com.company.U1M6GroupProject.model.Invoice;
import com.company.U1M6GroupProject.model.InvoiceItem;
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

    //Customer API



    //Item API

    //Invoice API

    //InvoiceItemAPI


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

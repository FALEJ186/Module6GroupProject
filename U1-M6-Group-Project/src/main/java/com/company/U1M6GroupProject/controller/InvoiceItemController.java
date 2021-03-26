package com.company.U1M6GroupProject.controller;

import com.company.U1M6GroupProject.model.Customer;
import com.company.U1M6GroupProject.model.InvoiceItem;
import com.company.U1M6GroupProject.service.ServiceLayer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class InvoiceItemController {

    ServiceLayer serviceLayer;


    @Autowired
    public InvoiceItemController(ServiceLayer serviceLayer) {
        this.serviceLayer = serviceLayer;
    }

    @RequestMapping(value = "/invoiceitem", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    public List<InvoiceItem> getAllInvoiceItems() {
        return serviceLayer.findAllInvoiceItems();
    }

    @RequestMapping(value ="/invoiceitem/{id}", method = RequestMethod.GET)
    @ResponseStatus (HttpStatus.OK)
    public InvoiceItem getInvoiceItemById (@PathVariable int id) {
        return serviceLayer.findInvoiceItem(id);
    }

    @RequestMapping (value = "/invoiceitem", method = RequestMethod.POST)
    @ResponseStatus (value = HttpStatus.CREATED)
    public InvoiceItem createAInvoiceItem( @RequestBody InvoiceItem invoiceItem) {
        return serviceLayer.saveInvoiceItem(invoiceItem);

    }

    @RequestMapping (value = "/invoiceitem/{id}", method = RequestMethod.PUT)
    @ResponseStatus (value = HttpStatus.OK)
    public void updateAInvoiceItem(@PathVariable int id, @RequestBody InvoiceItem invoiceItem) {
        serviceLayer.updateInvoiceItem(invoiceItem);

    }

    @RequestMapping (value = "/invoiceitem/{id}", method = RequestMethod.DELETE)
    @ResponseStatus (value = HttpStatus.OK)
    public void deleteAInvoiceItem(@PathVariable int id) {
        serviceLayer.removeInvoiceItem(id);

    }
}

package com.company.U1M6GroupProject.controller;

import com.company.U1M6GroupProject.model.Customer;
import com.company.U1M6GroupProject.model.Invoice;
import com.company.U1M6GroupProject.service.ServiceLayer;
import com.company.U1M6GroupProject.viewmodel.InvoiceViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class InvoiceController {

    ServiceLayer serviceLayer;

    @Autowired
    public InvoiceController (ServiceLayer serviceLayer) {
        this.serviceLayer = serviceLayer;
    }

    @RequestMapping(value = "/invoice", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    public List<InvoiceViewModel> FindAllInvoices() {
        return serviceLayer.findAllInvoices();
    }

    @RequestMapping(value ="/invoice/{id}", method = RequestMethod.GET)
    @ResponseStatus (HttpStatus.OK)
    public InvoiceViewModel findInvoice (@PathVariable int invoiceId) {
        return serviceLayer.findInvoice(invoiceId);
    }

    @RequestMapping (value = "/invoice", method = RequestMethod.POST)
    @ResponseStatus (value = HttpStatus.CREATED)
    public InvoiceViewModel saveInvoice ( @RequestBody InvoiceViewModel invoiceViewModel) {
        return serviceLayer.saveInvoice(invoiceViewModel);
    }

    @RequestMapping (value = "/invoice/{id}", method = RequestMethod.PUT)
    @ResponseStatus (value = HttpStatus.OK)
    public void updateInvoice(@PathVariable int invoiceId, @RequestBody InvoiceViewModel invoiceViewModel) {
        serviceLayer.updateInvoice(invoiceViewModel, invoiceId);
    }

    @RequestMapping (value = "/invoice/{id}", method = RequestMethod.DELETE)
    @ResponseStatus (value = HttpStatus.OK)
    public void removeInvoice (@PathVariable int invoiceId) {
        serviceLayer.removeInvoice(invoiceId);

    }
    @RequestMapping(value ="/invoice/customer/{id}", method = RequestMethod.GET)
    @ResponseStatus (HttpStatus.OK)
    public List <InvoiceViewModel> getInvoiceByCustomerId (@PathVariable int invoiceId) {
        return serviceLayer.findInvoiceByCustomerId(invoiceId);
    }

}

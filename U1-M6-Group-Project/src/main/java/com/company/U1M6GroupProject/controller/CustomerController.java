package com.company.U1M6GroupProject.controller;

import com.company.U1M6GroupProject.dao.CustomerDao;
import com.company.U1M6GroupProject.model.Customer;
import com.company.U1M6GroupProject.service.ServiceLayer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CustomerController {

    ServiceLayer serviceLayer;


    @Autowired
    public CustomerController(ServiceLayer serviceLayer) {
        this.serviceLayer = serviceLayer;
    }

    @RequestMapping(value = "/customer", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    public List<Customer> getAllCustomers() {
        return serviceLayer.findAllCustomers();
    }

    @RequestMapping(value ="/customer/{id}", method = RequestMethod.GET)
    @ResponseStatus (HttpStatus.OK)
    public Customer getCustomerById (@PathVariable int id) {
        return serviceLayer.findCustomer(id);
    }

    @RequestMapping (value = "/customer", method = RequestMethod.POST)
    @ResponseStatus (value = HttpStatus.CREATED)
    public Customer createACustomer(@RequestBody Customer customer) {
        return serviceLayer.saveCustomer(customer);

    }

    @RequestMapping (value = "/customer/{id}", method = RequestMethod.PUT)
    @ResponseStatus (value = HttpStatus.OK)
    public void updateACustomer(@PathVariable int id, @RequestBody Customer customer) {
        serviceLayer.updateCustomer(customer);

    }

    @RequestMapping (value = "/customer/{id}", method = RequestMethod.DELETE)
    @ResponseStatus (value = HttpStatus.OK)
    public void deleteACustomer(@PathVariable int id) {
         serviceLayer.removeCustomer(id);

    }





}

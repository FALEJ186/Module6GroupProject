package com.company.U1M6GroupProject.dao;

import com.company.U1M6GroupProject.model.Customer;

import java.util.List;

public interface CustomerDao {

    Customer addACustomer(Customer customer);

    Customer getACustomer (int customerId);

    List<Customer> getAllCustomers();

    void updateCustomer(Customer customer);

    void deleteACustomer(int customerId);




}

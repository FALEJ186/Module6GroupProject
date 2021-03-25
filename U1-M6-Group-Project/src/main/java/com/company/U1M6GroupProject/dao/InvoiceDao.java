package com.company.U1M6GroupProject.dao;

import com.company.U1M6GroupProject.model.Invoice;

import java.util.List;

public interface InvoiceDao {

    Invoice createInvoice(Invoice invoice);

    void updateInvoice (Invoice invoice);

    void deleteInvoice(int id);

    List<Invoice> findInvoiceByCustomer(String customer);

    List<Invoice> getAllInvoices();
}

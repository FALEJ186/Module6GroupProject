package com.company.U1M6GroupProject.dao;

import com.company.U1M6GroupProject.model.Invoice;

import java.util.List;

public interface InvoiceDao {

    Invoice addInvoice(Invoice invoice);

    void updateInvoice (Invoice invoice);

    void deleteInvoice(int id);

    List<Invoice> findInvoiceByCustomerId(int customerId);

    List<Invoice> getAllInvoices();

    Invoice getInvoice (int id);
}

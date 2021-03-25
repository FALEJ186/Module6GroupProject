package com.company.U1M6GroupProject.dao;


import com.company.U1M6GroupProject.model.Invoice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class InvoiceDaoJdbcTemplateImpl implements InvoiceDao {

    private JdbcTemplate jdbcTemplate;

    private static final String INSERT_INVOICE_SQL =
            "insert into invoice (invoiceId, customerId, orderDate, pickupDate, returnDate, lateFees)" +
                    "  values (?, ?, ?, ?, ?, ?)";
    private static final String DELETE_INVOICE_SQL =
            "delete from invoice where Id = ?";
    private static final String SELECT_INVOICE_BY_CUSTOMER_SQL =
            "select * from invoice where customerId = ?";
    private static final String SELECT_ALL_INVOICES_SQL =
            "select * from invoice";

    @Autowired
    public InvoiceDaoJdbcTemplateImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Invoice createInvoice(Invoice invoice) {
        jdbcTemplate.update(INSERT_INVOICE_SQL,
                invoice.getInvoiceId(),
                invoice.getCustomerId(),
                invoice.getOrderDate(),
                invoice.getPickupDate(),
                invoice.getReturnDate(),
                invoice.getLateFee());

        int id = jdbcTemplate.queryForObject("select_invoice_id()", Integer.class);

        invoice.setInvoiceId(id);

        return invoice;
    }

    @Override
    public void deleteInvoice(int id) {
        jdbcTemplate.update(DELETE_INVOICE_SQL, id);
    }

    @Override
    public List<Invoice> findInvoiceByCustomer(String customer) {
        return jdbcTemplate.query(SELECT_INVOICE_BY_CUSTOMER_SQL, this:: mapRowToInvoice, customer);
    }

    @Override
    public List<Invoice> getAllInvoices() {
        return jdbcTemplate.query(SELECT_ALL_INVOICES_SQL, this::mapRowToInvoice);
    }

    private Invoice mapRowToInvoice(ResultSet resultSet, int rowNumber) throws SQLException {
        Invoice invoice1 = new Invoice();
        invoice1.setInvoiceId(resultSet.getInt("invoiceId"));
        invoice1.setCustomerId(resultSet.getInt("isbn"));
        invoice1.setOrderDate(resultSet.getDate("orderDate").toLocalDate()); //why is this not working?
        invoice1.setPickupDate(resultSet.getDate("pickupDate").toLocalDate());
        invoice1.setReturnDate(resultSet.getDate("returnDate").toLocalDate());
        invoice1.setLateFee(resultSet.getBigDecimal("lateFee"));

        return invoice1;
    }
}

package com.company.U1M6GroupProject.dao;


import com.company.U1M6GroupProject.model.Invoice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class InvoiceDaoJdbcTemplateImpl implements InvoiceDao {

    private JdbcTemplate jdbcTemplate;

    private static final String INSERT_INVOICE_SQL =
            "insert into invoice (invoice_id, customer_id, order_date, pickup_date, return_date, late_fee)" +
                    "  values (?, ?, ?, ?, ?, ?)";
    private static final String DELETE_INVOICE_SQL =
            "delete from invoice where customer_id = ?";
    private static final String SELECT_INVOICE_BY_CUSTOMER_SQL =
            "select * from invoice where customer_id = ?";
    private static final String SELECT_ALL_INVOICES_SQL =
            "select * from invoice";
    private static final String UPDATE_INVOICE_SQL =
            "update book set invoice_id = ?, customer_id = ?, order_date = ?, pickup_date = ?, return_date = ? where late_fee = ?";
    private static final String SELECT_INVOICE_SQL =
            "select * from invoice where invoice_id = ? ";

    @Autowired
    public InvoiceDaoJdbcTemplateImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }



    @Override
    public Invoice addInvoice(Invoice invoice) {
        jdbcTemplate.update(INSERT_INVOICE_SQL,
                invoice.getId(),
                invoice.getCustomerId(),
                invoice.getOrderDate(),
                invoice.getPickupDate(),
                invoice.getReturnDate(),
                invoice.getLateFee());

        int id = jdbcTemplate.queryForObject("select LAST_INSERT_ID()", Integer.class);

        invoice.setId(id);

        return invoice;
    }

    @Override
    public void updateInvoice(Invoice invoice) {
        jdbcTemplate.update(UPDATE_INVOICE_SQL,
                invoice.getId(),
                invoice.getCustomerId(),
                invoice.getOrderDate(),
                invoice.getPickupDate(),
                invoice.getReturnDate(),
                invoice.getLateFee());
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

    @Override
    public Invoice getInvoice(int id) {
        try {

            return jdbcTemplate.queryForObject(SELECT_INVOICE_SQL, this::mapRowToInvoice, id);

        } catch (EmptyResultDataAccessException exception) {
            // if nothing is returned just catch the exception and return null
            return null;
        }
    }

    private Invoice mapRowToInvoice(ResultSet resultSet, int rowNumber) throws SQLException {
        Invoice invoice1 = new Invoice();
        invoice1.setId(resultSet.getInt("invoice_id"));
        invoice1.setCustomerId(resultSet.getInt("customer_id"));
        invoice1.setOrderDate(resultSet.getDate("order_date").toLocalDate()); //why is this not working?
        invoice1.setPickupDate(resultSet.getDate("pickup_date").toLocalDate());
        invoice1.setReturnDate(resultSet.getDate("return_date").toLocalDate());
        invoice1.setLateFee(resultSet.getBigDecimal("late_fee"));

        return invoice1;
    }
}

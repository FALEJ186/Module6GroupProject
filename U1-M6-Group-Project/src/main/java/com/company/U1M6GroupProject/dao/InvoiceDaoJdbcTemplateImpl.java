package com.company.U1M6GroupProject.dao;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class InvoiceDaoJdbcTemplateImpl {

    private JdbcTemplate jdbcTemplate;

    private static final String INSERT_INVOICE_SQL =
            "insert into invoice (invoiceId, customerId, orderDate, pickupDate, returnDate, lateFees)" +
                    "  values (?, ?, ?, ?, ?, ?)";
    private static final String DELETE_INVOICE_SQL =
            "delete from invoice where Id = ?";
    private static final String SELECT_INVOICE_BY_CUSTOMER_SQL =
            "select * from invoice where customerId = ?";

    @Autowired

}

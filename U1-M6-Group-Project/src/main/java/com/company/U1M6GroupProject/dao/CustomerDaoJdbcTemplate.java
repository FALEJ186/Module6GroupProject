package com.company.U1M6GroupProject.dao;

import com.company.U1M6GroupProject.model.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class CustomerDaoJdbcTemplate implements CustomerDao{

    private JdbcTemplate jdbcTemplate;

    private static final String INSERT_CUSTOMER_SQL =
            "insert into customer (first_name, last_name, email, company, phone) values (?, ?, ?, ?, ?)";

    private static final String SELECT_CUSTOMER_SQL =
            "select * from customer where customer_id = ?";

    private static final String SELECT_ALL_CUSTOMERS_SQL =
            "select * from customer";

    private static final String UPDATE_CUSTOMERS_SQL =
            "update customer set first_name = ?, last_name = ?, email = ?, company = ?, phone = ? where customer_id = ?";

    private static final String DELETE_CUSTOMERS_SQL =
            "delete from customer where customer_id = ?";

    @Autowired
    public CustomerDaoJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public Customer addACustomer(Customer customer) {
        jdbcTemplate.update(INSERT_CUSTOMER_SQL, customer.getFirstName(), customer.getLastName(), customer.getEmail(), customer.getCompany(), customer.getPhone());

        int id = jdbcTemplate.queryForObject("select LAST_INSERT_ID()", Integer.class);

        customer.setId(id);
        return customer;
    }

    @Override
    public Customer getACustomer(int customerId) {
        try {
            return jdbcTemplate.queryForObject(SELECT_CUSTOMER_SQL, this::mapRowToCustomer, customerId);
        } catch (EmptyResultDataAccessException e) {
            // if there is no Artist with this id, just return null
            return null;
        }
    }

    @Override
    public List<Customer> getAllCustomers() {
        return jdbcTemplate.query(SELECT_ALL_CUSTOMERS_SQL, this::mapRowToCustomer);
    }

    @Override
    public void updateCustomer(Customer customer) {
        jdbcTemplate.update(UPDATE_CUSTOMERS_SQL, customer.getFirstName(), customer.getLastName(), customer.getEmail(), customer.getCompany(), customer.getPhone(), customer.getId());

    }

    @Override
    public void deleteACustomer(int customerId) {
        jdbcTemplate.update(DELETE_CUSTOMERS_SQL, customerId);

    }

    private Customer mapRowToCustomer (ResultSet rs, int rowNum) throws SQLException {
        Customer customer = new Customer();
        customer.setId((rs.getInt("customer_id")));
        customer.setFirstName(rs.getString("first_name"));
        customer.setLastName(rs.getString("last_name"));
        customer.setEmail(rs.getString("email"));
        customer.setCompany((rs.getString("company")));
        customer.setPhone(rs.getString("phone"));

        return customer;
    }
}

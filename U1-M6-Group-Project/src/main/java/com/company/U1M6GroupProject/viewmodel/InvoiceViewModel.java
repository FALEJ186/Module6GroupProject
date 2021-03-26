package com.company.U1M6GroupProject.viewmodel;

import com.company.U1M6GroupProject.model.Customer;
import com.company.U1M6GroupProject.model.InvoiceItem;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class InvoiceViewModel {
    private int id;
    private Customer customer;
    private LocalDate orderDate;
    private LocalDate pickupDate;
    private LocalDate returnDate;
    private BigDecimal lateFee;
    private List<InvoiceItem> invoiceItems = new ArrayList<>();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
    }

    public LocalDate getPickupDate() {
        return pickupDate;
    }

    public void setPickupDate(LocalDate pickupDate) {
        this.pickupDate = pickupDate;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }

    public BigDecimal getLateFee() {
        return lateFee;
    }

    public void setLateFee(BigDecimal lateFee) {
        this.lateFee = lateFee;
    }

    public List<InvoiceItem> getInvoiceItems() {
        return invoiceItems;
    }

    public void setInvoiceItems(List<InvoiceItem> invoiceItems) {
        this.invoiceItems = invoiceItems;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InvoiceViewModel that = (InvoiceViewModel) o;
        return id == that.id && Objects.equals(customer, that.customer) && Objects.equals(orderDate, that.orderDate) && Objects.equals(pickupDate, that.pickupDate) && Objects.equals(returnDate, that.returnDate) && Objects.equals(lateFee, that.lateFee) && Objects.equals(invoiceItems, that.invoiceItems);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, customer, orderDate, pickupDate, returnDate, lateFee, invoiceItems);
    }

    @Override
    public String toString() {
        return "InvoiceViewModel{" +
                "id=" + id +
                ", customer=" + customer +
                ", orderDate=" + orderDate +
                ", pickupDate=" + pickupDate +
                ", returnDate=" + returnDate +
                ", lateFee=" + lateFee +
                ", invoiceItems=" + invoiceItems +
                '}';
    }
}

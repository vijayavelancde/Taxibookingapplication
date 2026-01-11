package cabbooking;

import java.util.List;

public class CustomerService implements CustomerServiceinterface {

    private final CustomerDAO customerDAO;

    public CustomerService(CustomerDAO customerDAO) {
        this.customerDAO = customerDAO;
    }

    public void addCustomer(String customerID, String customerName, long phone) {

        Customers existing = customerDAO.getById(customerID);
        if (existing != null) {
            throw new RuntimeException("Customer already exists with ID: " + customerID);
        }

        Customers customer = new Customers(customerID, customerName, phone);
        customerDAO.add(customer);
    }

    public Customers getCustomerById(String customerID) {

        Customers customer = customerDAO.getById(customerID);
        if (customer == null) {
            throw new RuntimeException("Customer not found: " + customerID);
        }

        return customer;
    }

    public void updateCustomerName(String customerID, String newName) {

        Customers customer = customerDAO.getById(customerID);
        if (customer == null) {
            throw new RuntimeException("Customer not found: " + customerID);
        }

        customerDAO.updateCustomerName(customerID, newName);
    }

    public void updateCustomerPhone(String customerID, long newPhone) {

        Customers customer = customerDAO.getById(customerID);
        if (customer == null) {
            throw new RuntimeException("Customer not found: " + customerID);
        }

        customerDAO.updateCustomerPhone(customerID, newPhone);
    }

    public List<RideDetails> getCustomerRides(String customerID) {

        Customers customer = customerDAO.getById(customerID);
        if (customer == null) {
            throw new RuntimeException("Customer not found: " + customerID);
        }

        return customerDAO.getCustomerRides(customerID);
    }

    public void deleteCustomer(String customerID) {

        Customers customer = customerDAO.getById(customerID);
        if (customer == null) {
            throw new RuntimeException("Customer not found: " + customerID);
        }

        customerDAO.delete(customerID);
    }
}

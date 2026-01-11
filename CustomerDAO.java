package cabbooking;

import java.util.ArrayList;

public interface CustomerDAO {

    void add(Customers customer);

    Customers getById(String customerID);

    void updateCustomerName(String customerID, String customerName);
    
    void updateCustomerPhone(String customerID, long customerPhoneNumber);
    
    boolean isAnExistingCustomer(String customerID);
    
    
    ArrayList<RideDetails> getCustomerRides(String customerID);

    void delete(String customerID);
}

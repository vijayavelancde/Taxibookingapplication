package cabbooking;

import java.util.List;

public interface CustomerServiceinterface {

    void addCustomer(String customerID, String customerName, long phone);

    Customers getCustomerById(String customerID);

    void updateCustomerName(String customerID, String newName);

    void updateCustomerPhone(String customerID, long newPhone);

    List<RideDetails> getCustomerRides(String customerID);

    void deleteCustomer(String customerID);
}

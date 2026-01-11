package cabbooking;

import java.util.ArrayList;
import java.util.HashMap;

public class InMemoryCustomerDAO extends InMemoryBaseDAO<Customers> implements CustomerDAO{

    public void updateCustomerName(String customerID, String name) {
        Customers obj = getById(customerID);
        if (obj == null) return;

        obj.setCustomerName(name);
        
    }
    
    public void updateCustomerPhone(String customerID, long customerPhoneNumber) {
	     Customers obj = getById(customerID);
	        if (obj == null) return;

	        obj.setPhone(customerPhoneNumber);
	       
		
	}

    public void editCustomerPhone(String customerID, long phone) {
        Customers obj = getById(customerID);
        if (obj == null) return;

        obj.setPhone(phone);
        
    }
 
    public ArrayList<RideDetails> getCustomerRides(String customerID) {

        Customers customer = map.get(customerID);

        if (customer == null) return new ArrayList<>();

        return new ArrayList<>(customer.getCustomerRideDetails().values());
    }

    
    public  boolean isAnExistingCustomer(String customerID) {
    	
    	if (!map.containsKey(customerID)) {
    		  return false;
    	  }
    	  return true;
        }
    
	
    }
    


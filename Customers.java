package cabbooking;

import java.util.HashMap;

public class Customers implements Identifiable{
    	
	private String customerID;
	private String customerName;
	private	long phone;
	private HashMap<String, RideDetails> customerRideDetails = new HashMap<>();
	
	Customers(String customerID, String customerName, long phone, HashMap<String, RideDetails> customerRideDetails) {
         this.customerID = customerID;
         this.customerName = customerName;
         this.phone = phone;
         this.customerRideDetails = customerRideDetails;
   }
	
	Customers(String customerID, String customerName, long phone) {
        this.customerID = customerID;
        this.customerName = customerName;
        this.phone = phone;
	}
	 
	    public String getID() {
	        return customerID;
	    }

	    public String getEntityName() {
	        return "Customer";
	    }
	
	  public String getCustomerID() {	
	        return customerID;
	    }

	    public void setCustomerID(String customerID) {
	        this.customerID = customerID;
	    }

	    public String getCustomerName() {
	        return customerName;
	    }

	    public void setCustomerName(String customerName) {
	        this.customerName = customerName;
	    }

	    public long getPhone() {
	        return phone;
	    }

	    public void setPhone(long phone) {
	        this.phone = phone;
	    } 	
	    
	    public HashMap<String, RideDetails> getCustomerRideDetails(){
	    	return this.customerRideDetails;
	    }
        
	    
}

package Taxibooking;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Trip {

	private int CabID;
	private String Cabdriver_name;
	private char Source;
	private char Destination;
	private int customerID;
	private int fare;
	private int ZulaCommission;
	private int numoftrips;
	
	Trip(int cabID, String cabdrivername, char source, char destination, int customID, int fare, int Zulacommision, int numtrips ){
		this.CabID = cabID;
		this.Cabdriver_name =cabdrivername;
		this.Source = source;
		this.Destination = destination;
		this.customerID = customID;
		this.fare = fare;
		this.ZulaCommission = Zulacommision;
		this.numoftrips = numtrips;
	}
	
	public  static void  cabdriversummary(int cabID, ArrayList<Trip> cabdrivertripsummary) {
		System.out.println("below is the Summary of rides that is taken by the " + cabID);
		
		System.out.println("\nTrip Details:");
        System.out.println("\nCabID\tCabdriver name\tSource\tDestination\tCustomer ID\tFare\tZulaCommission");
		
		for(Trip iterator : cabdrivertripsummary) {
			if(iterator.CabID == cabID) {
				
				 System.out.println(iterator.CabID + "\t" + iterator.Cabdriver_name + "\t\t" + iterator.Source + "\t\t" + iterator.Destination + "\t\t" + iterator.customerID + "\t" + iterator.fare + "\t" + iterator.ZulaCommission
		            		);
		}
		}	
	}
	
	public  static void  customertripsummary(int cxID, ArrayList<Trip> cabdrivertripsummary) {
		System.out.println("below is the Summary of rides that is taken by the " + cxID);
		
		System.out.println("\nTrip Details:");
        System.out.println("\nCabID\tCabdriver name\tSource\tDestination\tCustomer ID\tFare");
		
		for(Trip iterator : cabdrivertripsummary) {
			if(iterator.customerID == cxID) {

			
			 System.out.println(iterator.CabID + "\t" + iterator.Cabdriver_name + "\t\t" + iterator.Source + "\t\t" + iterator.Destination + "\t\t" + iterator.customerID + "\t" + iterator.fare
	            		);
			
		}
		}	
	}
	
	public static void admintripsummary(ArrayList<Trip> cabdrivertripsummary) {
		
		HashMap<Integer, List<Trip>> cabtripsummary = new HashMap<>();
		
		for(Trip cabid: cabdrivertripsummary) {
			cabtripsummary.computeIfAbsent(cabid.CabID, k -> new ArrayList<>()).add(cabid);
		}
		
		for(Map.Entry<Integer, List<Trip>> iterator: cabtripsummary.entrySet()) {
			
			int CabID = iterator.getKey();
			List<Trip> val = iterator.getValue();
			
			int fare = 0;
			int Zula = 0;
			
			  System.out.println("\nCab Id: " + CabID);
		        System.out.println("Total Number of Trips: " + val.size());
		        
		        for(Trip t : val) {
		        	fare += t.fare;
		        	Zula += t.ZulaCommission;
		        }
		        
		        System.out.println("Total Fare Collected: " + fare);
		        System.out.println("Total ZULA Commission: " + Zula);

		        System.out.println("\nTrip Details:");
		        System.out.println("Source\tDestination\tCustomer Detail\tFare\tZULA commission");
		        
		        for (Trip T : val) {
		            System.out.println(T.Source + "\t" + T.Destination + "\t\t" + T.customerID + "\t\t" + T.fare + "\t" + T.ZulaCommission
		            		);
		        }

			
		}
		
	}
	
	
}

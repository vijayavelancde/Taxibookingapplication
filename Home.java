package Taxibooking;

import java.util.ArrayList;
import java.util.Scanner;

public class Home {
	
	public static int calculatedistance(int a, int b) {
		
		int distance = a - b;
		distance = Math.abs(distance);
		return distance;
	}
	
	public static int calculatefair(int dis) {
		return dis*10;
	}

	public static void main(String[] args) {
		
		 ArrayList<Locations> locList = new ArrayList<>();

	        locList.add(new Locations('A', 0,1));
	        locList.add(new Locations('C', 4,3));
	        locList.add(new Locations('D', 7,4));
	        locList.add(new Locations('F', 11,6));
	        locList.add(new Locations('B', 15,2));
	        locList.add(new Locations('G', 18,7));
	        locList.add(new Locations('H', 20,8));
	        locList.add(new Locations('E', 23,5));
		
	        ArrayList<Cabdrivers> cabdrivers = new ArrayList<>();

	        Cabdrivers c1 = new Cabdrivers(1, "aaa", 111, 25, locList.get(7),0,0,0);
	        Cabdrivers c2 = new Cabdrivers(2, "bbb", 222, 36, locList.get(1),0,1,0);
	        Cabdrivers c3 = new Cabdrivers(3, "ccc", 333, 31, locList.get(0),0,0,0);
	        Cabdrivers c4 = new Cabdrivers(4, "ddd", 444, 28, locList.get(1),0,1,0);
	        
	        cabdrivers.add(c1);
	        cabdrivers.add(c2);
	        cabdrivers.add(c3);
	        cabdrivers.add(c4);
		
		Customers C1 = new Customers(1,"WW",55,25);
		Customers C2 = new Customers(2,"XX",66,36);
		Customers C3 = new Customers(3,"YY",77,31);
		Customers C4 = new Customers(4,"ZZ",88,28);
		
		ArrayList<Trip> cabdrivertripsummary = new ArrayList<>();
		ArrayList<Trip> customertripsummary = new ArrayList<>();
		
		Scanner scan = new Scanner(System.in);
		System.out.println("Welcome to Zula! Whats on your mind! \n");
		
	while(true) {	
		System.out.println(" 1) Driver login \n 2) Customer Login \n 3) ZULA Administrator \n 4) Exit");
		System.out.println(" \nSelect one of the operations to proceed");
		String option = scan.nextLine();
		int opt = Integer.parseInt(option);
		if(opt == 1) {
			System.out.println("Enter the username");
			String cabdriver_username = scan.nextLine();
			System.out.println("Enter the password");
			String cabdriver_password = scan.nextLine();
			System.out.println("You have successfully logged in");
			System.out.println("Enter the cab ID:");
			String cabID = scan.nextLine();
			int cabid = Integer.parseInt(cabID);
			System.out.println("To view the cab summary, click 1 and 0 to exit");
			String cabopt = scan.nextLine();
			if(Integer.parseInt(cabopt) == 1) {
			Trip.cabdriversummary(cabid, cabdrivertripsummary);
			}
//			else {
//				System.out.println("Exiting");
//				return;
//			}
		}
		else if(opt == 2) {
			System.out.println("For login press 1 / New to ZULA press 2");
			String login_option = scan.nextLine();
			if(login_option.equalsIgnoreCase("1")) {
			System.out.println("Enter the username");
			String customer_username = scan.nextLine();
			System.out.println("Enter the password");
			String customer_password = scan.nextLine();
			System.out.println("You have successfully logged in");
		}
			else if(login_option.equalsIgnoreCase("2")) {
				System.out.println("Enter the mobile number");
				String mobile_number = scan.nextLine();
				System.out.println("Share the six digit OTP here");
				String signup_otp = scan.nextLine();
				if(signup_otp.length() != 6) {
					System.out.println("Wrong OTP, process failed");
				}
				else {
					System.out.println("Create the username");
					String signup_username = scan.nextLine();
					System.out.println("Enter the password");
					String signup_password = scan.nextLine();
					System.out.println("You have successfully signed up");
					
					}
				}
			System.out.println("Enter the CX ID, sent to your mobile");
			String cxID = scan.nextLine();
			int cxid; 
			cxid = Integer.parseInt(cxID);
			System.out.println("Enter the name");
			String cxname;
			cxname = scan.nextLine();
			System.out.println("\nPlease select the pick up location" );
			for(Locations local: locList) {
				System.out.println(local.localname());
			}
			char sourceconv = 0;
			String Source = scan.nextLine();
		    if(Source.length() > 1) {
		    	System.out.println("Wrong input! process failed");
		    } 
		    else {
		    	Source =  Source.toUpperCase();
		    	sourceconv = Source.charAt(0);
		    }
		    int source_distance = 0;
		    boolean flag = false;
		    for(Locations valid: locList) {
		    	if(valid.localname() == sourceconv) {
		    		flag = true;
		    		source_distance = valid.getdistance();
		    	}
		    }
		    
		    if(flag == false) {
		    	System.out.println("Invalid input");
		    	return;
		    }
		    
			System.out.println("\nPlease select the drop location" );
			for(Locations local: locList) {
				System.out.println(local.localname());
			}
			char destinationconv = 0;
			String Destination = scan.nextLine();
		    if(Destination.length() > 1) {
		    	System.out.println("Wrong input! process failed");
		    	return;
		    } 
		    else {
		    	Destination =  Destination.toUpperCase();
		    	destinationconv = Destination.charAt(0);
		    }
		    int destination_distance = 0;
		    boolean check = false;
		    for(Locations valid: locList) {
		    	if(valid.localname() == destinationconv) {
		    		check = true;
		    		destination_distance = valid.getdistance();
		    	}
		    }
		    
		    if(check == false) {
		    	System.out.println("Invalid input");
		    	return;
		    }
		    
		   int travel_distance = calculatedistance(destination_distance,source_distance);
		   int fair = calculatefair(travel_distance);
		   Cabdrivers hailacab = Cabdrivers.Hailacab(source_distance,cabdrivers);
		   hailacab.setfair(fair);
		   hailacab.incrementtrips();
		   int Zulacom = hailacab.Zulacommission(fair);
		   
		   cabdrivertripsummary.add(new Trip(hailacab.cabID(),hailacab.cabdrivername(),sourceconv,destinationconv,cxid,hailacab.getfair(),Zulacom,hailacab.getnumberoftrips()));
		   
		  
		   System.out.println("Thanks for confirming \nFor " + travel_distance + " kms, the travel fair is " + fair);
		   System.out.println(" ");
		   System.out.println("A cab has been assigned to you, The following are the details, " + hailacab.cabdrivername());
		   
			System.out.println("To view the cab summary, click 1 and 0 to exit");
			String cabopt = scan.nextLine();
			if(Integer.parseInt(cabopt) == 1) {
			Trip.customertripsummary(cxid, cabdrivertripsummary);
			}
			
		   
		   
			System.out.println("How was our service,rate us");
			String rate = scan.nextLine();
		}
		else if(opt == 3) {
			System.out.println("Hello Admin! WHats up \n Enter the username:-");
			String admin_username = scan.nextLine();
			System.out.println("Enter the password");
			String admin_password = scan.nextLine();
			System.out.println("To view the entire trip summary, click 1 and 0 to exit");
			String cabopt = scan.nextLine();
			if(Integer.parseInt(cabopt) == 1) {
			Trip.admintripsummary(cabdrivertripsummary);
			}
			else {
				System.out.println("Exiting");
				return;
			}
		}
		else if(opt == 4) {
			System.out.println("Exiting!");
		}
		
	}
	}
}

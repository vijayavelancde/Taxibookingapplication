package cabbooking;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Homepage {

    public static void main(String[] args) {

        boolean useJdbc = false;

        DriverDAO driverDAO;
        LocationDAO locationDAO;
        CustomerDAO customerDAO;
        PeakHourDAO peakHourDAO;
        RideDAO rideDAO;
        ReportDAO reportservice;

        if (useJdbc) {
            driverDAO = new JdbcDriverDAO();
            locationDAO = new JdbcLocationDAO();
            customerDAO = new JdbcCustomerDAO();
            peakHourDAO = new JdbcPeakHourDAO();
            rideDAO = new JdbcRideDAO();
            reportservice = new JdbcReportDAO();
        } else {
            driverDAO = new InMemoryDriverDAO();
            locationDAO = new InMemoryLocationDAO();
            customerDAO = new InMemoryCustomerDAO();
            peakHourDAO = new InMemoryPeakHourDAO();
            rideDAO = new InMemoryRideDAO();
        }

        DriverService driverService = new DriverService(driverDAO, locationDAO);
        LocationService locationService = new LocationService(locationDAO);
        CustomerService customerService = new CustomerService(customerDAO);
        PeakHourService peakHourService = new PeakHourService(peakHourDAO);

        RideService rideService =
                new RideService(driverDAO, locationDAO, customerDAO, peakHourDAO, rideDAO);
        
        ReportService reportService = new ReportService(new JdbcReportDAO());

        
        seedData(driverService, locationService);

        Scanner scan = new Scanner(System.in);

        while (true) {

            System.out.println("\nHi, Welcome to Zoho Cabs");
            System.out.println("1. Driver Operations");
            System.out.println("2. Location Operations");
            System.out.println("3. Book a Ride");
            System.out.println("4. View Driver Invoice");
            System.out.println("5. Customer Operations");
            System.out.println("6. Peak Hour Operations");
            System.out.println("7. Reports");
            System.out.println("8. Exit");


            int userChoice = scan.nextInt();
            scan.nextLine();

            try {

                if (userChoice == 1) {
                    driverMenu(scan, driverService);
                }

                else if (userChoice == 2) {
                    locationMenu(scan, locationService);
                }

                else if (userChoice == 3) {

                    System.out.println("Enter Customer ID");
                    String customerID = scan.nextLine();

                    Customers customer;
                    try {
                        customer = customerService.getCustomerById(customerID);
                    } catch (Exception e) {
                        System.out.println("Enter Customer Name");
                        String name = scan.nextLine();

                        System.out.println("Enter Mobile Number");
                        long mobile = scan.nextLong();
                        scan.nextLine();

                        customerService.addCustomer(customerID, name, mobile);
                        customer = customerService.getCustomerById(customerID);
                    }

                    List<Locations> locations = locationService.getAllLocations();
                    for (Locations l : locations) {
                        System.out.println(l.getLocationID() + " - " + l.getLocationName());
                    }

                    System.out.println("Enter Pickup Location ID");
                    String pickupID = scan.nextLine();

                    System.out.println("Enter Drop Location ID");
                    String dropID = scan.nextLine();

                    RideDetails ride = rideService.bookRide(customer, pickupID, dropID);

                    if (ride.getRideStatus() == RideDetails.RideStatus.CANCELLED) {
                        System.out.println("No cabs available. Ride cancelled.");
                    } else {
                        System.out.println("\nRide booked successfully!");
                        System.out.println("Ride ID   : " + ride.getRideID());
                        System.out.println("Driver    : " + ride.getDriverName());
                        System.out.println("Pickup    : " + ride.getPickupLocationID());
                        System.out.println("Drop      : " + ride.getDropLocationID());
                        System.out.println("Fare      : " + ride.getRidefair());
                    }
                }

                else if (userChoice == 4) {

                    System.out.println("Enter Driver ID");
                    String driverID = scan.nextLine();

                    List<RideDetails> rides = driverService.getDriverRideHistory(driverID);

                    if (rides.isEmpty()) {
                        System.out.println("No rides found.");
                    } else {
                        for (RideDetails r : rides) {
                            System.out.println(
                                    r.getRideID() + " | " +
                                    r.getPickupLocationID() + " -> " +
                                    r.getDropLocationID() + " | " +
                                    r.getRidefair() + " | " +
                                    r.getRideStatus()
                            );
                        }
                    }
                }

                else if (userChoice == 5) {
                    customerMenu(scan, customerService);
                }

                else if (userChoice == 6) {
                    peakHourMenu(scan, peakHourService);
                }
                
                else if (userChoice == 7) {
                    reportMenu(scan, reportService);
                }

                else if (userChoice == 8) {
                    System.out.println("Thank you for using Zoho Cabs!");
                    break;
                }

                else {
                    System.out.println("Invalid choice. Try again.");
                }

            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

        scan.close();
    }
    
    private static void reportMenu(Scanner scan, ReportDAO reportService) {

    	System.out.println("\n========= REPORTS MENU =========");
    	System.out.println("1. Ride Count By Status");
    	System.out.println("2. Total Revenue");
    	System.out.println("3. Total Earnings Per Driver");
    	System.out.println("4. Top 5 Highest Earning Drivers");
    	System.out.println("5. Bottom 5 Lowest Earning Drivers");
    	System.out.println("6. Most Used Pickup Location");
    	System.out.println("7. Driver Analytics");
    	System.out.println("8. Peak Hour Ride Count");
    	System.out.println("9. Peak Hour Revenue");
    	System.out.println("10. Revenue By Peak Hour Slot");
    	System.out.println("0. Back to Main Menu");
    	System.out.print("Enter your choice: ");


    	int choice = scan.nextInt();

    	switch (choice) {

    	    case 1: {
    	        HashMap<RideDetails.RideStatus, Integer> map =
    	                reportService.getRideCountByStatus();

    	        System.out.println("\n--- Ride Count By Status ---");
    	        for (Map.Entry<RideDetails.RideStatus, Integer> entry : map.entrySet()) {
    	            System.out.println(entry.getKey() + " : " + entry.getValue());
    	        }
    	        break;
    	    }

    	    case 2: {
    	        int totalRevenue = reportService.getTotalRevenue();
    	        System.out.println("\nTotal Revenue (Completed Rides): " + totalRevenue);
    	        break;
    	    }

    	    case 3: {
    	        HashMap<String, Integer> map =
    	                reportService.getTotalEarningsPerDriver();

    	        System.out.println("\n--- Total Earnings Per Driver ---");
    	        for (Map.Entry<String, Integer> entry : map.entrySet()) {
    	            System.out.println("Driver ID: " + entry.getKey()
    	                    + " | Total Earning: " + entry.getValue());
    	        }
    	        break;
    	    }

    	    case 4: {
    	        ArrayList<DriverEarningDTO> list =
    	                reportService.getTop5HighestEarningDrivers();

    	        System.out.println("\n--- Top 5 Highest Earning Drivers ---");
    	        for (DriverEarningDTO dto : list) {
    	            System.out.println("Driver ID: " + dto.getDriverId()
    	                    + " | Total Earning: " + dto.getTotalEarning());
    	        }
    	        break;
    	    }

    	    case 5: {
    	        ArrayList<DriverEarningDTO> list =
    	                reportService.getBottom5LowestEarningDrivers();

    	        System.out.println("\n--- Bottom 5 Lowest Earning Drivers ---");
    	        for (DriverEarningDTO dto : list) {
    	            System.out.println("Driver ID: " + dto.getDriverId()
    	                    + " | Total Earning: " + dto.getTotalEarning());
    	        }
    	        break;
    	    }

    	    case 6: {
    	        LocationUsageDTO dto = reportService.getMostUsedPickupLocation();

    	        System.out.println("\n--- Most Used Pickup Location ---");
    	        if (dto != null) {
    	            System.out.println("Location ID: " + dto.getLocationId());
    	            System.out.println("Number of Rides: " + dto.getRideCount());
    	        } else {
    	            System.out.println("No data available.");
    	        }
    	        break;
    	    }

    	    case 7: {
    	        ArrayList<DriverAnalyticsDTO> list =
    	                reportService.getDriverAnalytics();

    	        System.out.println("\n--- Driver Analytics ---");
    	        for (DriverAnalyticsDTO dto : list) {
    	            System.out.println(
    	                    "Driver ID: " + dto.getDriverId() +
    	                    " | Name: " + dto.getDriverName() +
    	                    " | Total Rides: " + dto.getTotalRides() +
    	                    " | Total Earning: " + dto.getTotalEarning() +
    	                    " | Avg Fare: " + dto.getAvgFare()
    	            );
    	        }
    	        break;
    	    }

    	    case 8: {
    	        int count = reportService.getPeakHourRideCount();
    	        System.out.println("\nPeak Hour Ride Count: " + count);
    	        break;
    	    }

    	    case 9: {
    	        int revenue = reportService.getPeakHourRevenue();
    	        System.out.println("\nPeak Hour Revenue: " + revenue);
    	        break;
    	    }

    	    case 10: {
    	        ArrayList<PeakHourRevenueDTO> list =
    	                reportService.getRevenueByPeakHourSlot();

    	        System.out.println("\n--- Revenue By Peak Hour Slot ---");
    	        for (PeakHourRevenueDTO dto : list) {
    	            System.out.println("Peak Slot: " + dto.getPeakSlot()
    	                    + " | Total Revenue: " + dto.getTotalRevenue());
    	        }
    	        break;
    	    }

    	    case 0:
    	        System.out.println("Returning to main menu...");
    	        break;

    	    default:
    	        System.out.println("Invalid choice. Please try again.");
    	}

    }


    private static void driverMenu(Scanner scan, DriverService driverService) {

        System.out.println("1. Onboard Driver");
        System.out.println("2. Update Driver Availability");

        int choice = scan.nextInt();
        scan.nextLine();

        if (choice == 1) {

            System.out.println("Enter Driver ID");
            String driverID = scan.nextLine();

            System.out.println("Enter Driver Name");
            String name = scan.nextLine();

            System.out.println("Enter Location ID");
            String locationID = scan.nextLine();

            driverService.onboardDriver(driverID, name, locationID);
            System.out.println("Driver onboarded successfully!");
        }

        else if (choice == 2) {

            System.out.println("Enter Driver ID");
            String driverID = scan.nextLine();

            System.out.println("1. Mark Available");
            System.out.println("2. Mark Unavailable");
            int opt = scan.nextInt();
            scan.nextLine();

            if (opt == 1) {
                driverService.markDriverAvailable(driverID);
                System.out.println("Driver marked available");
            } else {
                driverService.markDriverUnavailable(driverID);
                System.out.println("Driver marked unavailable");
            }
        }
    }

    private static void locationMenu(Scanner scan, LocationService locationService) {

        System.out.println("1. Add Location");
        System.out.println("2. Update Location Name");
        System.out.println("3. Update Location Distance");

        int choice = scan.nextInt();
        scan.nextLine();

        if (choice == 1) {

            System.out.println("Enter Location ID");
            String id = scan.nextLine();

            System.out.println("Enter Location Name");
            String name = scan.nextLine();

            System.out.println("Enter Distance from Origin");
            int dist = scan.nextInt();
            scan.nextLine();

            locationService.addLocation(id, name, dist);
            System.out.println("Location added successfully");
        }

        else if (choice == 2) {

            System.out.println("Enter Location ID");
            String id = scan.nextLine();

            System.out.println("Enter New Name");
            String name = scan.nextLine();

            locationService.updateLocationName(id, name);
            System.out.println("Location name updated");
        }

        else if (choice == 3) {

            System.out.println("Enter Location ID");
            String id = scan.nextLine();

            System.out.println("Enter New Distance");
            int dist = scan.nextInt();
            scan.nextLine();

            locationService.updateLocationDistance(id, dist);
            System.out.println("Location distance updated");
        }
    }

    private static void customerMenu(Scanner scan, CustomerService customerService) {

        System.out.println("1. Update Customer Name");
        System.out.println("2. Update Customer Phone");
        System.out.println("3. View Customer Ride History");

        int choice = scan.nextInt();
        scan.nextLine();

        if (choice == 1) {

            System.out.println("Enter Customer ID");
            String id = scan.nextLine();

            System.out.println("Enter New Name");
            String name = scan.nextLine();

            customerService.updateCustomerName(id, name);
            System.out.println("Customer name updated");
        }

        else if (choice == 2) {

            System.out.println("Enter Customer ID");
            String id = scan.nextLine();

            System.out.println("Enter New Phone");
            long phone = scan.nextLong();
            scan.nextLine();

            customerService.updateCustomerPhone(id, phone);
            System.out.println("Customer phone updated");
        }

        else if (choice == 3) {

            System.out.println("Enter Customer ID");
            String id = scan.nextLine();

            List<RideDetails> rides = customerService.getCustomerRides(id);

            if (rides.isEmpty()) {
                System.out.println("No rides found");
            } else {
                for (RideDetails r : rides) {
                    System.out.println(
                            r.getRideID() + " | " +
                            r.getPickupLocationID() + " -> " +
                            r.getDropLocationID() + " | " +
                            r.getRideStatus() + " | " +
                            r.getRidefair()
                    );
                }
            }
        }
    }

    private static void peakHourMenu(Scanner scan, PeakHourService peakHourService) {

        System.out.println("1. Add Peak Hour");
        System.out.println("2. View Peak Hours");

        int choice = scan.nextInt();
        scan.nextLine();

        if (choice == 1) {

            System.out.println("Enter Peak Hour ID");
            String id = scan.nextLine();

            System.out.println("Enter Start Time (HH:mm)");
            String start = scan.nextLine();

            System.out.println("Enter End Time (HH:mm)");
            String end = scan.nextLine();

            peakHourService.addPeakHour(id, start, end);
            System.out.println("Peak hour added successfully");
        }

        else if (choice == 2) {

            List<PeakHour> list = peakHourService.getAllPeakHours();

            if (list.isEmpty()) {
                System.out.println("No peak hours configured");
            } else {
                for (PeakHour p : list) {
                    System.out.println(
                            p.getID() + " | " +
                            p.getStartTime() + " - " +
                            p.getEndTime()
                    );
                }
            }
        }
    }
    
    private static void seedData(DriverService driverService, LocationService locationService) {
    	    try { locationService.addLocation("L1", "Tambaram", 15); } catch (Exception ignored) {}
    	    try { locationService.addLocation("L2", "Velachery", 10); } catch (Exception ignored) {}
    	    try { locationService.addLocation("L3", "Guindy", 8); } catch (Exception ignored) {}
    	    try { locationService.addLocation("L4", "T Nagar", 12); } catch (Exception ignored) {}
    	    try { locationService.addLocation("L5", "Anna Nagar", 18); } catch (Exception ignored) {}

    	    try { driverService.onboardDriver("D1", "Arun", "L1"); } catch (Exception ignored) {}
    	    try { driverService.onboardDriver("D2", "Bala", "L2"); } catch (Exception ignored) {}
    	    try { driverService.onboardDriver("D3", "Charan", "L3"); } catch (Exception ignored) {}
    }

    
}

package cabbooking;

import java.util.HashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class RideService {

    private DriverDAO driverDAO;
    private LocationDAO locationDAO;
    private CustomerDAO customerDAO;
    private PeakHourDAO peakHourDAO;
    private RideDAO rideDAO;

    private static final int BASE_RATE = 10;
    private static final int PEAK_HOUR_RATE = 15;

    public RideService(DriverDAO driverDAO,
                       LocationDAO locationDAO,
                       CustomerDAO customerDAO,
                       PeakHourDAO peakHourDAO,
                       RideDAO rideDAO) {

        this.driverDAO = driverDAO;
        this.locationDAO = locationDAO;
        this.customerDAO = customerDAO;
        this.peakHourDAO = peakHourDAO;
        this.rideDAO = rideDAO;
    }

    public RideDetails bookRide(Customers customer,
                                String pickupLocationID,
                                String dropLocationID) {

        RideContext context =
                prepareRideContext(pickupLocationID, dropLocationID);

        Drivers driver =
                selectBestDriver(context.pickupDistance);

        if (driver == null) {
            RideDetails cancelledRide =
                    createCancelledRide(customer, context,
                            pickupLocationID, dropLocationID);

            addCustomerRideInvoice(cancelledRide);
            rideDAO.createRide(cancelledRide);
            return cancelledRide;
        }

        RideDetails ride =
                createAssignedRide(customer, driver, context,
                        pickupLocationID, dropLocationID);

        addCustomerRideInvoice(ride);
        addDriverRideInvoice(ride);
        rideDAO.createRide(ride);

        scheduleRideCompletion(ride, driver, context);

        return ride;
    }

  

    private RideContext prepareRideContext(String pickupID,
                                           String dropID) {

        int pickupDistance = getLocationDistancebyID(pickupID);
        int rideDistance = getDistance(pickupID, dropID);
        int fare = calculateFare(rideDistance);

        return new RideContext(pickupDistance, rideDistance, fare);
    }

    private Drivers selectBestDriver(int pickupDistance) {
        return driverDAO.findBestAvailableDriver(pickupDistance);
    }

    private RideDetails createAssignedRide(Customers customer,
                                           Drivers driver,
                                           RideContext context,
                                           String pickupID,
                                           String dropID) {

        driverDAO.updateAvailability(driver.getDriverID(), false);

        RideDetails ride = new RideDetails();
        ride.setRideID(generateRideID());
        ride.setCustomerID(customer.getCustomerID());
        ride.setCustomerName(customer.getCustomerName());
        ride.setDriverID(driver.getDriverID());
        ride.setDriverName(driver.getDriverName());
        ride.setPickupLocationID(pickupID);
        ride.setDropLocationID(dropID);
        ride.setRideDistance(context.rideDistance);
        ride.setRidefair(context.fare);
        ride.setRideStatus(RideDetails.RideStatus.ASSIGNED);

        return ride;
    }

    private RideDetails createCancelledRide(Customers customer,
                                            RideContext context,
                                            String pickupID,
                                            String dropID) {

        RideDetails ride = new RideDetails();
        ride.setRideID(generateRideID());
        ride.setCustomerID(customer.getCustomerID());
        ride.setCustomerName(customer.getCustomerName());
        ride.setPickupLocationID(pickupID);
        ride.setDropLocationID(dropID);
        ride.setRideDistance(context.rideDistance);
        ride.setRidefair(context.fare);
        ride.setRideStatus(RideDetails.RideStatus.CANCELLED);

        return ride;
    }

    private void scheduleRideCompletion(RideDetails ride,
                                        Drivers driver,
                                        RideContext context) {

        ScheduledExecutorService scheduler =
                Executors.newSingleThreadScheduledExecutor();

        scheduler.schedule(() -> {
            completeRide(ride, driver);
            System.out.println("Ride " + ride.getRideID() + " completed");
            scheduler.shutdown();
        }, context.rideDistance, TimeUnit.SECONDS);
    }

    private void completeRide(RideDetails ride,
                              Drivers driver) {

        driverDAO.updateAvailability(driver.getDriverID(), true);
        driverDAO.incrementCompletedRideCount(driver.getDriverID());
        driverDAO.updateEarnings(driver.getDriverID(), ride.getRidefair());

        rideDAO.updateRideStatus(
                ride.getRideID(),
                RideDetails.RideStatus.COMPLETED,
                ride.getRidefair()
        );
    }

   

    private void addCustomerRideInvoice(RideDetails ride) {
        Customers customer =
                customerDAO.getById(ride.getCustomerID());

        HashMap<String, RideDetails> map =
                customer.getCustomerRideDetails();

        map.put(ride.getRideID(), ride);
    }

    private void addDriverRideInvoice(RideDetails ride) {
        Drivers driver =
                driverDAO.getById(ride.getDriverID());

        HashMap<String, RideDetails> map =
                driver.getRideHistory();

        map.put(ride.getRideID(), ride);
    }

  
    private int getLocationDistancebyID(String locationID) {
        Locations loc = locationDAO.getById(locationID);
        return loc.getLocationDistancefromOrgin();
    }

    private String getLocationNamebyID(String locationID) {
        Locations loc = locationDAO.getById(locationID);
        return loc.getLocationName();
    }

    private int getDistance(String pickupLocationID,
                            String dropLocationID) {

        int pickupDis = getLocationDistancebyID(pickupLocationID);
        int dropDis = getLocationDistancebyID(dropLocationID);

        return Math.abs(pickupDis - dropDis);
    }

    private int calculateFare(int rideDistance) {

        if (peakHourDAO.isPeakHourNow()) {
            return PEAK_HOUR_RATE * rideDistance;
        }
        return BASE_RATE * rideDistance;
    }

    private static String generateRideID() {
        return "ZOHO-" + System.currentTimeMillis();
    }

   
    private static class RideContext {
        int pickupDistance;
        int rideDistance;
        int fare;

        RideContext(int pickupDistance,
                    int rideDistance,
                    int fare) {
            this.pickupDistance = pickupDistance;
            this.rideDistance = rideDistance;
            this.fare = fare;
        }
    }
}

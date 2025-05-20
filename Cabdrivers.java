package Taxibooking;

import java.util.ArrayList;

public class Cabdrivers  {
	
	private int Cabdriver_ID;
	private String Cabdriver_Name;
	private int Pass;
	private int Age;
	Locations location;
	private int fair;
	private int trips;
	private int counter;
	
	
	Cabdrivers(int ID, String Name, int Pass, int Age, Locations location,int fair,int trips, int counter){
		this.Cabdriver_ID = ID;
		this.Cabdriver_Name = Name;
		this.Pass = Pass;
		this.Age = Age;
		this.location = location;
		this.fair = fair;
		this.trips = trips;
		this.counter = counter;
	}
	
	public int cabID() {
		return Cabdriver_ID;
	}
	
	public String cabdrivername() {
		return Cabdriver_Name;
	}
	
	public Locations cablocation() {
		return location;
	}
	
	
	
	public int getfair() {
		return fair;
	}
	public int getnumberoftrips() {
		return trips;
	}
	
	public void incrementtrips() {
		trips++;
	}
	
	public void setfair(int x) {
		 fair += x;
	}
	public int Zulacommission(int fair) {
		return fair *= 0.3;
	}
	public void setcounter(int z) {
		counter = z;
	}
	
	
	public static Cabdrivers Hailacab(int source,ArrayList<Cabdrivers> cabdrivers) {
		
		for(Cabdrivers count: cabdrivers) {
			if(count.counter > 0) {
				count.counter--;
			}
		}
		
		Cabdrivers nearest = null;
		int trips = Integer.MAX_VALUE; 
		int mindifference = Integer.MAX_VALUE; 
		for(Cabdrivers near: cabdrivers) {
			if(near.counter==0) {
			int difference = Math.abs(near.cablocation().getdistance() - source);
			if(difference < mindifference) {
				mindifference = difference;
				nearest = near;
				trips =  near.getnumberoftrips();
			}
			else if(difference == mindifference && near.getnumberoftrips() < trips) {
				nearest = near;
				trips = near.getnumberoftrips();
			}
		}
		}
		
		if(nearest  != null) {
			nearest.setcounter(2);
		}
		
		return nearest;
	}
	
	
	
}

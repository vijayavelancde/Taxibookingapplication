package Taxibooking;

public class Locations {
 
	private char location;
	private int ID;
	private int distancefromlogin;
	
	Locations(char local,int orgin, int ID){
		this.location = local;
		this.ID = ID;
		this.distancefromlogin = orgin;
	}
	
	public char localname () {
		return location;
	}
	
	public int ID () {
		return ID;
	}
	
	public int getdistance() {
		return distancefromlogin;
	}
	
}

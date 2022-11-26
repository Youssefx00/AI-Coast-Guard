package artificial_intelligence;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Vector;

public class grid {
int M;
int N;
int maxNumberofPassengers;
Vector<String> thegrid;
int coastGuardX;
int coastGuardY;
ArrayList<station> stationslist;
ArrayList<ship> shipslist;
int[] visited;


public grid(String input) {
	String[] temp = input.split(";");
	//	Now The String is split into Three Different sectors and should look like this
	//  [m,n; C ; grid] m and n need to be split again
	//  [0  , 1 , 2]
	
	//C Extraction

	
	
	//M and N Extraction
	String[] MandN = temp[0].split(",");
	M = Integer.parseInt(MandN[0]);
	N = Integer.parseInt(MandN[1]);
	
	maxNumberofPassengers = Integer.parseInt(temp[1]);
	
	//initial coast goard location
	String[] coastGuardloc = temp[2].split(",");
	
	coastGuardX = Integer.parseInt(coastGuardloc[0]);
	coastGuardY = Integer.parseInt(coastGuardloc[1]);
	
	
	
	
	//Grid Extraction
	
	
	
	String[] stations = temp[3].split(",");
	for(int i = 0; i<temp.length;i= i+2) {
		station thisStation =  new station(i,i+1);
		stationslist.add(thisStation);
	}
	String[] ships = temp[4].split(",");
	for(int i = 0; i<ships.length; i = i+3) {
		ship thisShip = new ship(i,i+1,i+2);
		shipslist.add(thisShip);
		
	}
	
	

}

public int isThisAShip(int x,int y){
	for(int i = 0;i<shipslist.size();i++) {
		if(shipslist.get(i).x== x && shipslist.get(i).y==y) {
			return i;
		}
	}
	return -1;
}

public int isThisAStation(int x,int y){
	for(int i = 0;i<stationslist.size();i++) {
		if(stationslist.get(i).x== x && stationslist.get(i).y==y) {
			return i;
		}
	}
	return -1;
}



public boolean possiblemove(operator operating,int x, int y) {
	
	
		if(operating== operator.Drop){
		
			
			//function to return if the ship has black box
			boolean hasbox = false;
			if(hasbox) {
				return true;
				}
			//insert the variable refrencing number of current passengers
			
			int numberOfPassengersOnBoard= 0;
			if(numberOfPassengersOnBoard>0) {
			return true;
			
			}else 
				return false;
		
		
		}
				
				if(operating== operator.Pickup){
					
					
					
					int ShipNumber1 =isThisAShip(x,y);
					if(ShipNumber1 != -1) {
					if(shipslist.get(ShipNumber1).getNumberOfPassengers()>0) {
						return true;
						}	
						}
						}else
						return false;
			
				
				
			
			
			
			
			
		
		if(operating== operator.Retrieve){
			
			//get function to check the ammount of passengers on board\
		
			int stationNumber =isThisAStation(x,y);
			if(stationNumber != -1) {
				
				if(stationslist.get(stationNumber).isHasBlackBox()) {
				return true;
				}
				}
		
			
		}
		if(operating== operator.Up){
			if(y-1>0) {
				return true;
			}
		}
		if(operating== operator.Left){
			if(x-1>0) {
				return true;
			}
		}
		if(operating== operator.Right){	
			if(x+1<M) {
				return true;
			}
			
		}
		if(operating== operator.Down){
			if(y+1<N) {
				return true;
			}
		
		}
		
	
	return false;
}
	


}

package artificial_intelligence;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
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
	


public String GenGrid() {
	String theString= "";
	int sizem = 5 +  (int)(Math.random() * ((5 - 15) + 1)); 
	int sizen = 5 +  (int)(Math.random() * ((4 - 15) + 1));
	theString = theString + sizem + ",";
	theString = theString + sizen + ";";
	
	int coastGuardmax =  30+  (int)(Math.random() * ((30 - 100) + 1));
	theString = theString + coastGuardmax+ ";";
	
	int coastGuardX =   (int)(Math.random() * ((sizem) + 1));
	int coastGuardY =  (int)(Math.random() * ((sizen) + 1));
	theString = theString + coastGuardX+ ","+ coastGuardY+ ";";

	
	
	
	int shipx= 0;
	int shipy=0;
	int shipPassengers=(int)(Math.random() * ((100) + 1));
	String shipstring = "";
	while(true) {
		shipx =  (int)(Math.random() * ((sizem) + 1));
		shipy=  (int)(Math.random() * ((sizem) + 1));
		if(shipx != coastGuardX && shipy != coastGuardY) {
			shipstring = shipstring + shipx + "," + shipy+ "," +shipPassengers+",";
			
		break;
		}
	}
		
	int stationx= 0;
	int stationy=1;
	String stationstring = "";
	while(true) {
		stationx =  (int)(Math.random() * ((sizem-1) + 1));
		stationy=  (int)(Math.random() * ((sizem-1) + 1));
		
		if(stationx != coastGuardX && stationy != coastGuardY) {
			if(stationx != shipx && stationy != shipy) {
				stationstring = stationstring + stationx + "," + stationy+ ",";
			break;
			
		}
			
		}
		
	}
	
	
	
	
	
	
	for(int x = 0; x<sizem;x++) {
		for(int y = 0; y<sizen;y++ ) {
			if(x == stationx && y == stationy) {
				
			}else
			if(x == shipx && y == shipy) {
				
			}else
			if(x == coastGuardX && y == coastGuardY) {
				
			}else {
			int luckofthedraw = (int)(Math.random() * ((10) + 1));
			if(luckofthedraw==10) {
				stationstring = stationstring +x +","+ y+ ";";
			}
			if(luckofthedraw== 9) {
				int currshipPassengers=(int)(Math.random() * ((100) + 1));
				shipstring = shipstring +x +","+ y+ ","+ currshipPassengers + ",";
			}
			
			
			
			
		}
			}
		
		
	}
		theString = theString+ stationstring +";" + shipstring+";";
	
	
		
	
	
	return theString;
	
}

}

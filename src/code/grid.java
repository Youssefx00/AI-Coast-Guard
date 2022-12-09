package code;

import java.util.ArrayList;
import java.util.Vector;

public class grid {
	int M;
	int N;
	int maxNumberofPassengers;
	Vector<String> thegrid;
	int coastGuardX;
	int coastGuardY;
	ArrayList<station> stationslist = new ArrayList<station>();
	ArrayList<ship> shipslist = new ArrayList<ship>();
	int[] visited;
	
	
	
	public grid(String input) {
		String[] temp = input.split(";");
		//	Now The String is split into Three Different sectors and should look like this
		//  [m,n; C ; grid] m and n need to be split again
		//  [0  , 1 , 2]

		//M and N Extraction
		String[] MandN = temp[0].split(",");
		M = Integer.parseInt(MandN[0]);
		N = Integer.parseInt(MandN[1]);
		
		
		maxNumberofPassengers = Integer.parseInt(temp[1]);
		
		//initial coast guard location
		String[] coastGuardloc = temp[2].split(",");
		coastGuardX = Integer.parseInt(coastGuardloc[0]);
		coastGuardY = Integer.parseInt(coastGuardloc[1]);
	
		//Grid Extraction
		String[] stations = temp[3].split(",");
		for(int i = 0; i<stations.length;i +=2) {
			int x = Integer.parseInt(stations[i]);
			int y = Integer.parseInt(stations[i+1]);
			station thisStation =  new station(x,y);
			stationslist.add(thisStation);
		}
		String[] ships = temp[4].split(",");
		for(int i = 0; i<ships.length; i += 3) {
			int x = Integer.parseInt(ships[i]);
			int y = Integer.parseInt(ships[i+1]);
			int Pass = Integer.parseInt(ships[i+2]);
			ship thisShip = new ship(x,y,Pass, 20, false, true);
			shipslist.add(thisShip);
			
		}
	
	}

	
	
	
	
	public boolean are_there_BlackBoxes() {
		
		for(int i = 0; i<shipslist.size();i++) {
			if(shipslist.get(i).hasBlackBox) {
				return true;
			}
			
		}
		
		
		return false;
	}

	public boolean are_there_humans() {
		
		for(int i = 0; i<shipslist.size();i++) {
			if(shipslist.get(i).are_there_people_here()) {
				return true;
			}
			
		}
		
		
		return false;
	}
	
	public int isThisAShip(node node){
		for(int i = 0;i<node.state.ships.size();i++) {
			if(node.state.ships.get(i).x == node.state.x && node.state.ships.get(i).y== node.state.y) {
				return i;
			}
		}
		return -1;
	}
	
	public boolean isThisAStation(node node){
		for(int i = 0;i<stationslist.size();i++) {
			if(stationslist.get(i).x == node.state.x && stationslist.get(i).y == node.state.y) {
				return true;
			}
		}
		return false;
	}
	 
	public boolean possiblemove(operator operating,node node) {
		
		if(operating== operator.Pickup){
			
			int ShipNumber1 = isThisAShip(node);
			if(ShipNumber1 != -1) {
				if(node.state.ships.get(ShipNumber1).numberOfPassengers>0 
						&& node.state.ships.get(ShipNumber1).isWreck == false 
						&& ((this.maxNumberofPassengers-node.state.carriedPassengers)>0)) {
					return true;
				}
				else {
					return false;
				}
			}
			else {
				return false;
			}
			
		}
		
		
		if(operating== operator.Drop){

			if(node.state.carriedPassengers> 0 && isThisAStation(node)) {
				
				return true;
			} else {
				
				return false;
			}

		}
					
	
			
		if(operating== operator.Retrieve){
			
			//get function to check the amount of passengers on board\
		
			int stationNumber = isThisAShip(node);
			if(stationNumber != -1) {
				if(node.state.ships.get(stationNumber).hasBlackBox == true && node.state.ships.get(stationNumber).isWreck == true && node.state.ships.get(stationNumber).BlackBoxHp>0) {
					return true;
				}
				else {
					return false;
				}
			}
			else {
					return false;
			}
		
		}
		if(operating == operator.Up){
			//if(y-1>0) {
			//if(node.state.x-1>=0 && node.state.x-1<M) {
			if(node.state.x-1>=0) {
				return true;
			}
		}
		if(operating == operator.Down){
			//if(y+1<N) {
			//if(node.state.x+1>0 && node.state.x+1<=M) {
			if(node.state.x+1<N) {
				return true;
			}
		}
		if(operating == operator.Right){	
			//if(x+1<M) {
			if(node.state.y+1<M) {
				return true;
			}
		}
		if(operating == operator.Left){
			//if(x-1>0) {
			//if(node.state.y-1>0 && node.state.y-1<=N) {
			if(node.state.y-1>=0 ) {
				return true;
			}
		}
	
		return false;
	}

}

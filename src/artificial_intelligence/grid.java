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

public grid(String input) {
	String[] temp = input.split(";");
	//	Now The String is split into Three Different sectors and should look like this
	//  [m,n; C ; grid] m and n need to be split again
	//  [0  , 1 , 2]
	
	//C Extraction
	maxNumberofPassengers = Integer.parseInt(temp[1]);
	
	
	
	//M and N Extraction
	String[] MandN = temp[0].split(",");
	M = Integer.parseInt(MandN[0]);
	N = Integer.parseInt(MandN[1]);
	
	//initial coast goard location
	String[] coastGuardloc = temp[3].split(",");
	
	coastGuardX = Integer.parseInt(coastGuardloc[0]);
	coastGuardY = Integer.parseInt(coastGuardloc[1]);
	
	
	//Grid Extraction
	String[] gridAlone = temp[4].split(",");
	
	String[] stations = temp[5].split(",");
	for(int i = 0; i<temp.length;i= i+2) {
		station thisStation =  new station(i,i+1);
		stationslist.add(thisStation);
	}
	String[] ships = temp[5].split(",");
	for(int i = 0; i<ships.length; i = i+3) {
		ship thisShip = new ship(i,i+1,i+2);
		shipslist.add(thisShip);
		
	}
	
	Vector<String> vec = new Vector<String>();
	vec.addAll(Arrays.asList(gridAlone));
	thegrid = vec;

}
}

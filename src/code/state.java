package code;

import java.util.ArrayList;

public class state {
	//CoastGuard
	int x;
	int y;
	//Passengers
	int unrescusedPassengers;
	int deaths;
	int carriedPassengers;
	//Boxes
	int undamagedBoxes;
	int retrievedBoxes;
	int lostBoxes;
	//currentShips
	ArrayList<ship> ships = new ArrayList<ship>();
	public state(int x, int y, int unrescusedPassengers, int deaths, int carriedPassengers, int undamagedBoxes, int retrievedBoxes, int lostBoxes, ArrayList<ship> ships) {
		this.x = x;
		this.y = y;
		this.unrescusedPassengers = unrescusedPassengers;
		this.deaths = deaths;
		this.carriedPassengers = carriedPassengers;
		this.undamagedBoxes = undamagedBoxes;
		this.retrievedBoxes = retrievedBoxes;
		this.lostBoxes = lostBoxes;
		this.ships = ships;
	}
}

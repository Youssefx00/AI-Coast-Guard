package code;

import java.util.ArrayList;

public class state {
	//CoastGuard
	int x;
	int y;
	//Passengers
	int unrescusedPassengers;
	int deaths;
	//Boxes
	int undamagedBoxes;
	int retrievedBoxes;
	//currentShips
	ArrayList<ship> ships;
	public state(int x, int y, int unrescusedPassengers, int deaths, int undamagedBoxes, int retrievedBoxes, ArrayList<ship> ships) {
		this.x = x;
		this.y = y;
		this.unrescusedPassengers = unrescusedPassengers;
		this.deaths = deaths;
		this.undamagedBoxes = undamagedBoxes;
		this.retrievedBoxes = retrievedBoxes;
		this.ships = ships;
	}
}

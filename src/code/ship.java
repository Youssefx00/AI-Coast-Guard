package code;

public class ship {
	int x;
	int y;
	int numberOfPassengers;

	int BlackBoxHp = 20;
public ship(int xCoordinate,int yCoordinate, int initNumberOfPassengers) {
	x= xCoordinate;
	y= yCoordinate;
	numberOfPassengers = initNumberOfPassengers;
}
public int getNumberOfPassengers() {
	return numberOfPassengers;
}
public void setNumberOfPassengers(int numberOfPassengers) {
	this.numberOfPassengers = numberOfPassengers;
}

public int getBlackBoxHp() {
	return BlackBoxHp;
}
public void setBlackBoxHp(int blackBoxHp) {
	BlackBoxHp = blackBoxHp;
}

}

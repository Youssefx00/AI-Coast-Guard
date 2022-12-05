package code;

public class ship {
	int x;
	int y;
	int numberOfPassengers;

	int BlackBoxHp = 20;
	boolean hasBlackBox = true;
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
public boolean isHasBlackBox() {
	return hasBlackBox;
}
public void setHasBlackBox(boolean hasBlackBox) {
	this.hasBlackBox = hasBlackBox;
}
public boolean are_there_people_here() {
	
	if(numberOfPassengers<0) {
	return true;
	}
	return false;
}
}

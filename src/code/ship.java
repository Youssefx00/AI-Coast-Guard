package code;

public class ship {
	int x;
	int y;
	int numberOfPassengers;
	int BlackBoxHp = 20;
	boolean isWreck;
	boolean hasBlackBox;
	
public ship(int xCoordinate,int yCoordinate, int initNumberOfPassengers, int BlackBoxHp, boolean isWreck, boolean hasBlackBox) {
	this.x= xCoordinate;
	this.y= yCoordinate;
	this.numberOfPassengers = initNumberOfPassengers;
	this.BlackBoxHp = BlackBoxHp;
	this.isWreck = isWreck;
	this.hasBlackBox = hasBlackBox;
	
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

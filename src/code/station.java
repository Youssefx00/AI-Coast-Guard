package code;

public class station {
	int x;
	int y;
	boolean hasBlackBox;
public station(int xCoordinate, int yCoordinate) {
	x = xCoordinate;
	y = yCoordinate;
}
public boolean isHasBlackBox() {
	return hasBlackBox;
}
public void setHasBlackBox(boolean hasBlackBox) {
	this.hasBlackBox = hasBlackBox;
}
}

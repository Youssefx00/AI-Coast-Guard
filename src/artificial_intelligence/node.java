package artificial_intelligence;

public class node {
	//int[] parentNode;
	node parentNode;
	operator operator;
	int depth;
	float pathCost;
//	State
	int numberOfLivingOfUnrescued;
	int numberOfUndamagedBoxesUnrescued;
	int numberOfPassengers;
	
	
	public node(node parentNode, operator operator, int depth, float pathCost, int numberOfLivingOfUnrescued, int numberOfUndamagedBoxesUnrescued, int numberOfPassengers) {
		this.parentNode = parentNode;
		this.operator = operator;
		this.depth = depth;
		this.pathCost = pathCost;
		this.numberOfLivingOfUnrescued = numberOfLivingOfUnrescued;
		this.numberOfUndamagedBoxesUnrescued = numberOfUndamagedBoxesUnrescued;
		this.numberOfPassengers = numberOfPassengers;
	}
	
	
}

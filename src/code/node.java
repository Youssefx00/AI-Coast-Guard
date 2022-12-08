package code;

public class node {
	node parentNode;
	operator operator;
	int depth;
	float pathCost;
	state state;
	String Solution;
	public node(node parentNode, operator operator, int depth, float pathCost, state state) {
		this.parentNode = parentNode;
		this.operator = operator;
		this.depth = depth;
		this.pathCost = pathCost;
		this.state =  state;
	}
	
	
}

package code;

public class node implements Comparable{
	node parentNode;
	operator operator;
	int depth;
	int[] pathCost = new int[2];
	state state;
	String Solution;
	public node(node parentNode, operator operator, int depth, int[] pathCost, state state) {
		this.parentNode = parentNode;
		this.operator = operator;
		this.depth = depth;
		this.pathCost = pathCost;
		this.state =  state;
	}
	@Override
	public int compareTo(Object o) {
		node otherNode = (node) o;
		
		if(this.pathCost[0] > otherNode.pathCost[0] || this.pathCost[1] > otherNode.pathCost[0]) {
			
			return 1;
		} else if(this.pathCost[0] == otherNode.pathCost[0] && this.pathCost[1] == otherNode.pathCost[0]) {
			return 0;
		}
		else if (this.pathCost[0] < otherNode.pathCost[0] || this.pathCost[1] < otherNode.pathCost[0]){
			return -1;
		}
		return 0;
	}
	
	
}

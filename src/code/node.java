package code;
import java.util.*;
public class node{
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
	
	
}
class UCScomparable implements Comparator<node>{
	@Override
	public int compare(node node1, node node2) {
		
		if(node1.pathCost[0] > node2.pathCost[0] || node1.pathCost[1] > node2.pathCost[1]) {
			return -1;
		} else if(node1.pathCost[0] == node2.pathCost[0] && node1.pathCost[1] == node2.pathCost[1]) {
			return 0;
		}
		else if (node1.pathCost[0] < node2.pathCost[0] || node1.pathCost[1] < node2.pathCost[1]){
			return 1;
		}
		return 0;
//		if(node1.pathCost[0] > node2.pathCost[0] ) {
//			return 1;
//		} else if(node1.pathCost[0] > node2.pathCost[0]){
//			
//		}
//			else if(node1.pathCost[0] == node2.pathCost[0] && node1.pathCost[1] == node2.pathCost[0]) {
//		}
//			return 0;
//		}
//		else if (node1.pathCost[0] < node2.pathCost[0] && node1.pathCost[1] < node2.pathCost[0]){
//			return -1;
//		}
//		return 0;
		
	}
	
}
class HR1comparable implements Comparator<node>{
	@Override
	public int compare(node node1, node node2) {
		
		//System.out.println("Node 1:" + CoastGuard.priorityHumanDecider(node1));
		//System.out.println("Node 2:" + CoastGuard.priorityHumanDecider(node2));
		if(CoastGuard.priorityHumanDecider(node1) > CoastGuard.priorityHumanDecider(node2)) {
			
			return -1;
		} else if(CoastGuard.priorityHumanDecider(node1) == CoastGuard.priorityHumanDecider(node2) ) {
			return 0;
		}
		else if (CoastGuard.priorityHumanDecider(node1) < CoastGuard.priorityHumanDecider(node2)){
			return 1;
		}
		return 0;
	}
	
}

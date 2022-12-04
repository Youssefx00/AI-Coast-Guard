package code;

import java.util.LinkedList;
import java.util.Queue;

public class CoastGuard extends GenericSearchProblem{
	
	
	@SuppressWarnings("unused")
	public static String solve(String grid,String strategy,Boolean visualize) {
		
		String Final_Solution = null;
		grid FirstGrid = new grid(grid);
		
		if(strategy.equals("BF")) {
			
		}
		if(strategy.equals("DF")) {
			
		}
		if(strategy.equals("ID")) {
			
		}
		if(strategy.equals("GR1")) {
				
		}
		if(strategy.equals("GR2")) {
			
		}
		if(strategy.equals("AS2")) {
			
		}
		if(strategy.equals("AS1")) {
			
		}
		
		return Final_Solution;
	}
	public node General_Search_Procedure(node initialNode, String strat) {
		Queue<node> nodes = new LinkedList<>();
		
		nodes.add(initialNode);
		
		switch (strat) {
		case "BF": 
			if(nodes.isEmpty())
				return null;
			while (!nodes.isEmpty()) {
				node currNode = nodes.remove();
				if(GoalTest(currNode) == true) {
					return currNode;
				}
				nodes.add(expand(currNode));
			}
			break;
		default: break;
		}
		
		return null;
	}
	
	node expand(node node) {
		return null;
	}
	@Override
	Boolean GoalTest(node node) {
		
		if(node.state.unrescusedPassengers == 0 && node.state.undamagedBoxes == 0) {
			return true;
		}
		return false;
	}
	@Override
	int PathCost(node node) {
		// TODO Auto-generated method stub
		return 0;
	}
}

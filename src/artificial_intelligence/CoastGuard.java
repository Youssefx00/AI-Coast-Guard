package artificial_intelligence;

public class CoastGuard extends GenericSearchProblem{
	
	
	public static String Solve(String grid,String strategy,Boolean visualize) {
		
		String Final_Solution = null;
		grid FirstGrid = new grid(grid);
		
		if(strategy.equals("BF")) {
			
		}
		if(strategy.equals("DF")) {
			
		}
		if(strategy.equals("ID")) {
	
		}
		if(strategy.equals("GR2")) {
	
		}
		if(strategy.equals("AS2")) {
	
		}
		if(strategy.equals("AS1")) {
			
		}
		if(strategy.equals("GR1")) {
			
		}
		return Final_Solution;
	}
	public void General_Search_Procedure(GenericSearchProblem x, String strat) {
		
	}
	public void Best_First_Search(GenericSearchProblem x, String strat) {
		
	}
	@Override
	Boolean GoalTest() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	int PathCost() {
		// TODO Auto-generated method stub
		return 0;
	}
}

package artificial_intelligence;

import java.util.ArrayList;

public abstract class GenericSearchProblem {
	ArrayList<operator> operators = new ArrayList<operator>();
	int[] initialState;
	Object[][] stateSpace;
	abstract Boolean GoalTest();
	abstract int PathCost();
}

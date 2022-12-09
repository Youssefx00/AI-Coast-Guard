package code;

import java.rmi.server.Operation;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Stack;
import java.util.concurrent.ThreadLocalRandom;

public class CoastGuard extends GenericSearchProblem {

	static int gridM = 0;
	static int gridN = 0;
	static int maxPassengers = 0;
	static ArrayList<station> stationslist = new ArrayList<station>();
	static int iterativeDepth = 0;
	static HashMap<String, state> RepeatedStates;//

	@SuppressWarnings("unused")
	public static String solve(String grid, String strategy, Boolean visualize) {
		RepeatedStates = new HashMap<String, state>();
		String Final_Solution = null;
		grid FirstGrid = new grid(grid);
		gridM = FirstGrid.M;
		gridN = FirstGrid.N;
		stationslist = FirstGrid.stationslist;
		maxPassengers = FirstGrid.maxNumberofPassengers;
		iterativeDepth = 1;
		// create initial node
		state initialState = new state(FirstGrid.coastGuardX, FirstGrid.coastGuardY,
				calcPassengers(FirstGrid.shipslist), 0, 0, 0, 0, 0,FirstGrid.shipslist);

		int[] pathCost = { 0, 0 };
		node initialNode = new node(null, null, 0, pathCost, initialState);
		node goalNode = null;
		// get goal node
		if (strategy == "ID") {
			while (goalNode == null) {
				RepeatedStates = new HashMap<String, state>();
				goalNode = General_Search_Procedure(FirstGrid, initialNode, "ID");
				iterativeDepth += 1;
			}
		} else {
			if(strategy == "GR1" || strategy == "UC" || strategy == "GR2" || strategy == "AS1" || strategy == "AS2") {
			goalNode = General_Search_ProcedureHR(FirstGrid, initialNode, strategy);
			}
			else {
				goalNode = General_Search_Procedure(FirstGrid, initialNode, strategy);
			}
		}

		// create String
		String tempStr = goalNode.Solution;
		Final_Solution = goalNode.Solution.substring(4, tempStr.length() - 1);
		String[] tempArr = Final_Solution.split(",");
		Final_Solution += ";" + goalNode.state.deaths + ";";
		Final_Solution += goalNode.state.retrievedBoxes + ";";
		Final_Solution += tempArr.length + "";
		// System.out.println(Final_Solution);
		
		if(visualize == true) {
			Stack<node> reversedNodes = new Stack<node>();
			node currNode = goalNode;
			while(currNode!=null) {
				reversedNodes.push(currNode);
				currNode = currNode.parentNode;
			}
			Stack<node> nodes = new Stack<node>();
			while(!nodes.isEmpty()) {
				Visualise(nodes.pop());
			}
		}
		return Final_Solution;
	}

	public static node General_Search_Procedure(grid grid, node initialNode, String strat) {
		Deque<node> nodes = new LinkedList<>();
		nodes.add(initialNode);
		while (!nodes.isEmpty()) {
			node currNode = nodes.removeFirst();
//			System.out.println("operator: " +currNode.operator);
//			System.out.println("depth: " +currNode.depth);
//			System.out.println("pathCost: " +currNode.pathCost[0] + ", " + currNode.pathCost[1]);
//			System.out.println("x: " +currNode.state.x);
//			System.out.println("y: " +currNode.state.y);
//			System.out.println("unrescued: " +currNode.state.unrescusedPassengers);
//			System.out.println("deaths: " +currNode.state.deaths);
//			System.out.println("carried: " +currNode.state.carriedPassengers);
//			System.out.println("undamaged: " +currNode.state.undamagedBoxes);
//			System.out.println("retrieved: " +currNode.state.retrievedBoxes);
//			for(int i = 0; i<currNode.state.ships.size(); i++) {
//				System.out.println("Ship: " +(i+1)+":- "+ currNode.state.ships.get(i).numberOfPassengers + " , BlackBoxHP: "+ currNode.state.ships.get(i).BlackBoxHp);
//			}
//			
//			System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");		
			if(GoalTest(currNode) == true) {
				System.out.println("Success");
				return currNode;
			}
			Deque<node> newNodes = new LinkedList<>();
			newNodes = Expand(grid, currNode, strat);
			nodes = QingFunc(nodes, newNodes, strat);
			// System.out.println("GS expanded: " + nodes.size());

		}
		System.out.println("Failure");
		return null;

	}
	public static node General_Search_ProcedureHR(grid grid, node initialNode, String strat) {
		PriorityQueue<node> nodes = new PriorityQueue<node>(new AS1comparable());
		nodes.add(initialNode);
		while (!nodes.isEmpty()) {
			node currNode = nodes.remove();
//			System.out.println("operator: " +currNode.operator);
//			System.out.println("depth: " +currNode.depth);
//			System.out.println("pathCost: " +currNode.pathCost[0] + ", " + currNode.pathCost[1]);
//			System.out.println("x: " +currNode.state.x);
//			System.out.println("y: " +currNode.state.y);
//			System.out.println("unrescued: " +currNode.state.unrescusedPassengers);
//			System.out.println("deaths: " +currNode.state.deaths);
//			System.out.println("carried: " +currNode.state.carriedPassengers);
//			System.out.println("undamaged: " +currNode.state.undamagedBoxes);
//			System.out.println("retrieved: " +currNode.state.retrievedBoxes);
//			for(int i = 0; i<currNode.state.ships.size(); i++) {
//				System.out.println("Ship: " +(i+1)+":- "+ currNode.state.ships.get(i).numberOfPassengers + " , BlackBoxHP: "+ currNode.state.ships.get(i).BlackBoxHp);
//			}
//			
//			System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");		
			if(GoalTest(currNode) == true) {
				System.out.println("Success");
				return currNode;
			}
			Deque<node> newNodes = new LinkedList<>();
			newNodes = Expand(grid, currNode, strat);
			nodes = QingFuncHR(nodes, newNodes, strat);
			// System.out.println("GS expanded: " + nodes.size());

		}
		System.out.println("Failure");
		return null;

	}
	static PriorityQueue<node> QingFuncHR(PriorityQueue<node> nodes, Deque<node> newNodes, String strat) {
		PriorityQueue<node> finalNodes= new PriorityQueue<node>();
		
		
		switch (strat) {
		case "UC":
			finalNodes = new PriorityQueue<node>(new UCScomparable());
			finalNodes.addAll(newNodes);
			PriorityQueue<node> pq = new PriorityQueue<node>(new UCScomparable());
			pq.addAll(nodes);
			for (int i = 0; i < newNodes.size(); i++) {
				pq.add(newNodes.removeFirst());
			}
			finalNodes.addAll(pq);
			break;
		case "GR1":
			finalNodes = new PriorityQueue<node>(new GR1comparable());
			finalNodes.addAll(newNodes);
			PriorityQueue<node> pq1 = new PriorityQueue<node>(new GR1comparable());
			for (int i = 0; i < newNodes.size(); i++) {
				pq1.add(newNodes.removeFirst());
			}
			finalNodes.addAll(pq1);
			break;
		case "GR2":
			finalNodes = new PriorityQueue<node>(new GR2comparable());
			finalNodes.addAll(newNodes);
			PriorityQueue<node> pq2 = new PriorityQueue<node>(new GR2comparable());
			for (int i = 0; i < newNodes.size(); i++) {
				pq2.add(newNodes.removeFirst());
			}
			finalNodes.addAll(pq2);
			break;
		case "AS1":
			finalNodes = new PriorityQueue<node>(new AS1comparable());
			finalNodes.addAll(newNodes);
			PriorityQueue<node> pq3 = new PriorityQueue<node>(new AS1comparable());
			for (int i = 0; i < newNodes.size(); i++) {
				pq3.add(newNodes.removeFirst());
			}
			finalNodes.addAll(pq3);
			break;
		case "AS2":
			finalNodes = new PriorityQueue<node>(new AS2comparable());
			finalNodes.addAll(newNodes);
			PriorityQueue<node> pq4 = new PriorityQueue<node>(new AS2comparable());
			for (int i = 0; i < newNodes.size(); i++) {
				pq4.add(newNodes.removeFirst());
			}
			finalNodes.addAll(pq4);
			break;
		default:
			break;
		}

		return finalNodes;
	}
	static boolean isFull(node node) {
		boolean bool = false;
		if (node.state.carriedPassengers >= maxPassengers) {
			bool = true;
		}
		return bool;
	}

	static Deque<node> QingFunc(Deque<node> nodes, Deque<node> newNodes, String strat) {
		// Deque<node> finalNodes = nodes;
		Deque<node> finalNodes = new LinkedList<>();
		
		switch (strat) {
		case "DF":
			finalNodes.addAll(nodes);
			for (int i = 0; i < newNodes.size(); i++) {
				finalNodes.addFirst(newNodes.removeFirst());
			}
			break;
		case "BF":
			finalNodes.addAll(nodes);
			for (int i = 0; i < newNodes.size(); i++) {
				finalNodes.addLast(newNodes.removeFirst());
			}
			break;
		case "ID":
			finalNodes.addAll(nodes);
			for (int i = 0; i < newNodes.size(); i++) {
				node tempNode = newNodes.removeFirst();
				if (tempNode.depth <= iterativeDepth) {
					finalNodes.addFirst(tempNode);
				}
			}
			break;
//		case "UC":
////			PriorityQueue<node> oldNodesUC = new PriorityQueue<node>(new UCScomparable());
////			for (int i = 0; i < nodes.size(); i++) {
////				oldNodesUC.add(nodes.removeFirst());
////			}
//			PriorityQueue<node> pq = new PriorityQueue<node>(new UCScomparable());
//			pq.addAll(nodes);
//			for (int i = 0; i < newNodes.size(); i++) {
//				pq.add(newNodes.removeFirst());
//			}
//			finalNodes.addAll(pq);
//			break;
//		case "GR1":
//			
////			PriorityQueue<node> oldNodesGR1 = new PriorityQueue<node>(new HR1comparable());
////			for (int i = 0; i < nodes.size(); i++) {
////				oldNodesGR1.add(nodes.removeFirst());
////			}
//			PriorityQueue<node> pq1 = new PriorityQueue<node>(new HR1comparable());
//			pq1.addAll(nodes);
//			for (int i = 0; i < newNodes.size(); i++) {
//				pq1.add(newNodes.removeFirst());
//			}
//			finalNodes.addAll(pq1);
//			break;
//		case "GR2":
//			for (int i = 0; i < newNodes.size(); i++) {
//				finalNodes.addLast(newNodes.removeFirst());
//			}
//			break;
//		case "AS1":
//			for (int i = 0; i < newNodes.size(); i++) {
//				finalNodes.addLast(newNodes.removeFirst());
//			}
//			break;
//		case "AS2":
//			for (int i = 0; i < newNodes.size(); i++) {
//				finalNodes.addLast(newNodes.removeFirst());
//			}
//			break;
		default:
			break;
		}

		return finalNodes;
	}

	static void Visualise(node theNode) {
		String[][] map = new String[gridN][gridM];
		for (int i = 0; i < theNode.state.ships.size(); i++) {
			ArrayList<ship> currships = theNode.state.ships;
			ship thisShip = currships.get(i);

			if (!thisShip.isWreck) {
				map[thisShip.y][thisShip.x] = "Ship " + "(" + thisShip.numberOfPassengers + ")";

			} else {
				map[thisShip.y][thisShip.x] = "Wreck " + "(" + thisShip.BlackBoxHp + ")";

			}
		}

		for (int i = 0; i < stationslist.size(); i++) {
			station thisStation = stationslist.get(i);
			map[thisStation.x][thisStation.y] = "Station";

		}
		map[theNode.state.y][theNode.state.x] = map[theNode.state.y][theNode.state.x] + "|| CoastGuard " + "("
				+ theNode.state.carriedPassengers + ")";

		for (int i = 0; i < map.length; i++) {
			System.out.println("");
			for (int j = 0; j < map[i].length; j++) {
				System.out.print(map[i][j] + ",");
			}

		}

	}

	static Deque<node> Expand(grid grid, node node, String strat) {

		Deque<node> nodes = new LinkedList<>();
		// System.out.println("expand");

		operator parentOp = null;

		try {
			parentOp = node.parentNode.operator;
		} catch (Exception e) {

		}
		if (grid.possiblemove(operator.Drop, node) == true) {

			nodes.addFirst(createNode(grid, node, operator.Drop));

		}
		if (grid.possiblemove(operator.Pickup, node) == true) {

			nodes.addFirst(createNode(grid, node, operator.Pickup));

		}
		if (grid.possiblemove(operator.Retrieve, node) == true) {

			nodes.addFirst(createNode(grid, node, operator.Retrieve));

		}

		if (grid.possiblemove(operator.Right, node) == true) {
			// if(grid.possiblemove(operator.Right, node) == true && parentOp !=
			// operator.Left) {

			node newNode = createNode(grid, node, operator.Right);
			String key = Integer.toString(newNode.state.x) + Integer.toString(newNode.state.y)
					+ Integer.toString(newNode.state.unrescusedPassengers) + Integer.toString(newNode.state.deaths)
					+ Integer.toString(newNode.state.undamagedBoxes) + Integer.toString(newNode.state.retrievedBoxes)
					+ Shiphealths(newNode.state.ships) + "";
			//if(!RepeatedStates.containsKey(key)|| strat == "GR1") {
			if (!RepeatedStates.containsKey(key)) {
				nodes.addFirst(newNode);
				RepeatedStates.put(key, newNode.state);
			}
		}

		if (grid.possiblemove(operator.Up, node) == true) {
			// if(grid.possiblemove(operator.Up, node) == true && parentOp != operator.Down)
			// {
			node newNode = createNode(grid, node, operator.Up);
			String key = Integer.toString(newNode.state.x) + Integer.toString(newNode.state.y)
					+ Integer.toString(newNode.state.unrescusedPassengers) + Integer.toString(newNode.state.deaths)
					+ Integer.toString(newNode.state.undamagedBoxes) + Integer.toString(newNode.state.retrievedBoxes)
					+ Shiphealths(newNode.state.ships) + "";
			//if(!RepeatedStates.containsKey(key)|| strat == "GR1") {
			if (!RepeatedStates.containsKey(key)) {
				nodes.addFirst(newNode);
				RepeatedStates.put(key, newNode.state);
			}
		}

		if (grid.possiblemove(operator.Left, node) == true) {
			// if(grid.possiblemove(operator.Left, node) == true && parentOp !=
			// operator.Right) {

			node newNode = createNode(grid, node, operator.Left);
			String key = Integer.toString(newNode.state.x) + Integer.toString(newNode.state.y)
					+ Integer.toString(newNode.state.unrescusedPassengers) + Integer.toString(newNode.state.deaths)
					+ Integer.toString(newNode.state.undamagedBoxes) + Integer.toString(newNode.state.retrievedBoxes)
					+ Shiphealths(newNode.state.ships) + "";
			// if(!RepeatedStates.containsKey(key)|| strat == "GR1") {
			if (!RepeatedStates.containsKey(key)) {
				nodes.addFirst(newNode);
				RepeatedStates.put(key, newNode.state);
			}
		}
		if (grid.possiblemove(operator.Down, node) == true) {
			// if(grid.possiblemove(operator.Down, node) == true && parentOp != operator.Up)
			// {

			node newNode = createNode(grid, node, operator.Down);
			String key = Integer.toString(newNode.state.x) + Integer.toString(newNode.state.y)
					+ Integer.toString(newNode.state.unrescusedPassengers) + Integer.toString(newNode.state.deaths)
					+ Integer.toString(newNode.state.undamagedBoxes) + Integer.toString(newNode.state.retrievedBoxes)
					+ Shiphealths(newNode.state.ships) + "";
			//if(!RepeatedStates.containsKey(key)|| strat == "GR1") {
			if (!RepeatedStates.containsKey(key)) {
				nodes.addFirst(newNode);
				RepeatedStates.put(key, newNode.state);
			}
		}

		// System.out.println("expanded: "+ nodes.size() + " nodes");
		return nodes;
	}

	static String Shiphealths(ArrayList<ship> ships) {
		String x = "";
		for (int i = 0; i < ships.size(); i++) {
			x += ships.get(i).BlackBoxHp + "";
		}
		return x;
	}

	static Boolean GoalTest(node node) {
		// if(hasBlaxBoxes == false && node.state.unrescusedPassengers == 0 &&
		// node.state.undamagedBoxes == 0 && node.state.carriedPassengers == 0) {
		if (node.state.unrescusedPassengers <= 0 && node.state.undamagedBoxes <= 0
				&& node.state.carriedPassengers <= 0) {
			// if(node.state.unrescusedPassengers == 0 && node.state.undamagedBoxes == 0 &&
			// node.state.carriedPassengers == 0) {
			return true;
		}
		return false;
	}

	static int[] pathCost(state state) {
		//int LostBoxes = state.undamagedBoxes - state.retrievedBoxes;
		int[] pathCost = {state.deaths, state.lostBoxes };
		return pathCost;
	}

	static int calcPassengers(ArrayList<ship> ships) {
		int Sum = 0;
		for (int i = 0; i < ships.size(); i++) {
			if (ships.get(i).isWreck == false) {
				Sum += ships.get(i).numberOfPassengers;
			}
		}
		return Sum;
	}

	static int calcBoxes(ArrayList<ship> ships) {
		int Sum = 0;
		for(int i = 0; i< ships.size();i++) {
			if(ships.get(i).hasBlackBox == true 
					&& ships.get(i).BlackBoxHp<20 ) {
				Sum += 1;
			}
		}
		return Sum;
	}
	static int calcLostBoxes(ArrayList<ship> ships) {
		int Sum = 0;
		for(int i = 0; i< ships.size();i++) {
			if(ships.get(i).hasBlackBox == false ) {
				Sum += 1;
			}
		}
		return Sum;
	}

	static int calcCarried(node node, ArrayList<ship> ships) {
		int Pass = 0;
		for (int i = 0; i < ships.size(); i++) {
			if (ships.get(i).x == node.state.x && ships.get(i).y == node.state.y) {
				Pass = node.state.carriedPassengers
						+ (node.state.ships.get(i).numberOfPassengers - ships.get(i).numberOfPassengers);
			}
		}
		return Pass;
	}

	static node createNode(grid grid, node node, operator operation) {
		 
		// int BoxDecrements = calcBoxDecrements(node);
		ArrayList<ship> updatedShips = updateShips(grid, node, operation);
		state newState = null;
		node newNode = null;
		int newPassengers = calcPassengers(updatedShips);
		int PassDecrements = calcPassDecrements(node);
		//int deaths = calcPassengers(grid.shipslist) - newPassengers;
		int deaths = node.state.deaths + PassDecrements;
		int x = node.state.x;
		int y = node.state.y;

		switch (operation) {
		case Up:
			newState = new state(x - 1, y, newPassengers, deaths, node.state.carriedPassengers, calcBoxes(updatedShips),
					node.state.retrievedBoxes,calcLostBoxes(updatedShips), updatedShips);
			newNode = new node(node, operator.Up, node.depth + 1, pathCost(newState), newState);
			newNode.Solution = node.Solution + "up,";
			break;
		case Down:
			newState = new state(x + 1, y, newPassengers, deaths, node.state.carriedPassengers, calcBoxes(updatedShips),
					node.state.retrievedBoxes,calcLostBoxes(updatedShips), updatedShips);
			newNode = new node(node, operator.Down, node.depth + 1, pathCost(newState), newState);
			newNode.Solution = node.Solution + "down,";
			break;
		case Left:
			newState = new state(x, y - 1, newPassengers, deaths, node.state.carriedPassengers, calcBoxes(updatedShips),
					node.state.retrievedBoxes, calcLostBoxes(updatedShips),updatedShips);
			newNode = new node(node, operator.Left, node.depth + 1, pathCost(newState), newState);
			newNode.Solution = node.Solution + "left,";
			break;
		case Right:
			newState = new state(x, y + 1, newPassengers, deaths, node.state.carriedPassengers, calcBoxes(updatedShips),
					node.state.retrievedBoxes,calcLostBoxes(updatedShips), updatedShips);
			newNode = new node(node, operator.Right, node.depth + 1, pathCost(newState), newState);
			newNode.Solution = node.Solution + "right,";
			break;
		case Retrieve:
			int retrieved = node.state.retrievedBoxes + 1;
			newState = new state(x, y, newPassengers, deaths, node.state.carriedPassengers, calcBoxes(updatedShips),
					retrieved,calcLostBoxes(updatedShips), updatedShips);
			newNode = new node(node, operator.Retrieve, node.depth + 1, pathCost(newState), newState);
			newNode.Solution = node.Solution + "retrieve,";
			break;
		case Pickup:

			newState = new state(x, y, newPassengers, deaths, calcCarried(node, updatedShips), calcBoxes(updatedShips),
					node.state.retrievedBoxes, calcLostBoxes(updatedShips),updatedShips);
			newNode = new node(node, operator.Pickup, node.depth + 1, pathCost(newState), newState);
			newNode.Solution = node.Solution + "pickup,";
			//updateTime(grid, newNode, operation);
			break;
		case Drop:

			newState = new state(x, y, newPassengers, deaths, 0, calcBoxes(updatedShips), node.state.retrievedBoxes,
					calcLostBoxes(updatedShips),updatedShips);
			newNode = new node(node, operator.Drop, node.depth + 1, pathCost(newState), newState);
			newNode.Solution = node.Solution + "drop,";
			break;
		default:
			break;
		}
		
		//newNode.state.undamagedBoxes = calcBoxes(updatedShips);
		return newNode;
	}

	// Update Number of passengers: wreck if passengers drops to 0, and removes ship
	// if blackboxhp drops to 0
	static ArrayList<ship> updateShips(grid grid, node node, operator operation) {
		ArrayList<ship> newShipList = new ArrayList<ship>();
		for(int i = 0; i<node.state.ships.size(); i++) {
			
			ship newShip = new ship(node.state.ships.get(i).x, node.state.ships.get(i).y, node.state.ships.get(i).numberOfPassengers, node.state.ships.get(i).BlackBoxHp, node.state.ships.get(i).isWreck, node.state.ships.get(i).hasBlackBox); 

			if(newShip.x == node.state.x && newShip.y == node.state.y) {
				switch (operation) {
				case Retrieve:
					newShip.hasBlackBox = false;
					newShip.isWreck = true;
					newShip.BlackBoxHp = 20;
					break;
				case Pickup:

					int x = grid.maxNumberofPassengers - node.state.carriedPassengers;

					if (newShip.numberOfPassengers > x) {
						newShip.numberOfPassengers -= x;
					} else if(newShip.numberOfPassengers == x){
						newShip.numberOfPassengers = 0;
						//newShip.isWreck = true;
					}
					else{

						newShip.numberOfPassengers = 0;
						//newShip.isWreck = true;
					}

					break;
				default:
					break;
				}
			}
			//update
			//if(operation != operator.Pickup) {
				if(newShip.isWreck == true && newShip.hasBlackBox == true) {
					
					if(newShip.BlackBoxHp<20) {
						newShip.BlackBoxHp +=1;
					}

				} else if (newShip.isWreck == false && newShip.hasBlackBox == true) {
					if (newShip.numberOfPassengers > 0) {
						newShip.numberOfPassengers -= 1;
					}
				}
				
				if(newShip.BlackBoxHp >= 20) {
					newShip.hasBlackBox = false;
					newShip.isWreck = true;
				}
				if(newShip.numberOfPassengers <= 0) {
					newShip.isWreck = true;
				}
			//}
			newShipList.add(newShip);
		}
		return newShipList;
	}
	static void updateTime(grid grid,node node, operator operation){
		
		for(int i = 0; i<node.state.ships.size(); i++) {
			if(node.state.ships.get(i).isWreck == true && node.state.ships.get(i).hasBlackBox == true) {
				if(node.state.ships.get(i).BlackBoxHp<20) {
					node.state.ships.get(i).BlackBoxHp +=1;
				}

			} else if (node.state.ships.get(i).isWreck == false && node.state.ships.get(i).hasBlackBox == true) {
				if (node.state.ships.get(i).numberOfPassengers > 0) {
					node.state.ships.get(i).numberOfPassengers -= 1;
				}

			}
//			else {
//				System.out.println("Wreck: " + node.state.ships.get(i).isWreck);
//				System.out.println("Box: " + node.state.ships.get(i).hasBlackBox);
//				System.out.println("failllllll");
//			}
			if(node.state.ships.get(i).BlackBoxHp > 20) {
				node.state.ships.get(i).hasBlackBox = false;
				node.state.ships.get(i).isWreck = true;
			}
			if(node.state.ships.get(i).numberOfPassengers <= 0) {
				node.state.ships.get(i).isWreck = true;
			}

		}

	}

	static int calcPassDecrements(node node) {
		int Sum = 0;
		for (int i = 0; i < node.state.ships.size(); i++) {
			if (node.state.ships.get(i).isWreck == false && node.state.ships.get(i).hasBlackBox == true && node.state.ships.get(i).numberOfPassengers > 0) {
				Sum += 1;
			}
		}
		return Sum;
	}

	static int calcBoxDecrements(node node) {
		int Sum = 0;
		for (int i = 0; i < node.state.ships.size(); i++) {
			if (node.state.ships.get(i).isWreck && node.state.ships.get(i).BlackBoxHp > 0) {
				Sum += 1;
			}
		}
		return Sum;
	}

	static String GenGrid() {
		String theString = "";

		ThreadLocalRandom.current().nextInt(5, 15);
		int sizem = ThreadLocalRandom.current().nextInt(5, 16);
		int sizen = ThreadLocalRandom.current().nextInt(5, 16);

		theString = theString + sizem + ",";
		theString = theString + sizen + ";";

		int coastGuardmax = ThreadLocalRandom.current().nextInt(30, 100);

//				30 +  (int)(Math.random() * ((30 - 100) + 1));
		coastGuardmax = Math.abs(coastGuardmax);
		theString = theString + coastGuardmax + ";";

		int coastGuardX = ThreadLocalRandom.current().nextInt(0, sizem);
//				(int)(Math.random() * ((sizem) + 1));
		int coastGuardY = ThreadLocalRandom.current().nextInt(0, sizen);
//				(int)(Math.random() * ((sizen) + 1));
		theString = theString + coastGuardX + "," + coastGuardY + ";";

		int shipx = 0;
		int shipy = 0;
		int shipPassengers =

				ThreadLocalRandom.current().nextInt(0, 100);

//				(int)(Math.random() * ((100) + 1));
		String shipstring = "";
		while (true) {
			shipx = ThreadLocalRandom.current().nextInt(0, sizem);
//					(int)(Math.random() * ((sizem) + 1));

			shipy = ThreadLocalRandom.current().nextInt(0, sizen);

//					(int)(Math.random() * ((sizem) + 1));
			if (shipx != coastGuardX && shipy != coastGuardY) {
				shipstring = shipstring + shipx + "," + shipy + "," + shipPassengers;

				break;
			}
		}

		int stationx = 0;
		int stationy = 1;
		String stationstring = "";
		while (true) {
			stationx = ThreadLocalRandom.current().nextInt(0, sizem);
//					(int)(Math.random() * ((sizem-1) + 1));
			stationy = ThreadLocalRandom.current().nextInt(0, sizen);
//					(int)(Math.random() * ((sizem-1) + 1));

			if (stationx != coastGuardX && stationy != coastGuardY) {
				if (stationx != shipx && stationy != shipy) {
					stationstring = stationstring + stationx + "," + stationy + ",";
					break;

				}

			}

		}

		for (int x = 0; x < sizem; x++) {
			for (int y = 0; y < sizen; y++) {
				if (x == stationx && y == stationy) {

				} else if (x == shipx && y == shipy) {

				} else if (x == coastGuardX && y == coastGuardY) {

				} else {
					int luckofthedraw = ThreadLocalRandom.current().nextInt(0, 11);

//						(int)(Math.random() * ((10) + 1));
					if (luckofthedraw == 10) {
						stationstring = stationstring + x + "," + y + ";";
					}
					if (luckofthedraw == 9) {
						int currshipPassengers = ThreadLocalRandom.current().nextInt(0, 100);
//							(int)(Math.random() * ((100) + 1));
						shipstring = shipstring + "," + x + "," + y + "," + currshipPassengers;
					}

				}
			}

		}
		theString = theString + stationstring + ";" + shipstring + ";";

		return theString;

	}

	public static void main(String[] args) {

		String grid0 = "5,6;50;0,1;0,4,3,3;1,1,90;";
		String grid1 = "6,6;52;2,0;2,4,4,0,5,4;2,1,19,4,2,6,5,0,8;";
		String grid2 = "7,5;40;2,3;3,6;1,1,10,4,5,90;";
		String grid3 = "8,5;60;4,6;2,7;3,4,37,3,5,93,4,0,40;";
		String grid4 = "5,7;63;4,2;6,2,6,3;0,0,17,0,2,73,3,0,30;";
		String grid5 = "5,5;69;3,3;0,0,0,1,1,0;0,3,78,1,2,2,1,3,14,4,4,9;";
		String grid6 = "7,5;86;0,0;1,3,1,5,4,2;1,1,42,2,5,99,3,5,89;";
		String grid7 = "6,7;82;1,4;2,3;1,1,58,3,0,58,4,2,72;";
		String grid8 = "6,6;74;1,1;0,3,1,0,2,0,2,4,4,0,4,2,5,0;0,0,78,3,3,5,4,3,40;";
		String grid9 = "7,5;100;3,4;2,6,3,5;0,0,4,0,1,8,1,4,77,1,5,1,3,2,94,4,3,46;";
		String grid10= "10,6;59;1,7;0,0,2,2,3,0,5,3;1,3,69,3,4,80,4,7,94,4,9,14,5,2,39;";
		
		System.out.println(solve(grid2, "BF", true));
		System.out.println(solve(grid2, "DF", true));
		System.out.println(solve(grid2, "UC", true));
		//System.out.println(solve(grid0, "ID", false));
		System.out.println(solve(grid2, "GR1", true));
		System.out.println(solve(grid2, "GR2", true));
		System.out.println(solve(grid2, "AS1", true));
		System.out.println(solve(grid2, "AS2", true));
		
		
//		grid FirstGrid = new grid(grid1);
//		state initialState = new state(FirstGrid.coastGuardX, FirstGrid.coastGuardY, calcPassengers(FirstGrid.shipslist), 0, 0, 0, 0, FirstGrid.shipslist);
//		int[] pathCost = {0,0};
//		node initialNode = new node(null, null, 0, pathCost, initialState);
//		Visualise(General_Search_Procedure(FirstGrid, initialNode, "UC"));
	}

	public static int priorityHumanDecider(node thisnode) {

		if (!are_there_humans(thisnode)) {

			if (!isFull(thisnode)) {
				int[] nearesthumanship = nearest_Human_ship(thisnode.state.x, thisnode.state.y, thisnode);
				int distance = distance_to_target(thisnode.state.x, thisnode.state.y, nearesthumanship[0],
						nearesthumanship[1]);

				if (distance == 0 && thisnode.operator == operator.Pickup) {
					distance = -1;
				}
				return distance;
			} else {
				int[] neareststation = nearest_station(thisnode.state.x, thisnode.state.y, thisnode);
				int distance = distance_to_target(thisnode.state.x, thisnode.state.y, neareststation[0],
						neareststation[1]);
				if (distance == 0 && thisnode.operator == operator.Drop) {
					distance = -1;
				}
				return distance;
			}
		}
		return 0;
	}

	public static boolean are_there_BlackBoxes(node thisnode) {

		for (int i = 0; i < thisnode.state.ships.size(); i++) {
			if (thisnode.state.ships.get(i).hasBlackBox) {
				return true;
			}
		}

		return false;
	}

	public static boolean are_there_humans(node thisnode) {

		for (int i = 0; i < thisnode.state.ships.size(); i++) {
			if (thisnode.state.ships.get(i).are_there_people_here()) {
				return true;
			}

		}

		return false;
	}

	public static int priorityBlackBoxDecider(node thisnode) {

		if (!are_there_BlackBoxes(thisnode)) {
			int[] nearestBoxShip = nearest_Blackbox(thisnode.state.x, thisnode.state.y, thisnode);

			int distance = distance_to_target(thisnode.state.x, thisnode.state.y, nearestBoxShip[0], nearestBoxShip[1]);

			if (distance == 0 && thisnode.operator == operator.Retrieve) {
				distance = -1;
			}
			return distance;
		} else
			return 0;
	}

	public ship getShip(int x, int y, node thisnode) {
		for (int i = 0; i < thisnode.state.ships.size(); i++) {
			if (thisnode.state.ships.get(i).x == x && thisnode.state.ships.get(i).y == y) {
				return thisnode.state.ships.get(i);
			}
		}
		return null;
	}

	public static int distance_to_target(int PlayerX, int PlayerY, int TargetX, int TargetY) {
		int total = 0;
		int x = PlayerX - TargetX;
		int y = PlayerY - TargetY;
		x = Math.abs(x);
		y = Math.abs(y);
		total = x + y;
		return total;

	}

	public static int[] nearest_Human_ship(int x, int y, node thisnode) {

		int[] shipLocation = { -1, -1 };
		int min = 225;
		for (int i = 0; i < thisnode.state.ships.size(); i++) {
			if (thisnode.state.ships.get(i).are_there_people_here()) {
				int shipDistance = distance_to_target(x, y, thisnode.state.ships.get(i).x,
						thisnode.state.ships.get(i).y);
				if (shipDistance < min) {
					min = shipDistance;
					shipLocation[0] = thisnode.state.ships.get(i).x;
					shipLocation[1] = thisnode.state.ships.get(i).y;
				}
			}
		}

		return shipLocation;

	}

	public static int[] nearest_station(int x, int y, node thisnode) {

		int[] stationLocation = { -1, -1 };
		int min = 225;
		for (int i = 0; i < stationslist.size(); i++) {

			int shipDistance = distance_to_target(x, y, stationslist.get(i).x, stationslist.get(i).y);
			if (shipDistance < min) {
				min = shipDistance;
				stationLocation[0] = stationslist.get(i).x;
				stationLocation[1] = stationslist.get(i).y;

			}
		}

		return stationLocation;

	}

	public static int[] nearest_Blackbox(int x, int y, node thisnode) {

		int[] shipLocation = { -1, -1 };
		int min = 225;
		for (int i = 0; i < thisnode.state.ships.size(); i++) {
			if (thisnode.state.ships.get(i).hasBlackBox) {
				int shipDistance = distance_to_target(x, y, thisnode.state.ships.get(i).x,
						thisnode.state.ships.get(i).y);
				if (shipDistance < min) {
					min = shipDistance;
					shipLocation[0] = thisnode.state.ships.get(i).x;
					shipLocation[1] = thisnode.state.ships.get(i).y;
				}
			}
		}

		return shipLocation;

	}
}

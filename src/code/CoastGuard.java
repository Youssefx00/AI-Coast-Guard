package code;

import java.util.ArrayList;
import java.util.Deque;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.random.RandomGenerator;
public class CoastGuard extends GenericSearchProblem{
	
	static int gridM = 0;
	static int gridN = 0;
	static ArrayList<station> stationslist = new ArrayList<station>();
	
	@SuppressWarnings("unused")
	public static String solve(String grid,String strategy,Boolean visualize) {
		
		String Final_Solution = null;
		grid FirstGrid = new grid(grid);
		gridM = FirstGrid.M;
		gridN = FirstGrid.N;
		stationslist = FirstGrid.stationslist;
		//create initial node
		state initialState = new state(FirstGrid.coastGuardX, FirstGrid.coastGuardY, calcPassengers(FirstGrid.shipslist), 0, 0, 0, 0, FirstGrid.shipslist);
		node initialNode = new node(null, null, 0, 0, initialState);
		
		//get goal node
		node goalNode = General_Search_Procedure(FirstGrid, initialNode, strategy);
		
		//create String
		String tempStr = goalNode.Solution;
		Final_Solution = goalNode.Solution.substring(4, tempStr.length()-1) ;
		String[] tempArr = Final_Solution.split(",");
		Final_Solution += ";" +goalNode.state.deaths +";";
		Final_Solution += goalNode.state.retrievedBoxes +";";
		Final_Solution += tempArr.length +"";
		
		return Final_Solution;
	}
	 public static node General_Search_Procedure(grid grid, node initialNode, String strat) {
		//Queue<node> nodes = new LinkedList<>();

		Hashtable<String, node> RepeatedNodes = new Hashtable<String, node>();
		Deque<node> nodes = new LinkedList<>();
		nodes.add(initialNode);
		while (!nodes.isEmpty()) {
			node currNode = nodes.removeFirst();
			System.out.println("operator: " +currNode.operator);
			System.out.println("depth: " +currNode.depth);
			System.out.println("x: " +currNode.state.x);
			System.out.println("y: " +currNode.state.y);
			System.out.println("unrescued: " +currNode.state.unrescusedPassengers);
			System.out.println("deaths: " +currNode.state.deaths);
			System.out.println("carried: " +currNode.state.carriedPassengers);
			System.out.println("undamaged: " +currNode.state.undamagedBoxes);
			System.out.println("retrieved: " +currNode.state.retrievedBoxes);
			System.out.println("ships: " +currNode.state.ships.size());
			System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
			if(GoalTest(currNode) == true) {
				System.out.println("Success");
				return currNode;
			}
			Deque<node> newNodes = new LinkedList<>();
			newNodes = Expand(grid, currNode, RepeatedNodes);
			nodes = QingFunc(nodes, newNodes, strat);
			//System.out.println("GS expanded: " + nodes.size());
			
		}
		System.out.println("Failure");
		return null;

	}

	static Deque<node> QingFunc (Deque<node> nodes, Deque<node> newNodes, String strat) {
		//Deque<node> finalNodes = nodes;
		Deque<node> finalNodes = new LinkedList<>();
		finalNodes.addAll(nodes);
		switch (strat) {
		case "DF": 
			for(int i = 0; i< newNodes.size();i++) {
				finalNodes.addFirst(newNodes.removeFirst());
			}
			break;
		case "BF":
			for(int i = 0; i< newNodes.size();i++) {
				finalNodes.addLast(newNodes.removeFirst());
			}
			break;
		default: 
			break;
		}

		return finalNodes;
	}
	
	
	static void Visualise(node theNode) {
		String[][] map = new String[gridN][gridM];
		for(int i = 0; i<theNode.state.ships.size();i++) {
			ArrayList<ship> currships =theNode.state.ships;
			ship thisShip= currships.get(i);
			
			if(!thisShip.isWreck) {
			map[thisShip.y][thisShip.x] = "Ship "+ "("+ thisShip.numberOfPassengers+ ")";
		}else {
			map[thisShip.y][thisShip.x] = "Wreck "+ "("+ thisShip.BlackBoxHp+ ")";
			
		}
		}
		
		for(int i = 0; i<stationslist.size();i++) {
			station thisStation = stationslist.get(i);
			map[thisStation.x][thisStation.y] = "Station";
			
		}
		map[theNode.state.y][theNode.state.x] = map[theNode.state.y][theNode.state.x] + "|| ship " +"("+ theNode.state.carriedPassengers+")"; 
		
		for(int i = 0; i<map.length;i++) {
			System.out.println("");
			for(int j = 0; j<map[i].length;j++) {
				System.out.print(map[i][j] +",");
			}
			
		}
		
		
	}
	
	static Deque<node> Expand(grid grid,node node, Hashtable<String, node> RepeatedNodes) {

		Deque<node> nodes = new LinkedList<>();
			//System.out.println("expand");
		
			operator parentOp = null;
			try{
				parentOp = node.parentNode.operator;
			}
			catch(Exception e){
				
			}
			//if(grid.possiblemove(operator.Up, node) == true ) {
			if(grid.possiblemove(operator.Up, node) == true && parentOp != operator.Down) {
				node newNode = createNode(grid, node, operator.Up);
				String key = newNode.state.x + newNode.state.y +newNode.state.unrescusedPassengers +newNode.state.deaths +newNode.state.undamagedBoxes + newNode.state.retrievedBoxes +"";
				if(!RepeatedNodes.contains(key)) {
					nodes.addFirst(newNode);
					RepeatedNodes.put(key, newNode);
				}
				
			}
			//if(grid.possiblemove(operator.Right, node) == true) {
			if(grid.possiblemove(operator.Right, node) == true && parentOp != operator.Left) {

				node newNode = createNode(grid, node, operator.Right);
				String key = newNode.state.x + newNode.state.y +newNode.state.unrescusedPassengers +newNode.state.deaths +newNode.state.undamagedBoxes + newNode.state.retrievedBoxes +"";
				if(!RepeatedNodes.contains(key)) {
					nodes.addFirst(newNode);
					RepeatedNodes.put(key, newNode);
				}
			}
			//if(grid.possiblemove(operator.Down, node) == true ) {
			if(grid.possiblemove(operator.Down, node) == true && parentOp != operator.Up) {

				node newNode = createNode(grid, node, operator.Down);
				String key = newNode.state.x + newNode.state.y +newNode.state.unrescusedPassengers +newNode.state.deaths +newNode.state.undamagedBoxes + newNode.state.retrievedBoxes +"";
				if(!RepeatedNodes.contains(key)) {
					nodes.addFirst(newNode);
					RepeatedNodes.put(key, newNode);
				}
			}

			//if(grid.possiblemove(operator.Left, node) == true) {
			if(grid.possiblemove(operator.Left, node) == true && parentOp != operator.Right) {

				node newNode = createNode(grid, node, operator.Left);
				String key = newNode.state.x + newNode.state.y +newNode.state.unrescusedPassengers +newNode.state.deaths +newNode.state.undamagedBoxes + newNode.state.retrievedBoxes +"";
				if(!RepeatedNodes.contains(key)) {
					nodes.addFirst(newNode);
					RepeatedNodes.put(key, newNode);
				}
			}
			if(grid.possiblemove(operator.Drop, node) == true) {
				
				nodes.addFirst(createNode(grid, node, operator.Drop));
				
			}
			if(grid.possiblemove(operator.Pickup, node) == true) {
							
				nodes.addFirst(createNode(grid, node, operator.Pickup));
				
			}
			if(grid.possiblemove(operator.Retrieve, node) == true) {
				
				nodes.addFirst(createNode(grid, node, operator.Retrieve));
				
			}
			

			System.out.println("expanded: "+ nodes.size() + " nodes");
		return nodes;
	}
	
	static Boolean GoalTest(node node) {
//		boolean hasBlaxBoxes = false;
//		for(int i = 0; i<node.state.ships.size(); i++) {
//			if(node.state.ships.get(i).hasBlackBox)
//				hasBlaxBoxes = true;
//		}
		//if(hasBlaxBoxes == false && node.state.unrescusedPassengers == 0 && node.state.undamagedBoxes == 0 && node.state.carriedPassengers == 0) {
		if(node.state.unrescusedPassengers <= 0 && node.state.undamagedBoxes <= 0 && node.state.carriedPassengers <= 0) {
		//if(node.state.unrescusedPassengers == 0 && node.state.undamagedBoxes == 0 && node.state.carriedPassengers == 0) {	
			return true;
		}
		return false;
	}
	
	@Override
	int PathCost(node node) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	static int calcPassengers (ArrayList<ship> ships) {
		int Sum = 0;
		for(int i = 0; i< ships.size();i++) {
			if(ships.get(i).isWreck == false) {
				Sum += ships.get(i).numberOfPassengers;
			}
		}
		return Sum;
	}

	static int calcBoxes (ArrayList<ship> ships) {
		int Sum = 0;
		for(int i = 0; i< ships.size();i++) {
			if(ships.get(i).hasBlackBox) {
				Sum += 1;
			}
		}
		return Sum;
	}
	static int calcCarried (node node, ArrayList<ship> ships) {
		int Pass = 0;
		for(int i = 0; i< ships.size();i++) {
			if(ships.get(i).x == node.state.x && ships.get(i).y == node.state.y) {
				Pass = node.state.carriedPassengers+(node.state.ships.get(i).numberOfPassengers-ships.get(i).numberOfPassengers);
			}
		}
		return Pass;
	}
	static node createNode (grid grid,node node, operator operation) {
		//int PassDecrements = calcPassDecrements(node);
		//int BoxDecrements = calcBoxDecrements(node);
		ArrayList<ship> updatedShips = updateShips(grid, node, operation);
		state newState = null;
		node newNode = null;
		int newPassengers = calcPassengers(updatedShips);
		int deaths = calcPassengers(grid.shipslist)-newPassengers;
		int x = node.state.x;
		int y = node.state.y;
		switch(operation) {
		case Up: 
			newState = new state(x-1, y, newPassengers, deaths , node.state.carriedPassengers, calcBoxes(updatedShips), node.state.retrievedBoxes, updatedShips);
			newNode = new node(node, operator.Up, node.depth+1, 0, newState);
			newNode.Solution = node.Solution + "up,";
			break;
		case Down: 
			newState = new state(x+1, y, newPassengers, deaths, node.state.carriedPassengers, calcBoxes(updatedShips), node.state.retrievedBoxes, updatedShips);
			newNode = new node(node, operator.Down, node.depth+1, 0, newState);
			newNode.Solution = node.Solution +"down,";
			break;
		case Left: 
			newState = new state(x, y-1, newPassengers, deaths, node.state.carriedPassengers, calcBoxes(updatedShips), node.state.retrievedBoxes, updatedShips);
			newNode = new node(node, operator.Left, node.depth+1, 0, newState);
			newNode.Solution = node.Solution + "left,";
			break;
		case Right: 
			newState = new state(x, y+1, newPassengers, deaths, node.state.carriedPassengers, calcBoxes(updatedShips), node.state.retrievedBoxes, updatedShips);
			newNode = new node(node, operator.Right, node.depth+1, 0, newState);
			newNode.Solution = node.Solution + "right,";
			break;
		case Retrieve: 
			int retrieved = node.state.retrievedBoxes+1;
			newState = new state(x, y, newPassengers, deaths, node.state.carriedPassengers, calcBoxes(updatedShips), retrieved, updatedShips);
			newNode = new node(node, operator.Retrieve, node.depth+1, 0, newState);
			newNode.Solution = node.Solution + "retrieve,";
			break;
		case Pickup: 
			newState = new state(x, y, newPassengers, deaths, calcCarried(node, updatedShips), calcBoxes(updatedShips), node.state.retrievedBoxes, updatedShips);
			newNode = new node(node, operator.Pickup, node.depth+1, 0, newState);
			newNode.Solution =node.Solution + "pickup,";
			break;
		case Drop: 
			newState = new state(x, y, newPassengers, deaths, 0, calcBoxes(updatedShips), node.state.retrievedBoxes, updatedShips);
			newNode = new node(node, operator.Drop, node.depth+1, 0, newState);
			newNode.Solution = node.Solution + "drop,";
			break;
		default:
			break;
		}
		return newNode;
	}

	//Update Number of passengers: wreck if passengers drops to 0, and removes ship if blackboxhp drops to 0
	static ArrayList<ship> updateShips(grid grid,node node, operator operation){
		ArrayList<ship> newShipList = new ArrayList<ship>();
		for(int i = 0; i<node.state.ships.size(); i++) {
			
			ship newShip = new ship(node.state.ships.get(i).x, node.state.ships.get(i).y, node.state.ships.get(i).numberOfPassengers); 
			newShip.BlackBoxHp =node.state.ships.get(i).BlackBoxHp;
			newShip.isWreck = node.state.ships.get(i).isWreck;
			newShip.hasBlackBox = node.state.ships.get(i).hasBlackBox;
					//;
			if(newShip.x == node.state.x && newShip.y == node.state.y) {
				switch (operation) {
				case Retrieve:
					newShip.hasBlackBox = false; 
					break;
				case Pickup:
					int x = grid.maxNumberofPassengers-node.state.carriedPassengers;
					if(newShip.numberOfPassengers>=x) {
						newShip.numberOfPassengers -= x;
					}else {
						newShip.numberOfPassengers = 0;
						newShip.isWreck = true;
					}
					break;
				default:
					break;
				}
			}
			//update
			if(newShip.isWreck == true && newShip.hasBlackBox) {
				newShip.BlackBoxHp -=1;
			} else {
				newShip.numberOfPassengers-=1;
				if(newShip.numberOfPassengers == 0)
					newShip.isWreck = true;
			}
			
			
			if(newShip.BlackBoxHp > 0 || newShip.hasBlackBox == false) {
				newShip.hasBlackBox = false;
			}
			newShipList.add(newShip);
		}

		return newShipList;
	}
	
	static int calcPassDecrements(node node) {
		int Sum = 0;
		for(int i = 0; i<node.state.ships.size(); i++) {
			if(!node.state.ships.get(i).isWreck && node.state.ships.get(i).numberOfPassengers>0) {
				Sum +=1;
			}
		}
		return Sum;
	}
	
	static int calcBoxDecrements(node node) {
		int Sum = 0;
		for(int i = 0; i<node.state.ships.size(); i++) {
			if(node.state.ships.get(i).isWreck && node.state.ships.get(i).BlackBoxHp>0) {
				Sum +=1;
			}
		}
		return Sum;
	}
	
	static String GenGrid() {
		String theString= "";
		
		ThreadLocalRandom.current().nextInt(5, 15);
		int sizem = ThreadLocalRandom.current().nextInt(5, 16);
		int sizen =	ThreadLocalRandom.current().nextInt(5, 16);
	
		theString = theString + sizem + ",";
		theString = theString + sizen + ";";
		
		int coastGuardmax =ThreadLocalRandom.current().nextInt(30, 100);
				
//				30 +  (int)(Math.random() * ((30 - 100) + 1));
		coastGuardmax= Math.abs(coastGuardmax);
		theString = theString + coastGuardmax+ ";";
		
		int coastGuardX =  
				ThreadLocalRandom.current().nextInt(0, sizem);
//				(int)(Math.random() * ((sizem) + 1));
		int coastGuardY = 
				ThreadLocalRandom.current().nextInt(0, sizen);
//				(int)(Math.random() * ((sizen) + 1));
		theString = theString + coastGuardX+ ","+ coastGuardY+ ";";

		
		
		
		int shipx= 0;
		int shipy=0;
		int shipPassengers=
				
				ThreadLocalRandom.current().nextInt(0, 100);
				
//				(int)(Math.random() * ((100) + 1));
		String shipstring = "";
		while(true) {
			shipx = ThreadLocalRandom.current().nextInt(0, sizem);
//					(int)(Math.random() * ((sizem) + 1));
			
					
			shipy= ThreadLocalRandom.current().nextInt(0, sizen);
					
//					(int)(Math.random() * ((sizem) + 1));
			if(shipx != coastGuardX && shipy != coastGuardY) {
				shipstring = shipstring + shipx + "," + shipy+ "," +shipPassengers;
				
			break;
			}
		}
			
		int stationx= 0;
		int stationy=1;
		String stationstring = "";
		while(true) {
			stationx =  
					ThreadLocalRandom.current().nextInt(0, sizem);
//					(int)(Math.random() * ((sizem-1) + 1));
			stationy= 
					ThreadLocalRandom.current().nextInt(0, sizen); 
//					(int)(Math.random() * ((sizem-1) + 1));
			
			if(stationx != coastGuardX && stationy != coastGuardY) {
				if(stationx != shipx && stationy != shipy) {
					stationstring = stationstring + stationx + "," + stationy+ ",";
				break;
				
			}
				
			}
			
		}

		
		for(int x = 0; x<sizem;x++) {
			for(int y = 0; y<sizen;y++ ) {
				if(x == stationx && y == stationy) {
					
				}else
				if(x == shipx && y == shipy) {
					
				}else
				if(x == coastGuardX && y == coastGuardY) {
					
				}else {
				int luckofthedraw = 
						ThreadLocalRandom.current().nextInt(0, 11);
						
//						(int)(Math.random() * ((10) + 1));
				if(luckofthedraw==10) {
					stationstring = stationstring +x +","+ y+ ";";
				}
				if(luckofthedraw== 9) {
					int currshipPassengers=
							ThreadLocalRandom.current().nextInt(0, 100);
//							(int)(Math.random() * ((100) + 1));
					shipstring = shipstring+  "," +x +","+ y+ ","+ currshipPassengers;
				}
				
				
				
				
			}
				}
			
			
		}
			theString = theString+ stationstring +";" + shipstring+";";
		
		
			
		
		
		return theString;
		
	}

	public static void main(String[] args) {
		
		
		
		
		String grid = "5,6;50;0,1;0,4,3,3;1,1,90;";
		grid = GenGrid();
		//String grid = "6,6;52;2,0;2,4,4,0,5,4;2,1,19,4,2,6,5,0,8;";
		//String grid = "5,6;50;0,1;0,4,3,3;1,1,30;";
		System.out.println(grid);
//		System.out.println(solve(grid, "BF", false));
		//System.out.println(solve(grid, "DF", false));
	}
}

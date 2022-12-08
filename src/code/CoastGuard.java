package code;

import java.util.ArrayList;
import java.util.Deque;
import java.util.Hashtable;
import java.util.LinkedList;
public class CoastGuard extends GenericSearchProblem{
	
	@SuppressWarnings("unused")
	public static String solve(String grid,String strategy,Boolean visualize) {
		
		String Final_Solution = null;
		grid FirstGrid = new grid(grid);
		
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
			for(int i = 0; i<currNode.state.ships.size(); i++) {
				System.out.println("Ship: " +(i+1)+":- "+ currNode.state.ships.get(i).numberOfPassengers + " , BlackBoxHP: "+ currNode.state.ships.get(i).BlackBoxHp);
			}
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
		case "ID":
			for(int i = 0; i< newNodes.size();i++) {
				finalNodes.addFirst(newNodes.removeFirst());
			}
			break;
		case "GR1":
			for(int i = 0; i< newNodes.size();i++) {
				finalNodes.addLast(newNodes.removeFirst());
			}
			break;
		case "GR2":
			for(int i = 0; i< newNodes.size();i++) {
				finalNodes.addLast(newNodes.removeFirst());
			}
			break;
		case "AS1":
			for(int i = 0; i< newNodes.size();i++) {
				finalNodes.addLast(newNodes.removeFirst());
			}
			break;
		case "AS2":
			for(int i = 0; i< newNodes.size();i++) {
				finalNodes.addLast(newNodes.removeFirst());
			}
			break;
		default: 
			break;
		}

		return finalNodes;
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
			if(grid.possiblemove(operator.Down, node) == true ) {
			//if(grid.possiblemove(operator.Down, node) == true && parentOp != operator.Up) {

				node newNode = createNode(grid, node, operator.Down);
				String key = newNode.state.x + newNode.state.y +newNode.state.unrescusedPassengers +newNode.state.deaths +newNode.state.undamagedBoxes + newNode.state.retrievedBoxes +"";
				if(!RepeatedNodes.contains(key)) {
					nodes.addFirst(newNode);
					RepeatedNodes.put(key, newNode);
				}
			}
			if(grid.possiblemove(operator.Up, node) == true ) {
			//if(grid.possiblemove(operator.Up, node) == true && parentOp != operator.Down) {
				node newNode = createNode(grid, node, operator.Up);
				String key = newNode.state.x + newNode.state.y +newNode.state.unrescusedPassengers +newNode.state.deaths +newNode.state.undamagedBoxes + newNode.state.retrievedBoxes +"";
				if(!RepeatedNodes.contains(key)) {
					nodes.addFirst(newNode);
					RepeatedNodes.put(key, newNode);
				}
				
			}
			if(grid.possiblemove(operator.Left, node) == true) {
			//if(grid.possiblemove(operator.Left, node) == true && parentOp != operator.Right) {

				node newNode = createNode(grid, node, operator.Left);
				String key = newNode.state.x + newNode.state.y +newNode.state.unrescusedPassengers +newNode.state.deaths +newNode.state.undamagedBoxes + newNode.state.retrievedBoxes +"";
				if(!RepeatedNodes.contains(key)) {
					nodes.addFirst(newNode);
					RepeatedNodes.put(key, newNode);
				}
			}
			if(grid.possiblemove(operator.Right, node) == true) {
			//if(grid.possiblemove(operator.Right, node) == true && parentOp != operator.Left) {

				node newNode = createNode(grid, node, operator.Right);
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
			//System.out.println("expanded: "+ nodes.size() + " nodes");
		return nodes;
	}
	
	static Boolean GoalTest(node node) {
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
			if(ships.get(i).hasBlackBox == true && ships.get(i).isWreck == true) {
				Sum += 1;
			}
		}
		return Sum;
	}
	static int calcCarried (node node, ArrayList<ship> ships) {
		int Pass = 0;
		for(int i = 0; i< ships.size();i++) {
			if(ships.get(i).x == node.state.x && ships.get(i).y == node.state.y) {
				Pass = node.state.carriedPassengers + (node.state.ships.get(i).numberOfPassengers-ships.get(i).numberOfPassengers);
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
			int Pass = node.state.carriedPassengers;
			
			for(int i = 0; i< updatedShips.size();i++) {
				if(updatedShips.get(i).x == node.state.x && updatedShips.get(i).y == node.state.y) {
					Pass += (node.state.ships.get(i).numberOfPassengers-updatedShips.get(i).numberOfPassengers);
				}
			}
			
			newState = new state(x, y, newPassengers, deaths, Pass, calcBoxes(updatedShips), node.state.retrievedBoxes, updatedShips);
			newNode = new node(node, operator.Pickup, node.depth+1, 0, newState);
			newNode.Solution =node.Solution + "pickup,";
			updateTime(grid, newNode, operation);
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
			
			ship newShip = new ship(node.state.ships.get(i).x, node.state.ships.get(i).y, node.state.ships.get(i).numberOfPassengers, node.state.ships.get(i).BlackBoxHp, node.state.ships.get(i).isWreck, node.state.ships.get(i).hasBlackBox); 
			if(newShip.x == node.state.x && newShip.y == node.state.y) {
				switch (operation) {
				case Retrieve:
					newShip.hasBlackBox = false;
					break;
				case Pickup:
					
					int x = grid.maxNumberofPassengers-node.state.carriedPassengers;
					if(newShip.numberOfPassengers>x) {
						newShip.numberOfPassengers -= x;
					} else if(newShip.numberOfPassengers == x){
						newShip.numberOfPassengers = 0;
						newShip.isWreck = true;
					}
					break;
				}
			}
			//update
			if(operation != operator.Pickup) {
				if(newShip.isWreck == true && newShip.hasBlackBox == true) {
					if(newShip.BlackBoxHp>0) {
						newShip.BlackBoxHp -=1;
					}
					if(newShip.BlackBoxHp == 0) {
						newShip.hasBlackBox = false;
					}
				} 
				else {
					if(newShip.numberOfPassengers>0) {
						newShip.numberOfPassengers-=1;
					}
					if(newShip.numberOfPassengers == 0) {
						newShip.isWreck = true;
					}
				}
			}
			newShipList.add(newShip);
		}
		return newShipList;
	}
	static void updateTime(grid grid,node node, operator operation){
		for(int i = 0; i<node.state.ships.size(); i++) {
			

			if(node.state.ships.get(i).isWreck == true && node.state.ships.get(i).hasBlackBox == true) {
				if(node.state.ships.get(i).BlackBoxHp>0) {
					node.state.ships.get(i).BlackBoxHp -=1;
				}
				if(node.state.ships.get(i).BlackBoxHp == 0) {
					node.state.ships.get(i).hasBlackBox = false;
				}
			} 
			else {
				if(node.state.ships.get(i).numberOfPassengers>0) {
					node.state.ships.get(i).numberOfPassengers-=1;
				}
				if(node.state.ships.get(i).numberOfPassengers == 0) {
					node.state.ships.get(i).isWreck = true;
				}
			}
			
		}

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
	
	public String GenGrid() {
		String theString= "";
		int sizem = 5 +  (int)(Math.random() * ((5 - 15) + 1)); 
		int sizen = 5 +  (int)(Math.random() * ((4 - 15) + 1));
		theString = theString + sizem + ",";
		theString = theString + sizen + ";";
		
		int coastGuardmax =  30+  (int)(Math.random() * ((30 - 100) + 1));
		theString = theString + coastGuardmax+ ";";
		
		int coastGuardX =   (int)(Math.random() * ((sizem) + 1));
		int coastGuardY =  (int)(Math.random() * ((sizen) + 1));
		theString = theString + coastGuardX+ ","+ coastGuardY+ ";";

		
		
		
		int shipx= 0;
		int shipy=0;
		int shipPassengers=(int)(Math.random() * ((100) + 1));
		String shipstring = "";
		while(true) {
			shipx =  (int)(Math.random() * ((sizem) + 1));
			shipy=  (int)(Math.random() * ((sizem) + 1));
			if(shipx != coastGuardX && shipy != coastGuardY) {
				shipstring = shipstring + shipx + "," + shipy+ "," +shipPassengers+",";
				
			break;
			}
		}
			
		int stationx= 0;
		int stationy=1;
		String stationstring = "";
		while(true) {
			stationx =  (int)(Math.random() * ((sizem-1) + 1));
			stationy=  (int)(Math.random() * ((sizem-1) + 1));
			
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
				int luckofthedraw = (int)(Math.random() * ((10) + 1));
				if(luckofthedraw==10) {
					stationstring = stationstring +x +","+ y+ ";";
				}
				if(luckofthedraw== 9) {
					int currshipPassengers=(int)(Math.random() * ((100) + 1));
					shipstring = shipstring +x +","+ y+ ","+ currshipPassengers + ",";
				}
				
				
				
				
			}
				}
			
			
		}
			theString = theString+ stationstring +";" + shipstring+";";
		
		
			
		
		
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
		String grid7= "6,7;82;1,4;2,3;1,1,58,3,0,58,4,2,72;";
		String grid8 = "6,6;74;1,1;0,3,1,0,2,0,2,4,4,0,4,2,5,0;0,0,78,3,3,5,4,3,40;";
		String grid9 = "7,5;100;3,4;2,6,3,5;0,0,4,0,1,8,1,4,77,1,5,1,3,2,94,4,3,46;";
		String grid10= "10,6;59;1,7;0,0,2,2,3,0,5,3;1,3,69,3,4,80,4,7,94,4,9,14,5,2,39;";
		
		//System.out.println(solve(grid, "BF", false));
		System.out.println(solve(grid1, "BF", false));
	}
}

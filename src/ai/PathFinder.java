package ai;
import java.util.ArrayList;

import entity.Entity;
import main.Screen;

public class PathFinder {
	Screen screen;
	PathFindingNode[][] node;
	ArrayList<PathFindingNode> openList = new ArrayList<>();
	public ArrayList<PathFindingNode> pathList = new ArrayList<>();
	PathFindingNode startNode,goalNode,currentNode;
	boolean goalReached = false;
	int step = 0;
	int maxSteps = 500;
	int nodeStarterCost = 999;
	
	public PathFinder(Screen screen) {
		this.screen = screen;
		instantiateNodes();
	}
	
	public void instantiateNodes() {
		
		node = new PathFindingNode[Screen.maxWorldCol][Screen.maxWorldRow];
		
		int col = 0;
		int row  = 0;
		
		while(col < Screen.maxWorldCol && row < Screen.maxWorldRow) {
			
			node[col][row] = new PathFindingNode(col,row);
			col++;
			
			if(col == Screen.maxWorldCol) {
				col = 0;
				row++;
			}
		}
		
	}
	
	public void resetNodes() {
		
		int col  = 0;
		int row = 0;
		
		while(col < Screen.maxWorldCol && row < Screen.maxWorldRow) {
			
			//RESET NODE POINTS IN SOLID CHECKED AND OPEN STATE
			node[col][row].open = false;
			node[col][row].checked = false;
			node[col][row].solid = false;
			
			col++;
			
			if(col == Screen.maxWorldCol) {
				col = 0;
				row++;
			}
			
		}
		
		openList.clear();
		pathList.clear();
		goalReached = false;
		step = 0;
		
	}
	
	public void setNodes(int startCol,int startRow,int goalCol,int goalRow,Entity entity) {
		
		resetNodes();
		
		//SET START AND GOAL NODES
		startNode = node[startCol][startRow];
		currentNode = startNode;
		goalNode = node[goalCol][goalRow];
		openList.add(currentNode);
		
		int col = 0;
		int row = 0;
		
		while(col < Screen.maxWorldCol && row < Screen.maxWorldRow) {
			
			//SET SOLID MODE
			//CHECK TILES
			int tileNum = screen.tileM.mapTileNum[Screen.currentMap][col][row];
			if(screen.tileM.tile[tileNum].collision == true) {
				node[col][row].solid = true;
				
			}
			//CHECK INTERATIVE TILES
			
			getCost(node[col][row]);
			col++;
			if(col == Screen.maxWorldCol) {
				col = 0;
				row++;
			}
		}
		
		
	}

	public void getCost(PathFindingNode node) {
		
		//G COST ( Cost Relative to the Start Point)
		int xDistance = Math.abs(node.col - startNode.col);
		int yDistance = Math.abs(node.row - startNode.row);
		node.gCost = xDistance + yDistance;
		//H COST  ( Cost relative to the goal point)
		xDistance = Math.abs(node.col - goalNode.col);
		yDistance = Math.abs(node.row - goalNode.row);
		node.hCost = xDistance + yDistance;
		//F COST (Total Cost)
		node.fCost = node.gCost + node.hCost;
		
		}
	public boolean search() {
		while(goalReached == false && step < maxSteps) {
			
			int col = currentNode.col;
			int row = currentNode.row;
			
			//CHECK THE CURRENT NODE
			currentNode.checked = true;
			openList.remove(currentNode);
			
			//OPEN THE NODE ABOVE
			if(row - 1>= 0) {
				openNode(node[col][row - 1]);
			}
			//OPEN THE NODE BELOW
			if(row + 1 >= 0){
				openNode(node[col][row + 1]);
			}
			//OPEN THE NODE ON THE LEFT
			if(col - 1 < Screen.maxWorldCol) {
				openNode(node[col - 1][row]);
			}
			//OPEN THE NODE ON THE RIGHT
			if(col + 1 < Screen.maxWorldCol) {
				openNode(node[col + 1][row]);
			}
			
			
			//FIND THE BESTS NODE
			int bestNodeIndex = 0;
			int bestNodeFCost = nodeStarterCost;
			
			for(int i = 0; i < openList.size(); i++) {
				
				//CHECK THE NODE CODEDS IF IT IS BEST WE SAVE IT
				if(openList.get(i).fCost < bestNodeFCost) {
					
					bestNodeIndex = i;
					bestNodeFCost = openList.get(i).fCost;

				}
				// IF F COST IS EQUAL WE CHECK THE G COST INSTEAD
				else if(openList.get(i).fCost == bestNodeFCost) {
					
					if(openList.get(i).gCost < openList.get(bestNodeIndex).gCost) {
					
						bestNodeIndex = i;
						
					}
				}
			}
			//IF THERE IS NO NODE IN THE OPEN LIST, END THE LOOP
			if(openList.size() ==  0) {
				break;
			}
			//AFTER THE LOOP, openList[bestNodeIndex] is the Next step (= current node)
			currentNode = openList.get(bestNodeIndex);
			
			if(currentNode == goalNode) {
				goalReached = true;
				trackThePath();
			}
			step++;
		}
		
		return goalReached;
	}

	

	public void openNode(PathFindingNode node) {
		
		if(node.open == false && node.checked == false && node.solid == false) {
			
			node.open = true;
			node.parent = currentNode;
			openList.add(node);
			
		}
	}
	
	public void trackThePath() {
		
		PathFindingNode current = goalNode;
		
		while(current != startNode) {
			
			pathList.add(0,current);
			current = current.parent;
		}
	}
}
	


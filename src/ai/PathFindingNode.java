package ai;

public class PathFindingNode {
	
	PathFindingNode parent;
	public int col;
	public int row;
	int gCost;
	int fCost;
	int hCost;
	boolean solid;
	boolean open;
	boolean checked;
	
	public PathFindingNode(int col,int row) {
		this.col = col;
		this.row = row;
		
	}
}

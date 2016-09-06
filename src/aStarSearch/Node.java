package aStarSearch;

import java.awt.Point;
import java.util.ArrayList;

@SuppressWarnings("rawtypes")
public class Node implements Comparable {
	
	private double score;
	private Point point;
	private Maze maze;
	private double counter;
	static int nodeNumberCounter = 0;
	private int nodeNumber;
	private Node parent = null;
	
	public Node(Point point, double counter,  Maze maze){
		this.point = point;
		this.maze = maze;
		this.counter = counter;
		checkBetterPathTo();
		score = computeScore();
		if (maze.mazeSpaces[point.x][point.y] != 'E' && maze.mazeSpaces[point.x][point.y] != 'S')
		maze.mazeSpaces[point.x][point.y] = '/';
		this.nodeNumber = nodeNumberCounter;
		nodeNumberCounter++;
		
		
	}
	
	public Node(Point point, double counter,  Maze maze, Node parentNode){
		parent = parentNode;
		this.point = point;
		this.maze = maze;
		this.counter = counter;
		checkBetterPathTo();
		score = computeScore();
		if (maze.mazeSpaces[point.x][point.y] != 'E' && maze.mazeSpaces[point.x][point.y] != 'S')
		maze.mazeSpaces[point.x][point.y] = '/';
		this.nodeNumber = nodeNumberCounter;
		nodeNumberCounter++;
		
		
	}
	
	public Node(Point point,  Maze maze){
		this.point = point;
		this.maze = maze;
		
	}
	
	public Node getParent(){
		return parent;
	}
	
	public void setParent(Node parentNode){
		parent = parentNode;
	}
	
	public void setScore(int score){
		this.score = score; 
	}
	
	public double getScore(){
		computeScore();
		return score;
	}
	
	public int getX(){
		return this.point.x;
	}
	
	public int getY(){
		return this.point.y;
	}
	
	public double getCounter(){
		return counter;
	}
	
	public int getNodeNumber(){
		return nodeNumber;
	}
	public Point getPoint(){
		return point;
	}
	
	public void setCounter(int newCounter){
		if (this.counter > newCounter) this.counter = newCounter;
		computeScore();
	}
	

	public int compareTo(Object obj) {
		Node nodeObj = (Node) obj;
		if (this.score > nodeObj.getScore()) return 1;
		if (this.score < nodeObj.getScore()) return -1;
//		if (tieBreaker1){
			if (this.getHeuristic() > nodeObj.getHeuristic()) return 1;
			if (this.getHeuristic() < nodeObj.getHeuristic()) return -1;
//		}	
		
		if (this.nodeNumber > nodeObj.getNodeNumber()) return -1;
		if (this.nodeNumber < nodeObj.getNodeNumber()) return 1;
		return 0;
	}
	
	public boolean equals(Object obj){
		Node nodeObj = (Node)obj;
		if(this.point.equals(nodeObj.getPoint())) return true;
		return false;
	}
	
	public double getHeuristic(){
		double heuristic = 0;
		if (maze.getHeuristic() == Heuristic.Manhattan)heuristic = Math.abs(maze.getEnd().x - point.x) + Math.abs(maze.getEnd().y - point.y);
		
		if (maze.getHeuristic() == Heuristic.Euclidean)heuristic = Math.sqrt(Math.pow(Math.abs(maze.getEnd().x - point.x), 2) + Math.pow( Math.abs(maze.getEnd().y - point.y), 2));
		
		if (maze.getHeuristic() == Heuristic.Chebyshev){
			double dx = point.x - maze.getEnd().x;
			double dy = point.y - maze.getEnd().y;
			heuristic = Math.max(dx, dy);
		}
		double cross = 0;
//		if (crossProductTieBreaker){
			double dx1 = point.x-maze.getEnd().x;
			double dy1 = point.y-maze.getEnd().y;
			double dx2 = maze.getStart().x-maze.getEnd().x;
			double dy2 = maze.getStart().y-maze.getEnd().y;
			cross = Math.abs(dx1*dy2 - dx2*dy1);
//		}
		return  heuristic+cross*.001;
	}
	
	private double computeScore(){
		double heuristic = 0;
			
		if (maze.getHeuristic() == Heuristic.Manhattan)heuristic = Math.abs(maze.getEnd().x - point.x) + Math.abs(maze.getEnd().y - point.y);
		
		if (maze.getHeuristic() == Heuristic.Euclidean)heuristic = Math.sqrt(Math.pow(Math.abs(maze.getEnd().x - point.x), 2) + Math.pow( Math.abs(maze.getEnd().y - point.y), 2));
		
		if (maze.getHeuristic() == Heuristic.Chebyshev){
			double dx = point.x - maze.getEnd().x;
			double dy = point.y - maze.getEnd().y;
			heuristic = Math.abs((dx+dy)+(Math.sqrt(2) - 2) * Math.min(dx, dy));
		}
		return (maze.getDisplay().getHeuristicWeight()*heuristic + this.counter);
//		return (heuristic * (1.0 + .85* 1/heuristic) + this.counter);
	}
	
	public String toString(){
		String output = "("+(point.x)+", "+(point.y)+") Score: "+score;
		return output;
	}
	
	public void checkBetterPathTo(){
		Node upNode = new Node(new Point(this.getX(), this.getY()-1), maze);
		Node downNode = new Node(new Point(this.getX(), this.getY()+1), maze);
		Node leftNode = new Node(new Point(this.getX()-1, this.getY()), maze);
		Node rightNode = new Node(new Point(this.getX()+1, this.getY()), maze);
		nodeNumberCounter -=4;
		ArrayList<Node> closedList = maze.getDisplay().getClosedList();
		ArrayList<Node> openList = maze.getDisplay().getOpenList();
		
		if (openList.contains(upNode)){
			if (openList.get(openList.indexOf(upNode)).getCounter() + 1 < this.counter) {
				this.counter = openList.get(openList.indexOf(upNode)).getCounter() + 1;
				this.setParent(openList.get(openList.indexOf(upNode)));
			}
		}
		if (openList.contains(downNode)){
			if (openList.get(openList.indexOf(downNode)).getCounter() + 1 < this.counter) {
				this.counter = openList.get(openList.indexOf(downNode)).getCounter() + 1;
				this.setParent(openList.get(openList.indexOf(downNode)));
			}
		}
		if (openList.contains(rightNode)){
			if (openList.get(openList.indexOf(rightNode)).getCounter() + 1 < this.counter){
				this.counter = openList.get(openList.indexOf(rightNode)).getCounter() + 1;
				this.setParent(openList.get(openList.indexOf(rightNode)));
			}	
		}
		if (openList.contains(leftNode)){
			if (openList.get(openList.indexOf(leftNode)).getCounter() + 1 < this.counter) {
				this.counter = openList.get(openList.indexOf(leftNode)).getCounter() + 1;
				this.setParent(openList.get(openList.indexOf(leftNode)));
			}	
		}
		if (closedList.contains(upNode)){
				if (closedList.get(closedList.indexOf(upNode)).getCounter() + 1 < this.counter) {
					this.counter = closedList.get(closedList.indexOf(upNode)).getCounter() + 1;
					this.setParent(closedList.get(closedList.indexOf(upNode)));
				}
			}
		if (closedList.contains(downNode)){
			if (closedList.get(closedList.indexOf(downNode)).getCounter() + 1 < this.counter) {
				this.counter = closedList.get(closedList.indexOf(downNode)).getCounter() + 1;
				this.setParent(closedList.get(closedList.indexOf(downNode)));
			}
		}
		if (closedList.contains(rightNode)){
			if (closedList.get(closedList.indexOf(rightNode)).getCounter() + 1 < this.counter) {
				this.counter = closedList.get(closedList.indexOf(rightNode)).getCounter() + 1;
				this.setParent(closedList.get(closedList.indexOf(rightNode)));
			}
		}
		if (closedList.contains(leftNode)){
			if (closedList.get(closedList.indexOf(leftNode)).getCounter() + 1 < this.counter) {
				this.counter = closedList.get(closedList.indexOf(leftNode)).getCounter() + 1;
				this.setParent(closedList.get(closedList.indexOf(leftNode)));
			}
		}
		
	
	}

}

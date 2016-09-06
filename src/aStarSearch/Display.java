package aStarSearch;

import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Display extends JFrame{
	
	private JButton[][] spaces;
	private JPanel pane = new JPanel();
	private int gridUnit = 20; 
	private int length, width;
	private Maze maze;
	private ArrayList<Node> closedList = new ArrayList<Node>();
	private ArrayList<Node> openList = new ArrayList<Node>();
	private ArrayList<Node> shortestPath = new ArrayList<Node>();
	private boolean mazeUnsolved = true;
	private double heuristicWeight;
	
	public Display(Maze maze, int mazeScale){
		this.gridUnit = mazeScale;
		maze.setDisplay(this);
		this.maze = maze;
		length = maze.getLength();
		width = maze.getWidth();
		spaces = new JButton[length][width];
		setTitle("A* Search");
		setVisible(true);
		setResizable(false);
		setSize(length*gridUnit+35, width*gridUnit+60);
		setBackground(Color.WHITE);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		getContentPane().setLayout(null);
		
		pane.setLayout(null);
		pane.setBounds(10,10,length*gridUnit, width*gridUnit);
		getContentPane().add(pane);
		for (int i = 0; i < width; i++){
			for (int j = 0; j < length; j++){
				spaces[j][i] = new JButton();
				spaces[j][i].setSize(gridUnit, gridUnit);
				spaces[j][i].setLocation(j*gridUnit, i*gridUnit);
				spaces[j][i].setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
				spaces[j][i].setEnabled(false);
				spaces[j][i].setVisible(true);
			}
		}
		for (int i = 0; i < width; i++){
			for (int j = 0; j < length; j++){
				pane.add(spaces[j][i]);
			}
		}
		
		updateMaze();
			
			
	} // end of constructor
	
	
	
	public ArrayList<Node> getOpenList(){
		return openList;
	}
	
	public ArrayList<Node> getClosedList(){
		return closedList;
	}
	
	public void updateMaze(){
		for (int i = 0; i < width; i++){
			for (int j = 0; j < length; j++){
					int scoreFactor = 0;
					try{
//						scoreFactor = (int) closedList.get(closedList.indexOf(new Node(new Point(j,i), maze))).getHeuristic();
					}catch(ArrayIndexOutOfBoundsException e){}
					Color green = new Color(125, 247, 156);
					Color red = new Color(248, 124, 124);
					Color lightBlue = new Color(146, 240, 248);
					Color blue =  new Color(146, 200, 200);
//					Color heuristicBased = new Color(150+scoreFactor*3, 170+scoreFactor, 200-scoreFactor*7);
					if (maze.mazeSpaces[j][i] == '.') spaces[j][i].setBackground(Color.WHITE);
					if (maze.mazeSpaces[j][i] == '#') spaces[j][i].setBackground(Color.BLACK);
					if (maze.mazeSpaces[j][i] == 'S') spaces[j][i].setBackground(green);
					if (maze.mazeSpaces[j][i] == 'E') spaces[j][i].setBackground(red);
					if (maze.mazeSpaces[j][i] == '/') spaces[j][i].setBackground(lightBlue);
					if (maze.mazeSpaces[j][i] == '-') spaces[j][i].setBackground(blue);
//					if (maze.mazeSpaces[j][i] == '-') spaces[j][i].setBackground(heuristicBased);
					if (maze.mazeSpaces[j][i] == '+') spaces[j][i].setBackground(Color.YELLOW);
					
					
				
			}
		}
			pane.repaint();
	}
	
	@SuppressWarnings("unchecked")
	public void solveMaze(Algorithm algorithm, double weight){
		heuristicWeight = weight;
		Node startingNode = new Node(maze.getStart(), 0, maze);
		openList.add(startingNode);
		Node currentNode = startingNode;
		
		while (mazeUnsolved){
			try {
				Thread.sleep(20);
			} catch (InterruptedException ie) {
			}
			Collections.sort(openList);
			currentNode = openList.get(0);
			if (algorithm.equals(Algorithm.JumpPointSearch)){
				
			}
			else if (algorithm.equals(Algorithm.AStar)){
				checkNodeAStar(currentNode);
			}
			else if (algorithm.equals(Algorithm.AStarDiagonal)){
				checkNodeAStarDiagonal(currentNode);
			}		
//			System.out.println(currentNode);
			updateMaze();
			
			if(openList.isEmpty()) mazeUnsolved = false;
		}
		
		findShortestPath(currentNode, startingNode );
		
		
	}
	
	public void findShortestPath(Node endNode, Node startingNode){
		Node currentNode = endNode;
		System.out.println(endNode.getParent().toString());
		while (!currentNode.equals(startingNode)){
			currentNode = currentNode.getParent();
			shortestPath.add(currentNode);
			if (!currentNode.equals(startingNode))
			maze.mazeSpaces[currentNode.getX()][currentNode.getY()] = '+';
			
		}
		updateMaze();
	}
	
	public void checkNodeAStar(Node currentNode){
		openList.remove(0);
		closedList.add(currentNode);	
		if (maze.mazeSpaces[currentNode.getX()][currentNode.getY()] == 'E') {
			mazeUnsolved = false;
		}
		else if (maze.mazeSpaces[currentNode.getX()][currentNode.getY()] != 'S'){
			maze.mazeSpaces[currentNode.getX()][currentNode.getY()] = '-';
			
		}
		try{
			
			if(maze.mazeSpaces[currentNode.getX()][currentNode.getY()-1] == '.' || maze.mazeSpaces[currentNode.getX()][currentNode.getY()-1] == 'E' ){
				Node addedNode = new Node(new Point(currentNode.getX(),currentNode.getY()-1), currentNode.getCounter()+1, maze, currentNode);
				openList.add(addedNode);
				spaces[currentNode.getX()][currentNode.getY()-1].setText(Integer.toString((int)addedNode.getScore()));
			}
		}catch(ArrayIndexOutOfBoundsException e){
			
		}
		try{
			if(maze.mazeSpaces[currentNode.getX()][currentNode.getY()+1] == '.' || maze.mazeSpaces[currentNode.getX()][currentNode.getY()+1] == 'E' ){
				Node addedNode = new Node(new Point(currentNode.getX(),currentNode.getY()+1), currentNode.getCounter()+1, maze, currentNode);
				openList.add(addedNode);
				spaces[currentNode.getX()][currentNode.getY()+1].setText(Integer.toString((int)addedNode.getScore()));
			}
		}catch(ArrayIndexOutOfBoundsException e){
			
		}
		try{
			if(maze.mazeSpaces[currentNode.getX()-1][currentNode.getY()] == '.' || maze.mazeSpaces[currentNode.getX()-1][currentNode.getY()] == 'E' ){
				Node addedNode = new Node(new Point(currentNode.getX()-1,currentNode.getY()), currentNode.getCounter()+1, maze, currentNode);
				openList.add(addedNode);
				spaces[currentNode.getX()-1][currentNode.getY()].setText(Integer.toString((int)addedNode.getScore()));
			}
		}catch(ArrayIndexOutOfBoundsException e){
			
		}
		try{
			if(maze.mazeSpaces[currentNode.getX()+1][currentNode.getY()] == '.' || maze.mazeSpaces[currentNode.getX()+1][currentNode.getY()] == 'E' ){
				Node addedNode = new Node(new Point(currentNode.getX()+1,currentNode.getY()), currentNode.getCounter()+1, maze, currentNode);
				openList.add(addedNode);
				spaces[currentNode.getX()+1][currentNode.getY()].setText(Integer.toString((int)addedNode.getScore()));
			}
		}catch(ArrayIndexOutOfBoundsException e){
			
		}
		
	}
	
	public void checkNodeAStarDiagonal(Node currentNode){
		openList.remove(0);
		closedList.add(currentNode);	
		if (maze.mazeSpaces[currentNode.getX()][currentNode.getY()] == 'E') {
			mazeUnsolved = false;
		}
		else if (maze.mazeSpaces[currentNode.getX()][currentNode.getY()] != 'S'){
			maze.mazeSpaces[currentNode.getX()][currentNode.getY()] = '-';
			
		}
		
		
		
		try{
			char up = maze.mazeSpaces[currentNode.getX()][currentNode.getY()-1];
			if(up == '.' || up == 'E' ){
				Node addedNode = new Node(new Point(currentNode.getX(),currentNode.getY()-1), currentNode.getCounter()+1, maze, currentNode);
				openList.add(addedNode);
				spaces[currentNode.getX()][currentNode.getY()-1].setText(Integer.toString((int)addedNode.getScore()));
			}
		}catch(ArrayIndexOutOfBoundsException e){}
		try{
			char down = maze.mazeSpaces[currentNode.getX()][currentNode.getY()+1];
			if(down == '.' || down == 'E' ){
				Node addedNode = new Node(new Point(currentNode.getX(),currentNode.getY()+1), currentNode.getCounter()+1, maze, currentNode);
				openList.add(addedNode);
				spaces[currentNode.getX()][currentNode.getY()+1].setText(Integer.toString((int)addedNode.getScore()));
			}
		}catch(ArrayIndexOutOfBoundsException e){}
		try{
			char left = maze.mazeSpaces[currentNode.getX()-1][currentNode.getY()];
			if(left == '.' || left == 'E' ){
				Node addedNode = new Node(new Point(currentNode.getX()-1,currentNode.getY()), currentNode.getCounter()+1, maze, currentNode);
				openList.add(addedNode);
				spaces[currentNode.getX()-1][currentNode.getY()].setText(Integer.toString((int)addedNode.getScore()));
			}
		}catch(ArrayIndexOutOfBoundsException e){}
		try{
			char right = maze.mazeSpaces[currentNode.getX()+1][currentNode.getY()];
			if(right == '.' || right == 'E' ){
				Node addedNode = new Node(new Point(currentNode.getX()+1,currentNode.getY()), currentNode.getCounter()+1, maze, currentNode);
				openList.add(addedNode);
				spaces[currentNode.getX()+1][currentNode.getY()].setText(Integer.toString((int)addedNode.getScore()));
			}
		}catch(ArrayIndexOutOfBoundsException e){}
		// diagonals
		try{
			char right = maze.mazeSpaces[currentNode.getX()+1][currentNode.getY()];
			char down = maze.mazeSpaces[currentNode.getX()][currentNode.getY()+1];
			char downAndRight = maze.mazeSpaces[currentNode.getX()+1][currentNode.getY()+1];
			if((right == '/' || right =='E' || right == 'S'|| down == '/' || down == 'E' || down == 'S') && (downAndRight == '.' || downAndRight == 'E' )){
				Node addedNode = new Node(new Point(currentNode.getX()+1,currentNode.getY()+1), currentNode.getCounter()+Math.sqrt(2), maze, currentNode);
				openList.add(addedNode);
				spaces[currentNode.getX()+1][currentNode.getY()+1].setText(Integer.toString((int)addedNode.getScore()));
			}
		}catch(ArrayIndexOutOfBoundsException e){}
		try{
			char right = maze.mazeSpaces[currentNode.getX()+1][currentNode.getY()];
			char up = maze.mazeSpaces[currentNode.getX()][currentNode.getY()-1];
			char upAndRight = maze.mazeSpaces[currentNode.getX()+1][currentNode.getY()-1];
			if((right == '/' || right =='E' || right == 'S'|| up == '/' || up == 'E' || up == 'S') && (upAndRight == '.' || upAndRight == 'E' )){
				Node addedNode = new Node(new Point(currentNode.getX()+1,currentNode.getY()-1), currentNode.getCounter()+Math.sqrt(2), maze, currentNode);
				openList.add(addedNode);
				spaces[currentNode.getX()+1][currentNode.getY()-1].setText(Integer.toString((int)addedNode.getScore()));
			}
		}catch(ArrayIndexOutOfBoundsException e){}
		try{
			char left = maze.mazeSpaces[currentNode.getX()-1][currentNode.getY()];
			char up = maze.mazeSpaces[currentNode.getX()][currentNode.getY()-1];
			char upAndLeft = maze.mazeSpaces[currentNode.getX()-1][currentNode.getY()-1];
			if((left == '/' || left =='E' || left == 'S'|| up == '/' || up == 'E' || up == 'S') && (upAndLeft == '.' || upAndLeft == 'E' )){
				Node addedNode = new Node(new Point(currentNode.getX()-1,currentNode.getY()-1), currentNode.getCounter()+Math.sqrt(2), maze, currentNode);
				openList.add(addedNode);
				spaces[currentNode.getX()-1][currentNode.getY()-1].setText(Integer.toString((int)addedNode.getScore()));
			}
		}catch(ArrayIndexOutOfBoundsException e){System.out.println("Test");}
		try{
			char left = maze.mazeSpaces[currentNode.getX()-1][currentNode.getY()];
			char down = maze.mazeSpaces[currentNode.getX()][currentNode.getY()+1];
			char downAndLeft = maze.mazeSpaces[currentNode.getX()-1][currentNode.getY()+1];
			if((left == '/' || left =='E' || left == 'S'|| down == '/' || down == 'E' || down == 'S') && (downAndLeft == '.' || downAndLeft == 'E' )){
				Node addedNode = new Node(new Point(currentNode.getX()-1,currentNode.getY()+1), currentNode.getCounter()+Math.sqrt(2), maze, currentNode);
				openList.add(addedNode);
				spaces[currentNode.getX()-1][currentNode.getY()+1].setText(Integer.toString((int)addedNode.getScore()));
			}
		}catch(ArrayIndexOutOfBoundsException e){}
		
		
		}



	public double getHeuristicWeight() {
		return heuristicWeight;
	}
	
		
		
}

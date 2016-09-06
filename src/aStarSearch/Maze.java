package aStarSearch;

import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;

public class Maze {

	private int length, width;
	public char[][] mazeSpaces;
	private Point start, end;
	private Display display;
	private Heuristic heuristic;
	
	public Maze(int length, int width, Heuristic h, String[] mazeLines){
		this.length = length;
		this.width = width;
		heuristic = h;
		mazeSpaces = new char[length][width];
		
		for (int i = 0; i < width; i++){
			for (int j = 0; j < length; j++){
//				System.out.print(mazeLines[i].charAt(j));
				mazeSpaces[j][i] = mazeLines[i].charAt(j);
				if (mazeLines[i].charAt(j) == 'S') start = new Point(j,i);
				if (mazeLines[i].charAt(j) == 'E') end = new Point(j,i);
					
				
				
			}
//			System.out.println();
		}
	}
	
	public Maze(int length, int width, String[] mazeLines){
		this.length = length;
		this.width = width;
		heuristic = Heuristic.Euclidean;
		mazeSpaces = new char[length][width];
		
		for (int i = 0; i < width; i++){
			for (int j = 0; j < length; j++){
//				System.out.print(mazeLines[i].charAt(j));
				mazeSpaces[j][i] = mazeLines[i].charAt(j);
				if (mazeLines[i].charAt(j) == 'S') start = new Point(j,i);
				if (mazeLines[i].charAt(j) == 'E') end = new Point(j,i);
					
				
				
			}
//			System.out.println();
		}
	}
	
	public Heuristic getHeuristic(){
		return heuristic;
	}
	
	public void setDisplay(Display d){
		display = d;
	}
	
	public Display getDisplay(){
		return display;
	}
	
	public int getLength(){
		return length;
	}
	
	public int getWidth(){
		return width;
	}
	public char[][] getMazeSpaces(){
		return mazeSpaces;
	}
	
	public Point getStart(){
		return start;
	}
	public Point getEnd(){
		return end;
	}
}

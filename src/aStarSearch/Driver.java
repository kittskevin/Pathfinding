package aStarSearch;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.*;

public class Driver {
	
	static Scanner scan;
	
	public static void main(String[] args) {
		
		ArrayList<Maze> mazes = new ArrayList<Maze>();
		Heuristic heuristic = Heuristic.Chebyshev;
		Algorithm algorithm = Algorithm.AStarDiagonal;
		double weight = 1;
		
		
		
		try{
			scan = new Scanner(new File("mazes.txt"));
			while (scan.hasNext()){
				int y = Integer.parseInt(scan.next());
				int x = Integer.parseInt(scan.next());
				String[] mazeLines = new String[y];
				for (int i = 0; i < y; i++){
					mazeLines[i] = scan.next();
				}
				mazes.add(new Maze(x, y, heuristic, mazeLines));
			}
			scan.close();
			
		}catch(FileNotFoundException e){
			System.out.println("File not Found");
		}
		
//		for(Maze m : mazes){
//			Display d1 =  new Display(m, 30);
//			d1.solveMaze(algorithm, weight);
//			for(Node n : d1.getOpenList()) System.out.println(n.toString());
//		}
			new Display(mazes.get(0), 30).solveMaze(algorithm, weight);
		
		
	}

}

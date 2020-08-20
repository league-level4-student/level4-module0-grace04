package _04_Maze_Maker;

import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;


public class MazeMaker{
	
	private static int width;
	private static int height;
	
	private static Maze maze;
	
	private static Random randGen = new Random();
	private static Stack<Cell> uncheckedCells = new Stack<Cell>();
	
	
	public static Maze generateMaze(int w, int h){
		width = w;
		height = h;
		maze = new Maze(width, height);
		
		//4. select a random cell to start
		int x = randGen.nextInt(w);
		int y = randGen.nextInt(h);
		//5. call selectNextPath method with the randomly selected cell
		Cell c = maze.getCell(x, y);
		selectNextPath(c);
		return maze;
	}

	//6. Complete the selectNextPathMethod
	private static void selectNextPath(Cell currentCell) {
		//A. mark cell as visited
		currentCell.hasBeenVisited();
		//B. Get an ArrayList of unvisited neighbors using the current cell and the method below
		ArrayList<Cell> unvisNei = getUnvisitedNeighbors(currentCell);
		//C. if has unvisited neighbors,
		if(unvisNei.size()>0) {
			//C1. select one at random.
			int x = randGen.nextInt(unvisNei.size());
			Cell c = unvisNei.get(x);
			//C2. push it to the stack
			uncheckedCells.push(c);
			//C3. remove the wall between the two cells
			removeWalls(currentCell, c);
			//C4. make the new cell the current cell and mark it as visited
			currentCell = c;
			//C5. call the selectNextPath method with the current cell
			selectNextPath(currentCell);
		}
		//D. if all neighbors are visited
		if(unvisNei.size()==0) {
			//D1. if the stack is not empty
			if(uncheckedCells.size()>0) {
				// D1a. pop a cell from the stack
				Cell c = uncheckedCells.pop();
				// D1b. make that the current cell
				currentCell = c;
				// D1c. call the selectNextPath method with the current cell
				selectNextPath(currentCell);
			}
		}
	}

	//7. Complete the remove walls method.
	//   This method will check if c1 and c2 are adjacent.
	//   If they are, the walls between them are removed.
	private static void removeWalls(Cell c1, Cell c2) {
		int x1 = c1.getX(), y1 = c1.getY();
		int x2 = c2.getX(), y2 = c2.getY();
		if(x1==x2) {
			if(y1<y2) {
				c1.setSouthWall(false);
				c2.setNorthWall(false);
			}
			if(y1>y2) {
				c1.setNorthWall(false);
				c2.setSouthWall(false);
			}
			else {
				System.out.println("Same cell");
			}
		}
		if(y1==y2) {
			if(x1<x2) {
				c1.setEastWall(false);
				c2.setWestWall(false);
			}
			if(x1>x2) {
				c1.setWestWall(false);
				c2.setEastWall(false);
			}
			else {
				System.out.println("Same cell");
			}
		}
		else {
			System.out.println("Cells are not adjacent");
		}
	}
	
	//8. Complete the getUnvisitedNeighbors method
	//   Any unvisited neighbor of the passed in cell gets added
	//   to the ArrayList
	private static ArrayList<Cell> getUnvisitedNeighbors(Cell c) {
		ArrayList<Cell> unvisNei = new ArrayList<Cell>();
		if (c.getY()>0)
			unvisNei.add(maze.getCell(c.getX(),c.getY()-1));
		if (c.getX()<unvisNei.size()-1)
			unvisNei.add(maze.getCell(c.getX()+1,c.getY()));
		if (c.getX()>0)
			unvisNei.add(maze.getCell(c.getX()-1,c.getY()));
		if (c.getY()<unvisNei.size()-1)
			unvisNei.add(maze.getCell(c.getX(),c.getY()+1));
		return unvisNei;
	}
}

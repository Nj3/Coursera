import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.TreeMap;

import edu.princeton.cs.algs4.MinPQ;

/**
 * @author Nj3
 * This class will solve n by n puzzle using A* Search algorithm.
 * heuristic chosen is manhattan
 */
public class Solver {
	
	private TreeMap<Integer, List<Board>> goalPaths = new TreeMap<Integer, List<Board>>();
	private int numOfMoves = 0;
	private boolean isSolvable = false;
	/*
	 * find a solution to the initial board (using the A* algorithm)
	 * Variable list:
	 * 1. gameTree      -> actual game tree which will be using it for solving.
	 * 2. twinGameTree  -> game tree formed after finding twin of initial board.
	 * 3. searchNode    -> Node which will be expanded at every iteration till puzzle is solved.
	 * 4. path          -> way the board moved by sliding the blocks inside the board.
	 * 5. exploredNodes -> list of game boards which is already expanded.
	 * 6. searchNodeTwin-> node which will be expanded at every iteration where the source is twin of initial game board.
	 */
	public Solver(Board initial) {
		if ( initial == null ) throw new java.lang.IllegalArgumentException();
		
		// local variable declarations
		PriorityOrder po = new PriorityOrder();
		MinPQ<Board> gameTree = new MinPQ<Board>(po);
		MinPQ<Board> twinGameTree = new MinPQ<Board>(po);
		List<Board> path = new ArrayList<Board>();
		List<Board> exploredNodes = new ArrayList<Board>();
		Board searchNode = initial;
		Board searchNodeTwin = initial.twin();
		exploredNodes.add(searchNode);
		exploredNodes.add(searchNodeTwin);
		
		// To check whether initial node is in goal state.
		if ( searchNode.isGoal() ) {
			isSolvable = true;
			return;
		}else if ( searchNodeTwin.isGoal() ) {
			isSolvable = false;
			return;
		}
		
		// Iterating through the tree to find the paths
		while ( true ) {
				
			// solving for actual board and add in goalpaths, map of num of moves to reach the goal and path taken to achieve it
			for ( Board b : searchNode.neighbors() ) {
				if ( !exploredNodes.contains(b) ) gameTree.insert(b);
			}
			
			searchNode = gameTree.delMin();
			exploredNodes.add(searchNode);
			path.add(searchNode);
			numOfMoves++;
			
			if ( searchNode.isGoal() ) {
				isSolvable = true;
				goalPaths.put(numOfMoves, path);
			}
			
			// solving the twin board. if its solvable then the initial board is unsolvable.
			for ( Board b : searchNodeTwin.neighbors() ) {
				if ( !exploredNodes.contains(b) ) twinGameTree.insert(b);
			}
			
			searchNodeTwin = twinGameTree.delMin();
			exploredNodes.add(searchNodeTwin);
			
			if ( searchNodeTwin.isGoal() ) {
				isSolvable = false;
				return;
			}
		}
	}
	
	/*
	 * f(n) = g(n) + h(n)
	 * f(n) -> total cost
	 * g(n) -> step cost
	 * h(n) -> heuristic ( manhattan function )
	 */
	private int Priority(Board searchNode) {
		return numOfMoves + searchNode.manhattan();
	}
	
	
	private class PriorityOrder implements Comparator<Board> {
		public int compare(Board b1, Board b2) {
			int f1 = Priority(b1);
			int f2 = Priority(b2);
			
			if ( f1 > f2 ) return 1;
			else if ( f1 < f2 ) return -1;
			else return 0;
		}
	}
	/*
	 * is the initial board solvable?
	 */
    public boolean isSolvable() {
    	return isSolvable;
    }
    
    /*
     * min number of moves to solve initial board; -1 if unsolvable
     */
    public int moves() {
    	if ( this.isSolvable ) return numOfMoves;
    	else return -1;
    }
    
    /*
     * sequence of boards in a shortest solution; null if unsolvable
     */
    public Iterable<Board> solution() {
    	if ( !this.isSolvable ) return null;
    	else {
    		int shortestNumOfMoves = goalPaths.firstKey();
    		return goalPaths.get(shortestNumOfMoves);
    	}
    }

}

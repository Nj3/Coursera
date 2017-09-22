import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import edu.princeton.cs.algs4.MinPQ;

/**
 * @author Nj3
 * This class will solve n by n puzzle using A* Search algorithm.
 * heuristic chosen is manhattan
 */
public class Solver {
	
	private List<Board> exploredNodes = new ArrayList<Board>();
	private int numOfMoves = 0;
	private boolean isSolvable = false;
	// a linked list for answer?
	/*
	 * find a solution to the initial board (using the A* algorithm)
	 * Variable list:
	 * 1. gameTree -> actual game tree which will be using it for solving.
	 * 2. twinGameTree -> game tree formed after finding twin of initial board.
	 * 3. searchNode -> Node which will be expanded at every iteration till 
	 * puzzle is solved.
	 * 
	 */
	public Solver(Board initial) {
		if ( initial == null ) throw new java.lang.IllegalArgumentException();
		
		// local variable declarations
		MinPQ<Board> gameTree = new MinPQ<Board>();
		MinPQ<Board> twinGameTree = new MinPQ<Board>();
		Board searchNode = initial;
		exploredNodes.add(searchNode);
		exploredNodes.add(searchNode.twin());
		
		while ( true ) {
			if ( searchNode.isGoal() ) {
				isSolvable = true;
				return;
			}
			
			// solving for actual board
			
			for ( Board b : searchNode.neighbors() ) {
				if ( !exploredNodes.contains(b) ) {
					gameTree.insert(b);
				}
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
    }

}

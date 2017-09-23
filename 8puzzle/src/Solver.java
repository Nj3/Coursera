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
	
	private TreeMap<Integer, List<GameBoard>> goalPaths = new TreeMap<Integer, List<GameBoard>>();
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
		MinPQ<GameBoard> gameTree = new MinPQ<GameBoard>(po);
		MinPQ<GameBoard> twinGameTree = new MinPQ<GameBoard>(po);
		List<GameBoard> path = new ArrayList<GameBoard>();
		List<GameBoard> exploredNodes = new ArrayList<GameBoard>();
		GameBoard searchNode = new GameBoard(initial);
		GameBoard searchNodeTwin = new GameBoard(initial.twin());
		gameTree.insert(searchNode);
		twinGameTree.insert(searchNodeTwin);
		
		// To check whether initial node is in goal state.
		if ( searchNode.currentGameboard.isGoal() ) {
			isSolvable = true;
			return;
		}else if ( searchNodeTwin.currentGameboard.isGoal() ) {
			isSolvable = false;
			return;
		}
		
		int n = 0;
		// Iterating through the tree to find the paths
		while ( n < 10 ) {
			
			searchNode = gameTree.delMin();
			
			if ( searchNode.currentGameboard.isGoal() ) {
				isSolvable = true;
				goalPaths.put(numOfMoves, path);
				numOfMoves = 0;
				if ( gameTree.isEmpty() ) return;
			}
			// solving for actual board and add in goalpaths, map of num of moves to reach the goal and path taken to achieve it
			if ( !exploredNodes.contains(searchNode) ) {
				for ( Board b : searchNode.currentGameboard.neighbors() ) {
					if ( !exploredNodes.contains(b) ) gameTree.insert(new GameBoard(b));
				}
			}
			
			exploredNodes.add(searchNode);
			path.add(searchNode);
			numOfMoves++;
			//System.out.println(" The search node in this iteration is :");
			//System.out.println(searchNode);		
			System.out.println(gameTree.size());
			System.out.println(goalPaths.toString());
			
			// solving the twin board. if its solvable then the initial board is unsolvable.
			searchNodeTwin = twinGameTree.delMin();	
			
			if ( searchNodeTwin.currentGameboard.isGoal() ) {
				isSolvable = false;
				return;
			}
			
			if ( !exploredNodes.contains(searchNodeTwin) ) {
				for ( Board b : searchNodeTwin.currentGameboard.neighbors() ) {
					if ( !exploredNodes.contains(b) ) twinGameTree.insert(new GameBoard(b));
				}
			}
			
			exploredNodes.add(searchNodeTwin);
			
			
			n++;
		}
	}
	
	
	/* A class to store the gameboard with moves + Actual Board class
	 * This is done because API doesn't allow moves as instance variable in Board class.
	 */
	private class GameBoard {
		
		int gameBoardManhattanDistance = 0;
		int movesToReachCurrentBoardState = 0;
		Board currentGameboard;
		
		public GameBoard(Board b) {
			currentGameboard = b;
			movesToReachCurrentBoardState = numOfMoves;
			gameBoardManhattanDistance = b.manhattan();
		}
	}
	
	/*
	 * f(n) = g(n) + h(n)
	 * f(n) -> total cost
	 * g(n) -> step cost
	 * h(n) -> heuristic ( manhattan function )
	 */
	private class PriorityOrder implements Comparator<GameBoard> {
		public int compare(GameBoard b1, GameBoard b2) {
			int f1 = b1.gameBoardManhattanDistance + b1.movesToReachCurrentBoardState;
			int f2 = b2.gameBoardManhattanDistance + b2.movesToReachCurrentBoardState;
			
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
    		List<GameBoard> temp = goalPaths.get(shortestNumOfMoves);
    		List<Board> soln = new ArrayList<Board>();
    		for ( GameBoard gb : temp ) {
    			soln.add(gb.currentGameboard);
    		}
    		return soln;
    	}
    }

}
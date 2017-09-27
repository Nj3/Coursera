import java.util.Arrays;
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
	
	private TreeMap<Integer, Board[]> goalPaths = new TreeMap<Integer, Board[]>();
	private int numOfMoves = 0;
	private boolean isSolvable = false;
	/*
	 * find a solution to the initial board (using the A* algorithm)
	 * Variable list:
	 * 1. gameTree      -> actual game tree which will be using it for solving.
	 * 2. twinGameTree  -> game tree formed after finding twin of initial board.
	 * 3. searchNode    -> Node which will be expanded at every iteration till puzzle is solved.
	 * 4. path          -> way the board moved by sliding the blocks inside the board.
	 * 5. searchNodeTwin-> node which will be expanded at every iteration where the source is twin of initial game board.
	 */
	public Solver(Board initial) {
		if ( initial == null ) throw new java.lang.IllegalArgumentException();
		
		// local variable declarations
		int minNumOfMoves = 0;
		PriorityOrder po = new PriorityOrder();
		MinPQ<GameBoard> gameTree = new MinPQ<GameBoard>(po);
		MinPQ<GameBoard> twinGameTree = new MinPQ<GameBoard>(po);
		Board[] path;
		GameBoard searchNode = new GameBoard(initial, null);
		GameBoard searchNodeTwin = new GameBoard(initial.twin(), null);
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
		
		// Iterating through the tree to find the paths
		while ( true ) {
			
			if ( gameTree.isEmpty() ) return;
			searchNode = gameTree.delMin();
			numOfMoves = searchNode.movesToReachCurrentBoardState;
			
			// solving for actual board and add in goalpaths, map of num of moves to reach the goal and path taken to achieve it
			if ( searchNode.currentGameboard.isGoal() ) {
				isSolvable = true;
				
				GameBoard boardToBeAddedInPath = searchNode;
				int i = numOfMoves;
				minNumOfMoves = numOfMoves;
				path = new Board[i+1];
				while ( i >= 0 ) {
					path[i] = boardToBeAddedInPath.currentGameboard;
					i--;
					boardToBeAddedInPath = boardToBeAddedInPath.previousGameBoard;
				}
				goalPaths.put(numOfMoves, path);
				
				if ( gameTree.isEmpty() ) return;
				else continue;
			}
			
			numOfMoves++;
			// minNumOfMoves will only be set when its reached the goal state atleast once.
			// so we have to expanding node to take place by keeping the below condition
			// which indicates we haven't reached the goal state yet.
			if ( minNumOfMoves == 0 || numOfMoves < minNumOfMoves ) {
				for ( Board b : searchNode.currentGameboard.neighbors() ) {
					if ( searchNode.previousGameBoard == null ) gameTree.insert(new GameBoard(b, searchNode)); // initial condition
					else if ( !searchNode.previousGameBoard.currentGameboard.equals(b) ) gameTree.insert(new GameBoard(b, searchNode));
				}
			}
			
			// solving the twin board. if its solvable then the initial board is unsolvable.
			// Only execute the below till actual board is not solvable
			if ( !isSolvable ) {
				searchNodeTwin = twinGameTree.delMin();	
				
				if ( searchNodeTwin.currentGameboard.isGoal() ) {
					isSolvable = false;
					return;
				}
			
				for ( Board b : searchNodeTwin.currentGameboard.neighbors() ) {
					if ( searchNodeTwin.previousGameBoard == null ) twinGameTree.insert(new GameBoard(b, searchNodeTwin)); // initial condition
					else if ( !searchNodeTwin.previousGameBoard.currentGameboard.equals(b) ) twinGameTree.insert(new GameBoard(b, searchNodeTwin));
				}
			
				if ( twinGameTree.isEmpty() ) return;
			}
		}
	}
	
	/* A class to store the gameboard with moves + Actual Board class
	 * This is done because API doesn't allow moves as instance variable in Board class.
	 */
	private class GameBoard {
		
		int gameBoardManhattanDistance = 0;
		int movesToReachCurrentBoardState = 0;
		Board currentGameboard;
		GameBoard previousGameBoard;
		
		public GameBoard(Board currentBoard, GameBoard previousBoard) {
			if ( currentBoard == null ) throw new java.lang.IllegalArgumentException("cannot have null board. Check the ip");
			
			currentGameboard = currentBoard;
			previousGameBoard = previousBoard;
			movesToReachCurrentBoardState = numOfMoves;
			gameBoardManhattanDistance = currentBoard.manhattan();
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
    	
    	if ( this.isSolvable ) return goalPaths.firstKey();
    	else return -1;
    }
    
    /*
     * sequence of boards in a shortest solution; null if unsolvable
     */
    public Iterable<Board> solution() {
    	if ( !this.isSolvable ) return null;
    	else {
    		int shortestNumOfMoves = goalPaths.firstKey();
    		List<Board> soln = Arrays.asList(goalPaths.get(shortestNumOfMoves));
    		return soln;
    	}
    }

}
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;

/**
 * @author Nj3
 * This class represents a set of points in the unit square and is implemented using 2d Tree which is a 
 * generalization of Binary Search Tree(BST). The idea is to build a BST with points in the nodes, 
 * using the x- and y-coordinates of the points as keys in strictly alternating sequence. 
 * Data structure used is SET from algs4.jar provided in the course.
 * It has following methods:
 * 1. isEmpty - to check if the set is empty.
 * 2. size - number of points in the set.
 * 3. insert - add the point to the set if it's already not in the set.
 * 4. contains - checks whether the given point is already in the set.
 * 5. draw - draw all points to standard draw.
 * 6. range - all points that are inside the rectangle.
 * 7. nearest - a nearest neighbour in the set to given point p.
 */
public class KdTree {
	
	private int size = 0;
	private Node root;
	
	public KdTree() {
	}
	
	private static class Node {
		private Point2D point; // point
		private RectHV rect; // the axis aligned rectangle corresponding to this node.
		private Node lb; // left/bottom subtree
		private Node rt; // right/top subtree
		
		public Node(Point2D pt) {
			this.point = pt;
		}
	}
	
	/*
	 * is the tree empty?
	 */
	public boolean isEmpty() {
		return size == 0;
	}
	
	/*
	 * number of points in the tree
	 */
	public int size() {
		return size;
	}
	// Refactor insert and contains such that it will check for equality first.
	
	/*
	 * Add the point to the tree if it's already not in the set.
	 * At every level alternate between x and y coordinates while inserting.
	 * 
	 * @param p - point to be inserted in the set.
	 * @throws IllegalArgumentException if p is null.
	 */
	public void insert(Point2D p) {
		if ( p == null ) throw new java.lang.IllegalArgumentException();
		
		root = insertByX(root, p);
		size++;
	}
	
	private Node insertByX(Node n, Point2D p) {
		if ( n == null ) return new Node(p);
		if ( p.x() < n.point.x() ) n.lb = insertByY(n.lb, p);
		else if ( p.x() >= n.point.x() ) n.rt = insertByY(n.rt, p);
		else if ( p.x() == n.point.x() && p.y() == n.point.y() ) return n;
		return n;
	}
	
	private Node insertByY(Node n, Point2D p) {
		if ( n == null ) return new Node(p);
		if ( p.y() < n.point.y() ) n.lb = insertByX(n.lb, p);
		else if ( p.y() >= n.point.y() ) n.rt = insertByX(n.rt, p);
		else if ( p.x() == n.point.x() && p.y() == n.point.y() ) return n;
		return n;
	}
	
	/*
	 * Does the tree contain point p
	 * 
	 * @param p - point to check whether it's already in the set.
	 * @throws IllegalArgumentException if p is null.
	 */
	public boolean contains(Point2D p) {
		if ( p == null ) throw new java.lang.IllegalArgumentException();
		
		return get(p)!= null;
	}
	
	private Node get(Point2D p) {
		return searchByX(root, p);
	}
	
	private Node searchByX(Node n, Point2D p) {
		if ( n == null || p == null ) return null;
		
		if ( p.x() < n.point.x() ) n.lb = searchByY(n.lb, p);
		else if ( p.x() >= n.point.x() ) n.rt = searchByY(n.rt, p);
		else if ( p.x() == n.point.x() && p.y() == n.point.y() ) return n;
		return null;
	}
	
	private Node searchByY(Node n, Point2D p) {
		if ( n == null || p == null) return null;
		
		if ( p.y() < n.point.y() ) n.lb = searchByX(n.lb, p);
		else if ( p.y() >= n.point.y() ) n.rt = searchByX(n.rt, p);
		else if ( p.x() == n.point.x() && p.y() == n.point.y() ) return n;
		return null;
	}
}

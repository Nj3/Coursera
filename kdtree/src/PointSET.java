import edu.princeton.cs.algs4.SET;

import java.util.ArrayList;

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

/**
 * @author Nj3
 * This class represents a set of points in the unit square and is implemented using red-black BST.
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
public class PointSET {
	
	private SET<Point2D> pts;
	
	/*
	 * Construct an empty set of points
	 */
	public PointSET() {
		pts = new SET<Point2D>();
	}
	
	/*
	 * is the set empty?
	 */
	public boolean isEmpty() {
		return pts.isEmpty();
	}
	
	/*
	 * number of points in the set
	 */
	public int size() {
		return pts.size();
	}
	
	/*
	 * Add the point to the set if it's already not in the set
	 * 
	 * @param p - point to be inserted in the set.
	 * @throws IllegalArgumentException if p is null.
	 */
	public void insert(Point2D p) {
		if ( p == null ) throw new java.lang.IllegalArgumentException();
		
		pts.add(p);
	}
	
	/*
	 * Does the set contain point p
	 * 
	 * @param p - point to check whether it's already in the set.
	 * @throws IllegalArgumentException if p is null.
	 */
	public boolean contains(Point2D p) {
		if ( p == null ) throw new java.lang.IllegalArgumentException();
		
		return pts.contains(p);
	}
	
	/*
	 * Draw all points to Standard draw
	 */
	public void draw() {
		StdDraw.setPenRadius(0.02);
		StdDraw.setPenColor(StdDraw.BLUE);
		
		for ( Point2D p : pts ) {
			StdDraw.point(p.x(), p.y());
		}
	}
	
	/*
	 * all points that are inside the rectangle (or on the boundary)
	 * 
	 *  @param rect - rectangle in 2D plane which may contains some points
	 *  @throws IllegalArgumentException if rect is null
	 */
	public Iterable<Point2D> range(RectHV rect) {
		if ( rect == null ) throw new java.lang.IllegalArgumentException();
		
		ArrayList<Point2D> pointsInRect = new ArrayList<Point2D>();
		
		for ( Point2D p : pts ) {
			if ( rect.contains(p) ) pointsInRect.add(p);
		}
		
		return pointsInRect;
	}
	
	/*
	 * a nearest neighbor in the set to point p; null if the set is empty 
	 * 
	 * @param p - reference point from which we will find the nearest point.
	 * @throws IllegalArgumentException if p is null.
	 */
	public Point2D nearest(Point2D p) {
		if ( p == null ) throw new java.lang.IllegalArgumentException();
		if ( pts.isEmpty() ) return null;
		
		double minDist = +2.0; // Since input co-ordinates will be always between 0 and 1. Maximum possible Euclidean distance is 1.414
		Point2D closestPoint = null;
		
		for ( Point2D pt : pts ) {
			double dist = pt.distanceSquaredTo(p);
			if ( dist < minDist ) {
				minDist = dist;
				closestPoint = pt;
			}
		}
		
		return closestPoint;
	}

}

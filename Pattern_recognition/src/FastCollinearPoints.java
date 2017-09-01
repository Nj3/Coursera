import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Nj3
 * this class will find the collinear points based on Sorting where the sorting will be
 * based on slope.
 * Whatever points have their slope equal with point of reference are collinear.
 */
public class FastCollinearPoints {
	
	private List<LineSegment> ls = new ArrayList<LineSegment>();
	private Point[] sorted_pts;
	private int num_of_lines = 0;
	private Map<Integer, Point[]> linePoints = new HashMap<Integer, Point[]>();
	
	/*
	 * @param points - list of points from which collinear points are to be found.
	 * List of variables:
	 * 1. N - total number of points.
	 * 2. sorted_pts - copy of points which is sorted with respect to slope.
	 * 3. collinearCount - a count variable to check whether number of collinear points
	 * 					   reached 3 excluding the origin.
	 * 4. prevSlope,currSlope - value of previous and currently calculated slope.
	 * 5. colnr - temp arraylist to hold the collinear points before making it to a linesegment.
	 */
	public FastCollinearPoints(Point[] points) {
		// finds all line segments containing 4 or more points
		checkIllegalParam(points);
		
		int N = points.length;
		sorted_pts = Arrays.copyOf(points, N);
		
		for ( int p = 0; p < N; ++p ) {
			Arrays.sort(sorted_pts, points[p].slopeOrder());
			int collinearCount = 0;
			double prevSlope = 0.0;
			ArrayList<Point> colnr = new ArrayList<Point>();
			for ( int q = 1; q < N; ++q ) {
				double currSlope = points[p].slopeTo(sorted_pts[q]);
				
				if ( collinearCount == 0 && q == 1) {
					colnr.add(sorted_pts[q]);
					collinearCount = 1;
					prevSlope = currSlope;
				}else if ( currSlope != prevSlope && collinearCount < 3 ) {
					colnr.clear();
					collinearCount = 1;
					colnr.add(sorted_pts[q]);
					prevSlope = currSlope;
				}else if ( currSlope == prevSlope ) {
					collinearCount++;
					colnr.add(sorted_pts[q]);
					if ( collinearCount >=3 && q == N-1 ) {
						colnr.add(points[p]);
						Collections.sort(colnr);
						LineSegment ln = new LineSegment(colnr.get(0), colnr.get(colnr.size()-1));
						if ( !isDuplicateLine(colnr.get(0), colnr.get(colnr.size()-1)) ) {
							linePoints.put(++num_of_lines, new Point[] {colnr.get(0), colnr.get(colnr.size()-1)});
							ls.add(ln);
						}	
					}
				}else if ( currSlope != prevSlope && collinearCount >=3 ) {
					colnr.add(points[p]);
					Collections.sort(colnr);
					LineSegment ln = new LineSegment(colnr.get(0), colnr.get(colnr.size()-1));
					if ( !isDuplicateLine(colnr.get(0), colnr.get(colnr.size()-1)) ) {
						linePoints.put(++num_of_lines, new Point[] {colnr.get(0), colnr.get(colnr.size()-1)});
						ls.add(ln);
					}
					prevSlope = currSlope;
					colnr.clear();
					collinearCount = 1;
					colnr.add(sorted_pts[q]);
				}
			}
		}
	}
	
	private boolean isDuplicateLine(Point startPoint, Point endPoint) {
		if ( ls.size() == 0 ) return false;
		else {
			for ( Point[] pts : linePoints.values() ) {
				if ( pts[0] == startPoint && pts[1] == endPoint ) return true;
			}
			return false;
		}	
	}
	
	public int numberOfSegments() {
		// the number of line segments
		return num_of_lines;
	}
	
	public LineSegment[] segments() {
		// the line segments
		LineSegment[] result = new LineSegment[num_of_lines];
		ls.toArray(result);
		return result;
	}

	/* checks whether parameter is null or contain any elements which has reference to null
	 * or any duplicate points.
	 * @param points
	 * Raise exception if it's any of the above.
	 */
	private void checkIllegalParam(Point[] points) {
		
		// check if parameter is null
		if (points == null) throw new java.lang.IllegalArgumentException();
		
		// check if any point in array is null (or) any point is repeated.
		for (int i=0; i<points.length; ++i) {
			if (points[i] == null ) throw new java.lang.IllegalArgumentException();
			for (int j=i+1; j<points.length; ++j) {
				if ( points[i].compareTo(points[j]) == 0 ) throw new java.lang.IllegalArgumentException();;
			}
		}
	}

}

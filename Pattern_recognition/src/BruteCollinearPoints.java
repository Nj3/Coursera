import java.util.Arrays;

public class BruteCollinearPoints {
	/* constructor will sort the point in ascending order
	 * and it will list the line segments which has 4 points 
	 * from left to right.
	 * @param points - list of points in (x,y) form.
	 */
	private int num_of_lines = 0;
	private LineSegment[] ls;
	
	// have to change logic
	
	public BruteCollinearPoints(Point[] points) {
		// finds all line segments containing 4 points
		Arrays.sort(points);
		
		/* Sorted output to check
		int N = points.length;
		for ( int i=0; i<N; ++i ) {
			System.out.println(points[i]);
		} */
		
		int N = points.length;
		ls = new LineSegment[N-3];
		for ( int i=0; i<N-3; ++i ) {
			double p2q_slope = points[i].slopeTo(points[i+1]);
			double p2r_slope = points[i].slopeTo(points[i+2]);
			double p2s_slope = points[i].slopeTo(points[i+3]);
			System.out.printf("%f, %f, %f",p2q_slope , p2r_slope ,  p2s_slope);
			if ( p2q_slope == p2r_slope && p2r_slope == p2s_slope ) {
				ls[i] = new LineSegment(points[i], points[i+3]);
				num_of_lines++;
			}
		}
	}
	
	public int numberOfSegments() {
		// the number of line segments
		return num_of_lines;
	}
	
	public LineSegment[] segments() {
		// the line segments which is collinear
		return ls;
	}

}

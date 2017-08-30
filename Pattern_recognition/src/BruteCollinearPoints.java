import java.util.ArrayList;
import java.util.Arrays;

public class BruteCollinearPoints {
	/* constructor will sort the point in ascending order
	 * and it will list the line segments which has 4 points 
	 * from left to right.
	 * @param points - list of points in (x,y) form.
	 */
	private int num_of_lines = 0;
	private ArrayList<LineSegment> ls = new ArrayList<LineSegment>();
	
	// have to change logic
	
	public BruteCollinearPoints(Point[] points) {
		// finds all line segments containing 4 points
		
		// check parameters
		checkIllegalParam(points);
		
		// finding list of collinear points
		int N = points.length;
		for (int p=0; p<N; ++p) {
			for (int q=0; q<N; ++q) {
				if ( p==q || p>q ) continue;
				double slope_p2q = points[p].slopeTo(points[q]);
				for (int r=0; r<N; ++r) {
					if ( p==r || q==r || p==q || q>r) continue;
					double slope_p2r = points[p].slopeTo(points[r]);
					if ( slope_p2q != slope_p2r ) continue;
					for (int s=0;  s<N; ++s) {
						if ( r==s || p==s || q==s || p==r || q==r || p==q || r>s ) continue;
						double slope_p2s = points[p].slopeTo(points[s]);
						if ( slope_p2q == slope_p2r && slope_p2q == slope_p2s ) {
							Point[] tmp = new Point[] {points[p], points[q], points[r], points[s]};
							Arrays.sort(tmp);
							//System.out.println(Arrays.toString(tmp));
							ls.add(new LineSegment(tmp[0], tmp[3]));
							num_of_lines++;
						}
					}
				}
			}
		}
		
	}
	

	public int numberOfSegments() {
		// the number of line segments
		return num_of_lines;
	}
	
	
	public LineSegment[] segments() {
		// the line segments which is collinear
		LineSegment[] result = new LineSegment[num_of_lines];
		ls.toArray(result);
		return result;
	}
	
	private void checkIllegalParam(Point[] points) {
		
		// check if parameter is null
		if (points == null) throw new java.lang.IllegalArgumentException();
		
		// check if any point in array is null (or) any point is repeated.
		for (int i=0; i<points.length; ++i) {
			if (points[i] == null ) throw new java.lang.IllegalArgumentException();
			for (int j=i+1; j<points.length; ++j) {
				if ( points[i].compareTo(points[j]) == 0 ) throw new java.lang.IllegalArgumentException();
			}
		}
	}

}

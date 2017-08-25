import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

/* this is for testing purpose 
 * Classes used:
 * 1. Point
 * 2. LineSegment
 * 3. BruteCollinearPoints
 */
public class CollinearTest {

	public static void main(String[] args) {
		// TODO UNIT TESTING
		// read the n points from a file
//	    In in = new In(args[0]);
		String f = "/home/itachi1793/workspace/Pattern_recognition/src/collinear/input10.txt";
	    In in = new In(f);
	    int n = in.readInt();
	    Point[] points = new Point[n];
	    for (int i = 0; i < n; i++) {
	        int x = in.readInt();
	        int y = in.readInt();
	        points[i] = new Point(x, y);
	    }

	    // draw the points
	    StdDraw.enableDoubleBuffering();
	    StdDraw.setXscale(0, 32768);
	    StdDraw.setYscale(0, 32768);
	    for (Point p : points) {
	        p.draw();
	    }
	    StdDraw.show();

	    // print and draw the line segments
	    BruteCollinearPoints collinear = new BruteCollinearPoints(points);
	    for (LineSegment segment : collinear.segments()) {
	        StdOut.println(segment.toString());
	        segment.draw();
	    }
	    StdDraw.show();
	    
	    }

}

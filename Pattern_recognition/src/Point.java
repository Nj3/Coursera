
import java.util.Comparator;
import edu.princeton.cs.algs4.StdDraw;

/**
 * @author Nj3
 * Implementation of Point 2D (x, y)
 */
public class Point implements Comparable<Point> {

	private final int x;
	private final int y;
	/**
	 * Initializes a new point.
	 * @param x, y It is the 2D spatial coordinates of a plane.
	 */
	public Point(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	/**
     * Draws this point to standard draw.
     */
    public void draw() {
        StdDraw.point(x, y);
    }

    /**
     * Draws the line segment between this point and the specified point
     * to standard draw.
     *
     * @param that the other point
     */
    public void drawTo(Point that) {
        StdDraw.line(this.x, this.y, that.x, that.y);
    }

    /**
     * Returns the slope between this point and the specified point.
     * Formally, if the two points are (x0, y0) and (x1, y1), then the slope
     * is (y1 - y0) / (x1 - x0). For completeness, the slope is defined to be
     * +0.0 if the line segment connecting the two points is horizontal;
     * Double.POSITIVE_INFINITY if the line segment is vertical;
     * and Double.NEGATIVE_INFINITY if (x0, y0) and (x1, y1) are equal.
     *
     * @param  that the other point
     * @return the slope between this point and the specified point
     */
    public double slopeTo(Point that) {
        double slope = (double) (that.y -  this.y) / (that.x - this.x);
        if ( this.y == that.y && this.x != that.x ) return +0.0;
        else if ( this.x == that.x && this.y != that.y ) return Double.POSITIVE_INFINITY;
        else if ( this.x == that.x && this.y == that.y ) return Double.NEGATIVE_INFINITY;
        else return slope;
    }

    /**
     * Compares two points by y-coordinate, breaking ties by x-coordinate.
     * Formally, the invoking point (x0, y0) is less than the argument point
     * (x1, y1) if and only if either y0 < y1 or if y0 = y1 and x0 < x1.
     *
     * @param  that the other point
     * @return the value <tt>0</tt> if this point is equal to the argument
     *         point (x0 = x1 and y0 = y1);
     *         a negative integer if this point is less than the argument
     *         point; and a positive integer if this point is greater than the
     *         argument point
     */
    public int compareTo(Point that) {
        if (this.y == that.y && this.x == that.x) return 0;
        else if (this.y < that.y) return -1;
        else if (this.y == that.y && this.x < that.x) return -1;
        else return +1;
    }

    /**
     * Compares two points by the slope they make with this point.
     * The slope is defined as in the slopeTo() method.
     *
     * @return the Comparator that defines this ordering on points
     */
    public Comparator<Point> slopeOrder  = new SlopeOrder();
    
    private class SlopeOrder implements Comparator<Point> {
    	@Override
    	public int compare(Point a, Point b) {
    		double slope_of_a = slopeTo(a);
    		double slope_of_b = slopeTo(b);
    		if (slope_of_a < slope_of_b) return -1;
    		else if (slope_of_a == slope_of_b) return 0;
    		else return +1;
    	}
    };


    /**
     * Returns a string representation of this point.
     * This method is provide for debugging;
     * your program should not rely on the format of the string representation.
     *
     * @return a string representation of this point
     */
    public String toString() {
        return "(" + x + ", " + y + ")";
    }
    
    /*
	public static void main(String[] args) {
		// TODO Unit testing
		Point[] p = new Point[3];
		Point p_invoke = new Point(4, -1);
		p[0] = new Point(5,0);
		p[1] = new Point(5,0);
		p[2] = new Point(1,7);
		System.out.println(p[0].toString());
		System.out.println(p[1].toString());
		System.out.println(p[2].toString());
		System.out.println(p[0].slopeTo(p[1]));
		System.out.println(p[1].slopeTo(p[2]));
		System.out.println(p_invoke.slopeOrder.compare(p[0], p[1]));
	} */

}

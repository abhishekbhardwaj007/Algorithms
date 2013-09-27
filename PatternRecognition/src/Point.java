/*************************************************************************
 * Name:
 * Email:
 *
 * Compilation:  javac Point.java
 * Execution:
 * Dependencies: StdDraw.java
 *
 * Description: An immutable data type for points in the plane.
 *
 *************************************************************************/

import java.util.Comparator;

public class Point implements Comparable<Point> {

    // compare points by slope
    public final Comparator<Point> SLOPE_ORDER = new SlopeOrder();       // YOUR DEFINITION HERE

    private final int x;                              // x coordinate
    private final int y;                              // y coordinate

    // create the point (x, y)
    public Point(int x, int y) {
        /* DO NOT MODIFY */
        this.x = x;
        this.y = y;
    }
    
	private static double abs(double a) {
		return (a <= 0.0) ? 0.0 - a : a;
	}

    // plot this point to standard drawing
    public void draw() {
        /* DO NOT MODIFY */
        StdDraw.point(x, y);
    }

    // draw line between this point and that point to standard drawing
    public void drawTo(Point that) {
        /* DO NOT MODIFY */
        StdDraw.line(this.x, this.y, that.x, that.y);
    }

    // slope between this point and that point
    public double slopeTo(Point that) {
        
    	// Slope between point and itself
    	if (this.equals(that)) {
    		return Double.NEGATIVE_INFINITY;
    	}
    	
       	// Slope between 2 points at same horizontal level
    	if (this.x == that.x) {
    		return Double.POSITIVE_INFINITY;
    	}
    	
       	// Slope between 2 points at same vertical level
    	if (this.y == that.y) {
    		return 0.0;
    	}
    	
    	return ((double)(this.y - that.y) / (this.x - that.x));
    }

    private class SlopeOrder implements Comparator<Point> {
    	
    	public int compare(Point v, Point w) {
    		
    		if ((v == null) || (w == null)) {
    			throw new java.lang.NullPointerException();
    		}
    		
    		double sV = Point.this.slopeTo(v);
    		double sW = Point.this.slopeTo(w);
    		
    		if ((sV == Double.NEGATIVE_INFINITY) && (sW == Double.NEGATIVE_INFINITY)) {
    			return 0;
    		}
    		
    		if ((sV == Double.POSITIVE_INFINITY) && (sW == Double.POSITIVE_INFINITY)) {
    			return 0;
    		}
    		
    		if ((sV == 0.0) && (sW == 0.0)) {
    			return 0;
    		}
    		
    		if (abs(sV - sW) <= 0.000001) {
    			return 0;
    		}
    		
    		if (sV < sW) {
    			return -1;
    		}
    		
    		if (sV > sW) {
    			return 1;
    		}
    		
    		return 0;
    	}
    }
    
    
    // is this point lexicographically smaller than that one?
    // comparing y-coordinates and breaking ties by x-coordinates
    public int compareTo(Point that) {
        
    	if (that == null)
    	{
    		throw new java.lang.NullPointerException();
    	}
    	
    	// less than case
    	if (this.y < that.y) {
    		return -1;
    	}
    	
    	if (this.y == that.y) {
    		
    		// less than
    		if (this.x < that.x) {
    			return -1;
    		}
    		else if (this.x == that.x) {
    			return 0;
    		}
    		else
    		{
    			return 1;
    		}
    	}
    	
    	if (this.y > that.y) {
    		return 1;
    	}
    	
    	return 0;
    }

    // return string representation of this point
    public String toString() {
        /* DO NOT MODIFY */
        return "(" + x + ", " + y + ")";
    }
    
    private boolean equals(Point that) {
    	
    	if ((this.x == that.x) && (this.y == that.y)) {
    		return true;
    	}
    	
    	return false;
    }

    // unit test
    public static void main(String[] args) {
        /* YOUR CODE HERE */
    }
}


public class PointSET {

	private SET<Point2D> Set;
	private int N;

	// construct an empty set of points
	public PointSET()         {

		Set = new SET<Point2D>();
		N   = 0;
	}

	private double abs(double in) {
		
		if (in < 0) {
			return -in;
		}
		
		return in;
	}
	
	// is the set empty?
	public boolean isEmpty()     {
		return (N == 0);
	}

	// number of points in the set
	public int size()  {
		return N;
	}

	// add the point p to the set (if it is not already in the set)
	public void insert(Point2D p)    {
		
		if (!Set.contains(p)) {
			N++;
			Set.add(p);
		}
	}

	// does the set contain the point p?
	public boolean contains(Point2D p)   {
		return Set.contains(p);
	}

	// draw all of the points to standard draw
	public void draw()  {
		
		 StdDraw.setPenColor(StdDraw.BLACK);
		 StdDraw.setPenRadius(.01);
		 
		 for (Point2D p : Set) {
			 StdDraw.point(p.x(), p.y());
		 }

	}

	// all points in the set that are inside the rectangle
	public Iterable<Point2D> range(RectHV rect) {

		Stack<Point2D> Stack = new Stack<Point2D>();

		for (Point2D p : Set) {

			if (rect.contains(p)) {
				Stack.push(p);
			}
		}

		return Stack;
	}

	// a nearest neighbor in the set to p; null if set is empty
	public Point2D nearest(Point2D p)        {
		
		double min = Double.MAX_VALUE;
		double dist = 0.0;
		Point2D minPoint = null;
		
		for (Point2D Point : Set) {
				
				dist = p.distanceTo(Point);
				
				if (dist < min) {
					min      = dist;
					minPoint = Point;
				}
			}
	
		return minPoint;
	}

}

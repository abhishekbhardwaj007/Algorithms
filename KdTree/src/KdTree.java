
public class KdTree {

	private Node Root;
	private int  N;

	private static class Node {
		private Point2D p;      // the point
		private RectHV rect;    // the axis-aligned rectangle corresponding to this node
		private Node lb;        // the left/bottom subtree
		private Node rt;        // the right/top subtree

		public Node(Point2D p, RectHV rect) {
			this.p = p;
			this.rect = rect;
		}
	}

	// construct an empty set of points
	public KdTree()         {
		N   = 0;
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
		Root = insert(Root, new RectHV(0.0, 0.0, 1.0, 1.0), p, 0, true);
	}

	private Node insert(Node TreeRoot, RectHV ParentRect, Point2D PointToInsert, int level, boolean isLeftChild) {

		if (TreeRoot == null) {
			RectHV rect = findChildRect(PointToInsert, ParentRect, level, isLeftChild);
			Node n = new Node(PointToInsert, rect);
			System.out.println(n.rect);
			return n;
		}

		int compare;

		// Comparing x-coordinate if level is even and y if level is odd
		if (level % 2 == 0) {
			compare = Point2D.X_ORDER.compare(PointToInsert, TreeRoot.p);
		}
		else {
			compare = Point2D.Y_ORDER.compare(PointToInsert, TreeRoot.p);
		}

		if (compare < 0) {
			TreeRoot.lb = insert(TreeRoot.lb, TreeRoot.rect, PointToInsert, level + 1, true);
		}
		else if (compare > 0) {
			TreeRoot.rt = insert(TreeRoot.rt, TreeRoot.rect, PointToInsert, level + 1, false);
		}
		else if (compare == 0) {
			return TreeRoot;
		}

		return TreeRoot;
	}

	private RectHV findChildRect(Point2D Point, RectHV ParentRectHV, int level, boolean isLeftChild) {

		if (level % 2 == 0) {

			if (isLeftChild) {

				return new RectHV(ParentRectHV.xmin(), ParentRectHV.ymin(), Point.x(), ParentRectHV.ymax());
			}
			else {
				return new RectHV(0.0, 0.0, Point.x(), ParentRectHV.ymin());
			}
		}
		else {
			if (isLeftChild) {

				return new RectHV(ParentRectHV.xmin(), Point.y(), ParentRectHV.xmax(), ParentRectHV.ymax());
			}
			else {
				
				return new RectHV(Point.x(), Point.y(), 1.0, 1.0);
			}
		}
	}

	// does the set contain the point p?
	public boolean contains(Point2D p)   {
		return (get(p) != null);
	}

	// return value associated with the given key, or null if no such key exists
	private Point2D get(Point2D Point) {
		return get(Root, Point, 0);
	}

	private Point2D get(Node TreeRoot, Point2D PointToGet, int level) {
		if (TreeRoot == null) return null;

		int compare;

		// Comparing x-coordinate if level is even and y if level is odd
		if (level % 2 == 0) {
			compare = Point2D.X_ORDER.compare(PointToGet, TreeRoot.p);
		}
		else {
			compare = Point2D.Y_ORDER.compare(PointToGet, TreeRoot.p);
		}

		if      (compare < 0) return get(TreeRoot.lb, PointToGet, level + 1);

		else if (compare > 0) return get(TreeRoot.rt, PointToGet, level + 1);

		else              return TreeRoot.p;
	}

	// draw all of the points to standard draw
	public void draw()                  {

	}

	// all points in the set that are inside the rectangle
	public Iterable<Point2D> range(RectHV rect) {
		return null;
	}

	// a nearest neighbor in the set to p; null if set is empty
	public Point2D nearest(Point2D p)        {
		return null;
	}

	public static void main(String[] Args) {

		KdTree Kd = new KdTree();

		Point2D P = new Point2D(0.7, 0.2);
		Kd.insert(P);
		System.out.println(Kd.contains(P));

		P = new Point2D(0.5, 0.4);
		Kd.insert(P);
		System.out.println(Kd.contains(P));

		P = new Point2D(0.2, 0.3);
		Kd.insert(P);
		System.out.println(Kd.contains(P));

		P = new Point2D(0.4, 0.7);
		Kd.insert(P);
		System.out.println(Kd.contains(P));

		P = new Point2D(0.9, 0.6);
		Kd.insert(P);
		System.out.println(Kd.contains(P));
	}
}

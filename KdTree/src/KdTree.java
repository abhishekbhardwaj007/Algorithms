
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
			// System.out.println(n.rect);
			N++;
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

			if (TreeRoot.p.equals(PointToInsert)) {
				TreeRoot.p = PointToInsert;
			}
			else {
				TreeRoot.rt = insert(TreeRoot.rt, TreeRoot.rect, PointToInsert, level + 1, false);
			}
		}

		return TreeRoot;
	}

	private RectHV findChildRect(Point2D Point, RectHV ParentRectHV, int level, boolean isLeftChild) {

		//System.out.println("Parent Rect  " + ParentRectHV);
		if (level % 2 == 0) {

			if (isLeftChild) {

				return new RectHV(ParentRectHV.xmin(), ParentRectHV.ymin(), Point.x(), ParentRectHV.ymax());
			}
			else {
				return new RectHV(ParentRectHV.xmin(), ParentRectHV.ymax(), Point.x(), 1.0);
			}
		}
		else {
			if (isLeftChild) {

				return new RectHV(ParentRectHV.xmin(), ParentRectHV.ymin(), ParentRectHV.xmax(), Point.y());
			}
			else {

				return new RectHV(ParentRectHV.xmax(), ParentRectHV.ymin(), 1.0, Point.y());
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

		if (TreeRoot.p.equals(PointToGet)) return TreeRoot.p;

		int compare;

		// Comparing x-coordinate if level is even and y if level is odd
		if (level % 2 == 0) {
			compare = Point2D.X_ORDER.compare(PointToGet, TreeRoot.p);
		}
		else {
			compare = Point2D.Y_ORDER.compare(PointToGet, TreeRoot.p);
		}

		if      (compare < 0) return get(TreeRoot.lb, PointToGet, level + 1);

		else if (compare >= 0) return get(TreeRoot.rt, PointToGet, level + 1);

		// Should never reach here
		else    return null;

	}

	// draw all of the points to standard draw
	public void draw()                  {
		draw(Root, 0);
	}

	private void draw(Node n, int level) {

		if (n == null) {
			return;
		}

		// left tree
		draw(n.lb, level + 1);

		// root
		//System.out.println("Drawing " + n.p + " " + n.rect);
		if (level % 2 == 0) {
			StdDraw.setPenColor(StdDraw.RED);
			StdDraw.setPenRadius();
			//System.out.println("Drawing Line " + n.rect.xmax() + " " + n.rect.ymin() + " " + n.rect.xmax() + " " + n.rect.ymax());
			StdDraw.line(n.rect.xmax(), n.rect.ymin(), n.rect.xmax(), n.rect.ymax());
		}
		else {
			StdDraw.setPenColor(StdDraw.BLUE);
			StdDraw.setPenRadius();
			//System.out.println("Drawing Line " + n.rect.xmin() + " " + n.rect.ymax() + " " + n.rect.xmax() + " " + n.rect.ymax());
			StdDraw.line(n.rect.xmin(), n.rect.ymax(), n.rect.xmax(), n.rect.ymax());
		}

		// right tree
		draw(n.rt, level + 1);
	}
	// all points in the set that are inside the rectangle
	public Iterable<Point2D> range(RectHV rect) {

		Stack<Point2D> Stack = new Stack<Point2D>();

		if (Root == null) {
			return Stack;
		}

		range(Root, rect, Stack, 0);

		return Stack;
	}

	private void range(Node n, RectHV rect, Stack<Point2D> st, int level) {

		if (n == null) {
			return;
		}
		
		// Add point if it belongs to stack
		if (rect.contains(n.p)) {
			st.push(n.p);
		}
		
		int i;
		i = GoLeftOrRightOrBoth(rect, n, level);
		
		// Both
		if (i == 2) {
			range(n.lb, rect, st, level + 1);
			range(n.rt, rect, st, level + 1);
		}
		else if (i == 0) { // left
			range(n.lb, rect, st, level + 1);
		}
		else if (i == 1) { // right
			range(n.rt, rect, st, level + 1);
		}
		
	}

	//
	// returns 0 if we have to go left
	// returns 1 if we have to go right
	// returns 2 if we should go both
	//
	private int GoLeftOrRightOrBoth(RectHV rect, Node n, int level) {
		
		if (level%2 == 0) {
			
			if ((n.p.x() >= rect.xmin()) && (rect.xmax() >= n.p.x())) {
				return 2;
			}
			else if (n.p.x() > rect.xmax()) {
				return 0;
			}
			else if (n.p.x() < rect.xmin()) {
				return 1;
			}
			else {
				assert false;
			}
			
			return 2;
		}
		else {
			
			if ((n.p.y() >= rect.ymin()) && (rect.ymax() >= n.p.y())) {
				return 2;
			}
			else if (n.p.y() > rect.ymax()) {
				return 0;
			}
			else if (n.p.y() < rect.ymin()) {
				return 1;
			}
			else {
				assert false;
			}
			
			return 2;
		}
	}
	

	// a nearest neighbor in the set to p; null if set is empty
	public Point2D nearest(Point2D p)        {

		// Initing variables that will keep state
		double  MinDistance = Double.MAX_VALUE;

		// In case no tree return null itself
		if (Root == null) {
			return null;
		}

		return nearest(p, Root, Root.p, MinDistance, 0);
	}

	private Point2D nearest(Point2D p, Node n, Point2D MinPoint, double MinDistance, int level) {

		Point2D MinPointLocal = MinPoint;

		if (n == null) {
			return MinPointLocal;
		}

		if (p.equals(n.p)) {
			return n.p;
		}

		double dist = p.distanceTo(n.p);
		boolean isOnLeft = isPointInLeftTree(p, n, level);

		if (dist <= MinDistance) {
			MinPointLocal = n.p;
			MinDistance   = dist;
		}

		if ((n.lb == null) && (n.rt == null)) {
			return MinPointLocal;
		}
		
		if (isOnLeft) {
			double dist2;

			MinPointLocal = nearest(p, n.lb, MinPointLocal, MinDistance, level + 1);
			
			MinDistance = p.distanceTo(MinPointLocal);
			dist2       = n.rect.distanceTo(p);
			
			if (dist2 <= MinDistance) {
				MinPointLocal = nearest(p, n.rt, MinPointLocal, MinDistance, level + 1);
			}
		}
		else {
			double dist2;

			MinPointLocal = nearest(p, n.rt, MinPointLocal, MinDistance, level + 1);
			
			MinDistance = p.distanceTo(MinPointLocal);
			dist2       = n.rect.distanceTo(p);
			
			if (dist2 <= MinDistance) {
				MinPointLocal = nearest(p, n.lb, MinPointLocal, MinDistance, level + 1);
			}
		}

		return MinPointLocal;
	}

	private boolean isPointInLeftTree(Point2D p, Node n, int level) {

		if (level % 2 == 0) {

			if (p.x() < n.rect.xmax()) {
				return true;
			}
			else {
				return false;
			}
		}
		else {
			if (p.y() < n.rect.ymax()) {
				return true;
			}
			else {
				return false;
			}	
		}
	}

	public static void main(String[] Args) {

		String filename = Args[0];
		In in = new In(filename);


		// initialize the two data structures with point from standard input
		PointSET brute = new PointSET();
		KdTree kdtree = new KdTree();
		while (!in.isEmpty()) {
			double x = in.readDouble();
			double y = in.readDouble();
			Point2D p = new Point2D(x, y);
			kdtree.insert(p);
			brute.insert(p);
		}


		kdtree.draw();
		System.out.println(kdtree.nearest(new Point2D(0.35, 0.29)));
		/*
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

		System.out.println(Kd.size());

		Kd.draw();

		RectHV rect = new RectHV(0.0, 0.0, 0.5, 0.5);

		Iterable<Point2D> it = Kd.range(rect);

		for(Point2D p : it) {
			System.out.println(p);
		}

		System.out.println("Nearest neighbour : " + Kd.nearest(new Point2D(0.2, 0.3)));	


		KdTree Kd = new KdTree();

		Point2D P = new Point2D(0.206107 , 0.095492);
		Kd.insert(P);
		System.out.println(Kd.contains(P));

		P = new Point2D(0.975528, 0.654508);
		Kd.insert(P);
		System.out.println(Kd.contains(P));

		P = new Point2D(0.024472 , 0.345492);
		Kd.insert(P);
		System.out.println(Kd.contains(P));


		P = new Point2D(0.793893,  0.095492);
		Kd.insert(P);
		System.out.println(Kd.contains(P));

		P = new Point2D(0.793893 , 0.904508);
		Kd.insert(P);
		System.out.println(Kd.contains(P));


		P = new Point2D(0.975528 , 0.345492);
		Kd.insert(P);
		System.out.println(Kd.contains(P));


		P = new Point2D(0.206107 , 0.904508);
		Kd.insert(P);
		System.out.println(Kd.contains(P));

		P = new Point2D(0.500000 , 0.000000);
		Kd.insert(P);
		System.out.println(Kd.contains(P));

		P = new Point2D(0.024472 , 0.654508);
		Kd.insert(P);
		System.out.println(Kd.contains(P));

		P = new Point2D(0.500000 , 1.000000);
		Kd.insert(P);
		System.out.println(Kd.contains(P));

		System.out.println(Kd.size());

		Kd.draw();


		RectHV rect = new RectHV(0.0, 0.0, 0.81, 0.3);

		Iterable<Point2D> it = Kd.range(rect);

		for(Point2D p : it) {
			System.out.println(p);
		}

		System.out.println("Nearest neighbour : " + Kd.nearest(new Point2D(0.206107, 0.095492)));

		 */
	}


}

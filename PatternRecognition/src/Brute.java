
public class Brute {

	private static double abs(double a) {
		return (a <= 0.0) ? 0.0 - a : a;
	}

	public static void main(String[] args) {

		int i, j, k, l;
		boolean found;

		// Opening file stream
		In in = new In(args[0]);

		int[] A = in.readAllInts();

		int N = A[0];

		Point[] PointArray = new Point[N];
		int index = 0;

		for (int m = 1; m < A.length; m += 2) {
			PointArray[index++] = new Point(A[m], A[m + 1]);
		}

		for (i = 0; i < PointArray.length; i++) {

			for (j = (i + 1); j < PointArray.length; j++) {

				for (k = (j + 1); k < PointArray.length; k++) {

					for (l = (k + 1); l < PointArray.length; l++) {

						Point p = PointArray[i];
						Point q = PointArray[j];
						Point r = PointArray[k];
						Point s = PointArray[l];
						
						if ((abs(p.slopeTo(q) - p.slopeTo(r)) <= 0.000001) && (abs(p.slopeTo(q) - p.slopeTo(s)) <= 0.000001)) {

							System.out.println(p + " -> " + q + " -> " + r + " -> " + s);
						}
					

					}
				}
			}
		}
        
		
	}

}

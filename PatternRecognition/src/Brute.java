import java.util.Arrays;


public class Brute {

	private static double abs(double a) {
		return (a <= 0.0) ? 0.0 - a : a;
	}
	
	private static void PrintAndDrawPoints(Point[] Points) {
		Arrays.sort(Points, 0, Points.length);
		
		System.out.println(Points[0] + " -> " + Points[1] + " -> " + Points[2] + " -> " + Points[3]);
		
		// Draw the line
		Points[0].drawTo(Points[3]);
	}

	public static void main(String[] args) {

		int i, j, k, l;
		
		Point[] P = new Point[4];
		
		// Opening file stream
		In in = new In(args[0]);

		int[] A = in.readAllInts();

		int N = A[0];

		Point[] PointArray = new Point[N];
		int index = 0;

		for (int m = 1; m < A.length; m += 2) {
			PointArray[index++] = new Point(A[m], A[m + 1]);
		}
		
		StdDraw.setXscale(0, 32768);
		StdDraw.setYscale(0, 32768);
		
		for (i = 0; i < PointArray.length; i++) {

			for (j = (i + 1); j < PointArray.length; j++) {

				for (k = (j + 1); k < PointArray.length; k++) {

					for (l = (k + 1); l < PointArray.length; l++) {

						P[0] = PointArray[i];
						P[1] = PointArray[j];
						P[2] = PointArray[k];
						P[3] = PointArray[l];
						
						if ((abs(P[0].slopeTo(P[1]) - P[0].slopeTo(P[2])) <= 0.000001) && (abs(P[0].slopeTo(P[1]) - P[0].slopeTo(P[3])) <= 0.000001)) {
							
							PrintAndDrawPoints(P);
						}
						else if ((P[0].slopeTo(P[1]) == Double.POSITIVE_INFINITY) && (P[0].slopeTo(P[2]) == Double.POSITIVE_INFINITY) && (P[0].slopeTo(P[3]) == Double.POSITIVE_INFINITY)) {
							PrintAndDrawPoints(P);
						}
					}
				}
			}
		}
	}

}

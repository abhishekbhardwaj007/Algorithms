import java.util.Arrays;


public class Fast {


	private static double abs(double a) {
		return (a <= 0.0) ? 0.0 - a : a;
	}

	private static int FindAndPrintSequence(Point P, Point[] Copy, int start)
	{
		double Slope = P.slopeTo(Copy[start]);

		int end = start + 1;

		while (end < Copy.length)
		{
			if ((abs(Slope -P.slopeTo(Copy[end])) <= 0.000001) || ((Slope == Double.POSITIVE_INFINITY) && (P.slopeTo(Copy[end]) == Double.POSITIVE_INFINITY)))
			{
				end++;
			}
			else
			{
				break;
			}
		}

		if (((end - start) >= 3) && (P.compareTo(Copy[start]) == -1))
		{
			System.out.print(P);

			for (int k = start; k < end; k++)
			{
				System.out.print(" -> " + Copy[k]);
			}
			
			System.out.println("");
			
			//
			// Draw points
			//
			P.drawTo(Copy[end - 1]);
			
			// Indice to search from next
			//return end;
		}
		
		// No segment found
		// return start;
		return (end - 1);
	}

	private static void PrintArray(Point[] PointArray)
	{
		for (int i = 0; i < PointArray.length; i++)
		{
			System.out.print(PointArray[i] + " ");
		}

		System.out.println("");
	}

	
	public static void main(String[] args) {

		int i, j;

		// Opening file stream
		In in = new In(args[0]);

		int[] A = in.readAllInts();

		int N = A[0];

		Point[] PointArray = new Point[N];
		Point[] Copy = new Point[N];
		int index = 0;

		for (int m = 1; m < A.length; m += 2) {
			PointArray[index++] = new Point(A[m], A[m + 1]);
		}

		// Get original and Copy in natural sorted order
		Arrays.sort(PointArray);

		StdDraw.setXscale(0, 32768);
		StdDraw.setYscale(0, 32768);
		
		// Debugging
		/*
		System.out.println("Natural Sort :");
		PrintArray(PointArray);
		*/
		 

		for (i = 0; i < PointArray.length; i++)
		{
			Copy[i] = PointArray[i];
		}

		for (i = 0; i < PointArray.length; i++)
		{
			Point P = PointArray[i];

			// Sort by slope order
			Arrays.sort(Copy, P.SLOPE_ORDER);

			// Debugging
			// PrintArray(Copy);

			// Keep finding subsequences whose length >= 3 and start is GREATER than reference point
			for (j = 1; j < Copy.length; j++)
			{
				int end;
				end = FindAndPrintSequence(P, Copy, j);
				
				j = end;
			}
	
			// Back to natural
			Arrays.sort(Copy);
		}
	}
}

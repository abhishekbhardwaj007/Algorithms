import java.util.Arrays;


public class Fast {

	private static int FindFirstSlopeIndex(Point Ref, Point[] PointArray, double Slope) {
		
		int First = -1;
		for (int i = 0; i < PointArray.length; i++)
		{
			if (Ref.slopeTo(PointArray[i]) == Slope) {
				First = i;
				break;
			}
		}
		
		return First;
	}
	
	private static int FindLastSlopeIndex(int First, Point Ref, Point[] PointArray, double Slope) {
		
		int Last = First;
		
		for (int i = First + 1; i < PointArray.length; i++)
		{
			if (Ref.slopeTo(PointArray[i]) != Slope) {
				Last = i - 1;
				break;
			}
			
			Last = i;
		}
		
		return Last;
	}
	
	public static void main(String[] args) {

		int i, j;
		
		int First;
		int Last;
		
		// Opening file stream
		In in = new In(args[0]);

		int[] A = in.readAllInts();

		int N = A[0];

		Point[] PointArray = new Point[N];
		Point[] Copy = new Point[N];
		boolean[] processed = new boolean[N];
		int index = 0;

		for (int m = 1; m < A.length; m += 2) {
			PointArray[index++] = new Point(A[m], A[m + 1]);
		}
		
		Arrays.sort(PointArray);
		
		for (i = 0; i < PointArray.length; i++)
		{
			Copy[i] = PointArray[i];
		}
		
		
		for (i = 0; i < PointArray.length; i++) {
			
			Point P = PointArray[i];
			
			Arrays.sort(Copy, P.SLOPE_ORDER);
			
			
			/*
			for (j = 0; j < Copy.length; j++) {
				System.out.print(Copy[j]);
				System.out.print("   " + "slope = " + P.slopeTo(Copy[j]) + " | ");
			}
			*/
			
			
			First = FindFirstSlopeIndex(P, Copy, 0);
			
			if (First != -1)
			{
				Last = FindLastSlopeIndex(First, P, Copy, 0);
				
				System.out.print(P);
				
				for (j = First ; j <= Last; j++)
				{
					System.out.print(" -> " + Copy[j]);
				}
				

				System.out.println("");
			}
			
			First = FindFirstSlopeIndex(P, Copy, Double.POSITIVE_INFINITY);
			
			if (First != -1)
			{
				Last = FindLastSlopeIndex(First, P, Copy, Double.POSITIVE_INFINITY);
				
				System.out.print(P);
				
				for (j = First ; j <= Last; j++)
				{
					System.out.print(" -> " + Copy[j]);
				}
				
				System.out.println("");
			}
			
			
		}
        
	}
}

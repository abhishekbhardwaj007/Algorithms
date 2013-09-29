import java.util.Arrays;

import javax.print.attribute.standard.Finishings;


public class Fast {

	private static int FindFirstZeroIndex(Point Ref, Point[] PointArray) {
		
		int First = -1;
		for (int i = 0; i < PointArray.length; i++)
		{
			if (Ref.slopeTo(PointArray[i]) == 0) {
				First = i;
				break;
			}
		}
		
		return First;
	}
	
	private static int FindLastZeroIndex(int First, Point Ref, Point[] PointArray) {
		
		int Last = 0;
		for (int i = First + 1; i < PointArray.length; i++)
		{
			if (Ref.slopeTo(PointArray[i]) != 0) {
				Last = i - 1;
				break;
			}
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
			
			System.out.println(P);
			
			/*
			for (j = 0; j < Copy.length; j++) {
				System.out.print(Copy[j]);
				System.out.print("   " + "slope = " + P.slopeTo(Copy[j]) + " | ");
			}
			*/
			
			
			First = FindFirstZeroIndex(P, Copy);
			
			if (First != -1)
			{
				Last = FindLastZeroIndex(First, P, Copy);
				
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

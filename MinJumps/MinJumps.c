//
// Minimum Cost
// DP
// www.geeksforgeeks.org/minimum-number-of-jumps-to-reach-end-of-a-given-array/ 
//
#include <stdio.h>
#include <string.h>
#include <stdlib.h>

#define INT_MAX (int)((1<<31) - 1)

int MinJumps(int arr[], int n)
{
	int i,j;
	int* jumps;

	jumps = (int*) malloc(n * sizeof(int));

	jumps[0] = 0;

	for (i = 1; i < n; i++)
	{
		jumps[i] = INT_MAX;
		for (j = 0; j < i; j++)
		{
			if ((i <= (j + arr[j])) && (jumps[j] != INT_MAX))
			{
				jumps[i] = jumps[j] + 1;
				break;
			}
		}
	}

	free(jumps);
	return jumps[n-1];
}

// Driver program to test above function
int main()
{
    int arr[] = {1, 3, 6, 1, 0, 9};
    int size  = sizeof(arr)/sizeof(int);
    printf("Minimum number of jumps to reach end is %d ", MinJumps(arr,size));
    return 0;
}

